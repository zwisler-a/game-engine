package engine.model.animation;

import java.util.Map;

public class KeyFrame {

    private final float timeStamp;
    private final JointTransform pose;


    public KeyFrame(float timeStamp, JointTransform jointKeyFrames) {
        this.timeStamp = timeStamp;
        this.pose = jointKeyFrames;
    }

    protected float getTimeStamp() {
        return timeStamp;
    }

    protected JointTransform getJointKeyFrames() {
        return pose;
    }
}
