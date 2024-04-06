package util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Function {
    public static final int SIZE = 1000;
    private final int numberOfThreads;
    private final int threadPayload;
    private final double[][] MF;
    double[][] MD;
    double[][] ME;
    double[][] MM;
    Thread[] threads;
    private final CyclicBarrier cyclicBarrier;
    private final CyclicBarrier endBarrier;

    public Function() {
        this.cyclicBarrier = new CyclicBarrier(4);
        this.endBarrier = new CyclicBarrier(5);
        this.numberOfThreads = Runtime.getRuntime().availableProcessors();
        this.threadPayload = SIZE / 4;
        this.MF = new double[SIZE][SIZE];
        DataImporter dataImporter = new DataImporter();
        MD = dataImporter.importMatrix("MD");
        ME = dataImporter.importMatrix("ME");
        MM = dataImporter.importMatrix("MM");
        this.threads = new Thread[numberOfThreads]; // thread pool
    }

    private void waitForOtherThreads() {
        try {
            cyclicBarrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitForTheOtherThreadsToEnd() {
        try {
            endBarrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void calculate() {
        System.out.println("Десять елементів першого рядка матриць до обчислень:\nMD:");
        Printer.printMatrixFirstRowInBounds(MD, 0, 10);
        System.out.println("\nME:");
        Printer.printMatrixFirstRowInBounds(ME, 0, 10);
        System.out.println("\nMМ:");
        Printer.printMatrixFirstRowInBounds(MM, 0, 10);
        // MF = MD * (ME + MM) - ME * MM
        double[][] tempVar = new double[SIZE][SIZE];    // tempVar for calculations
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                int n = finalI;
                // dynamically assign bounds for every thread via threadPayload var
                Operations.findMatricesSum(ME, MM, tempVar, n * threadPayload, (n + 1) * threadPayload);        // tempVar = ME + MM
                waitForOtherThreads();
                Operations.multiplyMatricesChunk(MD, tempVar, MF, n * threadPayload, (n + 1) * threadPayload);  // MF = MD * (ME + MM) OR MD * tempVar
                waitForOtherThreads();
                Operations.multiplyMatricesChunk(ME, MM, tempVar, n * threadPayload, (n + 1) * threadPayload);  // tempVar = ME * MM
                waitForOtherThreads();
                Operations.findMatricesDifference(MF, tempVar, MF, n * threadPayload, (n + 1) * threadPayload); // MF = MD * (ME + MM) - ME * MM OR MF - tempVar
                waitForOtherThreads();
                System.out.println("\n" + Thread.currentThread().getName() + " has finished ");
                waitForTheOtherThreadsToEnd();
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        waitForTheOtherThreadsToEnd();

        System.out.println("\nДесять елементів першого рядка результуючої матриці:");
        Printer.printMatrixFirstRowInBounds(MF, 0, 10);
    }
}
