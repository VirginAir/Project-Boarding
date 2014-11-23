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
 * @author Matthew
 */
public class BoardingHandler implements Runnable, ActionListener {
    
    // Plane information
    private final PlaneDimension planeDimension;
    private SeatingMethod seatingMethod;
    private ArrayList<Cell> seatingOrder;
    private ArrayList<Passenger> boardingPassengers;
    private ArrayList<Passenger> planePassengers;
    private ArrayList<Cell> seatsTaken;
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
    
    private boolean withTimer = false;
    private boolean hasCompleted = false;

    private SeatingMethod.DefaultSeatingMethod defaultMethod;
    private int[][] customMethod;

    public BoardingHandler(PlaneDimension planeDimension, SeatingMethod.DefaultSeatingMethod defaultMethod) {
        this.planeDimension = planeDimension;
        this.defaultMethod = defaultMethod;
        this.createClass();
    }
    
    public BoardingHandler(PlaneDimension planeDimension, int[][] customSeatingMethod) {
        this.planeDimension = planeDimension;
        this.customMethod = customSeatingMethod;
        this.createClass();
    }
    
    private void createClass() {
        this.seatingMethod = new SeatingMethod(this.planeDimension);
        this.boardingPassengers = new ArrayList<>();
        this.seatsTaken = new ArrayList<>();
        this.planePassengers = new ArrayList<>();
        this.seatVisualisation = this.planeDimension.getAllSeats();
        this.seatedPassengers = 0;
        this.r = new Random();
        this.totalTicks = 0;
        this.timer = new Timer(50, this);
    }
    
    public void setWithTimer(boolean withTimer) {
        this.withTimer = withTimer;
    }
    
    /**
     * Start the boarding process.
     */
    @Override
    public void run() {
        if (this.defaultMethod != null) {
            this.seatingOrder = this.seatingMethod.getDefaultSeatingOrder(this.defaultMethod);
        } else {
            this.seatingOrder = this.seatingMethod.getCustomSeatingOrder(this.customMethod);
        }
        
        // Record the initial time that the bording starts
        this.beginningBoardingTime = new DateTime();

        this.hasCompleted = false;
        
        // Start a timer which creates a new passenger every second
        if (this.withTimer) {
            timer = new Timer(50, this);
            timer.start();
        } else {
            while (!this.hasCompleted) {
                this.actionPerformed(null);
            }
        }
        
        while (!this.hasCompleted) {
            
        }
    }
    
    
    /**
     * Stop the boarding process.
     */
    public void stopBoarding() {
        // Record the initial time that the bording starts
        if (this.withTimer) {
            if (this.timer.isRunning()) {
                this.timer.stop();
            }
        }
        
        this.hasCompleted = true;
    }
    
    public void reset() {
        this.seatVisualisation = this.planeDimension.getAllSeats();
        boardingPassengers.clear();
        planePassengers.clear();
        seatsTaken.clear();
        seatedPassengers = 0;
        totalTicks = 0;
    }

    /**
     * Creates a new passenger every time the event is called.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("a");
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
            //seatVisualisation[passenger.getCurrentCell().getCellRow()][passenger.getCurrentCell().getCellColumn()].setHasPassenger(false);
            if (!passenger.isHasTakenSeat()) {
                if (passenger.getCurrentCell().getCellRow() != passenger.getSeat().getCellRow()) {
                    if (passenger.getTimePerRow() > 0) {
                        passenger.decreaseTimePerRow();
                    } else {
                        if (!seatVisualisation[passenger.getCurrentCell().getCellRow() + 1][passenger.getCurrentCell().getCellColumn()].getHasPassenger() & passenger.getTimePerRow() == 0) {
                            seatVisualisation[passenger.getCurrentCell().getCellRow()][passenger.getCurrentCell().getCellColumn()].setHasPassenger(false);
                            passenger.setCurrentCell(seatVisualisation[passenger.getCurrentCell().getCellRow() + 1][passenger.getCurrentCell().getCellColumn()]);
                            seatVisualisation[passenger.getCurrentCell().getCellRow()][passenger.getCurrentCell().getCellColumn()].setHasPassenger(true);
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
                                    System.out.println("Exception ");
                                }
                            }
                            seatVisualisation[passenger.getCurrentCell().getCellRow()][passenger.getCurrentCell().getCellColumn()].setHasPassenger(false);
                            passenger.setCurrentCell(passenger.getSeat());
                            seatVisualisation[passenger.getCurrentCell().getCellRow()][passenger.getCurrentCell().getCellColumn()].setHasPassenger(true);
                        }
                        passenger.decreaseSeatInterferenceTime();
                        if (passenger.getSeatInterferenceTime() < 0){
                            System.out.println("less than zero");
                        }
                        if (passenger.getSeatInterferenceTime() == 0) {
                            passenger.setHasTakenSeat(true);
                        }
                    }
                }
            } else {
                this.seatedPassengers +=1;
            }
            
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
            this.endBoardingTime = new DateTime();
            if (this.timer.isRunning()) {
                this.timer.stop();
            }
            System.out.println(this.totalTicks);
            
            this.hasCompleted = true;
            //System.out.println(this.totalBoardingTime().multipliedBy(20).getSeconds()+"s (\"real time\")"); // calculated real time
            if(this.totalTicks%60 == 0){
               System.out.println("Time taken: " + (int) Math.floor(this.totalTicks/60) + " minutes using " + this.seatingMethod.toString() + " seating method.");//calculated using one triggered action as a second time frame
            }else{
                System.out.println("Time taken: " + (int) Math.floor(this.totalTicks/60) + " minutes and " + this.totalTicks%60 + " seconds using " + this.seatingMethod.toString() + " seating method.");//calculated using one triggered action as a second time frame
            }
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
                smallestDifference = Math.abs(c.getCellColumn() - aisle);
                closestAisle = aisle;
            }
        }

        return closestAisle;
    }

    public Cell[][] getSeatVisualisation() {
        return this.seatVisualisation;
    }
    
    public ArrayList<Passenger> getPassengers() {
        return planePassengers;
    }
}
