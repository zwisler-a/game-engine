package engine.entity;

import common.Logger.Logger;
import engine.model.AnimatedModel;
import engine.model.animation.Animator;

public class AnimatedEntity extends Entity implements Simulated {

    protected Animator animator;

    public AnimatedEntity() {
        super();
        this.animator = new Animator();
    }

    public void update(double dt) {
        AnimatedModel model = (AnimatedModel) this.getModel().getModel();
        this.animator.update(model.getJoints(), model.getRootJoint(), dt);
    }

    @Override
    public void simulate(double dt) {
        this.update(dt);
    }

    public Animator getAnimator() {
        return this.animator;
    }
}
