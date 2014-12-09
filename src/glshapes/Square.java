package glshapes;

/**
 *
 * @author Ben Cook
 */
public class Square extends Shape {

    private final float[] positionData;
    private final float[] colourData;
    
    /**
     * Create the square
     * @param pd the position data
     * @param cd the colour data
     */
    public Square(float[] pd, float [] cd){
        positionData = pd;
        colourData = cd;
    }
    
    /**
     * Get the position data
     * @return the position data
     */
    @Override
    public float[] getPositionData() {
        return this.positionData;
    }

    /**
     * Get the colour data
     * @return the colour data
     */
    @Override
    public float[] getColourData() {
        return this.colourData;
    }
}
