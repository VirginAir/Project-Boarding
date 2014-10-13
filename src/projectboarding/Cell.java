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
    private int numPassengers;
    public enum CellType {
        SEAT, PRIORITY_SEAT, LANE, NONE
    }
    
    public Cell(int row, int column, CellType type) {
        this.cellRow = row;
        this.cellColumn = column;
        this.cellType = type;
        this.hasPassenger = false;
        this.numPassengers = 0;
    }
    
    public int getSeatRow() {
        return this.cellRow;
    }
    
    public int getSeatColumn() {
        return this.cellColumn;
    }

    public CellType getCellType() {
        return cellType;
    }
    
    public void setCellType(CellType type){
        this.cellType = type;
    }

    public int getCellColumn() {
        return cellColumn;
    }

    public int getCellRow() {
        return cellRow;
    }

    public Boolean getHasPassenger() {
        return hasPassenger;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public void setHasPassenger(Boolean hasPassenger) {
        this.hasPassenger = hasPassenger;
    }

    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }
    
    
    
    
}
