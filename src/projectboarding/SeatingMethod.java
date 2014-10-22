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
    
    // back-to-front - block boarding, random ordering, from back to front
    // block-boarding - block boarding, outside in ordering
    // by-seat - non-randmised, outside in, from front to back
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
                case BLOCK_BOARDING:
                    return this.calculateBlockBoardingSeatingOrder();
                case OUTSIDE_IN:
                    return this.calculateOutsideInSeatingOrder();
                case RANDOM:
                    return this.calculateRandomSeatingOrder();
                case ROTATING_ZONE:
                    return this.calculateRotatingZoneSeatingOrder();
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
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        ArrayList<Cell> list = this.convertArrayToArrayList(normalSeats);
        ArrayList<Cell> randomNormalSeats = this.createRandomSeatingOrderFromSeats(list);
        
        // Return the results
        return this.createFinalOrder(randomNormalSeats);
    }
    
    private ArrayList<Cell> calculateBackToFrontSeatingOrder() {
        ArrayList<ArrayList<Cell>> blocks = this.splitNormalRowsIntoBlocks();
        
        ArrayList<ArrayList<Cell>> randomisedBlocks = new ArrayList<>();
        for (ArrayList<Cell> block: blocks) {
            randomisedBlocks.add(this.createRandomSeatingOrderFromSeats(block));
        }
        
        return this.createFinalOrder(this.joinBlocksTogetherBackToFront(randomisedBlocks));
    }
    
    private ArrayList<Cell> calculateOutsideInSeatingOrder() {
        // Get the normal seats and place into an arraylist
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        ArrayList<Cell> normalSeatsList = this.convertArrayToArrayList(normalSeats);
        
        ArrayList<Cell> outsideInOrder = this.createOutsideInOrderForBlock(normalSeatsList);
        
        return this.createFinalOrder(outsideInOrder);
    }
        
    private ArrayList<Cell> calculateBlockBoardingSeatingOrder() {
        ArrayList<ArrayList<Cell>> blocks = this.splitNormalRowsIntoBlocks();
        
        ArrayList<ArrayList<Cell>> orderedBlocks = new ArrayList<>();
        for (ArrayList<Cell> block: blocks) {
            orderedBlocks.add(this.createOutsideInOrderForBlock(block));
        }
        
        return this.createFinalOrder(this.joinBlocksTogetherBackToFront(orderedBlocks));
    }
    
    private ArrayList<Cell> calculateRotatingZoneSeatingOrder() {
        ArrayList<ArrayList<Cell>> blocks = this.splitNormalRowsIntoBlocks();
        
        ArrayList<ArrayList<Cell>> randomisedBlocks = new ArrayList<>();
        for (ArrayList<Cell> block: blocks) {
            randomisedBlocks.add(this.createRandomSeatingOrderFromSeats(block));
        }
        
        // Join the blocks together from in rotating order
        ArrayList<Cell> jointBlocks = new ArrayList<>();
        while (!randomisedBlocks.isEmpty()) {
            jointBlocks.addAll(randomisedBlocks.remove(randomisedBlocks.size() - 1));
            if (!randomisedBlocks.isEmpty()) {
                jointBlocks.addAll(randomisedBlocks.remove(0));
            }
        }
        
        return this.createFinalOrder(jointBlocks);
    }
    
    private ArrayList<ArrayList<Cell>> splitNormalRowsIntoBlocks() {
        // Calculate how many blocks to split the plane into
        int numberOfRowsPerBlock = 1;
        int numberOfRows = this.planeDimension.getNumberOfNormalRows();
        int numberOfBlocks = numberOfRows / numberOfRowsPerBlock;
        int remainder = numberOfRows % numberOfRowsPerBlock;
        
        
        // Get the normal seats for the plane and set the current row index
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        int currentRowIndex = 0;
        
        // Create a container to hold the blocks of seats
        ArrayList<ArrayList<Cell>> blocks = new ArrayList<>();
        
        // Loop over each block
        for (int x = 0; x < numberOfBlocks; x++) {
            // Calculate the number of rows to get for this block
            int numberOfRowsToGet = numberOfRowsPerBlock;
            
            if (remainder > 0) {
                if (x == numberOfBlocks - 1) {
                    numberOfRowsToGet += remainder;
                    remainder = 0;
                } else {
                    numberOfRowsToGet++;
                    remainder--;
                }
            }
            
            // Create the containder to hold the seats for this block
            ArrayList<Cell> blockSeats = new ArrayList<>();
            
            // Loop over the total number of rows to get
            for (int y = 0; y < numberOfRowsToGet; y++) {
                // Add all of the seats from that row into the array
                blockSeats.addAll(Arrays.asList(normalSeats[currentRowIndex++]));
            }
            
            blocks.add(blockSeats);
        }
        
        return blocks;
    }
    
    private ArrayList<Cell> createOutsideInOrderForBlock(ArrayList<Cell> block) {
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
        for (Cell cell: block) {
            int cellColumn = cell.getCellColumn();
            columnNormalSeats.get(cellColumn).add(cell);
        }
        
        // Create a container to hold the joint columns
        ArrayList<ArrayList<Cell>> jointColumnNormalSeats = new ArrayList<>();
        
        // Loop until all columns have been added
        while (!columnNormalSeats.isEmpty()) {
            ArrayList<Cell> jointList = new ArrayList<>();
            
            jointList.addAll(columnNormalSeats.remove(0));
            if (!columnNormalSeats.isEmpty()) {
                jointList.addAll(columnNormalSeats.remove(columnNormalSeats.size() - 1));
            }
            
            jointColumnNormalSeats.add(jointList);
        }
        
        ArrayList<Cell> finalRandomisedOrder = new ArrayList<>();
        
        for (ArrayList<Cell> list: jointColumnNormalSeats) {
            finalRandomisedOrder.addAll(this.createRandomSeatingOrderFromSeats(list));
        }
        
        return finalRandomisedOrder;
    }
    
    private ArrayList<Cell> joinBlocksTogetherBackToFront(ArrayList<ArrayList<Cell>> blocks) {
        ArrayList<Cell> jointBlocks = new ArrayList<>();
        
        while (!blocks.isEmpty()) {
            jointBlocks.addAll(blocks.remove(blocks.size() - 1));
        }
        
        return jointBlocks;
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