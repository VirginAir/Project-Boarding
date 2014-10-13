/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Ben Cook
 */
public class GLWindow extends JFrame{
    
    private JPanel mainPanel;
    
    public GLWindow(String title, FPSAnimator animator, int width, int height) {
        super(title);
        setupWindow(animator, width, height);
        mainPanel = new JPanel(new BorderLayout());
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupWindow(final FPSAnimator animator, int width, int height) {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(width, height));
        this.setLocationRelativeTo(null);
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                if(animator.isStarted()){
                    System.out.println("Stopping FPSAnimator...");
                    animator.stop();
                    System.out.println("FPSAnimator stopped...");
                }
                System.exit(0);
            }
        }); 
    }
    
    public void setVisibility(boolean isVisible){
        this.setVisible(isVisible);
    }
    
    public void setGLCanvas(GLCanvas canvas, String position){
        mainPanel.add(canvas, position);
    }
}
