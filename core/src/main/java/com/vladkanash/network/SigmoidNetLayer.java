package com.vladkanash.network;

import java.util.function.UnaryOperator;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.LayerDimensions;

public class SigmoidNetLayer extends SimpleFunctionNetLayer {

    SigmoidNetLayer(final UnaryOperator<Double> function, final LayerDimensions dimensions) {
        super(function, dimensions);
    }

    @Override
    void backward(final DataSet deltas, final DataSet y) {
        deltas.update(new DataSet(
                outputs.getStreamData().map(e -> -e * (1 - e)).toArray(),
                deltas.getDimension()));
        this.deltas.update(deltas);
    }
}
