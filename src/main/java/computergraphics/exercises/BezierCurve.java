package computergraphics.exercises;

import computergraphics.math.Vector;

import java.util.List;

import computergraphics.math.MathHelpers;;

public class BezierCurve extends Curve{

	@Override
	public Vector getValue(double t) {
		Vector result = new Vector(4);
		int size = getControlPoints().size();
		for(int i = 0; i < size; i++){
			Vector temp = getControlPoints().get(i);
			temp = temp.multiply(MathHelpers.over(getDegree(), i) * Math.pow(t, i) * Math.pow(1.0 - t, getDegree()-i));
			result = result.add(temp);
		}
		
		return result;
	}

	@Override
	public Vector calculateTangent(double t) {
		
		Vector vecTan = new Vector(4);
		int size = getControlPoints().size();
		List<Vector> controlPoints = getControlPoints();
		
		for(int i = 0; i<(size-1); i++){
			//Q berechnen: n*(P1-P0)
			Vector q = controlPoints.get(i+1).subtract(controlPoints.get(i));
			double temp1 = MathHelpers.over(getDegree()-1, i);
			double temp2 = Math.pow(t, i);
			double temp3 = Math.pow((1 - t), (getDegree()-1 - i));
			q = q.multiply(temp1 * temp2 * temp3); 
			
			vecTan = vecTan.add(q);
		}
		return vecTan;
	}

}
