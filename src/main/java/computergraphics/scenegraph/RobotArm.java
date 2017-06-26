package computergraphics.scenegraph;

import computergraphics.math.Vector;

/**
 * Created by dimhof on 26.06.2017.
 */
public class RobotArm extends InnerNode {

    public void setA(double a) {
        this.a = a;
        joint_A.setAngle(a);
    }

    public void setB(double b) {
        this.b = b;
        joint_B.setAngle(b);
    }

    public void setY(double y) {
        this.y = y;
        joint_Y.setAngle(y);
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getY() {
        return y;
    }

    private double a;
    private double b;
    private double y;
    private RotationNode joint_B; // B axis
    private RotationNode joint_A;
    private RotationNode joint_Y; // Y axis
    private final double arm_len = 1;

    public RobotArm() {

        a = 100;
        b = 10;
        y = 0;
        joint_B = new RotationNode(new Vector(1,0,0),b);
        joint_A = new RotationNode(new Vector(1,0,0),a);
        joint_Y = new RotationNode(new Vector(0,0,1),y);


        CylinderNode base = new CylinderNode(1,0.1,50);
        addChild(base);

        //joint1
        SphereNode joint1 = new SphereNode(0.2,16);
        TranslationNode joint1_translation = new TranslationNode(new Vector(0,0,0.1));
        joint1_translation.addChild(joint1);


        joint_B.addChild(joint1_translation);


        joint_Y.addChild(joint_B);

        //arm1
        CylinderNode arm1 = new CylinderNode(0.15,arm_len,16);
        TranslationNode arm1_translation = new TranslationNode(new Vector(0,0,arm_len));
        arm1_translation.addChild(arm1);
        joint1_translation.addChild(arm1_translation);


        //joint2
        SphereNode joint2 = new SphereNode(0.2,16);
        TranslationNode joint2_translation = new TranslationNode(new Vector(0,0,arm_len*2));
        joint2_translation.addChild(joint2);
        joint1_translation.addChild(joint2_translation);



        //arm2
        CylinderNode arm2 = new CylinderNode(0.15,arm_len,16);
        TranslationNode arm2_translation = new TranslationNode(new Vector(0,0,arm_len));
        arm2_translation.addChild(arm2);

        joint_A.addChild(arm2_translation);
        joint2_translation.addChild(joint_A);


        // hand
//        SphereNode hand = new SphereNode(0.2,16);
//        TranslationNode hand_translation = new TranslationNode(new Vector(0,0,arm_len*2));
//        hand_translation.addChild(hand);
//        joint_A.addChild(hand_translation);


        addChild(joint_Y);
    }
}
