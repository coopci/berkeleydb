/*-
 * Copyright (c) 2002, 2019 Oracle and/or its affiliates.  All rights reserved.
 *
 * See the file LICENSE for license information.
 *
 */

package com.sleepycat.client.collections.test;

import com.sleepycat.client.collections.TransactionRunner;
import com.sleepycat.client.collections.TransactionWorker;
import com.sleepycat.client.SEnvironment;
import com.sleepycat.client.util.ExceptionUnwrapper;

class NullTransactionRunner extends TransactionRunner {

    NullTransactionRunner(SEnvironment env) {

        super(env);
    }

    public void run(TransactionWorker worker)
        throws Exception {

        try {
            worker.doWork();
        } catch (Exception e) {
            throw ExceptionUnwrapper.unwrap(e);
        }
    }
}
