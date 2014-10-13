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
        return this.calculateNumberOfColumns(this.priorityColumnSplit);
    }
    
    public int getNumberOfRows() {
        return this.numberOfRows;
    }
    
    public int[] getColumnSplit() {
        return this.columnSplit;
    } 
    
    public int getNumberOfColumns() {
        return this.calculateNumberOfColumns(this.columnSplit);
    }
    
    private int calculateNumberOfColumns(int[] split) {
        int numberOfColumns = 0;
        
        for (int column: split) {
            numberOfColumns += column;
        }
        
        return numberOfColumns;
    }
    
    public int getNumberOfLanes(){
        int numberOfLanes = 0;
        
        for (int column : this.columnSplit) {
            if (column== 0) {
                numberOfLanes += 1;
            }
            
        }
        return numberOfLanes;
    }
    
    
    public int totalNumberOfSeats() {        
        return (this.getNumberOfColumns() * this.getNumberOfRows()) + (this.getNumberOfPriorityColumns() * this.getNumberOfPriorityRows());
    }
    
    public Cell[][] getSeatVisualisation(){
        int totalRows = this.getNumberOfRows() + this.getNumberOfPriorityRows();
        int totalColumns = this.getNumberOfColumns() + this.getNumberOfLanes();
        
        Cell[][] seatVisualisation = new Cell[totalRows][totalColumns];
        
        int rowLocation = 0;
        for(int i = 0; i < totalRows; i++)
        {
            for(int j = 0; j < totalColumns; j++)
            {
                seatVisualisation[i][j] = new Cell(i, j, Cell.CellType.SEAT);
            }
        }
        for (int column : this.getColumnSplit()) {
            
            if(column > 0){
                rowLocation += column;
            } else {
                for (int i=0; i < totalRows; i++){
                    seatVisualisation[i][rowLocation].setCellType(Cell.CellType.LANE);
                }
            }
        }
        int emptySpaces = this.getNumberOfColumns() - this.getNumberOfPriorityColumns();
        for (int i = 0; i < this.getNumberOfPriorityRows(); i++) {
            for (int j = 0; j < totalColumns; j++) {
                if(seatVisualisation[i][j].getCellType().equals(Cell.CellType.SEAT)){
                    if (((emptySpaces/2)-1) < j && j < (totalColumns-(emptySpaces/2))){
                        seatVisualisation[i][j].setCellType(Cell.CellType.PRIORITY_SEAT);
                    }
                }
            }
            
        }
        return seatVisualisation;
    }
    
}
