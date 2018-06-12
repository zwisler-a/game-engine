package engine.model.collada;

import engine.model.animation.Joint;

import java.util.List;

public class Skeleton {
    private List<Joint> joints;
    private int[] jointIds;
    private float[] jointWights;
    private Joint rootJoint;

    public List<Joint> getJoints() {
        return joints;
    }

    public void setJoints(List<Joint> joints) {
        this.joints = joints;
    }

    public int[] getJointIds() {
        return jointIds;
    }

    public void setJointIds(int[] jointIds) {
        this.jointIds = jointIds;
    }

    public float[] getJointWights() {
        return jointWights;
    }

    public void setJointWights(float[] jointWights) {
        this.jointWights = jointWights;
    }

    public Joint getRootJoint() {
        return rootJoint;
    }

    public void setRootJoint(Joint rootJoint) {
        this.rootJoint = rootJoint;
    }
}
