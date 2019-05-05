
import java.awt.Graphics2D;

/**
 * Provides basic information about the screen and a device context.
 */
public interface OutputGraphicsProvider {
    /**
     * Gets the device context.
     * @return the device context
     */
    Graphics2D getGraphics2D();

    /**
     * Gets the back buffer width.
     * @return the back buffer width
     */
    int getBufferWidth();

    /**
     * Gets the back buffer height.
     * @return the back buffer height
     */
    int getBufferHeight();

    /**
     * Swaps the front and back buffers, and then displays them.
     */
    void swapBuffers();
}