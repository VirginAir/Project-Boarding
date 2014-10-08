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
        PlaneDimension planeDimension = new PlaneDimension(2, new int[]{2,2}, 23, new int[]{3,3});
        SeatingMethod seatingMethod = new SeatingMethod(SeatingMethod.DefaultSeatingMethod.RANDOM, planeDimension);
        
        BoardingController controller = new BoardingController(planeDimension, seatingMethod);
        controller.startBoarding();
        
    }
    
}
