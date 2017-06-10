package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.util.DataSetUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vladk on 07.05.2017.
 */
public class SoftmaxNetLayer extends NetLayer {

    private Double sum;

    SoftmaxNetLayer(
            final Dimension inputDimension,
            final Dimension weightsDimension,
            final int weightsCount,
            final ActivationFunction activationFunction) {
        super(inputDimension, inputDimension,
                DataSetUtils.generateDataSet(weightsDimension, weightsCount, () -> 1), activationFunction);
    }

    @Override
    void forward(final DataSet dataSet) {
        this.prevOutputs.update(dataSet);
        final double sum = dataSet.getStreamData().sum();
        dataSet.update(e -> e / sum);
        this.sum = sum;
        this.selfOutputs.update(dataSet);
    }

    @Override
    void backward(DataSet deltas, List<DataSet> childrenWeights) {
        final DataSet newDeltas = selfOutputs.update(e -> e * (1 - e));
        deltas.update(newDeltas);
        this.deltas.update(newDeltas);
    }

    @Override
    void lastLayerBackward(final DataSet deltas, final DataSet y, final DataSet outputs) {
//        final DataSet newDeltas = outputs.update(e -> e * (1 - e));
//        deltas.update(newDeltas);
//        this.deltas.update(newDeltas);

        final double[] layerDeltas = new double[deltas.getSize()];
        Arrays.setAll(layerDeltas, i -> outputs.get(i) - y.get(i));
        deltas.update(new DataSet(layerDeltas, deltas.getDimension()));

        this.deltas.update(deltas);
    }
}
