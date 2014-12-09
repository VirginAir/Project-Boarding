package projectboarding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Ben Cook
 */
public class Results {
    ArrayList<SinResult> results = new ArrayList<>();
    int iters = 1;

    public void addMethod(String name, int mins, int secs, int totalSecs){
        results.add(new SinResult(name, mins, secs, totalSecs));
    }
    
    public void sort(){
        Collections.sort(results, new Comparator<SinResult>() {
            public int compare(SinResult c1, SinResult c2) {
                if (c1.totalTimeInSecs > c2.totalTimeInSecs) return 1;
                if (c1.totalTimeInSecs < c2.totalTimeInSecs) return -1;
                return 0;
            }});
    }
    
    public ArrayList<SinResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<SinResult> results) {
        this.results = results;
    }

    public int getIters() {
        return iters;
    }

    public void setIters(int iters) {
        this.iters = iters;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Run for ").append(iters).append(" iterations.\n\n");
        sb.append("Method\t\tmm:ss\n");
        for(SinResult sr : results){
            sb.append(sr.getMethodName()).append("\t\t").append(String.format("%02d", sr.getMins())).append(":").append(String.format("%02d", sr.getSecs())).append("\n");
        }
        sb.append("\n\n").append("Suggested method: ").append(results.get(0).getMethodName());
        // Set the results variable to the string
        return sb.toString();
    }
    
    public static Results averageResults(ArrayList<Results> res){
        Results outResults = res.get(0);
        
        for(int i = 1; i < res.size(); i++){
            Results tempRes = res.get(i);
            for(int j = 0; j < tempRes.getResults().size(); j++){
                for(int k = 0; k < outResults.getResults().size(); k++){
                    if(tempRes.getResults().get(j).getMethodName().equals(outResults.getResults().get(k).getMethodName())){
                        outResults.getResults().get(k).addTime(tempRes.getResults().get(j).getTotalTimeInSecs());
                    }
                }
            }
        }
        
        for(SinResult sr : outResults.getResults()){
            sr.average(res.size());
            sr.calcMinsSecs();
        }
        
        outResults.setIters(res.size());
        
        return outResults;
    }
}
