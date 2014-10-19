package projectboarding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 *
 * @author Matthew Kempson
 */
public class BoardingController implements ActionListener {
    // Plane information

    private final PlaneDimension planeDimension;
    private final SeatingMethod seatingMethod;
    private final ArrayList<Cell> seatingOrder;
    private final ArrayList<Passenger> boardingPassengers;
    private final ArrayList<Passenger> planePassengers;
    private final ArrayList<Cell> seatsTaken;
    private Cell[][] seatVisualisation;
    // Timing information
    private Timer timer;
    private DateTime beginningBoardingTime;
    private DateTime endBoardingTime;
    //Ticks for moving and checking passengers
    private int newPassenger = 0;
    private Random r;
    private int seatedPassengers;
    private int totalTicks;

    public enum SeatInterference {

        MIDDLE, AISLE, MIDDLE_AISLE, NONE
    }

    public BoardingController(PlaneDimension planeDimension, SeatingMethod seatingMethod) {
        // Initalise variables
        this.planeDimension = planeDimension;
        this.seatingMethod = seatingMethod;
        this.seatingOrder = this.seatingMethod.getSeatingOrder();
        this.boardingPassengers = new ArrayList<>();
        this.seatsTaken = new ArrayList<>();
        this.planePassengers = new ArrayList<>();
        this.seatVisualisation = this.planeDimension.getAllSeats();
        this.seatedPassengers = 0;
        this.r = new Random();
        this.totalTicks = 0;
        
    }

    /**
     * Start the boarding process.
     */
    public void startBoarding() {
        // Record the initial time that the bording starts
        this.beginningBoardingTime = new DateTime();

        // Start a timer which creates a new passenger every second
        timer = new Timer(50, this);
        timer.start();
    }

    /**
     * Creates a new passenger every time the event is called.
     *
     * @param e the action event calling the method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.seatedPassengers = 0;
        if (this.newPassenger == 0 && !this.seatingOrder.isEmpty()) {
            // Get a seat that the user can sit on
            Cell seat = this.seatingOrder.remove(0);

            // Create a new passenger object
            Passenger passenger = new Passenger(seat, 0.8);
            passenger.setAisle(closestAisle(passenger.getSeat()));

            if (seatVisualisation[0][passenger.getAisle()].getHasPassenger() || this.boardingPassengers.size() > 0) {
                // Add the passenger object to the list of boarding passengers
                this.boardingPassengers.add(passenger);
            } else {
                passenger.setCurrentCell(seatVisualisation[0][passenger.getAisle()]);
                seatVisualisation[passenger.getCurrentCell().getCellRow()][passenger.getCurrentCell().getCellColumn()].setHasPassenger(true);
                planePassengers.add(passenger);
            }

            newPassenger = 5;
        } else if (this.newPassenger > 0 && !this.seatingOrder.isEmpty()) {
            newPassenger--;
        } else {
        }

        for (Passenger passenger : planePassengers) {
            seatVisualisation[passenger.getCurrentCell().getCellRow()][passenger.getCurrentCell().getCellColumn()].setHasPassenger(false);
            if (!passenger.isHasTakenSeat()) {
                if (passenger.getCurrentCell().getCellRow() != passenger.getSeat().getCellRow()) {
                    if (passenger.timePerRow > 0) {
                        passenger.decreaseTimePerRow();
                    } else {
                        if (!seatVisualisation[passenger.getCurrentCell().getCellRow() + 1][passenger.getCurrentCell().getCellColumn()].getHasPassenger() & passenger.getTimePerRow() == 0) {
                            passenger.setCurrentCell(seatVisualisation[passenger.getCurrentCell().getCellRow() + 1][passenger.getCurrentCell().getCellColumn()]);
                            passenger.resetTimePerRow();
                        }
                    }
                } else if (passenger.getCurrentCell().getCellRow() == passenger.getSeat().getCellRow()) {
                    if (passenger.getBaggageTime() > 0) {
                        passenger.decreaseBaggageTime();
                    } else {
                        if (passenger.getSeatInterferenceTime() == 0 && !passenger.isHasTakenSeat()) {
                            for (Passenger p1 : planePassengers) {
                                if (passenger.getCurrentCell().getCellRow() == p1.getCurrentCell().getCellRow()) {
                                    if (((passenger.getSeat().getCellColumn() < p1.getSeat().getCellColumn()) && (p1.getSeat().getCellColumn()< passenger.getAisle())) || ((passenger.getSeat().getCellColumn() > p1.getSeat().getCellColumn()) && (p1.getSeat().getCellColumn() > passenger.getAisle()))) {
                                        passenger.addInteferingPassengers();
                                    }
                                }
                            }
                            if (passenger.getInterferingPassengers() == 0) {
                                passenger.setSeatInterferenceTime(2);
                            } else if (passenger.getInterferingPassengers() == 1) {
                                if (passenger.getAisle() > passenger.getSeat().getCellColumn()) {
                                    for (Passenger p1 : planePassengers) {
                                        if (p1.getCurrentCell().getCellRow() == passenger.getCurrentCell().getCellRow() && p1.getCurrentCell().getCellColumn() == passenger.getCurrentCell().getCellColumn() - 1) {
                                            passenger.setSeatInterferenceTime(r.nextInt(6) + 2);
                                        } else if (p1.getCurrentCell().getCellRow() == passenger.getCurrentCell().getCellRow() && p1.getCurrentCell().getCellColumn() == passenger.getCurrentCell().getCellColumn() - 2) {
                                            passenger.setSeatInterferenceTime(r.nextInt(8) + 2);
                                        }
                                    }
                                } else if (passenger.getAisle() < passenger.getSeat().getCellColumn()) {
                                    for (Passenger p1 : planePassengers) {
                                        if (p1.getCurrentCell().getCellRow() == passenger.getCurrentCell().getCellRow() && p1.getCurrentCell().getCellColumn() == passenger.getCurrentCell().getCellColumn() + 1) {
                                            passenger.setSeatInterferenceTime(r.nextInt(6) + 2);
                                        } else if (p1.getCurrentCell().getCellRow() == passenger.getCurrentCell().getCellRow() && p1.getCurrentCell().getCellColumn() == passenger.getCurrentCell().getCellColumn() + 2) {
                                            passenger.setSeatInterferenceTime(r.nextInt(8) + 2);
                                        }
                                    }
                                }
                            } else if (passenger.getInterferingPassengers() == 2) {
                                try{
                                    passenger.setSeatInterferenceTime(r.nextInt(15) + 5);
                                } catch (Exception ex) {
                                    System.out.println("");
                                }
                            }
                            passenger.setCurrentCell(passenger.getSeat());
                        }
                        passenger.decreaseSeatInterferenceTime();
                        if (passenger.getSeatInterferenceTime() < 0){
                            System.out.println("");
                        }
                        if (passenger.getSeatInterferenceTime() == 0) {
                            passenger.setHasTakenSeat(true);
                        }
                    }
                }
            } else {
                this.seatedPassengers +=1;
            }
            seatVisualisation[passenger.getCurrentCell().getCellRow()][passenger.getCurrentCell().getCellColumn()].setHasPassenger(true);
        }
        
        if(!boardingPassengers.isEmpty() && !this.seatVisualisation[0][boardingPassengers.get(0).getAisle()].getHasPassenger()){
            Passenger passenger = boardingPassengers.remove(0);
            passenger.setCurrentCell(seatVisualisation[0][passenger.getAisle()]);
                seatVisualisation[passenger.getCurrentCell().getCellRow()][passenger.getCurrentCell().getCellColumn()].setHasPassenger(true);
                planePassengers.add(passenger);
            
        }




        this.totalTicks ++;
        if (this.seatedPassengers == this.planeDimension.getNumberOfPrioritySeats()+this.planeDimension.getNumberOfNormalSeats()) {
            // End the timer
            this.timer.stop();
            this.endBoardingTime = new DateTime();
            System.out.println(this.totalBoardingTime().multipliedBy(20).getSeconds()+"s (\"real time\")"); // calculated real time
            System.out.println(this.totalTicks + "s (calculated)");//calculated using one triggered action as a second time frame
        }
    }

    /**
     * End the boarding process.
     */
    public void finishedBoarding() {
        // Record the time that the bording finishes
        this.endBoardingTime = new DateTime();
    }

    /**
     * Returns total time taken to board the plane in seconds.
     *
     * @return time difference in seconds
     */
    public Seconds totalBoardingTime() {
        return Seconds.secondsBetween(this.beginningBoardingTime, this.endBoardingTime);
    }

    /**
     * The passenger has taken their seat so update the number of seats
     * remaining.
     *
     * @param seat the seat the passenger has taken.
     */
    public void takeSeat(Cell seat) {
        this.seatsTaken.add(seat);

        if (this.seatsTaken.size() == this.planeDimension.totalNumberOfSeats()) {
            this.finishedBoarding();
        }
    }

    public int closestAisle(Cell c) {
        ArrayList<Integer> aisleList = new ArrayList<>();
        int smallestDifference = 10;
        int closestAisle = 0;
        for (int i = 0; i < seatVisualisation[0].length; i++) {
            if (seatVisualisation[0][i].getCellType().equals(Cell.CellType.AISLE)) {
                aisleList.add(i);
            }
        }

        for (Integer aisle : aisleList) {
            if (Math.abs(c.getCellColumn() - aisle) <= smallestDifference) {
                closestAisle = aisle;
            }
        }

        return closestAisle;
    }

    public Cell[][] getSeatVisualisation() {
        return this.seatVisualisation;
    }

    public class Passenger {

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
}
