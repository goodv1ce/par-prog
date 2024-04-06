package util;

/**
 * The Printer class provides static methods for printing matrices and vectors.
 */
public class Printer {
    /**
     * Prints the elements of the first row of a matrix within the specified bounds.
     *
     * @param matrix The matrix whose first row will be printed.
     * @param a      The starting index (inclusive) within the first row.
     * @param b      The ending index (exclusive) within the first row.
     */
    public static void printMatrixFirstRowInBounds(double[][] matrix, int a, int b) {
        double[] row = matrix[0];
        for (int i = a; i < b; i++) {
            System.out.print(row[i]);
            if (i != b - 1) {
                System.out.print(", ");
            }
        }
    }

    /**
     * Prints the elements of a vector within the specified bounds.
     *
     * @param vector The vector to be printed.
     * @param a      The starting index (inclusive) within the vector.
     * @param b      The ending index (exclusive) within the vector.
     */
    public static void printVectorInBounds(double[] vector, int a, int b) {
        for (int i = a; i < b; i++) {
            System.out.print(vector[i]);
            if (i != b - 1) {
                System.out.print(", ");
            }
        }
    }
}
