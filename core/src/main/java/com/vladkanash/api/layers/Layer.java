package com.vladkanash.api.layers;

import com.vladkanash.network.data.Dimension;
import org.apache.commons.lang3.Validate;

/**
 * Created by vladk on 23.04.2017.
 */
public class Layer {

    public enum LayerType {
        CONVOLUTION,
        FULLY_CONNECTED,
        RELU,
        SIGMOID
    }

    private int neuronCount;
    private Dimension filterSize;
    private LayerType type;

    private Layer() {}

    private Layer(final LayerType type, int neuronCount, final Dimension filterSize) {
        Validate.notNull(type, "type must not be null");
        Validate.notNull(filterSize, "filter size must not be null");
        Validate.isTrue(neuronCount >= 0, "neuron count cannot be negative");

        this.neuronCount = neuronCount;
        this.filterSize = filterSize;
        this.type = type;
    }

    private Layer(final LayerType type, int neuronCount) {
        this(type, neuronCount, Dimension.EMPTY);
    }

    private Layer(final LayerType type, final Dimension dimension) {
        this(type, 0, dimension);
    }

    private Layer(final LayerType type) {
        this(type, 0, Dimension.EMPTY);
    }

    public int getNeuronCount() {
        return neuronCount;
    }

    public Dimension getFilterSize() {
        return filterSize;
    }

    public LayerType getType() {
        return type;
    }

    public static Layer ReLU() {
        return new Layer(LayerType.RELU);
    }

    public static Layer sigmoid() {
        return new Layer(LayerType.SIGMOID);
    }

    public static Layer conv(final Dimension dimension) {
        return new Layer(LayerType.CONVOLUTION, dimension);
    }

    public static Layer fullyConn(int neuronCount) {
        return new Layer(LayerType.FULLY_CONNECTED, neuronCount);
    }
}
