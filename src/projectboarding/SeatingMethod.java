package projectboarding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Matthew
 */
public class SeatingMethod {

    /**
     * A pre-defined seating method.
     */
    public enum DefaultSeatingMethod {

        BACK_TO_FRONT("Back To Front"), 
        BLOCK_BOARDING("Block Boarding"), 
        BY_SEAT("By Seat"), 
        OUTSIDE_IN("Outside In"), 
        RANDOM("Random"), 
        REVERSE_PYRAMID("Reverse Pyramid"), 
        ROTATING_ZONE("Rotating Zone"), 
        CUSTOM("Custom");
        
        private final String text;
        
        private DefaultSeatingMethod(final String text) {
            this.text = text;
        }
        
        @Override
        public String toString() {
            return text;
        }
    }

    private final PlaneDimension planeDimension;
    private final ArrayList<Cell> randomisedPrioritySeats;
    private DefaultSeatingMethod defaultMethod;
    private int[][] customMethod;

    /**
     * Initialize the seating method class with a default method.
     *
     * @param planeDimension the dimensions of the plane to order the seats
     * from.
     */
    public SeatingMethod(PlaneDimension planeDimension) {
        this.planeDimension = planeDimension;

        this.randomisedPrioritySeats = this.calculateRandomSeatingOrderForPrioritySeats();
    }

    /**
     * Get the seating order for a default seating method.
     *
     * @param seatingMethod the seating method to use.
     * @return an arrayList containing the seating order.
     */
    public ArrayList<Cell> getDefaultSeatingOrder(DefaultSeatingMethod seatingMethod) {

        defaultMethod = seatingMethod;

        switch (seatingMethod) {
            case BACK_TO_FRONT:
                return this.calculateBackToFrontSeatingOrder();
            case BLOCK_BOARDING:
                return this.calculateBlockBoardingSeatingOrder();
            case BY_SEAT:
                return this.calculateBySeatSeatingOrder();
            case OUTSIDE_IN:
                return this.calculateOutsideInSeatingOrder();
            case RANDOM:
                return this.calculateRandomSeatingOrder();
            case REVERSE_PYRAMID:
                return this.calculateReversePyramidSeatingOrder();
            case ROTATING_ZONE:
                return this.calculateRotatingZoneSeatingOrder();
            default:
                return null;
        }
    }

    public ArrayList<Cell> getCustomSeatingOrder(int[][] customSeatingMethod) {
        defaultMethod = DefaultSeatingMethod.CUSTOM;
        this.customMethod = customSeatingMethod;

        return this.calculateCustomSeatingOrder();
    }

    /**
     * Get the seating order for a user created seating method.
     *
     * @param userSeatingMethod the order for the seats to be taken.
     * @return an arrayList containing the seating order.
     */
    public ArrayList<Cell> getSeatingOrder(Cell[][] userSeatingMethod) {
        return null;
    }

    /**
     * Creates a set of randomly ordered seats for the priority seats in the
     * plane.
     *
     * @return an arrayList containing randomly ordered priority seats.
     */
    private ArrayList<Cell> calculateRandomSeatingOrderForPrioritySeats() {
        return this.createRandomSeatingOrderFromSeats(this.convertArrayToArrayList(this.planeDimension.getPrioritySeats()));
    }

    /**
     * Create the seating order for the default random seating method.
     *
     * @return an arrayList containing the seating order.
     */
    private ArrayList<Cell> calculateRandomSeatingOrder() {
        // Get the random order of the normal seats
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        ArrayList<Cell> list = this.convertArrayToArrayList(normalSeats);
        ArrayList<Cell> randomNormalSeats = this.createRandomSeatingOrderFromSeats(list);

        // Return the results
        return this.createFinalOrder(randomNormalSeats);
    }

    /**
     * Create the seating order for the back to front seating method.
     *
     * @return an arrayList containing the seating order.
     */
    private ArrayList<Cell> calculateBackToFrontSeatingOrder() {
        ArrayList<ArrayList<Cell>> blocks = this.splitNormalSeatsIntoBlocks();

        ArrayList<ArrayList<Cell>> randomisedBlocks = new ArrayList<>();
        for (ArrayList<Cell> block : blocks) {
            randomisedBlocks.add(this.createRandomSeatingOrderFromSeats(block));
        }

        return this.createFinalOrder(this.joinBlocksTogetherBackToFront(randomisedBlocks));
    }

    /**
     * Create the seating order for the outside in seating method.
     *
     * @return an arrayList containing the seating order.
     */
    private ArrayList<Cell> calculateOutsideInSeatingOrder() {
        // Get the normal seats and place into an arraylist
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        ArrayList<Cell> normalSeatsList = this.convertArrayToArrayList(normalSeats);

        ArrayList<ArrayList<Cell>> jointOrder = this.joinOutsideSeatsTogether(this.createOutsideInOrderForBlock(normalSeatsList));
        ArrayList<Cell> outsideInOrder = this.randomiseOutsideInOrder(jointOrder);

        return this.createFinalOrder(outsideInOrder);
    }

    /**
     * Create the seating order for the block boarding seating method.
     *
     * @return an arrayList containing the seating order.
     */
    private ArrayList<Cell> calculateBlockBoardingSeatingOrder() {
        ArrayList<ArrayList<Cell>> blocks = this.splitNormalSeatsIntoBlocks();

        ArrayList<ArrayList<Cell>> orderedBlocks = new ArrayList<>();
        for (ArrayList<Cell> block : blocks) {
            ArrayList<ArrayList<Cell>> jointOrder = this.joinOutsideSeatsTogether(this.createOutsideInOrderForBlock(block));
            ArrayList<Cell> seats = this.randomiseOutsideInOrder(jointOrder);
            orderedBlocks.add(seats);
        }

        return this.createFinalOrder(this.joinBlocksTogetherBackToFront(orderedBlocks));
    }

    /**
     * Create the seating order for the rotating zone seating method.
     *
     * @return an arrayList containing the seating order.
     */
    private ArrayList<Cell> calculateRotatingZoneSeatingOrder() {
        ArrayList<ArrayList<Cell>> blocks = this.splitNormalSeatsIntoBlocks();

        ArrayList<ArrayList<Cell>> randomisedBlocks = new ArrayList<>();
        for (ArrayList<Cell> block : blocks) {
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

    /**
     * Create the seating order for the by seat seating method.
     *
     * @return an arrayList containing the seating order.
     */
    private ArrayList<Cell> calculateBySeatSeatingOrder() {
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        ArrayList<ArrayList<Cell>> outsideInOrder = this.createOutsideInOrderForBlock(this.convertArrayToArrayList(normalSeats));

        // Order each list of cells
        ArrayList<ArrayList<Cell>> orderedSeats = new ArrayList<>();
        for (ArrayList<Cell> seats : outsideInOrder) {
            // Order the seats from back to front
            Collections.sort(seats, new Comparator<Cell>() {
                @Override
                public int compare(Cell cell1, Cell cell2) {
                    Integer cell1Row = cell1.getCellRow();
                    Integer cell2Row = cell2.getCellRow();

                    return cell2Row.compareTo(cell1Row);
                }
            });
            orderedSeats.add(seats);
        }

        ArrayList<ArrayList<Cell>> jointSeats = this.joinOutsideSeatsTogether(orderedSeats);
        ArrayList<Cell> finalOrder = new ArrayList<>();

        for (ArrayList<Cell> list : jointSeats) {
            finalOrder.addAll(list);
        }

        return this.createFinalOrder(finalOrder);
    }

    /**
     *
     * @return
     */
    private ArrayList<Cell> calculateReversePyramidSeatingOrder() {
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        ArrayList<ArrayList<Cell>> blocks = this.createOutsideInOrderForBlock(this.convertArrayToArrayList(normalSeats));

        // Get the number of splits rounded down
        int differentSplits = (int) (blocks.size() / 2.0) + 2;
        int numberOfNormalRows = this.planeDimension.getNumberOfNormalRows();
        int fortyPercent = (int) ((numberOfNormalRows / 100.0) * 40);
        int twentyPercent = numberOfNormalRows - (fortyPercent * 2);

        ArrayList<ArrayList<Cell>> splitSeating = new ArrayList<>();

        for (int x = 0; x < differentSplits; x++) {
            ArrayList<Cell> split = new ArrayList<>();
            splitSeating.add(split);
        }

        int x = 0;

        while (!blocks.isEmpty()) {
            ArrayList<Cell> cells1 = blocks.remove(0);
            ArrayList<Cell> cells2 = blocks.remove(blocks.size() - 1);

            /* 
              Need the try catch statments because sometimes blocks can be
              different sizes due to unbalanced plane dimensions
            */
            
            for (int t = 0; t < twentyPercent; t++) {
                try {
                    splitSeating.get(x + 2).add(cells1.remove(0));
                } catch (IndexOutOfBoundsException e) {
                    // Ignore    
                }
                try {
                    splitSeating.get(x + 2).add(cells2.remove(0));
                } catch (IndexOutOfBoundsException e) {
                    // Ignore    
                }
            }

            for (int f = 0; f < fortyPercent; f++) {
                try {
                    splitSeating.get(x + 1).add(cells1.remove(0));
                } catch (IndexOutOfBoundsException e) {
                    // Ignore    
                }
                try {
                    splitSeating.get(x + 1).add(cells2.remove(0));
                } catch (IndexOutOfBoundsException e) {
                    // Ignore    
                }
            }

            for (int f = 0; f < fortyPercent; f++) {
                try {
                    splitSeating.get(x).add(cells1.remove(0));
                } catch (IndexOutOfBoundsException e) {
                    // Ignore    
                }
                try {
                    splitSeating.get(x).add(cells2.remove(0));
                } catch (IndexOutOfBoundsException e) {
                    // Ignore    
                }
            }

            x++;
        }

        ArrayList<Cell> seatingOrder = new ArrayList<>();

        for (ArrayList<Cell> list : splitSeating) {
            seatingOrder.addAll(this.createRandomSeatingOrderFromSeats(list));
        }

        return this.createFinalOrder(seatingOrder);
    }

    private ArrayList<Cell> calculateCustomSeatingOrder() {
        // Find the maximum number
        int maximumNumber = 0;
        for (int[] array : this.customMethod) {
            for (int y = 0; y < array.length; y++) {
                if (array[y] > maximumNumber) {
                    maximumNumber = array[y];
                }
            }
        }

        // Create the container
        ArrayList<ArrayList<Cell>> container = new ArrayList<>();
        for (int x = 0; x <= maximumNumber; x++) {
            ArrayList<Cell> list = new ArrayList<>();
            container.add(list);
        }

        Cell[][] normalSeats = this.planeDimension.getNormalSeats();

        // Place all of the seats into the container
        for (int x = 0; x < this.customMethod.length; x++) {
            for (int y = 0; y < this.customMethod[x].length; y++) {
                container.get(this.customMethod[x][y]).add(normalSeats[x][y]);
            }
        }

        ArrayList<Cell> finalOrder = new ArrayList<>();

        for (ArrayList<Cell> list : container) {
            finalOrder.addAll(this.createRandomSeatingOrderFromSeats(list));
        }

        return this.createFinalOrder(finalOrder);
    }

    static {
        System.loadLibrary("SeatingMethodLibrary");
    }
    
    /**
     * Convert a 2D array into an arrayList.
     *
     * @param array a 2D array to be converted.
     * @return an arrayList containing the objects in the original array.
     */
    private native ArrayList<Cell> convertArrayToArrayList(Cell[][] array);

    /**
     * Randomize the order of the seats.
     *
     * @param seats an arrayList of seats to be randomized.
     * @return an arrayList containing the randomized seating order.
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
     * Join the ordered seats with the priority seats.
     *
     * @param orderedSeats an arrayList containing the ordered seats.
     * @return an arrayList containing the priority seats and ordered seats.
     */
    private ArrayList<Cell> createFinalOrder(ArrayList<Cell> orderedSeats) {
        // Join the two arrayLists together
        ArrayList<Cell> seatingOrder = new ArrayList<>();
        seatingOrder.addAll(this.randomisedPrioritySeats);
        seatingOrder.addAll(orderedSeats);

        return seatingOrder;
    }

    /**
     * Split the normal seats into blocks.
     *
     * @return an arrayList containing the blocks.
     */
    private ArrayList<ArrayList<Cell>> splitNormalSeatsIntoBlocks() {
        // Calculate how many blocks to split the plane into
        int numberOfRows = this.planeDimension.getNumberOfNormalRows();
        int numberOfRowsPerBlock = 3;
        if (numberOfRows < 3) {
            numberOfRowsPerBlock = numberOfRows;
        }
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

    /**
     * Creates an outside in order for the given block of cells.
     *
     * @param block an arrayList containing the block of cells.
     * @return an arrayList containing the ordered block of cells.
     */
    private ArrayList<ArrayList<Cell>> createOutsideInOrderForBlock(ArrayList<Cell> block) {
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
        for (Cell cell : block) {
            int cellColumn = cell.getCellColumn();
            columnNormalSeats.get(cellColumn).add(cell);
        }

        ArrayList<ArrayList<Cell>> finalNormalSeats = new ArrayList<>();
        for (ArrayList<Cell> list : columnNormalSeats) {
            if (!list.isEmpty()) {
                finalNormalSeats.add(list);
            }
        }

        return finalNormalSeats;
    }

    /**
     * Joins seats together in an outside in direction.
     *
     * @param seats the seats to join together.
     * @return an arrayList containing the joint seats.
     */
    private ArrayList<ArrayList<Cell>> joinOutsideSeatsTogether(ArrayList<ArrayList<Cell>> seats) {
        // Create a container to hold the joint columns
        ArrayList<ArrayList<Cell>> jointColumnNormalSeats = new ArrayList<>();

        // Loop until all columns have been added
        while (!seats.isEmpty()) {
            ArrayList<Cell> jointList = new ArrayList<>();

            jointList.addAll(seats.remove(0));
            if (!seats.isEmpty()) {
                jointList.addAll(seats.remove(seats.size() - 1));
            }

            jointColumnNormalSeats.add(jointList);
        }

        return jointColumnNormalSeats;
    }

    /**
     * Randomize seats from an outside in arrayList.
     *
     * @param seats the seats to randomize.
     * @return an arrayList containing the randomized seats.
     */
    private ArrayList<Cell> randomiseOutsideInOrder(ArrayList<ArrayList<Cell>> seats) {
        ArrayList<Cell> finalRandomisedOrder = new ArrayList<>();

        for (ArrayList<Cell> list : seats) {
            finalRandomisedOrder.addAll(this.createRandomSeatingOrderFromSeats(list));
        }

        return finalRandomisedOrder;
    }

    /**
     * Join the given blocks together in a back to front order.
     *
     * @param blocks an arrayList containing the blocks.
     * @return an arrayList containing the ordered blocks.
     */
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
        switch (this.defaultMethod) {
            case BACK_TO_FRONT:
                seatingMethod = "back-to-front";
                break;
            case OUTSIDE_IN:
                seatingMethod = "outside-in";
                break;
            case RANDOM:
                seatingMethod = "random";
                break;
            case BLOCK_BOARDING:
                seatingMethod = "block boarding";
                break;
            case BY_SEAT:
                seatingMethod = "by seat";
                break;
            case REVERSE_PYRAMID:
                seatingMethod = "reverse pyramid";
                break;
            case ROTATING_ZONE:
                seatingMethod = "rotating zone";
                break;
            case CUSTOM:
                seatingMethod = "custom";
                break;
            default:
                seatingMethod = "unrecognised method";
                break;
        }

        return seatingMethod;
    }

}
