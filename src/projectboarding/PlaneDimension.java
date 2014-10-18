package projectboarding;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Matthew
 */
public class PlaneDimension {
    
    public class PlaneColumnLayout {
        private final int numberOfRows;
        private final boolean isPriority;
        private final int[] columnSplit;
        
        public PlaneColumnLayout(int numberOfRows, boolean isPriority, int[] columnSplit) {
            this.numberOfRows = numberOfRows;
            this.isPriority = isPriority;
            this.columnSplit = columnSplit;
        }
        
        public int getNumberOfRows() {
            return this.numberOfRows;
        }
        
        public boolean isPriority() {
            return this.isPriority;
        }
        
        public int[] getColumnSplit() {
            return this.columnSplit;
        }
        
        public int getNumberOfColumns() {
            int numberOfColumns = 0;
            
            for (int column: this.columnSplit) {
                numberOfColumns += column;
            }
            
            return numberOfColumns;
        }
        
        public Integer[] getColumnNumbers(int[] planeSize) {
            ArrayList<Integer> columnNumbers = new ArrayList<>();
            int numberOfAisles = this.columnSplit.length - 1;
            int totalColumns = 0;
            
            for (int aisleNumber = 0; aisleNumber <= numberOfAisles; aisleNumber++) {
                int layoutSplit = this.columnSplit[aisleNumber];
                int planeSplit = planeSize[aisleNumber];
                int difference = planeSplit - layoutSplit;
                
                int startingPoint = 0;
                int endingPoint = 0;
                
                if (aisleNumber == 0) {
                    // Left window
                    startingPoint = 0;
                    endingPoint = layoutSplit;
                    
                } else if (aisleNumber == numberOfAisles) {
                    // Right window
                    startingPoint = totalColumns + difference;
                    endingPoint = totalColumns + planeSplit;
                    
                } else {
                    // Middle
                    if (layoutSplit == planeSplit) {
                        startingPoint = totalColumns;
                        endingPoint = totalColumns + planeSplit;
                    } else {
                        startingPoint = (difference/2) + totalColumns;
                        endingPoint = startingPoint + layoutSplit;
                    }
                }
                
                columnNumbers.addAll(this.createArrayOfNumbersBetween(startingPoint, endingPoint));
                
                totalColumns += planeSplit;
            }
            
            Object[] array = columnNumbers.toArray();
            return Arrays.copyOf(array, array.length, Integer[].class);
        }
        
        /**
         * Generate an array of integer numbers which range between starting to
         * ending. E.g. if starting = 1, ending = 5, output = {1,2,3,4,5}
         * @param starting
         * @param ending
         * @return an array containing a range of integers between the given values
         */
        private ArrayList<Integer> createArrayOfNumbersBetween(int starting, int ending) {
            ArrayList<Integer> numbers = new ArrayList<>();
            
            for (int x = starting; x < ending; x++) {
                numbers.add(x);
            }
            
            return numbers;
        }
    }

    private final PlaneColumnLayout[] planeColumnLayouts;
    private final int[] planeSize; // the maximum plane size e.g. {4,2,4}
    
    private Cell[][] planeSeats;
    
    public PlaneDimension(PlaneColumnLayout[] planeColumnLayouts, int[] planeSize) {
        this.planeColumnLayouts = planeColumnLayouts;
        this.planeSize = planeSize;
     
        this.createPlaneSeats();
    }

    private void createPlaneSeats() {
        
    }
    
    public Cell[][] getPrioritySeats() {
        
    }
    
    /**
     * Get the row numbers for the seats.
     * 
     * @param forPrioritySeats boolean to decide what seat type is needed
     * @return an integer array with the row numbers for the seats
     */
    private Integer[] calculateRowNumbers(boolean forPrioritySeats) {
        int totalRows = 0;
        ArrayList<Integer> rowNumbers = new ArrayList<>();
        
        for (PlaneColumnLayout layout : this.planeColumnLayouts) {
            // Check if this is the correct row type
            if (layout.isPriority == forPrioritySeats) {
                // Add all of the row numbers to the arraylist
                for (int y = 0; y < layout.getNumberOfRows(); y++) {
                    rowNumbers.add(y + totalRows);
                }
            }
            
            totalRows += layout.getNumberOfRows();
        }
        
        Object[] array = rowNumbers.toArray();
        return Arrays.copyOf(array, array.length, Integer[].class);
    }
    

//    
//    
//    
//    
//    
//    /**
//     * The number of priority rows for this plane dimension.
//     * 
//     * @return an integer of the number of rows
//     */
//    public int getNumberOfPriorityRows() {
//        
//        
//        // Priority rows are the first set of numbers
//        return this.planeDimentions[1][0];
//    }
//    
//    /**
//     * The split of the priority rows.
//     * 
//     * @return an array containing the split representing the priority rows
//     */
//    public int[] getPriorityColumnSplit() {
//        return this.planeDimentions[0];
//    }
//    
//    /**
//     * The number of of non-priority rows.
//     * 
//     * @return and integer of the number of non-priority rows
//     */
//    public int getNumberOfRows() {
//        int numberOfRows = 0;
//        
//        for (int x = 3; x < this.planeDimentions.length; x += 2) {
//            numberOfRows += this.planeDimentions[x][0];
//        }
//        
//        return numberOfRows;
//    }
//    
//    /**
//     * All of the column splits for the non-priority rows.
//     * 
//     * @return an integer array containing all of the column splits
//     */
//    public int[][] getColumnSplit() {
//        ArrayList<int[]> container = new ArrayList<>();
//        
//        for (int x = 2; x < this.planeDimentions.length; x += 2) {
//            container.add(this.planeDimentions[x]);
//        }
//        
//        return (int[][]) container.toArray();
//    } 
//    
//    /**
//     * The number of columns for the given row split, not including any aisles.
//     * 
//     * @param rowSplit the row split to calculate the number of columns
//     * @return the number of columns in the given row split
//     */
//    public int getNumberOfColumnsForRowSplit(int[] rowSplit) {
//        int numberOfColumns = 0;
//        
//        for (int column: rowSplit) {
//            if (column != 0) {
//                numberOfColumns += column;
//            }
//        }
//        
//        return numberOfColumns;
//    }
//    
//    /**
//     * The number of aisles for a 
//     * @return 
//     */
//    public int getNumberOfAisles(){
//        int numberOfAisles = 0;
//        
//        for (int column : this.columnSplit) {
//            if (column== 0) {
//                numberOfAisles += 1;
//            }
//            
//        }
//        return numberOfAisles;
//    }
//    
//    
//    public int totalNumberOfSeats() {        
//        return (this.getNumberOfColumns() * this.getNumberOfRows()) + (this.getNumberOfPriorityColumns() * this.getNumberOfPriorityRows());
//    }
    
}
