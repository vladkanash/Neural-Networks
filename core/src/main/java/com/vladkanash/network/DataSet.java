package com.vladkanash.network;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vladk on 13.04.2017.
 */
public class DataSet {

    private List<Double> data = new ArrayList<>();
    private Dimension dimension;

    public DataSet(final Double[] data, final Dimension dimension) {
        this.data.addAll(Arrays.asList(data));
        this.dimension = dimension;
    }

    public DataSet(final double[] data, final Dimension dimension) {
        this.data.addAll(Arrays.stream(data).boxed().collect(Collectors.toList()));
        this.dimension = dimension;
    }

    public DataSet(final Collection<Double> data, final Dimension dimension) {
        this.data.addAll(data);
        this.dimension = dimension;
    }

    public List<Double> getData() {
       return this.data;
    }

    public int getSize() {
        return data.size();
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void update(final List<Double> data, final Dimension dimension) {
        Validate.notNull(dimension, "dimension must not be null");
        Validate.notNull(data, "data must not be null");
        Validate.isTrue(data.size() == dimension.getSize(), "data size must match dimension");
        this.data.clear();
        this.data.addAll(data);
        this.dimension = dimension;
    }

    public void update(final DataSet dataSet) {
        update(dataSet.getData(), dataSet.getDimension());
    }

    public double[][] get2DArrayData() {
        Validate.isTrue(dimension.getDepth() <= 1, "cannot get 2D data with 3 dimensions");
        Validate.isTrue(dimension.getSize() == data.size(), "data size must match dimension");

        final int width = dimension.getWidth();
        final int height = dimension.getHeight();
        double[][] result = new double[height][width];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[j][i] = data.get(j * width + i);
            }
        }
        return result;
    }

    public double[] getArrayData() {
        return data.stream().mapToDouble(Double::valueOf).toArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSet dataSet = (DataSet) o;

        if (data != null ? !data.equals(dataSet.data) : dataSet.data != null) return false;
        return dimension != null ? dimension.equals(dataSet.dimension) : dataSet.dimension == null;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        return result;
    }
}
