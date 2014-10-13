/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glshapes;

/**
 *
 * @author Ben Cook
 */
public class Triangle extends Shape{

    private float[] positionData = {
      -0.8f, -0.8f, 0.0f,  
      0.8f, -0.8f, 0.0f,
      0.0f, 0.8f, 0.0f
    };
    
    private float[] colourData = {
      1.0f, 0.0f, 0.0f,  
      0.0f, 1.0f, 0.0f,
      0.0f, 0.0f, 1.0f
    };
    
    @Override
    public float[] getPositionData() {
        return this.positionData;
    }

    @Override
    public float[] getColourData() {
        return this.colourData;
    }
}
