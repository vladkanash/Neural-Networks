package com.vladkanash.network;

import java.util.Collections;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.data.LayerDimensions;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Created by vladk on 12.04.2017.
 */
abstract class NetLayer {

    protected final LayerDimensions layerDimensions;
    protected final DataSet deltas;
    protected final DataSet outputs;

    abstract void forward(final DataSet dataSet);

    abstract void backward(final DataSet deltas, final DataSet childrenWeights);

    void lastLayerBackward(final DataSet deltas, final DataSet y, final DataSet outputs) {
        throw new UnsupportedOperationException();
    }

    NetLayer(final Dimension inputDimension, final Dimension outputDimension) {
        this(new LayerDimensions(inputDimension, outputDimension));
    }

    NetLayer(final LayerDimensions dimensions) {
        this.layerDimensions = dimensions;
        this.deltas = new DataSet(dimensions.getOutputDimension(), () -> 1);
        this.outputs = new DataSet(dimensions.getOutputDimension(), () -> 0);
    }

    LayerDimensions getLayerDimensions() {
        return layerDimensions;
    }
}
