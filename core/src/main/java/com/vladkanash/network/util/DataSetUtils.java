package com.vladkanash.network.util;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.apache.commons.lang3.Validate;

import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by vladk on 23.04.2017.
 */
public class DataSetUtils {

    private static final Random random = new Random();

    private DataSetUtils() {
    }

    public static DataSet getRandomDataSet(final Dimension dimension) {
        Validate.notNull(dimension, "dimension must not be null");
        return new DataSet(random.doubles(dimension.getSize()).boxed().collect(Collectors.toList()),
                dimension);
    }
}
