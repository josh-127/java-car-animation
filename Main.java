import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main class for initializing the animation.
 */
public class Main extends JFrame implements OutputGraphicsProvider {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 360;
    private static final int SCALE = 2;

    private final BufferedImage backBuffer;
    private final Graphics2D graphics2D;

    /**
     * Entry point.
     * @param args the command line arguments which are not used
     */
    public static void main(String[] args) {
        new Main();
    }

	private Main() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(SCALE * WIDTH, SCALE * HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setIgnoreRepaint(true);
        setResizable(false);
        setTitle("Car Chase Animation");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        backBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics2D = backBuffer.createGraphics();
        new Thread(new Scene(this)).start();
    }

    /**
     * Gets the device context.
     * @return the device context
     */
    @Override
    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    /**
     * Gets the back buffer width.
     * @return the back buffer width
     */
    @Override
    public int getBufferWidth() {
        return WIDTH;
    }

    /**
     * Gets the back buffer height.
     * @return the back buffer height
     */
    @Override
    public int getBufferHeight() {
        return HEIGHT;
    }

    /**
     * Swaps the front and back buffers, and then displays them.
     */
    @Override
    public void swapBuffers() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(2);
        }
        else {
            Graphics g = bs.getDrawGraphics();
            g.drawImage(backBuffer, 3, 25, SCALE * WIDTH, SCALE * HEIGHT, null);
            g.dispose();
            bs.show();
        }
    }
}