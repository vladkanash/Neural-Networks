package com.vladkanash.layer;

/**
 * Created by vladk on 12.04.2017.
 */
public abstract class GenericLayer {

    private final int inputWidth;
    private final int outputWidth;

    public GenericLayer(final int inputWidth, final int outputWidth) {
        this.inputWidth = inputWidth;
        this.outputWidth = outputWidth;
    }
}
