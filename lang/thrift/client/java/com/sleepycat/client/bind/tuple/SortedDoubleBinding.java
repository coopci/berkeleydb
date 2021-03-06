/*-
 * Copyright (c) 2000, 2019 Oracle and/or its affiliates.  All rights reserved.
 *
 * See the file LICENSE for license information.
 *
 */

package com.sleepycat.client.bind.tuple;

import com.sleepycat.client.SDatabaseEntry;

/**
 * A concrete <code>TupleBinding</code> for a sorted <code>Double</code>
 * primitive wrapper or a sorted <code>double</code> primitive.
 *
 * <p>There are two ways to use this class:</p>
 * <ol>
 * <li>When using the {@link com.sleepycat.client} package directly, the static
 * methods in this class can be used to convert between primitive values and
 * {@link SDatabaseEntry} objects.</li>
 * <li>When using the {@link com.sleepycat.client.collections} package, an instance of
 * this class can be used with any stored collection.</li>
 * </ol>
 *
 * @see <a href="package-summary.html#floatFormats">Floating Point Formats</a>
 */
public class SortedDoubleBinding extends TupleBinding<Double> {

    /* javadoc is inherited */
    public Double entryToObject(TupleInput input) {

        return input.readSortedDouble();
    }

    /* javadoc is inherited */
    public void objectToEntry(Double object, TupleOutput output) {

        output.writeSortedDouble(object);
    }

    /* javadoc is inherited */
    protected TupleOutput getTupleOutput(Double object) {

        return DoubleBinding.sizedOutput();
    }

    /**
     * Converts an entry buffer into a simple <code>double</code> value.
     *
     * @param entry is the source entry buffer.
     *
     * @return the resulting value.
     */
    public static double entryToDouble(SDatabaseEntry entry) {

        return entryToInput(entry).readSortedDouble();
    }

    /**
     * Converts a simple <code>double</code> value into an entry buffer.
     *
     * @param val is the source value.
     *
     * @param entry is the destination entry buffer.
     */
    public static void doubleToEntry(double val, SDatabaseEntry entry) {

        outputToEntry(DoubleBinding.sizedOutput().writeSortedDouble(val),
                      entry);
    }
}
