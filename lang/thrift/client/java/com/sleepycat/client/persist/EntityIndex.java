/*-
 * Copyright (c) 2002, 2019 Oracle and/or its affiliates.  All rights reserved.
 *
 * See the file LICENSE for license information.
 *
 */

package com.sleepycat.client.persist;

import java.util.Map;
import java.util.SortedMap;

import com.sleepycat.client.collections.StoredMap;
import com.sleepycat.client.collections.StoredSortedMap;
import com.sleepycat.client.SCursorConfig;
import com.sleepycat.client.SDatabase;
import com.sleepycat.client.SDatabaseEntry;
import com.sleepycat.client.SDatabaseException;
import com.sleepycat.client.SEnvironment;
import com.sleepycat.client.SEnvironmentConfig;
import com.sleepycat.client.SLockMode;
import com.sleepycat.client.SSecondaryDatabase;
import com.sleepycat.client.STransaction;

/**
 * The interface for accessing keys and entities via a primary or secondary
 * index.
 *
 * <p>{@code EntityIndex} objects are thread-safe.  Multiple threads may safely
 * call the methods of a shared {@code EntityIndex} object.</p>
 *
 * <p>An index is conceptually a <em>map</em>. {key:value} mappings are
 * stored in the index and accessed by key.  In fact, for interoperability with
 * other libraries that use the standard Java {@link Map} or {@link SortedMap}
 * interfaces, an {@code EntityIndex} may be accessed via these standard
 * interfaces by calling the {@link #map} or {@link #sortedMap} methods.</p>
 *
 * <p>{@code EntityIndex} is an interface that is implemented by several
 * classes in this package for different purposes.  Depending on the context,
 * the key type (K) and value type (V) of the index take on different meanings.
 * The different classes that implement {@code EntityIndex} are:</p>
 * <ul>
 * <li>{@link PrimaryIndex} maps primary keys to entities.</li>
 * <li>{@link SecondaryIndex} maps secondary keys to entities.</li>
 * <li>{@link SecondaryIndex#keysIndex} maps secondary keys to primary
 * keys.</li>
 * <li>{@link SecondaryIndex#subIndex} maps primary keys to entities, for the
 * subset of entities having a specified secondary key.</li>
 * </ul>
 *
 * <p>In all cases, the index key type (K) is a primary or secondary key class.
 * The index value type (V) is an entity class in all cases except for a {@link
 * SecondaryIndex#keysIndex}, when it is a primary key class.</p>
 *
 * <p>In the following example, a {@code Employee} entity with a {@code
 * MANY_TO_ONE} secondary key is defined.</p>
 *
 * <pre class="code">
 * {@literal @Entity}
 * class Employee {
 *
 *     {@literal @PrimaryKey}
 *     long id;
 *
 *     {@literal @SecondaryKey(relate=MANY_TO_ONE)}
 *     String department;
 *
 *     String name;
 *
 *     private Employee() {}
 * }</pre>
 *
 * <p>Consider that we have stored the entities below:</p>
 *
 * <div><table class="code" border="1" summary="">
 *   <tr><th colspan="3">Entities</th></tr>
 *   <tr><th>ID</th><th>Department</th><th>Name</th></tr>
 *   <tr><td>1</td><td>Engineering</td><td>Jane Smith</td></tr>
 *   <tr><td>2</td><td>Sales</td><td>Joan Smith</td></tr>
 *   <tr><td>3</td><td>Engineering</td><td>John Smith</td></tr>
 *   <tr><td>4</td><td>Sales</td><td>Jim Smith</td></tr>
 * </table></div>
 *
 * <p>{@link PrimaryIndex} maps primary keys to entities:</p>
 *
 * <pre class="code">
 * {@code PrimaryIndex<Long, Employee>} primaryIndex =
 *     store.getPrimaryIndex(Long.class, Employee.class);</pre>
 *
 * <div><table class="code" border="1" summary="">
 *   <tr><th colspan="4">primaryIndex</th></tr>
 *   <tr><th>Primary Key</th><th colspan="3">Entity</th></tr>
 *   <tr><td>1</td><td>1</td><td>Engineering</td><td>Jane Smith</td></tr>
 *   <tr><td>2</td><td>2</td><td>Sales</td><td>Joan Smith</td></tr>
 *   <tr><td>3</td><td>3</td><td>Engineering</td><td>John Smith</td></tr>
 *   <tr><td>4</td><td>4</td><td>Sales</td><td>Jim Smith</td></tr>
 * </table></div>
 *
 * <p>{@link SecondaryIndex} maps secondary keys to entities:</p>
 *
 * <pre class="code">
 * {@code SecondaryIndex<String, Long, Employee>} secondaryIndex =
 *     store.getSecondaryIndex(primaryIndex, String.class, "department");</pre>
 *
 * <div><table class="code" border="1" summary="">
 *   <tr><th colspan="4">secondaryIndex</th></tr>
 *   <tr><th>Secondary Key</th><th colspan="3">Entity</th></tr>
 *   <tr><td>Engineering</td><td>1</td><td>Engineering</td><td>Jane Smith</td></tr>
 *   <tr><td>Engineering</td><td>3</td><td>Engineering</td><td>John Smith</td></tr>
 *   <tr><td>Sales</td><td>2</td><td>Sales</td><td>Joan Smith</td></tr>
 *   <tr><td>Sales</td><td>4</td><td>Sales</td><td>Jim Smith</td></tr>
 * </table></div>
 *
 * <p>{@link SecondaryIndex#keysIndex} maps secondary keys to primary
 * keys:</p>
 *
 * <pre class="code">
 * {@code EntityIndex<String, Long>} keysIndex = secondaryIndex.keysIndex();</pre>
 *
 * <div><table class="code" border="1" summary="">
 *   <tr><th colspan="4">keysIndex</th></tr>
 *   <tr><th>Secondary Key</th><th colspan="3">Primary Key</th></tr>
 *   <tr><td>Engineering</td><td>1</td></tr>
 *   <tr><td>Engineering</td><td>3</td></tr>
 *   <tr><td>Sales</td><td>2</td></tr>
 *   <tr><td>Sales</td><td>4</td></tr>
 * </table></div>
 *
 * <p>{@link SecondaryIndex#subIndex} maps primary keys to entities, for the
 * subset of entities having a specified secondary key:</p>
 *
 * <pre class="code">
 * {@code EntityIndex<Long, Entity>} subIndex = secondaryIndex.subIndex("Engineering");</pre>
 *
 * <div><table class="code" border="1" summary="">
 *   <tr><th colspan="4">subIndex</th></tr>
 *   <tr><th>Primary Key</th><th colspan="3">Entity</th></tr>
 *   <tr><td>1</td><td>1</td><td>Engineering</td><td>Jane Smith</td></tr>
 *   <tr><td>3</td><td>3</td><td>Engineering</td><td>John Smith</td></tr>
 * </table></div>
 *
 * <h3>Accessing the Index</h3>
 *
 * <p>An {@code EntityIndex} provides a variety of methods for retrieving
 * entities from an index.  It also provides methods for deleting entities.
 * However, it does not provide methods for inserting and updating.  To insert
 * and update entities, use the {@link PrimaryIndex#put} family of methods in
 * the {@link PrimaryIndex} class.</p>
 *
 * <p>An {@code EntityIndex} supports two mechanisms for retrieving
 * entities:</p>
 * <ol>
 * <li>The {@link #get} method returns a single value for a given key.  If there
 * are multiple values with the same secondary key (duplicates), it returns the
 * first entity in the duplicate set.</li>
 * <li>An {@link EntityCursor} can be obtained using the {@link #keys} and
 * {@link #entities} family of methods.  A cursor can be used to return all
 * values in the index, including duplicates.  A cursor can also be used to
 * return values within a specified range of keys.</li>
 * </ol>
 *
 * <p>Using the example entities above, calling {@link #get} on the primary
 * index will always return the employee with the given ID, or null if no such
 * ID exists.  But calling {@link #get} on the secondary index will retrieve
 * the first employee in the given department, which may not be very
 * useful:</p>
 *
 * <pre class="code">
 * Employee emp = primaryIndex.get(1);      // Returns by unique ID
 * emp = secondaryIndex.get("Engineering"); // Returns first in department</pre>
 *
 * <p>Using a cursor, you can iterate through all duplicates in the secondary
 * index:</p>
 *
 * <pre class="code">
 * {@code EntityCursor<Employee>} cursor = secondaryIndex.entities();
 * try {
 *     for (Employee entity : cursor) {
 *         if (entity.department.equals("Engineering")) {
 *             // Do something with the entity...
 *         }
 *     }
 * } finally {
 *     cursor.close();
 * }</pre>
 *
 * <p>But for a large database it is much more efficient to iterate over only
 * those entities with the secondary key you're searching for.  This could be
 * done by restricting a cursor to a range of keys:</p>
 *
 * <pre class="code">
 * {@code EntityCursor<Employee>} cursor =
 *     secondaryIndex.entities("Engineering", true, "Engineering", true);
 * try {
 *     for (Employee entity : cursor) {
 *         // Do something with the entity...
 *     }
 * } finally {
 *     cursor.close();
 * }</pre>
 *
 * <p>However, when you are interested only in the entities with a particular
 * secondary key value, it is more convenient to use a sub-index:</p>
 *
 * <pre class="code">
 * {@code EntityIndex<Long, Entity>} subIndex = secondaryIndex.subIndex("Engineering");
 * {@code EntityCursor<Employee>} cursor = subIndex.entities();
 * try {
 *     for (Employee entity : cursor) {
 *         // Do something with the entity...
 *     }
 * } finally {
 *     cursor.close();
 * }</pre>
 *
 * <p>In addition to being more convenient than a cursor range, a sub-index
 * allows retrieving by primary key:</p>
 *
 * <pre class="code">
 * Employee emp = subIndex.get(1);</pre>
 *
 * <p>When using a sub-index, all operations performed on the sub-index are
 * restricted to the single key that was specified when the sub-index was
 * created.  For example, the following returns null because employee 2 is not
 * in the Engineering department and therefore is not part of the
 * sub-index:</p>
 *
 * <pre class="code">
 * Employee emp = subIndex.get(2);</pre>
 *
 * <p>For more information on using cursors and cursor ranges, see {@link
 * EntityCursor}.</p>
 *
 * <p>Note that when using an index, keys and values are stored and retrieved
 * by value not by reference.  In other words, if an entity object is stored
 * and then retrieved, or retrieved twice, each object will be a separate
 * instance.  For example, in the code below the assertion will always
 * fail.</p>
 * <pre class="code">
 * MyKey key = ...;
 * MyEntity entity1 = index.get(key);
 * MyEntity entity2 = index.get(key);
 * assert entity1 == entity2; // always fails!
 * </pre>
 *
 * <h3>Deleting from the Index</h3>
 *
 * <p>Any type of index may be used to delete entities with a specified key by
 * calling {@link #delete}.  The important thing to keep in mind is that
 * <em>all entities</em> with the specified key are deleted.  In a primary index,
 * at most a single entity is deleted:</p>
 *
 * <pre class="code">
 * primaryIndex.delete(1); // Deletes a single employee by unique ID</pre>
 *
 * <p>But in a secondary index, multiple entities may be deleted:</p>
 *
 * <pre class="code">
 * secondaryIndex.delete("Engineering"); // Deletes all Engineering employees</pre>
 *
 * <p>This begs this question: How can a single entity be deleted without
 * knowing its primary key?  The answer is to use cursors.  After locating an
 * entity using a cursor, the entity can be deleted by calling {@link
 * EntityCursor#delete}.</p>
 *
 * <h3>Transactions</h3>
 *
 * <p>Transactions can be used to provide standard ACID (Atomicity,
 * Consistency, Integrity and Durability) guarantees when retrieving, storing
 * and deleting entities.  This section provides a brief overview of how to use
 * transactions with the Direct Persistence Layer.  For more information on
 * using transactions, see <a
 * href="{@docRoot}/../gsg_txn/JAVA/index.html">Writing
 * Transactional Applications</a>.</p>
 *
 * <p>All environments and databases created by BDB Server are transactional,
 * therefore, all {@link EntityStore}s are also transactional. For example:</p>
 *
 * <pre class="code">
 * SEnvironmentConfig envConfig = new SEnvironmentConfig();
 * envConfig.setAllowCreate(true);
 * SEnvironment env = new SEnvironment(new File("/my/data"), envConfig);
 *
 * StoreConfig storeConfig = new StoreConfig();
 * storeConfig.setAllowCreate(true);
 * EntityStore store = new EntityStore(env, "myStore", storeConfig);</pre>
 *
 * <p>Transactions are represented by {@link STransaction} objects, which are
 * part of the {@link com.sleepycat.client Base API}.  Transactions are created
 * using the {@link SEnvironment#beginTransaction SEnvironment.beginTransaction}
 * method.</p>
 *
 * <p>A transaction will include all operations for which the transaction
 * object is passed as a method argument.  All retrieval, storage and deletion
 * methods have an optional {@link STransaction} parameter for this purpose.
 * When a transaction is passed to a method that opens a cursor, all retrieval,
 * storage and deletion operations performed using that cursor will be included
 * in the transaction.</p>
 *
 * <p>A transaction may be committed by calling {@link STransaction#commit} or
 * aborted by calling {@link STransaction#abort}.  For example, two employees
 * may be deleted atomically with a transaction; other words, either both are
 * deleted or neither is deleted:</p>
 *
 * <pre class="code">
 * STransaction txn = env.beginTransaction(null, null);
 * try {
 *     primaryIndex.delete(txn, 1);
 *     primaryIndex.delete(txn, 2);
 *     txn.commit();
 *     txn = null;
 * } finally {
 *     if (txn != null) {
 *         txn.abort();
 *     }
 * }</pre>
 *
 * <p><em>WARNING:</em> Transactions must always be committed or aborted to
 * prevent resource leaks which could lead to the index becoming unusable or
 * cause an <code>OutOfMemoryError</code>.  To ensure that a transaction is
 * aborted in the face of exceptions, call {@link STransaction#abort} in a
 * finally block.</p>
 *
 * <p>For a transactional store, storage and deletion operations are always
 * transaction protected, whether or not a transaction is explicitly used.  A
 * null transaction argument means to perform the operation using auto-commit,
 * or the implied thread transaction if an XAEnvironment is being used.  A
 * transaction is automatically started as part of the operation and is
 * automatically committed if the operation completes successfully.  The
 * transaction is automatically aborted if an exception occurs during the
 * operation, and the exception is re-thrown to the caller.  For example, each
 * employee is deleted using a an auto-commit transaction below, but it is
 * possible that employee 1 will be deleted and employee 2 will not be deleted,
 * if an error or crash occurs while deleting employee 2:</p>
 *
 * <pre class="code">
 * primaryIndex.delete(null, 1);
 * primaryIndex.delete(null, 2);</pre>
 *
 * <p>When retrieving entities, a null transaction argument means to perform
 * the operation non-transactionally.  The operation is performed outside the
 * scope of any transaction, without providing transactional ACID guarantees.
 * If an implied thread transaction is present (i.e. if an XAEnvironment is
 * being used), that transaction is used.  When a non-transactional store is
 * used, transactional ACID guarantees are also not provided.</p>
 *
 * <p>For non-transactional and auto-commit usage, overloaded signatures for
 * retrieval, storage and deletion methods are provided to avoid having to pass
 * a null transaction argument.  For example, {@link #delete} may be called
 * instead of {@link #delete(STransaction,Object)}.  For example, the following
 * code is equivalent to the code above where null was passed for the
 * transaction:</p>
 *
 * <pre class="code">
 * primaryIndex.delete(1);
 * primaryIndex.delete(2);</pre>
 *
 * <p>For retrieval methods the overloaded signatures also include an optional
 * {@link SLockMode} parameter, and overloaded signatures for opening cursors
 * include an optional {@link SCursorConfig} parameter.  These parameters are
 * described further below in the Locking and Lock Modes section.</p>
 *
 * <h3>Transactions and Cursors</h3>
 *
 * <p>There are two special consideration when using cursors with transactions.
 * First, for a transactional store, a non-null transaction must be passed to
 * methods that open a cursor if that cursor will be used to delete or update
 * entities.  Cursors do not perform auto-commit when a null transaction is
 * explicitly passed or implied by the method signature.  For example, the
 * following code will throw {@link SDatabaseException} when the {@link
 * EntityCursor#delete} method is called:</p>
 *
 * <pre class="code">
 * // <strong>Does not work with a transactional store!</strong>
 * {@code EntityCursor<Employee>} cursor = primaryIndex.entities();
 * try {
 *     for (Employee entity : cursor) {
 *         cursor.delete(); // <strong>Will throw SDatabaseException.</strong>
 *     }
 * } finally {
 *     cursor.close();
 * }</pre>
 *
 * <p>Instead, the {@link #entities(STransaction,SCursorConfig)} signature must
 * be used and a non-null transaction must be passed:</p>
 *
 * <pre class="code">
 * {@code EntityCursor<Employee>} cursor = primaryIndex.entities(txn, null);
 * try {
 *     for (Employee entity : cursor) {
 *         cursor.delete();
 *     }
 * } finally {
 *     cursor.close();
 * }</pre>
 *
 * <p>The second consideration is that error handling is more complex when
 * using both transactions and cursors, for the following reasons:</p>
 * <ol>
 * <li>When an exception occurs, the transaction should be aborted.</li>
 * <li>Cursors must be closed whether or not an exception occurs.</li>
 * <li>Cursors must be closed before committing or aborting the
 * transaction.</li>
 * </ol>
 *
 * <p>For example:</p>
 *
 * <pre class="code">
 * STransaction txn = env.beginTransaction(null, null);
 * {@code EntityCursor<Employee>} cursor = null;
 * try {
 *     cursor = primaryIndex.entities(txn, null);
 *     for (Employee entity : cursor) {
 *         cursor.delete();
 *     }
 *     cursor.close();
 *     cursor = null;
 *     txn.commit();
 *     txn = null;
 * } finally {
 *     if (cursor != null) {
 *         cursor.close();
 *     }
 *     if (txn != null) {
 *         txn.abort();
 *     }
 * }</pre>
 *
 * <h3>Locking and Lock Modes</h3>
 *
 * <p>This section provides a brief overview of locking and describes how lock
 * modes are used with the Direct Persistence Layer.  For more information on
 * locking, see <a
 * href="{@docRoot}/../gsg_txn/JAVA/index.html">Writing
 * Transactional Applications</a>.</p>
 *
 * <p>When using transactions, locks are normally acquired on each entity that
 * is retrieved or stored.  The locks are used to isolate one transaction from
 * another.  Locks are normally released only when the transaction is committed
 * or aborted.</p>
 *
 * <p>When not using transactions, locks are also normally acquired on each
 * entity that is retrieved or stored.  However, these locks are released when
 * the operation is complete.  When using cursors, in order to provide
 * <em>cursor stability</em> locks are held until the cursor is moved to a
 * different entity or closed.</p>
 *
 * <p>This default locking behavior provides full transactional ACID guarantees
 * and cursor stability.  However, application performance can sometimes be
 * improved by compromising these guarantees.  As described in <a
 * href="{@docRoot}/../gsg_txn/JAVA/index.html">Writing
 * Transactional Applications</a>, the {@link SLockMode} and {@link
 * SCursorConfig} parameters are two of the mechanisms that can be used to make
 * compromises.</p>
 *
 * <p>For example, imagine that you need an approximate count of all entities
 * matching certain criterion, and it is acceptable for entities to be changed
 * by other threads or other transactions while performing this query.  {@link
 * SLockMode#READ_UNCOMMITTED} can be used to perform the retrievals without
 * acquiring any locks.  This reduces memory consumption, does less processing,
 * and improves concurrency.</p>
 *
 * <pre class="code">
 * {@code EntityCursor<Employee>} cursor = primaryIndex.entities(txn, null);
 * try {
 *     Employee entity;
 *     while ((entity = cursor.next(SLockMode.READ_UNCOMMITTED)) != null) {
 *         // Examine the entity and accumulate totals...
 *     }
 * } finally {
 *     cursor.close();
 * }</pre>
 *
 * <p>The {@link SLockMode} parameter specifies locking behavior on a
 * per-operation basis.  If null or {@link SLockMode#DEFAULT} is specified, the
 * default lock mode is used.</p>
 *
 * <p>It is also possible to specify the default locking behavior for a cursor
 * using {@link SCursorConfig}.  The example below is equivalent to the example
 * above:</p>
 *
 * <pre class="code">
 * SCursorConfig config = new SCursorConfig();
 * config.setReadUncommitted(true);
 * {@code EntityCursor<Employee>} cursor = primaryIndex.entities(txn, config);
 * try {
 *     Employee entity;
 *     while ((entity = cursor.next()) != null) {
 *         // Examine the entity and accumulate totals...
 *     }
 * } finally {
 *     cursor.close();
 * }</pre>
 *
 *
 * <p>The use of other lock modes, cursor configuration, and transaction
 * configuration are discussed in <a
 * href="{@docRoot}/../gsg_txn/JAVA/index.html">Writing
 * Transactional Applications</a>.</p>
 *
 * <h3><a name="retries">Performing STransaction Retries</a></h3>
 *
 * <p>Lock conflict handling is another important topic discussed in <a
 * href="{@docRoot}/../gsg_txn/JAVA/index.html">Writing
 * Transactional Applications</a>.  To go along with that material, here we
 * show a lock conflict handling loop in the context of the Direct Persistence
 * Layer.  The example below shows deleting all entities in a primary index in
 * a single transaction.  If a lock conflict occurs, the transaction is aborted
 * and the operation is retried.</p>
 *
 *
 * <pre class="code">
 *  void doTransaction(final SEnvironment env,
 *                     final {@code PrimaryIndex<Long, Employee>} primaryIndex,
 *                     final int maxTries)
 *      throws SDatabaseException {
 *
 *      boolean success = false;
 *      long sleepMillis = 0;
 *      for (int i = 0; i &lt; maxTries; i++) {
 *          // Sleep before retrying.
 *          if (sleepMillis != 0) {
 *              Thread.sleep(sleepMillis);
 *              sleepMillis = 0;
 *          }
 *          STransaction txn = null;
 *          try {
 *              txn = env.beginTransaction(null, null);
 *              final {@code EntityCursor<Employee>} cursor =
 *                  primaryIndex.entities(txn, null);
 *              try {
 *                  // INSERT APP-SPECIFIC CODE HERE:
 *                  // Perform read and write operations, for example:
 *                  for (Employee entity : cursor) {
 *                      cursor.delete();
 *                  }
 *              } finally {
 *                  cursor.close();
 *              }
 *              txn.commit();
 *              success = true;
 *              return;
 *          } catch (DeadlockException e) {
 *              sleepMillis = LOCK_CONFLICT_RETRY_SEC * 1000;
 *              continue;
 *          } finally {
 *              if (!success) {
 *                  if (txn != null) {
 *                      txn.abort();
 *                  }
 *              }
 *          }
 *      }
 *      // INSERT APP-SPECIFIC CODE HERE:
 *      // STransaction failed, despite retries.
 *      // Take some app-specific course of action.
 *  }</pre>
 *
 * <h3>Low Level Access</h3>
 *
 * <p>Each Direct Persistence Layer index is associated with an underlying
 * {@link SDatabase} or {@link SSecondaryDatabase} defined in the {@link
 * com.sleepycat.client Base API}.  At this level, an index is a Btree managed by
 * the Berkeley DB Java Edition transactional storage engine.  Although you may
 * never need to work at the {@code Base API} level, keep in mind that some
 * types of performance tuning can be done by configuring the underlying
 * databases.  See the {@link EntityStore} class for more information on
 * database and sequence configuration.</p>
 *
 * <p>If you wish to access an index using the {@code Base API}, you may call
 * the {@link PrimaryIndex#getDatabase} or {@link SecondaryIndex#getDatabase}
 * method to get the underlying database.  To translate between entity or key
 * objects and {@link SDatabaseEntry} objects at this level, use the bindings
 * returned by {@link PrimaryIndex#getEntityBinding}, {@link
 * PrimaryIndex#getKeyBinding}, and {@link SecondaryIndex#getKeyBinding}.</p>
 *
 * @author Mark Hayes
 */
public interface EntityIndex<K, V> {

    /**
     * Returns the underlying database for this index.
     *
     * @return the database.
     */
    SDatabase getDatabase();

    /**
     * Checks for existence of a key in this index.
     *
     * <p>The operation will not be transaction protected, and {@link
     * SLockMode#DEFAULT} is used implicitly.</p>
     *
     *
     * @param key the key to search for.
     *
     * @return whether the key exists in the index.
     *
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    boolean contains(K key)
        throws SDatabaseException;

    /**
     * Checks for existence of a key in this index.
     *
     *
     * @param txn the transaction used to protect this operation, or null
     * if the operation should not be transaction protected.
     *
     * @param key the key to search for.
     *
     * @param lockMode the lock mode to use for this operation, or null to
     * use {@link SLockMode#DEFAULT}.
     *
     * @return whether the key exists in the index.
     *
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    boolean contains(STransaction txn, K key, SLockMode lockMode)
        throws SDatabaseException;

    /**
     * Gets an entity via a key of this index.
     *
     * <p>The operation will not be transaction protected, and {@link
     * SLockMode#DEFAULT} is used implicitly.</p>
     *
     * @param key the key to search for.
     *
     * @return the value mapped to the given key, or null if the key is not
     * present in the index.
     *
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    V get(K key)
        throws SDatabaseException;

    /**
     * Gets an entity via a key of this index.
     *
     * @param txn the transaction used to protect this operation, or null
     * if the operation should not be transaction protected.
     *
     * @param key the key to search for.
     *
     * @param lockMode the lock mode to use for this operation, or null to
     * use {@link SLockMode#DEFAULT}.
     *
     * @return the value mapped to the given key, or null if the key is not
     * present in the index.
     *
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    V get(STransaction txn, K key, SLockMode lockMode)
        throws SDatabaseException;


    /**
     * Returns a non-transactional count of the entities in this index.
     *
     *
     * @return the number of entities in this index.
     *
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    long count()
        throws SDatabaseException;


    /**
     * Deletes all entities with a given index key.
     *
     * <p>Auto-commit is used implicitly if the store is transactional.</p>
     *
     * @param key the key to search for.
     *
     * @return whether any entities were deleted.
     *
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    boolean delete(K key)
        throws SDatabaseException;

    /**
     * Deletes all entities with a given index key.
     *
     * @param txn the transaction used to protect this operation, null to use
     * auto-commit, or null if the store is non-transactional.
     *
     * @param key the key to search for.
     *
     * @return whether any entities were deleted.
     *
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    boolean delete(STransaction txn, K key)
        throws SDatabaseException;


    /**
     * Opens a cursor for traversing all keys in this index.
     *
     * <p>The operations performed with the cursor will not be transaction
     * protected, and {@link SCursorConfig#DEFAULT} is used implicitly.  If the
     * store is transactional, the cursor may not be used to update or delete
     * entities.</p>
     *
     *
     * @return the cursor.
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    EntityCursor<K> keys()
        throws SDatabaseException;

    /**
     * Opens a cursor for traversing all keys in this index.
     *
     *
     * @param txn the transaction used to protect all operations performed with
     * the cursor, or null if the operations should not be transaction
     * protected.  If the store is non-transactional, null must be specified.
     * For a transactional store the transaction is optional for read-only
     * access and required for read-write access.
     *
     * @param config the cursor configuration that determines the default lock
     * mode used for all cursor operations, or null to implicitly use {@link
     * SCursorConfig#DEFAULT}.
     *
     * @return the cursor.
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    EntityCursor<K> keys(STransaction txn, SCursorConfig config)
        throws SDatabaseException;

    /**
     * Opens a cursor for traversing all entities in this index.
     *
     * <p>The operations performed with the cursor will not be transaction
     * protected, and {@link SCursorConfig#DEFAULT} is used implicitly.  If the
     * store is transactional, the cursor may not be used to update or delete
     * entities.</p>
     *
     * @return the cursor.
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    EntityCursor<V> entities()
        throws SDatabaseException;

    /**
     * Opens a cursor for traversing all entities in this index.
     *
     * @param txn the transaction used to protect all operations performed with
     * the cursor, or null if the operations should not be transaction
     * protected.  If the store is non-transactional, null must be specified.
     * For a transactional store the transaction is optional for read-only
     * access and required for read-write access.
     *
     * @param config the cursor configuration that determines the default lock
     * mode used for all cursor operations, or null to implicitly use {@link
     * SCursorConfig#DEFAULT}.
     *
     * @return the cursor.
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    EntityCursor<V> entities(STransaction txn,
                             SCursorConfig config)
        throws SDatabaseException;

    /**
     * Opens a cursor for traversing keys in a key range.
     *
     * <p>The operations performed with the cursor will not be transaction
     * protected, and {@link SCursorConfig#DEFAULT} is used implicitly.  If the
     * store is transactional, the cursor may not be used to update or delete
     * entities.</p>
     *
     *
     * @param fromKey is the lower bound of the key range, or null if the range
     * has no lower bound.
     *
     * @param fromInclusive is true if keys greater than or equal to fromKey
     * should be included in the key range, or false if only keys greater than
     * fromKey should be included.
     *
     * @param toKey is the upper bound of the key range, or null if the range
     * has no upper bound.
     *
     * @param toInclusive is true if keys less than or equal to toKey should be
     * included in the key range, or false if only keys less than toKey should
     * be included.
     *
     * @return the cursor.
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    EntityCursor<K> keys(K fromKey,
                         boolean fromInclusive,
                         K toKey,
                         boolean toInclusive)
        throws SDatabaseException;

    /**
     * Opens a cursor for traversing keys in a key range.
     *
     *
     * @param txn the transaction used to protect all operations performed with
     * the cursor, or null if the operations should not be transaction
     * protected.  If the store is non-transactional, null must be specified.
     * For a transactional store the transaction is optional for read-only
     * access and required for read-write access.
     *
     * @param fromKey is the lower bound of the key range, or null if the range
     * has no lower bound.
     *
     * @param fromInclusive is true if keys greater than or equal to fromKey
     * should be included in the key range, or false if only keys greater than
     * fromKey should be included.
     *
     * @param toKey is the upper bound of the key range, or null if the range
     * has no upper bound.
     *
     * @param toInclusive is true if keys less than or equal to toKey should be
     * included in the key range, or false if only keys less than toKey should
     * be included.
     *
     * @param config the cursor configuration that determines the default lock
     * mode used for all cursor operations, or null to implicitly use {@link
     * SCursorConfig#DEFAULT}.
     *
     * @return the cursor.
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    EntityCursor<K> keys(STransaction txn,
                         K fromKey,
                         boolean fromInclusive,
                         K toKey,
                         boolean toInclusive,
                         SCursorConfig config)
        throws SDatabaseException;

    /**
     * Opens a cursor for traversing entities in a key range.
     *
     * <p>The operations performed with the cursor will not be transaction
     * protected, and {@link SCursorConfig#DEFAULT} is used implicitly.  If the
     * store is transactional, the cursor may not be used to update or delete
     * entities.</p>
     *
     * @param fromKey is the lower bound of the key range, or null if the range
     * has no lower bound.
     *
     * @param fromInclusive is true if keys greater than or equal to fromKey
     * should be included in the key range, or false if only keys greater than
     * fromKey should be included.
     *
     * @param toKey is the upper bound of the key range, or null if the range
     * has no upper bound.
     *
     * @param toInclusive is true if keys less than or equal to toKey should be
     * included in the key range, or false if only keys less than toKey should
     * be included.
     *
     * @return the cursor.
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    EntityCursor<V> entities(K fromKey,
                             boolean fromInclusive,
                             K toKey,
                             boolean toInclusive)
        throws SDatabaseException;

    /**
     * Opens a cursor for traversing entities in a key range.
     *
     * @param txn the transaction used to protect all operations performed with
     * the cursor, or null if the operations should not be transaction
     * protected.  If the store is non-transactional, null must be specified.
     * For a transactional store the transaction is optional for read-only
     * access and required for read-write access.
     *
     * @param fromKey is the lower bound of the key range, or null if the range
     * has no lower bound.
     *
     * @param fromInclusive is true if keys greater than or equal to fromKey
     * should be included in the key range, or false if only keys greater than
     * fromKey should be included.
     *
     * @param toKey is the upper bound of the key range, or null if the range
     * has no upper bound.
     *
     * @param toInclusive is true if keys less than or equal to toKey should be
     * included in the key range, or false if only keys less than toKey should
     * be included.
     *
     * @param config the cursor configuration that determines the default lock
     * mode used for all cursor operations, or null to implicitly use {@link
     * SCursorConfig#DEFAULT}.
     *
     * @return the cursor.
     *
     * @throws SDatabaseException the base class for all BDB exceptions.
     */
    EntityCursor<V> entities(STransaction txn,
                             K fromKey,
                             boolean fromInclusive,
                             K toKey,
                             boolean toInclusive,
                             SCursorConfig config)
        throws SDatabaseException;

    /**
     * Returns a standard Java map based on this entity index.  The {@link
     * StoredMap} returned is defined by the {@linkplain
     * com.sleepycat.client.collections Collections API}.  Stored collections conform
     * to the standard Java collections framework interface.
     *
     * @return the map.
     */
    Map<K, V> map();

    /**
     * Returns a standard Java sorted map based on this entity index.  The
     * {@link StoredSortedMap} returned is defined by the {@linkplain
     * com.sleepycat.client.collections Collections API}.  Stored collections conform
     * to the standard Java collections framework interface.
     *
     * @return the map.
     */
    SortedMap<K, V> sortedMap();
}
