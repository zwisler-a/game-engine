package engine.model.animation;

public class Animation {
    private String name;
    private KeyFrame[] keyFrames;

    public Animation(String name, KeyFrame[] keyFrames) {
        this.name = name;
        this.keyFrames = keyFrames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KeyFrame[] getKeyFrames() {
        return keyFrames;
    }

    public void setKeyFrames(KeyFrame[] keyFrames) {
        this.keyFrames = keyFrames;
    }
}
