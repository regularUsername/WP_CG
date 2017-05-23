package computergraphics.exercises;

import computergraphics.math.Vector;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.MathHelpers;;

public class BezierCurve extends Curve{
	
	public BezierCurve(){
		super();
	}
	
	public BezierCurve(List<Vector> controlPoints) {
		super(controlPoints);
	}
	

	@Override
	public double baseFunction(double t, int i, int degree){
		return MathHelpers.over(degree, i) * Math.pow(t, i) * Math.pow(1.0 - t, degree-i);
	}

//	public Vector calculateTangent(double t) {
//
//		Vector vecTan = new Vector(3);
//		int size = getControlPoints().size();
//		List<Vector> controlPoints = getControlPoints();
//
//		for(int i = 0; i<(size-1); i++){
//			//Q berechnen: n*(P1-P0)
//			Vector q = controlPoints.get(i+1).subtract(controlPoints.get(i));
//			double temp1 = MathHelpers.over(getDegree()-1, i);
//			double temp2 = Math.pow(t, i);
//			double temp3 = Math.pow((1 - t), (getDegree()-1 - i));
//			q = q.multiply(temp1 * temp2 * temp3);
//
//			vecTan = vecTan.add(q);
//		}
//		return vecTan;
//	}

}
