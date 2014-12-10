package projectboarding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * SeatingMethod creates a seating order for a given seating pattern for a given
 * plane dimension.
 *
 * @author Matthew Kempson
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
        REVERSE_PYRAMID("Rev. Pyramid"),
        ROTATING_ZONE("Rotating Zone"),
        CUSTOM("Custom"),
        NONE("NA");
        private final String text;

        private DefaultSeatingMethod(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    /* Global variables  */
    private final PlaneDimension planeDimension;
    private final ArrayList<Cell> randomisedPrioritySeats;
    private int[][] customMethod;
    /* The number of rows per block, user definable */
    int numberOfRowsPerBlock = 5;

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

        if (this.planeDimension.getNormalSeats().length == 0) {
            return this.randomisedPrioritySeats;
        }

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

    /**
     * Get the seating order for a user created seating method.
     *
     * @param customSeatingMethod the order for the seats to be taken.
     * @return an arrayList containing the seating order.
     */
    public ArrayList<Cell> getCustomSeatingOrder(int[][] customSeatingMethod) {
        this.customMethod = customSeatingMethod;

        return this.calculateCustomSeatingOrder();
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
        // Get the normal seats as blocks
        ArrayList<ArrayList<Cell>> blocks = this.splitNormalSeatsIntoBlocks();

        // Loop over the blocks, randomise them and add to a final arrayList
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

        ArrayList<ArrayList<Cell>> jointOrder = this.joinOutsideSeatsTogether(this.splitBlockIntoColumns(normalSeatsList));
        ArrayList<Cell> outsideInOrder = this.randomiseOutsideInOrder(jointOrder);

        return this.createFinalOrder(outsideInOrder);
    }

    /**
     * Create the seating order for the block boarding seating method.
     *
     * @return an arrayList containing the seating order.
     */
    private ArrayList<Cell> calculateBlockBoardingSeatingOrder() {
        // Get the normal seats as blocks
        ArrayList<ArrayList<Cell>> blocks = this.splitNormalSeatsIntoBlocks();

        // Loop over the blocks, creating outside in order for each and adding to a final arrayList
        ArrayList<ArrayList<Cell>> orderedBlocks = new ArrayList<>();
        for (ArrayList<Cell> block : blocks) {
            ArrayList<ArrayList<Cell>> jointOrder = this.joinOutsideSeatsTogether(this.splitBlockIntoColumns(block));
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
        // Get the normal seats as blocks
        ArrayList<ArrayList<Cell>> blocks = this.splitNormalSeatsIntoBlocks();

        // Loop over the blocks and randomise the order
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
        ArrayList<ArrayList<Cell>> outsideInOrder = this.splitBlockIntoColumns(this.convertArrayToArrayList(normalSeats));

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
     * Create the seating order for the reverse pyramid seating method.
     *
     * @return an arrayList containing the seating order.
     */
    private ArrayList<Cell> calculateReversePyramidSeatingOrder() {
        // Split the entire plane into columns
        Cell[][] normalSeats = this.planeDimension.getNormalSeats();
        ArrayList<ArrayList<Cell>> blocks = this.splitBlockIntoColumns(this.convertArrayToArrayList(normalSeats));

        // Get the first (50%), second (30%) and third (20%) amount of seats to get
        int numberOfRows = this.planeDimension.getNumberOfNormalRows();
        int firstPercent = (int) Math.round((numberOfRows / 100.0) * 50.0);
        int secondPercent = (int) Math.round((numberOfRows / 100.0) * 30.0);
        int thirdPercent = numberOfRows - firstPercent - secondPercent;

        int thirdEndingPoint = thirdPercent;
        int secondEndingPoint = thirdPercent + secondPercent;

        /* Calculate the number of splits the reverse pyramid will
         create and create the spits */
        int numberOfSplits = (int) Math.ceil(blocks.size() / 2.0 + 2);
        ArrayList<ArrayList<Cell>> splits = new ArrayList<>();

        for (int x = 0; x < numberOfSplits; x++) {
            splits.add(new ArrayList<Cell>());
        }

        // Add the seats into the splits
        ArrayList<Integer> rowNumbers = this.planeDimension.getNormalRowNumbers();
        int splitHelper = 0;
        boolean isEven = blocks.size() % 2 == 0;
        int middle = blocks.size() / 2;

        for (int i = 0; i < blocks.size(); i++) {
            ArrayList<Cell> block = blocks.get(i);

            // Loop over all of the row numbers
            for (int j = 0; j < rowNumbers.size(); j++) {
                Integer rowNumber = rowNumbers.get(j);
                Cell cell = block.get(0);

                if (cell.getCellRow() == rowNumber) {
                    if (j < thirdEndingPoint) {
                        splits.get(splitHelper + 2).add(block.remove(0));
                    } else if (j < secondEndingPoint) {
                        splits.get(splitHelper + 1).add(block.remove(0));
                    } else {
                        splits.get(splitHelper + 0).add(block.remove(0));
                    }
                }

                if (block.isEmpty()) {
                    break;
                }
            }

            if (!isEven && i < middle || isEven && i < middle - 1) {
                splitHelper++;
            } else if (i >= middle) {
                splitHelper--;
            }
        }

        ArrayList<Cell> seatingOrder = new ArrayList<>();

        for (ArrayList<Cell> list : splits) {
            seatingOrder.addAll(this.createRandomSeatingOrderFromSeats(list));
        }

        return this.createFinalOrder(seatingOrder);
    }

    /**
     * Create the seating order for the custom method.
     *
     * @return an arrayList containing the seating order.
     */
    private ArrayList<Cell> calculateCustomSeatingOrder() {
        // Find the maximum number from the given order
        int maximumNumber = 0;
        for (int[] array : this.customMethod) {
            for (int y = 0; y < array.length; y++) {
                if (array[y] > maximumNumber) {
                    maximumNumber = array[y];
                }
            }
        }

        // Create the containers for the ordered seats
        ArrayList<ArrayList<Cell>> container = new ArrayList<>();
        for (int x = 0; x <= maximumNumber; x++) {
            ArrayList<Cell> list = new ArrayList<>();
            container.add(list);
        }

        Cell[][] normalSeats = this.planeDimension.getAllSeats();

        // Place all of the seats into the container
        for (int x = 0; x < this.customMethod.length; x++) {
            for (int y = 0; y < this.customMethod[x].length; y++) {
                if (this.customMethod[x][y] != -1) {
                    container.get(this.customMethod[x][y]).add(normalSeats[x][y]);
                }
            }
        }

        ArrayList<Cell> finalOrder = new ArrayList<>();

        for (ArrayList<Cell> list : container) {
            finalOrder.addAll(this.createRandomSeatingOrderFromSeats(list));
        }

        return this.createFinalOrder(finalOrder);
    }

    /**
     * Load the dynamic libraries
     */
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
        // Check the number of rows per block is a valid number
        if (numberOfRows < this.numberOfRowsPerBlock) {
            this.numberOfRowsPerBlock = numberOfRows;
        } else if (this.numberOfRowsPerBlock > numberOfRows) {
            this.numberOfRowsPerBlock = numberOfRows;
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
     * Splits a given block into columns.
     *
     * @param block an arrayList containing the block of cells.
     * @return an arrayList containing the ordered block of cells.
     */
    private ArrayList<ArrayList<Cell>> splitBlockIntoColumns(ArrayList<Cell> block) {
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

        // Remove the empty rows (the aisles)
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

    /**
     * Get the number of rows per block.
     *
     * @return an int containing the number of rows per block.
     */
    public int getNumberOfRowsPerBlock() {
        return this.numberOfRowsPerBlock;
    }

    /**
     * Set the number of rows per block.
     *
     * @param numberOfRowsPerBlock an int containing the number of rows per
     * block.
     */
    public void setNumberOfRowsPerBlock(int numberOfRowsPerBlock) {
        this.numberOfRowsPerBlock = numberOfRowsPerBlock;
    }
}
