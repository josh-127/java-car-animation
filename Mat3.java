
/**
 * Provides methods for 3x3 row-major matrix operations and transformations.
 * The matrix is represented as:
 * [ m11, m12, m13 ]
 * [ m21, m22, m23 ]
 * [ m31, m32, m33 ]
 */
public final class Mat3 {
    private Mat3() {}

    /**
     * Returns the determinant of a matrix.
     * @param mat the matrix to operate on
     * @return the determinant of the matrix
     */
    public static double determinant(double[] mat) {
        return mat[0] * (mat[4] * mat[8] - mat[5] * mat[7])
            -  mat[1] * (mat[3] * mat[8] - mat[5] * mat[6])
            +  mat[2] * (mat[3] * mat[7] - mat[4] * mat[6]);
    }

    /**
     * Returns a transposed version of a matrix
     * @param mat the matrix to operand on
     * @return the transposed version of the matrix
     */
    public static double[] transpose(double[] mat) {
        return new double[] {
            mat[0], mat[3], mat[6],
            mat[1], mat[4], mat[7],
            mat[2], mat[5], mat[8]
        };
    }

    /**
     * Returns the inverse of a matrix.
     * @param mat the matrix to operate on
     * @return the inverse of the matrix
     */
    public static double[] inverse(double[] mat) {
        double[] ret = new double[] {
            mat[4] * mat[8] - mat[5] * mat[7],
            mat[2] * mat[7] - mat[1] * mat[8],
            mat[1] * mat[5] - mat[2] * mat[4],

            mat[5] * mat[6] - mat[3] * mat[8],
            mat[0] * mat[8] - mat[2] * mat[6],
            mat[2] * mat[3] - mat[0] * mat[5],

            mat[3] * mat[7] - mat[4] * mat[6],
            mat[1] * mat[6] - mat[0] * mat[7],
            mat[0] * mat[4] - mat[1] * mat[3]
        };

        double determinant = determinant(mat);

        for (int i = 0; i < ret.length; i++)
            ret[i] /= determinant;

        return ret;
    }

    /**
     * Multiplies two matrices together.
     * @param lhs the left operand
     * @param rhs the right operand
     * @return the product of the two matrices
     */
    public static double[] mul(double[] lhs, double[] rhs) {
        return new double[] {
            lhs[0] * rhs[0] + lhs[1] * rhs[3] + lhs[2] * rhs[6],
            lhs[0] * rhs[1] + lhs[1] * rhs[4] + lhs[2] * rhs[7],
            lhs[0] * rhs[2] + lhs[1] * rhs[5] + lhs[2] * rhs[8],

            lhs[3] * rhs[0] + lhs[4] * rhs[3] + lhs[5] * rhs[6],
            lhs[3] * rhs[1] + lhs[4] * rhs[4] + lhs[5] * rhs[7],
            lhs[3] * rhs[2] + lhs[4] * rhs[5] + lhs[5] * rhs[8],

            lhs[6] * rhs[0] + lhs[7] * rhs[3] + lhs[8] * rhs[6],
            lhs[6] * rhs[1] + lhs[7] * rhs[4] + lhs[8] * rhs[7],
            lhs[6] * rhs[2] + lhs[7] * rhs[5] + lhs[8] * rhs[8],
        };
    }

    /**
     * Gets the affine transformation matrix used to transform triangle { a1, b1, c1 } to triangle { a2, b2, c2 }.
     * @param a1 the first vertex of the original triangle
     * @param b1 the second vertex of the original triangle
     * @param c1 the third vertex of the original triangle
     * @param a2 the first vertex of the transformed triangle
     * @param b2 the second vertex of the transformed triangle
     * @param c2 the third vertex of the transformed triangle
     * @return the affine transformation matrix used to transform the original triangle to the transformed triangle
     */
    public static double[] affineMatFromTriangle(Vec2 a1, Vec2 b1, Vec2 c1, Vec2 a2, Vec2 b2, Vec2 c2) {
        double[] originalTri = new double[] {
            a1.getX(), b1.getX(), c1.getX(),
            a1.getY(), b1.getY(), c1.getY(),
            1.0, 1.0, 1.0
        };

        double[] transformedTri = new double[] {
            a2.getX(), b2.getX(), c2.getX(),
            a2.getY(), b2.getY(), c2.getY(),
            1.0, 1.0, 1.0
        };

        return mul(transformedTri, inverse(originalTri));
    }

    /**
     * Converts a 3x3 row-major matrix to a 2x3 column-major matrix.
     * @return a 2x3 column-major matrix
     */
    public static double[] toColMajor2x3(double[] mat) {
        return new double[] {
            mat[0], mat[3],
            mat[1], mat[4],
            mat[2], mat[5]
        };
    }
}