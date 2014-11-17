package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import projectboarding.Cell.CellType;
import projectboarding.SeatingMethod.DefaultSeatingMethod;
import sceneobjects.Scene;

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
            new Cell(0,2,CellType.AISLE),
            new Cell(0,3,CellType.PRIORITY_SEAT),
            new Cell(0,4,CellType.PRIORITY_SEAT), 
            new Cell(0,5,CellType.PRIORITY_SEAT), 
            new Cell(0,6,CellType.PRIORITY_SEAT),
            new Cell(0,7,CellType.AISLE),
            new Cell(0,8,CellType.PRIORITY_SEAT),
            new Cell(0,9,CellType.NONE)};
        Cell[] priorityRow1 = new Cell[]{new Cell(1,0,CellType.NONE),
            new Cell(1,1,CellType.PRIORITY_SEAT), 
            new Cell(1,2,CellType.AISLE),
            new Cell(1,3,CellType.PRIORITY_SEAT),
            new Cell(1,4,CellType.PRIORITY_SEAT), 
            new Cell(1,5,CellType.PRIORITY_SEAT), 
            new Cell(1,6,CellType.PRIORITY_SEAT),
            new Cell(1,7,CellType.AISLE),
            new Cell(1,8,CellType.PRIORITY_SEAT),
            new Cell(1,9,CellType.NONE)};
        Cell[] normalRow = new Cell[]{new Cell(2,0,CellType.SEAT),
            new Cell(2,1,CellType.SEAT), 
            new Cell(2,2,CellType.AISLE),
            new Cell(2,3,CellType.SEAT),
            new Cell(2,4,CellType.SEAT), 
            new Cell(2,5,CellType.SEAT), 
            new Cell(2,6,CellType.SEAT),
            new Cell(2,7,CellType.AISLE),
            new Cell(2,8,CellType.SEAT),
            new Cell(2,9,CellType.SEAT)};
        Cell[] normalRow1 = new Cell[]{new Cell(3,0,CellType.SEAT),
            new Cell(3,1,CellType.SEAT), 
            new Cell(3,2,CellType.AISLE),
            new Cell(3,3,CellType.SEAT),
            new Cell(3,4,CellType.SEAT), 
            new Cell(3,5,CellType.SEAT), 
            new Cell(3,6,CellType.SEAT),
            new Cell(3,7,CellType.AISLE),
            new Cell(3,8,CellType.SEAT),
            new Cell(3,9,CellType.SEAT)};
        Cell[] normalRow2 = new Cell[]{new Cell(4,0,CellType.SEAT),
            new Cell(4,1,CellType.SEAT), 
            new Cell(4,2,CellType.AISLE),
            new Cell(4,3,CellType.SEAT),
            new Cell(4,4,CellType.SEAT), 
            new Cell(4,5,CellType.SEAT), 
            new Cell(4,6,CellType.SEAT),
            new Cell(4,7,CellType.AISLE),
            new Cell(4,8,CellType.SEAT),
            new Cell(4,9,CellType.SEAT)};
        Cell[] normalRow3 = new Cell[]{new Cell(5,0,CellType.SEAT),
            new Cell(5,1,CellType.SEAT), 
            new Cell(5,2,CellType.AISLE),
            new Cell(5,3,CellType.SEAT),
            new Cell(5,4,CellType.SEAT), 
            new Cell(5,5,CellType.SEAT), 
            new Cell(5,6,CellType.SEAT),
            new Cell(5,7,CellType.AISLE),
            new Cell(5,8,CellType.SEAT),
            new Cell(5,9,CellType.SEAT)};
        Cell[] normalRow4 = new Cell[]{new Cell(6,0,CellType.SEAT),
            new Cell(6,1,CellType.SEAT), 
            new Cell(6,2,CellType.AISLE),
            new Cell(6,3,CellType.SEAT),
            new Cell(6,4,CellType.SEAT), 
            new Cell(6,5,CellType.SEAT), 
            new Cell(6,6,CellType.SEAT),
            new Cell(6,7,CellType.AISLE),
            new Cell(6,8,CellType.SEAT),
            new Cell(6,9,CellType.SEAT)};
        Cell[] normalRow5 = new Cell[]{new Cell(7,0,CellType.SEAT),
            new Cell(7,1,CellType.SEAT), 
            new Cell(7,2,CellType.AISLE),
            new Cell(7,3,CellType.SEAT),
            new Cell(7,4,CellType.SEAT), 
            new Cell(7,5,CellType.SEAT), 
            new Cell(7,6,CellType.SEAT),
            new Cell(7,7,CellType.AISLE),
            new Cell(7,8,CellType.SEAT),
            new Cell(7,9,CellType.SEAT)};
        Cell[] normalRow6 = new Cell[]{new Cell(8,0,CellType.SEAT),
            new Cell(8,1,CellType.SEAT), 
            new Cell(8,2,CellType.AISLE),
            new Cell(8,3,CellType.SEAT),
            new Cell(8,4,CellType.SEAT), 
            new Cell(8,5,CellType.SEAT), 
            new Cell(8,6,CellType.SEAT),
            new Cell(8,7,CellType.AISLE),
            new Cell(8,8,CellType.SEAT),
            new Cell(8,9,CellType.SEAT)};
        Cell[] normalRow7 = new Cell[]{new Cell(9,0,CellType.SEAT),
            new Cell(9,1,CellType.SEAT), 
            new Cell(9,2,CellType.AISLE),
            new Cell(9,3,CellType.SEAT),
            new Cell(9,4,CellType.SEAT), 
            new Cell(9,5,CellType.SEAT), 
            new Cell(9,6,CellType.SEAT),
            new Cell(9,7,CellType.AISLE),
            new Cell(9,8,CellType.SEAT),
            new Cell(9,9,CellType.SEAT)};
        Cell[] normalRow8 = new Cell[]{new Cell(10,0,CellType.SEAT),
            new Cell(10,1,CellType.SEAT), 
            new Cell(10,2,CellType.AISLE),
            new Cell(10,3,CellType.SEAT),
            new Cell(10,4,CellType.SEAT), 
            new Cell(10,5,CellType.SEAT), 
            new Cell(10,6,CellType.SEAT),
            new Cell(10,7,CellType.AISLE),
            new Cell(10,8,CellType.SEAT),
            new Cell(10,9,CellType.SEAT)};
        Cell[] normalRow9 = new Cell[]{new Cell(11,0,CellType.SEAT),
            new Cell(11,1,CellType.SEAT), 
            new Cell(11,2,CellType.AISLE),
            new Cell(11,3,CellType.SEAT),
            new Cell(11,4,CellType.SEAT), 
            new Cell(11,5,CellType.SEAT), 
            new Cell(11,6,CellType.SEAT),
            new Cell(11,7,CellType.AISLE),
            new Cell(11,8,CellType.SEAT),
            new Cell(11,9,CellType.SEAT)};
        Cell[] normalRow10 = new Cell[]{new Cell(12,0,CellType.SEAT),
            new Cell(12,1,CellType.SEAT), 
            new Cell(12,2,CellType.AISLE),
            new Cell(12,3,CellType.SEAT),
            new Cell(12,4,CellType.SEAT), 
            new Cell(12,5,CellType.SEAT), 
            new Cell(12,6,CellType.SEAT),
            new Cell(12,7,CellType.AISLE),
            new Cell(12,8,CellType.SEAT),
            new Cell(12,9,CellType.SEAT)};
        Cell[] normalRow11 = new Cell[]{new Cell(13,0,CellType.SEAT),
            new Cell(13,1,CellType.SEAT), 
            new Cell(13,2,CellType.AISLE),
            new Cell(13,3,CellType.SEAT),
            new Cell(13,4,CellType.SEAT), 
            new Cell(13,5,CellType.SEAT), 
            new Cell(13,6,CellType.SEAT),
            new Cell(13,7,CellType.AISLE),
            new Cell(13,8,CellType.SEAT),
            new Cell(13,9,CellType.SEAT)};
        Cell[] normalRow12 = new Cell[]{new Cell(14,0,CellType.SEAT),
            new Cell(14,1,CellType.SEAT), 
            new Cell(14,2,CellType.AISLE),
            new Cell(14,3,CellType.SEAT),
            new Cell(14,4,CellType.SEAT), 
            new Cell(14,5,CellType.SEAT), 
            new Cell(14,6,CellType.SEAT),
            new Cell(14,7,CellType.AISLE),
            new Cell(14,8,CellType.SEAT),
            new Cell(14,9,CellType.SEAT)};
        Cell[] normalRow13 = new Cell[]{new Cell(15,0,CellType.SEAT),
            new Cell(15,1,CellType.SEAT), 
            new Cell(15,2,CellType.AISLE),
            new Cell(15,3,CellType.SEAT),
            new Cell(15,4,CellType.SEAT), 
            new Cell(15,5,CellType.SEAT), 
            new Cell(15,6,CellType.SEAT),
            new Cell(15,7,CellType.AISLE),
            new Cell(15,8,CellType.SEAT),
            new Cell(15,9,CellType.SEAT)};
        Cell[] normalRow14 = new Cell[]{new Cell(16,0,CellType.SEAT),
            new Cell(16,1,CellType.SEAT), 
            new Cell(16,2,CellType.AISLE),
            new Cell(16,3,CellType.SEAT),
            new Cell(16,4,CellType.SEAT), 
            new Cell(16,5,CellType.SEAT), 
            new Cell(16,6,CellType.SEAT),
            new Cell(16,7,CellType.AISLE),
            new Cell(16,8,CellType.SEAT),
            new Cell(16,9,CellType.SEAT)};
        
        PlaneDimension planeDimension = new PlaneDimension(
                new Cell[][]{priorityRow, priorityRow1, normalRow, normalRow1, normalRow2, normalRow3, normalRow4, normalRow5, normalRow6, normalRow7, normalRow8, normalRow9, normalRow10, normalRow11, normalRow12, normalRow13, normalRow14});
        SeatingMethod seatingMethod = new SeatingMethod(planeDimension);
        

        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.RANDOM);
        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.BACK_TO_FRONT);
        BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.BLOCK_BOARDING);
        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.BY_SEAT);
        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.OUTSIDE_IN);
        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.ROTATING_ZONE);
        //BoardingController controller = new BoardingController(planeDimension, seatingMethod, DefaultSeatingMethod.REVERSE_PYRAMID);
        /*BoardingController controller = new BoardingController(planeDimension, seatingMethod, 
                new int[][]{
                    { 2, 3, 4, 4, 4, 4, 3, 2},
                    { 2, 3, 1, 1, 1, 1, 3, 2},
                    { 2, 3, 4, 4, 1, 4, 3, 2},
                    { 2, 3, 4, 4, 1, 4, 3, 2},
                    { 2, 3, 1, 1, 1, 1, 3, 2},
                    { 2, 3, 4, 4, 4, 4, 3, 2},
                    { 2, 3, 1, 1, 1, 1, 3, 2},
                    { 2, 3, 4, 4, 4, 4, 3, 2},
                    { 2, 3, 1, 4, 1, 1, 3, 2},
                    { 2, 3, 4, 4, 4, 4, 3, 2},
                    { 2, 3, 4, 4, 4, 4, 3, 2},
                    { 2, 3, 4, 1, 4, 1, 3, 2},
                    { 2, 3, 1, 4, 4, 4, 3, 2},
                    { 2, 3, 1, 4, 4, 4, 3, 2},
                    { 2, 3, 4, 1, 4, 1, 3, 2}});*/
        
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
        
        controller.startBoarding();
        
        
    }
}
