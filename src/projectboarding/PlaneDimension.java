package projectboarding;

/**
 *
 * @author Matthew
 */
public class PlaneDimension {
    
    private final int numberOfRows;
    private final int[] columnSplit;
    
    public PlaneDimension(int numberOfRows, int[] columnSplit) {
        this.numberOfRows = numberOfRows;
        this.columnSplit = columnSplit;
    }
    
    public int getNumberOfRows() {
        return this.numberOfRows;
    }
    
    public int[] getColumnSplit() {
        return this.columnSplit;
    } 
    
    public int getNumberOfColumns() {
        int numberOfColumns = 0;
        
        for (int column: this.columnSplit) {
            numberOfColumns += column;
        }
        
        return numberOfColumns;
    }
    
    public int totalNumberOfSeats() {        
        return this.getNumberOfColumns() * this.numberOfRows;
    }
    
}
