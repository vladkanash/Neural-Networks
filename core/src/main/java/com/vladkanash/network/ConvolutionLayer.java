package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.computation.ApacheMathOperations;
import com.vladkanash.network.computation.MathOperations;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.util.DataSetUtils;
import org.apache.commons.lang3.Validate;

/**
 * Created by vladk on 30.04.2017.
 */
public class ConvolutionLayer extends NetLayer {

    private final MathOperations mathOperations = ApacheMathOperations.getInstance();

    ConvolutionLayer(Dimension inputDimension,
                     Dimension weightsDimension,
                     ActivationFunction activationFunction) {
        super(inputDimension, getOutputDimension(inputDimension, weightsDimension),
                DataSetUtils.getRandomDataSet(weightsDimension), activationFunction);
    }

    private static Dimension getOutputDimension(final Dimension inputDim, final Dimension filterDim) {
        Validate.isTrue(inputDim.getWidth() >= filterDim.getWidth());
        Validate.isTrue(inputDim.getHeight() >= filterDim.getHeight());
        Validate.isTrue(inputDim.getDepth() == filterDim.getDepth(),
                "Filter depth must match input depth");

        return new Dimension(inputDim.getWidth() - filterDim.getWidth() + 1,
                inputDim.getHeight() - filterDim.getHeight() + 1,
                inputDim.getDepth() - filterDim.getDepth() + 1);
    }

    @Override
    void forward(DataSet dataSet) {
        Validate.isTrue(dataSet.getDimension().equals(getLayerDimensions().getInputDimension()),
                "DataSet must match input dimension");
        dataSet.update(mathOperations.convolve(weights, dataSet));
    }

    @Override
    void backward(DataSet deltas, DataSet childrenWeights) {

    }

    @Override
    void lastLayerBackward(DataSet deltas, DataSet y, DataSet outputs) {

    }
}
