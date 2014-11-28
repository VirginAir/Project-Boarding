package projectboarding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Timer;
import projectboarding.SeatingMethod.DefaultSeatingMethod;

/**
 *
 * @author Matthew Kempson
 */
public class BoardingController {

    private final PlaneDimension planeDimension;
    
    private BoardingHandler btfBoardingHandler;
    private BoardingHandler bBoardingHandler;
    private BoardingHandler bsBoardingHandler;
    private BoardingHandler oiBoardingHandler;
    private BoardingHandler rBoardingHandler;
    private BoardingHandler rpBoardingHandler;
    private BoardingHandler rzBoardingHandler;
    private BoardingHandler cBoardingHandler;
    
    private Thread[] threads = new Thread[8];
    
    private String results;
    
    private boolean threadsCreated = false;
    private boolean useCustom = false;

    public BoardingController(PlaneDimension planeDimension, boolean useCustom, int[][] customMethod) {
        this.planeDimension = planeDimension;
        
        this.useCustom = useCustom;
        
        this.btfBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.BACK_TO_FRONT);
        this.bBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.BLOCK_BOARDING);
        this.bsBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.BY_SEAT);
        this.oiBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.OUTSIDE_IN);
        this.rBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.RANDOM);
        this.rpBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.REVERSE_PYRAMID);
        this.rzBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.ROTATING_ZONE);
        if(useCustom){
            this.cBoardingHandler = new BoardingHandler(planeDimension, customMethod);
        }
        
        this.threads[0] = new Thread(this.btfBoardingHandler);
        this.threads[1] = new Thread(this.bBoardingHandler);
        this.threads[2] = new Thread(this.bsBoardingHandler);
        this.threads[3] = new Thread(this.oiBoardingHandler);
        this.threads[4] = new Thread(this.rBoardingHandler);
        this.threads[5] = new Thread(this.rpBoardingHandler);
        this.threads[6] = new Thread(this.rzBoardingHandler);
        if(useCustom){
             this.threads[7] = new Thread(this.cBoardingHandler);
        }
        
        threadsCreated = true;
    }
    
    public void startBoarding(DefaultSeatingMethod toTime) {
        
        this.btfBoardingHandler.reset();
        this.bBoardingHandler.reset();
        this.bsBoardingHandler.reset();
        this.oiBoardingHandler.reset();
        this.rBoardingHandler.reset();
        this.rpBoardingHandler.reset();
        this.rzBoardingHandler.reset();
        if(useCustom){
             this.cBoardingHandler.reset();
        }
        
        this.threads[0] = new Thread(this.btfBoardingHandler);
        this.threads[1] = new Thread(this.bBoardingHandler);
        this.threads[2] = new Thread(this.bsBoardingHandler);
        this.threads[3] = new Thread(this.oiBoardingHandler);
        this.threads[4] = new Thread(this.rBoardingHandler);
        this.threads[5] = new Thread(this.rpBoardingHandler);
        this.threads[6] = new Thread(this.rzBoardingHandler);
        if(useCustom){
            this.threads[7] = new Thread(this.cBoardingHandler);
        }
        
        if(toTime.equals(DefaultSeatingMethod.BACK_TO_FRONT)){
            this.btfBoardingHandler.setWithTimer(true);
        } else if(toTime.equals(DefaultSeatingMethod.BLOCK_BOARDING)){
            this.bBoardingHandler.setWithTimer(true);
        } else if(toTime.equals(DefaultSeatingMethod.BY_SEAT)){
            this.bsBoardingHandler.setWithTimer(true);
        } else if(toTime.equals(DefaultSeatingMethod.OUTSIDE_IN)){
            this.oiBoardingHandler.setWithTimer(true);
        } else if(toTime.equals(DefaultSeatingMethod.RANDOM)){
            this.rBoardingHandler.setWithTimer(true);
        } else if(toTime.equals(DefaultSeatingMethod.REVERSE_PYRAMID)){
            this.rpBoardingHandler.setWithTimer(true);
        } else if(toTime.equals(DefaultSeatingMethod.ROTATING_ZONE)){
            this.rzBoardingHandler.setWithTimer(true);
        } else if(toTime.equals(DefaultSeatingMethod.CUSTOM)){
            this.cBoardingHandler.setWithTimer(true);
        }
        
        for (int i = 0; i < this.threads.length; i++) {
            if(!(i == 7 && !useCustom)){
                System.out.println(threads[i].getState().toString());
            }
        }
        
        for (int i = 0; i < this.threads.length; i++) {
            if(!(i == 7 && !useCustom)){
                System.out.println(threads[i].isAlive());
                if(!threads[i].isAlive()){
                    threads[i].start();
                }
            }
        }
        
    }
    
    public void stopBoarding() {
        this.btfBoardingHandler.stopBoarding();
        this.bBoardingHandler.stopBoarding();
        this.bsBoardingHandler.stopBoarding();
        this.oiBoardingHandler.stopBoarding();
        this.rBoardingHandler.stopBoarding();
        this.rpBoardingHandler.stopBoarding();
        this.rzBoardingHandler.stopBoarding();
        if(useCustom){
            this.cBoardingHandler.stopBoarding();
        }        
    }
    
    public Cell[][] getSeatVisualisationForMethod(DefaultSeatingMethod method) {
        switch (method) {
            case BACK_TO_FRONT:
                return this.btfBoardingHandler.getSeatVisualisation();
            case BLOCK_BOARDING:
                return this.bBoardingHandler.getSeatVisualisation();
            case BY_SEAT:
                return this.bsBoardingHandler.getSeatVisualisation();
            case OUTSIDE_IN:
                return this.oiBoardingHandler.getSeatVisualisation();
            case RANDOM:
                return this.rBoardingHandler.getSeatVisualisation();
            case REVERSE_PYRAMID:
                return this.rpBoardingHandler.getSeatVisualisation();
            case ROTATING_ZONE:
                return this.rzBoardingHandler.getSeatVisualisation();
            case CUSTOM:
                return this.cBoardingHandler.getSeatVisualisation();
            default:
                return null;
        }
    }
    
    public ArrayList<Passenger> getPassengersForMethod(DefaultSeatingMethod method) {
        switch (method) {
            case BACK_TO_FRONT:
                return this.btfBoardingHandler.getPassengers();
            case BLOCK_BOARDING:
                return this.bBoardingHandler.getPassengers();
            case BY_SEAT:
                return this.bsBoardingHandler.getPassengers();
            case OUTSIDE_IN:
                return this.oiBoardingHandler.getPassengers();
            case RANDOM:
                return this.rBoardingHandler.getPassengers();
            case REVERSE_PYRAMID:
                return this.rpBoardingHandler.getPassengers();
            case ROTATING_ZONE:
                return this.rzBoardingHandler.getPassengers();
            case CUSTOM:
                return this.cBoardingHandler.getPassengers();
            default:
                return null;
        }
    }
    
    public boolean checkComplete(){
        if(!threadsCreated
                ||!this.btfBoardingHandler.isHasCompleted() 
                || !this.bBoardingHandler.isHasCompleted() 
                || !this.bsBoardingHandler.isHasCompleted()
                || !this.oiBoardingHandler.isHasCompleted()
                || !this.rBoardingHandler.isHasCompleted()
                || !this.rpBoardingHandler.isHasCompleted()
                || !this.rzBoardingHandler.isHasCompleted()
                || (useCustom && !this.cBoardingHandler.isHasCompleted())){
            return false;
        }
        
        DecimalFormat formatter = new DecimalFormat("##");
        
        StringBuilder sb = new StringBuilder();
        sb.append("Method\t\tmm:ss\n");
        sb.append(DefaultSeatingMethod.BACK_TO_FRONT.toString()).append("\t\t").append(String.format("%02d", this.btfBoardingHandler.getTimeMin())).append(":").append(String.format("%02d", this.btfBoardingHandler.getTimeSec())).append("\n");
        sb.append(DefaultSeatingMethod.BLOCK_BOARDING.toString()).append("\t\t").append(String.format("%02d", this.bBoardingHandler.getTimeMin())).append(":").append(String.format("%02d", this.bBoardingHandler.getTimeSec())).append("\n");
        sb.append(DefaultSeatingMethod.BY_SEAT.toString()).append("\t\t").append(String.format("%02d", this.bsBoardingHandler.getTimeMin())).append(":").append(String.format("%02d", this.bsBoardingHandler.getTimeSec())).append("\n");
        sb.append(DefaultSeatingMethod.OUTSIDE_IN.toString()).append("\t\t").append(String.format("%02d", this.oiBoardingHandler.getTimeMin())).append(":").append(String.format("%02d", this.oiBoardingHandler.getTimeSec())).append("\n");
        sb.append(DefaultSeatingMethod.RANDOM.toString()).append("\t\t").append(String.format("%02d", this.rBoardingHandler.getTimeMin())).append(":").append(String.format("%02d", this.rBoardingHandler.getTimeSec())).append("\n");
        sb.append(DefaultSeatingMethod.REVERSE_PYRAMID.toString()).append("\t").append(String.format("%02d", this.rpBoardingHandler.getTimeMin())).append(":").append(String.format("%02d", this.rpBoardingHandler.getTimeSec())).append("\n");
        sb.append(DefaultSeatingMethod.ROTATING_ZONE.toString()).append("\t\t").append(String.format("%02d", this.rzBoardingHandler.getTimeMin())).append(":").append(String.format("%02d", this.rzBoardingHandler.getTimeSec())).append("\n");
        if(useCustom){
            sb.append(DefaultSeatingMethod.CUSTOM.toString()).append("\t\t").append(String.format("%02d", this.cBoardingHandler.getTimeMin())).append(":").append(String.format("%02d", this.cBoardingHandler.getTimeSec())).append("\n");
        }
        
        results = sb.toString();
        
        return true;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
    
    
    
    public PlaneDimension getPlaneDimension() {
        return this.planeDimension;
    }
}
