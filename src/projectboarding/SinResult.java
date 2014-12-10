package projectboarding;

/**
 *
 * @author Ben Cook
 */
public class SinResult {

    String methodName = "";
    int mins = 0;
    int secs = 0;
    int totalTimeInSecs = 0;

    /**
     * Create a new sin result
     *
     * @param name the method name
     * @param mins the minutes
     * @param secs the seconds
     * @param totalSecs the total seconds
     */
    public SinResult(String name, int mins, int secs, int totalSecs) {
        methodName = name;
        this.mins = mins;
        this.secs = secs;
        this.totalTimeInSecs = totalSecs;
    }

    /**
     * Get the method name
     *
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Set the method name
     *
     * @param methodName the method name
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Get the minutes
     *
     * @return the minutes
     */
    public int getMins() {
        return mins;
    }

    /**
     * Set the minutes
     *
     * @param mins the minutes
     */
    public void setMins(int mins) {
        this.mins = mins;
    }

    /**
     * Get the seconds
     *
     * @return the seconds
     */
    public int getSecs() {
        return secs;
    }

    /**
     * Set the seconds
     *
     * @param secs the seconds
     */
    public void setSecs(int secs) {
        this.secs = secs;
    }

    /**
     * Get the total time in seconds
     *
     * @return the total time in seconds
     */
    public int getTotalTimeInSecs() {
        return totalTimeInSecs;
    }

    /**
     * Set the total time in seconds
     *
     * @param totalTimeInSecs the total time in seconds
     */
    public void setTotalTimeInSecs(int totalTimeInSecs) {
        this.totalTimeInSecs = totalTimeInSecs;
    }

    /**
     * Add a time to the total time in seconds
     *
     * @param timeToAdd the time to add
     */
    public void addTime(int timeToAdd) {
        this.totalTimeInSecs += timeToAdd;
    }

    /**
     * Average time in seconds
     *
     * @param iters the number of iterations to divide by
     */
    public void average(int iters) {
        this.totalTimeInSecs /= iters;
    }

    /**
     * Calculate the minutes and the seconds
     */
    public void calcMinsSecs() {
        mins = (int) Math.floor(this.totalTimeInSecs / 60);
        secs = this.totalTimeInSecs % 60;
    }
}
