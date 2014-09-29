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
        BoardingController controller = new BoardingController();
        controller.startBoarding();
        
    }
    
}
