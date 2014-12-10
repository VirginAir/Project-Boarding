package projectboarding;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import projectboarding.Cell.CellType;
import projectboarding.SeatingMethod.DefaultSeatingMethod;

/**
 *
 * @author Ben Cook
 */
public class WizardWindow extends JFrame{
    
    private JPanel mainPanel;
    private JButton pDim;
    private JButton customMethod;
    private JCheckBox customCheck;
    private JComboBox selectionBox;
    private JButton run;
    private JButton save; 
    private JButton load; 
    private JButton savec; 
    private JButton loadc; 
    private JTextField iterCount;
    
    private boolean useCustom;
    private boolean toRun;
    private DefaultSeatingMethod toView;
    private int[][] customMethodLayout;
    private int iterCountVal;
    private SeatingWindow sw;
    private CustomWindow cw;
    private PlaneDimension pd;
    
    /**
     * Private class that loads the seating window
     */
    private class SeatingWindow extends JFrame{
        
        private JPanel bottomPanel;
        private JPanel mainPanel;
        private JButton okay;
        
        private ArrayList<DimButton> buttonList;
        private ArrayList<AisleButton> aisleList;
        private int rows;
        private int columns;
 
        private boolean done;

        /**
         * Create the seating window
         * @param title the window title
         * @param rows the number of rows
         * @param columns the number of columns
         */
        public SeatingWindow(String title, int rows, int columns){
            super(title);
            done = false;
            this.setLayout(new BorderLayout());
            this.setSize(new Dimension(rows*45, columns*45));
            this.setLocationRelativeTo(null);

            ImageIcon img = new ImageIcon("airplane.jpg");
            this.setIconImage(img.getImage());

            this.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            });
            JPanel superPanel = new JPanel();
            bottomPanel = new JPanel();
            okay = new JButton("OK");
            okay.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                  getPlaneLayout();
                  done = true;
              }
            });
            bottomPanel.add(okay);
            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                  done = true;
              }
            });
            bottomPanel.add(cancel);
            
            
            mainPanel = new JPanel(new GridLayout(columns,rows+1,0,0));
            buttonList = new ArrayList<DimButton>();
            aisleList = new ArrayList<AisleButton>();
            int buttonCount = rows*columns;
            for(int i = 0; i < buttonCount; i++){
                if(i%rows == 0){
                    aisleList.add(new AisleButton(new JButton("Make Aisle")));
                    mainPanel.add(aisleList.get(aisleList.size()-1).getButton());
                }
                
                buttonList.add(new DimButton(new JButton("S")));
                aisleList.get(aisleList.size()-1).addButton(buttonList.get(buttonList.size()-1));
                mainPanel.add(buttonList.get(buttonList.size()-1).getButton());
            }
            
            superPanel.add(mainPanel);
            superPanel.add(bottomPanel);
            
            this.getContentPane().add(superPanel);
            
            this.rows = rows;
            this.columns = columns;
            
            this.pack();
        }
        
        /**
         * Create the plane layout
         */
        public void getPlaneLayout(){
            Cell[][] dim = new Cell[rows][columns];
            for(int i = 0; i < rows; i++){
                Cell[] row = new Cell[columns];
                for(int j = 0; j < columns; j++){
                    CellType ct = CellType.SEAT;
                    switch(buttonList.get(rows*j + i).getSeatType()){
                        case 0:
                            ct = CellType.NONE;
                            break;
                        case 1:
                            ct = CellType.SEAT;
                            break;
                        case 2:
                            ct = CellType.PRIORITY_SEAT;
                            break;
                        case 3:
                            ct = CellType.AISLE;
                    }
                    row[j] = new Cell(i,j, ct);
                }
                dim[i] = row;
            }
            pd = new PlaneDimension(dim);
        }
        
        /**
         * Set the visibility
         * @param isVisible boolen true visible, false not
         */
        public void setVisibility(boolean isVisible){
            this.setVisible(isVisible);
        }

        /**
         * Get if it is done
         * @return true done, false not
         */
        public boolean isDone() {
            return done;
        }

        /**
         * Set if it is done
         * @param done true done, false not
         */
        public void setDone(boolean done) {
            this.done = done;
        }
    }
    
    /**
     * Private class that creates a custom window
     */
    private class CustomWindow extends JFrame{
        
        private JPanel bottomPanel;
        private JPanel mainPanel;
        private JButton okay;
        
        private ArrayList<DimField> fieldList;
        private int rows;
        private int columns;
        
        private boolean done;
        
        /**
         * Default initalizer
         */
        public CustomWindow(){
        
        };
        
        /**
         * Create a custom window
         * @param title the window title
         * @param rows the number of rows
         * @param columns the number of columns
         */
        public CustomWindow(String title, int rows, int columns){
            super(title);
            done = false;
            this.setLayout(new BorderLayout());
            this.setSize(new Dimension(rows*30, 10));
            this.setLocationRelativeTo(null);

            ImageIcon img = new ImageIcon("airplane.jpg");
            this.setIconImage(img.getImage());

            this.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            });
            JPanel superPanel = new JPanel(new GridLayout(2,1,0,0));
            bottomPanel = new JPanel();
            okay = new JButton("OK");
            okay.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                  getMethodLayout();
                  done = true;
              }
            });
            bottomPanel.add(okay);
            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                  done = true;
              }
            });
            bottomPanel.add(cancel);
            
            mainPanel = new JPanel(new GridLayout(rows,columns,0,0));
            fieldList = new ArrayList<DimField>();
            
            for(Cell[] row : pd.getAllSeats()){
                for(Cell column : row)
                    if(column.getCellType() == CellType.SEAT){
                        fieldList.add(new DimField(new JTextField("1")));
                    } else {
                        fieldList.add(new DimField(new JTextField("-1")));
                    }
            }
            
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    mainPanel.add(fieldList.get(j*rows + i).getField());
                }
            }
            
            superPanel.add(mainPanel);
            superPanel.add(bottomPanel);
            
            this.getContentPane().add(superPanel);
            
            this.rows = rows;
            this.columns = columns;
            
            this.pack();
        }
        
        /**
         * Get the method layout
         */
        public void getMethodLayout(){
            int[][] dim = new int[columns][rows];
            int cur = 0;
            for(int i = 0; i < columns; i++){
                int[] row = new int[rows];
                for(int j = 0; j < rows; j++){
                    
                    row[j] = Integer.parseInt(fieldList.get(cur).getField().getText());
                    cur++;
                }
                dim[i] = row;
            }
            customMethodLayout = dim;
        }
        
        /**
         * Set the visibility
         * @param isVisible true visible, false not
         */
        public void setVisibility(boolean isVisible){
            this.setVisible(isVisible);
        }

        /**
         * Get if it is done
         * @return true done, false not
         */
        public boolean isDone() {
            return done;
        }

        /**
         * Set if it is done
         * @return true done, false not
         */
        public void setDone(boolean done) {
            this.done = done;
        }
    }
    
    /**
     * Create the wizard window
     * @param title the title
     * @param width the width
     * @param height the height
     */
    public WizardWindow(String title, int width, int height){
        super(title);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(width, height));
        this.setLocationRelativeTo(null);
        
        ImageIcon img = new ImageIcon("airplane.jpg");
        this.setIconImage(img.getImage());
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        
        mainPanel = new JPanel(new GridLayout(5,1,10,10));
        
        sw = new SeatingWindow("Plane Dimensions", 1,1);
        cw = new CustomWindow();
        
        JPanel topDiv = new JPanel(new GridLayout(1,3,0,0));
        
        pDim = new JButton("Plane Dimensions");
        pDim.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              String row = "";
              String column = "";
              boolean NaN = true;
              while(NaN){
                row = (String)JOptionPane.showInputDialog(
                      null,
                      "Row Count",
                      "Dimension Selection",
                      JOptionPane.PLAIN_MESSAGE);
                try { 
                    Integer.parseInt(row); 
                    NaN = false;
                } catch(NumberFormatException ex) { 
                    JOptionPane.showMessageDialog(null, "Not a number!", "Error", 2);
                }
              }
              NaN = true;
              while(NaN){
                column = (String)JOptionPane.showInputDialog(
                      null,
                      "Column Count",
                      "Dimension Selection",
                      JOptionPane.PLAIN_MESSAGE);
                try { 
                    Integer.parseInt(column); 
                    NaN = false;
                } catch(NumberFormatException ex) { 
                    JOptionPane.showMessageDialog(null, "Not a number!", "Error", 2);
                }
              }
              int rowInt = Integer.parseInt(row);
              int colInt = Integer.parseInt(column);
              sw = new SeatingWindow("Plane Dimensions", rowInt, colInt);
              sw.setVisibility(true);
          }
        });
        
        save = new JButton("Save");
        save.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {            
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir")+"//saves");
            File file = new File("Untitled.pd");
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
              file = fileChooser.getSelectedFile();
              String file_name = file.getPath();
              if (!file_name.endsWith(".pd")){
                  file_name += ".pd";
                  file = new File(file_name);
              }
            }
            
            DimensionLoader.saveDimension(file, pd);
            
          }
        });
        
        load = new JButton("Load");
        load.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir")+"//saves");
              if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                  pd = DimensionLoader.loadDimension(fileChooser.getSelectedFile());
              }
          }
        });
        
        topDiv.add(pDim);
        topDiv.add(load);
        topDiv.add(save);
        
        mainPanel.add(topDiv);
        
        JPanel customSplit = new JPanel(new GridLayout(1,4,0,0));
        customCheck = new JCheckBox("Use Custom? ");
        customCheck.setHorizontalAlignment(JCheckBox.RIGHT);
        customMethod = new JButton("Custom Method");
        customMethod.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              cw = new CustomWindow("Custom Method" , pd.getNumberOfColumns(), pd.totalNumberOfRows());
              cw.setVisibility(true);
          }
        });
        
        savec = new JButton("Save");
        savec.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {            
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir")+"//saves");
            File file = new File("Untitled.cm");
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
              file = fileChooser.getSelectedFile();
              String file_name = file.getPath();
              if (!file_name.endsWith(".cm")){
                  file_name += ".cm";
                  file = new File(file_name);
              }
            }
            
            DimensionLoader.saveMethod(file, customMethodLayout);
            
          }
        });
        
        loadc = new JButton("Load");
        loadc.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir")+"//saves");
              if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                  customMethodLayout = DimensionLoader.loadMethod(fileChooser.getSelectedFile());
              }
          }
        });
        
        
        customSplit.add(customCheck);
        customSplit.add(customMethod);
        customSplit.add(loadc);
        customSplit.add(savec);
        mainPanel.add(customSplit);
        
        JPanel comboSplit = new JPanel(new GridLayout(1,2,10,10));
        
        JLabel boxLabel = new JLabel("View: ");
        boxLabel.setHorizontalAlignment(JLabel.RIGHT);
        selectionBox = new JComboBox();
        selectionBox.addItem("Random");
        selectionBox.addItem("By Seat"); //BACK_TO_FRONT, BLOCK_BOARDING, BY_SEAT, OUTSIDE_IN, RANDOM, REVERSE_PYRAMID, ROTATING_ZONE
        selectionBox.addItem("Back To Front");
        selectionBox.addItem("Block Boarding");
        selectionBox.addItem("Outside In");
        selectionBox.addItem("Reverse Pyramid");
        selectionBox.addItem("Rotating Zone");
        selectionBox.addItem("Custom");
        selectionBox.addItem("No Visual");
        
        comboSplit.add(boxLabel);
        comboSplit.add(selectionBox);
        mainPanel.add(comboSplit);
        
        JPanel iterSplit = new JPanel(new GridLayout(1,2,10,10));
        JLabel runLabel = new JLabel("Iteration Count: ");
        runLabel.setHorizontalAlignment(JLabel.RIGHT);
        iterCount = new JTextField("1");
        iterSplit.add(runLabel);
        iterSplit.add(iterCount);
        mainPanel.add(iterSplit);
        
        JPanel runSplit = new JPanel(new GridLayout(1,4,10,10));
        run = new JButton("Run");
        run.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              if(!customCheck.isSelected() && selectionBox.getSelectedItem().equals("Custom")){
                  JOptionPane.showMessageDialog(null, "You can't view the Custom simulation if it isn't active!", "Error", 1);
                  return;
              }
              
              try { 
                    int count = Integer.parseInt(iterCount.getText()); 
                    if(count < 1){
                        JOptionPane.showMessageDialog(null, "Iteration Count must be above 0!", "Error", 1);
                        return;
                    }
              } catch(NumberFormatException ne) { 
                    JOptionPane.showMessageDialog(null, "Iteration Count is not a number!", "Error", 1);
                    return; 
              }
              
              iterCountVal = Integer.parseInt(iterCount.getText());
              
              toRun = true;
              if(selectionBox.getSelectedItem().equals("Random")){
                    toView = DefaultSeatingMethod.RANDOM;
              } else if(selectionBox.getSelectedItem().equals("By Seat")){
                    toView = DefaultSeatingMethod.BY_SEAT;
              } else if(selectionBox.getSelectedItem().equals("Back To Front")){
                    toView = DefaultSeatingMethod.BACK_TO_FRONT;
              } else if(selectionBox.getSelectedItem().equals("Block Boarding")){
                    toView = DefaultSeatingMethod.BLOCK_BOARDING;
              } else if(selectionBox.getSelectedItem().equals("Outside In")){
                    toView = DefaultSeatingMethod.OUTSIDE_IN;
              } else if(selectionBox.getSelectedItem().equals("Reverse Pyramid")){
                    toView = DefaultSeatingMethod.REVERSE_PYRAMID;
              } else if(selectionBox.getSelectedItem().equals("Rotating Zone")){
                    toView = DefaultSeatingMethod.ROTATING_ZONE;
              } else if(selectionBox.getSelectedItem().equals("Custom")){
                    toView = DefaultSeatingMethod.CUSTOM;
              } else if(selectionBox.getSelectedItem().equals("No Visual")){
                    toView = DefaultSeatingMethod.NONE;
              }
              
              useCustom = customCheck.isSelected();
            }
        });
        
        
        JButton help = new JButton("Help");
            help.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                  new HelpFrame();
              }
            });
        
        
        runSplit.add(run);
        runSplit.add(help);
        mainPanel.add(runSplit);
        
        this.getContentPane().add(mainPanel);
        
        toRun = false;
        toView = DefaultSeatingMethod.RANDOM;
        
    }
    
    /**
     * Update the visibilities
     */
    public void update(){
        if(sw.isDone()){
            sw.setVisibility(false);
        }
        
        if(cw.isDone()){
            cw.setVisibility(false);
        }
    }

    /**
     * Get the iteration count
     * @return the iteration count
     */
    public int getIterCountVal() {
        return iterCountVal;
    }

    /**
     * Set the iteration count
     * @param iterCountVal the iteration count
     */
    public void setIterCountVal(int iterCountVal) {
        this.iterCountVal = iterCountVal;
    }
        
    /**
     * Set the visibility
     * @param isVisible true visible, false not
     */
    public void setVisibility(boolean isVisible){
        this.setVisible(isVisible);
    }

    /**
     * Get if it is to run
     * @return true is to run, false not
     */
    public boolean isToRun() {
        return toRun;
    }

    /**
     * Get if it is to run
     * @param toRun true is to run, false not
     */
    public void setToRun(boolean toRun) {
        this.toRun = toRun;
    }

    /**
     * Get the method to view
     * @return method to view
     */
    public DefaultSeatingMethod getToView() {
        return toView;
    }

    /**
     * Set the method to view
     * @param toView method to view
     */
    public void setToView(DefaultSeatingMethod toView) {
        this.toView = toView;
    }

    /**
     * Set the plane dimension
     * @return the plane dimension
     */
    public PlaneDimension getPd() {
        return pd;
    }

    /**
     * Set the plane dimension
     * @param pd the plane dimension
     */
    public void setPd(PlaneDimension pd) {
        this.pd = pd;
    }

    /**
     * Get if to use the custom
     * @return true use custom, false not
     */
    public boolean isUseCustom() {
        return useCustom;
    }

    /**
     * Get if to use the custom
     * @param useCustom true use custom, false not
     */
    public void setUseCustom(boolean useCustom) {
        this.useCustom = useCustom;
    }

    /**
     * Get the custom method layout
     * @return the custom method layout
     */
    public int[][] getCustomMethodLayout() {
        return customMethodLayout;
    }

    /**
     * Get the custom method layout
     * @param customMethodLayout the custom method layout
     */
    public void setCustomMethodLayout(int[][] customMethodLayout) {
        this.customMethodLayout = customMethodLayout;
    }
}
