package com.vladkanash.network;

import com.vladkanash.api.layers.Layer;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by vladk on 27.04.2017.
 */
public class TrainerTest {


    @Test
    public void trainSingleTest() throws Exception {
        final Network network = new Network(new Dimension(3, 3));
        network.addLayer(Layer.conv(new Dimension(2, 2)));
        network.addLayer(Layer.fullyConn(2).withSigmoidActivation());
        network.addLayer(Layer.fullyConn(21).withSigmoidActivation());
        network.addLayer(Layer.fullyConn(1).withSigmoidActivation());

        final Double[] initialResult = network.forward(new Double[] {4.56, 9.03, 3.0, -12.3, 65.0, 0.1, 4.9, -2.27, 0.0});
        Arrays.stream(initialResult).forEach(System.out::println);

        final Trainer trainer = new Trainer(network);
        for (int i = 0; i < 300; i++) {
            trainer.trainSingle(new DataSet(new Double[]{4.56, 9.03, 3.0, -12.3, 65.0, 0.1, 4.9, -2.27, 0.0}, new Dimension(3, 3)),
                                new DataSet(new Double[]{0.01}, new Dimension(1)));
        }

        final Double[] newResult = network.forward(new Double[] {4.56, 9.03, 3.0, -12.3, 65.0, 0.1, 4.9, -2.27, 0.0});
        Arrays.stream(newResult).forEach(System.out::println);
        Assert.assertTrue(initialResult[0] > newResult[0]);
    }

}