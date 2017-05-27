package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.data.LayerDimensions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladk on 12.04.2017.
 */
public abstract class NetLayer {

    final LayerDimensions layerDimensions;
    final DataSet deltas;
    final List<DataSet> weights;
    final DataSet prevOutputs;
    final DataSet selfOutputs;
    final ActivationFunction activationFunction;

    abstract void forward(final DataSet dataSet);

    abstract void backward(final DataSet deltas, final List<DataSet> childrenWeights);

    abstract void lastLayerBackward(final DataSet deltas, final DataSet y, final DataSet outputs);

    NetLayer(final Dimension inputDimension,
             final Dimension outputDimension,
             final List<DataSet> weights,
             final ActivationFunction activationFunction) {
        this(new LayerDimensions(inputDimension, outputDimension), weights, activationFunction);
    }

    NetLayer(final LayerDimensions dimensions,
             final List<DataSet> weights,
             final ActivationFunction activationFunction) {
        this.layerDimensions = dimensions;
        this.deltas = new DataSet(dimensions.getOutputDimension(), () -> 1);
        this.prevOutputs = new DataSet(dimensions.getInputDimension(), () -> 0);
        this.selfOutputs = new DataSet(dimensions.getOutputDimension(), () -> 0);
        this.activationFunction = activationFunction;
        this.weights = weights;
    }

    LayerDimensions getLayerDimensions() {
        return layerDimensions;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public List<DataSet> getWeights() {
        return weights;
    }

    DataSet getDeltas() {
        return deltas;
    }

    DataSet getPrevOutputs() {
        return prevOutputs;
    }

    DataSet getSelfOutputs() {
        return selfOutputs;
    }
}
