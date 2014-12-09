package sceneobjects;

//import glshapes.Square;

/**
 *
 * @author Ben Cook
 */
public class Chair {
    private Vector position;
    private int handle;
    private boolean visible;

    /**
     * Create the chair
     * @param position the position
     * @param handle the handle
     * @param visible if it is visible
     */
    public Chair(Vector position, int handle, boolean visible) {
        this.position = position;
        this.handle = handle;
        this.visible = visible;
    }

    /**
     * Get the chair position
     * @return the position
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * Get the handle
     * @return the handle
     */
    public int getHandle() {
        return handle;
    }

    /**
     * Set the handle
     * @param handle the handle
     */
    public void setHandle(int handle) {
        this.handle = handle;
    }

    /**
     * Get if it is visible
     * @return true visible, false not
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set if it is visible
     * @param visible true visible, false not
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Set the chair position
     * @param position the position
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    /**
     * Get the handle
     * @return the handle
     */
    public int getObject() {
        return handle;
    }

    /**
     * Set the handle
     * @param handle the handle
     */
    public void setObject(int handle) {
        this.handle = handle;
    }    
}
