package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The DataExporter class provides methods for exporting data (matrices and vectors) to files.
 */
public class DataExporter {
    private final String targetDirectoryPath;

    /**
     * Constructs a new DataExporter object with the default target directory path.
     */
    public DataExporter() {
        this.targetDirectoryPath = System.getProperty("user.dir") + "/data";
    }

    /**
     * Saves a matrix to a file with the specified filename.
     *
     * @param matrix    The matrix to be saved.
     * @param fileName  The name of the file to which the matrix will be saved.
     */
    public void save(double[][] matrix, String fileName) {
        final String path = targetDirectoryPath + "/" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (double[] currRow : matrix) {
                for (int j = 0; j < currRow.length; j++) {
                    writer.write(Double.toString(currRow[j]));
                    if (j < currRow.length - 1) {
                        writer.write(", ");
                    }
                }
                writer.newLine();
            }
            System.out.println("Data has been saved to " + path);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Saves a vector to a file with the specified filename.
     *
     * @param vector    The vector to be saved.
     * @param fileName  The name of the file to which the vector will be saved.
     */
    public void save(double[] vector, String fileName) {
        final String path = targetDirectoryPath + "/" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < vector.length; i++) {
                writer.write(Double.toString(vector[i]));
                if (i < vector.length - 1) {
                    writer.write(", ");
                }
            }
            System.out.println("Data has been saved to " + path);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
