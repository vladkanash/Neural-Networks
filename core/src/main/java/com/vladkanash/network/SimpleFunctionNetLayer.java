package com.vladkanash.network;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.LayerDimensions;
import org.apache.commons.lang3.Validate;

import java.util.function.UnaryOperator;

/**
 * Created by vladk on 22.04.2017.
 */
abstract class SimpleFunctionNetLayer extends NetLayer {

    private final UnaryOperator<Double> forwardFunction;

    SimpleFunctionNetLayer(final UnaryOperator<Double> forwardFunction,
            final LayerDimensions dimensions) {
        super(dimensions);
        Validate.notNull(forwardFunction, "operator must not be null");
        this.forwardFunction = forwardFunction;
    }

    @Override
    void forward(DataSet dataSet) {
        dataSet.update(forwardFunction::apply);
        this.outputs.update(dataSet);
    }

    //TODO backwards operator
}
