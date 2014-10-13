package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

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
        PlaneDimension planeDimension = new PlaneDimension(2, new int[]{2,0,2}, 23, new int[]{3,0,3});
        SeatingMethod seatingMethod = new SeatingMethod(SeatingMethod.DefaultSeatingMethod.RANDOM, planeDimension);
        Cell[][] seatVisualisation = planeDimension.getSeatVisualisation();
        
        BoardingController controller = new BoardingController(planeDimension, seatingMethod);
        controller.startBoarding();
        
    }
    
}
