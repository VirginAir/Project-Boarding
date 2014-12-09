/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectboarding;

import java.util.Random;

/**
 *
 * @author Matthew
 */
public class Passenger {
    
    public enum SeatInterference {
        MIDDLE, AISLE, MIDDLE_AISLE, NONE
    }

        private Cell seat;
        private Cell currentCell;
        private boolean hasBaggage;
        private boolean hasTakenSeat;
        private int baggageTime;
        private int timePerRow;
        public int permTimePerRow;
        private SeatInterference seatInterference;
        private int seatInterferenceTime;
        private int aisle;
        private int interferingPassengers;

        public Passenger(Cell seat, double hasBaggageWeight) {
            Random r = new Random();
            this.seat = seat;
            if (r.nextDouble() < hasBaggageWeight) {
                this.hasBaggage = true;
                baggageTime = r.nextInt(16) + 4;
            } else {
                this.hasBaggage = false;
                baggageTime = 0;
            }
            this.hasTakenSeat = false;

            timePerRow = r.nextInt(3) + 2;
            permTimePerRow = timePerRow;
            seatInterference = SeatInterference.NONE;
            seatInterferenceTime = 0;
            interferingPassengers = 0;
        }

        public int getTimePerRow() {
            return timePerRow;
        }

        public SeatInterference getSeatInterference() {
            return seatInterference;
        }

        public Cell getSeat() {
            return seat;
        }

        public int getBaggageTime() {
            return baggageTime;
        }

        public int getAisle() {
            return aisle;
        }

        public int getSeatInterferenceTime() {
            return seatInterferenceTime;
        }

        public Cell getCurrentCell() {
            return currentCell;
        }

        public boolean isHasBaggage() {
            return hasBaggage;
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

        public void setBaggageTime(int baggageTime) {
            this.baggageTime = baggageTime;
        }

        public void setHasBaggage(boolean hasBaggage) {
            this.hasBaggage = hasBaggage;
        }

        public void setHasTakenSeat(boolean hasTakenSeat) {
            this.hasTakenSeat = hasTakenSeat;
        }

        public void setSeat(Cell seat) {
            this.seat = seat;
        }

        public void setSeatInterference(SeatInterference seatInterference) {
            this.seatInterference = seatInterference;
        }

        public void setTimePerRow(int timePerRow) {
            this.timePerRow = timePerRow;
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
            
            //if (this.seatInterferenceTime < 0) {
            //    this.seatInterferenceTime = 0;
            //}
        }

        public void resetTimePerRow() {
            timePerRow = permTimePerRow;
        }

        public int getInterferingPassengers() {
            return interferingPassengers;
        }

        public int getPermTimePerRow() {
            return permTimePerRow;
        }

        public void setInterferingPassengers(int interferingPassengers) {
            this.interferingPassengers = interferingPassengers;
        }

        public void setPermTimePerRow(int permTimePerRow) {
            this.permTimePerRow = permTimePerRow;
        }

        public void addInteferingPassengers() {
            this.interferingPassengers++;
        }
        
        
    }
