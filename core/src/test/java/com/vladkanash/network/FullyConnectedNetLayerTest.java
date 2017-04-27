package com.vladkanash.network;

import static org.junit.Assert.*;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FullyConnectedNetLayerTest {

    @Spy
    private FullyConnectedNetLayer layer = new FullyConnectedNetLayer(4, new Dimension(2, 2, 2));

    private final static double[] mockWeights = new double[] {2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0,
                                                              2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0,
                                                              2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0,
                                                              2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0};

    public void setup() {
        this.layer = new FullyConnectedNetLayer(4, new Dimension(2, 2, 2));


        Mockito.when(layer.getWeights()).thenReturn(new DataSet(mockWeights, new Dimension(8, 4)));
        Mockito.doNothing().when(layer).backward(Mockito.any(), Mockito.any());
    }

    @Test
    public void notLastLayerBackwardTest() throws Exception {
        final DataSet deltas = new DataSet(new double[] {1.0, 2.0, 3.0, 4.0}, new Dimension(1, 1,4));
        final DataSet y = new DataSet(new double[] {1.0, 1.0, 0.0, 0.0}, new Dimension(1, 1, 4));
        layer.backward(deltas, y);

        Assert.assertEquals(8, deltas.getSize());
    }

}