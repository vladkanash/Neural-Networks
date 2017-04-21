package com.vladkanash.network;

import com.vladkanash.layer.Dimension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vladk on 13.04.2017.
 */
public class DataSet {

    private List<Double> data = new ArrayList<>();
    private Dimension dimension;

    public DataSet(int height, int width, int depth) {
        this.dimension = new Dimension(height, width, depth);
        this.data = new ArrayList<>(dimension.getSize());
    }

    public DataSet(int height, int width) {
        this(height, width, 1);
    }

    public DataSet(int height) {
        this(height, 1, 1);
    }

    public void addData(final Double[] data) {
        this.data.addAll(Arrays.asList(data));
    }

    public List<Double> getData() {
       return this.data;
    }

    public Double[] getArrayData() {
        return this.data.toArray(new Double[dimension.getSize()]);
    }

}
