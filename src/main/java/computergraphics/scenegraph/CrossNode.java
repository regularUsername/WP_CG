package computergraphics.scenegraph;

import computergraphics.math.Vector;

/**
 * Created by dimhof on 26.06.2017.
 */
public class CrossNode extends InnerNode {
    public CrossNode(double length, float lineWidth,boolean bidir) {
        LineNode x;
        LineNode y;
        LineNode z;
        if (bidir) {
            x = new LineNode(new Vector(-length, 0, 0), new Vector(length, 0, 0), lineWidth, new Vector(0, 1, 0, 1));
            y = new LineNode(new Vector(0, -length, 0), new Vector(0, length, 0), lineWidth, new Vector(1, 0, 0, 1));
            z = new LineNode(new Vector(0, 0, -length), new Vector(0, 0, length), lineWidth, new Vector(0, 0, 1, 1));
        } else {
            x = new LineNode(new Vector(0, 0, 0), new Vector(length, 0, 0), lineWidth, new Vector(0, 1, 0, 1));
            y = new LineNode(new Vector(0, 0, 0), new Vector(0, length, 0), lineWidth, new Vector(1, 0, 0, 1));
            z = new LineNode(new Vector(0, 0, 0), new Vector(0, 0, length), lineWidth, new Vector(0, 0, 1, 1));
        }

        addChild(x);
        addChild(y);
        addChild(z);
    }
}
