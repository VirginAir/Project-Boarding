package projectboarding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Matthew
 */
public class SeatingMethod {
    public enum DefaultSeatingMethod {
        BACK_TO_FRONT, BLOCK_BOARDING, OUTSIDE_IN_BY_COLUMN, RANDOM, REVERSE_PYRAMID, ROTATING_ZONE
    }
    
    private final DefaultSeatingMethod defaultMethod;
    private final PlaneDimension planeDimension;
    
    /**
     * Initialize the seating method class with a default method.
     * 
     * @param defaultMethod the default seating method.
     * @param planeDimension the dimensions of the plane to order the seats from.
     */
    public SeatingMethod(DefaultSeatingMethod defaultMethod, PlaneDimension planeDimension) {
        this.defaultMethod = defaultMethod;
        this.planeDimension = planeDimension;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Cell> getSeatingOrder() {
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
    
    /**
     * Creates a random order for the set of seats provided.
     * 
     * @param seats set of seats to be randomized.
     * @return a randomized set of seats.
     */
    private ArrayList<Cell> createRandomSeatingOrderFromSeats(ArrayList<Cell> seats) {
        // Create the arrayList containing the seating order
        ArrayList<Cell> seatingOrder = new ArrayList<>();
        
        while (!seats.isEmpty()) {
            Random randomGenerator = new Random();
            int seatNumber = randomGenerator.nextInt(seats.size());
            
            seatingOrder.add(seats.remove(seatNumber));
        }
        
        return seatingOrder;
    }
    
    /**
     * Creates a set of randomly ordered seats for the priority seats in the plane.
     * 
     * @return a randomized set of priority seats.
     */
    private ArrayList<Cell> calculateRandomSeatingOrderForPrioritySeats() {
        return this.createRandomSeatingOrderFromSeats(this.convertArrayListToArray(this.planeDimension.getPrioritySeats()));
    }
    
    private ArrayList<Cell> convertArrayListToArray(Cell[][] array) {
        ArrayList<Cell> seats = new ArrayList<>();
 
        for (Cell[] row: array) {
            seats.addAll(Arrays.asList(row));
        }
        
        return seats;
    }
    
    /**
     * Create the seating order for the random seating method.
     * 
     * @return a randomly ordered set of seats.
     */
    private ArrayList<Cell> calculateRandomSeatingOrder() {
        // Get the random order of the priority seats
        ArrayList<Cell> randomPrioritySeats = this.calculateRandomSeatingOrderForPrioritySeats();
        
        // Get the random order of the normal seats
        ArrayList<Cell> randomNormalSeats = 
                this.createRandomSeatingOrderFromSeats(this.convertArrayListToArray(this.planeDimension.getNormalSeats()));
        
        // Join the two arrayLists together
        ArrayList<Cell> seatingOrder = new ArrayList<>();
        seatingOrder.addAll(randomPrioritySeats);
        seatingOrder.addAll(randomNormalSeats);
        
        return seatingOrder;
    }
}