package sceneobjects;

/**
 *
 * @author Ben Cook
 */
public class PassengerObject {
    private int ID;
    private Vector position;
    private int handle;

    public PassengerObject(int ID, Vector position, int handle) {
        this.ID = ID;
        this.position = position;
        this.handle = handle;
    }

    public Vector getPosition() {
        return position;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }
}
