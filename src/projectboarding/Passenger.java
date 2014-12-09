package projectboarding;

import java.util.Random;

/**
 *
 * @author Matthew
 */
public class Passenger {

    private final Cell seat;
    private Cell currentCell;
    private boolean hasTakenSeat;
    private int baggageTime;
    private int timePerRow;
    private final int permTimePerRow;
    private int seatInterferenceTime;
    private int aisle;
    private int interferingPassengers;

    /**
     * Create a new passenger
     * @param seat the seat the passenger going to
     * @param hasBaggageWeight the weighting to give the baggage
     */
    public Passenger(Cell seat, double hasBaggageWeight) {
        Random r = new Random();
        this.seat = seat;
        if (r.nextDouble() < hasBaggageWeight) {
            baggageTime = r.nextInt(16) + 4;
        } else {
            baggageTime = 0;
        }
        this.hasTakenSeat = false;

        timePerRow = r.nextInt(3) + 2;
        permTimePerRow = timePerRow;
        seatInterferenceTime = 0;
        interferingPassengers = 0;
    }

    /**
     * get the time per row
     * @return the time per row
     */
    public int getTimePerRow() {
        return timePerRow;
    }

    /**
     * get the seat
     * @return the seat the passenger is on
     */
    public Cell getSeat() {
        return seat;
    }

    /**
     * Get the baggage time
     * @return get the time it take to put away baggage
     */
    public int getBaggageTime() {
        return baggageTime;
    }

    /**
     * Get the seat interference time
     * @return the seat interference time
     */
    public int getSeatInterferenceTime() {
        return seatInterferenceTime;
    }

    /**
     * Get the current cell
     * @return the current cell of the passenger
     */
    public Cell getCurrentCell() {
        return currentCell;
    }

    /**
     * Get if the passenger has taken their seat
     * @return true taken false not taken
     */
    public boolean isHasTakenSeat() {
        return hasTakenSeat;
    }

    /**
     * Set the current cell of the passenger
     * @param currentCell the current cell the passenger is on
     */
    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    /**
     * Set the aisle the passenger is on
     * @param aisle the aisle the passenger is on
     */
    public void setAisle(int aisle) {
        this.aisle = aisle;
    }
    
    /**
     * Get the aisle the passenger is on
     * @return the aisle the passenger is on
     */
    public int getAisle() {
        return this.aisle;
    }

    /**
     * Set if the passenger has taken their seat
     * @param hasTakenSeat true taken false not taken
     */
    public void setHasTakenSeat(boolean hasTakenSeat) {
        this.hasTakenSeat = hasTakenSeat;
    }

    /**
     * Set the seat interference time
     * @param seatInterferenceTime set the seat interference time
     */
    public void setSeatInterferenceTime(int seatInterferenceTime) {
        this.seatInterferenceTime = seatInterferenceTime;
    }

    /**
     * Decrease the timer per row by one
     */
    public void decreaseTimePerRow() {
        this.timePerRow--;
    }

    /**
     * Decrease the baggage time by one
     */
    public void decreaseBaggageTime() {
        this.baggageTime--;
    }

    /**
     * Decrease the seat interference by one
     */
    public void decreaseSeatInterferenceTime() {
        this.seatInterferenceTime--;
    }

    /**
     * Reset the time per row to the original
     */
    public void resetTimePerRow() {
        timePerRow = permTimePerRow;
    }

    /**
     * Get the number of interfering passengers
     * @return the number of interfering passengers
     */
    public int getInterferingPassengers() {
        return interferingPassengers;
    }

    /**
     * Add one interfering passenger
     */
    public void addInteferingPassengers() {
        this.interferingPassengers++;
    }    
}