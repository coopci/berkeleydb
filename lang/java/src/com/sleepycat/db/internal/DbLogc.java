/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.sleepycat.db.internal;

import com.sleepycat.db.*;
import java.util.Comparator;

public class DbLogc {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected DbLogc(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(DbLogc obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  /* package */ synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        throw new UnsupportedOperationException("C++ destructor does not have public access");
      }
      swigCPtr = 0;
    }
  }

	public synchronized void close(int flags) throws DatabaseException {
		try {
			close0(flags);
		} finally {
			swigCPtr = 0;
		}
	}

  /* package */ void close0(int flags) { db_javaJNI.DbLogc_close0(swigCPtr, this, flags); }

  public int get(com.sleepycat.db.LogSequenceNumber lsn, com.sleepycat.db.DatabaseEntry data, int flags) throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbLogc_get(swigCPtr, this, lsn, data, flags);
  }

  public int version(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbLogc_version(swigCPtr, this, flags); }

}
