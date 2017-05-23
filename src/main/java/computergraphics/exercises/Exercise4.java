package computergraphics.exercises;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.INode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TranslationNode;
import computergraphics.scenegraph.TriangleMeshNode;

import java.awt.event.KeyEvent;

public class Exercise4 extends Scene{

	
    public Exercise4() {
		super(100, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3701527599796171631L;

	
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
        TensorProductSurface tensorProductSurface = new TensorProductSurface(grid,3,3);
        TriangleMeshNode triangleMeshNode = new TriangleMeshNode(tensorProductSurface.getTriangleMesh(10));
        getRoot().addChild(triangleMeshNode);

        
	}


	@Override
    public void keyPressed(KeyEvent keyEvent){}

	/**
     * Program entry point.
     */
    public static void main(String[] args) {
        new Exercise4();
    }

	
}
