package util;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Operations {
    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * Finds the maximum value of a matrix.
     *
     * @param matrix the 2D array of doubles
     * @return the maximum value in the array
     * @throws java.util.NoSuchElementException if the array is empty
     */
    public static double findMatrixMax(double[][] matrix) {
        // Transforms the 2D array to the stream of double values
        // and uses parallel streams to find the maximum value
        return Arrays.stream(matrix)
                .parallel()
                .flatMapToDouble(Arrays::stream)
                .max()
                .orElseThrow();
    }

    /**
     * Finds the sum of two matrices for the specified chunk
     *
     * @param matrixA the first matrix
     * @param matrixB the second matrix
     * @param result the result matrix
     * @param a the "from" bound
     * @param b the "to" bound
     */
    public static void findMatricesSum(double[][] matrixA, double[][] matrixB, double[][] result, int a, int b) {
        final double[][] localResult = new double[Function.SIZE][Function.SIZE];
        int columns = matrixA[0].length;
        // Iterate over the chunk rows
        for (int i = a; i < b; i++) {
            // Iterate over the columns
            for (int j = 0; j < columns; j++) {
                // Compute the sum of corresponding elements from matrixA and matrixB
                localResult[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        moveValuesMatrices(localResult, result, a, b);
    }

    /**
     * Find the sum of two vectors
     *
     * @param vectorA the first vector
     * @param vectorB the second vector
     * @param result the result vector
     * @param a the "from" bound
     * @param b the "to" bound
     */
    public static void findVectorsSum(double[] vectorA, double[] vectorB, double[] result, int a, int b) {
        final double[] localResult = new double[Function.SIZE];
        for (int i = a; i < b; i++) {
            localResult[i] = vectorA[i] + vectorB[i];
        }
        moveValuesVectors(localResult, result, a, b);
    }

    /**
     * Finds the difference of two matrices for the specified chunk
     *
     * @param matrixA the first matrix
     * @param matrixB the second matrix
     * @param result the result matrix
     * @param a the "from" bound
     * @param b the "to" bound
     */
    public static void findMatricesDifference(double[][] matrixA, double[][] matrixB, double[][] result, int a, int b) {
        final double[][] localResult = new double[Function.SIZE][Function.SIZE];
        int columns = matrixA[0].length;

        // Iterate over the chunk rows
        for (int i = a; i < b; i++) {
            // Iterate over the columns
            for (int j = 0; j < columns; j++) {
                // Compute the difference of corresponding elements from matrixA and matrixB
                localResult[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }
        moveValuesMatrices(localResult, result, a, b);
    }

    /**
     * Multiplies two matrices for the specified chunk
     *
     * @param matrixA the first matrix
     * @param matrixB the second matrix
     * @param result the result matrix
     * @param a the "from" bound
     * @param b the "to" bound
     */
    public static void multiplyMatricesChunk(double[][] matrixA, double[][] matrixB, double[][] result, int a, int b) {
        final double[][] localResult = new double[Function.SIZE][Function.SIZE];
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        // Iterate over the chunk rows
        for (int i = a; i < b; i++) {
            // Iterate over the columns of the second matrix
            for (int j = 0; j < colsB; j++) {
                double c = 0.0;     // error sum
                double sum = 0.0;
                // Compute the dot product of the corresponding row from matrixA and column from matrixB
                for (int k = 0; k < colsA; k++) {
                    double y = (matrixA[i][k] * matrixB[k][j]) - c;
                    double t = sum + y;
                    c = (t - sum) - y;
                    sum = t;
                }
                localResult[i][j] = sum;
            }
        }
        moveValuesMatrices(localResult, result, a, b);
    }

    /**
     * Multiplies scalar by vector
     *
     * @param vector the vector
     * @param scalar the scalar
     * @param result the result vector
     * @param a the "from" bound
     * @param b the "to" bound
     */
    public static void multiplyScalarByVector(double[] vector, double scalar, double[] result, int a, int b) {
        final double[] localResult = new double[Function.SIZE];
        for (int i = a; i < b; i++) {
            localResult[i] = vector[i] * scalar;
        }
        moveValuesVectors(localResult, result, a, b);
    }

    /**
     * Multiplies vector by matrix
     *
     * @param vector the vector
     * @param matrix the matrix
     * @param result the result vector
     * @param a the "from" bound
     * @param b the "to" bound
     */
    public static void multiplyVectorByMatrix(double[] vector, double[][] matrix, double[] result, int a, int b) {
        final double[] localResult = new double[Function.SIZE];
        // Iterate over the specified range [a, b]
        for (int col = a; col < b; col++) {
            double c = 0.0;     // error sum
            double sum = 0.0;

            // Multiply each element of the vector by the corresponding row element of the matrix column
            for (int row = 0; row < vector.length; row++) {
                double y = (vector[row] * matrix[row][col]) - c;
                double t = sum + y;
                c = (t - sum) - y;
                sum = t;
            }
            localResult[col] = sum;
        }
        moveValuesVectors(localResult, result, a, b);
    }

    /**
     * Moves values from one matrix to another within the specified range of rows.
     *
     * @param fromMatrix The source matrix from which values will be copied.
     * @param toMatrix   The destination matrix where values will be copied.
     * @param a          The starting index (inclusive) of rows to move values from.
     * @param b          The ending index (exclusive) of rows to move values from.
     */
    private static void moveValuesMatrices(double[][] fromMatrix, double[][] toMatrix, int a, int b) {
        lock.lock();
        try {
            for (int i = a; i < b; i++) {
                System.arraycopy(fromMatrix[i], 0, toMatrix[i], 0, fromMatrix[0].length);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Moves values from one vector to another within the specified range of indices.
     *
     * @param fromVector The source vector from which values will be copied.
     * @param toVector   The destination vector where values will be copied.
     * @param a          The starting index (inclusive) from which values will be copied.
     * @param b          The ending index (exclusive) up to which values will be copied.
     */
    private static void moveValuesVectors(double[] fromVector, double[] toVector, int a, int b) {
        lock.lock();
        try {
            if (b - a >= 0) System.arraycopy(fromVector, a, toVector, a, b - a);
        } finally {
            lock.unlock();
        }
    }
}
