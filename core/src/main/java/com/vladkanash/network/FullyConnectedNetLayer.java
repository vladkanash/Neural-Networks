package com.vladkanash.network;

import java.util.Arrays;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.computation.MathOperations;
import com.vladkanash.network.computation.ApacheMathOperations;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.util.DataSetUtils;
import org.apache.commons.lang3.Validate;

/**
 * Created by vladk on 22.04.2017.
 */
class FullyConnectedNetLayer extends NetLayer {

    private final MathOperations mathOperations = ApacheMathOperations.getInstance();

    FullyConnectedNetLayer(int neuronCount,
                           final Dimension inputDimension,
                           final ActivationFunction activationFunction) {
        super(inputDimension,
                new Dimension(1, 1, neuronCount),
                DataSetUtils.getRandomDataSet(new Dimension(inputDimension.getSize(), neuronCount)),
                activationFunction);
    }

    @Override
    void forward(DataSet dataSet) {
        this.prevOutputs.update(dataSet);
        Validate.isTrue(dataSet.getSize() == getInputSize(),
                "size of dataset must be valid");
        Validate.isTrue(dataSet.getSize() == getWeights().getDimension().getWidth(),
                "Dimensions must match");

        final DataSet result = mathOperations.forwardLayer(getWeights(), dataSet);
        dataSet.update(result);
        dataSet.update(this.activationFunction.getForwardOperator());
        this.selfOutputs.update(dataSet);
    }

    @Override
    void backward(final DataSet deltas, final DataSet childrenWeights) {
        final DataSet result = mathOperations.backwardLayer(childrenWeights, deltas);
        final DataSet activationGrad = new DataSet(selfOutputs)
                .update(this.activationFunction.getBackwardOperator());

        deltas.update(result.merge(activationGrad, (a, b) -> a * b));
        this.deltas.update(deltas);

        //update weights here
        getWeights().merge(mathOperations.outerProduct(this.deltas, this.prevOutputs), (a, b) -> a + b);

        //TODO apply nu parameter here
    }

    @Override
    void lastLayerBackward(final DataSet deltas, final DataSet y, final DataSet outputs) {
        final double[] layerDeltas = new double[deltas.getSize()];
        Arrays.setAll(layerDeltas, i ->
                this.activationFunction.getBackwardOperator().applyAsDouble(outputs.get(i)) * (y.get(i) - outputs.get(i)));
        deltas.update(new DataSet(layerDeltas, deltas.getDimension()));

        this.deltas.update(deltas);
        //update weights here
        getWeights().merge(mathOperations.outerProduct(this.deltas, this.prevOutputs), (a, b) -> a + b);

        //TODO apply nu parameter here
    }

    private int getInputSize() {
        return this.getLayerDimensions().getInputDimension().getSize();
    }

    private int getOutputSize() {
        return this.getLayerDimensions().getOutputDimension().getSize();
    }
}
