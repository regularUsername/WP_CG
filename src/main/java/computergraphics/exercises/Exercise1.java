/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * <p>
 * Base framework for "WP Computergrafik".
 */

package computergraphics.exercises;

import java.awt.event.KeyEvent;

import com.jogamp.opengl.GL2;

import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.*;
import computergraphics.scenegraph.INode.RenderMode;

/**
 * Application for the first exercise.
 *
 * @author Philipp Jenke
 */
public class Exercise1 extends Scene {
    private static final long serialVersionUID = 8141036480333465137L;

    public Exercise1() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(16, Shader.ShaderMode.PHONG, RenderMode.REGULAR);
    }

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

        // Rotate Cube
//        AnimatedRotationNode cubeRotation = new AnimatedRotationNode(new Vector(0, 0, 1),5.0);
//        CubeNode cubeNode1 = new CubeNode(0.5);
//        cubeRotation.addChild(cubeNode1);
//        getRoot().addChild(cubeRotation);

        // Tree
        
        
        
     // Minion

     		TranslationNode MinionTranslation = new TranslationNode(new Vector(2.0, 0.0, 0.0));

     		CylinderNode minionBody = new CylinderNode(0.5, 0.4, 16, new Vector(1, 0.85, 0, 1));
     		SphereNode minionTop = new SphereNode(0.5, 16, new Vector(1, 0.85, 0, 1));
     		SphereNode minionBottom = new SphereNode(0.5, 16, new Vector(1, 0.85, 0, 1));
     		CylinderNode minionLegOne = new CylinderNode(0.1, 0.2, 16, new Vector(1, 0.85, 0, 1));
     		CylinderNode minionArmOne = new CylinderNode(0.1, 0.2, 16, new Vector(1, 0.85, 0, 1));
     		CylinderNode minionGlassStrap = new CylinderNode(0.51, 0.05, 16, new Vector(0.34, 0.34, 0.34, 1)); // Change
     																											// Color!
     		CylinderNode minionEye = new CylinderNode(0.25, 0.2, 16, new Vector(0.3, 0.3, 0.3, 1)); // Change
     																								// Color!

     		TranslationNode minionTopTranslation = new TranslationNode(new Vector(0.0, 0.0, 1));
     		TranslationNode minionBottomTranslation = new TranslationNode(new Vector(0.0, 0.0, 0.2));
     		TranslationNode minionBodyTranslation = new TranslationNode(new Vector(0.0, 0.0, 0.6));
     		TranslationNode minionLegOneTranslation = new TranslationNode(new Vector(0.2, 0.0, -0.4));
     		TranslationNode minionLegTwoTranslation = new TranslationNode(new Vector(-0.2, 0.0, -0.4));
     		TranslationNode minionGlassStrapTranslation = new TranslationNode(new Vector(0.0, 0.0, 0.9));
     		TranslationNode minionArmOneTranslation = new TranslationNode(new Vector(-0.5, 0.0, 0.2));
     		TranslationNode minionArmTwoTranslation = new TranslationNode(new Vector(0.5, 0.0, 0.2));
     		TranslationNode minionEyeTranslation = new TranslationNode(new Vector(0.0, -0.3, 0.9));

     		RotationNode minionArmOneRotation = new RotationNode(new Vector(0, 1, 0), 30.0);
     		RotationNode minionArmTwoRotation = new RotationNode(new Vector(0, 1, 0), -30.0);
     		RotationNode minionEyeRotation = new RotationNode(new Vector(1, 0, 0), 90.0);

     		minionTopTranslation.addChild(minionTop);
     		minionBottomTranslation.addChild(minionBottom);
     		minionBodyTranslation.addChild(minionBody);
     		minionLegOneTranslation.addChild(minionLegOne);
     		minionLegTwoTranslation.addChild(minionLegOne);
     		minionGlassStrapTranslation.addChild(minionGlassStrap);

     		minionArmOneRotation.addChild(minionArmOne);
     		minionArmTwoRotation.addChild(minionArmOne);
     		minionEyeRotation.addChild(minionEye);

     		minionArmOneTranslation.addChild(minionArmOneRotation);
     		minionArmTwoTranslation.addChild(minionArmTwoRotation);
     		minionEyeTranslation.addChild(minionEyeRotation);

     		MinionTranslation.addChild(minionBodyTranslation);
     		MinionTranslation.addChild(minionTopTranslation);
     		MinionTranslation.addChild(minionBottomTranslation);
     		MinionTranslation.addChild(minionLegOneTranslation);
     		MinionTranslation.addChild(minionLegTwoTranslation);
     		MinionTranslation.addChild(minionGlassStrapTranslation);
     		MinionTranslation.addChild(minionEyeTranslation);
     		MinionTranslation.addChild(minionArmOneTranslation);
     		MinionTranslation.addChild(minionArmTwoTranslation);


//     		getRoot().addChild(MinionTranslation);
     		
     		
          AnimatedRotationNode cubeRotation = new AnimatedRotationNode(new Vector(0, 0, 1),2.0);
          cubeRotation.addChild(MinionTranslation);
          getRoot().addChild(cubeRotation);
        
        
        
        
        
        
        
        CylinderNode trunk = new CylinderNode(0.2,0.5,16);
        ScaleNode treeScale = new ScaleNode(new Vector(0.5,0.5,0.5));
        treeScale.addChild(trunk);
        SphereNode crown = new SphereNode(0.6, 20, new Vector(0.25, 0.75, 0.25, 1));
        TranslationNode crownTranslation = new TranslationNode(new Vector(0.0,0.0,0.6));
        crownTranslation.addChild(crown);
        treeScale.addChild(crownTranslation); // Wurzel des Baums
        
        for (int i = 0; i < 10;  i++) {
			Double x =  (Math.random()*10)-5;
			Double y =  (Math.random()*10)-5;
			
			TranslationNode treeTranslation = new TranslationNode(new Vector(x,y,-0.25));
			treeTranslation.addChild(treeScale);
			getRoot().addChild(treeTranslation);
			
		}
        
        // Tree end

        // Plane
        TranslationNode planeTranslation = new TranslationNode(new Vector(0,0,-0.5));
        PlaneNode planeNode = new PlaneNode(5);
        planeTranslation.addChild(planeNode);
        getRoot().addChild(planeTranslation);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        // Key pressed event
    }

    @Override
    public void timerTick(int counter) {
        // Timer tick event
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        new Exercise1();
    }
}
