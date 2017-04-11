package computergraphics.exercises;

import com.jogamp.opengl.GL2;
import computergraphics.datastructures.mesh.ObjReader;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.*;

import java.awt.event.KeyEvent;


public class Exercise2 extends Scene {
    private static final long serialVersionUID = 8141036480333465137L;

    public Exercise2() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(16, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);
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
        TranslationNode planeTranslation = new TranslationNode(new Vector(0,0,-0.5));
        PlaneNode planeNode = new PlaneNode(5);
        planeTranslation.addChild(planeNode);
        getRoot().addChild(planeTranslation);

        // TriangleMesh
        ObjReader objReader = new ObjReader();
        TriangleMesh triangleMesh = new TriangleMesh(new Vector(1,0,0,1));
        objReader.read("meshes/bunny.obj",triangleMesh);
        TriangleMeshNode triangleMeshNode = new TriangleMeshNode(triangleMesh,true,0.01);

        ScaleNode scaleNode = new ScaleNode(new Vector(10,10,10));
        scaleNode.addChild(triangleMeshNode);

        RotationNode rotationNode = new RotationNode(new Vector(1,0,0),90.0);
        rotationNode.addChild(scaleNode);
        getRoot().addChild(rotationNode);
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
        new Exercise2();
    }
}