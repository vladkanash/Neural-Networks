package com.vladkanash.network;

import com.vladkanash.api.layers.Layer;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.apache.commons.lang3.Validate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

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

        if (getTopLayer() != null) {
            getTopLayer().setLastLayer(false);
        }
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
                newLayer = new FullyConnectedNetLayer(layer.getNeuronCount(), inputDim);
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

    public Double[] forward(final Double[] data) {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Cannot call forward() on empty network");
        }

        final DataSet dataSet = new DataSet(data, inputDimension);
        this.layers.forEach(l -> l.forward(dataSet));
        return dataSet.getWrapperArrayData();
    }

    void backward(final DataSet y) {
        final Iterator<NetLayer> iter = layers.descendingIterator();
        final DataSet deltas = new DataSet(y.getDimension());

        while (iter.hasNext()) {
            iter.next().backward(deltas, y);
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
        return layers.isEmpty() ? inputDimension : layers.get(0).getLayerDimensions().getOutputDimension();
    }

    private NetLayer getTopLayer() {
        return layers.isEmpty() ? null : layers.getLast();
    }
}
