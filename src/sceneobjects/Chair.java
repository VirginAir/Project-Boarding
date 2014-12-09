package sceneobjects;

//import glshapes.Square;

import projectboarding.GLTexture;


/**
 *
 * @author Ben Cook
 */
public class Chair {
    private Vector position;
    private int handle;
    private GLTexture tex;
    private boolean visible;

    public Chair(Vector position, int handle, GLTexture tex, boolean visible) {
        this.position = position;
        this.handle = handle;
        this.visible = visible;
        this.tex = tex;
    }

    public Vector getPosition() {
        return position;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public int getObject() {
        return handle;
    }

    public void setObject(int handle) {
        this.handle = handle;
    }

    public GLTexture getTex() {
        return tex;
    }

    public void setTex(GLTexture tex) {
        this.tex = tex;
    }
    
    
}
