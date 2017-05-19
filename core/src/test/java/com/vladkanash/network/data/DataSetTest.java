package com.vladkanash.network.data;

import static org.junit.Assert.*;

import java.util.Arrays;

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
        assertEquals(46, dataSet.getStreamData().sum(), 0.01);
    }

    @Test
    public void mergeTest() {
        dataSet.merge(new DataSet(new double[] {2.0, 9.0, 0.0, -1.0, 20.0}, dataSet.getDimension()), (a, b) -> a + b);
        assertArrayEquals(dataSet.getArrayData(), new double[] {4.0, 12.0, 5.0, 5.0, 27.0}, 0.0001);
    }

    @Test
    public void rotateTest() {
        DataSet dataSet = new DataSet(new double[][] {{1.0, 2.0, 15.0}, {3.0, 4.0, 0.04}, {12.76, 0.11, 5.0}}, new Dimension(3, 3));
        final DataSet result = dataSet.rotate();
        assertEquals(5.0, result.get(0), 0);
    }

    @Test
    public void updateListTest() {
        dataSet.update(Arrays.asList(7.6, 8.1, 12.5, 16.8), new Dimension(2,2));
        assertEquals(16.8, dataSet.get(1,1,0), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateErrorTest() {
        dataSet.update(Arrays.asList(8.0, 11.1, 45.6), new Dimension(5, 5, 3));
    }

    @Test
    public void getTest() {
        assertEquals(5.0, dataSet.get(2), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorTest1() {
        dataSet.get(-14);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorTest2() {
        dataSet.get(17);
    }

    @Test
    public void setTest() {
        dataSet.set(0, 0, 0, -13.0);
        assertEquals(-13.0, dataSet.get(0), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setErrorTest1() {
        dataSet.set(0, -2, 0, 4.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setErrorTest2() {
        dataSet.set(13, 2, 0, 4.5);
    }
}