package engine.entity;

import engine.model.AnimatedModel;
import engine.model.animation.Animator;

public class AnimatedEntity extends Entity {

    private Animator animator;

    public AnimatedEntity() {
        super();
    }

    public void update() {
        this.animator.update(((AnimatedModel) this.getModel().getModel()).getRootJoint());
    }

}
