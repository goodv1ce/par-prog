package util;

import java.util.Random;

/**
 * The DataGenerator class provides methods for generating random matrices and vectors.
 */
public class DataGenerator {
    /**
     * Generates a random matrix with the specified number of rows and columns.
     *
     * @param rows      The number of rows in the matrix.
     * @param cols      The number of columns in the matrix.
     * @param maxValue  The maximum value for the elements in the matrix.
     * @return          A 2D array representing the generated matrix.
     */
    public static double[][] generateMatrix(int rows, int cols, double maxValue) {
        double[][] matrix = new double[rows][cols];
        final Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextDouble() * maxValue;
            }
        }

        return matrix;
    }

    /**
     * Generates a random vector with the specified size.
     *
     * @param size      The size of the vector.
     * @param maxValue  The maximum value for the elements in the vector.
     * @return          An array representing the generated vector.
     */
    public static double[] generateVector(int size, double maxValue) {
        double[] vector = new double[size];
        final Random random = new Random();
        for (int i = 0; i < size; i++) {
            vector[i] = random.nextDouble() * maxValue;
        }
        return vector;
    }
}
