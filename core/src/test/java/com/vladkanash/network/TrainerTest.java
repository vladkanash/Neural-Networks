package com.vladkanash.network;

import com.vladkanash.api.layers.ActivationFunction;
import com.vladkanash.api.layers.Layer;
import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by vladk on 27.04.2017.
 */
public class TrainerTest {


    @Test
    public void trainSingleTest() throws Exception {
        final Network network = new Network(new Dimension(2));
        network.addLayer(Layer.fullyConn(4).withSigmoidActivation());
        network.addLayer(Layer.fullyConn(1).withSigmoidActivation());

        final Double[] initialResult = network.forward(new Double[] {4.56, 9.03});
        Arrays.stream(initialResult).forEach(System.out::println);

        final Trainer trainer = new Trainer(network);
        for (int i = 0; i < 300; i++) {
            trainer.trainSingle(new DataSet(new Double[]{4.56, 9.03}, new Dimension(2)),
                                new DataSet(new Double[]{0.8}, new Dimension(1)));
        }

        final Double[] newResult = network.forward(new Double[] {4.56, 9.03});
        Arrays.stream(newResult).forEach(System.out::println);
    }

}