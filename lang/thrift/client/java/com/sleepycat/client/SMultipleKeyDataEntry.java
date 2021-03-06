/*-
 * Copyright (c) 2002, 2019 Oracle and/or its affiliates.  All rights reserved.
 *
 * See the file LICENSE for license information.
 *
 * $Id$
 */

package com.sleepycat.client;

/**
 * A container that holds multiple key / data item pairs.
 */
public class SMultipleKeyDataEntry extends SMultiplePairs {

    /**
     * Append an entry to the container.
     *
     * @param key an array containing the key to be added
     * @param data an array containing the data to be added
     */
    public void append(byte[] key, byte[] data) {
        append(new SDatabaseEntry(key), new SDatabaseEntry(data));
    }
}
