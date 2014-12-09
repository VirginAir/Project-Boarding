package sceneobjects;

/**
 * 
 * @author Ben Cook
 */
public class Vector {
    private float x;
    private float y;

    /**
     * Create the vector
     * @param x x value
     * @param y y value
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x value
     * @return the x value
     */
    public float getX() {
        return x;
    }

    /**
     * Set the x value
     * @param x the x value
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Get the y value
     * @return the y value
     */
    public float getY() {
        return y;
    }

    /**
     * Set the y value
     * @param y the y value
     */
    public void setY(float y) {
        this.y = y;
    }
}
