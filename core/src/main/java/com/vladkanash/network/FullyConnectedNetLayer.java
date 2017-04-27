package com.vladkanash.network;

import java.util.Arrays;

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

    private final int neuronCount;
    private final DataSet weights;
    private final MathOperations mathOperations = ApacheMathOperations.getInstance();

    FullyConnectedNetLayer(int neuronCount,
            final Dimension inputDimension) {
        super(inputDimension, new Dimension(1, 1, neuronCount));
        this.neuronCount = neuronCount;
        this.weights = DataSetUtils.getRandomDataSet(new Dimension(getInputSize(), neuronCount));
    }

    @Override
    void forward(DataSet dataSet) {
        Validate.isTrue(dataSet.getSize() == getInputSize(),
                "size of dataset must be valid");
        Validate.isTrue(dataSet.getSize() == getWeights().getDimension().getWidth(),
                "Dimensions must match");

        final DataSet result = mathOperations.forwardLayer(getWeights(), dataSet);
        dataSet.update(result);
        this.outputs.update(result);
    }

    @Override
    void backward(final DataSet deltas, final DataSet y) {
        if (lastLayer) {
            final double[] layerDeltas = new double[deltas.getSize()];
            Arrays.setAll(layerDeltas, i ->
                    (deltas.get(i) == 0 ? 1 : deltas.get(i)) * (y.get(i) - outputs.get(i)));
            deltas.update(new DataSet(layerDeltas, deltas.getDimension()));
        } else {
            Validate.isTrue(deltas.getSize() == getWeights().getDimension().getHeight(),
                    "Dimensions must match");
            final DataSet result = mathOperations.backwardLayer(this.getWeights(), deltas);
            deltas.update(result);
        }
        this.deltas.update(deltas);

        //update weights here
        getWeights().merge(mathOperations.outerProduct(this.deltas, this.outputs));

        //TODO apply nu parameter here
    }

    private int getInputSize() {
        return this.getLayerDimensions().getInputDimension().getSize();
    }

    private int getOutputSize() {
        return this.getLayerDimensions().getOutputDimension().getSize();
    }

    int getNeuronCount() {
        return neuronCount;
    }

    DataSet getWeights() {
        return weights;
    }
}
