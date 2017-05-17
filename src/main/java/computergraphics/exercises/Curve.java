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
		 * @param controlPoints
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
		 * @param controlPointList
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
		 * @param p
		 * @return
		 */
		public abstract Vector getValue(double t);
		
		
		/**
		 * tangent at position t
		 * @param controlPoints
		 * @return
		 */
		public abstract Vector calculateTangent(double t);
		
		
	
}
