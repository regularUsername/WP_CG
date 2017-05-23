package computergraphics.exercises;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Vector;

public abstract class Curve {

private List<Vector> controlPoints;
	
	public Curve(){
		controlPoints = new ArrayList<>();
	}
	
	public Curve(List<Vector> controlPoints){
		this.controlPoints = controlPoints;
	}
	
	 
		public void addcontrolPoints(Vector controlPoints){
			controlPoints.add(controlPoints);		
		}
		
		/**
		 * TODO
		 * @param controlPoint
		 */
		public void deleteControlPoints(Vector controlPoint){
			controlPoints.remove(controlPoint);
		}
		
		/**
		 * @return
		 */
		public List<Vector> getControlPoints(){

			return controlPoints;
		}
		
		/**
		 * @param controlPointsList
		 */
		public void addcontrolPoints(List<Vector> controlPointsList){
			controlPoints.addAll(controlPointsList);
		}
		
		
		/**
		 * liefert eine Liste von 
		 * @return list
		 */
		public List<Vector> calculateCurve(){
			
			List<Vector> list = new ArrayList<>();		
		
			
			return list;
		}
		

		
		/**
		 * Degree of Curve
		 * @return
		 */
		public int getDegree(){
			return controlPoints.size() -1;
		}
		
		/**
		 * curve value at position t
		 * @param t
		 * @return
		 */
		public Vector getValue(double t) {
			Vector result = new Vector(3);
			int size = getControlPoints().size();
			for(int i = 0; i < size; i++){
				Vector temp = getControlPoints().get(i);
				temp = temp.multiply(baseFunction(t, i,getDegree()));
				result = result.add(temp);
			}

			return result;
		}

		public abstract double baseFunction(double t, int i, int degree);
		
		/**
		 * tangent at position t
		 * @param t
		 * @return
		 */
        public Vector calculateTangent(double t) {
            final double d = 0.001;
            double t1 = t+ d;

            Vector point0 = getValue(t);
            Vector point1 = getValue(t1);

            return point1.subtract(point0).multiply(1/d);
        }
		
		
	
}
