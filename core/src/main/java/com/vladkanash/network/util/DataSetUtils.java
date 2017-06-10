package com.vladkanash.network.util;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;

/**
 * Created by vladk on 23.04.2017.
 */
public class DataSetUtils {

    private static final Random random = new Random();

    private DataSetUtils() {
    }

    public static List<DataSet> getRandomDataSetList(final Dimension dimension, final int size) {
        return generateDataSet(dimension, size, () -> random.nextDouble() / 4);
    }

    public static List<DataSet> generateDataSet(final Dimension dimension,
                                                final int size,
                                                final DoubleSupplier supplier) {
        Validate.notNull(dimension, "dimension must not be null");
        Validate.isTrue(size >= 1);

        final List<DataSet> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(new DataSet(dimension, supplier));
        }
        return result;
    }
}
