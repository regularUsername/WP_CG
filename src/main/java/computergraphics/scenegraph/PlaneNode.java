package computergraphics.scenegraph;


import com.jogamp.opengl.GL2;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.rendering.RenderVertex;
import computergraphics.rendering.VertexBufferObject;

import java.util.ArrayList;
import java.util.List;

public class PlaneNode extends LeafNode {

    private VertexBufferObject vbo = new VertexBufferObject();

    public PlaneNode(double sideLength) {
        List<RenderVertex> renderVertices = new ArrayList<>();
        Vector p0 = new Vector(-sideLength, -sideLength, 0);
        Vector p1 = new Vector(sideLength, -sideLength, 0);
        Vector p2 = new Vector(sideLength, sideLength, 0);
        Vector p3 = new Vector(-sideLength, sideLength, 0);
        Vector normal = new Vector(0, 0, 1);
        Vector color = new Vector(0.25, 0.75, 0.25, 1);

        renderVertices.add(new RenderVertex(p0, normal, color));
        renderVertices.add(new RenderVertex(p1, normal, color));
        renderVertices.add(new RenderVertex(p2, normal, color));
        renderVertices.add(new RenderVertex(p3, normal, color));

        vbo.Setup(renderVertices, GL2.GL_QUADS);

    }

    @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        if (mode == RenderMode.REGULAR) {
            vbo.draw(gl);
        }
    }
}
