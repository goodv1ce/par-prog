package util;

public class DataGeneratorRunner {
    public static void main(String[] args) {
        final DataExporter dataExporter = new DataExporter();

        double[][] matrixF = DataGenerator.generateMatrix(1000,1000,1e-15);
        double[][] matrixD = DataGenerator.generateMatrix(1000,1000,1e-15);
        double[][] matrixE = DataGenerator.generateMatrix(1000,1000,1e-15);
        double[][] matrixM = DataGenerator.generateMatrix(1000,1000,1e-15);

        dataExporter.save(matrixF, "MF.txt");
        dataExporter.save(matrixD, "MD.txt");
        dataExporter.save(matrixE, "ME.txt");
        dataExporter.save(matrixM, "MM.txt");

        double[] vectorE = DataGenerator.generateVector(1000, 1e10);
        double[] vectorB = DataGenerator.generateVector(1000, 1e10);
        double[] vectorD = DataGenerator.generateVector(1000, 1e10);

        dataExporter.save(vectorE, "E.txt");
        dataExporter.save(vectorB, "B.txt");
        dataExporter.save(vectorD, "D.txt");
    }
}
