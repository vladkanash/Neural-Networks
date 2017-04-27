package com.vladkanash.network;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vladk on 21.04.2017.
 */
public class ReLULayerTest {

    private final NetLayer layer = NetLayerFactory.ReLU(Dimension.EMPTY);
    private DataSet dataSet = new DataSet(new Double[] {1.8, -3.0, -45.0, 7.0, 45.7, -74.5, 745.4,
            457.11, 45.64, -3.005, -7.12, 9708.54, 5634.76, 1.9, -0.1}, new Dimension(5, 3));

    @Test
    public void forward() throws Exception {
        layer.forward(dataSet);
        Assert.assertTrue(dataSet.getStreamData().noneMatch(e -> e < 0));
    }

}