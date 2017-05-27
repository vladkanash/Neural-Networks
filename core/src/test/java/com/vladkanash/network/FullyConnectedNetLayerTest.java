package com.vladkanash.network;

import static org.junit.Assert.*;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class FullyConnectedNetLayerTest {

    @Spy
    private FullyConnectedNetLayer layer = new FullyConnectedNetLayer(4,
            new Dimension(2, 2, 2), ActivationFunction.IDENTITY);

    private final static double[] mockWeights = new double[] {2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0,
                                                              2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0,
                                                              2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0,
                                                              2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0};

    @Before
    public void setup() {
        this.layer = new FullyConnectedNetLayer(4,
                new Dimension(2, 2, 2), ActivationFunction.IDENTITY);

        //Mockito.when(layer.getWeights()).thenReturn(new DataSet(mockWeights, new Dimension(8, 4)));
        //Mockito.doNothing().when(layer).backward(Mockito.any(), Mockito.any());
    }

    @Test
    public void notLastLayerBackwardTest() throws Exception {
        final DataSet deltas = new DataSet(
                new double[] {3.0, 4.0}, new Dimension(1, 2,1));
        final DataSet childrenWeights = new DataSet(
                new double[] {1.0, 1.0, 0.0, 0.0, 1.5, 0.4, 9.8, 1.1}, new Dimension(4, 2, 1));

        layer.backward(deltas, Collections.singletonList(childrenWeights));

        Assert.assertEquals(4, deltas.getSize());
    }

}