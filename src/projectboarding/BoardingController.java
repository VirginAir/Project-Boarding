package projectboarding;

import java.util.ArrayList;
import projectboarding.SeatingMethod.DefaultSeatingMethod;

/**
 *
 * @author Matthew Kempson
 */
public class BoardingController {

    /* The plane dimension */
    private final PlaneDimension planeDimension;
    
    /* The boarding handlers for each of the different methods */
    private final BoardingHandler btfBoardingHandler;
    private final BoardingHandler bBoardingHandler;
    private final BoardingHandler bsBoardingHandler;
    private final BoardingHandler oiBoardingHandler;
    private final BoardingHandler rBoardingHandler;
    private final BoardingHandler rpBoardingHandler;
    private final BoardingHandler rzBoardingHandler;
    private BoardingHandler cBoardingHandler;
    
    /* Container for all of the threads */
    private final Thread[] threads = new Thread[8];
    
    /* The final results string */
    //private String results;
    
    /* Boolean values to determin certain continuation rules */
    private boolean threadsCreated = false;
    private boolean useCustom = false;

    /**
     * Create the boarding controller for the given run of results
     * 
     * @param planeDimension The plane dimension created by the user
     * @param customMethod The custom method created by the user, can be null
     */
    public BoardingController(PlaneDimension planeDimension, boolean useCustom, int[][] customMethod) {
        this.planeDimension = planeDimension;
        
        // Set the boolean value
        this.useCustom = useCustom;

        // Create the boarding handlers
        this.btfBoardingHandler = new BoardingHandler(new PlaneDimension(planeDimension), DefaultSeatingMethod.BACK_TO_FRONT);
        this.bBoardingHandler = new BoardingHandler(new PlaneDimension(planeDimension), DefaultSeatingMethod.BLOCK_BOARDING);
        this.bsBoardingHandler = new BoardingHandler(new PlaneDimension(planeDimension), DefaultSeatingMethod.BY_SEAT);
        this.oiBoardingHandler = new BoardingHandler(new PlaneDimension(planeDimension), DefaultSeatingMethod.OUTSIDE_IN);
        this.rBoardingHandler = new BoardingHandler(new PlaneDimension(planeDimension), DefaultSeatingMethod.RANDOM);
        this.rpBoardingHandler = new BoardingHandler(new PlaneDimension(planeDimension), DefaultSeatingMethod.REVERSE_PYRAMID);
        this.rzBoardingHandler = new BoardingHandler(new PlaneDimension(planeDimension), DefaultSeatingMethod.ROTATING_ZONE);
        if(useCustom){
            this.cBoardingHandler = new BoardingHandler(new PlaneDimension(planeDimension), customMethod);
        }
        
        this.resetBoardingHandlers();
        
        // Let the program know the threads have been created
        threadsCreated = true;
    }
    
    /**
     * Start the boarding process for all of the handlers
     * @param toTime The method the user has selected to view on screen
     */
    public void startBoarding(DefaultSeatingMethod toTime) {
        this.resetBoardingHandlers();
        
        // Set the handler which is on a timer
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
        
        // DELETE THIS 
//        for (int i = 0; i < this.threads.length; i++) {
//            if(!(i == 7 && !useCustom)){
//                System.out.println(threads[i].getState().toString());
//            }
//        }
        
        // Start all of the the threads simultaniously
        for (int i = 0; i < this.threads.length; i++) {
            if(!(i == 7 && !useCustom)&&!threads[i].isAlive()){
//                System.out.println(threads[i].isAlive());
                //if(!threads[i].isAlive()){
                    threads[i].start();
                //}
            }
        }
    }
    
    private void resetBoardingHandlers() {
        // Reset all of the simulations
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
        
        /* Recreate the threads. Has to be done because when 
           a thread teminated in Java you are unable to restart it */
        this.threads[0] = new Thread(this.btfBoardingHandler);
        this.threads[0].setName("Back to Front");
        this.threads[1] = new Thread(this.bBoardingHandler);
        this.threads[1].setName("Block boarding");
        this.threads[2] = new Thread(this.bsBoardingHandler);
        this.threads[2].setName("By Seat");
        this.threads[3] = new Thread(this.oiBoardingHandler);
        this.threads[3].setName("Outside In");
        this.threads[4] = new Thread(this.rBoardingHandler);
        this.threads[4].setName("Random");
        this.threads[5] = new Thread(this.rpBoardingHandler);
        this.threads[5].setName("Reverse Pyramid");
        this.threads[6] = new Thread(this.rzBoardingHandler);
        this.threads[6].setName("Rotating Zone");
        if(useCustom){
            this.threads[7] = new Thread(this.cBoardingHandler);
            this.threads[7].setName("Custom");
        }
        
        // Reset the timer boolean
        this.btfBoardingHandler.setWithTimer(false);
        this.bBoardingHandler.setWithTimer(false);
        this.bsBoardingHandler.setWithTimer(false);
        this.oiBoardingHandler.setWithTimer(false);
        this.rBoardingHandler.setWithTimer(false);
        this.rpBoardingHandler.setWithTimer(false);
        this.rzBoardingHandler.setWithTimer(false);
        if(useCustom){
             this.cBoardingHandler.setWithTimer(false);
        }
    }
    
    /**
     * Stop the boarding process for all of the handlers
     */
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
    
    /**
     * Get the seat visualisation for use in the UI
     * @param method The method for the visualisation
     * @return A 2D Cell array containing the seating visualisation
     */
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
    
    /**
     * Get the passengers for the given seating method
     * @param method The seating method
     * @return The passengers list for the given seating method
     */
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
    
    /**
     * Check if the boarding handlers have completed the boarding process
     * @return A boolean, true = complete
     */
    public boolean checkComplete(){
        // Check that the threads have completed
        if(!threadsCreated
                ||!this.btfBoardingHandler.isHasCompleted() 
                || !this.bBoardingHandler.isHasCompleted() 
                || !this.bsBoardingHandler.isHasCompleted()
                || !this.oiBoardingHandler.isHasCompleted()
                || !this.rBoardingHandler.isHasCompleted()
                || !this.rpBoardingHandler.isHasCompleted()
                || !this.rzBoardingHandler.isHasCompleted()
                || useCustom && !this.cBoardingHandler.isHasCompleted()){
            return false;
        }
        
        // Create a formatter to represent the 
        //DecimalFormat formatter = new DecimalFormat("##");
        
        // String build the output results of the program
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
        
        // Set the results variable to the string
        //results = sb.toString();
        
        return true;
    }

    /**
     * Get the results of the plane boarding process
     * @return A Results object containing the results
     */
    public Results getResults() {
        Results res = new Results();
        res.addMethod(DefaultSeatingMethod.BACK_TO_FRONT.toString(), this.btfBoardingHandler.getTimeMin(), this.btfBoardingHandler.getTimeSec(), this.btfBoardingHandler.getTotalTicks().intValue());
        res.addMethod(DefaultSeatingMethod.BLOCK_BOARDING.toString(), this.bBoardingHandler.getTimeMin(), this.bBoardingHandler.getTimeSec(), this.bBoardingHandler.getTotalTicks().intValue());
        res.addMethod(DefaultSeatingMethod.BY_SEAT.toString(), this.bsBoardingHandler.getTimeMin(), this.bsBoardingHandler.getTimeSec(), this.bsBoardingHandler.getTotalTicks().intValue());
        res.addMethod(DefaultSeatingMethod.OUTSIDE_IN.toString(), this.oiBoardingHandler.getTimeMin(), this.oiBoardingHandler.getTimeSec(), this.oiBoardingHandler.getTotalTicks().intValue());
        res.addMethod(DefaultSeatingMethod.RANDOM.toString(), this.rBoardingHandler.getTimeMin(), this.rBoardingHandler.getTimeSec(), this.rBoardingHandler.getTotalTicks().intValue());
        res.addMethod(DefaultSeatingMethod.REVERSE_PYRAMID.toString(), this.rpBoardingHandler.getTimeMin(), this.rpBoardingHandler.getTimeSec(), this.rpBoardingHandler.getTotalTicks().intValue());
        res.addMethod(DefaultSeatingMethod.ROTATING_ZONE.toString(), this.rzBoardingHandler.getTimeMin(), this.rzBoardingHandler.getTimeSec(), this.rzBoardingHandler.getTotalTicks().intValue());
        if(useCustom){
            res.addMethod(DefaultSeatingMethod.CUSTOM.toString(), this.cBoardingHandler.getTimeMin(), this.cBoardingHandler.getTimeSec(), this.cBoardingHandler.getTotalTicks().intValue());
        }
        
        res.sort();
        
        return res;
    }

    /**
     * Set the results of the plane boarding process
     * @param results The results string to set as
     */
//    public void setResults(String results) {
//        this.results = results;
//    }

    /**
     * Get the plane dimension for this current boarding controller
     * @return The plane dimension
     */
    public PlaneDimension getPlaneDimension() {
        return this.planeDimension;
    }
}
