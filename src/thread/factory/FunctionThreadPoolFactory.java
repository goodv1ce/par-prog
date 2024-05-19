package thread.factory;

import thread.callable.PartialFirstFunctionTask;
import thread.callable.PartialSecondFunctionTask;
import util.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * The factory for operating objects that implements Callable and used to calculate function parts.
 */
public class FunctionThreadPoolFactory {
    private final Function function;

    public FunctionThreadPoolFactory(Function function) {
        this.function = function;
    }

    /**
     * Calculates the first function asynchronously. Returns a list of future tasks.
     * Creates callable instances to calculate function chunks parallel
     * and asynchronously using future tasks and running it.
     *
     * @return list of a FutureTask with a result String message
     */
    public List<FutureTask<String>> calculateFirstFunction() {
        ExecutorService executorService = Executors.newFixedThreadPool(Function.NUMBER_OF_THREADS);
        double[][] tempVar = new double[Function.SIZE][Function.SIZE];
        List<FutureTask<String>> futureTasks = new ArrayList<>();
        for (int i = 0; i < Function.NUMBER_OF_THREADS; i++) {
            final FutureTask<String> futureTask = new FutureTask<>(
                    new PartialFirstFunctionTask(
                            function.getME(),
                            function.getMM(),
                            function.getMD(),
                            function.getMF(),
                            tempVar,
                            i,
                            function.getCyclicBarrier()
                    )
            );
            futureTasks.add(futureTask);
            executorService.execute(futureTask);
        }
        return futureTasks;
    }

    /**
     * Calculates the second function asynchronously. Returns a list of future tasks.
     * Creates callable instances to calculate function chunks parallel
     * and asynchronously using future tasks and running it.
     *
     * @return list of a FutureTask with a result String message
     */
    public List<FutureTask<String>> calculateSecondFunction() {
        ExecutorService executorService = Executors.newFixedThreadPool(Function.NUMBER_OF_THREADS);
        double[] tempVar = new double[Function.SIZE];
        List<FutureTask<String>> futureTasks = new ArrayList<>();
        for (int i = 0; i < Function.NUMBER_OF_THREADS; i++) {
            final FutureTask<String> futureTask = new FutureTask<>(
                    new PartialSecondFunctionTask(
                            function.getB(),
                            function.getD(),
                            function.getE(),
                            function.getME(),
                            function.getMM(),
                            tempVar,
                            i,
                            Function.THREAD_PAYLOAD,
                            function.getCyclicBarrier()
                    )
            );
            futureTasks.add(futureTask);
            executorService.execute(futureTask);
        }
        return futureTasks;
    }
}
