package com.vladkanash.network;

import com.vladkanash.api.layers.Layer;
import com.vladkanash.network.netlayer.FullyConnectedNetLayer;
import com.vladkanash.network.netlayer.NetLayer;
import com.vladkanash.network.netlayer.NetLayerFactory;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vladk on 13.04.2017.
 */
public class Network {

    private final List<NetLayer> layers = new LinkedList<>();
    private final Dimension inputDimension;

    public Network(final Dimension inputDimension) {
        Validate.notNull(inputDimension, "input dimension must not be null");
        this.inputDimension = inputDimension;
    }

    public void addLayer(final Layer layer) {
        Validate.notNull(layer, "layer must not be null");

        NetLayer newLayer = null;
        switch(layer.getType()) {
            case RELU: {
                newLayer = NetLayerFactory.ReLU(getTopDimension());
                break;
            }
            case SIGMOID: {
                newLayer = NetLayerFactory.sigmoid(getTopDimension());
                break;
            }
            case FULLY_CONNECTED: {
                final Dimension inputDim = getTopDimension();
                final Dimension outputDim = new Dimension(1, 1, layer.getNeuronCount());
                newLayer = new FullyConnectedNetLayer(layer.getNeuronCount(), new LayerDimensions(inputDim, outputDim));
                break;
            }
            case CONVOLUTION: {
                break;
            }
            default: {
                break;
            }
        }

        this.layers.add(newLayer);
    }

    public Collection<Double> forward(final Double[] data) {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Cannot call forward() on empty network");
        }

        final DataSet dataSet = new DataSet(data, Dimension.EMPTY);
        this.layers.forEach(l -> l.forward(dataSet));
        return dataSet.getData();
    }

    private NetLayer getFirstLayer() {
        return layers.isEmpty() ? null : layers.get(0);
    }

    private Dimension getTopDimension() {
        return layers.isEmpty() ? inputDimension : layers.get(0).getLayerDimensions().getOutputDimension();
    }

    public Dimension getInputDimension() {
        return inputDimension;
    }
}
