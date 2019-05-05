
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

/**
 * Provides methods for 4x4 row-major matrix operations and transformations.
 * The matrix is represented as:
 * [ m11, m12, m13, m14 ]
 * [ m21, m22, m23, m24 ]
 * [ m31, m32, m33, m34 ]
 * [ m41, m41, m43, m44 ]
 */
public final class Mat4 {
    private Mat4() {}

    public static final double[] identity = {
        1.0, 0.0, 0.0, 0.0,
        0.0, 1.0, 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    };

    /**
     * Multiplies two matrices together.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the product of the two matrices
     */
    public static double[] mul(double[] lhs, double[] rhs) {
        // _DEBUG
        /*//
        if (lhs.length != 16) throw new IllegalArgumentException("lhs.length == 16");
        if (rhs.length != 16) throw new IllegalArgumentException("rhs.length == 16");
        //*/

        return new double[] {
            lhs[0]  * rhs[0] + lhs[1]  * rhs[4] + lhs[2]  * rhs[8]  + lhs[3]  * rhs[12],
            lhs[0]  * rhs[1] + lhs[1]  * rhs[5] + lhs[2]  * rhs[9]  + lhs[3]  * rhs[13],
            lhs[0]  * rhs[2] + lhs[1]  * rhs[6] + lhs[2]  * rhs[10] + lhs[3]  * rhs[14],
            lhs[0]  * rhs[3] + lhs[1]  * rhs[7] + lhs[2]  * rhs[11] + lhs[3]  * rhs[15],
            
            lhs[4]  * rhs[0] + lhs[5]  * rhs[4] + lhs[6]  * rhs[8]  + lhs[7]  * rhs[12],
            lhs[4]  * rhs[1] + lhs[5]  * rhs[5] + lhs[6]  * rhs[9]  + lhs[7]  * rhs[13],
            lhs[4]  * rhs[2] + lhs[5]  * rhs[6] + lhs[6]  * rhs[10] + lhs[7]  * rhs[14],
            lhs[4]  * rhs[3] + lhs[5]  * rhs[7] + lhs[6]  * rhs[11] + lhs[7]  * rhs[15],
            
            lhs[8]  * rhs[0] + lhs[9]  * rhs[4] + lhs[10] * rhs[8]  + lhs[11] * rhs[12],
            lhs[8]  * rhs[1] + lhs[9]  * rhs[5] + lhs[10] * rhs[9]  + lhs[11] * rhs[13],
            lhs[8]  * rhs[2] + lhs[9]  * rhs[6] + lhs[10] * rhs[10] + lhs[11] * rhs[14],
            lhs[8]  * rhs[3] + lhs[9]  * rhs[7] + lhs[10] * rhs[11] + lhs[11] * rhs[15],
            
            lhs[12] * rhs[0] + lhs[13] * rhs[4] + lhs[14] * rhs[8]  + lhs[15] * rhs[12],
            lhs[12] * rhs[1] + lhs[13] * rhs[5] + lhs[14] * rhs[9]  + lhs[15] * rhs[13],
            lhs[12] * rhs[2] + lhs[13] * rhs[6] + lhs[14] * rhs[10] + lhs[15] * rhs[14],
            lhs[12] * rhs[3] + lhs[13] * rhs[7] + lhs[14] * rhs[11] + lhs[15] * rhs[15]
        };
    }

    /**
     * Multiplies a vector and a matrix together. The three-dimensional vector is converted
     * to a four-dimensional vector with a w-component of one.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the product of the vector and matrix
     */
    public static Vec4 mul(Vec3 lhs, double[] rhs) {
        // _DEBUG
        // if (rhs.length != 16) throw new IllegalArgumentException("rhs.length == 16");

        return new Vec4(
            lhs.getX() * rhs[0] + lhs.getY() * rhs[4] + lhs.getZ() * rhs[8]  + rhs[12],
            lhs.getX() * rhs[1] + lhs.getY() * rhs[5] + lhs.getZ() * rhs[9]  + rhs[13],
            lhs.getX() * rhs[2] + lhs.getY() * rhs[6] + lhs.getZ() * rhs[10] + rhs[14],
            lhs.getX() * rhs[3] + lhs.getY() * rhs[7] + lhs.getZ() * rhs[11] + rhs[15]);
    }

    /**
     * Converts screen-space coordinate space to a pixel coordinate space.
     * Screen-space coordinates have a viewing boundary of X: [-1, 1], Y: [-1, 1].
     * Pixel-space coordinates have a viewing boundary of X: [0, width], Y: [0, height].
     * @param width the width of the pixel coordinate space
     * @param height the height of the pixel coordinate space
     * @return a pixel coordinate space matrix
     */
    public static double[] toPixelSpace(int width, int height) {
        return mul(
            scale(new Vec3(0.5 * width, -0.5 * height, 1.0)),
            translate(new Vec3(0.5 * width, 0.5 * height, 0.0)));
    }

    /**
     * Returns a translation matrix from a translation vector.
     * @translation the vector used to make a translation matrix
     * @return a translation matrix
     */
    public static double[] translate(Vec3 translation) {
        return new double[] {
            1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            translation.getX(), translation.getY(), translation.getZ(), 1.0
        };
    }

    /**
     * Returns an x-rotation matrix from an angle.
     * @param angle the angle of rotation in radians
     * @return an x-rotation matrix
     */
    public static double[] rotateX(double angle) {
        return new double[] {
            1.0, 0.0, 0.0, 0.0,
            0.0, cos(angle), -sin(angle), 0.0,
            0.0, sin(angle), cos(angle), 0.0,
            0.0, 0.0, 0.0, 1.0
        };
    }

    /**
     * Returns a y-rotation matrix from an angle.
     * @param angle the angle of rotation in radians
     * @return a y-rotation matrix
     */
    public static double[] rotateY(double angle) {
        return new double[] {
            cos(angle), 0.0, sin(angle), 0.0,
            0.0, 1.0, 0.0, 0.0,
            -sin(angle), 0.0, cos(angle), 0.0,
            0.0, 0.0, 0.0, 1.0
        };
    }

    /**
     * Returns a z-rotation matrix from an angle.
     * @param angle the angle of rotation in radians
     * @return a z-rotation matrix
     */
    public static double[] rotateZ(double angle) {
        return new double[] {
            cos(angle), -sin(angle), 0.0, 0.0,
            sin(angle), cos(angle), 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            0.0, 0.0, 0.0, 1.0
        };
    }

    /**
     * Returns a scale matrix from a scale vector.
     * @param scale the scaling coefficient vector
     * @return a scale matrix
     */
    public static double[] scale(Vec3 scale) {
        return new double[] {
            scale.getX(), 0.0, 0.0, 0.0,
            0.0, scale.getY(), 0.0, 0.0,
            0.0, 0.0, scale.getZ(), 0.0,
            0.0, 0.0, 0.0, 1.0
        };
    }

    /**
     * Returns a look-at view matrix which has a position, target, and up.
     * @param position the position of the camera
     * @param target the orientation of the camera based off a target
     * @param up the up direction for the camera
     * @return a look-at view matrix
     */
    public static double[] lookAt(Vec3 position, Vec3 target, Vec3 up) {
        Vec3 axisZ = Vec3.sub(position, target).normalized();
        Vec3 axisX = Vec3.cross(up, axisZ).normalized();
        Vec3 axisY = Vec3.cross(axisZ, axisX);

        return new double[] {
            axisX.getX(), axisY.getX(), axisZ.getX(), 0.0,
            axisX.getY(), axisY.getY(), axisZ.getY(), 0.0,
            axisX.getZ(), axisY.getZ(), axisZ.getZ(), 0.0,
            -Vec3.dot(axisX, position), -Vec3.dot(axisY, position), -Vec3.dot(axisZ, position), 1.0
        };
    }

    /**
     * Returns a perspective projection matrix.
     * @param fov the field of view in radians
     * @param aspect the aspect ratio which is equal to camera width / camera height
     * @param near the minimum z-position in screen-space coordinates
     * @param far the maximum z-position in screen-space coordinates
     */
    public static double[] perspective(double fov, double aspect, double near, double far) {
        double m22 = 1.0 / tan(fov / 2.0);
        double m11 = m22 / aspect;
        double m33 = far / (near - far);
        double m43 = (near * far) / (near - far);

        return new double[] {
            m11, 0.0, 0.0, 0.0,
            0.0, m22, 0.0, 0.0,
            0.0, 0.0, m33, -1.0,
            0.0, 0.0, m43, 0.0
        };
    }
}