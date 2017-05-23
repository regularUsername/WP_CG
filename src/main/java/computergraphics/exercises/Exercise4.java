package computergraphics.exercises;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.*;

import java.awt.event.KeyEvent;

public class Exercise4 extends Scene{


    private TensorProductSurface tensorProductSurface;

    public Exercise4() {
		super(100, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3701527599796171631L;

	private double u,v;
	private LineNode lineNode;

	
	@Override
	public void setupScenegraph(GL2 gl) {
		 // Setup scene after OpenGL is ready
        getRoot().setLightPosition(new Vector(1, 1, 1));
        getRoot().setAnimated(true);


        // Light geometry
        TranslationNode lightTranslation =
                new TranslationNode(getRoot().getLightPosition());
        INode lightSphereNode = new SphereNode(0.1f, 10);
        lightTranslation.addChild(lightSphereNode);
        getRoot().addChild(lightTranslation);
        
        // Plane
//        TranslationNode planeTranslation = new TranslationNode(new Vector(0,0,-0.5));
//        PlaneNode planeNode = new PlaneNode(5);
//        planeTranslation.addChild(planeNode);
//        getRoot().addChild(planeTranslation);
        
        Vector[][] grid = new Vector[4][4];
        grid[0][0] = new Vector(0,3,0); // startpunkt
        grid[0][1] = new Vector(1,3,1); // tangente start
        grid[0][2] = new Vector(2,3,0); // tangente ende
        grid[0][3] = new Vector(3,3,0); // endpunkt
        
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
        tensorProductSurface = new TensorProductSurface(grid,3,3);
        u = 0.5;
        v = 0.5;
        Vector surfacePoint = tensorProductSurface.getValue(u,v);
        Vector surfaceNormal = tensorProductSurface.getNormal(u,v);
        System.out.println(surfacePoint+"   "+surfaceNormal);
        lineNode = new LineNode(surfacePoint,surfacePoint.add(surfaceNormal));
        getRoot().addChild(lineNode);

        TriangleMeshNode triangleMeshNode = new TriangleMeshNode(tensorProductSurface.getTriangleMesh(10));
        getRoot().addChild(triangleMeshNode);

        
	}


	@Override
    public void keyPressed(KeyEvent keyEvent){
	    double d = 0.05;
	    switch(keyEvent.getKeyChar()){
            case 'a':
                System.out.println("pressed u -"+d);
                u-=d;
                break;
            case 's':
                System.out.println("pressed v -"+d);
                v-=d;
                break;
            case 'd':
                System.out.println("pressed u +"+d);
                u+=d;
                break;
            case 'w':
                System.out.println("pressed v +"+d);
                v+=d;
                break;
            default:
                break;
        }

        if(v>1){
	        v=1;
        }
        if(v<0){
            v=0;
        }
        if(u>1){
            u=1;
        }
        if(u<0){
            u=0;
        }

        Vector surfacePoint = tensorProductSurface.getValue(u,v);
        Vector surfaceNormal = tensorProductSurface.getNormal(u,v);
        System.out.println(surfacePoint+"   "+surfaceNormal);
        lineNode.setVecs(surfacePoint,surfacePoint.add(surfaceNormal));
    }

	/**
     * Program entry point.
     */
    public static void main(String[] args) {
        new Exercise4();
    }

	
}
