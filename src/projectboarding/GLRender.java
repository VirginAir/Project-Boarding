package projectboarding;

import glhandler.ShaderHandler;
import java.io.IOException;
//import glshapes.Triangle;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.media.opengl.GL;
import jml.mat3;
import sceneobjects.Scene;

/**
 *
 * @author Ben Cook
 */
public class GLRender implements GLEventListener{
    
    private Cell[][] cells;
    private ArrayList<Passenger> passengers;
    private int programHandle;
    private GLTexture tex;
    //private boolean test = false;
    
    private Scene scene;

    /**
     * Create the renderer
     * @param cells the cells to render
     * @param passengers the passengers to render
     */
    public GLRender(Cell[][] cells, ArrayList<Passenger> passengers){
        this.cells = cells;
        this.passengers = passengers;
        this.scene = new Scene();
    }
    
    /** 
     * Get the cells
     * @return the cells
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Set the cells
     * @param cells the cells
     */
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * Get the passengers
     * @return the passengers
     */
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * Set the passengers 
     * @param passengers the passengers
     */
    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
        scene.resetPassengerList();
    }

    /**
     * Initalize the render screen
     * @param drawable 
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        final GL3 gl = drawable.getGL().getGL3();
        
        gl.glEnable(GL3.GL_BLEND);
        gl.glBlendFunc(GL3.GL_SRC_ALPHA, GL3.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL3.GL_TEXTURE_2D);
        
        try {
            this.scene.createScene(600, 800, drawable, this.cells, this.passengers);
        } catch (IOException ex) {
            Logger.getLogger(GLRender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        gl.glClearColor(0.5f, 0.8f, 0.5f, 0.0f);
        
        int vertexShader = ShaderHandler.createShader("shaders/vertex_shader.glsl", GL3.GL_VERTEX_SHADER, gl);
        int fragmentShader = ShaderHandler.createShader("shaders/fragment_shader.glsl", GL3.GL_FRAGMENT_SHADER, gl);
        
        int shaderList[] = {vertexShader, fragmentShader};
        
        programHandle = ShaderHandler.createProgram(shaderList, gl);
     
        ShaderHandler.linkProgram(programHandle, gl);
        gl.glUseProgram(programHandle);
    }

    /**
     * Dispose of the render
     * @param drawable 
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
        final GL3 gl = drawable.getGL().getGL3();
    }

    /**
     * Display the render
     * @param drawable 
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        final GL3 gl = drawable.getGL().getGL3();
        gl.glUseProgram(programHandle);
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        
        mat3 modelMatrix = new mat3();
        int modelviewMatrixLocation = gl.glGetUniformLocation(programHandle, "modelViewMatrix");
        gl.glUniformMatrix4fv(modelviewMatrixLocation, 1, false, modelMatrix.getMatrixGLForm(), 0);
        
        int textureLocation = gl.glGetUniformLocation(programHandle, "textureSampler");
        gl.glUniform1i(textureLocation, 0);
            
        scene.getHullTexture().getTx().enable(gl);
        scene.getHullTexture().getTx().bind(gl);
        gl.glBindVertexArray(scene.getHullID());
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, 4);
        
        for(int i = 0; i < scene.getChairList().size(); i++){
            if(scene.getChairList().get(i).isVisible()){
                modelMatrix.setIdentity();
                modelMatrix.translate(scene.getChairList().get(i).getPosition().getX(), scene.getChairList().get(i).getPosition().getY());


                gl.glUniformMatrix4fv(modelviewMatrixLocation, 1, false, modelMatrix.getMatrixGLForm(), 0);

                scene.getChairList().get(i).getTex().getTx().enable(gl);
                scene.getChairList().get(i).getTex().getTx().bind(gl);
                gl.glBindVertexArray(scene.getChairList().get(i).getObject());
                gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, 4);
            }
        }
        scene.updatePassengers(passengers);
        
        for(int i = 0; i < scene.getPassengerList().size(); i++){
            modelMatrix.setIdentity();
            modelMatrix.translate(scene.getPassengerList().get(i).getPosition().getX(), scene.getPassengerList().get(i).getPosition().getY());
            
            gl.glUniformMatrix4fv(modelviewMatrixLocation, 1, false, modelMatrix.getMatrixGLForm(), 0);
            
            
            scene.getPassengerList().get(i).getTex().getTx().enable(gl);
            scene.getPassengerList().get(i).getTex().getTx().bind(gl);
            gl.glBindVertexArray(scene.getPassengerList().get(i).getHandle());
            gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, 4);
        }
        gl.glFlush();
        
    }

    /**
     * Reshape the render
     * @param drawable
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL3 gl = drawable.getGL().getGL3();
    }
    
}
