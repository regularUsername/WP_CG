package computergraphics.scenegraph;

import computergraphics.math.Matrix;
import computergraphics.math.Vector;

/**
 * Created by dimhof on 26.06.2017.
 */
public class RobotArm extends InnerNode {

    public void setA(double a) {
        this.a = a;
        joint_A.setAngle(a);
        target_translation.setTranslation(calculateTarget());
    }

    public void setB(double b) {
        this.b = b;
        joint_B.setAngle(90-b);
        target_translation.setTranslation(calculateTarget());
    }

    public void setY(double y) {
        this.y = y;
        joint_Y.setAngle(y);
        target_translation.setTranslation(calculateTarget());
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
    private final double arm_len2 = 1;
    private final double arm_len1 = 0.5;
    private final double arm_radius = 0.1;
    private final double joint_radius = 0.15;


    public Vector calculateTarget(){

        // achsen sind hier vertauscht gegenüber die aufgabenstellung kein plan wieso
        // y -> z
        // z -> x
        // x -> y
        Matrix ryy = Matrix.createRotationMatrix4(new Vector(0,0,1),Math.toRadians(y)); // R_y(gamma)
        Matrix rzb = Matrix.createRotationMatrix4(new Vector(1,0,0),Math.toRadians(180-b)); // R_z(beta)
        Matrix rza = Matrix.createRotationMatrix4(new Vector(1,0,0),Math.toRadians(a)); // R_z(alpha)
        Matrix tl1 = Matrix.createTranslationMatrix4(new Vector(0,arm_len1,0));
        Matrix tl2 = Matrix.createTranslationMatrix4(new Vector(0,arm_len2,0));


        Vector tmp = tl1.multiply(new Vector(0,0,0,1));
        tmp = rza.multiply(tmp);
        tmp = tl2.multiply(tmp);
        tmp = rzb.multiply(tmp);
        tmp = ryy.multiply(tmp);

        return tmp;
    }

    private TranslationNode target_translation;

    public RobotArm() {

        a = 10;
        b = 90;
        y = 0;
        joint_B = new RotationNode(new Vector(1,0,0),90-b);
        joint_A = new RotationNode(new Vector(1,0,0),a);
        joint_Y = new RotationNode(new Vector(0,0,1),y);


        // darstellen der zielposition
        CrossNode target = new CrossNode(0.5,2,true);
        target_translation = new TranslationNode(calculateTarget());
        target_translation.addChild(target);

        // hand
//        SphereNode hand = new SphereNode(joint_radius,16);
//        target_translation.addChild(hand);

        addChild(target_translation);


        CylinderNode base = new CylinderNode(0.5,0.1,50);
        addChild(base);

        //joint1
        SphereNode joint1 = new SphereNode(joint_radius,16);
        TranslationNode joint1_translation = new TranslationNode(new Vector(0,0,0.1));
        joint1_translation.addChild(joint1);


        joint_B.addChild(joint1_translation);


        joint_Y.addChild(joint_B);

        //arm1
        CylinderNode arm1 = new CylinderNode(arm_radius,arm_len2/2,16);
        TranslationNode arm1_translation = new TranslationNode(new Vector(0,0,arm_len2/2));
        arm1_translation.addChild(arm1);
        joint1_translation.addChild(arm1_translation);


        //joint2
        SphereNode joint2 = new SphereNode(joint_radius,16);
        TranslationNode joint2_translation = new TranslationNode(new Vector(0,0,arm_len2));
        joint2_translation.addChild(joint2);
        joint1_translation.addChild(joint2_translation);


        //arm2
        CylinderNode arm2 = new CylinderNode(arm_radius,arm_len1/2,16);
        TranslationNode arm2_translation = new TranslationNode(new Vector(0,0,arm_len1/2));
        arm2_translation.addChild(arm2);

        joint_A.addChild(arm2_translation);
        joint2_translation.addChild(joint_A);


        addChild(joint_Y);
    }
}
