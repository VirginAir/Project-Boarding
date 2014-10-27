package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import projectboarding.Cell.CellType;
import projectboarding.SeatingMethod.DefaultSeatingMethod;

/**
 *
 * @author Matthew Kempson, Benjamin Cook, Matthew Horn
 */
public class ProjectBoarding {

    private static final int WINDOW_HEIGHT = 800;
    private static final int WINDOW_WIDTH = 600;
    private static final int FPS = 60;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
              
        // Create and run the boarding
        // DONT WORRY THIS WILL ALL BE REMOVED, IT SHOULD BE CREATED PROGRAMMATICALLY
        // JUST HERE TO BE USED AS A TEST
        Cell[] priorityRow = new Cell[]{new Cell(0,0,CellType.NONE),
            new Cell(0,1,CellType.PRIORITY_SEAT), 
            new Cell(0,2,CellType.PRIORITY_SEAT), 
            new Cell(0,3,CellType.AISLE), 
            new Cell(0,4,CellType.PRIORITY_SEAT), 
            new Cell(0,5,CellType.PRIORITY_SEAT), 
            new Cell(0,6,CellType.NONE)};
        Cell[] priorityRow1 = new Cell[]{new Cell(1,0,CellType.NONE),
            new Cell(1,1,CellType.PRIORITY_SEAT), 
            new Cell(1,2,CellType.PRIORITY_SEAT), 
            new Cell(1,3,CellType.AISLE), 
            new Cell(1,4,CellType.PRIORITY_SEAT), 
            new Cell(1,5,CellType.PRIORITY_SEAT), 
            new Cell(1,6,CellType.NONE)};
        Cell[] normalRow = new Cell[]{new Cell(2,0,CellType.SEAT),
            new Cell(2,1,CellType.SEAT), 
            new Cell(2,2,CellType.SEAT), 
            new Cell(2,3,CellType.AISLE), 
            new Cell(2,4,CellType.SEAT), 
            new Cell(2,5,CellType.SEAT), 
            new Cell(2,6,CellType.SEAT)};
        Cell[] normalRow1 = new Cell[]{new Cell(3,0,CellType.SEAT),
            new Cell(3,1,CellType.SEAT), 
            new Cell(3,2,CellType.SEAT), 
            new Cell(3,3,CellType.AISLE), 
            new Cell(3,4,CellType.SEAT), 
            new Cell(3,5,CellType.SEAT), 
            new Cell(3,6,CellType.SEAT)};
        Cell[] normalRow2 = new Cell[]{new Cell(4,0,CellType.SEAT),
            new Cell(4,1,CellType.SEAT), 
            new Cell(4,2,CellType.SEAT), 
            new Cell(4,3,CellType.AISLE), 
            new Cell(4,4,CellType.SEAT), 
            new Cell(4,5,CellType.SEAT), 
            new Cell(4,6,CellType.SEAT)};
        Cell[] normalRow3 = new Cell[]{new Cell(5,0,CellType.SEAT),
            new Cell(5,1,CellType.SEAT), 
            new Cell(5,2,CellType.SEAT), 
            new Cell(5,3,CellType.AISLE), 
            new Cell(5,4,CellType.SEAT), 
            new Cell(5,5,CellType.SEAT), 
            new Cell(5,6,CellType.SEAT)};
        Cell[] normalRow4 = new Cell[]{new Cell(6,0,CellType.SEAT),
            new Cell(6,1,CellType.SEAT), 
            new Cell(6,2,CellType.SEAT), 
            new Cell(6,3,CellType.AISLE), 
            new Cell(6,4,CellType.SEAT), 
            new Cell(6,5,CellType.SEAT), 
            new Cell(6,6,CellType.SEAT)};
        
        PlaneDimension planeDimension = new PlaneDimension(
                new Cell[][]{priorityRow, priorityRow1, normalRow, normalRow1, normalRow2, normalRow3, normalRow4});
        SeatingMethod seatingMethod = new SeatingMethod(planeDimension);
        
        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.RANDOM);
        BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.BACK_TO_FRONT);
        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.BLOCK_BOARDING);
        //oardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.BY_SEAT);
        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.OUTSIDE_IN);
        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.ROTATING_ZONE);
        Cell[][] seatVisualisation = controller.getSeatVisualisation();
        
        //Setup Window
        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);
        capabilities.setHardwareAccelerated(true);
        
        GLCanvas canvas = new GLCanvas(capabilities);
        FPSAnimator animator = new FPSAnimator(canvas, FPS);
        GLRender renderer = new GLRender(seatVisualisation, controller.getPassengers());
        
        GLWindow window = new GLWindow("Project-Boarding", animator, WINDOW_HEIGHT, WINDOW_WIDTH);
        
        canvas.addGLEventListener(renderer);
        
        window.setGLCanvas(canvas, BorderLayout.CENTER);
        animator.start();
        window.setVisibility(true);
        
        //Start Simulation
        controller.startBoarding();
    }
}
