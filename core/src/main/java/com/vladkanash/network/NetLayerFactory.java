package com.vladkanash.network;

import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.data.LayerDimensions;
import org.apache.commons.math3.analysis.function.Sigmoid;

/**
 * Created by vladk on 23.04.2017.
 */
class NetLayerFactory {

    private NetLayerFactory() {}

    static NetLayer ReLU(final Dimension dimension) {
        return new ReLUNetLayer(e -> e > 0 ? e : 0, new LayerDimensions(dimension));
    }

    static NetLayer sigmoid(final Dimension dimension) {
        final Sigmoid sigmoid = new Sigmoid();
        return new SigmoidNetLayer(sigmoid::value, new LayerDimensions(dimension));
    }
}
