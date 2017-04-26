package computergraphics.exercises;

import com.jogamp.opengl.GL2;
import computergraphics.datastructures.mesh.ObjReader;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.rendering.Texture;
import computergraphics.scenegraph.*;

import java.awt.event.KeyEvent;


public class Exercise3 extends Scene {
    private static final long serialVersionUID = 8141036480333465137L;

    public Exercise3() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(100, Shader.ShaderMode.TEXTURE, INode.RenderMode.REGULAR);
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


        // Square
        ObjReader objReader = new ObjReader();
        TriangleMesh square = new TriangleMesh(new Vector(1,0,0,1));
//        triangleMesh.setTexture(new Texture("textures/lego.png"));
//        objReader.read("meshes/square.obj",triangleMesh);
        objReader.read("meshes/square.obj","meshes/square.mtl",square);
        TriangleMeshNode squareNode = new TriangleMeshNode(square,true,0.01);
                getRoot().addChild(squareNode);

        // Box
        ObjReader objReader2 = new ObjReader();
        TriangleMesh box = new TriangleMesh(new Vector(0,1,0,1));
        objReader2.read("meshes/box.obj", box);
        box.setTexture(new Texture("textures/box.jpg"));
        TriangleMeshNode boxNode = new TriangleMeshNode(box,true,0.01);

        TranslationNode boxTranslation = new TranslationNode(new Vector(1.2,0,0));
        
        
        boxTranslation.addChild(boxNode);
        
        RotationNode boxRotation = new RotationNode(new Vector(1,0,0), -90);
        boxRotation.addChild(boxTranslation);

        getRoot().addChild(boxRotation);
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
        new Exercise3();
    }
}