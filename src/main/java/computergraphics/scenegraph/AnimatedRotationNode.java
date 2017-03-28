package computergraphics.scenegraph;

import computergraphics.math.Vector;

public class AnimatedRotationNode extends RotationNode {

    private double rate;
    public AnimatedRotationNode(Vector axis,Double rate) {
        super(axis, 0.0);
        this.rate = rate;
    }

    @Override
    public void timerTick(int counter) {
        setAngle((double) (counter*rate));
        super.timerTick(counter);
    }
}
