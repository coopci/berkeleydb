/*-
 * Copyright (c) 2002, 2019 Oracle and/or its affiliates.  All rights reserved.
 *
 * See the file LICENSE for license information.
 *
 */

package com.sleepycat.client.persist.evolve;

/**
 * The listener interface called during eager entity evolution.
 *
 * @see com.sleepycat.client.persist.evolve Class Evolution
 * @author Mark Hayes
 */
public interface EvolveListener {

    /**
     * The listener method called during eager entity evolution.
     *
     * @param event the EvolveEvent.
     *
     * @return true to continue evolution or false to stop.
     */
    boolean evolveProgress(EvolveEvent event);
}
