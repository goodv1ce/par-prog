package util;

import java.util.Arrays;

public class Operations {

    public static void print(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

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
        int columns = matrixA[0].length;

        // Iterate over the chunk rows
        for (int i = a; i < b; i++) {
            // Iterate over the columns
            for (int j = 0; j < columns; j++) {
                // Compute the sum of corresponding elements from matrixA and matrixB
                result[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
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
        int columns = matrixA[0].length;

        // Iterate over the chunk rows
        for (int i = a; i < b; i++) {
            // Iterate over the columns
            for (int j = 0; j < columns; j++) {
                // Compute the difference of corresponding elements from matrixA and matrixB
                result[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }
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
                result[i][j] = sum;
            }
        }
    }

}
