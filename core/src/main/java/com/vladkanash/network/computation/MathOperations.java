package com.vladkanash.network.computation;

import com.vladkanash.network.data.DataSet;

/**
 * Created by vladk on 23.04.2017.
 */
public interface MathOperations {

    DataSet forwardLayer(final DataSet weights, final DataSet input);

    DataSet backwardLayer(final DataSet weights, final DataSet delta);

    DataSet outerProduct(final DataSet vectorA, final DataSet vectorB);

    DataSet convolve(final DataSet kernel, final DataSet input);
}
