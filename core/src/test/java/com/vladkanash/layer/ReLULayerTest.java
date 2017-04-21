package com.vladkanash.layer;

import com.vladkanash.network.DataSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
/**
 * Created by vladk on 21.04.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReLULayerTest {

    private static final int TEST_INPUT = 3;
    private static final int TEST_OUTPUT = 4;

    private final ReLULayer layer = new ReLULayer(TEST_INPUT, TEST_OUTPUT);

    @Mock
    private DataSet dataSet;

    private Double[] data;

    @Before
    public void setup() {
        data = new Double[] {1.8, -3.0, -45.0, 7.0, 45.7, -74.5, 745.4,
                457.11, 45.64, -3.005, -7.12, 9708.54, 5634.76, 1.9, -0.1};
        Mockito.when(dataSet.getArrayData()).thenReturn(data);
    }

    @Test
    public void forward() throws Exception {
        layer.forward(dataSet);
        Assert.assertTrue(Arrays.stream(data)
                .mapToDouble(Double::valueOf).noneMatch(e -> e < 0));
    }

}