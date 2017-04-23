package com.vladkanash.network.netlayer;

import com.vladkanash.network.DataSet;
import com.vladkanash.network.Dimension;
import com.vladkanash.network.LayerDimensions;
import com.vladkanash.network.computation.MathOperations;
import com.vladkanash.network.computation.SingleThreadMathOperations;
import com.vladkanash.network.util.DataSetUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * Created by vladk on 22.04.2017.
 */
public class FullyConnectedNetLayer extends NetLayer {

    private final int neuronCount;
    private final DataSet weigths;
    private final MathOperations mathOperations = SingleThreadMathOperations.getInstance();

    public FullyConnectedNetLayer(int neuronCount,
                                  final LayerDimensions dimensions) {
        super(dimensions);
        Validate.isTrue(neuronCount >= 0, "neuron count cannot be negative");
        this.neuronCount = neuronCount;
        this.weigths = DataSetUtils.getRandomDataSet(new Dimension(getInputSize(), getInputSize()));
    }

    @Override
    public void forward(DataSet dataSet) {
        Validate.isTrue(dataSet.getSize() == getInputSize(),
                "size of dataset must be valid");

        final DataSet result = mathOperations.getFullyConnOutput(weigths, dataSet);
        dataSet.update(result);
    }

    private int getInputSize() {
        return this.getLayerDimensions().getInputDimension().getSize();
    }

    private int getOutputSize() {
        return this.getLayerDimensions().getOutputDimension().getSize();
    }

    public int getNeuronCount() {
        return neuronCount;
    }

}
