/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glhandler;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import javax.media.opengl.GL3;

/**
 *
 * @author Ben Cook
 */
public class GLBufferHandler {
    
    public static void setupBuffers(int[] objectVaoHandle, float[] positionData,  float[] uvData, final int vertexPositionIndex, final int uvPositionIndex, GL3 gl){
        int vboHandles[] = new int[3];
        gl.glGenBuffers(3, vboHandles, 0);
        
        int positionBufferHandle = vboHandles[0];
        int uvBufferHandle = vboHandles[1];
        
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, positionBufferHandle);
        FloatBuffer positionBufferData = Buffers.newDirectFloatBuffer(positionData);
        int numBytes = positionData.length * 4;
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, numBytes, positionBufferData, GL3.GL_STATIC_DRAW);
        
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, uvBufferHandle);
        FloatBuffer uvBufferData = Buffers.newDirectFloatBuffer(uvData);
        numBytes = uvData.length * 4;
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, numBytes, uvBufferData, GL3.GL_STATIC_DRAW);
        
        gl.glGenVertexArrays(1, objectVaoHandle, 0);
        gl.glBindVertexArray(objectVaoHandle[0]);
        
        gl.glEnableVertexAttribArray(vertexPositionIndex);
        gl.glEnableVertexAttribArray(uvPositionIndex);
        
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, positionBufferHandle);
        gl.glVertexAttribPointer(vertexPositionIndex, 3, GL3.GL_FLOAT, Boolean.FALSE, 0, 0);
        
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, uvBufferHandle);
        gl.glVertexAttribPointer(uvPositionIndex, 2, GL3.GL_FLOAT, Boolean.FALSE, 0, 0);
    }
}
