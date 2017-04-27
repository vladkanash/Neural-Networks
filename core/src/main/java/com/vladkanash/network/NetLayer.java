package com.vladkanash.network;

import java.util.Collections;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.data.LayerDimensions;

/**
 * Created by vladk on 12.04.2017.
 */
abstract class NetLayer {

    protected final LayerDimensions layerDimensions;
    protected final DataSet deltas;
    protected final DataSet outputs;
    protected boolean lastLayer = true;

    abstract void forward(final DataSet dataSet);

    abstract void backward(final DataSet deltas, final DataSet y);

    NetLayer(final Dimension inputDimension, final Dimension outputDimension) {
        this(new LayerDimensions(inputDimension, outputDimension));
    }

    NetLayer(final LayerDimensions dimensions) {
        this.layerDimensions = dimensions;
        this.deltas = new DataSet(dimensions.getOutputDimension());
        this.outputs = new DataSet(dimensions.getOutputDimension());
    }

    LayerDimensions getLayerDimensions() {
        return layerDimensions;
    }

    void setLastLayer(boolean lastLayer) {
        this.lastLayer = lastLayer;
    }
}
