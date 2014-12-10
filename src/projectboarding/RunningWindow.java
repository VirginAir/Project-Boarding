package projectboarding;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ben Cook
 */
public class RunningWindow extends JFrame {

    private JLabel status;
    private int totalRuns;

    /**
     * Create the running window
     *
     * @param title the window title
     * @param totalRuns the total number of runs
     */
    public RunningWindow(String title, int totalRuns) {
        super(title);
        this.setSize(new Dimension(175, 75));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        ImageIcon img = new ImageIcon("airplane.jpg");
        this.setIconImage(img.getImage());

        this.totalRuns = totalRuns;
        status = new JLabel("Running... 0/" + this.totalRuns);
        JPanel mainPanel = new JPanel();
        mainPanel.add(status);

        this.getContentPane().add(mainPanel);
    }

    /**
     * Set the iteration count
     *
     * @param count current iteration
     */
    public void setIterationCount(int count) {
        status.setText("Running... " + count + "/" + this.totalRuns);
    }

    /**
     * Set the visibility
     *
     * @param isVisible true visible, false not
     */
    public void setVisibility(boolean isVisible) {
        this.setVisible(isVisible);
    }
}
