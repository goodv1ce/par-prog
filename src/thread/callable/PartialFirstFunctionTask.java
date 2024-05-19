package thread.callable;

import util.Function;
import util.Operations;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;


/**
 * This class contains a logic to calculate the part of the first function.
 * Should be wrapped with a FutureTask
 */
public class PartialFirstFunctionTask implements Callable<String> {
    private final double[][] ME;
    private final double[][] MM;
    private final double[][] MD;
    private final double[][] MF;
    private final double[][] tempVar;
    private final int threadNumber;
    private final int threadPayload;
    private final CyclicBarrier barrier;

    public PartialFirstFunctionTask(double[][] ME, double[][] MM, double[][] MD, double[][] MF, double[][] tempVar, int threadNumber, CyclicBarrier barrier) {
        this.ME = ME;
        this.MM = MM;
        this.MD = MD;
        this.MF = MF;
        this.tempVar = tempVar;
        this.threadNumber = threadNumber;
        this.threadPayload = Function.THREAD_PAYLOAD;
        this.barrier = barrier;
    }

    @Override
    public String call() {
        Operations.findMatricesSum(ME, MM, tempVar, threadNumber * threadPayload, (threadNumber + 1) * threadPayload);        // tempVar = ME + MM
        waitForOtherThreads();
        Operations.multiplyMatricesChunk(MD, tempVar, MF, threadNumber * threadPayload, (threadNumber + 1) * threadPayload);  // MF = MD * (ME + MM) OR MD * tempVar
        waitForOtherThreads();
        Operations.multiplyMatricesChunk(ME, MM, tempVar, threadNumber * threadPayload, (threadNumber + 1) * threadPayload);  // tempVar = ME * MM
        waitForOtherThreads();
        Operations.findMatricesDifference(MF, tempVar, MF, threadNumber * threadPayload, (threadNumber + 1) * threadPayload); // MF = MD * (ME + MM) - ME * MM OR MF - tempVar
        waitForOtherThreads();

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
