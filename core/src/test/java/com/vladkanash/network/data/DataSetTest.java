package com.vladkanash.network.data;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataSetTest {

    private DataSet dataSet;

    @Before
    public void setup() {
        dataSet = new DataSet(new double[] {2.0, 3.0, 5.0, 6.0, 7.0},
                new Dimension(1, 5, 1));
    }

    @Test
    public void updateTest() {
        dataSet.update(e -> e * 2);
        Assert.assertEquals(46, dataSet.getStreamData().sum(), 0.01);
    }

    @Test
    public void mergeTest() {
        dataSet.merge(new DataSet(new double[] {2.0, 9.0, 0.0, -1.0, 20.0}, dataSet.getDimension()), (a, b) -> a + b);
        Assert.assertArrayEquals(dataSet.getArrayData(), new double[] {4.0, 12.0, 5.0, 5.0, 27.0}, 0.0001);
    }

    @Test
    public void rotateTest() {
        DataSet dataSet = new DataSet(new double[][] {{1.0, 2.0, 15.0}, {3.0, 4.0, 0.04}, {12.76, 0.11, 5.0}}, new Dimension(3, 3));
        final DataSet result = dataSet.rotate();
        Assert.assertEquals(5.0, result.get(0), 0);
    }

}