package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;

import java.util.List;

/**
 * Created by vladk on 10.06.2017.
 */
public class PoolingNetLayer extends NetLayer {

    PoolingNetLayer(Dimension inputDimension, Dimension outputDimension, List<DataSet> weights, ActivationFunction activationFunction) {
        super(inputDimension, outputDimension, weights, activationFunction);
    }

    @Override
    void forward(DataSet dataSet) {

    }

    @Override
    void backward(DataSet deltas, List<DataSet> childrenWeights) {

    }

    @Override
    void lastLayerBackward(DataSet deltas, DataSet y, DataSet outputs) {

    }
}
