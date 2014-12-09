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
    
    public int getSeatType() {
        return seatType;
    }

    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
    
    
}
