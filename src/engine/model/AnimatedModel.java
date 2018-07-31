package engine.model;

import engine.model.animation.Joint;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class AnimatedModel extends Model {

    /**
     * Copies a Joints structure with its childrens linking to the copied joints
     * @param current root joint to start copying
     * @param copyParent null if start copying. Used for recursion
     * @param joints List of joint to add to
     * @return Copied current
     */
    private static Joint copyJoint(Joint current, Joint copyParent, List<Joint> joints) {
        Joint copy = new Joint(current);
        if (copyParent != null) {
            copyParent.getChildren().add(copy);
        }
        joints.add(copy);
        for (Joint joint : current.getChildren()) {
            copyJoint(joint, copy, joints);
        }
        return copy;
    }


    private List<Joint> joints;
    private Joint rootJoint;

    public AnimatedModel(Model model, List<Joint> joints, Joint rootJoint) {
        super(model);
        this.joints = joints;
        this.rootJoint = rootJoint;
    }

    public AnimatedModel(AnimatedModel model) {
        super(model);
        this.joints = new ArrayList<>();
        this.rootJoint = copyJoint(model.getRootJoint(), null, this.joints);
    }



    public Matrix4f[] getJointTransforms() {
        Matrix4f[] transforms = new Matrix4f[this.joints.size()];
        for (int i = 0; i < transforms.length; i++) {
            transforms[i] = this.joints.get(i).getTransform();
        }
        return transforms;
    }

    public List<Joint> getJoints() {
        return joints;
    }

    public Joint getRootJoint() {
        return this.rootJoint;
    }
}
