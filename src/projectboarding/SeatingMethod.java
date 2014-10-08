package projectboarding;

import java.util.ArrayList;
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
    
    /**
     * Creates a set of available seats for the row and columns provided.
     * 
     * @param rows set of row numbers.
     * @param columns set of column numbers.
     * @param isPriority boolean whether the seat is a priority seat.
     * @return set of available seats.
     */
    private ArrayList<Seat> createAvailableSeats(int[] rows, int[] columns, boolean isPriority) {
        // Create an arrayList of seats that can be taken
        ArrayList<Seat> availableSeats = new ArrayList<>();
        
        for (int row: rows) {
            for (int column: columns) {
                Seat seat = new Seat(row, column, isPriority);
                availableSeats.add(seat);
            }
        }
        
        return availableSeats;
    }
    
    /**
     * Creates a random order for the set of seats provided.
     * 
     * @param seats set of seats to be randomized.
     * @return a randomized set of seats.
     */
    private ArrayList<Seat> createRandomSeatingOrderFromSeats(ArrayList<Seat> seats) {
        // Create the arrayList containing the seating order
        ArrayList<Seat> seatingOrder = new ArrayList<>();
        
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
    private ArrayList<Seat> calculateRandomSeatingOrderForPrioritySeats() {
        // Create the rows
        int[] rows = new int[this.planeDimension.getNumberOfPriorityRows()];
        for (int row = 0; row < this.planeDimension.getNumberOfPriorityRows(); row++) {
            rows[row] = row;
        }
        
        // Create the columns
        int[] columns = new int[this.planeDimension.getNumberOfPriorityRows()];
        for (int column = 0; column < this.planeDimension.getNumberOfPriorityRows(); column++) {
            columns[column] = column;
        }
        
        return this.createRandomSeatingOrderFromSeats(this.createAvailableSeats(rows, columns, true));
    }
    
    /**
     * Create the seating order for the random seating method.
     * 
     * @return a randomly ordered set of seats.
     */
    private ArrayList<Seat> calculateRandomSeatingOrder() {
        // Get the random order of the priority seats
        ArrayList<Seat> priorityOrderSeats = this.calculateRandomSeatingOrderForPrioritySeats();
        
        // Get the random order of the standard seats
        // Create the rows
        int[] rows = new int[this.planeDimension.getNumberOfRows()];
        for (int row = 0; row < this.planeDimension.getNumberOfRows(); row++) {
            rows[row] = row;
        }
        
        // Create the columns
        int[] columns = new int[this.planeDimension.getNumberOfRows()];
        for (int column = 0; column < this.planeDimension.getNumberOfRows(); column++) {
            columns[column] = column;
        }
        
        ArrayList<Seat> availableSeats = this.createAvailableSeats(rows, columns, false);
        ArrayList<Seat> randomOrder = this.createRandomSeatingOrderFromSeats(availableSeats);
        
        // Join the two arrayLists together
        ArrayList<Seat> seatingOrder = new ArrayList<>();
        seatingOrder.addAll(priorityOrderSeats);
        seatingOrder.addAll(randomOrder);
        
        return seatingOrder;
    }
}