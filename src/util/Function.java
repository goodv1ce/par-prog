package util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * The Function class provides methods for performing calculations according to predefined functions.
 * It uses multithreading to speed up computation.
 */
public class Function {
    public static final int SIZE = 1000;
    private final int threadPayload;
    private final double[][] MF;
    double[][] MD;
    double[][] ME;
    double[][] MM;
    double[] B;
    double[] D;
    double[] E;
    Thread[] threads;
    private final CyclicBarrier cyclicBarrier;
    private final CyclicBarrier endBarrier;
    private boolean maxMethodCalled;

    /**
     * Constructs a Function object.
     * Initializes matrices and vectors from imported data, sets up barriers for synchronization,
     * and determines the number of threads to use for computation.
     */
    public Function() {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        this.cyclicBarrier = new CyclicBarrier(numberOfThreads);                      // barrier for threads which work with a function
        this.endBarrier = new CyclicBarrier(numberOfThreads + 1);                         // barrier for the main thread to wait for the end of calculations
        this.threadPayload = SIZE / 4;                                         // defining the part of the matrices and vector for threads to work with
        this.MF = new double[SIZE][SIZE];                                      // defining result matrix
        this.maxMethodCalled = false;                                          // flag to check if one of threads called the max of the matrix function
        this.E = new double[SIZE];
        DataImporter dataImporter = new DataImporter();
        MD = dataImporter.importMatrix("MD");
        ME = dataImporter.importMatrix("ME");
        MM = dataImporter.importMatrix("MM");
        B = dataImporter.importVector("B");
        D = dataImporter.importVector("D");
        this.threads = new Thread[numberOfThreads];                            // thread pool
    }

    /**
     * Waits for other threads to reach the barrier (for inner function calculations)
     */
    private void waitForOtherThreads() {
        try {
            cyclicBarrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for all threads to reach the barrier (wait for the end of calculation)
     */
    private void waitForTheOtherThreadsToEnd() {
        try {
            endBarrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the first function as per the defined formula.
     */
    public void calculateFirstFunction() {
        Timer firstFunctionTimer = new Timer();
        System.out.println("\n\nFunction 1:\n");
        System.out.println("Ten elements of the first row of every matrix before calculations:\nMD:");
        Printer.printMatrixFirstRowInBounds(MD, 0, 10);
        System.out.println("\n\nME:");
        Printer.printMatrixFirstRowInBounds(ME, 0, 10);
        System.out.println("\n\nMМ:");
        Printer.printMatrixFirstRowInBounds(MM, 0, 10);
        System.out.println();

        // MF = MD * (ME + MM) - ME * MM
        double[][] tempVar = new double[SIZE][SIZE];    // tempVar for calculations
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                // dynamically assign bounds for every thread via threadPayload var
                Operations.findMatricesSum(ME, MM, tempVar, finalI * threadPayload, (finalI + 1) * threadPayload);        // tempVar = ME + MM
                waitForOtherThreads();
                Operations.multiplyMatricesChunk(MD, tempVar, MF, finalI * threadPayload, (finalI + 1) * threadPayload);  // MF = MD * (ME + MM) OR MD * tempVar
                waitForOtherThreads();
                Operations.multiplyMatricesChunk(ME, MM, tempVar, finalI * threadPayload, (finalI + 1) * threadPayload);  // tempVar = ME * MM
                waitForOtherThreads();
                Operations.findMatricesDifference(MF, tempVar, MF, finalI * threadPayload, (finalI + 1) * threadPayload); // MF = MD * (ME + MM) - ME * MM OR MF - tempVar
                waitForOtherThreads();
                System.out.println("\n" + Thread.currentThread().getName() + " has finished ");
                waitForTheOtherThreadsToEnd();
            });
        }

        firstFunctionTimer.startCountdown();
        // starting threads
        for (Thread thread : threads) {
            thread.start();
        }

        // waiting for the end of threads work
        waitForTheOtherThreadsToEnd();
        firstFunctionTimer.endCountDown();
        firstFunctionTimer.printResult();

        System.out.println("\nTen elements of the first row of the result matrix:");
        Printer.printMatrixFirstRowInBounds(MF, 0, 10);
        System.out.println();

        // exporting to the file
        DataExporter dataExporter = new DataExporter();
        dataExporter.save(MF, "MF-result.txt");
    }

    /**
     * Calculates the second function as per the defined formula.
     */
    public void calculateSecondFunction() {
        Timer secondFunctionTimer = new Timer();
        System.out.println("Function 2:\n");
        System.out.println("Ten elements of every vector and matrix:\nB:");
        Printer.printVectorInBounds(B, 0, 10);
        System.out.println("\n\nD:");
        Printer.printVectorInBounds(D, 0, 10);
        System.out.println("\n\nME:");
        Printer.printMatrixFirstRowInBounds(ME, 0, 10);
        System.out.println("\n\nMМ:");
        Printer.printMatrixFirstRowInBounds(MM, 0, 10);
        System.out.println();

        // E = B * ME + D * max(MM)
        double[] tempVar = new double[SIZE];    // tempVar for calculations
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                double t = 0.0;
                // calling the findMatrixMax method only one and setting the flag
                // so other threads won't call the method
                synchronized (this) {
                    if (!maxMethodCalled) {
                        t = Operations.findMatrixMax(MM);   // t = max(MM)
                        maxMethodCalled = true;
                    }
                }
                // dynamically assign bounds for every thread via threadPayload var
                Operations.multiplyScalarByVector(D, t, tempVar, finalI * threadPayload, (finalI + 1) * threadPayload);   // tempVar = D * max(MM)
                waitForOtherThreads();
                Operations.multiplyVectorByMatrix(B, ME, E, finalI * threadPayload, (finalI + 1) * threadPayload);        // E = B * ME
                waitForOtherThreads();
                Operations.findVectorsSum(E, tempVar, E, finalI * threadPayload, (finalI + 1) * threadPayload);          // E = B * ME + D * max(MM) OR E + tempVar
                System.out.println("\n" + Thread.currentThread().getName() + " has finished ");
                waitForTheOtherThreadsToEnd();
            });
        }

        secondFunctionTimer.startCountdown();
        // starting threads
        for (Thread thread : threads) {
            thread.start();
        }

        // waiting for the end of threads work
        waitForTheOtherThreadsToEnd();
        secondFunctionTimer.endCountDown();
        secondFunctionTimer.printResult();

        System.out.println("\nFirst ten elements of the result vector:");
        Printer.printVectorInBounds(E, 0, 10);

        // exporting to the file
        DataExporter dataExporter = new DataExporter();
        dataExporter.save(E, "E-result.txt");
    }
}
