package computergraphics.datastructures.mesh;

import computergraphics.math.Vector;
import computergraphics.rendering.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimhof on 07.04.17.
 */
public class TriangleMesh implements ITriangleMesh {

    List<Vertex> vertexList;
    List<Triangle> triangleList;
    Vector color;
    public TriangleMesh(Vector color) {
        vertexList = new ArrayList<>();
        triangleList = new ArrayList<>();
        this.color = color;
    }

    @Override
    public int addVertex(Vector position) {
        vertexList.add(new Vertex(position));

        return vertexList.size()-1;
    }

    @Override
    public Vertex getVertex(int index) {
        return vertexList.get(index);
    }

    @Override
    public int getNumberOfVertices() {
        return vertexList.size();
    }

    @Override
    public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
        triangleList.add(new Triangle(vertexIndex1,vertexIndex2,vertexIndex3));
    }

    @Override
    public void addTriangle(Triangle t) {
        triangleList.add(t);
    }

    @Override
    public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3, int texCoordIndex1, int texCoordIndex2, int texCoordIndex3) {
        triangleList.add(new Triangle(vertexIndex1,vertexIndex2,vertexIndex3,texCoordIndex1,texCoordIndex2,texCoordIndex3));
    }

    @Override
    public int getNumberOfTriangles() {
        return triangleList.size();
    }

    @Override
    public Triangle getTriangle(int triangleIndex) {
        return triangleList.get(triangleIndex);
    }

    @Override
    public void clear() {
        triangleList.clear();
        vertexList.clear();
    }

    @Override
    public void computeTriangleNormals() {
        //
        for (Triangle triangel:triangleList){

            //FIXME das hier geht noch nicht
            Vector p0 = vertexList.get(triangel.getVertexIndex(0)).getPosition();
            Vector p1 = vertexList.get(triangel.getVertexIndex(1)).getPosition();
            Vector p2 = vertexList.get(triangel.getVertexIndex(2)).getPosition();

            Vector n = p0.cross(p1).add(p1.cross(p2)).add(p2.cross(p0));
            triangel.setNormal(n);

        }
    }

    @Override
    public Vector getColor() {
        return color;
    }

    @Override
    public void setColor(Vector color) {
        this.color = color;
    }

    @Override
    public Vector getTextureCoordinate(int index) {
        return null;
    }

    @Override
    public void addTextureCoordinate(Vector t) {

    }

    @Override
    public void setTexture(Texture texture) {

    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public void createShadowPolygons(Vector lightPosition, float extend, ITriangleMesh shadowPolygonMesh) {

    }
}
