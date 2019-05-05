
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Represents a three-dimensional model made up of triangles and a texture.
 */
public class Model {
    private final Triangle[] triangles;
    private final BufferedImage texture;
    private final TexturePaint paint;

    private Model(Triangle[] triangles, BufferedImage texture) {
        this.triangles = triangles;
        this.texture = texture;
        paint = texture != null
            ? new TexturePaint(texture, new Rectangle2D.Double(0.0, 0.0, texture.getWidth(), texture.getHeight()))
            : null;
    }

    /**
     * Gets the triangles of the model.
     * @return the triangles of the model
     */
    public Triangle[] getTriangles() {
        return triangles;
    }

    /**
     * Gets the model texture.
     * @return the model texture
     */
    public BufferedImage getTexture() {
        return texture;
    }

    /**
     * Gets the model texture paint.
     * @return the model texture paint
     */
    public TexturePaint getTexturePaint() {
        return paint;
    }

    /**
     * Loads a model from an obj file and a texture.
     * @param fileName the file of the model
     * @param textureFileName the texture file of the model
     * @return the loaded model
     * @throws FileNotFoundException when fileName or textureFileName does not exist
     */
    public static Model load(String fileName, String textureFileName) {
        Scanner scanner = null;
        try {
            BufferedImage texture = textureFileName != null ? ImageIO.read(new File(textureFileName)) : null;
            Vec2 textureSize = texture != null ? new Vec2(texture.getWidth(), texture.getHeight()) : Vec2.ONE;
            ArrayList<Vec3> positions = new ArrayList<>();
            ArrayList<Vec2> texCoords = new ArrayList<>();
            ArrayList<Vec3> normals = new ArrayList<>();
            ArrayList<Triangle> triangles = new ArrayList<>();
            scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                switch (scanner.next()) {
                case "v": {
                    positions.add(new Vec3(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble()));
                } break;

                case "vt": {
                    texCoords.add(new Vec2(scanner.nextDouble(), scanner.nextDouble()));
                } break;

                case "vn": {
                    normals.add(new Vec3(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble()));
                } break;

                case "f": {
                    String[] a = scanner.next().split("/");
                    String[] b = scanner.next().split("/");
                    String[] c = scanner.next().split("/");
                    triangles.add(new Triangle(
                        new Vertex(
                            positions.get(Integer.parseInt(a[0]) - 1),
                            Vec2.mul(textureSize, texCoords.get(Integer.parseInt(a[1]) - 1))),
                        new Vertex(
                            positions.get(Integer.parseInt(b[0]) - 1),
                            Vec2.mul(textureSize, texCoords.get(Integer.parseInt(b[1]) - 1))),
                        new Vertex(
                            positions.get(Integer.parseInt(c[0]) - 1),
                            Vec2.mul(textureSize, texCoords.get(Integer.parseInt(c[1]) - 1))),
                        normals.get(Integer.parseInt(a[2]) - 1)));
                } break;

                case "#":
                case "s": break;
                }

                scanner.nextLine();
            }

            scanner.close();
            return new Model(triangles.toArray(new Triangle[triangles.size()]), texture);
        }
        catch (IOException e) {
            System.err.println("File not found: " + fileName + ", " + textureFileName);
            System.exit(-1);
        }
        finally {
            scanner.close();
        }

        return null;
    }

    /**
     * Represents a three-dimensional vertex that has a position.
     */
    public static class Vertex {
        private final Vec3 position;
        private final Vec2 texCoord;

        /**
         * Constructs a vertex.
         * @param position the position of the vertex
         */
        public Vertex(Vec3 position, Vec2 texCoord) {
            this.position = position;
            this.texCoord = texCoord;
        }

        /**
         * Gets the position of the vertex.
         * @return the position of the vertex
         */
        public Vec3 getPosition() {
            return position;
        }

        /**
         * Gets the texture coordinate of the vertex.
         * @return the texture coordinate of the vertex
         */
        public Vec2 getTexCoord() {
            return texCoord;
        }
    }

    /**
     * Represents a three-dimensional triangle that has three vertices and a normal.
     */
    public static class Triangle {
        private Vertex a;
        private Vertex b;
        private Vertex c;
        private Vec3 normal;

        /**
         * Constructs a triangle.
         * @param a the first vertex of the triangle
         * @param b the second vertex of the triangle
         * @param c the third vertex of the triangle
         * @param normal the normal of the triangle
         */
        public Triangle(Vertex a, Vertex b, Vertex c, Vec3 normal) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.normal = normal;
        }

        /**
         * Gets the first vertex of the triangle.
         * @return the first vertex of the triangle
         */
        public Vertex getA() {
            return a;
        }

        /**
         * Gets the second vertex of the triangle.
         * @return the second vertex of the triangle
         */
        public Vertex getB() {
            return b;
        }

        /**
         * Gets the third vertex of the triangle.
         * @return the third vertex of the triangle
         */
        public Vertex getC() {
            return c;
        }

        /**
         * Gets the normal of the triangle.
         * @return the normal of the triangle
         */
        public Vec3 getNormal() {
            return normal;
        }
    }
}