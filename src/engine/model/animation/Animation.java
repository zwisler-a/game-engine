package engine.model.animation;

import java.util.HashMap;

public class Animation {

    private final float length;//in seconds
    private final HashMap<String,KeyFrame[]> keyFrames;
    private double animationSpeed = 0.01f;

    public Animation(float lengthInSeconds, HashMap<String,KeyFrame[]> frames) {
        this.keyFrames = frames;
        this.length = lengthInSeconds;
    }

    public float getLength() {
        return length;
    }

    public KeyFrame[] getKeyFrames(Joint joint) {
        return keyFrames.get(joint.getName());
    }

    public double getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(double animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
}