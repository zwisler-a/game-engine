package engine.model.animation;

import engine.model.AnimatedModel;

public class Animator {

    private Animation currentAnimiation;
    private int animationTime = 0;


    public void doAnimation(Animation animation) {
        this.currentAnimiation = animation;
        this.animationTime = 0;
    }

    public void update(Joint rootJoint) {

    }

}
