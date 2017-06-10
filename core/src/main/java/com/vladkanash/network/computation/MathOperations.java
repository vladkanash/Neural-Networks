package com.vladkanash.network.computation;

import java.util.ArrayList;
import java.util.List;

import com.vladkanash.network.data.DataSet;

/**
 * Created by vladk on 23.04.2017.
 */
public interface MathOperations {

    DataSet forwardLayer(final DataSet weights, final DataSet input);

    DataSet backwardLayer(final DataSet weights, final DataSet delta);

    DataSet outerProduct(final DataSet vectorA, final DataSet vectorB);

    DataSet convolve(final List<DataSet> kernels, final DataSet input, final int padding);

    DataSet convolveGradient(final DataSet deltas, final DataSet input, final int padding);

    default DataSet convolve(final DataSet kernel, final DataSet input, final int padding) {
        final List<DataSet> kernels = new ArrayList<>();
        kernels.add(kernel);
        return this.convolve(kernels, input, padding);
    }
}
