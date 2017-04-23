package com.vladkanash.network.netlayer;

import com.vladkanash.network.DataSet;
import com.vladkanash.network.Dimension;
import com.vladkanash.network.LayerDimensions;

/**
 * Created by vladk on 12.04.2017.
 */
public abstract class NetLayer {

    private final LayerDimensions layerDimensions;

    public abstract void forward(final DataSet dataSet);

    public NetLayer(final Dimension inputDimension, final Dimension outputDimension) {
        this(new LayerDimensions(inputDimension, outputDimension));
    }

    public NetLayer(final LayerDimensions dimensions) {
        this.layerDimensions = dimensions;
    }

    public LayerDimensions getLayerDimensions() {
        return layerDimensions;
    }
}
