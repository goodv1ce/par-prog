package thread.callable;

import util.Operations;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

/**
 * This class contains a logic to calculate the part of the first function.
 * Should be wrapped with a FutureTask
 */
public class PartialSecondFunctionTask implements Callable<String> {
    private final double[] B;
    private final double[] D;
    private final double[][] ME;
    private final double[][] MM;
    private final double[] E;
    private final double[] tempVar;
    private final int threadNumber;
    private final int threadPayload;
    private final CyclicBarrier barrier;
    private boolean maxMethodCalled;

    public PartialSecondFunctionTask(double[] b, double[] d, double[] e, double[][] me, double[][] mm, double[] tempVar,
                                     int threadNumber, int threadPayload, CyclicBarrier barrier) {
        B = b;
        D = d;
        E = e;
        ME = me;
        MM = mm;
        this.tempVar = tempVar;
        this.threadNumber = threadNumber;
        this.threadPayload = threadPayload;
        this.barrier = barrier;
    }

    /**
     * Calculates the part of the second function
     *
     * @return a message about the end of calculations
     */
    @Override
    public String call() {
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
        Operations.multiplyScalarByVector(D, t, tempVar, threadNumber * threadPayload, (threadNumber + 1) * threadPayload);   // tempVar = D * max(MM)
        waitForOtherThreads();
        Operations.multiplyVectorByMatrix(B, ME, E, threadNumber * threadPayload, (threadNumber + 1) * threadPayload);        // E = B * ME
        waitForOtherThreads();
        Operations.findVectorsSum(E, tempVar, E, threadNumber * threadPayload, (threadNumber + 1) * threadPayload);          // E = B * ME + D * max(MM) OR E + tempVar
        return "\nThread " + threadNumber + " has finished.";
    }

    private void waitForOtherThreads() {
        try {
            barrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
