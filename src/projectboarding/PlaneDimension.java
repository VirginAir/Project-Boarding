package projectboarding;

import java.util.ArrayList;
import static projectboarding.Cell.CellType;

/**
 *
 * @author Matthew
 */
public class PlaneDimension {
    static {
        System.loadLibrary("PlaneDimensionLibrary");
    }
    
    private final Cell[][] planeSeats;
    private final Cell[][] prioritySeats;
    private final Cell[][] normalSeats;
    
    public PlaneDimension(Cell[][] planeSeats) {
        this.planeSeats = planeSeats;
        
        this.prioritySeats = this.getSeats(true);
        this.normalSeats = this.getSeats(false);
    }
    
    public PlaneDimension(PlaneDimension pd){
        this.planeSeats = pd.getAllSeats().clone();
        this.normalSeats = pd.getNormalSeats().clone();
        this.prioritySeats = pd.getPrioritySeats().clone();
    }
    
    public native Cell[][] getAllSeats();
    
    public native Cell[][] getPrioritySeats();
    
    public native Cell[][] getNormalSeats();
    
    private Cell[][] getSeats(boolean priority) {
        ArrayList<ArrayList<Cell>> seats = new ArrayList<>();
        
        for (Cell[] row: this.planeSeats) {
            ArrayList<Cell> rowSeats = new ArrayList<>();
            
            for (Cell cell : row) {
                if ((priority && cell.getCellType() == CellType.PRIORITY_SEAT) 
                        || (!priority && cell.getCellType() == CellType.SEAT)) {
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
    
    public native int getNumberOfPriorityRows();
    
    public native int getNumberOfNormalRows();
    
    public native int totalNumberOfRows();
    
    public int getNumberOfColumns() {
        return this.planeSeats[0].length;
    }
    
    public int getNumberOfAisles() {
        int aisles = 0;
        
        for (Cell cell: this.planeSeats[0]) {
            if (cell.getCellType() == CellType.AISLE) {
                aisles++;
            }
        }
        
        return aisles;
    }
    
    public int getNumberOfPrioritySeats() {
        return this.getNumberOfSeats(true);
    }
    
    public int getNumberOfNormalSeats() {
        return this.getNumberOfSeats(false);
    }
    
    private int getNumberOfSeats(boolean priority) {
        Cell[][] seats;
        if (priority) {
            seats = this.prioritySeats;
        } else {
            seats = this.normalSeats;
        }
        
        int numberOfSeats = 0;
        
        for (Cell[] cells: seats) {
            numberOfSeats += cells.length;
        }
        
        return numberOfSeats;
    }
    
    public int totalNumberOfSeats() {
        return (this.getNumberOfNormalSeats() + this.getNumberOfPrioritySeats());
    }
    
    public void resetHasPassengers(){
        for (Cell[] cell : planeSeats) {
            for (Cell cell1 : cell) {
                cell1.setHasPassenger(false);
            }
        }
    }
}
