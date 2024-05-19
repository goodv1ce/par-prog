package util;

import thread.factory.FunctionThreadPoolFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * The Function class provides methods for performing calculations according to predefined functions.
 * It uses multithreading to speed up computation.
 */
public class Function {
    public static final int SIZE = 1000;
    public static final int THREAD_PAYLOAD = SIZE / 4;
    private final double[][] MF;
    public static final int NUMBER_OF_THREADS = 4;
    private final FunctionThreadPoolFactory threadPoolFactory;
    double[][] MD;
    double[][] ME;
    double[][] MM;
    double[] B;
    double[] D;
    double[] E;
    private final CyclicBarrier cyclicBarrier;

    /**
     * Constructs a Function object.
     * Initializes matrices and vectors from imported data, sets up barriers for synchronization,
     * and determines the number of threads to use for computation.
     */
    public Function() {
        this.cyclicBarrier = new CyclicBarrier(NUMBER_OF_THREADS);
        this.MF = new double[SIZE][SIZE];
        this.E = new double[SIZE];
        DataImporter dataImporter = new DataImporter();
        MD = dataImporter.importMatrix("MD");
        ME = dataImporter.importMatrix("ME");
        MM = dataImporter.importMatrix("MM");
        B = dataImporter.importVector("B");
        D = dataImporter.importVector("D");         // thread pool
        this.threadPoolFactory = new FunctionThreadPoolFactory(this);
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
     * Calculates the first function
     */
    public void calculateFirstFunction() {
        Timer firstFunctionTimer = Timer.getInstance();
        firstFunctionTimer.startCountdown();
        List<FutureTask<String>> futureTasks = threadPoolFactory.calculateFirstFunction();
        for (FutureTask<String> task : futureTasks) {
            try {
                System.out.println(task.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        Timer secondFunctionTimer = Timer.getInstance();
        // E = B * ME + D * max(MM)
        secondFunctionTimer.startCountdown();
        List<FutureTask<String>> futureTasks = threadPoolFactory.calculateSecondFunction();
        for (FutureTask<String> task : futureTasks) {
            try {
                System.out.println(task.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        secondFunctionTimer.endCountDown();
        secondFunctionTimer.printResult();

        System.out.println("\nFirst ten elements of the result vector:");
        Printer.printVectorInBounds(E, 0, 10);

        // exporting to the file
        DataExporter dataExporter = new DataExporter();
        dataExporter.save(E, "E-result.txt");
    }

    public double[][] getMF() {
        return MF;
    }

    public double[][] getMD() {
        return MD;
    }

    public double[][] getME() {
        return ME;
    }

    public double[][] getMM() {
        return MM;
    }

    public double[] getB() {
        return B;
    }

    public double[] getD() {
        return D;
    }

    public double[] getE() {
        return E;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }
}
