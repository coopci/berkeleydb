/*-
 * Copyright (c) 2010, 2019 Oracle and/or its affiliates.  All rights reserved.
 *
 * See the file LICENSE for license information.
 */

#include <errno.h>

#include "sqliteInt.h"
#include <db.h>

/* Internal DB functions that are used by adapter source files. */
extern int __db_check_chksum(ENV *, void *, DB_CIPHER *, u_int8_t *, void *,
    size_t, int);
extern void __db_chksum(void *, u_int8_t *, size_t, u_int8_t *, u_int8_t *);
extern int __env_encrypt(DB_ENV *, u_int8_t *, u_int8_t *, size_t);
extern int __env_encrypt_adj_size(DB_ENV *, size_t, size_t *);
extern int __env_decrypt(DB_ENV *, u_int8_t *, u_int8_t *, size_t);
extern int __env_ref_get(DB_ENV *, u_int32_t *);
extern int __os_closehandle(ENV *, DB_FH *);
extern int __os_cpu_count(void);
extern int __os_dirlist(ENV *, const char *, int, char ***, int *);
extern void __os_dirfree(ENV *, char **, int);
extern int __os_exists(ENV *, const char *, int *);
extern int __os_fileid(ENV *, const char *, int, u_int8_t *);
extern int __os_io(ENV *, int, DB_FH *, db_pgno_t, u_int32_t, u_int32_t,
    u_int32_t, u_int8_t *, size_t *);
extern int __os_ioinfo(ENV *, const char *, DB_FH *, u_int32_t *, u_int32_t *,
    u_int32_t *);
extern int __os_mkdir(ENV *, const char *, int);
extern int __os_open(ENV *, const char *, u_int32_t, u_int32_t, int, DB_FH **);
extern int __os_read(ENV *, DB_FH *, void *, size_t, size_t *);
extern int __os_rename(ENV *, const char *, const char *, u_int32_t);
extern int __os_seek(ENV *, DB_FH *, db_pgno_t, u_int32_t, off_t);
extern void __os_stack_text(const ENV *, char *, size_t, unsigned, unsigned);
extern int __os_unlink(ENV *, const char *, int);
extern int __os_write(ENV *, DB_FH *, void *, size_t, size_t *);
extern void __os_yield(ENV *, u_long, u_long);

#ifdef BDBSQL_SHARE_PRIVATE
/* BDBSQL_SHARE_PRIVATE implies BDBSQL_SINGLE_PROCESS */
#define	BDBSQL_SINGLE_PROCESS
#endif

#define	INTKEY_BUFSIZE	(sizeof(i64) + 2) /* We add 2 bytes to negatives. */
/* MULTI_BUFSIZE needs to be at least as large as the maximum page size. */
#define	MULTI_BUFSIZE	SQLITE_MAX_PAGE_SIZE
#define	DBNAME_SIZE	20
#define	NUMMETA		16
#define	NUM_DB_PRAGMA	30
#define	CURSOR_BUFSIZE	32 /* For holding index keys. */
/* This should match SQLite VFS.mxPathname */
#define	BT_MAX_PATH 512

/*
 * Sizes for save error information:
 *	ERR_SAVE_SIZE is the fixed size of the save error message buffer in
 *	BtShared.err_msg.  Allocating it just once eliminates the problems which
 *	occur in the sqlite do_malloc_tests; which use faultsimConfig to test
 *	every malloc failure error path (for a few test scripts).
 *	ERRFILE_SAVE_SIZE is used for BtShared.err_file.
 */
#define ERR_SAVE_SIZE		1024
#define ERRFILE_SAVE_SIZE	BT_MAX_PATH

#define BT_MAX_SEQ_NAME 128

/*
 * If greater than 0, records larger than or equal to N bytes will be stored
 * in an alternate format that improves the reading and updating speed of large
 * records.
 */
#ifndef BDBSQL_LARGE_RECORD_OPTIMIZATION
# define BDBSQL_LARGE_RECORD_OPTIMIZATION 0
#endif

/*
 * The default size of the Berkeley DB environment's logging area, in
 * bytes.
 */
#ifndef BDBSQL_LOG_REGIONMAX
# define BDBSQL_LOG_REGIONMAX (300 * 1024)
#endif

/*
 * The default policy for enabling the transactional bulk insert
 * optimization.
 */
#ifndef BDBSQL_TXN_BULK_DEFAULT
# define BDBSQL_TXN_BULK_DEFAULT 0
#endif

/*
 * The default pages number for incremental vacuum
 */
#ifndef BDBSQL_INCR_VACUUM_PAGES
# define BDBSQL_INCR_VACUUM_PAGES 128
#endif

/*
 * The default fill percent for vacuum
 */
#ifndef BDBSQL_VACUUM_FILLPERCENT
# define BDBSQL_VACUUM_FILLPERCENT 85
#endif

#ifndef UINT32_MAX                      /* Maximum 32-bit unsigned. */
#define	UINT32_MAX      4294967295U
#endif
#ifndef INT64_MAX
#define	INT64_MAX ((((i64)0x7fffffff) << 32) | 0xffffffff)
#endif
#ifndef GIGABYTE
#define	GIGABYTE 1073741824
#endif

#define	MAP_ERR(rc, ret, p)					\
	((rc != SQLITE_OK) ? rc : (ret == 0) ? SQLITE_OK :	\
	    dberr2sqlite(ret, p))

#define	MAP_ERR_LOCKED(rc, ret, p)					\
	((rc != SQLITE_OK) ? rc : (ret == 0) ? SQLITE_OK :	\
	    dberr2sqlitelocked(ret, p))

/* Declare custom functions added by Berkeley DB to SQL. */
int add_sequence_functions(sqlite3 *db);

typedef int (*compareFunc)(void*,int,const void*,int,const void*);

typedef struct {
	/*
	 * There are two types of tables stored in this cache:
	 * * Normal tables created by SQLite. These have 8 char names.
	 * * Tables used to handle sequences, which can have arbitrary names.
	 */
#define	CACHE_KEY_SIZE 9		/* 8 hex characters + NUL */
	char key[BT_MAX_SEQ_NAME];
	DB *dbp;
	int is_sequence;
	db_lockmode_t lock_mode;
	int created;
	void *cookie;
} CACHED_DB;

typedef struct {
	u32	cache;
	int64_t min_val;
	int64_t max_val;
	int64_t start_val;
	int32_t incr;
	u8	decrementing;
	u8	used;
	int64_t val; /* If not using a cache - this is the last value. */
	char    name[BT_MAX_SEQ_NAME];
	int32_t name_len;
	DB_SEQUENCE *handle; /* Never used directly from the DB cache key. */
} SEQ_COOKIE;

typedef struct {
	u32 value;
	u8 cached;
} CACHED_META;

typedef struct {
	char *value;
	u32 size;
	u32 offset;
} CACHED_PRAGMA;

typedef struct DELETED_TABLE DELETED_TABLE;
struct DELETED_TABLE {
	int iTable;
	DB_TXN *txn;
#ifdef BDBSQL_FILE_PER_TABLE
	int flag;
#define	DTF_DELETE	0x00
#define	DTF_DROP	0x01
#endif
	DELETED_TABLE *next;
};

#ifndef BDBSQL_SINGLE_THREAD
typedef struct {
	BtShared *pBt;
	KeyInfo *pKeyInfo;
	int iTable;
} TableInfo;
#endif

#ifdef BDBSQL_SHARE_PRIVATE
typedef struct {
	int fd;
	void *mapAddr;
	int generation;
	int readlock_count;
	int writelock_count;
	int write_waiting;
	int in_env_open;
	sqlite3_mutex *mutex;
} LockFileInfo;
#endif

typedef enum { CLEANUP_COMMIT, CLEANUP_ABORT, CLEANUP_CLOSE,
    CLEANUP_DROP_LOCKS, CLEANUP_GET_LOCKS } cleanup_mode_t;
/* There are three possible table types in SQLite. */
typedef enum { DB_STORE_NAMED, DB_STORE_TMP, DB_STORE_INMEM } storage_mode_t;
typedef enum { TRANS_NONE, TRANS_READ, TRANS_WRITE } txn_mode_t;
typedef enum { LOCKMODE_NONE, LOCKMODE_READ, LOCKMODE_WRITE } lock_mode_t;
typedef enum { NO_LSN_RESET, LSN_RESET_FILE } lsn_reset_t;
typedef enum { BDBSQL_REP_CLIENT, BDBSQL_REP_MASTER, BDBSQL_REP_UNKNOWN } rep_site_type_t;

/* Declarations for functions that are shared by adapter source files. */
int btreeBeginTransInternal(Btree *p, int wrflag);
void *btreeCreateIndexKey(BtCursor *pCur);
int btreeSetErrorFile(BtShared *pBt, const char *fname);
void btreeGetErrorFile(BtShared *pBt, char *errname);
Index *btreeGetIndex(Btree *p, int iTable);
int btreeGetPageCount(Btree *p, int **tables, u32 *pageCount, u32 *free, DB_TXN *txn);
int btreeGetUserTable(Btree *p, DB_TXN *pTxn, DB **pDb, int iTable);
int btreeGetTables(Btree *, int **, DB_TXN *);
int btreeLockSchema(Btree *p, lock_mode_t lockMode);
int btreeOpenEnvironment(Btree *p, int needLock);
int btreeOpenMetaTables(Btree *p, int *pCreating);
int btreeReopenEnvironment(Btree *p, int removingRep);
#ifndef SQLITE_OMIT_VACUUM
int btreeIncrVacuum(Btree *p, u_int32_t *truncatedPages);
int btreeVacuum(Btree *p, char **pzErrMsg);
void btreeFreeVacuumInfo(Btree *p);
#endif
int dberr2sqlite(int, Btree *p);
int closeDB(Btree *p, DB *dbp, u_int32_t flags);
void *allocateCursorIndex(BtCursor *pCur, u_int32_t amount);
int splitIndexKey(BtCursor *pCur);
int isDupIndex(int flags, int storage, KeyInfo *keyInfo, DB *db);
#ifdef BDBSQL_SHARE_PRIVATE
int btreeScopedFileLock(Btree *p, int iswrite, int dontreopen);
int btreeScopedFileUnlock(Btree *p, int iswrite);
int btreeHasFileLock(Btree *p, int iswrite);
#endif
#ifdef SQLITE_HAS_CODEC
int sqlite3CodecAttach(sqlite3*, int, const void*, int);
#endif
int getPersistentPragma(Btree *p, const char *pragma_name, char **value, 
    Parse *pParse);
int setPersistentPragma(Btree *p, const char *pragma_name, const char *value, 
    Parse *pParse);
int encodeI64(u_int8_t *buf, i64 num);
int cleanPragmaCache(Btree *p);
int getHostPort(const char *hpstr, char **host, u_int *port);
int setRepVerboseFile(BtShared *pBt, DB_ENV *dbenv, const char *fname,
    char *msg);
int unsetRepVerboseFile(BtShared *pBt, DB_ENV *dbenv, char **msg);
/* Returns the thread id as a void *, which needs to be freed. */
void *getThreadID(sqlite3 *db);
/* Checks if the thread id item identifies the current thread. */
int isCurrentThread(void *tid);
void btreeHandleDbError(
    const DB_ENV *dbenv, const char *errpfx, const char *msg);

#define	CLEAR_PWD(pBt)	do {						\
	memset((pBt)->encrypt_pwd, 0xff, (pBt)->encrypt_pwd_len);	\
	free((pBt)->encrypt_pwd);				\
	(pBt)->encrypt_pwd_len = 0;					\
	(pBt)->encrypt_pwd = NULL;					\
} while (0)

/*
 * There is some subtlety about which mutex to use: for shared handles, we
 * update some structures that are protected by the open mutex.  In-memory
 * databases all share the same g_tmp_env handle, so we need to make sure they
 * get it single-threaded (so the initial open is done once).
 *
 * However, we can't use the open mutex to protect transient database opens and
 * closes: we might already be holding locks in a shared environment when we
 * try to open the temporary env, which would lead to a lock/mutex deadlock.
 * We take a different static mutex from SQLite, previously used in the pager.
 */
#define	OPEN_MUTEX(store)	((store == DB_STORE_NAMED) ?	\
	SQLITE_MUTEX_STATIC_OPEN : SQLITE_MUTEX_STATIC_LRU)

#ifdef BDBSQL_FILE_PER_TABLE
/* Name of the metadata table in BDBSQL_FILE_PER_TABLE */
#define	BDBSQL_META_DATA_TABLE "metadata"
int getMetaDataFileName(const char *full_name, char **filename);
#endif

/*
 * BtShared --
 *	This is the BDBSQL shared data struct that 'controls' a BDB environment.
 *	Every Btree that refers to a sqlite table in an environment shares a
 *	pointer to this struct.
 *	
 *	Many of its fields are useful only for DB_STORE_NAMED databases.
 *
 *	The full_name field is the unique key for this struct. (At one time, the
 *	unique 20 byte fileid of the BDB SQL master DB was used as the key.
 *	Unfortunately, there is no fileid until the DB exists, so that had a
 *	race condition with simultaneous opens of a not-yet-existing db. #26708)
 *
 *	Most of its character strings are allocated from sqlite3 malloc memory,
 *	as from sqlite3_strdup().
 */
struct BtShared {
	/* These four fields are null unless dbStorage == DB_STORE_NAMED. */
	char *full_name;	/* Full path to the container DB file. */
	char *dir_name;		/* Environment home: full_name + "-journal". */
	char *orig_name;	/* 'User' name, e.g. from dbsql command line */
	char *short_name;	/* Points after last path-sep. of orig_name. */

	/*
	 * BDBSQL installs btreeHandleDbError to write messages to either:
	 *	- the filename mentioned in the bdbsql_error_file pragma, or
	 *	- sql-errors.txt in the %s-journal.db if DB_STORE_NAMED, or
	 *	- sql-errors.txt in the current directory, for other stores, or
	 *	- stderr if nowhere else can be written.
	 * Any changes to err_file and err_fp should be protected by err_mutex.
	 * The BtShared 'constructor' allocates err_msg.  This avoids needing to
	 * allocate memory for the message body while handling an error -- that
	 * would be problematic for failures during memory allocation.
	 */
	char *err_file;
	FILE *err_fp;
	sqlite3_mutex *err_mutex;
	char *err_msg;

	char *master_address;	/* Address of the replication master. */
	char *encrypt_pwd;
	lsn_reset_t lsn_reset;
	storage_mode_t dbStorage;
	u_int32_t env_oflags;
	DB_ENV *dbenv;
	int env_opened, encrypted, encrypt_pwd_len, last_table, need_open;
	/*
	 * Handles for the metadata DB, which holds the SQLite metadata for a
	 * file, and the tables DB, which is the Berkeley DB-internal database
	 * of sub-databases in a file.
	 */
	DB *metadb, *tablesdb;
	/* Caches persistent pragma values. */
	CACHED_PRAGMA pragma[NUM_DB_PRAGMA];
	sqlite3_mutex *pragma_cache_mutex;
	u8 cache_loaded;
	CACHED_META meta[NUMMETA];
	Hash db_cache;
#ifdef BDBSQL_SHARE_PRIVATE
	LockFileInfo lockfile;
#endif
	/*
	 * A unique name is assigned to each in memory table. This value is
	 * used to ensure that each BtShared object gets a unique identifier.
	 * NOTE: For DB_STORE_INMEM tables, despite sharing the same environment
	 * handle, the internal table name is unique because it comprises of
	 * both the uid and iTable.
	 */
	u_int32_t uid;
	u_int32_t flags;
	u_int32_t panic; /* If the environment is not in a usable state. */
	u_int32_t db_oflags;
	u_int32_t transactional;
	u_int32_t pageSize;
	u_int32_t pageCount;
	u_int32_t pageSizeFixed;
	u_int32_t cacheSize;
	u_int32_t logFileSize; /* In bytes */
	u_int32_t database_existed; /* Did the database file exist on open. */
	u_int32_t read_txn_flags; /* Flags passed to the read transaction. */
	/* Records >= blob_threshold stored as blob files.*/
	u_int32_t blob_threshold;
	u8 blobs_enabled; /* Whether this database can support blobs. */
	u8 autoVacuum; /* Is auto-vacuum enabled? */
	u8 incrVacuum; /* Is incremental vacuum enabled? */
	u8 resultsBuffer; /* Query results are stored in a in-memory buffer */
	u8 secureDelete; /* Overwrite deleted data */
	/* Non-recursive mutex required to access this struct */
	sqlite3_mutex *mutex;
	BtCursor *first_cursor;

	/* These store the linked list of shared DB_STORE_NAMED objects. */
	BtShared *pNextBtS;
	BtShared *pPrevBtS;
	Btree *btrees; /* A linked list of btrees that have been opened in this BtShared. */
	int nRef;
	int readonly;
	int repStartMaster; /* Start replication site as initial master? */
	FILE *repVerbFile; /* File for replication verbose output. */
	int repStarted; /* Replication is configured and started. */
	int repForceRecover; /* Force recovery on next open environment. */
	int single_process; /* If non-zero, keep all environment on the heap. */
	rep_site_type_t repRole; /* Whether this site is a master, client, unknown. */
	u_int32_t permFailures; /* Number of perm failures. */
	char *stat_filename; /* File to which statistics are printed. */
};

struct BtCursor {
	Btree *pBtree;
	int tableIndex;
	u_int32_t flags;
	u8 isDupIndex, isFirst, isIncrblobHandle, wrFlag;
	CACHED_DB *cached_db;
	DBC *dbc;
	DB_TXN *txn;
	struct KeyInfo *keyInfo;
	enum {
		CURSOR_INVALID, CURSOR_VALID, CURSOR_REQUIRESEEK, CURSOR_FAULT, CURSOR_SKIPNEXT
	} eState;
	int error, lastRes;
	i64 cachedRowid, savedIntKey, lastKey;
	DBT key, data, index;
	i64 nKey;
	u8 indexKeyBuf[CURSOR_BUFSIZE];
	u8 hints;
	DBT multiData;
	void *multiGetPtr, *multiPutPtr;
	void *threadID;
	int skipMulti;
	BtCursor *next;
};

struct Btree {
	struct BtShared *pBt;
	sqlite3 *db;

	int connected;		/* Set up with an open environment */
	DB_TXN *family_txn;	/* Makes txns and cursors lock-compatible. */
	DB_TXN *main_txn;	/* Base transaction for read and savepoint. */
	DB_TXN *read_txn;
	DB_TXN *savepoint_txn;
	int nSavepoint;		/* The number of open savepoints. */
#ifdef BDBSQL_SHARE_PRIVATE
	int maintxn_is_write;
#endif
	int vfsFlags;

	void* schema;		/* Opaque schema handle used by SQLite */
	void (*free_schema)(void*);	/* Destructor for schema */

	DELETED_TABLE *deleted_tables;

	struct VacuumInfo {
		DBT start;
		int iTable;
		struct VacuumInfo* next;
	} *vacuumInfo;       /* Keep incremental vacuum infomation */
	u8 inVacuum;	     /* True if vacuum is in progress */
	u8 needVacuum;	     /* True if the Btree needs vacuum in txn commit */
	u32 vacuumPages;     /* Num of pages for AutoVacuum/IncrVacuum */
	u32 fillPercent;     /* fillPercent for Vacuum */
	DBC *compact_cursor; /* Walks over table names during vacuum. */

	txn_mode_t inTrans;
	lock_mode_t schemaLockMode;
	DBC *schemaLock;
	u8 sharable;	/* True if we can share pBt with another db */
	u8 locked;	/* True if db currently has pBt locked */
	u8 txn_excl;	/* True if in an exclusive transaction */
	u8 txn_bulk;	/* True to enable the bulk loading optimization */
	u32 txn_priority;	/* Transaction priority. */
	int wantToLock;	/* Number of nested calls to sqlite3BtreeEnter() */
	int nBackup;	/* Number of backup operations reading this btree */
	u32 updateDuringBackup; /* An update was performed during a backup. */
	int readonly;
	Btree *pNext;
	Btree *pPrev;
};

/* Shared by btree.c and btmutex.c */
typedef enum {
	LOG_VERBOSE, LOG_DEBUG, LOG_NORMAL, LOG_RELEASE, LOG_NONE
} loglevel_t;

/*
 * The Makefile can override this default; e.g., -DCURRENT_LOG_LEVEL=LOG_VERBOSE
 */
#ifndef CURRENT_LOG_LEVEL
#define	CURRENT_LOG_LEVEL	LOG_RELEASE
#endif

#ifdef NDEBUG
#define	log_msg(...)	do { } while (0)
#else
/* Utility functions. */
void log_msg(loglevel_t level, const char *fmt, ...);
#endif

/* 
 * Common functions for internal DBSQL btree components (btree.c, vacuum.c, etc)
 */ 
int btreeFindOrCreateDataTable(Btree *, int *, CACHED_DB **, int); 
int btreeGetKeyInfo(Btree *p, int iTable, KeyInfo **pKeyInfo); 
int btreeTableNameToId(const char *subdb, int len, int *pid); 

/* 
 * Common macros for internal DBSQL btree components (btree.c, vacuum.c, etc)
 */ 
#define	pDbEnv		(pBt->dbenv)
#define	pMetaDb		(pBt->metadb)
#define	pTablesDb	(pBt->tablesdb)
#define	pFamilyTxn	(p->family_txn)
#define	pReadTxn	(p->read_txn)
#define	pMainTxn	(p->main_txn)
#define	pSavepointTxn	(p->savepoint_txn)

#ifdef BDBSQL_FILE_PER_TABLE
#define	FIX_TABLENAME(pBt, fileName, tableName) do {		\
	if (pBt->dbStorage == DB_STORE_NAMED) {			\
		fileName = tableName;				\
	} else							\
		fileName = pBt->short_name;			\
} while (0)
#else
#define	FIX_TABLENAME(pBt, fileName, tableName) do {		\
	fileName = pBt->short_name;				\
} while (0)
#endif

#define	GET_AUTO_COMMIT(pBt, txn) (((pBt)->transactional &&	\
	(!(txn) || (txn) == pFamilyTxn)) ? DB_AUTO_COMMIT : 0)

/*
 * If an update occurs while this Btree is also performing backup then
 * increase the updateDuringBackup counter.  This value is checked before
 * and after each backup step, and if it has increase then the backup
 * process is reset.
 */
#define	UPDATE_DURING_BACKUP(p)  \
    if (p->nBackup > 0)     \
	p->updateDuringBackup++;

