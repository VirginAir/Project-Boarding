package projectboarding;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Matthew
 */
public class SeatingMethod {
    public enum DefaultSeatingMethod {
        RANDOM, OUTSIDE_IN_BY_COLUMN, BLOCK_BOARDING, REVERSE_PYRAMID, ROTATING_ZONE
    }
    
    private final DefaultSeatingMethod defaultMethod;
    private final PlaneDimension planeDimension;
    
    public SeatingMethod(DefaultSeatingMethod defaultMethod, PlaneDimension planeDimension) {
        this.defaultMethod = defaultMethod;
        this.planeDimension = planeDimension;
    }
    
    public ArrayList<Seat> getSeatingOrder() {
        if (defaultMethod != null) {
            switch (this.defaultMethod) {
                case RANDOM:
                    return this.calculateRandomSeatingOrder();
                default:
                    break;
            }
        }
        
        return null;
    }
    
    private ArrayList<Seat> calculateRandomSeatingOrder() {
        // Create an arrayList of seats that can be taken
        ArrayList<Seat> availableSeats = new ArrayList<>();
        
        for (int rowNumber = 0; rowNumber < this.planeDimension.getNumberOfRows(); rowNumber++) {
            for (int columnNumber = 0; columnNumber < this.planeDimension.getNumberOfColumns(); columnNumber++) {
                Seat seat = new Seat(rowNumber, columnNumber);
                availableSeats.add(seat);
            }
        }
        
        // Create the arrayList containing the seating order
        ArrayList<Seat> seatingOrder = new ArrayList<>();
        
        while (!availableSeats.isEmpty()) {
            Random randomGenerator = new Random();
            int seatNumber = randomGenerator.nextInt(availableSeats.size());
            
            seatingOrder.add(availableSeats.remove(seatNumber));
        }
        
        return seatingOrder;
    }
}