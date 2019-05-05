
/**
 * A RenderContext is a 3D rendering interface.
 */
public interface RenderContext {
    /**
     * Gets the camera.
     * @return the camera
     */
    Camera getCamera();

    /**
     * Draws a 3D model that has a model transformation.
     * @param model the model to draw
     * @param transform the model transformation
     */
    void drawModel(Model model, double[] transform);
}