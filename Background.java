
/**
 * A background is static three-dimensional scenery model.
 */
public class Background {
    private final Model model;
    protected Vec3 position;

    /**
     * Constructs a background.
     * @param model the model of the background
     */
    public Background(Model model) {
        this.model = model;
        position = Vec3.ZERO;
    }

    /**
     * Gets the position of the background.
     * @return the position of the background
     */
    public Vec3 getPosition() {
        return position;
    }

    /**
     * Sets the position of the background.
     * @param position the new position of the background
     */
    public void setPosition(Vec3 position) {
        this.position = position;
    }

    /**
     * Gets the model of the background.
     * @return the model of the background
     */
    public Model getModel() {
        return model;
    }

    /**
     * Draws the background.
     * @param context the renderer used to draw the background
     */
    public void draw(RenderContext context) {
        context.drawModel(model, Mat4.translate(position));
    }
}