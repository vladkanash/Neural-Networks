package com.vladkanash.network;

import com.vladkanash.network.data.DataSet;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trainer {

    private final Network network;
    private List<Double> costs = new ArrayList<>();

    public Trainer(final Network network) {
        Validate.notNull(network, "network must not be null");
        Validate.isTrue(!network.isEmpty(), "cannot train an empty network");

        this.network = network;
    }

    public void trainSingle(final DataSet x, final DataSet y, final double alpha) {
        final DataSet outputs = network.forward(x);
        network.backward(y, outputs);
        network.updateWeights(alpha);
        addCost(y, outputs);
    }

    private void addCost(DataSet y, DataSet outputs) {
        final Double cost = new DataSet(y.merge(outputs, (a, b) -> a - b))
                .update(e -> e * e)
                .getStreamData().sum() / 2;
        this.costs.add(cost);
    }

    public void trainBatch(final Map<DataSet, DataSet> data, final int batchSize, final double alpha) {
        Validate.isTrue(batchSize > 0, "Batch size must be positive");
        int i = 0;
        for (Map.Entry<DataSet, DataSet> trainExample : data.entrySet()) {
            i++;
            final DataSet outputs = network.forward(trainExample.getKey());
            network.backward(trainExample.getValue(), outputs);
            addCost(trainExample.getValue(), outputs);
            if (i >= batchSize) {
                network.updateWeights(alpha);
                i = 0;
            }
        }
        network.updateWeights(alpha);
    }

    public void trainFull(final Map<DataSet, DataSet> data, final double alpha) {
        for (Map.Entry<DataSet, DataSet> trainExample : data.entrySet()) {
            final DataSet outputs = network.forward(trainExample.getKey());
            network.backward(trainExample.getValue(), outputs);
            addCost(trainExample.getValue(), outputs);
        }
        network.updateWeights(alpha);
    }

    public List<Double> getCosts() {
        return this.costs;
    }

    public void clearCosts() {
        this.costs.clear();
    }
}
