# Copyright (c) 2007, 2019 Oracle and/or its affiliates.  All rights reserved.
#
# See the file LICENSE for license information. 
# 
# $Id: backup.tcl,v 4b37b36844da 2012/10/18 15:03:32 sue $
#
# TEST	backup
# TEST 	Test of hotbackup functionality. 
# TEST	
# TEST	Do all the of the following tests with and without 
# TEST	the -c (checkpoint) option; and with and without the
# TEST  transactional bulk loading optimization; and with
# TEST  and without BLOB, with and without -deepcopy.
# TEST  Make sure that -c and -d (data_dir)
# TEST  are not allowed together; and backing up with BLOB
# TEST  but without -log_blob is not allowed.  If slices are
# TEST  enabled, run some of the tests with the -sliced
# TEST  database flag.
# TEST
# TEST	(1) Test that plain and simple hotbackup works. 
# TEST	(2) Test with -data_dir (-d). 
# TEST	(3) Test updating an existing hot backup (-u).
# TEST	(4) Test with absolute path. 
# TEST	(5) Test with DB_CONFIG, setting log_dir (-l)
# TEST		and data_dir (-d).
# TEST	(6) DB_CONFIG and update.  
# TEST	(7) Repeat hot backup (non-update) with DB_CONFIG, 
# TEST		DB_CONFIG (-D) and existing directories. 
# TEST	(8) Incremental hot backup when txn is active and the number
# TEST		of log files is successively increased.

proc backup { method {nentries 1000} } {
	source ./include.tcl

	if { [is_btree $method] != 1 && \
	    [is_heap $method] != 1 && [is_hash $method] != 1 } {
		puts "Skipping backup for method $method."
		return
	}

	foreach txnmode { txn bulk } {
		foreach ckpoption { nocheckpoint checkpoint } {
			foreach bloption { noblob blob } {
				foreach cpoption { shallow deep } {
					backup_sub $method \
					    $nentries $txnmode \
					    $ckpoption $bloption \
					    0 $cpoption
				}
			}
		}
	}

	# If slices are enabled and the method is not heap, run
	# on a sliced database.
	if { [berkdb slice_enabled] && [is_heap $method] != 1 } {
		set num_slices 2
		foreach ckpoption { checkpoint nocheckpoint } {
			backup_sub $method $nentries \
			    txn $ckpoption noblob \
			    $num_slices shallow
		}
	}
}

proc backup_sub { method nentries txnmode \
		      ckpoption bloption num_slices cpoption } {
	source ./include.tcl

	set omethod [convert_method $method]

	set testfile "foo.db"
	set backupdir "backup"
	set backupapidir "backupapi"

	# Set up small logs so we quickly create more than one. 
	set log_size 20000
	set env_flags " -create -txn -home $testdir -log_max $log_size"
	set db_flags " -create $omethod -auto_commit "
	set bu_flags " -create -clean -files "

	if { $txnmode == "bulk" } {
		set bmsg "with bulk optimization"
	} else {
		set bmsg "without bulk optimization"
	}
	if { $ckpoption == "checkpoint" } {
		set c "c"
		set msg "with checkpoint"
	} else {
		set c ""
		set msg "without checkpoint"
	}
	if { $bloption == "blob" } {
		# Pick up the blob threshold as 10. The test populates the
		# database with strings some shorter and longer than this
		# threshold. So we will have a database with blob and
		# non-blob records.
		set threshold 10
		set blmsg "with blob"
		set env_flags "$env_flags -blob_threshold $threshold -log_blob"

		# This test runs a bit slowly when blob gets enabled.
		# Cut down the number of entries to 100 for blob case.
		set nentries 100
	} else {
		set blmsg "without blob"
	}
	
	set slicemsg ""
	if { $num_slices > 0 } {
		set db_flags " -sliced $db_flags "
		set slicemsg " with slices"
	}

	if { $cpoption == "deep" } {
		set cpmsg "with deep copy"
		set subdir1 "subdir1"
		set subdir2 "$subdir1/subdir2"
		set subfile1 $subdir1/$testfile
		set subfile2 $subdir2/$testfile
		set subdb_flags $db_flags
		set deep "C"
		set bu_flags " -deep_copy $bu_flags "
	} else {
		set cpmsg "with shallow copy"
		set deep ""
	}
	set db_flags "$db_flags $testfile"

	puts "Backuptest ($omethod) $bmsg $cpmsg $msg $blmsg$slicemsg."

	env_cleanup $testdir
	env_cleanup $backupdir
	env_cleanup $backupapidir 

	if { $num_slices > 0 } {
		slice_db_config $num_slices
	}
	set env [eval {berkdb_env} $env_flags]
	set db [eval {berkdb_open} -env $env $db_flags]
	error_check_good envopen [is_valid_env $env] TRUE
	error_check_good dbopen [is_valid_db $db] TRUE
	if { $cpoption == "deep" } {
		file mkdir $testdir/$subdir1
		file mkdir $testdir/$subdir2
		set subdb1 [eval {berkdb_open} \
		    -env $env $subdb_flags $subfile1 ]
		error_check_good subdb1open [is_valid_db $subdb1] TRUE
		set subdb2 [eval {berkdb_open} \
		    -env $env $subdb_flags $subfile2 ]
		error_check_good subdb2open [is_valid_db $subdb2] TRUE
	}

	if { $num_slices > 0 } {
		set txn ""
	} elseif { $txnmode == "bulk" } {
		set txn [$env txn -txn_bulk]
	} else {
		set txn [$env txn]
	}
	populate $db $omethod $txn $nentries 0 0
	if { $cpoption == "deep" } {
		populate $subdb1 $omethod $txn $nentries 0 0
		populate $subdb2 $omethod $txn $nentries 0 0
	}
	if { $txn != "" } {
		error_check_good txn_commit [$txn commit] 0
	}
	error_check_good db_sync [$db sync] 0
	if { $cpoption == "deep" } {
		error_check_good subdb1_sync [$subdb1 sync] 0
		error_check_good subdb2_sync [$subdb2 sync] 0
	}

	# Verify that blobs are created.
	if { $bloption == "blob" } {
		set blob_subdir [$db get_blob_sub_dir]
		set files [glob -nocomplain $testdir/__db_bl/$blob_subdir/*]
		error_check_bad created_blobs [llength $files] 0
	}

	# Backup directory is empty before hot backup.
	set files [glob -nocomplain $backupdir/*]
	error_check_good no_files [llength $files] 0

	# Backup API directory is empty before hot backup.
	set files [glob -nocomplain $backupapidir/*]
	error_check_good no_files [llength $files] 0

	puts "\tBackuptest.a.0: Hot backup to directory $backupdir."
	for {set i 0} {$i < $num_slices} {incr i} {
		file mkdir $backupdir/__db.slice00$i
		if {[catch { eval exec $util_path/db_hotbackup\
		    -${c}vh $testdir/__db.slice00$i \
		    -b $backupdir/__db.slice00$i } res] } {
			error "FAIL: $res"
		}	
	}
	# Blobs and deep copy are incompatible.
	set deepmsg "DB_BACKUP_DEEP_COPY and external file support cannot"
	if { $cpoption == "deep" && $bloption == "blob" } {
		catch {eval exec $util_path/db_hotbackup \
		    -${c}${deep}vh $testdir -b $backupdir } res
		error_check_good deep_and_blob [is_substr $res $deepmsg] 1
		error_check_good db_close [$db close] 0
		error_check_good subdb1_close [$subdb1 close] 0
		error_check_good subdb2_close [$subdb2 close] 0
		error_check_good env_close [$env close] 0
		env_cleanup $testdir
		env_cleanup $backupdir
		env_cleanup $backupapidir	
		return;
	} elseif {[catch { eval exec $util_path/db_hotbackup \
	    -${c}${deep}vh $testdir -b $backupdir } res] } {
		error "FAIL: $res"
	}

	set logfiles [glob $backupdir/log*]
	error_check_bad found_logs [llength $logfiles] 0
	error_check_good found_db [file exists $backupdir/$testfile] 1
	if { $cpoption == "deep" } {
		error_check_good found_subdb1 \
		    [file exists $backupdir/$subfile1] 1
		error_check_good found_subdb2 \
		    [file exists $backupdir/$subfile2] 1
	}
	if { $bloption == "blob" } {
		set blfiles [glob -nocomplain \
		    $backupdir/__db_bl/$blob_subdir/*]
		error_check_bad found_blobs [llength $blfiles] 0
	}
	# Verify slices are backed up
	for {set i 0} {$i < $num_slices} {incr i} {
	    error_check_good created_slices_db$i \
		[file exists $backupdir/__db.slice00$i/$testfile] 1
	    set logfiles [glob $backupdir/__db.slice00$i/log*]
	    error_check_bad slices_found_logs [llength $logfiles] 0
	}

	puts "\tBackuptest.a.1: API hot backup to directory $backupapidir."
	if { [catch {eval $env backup $bu_flags \
	    -single_dir $backupapidir} res] } {
		error "FAIL: $res"
	}
	if { $num_slices > 0 } {
		set slices [$env get_slices]
		set i 0
		foreach slice $slices {
			file mkdir $backupapidir/__db.slice00$i
			if { [catch {eval $slice backup $bu_flags \
			    -single_dir $backupapidir/__db.slice00$i} res] } {
				error "FAIL: $res"
			}
			incr i
		}	
	}
	set logfiles [glob $backupapidir/log*]
	error_check_bad found_logs [llength $logfiles] 0
	error_check_good found_db\
	    [file exists $backupapidir/$testfile] 1
	if { $cpoption == "deep" } {
		error_check_good found_subdb1 \
		    [file exists $backupapidir/$subfile1] 1
		error_check_good found_subdb2 \
		    [file exists $backupapidir/$subfile2] 1
	}
	if { $bloption == "blob" } {
		set blfiles [glob -nocomplain \
		    $backupapidir/__db_bl/$blob_subdir/*]
		error_check_bad found_blobs [llength $blfiles] 0
	}
	# Verify slices are backed up
	for {set i 0} {$i < $num_slices} {incr i} {
	    error_check_good created_slices_db$i \
		[file exists $backupapidir/__db.slice00$i/$testfile] 1
	    set logfiles [glob $backupapidir/__db.slice00$i/log*]
	    error_check_bad slices_found_logs [llength $logfiles] 0
	}

	puts "\tBackuptest.a.2: API hot backup with -no_logs flag."
	if { [catch {eval $env backup $bu_flags \
	    -single_dir -no_logs $backupapidir} res] } {
		error "FAIL: $res"
	}
	if { $num_slices > 0 } {
		set slices [$env get_slices]
		set i 0
		foreach slice $slices {
			file mkdir $backupapidir/__db.slice00$i
			if { [catch {eval $slice backup $bu_flags -no_logs \
			    -single_dir $backupapidir/__db.slice00$i} res] } {
				error "FAIL: $res"
			}
			incr i
		}	
	}
	set logfiles [glob -nocomplain $backupapidir/log*]
	error_check_good found_no_logs [llength $logfiles] 0
	error_check_good found_db\
	    [file exists $backupapidir/$testfile] 1
	if { $cpoption == "deep" } {
		error_check_good found_subdb1 \
		    [file exists $backupapidir/$subfile1] 1
		error_check_good found_subdb2 \
		    [file exists $backupapidir/$subfile2] 1
	}
	if { $bloption == "blob" } {
		set blfiles [glob -nocomplain \
		    $backupapidir/__db_bl/$blob_subdir/*]
		error_check_bad found_blobs [llength $blfiles] 0
	}
	# Verify slices are backed up
	for {set i 0} {$i < $num_slices} {incr i} {
	    error_check_good created_slices_db$i \
		[file exists $backupapidir/__db.slice00$i/$testfile] 1
	    set logfiles [glob -nocomplain $backupapidir/__db.slice00$i/log*]
	    error_check_good slices_found_no_logs [llength $logfiles] 0
	}
	
	set stmsg "a.3"
	# If either checkpoint or bulk is in effect, the copy
	# will exactly match the original database.
	if { $ckpoption == "checkpoint" || $txnmode == "bulk"} {

		puts "\tBackuptest.$stmsg:\
		    Verify backup matches original file."
		if { $bloption == "blob" } {
			set stmsg "a.3"
			dump_compare_blobs \
			    $testdir/$testfile $backupdir/$testfile \
			    $testdir/__db_bl $backupdir/__db_bl
			dump_compare_blobs \
			    $testdir/$testfile $backupapidir/$testfile \
			    $testdir/__db_bl $backupapidir/__db_bl
		} else {
			dump_compare $testdir/$testfile $backupdir/$testfile
			dump_compare $testdir/$testfile $backupapidir/$testfile
			if { $cpoption == "deep" } {
				dump_compare $testdir/$subfile1 \
				    $backupdir/$subfile1
				dump_compare $testdir/$subfile1 \
				    $backupapidir/$subfile1
				dump_compare $testdir/$subfile2 \
				    $backupdir/$subfile2
				dump_compare $testdir/$subfile2 \
				    $backupapidir/$subfile2
			}
		}
	}
	# Hot backup requires -log_blob. Testing for the error once is enough.
	if { $bloption == "blob" && \
	    $txnmode == "txn" && $ckpoption == "nocheckpoint" } {
		puts "\tBackuptest.$stmsg:\
		    API backup without -log_blob will fail."
		set env1 [eval {berkdb_env_noerr} -home $testdir]
		set ret [catch {eval $env1 backup $bu_flags $backupapidir} res]
		error_check_bad backup_no_logblob $ret 0
		error_check_good backup_failmsg \
		    [is_substr $res "requires DB_LOG_EXT_FILE"] 1
		error_check_good env_close [$env1 close] 0
	}
	error_check_good db_close [$db close] 0
	if { $cpoption == "deep" } {
		error_check_good subdb1_close [$subdb1 close] 0
		error_check_good subdb2_close [$subdb2 close] 0
	}
	error_check_good env_close [$env close] 0
	env_cleanup $testdir
	env_cleanup $backupdir
	env_cleanup $backupapidir
	
	set dir_flags "-data_dir data1"
	set bk_dirflags "-d $testdir/data1"
	set dirmsg "with data_dir"
	if { $bloption == "blob" } {
		append dirmsg " and blob_dir"
		append dir_flags " -blob_dir blobs"
		append bk_dirflags " -i blobs"
	}
	puts "\tBackuptest.b: Hot backup $dirmsg."
	file mkdir $testdir/data1
	error_check_good db_data_dir [file exists $testdir/data1/$testfile] 0
	if { $cpoption == "deep" } {
		file mkdir $testdir/data1/$subdir1
		file mkdir $testdir/data1/$subdir2
	}
	for {set i 0} {$i < $num_slices} {incr i} {
	    file mkdir $testdir/__db.slice00$i
	    file mkdir $testdir/__db.slice00$i/data1
	}
	if { $num_slices > 0 } {
		slice_db_config $num_slices \
		    { "set_data_dir data1" } { "set_data_dir data1" }
	}

	# Create a new env with data_dir.
	set env [eval {berkdb_env_noerr} $env_flags $dir_flags]
	set db [eval {berkdb_open} -env $env $db_flags]
	error_check_good envopen [is_valid_env $env] TRUE
	error_check_good dbopen [is_valid_db $db] TRUE
	if { $cpoption == "deep" } {
		file mkdir $testdir/$subdir1
		file mkdir $testdir/$subdir2
		set subdb1 [eval {berkdb_open} \
				-env $env $subdb_flags $subfile1 ]
		error_check_good subdb1open [is_valid_db $subdb1] TRUE
		set subdb2 [eval {berkdb_open} \
				-env $env $subdb_flags $subfile2 ]
		error_check_good subdb2open [is_valid_db $subdb2] TRUE
	}

	if { $num_slices > 0 } {
		set txn ""
	} elseif { $txnmode == "bulk" } {
		set txn [$env txn -txn_bulk]
	} else {
		set txn [$env txn]
	}

	populate $db $omethod $txn $nentries 0 0
	if { $cpoption == "deep" } {
		populate $subdb1 $omethod $txn $nentries 0 0
		populate $subdb2 $omethod $txn $nentries 0 0
	}
	if { $txn != "" } {
		error_check_good txn_commit [$txn commit] 0
	}
	error_check_good db_sync [$db sync] 0
	if { $cpoption == "deep" } {
		error_check_good subdb1_sync [$subdb1 sync] 0
		error_check_good subdb2_sync [$subdb2 sync] 0
	}

	# Check that data went into data_dir.
	error_check_good db_data_dir [file exists $testdir/data1/$testfile] 1
	if { $cpoption == "deep" } {
		error_check_good subdb1_data_dir \
		    [file exists $testdir/data1/$subfile1] 1
		error_check_good subdb2_data_dir \
		    [file exists $testdir/data1/$subfile2] 1
	}
	for {set i 0} {$i < $num_slices} {incr i} {
	    error_check_good slice_db_data_dir$i \
		[file exists $testdir/__db.slice00$i/data1/$testfile] 1
	}

	# Check that blobs went into blob_dir.
	if { $bloption == "blob" } {
		set blob_subdir [$db get_blob_sub_dir]
		set files [glob -nocomplain $testdir/blobs/$blob_subdir/*]
		error_check_bad created_blobs [llength $files] 0
	}

	# You may not specify both -d (data_dir) and -c 
	# (checkpoint).
	set msg2 "cannot specify -d and -c"
	if { $ckpoption == "checkpoint" } {
		catch {eval exec $util_path/db_hotbackup \
		    -${c}${deep}vh $testdir -b $backupdir $bk_dirflags} res
		error_check_good c_and_d [is_substr $res $msg2] 1
	} else {
		if {[catch {eval exec $util_path/db_hotbackup\
		    -${c}${deep}vh $testdir -b $backupdir $bk_dirflags} res] } {
			error "FAIL: $res"
		}
		for {set i 0} {$i < $num_slices} {incr i} {
			if {[catch {eval exec $util_path/db_hotbackup\
			    -${c}vh $testdir/__db.slice00$i \
			    -b $backupdir/__db.slice00$i "-d data1"} res] } {
				error "FAIL: $res"
			}
		}
		# Check that logs and db are in backupdir.
		error_check_good db_backup [file exists $backupdir/$testfile] 1
		if { $cpoption == "deep" } {
			error_check_good subdb1_backup \
			    [file exists $backupdir/$subfile1] 1
			error_check_good subdb2_backup \
			    [file exists $backupdir/$subfile2] 1
		}
		set logfiles [glob $backupdir/log*]
		error_check_bad logs_backed_up [llength $logfiles] 0
		# Check that blobs are in backupdir/__db_bl.
		if { $bloption == "blob" } {
			set bl_files [glob -nocomplain \
			    $backupdir/__db_bl/$blob_subdir/*]
			error_check_bad blobs_backed_up [llength $bl_files] 0
		}
		for {set i 0} {$i < $num_slices} {incr i} {
			set logfiles [glob $backupdir/__db.slice00$i/log*]
			error_check_bad logs_backedup$i [llength $logfiles] 0
			error_check_good slice_backedup$i [file exists \
			    $backupdir/__db.slice00$i/$testfile] 1
		}

		#
		# Using the API, the env handle already has
		# the datadir set, so it should just find it.
		#
		puts "\tBackuptest.b.1: API hot backup $dirmsg."
		if { [catch {eval $env backup $bu_flags\
		    -single_dir $backupapidir} res] } {
			error "FAIL: $res"
		}
		if { $num_slices > 0 } {
			set slices [$env get_slices]
			set i 0
			foreach slice $slices {
				file mkdir $backupapidir/__db.slice00$i
				if { [catch {eval $env backup $bu_flags\
				    -single_dir $backupapidir/__db.slice00$i}\
				    res] } {
					error "FAIL: $res"
				}
				incr i
			}	
		}
		error_check_good db_backup\
		    [file exists $backupapidir/$testfile] 1
		if { $cpoption == "deep" } {
			error_check_good subdb1_backup \
			    [file exists $backupapidir/$subfile1] 1
			error_check_good subdb2_backup \
			    [file exists $backupapidir/$subfile2] 1
		}
		set logfiles [glob $backupapidir/log*]
		error_check_bad logs_backed_up [llength $logfiles] 0
		if { $bloption == "blob" } {
			set bl_files [glob -nocomplain \
			    $backupapidir/__db_bl/$blob_subdir/*]
			error_check_bad blobs_backed_up [llength $bl_files] 0
		}
		for {set i 0} {$i < $num_slices} {incr i} {
			set slogfiles [glob $backupapidir/__db.slice00$i/log*]
			error_check_bad logs_backedup$i [llength $slogfiles] 0
			error_check_good slice_backedup$i [file exists \
			    $backupapidir/__db.slice00$i/$testfile] 1
		}
	}

	# Add more data and try the "update" flag. 
	puts "\tBackuptest.c: Update existing hot backup."

	if { $num_slices > 0 } {
		set txn ""
	} elseif { $txnmode == "bulk" } {
		set txn [$env txn -txn_bulk]
	} else {
		set txn [$env txn]
	}

	populate $db $omethod $txn [expr $nentries * 2] 0 0
	if { $cpoption == "deep" } {
		populate $subdb1 $omethod $txn [expr $nentries * 2] 0 0
		populate $subdb2 $omethod $txn [expr $nentries * 2] 0 0
	}
	if { $txn != "" } {
		error_check_good txn_commit [$txn commit] 0
	}
	error_check_good db_sync [$db sync] 0
	if { $cpoption == "deep" } {
		error_check_good subdb1_sync [$subdb1 sync] 0
		error_check_good subdb2_sync [$subdb2 sync] 0
	}

	if { $ckpoption == "checkpoint" } {
		catch {eval exec $util_path/db_hotbackup\
		    -${c}${deep}vuh $testdir -b backup $bk_dirflags} res
		error_check_good c_and_d [is_substr $res $msg2] 1
	} else {
		if {[catch {eval exec $util_path/db_hotbackup\
		    -${c}${deep}vuh $testdir -b backup $bk_dirflags} res] } {
			error "FAIL: $res"
		}
		for {set i 0} {$i < $num_slices} {incr i} {
			file mkdir $backupdir/__db.slice00$i
			if {[catch { eval exec $util_path/db_hotbackup\
			    -${c}vuh $testdir/__db.slice00$i \
			    -b $backupdir/__db.slice00$i } res] } {
				error "FAIL: $res"
			}	
		}
		# There should be more log files now, unless using slices.
		if { $num_slices == 0 } {
			set newlogfiles [glob $backupdir/log*]
			error_check_bad more_logs $newlogfiles $logfiles
		}
		# The hotbackup utility runs recovery on the backup,
		# so we will find the same files in backup and source.
		if { $bloption == "blob" } {
			set files [glob -nocomplain \
			    $testdir/blobs/$blob_subdir/*]
			set bl_files [glob -nocomplain \
			    $backupdir/__db_bl/$blob_subdir/*]
			error_check_good more_blobs \
			    [llength $files] [llength $bl_files]
		}

		puts "\tBackuptest.c.1: API hot backup $dirmsg."
		if { [catch {eval $env backup $bu_flags\
		    -single_dir -update $backupapidir} res] } {
			error "FAIL: $res"
		}
		if { $num_slices > 0 } {
			set i 0
			foreach slice $slices {
				file mkdir $backupapidir/__db.slice00$i
				if { [catch {eval $env backup $bu_flags\
				    -single_dir -update \
				    $backupapidir/__db.slice00$i} res] } {
					error "FAIL: $res"
				}
				incr i
			}	
		}
		error_check_good db_backup\
		    [file exists $backupapidir/$testfile] 1
		if { $cpoption == "deep" } {
			error_check_good subdb1_backup \
			    [file exists $backupapidir/$subfile1] 1
			error_check_good subdb2_backup \
			    [file exists $backupapidir/$subfile2] 1
		}
		for {set i 0} {$i < $num_slices} {incr i} {
			error_check_good slice_backedup$i [file exists \
			    $backupapidir/__db.slice00$i/$testfile] 1
		}
		if { $num_slices == 0 } {
			set newlogfiles [glob $backupapidir/log*]
			error_check_bad more_logs $newlogfiles $logfiles
		}
		# API hot backup does not run recovery on the backup.
		# So the number of blob files in the backup and
		# source are different.
		if { $bloption == "blob" } {
			set bl_files [glob -nocomplain \
			    $backupapidir/__db_bl/$blob_subdir/*]
			error_check_bad no_more_blobs \
			    [llength $files] [llength $bl_files]
		}
	}

	puts "\tBackuptest.d.0: Hot backup with full path."
	set fullpath [pwd]
	set bk_dirflags "-d $fullpath/$testdir/data1"
	if { $bloption == "blob" } {
		append bk_dirflags " -i $fullpath/$testdir/blobs"
	}

	if { $ckpoption == "checkpoint" } {
		catch {eval exec $util_path/db_hotbackup -${c}${deep}vh\
		    $fullpath/$testdir -b backup $bk_dirflags} res
			error_check_good c_and_d [is_substr $res $msg2] 1
	} else {
		if {[catch {eval exec $util_path/db_hotbackup -${c}${deep}vh\
		    $fullpath/$testdir -b backup $bk_dirflags} res] } {
			error "FAIL: $res"
		}
		for {set i 0} {$i < $num_slices} {incr i} {
			set slice_flags \
			    "-d $fullpath/$testdir/__db.slice00$i/data1"
			if {[catch {eval exec $util_path/db_hotbackup \
			    -${c}vh $testdir/__db.slice00$i -b backup \
			    $slice_flags} \
			    res] } {
				error "FAIL: $res"
			}
		}
		
	}
	error_check_good db_close [$db close] 0
	if { $cpoption == "deep" } {
		error_check_good subdb1_close [$subdb1 close] 0
		error_check_good supdb2_close [$subdb2 close] 0
	}
	error_check_good env_close [$env close] 0
	env_cleanup $testdir
	env_cleanup $backupdir
	env_cleanup $backupapidir
	# Back up with absolute data/log/blob path but
	# without -single_dir will fail.
	# Testing for this error once is enough.
	if { $bloption == "blob" && \
	    $txnmode == "txn" && $ckpoption == "nocheckpoint" } {
		file mkdir $testdir/data
		file mkdir $testdir/log

		puts "\tBackuptest.d.1: API Hot backup\
		    with full path but without -single_dir."

		foreach dir { data log blob } {
			set dflags "-${dir}_dir $fullpath/$testdir/$dir"
			set env [eval {berkdb_env_noerr} $env_flags $dflags]
			error_check_good envopen [is_valid_env $env] TRUE
			set ret [catch {eval $env backup \
			    $bu_flags $backupapidir} res]
			error_check_bad backup_fullpath $ret 0
			error_check_good backup_failmsg1 \
			    [is_substr $res "absolute path"] 1
			if { $dir == "blob" } {
				error_check_good backup_failmsg2 \
				    [is_substr $res "external file directory"] 1
			} else {
				error_check_good backup_failmsg2 \
				    [is_substr $res "$dir directory"] 1
			}
			error_check_good env_close [$env close] 0
		}

		env_cleanup $testdir
		env_cleanup $backupapidir
	}

	puts "\tBackuptest.e: Hot backup with DB_CONFIG."
	backuptest_makeconfig $bloption $num_slices
	set msg3 "use of -l with DB_CONFIG file is deprecated"

	set env [eval {berkdb_env_noerr} $env_flags]
	set db [eval {berkdb_open} -env $env $db_flags]
	error_check_good envopen [is_valid_env $env] TRUE
	error_check_good dbopen [is_valid_db $db] TRUE
	if { $cpoption == "deep" } {
		file mkdir $testdir/data1/$subdir1
		file mkdir $testdir/data1/$subdir2
		set subdb1 [eval {berkdb_open} \
				-env $env $subdb_flags $subfile1 ]
		error_check_good subdb1open [is_valid_db $subdb1] TRUE
		set subdb2 [eval {berkdb_open} \
				-env $env $subdb_flags $subfile2 ]
		error_check_good subdb2open [is_valid_db $subdb2] TRUE
	}

	if { $num_slices > 0 } {
		set txn ""
	} elseif { $txnmode == "bulk" } {
		set txn [$env txn -txn_bulk]
	} else {
		set txn [$env txn]
	}

	populate $db $omethod $txn $nentries 0 0
	if { $cpoption == "deep" } {
		populate $subdb1 $omethod $txn $nentries 0 0
		populate $subdb2 $omethod $txn $nentries 0 0
	}
	if { $txn != "" } {
		error_check_good txn_commit [$txn commit] 0
	}
	error_check_good db_sync [$db sync] 0
	if { $cpoption == "deep" } {
		error_check_good subdb1_sync [$subdb1 sync] 0
		error_check_good subdb2_sync [$subdb2 sync] 0
	}

	set bk_dirflags "-l logs -d $testdir/data1"
	if { $bloption == "blob" } {
		append bk_dirflags " -i blobs"
	}
	# With checkpoint, this fails.  Without checkpoint, 
	# just look for the warning message.
	if { $ckpoption == "checkpoint" } {
		catch {eval exec $util_path/db_hotbackup\
		    -${c}${deep}vh $testdir -b $backupdir $bk_dirflags} res
		error_check_good c_and_d [is_substr $res $msg2] 1
	} else {
		catch {eval exec $util_path/db_hotbackup\
		    -${c}${deep}vh $testdir -b $backupdir $bk_dirflags} res
		error_check_good l_and_config [is_substr $res $msg3] 1
		for {set i 0} {$i < $num_slices} {incr i} {
			if {[catch {eval exec $util_path/db_hotbackup\
			    -${c}vh $testdir/__db.slice00$i -b \
			    $backupdir/__db.slice00$i \
			    "-l logs -d data1"} res]} {
				error "FAIL: $res"
			}
		}

		# Check that logs and db are in backupdir.
		error_check_good db_backup [file exists $backupdir/$testfile] 1
		if { $cpoption == "deep" } {
			error_check_good subdb1_backup \
			    [file exists $backupdir/$subfile1] 1
			error_check_good subdb2_backup \
			    [file exists $backupdir/$subfile2] 1
		}
		set logfiles [glob $backupdir/log*]
		error_check_bad logs_backed_up [llength $logfiles] 0
		# Check that blobs are in backupdir/__db_bl.
		if { $bloption == "blob" } {
			set blob_subdir [$db get_blob_sub_dir]
			set bl_files [glob -nocomplain \
			    $backupdir/__db_bl/$blob_subdir/*]
			error_check_bad blobs_backed_up [llength $bl_files] 0
			error_check_bad blobdir_backed_up \
			    [file exists $backupdir/blobs] 1
		}
		for {set i 0} {$i < $num_slices} {incr i} {
			set logfiles [glob $backupdir/__db.slice00$i/log*]
			error_check_bad logs_backedup$i [llength $logfiles] 0
			error_check_good slice_backedup$i [file exists \
			    $backupdir/__db.slice00$i/$testfile] 1
		}
		puts "\tBackuptest.e.1: API hot backup with DB_CONFIG."
		if { [catch {eval $env backup $bu_flags\
		    -single_dir $backupapidir} res] } {
			error "FAIL: $res"
		}

		if { $num_slices > 0 } {
			set slices [$env get_slices]
			set i 0
			foreach slice $slices {
				file mkdir $backupapidir/__db.slice00$i
				if { [catch {eval $env backup $bu_flags\
				    -single_dir $backupapidir/__db.slice00$i}\
				    res] } {
					error "FAIL: $res"
				}
				incr i
			}	
		}
		error_check_good db_backup\
		    [file exists $backupapidir/$testfile] 1
		if { $cpoption == "deep" } {
			error_check_good found_subdb1 \
			    [file exists $backupapidir/$subfile1] 1
			error_check_good found_subdb2 \
			    [file exists $backupapidir/$subfile2] 1
		}
		set logfiles [glob $backupapidir/log*]
		error_check_bad logs_backed_up [llength $logfiles] 0
		if { $bloption == "blob" } {
			set bl_files [glob -nocomplain \
			    $backupapidir/__db_bl/$blob_subdir/*]
			error_check_bad blobs_backed_up [llength $bl_files] 0
			error_check_bad blobdir_backed_up \
			    [file exists $backupapidir/blobs] 1
		}
		for {set i 0} {$i < $num_slices} {incr i} {
			set slogfiles [glob $backupapidir/__db.slice00$i/log*]
			error_check_bad logs_backedup$i [llength $slogfiles] 0
			error_check_good slice_backedup$i [file exists \
			    $backupapidir/__db.slice00$i/$testfile] 1
		}
	}

	if { $num_slices > 0 } {
		set txn ""
	} elseif { $txnmode == "bulk" } {
		set txn [$env txn -txn_bulk]
	} else {
		set txn [$env txn]
	}

	populate $db $omethod $txn [expr $nentries * 2] 0 0
	if { $cpoption == "deep" } {
		populate $subdb1 $omethod $txn [expr $nentries * 2] 0 0
		populate $subdb2 $omethod $txn [expr $nentries * 2] 0 0
	}
	if { $txn != "" } {
		error_check_good txn_commit [$txn commit] 0
	}
	error_check_good db_sync [$db sync] 0
	if { $cpoption == "deep" } {
		error_check_good subdb1_sync [$subdb1 sync] 0
		error_check_good subdb2_sync [$subdb2 sync] 0
	}

	puts "\tBackuptest.f: Hot backup update with DB_CONFIG."
	if { $ckpoption == "checkpoint" } {
		catch {eval exec $util_path/db_hotbackup\
		    -${c}${deep}vuh $testdir -b backup $bk_dirflags} res
		error_check_good c_and_d [is_substr $res $msg2] 1
	} else {
		catch {eval exec $util_path/db_hotbackup\
		    -${c}${deep}vuh $testdir -b backup $bk_dirflags} res
		error_check_good l_and_config [is_substr $res $msg3] 1
		for {set i 0} {$i < $num_slices} {incr i} {
			if {[catch {eval exec $util_path/db_hotbackup\
			    -${c}vuh $testdir/__db.slice00$i -b \
			    $backupdir/__db.slice00$i \
			    "-l logs -d data1"} res]} {
				error "FAIL: $res"
			}
		}

		# There should be more log files now if not using slices.
		if { $num_slices == 0 } {
			set newlogfiles [glob $backupdir/log*]
			error_check_bad more_logs $newlogfiles $logfiles
		}
		if { $bloption == "blob" } {
			set files [glob -nocomplain \
			    $testdir/blobs/$blob_subdir/*]
			set bl_files [glob -nocomplain \
			    $backupdir/__db_bl/$blob_subdir/*]
			error_check_good more_blobs \
			    [llength $files] [llength $bl_files]
		}

		puts "\tBackuptest.f.1: API hot backup with DB_CONFIG."
		if { [catch {eval $env backup $bu_flags\
		    -single_dir -update $backupapidir} res] } {
			error "FAIL: $res"
		}
		if { $num_slices > 0 } {
			set i 0
			foreach slice $slices {
				file mkdir $backupapidir/__db.slice00$i
				if { [catch {eval $slice backup $bu_flags \
				    -single_dir -update \
				    $backupapidir/__db.slice00$i} res] } {
					error "FAIL: $res"
				}
				incr i
			}	
		}
		error_check_good db_backup\
		    [file exists $backupapidir/$testfile] 1
		if { $cpoption == "deep" } {
			error_check_good found_subdb1 \
			    [file exists $backupapidir/$subfile1] 1
			error_check_good found_subdb2 \
			    [file exists $backupapidir/$subfile2] 1
		}
		for {set i 0} {$i < $num_slices} {incr i} {
			error_check_good db_backup_slice$i \
			    [file exists \
			    $backupapidir/__db.slice00$i/$testfile] 1
		}
		if { $num_slices == 0 } {
			set newlogfiles [glob $backupapidir/log*]
			error_check_bad more_logs $newlogfiles $logfiles
		}
		if { $bloption == "blob" } {
			set bl_files [glob -nocomplain \
			    $backupapidir/__db_bl/$blob_subdir/*]
			error_check_bad no_more_blobs \
			    [llength $files] [llength $bl_files]
		}
	}

	# Skip the next test if slices enabled, they depend on either
	# the slice directory containing a DB_CONFIG, or a transaction
	# covering multiple operations before being aborted.
	if { $num_slices > 0 } {
		error_check_good db_close [$db close] 0
		error_check_good env_close [$env close] 0
		env_cleanup $testdir
		env_cleanup $backupdir
		env_cleanup $backupapidir
		return
	}

	# Repeat with directories already there to test 
	# cleaning. We are not doing an update this time.
	puts "\tBackuptest.g.0: Hot backup with -D (non-update)."
	if { [catch { eval exec $util_path/db_hotbackup\
	    -${c}${deep}vh $testdir -b $backupdir -D } res] } {
		error "FAIL: $res"
	}
	# Check that DB_CONFIG file is in backupdir.
	error_check_good found_db_config\
	    [file exists $backupdir/DB_CONFIG] 1
	# Check that db is in backupdir/data1 and not in backupdir.
	error_check_good found_db\
	    [file exists $backupdir/data1/$testfile] 1
	if { $cpoption == "deep" } {
		error_check_good found_subdb1 \
		    [file exists $backupdir/data1/$subfile1] 1
		error_check_good found_subdb2 \
		    [file exists $backupdir/data1/$subfile2] 1
	}
	error_check_bad found_db\
	    [file exists $backupdir/$testfile] 1
	if { $cpoption == "deep" } {
		error_check_bad found_subdb1 \
		    [file exists $backupdir/$subfile1] 1
		error_check_bad found_subdb2 \
		    [file exists $backupdir/$subfile2] 1
	}
	# Check that logs are in backupdir/logs and not in backupdir.
	set logfiles [glob $backupdir/logs/log*]
	error_check_bad found_logs [llength $logfiles] 0
	set logfiles [glob $backupdir/log*]
	error_check_good found_logs [llength $logfiles] 1
	# Check that blobs are in backupdir/blobs.
	if { $bloption == "blob" } {
		set bl_files [glob -nocomplain \
		    $backupdir/blobs/$blob_subdir/*]
		error_check_bad found_blobs [llength $bl_files] 0
	}

	puts "\tBackuptest.g.1: API hot backup with -D (non-update)."
	if { [catch {eval $env backup $bu_flags $backupapidir} res] } {
		error "FAIL: $res"
	}
	# Check that DB_CONFIG file is in backupapidir.
	error_check_good found_db_config\
	    [file exists $backupapidir/DB_CONFIG] 1
	# Check that db is in backupapidir/data1 and not in backupapidir.
	error_check_good found_db\
	    [file exists $backupapidir/data1/$testfile] 1
	error_check_bad found_db\
	    [file exists $backupapidir/$testfile] 1
	if { $cpoption == "deep" } {
		error_check_good found_subdb1 \
		    [file exists $backupapidir/data1/$subfile1] 1
		error_check_good found_subdb2 \
		    [file exists $backupapidir/data1/$subfile2] 1
		error_check_bad found_subdb1 \
		    [file exists $backupapidir/$subfile1] 1
		error_check_bad found_subdb2 \
		    [file exists $backupapidir/$subfile2] 1
	}
	# Check that logs are in backupapidir/logs and not in backupapidir.
	set logfiles [glob $backupapidir/logs/log*]
	error_check_bad found_logs [llength $logfiles] 0
	set logfiles [glob $backupapidir/log*]
	error_check_good found_logs [llength $logfiles] 1

	# Check that blobs are in backupapidir/blobs.
	if { $bloption == "blob" } {
		set bl_files [glob -nocomplain \
		    $backupapidir/blobs/$blob_subdir/*]
		error_check_bad found_blobs [llength $bl_files] 0
	}

	# We are not doing an update this time.
	puts "\tBackuptest.g.2: Hot backup with DB_CONFIG (non-update)."
	if { [catch { eval exec $util_path/db_hotbackup\
	    -${c}${deep}vh $testdir -b $backupdir } res] } {
		error "FAIL: $res"
	}
	# Check that no DB_CONFIG file is in backupdir.
	error_check_bad found_db_config [file exists $backupdir/DB_CONFIG] 1
	# Check that db is in backupdir.
	error_check_good found_db [file exists $backupdir/$testfile] 1
	if { $cpoption == "deep" } {
		error_check_good subdb1_backup \
		    [file exists $backupdir/$subfile1] 1
		error_check_good subdb2_backup \
		    [file exists $backupdir/$subfile2] 1	
	}
	# Check that logs are in backupdir.
	set logfiles [glob $backupdir/log*]
	error_check_good found_logs [expr [llength $logfiles] > 1] 1
	# Check that blobs are in backupdir/__db_bl.
	if { $bloption == "blob" } {
		set bl_files [glob -nocomplain \
		    $backupdir/__db_bl/$blob_subdir/*]
		error_check_bad found_blobs [llength $bl_files] 0
	}

	puts "\tBackuptest.g.3: API hot backup with DB_CONFIG (non-update)."
	if { [catch {eval $env backup $bu_flags \
	    -single_dir $backupapidir} res] } {
		error "FAIL: $res"
	}
	# Check that no DB_CONFIG file is in backupapidir.
	error_check_bad found_db_config [file exists $backupapidir/DB_CONFIG] 1
	# Check that db is in backupapidir.
	error_check_good found_db [file exists $backupapidir/$testfile] 1
	if { $cpoption == "deep" } {
		error_check_good found_subdb1 \
		    [file exists $backupapidir/$subfile1] 1
		error_check_good found_subdb2 \
		    [file exists $backupapidir/$subfile2] 1
	}
	# Check that logs are in backupapidir.
	set logfiles [glob $backupapidir/log*]
	error_check_good found_logs [expr [llength $logfiles] > 1] 1
	# Check that blobs are in backupapidir/__db_bl/
	if { $bloption == "blob" } {
		set bl_files [glob -nocomplain \
		    $backupapidir/__db_bl/$blob_subdir/*]
		error_check_bad found_blobs [llength $bl_files] 0
	}

	error_check_good db_close [$db close] 0
	if { $cpoption == "deep" } {
		error_check_good subdb1_close [$subdb1 close] 0
		error_check_good supdb2_close [$subdb2 close] 0
	}
	error_check_good env_close [$env close] 0
	tclsleep 1

	set dirmsg "with different -log_dir, -data_dir"
	set dir_flags "-data_dir data1 -log_dir log1"
	if { $bloption == "blob" } {
		append dirmsg " and -blob_dir"
		append dir_flags " -blob_dir blobs1"
        }
	puts "\tBackuptest.h: Hot backup update $dirmsg."

	# Clean up previous environment.
	env_cleanup $testdir
	env_cleanup $backupdir 
	env_cleanup $backupapidir

	file mkdir $testdir/data1
	file mkdir $testdir/log1
	error_check_good db_data_dir\
	    [file exists $testdir/data1/$testfile] 0
	error_check_good db_log_dir\
	    [file exists $testdir/log1/$testfile] 0

	# Create a new environment with a different data dir.
	set env [eval {berkdb_env_noerr} $env_flags $dir_flags]
	set db [eval {berkdb_open} -env $env $db_flags]
	error_check_good envopen [is_valid_env $env] TRUE
	error_check_good dbopen [is_valid_db $db] TRUE
	if { $cpoption == "deep" } {
		file mkdir $testdir/data1/$subdir1
		file mkdir $testdir/data1/$subdir2
		set subdb1 [eval {berkdb_open} \
				-env $env $subdb_flags $subfile1 ]
		error_check_good subdb1open [is_valid_db $subdb1] TRUE
		set subdb2 [eval {berkdb_open} \
				-env $env $subdb_flags $subfile2 ]
		error_check_good subdb2open [is_valid_db $subdb2] TRUE
	}

	if { $txnmode == "bulk" } {
		set txn [$env txn -txn_bulk]
	} else {
		set txn [$env txn]
	}
			
	# Fill DB until several log files appear.
	set max_fill_cycle 20
	set min_log_num 3
	backuptest_makemorelogs $db $omethod $txn $testfile data1 log1 \
	    $nentries $max_fill_cycle $min_log_num
	error_check_good txn_commit [$txn commit] 0
	error_check_good db_sync [$db sync] 0

	# Make checkpoint here, prerequisite environment built.
	if {[catch {$env txn_checkpoint}]} {
		error "FAIL: $res"
	}

	puts "\tBackuptest.h2: Full hot backup with -l"

	if {[catch {eval exec $util_path/db_hotbackup -${deep}vh \
	    $testdir -b $backupdir -d $testdir/data1 -l log1} res] } {
		error "FAIL: <$res>"
	}

	puts "\tBackuptest.h3: Hot backup update with -l -u"
	if {[catch {eval exec $util_path/db_hotbackup -${deep}vh \
	    $testdir -b $backupdir -d $testdir/data1 -l log1 -u} res] } {
		error "FAIL: <$res>"
	}

	error_check_good db_close [$db close] 0
	if { $cpoption == "deep" } {
		error_check_good subdb1_close [$subdb1 close] 0
		error_check_good supdb2_close [$subdb2 close] 0
	}
	error_check_good env_close [$env close] 0
	
	# Whether deep copy is used or not has no effect on the
	# incremental tests, so quit.
	if { $cpoption == "deep" } {
		env_cleanup $testdir
		env_cleanup $backupdir 
		env_cleanup $backupapidir
		return
	}

	foreach bumode { util api } {
		if { $bumode == "util" } {
			set s "i"
			set msg "Hot backup"
			set dir $backupdir
		} else {
			set s "j"
			set msg "API hot backup"
			set dir $backupapidir
		}

		puts "\tBackuptest.$s: Incremental $msg\
		    with an active txn as log files are added."

		# Clean up previous environment.
		env_cleanup $testdir
		env_cleanup $dir

		set env [eval {berkdb_env} $env_flags]
		error_check_good envopen [is_valid_env $env] TRUE
		if { $bumode == "util" } {
			set e NULL
		} else {
			set e $env
		}

		# Set up the db open flags.
		if { $txnmode == "bulk" } {
			set txn [$env txn -txn_bulk]
		} else {
			set txn [$env txn]
		}

		# Open db and commit the txn.
		set db [eval {berkdb_open} -env $env\
		    -txn $txn -create $omethod $testfile]
		error_check_good dbopen [is_valid_db $db] TRUE
		error_check_good txn_commit [$txn commit] 0

		# Verify the db file is created.
		error_check_good found_db [file exists $testdir/$testfile] 1

		# Verify there is at least one log file.
		set log_list [glob -nocomplain -directory $testdir log.*]
		set log_num [llength $log_list]
		error_check_bad found_logs $log_num 0

		# Fill db and commit the txn.
		if { $txnmode == "bulk" } {
			set txn [$env txn -txn_bulk]
		} else {
			set txn [$env txn]
		}
		populate $db $omethod $txn $nentries 0 0 
		error_check_good txn_commit [$txn commit] 0
		error_check_good db_sync [$db sync] 0

		# Verify there are blobs.
		if { $bloption == "blob" } {
			set blob_subdir [$db get_blob_sub_dir]
			set bl_files [glob -nocomplain \
			    $testdir/__db_bl/$blob_subdir/*]
			error_check_bad found_blobs [llength $bl_files] 0
		}

		# Begin another txn and fill db until there are more log files.
		if { $txnmode == "bulk" } {
			set txn [$env txn -txn_bulk]
		} else {
			set txn [$env txn]
		}
		set log_list [glob -nocomplain -directory $testdir log.*]
		set log_num [llength $log_list]
		incr log_num
		set max_fill_cycle 3
		backuptest_makemorelogs $db $omethod $txn\
		    $testfile NULL NULL $nentries $max_fill_cycle $log_num
		error_check_good db_sync [$db sync] 0

		# Backup/backupapi directory should be empty.
		set files [glob -nocomplain $dir/*]
		error_check_good no_files [llength $files] 0

		puts "\tBackuptest.$s.0: $msg when txn is active."
		backup_and_recover $e $bu_flags $dir $c ""

		puts "\tBackuptest.$s.1: Verify the db and\
		    log files are in the $dir directory."
		error_check_good found_db [file exists $dir/$testfile] 1
		set log_list [glob -nocomplain -directory $dir log.*]
		error_check_bad found_logs [llength $log_list] 0
		if { $bloption == "blob" } {
			set bl_files [glob -nocomplain \
			    $dir/__db_bl/$blob_subdir/*]
			error_check_bad found_blobs [llength $bl_files] 0
			dump_compare_blobs $testdir/$testfile $dir/$testfile \
			    $testdir/__db_bl $dir/__db_bl
		} else {
			dump_compare $testdir/$testfile $dir/$testfile
		}

		#
		# Txn continues filling db until there are
		# more log files and then aborts.
		#
		puts "\tBackuptest.$s.2: Txn continues and then aborts."
		set log_list [glob -nocomplain -directory $testdir log.*]
		set log_num [llength $log_list]
		incr log_num
		set max_fill_cycle 20
		backuptest_makemorelogs $db $omethod $txn\
		    $testfile NULL NULL $nentries $max_fill_cycle $log_num
		error_check_good txn_abort [$txn abort] 0
		error_check_good db_sync [$db sync] 0

		puts "\tBackuptest.$s.3: $msg (update)."
		# Set up the backup flags.
		set bflags "-create"
		backup_and_recover $e $bflags $dir $c u

		puts "\tBackuptest.$s.4: Compare the db files."
		if { $bloption == "blob" } {
			dump_compare_blobs $testdir/$testfile $dir/$testfile \
			    $testdir/__db_bl $dir/__db_bl
		} else {
			dump_compare $testdir/$testfile $dir/$testfile
		}

		# Open another txn and fill db until there are more log files.
		puts "\tBackuptest.$s.5: Begin another txn and fill db."
		if { $txnmode == "bulk" } {
			set txn [$env txn -txn_bulk]
		} else {
			set txn [$env txn]
		}
		set log_list [glob -nocomplain -directory $testdir log.*]
		set log_num [llength $log_list]
		incr log_num
		backuptest_makemorelogs $db $omethod $txn\
		    $testfile NULL NULL $nentries $max_fill_cycle $log_num

		puts "\tBackuptest.$s.6: $msg (update) when the txn is active."
		backup_and_recover $e $bflags $dir $c u

		puts "\tBackuptest.$s.7: Compare the db files."
		if { $bloption == "blob" } {
			dump_compare_blobs $testdir/$testfile $dir/$testfile \
			    $testdir/__db_bl $dir/__db_bl
		} else {
			dump_compare $testdir/$testfile $dir/$testfile
		}

		#
		# Txn continues filling db until there are more
		# log files and then commits.
		#
		puts "\tBackuptest.$s.8: Txn continues and then commits."
		set log_list [glob -nocomplain -directory $testdir log.*]
		set log_num [llength $log_list]
		incr log_num
		backuptest_makemorelogs $db $omethod $txn\
		    $testfile NULL NULL $nentries $max_fill_cycle $log_num
		error_check_good txn_commit [$txn commit] 0
		error_check_good db_sync [$db sync] 0

		puts "\tBackuptest.$s.9: $msg (update)."
		backup_and_recover $e $bflags $dir $c u

		puts "\tBackuptest.$s.10: Compare the db files."
		if { $bloption == "blob" } {
			dump_compare_blobs $testdir/$testfile $dir/$testfile \
			    $testdir/__db_bl $dir/__db_bl
		} else {
			dump_compare $testdir/$testfile $dir/$testfile
		}

		error_check_good db_close [$db close] 0
		error_check_good env_close [$env close] 0
	}
}

proc backuptest_makeconfig { bloption { num_slices 0 } } {
	source ./include.tcl

	file mkdir $testdir/logs
	file mkdir $testdir/data1
	for {set i 0} {$i < $num_slices} {incr i} {
		file mkdir $testdir/__db.slice00$i/logs
		file mkdir $testdir/__db.slice00$i/data1
	}

	set cid [open $testdir/DB_CONFIG w]
	if { $num_slices > 0 } {
		puts $cid "set_slice_count $num_slices"
	}
	puts $cid "set_lg_dir logs"
	puts $cid "set_data_dir data1"
	if { $bloption == "blob" } {
		puts $cid "set_blob_dir blobs"
	}
	if { $num_slices > 0 } {
		puts $cid "slice all set_data_dir data1"
		puts $cid "slice all set_lg_dir logs"
	}
	close $cid
}

proc backuptest_makemorelogs { db omethod txn dbfile data_dir lg_dir
    nentries max_fill_cycle min_log_num } {
	source ./include.tcl

	if { $data_dir != "NULL" } {
		set db_path "$testdir/$data_dir/$dbfile"
	} else {
		set db_path "$testdir/$dbfile"
	}

	if { $lg_dir != "NULL" } {
		set lg_path "$testdir/$lg_dir"
	} else {
		set lg_path "$testdir"
	}

	set fill_cycle 0
	while { 1 } {
		incr fill_cycle

		populate $db $omethod $txn $nentries 0 0

		# Check whether number of log files is enough.
		set log_list\
		    [glob -directory $lg_path log.*]
		set log_num [llength $log_list]
		if { $log_num >= $min_log_num } {
			break
		} elseif { $max_fill_cycle <= $fill_cycle } {
			error "FAIL: max_fill_cycle exceeded, could not\
			    generate requested number of log files."
		}
	}

	# Check that data went into data_dir.
	error_check_good db_data_dir\
	    [file exists $db_path] 1
}

proc backup_and_recover { env flags dir ckp update } {
	source ./include.tcl

	# If env handle is passed, then backup by API, otherwise by utility.
	if { $env != "NULL" } {
		# Make a txn checkpoint.
		if { $ckp != "" } {
			error_check_good txn_ckp [$env txn_checkpoint] 0
		}

		# Check if we need to add "-update" to the backup flags.
		if { $update != "" } {
			set indx [lsearch -exact $flags "-update"]
			if { $indx == -1 } {
				set flags "$flags -update "
			}
		}

		# Hot backup by API.
		if { [catch {eval $env backup $flags $dir} res] } {
			error "FAIL $env backup: $res"
		}

		# Recover the backup.
		set benv [eval {berkdb env} -home $dir\
		    -create -log -txn -private -recover_fatal]
		error_check_good is_valid_env [is_valid_env $benv] TRUE
		error_check_good env_close [$benv close] 0

	} else {
		# Hot backup by utility.
		if {[catch { eval exec $util_path/db_hotbackup\
		    -${ckp}${update}vh $testdir -b $dir } res] } {
			error "FAIL db_hotbackup: $res"
		}
	}
}
