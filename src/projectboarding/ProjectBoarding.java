package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
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
     * The main method, creates and runs the wizard
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        
        
        PlaneDimension planeDimension = DimensionLoader.loadDimension(new File("saves/dim/main.pd"));
        int[][] custom = DimensionLoader.loadMethod(new File("saves/met/main.cm"));     
        
        BoardingController controller = new BoardingController(planeDimension, true, custom);
        
        //Setup Window
        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);
        capabilities.setHardwareAccelerated(true);
        
        GLCanvas canvas = new GLCanvas(capabilities);
        FPSAnimator animator = new FPSAnimator(canvas, FPS);
        
        GLRender renderer = new GLRender(
                controller.getSeatVisualisationForMethod(DefaultSeatingMethod.RANDOM),
                controller.getPassengersForMethod(DefaultSeatingMethod.RANDOM));
        
        GLWindow window = new GLWindow("Project-Boarding", animator, WINDOW_HEIGHT, WINDOW_WIDTH);
        
        canvas.addGLEventListener(renderer);
        
        window.setGLCanvas(canvas, BorderLayout.CENTER);
        animator.start();
        
        WizardWindow wzWindow = new WizardWindow("Project-Boarding Wizard", 550, 250);
        wzWindow.setPd(planeDimension);
        wzWindow.setCustomMethodLayout(custom);
        wzWindow.setVisibility(true);
        
        
        LoopState state = LoopState.WIZARD;
        int repeat = 0;
        ArrayList<Results> resultList = new ArrayList<>();
        
        RunningWindow rw = new RunningWindow("Running...", 0);
        int currentIter = 0;
        
        
        while(true){
            Thread.sleep(1); //Java's being buggy. Srsly. The 'if' statement here goes ignored, if this 'sleep' isn't here.
            if(state == LoopState.WIZARD){
                wzWindow.update();
                if(wzWindow.isToRun()){
                    wzWindow.setToRun(false);
                    state = LoopState.SIMULATION;
                    custom = wzWindow.getCustomMethodLayout();
                    controller = new BoardingController(wzWindow.getPd(), wzWindow.isUseCustom(), custom);
                    controller.stopBoarding();
                    controller.getPlaneDimension().resetHasPassengers();
                    
                    resultList = new ArrayList<>();
                    repeat = wzWindow.getIterCountVal()-1;
                    
                    if(repeat == 0 && wzWindow.getToView() != DefaultSeatingMethod.NONE){
                        renderer = new GLRender(controller.getSeatVisualisationForMethod(DefaultSeatingMethod.RANDOM), controller.getPassengersForMethod(DefaultSeatingMethod.RANDOM));
                        canvas.addGLEventListener(renderer);
                        window = new GLWindow("Project-Boarding", animator, WINDOW_HEIGHT, WINDOW_WIDTH);
                        window.setGLCanvas(canvas, BorderLayout.CENTER);
                        renderer.setPassengers(controller.getPassengersForMethod(wzWindow.getToView()));
                        window.updateRunning(wzWindow.getToView().toString());
                        window.setVisibility(true);
                        wzWindow.setVisibility(false);
                        controller.startBoarding(wzWindow.getToView());
                    } else {
                        rw = new RunningWindow("Running", repeat+1);
                        rw.setVisibility(true);
                        wzWindow.setVisibility(false);
                        currentIter = 0;
                        controller.startBoarding(DefaultSeatingMethod.NONE);
                    }
                    
                }
            } else if (state == LoopState.SIMULATION) {
                if(controller.checkComplete()){
                    if(repeat > 1){
                        repeat--;
                        currentIter++;
                        rw.setIterationCount(currentIter);
                        resultList.add(controller.getResults());
                        controller.stopBoarding();
                        controller = new BoardingController(wzWindow.getPd(), wzWindow.isUseCustom(), custom);
                        controller.getPlaneDimension().resetHasPassengers();
                        controller.startBoarding(DefaultSeatingMethod.NONE);
                        
                    } else if(repeat == 1) {
                        repeat--;
                        resultList.add(controller.getResults());
                        
                        rw.setVisibility(false);
                        controller = new BoardingController(wzWindow.getPd(), wzWindow.isUseCustom(), custom);
                        controller.stopBoarding();
                        controller.getPlaneDimension().resetHasPassengers();
                        
                        if(wzWindow.getToView() != DefaultSeatingMethod.NONE){
                            renderer = new GLRender(controller.getSeatVisualisationForMethod(DefaultSeatingMethod.RANDOM), controller.getPassengersForMethod(DefaultSeatingMethod.RANDOM));
                            canvas.addGLEventListener(renderer);
                            window = new GLWindow("Project-Boarding", animator, WINDOW_HEIGHT, WINDOW_WIDTH);
                            window.setGLCanvas(canvas, BorderLayout.CENTER);
                            renderer.setPassengers(controller.getPassengersForMethod(wzWindow.getToView()));
                            window.updateRunning(wzWindow.getToView().toString());
                            window.setVisibility(true);
                            wzWindow.setVisibility(false);
                        }
                        
                        controller.startBoarding(wzWindow.getToView());
                    } else {
                        window.setVisibility(false);
                        state = LoopState.RESULTS;
                        
                    }
                } else if (window.isStopped()) {
                    window.setStopped(false);
                    controller.stopBoarding();
                    JOptionPane.showMessageDialog(null, "Simulation Stopped!", "Stopped", 1);
                    state = LoopState.WIZARD;
                    wzWindow.setVisibility(true);
                    window.setVisibility(false);
                }
            } else if (state == LoopState.RESULTS) {
                rw.setVisibility(false);
                resultList.add(controller.getResults());
                Results finalResults = Results.averageResults(resultList);
                finalResults.sort();
                JTextArea toDisplay = new JTextArea(finalResults.toString());
                toDisplay.setEditable(false);
                JOptionPane.showMessageDialog(null, toDisplay, "Results", JOptionPane.PLAIN_MESSAGE);
                state = LoopState.WIZARD;
                wzWindow.setVisibility(true);
            }
        }
        
        
        
    }
}
