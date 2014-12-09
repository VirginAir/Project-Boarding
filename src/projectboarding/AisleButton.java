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
    
    /**
     * Create a new aisle button
     * @param extButton the aisle button
     */
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
     * @return the aisle button
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Set the button
     * @param button the new aisle button
     */
    public void setButton(JButton button) {
        this.button = button;
    }
    
    /**
     * Add a button to the column of buttons
     * @param db the new button
     */
    public void addButton(DimButton db){
        column.add(db);
    }
}
