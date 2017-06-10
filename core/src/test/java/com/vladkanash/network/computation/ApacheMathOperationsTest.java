package com.vladkanash.network.computation;

import java.util.ArrayList;
import java.util.List;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vladk on 23.04.2017.
 */
public class ApacheMathOperationsTest {

    private final MathOperations operations = ApacheMathOperations.getInstance();

    @Test(timeout = 400L)
    public void fullyConnOutputTest() {
        final DataSet input = new DataSet(new Double[]{3.1, 2.0, 8.0, 17.0},
                new Dimension(4));
        final DataSet weights = new DataSet(new Double[]{0.4, 5.0, 7.2, 3.0, 0.1, 4.0, 4.0, 2.0, 1.0, 0.9, 5.0, 2.0},
                new Dimension(4, 3));

        final DataSet result = operations.forwardLayer(weights, input);
        Assert.assertEquals(119.84, result.getData().get(0), 0);
        Assert.assertEquals(74.31, result.getData().get(1), 0);
        Assert.assertEquals(78.9, result.getData().get(2), 0);
    }

    @Test
    public void convolutionTest() {
        final DataSet input = new DataSet(new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0},
                new Dimension(2, 2, 3));
        final DataSet kernel = new DataSet(new Dimension(2, 2, 3), () -> 1.0);
        final List<DataSet> kernels = new ArrayList<>();
        for (int k = 0; k < 5; k++) {
            kernels.add(kernel);
        }

        final DataSet result = operations.convolve(kernels, input, 0);
        Assert.assertEquals(kernels.size(), result.getDimension().getDepth());
        Assert.assertEquals(24.0, result.getData().get(0), 0.0);
    }

    @Test
    public void paddingConvolutionTest() {
        final DataSet input = new DataSet(new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0},
                new Dimension(2, 2, 3));
        final DataSet kernel = new DataSet(new Dimension(2, 2, 3), () -> 1.0);

        final DataSet result = operations.convolve(kernel, input, 1);
        System.out.println(result);
    }

    @Test
    public void convolveGradientTest() {
        final DataSet input = new DataSet(new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0},
                new Dimension(2, 2, 3));
        final DataSet kernel = new DataSet(new Dimension(2, 2, 1), () -> 1.0);
        final DataSet result = operations.convolveGradient(kernel, input, 0);
        Assert.assertEquals(input.getDimension().getDepth(), result.getDimension().getDepth());
    }

}