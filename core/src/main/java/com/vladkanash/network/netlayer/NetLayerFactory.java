package com.vladkanash.network.netlayer;

import com.vladkanash.network.Dimension;
import com.vladkanash.network.LayerDimensions;

/**
 * Created by vladk on 23.04.2017.
 */
public class NetLayerFactory {

    private NetLayerFactory() {}

    public static SimpleFunctionNetLayer ReLU(final Dimension dimension) {
        return new SimpleFunctionNetLayer(e -> e > 0 ? e : 0, new LayerDimensions(dimension));
    }

    public static SimpleFunctionNetLayer sigmoid(final Dimension dimension) {
        //TODO sigmoid function
        return new SimpleFunctionNetLayer(null, new LayerDimensions(dimension));
    }


}
