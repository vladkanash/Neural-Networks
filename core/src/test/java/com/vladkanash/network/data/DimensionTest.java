package com.vladkanash.network.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DimensionTest {

    private Dimension dimension;

    @Before
    public void setUp() throws Exception {
        dimension = new Dimension(2, 3, 4);
    }

    @Test
    public void singleDimensionalNegativeTest() {
        assertFalse(dimension.isSingleDimensional());
    }

    @Test
    public void singleDimensionalPositiveTest() {
        final Dimension dimension = new Dimension(5, 1, 1);
        assertTrue(dimension.isSingleDimensional());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDimensionErrorTest1() {
        final Dimension dimension = new Dimension(4, -17, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDimensionErrorTest2() {
        final Dimension dimension = new Dimension(0, 6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDimensionErrorTest3() {
        final Dimension dimension = new Dimension(-5);
    }

    @Test
    public void getWidthTest() {
        assertEquals(2, dimension.getWidth());
    }

    @Test
    public void getHeightTest() {
        assertEquals(3, dimension.getHeight());
    }

    @Test
    public void getDepthTest() {
        assertEquals(4, dimension.getDepth());
    }

    @Test
    public void getSizeTest() {
        assertEquals(24, dimension.getSize());
    }
}