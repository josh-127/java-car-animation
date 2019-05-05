
/**
 * A Vec3 is a two-dimensional mathematical vector.
 */
public final class Vec2 {
    public static final Vec2 ZERO = new Vec2(0.0, 0.0);
    public static final Vec2 ONE = new Vec2(1.0, 1.0);
    public static final Vec2 UP = new Vec2(0.0, 1.0);
    public static final Vec2 RIGHT = new Vec2(1.0, 0.0);

    private final double x;
    private final double y;

    /**
     * Constructs a three-dimensional vector.
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     */
    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-component of the vector.
     * @return the x-component of the vector
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-component of the vector.
     * @return the y-component of the vector
     */
    public double getY() {
        return y;
    }

    /**
     * Multiplies two vectors together.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the product of the two vectors
     */
    public static Vec2 mul(Vec2 lhs, Vec2 rhs) {
        return new Vec2(lhs.x * rhs.x, lhs.y * rhs.y);
    }
}