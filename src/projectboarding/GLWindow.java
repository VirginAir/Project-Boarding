package projectboarding;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.awt.GLCanvas;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Ben Cook
 */
public class GLWindow extends JFrame{
    
    private final JPanel mainPanel;
    private final JPanel controlPanel;
    private final JLabel running;
    private boolean windowResponse;
    private int response;
    private int lastSelected;
    private boolean stopped;

    /**
     * Get the last selected
     * @return the last selected
     */
    public int getLastSelected() {
        return lastSelected;
    }

    /**
     * Set the last selected
     * @param lastSelected the last selected
     */
    public void setLastSelected(int lastSelected) {
        this.lastSelected = lastSelected;
    }

    /**
     * Get if it is stopped
     * @return boolean true stopped, false not 
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * Set if stopped
     * @param stopped boolean true stopped, false not 
     */
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    /**
     * Get is window response
     * @return boolean true response, false not
     */
    public boolean isWindowResponse() {
        return windowResponse;
    }

    /**
     * Set the window response
     * @param windowResponse boolean true response, false not
     */
    public void setWindowResponse(boolean windowResponse) {
        this.windowResponse = windowResponse;
    }

    /**
     * Get the response
     * @return the response
     */
    public int getResponse() {
        return response;
    }

    /**
     * Set the response
     * @param response the response
     */
    public void setResponse(int response) {
        this.response = response;
    }
    
    /**
     * Update the running text
     * @param running the text
     */
    public void updateRunning(String running) {
        this.running.setText("Viewing " + running);
    }
    
    /**
     * Create the window
     * @param title the window title
     * @param animator the window animator
     * @param width the window width
     * @param height the window height
     */
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
        
        this.setResizable(false);
    }
    
    /**
     * Setup the window
     * @param animator the animator
     * @param width the window width
     * @param height the window height
     */
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
    
    /**
     * Set the visability
     * @param isVisible boolean true visible, false not
     */
    public void setVisibility(boolean isVisible){
        this.setVisible(isVisible);
    }
    
    /**
     * Set the canvas
     * @param canvas the canvas
     * @param position the positon
     */
    public void setGLCanvas(GLCanvas canvas, String position){
        canvas.addKeyListener(new CustomKeyListener());
        mainPanel.add(canvas, position);
    }
    
    /**
     * Custom key listener class
     */
    class CustomKeyListener implements KeyListener{
        public void keyTyped(KeyEvent e) {           
        
        }
      
        /**
         * Action even if a key is pressed
         * @param e 
         */
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
