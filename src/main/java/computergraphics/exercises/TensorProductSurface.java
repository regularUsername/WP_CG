package computergraphics.exercises;


import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;

public class TensorProductSurface {

    private final Curve f;
    private final Curve g;
    private final Vector[][] controlPoints;
    private final int degreeU;
    private final int degreeV;

    public TensorProductSurface(Vector[][] controlPoints, int degreeU, int degreeV, Curve f, Curve g) {
        this.controlPoints = controlPoints;
        this.degreeU = degreeU;
        this.degreeV = degreeV;
        this.f = f; // wird nur für basefunction gebraucht
        this.g = g;
    }

    public Vector getValue(double u, double v) {
        Vector result = new Vector(3);
        for (int i = 0; i < degreeU; i++) {
            for (int j = 0; j < degreeV + 1; j++) {
                Vector temp = controlPoints[i][j]; // c(ij)
                temp = temp.multiply(f.baseFunction(u, i, degreeU)).multiply(g.baseFunction(v, j, degreeV)); // c * F * G
                result = result.add(temp);
            }
        }
        return result;
    }

    public Vector getTangent_U(double u, double v) {
        final Double d = 0.0001;


        Vector point0 = getValue(u, v);
        Vector point1 = getValue(u + d, v);

        Vector result = point1.subtract(point0).multiply(1 / d);
        result.normalize();

        return result;
    }

    public Vector getTangent_V(double u, double v) {
        final Double d = 0.0001;


        Vector point0 = getValue(u, v);
        Vector point1 = getValue(u, v + d);

        Vector result = point1.subtract(point0).multiply(1 / d);
        result.normalize();

        return result;
    }

    public Vector getNormal(double u, double v) {
        Vector result = getTangent_U(u, v).cross(getTangent_V(u, v));
        result.normalize();
        return result;
    }

    public TriangleMesh getTriangleMesh(int steps, Vector color) {
        Integer[][] gridIndices = new Integer[steps][steps];
        TriangleMesh result = new TriangleMesh(color);
        for (int i = 0; i < steps; i++) {
            for (int j = 0; j < steps; j++) {
                Vector tmp = getValue(((double) i) / steps, ((double) j) / steps);
                gridIndices[i][j] = result.addVertex(tmp);
            }
        }

        for (int u = 0; u < steps - 1; u++) {
            for (int v = 0; v < steps - 1; v++) {
                result.addTriangle(gridIndices[u][v], gridIndices[u + 1][v], gridIndices[u][v + 1]);
                result.addTriangle(gridIndices[u + 1][v], gridIndices[u + 1][v + 1], gridIndices[u][v + 1]);
            }
        }

        return result;
    }
}
