package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.datastructures.mesh.Triangle;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.datastructures.mesh.Vertex;
import computergraphics.math.Matrix;
import computergraphics.rendering.RenderVertex;
import computergraphics.rendering.VertexBufferObject;

import java.util.ArrayList;
import java.util.List;

public class TriangleMeshNode extends LeafNode {
    private VertexBufferObject vbo;

    public TriangleMeshNode(TriangleMesh triangleMesh) {
        vbo = new VertexBufferObject();
        createVbo(triangleMesh);
    }

    private void createVbo(TriangleMesh triangleMesh){
        List<RenderVertex> renderVertices = new ArrayList<>();
        for (int i = 0; i < triangleMesh.getNumberOfTriangles(); i++) {
            Triangle triangle = triangleMesh.getTriangle(i);

            Vertex v0 = triangleMesh.getVertex(triangle.getVertexIndex(0));
            Vertex v1 = triangleMesh.getVertex(triangle.getVertexIndex(1));
            Vertex v2 = triangleMesh.getVertex(triangle.getVertexIndex(2));
            renderVertices.add(new RenderVertex(v0.getPosition(),triangle.getNormal(),triangleMesh.getColor()));
            renderVertices.add(new RenderVertex(v1.getPosition(),triangle.getNormal(),triangleMesh.getColor()));
            renderVertices.add(new RenderVertex(v2.getPosition(),triangle.getNormal(),triangleMesh.getColor()));
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
