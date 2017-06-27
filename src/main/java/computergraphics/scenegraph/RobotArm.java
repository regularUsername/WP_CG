package computergraphics.scenegraph;

import computergraphics.math.Matrix;
import computergraphics.math.Vector;

/**
 * Created by dimhof on 26.06.2017.
 */
public class RobotArm extends InnerNode {

    private double alpha;
    private double beta;
    private double gamma;
    private RotationNode jointBeta;
    private RotationNode jointAlpha;
    private RotationNode jointGamma;
    private final double arm_len2 = 1;
    private final double arm_len1 = 0.5;
    private final double arm_radius = 0.1;
    private final double joint_radius = 0.2;

    private TranslationNode target_translation;

    public void setAlpha(double alpha) {
        this.alpha = alpha;
        jointAlpha.setAngle(alpha);
//        target_translation.setTranslation(getFingertipPos());
    }

    public void setBeta(double beta) {
        this.beta = beta;
        jointBeta.setAngle(90 - beta);
//        target_translation.setTranslation(getFingertipPos());
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
        jointGamma.setAngle(gamma);
//        target_translation.setTranslation(getFingertipPos());
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getGamma() {
        return gamma;
    }



    public Vector getFingertipPos() {

        // achsen sind hier vertauscht gegenüber die aufgabenstellung kein plan wieso
        // y -> z
        // z -> x
        // x -> y
        Matrix ryy = Matrix.createRotationMatrix4(new Vector(0, 0, 1), Math.toRadians(gamma)); // R_y(gamma)
        Matrix rzb = Matrix.createRotationMatrix4(new Vector(1, 0, 0), Math.toRadians(180 - beta)); // R_z(beta)
        Matrix rza = Matrix.createRotationMatrix4(new Vector(1, 0, 0), Math.toRadians(alpha)); // R_z(alpha)
        Matrix tl1 = Matrix.createTranslationMatrix4(new Vector(0, arm_len1, 0));
        Matrix tl2 = Matrix.createTranslationMatrix4(new Vector(0, arm_len2, 0));


        Vector tmp = tl1.multiply(new Vector(0, 0, 0, 1));
        tmp = rza.multiply(tmp);
        tmp = tl2.multiply(tmp);
        tmp = rzb.multiply(tmp);
        tmp = ryy.multiply(tmp);

        return tmp;
    }

    public void setTargetIK(Vector targetPos) {

        target_translation.setTranslation(targetPos);
        Vector currentPos = getFingertipPos();

        Vector tmp = currentPos.xyz().subtract(targetPos);
        double distance = tmp.multiply(tmp);

        System.out.println("Quadratic Distance: "+distance);

    }



    public RobotArm() {

        alpha = 10;
        beta = 90;
        gamma = 0;
        jointBeta = new RotationNode(new Vector(1, 0, 0), 90 - beta);
        jointAlpha = new RotationNode(new Vector(1, 0, 0), alpha);
        jointGamma = new RotationNode(new Vector(0, 0, 1), gamma);


        // darstellen der zielposition
        CrossNode target = new CrossNode(0.5, 2, true);
        target_translation = new TranslationNode(getFingertipPos());
        target_translation.addChild(target);

        // hand
//        SphereNode hand = new SphereNode(joint_radius,16);
//        target_translation.addChild(hand);

        addChild(target_translation);


        CylinderNode base = new CylinderNode(0.5, 0.1, 50);
        addChild(base);

        //joint1
        SphereNode joint1 = new SphereNode(joint_radius, 16);
        TranslationNode joint1_translation = new TranslationNode(new Vector(0, 0, 0));
        joint1_translation.addChild(joint1);


        jointBeta.addChild(joint1_translation);


        jointGamma.addChild(jointBeta);

        //arm1
        CylinderNode arm1 = new CylinderNode(arm_radius, arm_len2 / 2, 16);
        TranslationNode arm1_translation = new TranslationNode(new Vector(0, 0, arm_len2 / 2));
        arm1_translation.addChild(arm1);
        joint1_translation.addChild(arm1_translation);


        //joint2
        SphereNode joint2 = new SphereNode(joint_radius, 16);
        TranslationNode joint2_translation = new TranslationNode(new Vector(0, 0, arm_len2));
        joint2_translation.addChild(joint2);
        joint1_translation.addChild(joint2_translation);


        //arm2
        CylinderNode arm2 = new CylinderNode(arm_radius, arm_len1 / 2, 16);
        TranslationNode arm2_translation = new TranslationNode(new Vector(0, 0, arm_len1 / 2));
        arm2_translation.addChild(arm2);

        jointAlpha.addChild(arm2_translation);
        joint2_translation.addChild(jointAlpha);


        addChild(jointGamma);
    }
}
