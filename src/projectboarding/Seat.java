package projectboarding;

/**
 *
 * @author Matthew Kempson
 */
public class Seat {
    private final int seatRow;
    private final int seatColumn;
    private final boolean isPrioritySeat;
    
    public Seat(int row, int column, boolean isPrioritySeat) {
        this.seatRow = row;
        this.seatColumn = column;
        this.isPrioritySeat = isPrioritySeat;
    }
    
    public int getSeatRow() {
        return this.seatRow;
    }
    
    public int getSeatColumn() {
        return this.seatColumn;
    }
    
    public boolean isPrioritySeat() {
        return this.isPrioritySeat;
    }
}
