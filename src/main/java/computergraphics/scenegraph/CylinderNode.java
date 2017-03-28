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

    private Vector color = new Vector(0.75, 0.25, 0.25, 1);

    public CylinderNode(double radius, double length, int resolution) {
        this.radius = radius;
        this.resolution = resolution;
        this.length = length;
        vbo = new VertexBufferObject();
        createVbo();
    }

    private void createVbo() {
        List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

        // TODO hier den zylinder erstellen
        float dTheta = (float) (Math.PI / resolution);
        float dPhi = (float) (Math.PI * 2.0 / resolution);
        int i = 1;
        for (int j = 0; j < resolution; j++) {
            Vector p0 = evaluateSpherePoint(i * dTheta, j * dPhi);
            Vector p1 = evaluateSpherePoint(i * dTheta, (j + 1) * dPhi);
            Vector p2 = evaluateSpherePoint((i + 1) * dTheta, (j + 1) * dPhi);
            Vector p3 = evaluateSpherePoint((i + 1) * dTheta, j * dPhi);
            Vector normal = evaluateSpherePoint((i + 0.5f) * dTheta,
                    (j + 0.5f) * dPhi).getNormalized();

            AddSideVertices(renderVertices, p0, p1, p2, p3, normal, color);
        }
        vbo.Setup(renderVertices, GL2.GL_QUADS);
    }

    private void AddSideVertices(List<RenderVertex> renderVertices, Vector p0,
                                 Vector p1, Vector p2, Vector p3, Vector normal, Vector color) {
        renderVertices.add(new RenderVertex(p3, normal, color));
        renderVertices.add(new RenderVertex(p2, normal, color));
        renderVertices.add(new RenderVertex(p1, normal, color));
        renderVertices.add(new RenderVertex(p0, normal, color));
    }

    private Vector evaluateSpherePoint(float theta, float phi) {
        float x = (float) (radius * Math.sin(theta) * Math.cos(phi));
        float y = (float) (radius * Math.sin(theta) * Math.sin(phi));
        float z = (float) (radius * Math.cos(theta));
        return new Vector(x, y, z);
    }

    @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        if (mode == RenderMode.REGULAR) {
            vbo.draw(gl);
        }
    }
}
