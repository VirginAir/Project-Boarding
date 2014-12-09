package projectboarding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author Ben Cook
 */
public class DimensionLoader {
    
    
    /**
     * Load a PlaneDimension
     * @param file the location of the dimension file
     * @return the PlaneDimension
     */
    public static PlaneDimension loadDimension(File file){
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            ArrayList<ArrayList<Integer>> seatList = new ArrayList<ArrayList<Integer>>();
            while ((line = br.readLine()) != null) {
                String[] vals = line.split("\\s+");
                seatList.add(new ArrayList<Integer>());
                for(String a : vals){
                    seatList.get(seatList.size()-1).add(Integer.parseInt(a));
                }
            }
            reader.close();
            br.close();
            int columns = seatList.size();
            int rows = seatList.get(0).size();
            Cell[][] dim = new Cell[rows][columns];
            for(int i = 0; i < rows; i++){
                Cell[] row = new Cell[columns];
                for(int j = 0; j < columns; j++){
                    Cell.CellType ct = Cell.CellType.SEAT;
                    switch(seatList.get(j).get(i)){
                        case 0:
                            ct = Cell.CellType.NONE;
                            break;
                        case 1:
                            ct = Cell.CellType.SEAT;
                            break;
                        case 2:
                            ct = Cell.CellType.PRIORITY_SEAT;
                            break;
                        case 3:
                            ct = Cell.CellType.AISLE;
                    }
                    row[j] = new Cell(i,j, ct);
                }
                dim[i] = row;
            }
            return new PlaneDimension(dim);   
        } catch (IOException ex) {
            Cell[] priorityRow = new Cell[]{new Cell(0,0,Cell.CellType.NONE),
                new Cell(0,1,Cell.CellType.PRIORITY_SEAT), 
                new Cell(0,2,Cell.CellType.AISLE),
                new Cell(0,3,Cell.CellType.PRIORITY_SEAT),
                new Cell(0,4,Cell.CellType.PRIORITY_SEAT), 
                new Cell(0,5,Cell.CellType.PRIORITY_SEAT), 
                new Cell(0,6,Cell.CellType.PRIORITY_SEAT),
                new Cell(0,7,Cell.CellType.AISLE),
                new Cell(0,8,Cell.CellType.PRIORITY_SEAT),
                new Cell(0,9,Cell.CellType.NONE)};
            Cell[] priorityRow1 = new Cell[]{new Cell(1,0,Cell.CellType.NONE),
                new Cell(1,1,Cell.CellType.PRIORITY_SEAT), 
                new Cell(1,2,Cell.CellType.AISLE),
                new Cell(1,3,Cell.CellType.PRIORITY_SEAT),
                new Cell(1,4,Cell.CellType.PRIORITY_SEAT), 
                new Cell(1,5,Cell.CellType.PRIORITY_SEAT), 
                new Cell(1,6,Cell.CellType.PRIORITY_SEAT),
                new Cell(1,7,Cell.CellType.AISLE),
                new Cell(1,8,Cell.CellType.PRIORITY_SEAT),
                new Cell(1,9,Cell.CellType.NONE)};
            return new PlaneDimension(new Cell[][]{priorityRow, priorityRow1});
        }
    }
    
    /**
     * Save a PlaneDimension
     * @param file the location to save to
     * @param pd the PlaneDimension to save
     */
    public static void saveDimension(File file, PlaneDimension pd){
        Cell[][] dim = pd.getAllSeats();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < dim[0].length; i++){
          for(int j = 0; j < dim.length; j++){
              int outputVal = 0;
              Cell.CellType ct = dim[j][i].getCellType();
              if(ct == Cell.CellType.SEAT){
                  outputVal = 1;
              } else if(ct == Cell.CellType.PRIORITY_SEAT) {
                  outputVal = 2;
              } else if(ct == Cell.CellType.AISLE) {
                  outputVal = 3;
              }


              sb.append(outputVal).append(" ");
          }
          sb.append("\n");
        }
        String toOut = sb.toString();


        try {
          
          FileOutputStream fos;
          fos = new FileOutputStream(file);
          BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

          bw.write(toOut);

          bw.close();
          fos.close();
        } catch (Exception ex) {
        }
    }
    
    /**
     * Load a Seating Method
     * @param file the location of the method
     * @return the seating method array
     */
    public static int[][] loadMethod(File file){
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            ArrayList<ArrayList<Integer>> posList = new ArrayList<ArrayList<Integer>>();
            while ((line = br.readLine()) != null) {
                String[] vals = line.split("\\s+");
                posList.add(new ArrayList<Integer>());
                for(String a : vals){
                    posList.get(posList.size()-1).add(Integer.parseInt(a));
                }
            }
            reader.close();
            br.close();
            int columns = posList.size();
            int rows = posList.get(0).size();
            int[][] cus = new int[rows][columns];
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    cus[i][j] = posList.get(j).get(i);
                }
            }
            return cus;
        } catch (IOException ex) {
            return new int[1][1];
        }
    }
    
    /**
     * Save a seating method
     * @param file the location to save to
     * @param cm the array to save
     */
    public static void saveMethod(File file, int[][] cm){
        int rows = cm.length;
        int columns = cm[0].length;
        
        
        StringBuilder sb = new StringBuilder();        
        for(int i = 0; i < columns; i++){
          for(int j = 0; j < rows; j++){
              
              sb.append(cm[j][i]).append(" ");
          }
          sb.append("\n");
        }
        String toOut = sb.toString();


        try {
          
          FileOutputStream fos;
          fos = new FileOutputStream(file);
          BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

          bw.write(toOut);

          bw.close();
          fos.close();
        } catch (Exception ex) {
        }
    }
    
    
}
