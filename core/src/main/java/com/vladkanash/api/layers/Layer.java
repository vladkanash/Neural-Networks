package com.vladkanash.api.layers;

import java.util.function.DoubleUnaryOperator;

import com.vladkanash.network.data.Dimension;
import org.apache.commons.lang3.Validate;

/**
 * Created by vladk on 23.04.2017.
 */
public class Layer {

    public enum LayerType {
        CONVOLUTION,
        FULLY_CONNECTED,
        SOFTMAX,
        POOLING
    }

    private int neuronCount;
    private Dimension filterSize;
    private LayerType type;
    private ActivationFunction activationFunction;

    private Layer() {}

    private Layer(final LayerType type, int neuronCount, final Dimension filterSize) {
        Validate.notNull(type, "type must not be null");
        Validate.notNull(filterSize, "filter size must not be null");
        Validate.isTrue(neuronCount >= 0, "neuron count cannot be negative");

        this.activationFunction = ActivationFunction.IDENTITY;
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

    public Layer withActivationFunction(final ActivationFunction function) {
        Validate.notNull(function, "ActivationFunction must not be null");

        this.activationFunction = activationFunction;
        return this;
    }

    public Layer withSigmoidActivation() {
        appendActivationFunction(ActivationFunction.SIGMOID);
        return this;
    }

    public Layer withReLUActivation() {
        appendActivationFunction(ActivationFunction.RELU);
        return this;
    }

    public Layer withIdentityActivation() {
        appendActivationFunction(ActivationFunction.IDENTITY);
        return this;
    }

    private void appendActivationFunction(final ActivationFunction function) {
        Validate.notNull(function, "function must not be null");
        final DoubleUnaryOperator newForward = this.activationFunction.getForwardOperator()
                .andThen(function.getForwardOperator());
        final DoubleUnaryOperator newBackward = this.activationFunction.getBackwardOperator()
                .andThen(function.getBackwardOperator());

        this.activationFunction = new ActivationFunction(newForward, newBackward);
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

    public ActivationFunction getActivationFunction() {
        return this.activationFunction;
    }

    public static Layer conv(final Dimension dimension, int kernelCount) {
        return new Layer(LayerType.CONVOLUTION, kernelCount, dimension);
    }

    public static Layer fullyConn(int neuronCount) {
        return new Layer(LayerType.FULLY_CONNECTED, neuronCount);
    }

    public static Layer softMax() { return new Layer(LayerType.SOFTMAX, 0);}
}
