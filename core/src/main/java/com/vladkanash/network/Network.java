package com.vladkanash.network;

/**
 * Created by vladk on 13.04.2017.
 */
abstract class Network {

    private final DataSet dataSet = new DataSet();

    public abstract DataSet Forward(final DataSet input);

    public abstract DataSet Backward(final DataSet y);

    public abstract DataSet getCost(final DataSet input, final DataSet y);


}
