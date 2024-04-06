package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The DataImporter class provides methods for importing matrices and vectors from files.
 */
public class DataImporter {
    private final String targetDirectoryPath;

    /**
     * Constructs a new DataImporter object with the default target directory path.
     */
    public DataImporter() {
        this.targetDirectoryPath = System.getProperty("user.dir") + "/data";
    }

    /**
     * Imports a matrix from a file with the specified name.
     *
     * @param name The name of the file from which the matrix will be imported.
     * @return The imported matrix as a 2D array of doubles.
     */
    public double[][] importMatrix(String name) {
        int size = Function.SIZE;
        String line;
        final String path = targetDirectoryPath + "/" + name + ".txt";
        double[][] matrix = new double[size][size];
        int rowIndex = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(", ");
                for (int i = 0; i < elements.length; i++) {
                    matrix[rowIndex][i] = Double.parseDouble(elements[i]);
                }
                rowIndex++;
            }
            return matrix;
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
            return null;
        }
    }

    /**
     * Imports a vector from a file with the specified name.
     *
     * @param name The name of the file from which the vector will be imported.
     * @return The imported vector as an array of doubles.
     */
    public double[] importVector(String name) {
        int size = Function.SIZE;
        final String path = targetDirectoryPath + "/" + name + ".txt";
        double[] vector = new double[size];
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            String[] elements = line.split(", ");
            for (int i = 0; i < elements.length; i++) {
                vector[i] = Double.parseDouble(elements[i]);
            }
            return vector;
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
            return null;
        }
    }
}
