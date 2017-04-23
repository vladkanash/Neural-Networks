package com.vladkanash.network.netlayer;

import com.vladkanash.network.DataSet;
import com.vladkanash.network.LayerDimensions;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Created by vladk on 22.04.2017.
 */
public class SimpleFunctionNetLayer extends NetLayer {

    private final UnaryOperator<Double> function;

    public SimpleFunctionNetLayer(final UnaryOperator<Double> function,
                                  final LayerDimensions dimensions) {
        super(dimensions);
        Validate.notNull(function, "operator must not be null");
        this.function = function;
    }

    @Override
    public void forward(DataSet dataSet) {
        final List<Double> data = dataSet.getData();
        data.replaceAll(function);
    }
}
