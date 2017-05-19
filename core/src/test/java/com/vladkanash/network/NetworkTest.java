package com.vladkanash.network;

import com.vladkanash.api.layers.Layer;
import com.vladkanash.network.data.Dimension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by vladk on 21.04.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class NetworkTest {

    private Network network;

    @Before
    public void setUp() {
        network = new Network(new Dimension(2,2,2));
    }

    @Test
    public void networkTest() {
        network.addLayer(Layer.fullyConn(5).withReLUActivation());
        final Double[] result = network.forward(new Double[] {1.9, -2.8, 3.7, -4.6, 5.5, -6.4, 7.3, 1.4});
        Assert.assertTrue(Arrays.stream(result).noneMatch(e -> e < 0));
    }

    @Test
    public void outputDimensionTest() {
        network.addLayer(Layer.conv(new Dimension(1,2, 2)).withSigmoidActivation());
        network.addLayer(Layer.fullyConn(10).withSigmoidActivation());
        final Double[] result = network.forward(new Double[] {1.9, -2.8, 3.7, -4.6, 5.5, -6.4, 7.3, 9.9});
        Assert.assertEquals(10, result.length);
    }

}