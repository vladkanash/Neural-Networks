package com.vladkanash.network;

import com.vladkanash.api.layers.Layer;
import com.vladkanash.network.netlayer.NetLayerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

/**
 * Created by vladk on 21.04.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class NetworkTest {

    private Network network = new Network(Dimension.EMPTY);

    @Before
    public void setUp() {

    }

    @Test
    public void NetworkTest() {
        network.addLayer(Layer.ReLU());
        final Collection<Double> result = network.forward(new Double[] {1.9, -2.8, 3.7, -4.6, 5.5, -6.4, 7.3});
        Assert.assertTrue(result.stream().noneMatch(e -> e < 0));
    }

}