package projectboarding;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author Ben Cook
 */
public class AisleButton {
    private int seatType = 1;
    private JButton button;
    private ArrayList<DimButton> column;
    
    public AisleButton(JButton extButton){
        this.column = new ArrayList<>();
        this.button = extButton;
        this.button.setText("Aisle");
        this.button.setBackground(Color.white);
        
        this.button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                for(DimButton db : column){
                    db.makeAisle();
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
    
    public void addButton(DimButton db){
        column.add(db);
    }
    
    
}
