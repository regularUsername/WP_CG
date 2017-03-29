package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.rendering.RenderVertex;
import computergraphics.rendering.VertexBufferObject;

import java.util.ArrayList;
import java.util.List;

public class CylinderNode extends LeafNode {

    private double radius;
    private double length;
    private int resolution;

    private VertexBufferObject vbo;

    private Vector color = new Vector(0.69, 0.30, 0.18, 1);

    public CylinderNode(double radius, double length, int resolution) {
        this.radius = radius;
        this.resolution = resolution;
        this.length = length;
        vbo = new VertexBufferObject();
        createVboTriangles();
    }

    private void createVboTriangleStrips() {
        List<RenderVertex> renderVertices = new ArrayList<>();

        Vector normal = new Vector(0.0, -1.0, 0.0);
        for (int i = 0; i < resolution; i++) {
            double t0 = ((double) i) * 2.0 * Math.PI / resolution;
            double t1 = ((double) i + 1) * 2.0 * Math.PI / resolution;

            double x0 = radius * Math.cos(t0);
            double y0 = radius * Math.sin(t0);
            double x1 = radius * Math.cos(t1);
            double y1 = radius * Math.sin(t1);

            // TODO wie macht man hier die normal vektoren ?

            // TODO degenerate triangles ? um strips zu trennen

            // abwechselnd oben und unten anfangen um zylinder in einem zusammenhängendem triangle strip zu rendern
            double l = i % 2 == 0 ? length : -length;

            renderVertices.add(new RenderVertex(new Vector(0.0, 0.0, l), normal, color));
            renderVertices.add(new RenderVertex(new Vector(x0, y0, l), normal, color));
            renderVertices.add(new RenderVertex(new Vector(x1, y1, l), normal, color));

            renderVertices.add(new RenderVertex(new Vector(x0, y0, -l), normal, color));
            renderVertices.add(new RenderVertex(new Vector(x1, y1, -l), normal, color));
            if (i == resolution - 1) {
                renderVertices.add(new RenderVertex(new Vector(0.0, 0.0, -l), normal, color));
            }

        }

        vbo.Setup(renderVertices, GL2.GL_TRIANGLE_STRIP);
    }

    private void createVboTriangles() {
        List<RenderVertex> renderVertices = new ArrayList<>();

        Vector nTop = new Vector(0.0, -1.0, 0.0);
        Vector nBot = new Vector(0.0, 1.0,0.0);
        for (int i = 0; i < resolution; i++) {
            double t0 = ((double) i) * 2.0 * Math.PI / resolution;
            double t1 = ((double) i + 1) * 2.0 * Math.PI / resolution;

            double x0 = radius * Math.cos(t0);
            double y0 = radius * Math.sin(t0);
            double x1 = radius * Math.cos(t1);
            double y1 = radius * Math.sin(t1);

            double tn = ((double) i + 0.5) * 2.0 * Math.PI / resolution;
            Vector normal = new Vector(Math.cos(tn),Math.sin(tn), 0.0);
            
            // TODO wie macht man hier die normal vektoren ?
            renderVertices.add(new RenderVertex(new Vector(0.0, 0.0, length), nTop, color));
            renderVertices.add(new RenderVertex(new Vector(x0, y0, length), nTop, color));
            renderVertices.add(new RenderVertex(new Vector(x1, y1, length), nTop, color));

            renderVertices.add(new RenderVertex(new Vector(x1, y1, length), normal, color));
            renderVertices.add(new RenderVertex(new Vector(x0, y0, length), normal, color));
            renderVertices.add(new RenderVertex(new Vector(x0, y0, -length), normal, color));

            renderVertices.add(new RenderVertex(new Vector(x1, y1, length), normal, color));
            renderVertices.add(new RenderVertex(new Vector(x0, y0, -length), normal, color));
            renderVertices.add(new RenderVertex(new Vector(x1, y1, -length), normal, color));

            renderVertices.add(new RenderVertex(new Vector(x0, y0, -length), nBot, color));
            renderVertices.add(new RenderVertex(new Vector(0.0, 0.0, -length), nBot, color));
            renderVertices.add(new RenderVertex(new Vector(x1, y1, -length), nBot, color));
        }
        vbo.Setup(renderVertices, GL2.GL_TRIANGLES);
    }

    @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        if (mode == RenderMode.REGULAR) {
            vbo.draw(gl);
        }
    }
}
