package projectboarding;

import java.util.ArrayList;

/**
 *
 * @author Matthew
 */
public class BoardingController {
    // Plane dimentions
    int columns = 6;
    int rows = 10;
    
    // Passenger information
    int numberOfPassengers = 60;
    ArrayList<Integer> boardingPassengers; // Integer only temporary, change to Passenger class
    
    // Plane seating information
    boolean[][] planeSeating;
    int numberOfBoardedPassengers;
           
    public BoardingController() {
        // Initalise variables
        this.planeSeating = new boolean[this.columns][this.rows];
        this.boardingPassengers = new ArrayList<>();
        this.numberOfBoardedPassengers = 0;
    }        
    
    /**
     * Start the boarding process 
     */
    public void startBoarding() {
        // start a timer
        // create a passenger object every x seconds
    }
    
    /**
     * End the boarding process
     */
    public void finishedBoarding() {
        // end a timer
    }
    
    /**
     * The passenger has taken their seat so update the seating plan
     * 
     * @param x the row of the passenger
     * @param y the column of the passenger
     */
    public void seatTaken(int x, int y) {
        // Check seating numbers given are vaild
        if ((x >= 0 || x < this.columns) && (y >= 0 || y < this.rows)) {
            // Update the plane seating to true and the number of boarded passengers
            this.planeSeating[x][y] = true;
            this.numberOfBoardedPassengers++;
            
            // Check if we have boarded everybody
            if (this.numberOfBoardedPassengers == this.numberOfPassengers) {
                // Call the finished boarding method
                this.finishedBoarding();
            }
        }
    }
}
