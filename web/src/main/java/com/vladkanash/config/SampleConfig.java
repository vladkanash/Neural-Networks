package com.vladkanash.config;

import com.vladkanash.api.layers.Layer;
import com.vladkanash.network.Network;
import com.vladkanash.network.Trainer;
import com.vladkanash.network.data.Dimension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * Created by vladk on 10.06.2017.
 */

@Configuration
public class SampleConfig {

    private Network getNetwork() {
        final Network network = new Network(new Dimension(3, 3));
        network.addLayer(Layer.conv(new Dimension(2, 2), 1).withSigmoidActivation());
        network.addLayer(Layer.fullyConn(2).withSigmoidActivation());
        network.addLayer(Layer.fullyConn(2).withSigmoidActivation());

        return network;
    }

    @Bean
    @Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
    public Trainer getTrainer() {
        return new Trainer(getNetwork());
    }
}
