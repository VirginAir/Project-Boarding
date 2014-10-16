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
public class BoardingController implements ActionListener{
    // Plane information
    private final PlaneDimension planeDimension;
    private final SeatingMethod seatingMethod;
    private final ArrayList<Cell> seatingOrder;
    private final ArrayList<Passenger> boardingPassengers;
    private final ArrayList<Cell> seatsTaken;
    private Cell[][] seatVisualisation;
    
    // Timing information
    private Timer timer;
    private DateTime beginningBoardingTime;
    private DateTime endBoardingTime;
    
    //Ticks for moving and checking passengers
    private int newPassenger=0;
    
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
    }        
    
    /**
     * Start the boarding process.
     */
    public void startBoarding() {
        // Record the initial time that the bording starts
        this.beginningBoardingTime = new DateTime();
        
        // Start a timer which creates a new passenger every second
        timer = new Timer(1000, this);
        timer.start();
    }
    
    /**
     * Creates a new passenger every time the event is called. 
     * 
     * @param e the action event calling the method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (newPassenger == 0){
        // Get a seat that the user can sit on
        Cell seat = this.seatingOrder.remove(0);
       
        // Create a new passenger object
        Passenger passenger = new Passenger(seat, 0.8);
        passenger.setAisle(closestAisle(passenger.getSeat()));
                    
        // Add the passenger object to the list of boarding passengers
        this.boardingPassengers.add(passenger);
            newPassenger = 5;
        } else {
            newPassenger--;
        }
        if (this.seatingOrder.isEmpty()) {
            // End the timer
            this.timer.stop();
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
     * The passenger has taken their seat so update the number of seats remaining.
     * 
     * @param seat the seat the passenger has taken.
     */
    public void takeSeat(Cell seat) {
        this.seatsTaken.add(seat);
        
        if (this.seatsTaken.size() == this.planeDimension.totalNumberOfSeats()) {
            this.finishedBoarding();
        }
    }
    
    public int closestAisle(Cell c){
        ArrayList<Integer> aisleList = new ArrayList<>();
        int smallestDifference = 10;
        int closestAisle = 0;
        for (int i = 0; i < seatVisualisation[0].length;i++) {
            if (seatVisualisation[0][i].getCellType().equals(Cell.CellType.AISLE)) {
                aisleList.add(i);
            }
        }
        
        for (Integer aisle : aisleList) {
            if (Math.abs(c.getCellColumn()-aisle) <= smallestDifference){
                closestAisle = aisle;
            }
        }
        
        return closestAisle;
    }
    
    public Cell[][] getSeatVisualisation(){
        int totalRows = this.planeDimension.getNumberOfRows() + this.planeDimension.getNumberOfPriorityRows();
        int totalColumns = this.planeDimension.getNumberOfColumns() + this.planeDimension.getNumberOfAisles();
        
        seatVisualisation = new Cell[totalRows][totalColumns];
        
        int rowLocation = 0;
        for(int i = 0; i < totalRows; i++)
        {
            for(int j = 0; j < totalColumns; j++)
            {
                seatVisualisation[i][j] = new Cell(i, j, Cell.CellType.SEAT);
            }
        }
        for (int column : this.planeDimension.getColumnSplit()) {
            
            if(column > 0){
                rowLocation += column;
            } else {
                for (int i=0; i < totalRows; i++){
                    seatVisualisation[i][rowLocation].setCellType(Cell.CellType.AISLE);
                }
            }
        }
        int emptySpaces = this.planeDimension.getNumberOfColumns() - this.planeDimension.getNumberOfPriorityColumns();
        for (int i = 0; i < this.planeDimension.getNumberOfPriorityRows(); i++) {
            for (int j = 0; j < totalColumns; j++) {
                if(seatVisualisation[i][j].getCellType().equals(Cell.CellType.SEAT)){
                    if (((emptySpaces/2)-1) < j && j < (totalColumns-(emptySpaces/2))){
                        seatVisualisation[i][j].setCellType(Cell.CellType.PRIORITY_SEAT);
                    } else {
                        seatVisualisation[i][j].setCellType(Cell.CellType.NONE);
                    }
                }
            }
            
        }
        return seatVisualisation;
    }
    
    public class Passenger {
        private Cell seat;
        private boolean hasBaggage;
        private boolean hasTakenSeat;
        private int baggageTime;
        private int timePerRow;
        private SeatInterference seatInterference;
        private int seatInterferenceTime;
        private int aisle;
        
        
    
        public Passenger(Cell seat, double hasBaggageWeight) {
            Random r = new Random();
            this.seat = seat;
            if (r.nextDouble() < hasBaggageWeight){
                this.hasBaggage = true;
            } else { 
                this.hasBaggage = false;
            }
            this.hasTakenSeat = false;
            if (this.hasBaggage) {
                baggageTime = r.nextInt(16)+4;
            } else {
                baggageTime = 0;
            }
            timePerRow = r.nextInt(3)+2;
            seatInterference = SeatInterference.NONE;
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

        public boolean isHasBaggage() {
            return hasBaggage;
        }

        public boolean isHasTakenSeat() {
            return hasTakenSeat;
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
        
        public void decreaseTimePerRow(){
            this.timePerRow--;
        }
        
        public void decreaseBaggageTime(){
            this.baggageTime--;
        }
        
        public void decreaseSeatInterferenceTime(){
            this.seatInterferenceTime--;
        }
    }
}
