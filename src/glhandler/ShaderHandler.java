package glhandler;

import javax.media.opengl.GL3;

/**
 *
 * @author Ben Cook
 */
public class ShaderHandler {
    
    /**
     * Create the shader handler
     * @param path
     * @param type
     * @param gl
     * @return 
     */
    public static int createShader(String path, int type, GL3 gl){
        
        int shader = gl.glCreateShader(type);
        
        if(shader ==0){
            System.err.println("Couldn't create shader");
            System.exit(1);
        }
        
        String code = ShaderLoader.loadShaderFile(path);
        gl.glShaderSource(shader, 1, new String[]{code}, null);
        
        gl.glCompileShader(shader);
        
        int result[] = new int[1];
        gl.glGetShaderiv(shader, GL3.GL_COMPILE_STATUS, result, 0);
        if(result[0] == GL3.GL_FALSE){
            System.err.println("Shader compilation error: " + path);
            
            int logLength[] = new int[1];
            gl.glGetShaderiv(shader, GL3.GL_INFO_LOG_LENGTH, logLength, 0);
            
            if(logLength[0] > 0){
                byte[] log = new byte[logLength[0]];
                gl.glGetShaderInfoLog(shader, logLength[0], (int[])null, 0, log, 0);
                System.out.println("Shader log: " + new String(log));
            }
            
            System.exit(1);
        }
        
        return shader;
    }
    
    /**
     * Create the program
     * @param shaderList
     * @param gl
     * @return 
     */
    public static int createProgram(int shaderList[], GL3 gl){
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
    
    /**
     * Link the program
     * @param programHandle
     * @param gl 
     */
    public static void linkProgram(int programHandle, GL3 gl){
        gl.glLinkProgram(programHandle);
        
        int status[] = new int[1];
        gl.glGetProgramiv(programHandle, GL3.GL_LINK_STATUS, status, 0);
        
        if(status[0] == GL3.GL_FALSE){
            System.err.println("Failed to link shader program");
            int logLength[] = new int[1];
            gl.glGetShaderiv(programHandle, GL3.GL_INFO_LOG_LENGTH, logLength, 0);
            
            if(logLength[0] > 1){
                byte[] log = new byte[logLength[0]];
                gl.glGetShaderInfoLog(programHandle, logLength[0], (int[])null, 0, log, 0);
                System.out.println("Program log: " + new String(log));
            }
            System.exit(1);
            
        }
        
    }
}
