package projectboarding;

//import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Ben Cook
 */
public class DimField {
    private int seatType = 1;
    private JTextField field;
    
    public DimField(JTextField extField){
        this.field = extField;
        this.field.setPreferredSize(new Dimension(30,30));
        this.field.setHorizontalAlignment(JTextField.CENTER);
        seatType = Integer.parseInt(extField.getText());
        if(extField.getText().equals("-1")){
            this.field.setVisible(false);
        }
        
        this.field.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
            }
        });
    }

    public int getSeatType() {
        return seatType;
    }

    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }

    public JTextField getField() {
        return field;
    }

    public void setField(JTextField field) {
        this.field = field;
    }
    
    
}
