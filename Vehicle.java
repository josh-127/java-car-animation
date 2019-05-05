
/**
 * A vehicle is a transportation machine.
 */
public abstract class Vehicle implements Runnable {
    protected final Scene scene;
    private final Model model;
    private final double maxSpeed;

    protected Vec3 position;
    protected Vec3 rotation;
    protected Vec3 velocity;
    protected Vec3 acceleration;
    private volatile boolean ticked;

    /**
     * Constructs a vehicle.
     * @param scene the scene the vehicle is in
     * @param model the model used for the vehicle
     * @param maxSpeed the maximum speed of the vehicle
     */
    public Vehicle(Scene scene, Model model, double maxSpeed) {
        this.scene = scene;
        this.model = model;
        this.maxSpeed = maxSpeed;
        position = Vec3.ZERO;
        rotation = Vec3.ZERO;
        velocity = Vec3.ZERO;
        acceleration = Vec3.ZERO;
        ticked = false;
    }

    /**
     * Gets the position of the vehicle.
     * @return the position of the vehicle
     */
    public Vec3 getPosition() {
        return position;
    }

    /**
     * Sets the position of the vehicle.
     * @param position the new position of the vehicle
     */
    public void setPosition(Vec3 position) {
        this.position = position;
    }

    /**
     * Returns true if the vehicle ticked.
     * @return true if the vehicle ticked; otherwise false
     */
    public boolean ticked() {
        return ticked;
    }

    /**
     * Allows the vehicle to execute its next tick.
     */
    public void unsetTick() {
        ticked = false;
    }

    protected abstract void tick();

    protected void draw() {
        scene.getRenderContext().drawModel(model,
            Mat4.mul(Mat4.mul(Mat4.mul(
                Mat4.rotateY(rotation.getY()),
                Mat4.rotateZ(rotation.getZ())),
                Mat4.rotateX(rotation.getX())),
                Mat4.translate(position)));
    }

    /**
     * The implementation of the Runnable.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            tick();
            velocity = Vec3.add(velocity, acceleration);

            if (velocity.sqrMagnitude() >= maxSpeed * maxSpeed) {
                velocity = Vec3.mul(velocity.normalized(), maxSpeed);
            }

            position = Vec3.add(position, velocity);

            while (ticked) {}

            draw();
            ticked = true;
        }
    }
}