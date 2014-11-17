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
    private boolean windowResponse;
    private int response;
    private int lastSelected;
    private boolean newSelection;

    public int getLastSelected() {
        return lastSelected;
    }

    public void setLastSelected(int lastSelected) {
        this.lastSelected = lastSelected;
    }

    public boolean isNewSelection() {
        return newSelection;
    }

    public void setNewSelection(boolean newSelection) {
        this.newSelection = newSelection;
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
    
    public GLWindow(String title, FPSAnimator animator, int width, int height) {
        super(title);
        setupWindow(animator, width+150, height);
        
        Container pane = this.getContentPane();
        
        lastSelected = 0;
        newSelection = false;
        
        
        controlPanel = new JPanel();
        final JComboBox selectionBox = new JComboBox();
        selectionBox.addItem("Random");
        selectionBox.addItem("By Seat"); //BACK_TO_FRONT, BLOCK_BOARDING, BY_SEAT, OUTSIDE_IN, RANDOM, REVERSE_PYRAMID, ROTATING_ZONE
        selectionBox.addItem("Back To Front");
        selectionBox.addItem("Block Boarding");
        selectionBox.addItem("Outside In");
        selectionBox.addItem("Reverse Pyramid");
        selectionBox.addItem("Rotating Zone");
        selectionBox.addItem("Custom (WIP)");
        controlPanel.add(selectionBox);
        final JButton button = new JButton("Run");
        JButton button2 = new JButton("Check");
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              
            newSelection = true;
            if(selectionBox.getSelectedItem().equals("Random")){
                lastSelected = 0;
            } else if(selectionBox.getSelectedItem().equals("By Seat")){
                lastSelected = 1;
            } else if(selectionBox.getSelectedItem().equals("Back To Front")){
                lastSelected = 2;
            } else if(selectionBox.getSelectedItem().equals("Block Boarding")){
                lastSelected = 3;
            } else if(selectionBox.getSelectedItem().equals("Outside In")){
                lastSelected = 4;
            } else if(selectionBox.getSelectedItem().equals("Reverse Pyramid")){
                lastSelected = 5;
            } else if(selectionBox.getSelectedItem().equals("Rotating Zone")){
                lastSelected = 6;
            } else if(selectionBox.getSelectedItem().equals("Custom (WIP)")){
                lastSelected = 7;
            }
            //button.setEnabled(false);
          }
        });
        
        button2.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
                JOptionPane.showMessageDialog(null, GLWindow.this.isNewSelection(), "Haha", 1);
          }
        });
        
        controlPanel.add(button);
        controlPanel.add(button2);
        controlPanel.setPreferredSize(new Dimension(150,600));
        pane.add(controlPanel, BorderLayout.WEST);
        
        
        
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
            JOptionPane.showMessageDialog(null, newSelection, "Haha", 1);
        }
      }

      public void keyReleased(KeyEvent e) {            
      }    
   } 
}
