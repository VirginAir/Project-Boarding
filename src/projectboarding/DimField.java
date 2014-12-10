package projectboarding;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 *
 * @author Ben Cook
 */
public class DimField {

    private int seatType = 1;
    private JTextField field;

    /**
     * Create the dim field
     *
     * @param extField the field
     */
    public DimField(JTextField extField) {
        this.field = extField;
        this.field.setPreferredSize(new Dimension(30, 30));
        this.field.setHorizontalAlignment(JTextField.CENTER);
        seatType = Integer.parseInt(extField.getText());
        if (extField.getText().equals("-1")) {
            this.field.setVisible(false);
        }

        this.field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    /**
     * Get the seat type
     *
     * @return
     */
    public int getSeatType() {
        return seatType;
    }

    /**
     * Set the seat type
     *
     * @param seatType
     */
    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }

    /**
     * Get the field
     *
     * @return the field
     */
    public JTextField getField() {
        return field;
    }

    /**
     * Set the field
     *
     * @param field the field
     */
    public void setField(JTextField field) {
        this.field = field;
    }
}
