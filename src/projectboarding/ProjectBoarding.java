package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import projectboarding.Cell.CellType;
import projectboarding.SeatingMethod.DefaultSeatingMethod;

/**
 *
 * @author Matthew Kempson, Benjamin Cook, Matthew Horn
 */
public class ProjectBoarding {

    private static final int WINDOW_HEIGHT = 1280;
    private static final int WINDOW_WIDTH = 960;
    private static final int FPS = 60;
    
    public enum LoopState {

        WIZARD, SIMULATION, RESULTS
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
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
        
        PlaneDimension planeDimension = new PlaneDimension(
                new Cell[][]{priorityRow, priorityRow1});
        
        int[][] custom = new int[][]{{ 2, 3, 4, 4, 4, 4, 3, 2},
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
                    { 2, 3, 4, 1, 4, 1, 3, 2}};
        
        BoardingController controller = new BoardingController(planeDimension, custom);
        
        //Setup Window
        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);
        capabilities.setHardwareAccelerated(true);
        
        // delete this!
        new HelpFrame();
        
        GLCanvas canvas = new GLCanvas(capabilities);
        FPSAnimator animator = new FPSAnimator(canvas, FPS);
        
        GLRender renderer = new GLRender(
                controller.getSeatVisualisationForMethod(DefaultSeatingMethod.RANDOM),
                controller.getPassengersForMethod(DefaultSeatingMethod.RANDOM));
        
        GLWindow window = new GLWindow("Project-Boarding", animator, WINDOW_HEIGHT, WINDOW_WIDTH);
        
        canvas.addGLEventListener(renderer);
        
        window.setGLCanvas(canvas, BorderLayout.CENTER);
        animator.start();
        //window.setVisibility(true);
        
        WizardWindow wzWindow = new WizardWindow("Project-Boarding Wizard", 300, 200);
        wzWindow.setVisibility(true);
        
        
        LoopState state = LoopState.WIZARD;
        
        while(true){
            Thread.sleep(1); //Java's being buggy. Srsly. The 'if' statement here goes ignored, if this 'sleep' isn't here.
            if(state == LoopState.WIZARD){
                wzWindow.update();
                if(wzWindow.isToRun()){
                    wzWindow.setToRun(false);
                    state = LoopState.SIMULATION;
                    controller = new BoardingController(wzWindow.getPd()/*, wzWindow.isUseCustom()*/, custom);
                    renderer = new GLRender(controller.getSeatVisualisationForMethod(DefaultSeatingMethod.RANDOM), controller.getPassengersForMethod(DefaultSeatingMethod.RANDOM));
                    canvas.addGLEventListener(renderer);
                    window = new GLWindow("Project-Boarding", animator, WINDOW_HEIGHT, WINDOW_WIDTH);
                    window.setGLCanvas(canvas, BorderLayout.CENTER);
                    controller.stopBoarding();
                    controller.getPlaneDimension().resetHasPassengers();
                    renderer.setPassengers(controller.getPassengersForMethod(wzWindow.getToView()));
                    window.updateRunning(wzWindow.getToView().toString());
                    window.setVisibility(true);
                    wzWindow.setVisibility(false);
                    controller.startBoarding(wzWindow.getToView());
                }
            } else if (state == LoopState.SIMULATION) {
                if(controller.checkComplete()){
                    state = LoopState.RESULTS;
                    window.setVisibility(false);
                } else if (window.isStopped()) {
                    window.setStopped(false);
                    controller.stopBoarding();
                    JOptionPane.showMessageDialog(null, "Simulation Stopped!", "Stopped", 1);
                    state = LoopState.WIZARD;
                    wzWindow.setVisibility(true);
                    window.setVisibility(false);
                }
            } else if (state == LoopState.RESULTS) {
                JTextArea toDisplay = new JTextArea(controller.getResults());
                toDisplay.setEditable(false);
                JOptionPane.showMessageDialog(null, toDisplay, "Results", JOptionPane.PLAIN_MESSAGE);
                state = LoopState.WIZARD;
                wzWindow.setVisibility(true);
            }
        }
        
        
        
    }
}

// create all boarding controllers for each different type
// get times for each of the ones the user hasnt selected
// let ui run normal for selected 