package projectboarding;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Ben Cook
 */
public class DimButton {
    private int seatType = 1;
    private JButton button;
    private boolean isAisle;
    
    /**
     * Create the dim button
     * @param extButton the button
     */
    public DimButton(JButton extButton){
        this.isAisle = false;
        this.button = extButton;
        this.button.setText("S");
        this.button.setBackground(Color.red);
        
        this.button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(!isAisle){
                    if(seatType == 1){
                        seatType = 2;
                        button.setText("P");
                        button.setBackground(Color.yellow);
                    } else if (seatType == 2){
                        seatType = 0;
                        button.setText("N");
                        button.setBackground(Color.BLUE);
                    } else if (seatType == 0){
                        seatType = 1;
                        button.setText("S");
                        button.setBackground(Color.RED);
                    }
                }
            }
        });
    }

    /**
     * Set the dim button to an aisle or unset from aisle
     */
    public void makeAisle(){
        if(isAisle){
            isAisle = false;
            seatType = 1;
            button.setText("S");
            button.setBackground(Color.RED);
        } else {
            isAisle = true;
            seatType = 3;
            button.setText("A");
            button.setBackground(Color.BLACK);
        }
    }
    
    /**
     * Get the seat type
     * @return the seat type
     */
    public int getSeatType() {
        return seatType;
    }

    /**
     * Set the seat type
     * @param seatType the seat type
     */
    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }

    /**
     * Get the button
     * @return the button
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Set the button
     * @param button the button
     */
    public void setButton(JButton button) {
        this.button = button;
    }
    
    
}
