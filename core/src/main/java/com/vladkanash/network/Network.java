package com.vladkanash.network;

import com.vladkanash.api.layers.Layer;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.apache.commons.lang3.Validate;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by vladk on 13.04.2017.
 */
public class Network {

    private final LinkedList<NetLayer> layers = new LinkedList<>();
    private final Dimension inputDimension;

    public Network(final Dimension inputDimension) {
        Validate.notNull(inputDimension, "input dimension must not be null");
        this.inputDimension = inputDimension;
    }

    public void addLayer(final Layer layer) {
        Validate.notNull(layer, "layer must not be null");

        NetLayer newLayer = null;
        final Dimension inputDim = getTopDimension();
        switch(layer.getType()) {
            case FULLY_CONNECTED: {
                newLayer = new FullyConnectedNetLayer(layer.getNeuronCount(), inputDim, layer.getActivationFunction());
                break;
            }
            case CONVOLUTION: {
                newLayer = new ConvolutionNetLayer(inputDim, layer.getFilterSize(), layer.getNeuronCount(), layer.getActivationFunction());
                break;
            }
            default: {
                break;
            }
        }

        this.layers.add(newLayer);
    }

    public Double[] forward(final Double[] data) {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Cannot call forward() on empty network");
        }

        final DataSet dataSet = new DataSet(data, inputDimension);
        this.layers.forEach(l -> l.forward(dataSet));
        return dataSet.getWrapperArrayData();
    }

    public DataSet forward(final DataSet data) {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Cannot call forward() on empty network");
        }
        final DataSet dataSet = new DataSet(data);
        this.layers.forEach(l -> l.forward(dataSet));
        return dataSet;
    }

    void backward(final DataSet y, final DataSet outputs) {
        final Iterator<NetLayer> iter = layers.descendingIterator();
        final DataSet deltas = new DataSet(y.getDimension(), () -> 1);

        NetLayer lastLayer = null;
        while (iter.hasNext()) {
            final NetLayer layer = iter.next();
            if (lastLayer == null) {
                layer.lastLayerBackward(deltas, y, outputs);
            } else {
                layer.backward(deltas, lastLayer.getWeights());
            }
            lastLayer = layer;
        }
    }

    public Dimension getInputDimension() {
        return inputDimension;
    }

    public boolean isEmpty() {
        return this.layers.isEmpty();
    }

    private NetLayer getBottomLayer() {
        return layers.isEmpty() ? null : layers.getFirst();
    }

    private Dimension getTopDimension() {
        return layers.isEmpty() ? inputDimension : layers.getLast().getLayerDimensions().getOutputDimension();
    }

    private NetLayer getTopLayer() {
        return layers.isEmpty() ? null : layers.getLast();
    }
}
