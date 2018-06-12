package engine.model;

import engine.model.Model;
import engine.model.animation.Joint;
import org.joml.Matrix4f;

import java.util.List;

public class AnimatedModel extends Model {
    private List<Joint> joints;
    private Joint rootJoint;

    public AnimatedModel(Model model, List<Joint> joints, Joint rootJoint) {
        super(model);
        this.joints = joints;
        this.rootJoint = rootJoint;
    }


    public Matrix4f[] getJointTransforms() {
        Matrix4f[] transforms = new Matrix4f[this.joints.size()];
        for (int i = 0; i < transforms.length; i++) {
            transforms[i] = this.joints.get(i).getTransform();
        }
        return transforms;
    }

    public Joint getRootJoint() {
        return this.rootJoint;
    }
}
