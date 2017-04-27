package com.vladkanash.network.data;

import org.apache.commons.lang3.Validate;

/**
 * Created by vladk on 23.04.2017.
 */
public class LayerDimensions {

    private final Dimension inputDimension;
    private final Dimension outputDimension;

    public LayerDimensions(final Dimension inputDimension, final Dimension outputDimension) {
        Validate.notNull(inputDimension, "input dimension must not be null");
        Validate.notNull(outputDimension, "output dimension must not be null");
        this.inputDimension = inputDimension;
        this.outputDimension = outputDimension;
    }

    public LayerDimensions(final Dimension inputDimension) {
        this(inputDimension, inputDimension);
    }

    public final static LayerDimensions EMPTY = new LayerDimensions(Dimension.EMPTY);

    public Dimension getInputDimension() {
        return inputDimension;
    }

    public Dimension getOutputDimension() {
        return outputDimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LayerDimensions that = (LayerDimensions) o;

        if (!inputDimension.equals(that.inputDimension)) return false;
        return outputDimension.equals(that.outputDimension);
    }

    @Override
    public int hashCode() {
        int result = inputDimension.hashCode();
        result = 31 * result + outputDimension.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LayerDimensions{" +
                "inputDimension=" + inputDimension +
                ", outputDimension=" + outputDimension +
                '}';
    }
}
