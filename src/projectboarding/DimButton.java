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
    
    public DimButton(JButton extButton){
        this.button = extButton;
        this.button.setText("S");
        this.button.setBackground(Color.red);
        
        this.button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(seatType == 1){
                    seatType = 2;
                    button.setText("P");
                    button.setBackground(Color.yellow);
                } else if (seatType == 2){
                    seatType = 3;
                    button.setText("A");
                    button.setBackground(Color.BLACK);
                } else if (seatType == 3){
                    seatType = 0;
                    button.setText("N");
                    button.setBackground(Color.BLUE);
                } else if (seatType == 0){
                    seatType = 1;
                    button.setText("S");
                    button.setBackground(Color.RED);
                }
            }
        });
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
