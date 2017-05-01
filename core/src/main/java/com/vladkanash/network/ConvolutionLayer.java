package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.computation.ApacheMathOperations;
import com.vladkanash.network.computation.MathOperations;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.apache.commons.lang3.Validate;

/**
 * Created by vladk on 30.04.2017.
 */
public class ConvolutionLayer extends NetLayer {

    private final MathOperations mathOperations = ApacheMathOperations.getInstance();

    ConvolutionLayer(Dimension inputDimension,
                     Dimension outputDimension,
                     DataSet weights,
                     ActivationFunction activationFunction) {
        super(inputDimension, outputDimension, weights, activationFunction);
    }

    @Override
    void forward(DataSet dataSet) {
        Validate.isTrue(dataSet.getDimension().equals(getLayerDimensions().getInputDimension()),
                "DataSet must match input dimension");

        mathOperations.
    }

    @Override
    void backward(DataSet deltas, DataSet childrenWeights) {

    }

    @Override
    void lastLayerBackward(DataSet deltas, DataSet y, DataSet outputs) {

    }
}
