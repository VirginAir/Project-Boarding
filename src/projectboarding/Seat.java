package projectboarding;

/**
 *
 * @author Matthew Kempson
 */
public class Seat {
    private final int seatRow;
    private final int seatColumn;
    
    public Seat(int row, int column) {
        this.seatRow = row;
        this.seatColumn = column;
    }
    
    public int getSeatRow() {
        return this.seatRow;
    }
    
    public int getSeatColumn() {
        return this.seatColumn;
    }
}
