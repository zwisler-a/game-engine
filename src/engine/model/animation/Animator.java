package engine.model.animation;

import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animator {

    private Animation currentAnimation;
    private float animationTime = 0;

    public boolean isAnimatring(){
        return this.currentAnimation != null;
    }


    public void doAnimation(Animation animation) {
        this.currentAnimation = animation;
        this.animationTime = 0;
    }

    public void update(List<Joint> joints, Joint rootJoint, double dt) {
        if (currentAnimation == null) {
            return;
        }
        increaseAnimationTime(dt);
        Map<String, Matrix4f> currentPose = calculateCurrentAnimationPose(joints);
        applyPoseToJoints(currentPose, rootJoint, new Matrix4f());
    }

    private void increaseAnimationTime(double time) {
        animationTime += time * this.currentAnimation.getAnimationSpeed();
        if (animationTime > currentAnimation.getLength()) {
            this.animationTime %= currentAnimation.getLength();
        }
    }

    private Map<String, Matrix4f> calculateCurrentAnimationPose(List<Joint> joints) {
        Map<String, Matrix4f> pose = new HashMap<>();
        for (Joint j : joints) {
            pose.put(j.getName(), calculateCurrentAnimationJointPose(j));
        }
        return pose;
    }

    private Matrix4f calculateCurrentAnimationJointPose(Joint j) {
        KeyFrame[] frames = getPreviousAndNextFrames(j);
        float progression = calculateProgression(frames[0], frames[1]);
        return interpolatePoses(frames[0], frames[1], progression);
    }

    private void applyPoseToJoints(Map<String, Matrix4f> currentPose, Joint joint, Matrix4f parentTransform) {
        Matrix4f currentLocalTransform = currentPose.get(joint.getName());
        Matrix4f currentTransform = parentTransform.mul(currentLocalTransform, new Matrix4f());
        for (Joint childJoint : joint.getChildren()) {
            applyPoseToJoints(currentPose, childJoint, currentTransform);
        }
        currentTransform.mul(joint.getInverseBindTransform());
        joint.setTransform(currentTransform);
    }

    private KeyFrame[] getPreviousAndNextFrames(Joint joint) {
        KeyFrame[] allFrames = currentAnimation.getKeyFrames(joint);
        KeyFrame previousFrame = allFrames[0];
        KeyFrame nextFrame = allFrames[0];
        for (int i = 1; i < allFrames.length; i++) {
            nextFrame = allFrames[i];
            if (nextFrame.getTimeStamp() > animationTime) {
                break;
            }
            previousFrame = allFrames[i];
        }
        return new KeyFrame[]{previousFrame, nextFrame};
    }

    private float calculateProgression(KeyFrame previousFrame, KeyFrame nextFrame) {
        float totalTime = nextFrame.getTimeStamp() - previousFrame.getTimeStamp();
        float currentTime = animationTime - previousFrame.getTimeStamp();
        return currentTime / totalTime;
    }

    private Matrix4f interpolatePoses(KeyFrame previousFrame, KeyFrame nextFrame, float progression) {
        JointTransform previousTransform = previousFrame.getJointKeyFrames();
        JointTransform nextTransform = nextFrame.getJointKeyFrames();
        return JointTransform.interpolate(previousTransform, nextTransform, progression).getLocalTransform();

    }

}
