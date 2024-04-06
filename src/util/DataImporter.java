package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataImporter {
    private final String targetDirectoryPath;

    public DataImporter() {
        this.targetDirectoryPath = System.getProperty("user.dir") + "/data";
    }

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
