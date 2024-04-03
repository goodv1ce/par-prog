package util;

import java.util.Random;

public class DataGenerator {
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

    public static double[] generateVector(int size, double maxValue) {
        double[] vector = new double[size];
        final Random random = new Random();
        for (int i = 0; i < size; i++) {
            vector[i] = random.nextDouble() * maxValue;
        }
        return vector;
    }
}
