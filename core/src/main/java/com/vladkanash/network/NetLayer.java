package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.data.LayerDimensions;

/**
 * Created by vladk on 12.04.2017.
 */
abstract class NetLayer {

    protected final LayerDimensions layerDimensions;
    protected final DataSet deltas;
    protected final DataSet prevOutputs;
    protected final DataSet selfOutputs;
    protected final ActivationFunction activationFunction;

    abstract void forward(final DataSet dataSet);

    abstract void backward(final DataSet deltas, final DataSet childrenWeights);

    void lastLayerBackward(final DataSet deltas, final DataSet y, final DataSet outputs) {
        throw new UnsupportedOperationException();
    }

    NetLayer(final Dimension inputDimension,
             final Dimension outputDimension,
             final ActivationFunction activationFunction) {
        this(new LayerDimensions(inputDimension, outputDimension), activationFunction);
    }

    NetLayer(final LayerDimensions dimensions,
             final ActivationFunction activationFunction) {
        this.layerDimensions = dimensions;
        this.deltas = new DataSet(dimensions.getOutputDimension(), () -> 1);
        this.prevOutputs = new DataSet(dimensions.getInputDimension(), () -> 0);
        this.selfOutputs = new DataSet(dimensions.getOutputDimension(), () -> 0);
        this.activationFunction = activationFunction;
    }

    LayerDimensions getLayerDimensions() {
        return layerDimensions;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
}
