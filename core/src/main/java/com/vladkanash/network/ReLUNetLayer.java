package com.vladkanash.network;

import java.util.function.UnaryOperator;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.LayerDimensions;

public class ReLUNetLayer extends SimpleFunctionNetLayer {

    ReLUNetLayer(final UnaryOperator<Double> function, final LayerDimensions dimensions) {
        super(function, dimensions);
    }

    @Override
    void backward(final DataSet deltas, final DataSet y) {

    }
}
