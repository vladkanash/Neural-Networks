package com.vladkanash.layer;

/**
 * Created by vladk on 22.04.2017.
 */
public class Dimension {

    private final int height;
    private final int width;
    private final int depth;

    public Dimension(int height, int width, int depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    public Dimension(int height, int width) {
        this(height, width, 1);
    }

    public Dimension(int height) {
        this(height, 1, 1);
    }

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
}
