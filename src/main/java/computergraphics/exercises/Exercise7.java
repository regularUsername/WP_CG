/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * <p>
 * Base framework for "WP Computergrafik".
 */

package computergraphics.exercises;

import com.jogamp.opengl.GL2;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.*;
import computergraphics.scenegraph.INode.RenderMode;

import java.awt.event.KeyEvent;

/**
 * Application for the first exercise.
 *
 * @author Philipp Jenke
 */
public class Exercise7 extends Scene {
    private static final long serialVersionUID = 8141036480333465137L;


    private final double stepSize = 5;

    private RobotArm robotArm;

    public Exercise7() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(16, Shader.ShaderMode.PHONG, RenderMode.REGULAR);


        robotArm = new RobotArm();
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

		// Plane
		TranslationNode planeTranslation = new TranslationNode(new Vector(0,0,-0.1));
		PlaneNode planeNode = new PlaneNode(5);
		planeTranslation.addChild(planeNode);
		getRoot().addChild(planeTranslation);


        getRoot().addChild(robotArm);

		getRoot().addChild(new CrossNode(2,1,false));


    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        char c = keyEvent.getKeyChar();

        // nur zum testen der visualisierung später wird hier die ziel position verändert
        switch (c) {
            case 'w':
                robotArm.setB(robotArm.getB()-stepSize);
                break;
            case 'a':
                robotArm.setY(robotArm.getY()-stepSize);
                break;
            case 's':
                robotArm.setB(robotArm.getB()+stepSize);
                break;
            case 'd':
                robotArm.setY(robotArm.getY()+stepSize);
                break;

            case 'r':
                robotArm.setA(robotArm.getA()-stepSize);
                break;
            case 'f':
                robotArm.setA(robotArm.getA()+stepSize);
                break;
            default:
                System.out.println("keyPressed: " + c);
                break;
        }
    }

    @Override
    public void timerTick(int counter) {
        // Timer tick event
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        new Exercise7();
    }
}
