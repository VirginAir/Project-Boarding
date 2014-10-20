
package projectboarding;

import glhandler.ShaderHandler;
import glshapes.Triangle;
import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import glhandler.GLBufferHandler;
import glshapes.Square;
import java.util.ArrayList;
import projectboarding.BoardingController.Passenger;

/**
 *
 * @author Ben Cook
 */
public class GLRender implements GLEventListener{
    
    private int[] triangleVaoHandle = new int[1];
    private int[][] squareVaoHandle = new int[50][1];
    private int[][] squareTakenVaoHandle = new int[49][1];
    private Cell[][] cells;
    private int numRows;
    private int cellsInRow;
    private ArrayList<Passenger> passengers;
    private int pCount;
    
    public GLRender(Cell[][] cells, ArrayList<Passenger> passengers){
        this.cells = cells;
        this.numRows = this.cells.length;
        this.cellsInRow = this.cells[0].length;
        this.passengers = passengers;
        this.pCount = passengers.size();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL4 gl = drawable.getGL().getGL4();
        gl.glClearColor(0.5f, 0.8f, 0.5f, 0.0f);
        
        int vertexShader = ShaderHandler.createShader("shaders/vertex_shader.glsl", GL4.GL_VERTEX_SHADER, gl);
        int fragmentShader = ShaderHandler.createShader("shaders/fragment_shader.glsl", GL4.GL_FRAGMENT_SHADER, gl);
        
        int shaderList[] = {vertexShader, fragmentShader};
        
        int programHandle = ShaderHandler.createProgram(shaderList, gl);
        
        final int VERTEX_POSITION_INDEX = 0;
        final int VERTEX_COLOUR_INDEX = 1;
        
        float[] posDataHull = 
        {
            -1.0f, 0.6f, 0.0f,
            -1.0f, -0.6f, 0.0f,
            1.0f, -0.6f, 0.0f,
            1.0f, 0.6f, 0.0f
        };
        float[] colDataHull = 
        {
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f
        };
        Square square = new Square(posDataHull, colDataHull);
        GLBufferHandler.setupBuffers(squareVaoHandle[0], square.getPositionData(), square.getColourData(), VERTEX_POSITION_INDEX, VERTEX_COLOUR_INDEX, gl);
        int k = 0;
        int s = 0;
        for(int j = 0; j < numRows; j++){
            float jump = 2.0f/7.0f;
             float xpos = -1.0f + jump*(float)j + 0.1f;
            for(int i = 0; i < cellsInRow; i++){
                
                
                float ypos = -0.55f + (float)i*0.16f;
                
                float[] posDataChairTaken = 
                {
                    xpos, ypos+0.15f, 0.0f,
                    xpos, ypos, 0.0f,
                    xpos+jump-0.16f, ypos, 0.0f,
                    xpos+jump-0.16f, ypos+0.15f, 0.0f
                };
                
                float[] colDataChairTaken = 
                    {
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f
                    };
                s++;
                Square takenChair = new Square(posDataChairTaken, colDataChairTaken);
                GLBufferHandler.setupBuffers(squareTakenVaoHandle[j*cellsInRow + i], takenChair.getPositionData(), takenChair.getColourData(), VERTEX_POSITION_INDEX, VERTEX_COLOUR_INDEX, gl);
                
                if(cells[j][i].getCellType() == Cell.CellType.NONE || cells[j][i].getCellType() == Cell.CellType.AISLE){
                    continue;
                }
                
                float[] posDataChair = 
                {
                    xpos, ypos+0.15f, 0.0f,
                    xpos, ypos, 0.0f,
                    xpos+jump-0.16f, ypos, 0.0f,
                    xpos+jump-0.16f, ypos+0.15f, 0.0f
                };
                if(cells[j][i].getCellType() == Cell.CellType.PRIORITY_SEAT){
                    float[] colDataChair = 
                    {
                        1.0f, 1.0f, 0.0f,
                        1.0f, 1.0f, 0.0f,
                        1.0f, 1.0f, 0.0f,
                        1.0f, 1.0f, 0.0f
                    };
                    Square squareChair = new Square(posDataChair, colDataChair);
                    GLBufferHandler.setupBuffers(squareVaoHandle[k+1], squareChair.getPositionData(), squareChair.getColourData(), VERTEX_POSITION_INDEX, VERTEX_COLOUR_INDEX, gl);
                } else { 
                    float[] colDataChair = 
                    {
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f
                    };
                    Square squareChair = new Square(posDataChair, colDataChair);
                    GLBufferHandler.setupBuffers(squareVaoHandle[k+1], squareChair.getPositionData(), squareChair.getColourData(), VERTEX_POSITION_INDEX, VERTEX_COLOUR_INDEX, gl);
                } 
                
                k++;
            }
        }

        ShaderHandler.linkProgram(programHandle, gl);
        gl.glUseProgram(programHandle);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        final GL4 gl = drawable.getGL().getGL4();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL4 gl = drawable.getGL().getGL4();
        
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
        
        for(int i = 0; i < 49; i++){
            gl.glBindVertexArray(squareVaoHandle[i][0]);
            gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 0, 4);
        }
        
        pCount = passengers.size();
        
        for(int k = 0; k < pCount; k++){
           Cell pCell = passengers.get(k).getCurrentCell();
           int row = pCell.getCellRow();
           int column = pCell.getCellColumn();
           int place = row*cellsInRow + column;
           gl.glBindVertexArray(squareTakenVaoHandle[place][0]);
           gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 0, 4);
        }
        
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL4 gl = drawable.getGL().getGL4();
    }
    
}
