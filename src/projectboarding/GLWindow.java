/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

/**
 *
 * @author Ben Cook
 */
public class GLWindow extends JFrame{
    
    private JPanel mainPanel;
    private boolean windowResponse;
    private int response;

    public boolean isWindowResponse() {
        return windowResponse;
    }

    public void setWindowResponse(boolean windowResponse) {
        this.windowResponse = windowResponse;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
    
    public GLWindow(String title, FPSAnimator animator, int width, int height) {
        super(title);
        setupWindow(animator, width, height);
        mainPanel = new JPanel(new BorderLayout());
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        windowResponse = false;
        response = 0;
        
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
        canvas.addKeyListener(new CustomKeyListener());
        mainPanel.add(canvas, position);
    }
    
    class CustomKeyListener implements KeyListener{
      public void keyTyped(KeyEvent e) {           
      }
      @Override
      public void keyPressed(KeyEvent e) {
         int key = e.getKeyCode();
        if(key == KeyEvent.VK_P){
            Object[] possibilities = {"Random", "By Seat"};
            String s = (String)JOptionPane.showInputDialog(
                    null,
                    "Select Method:",
                    "Method",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "Random");
            if(s.equals("Random")){
                response = 0;
            } else if (s.equals("By Seat")){
                response = 1;
            }
            windowResponse = true;
        }
      }

      public void keyReleased(KeyEvent e) {            
      }    
   } 
}
