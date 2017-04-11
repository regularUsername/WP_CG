package computergraphics.scenegraph;


import com.jogamp.opengl.GL2;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;

public class RotationNode extends InnerNode {
    private Matrix rotation;
    private final Vector axis;

    public RotationNode(Vector axis, double angle) {
        this.axis = axis;
        this.rotation = Matrix.createRotationMatrix4GL(axis, Math.toRadians(angle));
    }

    public void setAngle(double angle) {
        this.rotation = Matrix.createRotationMatrix4GL(axis, Math.toRadians(angle));
    }

    public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        super.traverse(gl, mode, rotation.multiply(modelMatrix));
    }

    public void timerTick(int counter) {
        super.timerTick(counter);
    }
}
