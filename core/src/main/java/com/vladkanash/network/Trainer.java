package com.vladkanash.network;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.apache.commons.lang3.Validate;

public class Trainer {

    private final Network network;

    public Trainer(final Network network) {
        Validate.notNull(network, "network must not be null");
        Validate.isTrue(!network.isEmpty(), "cannot train an empty network");

        this.network = network;
    }

    public void trainSingle(final DataSet x, final DataSet y) {
        final DataSet outputs = network.forward(x);
        network.backward(y, outputs);
    }
}
