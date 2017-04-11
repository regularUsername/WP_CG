package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.datastructures.mesh.Triangle;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.rendering.RenderVertex;
import computergraphics.rendering.VertexBufferObject;

import java.util.ArrayList;
import java.util.List;

public class TriangleMeshNode extends LeafNode {
    private VertexBufferObject vbo;
    private VertexBufferObject vbo_normals; // vbo zum darstellen der normalen als linien
    private boolean showNormals = false;
    private double normalScale = 0.1; // skalierung der länge der normalen

    public TriangleMeshNode(TriangleMesh triangleMesh) {
        vbo = new VertexBufferObject();

        createVbo(triangleMesh);
    }

    public TriangleMeshNode(TriangleMesh triangleMesh, Boolean showNormals) {
        this(triangleMesh);

        this.showNormals = showNormals;
        vbo_normals = new VertexBufferObject();

        if (showNormals) {
            createVboNormals(triangleMesh);
        }
    }

    public TriangleMeshNode(TriangleMesh triangleMesh, Boolean showNormals, double normalScale) {
        this(triangleMesh);
        this.normalScale = normalScale;
        this.showNormals = showNormals;
        vbo_normals = new VertexBufferObject();

        if (showNormals) {
            createVboNormals(triangleMesh);
        }
    }

    private void createVbo(TriangleMesh triangleMesh) {
        List<RenderVertex> renderVertices = new ArrayList<>();

        for (int i = 0; i < triangleMesh.getNumberOfTriangles(); i++) {
            Triangle triangle = triangleMesh.getTriangle(i);

            // Vektoren der vertices holen
            Vector v0 = triangleMesh.getVertex(triangle.getVertexIndex(0)).getPosition();
            Vector v1 = triangleMesh.getVertex(triangle.getVertexIndex(1)).getPosition();
            Vector v2 = triangleMesh.getVertex(triangle.getVertexIndex(2)).getPosition();

            renderVertices.add(new RenderVertex(v0, triangle.getNormal(), triangleMesh.getColor()));
            renderVertices.add(new RenderVertex(v1, triangle.getNormal(), triangleMesh.getColor()));
            renderVertices.add(new RenderVertex(v2, triangle.getNormal(), triangleMesh.getColor()));
        }
        vbo.Setup(renderVertices, GL2.GL_TRIANGLES);
    }

    // füllt das vertexbufferobject zum darstellen der normalen
    private void createVboNormals(TriangleMesh triangleMesh) {
        List<RenderVertex> renderLines = new ArrayList<>();

        Vector color = new Vector(0, 0, 1, 1);

        for (int i = 0; i < triangleMesh.getNumberOfTriangles(); i++) {
            Triangle triangle = triangleMesh.getTriangle(i);

            // Vektoren der vertices holen
            Vector v0 = triangleMesh.getVertex(triangle.getVertexIndex(0)).getPosition();
            Vector v1 = triangleMesh.getVertex(triangle.getVertexIndex(1)).getPosition();
            Vector v2 = triangleMesh.getVertex(triangle.getVertexIndex(2)).getPosition();

            //mittelpunkt des dreiecks berechnen
            //TODO liegt dieser Punkt überhaubt auf der Fläche ? gibt es eine bessere formel ? ( es gibt tausende arten nen mittelpunkt zu berechnen )
            Vector center = v0.add(v1).add(v2).multiply(1.0 / 3.0); // (v0+v1+v2)/3

            renderLines.add(new RenderVertex(center, triangle.getNormal(), color));
            renderLines.add(new RenderVertex(center.add(triangle.getNormal().multiply(normalScale)), triangle.getNormal(), color));
        }
        vbo_normals.Setup(renderLines, GL2.GL_LINES);
    }

    @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        if (mode == RenderMode.REGULAR) {
            vbo.draw(gl);
            if (showNormals) {
                vbo_normals.draw(gl);
            }
        }
    }
}
