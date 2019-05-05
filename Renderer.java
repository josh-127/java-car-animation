import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A renderer implements RenderContext using Java's drawing methods.
 */
public class Renderer implements RenderContext {
    private final int width;
    private final int height;
    private final Camera camera;
    private final Queue<Model> modelQueue;
    private final Queue<double[]> transformQueue;
    private final Lock queueLock;
    private final Condition queueCondition;
    private boolean isRendering;

    /**
     * Constructs a renderer.
     * @param width the screen width
     * @param height the screen height
     */
    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;
        camera = new Camera();
        modelQueue = new LinkedList<>();
        transformQueue = new LinkedList<>();
        queueLock = new ReentrantLock();
        queueCondition = queueLock.newCondition();
        isRendering = false;
    }

    /**
     * Gets the camera of the renderer.
     * @return the camera of the renderer
     */
    @Override
    public Camera getCamera() {
        return camera;
    }

    /**
     * Draws a model.
     * @param model the model to draw
     * @param transform the model transformation of the model
     */
    @Override
    public synchronized void drawModel(Model model, double[] transform) {
        try {
            queueLock.lock();
            while (isRendering) {
                queueCondition.await();
            }

            modelQueue.add(model);
            transformQueue.add(transform);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            queueLock.unlock();
        }
    }

    /**
     * Renders all the models.
     * @param g the device context used to draw the models
     */
    public void render(Graphics2D g) {
        try {
            queueLock.lock();
            isRendering = true;

            double[] viewProj = Mat4.mul(Mat4.mul(
                Mat4.lookAt(camera.getPosition(), camera.getTarget(), camera.getUp()),
                Mat4.perspective(70.0 * Math.PI / 180.0, (double) width / height, 0.0, 1.0)),
                Mat4.toPixelSpace(width, height));

            Vec3 cameraOrientation = Vec3.sub(camera.getTarget(), camera.getPosition());

            while (!modelQueue.isEmpty()) {
                Model model = modelQueue.remove();
                double[] mvp = Mat4.mul(transformQueue.remove(), viewProj);
                Model.Triangle[] modelTriangles = model.getTriangles();
                RenderTriangle[] renderTriangles = new RenderTriangle[modelTriangles.length];
                Polygon polygon = new Polygon(new int[3], new int[3], 3);

                if (model.getTexturePaint() != null) {
                    g.setPaint(model.getTexturePaint());
                }
                else {
                    polygon.xpoints[0] = 0;
                    polygon.ypoints[0] = 0;
                    polygon.xpoints[1] = 1;
                    polygon.ypoints[1] = 0;
                    polygon.xpoints[2] = 0;
                    polygon.ypoints[2] = 1;
                }

                for (int i = 0; i < renderTriangles.length; i++) {
                    Vec4 a = Mat4.mul(modelTriangles[i].getA().getPosition(), mvp);
                    Vec4 b = Mat4.mul(modelTriangles[i].getB().getPosition(), mvp);
                    Vec4 c = Mat4.mul(modelTriangles[i].getC().getPosition(), mvp);

                    renderTriangles[i] = new RenderTriangle(
                        new Vec3(a.getX() / a.getW(), a.getY() / a.getW(), a.getW()),
                        new Vec3(b.getX() / b.getW(), b.getY() / b.getW(), b.getW()),
                        new Vec3(c.getX() / c.getW(), c.getY() / c.getW(), c.getW()),
                        modelTriangles[i].getA().getTexCoord(),
                        modelTriangles[i].getB().getTexCoord(),
                        modelTriangles[i].getC().getTexCoord(),
                        modelTriangles[i].getNormal());
                }

                sortTriangles(renderTriangles, 0, renderTriangles.length - 1);

                for (int i = 0; i < renderTriangles.length; i++) {
                    if (Vec3.dot(cameraOrientation, renderTriangles[i].normal) < 0.0
                        && getDepth(renderTriangles[i]) > 5.5)
                    {
                        RenderTriangle renderTriangle = renderTriangles[i];
                        polygon.xpoints[0] = (int) renderTriangles[i].uvA.getX();
                        polygon.ypoints[0] = (int) renderTriangles[i].uvA.getY();
                        polygon.xpoints[1] = (int) renderTriangles[i].uvB.getX();
                        polygon.ypoints[1] = (int) renderTriangles[i].uvB.getY();
                        polygon.xpoints[2] = (int) renderTriangles[i].uvC.getX();
                        polygon.ypoints[2] = (int) renderTriangles[i].uvC.getY();
                        double[] affine = Mat3.affineMatFromTriangle(
                                new Vec2(polygon.xpoints[0], polygon.ypoints[0]),
                                new Vec2(polygon.xpoints[1], polygon.ypoints[1]),
                                new Vec2(polygon.xpoints[2], polygon.ypoints[2]),
                                renderTriangle.posA.xy(),
                                renderTriangle.posB.xy(),
                                renderTriangle.posC.xy());

                        g.setTransform(new AffineTransform(Mat3.toColMajor2x3(affine)));
                        g.fillPolygon(polygon);
                    }
                }
            }
        }
        finally {
            isRendering = false;
            queueCondition.signalAll();
            queueLock.unlock();
        }
    }

    private static void sortTriangles(RenderTriangle[] triangles, int start, int end) {
        if (start < end) {
            int pivotIndex = start;
            RenderTriangle pivot = triangles[start];
            int index = start + 1;

            while (index <= end) {
                if (getDepth(triangles[index]) > getDepth(pivot)) {
                    pivotIndex++;
                    RenderTriangle temp = triangles[index];
                    triangles[index] = triangles[pivotIndex];
                    triangles[pivotIndex] = temp;
                }

                index++;
            }

            RenderTriangle temp = triangles[start];
            triangles[start] = triangles[pivotIndex];
            triangles[pivotIndex] = temp;
            sortTriangles(triangles, start, pivotIndex - 1);
            sortTriangles(triangles, pivotIndex + 1, end);
        }
    }

    private static double getDepth(RenderTriangle triangle) {
        return Vec3.add(Vec3.add(triangle.posA, triangle.posB), triangle.posC).getZ();
    }



    private static class RenderTriangle {
        private final Vec3 posA;
        private final Vec3 posB;
        private final Vec3 posC;
        private final Vec2 uvA;
        private final Vec2 uvB;
        private final Vec2 uvC;
        private final Vec3 normal;

        /**
         * Constructs a render triangle.
         * @param posA the first vertex position
         * @param posB the second vertex position
         * @param posC the third vertex position
         * @param uvA the first vertex texture coordinate
         * @param uvB the second vertex texture coordinate
         * @param uvC the third vertex texture coordinate
         * @param normal the normal of the triangle
         */
        public RenderTriangle(Vec3 posA, Vec3 posB, Vec3 posC, Vec2 uvA, Vec2 uvB, Vec2 uvC, Vec3 normal) {
            this.posA = posA;
            this.posB = posB;
            this.posC = posC;
            this.uvA = uvA;
            this.uvB = uvB;
            this.uvC = uvC;
            this.normal = normal;
        }
    }
}