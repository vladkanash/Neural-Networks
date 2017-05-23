package com.vladkanash.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class ConvolutionNetLayer extends NetLayer {

    private final MathOperations mathOperations = ApacheMathOperations.getInstance();

    ConvolutionNetLayer(Dimension inputDimension,
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
        this.prevOutputs.update(dataSet);
        Validate.isTrue(dataSet.getDimension().equals(getLayerDimensions().getInputDimension()),
                "DataSet must match input dimension");
        dataSet.update(mathOperations.convolve(weights, dataSet, 0));
        this.selfOutputs.update(dataSet);
        dataSet.update(this.activationFunction.getForwardOperator());
    }

    @Override
    void backward(DataSet deltas, DataSet childrenWeights) {
        final DataSet result = mathOperations.convolve(childrenWeights.rotate(), deltas, 0);
        final DataSet activationGrad = new DataSet(selfOutputs)
                .update(this.activationFunction.getBackwardOperator());

        deltas.update(result.merge(activationGrad, (a, b) -> a * b));
        this.deltas.update(deltas);

        getWeights().merge(mathOperations.convolve(this.deltas, this.prevOutputs.rotate(), 0), (a, b) -> a + b);
    }

    @Override
    void lastLayerBackward(DataSet deltas, DataSet y, DataSet outputs) {
        final double[] layerDeltas = new double[deltas.getSize()];
        Arrays.setAll(layerDeltas, i ->
                this.activationFunction.getBackwardOperator().applyAsDouble(outputs.get(i)) * (y.get(i) - outputs.get(i)));
        deltas.update(new DataSet(layerDeltas, deltas.getDimension()));

        this.deltas.update(deltas);
        //update weights here
        getWeights().merge(mathOperations.outerProduct(this.deltas, this.prevOutputs), (a, b) -> a + b);

        //TODO apply nu parameter here
    }
}
