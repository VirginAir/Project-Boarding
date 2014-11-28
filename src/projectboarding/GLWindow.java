/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.Box;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
    private JPanel controlPanel;
    private JLabel running;
    private boolean windowResponse;
    private int response;
    private int lastSelected;
    private boolean stopped;

    public int getLastSelected() {
        return lastSelected;
    }

    public void setLastSelected(int lastSelected) {
        this.lastSelected = lastSelected;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

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
    
    public void updateRunning(String running) {
        this.running.setText("Viewing " + running);
    }
    
    public GLWindow(String title, FPSAnimator animator, int width, int height) {
        super(title);
        setupWindow(animator, width, height+40);
        
        Container pane = this.getContentPane();
        
        lastSelected = 0;
        stopped = false;
        
        
        controlPanel = new JPanel();
        running = new JLabel("Random");
        controlPanel.add(running);
        JButton stop = new JButton("Stop");
        stop.setAlignmentY(Component.CENTER_ALIGNMENT);
        stop.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              stopped = true;
          }
        });
        
        controlPanel.add(stop);
        controlPanel.setPreferredSize(new Dimension(800,40));
        pane.add(controlPanel, BorderLayout.NORTH);
        
        
        
        mainPanel = new JPanel(new BorderLayout());
        pane.add(mainPanel, BorderLayout.CENTER);
        
        
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        windowResponse = false;
        response = 0;
        
    }
    
    private void setupWindow(final FPSAnimator animator, int width, int height) {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(width, height));
        this.setLocationRelativeTo(null);
        
        
        
        ImageIcon img = new ImageIcon("airplane.jpg");
        this.setIconImage(img.getImage());
        
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
            JOptionPane.showMessageDialog(null, stopped, "Haha", 1);
        }
      }

      public void keyReleased(KeyEvent e) {            
      }    
   } 
}
