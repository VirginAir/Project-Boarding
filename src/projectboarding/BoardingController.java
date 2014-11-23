package projectboarding;

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

    public BoardingController(PlaneDimension planeDimension, int[][] customMethod) {
        this.planeDimension = planeDimension;
        
        this.btfBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.BACK_TO_FRONT);
        this.bBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.BLOCK_BOARDING);
        this.bsBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.BY_SEAT);
        this.oiBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.OUTSIDE_IN);
        this.rBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.RANDOM);
        this.rpBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.REVERSE_PYRAMID);
        this.rzBoardingHandler = new BoardingHandler(planeDimension, DefaultSeatingMethod.ROTATING_ZONE);
        this.cBoardingHandler = new BoardingHandler(planeDimension, customMethod);
        
        this.threads[0] = new Thread(this.btfBoardingHandler);
        this.threads[1] = new Thread(this.bBoardingHandler);
        this.threads[2] = new Thread(this.bsBoardingHandler);
        this.threads[3] = new Thread(this.oiBoardingHandler);
        this.threads[4] = new Thread(this.rBoardingHandler);
        this.threads[5] = new Thread(this.rpBoardingHandler);
        this.threads[6] = new Thread(this.rzBoardingHandler);
        this.threads[7] = new Thread(this.cBoardingHandler);
    }
    
    public void startBoarding() {
        this.bBoardingHandler.setWithTimer(true);
        
        for (Thread thread: this.threads) {
            thread.start();
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
        this.cBoardingHandler.stopBoarding();
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
    
    public PlaneDimension getPlaneDimension() {
        return this.planeDimension;
    }
}
