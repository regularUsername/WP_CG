package computergraphics.exercises;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.*;

import java.awt.event.KeyEvent;

public class Exercise4 extends Scene{

    private final double stepSize = 0.05;


    private TensorProductSurface tensorProductSurface;

    public Exercise4() {
		super(100, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);
		u = 0.5;
		v = 0.5;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3701527599796171631L;

	private double u,v;
	private LineNode tpsNormal;
	private LineNode tpsTangentU;
	private LineNode tpsTangentV;

	
	@Override
	public void setupScenegraph(GL2 gl) {
		 // Setup scene after OpenGL is ready
        getRoot().setLightPosition(new Vector(1, 1, 2));
        getRoot().setAnimated(true);


        // Light geometry
        TranslationNode lightTranslation =
                new TranslationNode(getRoot().getLightPosition());
        INode lightSphereNode = new SphereNode(0.1f, 10);
        lightTranslation.addChild(lightSphereNode);
        getRoot().addChild(lightTranslation);


        int degreeU = 3;
        int degreeV = 3;
        Vector[][] grid = new Vector[degreeU+1][degreeV+1];
        grid[0][0] = new Vector(0,3,0);
        grid[0][1] = new Vector(1,3,1);
        grid[0][2] = new Vector(2,3,0);
        grid[0][3] = new Vector(3,3,0);
        
        grid[1][0] = new Vector(0,2,0);
        grid[1][1] = new Vector(1,2,-2);
        grid[1][2] = new Vector(0,1,1);
        grid[1][3] = new Vector(2,2,2);

        grid[2][0] = new Vector(0,1,0);
        grid[2][1] = new Vector(1,1,1);
        grid[2][2] = new Vector(2,1,4);
        grid[2][3] = new Vector(3,1,0);

        grid[3][0] = new Vector(0,0,0);
        grid[3][1] = new Vector(1,0,0);
        grid[3][2] = new Vector(2,0,0);
        grid[3][3] = new Vector(3,0,0);
        tensorProductSurface = new TensorProductSurface(grid,3,3,new BezierCurve(), new BezierCurve());

        Vector surfacePoint = tensorProductSurface.getValue(u,v);
        Vector surfaceNormal = tensorProductSurface.getNormal(u,v);
        Vector surfaceTangentU = tensorProductSurface.getTangent_U(u,v);
        Vector surfaceTangentV = tensorProductSurface.getTangent_V(u,v);
        System.out.println(surfacePoint+"   "+surfaceNormal);
        tpsNormal = new LineNode(surfacePoint,surfacePoint.add(surfaceNormal),new Vector(0,1,0,1));
        tpsTangentU = new LineNode(surfacePoint.subtract(surfaceTangentU),surfacePoint.add(surfaceTangentU),new Vector(0,0,1,1));
        tpsTangentV = new LineNode(surfacePoint.subtract(surfaceTangentV),surfacePoint.add(surfaceTangentV),new Vector(1,0,0,1));

        TranslationNode tpsTranslation = new TranslationNode(new Vector(-1,-1,0));
        tpsTranslation.addChild(tpsNormal);
        tpsTranslation.addChild(tpsTangentV);
        tpsTranslation.addChild(tpsTangentU);

        TriangleMeshNode triangleMeshNode = new TriangleMeshNode(tensorProductSurface.getTriangleMesh(10,new Vector(0.7,0.7,0.7,1)));
        tpsTranslation.addChild(triangleMeshNode);

        getRoot().addChild(tpsTranslation);

        
	}


	@Override
    public void keyPressed(KeyEvent keyEvent){
	    switch(keyEvent.getKeyChar()){
            case 'w':
                u-=stepSize;
                if(u<0){
                    u=0;
                }
                break;
            case 'a':
                v-=stepSize;
                if(v<0){
                    v=0;
                }
                break;
            case 's':
                u+=stepSize;
                if(u>1){
                    u=1;
                }
                break;
            case 'd':
                v+=stepSize;
                if(v>1){
                    v=1;
                }
                break;
            default:
                break;
        }


        Vector surfacePoint = tensorProductSurface.getValue(u,v);
        Vector surfaceNormal = tensorProductSurface.getNormal(u,v);
        Vector surfaceTangentU = tensorProductSurface.getTangent_U(u,v);
        Vector surfaceTangentV = tensorProductSurface.getTangent_V(u,v);

        tpsNormal.setVecs(surfacePoint,surfacePoint.add(surfaceNormal));
        tpsTangentU.setVecs(surfacePoint.subtract(surfaceTangentU),surfacePoint.add(surfaceTangentU));
        tpsTangentV.setVecs(surfacePoint.subtract(surfaceTangentV),surfacePoint.add(surfaceTangentV));

    }

	/**
     * Program entry point.
     */
    public static void main(String[] args) {
        new Exercise4();
    }

	
}
