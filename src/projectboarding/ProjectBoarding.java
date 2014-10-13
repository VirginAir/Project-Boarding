package projectboarding;

/**
 *
 * @author Matthew Kempson, Benjamin Cook, Matthew Horn
 */
public class ProjectBoarding {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create and run the boarding
        
        PlaneDimension planeDimension = new PlaneDimension(2, new int[]{2,0,2}, 23, new int[]{3,0,3});
        SeatingMethod seatingMethod = new SeatingMethod(SeatingMethod.DefaultSeatingMethod.RANDOM, planeDimension);
        Cell[][] seatVisualisation = planeDimension.getSeatVisualisation();
        
        BoardingController controller = new BoardingController(planeDimension, seatingMethod);
        controller.startBoarding();
        
    }
    
}
