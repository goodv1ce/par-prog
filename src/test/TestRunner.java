package test;

import util.*;

public class TestRunner {
    public static void main(String[] args) {
//        double[][] matrixF = DataGenerator.generateMatrix(10000,10000,1e-15);
//
//        long startTime = System.currentTimeMillis();
//        double max = Operations.matrixMax(matrixF);
//        long endTime = System.currentTimeMillis();
//        double duration = (endTime - startTime) / 1000.0;
//        System.out.println("1Time: " + duration + "\nvalue: " + max);

//        double[][] matrixA = {
//                {5,9,4,9,5,2,9,0},
//                {1,5,7,9,9,4,9,3},
//                {9,5,1,2,8,5,5,8},
//                {2,2,7,8,2,6,4,5},
//                {5,7,7,3,3,9,4,0},
//                {1,6,0,5,4,4,5,2},
//                {7,3,2,9,3,4,4,1},
//                {2,0,0,5,7,7,6,4}
//        };
//
//        double[][] matrixB = {
//                {2,0,0,3,2,1,8,0},
//                {5,9,4,1,6,1,8,4},
//                {6,8,4,1,9,5,8,6},
//                {9,4,4,3,8,7,5,2},
//                {9,0,8,6,7,2,4,6},
//                {1,2,4,5,7,2,0,0},
//                {0,4,4,8,5,4,0,0},
//                {1,9,2,5,9,3,5,0}
//        };
//        double[][] resultMatrix = new double[8][8];
//
//        Operations.multiplyMatricesChunk(matrixA, matrixB, resultMatrix, 0,8);
//        Operations.print(resultMatrix);
//
//        System.out.println();
//
//        resultMatrix = new double[8][8];
//        Operations.multiplyMatricesChunkPrev(matrixA, matrixB, resultMatrix, 0,8);
//        Operations.print(resultMatrix);


//        double[][] matrixA = DataGenerator.generateMatrix(1000,1000,1e-15);
//        double[][] matrixB = DataGenerator.generateMatrix(1000,1000,1e-15);
//        double[][] resultMatrix1 = new double[1000][1000];
//
//        Operations.multiplyMatricesChunk(matrixA, matrixB, resultMatrix1, 0, 1000);
//
//        double[][] resultMatrix2 = new double[1000][1000];
//        Operations.multiplyMatricesChunkPrev(matrixA, matrixB, resultMatrix2, 0, 1000);
//
//        for (int i = 0; i < resultMatrix1.length; i++) {
//            for (int j = 0; j < resultMatrix1[0].length; j++) {
//                // Порівняння елементів матриць
//                if (resultMatrix1[i][j] != resultMatrix2[i][j]) {
//                    System.out.println("Елемент [" + i + "][" + j + "]: " + matrixA[i][j] + " не збігається з " + matrixB[i][j]);
//                }
//            }
//        }
//
//        System.out.println("Порівняння завершено.");

//        double[][] matrixA = DataGenerator.generateMatrix(5,5,1e-15);
//        double[][] matrixB = DataGenerator.generateMatrix(5,5,1e-15);
//        double[][] resultMatrix1 = new double[5][5];
//
//        Operations.multiplyMatricesChunk(matrixA, matrixB, resultMatrix1, 0, 5);
//        Operations.print(matrixA);
//        System.out.println();
//        Operations.print(matrixB);
//        System.out.println();
//        Operations.print(resultMatrix1);

        double[] vector = {3,5,7};
        double[] vector1 = {3,2,1};
//        double[][] matrix = {
//                {1,2,3},
//                {4,5,6},
//                {7,8,9}
//        };

        double[] result = new double[3];
        Operations.findVectorsSum(vector, vector1, result, 0, 3);
        Printer.printVectorInBounds(result, 0, 3);
    }
}
