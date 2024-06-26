package util;

public class Printer {
    public static void printMatrixFirstRowInBounds(double[][] matrix, int a, int b) {
        double[] row = matrix[0];
        for (int i = a; i < b; i++) {
            System.out.print(row[i]);
            if (i != b - 1) {
                System.out.print(", ");
            }
        }
    }

    public static void printVectorInBounds(double[] vector, int a, int b) {
        for (int i = a; i < b; i++) {
            System.out.print(vector[i]);
            if (i != b - 1) {
                System.out.print(", ");
            }
        }
    }
}
