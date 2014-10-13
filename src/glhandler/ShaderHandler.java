/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glhandler;

import javax.media.opengl.GL4;

/**
 *
 * @author Ben Cook
 */
public class ShaderHandler {
    public static int createShader(String path, int type, GL4 gl){
        
        int shader = gl.glCreateShader(type);
        
        if(shader ==0){
            System.err.println("Couldn't create shader");
            System.exit(1);
        }
        
        String code = ShaderLoader.loadShaderFile(path);
        gl.glShaderSource(shader, 1, new String[]{code}, null);
        
        gl.glCompileShader(shader);
        
        int result[] = new int[1];
        gl.glGetShaderiv(shader, GL4.GL_COMPILE_STATUS, result, 0);
        if(result[0] == GL4.GL_FALSE){
            System.err.println("Shader compilation error: " + path);
            
            int logLength[] = new int[1];
            gl.glGetShaderiv(shader, GL4.GL_INFO_LOG_LENGTH, logLength, 0);
            
            if(logLength[0] > 0){
                byte[] log = new byte[logLength[0]];
                gl.glGetShaderInfoLog(shader, logLength[0], (int[])null, 0, log, 0);
                System.out.println("Shader log: " + new String(log));
            }
            
            System.exit(1);
        }
        
        return shader;
    }
    
    public static int createProgram(int shaderList[], GL4 gl){
        int programHandle = gl.glCreateProgram();
        
        if(programHandle == 0){
            System.err.println("Error creating shader program");
            System.exit(1);
        }
        
        for(int shader : shaderList){
            gl.glAttachShader(programHandle, shader);
        }
        
        return programHandle;
    }
    
    public static void linkProgram(int programHandle, GL4 gl){
        gl.glLinkProgram(programHandle);
        
        int status[] = new int[1];
        gl.glGetProgramiv(programHandle, GL4.GL_LINK_STATUS, status, 0);
        
        if(status[0] == GL4.GL_FALSE){
            System.err.println("Failed to link shader program");
            int logLength[] = new int[1];
            gl.glGetShaderiv(programHandle, GL4.GL_INFO_LOG_LENGTH, logLength, 0);
            
            if(logLength[0] > 1){
                byte[] log = new byte[logLength[0]];
                gl.glGetShaderInfoLog(programHandle, logLength[0], (int[])null, 0, log, 0);
                System.out.println("Program log: " + new String(log));
            }
            System.exit(1);
            
        }
        
    }
}
