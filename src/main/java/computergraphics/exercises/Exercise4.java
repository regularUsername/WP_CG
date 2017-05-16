package computergraphics.exercises;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.rendering.Shader.ShaderMode;
import computergraphics.scenegraph.INode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TranslationNode;
import computergraphics.scenegraph.INode.RenderMode;

public class Exercise4 extends Scene{

	
    public Exercise4() {
		super(100, Shader.ShaderMode.TEXTURE, INode.RenderMode.REGULAR);
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

        
	}
	
	/**
     * Program entry point.
     */
    public static void main(String[] args) {
        new Exercise4();
    }

	
}
