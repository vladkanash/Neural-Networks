package com.vladkanash.network.computation;

import com.vladkanash.network.DataSet;
import com.vladkanash.network.Dimension;
import org.apache.commons.lang3.Validate;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by vladk on 23.04.2017.
 */

//Weigth matrix: INPUTS(width) * NEURONS(height)

public class SingleThreadMathOperations implements MathOperations {

    private final static MathOperations instance = new SingleThreadMathOperations();

    private SingleThreadMathOperations() {}

    public static MathOperations getInstance() {
        return instance;
    }

    @Override
    public DataSet getFullyConnOutput(DataSet weights, DataSet input) {
        Validate.isTrue(weights.getDimension().getWidth() == input.getSize(),
                "Dimensions must match");
        Validate.isTrue(input.getDimension().isSingleDimensional(),
                "Input must be one-dimensional");

        final RealMatrix weightsMatrix = MatrixUtils.createRealMatrix(weights.get2DArrayData());

        final double[] result = weightsMatrix.operate(input.getArrayData());
        final Dimension resultDimension = new Dimension(1, 1, weights.getDimension().getHeight());
        return new DataSet(result, resultDimension);
    }
}
