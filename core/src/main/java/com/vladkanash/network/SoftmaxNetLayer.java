package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import com.vladkanash.network.util.DataSetUtils;

import java.util.List;

/**
 * Created by vladk on 07.05.2017.
 */
public class SoftmaxNetLayer extends NetLayer {


    SoftmaxNetLayer(
            final Dimension inputDimension,
            final Dimension outputDimension,
            final ActivationFunction activationFunction) {
        super(inputDimension, outputDimension,
                DataSetUtils.getRandomDataSetList(Dimension.EMPTY, 1), activationFunction);
    }

    @Override
    void forward(final DataSet dataSet) {
        final double sum = dataSet.getStreamData().sum();
        dataSet.update(e -> e / sum);
    }

    @Override
    void backward(DataSet deltas, List<DataSet> childrenWeights) {

    }

    @Override
    void lastLayerBackward(final DataSet deltas, final DataSet y, final DataSet outputs) {

    }
}
