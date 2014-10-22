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
        BACK_TO_FRONT, BLOCK_BOARDING, BY_SEAT, OUTSIDE_IN, RANDOM, REVERSE_PYRAMID, ROTATING_ZONE
    }
    
    // back-to-front - block boarding from back to front
    // block-boarding - block boarding with outside in ordering per block
    // by-seat - non-randmised, outside in from front to back
    // outside-in - outside in without blocks
    // random - randmise all the seats
    // rotating-zone - block boarding with alternating back/front/back/front/etc...
    // reverse-pyramid - back to front with outside-in
    
    private final DefaultSeatingMethod defaultMethod;
    private final PlaneDimension planeDimension;
    
    // Can create this immediatly since it wont change for any of the seating methods
    private final ArrayList<Cell> randomisedPrioritySeats;
    
    /**
     * Initialize the seating method class with a default method.
     * 
     * @param defaultMethod the default seating method.
     * @param planeDimension the dimensions of the plane to order the seats from.
     */
    public SeatingMethod(DefaultSeatingMethod defaultMethod, PlaneDimension planeDimension) {
        this.defaultMethod = defaultMethod;
        this.planeDimension = planeDimension;
        
        this.randomisedPrioritySeats = this.calculateRandomSeatingOrderForPrioritySeats();
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Cell> getSeatingOrder() {
        if (defaultMethod != null) {
            switch (this.defaultMethod) {
                case BACK_TO_FRONT:
                    return this.calculateBackToFrontSeatingOrder();
                case OUTSIDE_IN:
                    return this.calculateOutsideInSeatingOrder();
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
        return this.createRandomSeatingOrderFromSeats(this.convertArrayToArrayList(this.planeDimension.getPrioritySeats()));
    }
    
    /**
     * Converts a given 2D array into an arrayList.
     * 
     * @param array the 2D array to be transformed
     * @return an arrayList containing the 2D array
     */
    private ArrayList<Cell> convertArrayToArrayList(Cell[][] array) {
        ArrayList<Cell> seats = new ArrayList<>();
 
        for (Cell[] row: array) {
            seats.addAll(Arrays.asList(row));
        }
        
        return seats;
    }
    
    private ArrayList<Cell> createFinalOrder(ArrayList<Cell> normalSeats) {
        // Join the two arrayLists together
        ArrayList<Cell> seatingOrder = new ArrayList<>();
        seatingOrder.addAll(this.randomisedPrioritySeats);
        seatingOrder.addAll(normalSeats);
        
        return seatingOrder;
    }
    
    /**
     * Create the seating order for the random seating method.
     * 
     * @return a randomly ordered set of seats.
     */
    private ArrayList<Cell> calculateRandomSeatingOrder() {        
        // Get the random order of the normal seats
        ArrayList<Cell> randomNormalSeats = 
                this.createRandomSeatingOrderFromSeats(this.convertArrayToArrayList(this.planeDimension.getNormalSeats()));
        
        // Return the results
        return this.createFinalOrder(randomNormalSeats);
    }
    
    private ArrayList<Cell> calculateBackToFrontSeatingOrder() {
        int numberOfRowsPerBlock = 1;
        
        // Calculate how many blocks to split the plane into
        int numberOfRows = this.planeDimension.getNumberOfNormalRows();
        int numberOfBlocks = numberOfRows / numberOfRowsPerBlock;
        float remainder = numberOfRows % numberOfRowsPerBlock;
        
        // Get the normal seats for the plane and get the last index in the array
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        int currentRowIndex = normalSeats.length - 1;
        
        // Create a container to hold the ordered seats
        ArrayList<Cell> backToFrontRandomisedSeats = new ArrayList<>();
        
        // Loop over each block the seats have been split into
        for (int x = 0; x < numberOfBlocks; x++) {
            // Calculate the number of rows to get for this block
            int numberOfRowsToGet = numberOfRowsPerBlock;
            if (remainder > 0) {
                numberOfRowsToGet++;
                remainder--;
            }
            
            // Create the containder to hold the seats for this block
            ArrayList<Cell> blockSeats = new ArrayList<>();
            
            // Loop over the total number of rows to get
            for (int y = 0; y < numberOfRowsToGet; y++) {
                // Add all of the seats from that row into the array
                blockSeats.addAll(Arrays.asList(normalSeats[currentRowIndex--]));
            }
            
            // Randomise the seats which have been collected
            ArrayList<Cell> randomisedBlockCells = this.createRandomSeatingOrderFromSeats(blockSeats);
            backToFrontRandomisedSeats.addAll(randomisedBlockCells);
        }
        
        return this.createFinalOrder(backToFrontRandomisedSeats);
    }
    
    private ArrayList<Cell> calculateOutsideInSeatingOrder() {
        // Get the normal seats and place into an arraylist
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        ArrayList<Cell> normalSeatsList = this.convertArrayToArrayList(normalSeats);
        
        // Get the number of columns the plane holds
        int numberOfColumns = this.planeDimension.getNumberOfColumns();
        
        // Create the container to hold the column lists
        ArrayList<ArrayList<Cell>> columnNormalSeats = new ArrayList<>();
        
        // Create the array list containers for each column
        for (int x = 0; x < numberOfColumns; x++) {
            ArrayList<Cell> list = new ArrayList<>();
            columnNormalSeats.add(list);
        }
        
        // Place each of the cells into the correct list
        for (Cell cell: normalSeatsList) {
            int cellColumn = cell.getCellColumn();
            columnNormalSeats.get(cellColumn).add(cell);
        }
        
        ArrayList<ArrayList<Cell>> randomisedColumnOrder = new ArrayList<>();
        
        // Randomise each of the lists in the container
        for (int x = 0; x < numberOfColumns; x++) {
            randomisedColumnOrder.add(this.createRandomSeatingOrderFromSeats(columnNormalSeats.remove(0)));
        }
        
        // Create the final seating order
        ArrayList<Cell> finalNormalSeatOrder = new ArrayList<>();
        while (!randomisedColumnOrder.isEmpty()) {
            finalNormalSeatOrder.addAll(randomisedColumnOrder.remove(0));
            if (!randomisedColumnOrder.isEmpty()) {
                finalNormalSeatOrder.addAll(randomisedColumnOrder.remove(randomisedColumnOrder.size() - 1));
            }
        }
        
        return this.createFinalOrder(finalNormalSeatOrder);
    }

    @Override
    public String toString() {
        String seatingMethod; 
        switch(this.defaultMethod){
            case BACK_TO_FRONT:
                seatingMethod = "back-to-front";
                break;
            case OUTSIDE_IN:
                seatingMethod = "outside-in";
                break;
            case RANDOM:
                seatingMethod = "random";
                break;
            default:
                seatingMethod = "unrecognised method";
                break;
        }
        return seatingMethod;
    }
    
    
}