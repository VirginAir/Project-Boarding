package sceneobjects;

import glhandler.GLBufferHandler;
import glshapes.Square;
import java.util.ArrayList;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import projectboarding.BoardingController;
import projectboarding.BoardingController.Passenger;
import projectboarding.Cell;

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
    
    public Scene(){
        chairList = new ArrayList<Chair>();
        passengerList = new ArrayList<PassengerObject>();
    }
    public void createScene(int screenHeight, int screenWidth, GLAutoDrawable drawable, Cell[][] cells, ArrayList<BoardingController.Passenger> passengers){
        
        
        rowCount = cells.length;
        columnCount = cells[0].length;
        
        float screenRatio = (float)screenWidth/(float)screenHeight;
        float seatRatio = (float)rowCount/(float)columnCount;
        
        
        float chairWidthBoundarySize = 2.f/(float)rowCount;
        float chairHeightBoundarySize = 1.2f/(float)columnCount;
        float paddingHeight = chairHeightBoundarySize*0.05f;
        float paddingWidth = chairWidthBoundarySize*0.05f;
        float chairWidthSize = (chairWidthBoundarySize-(paddingWidth));
        float chairHeightSize = (chairHeightBoundarySize-(paddingHeight));
        
        float actualHeight = (chairHeightBoundarySize*screenHeight);
        chairWidthBoundarySize = (actualHeight/screenWidth);
        float actualPaddingHeight = (paddingHeight*screenHeight);
        paddingWidth = actualPaddingHeight/screenWidth;
            
        chairWidthSize = (chairWidthBoundarySize-(paddingWidth));
        
        
        
        
        if((chairWidthBoundarySize*rowCount) > 2.f){
            chairWidthBoundarySize = 2.f/(float)rowCount;
            paddingWidth = chairWidthBoundarySize*0.05f;
            chairWidthSize = (chairWidthBoundarySize-(paddingWidth));
            float actualWidth = (chairWidthBoundarySize*screenWidth);
            chairHeightBoundarySize = (actualWidth/screenHeight);
            float actualPaddingWidth = (paddingWidth*screenWidth);
            paddingHeight = actualPaddingWidth/screenHeight;

            chairHeightSize = (chairHeightBoundarySize-(paddingHeight));
            
        }
        float hDistance = chairWidthBoundarySize*rowCount;
        float hAdjust = (hDistance/2)-1;
        
        float vDistance = chairHeightBoundarySize*columnCount;
        float vAdjust = (vDistance/2)-1;
        
        
        
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
            float x = (i*chairWidthBoundarySize) - hAdjust;
            for(int j = 0; j < columnCount; j++){
                
                if(cells[i][j].getCellType() == Cell.CellType.PRIORITY_SEAT){
                    float y = (j*chairHeightBoundarySize) - vAdjust;
                    chairList.add(new Chair(new Vector(x, y), handlePrior[0], true));
                } else if(cells[i][j].getCellType() == Cell.CellType.SEAT) {
                    float y = (j*chairHeightBoundarySize) - vAdjust;
                    chairList.add(new Chair(new Vector(x, y), handleNorm[0], true));
                } else  {
                    float y = (j*chairHeightBoundarySize) - vAdjust;
                    chairList.add(new Chair(new Vector(x, y), handleNorm[0], false));
                }
            }
        }
        
    }

    public int getHullID() {
        return hullID;
    }

    public void setHullID(int hullID) {
        this.hullID = hullID;
    }
    
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

    public ArrayList<PassengerObject> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(ArrayList<PassengerObject> passengerList) {
        this.passengerList = passengerList;
    }

    public ArrayList<Chair> getChairList() {
        return chairList;
    }

    public void setChairList(ArrayList<Chair> chairList) {
        this.chairList = chairList;
    }
    
    
    
}
