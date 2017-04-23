package com.vladkanash.network.computation;

import com.vladkanash.network.DataSet;

/**
 * Created by vladk on 23.04.2017.
 */
public interface MathOperations {

    DataSet getFullyConnOutput(final DataSet weights, final DataSet input);
}
