package com.vladkanash.layer;

import com.vladkanash.network.DataSet;

/**
 * Created by vladk on 21.04.2017.
 */
public class ReLULayer extends GenericLayer {

    public ReLULayer(int inputWidth, int outputWidth) {
        super(inputWidth, outputWidth);
    }

    @Override
    public void forward(DataSet dataSet) {
        final Double[] data = dataSet.getArrayData();
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i] > 0 ? data[i] : 0;
        }
    }
}
