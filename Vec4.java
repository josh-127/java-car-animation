
/**
 * A Vec3 is a four-dimensional mathematical vector.
 */
public final class Vec4 {
    private final double x;
    private final double y;
    private final double z;
    private final double w;

    /**
     * Constructs a four-dimensional vector.
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @param w the w-component of the vector
     */
    public Vec4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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
     * Gets the z-component of the vector.
     * @return the z-component of the vector
     */
    public double getZ() {
        return z;
    }

    /**
     * Gets the w-component of the vector.
     * @return the w-component of the vector
     */
    public double getW() {
        return w;
    }

    /**
     * Gets the x, y, and z component of the vector.
     * @return the x, y, and z component of the vector
     */
    public Vec3 xyz() {
        return new Vec3(x, y, z);
    }
}