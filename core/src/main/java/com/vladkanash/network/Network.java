package com.vladkanash.network;

import com.vladkanash.layer.GenericLayer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vladk on 13.04.2017.
 */
abstract class Network {

    private DataSet dataSet;
    private final List<GenericLayer> layers = new LinkedList<GenericLayer>();

    public Network() {

    }

    public void addLayer(final GenericLayer layer) {
        this.layers.add(layer);
    }

    public void Forward() {
        this.layers.forEach(l -> l.forward(dataSet));
    }

    public abstract void Backward();

    public abstract void getCost();


}
