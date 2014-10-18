package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import projectboarding.Cell.CellType;

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
        
        final GLProfile profile = GLProfile.get(GLProfile.GL4);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);
        capabilities.setHardwareAccelerated(true);
        
        GLCanvas canvas = new GLCanvas(capabilities);
        FPSAnimator animator = new FPSAnimator(canvas, FPS);
        GLRender renderer = new GLRender();
        
        GLWindow window = new GLWindow("Project-Boarding", animator, WINDOW_HEIGHT, WINDOW_WIDTH);
        
        canvas.addGLEventListener(renderer);
        
        window.setGLCanvas(canvas, BorderLayout.CENTER);
        animator.start();
        window.setVisibility(true);
        
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
        
        PlaneDimension planeDimension = new PlaneDimension(
                new Cell[][]{priorityRow, priorityRow1, normalRow, normalRow1, normalRow2});
        SeatingMethod seatingMethod = new SeatingMethod(SeatingMethod.DefaultSeatingMethod.RANDOM, planeDimension);
        ArrayList<Cell> seats = seatingMethod.getSeatingOrder();
        
        BoardingController controller = new BoardingController(planeDimension, seatingMethod);
        Cell[][] seatVisualisation = controller.getSeatVisualisation();
        controller.startBoarding();
        
    }
    
}
