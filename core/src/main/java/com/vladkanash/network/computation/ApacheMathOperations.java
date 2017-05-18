package com.vladkanash.network.computation;

import com.vladkanash.network.data.DataSet;
import com.vladkanash.network.data.Dimension;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladk on 23.04.2017.
 */

//Weigth matrix: INPUTS(width) * NEURONS(height)

public class ApacheMathOperations implements MathOperations {

    private final static MathOperations instance = new ApacheMathOperations();

    private ApacheMathOperations() {}

    public static MathOperations getInstance() {
        return instance;
    }

    @Override
    public DataSet forwardLayer(final DataSet weights, final DataSet input) {
        final RealMatrix weightsMatrix = MatrixUtils.createRealMatrix(weights.get2DArrayData());
        return multiply(weightsMatrix, input);
    }

    @Override
    public DataSet backwardLayer(final DataSet weights, final DataSet delta) {
        final RealMatrix weightsMatrix = MatrixUtils.createRealMatrix(weights.get2DArrayData()).transpose();
        return multiply(weightsMatrix, delta);
    }

    @Override
    public DataSet outerProduct(final DataSet dataSetA, final DataSet dataSetB) {
        final RealVector vectorA = MatrixUtils.createRealVector(dataSetA.getArrayData());
        final RealVector vectorB = MatrixUtils.createRealVector(dataSetB.getArrayData());

        final RealMatrix result = vectorA.outerProduct(vectorB);
        final Dimension resultDimension = new Dimension(vectorA.getDimension(), vectorB.getDimension());
        return new DataSet(result.getData(), resultDimension);
    }

    @Override
    public DataSet convolve(final DataSet kernel, final DataSet input) {
        final int inputWidth = input.getDimension().getWidth();
        final int inputHeight = input.getDimension().getHeight();
        final int inputDepth = input.getDimension().getDepth();

        final int kernelWidth = kernel.getDimension().getWidth();
        final int kernelHeight = kernel.getDimension().getHeight();
        final int kernelDepth = input.getDimension().getDepth();

        final int outputWidth = inputWidth - kernelWidth + 1;
        final int outputHeight = inputHeight - kernelHeight + 1;
        final int outputDepth = 1;

        final List<Double> result = new ArrayList<>(outputDepth * outputHeight * outputWidth);

        for (int z = 0; z < outputDepth; z++) {
            int ay = -kernelHeight + 1;
            for (int y = 0; y < outputHeight; y++, ay++) {
                int ax = -kernelWidth + 1;
                for (int x = 0; x < outputWidth; x++, ax++) {
                    double res = 0.0;



                    for (int fx = 0; fx < kernelWidth; fx++) {
                        int ox = ax + fx;
                        for (int fy = 0; fy < kernelHeight; fy++) {
                            int oy = ay + fy;

                            if (oy >= 0 && oy < inputHeight && ox >= 0 && ox < inputWidth) {
                                for (int fd = 0; fd < kernelDepth; fd++) {
                                    res += kernel.get(fx, fy, fd) * input.get(ox, oy, fd);
                                }
                            }
                        }
                    }
                    result.add(res);
                }
            }
        }
        return new DataSet(result, new Dimension(outputWidth, outputHeight, outputDepth));
    }

    private DataSet multiply(final RealMatrix matrix, final DataSet vector) {
        final double[] result = matrix.operate(vector.getArrayData());
        final Dimension resultDimension = new Dimension(1, 1, matrix.getRowDimension());
        return new DataSet(result, resultDimension);
    }
}
