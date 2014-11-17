
package projectboarding;

import glhandler.ShaderHandler;
import glshapes.Triangle;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import glhandler.GLBufferHandler;
import glshapes.Square;
import java.util.ArrayList;
import javax.media.opengl.GL;
import jml.mat3;
import projectboarding.BoardingController.Passenger;
import sceneobjects.Scene;

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
    private int programHandle;
    private boolean test = false;
    
    private Scene scene;

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
        scene.resetPassengerList();
    }
    
    public GLRender(Cell[][] cells, ArrayList<Passenger> passengers){
        this.cells = cells;
        this.numRows = this.cells.length;
        this.cellsInRow = this.cells[0].length;
        this.passengers = passengers;
        this.pCount = passengers.size();
        this.scene = new Scene();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL3 gl = drawable.getGL().getGL3();
        
        this.scene.createScene(600, 800, drawable, this.cells, this.passengers);
        gl.glClearColor(0.5f, 0.8f, 0.5f, 0.0f);
        
        int vertexShader = ShaderHandler.createShader("shaders/vertex_shader.glsl", GL3.GL_VERTEX_SHADER, gl);
        int fragmentShader = ShaderHandler.createShader("shaders/fragment_shader.glsl", GL3.GL_FRAGMENT_SHADER, gl);
        
        int shaderList[] = {vertexShader, fragmentShader};
        
        programHandle = ShaderHandler.createProgram(shaderList, gl);
     
        ShaderHandler.linkProgram(programHandle, gl);
        gl.glUseProgram(programHandle);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        final GL3 gl = drawable.getGL().getGL3();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL3 gl = drawable.getGL().getGL3();
        gl.glUseProgram(programHandle);
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        
        mat3 modelMatrix = new mat3();
        int modelviewMatrixLocation = gl.glGetUniformLocation(programHandle, "modelViewMatrix");
        gl.glUniformMatrix4fv(modelviewMatrixLocation, 1, false, modelMatrix.getMatrixGLForm(), 0);
            
        gl.glBindVertexArray(scene.getHullID());
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, 4);
        
        for(int i = 0; i < scene.getChairList().size(); i++){
            if(scene.getChairList().get(i).isVisible()){
                modelMatrix.setIdentity();
                modelMatrix.translate(scene.getChairList().get(i).getPosition().getX(), scene.getChairList().get(i).getPosition().getY());


                gl.glUniformMatrix4fv(modelviewMatrixLocation, 1, false, modelMatrix.getMatrixGLForm(), 0);

                gl.glBindVertexArray(scene.getChairList().get(i).getObject());
                gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, 4);
            }
        }
        scene.updatePassengers(passengers);
        
        for(int i = 0; i < scene.getPassengerList().size(); i++){
            modelMatrix.setIdentity();
            modelMatrix.translate(scene.getPassengerList().get(i).getPosition().getX(), scene.getPassengerList().get(i).getPosition().getY());
            
            gl.glUniformMatrix4fv(modelviewMatrixLocation, 1, false, modelMatrix.getMatrixGLForm(), 0);
            
            gl.glBindVertexArray(scene.getPassengerList().get(i).getHandle());
            gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, 4);
        }
        gl.glFlush();
        
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL3 gl = drawable.getGL().getGL3();
    }
    
}