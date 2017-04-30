package com.vladkanash.api.layers;

import java.util.function.DoubleUnaryOperator;

public class ActivationFunction {

    private final DoubleUnaryOperator forwardOperator;
    private final DoubleUnaryOperator backwardOperator;

    public static final ActivationFunction SIGMOID = new ActivationFunction(
                    e -> 1/( 1 + Math.pow(Math.E,(-1*e))),
                    e -> e * (1 - e));

    public static final ActivationFunction RELU = new ActivationFunction(
                    e -> e > 0 ? e : 0,
                    e -> e > 0 ? 1 : 0);

    public static final ActivationFunction IDENTITY = new ActivationFunction(
                    DoubleUnaryOperator.identity(), DoubleUnaryOperator.identity());

    public ActivationFunction(
            final DoubleUnaryOperator forwardOperator,
            final DoubleUnaryOperator backwardOperator) {
        this.forwardOperator = forwardOperator;
        this.backwardOperator = backwardOperator;
    }

    public DoubleUnaryOperator getForwardOperator() {
        return forwardOperator;
    }

    public DoubleUnaryOperator getBackwardOperator() {
        return backwardOperator;
    }
}
