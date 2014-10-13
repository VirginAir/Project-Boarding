
package projectboarding;

import glhandler.ShaderHandler;
import glshapes.Triangle;
import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import glhandler.GLBufferHandler;
import glshapes.Square;

/**
 *
 * @author Ben cook
 */
public class GLRender implements GLEventListener{
    
    private int[] triangleVaoHandle = new int[1];
    private int[][] squareVaoHandle = new int[10][1];
    
    public GLRender(){
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
        
        for(int i = 0; i < 9; i++){
            float jump = 2.0f/9.0f;
            float xpos = -1.0f + jump*(float)i + 0.05f;
            float[] posDataChair = 
            {
                xpos, -0.35f, 0.0f,
                xpos, -0.5f, 0.0f,
                xpos+jump-0.1f, -0.5f, 0.0f,
                xpos+jump-0.1f, -0.35f, 0.0f
            };
            float[] colDataChair = 
            {
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f
            };
            Square squareChair = new Square(posDataChair, colDataChair);
            GLBufferHandler.setupBuffers(squareVaoHandle[i+1], squareChair.getPositionData(), squareChair.getColourData(), VERTEX_POSITION_INDEX, VERTEX_COLOUR_INDEX, gl);
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
        
        for(int i = 0; i < 10; i++){
            gl.glBindVertexArray(squareVaoHandle[i][0]);
            gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 0, 4);
        }
        
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL4 gl = drawable.getGL().getGL4();
    }
    
}
