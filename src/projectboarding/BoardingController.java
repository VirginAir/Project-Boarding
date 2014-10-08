package projectboarding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private final ArrayList<Seat> seatingOrder;
    private final ArrayList<Passenger> boardingPassengers;
    private final ArrayList<Seat> seatsTaken;
    
    // Timing information
    private Timer timer;
    private DateTime beginningBoardingTime;
    private DateTime endBoardingTime;
           
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
        // Get a seat that the user can sit on
        Seat seat = this.seatingOrder.remove(0);
       
        // Create a new passenger object
        Passenger passenger = new Passenger(seat, false);
        
        // Add the passenger object to the list of boarding passengers
        this.boardingPassengers.add(passenger);
        
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
    public void takeSeat(Seat seat) {
        this.seatsTaken.add(seat);
        
        if (this.seatsTaken.size() == this.planeDimension.totalNumberOfSeats()) {
            this.finishedBoarding();
        }
    }
    
    public class Passenger {
        private Seat seat;
        private boolean hasBaggage;
        private boolean hasTakenSeat;
    
        public Passenger(Seat seat, boolean hasBaggage) {
            this.seat = seat;
            this.hasBaggage = hasBaggage;
            this.hasTakenSeat = false;
        }
    }
}
