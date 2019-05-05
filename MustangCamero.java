
import java.awt.Color;
import java.util.Random;

/**
 * Represents a yellow Mustang Camero.
 */
public class MustangCamero extends Vehicle {
    private static final int STRAIGHT = 0;
    private static final int SPIN = 1;
    private static final int JUMP = 2;
    private static final int FALL = 3;
    private static final int WHEELIE_UP = 4;
    private static final int WHEELIE_HOLD = 5;
    private static final int WHEELIE_DOWN = 6;
    private static final int STATE_COUNT = 7;
    private static final Random random = new Random();

    private int state;
    private int time;

    /**
     * Constructs a Mustang Camero.
     * @param scene the scene the car is in
     */
    public MustangCamero(Scene scene) {
        super(scene, Model.load("asset/camero2.obj", "asset/camero_map.png"), 0.3);
        state = JUMP;
        time = 0;
    }

    @Override
    protected void tick() {
        acceleration = Vec3.add(acceleration, new Vec3(0.0, 0.0, 0.0012));

        switch (state) {
        case STRAIGHT:
            if (++time % 30 == 0) {
                state = random.nextInt(STATE_COUNT);
            }
            break;

        case SPIN:
            rotation = Vec3.add(rotation, new Vec3(0.0, 0.1, 0.0));
            if (rotation.getY() >= 2.0 * Math.PI) {
                rotation = rotation.withY(0.0);
                state = STRAIGHT;
            }
            break;

        case JUMP:
            velocity = velocity.withY(0.4);
            state = FALL;
            break;

        case FALL:
            acceleration = new Vec3(0.0, -0.03, -0.0015);
            if (position.getY() <= 0.0) {
                position = position.withY(0.0);
                velocity = velocity.withY(0.0);
                acceleration = acceleration.withY(0.0);
                state = STRAIGHT;
            }
            break;

        case WHEELIE_UP:
            rotation = rotation.withX(rotation.getX() + 0.04);
            if (rotation.getX() >= Math.PI / 4.0) {
                state = WHEELIE_HOLD;
            }
            break;

        case WHEELIE_HOLD:
            if (++time % 80 == 0) {
                state = WHEELIE_DOWN;
            }
            break;

        case WHEELIE_DOWN:
            rotation = rotation.withX(rotation.getX() - 0.08);
            if (rotation.getX() <= 0.0) {
                rotation = rotation.withX(0.0);
                state = STRAIGHT;
            }
            break;
        }
    }
}