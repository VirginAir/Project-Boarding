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
    // Decision type enum
    public enum DecisionType{
        RANDOM //Add more types here
    }
    
    // Descision type
    private DecisionType decisionType;
    
    // Plane dimentions
    private int columns = 6;
    private int rows = 10;
    
    // Passenger information
    private int numberOfPassengers = 60;
    private ArrayList<Passenger> boardingPassengers;
    
    // Plane seating information
    private boolean[][] planeSeating;
    private ArrayList<Seat> availableSeats;
    private int numberOfBoardedPassengers;
    
    // Timing information
    private DateTime beginningBoardingTime;
    private DateTime endBoardingTime;
           
    public BoardingController() {
        // Initalise variables
        this.planeSeating = new boolean[this.rows][this.columns];
        this.boardingPassengers = new ArrayList<>();
        this.numberOfBoardedPassengers = 0;
        this.decisionType = DecisionType.RANDOM;
        
        this.availableSeats = new ArrayList<>();
        // Create the seats
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                Seat seat = new Seat(row, column);
                this.availableSeats.add(seat);
            }
        }
    }        
    
    /**
     * Start the boarding process.
     */
    public void startBoarding() {
        // Record the initial time that the bording starts
        this.beginningBoardingTime = new DateTime();
        
        // Start a timer which creates a new passenger every second
        Timer timer = new Timer(1000, this);
        timer.start();
        
        this.displayPlaneSimple();
    }
    
    /**
     * Creates a new passenger every time the event is called. 
     * 
     * @param e the action event calling the method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get a seat that the user can sit on
        Seat seat = this.decideSeat(this.decisionType);
       
        // Create a new passenger object
        Passenger passenger = new Passenger(seat, false);
        
        // Add the passenger object to the list of boarding passengers
        this.boardingPassengers.add(passenger);
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
     * Based on the seating method, this allocates a seat based on the currently
     * available seats.
     * 
     * @param type the seating method
     * @return a seat for the passenger
     */
    public Seat decideSeat(DecisionType type) {
        Seat seat = null;
        
        if (type == DecisionType.RANDOM) {
            // Choose a random seat
            Random randomGenerator = new Random();
            int seatNumber = randomGenerator.nextInt(this.availableSeats.size());
            
            // Get the seat and remove from the list of available seats
            seat = this.availableSeats.remove(seatNumber);
        }
        
        // Return the seat for the passenger
        return seat;
    }
    
    /**
     * The passenger has taken their seat so update the seating plan
     * 
     * @param seat the seat assigned to the passenger
     * @return true if correct seat number, false otherwise
     */
    public boolean takeSeat(Seat seat) {
        // Check seating numbers given are vaild
        if ((seat.getSeatRow() >= 0 || seat.getSeatRow() < this.rows) 
                && (seat.getSeatColumn() >= 0 || seat.getSeatColumn() < this.columns)) {
            // Update the plane seating to true and the number of boarded passengers
            this.planeSeating[seat.getSeatRow()][seat.getSeatColumn()] = true;
            this.numberOfBoardedPassengers++;
            
            // Check if we have boarded everybody
            if (this.numberOfBoardedPassengers == this.numberOfPassengers) {
                // Call the finished boarding method
                this.finishedBoarding();
            }
            
            // Print out the updated plane
            this.displayPlaneSimple();
            
            // Successful
            return true;
        }
        
        // Unsuccessful
        return false;
    }
    
    /**
     * Displays the plane seating with indicating if the seats are taken.
     */
    public void displayPlaneSimple() {
        String outputString = "";
        
        // Loop through all of the rows and the columns of the plane
        for (int currentRow = 0; currentRow < this.rows; currentRow++) {
            for (int currentColumn = 0; currentColumn < this.columns; currentColumn++) {
                // Insert a tab to show the aisle // Change this to be non-hardcoded
                if (currentColumn == 3) {
                    outputString += "\t";
                }
                
                // Check which output to print
                if (this.planeSeating[currentRow][currentColumn]) {
                    outputString += "1 ";
                } else {
                    outputString += "0 ";
                }
            }
            
            // Insert a new line for the next row
            if (currentRow != this.rows - 1) {
                outputString += "\n";
            }
        }
        
        // Display the plane
        System.out.println(outputString);
    }
    
    public class Passenger {
    private Seat seat;
    private boolean hasBaggage;
    
    public Passenger(Seat seat, boolean hasBaggage) {
        this.seat = seat;
        this.hasBaggage = hasBaggage;
    }
}
}
