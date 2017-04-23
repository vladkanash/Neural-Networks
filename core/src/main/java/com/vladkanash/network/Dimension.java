package com.vladkanash.network;

import org.apache.commons.lang3.Validate;

/**
 * Created by vladk on 22.04.2017.
 */
public class Dimension {

    private final int height;
    private final int width;
    private final int depth;

    public Dimension(int width, int height, int depth) {
        Validate.isTrue(height > 0, "height must be positive");
        Validate.isTrue(width > 0, "width must be positive");
        Validate.isTrue(depth > 0, "depth must be positive");
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    public Dimension(int width, int height) {
        this(width, height, 1);
    }

    public Dimension(int width) {
        this(width, 1, 1);
    }

    public final static Dimension EMPTY = new Dimension(1);

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public int getSize() {
        return height * width * depth;
    }

    public boolean isSingleDimensional() {
        return (width <= 1 && height <= 1 && depth > 1)
            || (width <= 1 && height > 1  && depth <= 1)
            || (width > 1  && height <= 1 && depth <= 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dimension dimension = (Dimension) o;

        if (height != dimension.height) return false;
        if (width != dimension.width) return false;
        return depth == dimension.depth;
    }

    @Override
    public int hashCode() {
        int result = height;
        result = 31 * result + width;
        result = 31 * result + depth;
        return result;
    }

    @Override
    public String toString() {
        return "H=" + height +
                ", W=" + width +
                ", D=" + depth;
    }
}
