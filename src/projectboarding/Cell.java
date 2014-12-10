package projectboarding;

/**
 *
 * @author Matthew Kempson
 */
public class Cell {

    private final int cellRow;
    private final int cellColumn;
    private CellType cellType;
    private Boolean hasPassenger;

    public enum CellType {

        SEAT, PRIORITY_SEAT, AISLE, NONE
    }

    /**
     * Create the new cell
     *
     * @param row the row of the cell
     * @param column the column of the cell
     * @param type the cell type
     */
    public Cell(int row, int column, CellType type) {
        this.cellRow = row;
        this.cellColumn = column;
        this.cellType = type;
        this.hasPassenger = false;
    }

    /**
     * Get the type of the cell
     *
     * @return the cell type
     */
    public CellType getCellType() {
        return cellType;
    }

    /**
     * Set the type of the cell
     *
     * @param type the type of the cell
     */
    public void setCellType(CellType type) {
        this.cellType = type;
    }

    /**
     * Get the cell column
     *
     * @return the column the cell is on
     */
    public int getCellColumn() {
        return cellColumn;
    }

    /**
     * Get the cell row
     *
     * @return the row the cell is on
     */
    public int getCellRow() {
        return cellRow;
    }

    /**
     * Get if the cell has a passenger on
     *
     * @return true if on false if not
     */
    public Boolean getHasPassenger() {
        return hasPassenger;
    }

    /**
     * Set if the cell has a passenger on
     *
     * @param hasPassenger true if on false if not
     */
    public void setHasPassenger(Boolean hasPassenger) {
        this.hasPassenger = hasPassenger;
    }

    /**
     * Clone the cell into a new cell
     *
     * @return a new cell cloned from the current one
     */
    @Override
    public Cell clone() {
        return new Cell(this.cellRow, this.cellColumn, this.cellType);
    }
}
