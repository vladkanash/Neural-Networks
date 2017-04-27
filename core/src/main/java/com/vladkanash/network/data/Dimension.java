package com.vladkanash.network.data;

import org.apache.commons.lang3.Validate;

/**
 * This class contains a dimension values for a 3-dimensional DataSet (width, height, depth)
 * Created by vladk on 22.04.2017.
 */
public class Dimension {

    private final int height;
    private final int width;
    private final int depth;

    /**
     * Standard constructor
     * @param width dimension width
     * @param height dimension height
     * @param depth dimension depth
     */
    public Dimension(int width, int height, int depth) {
        Validate.isTrue(height > 0, "height must be positive");
        Validate.isTrue(width > 0, "width must be positive");
        Validate.isTrue(depth > 0, "depth must be positive");
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    /**
     * Creates a dimension with depth equals to 1
     * @param width dimension width
     * @param height dimension height
     */
    public Dimension(int width, int height) {
        this(width, height, 1);
    }

    /**
     * Creates a dimension with height and depth equals to 1
     * @param width dimension width
     */
    public Dimension(int width) {
        this(width, 1, 1);
    }

    /**
     * Constant for a dimension with all values equal to 1
     */
    public final static Dimension EMPTY = new Dimension(1);

    /**
     * Returns height of the dimension
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns width of the dimension
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns depth of the dimension
     * @return depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Returns total size of the dimension
     * @return width * height * depth
     */
    public int getSize() {
        return width * height * depth;
    }

    /**
     * Shows if the dimension have > 1 value only for single measuring
     * Ex: width = 1, depth = 5, height = 9 => false
     *     width = 1, depth = 5, height = 1 => true
     *     width = 8, depth = 1, height = 1 => true
     *     width = 1, depth = 1, height = 1 => true
     *
     * @return is the current dimension single dimensional
     */
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
