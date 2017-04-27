package com.vladkanash.network.data;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Created by vladk on 13.04.2017.
 */
public class DataSet {

    private final List<Double> data = new ArrayList<>();
    private Dimension dimension;

    public final static DataSet EMPTY = new DataSet(Collections.singletonList(0.0), Dimension.EMPTY);

    public DataSet(final Double[] data, final Dimension dimension) {
        Validate.isTrue(data.length == dimension.getSize(),
                "data size must match dimension");
        this.data.addAll(Arrays.asList(data));
        this.dimension = dimension;
    }

    public DataSet(final double[] data, final Dimension dimension) {
        Validate.isTrue(data.length == dimension.getSize(),
                "data size must match dimension");
        this.data.addAll(Arrays.stream(data).boxed().collect(Collectors.toList()));
        this.dimension = dimension;
    }

    public DataSet(final double[][] data, final Dimension dimension) {
        this(Arrays.stream(data).flatMapToDouble(Arrays::stream)
                .boxed().collect(Collectors.toList()), dimension);
    }

    public DataSet(final Collection<Double> data, final Dimension dimension) {
        Validate.isTrue(data.size() == dimension.getSize(),
                "data size must match dimension");
        this.data.addAll(data);
        this.dimension = dimension;
    }

    /**
     * Creates a new DataSet from the existing one (copy constructor)
     * @param dataSet dataSet
     */
    public DataSet(final DataSet dataSet) {
        this(dataSet.getData(), dataSet.getDimension());
    }

    /**
     * Creates a DataSet filled with 0's for a given dimension.
     * @param dimension dimension
     */
    public DataSet(final Dimension dimension) {
        this(DoubleStream.generate(() -> 0)
                .limit(dimension.getSize()).toArray(), dimension);
    }

    public void update(final DoubleUnaryOperator operator) {
        this.data.replaceAll(operator::applyAsDouble);
    }

    public void merge(final DataSet other) {
        Validate.isTrue(this.getDimension().equals(other.getDimension()),
                "Dimensions must match");

        final double[] dataArray = getArrayData();
        final double[] otherArray = other.getArrayData();
        Arrays.setAll(dataArray, i -> dataArray[i] + otherArray[i]);
        this.data.clear();
        this.data.addAll(Arrays.stream(dataArray).boxed().collect(Collectors.toList()));
    }

    public List<Double> getData() {
       return this.data;
    }

    public DoubleStream getStreamData() {
        return this.data.stream().mapToDouble(Double::valueOf);
    }

    public Double[] getWrapperArrayData() {
        return this.data.toArray(new Double[this.data.size()]);
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

    public double get(int idx) {
        return data.get(idx);
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
