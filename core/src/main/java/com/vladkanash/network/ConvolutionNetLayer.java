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

    ConvolutionNetLayer(final Dimension inputDimension,
                        final Dimension weightsDimension,
                        final int kernelCount,
                        final ActivationFunction activationFunction) {
        super(inputDimension, getOutputDimension(inputDimension, weightsDimension, kernelCount),
                DataSetUtils.getRandomDataSetList(weightsDimension, kernelCount), activationFunction);
    }

    private static Dimension getOutputDimension(final Dimension inputDim, final Dimension filterDim, final int kernelCount) {
        Validate.isTrue(inputDim.getWidth() >= filterDim.getWidth());
        Validate.isTrue(inputDim.getHeight() >= filterDim.getHeight());
        Validate.isTrue(kernelCount >= 1,
                "Kernel count must be a positive value");

        return new Dimension(inputDim.getWidth() - filterDim.getWidth() + 1,
                inputDim.getHeight() - filterDim.getHeight() + 1, kernelCount);
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
    void backward(DataSet deltas, List<DataSet> childrenWeights) {
        final double learningRate = 0.5;
        final int padding  = this.getWeights().get(0).getDimension().getWidth() - 1;

        final DataSet result = new DataSet(selfOutputs.getDimension(), () -> 0);
        for (int i = 0; i < childrenWeights.size(); i++) {
            final DataSet partialResult = mathOperations.convolve(childrenWeights.get(i).rotate(), deltas.getChannel(i), padding);
            result.merge(partialResult, (a, b) -> a + b);
        }

        final DataSet activationGrad = new DataSet(selfOutputs)
                .update(this.activationFunction.getBackwardOperator());

        deltas.update(result.merge(activationGrad, (a, b) -> a * b));

        this.deltas.update(deltas);
        for (int i = 0; i < deltas.getDimension().getDepth(); i++) {
            final DataSet weightsDelta = mathOperations.convolve(this.deltas.getChannel(i),
                    this.prevOutputs.rotate().update(activationFunction.getForwardOperator()), 0);
            getWeights().get(i).merge(weightsDelta.update(e -> e * learningRate), (a, b) -> a - b);
        }
//        this.deltas.getData().forEach(System.out::println);
//        System.out.println('\n');
    }

    @Override
    void lastLayerBackward(DataSet deltas, DataSet y, DataSet outputs) {
        final double learningRate = 2;

        final double[] layerDeltas = new double[deltas.getSize()];
        Arrays.setAll(layerDeltas, i ->
                this.activationFunction.getBackwardOperator().applyAsDouble(outputs.get(i)) * (outputs.get(i) - y.get(i)));
        deltas.update(new DataSet(layerDeltas, deltas.getDimension()));

        this.deltas.update(deltas);
        for (int i = 0; i < deltas.getDimension().getDepth(); i++) {
            final DataSet weightsDelta = mathOperations.convolve(this.deltas.getChannel(i),
                    this.prevOutputs.rotate().update(activationFunction.getForwardOperator()), 0);
            getWeights().get(i).merge(weightsDelta.update(e -> e * learningRate), (a, b) -> a - b);
        }

//        this.deltas.getData().forEach(System.out::println);
//        System.out.println('\n');
    }
}
