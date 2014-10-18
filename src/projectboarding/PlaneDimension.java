package projectboarding;

import java.util.ArrayList;
import static projectboarding.Cell.CellType;

/**
 *
 * @author Matthew
 */
public class PlaneDimension {
    
    private final Cell[][] planeSeats;
    private final Cell[][] prioritySeats;
    private final Cell[][] normalSeats;
    
    public PlaneDimension(Cell[][] planeSeats) {
        this.planeSeats = planeSeats;
        
        this.prioritySeats = this.getSeats(true);
        this.normalSeats = this.getSeats(false);
    }
    
    public Cell[][] getAllSeats() {
        return this.planeSeats;
    }
    
    public Cell[][] getPrioritySeats() {
        return this.prioritySeats;
    }
    
    public Cell[][] getNormalSeats() {
        return this.normalSeats;
    }
    
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
       
        return (Cell[][]) seats.toArray();
    }
    
    public int getNumberOfPriorityRows() {
        return this.prioritySeats.length;
    }
    
    public int getNumberOfNormalRows() {
        return this.normalSeats.length;
    }
    
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
    
}
