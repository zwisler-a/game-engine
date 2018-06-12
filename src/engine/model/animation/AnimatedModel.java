package engine.model.animation;

import engine.model.Model;
import org.joml.Matrix4f;

import java.util.List;

public class AnimatedModel extends Model {
    private List<Joint> joints;

    public AnimatedModel(Model model, List<Joint> joints) {
        super(model);
        this.joints = joints;
    }


    public Matrix4f[] getJointTransforms() {
        Matrix4f[] transforms = new Matrix4f[this.joints.size()];
        for (int i = 0; i < transforms.length; i++) {
            transforms[i] = this.joints.get(i).getTransform();
        }
        return transforms;
    }
}
