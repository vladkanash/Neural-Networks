package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;

/**
 * Created by vladk on 07.05.2017.
 */
public class SoftmaxNetLayer extends NetLayer {


    SoftmaxNetLayer(
            final Dimension inputDimension,
            final Dimension outputDimension,
            final DataSet weights,
            final ActivationFunction activationFunction) {
        super(inputDimension, outputDimension, weights, activationFunction);
    }

    @Override
    void forward(final DataSet dataSet) {

    }

    @Override
    void backward(final DataSet deltas, final DataSet childrenWeights) {

    }

    @Override
    void lastLayerBackward(final DataSet deltas, final DataSet y, final DataSet outputs) {

    }
}
