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

    public int getTimePerRow() {
        return timePerRow;
    }

    public Cell getSeat() {
        return seat;
    }

    public int getBaggageTime() {
        return baggageTime;
    }

    public int getSeatInterferenceTime() {
        return seatInterferenceTime;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isHasTakenSeat() {
        return hasTakenSeat;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }
    
    public int getAisle() {
        return this.aisle;
    }

    public void setHasTakenSeat(boolean hasTakenSeat) {
        this.hasTakenSeat = hasTakenSeat;
    }

    public void setSeatInterferenceTime(int seatInterferenceTime) {
        this.seatInterferenceTime = seatInterferenceTime;
    }

    public void decreaseTimePerRow() {
        this.timePerRow--;
    }

    public void decreaseBaggageTime() {
        this.baggageTime--;
    }

    public void decreaseSeatInterferenceTime() {
        this.seatInterferenceTime--;
    }

    public void resetTimePerRow() {
        timePerRow = permTimePerRow;
    }

    public int getInterferingPassengers() {
        return interferingPassengers;
    }

    public void addInteferingPassengers() {
        this.interferingPassengers++;
    }    
}