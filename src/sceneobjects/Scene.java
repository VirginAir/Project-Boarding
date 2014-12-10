package sceneobjects;

import glhandler.GLBufferHandler;
import java.io.IOException;
//import glshapes.Square;
import java.util.ArrayList;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import projectboarding.Cell;
import projectboarding.GLTexture;
import projectboarding.Passenger;

/**
 *
 * @author Ben Cook
 */
public class Scene {

    private int hullID;
    private GLTexture hullTexture;
    private int passengerHandleID;
    private ArrayList<Chair> chairList;
    private ArrayList<PassengerObject> passengerList;
    private ArrayList<GLTexture> textureList;
    private int rowCount;
    private int columnCount;

    /**
     * Create the scene
     */
    public Scene() {
        chairList = new ArrayList<Chair>();
        passengerList = new ArrayList<PassengerObject>();
    }

    /**
     * Create the scene
     *
     * @param screenHeight the height
     * @param screenWidth the width
     * @param drawable the drawable
     * @param cells the cells
     * @param passengers the passengers
     */
    public void createScene(int screenHeight, int screenWidth, GLAutoDrawable drawable, Cell[][] cells, ArrayList<Passenger> passengers) throws IOException {
        rowCount = cells.length;
        columnCount = cells[0].length;

        float chairWidthBoundarySize = 2.f / (float) rowCount;
        float chairHeightBoundarySize = 1.2f / (float) columnCount;
        float paddingHeight = chairHeightBoundarySize * 0.05f;
        float paddingWidth = chairWidthBoundarySize * 0.05f;
        float chairWidthSize = chairWidthBoundarySize - paddingWidth;
        float chairHeightSize = chairHeightBoundarySize - paddingHeight;

        float actualHeight = chairHeightBoundarySize * screenHeight;
        chairWidthBoundarySize = actualHeight / screenWidth;
        float actualPaddingHeight = paddingHeight * screenHeight;
        paddingWidth = actualPaddingHeight / screenWidth;

        chairWidthSize = chairWidthBoundarySize - paddingWidth;

        if ((chairWidthBoundarySize * rowCount) > 2.f) {
            chairWidthBoundarySize = 2.f / (float) rowCount;
            paddingWidth = chairWidthBoundarySize * 0.05f;
            chairWidthSize = chairWidthBoundarySize - paddingWidth;
            float actualWidth = chairWidthBoundarySize * screenWidth;
            chairHeightBoundarySize = actualWidth / screenHeight;
            float actualPaddingWidth = paddingWidth * screenWidth;
            paddingHeight = actualPaddingWidth / screenHeight;

            chairHeightSize = chairHeightBoundarySize - paddingHeight;

        }
        float hDistance = chairWidthBoundarySize * rowCount;
        float hAdjust = hDistance / 2 - 1;

        float vDistance = chairHeightBoundarySize * columnCount;
        float vAdjust = vDistance / 2 - 1;

        float[] posData = {
            paddingWidth - 1, -(paddingHeight - 1), 0.0f,
            paddingWidth - 1, -(chairHeightSize - paddingHeight - 1), 0.0f,
            chairWidthSize - paddingWidth - 1, -(chairHeightSize - paddingHeight - 1), 0.0f,
            chairWidthSize - paddingWidth - 1, -(paddingHeight - 1), 0.0f
        };

        float[] uvData = {
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
        };

        final GL3 gl = drawable.getGL().getGL3();

        textureList = new ArrayList<>();
        textureList.add(new GLTexture("Chair", "images/chair.png", gl));
        textureList.add(new GLTexture("Priority", "images/priority.png", gl));
        textureList.add(new GLTexture("Passenger", "images/passenger.png", gl));
        textureList.add(new GLTexture("Passenger", "images/hull.png", gl));


        createHull(vDistance, vAdjust, paddingHeight + 0.02f, gl);
        final int VERTEX_POSITION_INDEX = 0;
        final int VERTEX_UV_INDEX = 1;
        int[] handleNorm = {0};
        int[] handlePrior = {0};
        int[] handlePass = {0};
        GLBufferHandler.setupBuffers(handleNorm, posData, uvData, VERTEX_POSITION_INDEX, VERTEX_UV_INDEX, gl);
        GLBufferHandler.setupBuffers(handlePrior, posData, uvData, VERTEX_POSITION_INDEX, VERTEX_UV_INDEX, gl);
        GLBufferHandler.setupBuffers(handlePass, posData, uvData, VERTEX_POSITION_INDEX, VERTEX_UV_INDEX, gl);

        passengerHandleID = handlePass[0];

        for (int i = 0; i < rowCount; i++) {
            float x = i * chairWidthBoundarySize - hAdjust;
            for (int j = 0; j < columnCount; j++) {

                if (cells[i][j].getCellType() == Cell.CellType.PRIORITY_SEAT) {
                    float y = -(j * chairHeightBoundarySize - vAdjust);
                    chairList.add(new Chair(new Vector(x, y), handlePrior[0], textureList.get(1), true));
                } else if (cells[i][j].getCellType() == Cell.CellType.SEAT) {
                    float y = -(j * chairHeightBoundarySize - vAdjust);
                    chairList.add(new Chair(new Vector(x, y), handleNorm[0], textureList.get(0), true));
                } else {
                    float y = -(j * chairHeightBoundarySize - vAdjust);
                    chairList.add(new Chair(new Vector(x, y), handleNorm[0], textureList.get(1), false));
                }
            }
        }

    }

    /**
     * The hull id
     *
     * @return the hull id
     */
    public int getHullID() {
        return hullID;
    }

    /**
     * Set the hull id
     *
     * @param hullID the hull id
     */
    public void setHullID(int hullID) {
        this.hullID = hullID;
    }

    /**
     * Create the hull
     *
     * @param distance the distance
     * @param vAdjust the adjust
     * @param padding the padding
     * @param gl the gl
     */
    private void createHull(float distance, float vAdjust, float padding, GL3 gl) {
        float[] posData = {
            -1.f, Math.abs(vAdjust) - padding - 1, 0.0f,
            -1.f, Math.abs(vAdjust) + padding + distance - 1, 0.0f,
            1.f, Math.abs(vAdjust) + padding + distance - 1, 0.0f,
            1.f, Math.abs(vAdjust) - padding - 1, 0.0f
        };

        float[] uvData = {
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
        };

        final int VERTEX_POSITION_INDEX = 0;
        final int VERTEX_UV_INDEX = 1;

        int[] handle = {0};
        GLBufferHandler.setupBuffers(handle, posData, uvData, VERTEX_POSITION_INDEX, VERTEX_UV_INDEX, gl);

        hullID = handle[0];

        hullTexture = textureList.get(3);
    }

    /**
     * Update the passengers
     *
     * @param passengers the passengers
     */
    public void updatePassengers(ArrayList<Passenger> passengers) {
        for (int i = 0; i < passengers.size(); i++) {
            if (i >= this.passengerList.size()) {
                int row = passengers.get(i).getCurrentCell().getCellRow();
                int column = passengers.get(i).getCurrentCell().getCellColumn();
                passengerList.add(new PassengerObject(i, chairList.get(row * columnCount + column).getPosition(), passengerHandleID, textureList.get(2)));
            } else {
                int row = passengers.get(i).getCurrentCell().getCellRow();
                int column = passengers.get(i).getCurrentCell().getCellColumn();
                passengerList.get(i).setPosition(chairList.get(row * columnCount + column).getPosition());
            }
        }
    }

    /**
     * Get the passenger list
     *
     * @return the passenger list
     */
    public ArrayList<PassengerObject> getPassengerList() {
        return passengerList;
    }

    /**
     * Set the passenger list
     *
     * @param passengerList the passenger list
     */
    public void setPassengerList(ArrayList<PassengerObject> passengerList) {
        this.passengerList = passengerList;
    }

    /**
     * Reset the passenger list
     */
    public void resetPassengerList() {
        this.passengerList.clear();
    }

    /**
     * Get the chair list
     *
     * @return the chair list
     */
    public ArrayList<Chair> getChairList() {
        return chairList;
    }

    /**
     * Set the chair list
     *
     * @param chairList the chair list
     */
    public void setChairList(ArrayList<Chair> chairList) {
        this.chairList = chairList;
    }

    /**
     * Get hull GLTexture
     *
     * @return hull's GLTexture
     */
    public GLTexture getHullTexture() {
        return hullTexture;
    }

    /**
     * Set the hull's texture
     *
     * @param hullTexture texture to set to
     */
    public void setHullTexture(GLTexture hullTexture) {
        this.hullTexture = hullTexture;
    }
}
