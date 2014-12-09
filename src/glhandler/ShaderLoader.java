package glhandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Ben Cook
 */
public class ShaderLoader {
    
    /**
     * Load the shader file
     * @param path
     * @return 
     */
    public static String loadShaderFile(String path){
        File file = new File(path);
        BufferedReader reader;
        String temp;
        
        String separator = System.getProperty("line.separator");
        StringBuilder code = new StringBuilder();
        
        if(!file.exists()){
            System.err.println("Couldn't find file: " + path);
            System.exit(1);
        }
        
        try{
            reader = new BufferedReader(new FileReader(file));
            
            while((temp = reader.readLine()) != null){
                code.append(temp);
                code.append(separator);
            }
            reader.close();
            
        }catch(Exception e){
            System.err.println("Error reading: " + path + ", " + e.getMessage());
            System.exit(1);
        }
        
        return code.toString();
    }
}
