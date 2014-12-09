package sceneobjects;

import glhandler.GLBufferHandler;
import java.util.ArrayList;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import projectboarding.Cell;
import projectboarding.Passenger;

/**
 *
 * @author Ben Cook
 */
public class Scene {
    
    private int hullID;
    private int passengerHandleID;
    private ArrayList<Chair> chairList;
    private ArrayList<PassengerObject> passengerList;
    
    private int rowCount;
    private int columnCount;
    
    /**
     * Create the scene
     */
    public Scene(){
        chairList = new ArrayList<Chair>();
        passengerList = new ArrayList<PassengerObject>();
    }
    
    /**
     * Create the scene
     * @param screenHeight the height
     * @param screenWidth the width
     * @param drawable the drawable
     * @param cells the cells
     * @param passengers the passengers
     */
    public void createScene(int screenHeight, int screenWidth, GLAutoDrawable drawable, Cell[][] cells, ArrayList<Passenger> passengers){
        rowCount = cells.length;
        columnCount = cells[0].length;
        
        float chairWidthBoundarySize = 2.f/(float)rowCount;
        float chairHeightBoundarySize = 1.2f/(float)columnCount;
        float paddingHeight = chairHeightBoundarySize*0.05f;
        float paddingWidth = chairWidthBoundarySize*0.05f;
        float chairWidthSize = chairWidthBoundarySize-paddingWidth;
        float chairHeightSize = chairHeightBoundarySize-paddingHeight;
        
        float actualHeight = chairHeightBoundarySize*screenHeight;
        chairWidthBoundarySize = actualHeight/screenWidth;
        float actualPaddingHeight = paddingHeight*screenHeight;
        paddingWidth = actualPaddingHeight/screenWidth;
            
        chairWidthSize = chairWidthBoundarySize-paddingWidth;

        if((chairWidthBoundarySize*rowCount) > 2.f){
            chairWidthBoundarySize = 2.f/(float)rowCount;
            paddingWidth = chairWidthBoundarySize*0.05f;
            chairWidthSize = chairWidthBoundarySize-paddingWidth;
            float actualWidth = chairWidthBoundarySize*screenWidth;
            chairHeightBoundarySize = actualWidth/screenHeight;
            float actualPaddingWidth = paddingWidth*screenWidth;
            paddingHeight = actualPaddingWidth/screenHeight;

            chairHeightSize = chairHeightBoundarySize-paddingHeight;
            
        }
        float hDistance = chairWidthBoundarySize*rowCount;
        float hAdjust = hDistance/2-1;
        
        float vDistance = chairHeightBoundarySize*columnCount;
        float vAdjust = vDistance/2-1;
        
        float[] posData = 
        {
             paddingWidth-1,             paddingHeight-1, 0.0f,
             chairWidthSize-paddingWidth-1,   paddingHeight-1, 0.0f,
             chairWidthSize-paddingWidth-1,   chairHeightSize-paddingHeight-1, 0.0f,
             paddingWidth-1,            chairHeightSize-paddingHeight-1, 0.0f
        };
        
        float[] colDataNormSeat = 
        {
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f
        };
        
        float[] colDataPrioritySeat = 
        {
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f
        };
        
        float[] colDataPassenger = 
        {
           0.0f, 1.0f, 0.0f,
           0.0f, 1.0f, 0.0f,
           0.0f, 1.0f, 0.0f,
           0.0f, 1.0f, 0.0f
        };
        
        final GL3 gl = drawable.getGL().getGL3();
        createHull(vDistance, vAdjust, paddingHeight, gl);
        final int VERTEX_POSITION_INDEX = 0;
        final int VERTEX_COLOUR_INDEX = 1;
        int[] handleNorm = {0};
        int[] handlePrior = {0};
        int[] handlePass = {0};
        GLBufferHandler.setupBuffers(handleNorm, posData, colDataNormSeat, VERTEX_POSITION_INDEX, VERTEX_COLOUR_INDEX, gl);
        GLBufferHandler.setupBuffers(handlePrior, posData, colDataPrioritySeat, VERTEX_POSITION_INDEX, VERTEX_COLOUR_INDEX, gl);
        GLBufferHandler.setupBuffers(handlePass, posData, colDataPassenger, VERTEX_POSITION_INDEX, VERTEX_COLOUR_INDEX, gl);
        
        passengerHandleID = handlePass[0];
        
        for(int i = 0; i < rowCount; i++){
            float x = i*chairWidthBoundarySize - hAdjust;
            for(int j = 0; j < columnCount; j++){
                
                if(cells[i][j].getCellType() == Cell.CellType.PRIORITY_SEAT){
                    float y = j*chairHeightBoundarySize - vAdjust;
                    chairList.add(new Chair(new Vector(x, y), handlePrior[0], true));
                } else if(cells[i][j].getCellType() == Cell.CellType.SEAT) {
                    float y = j*chairHeightBoundarySize - vAdjust;
                    chairList.add(new Chair(new Vector(x, y), handleNorm[0], true));
                } else  {
                    float y = j*chairHeightBoundarySize - vAdjust;
                    chairList.add(new Chair(new Vector(x, y), handleNorm[0], false));
                }
            }
        }
        
    }

    /**
     * The hull id
     * @return the hull id
     */
    public int getHullID() {
        return hullID;
    }

    /**
     * Set the hull id
     * @param hullID the hull id
     */
    public void setHullID(int hullID) {
        this.hullID = hullID;
    }
    
    /**
     * Create the hull
     * @param distance the distance
     * @param vAdjust the adjust
     * @param padding the padding
     * @param gl the gl
     */
    private void createHull(float distance, float vAdjust, float padding, GL3 gl){
        float[] posData = 
        {
             -1.f,             Math.abs(vAdjust)-padding-1, 0.0f,
             -1.f,   Math.abs(vAdjust)+padding+distance-1, 0.0f,
             1.f,   Math.abs(vAdjust)+padding+distance-1, 0.0f,
             1.f,            Math.abs(vAdjust)-padding-1, 0.0f
        };
        
        float[] colData = 
        {
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f
        };
        
        final int VERTEX_POSITION_INDEX = 0;
        final int VERTEX_COLOUR_INDEX = 1;
        
        int[] handle = {0};
        GLBufferHandler.setupBuffers(handle, posData, colData, VERTEX_POSITION_INDEX, VERTEX_COLOUR_INDEX, gl);
        
        hullID = handle[0];
    }
    
    /**
     * Update the passengers
     * @param passengers the passengers
     */
    public void updatePassengers(ArrayList<Passenger> passengers){
        for(int i = 0; i < passengers.size(); i++){
            if(i >= this.passengerList.size()){
                int row = passengers.get(i).getCurrentCell().getCellRow();
                int column = passengers.get(i).getCurrentCell().getCellColumn();
                passengerList.add(new PassengerObject(i, chairList.get(row*columnCount + column).getPosition(), passengerHandleID));
            } else {
                int row = passengers.get(i).getCurrentCell().getCellRow();
                int column = passengers.get(i).getCurrentCell().getCellColumn();
                passengerList.get(i).setPosition(chairList.get(row*columnCount + column).getPosition());
            }
        }
    }

    /**
     * Get the passenger list
     * @return the passenger list
     */
    public ArrayList<PassengerObject> getPassengerList() {
        return passengerList;
    }

    /**
     * Set the passenger list
     * @param passengerList the passenger list
     */
    public void setPassengerList(ArrayList<PassengerObject> passengerList) {
        this.passengerList = passengerList;
    }
    
    /**
     * Reset the passenger list
     */
    public void resetPassengerList(){
        this.passengerList.clear();
    }

    /**
     * Get the chair list
     * @return the chair list
     */
    public ArrayList<Chair> getChairList() {
        return chairList;
    }

    /**
     * Set the chair list
     * @param chairList the chair list
     */
    public void setChairList(ArrayList<Chair> chairList) {
        this.chairList = chairList;
    }
}