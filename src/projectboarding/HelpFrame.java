package projectboarding;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

/**
 * The help screen for the application
 * 
 * @author Matthew
 * @version 1.0
 */
public class HelpFrame extends JFrame {
    
    // The tabbed pane
    private JTabbedPane tabbedPane;
    
    // Panels which display the help information
    private JPanel indexPanel;
    private JPanel useExample;
    private JPanel planeCreation;
    private JPanel defaultSeatingMethod;
    private JPanel customSeatingMethod;
    private JPanel resultsScreen;
    
    /**
     * Initalize the help frame
     */
    public HelpFrame() {
        initHelpFrame();
    }
    
    /**
     * Initalize the help frame
     */
    private void initHelpFrame() {    
        // Initialize the frame
        this.setTitle("Help");
        this.setSize(new Dimension(800, 700));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(new GridLayout(1, 1));  
        
        // Create the tabbed pane and intialize panels
        this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        
        this.indexPanel = new JPanel();
        this.useExample = new JPanel();
        this.planeCreation = new JPanel();
        this.defaultSeatingMethod = new JPanel();
        this.customSeatingMethod = new JPanel();
        this.resultsScreen = new JPanel();
        
        // Create the information from the HTML files
        try {
            this.indexPanel.add(this.createScrollPane("index"));
            this.useExample.add(this.createScrollPane("useExample"));
            this.planeCreation.add(this.createScrollPane("planeCreation"));
            this.defaultSeatingMethod.add(this.createScrollPane("defaultSeatingMethod"));
            this.customSeatingMethod.add(this.createScrollPane("customSeatingMethod"));
            this.resultsScreen.add(this.createScrollPane("resultsScreen"));
            
        } catch (IOException ex) {
            Logger.getLogger(HelpFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Set the layouts for the panels
        this.indexPanel.setLayout(new GridLayout(1, 1));
        this.useExample.setLayout(new GridLayout(1, 1));
        this.planeCreation.setLayout(new GridLayout(1, 1));
        this.defaultSeatingMethod.setLayout(new GridLayout(1, 1));
        this.customSeatingMethod.setLayout(new GridLayout(1, 1));
        this.resultsScreen.setLayout(new GridLayout(1, 1));
         
        // Add the tabs to the pane
        this.tabbedPane.addTab("Index", indexPanel);
        this.tabbedPane.addTab("Use Example", this.useExample);
        this.tabbedPane.addTab("Plane Creation", this.planeCreation);
        this.tabbedPane.addTab("Default Method", this.defaultSeatingMethod);
        this.tabbedPane.addTab("Custom Method", this.customSeatingMethod);
        this.tabbedPane.addTab("Results Screen", this.resultsScreen);
        
        // Add the tabbed pane to the frame
        this.getContentPane().add(this.tabbedPane);
        
        // Pack and show the frame
        this.setVisible(true);
        this.setResizable(false);
    }    
    
    /**
     * Create the scroll pane for the tab from a HTML file
     * 
     * @param fileName String The name of the HTML file
     * @return JScrollPane The pane for use in the panel
     * @throws IOException 
     */
    private JScrollPane createScrollPane(String fileName) throws IOException {        
        // Initialize the text pane
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setContentType("text/html");
        
        // Set the text pane to show the html file
        File file = new File(System.getProperty("user.dir") + "/help_documents/" + fileName + ".html");
        textPane.setPage(file.toURI().toURL());
        
        // Initialize the scroll pane
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        return scrollPane;
    }
    
}