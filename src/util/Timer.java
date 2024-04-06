package util;

/**
 * A stopwatch abstraction that measures the time between calls to the startCountdown and endCountdown functions.
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
     * Ends the countdown and calculates the execution time.
     */
    public void endCountDown() {
        long endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
    }

    /**
     * Prints the last recorded execution time in milliseconds.
     */
    public void printResult() {
        System.out.println("Program execution time: " + executionTime + " ms");
    }
}
