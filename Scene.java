import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Represents a police chase car animation in a highway between Dallas and Houston.
 */
public class Scene implements Runnable {
    private static final int POLICE_CAR_COUNT = 2;

    private final OutputGraphicsProvider output;
    private final Renderer renderContext;
    private final Background sky;
    private final Background hills;
    private final Background road;
    private final Vehicle camero;
    private final Vehicle[] policeCars;

    /**
     * Constructs a scene.
     * @param output the output graphics provider used to display the scene
     */
    public Scene(OutputGraphicsProvider output) {
        this.output = output;
        renderContext = new Renderer(output.getBufferWidth(), output.getBufferHeight());
        sky = new Background(Model.load("asset/sky.obj", "asset/sky.png"));
        hills = new Background(Model.load("asset/grass.obj", "asset/grass.png"));
        road = new Background(Model.load("asset/road.obj", "asset/road0.png"));
        camero = new MustangCamero(this);
        policeCars = new Vehicle[POLICE_CAR_COUNT];

        for (int i = 0; i < policeCars.length; i++) {
            policeCars[i] = new GenericPoliceCar(this);
            policeCars[i].setPosition(new Vec3((i - policeCars.length / 2) * 3.0, 0.0, -10.0));
        }

        new Thread(camero).start();
        for (int i = 0; i < policeCars.length; i++) {
            new Thread(policeCars[i]).start();
        }
    }

    /**
     * Gets the renderer of the scene.
     * @return the renderer of the scene
     */
    public RenderContext getRenderContext() {
        return renderContext;
    }

    /**
     * The implementation of Runnable.
     */
    @Override
    public void run() {
        double time = 0.0;

        for (;;) {
            time += 0.005;
            sky.setPosition(camero.getPosition());
            road.setPosition(new Vec3(0.0, 0.0, (int) camero.getPosition().getZ() / 27 * 27 - 32));
            hills.setPosition(new Vec3(0.0, 0.0, (int) camero.getPosition().getZ() / 27 * 27 - 32));
            sky.draw(renderContext);
            hills.draw(renderContext);
            road.draw(renderContext);

            camero.unsetTick();
            for (int i = 0; i < policeCars.length; i++) {
                policeCars[i].unsetTick();
            }

            Graphics2D g = output.getGraphics2D();
            g.setColor(new Color(0, 127, 255));
            g.setTransform(new AffineTransform());
            g.fillRect(0, 0, output.getBufferWidth(), output.getBufferHeight());
            renderContext.getCamera().setPosition(Vec3.add(camero.getPosition(), new Vec3(8.0 * cos(time), 1.9, 10.0 * sin(time))));
            renderContext.getCamera().setTarget(camero.getPosition());
            renderContext.render(g);
            output.swapBuffers();
        }
    }
}