/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glshapes;

/**
 *
 * @author Ben Cook
 */
public class Square extends Shape {

    private float[] positionData;
    private float[] colourData;
    
    public Square(float[] pd, float [] cd){
        positionData = pd;
        colourData = cd;
    }
    
    @Override
    public float[] getPositionData() {
        return this.positionData;
    }

    @Override
    public float[] getColourData() {
        return this.colourData;
    }
    
}
