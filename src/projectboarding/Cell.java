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
        SEAT, PRIORITY_SEAT, LANE, NONE
    }
    
    public Cell(int row, int column, CellType type) {
        this.cellRow = row;
        this.cellColumn = column;
        this.cellType = type;
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
    
    
}
