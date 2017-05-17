package computergraphics.exercises;


import java.util.Iterator;
import java.util.List;

import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;

public class TensorProductSurfaces {
	
	
//	BezierCurve f;
//	BezierCurve g;
	Vector[][] controlPoints;
	int degree;
	
	public TensorProductSurfaces(Vector[][] controlPoints,int degree){
		this.controlPoints = controlPoints;
		this.degree = degree;
//		f = new BezierCurve();
//		g = new BezierCurve();
//		
//		for (int i = 0; i < degree+1; i++) {
//			f.addcontrolPoints(controlPoints[0][i]);
//			g.addcontrolPoints(controlPoints[1][i]);
//		}
	}
	
	public TensorProductSurfaces(List<Vector> f, List<Vector> g){
		this.controlPoints = new Vector[2][f.size() > g.size()? f.size():g.size()];
		for (int i = 0; i < f.size(); i++) {
			this.controlPoints[0][i] = f.get(i);
		}
		for (int i = 0; i < g.size(); i++) {
			this.controlPoints[1][i] = g.get(i);
		}
	}
	
	public Vector getValue(double u,double v){
		Vector result = new Vector(3);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < degree+1; j++) {
					Vector temp = controlPoints[i][j]; // c(ij)
					temp = temp.multiply(BezierCurve.baseFunction(u, i,degree)).multiply(BezierCurve.baseFunction(v, j,degree)); // c * F * G
					result = result.add(temp);
			}
		}
		return result;
	}

	public Vector getTangent_U(double v){
		Vector result = new Vector(3);
		//for ()
		//TO-DO
		//siehe Folie 53 Satz 04
		//controlpoints?? wann welche mit v oder u multiplizieren?
		
		return null;
		
	}
	
	public Vector getTangent_V(double u){
		
		return null;
		
	}
	public TriangleMesh getTriangleMesh(int steps){
//		Vector[][] grid = new Vector[steps][steps];
		Integer[][] gridIndices = new Integer[steps][steps];
		TriangleMesh result = new TriangleMesh(new Vector(1,0,0,1));
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < steps; j++) {
//				grid[i][j] = getValue(i/steps, j/steps);
				Vector tmp = getValue(((double)i)/steps, ((double)j)/steps);
				gridIndices[i][j] = result.addVertex(tmp);

				System.out.println("u: "+((double)i)/steps+" v: "+((double)j)/steps+ " = "+tmp );
			}
		}
		
		for (int u = 0; u < steps-1; u++) {
			for (int v = 0; v < steps-1; v++) {
				result.addTriangle(gridIndices[u][v],gridIndices[u][v+1],gridIndices[u+1][v]);
				result.addTriangle(gridIndices[u+1][v],gridIndices[u][v+1],gridIndices[u+1][v+1]);
			}
		}
		
		return result;
	}
}
