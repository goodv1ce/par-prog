package thread.factory;

import thread.callable.PartialFirstFunctionTask;
import thread.callable.PartialSecondFunctionTask;
import util.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * The factory for operating objects that implements Callable and used to calculate function parts.
 */
public class FunctionForkAndJoinPoolFactory {
    private final Function function;

    public FunctionForkAndJoinPoolFactory(Function function) {
        this.function = function;
    }

    /**
     * Calculates the first function asynchronously. Returns a list of future tasks.
     * Creates callable instances to calculate function chunks parallel
     * and asynchronously using future tasks and running it.
     *
     * @return list of a FutureTask with a result String message
     */
    public List<ForkJoinTask<String>> calculateFirstFunction() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        double[][] tempVar = new double[Function.SIZE][Function.SIZE];
        List<ForkJoinTask<String>> tasks = new ArrayList<>();
        for (int i = 0; i < Function.NUMBER_OF_THREADS; i++) {
            final RecursiveTask<String> task = new PartialFirstFunctionTask(
                    function.getME(),
                    function.getMM(),
                    function.getMD(),
                    function.getMF(),
                    tempVar,
                    i,
                    function.getCyclicBarrier()
            );
            tasks.add(forkJoinPool.submit(task));
        }
        return tasks;
    }

    /**
     * Calculates the second function asynchronously. Returns a list of future tasks.
     * Creates callable instances to calculate function chunks parallel
     * and asynchronously using future tasks and running it.
     *
     * @return list of a FutureTask with a result String message
     */
    public List<ForkJoinTask<String>> calculateSecondFunction() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        double[] tempVar = new double[Function.SIZE];
        List<ForkJoinTask<String>> tasks = new ArrayList<>();
        for (int i = 0; i < Function.NUMBER_OF_THREADS; i++) {
            final RecursiveTask<String> task = new PartialSecondFunctionTask(
                    function.getB(),
                    function.getD(),
                    function.getE(),
                    function.getME(),
                    function.getMM(),
                    tempVar,
                    i,
                    Function.THREAD_PAYLOAD,
                    function.getCyclicBarrier()
            );
            tasks.add(forkJoinPool.submit(task));
        }
        return tasks;
    }
}
