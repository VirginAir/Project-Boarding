package projectboarding;

/**
 *
 * @author Matthew
 */
public class PlaneDimension {
    
    private final int numberOfRows;
    private final int[] columnSplit; // i.e. 3-3-3 or 3-2-3 or 3-3 etc...
    
    private final int numberOfPriorityRows;
    private final int[] priorityColumnSplit; //i.e 2-2-2 or 1-2-1 or 2-2 etc..
    
    // The dimentions are based on the priority rows being at the front
    
    public PlaneDimension(int numberOfPriorityRows, int[] priorityColumnSplit, int numberOfRows, int[] columnSplit) {
        this.numberOfPriorityRows = numberOfPriorityRows;
        this.priorityColumnSplit = priorityColumnSplit;
        
        this.numberOfRows = numberOfRows;
        this.columnSplit = columnSplit;
    }
    
    public int getNumberOfPriorityRows() {
        return this.numberOfPriorityRows;
    }
    
    public int[] getPriorityColumnSplit() {
        return this.priorityColumnSplit;
    }
    
    public int getNumberOfPriorityColumns() {
        int numberOfColumns = 0;
        
        for (int column: this.priorityColumnSplit) {
            numberOfColumns += column;
        }
        
        return numberOfColumns;
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
