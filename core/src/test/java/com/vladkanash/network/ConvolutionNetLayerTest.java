package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ConvolutionNetLayerTest {

    @Spy
    private ConvolutionNetLayer layer = new ConvolutionNetLayer(
            new Dimension(10, 10, 3),
            new Dimension(3, 3, 3),
            1,
            ActivationFunction.SIGMOID);

    @Test
    public void forward() throws Exception {
        final DataSet dataSet = new DataSet(new Dimension(10, 10, 3), () -> 1);
        layer.forward(dataSet);

        final Dimension expected = new Dimension(8, 8, 1);
        Assert.assertEquals(expected, dataSet.getDimension());
    }

    @Test
    public void backward() throws Exception {

    }
}