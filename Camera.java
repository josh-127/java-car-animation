
/**
 * Represents a camera that contains a position, target, and an up-axis.
 */
public class Camera {
    private Vec3 position;
    private Vec3 target;
    private Vec3 up;

    /**
     * Constructs a camera.
     */
    public Camera() {
        position = new Vec3(1.0, 1.0, 1.0);
        target = new Vec3(0.0, 0.0, 0.0);
        up = new Vec3(0.0, 1.0, 0.0);
    }

    /**
     * Gets the position of the camera
     * @return the position of the camera
     */
    public Vec3 getPosition() {
        return position;
    }

    /**
     * Gets the location the camera is pointing at.
     * @return the location the camera is pointing at
     */
    public Vec3 getTarget() {
        return target;
    }

    /**
     * Gets the up-right axis of the camera
     * @return the up-right axis of the camera
     */
    public Vec3 getUp() {
        return up;
    }

    /**
     * Sets the position of the camera.
     * @param position the new position of the camera
     */
    public void setPosition(Vec3 position) {
        this.position = position;
    }

    /**
     * Sets the target of the camera.
     * @param target the new target of the camera
     */
    public void setTarget(Vec3 target) {
        this.target = target;
    }

    /**
     * Sets the up-right axis of the camera.
     * @param up the new up-right axis of the camera
     */
    public void setUp(Vec3 up) {
        this.up = up;
    }
}