/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glhandler;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import javax.media.opengl.GL4;

/**
 *
 * @author Ben Cook
 */
public class GLBufferHandler {
    
    public static void setupBuffers(int[] objectVaoHandle, float[] positionData, float[] colourData, final int vertexPositionIndex, final int colourPositionIndex, GL4 gl){
        int vboHandles[] = new int[2];
        gl.glGenBuffers(2, vboHandles, 0);
        
        int positionBufferHandle = vboHandles[0];
        int colourBufferHandle = vboHandles[1];
        
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, positionBufferHandle);
        FloatBuffer positionBufferData = Buffers.newDirectFloatBuffer(positionData);
        int numBytes = positionData.length * 4;
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, numBytes, positionBufferData, GL4.GL_STATIC_DRAW);
        
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, colourBufferHandle);
        FloatBuffer colourBufferData = Buffers.newDirectFloatBuffer(colourData);
        numBytes = colourData.length * 4;
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, numBytes, colourBufferData, GL4.GL_STATIC_DRAW);
        
        gl.glGenVertexArrays(1, objectVaoHandle, 0);
        gl.glBindVertexArray(objectVaoHandle[0]);
        
        gl.glEnableVertexAttribArray(vertexPositionIndex);
        gl.glEnableVertexAttribArray(colourPositionIndex);
        
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, positionBufferHandle);
        gl.glVertexAttribPointer(vertexPositionIndex, 3, GL4.GL_FLOAT, Boolean.FALSE, 0, 0);
        
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, colourBufferHandle);
        gl.glVertexAttribPointer(colourPositionIndex, 3, GL4.GL_FLOAT, Boolean.FALSE, 0, 0);
    }
}
