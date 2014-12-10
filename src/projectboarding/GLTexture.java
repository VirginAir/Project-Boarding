package projectboarding;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.opengl.GL3;

/**
 *
 * @author Ben Cook
 */
public class GLTexture {

    private String name;
    private Texture tx;
    private String location;

    /**
     * Create a GLTexture instance
     *
     * @param name the name of the texture
     * @param location the file location of the texture
     * @param gl the GL context to reference
     */
    public GLTexture(String name, String location, GL3 gl) throws IOException {
        this.name = name;
        this.location = location;

        try {
            tx = TextureIO.newTexture(new File(location), true);
            tx.setTexParameteri(gl, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
            tx.setTexParameteri(gl, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
            tx.setTexParameteri(gl, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_EDGE);
            tx.setTexParameteri(gl, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_EDGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the file location
     *
     * @return the file location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the texture object
     *
     * @return the texture object
     */
    public Texture getTx() {
        return tx;
    }

    /**
     * Set the texture object
     *
     * @param tx the texture to change to
     */
    public void setTx(Texture tx) {
        this.tx = tx;
    }

    /**
     * Get the name of the texture
     *
     * @return the name of the texture
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the texture
     *
     * @param name the name to change to
     */
    public void setName(String name) {
        this.name = name;
    }
}
