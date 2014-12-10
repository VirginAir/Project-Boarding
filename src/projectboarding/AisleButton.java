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

    private boolean aislePresent;
    private JButton button;
    private ArrayList<DimButton> column;

    /**
     * Create a new aisle button
     *
     * @param extButton the aisle button
     */
    public AisleButton(JButton extButton) {
        this.column = new ArrayList<>();
        this.button = extButton;
        this.button.setText("Aisle");
        this.button.setBackground(Color.white);
        this.aislePresent = false;

        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Make all attached buttons in to aisles (or undo)
                for (DimButton db : column) {
                    db.makeAisle();
                }
                aislePresent = !aislePresent;
            }
        });
    }

    /**
     * Get the button
     *
     * @return the aisle button
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Set the button
     *
     * @param button the new aisle button
     */
    public void setButton(JButton button) {
        this.button = button;
    }

    /**
     * Add a button to the column of buttons
     *
     * @param db the new button
     */
    public void addButton(DimButton db) {
        column.add(db);
    }

    /**
     * Get the aisle presence
     *
     * @return whether there is an aisle present
     */
    public boolean isAislePresent() {
        return aislePresent;
    }

    /**
     * Change the aisle's presence
     *
     * @param ap the new presence
     */
    public void setAislePresent(boolean ap) {
        aislePresent = ap;
    }
}
