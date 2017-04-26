package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.datastructures.mesh.Triangle;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.rendering.RenderVertex;
import computergraphics.rendering.Texture;
import computergraphics.rendering.VertexBufferObject;

import java.util.ArrayList;
import java.util.List;

public class TriangleMeshNode extends LeafNode {
    private VertexBufferObject vbo;
    private VertexBufferObject vbo_normals; // vbo zum darstellen der normalen als linien
    private boolean showNormals = false;
    private double normalScale = 0.1; // skalierung der länge der normalen
    private TriangleMesh triangleMesh;

    public TriangleMeshNode(TriangleMesh triangleMesh) {
        this.triangleMesh = triangleMesh;

        vbo = new VertexBufferObject();

        createVbo();
    }

    public TriangleMeshNode(TriangleMesh triangleMesh, Boolean showNormals) {
        this(triangleMesh);

        this.showNormals = showNormals;
        vbo_normals = new VertexBufferObject();

        if (showNormals) {
            createVboNormals();
        }
    }

    public TriangleMeshNode(TriangleMesh triangleMesh, Boolean showNormals, double normalScale) {
        this(triangleMesh);
        this.normalScale = normalScale;
        this.showNormals = showNormals;
        vbo_normals = new VertexBufferObject();

        if (showNormals) {
            createVboNormals();
        }
    }

    private void createVbo() {
        List<RenderVertex> renderVertices = new ArrayList<>();

        boolean texture = triangleMesh.getTexture() != null;

        for (int i = 0; i < triangleMesh.getNumberOfTriangles(); i++) {
            Triangle triangle = triangleMesh.getTriangle(i);

            int i0 = triangle.getVertexIndex(0);
            int i1 = triangle.getVertexIndex(1);
            int i2 = triangle.getVertexIndex(2);

            int j0 = triangle.getTexCoordIndex(0);
            int j1 = triangle.getTexCoordIndex(1);
            int j2 = triangle.getTexCoordIndex(2);


            // Vektoren der vertices holen
            Vector v0 = triangleMesh.getVertex(i0).getPosition();
            Vector v1 = triangleMesh.getVertex(i1).getPosition();
            Vector v2 = triangleMesh.getVertex(i2).getPosition();

            if (!texture) {
                renderVertices.add(new RenderVertex(v0, triangle.getNormal(), triangleMesh.getColor()));
                renderVertices.add(new RenderVertex(v1, triangle.getNormal(), triangleMesh.getColor()));
                renderVertices.add(new RenderVertex(v2, triangle.getNormal(), triangleMesh.getColor()));
            } else {
                renderVertices.add(new RenderVertex(v0, triangle.getNormal(), triangleMesh.getColor(),triangleMesh.getTextureCoordinate(j0)));
                renderVertices.add(new RenderVertex(v1, triangle.getNormal(), triangleMesh.getColor(),triangleMesh.getTextureCoordinate(j1)));
                renderVertices.add(new RenderVertex(v2, triangle.getNormal(), triangleMesh.getColor(),triangleMesh.getTextureCoordinate(j2)));
            }
        }
        vbo.Setup(renderVertices, GL2.GL_TRIANGLES);
    }

    // füllt das vertexbufferobject zum darstellen der normalen
    private void createVboNormals() {
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
            Texture texture = triangleMesh.getTexture();
            if(texture != null){
            	if (texture.isLoaded()){
            		texture.bind(gl);
            	} else {
            		texture.load(gl);
            	}
            }
        	
            vbo.draw(gl);
            if (showNormals) {
                vbo_normals.draw(gl);
            }

        }
    }
}
