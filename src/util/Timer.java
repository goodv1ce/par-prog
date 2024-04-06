package util;

/**
 * A stopwatch abstraction that measures the time between calls to the startCountdown and endCountdown functions
 */
public class Timer {
    private long startTime;
    private long executionTime;

    /**
     * Starts the countdown
     */
    public void startCountdown() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Ends the countdown
     */
    public void endCountDown() {
        long endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
    }

    /**
     * Prints in the console last result
     */
    public void printResult() {
        System.out.println("Program execution time: " + executionTime + " ms");
    }
}
