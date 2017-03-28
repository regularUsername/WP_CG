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
        super(100, Shader.ShaderMode.PHONG, RenderMode.REGULAR);
    }

    @Override
    public void setupScenegraph(GL2 gl) {
        // Setup scene after OpenGL is ready
        getRoot().setLightPosition(new Vector(1, 1, 1));
        getRoot().setAnimated(true);

        // Sphere geometry
        TranslationNode sphereTranslation =
                new TranslationNode(new Vector(1, -0.5, 0));
        SphereNode sphereNode = new SphereNode(0.5, 20);
        sphereTranslation.addChild(sphereNode);
        getRoot().addChild(sphereTranslation);

        // Cube geometry
        TranslationNode cubeTranslation =
                new TranslationNode(new Vector(-1, 0.5, 0));
        CubeNode cubeNode = new CubeNode(0.5);
        cubeTranslation.addChild(cubeNode);
        getRoot().addChild(cubeTranslation);

        // Light geometry
        TranslationNode lightTranslation =
                new TranslationNode(getRoot().getLightPosition());
        INode lightSphereNode = new SphereNode(0.1f, 10);
        lightTranslation.addChild(lightSphereNode);
        getRoot().addChild(lightTranslation);

        // Rotate Cube
//        RotationNode cubeRotation = new RotationNode(new Vector(0, 0, 1), 45.0);
//        AnimatedRotationNode cubeRotation = new AnimatedRotationNode(new Vector(0, 0, 1),5.0);
//        CubeNode cubeNode1 = new CubeNode(0.5);
//        cubeRotation.addChild(cubeNode1);
//        getRoot().addChild(cubeRotation);

        // Cylinder test
        CylinderNode cylinderNode = new CylinderNode(0.5,1,16);
        getRoot().addChild(cylinderNode);

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
