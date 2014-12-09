package projectboarding;

import java.util.ArrayList;

/**
 *
 * @author Ben Cook
 */
public class SinResult {
    String methodName = "";
    int mins = 0;
    int secs = 0;
    int totalTimeInSecs = 0;
    
    
    public SinResult(String name, int mins, int secs, int totalSecs){
        methodName = name;
        this.mins = mins;
        this.secs = secs;
        this.totalTimeInSecs = totalSecs;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getMins() {
        return mins;
    }

    public void setMins(int mins) {
        this.mins = mins;
    }

    public int getSecs() {
        return secs;
    }

    public void setSecs(int secs) {
        this.secs = secs;
    }

    public int getTotalTimeInSecs() {
        return totalTimeInSecs;
    }

    public void setTotalTimeInSecs(int totalTimeInSecs) {
        this.totalTimeInSecs = totalTimeInSecs;
    }
    
    public void addTime(int timeToAdd){
        this.totalTimeInSecs += timeToAdd;
    }
    
    public void average(int iters){
        this.totalTimeInSecs /= iters;
    }
    
    public void calcMinsSecs(){
        mins = (int) Math.floor(this.totalTimeInSecs/60);
        secs = this.totalTimeInSecs%60;
    }
    
}
