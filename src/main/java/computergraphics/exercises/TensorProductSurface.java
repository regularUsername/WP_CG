package computergraphics.exercises;


import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;

import java.util.List;

public class TensorProductSurface {

    class Pair<T> {
        public final T a;
        public final T b;

        Pair(T a, T b) {
            this.a = a;
            this.b = b;
        }
    }


    Curve f;
    //	Curve g;
    Vector[][] controlPoints;
    int degreeU;
    int degreeV;

    public TensorProductSurface(Vector[][] controlPoints, int degreeU, int degreeV) {
        this.controlPoints = controlPoints;
        this.degreeU = degreeU;
        this.degreeV = degreeV;
        f = new BezierCurve();
//		g = new BezierCurve();
//		
//		for (int i = 0; i < degree+1; i++) {
//			f.addcontrolPoints(controlPoints[0][i]);
//			g.addcontrolPoints(controlPoints[1][i]);
//		}
    }

    public TensorProductSurface(List<Vector> f, List<Vector> g) {
        this.controlPoints = new Vector[2][f.size() > g.size() ? f.size() : g.size()];
        for (int i = 0; i < f.size(); i++) {
            this.controlPoints[0][i] = f.get(i);
        }
        for (int i = 0; i < g.size(); i++) {
            this.controlPoints[1][i] = g.get(i);
        }
    }

    public Vector getValue(double u, double v) {
        Vector result = new Vector(3);
        for (int i = 0; i < degreeU; i++) {
            for (int j = 0; j < degreeV + 1; j++) {
                Vector temp = controlPoints[i][j]; // c(ij)
                temp = temp.multiply(f.baseFunction(u, i, degreeU)).multiply(f.baseFunction(v, j, degreeV)); // c * F * G
                result = result.add(temp);
            }
        }
        return result;
    }

    public Vector getTangent_U(double u, double v) {
        final Double d = 0.0001;
        Vector result = new Vector(3);


        Vector point0 = getValue(u, v);
        Vector point1 = getValue(u + d, v);

        return point1.subtract(point0).multiply(1 / d);
    }

    public Vector getTangent_V(double u, double v) {
        final Double d = 0.0001;
        Vector result = new Vector(3);


        Vector point0 = getValue(u, v);
        Vector point1 = getValue(u, v + d);

        return point1.subtract(point0).multiply(1 / d);
    }

    public Vector getNormal(double u, double v){
        Vector result = getTangent_U(u,v).cross(getTangent_V(u,v));
        result.normalize();
        return result;
    }

    public TriangleMesh getTriangleMesh(int steps) {
//		Vector[][] grid = new Vector[steps][steps];
        Integer[][] gridIndices = new Integer[steps][steps];
        TriangleMesh result = new TriangleMesh(new Vector(1, 0, 0, 1));
        for (int i = 0; i < steps; i++) {
            for (int j = 0; j < steps; j++) {
//				grid[i][j] = getValue(i/steps, j/steps);
                Vector tmp = getValue(((double) i) / steps, ((double) j) / steps);
                gridIndices[i][j] = result.addVertex(tmp);

//                System.out.println("u: " + ((double) i) / steps + " v: " + ((double) j) / steps + " = " + tmp);
            }
        }

        for (int u = 0; u < steps - 1; u++) {
            for (int v = 0; v < steps - 1; v++) {
//                result.addTriangle(gridIndices[u][v], gridIndices[u][v + 1], gridIndices[u + 1][v]);
//                result.addTriangle(gridIndices[u + 1][v], gridIndices[u][v + 1], gridIndices[u + 1][v + 1]);
                result.addTriangle(gridIndices[u][v], gridIndices[u + 1][v],gridIndices[u][v + 1] );
                result.addTriangle(gridIndices[u + 1][v],  gridIndices[u + 1][v + 1],gridIndices[u][v + 1]);
            }
        }

        return result;
    }
}
