import static java.lang.Math.sqrt;

/**
 * A Vec3 is a three-dimensional mathematical vector.
 */
public final class Vec3 {
    public static final Vec3 ZERO = new Vec3(0.0, 0.0, 0.0);

    private final double x;
    private final double y;
    private final double z;

    /**
     * Constructs a three-dimensional vector.
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     */
    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
     * Gets the x and y component of the vector.
     * @return the x and y component of the vector
     */
    public Vec2 xy() {
        return new Vec2(x, y);
    }

    /**
     * Gets the magnitude of the vector.
     * @return the magnitude of the vector
     */
    public double magnitude() {
        return sqrt(x * x + y * y + z * z);
    }

    /**
     * Gets the square magnitude of the vector.
     * @return the square magnitude of the vector
     */
    public double sqrMagnitude() {
        return x * x + y * y + z * z;
    }

    /**
     * Returns a vector with the same direction as this vector, except with a magnitude of one.
     * @return a vector with the same direction as this vector, except with a magnitude of one
     */
    public Vec3 normalized() {
        double invMagnitude = 1.0 / magnitude();
        return new Vec3(x * invMagnitude, y * invMagnitude, z * invMagnitude);
    }

    /**
     * Returns a vector with a different x-component.
     * @param x the new x-component
     * @return a vector with a different x-component
     */
    public Vec3 withX(double x) {
        return new Vec3(x, y, z);
    }

    /**
     * Returns a vector with a different y-component.
     * @param y the new y-component
     * @return a vector with a different y-component
     */
    public Vec3 withY(double y) {
        return new Vec3(x, y, z);
    }

    /**
     * Returns a vector with a different z-component.
     * @param z the new z-component
     * @return a vector with a different z-component
     */
    public Vec3 withZ(double z) {
        return new Vec3(x, y, z);
    }

    /**
     * Returns the dot product of two vectors.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the dot product of lhs and rhs
     */
    public static double dot(Vec3 lhs, Vec3 rhs) {
        return lhs.x * rhs.x + lhs.y * rhs.y + lhs.z * rhs.z;
    }

    /**
     * Returns the cross product of two vectors.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the cross product of lhs and rhs
     */
    public static Vec3 cross(Vec3 lhs, Vec3 rhs) {
        return new Vec3(
            lhs.y * rhs.z - lhs.z * rhs.y,
            lhs.z * rhs.x - lhs.x * rhs.z,
            lhs.x * rhs.y - lhs.y * rhs.x);
    }

    /**
     * Adds two vectors together.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the sum of the two vectors
     */
    public static Vec3 add(Vec3 lhs, Vec3 rhs) {
        return new Vec3(lhs.x + rhs.x, lhs.y + rhs.y, lhs.z + rhs.z);
    }

    /**
     * Subtracts two vectors together.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the difference between the two vectors
     */
    public static Vec3 sub(Vec3 lhs, Vec3 rhs) {
        return new Vec3(lhs.x - rhs.x, lhs.y - rhs.y, lhs.z - rhs.z);
    }

    /**
     * Multiplies two vectors together.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the product of the two vectors
     */
    public static Vec3 mul(Vec3 lhs, Vec3 rhs) {
        return new Vec3(lhs.x * rhs.x, lhs.y * rhs.y, lhs.z * rhs.z);
    }

    /**
     * Divides two vectors together.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the quotient of the two vectors
     */
    public static Vec3 div(Vec3 lhs, Vec3 rhs) {
        return new Vec3(lhs.x / rhs.x, lhs.y / rhs.y, lhs.z / rhs.z);
    }

    /**
     * Multiplies a vector and a scalar together.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the product of the vector and scalar
     */
    public static Vec3 mul(Vec3 lhs, double rhs) {
        return new Vec3(lhs.x * rhs, lhs.y * rhs, lhs.z * rhs);
    }

    /**
     * Divides a vector by a scalar.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the quotient of the vector and scalar
     */
    public static Vec3 div(Vec3 lhs, double rhs) {
        return new Vec3(lhs.x / rhs, lhs.y / rhs, lhs.z / rhs);
    }

    /**
     * Multiplies a scalar by a vector.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the product of the scalar and vector
     */
    public static Vec3 mul(double lhs, Vec3 rhs) {
        return new Vec3(lhs * rhs.x, lhs * rhs.y, lhs * rhs.z);
    }

    /**
     * Divides a scalar by a vector.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the quotient of the scalar and vector
     */
    public static Vec3 div(double lhs, Vec3 rhs) {
        return new Vec3(lhs / rhs.x, lhs / rhs.y, lhs / rhs.z);
    }

    /**
     * Checks if this vector is equal to another vector.
     * @param o the other vector
     * @return true if o is a Vec3 and is equal to this vector; otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vec3)) {
            return false;
        }

        Vec3 v = (Vec3) o;
        return x == v.x && y == v.y && z == v.z;
    }

    /**
     * Returns a readable representation of the vector.
     * @return a readable representation of the vector
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}