package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataExporter {
    private final String targetDirectoryPath;

    public DataExporter() {
        this.targetDirectoryPath = System.getProperty("user.dir") + "/data";
    }

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
