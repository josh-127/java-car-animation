
/**
 * Represents a generic police car.
 */
public class GenericPoliceCar extends Vehicle {
    /**
     * Constructs a generic police car.
     * @param scene the scene the police car is in
     */
    public GenericPoliceCar(Scene scene) {
        super(scene, Model.load("asset/police2.obj", "asset/police_map.png"), 0.29);
    }

    @Override
    protected void tick() {
        acceleration = Vec3.add(acceleration, new Vec3(0.0, 0.0, 0.0004));
    }
}