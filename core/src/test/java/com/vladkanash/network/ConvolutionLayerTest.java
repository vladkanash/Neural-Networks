package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.util.DataSetUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Spy;

import static org.junit.Assert.*;

/**
 * Created by vladk on 04.05.2017.
 */
public class ConvolutionLayerTest {

    @Spy
    private ConvolutionLayer layer = new ConvolutionLayer(
            new Dimension(10, 10, 3),
            new Dimension(3, 3, 3),
            ActivationFunction.SIGMOID);

    @Test
    public void forward() throws Exception {
        final DataSet dataSet = new DataSet(new Dimension(10, 10, 3), () -> 1);
        layer.forward(dataSet);

        final Dimension expected = new Dimension(8, 8, 1);
        Assert.assertEquals(expected, dataSet.getDimension());
    }

}