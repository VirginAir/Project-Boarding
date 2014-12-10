package projectboarding;

import java.util.ArrayList;
import projectboarding.Cell.CellType;

/**
 *
 * @author Matthew
 */
public class PlaneDimension {

    /**
     * Load the dynamic library
     */
    static {
        System.loadLibrary("PlaneDimensionLibrary");
    }
    private final Cell[][] planeSeats;
    private final Cell[][] prioritySeats;
    private final Cell[][] normalSeats;

    /**
     * Create the plane dimension
     *
     * @param planeSeats the seats to create the dimension from
     */
    public PlaneDimension(Cell[][] planeSeats) {
        this.planeSeats = planeSeats;

        this.prioritySeats = this.getSeats(true);
        this.normalSeats = this.getSeats(false);
    }

    /**
     * Create the plane dimension
     *
     * @param pd the plane dimension to create a new instance of
     */
    public PlaneDimension(PlaneDimension pd) {

        int row = pd.getAllSeats().length;
        int column = pd.getAllSeats()[0].length;

        planeSeats = new Cell[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                this.planeSeats[i][j] = pd.getAllSeats()[i][j].clone();
            }
        }

        this.prioritySeats = this.getSeats(true);
        this.normalSeats = this.getSeats(false);
    }

    /**
     * Get all of the seats
     *
     * @return the seats
     */
    public native Cell[][] getAllSeats();

    /**
     * Get all of the priority seats
     *
     * @return the priority seats
     */
    public native Cell[][] getPrioritySeats();

    /**
     * Get all of the normal seats
     *
     * @return
     */
    public native Cell[][] getNormalSeats();

    /**
     * Get the seats
     *
     * @param priority The seating type to get
     * @return the seats
     */
    private Cell[][] getSeats(boolean priority) {
        ArrayList<ArrayList<Cell>> seats = new ArrayList<>();

        for (Cell[] row : this.planeSeats) {
            ArrayList<Cell> rowSeats = new ArrayList<>();

            for (Cell cell : row) {
                if (priority && cell.getCellType() == CellType.PRIORITY_SEAT
                        || !priority && cell.getCellType() == CellType.SEAT) {
                    rowSeats.add(cell);
                }
            }

            if (!rowSeats.isEmpty()) {
                seats.add(rowSeats);
            }
        }

        Cell[][] array = new Cell[seats.size()][];
        for (int i = 0; i < seats.size(); i++) {
            ArrayList<Cell> row = seats.get(i);
            array[i] = row.toArray(new Cell[row.size()]);
        }

        return array;
    }

    /**
     * Get the number of priority rows
     *
     * @return the number of priority rows
     */
    public native int getNumberOfPriorityRows();

    /**
     * Get the number of normal rows
     *
     * @return the number of normal rows
     */
    public native int getNumberOfNormalRows();

    /**
     * Get the total number of rows
     *
     * @return the total number of rows
     */
    public native int totalNumberOfRows();

    /**
     * Get the row numbers of the normal seats
     *
     * @return the row numbers
     */
    public ArrayList<Integer> getNormalRowNumbers() {
        ArrayList<Integer> rowNumbers = new ArrayList<>();

        for (Cell[] cellRow : this.normalSeats) {
            rowNumbers.add(cellRow[0].getCellRow());
        }

        return rowNumbers;
    }

    /**
     * Get the number of columns in the plane
     *
     * @return the number of columns
     */
    public int getNumberOfColumns() {
        return this.planeSeats[0].length;
    }

    /**
     * Get the number of aisles
     *
     * @return the number of aisles
     */
    public int getNumberOfAisles() {
        return this.getAisleColumnNumbers().size();
    }

    /**
     * Get the aisle column numbers
     *
     * @return the aisle column numbers
     */
    public ArrayList<Integer> getAisleColumnNumbers() {
        ArrayList<Integer> columnNumbers = new ArrayList<>();

        for (Cell cell : this.planeSeats[0]) {
            if (cell.getCellType() == CellType.AISLE) {
                columnNumbers.add(cell.getCellColumn());
            }
        }

        return columnNumbers;
    }

    /**
     * Get the number of priority seats
     *
     * @return the number of priority seats
     */
    public int getNumberOfPrioritySeats() {
        return this.getNumberOfSeats(true);
    }

    /**
     * Get the number of normal seats
     *
     * @return the number of normal seats
     */
    public int getNumberOfNormalSeats() {
        return this.getNumberOfSeats(false);
    }

    /**
     * Get the number of seats
     *
     * @param priority booelan true priority seats, false normal seats
     * @return the number of seats for the given boolean
     */
    private int getNumberOfSeats(boolean priority) {
        Cell[][] seats;
        if (priority) {
            seats = this.prioritySeats;
        } else {
            seats = this.normalSeats;
        }

        int numberOfSeats = 0;

        for (Cell[] cells : seats) {
            numberOfSeats += cells.length;
        }

        return numberOfSeats;
    }

    /**
     * Get the total number of seats
     *
     * @return the total number of seats
     */
    public int totalNumberOfSeats() {
        return this.getNumberOfNormalSeats() + this.getNumberOfPrioritySeats();
    }

    /**
     * Reset the seats to have no passengers
     */
    public void resetHasPassengers() {
        for (Cell[] cell : planeSeats) {
            for (Cell cell1 : cell) {
                cell1.setHasPassenger(false);
            }
        }
    }
}
