package sceneobjects;

/**
 *
 * @author Ben Cook
 */
public class PassengerObject {
    private int ID;
    private Vector position;
    private int handle;

    /**
     * Create the passenger object
     * @param ID the passenger id
     * @param position the position
     * @param handle the handle
     */
    public PassengerObject(int ID, Vector position, int handle) {
        this.ID = ID;
        this.position = position;
        this.handle = handle;
    }

    /**
     * Get the position
     * @return the position
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * Get the id
     * @return the id
     */
    public int getID() {
        return ID;
    }

    /**
     * set the id
     * @param ID the id
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Set the position
     * @param position the position
     */
    public void setPosition(Vector position) {
        this.position = position;
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
}
