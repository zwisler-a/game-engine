package physics;

import org.joml.Vector3f;

public class HitBox {
    private Vector3f offset;
    private Vector3f size;


    public HitBox(Vector3f offset, Vector3f size) {
        this.offset = offset;
        this.size = size;
    }

    public Vector3f getSize() {
        return size;
    }

    public void setSize(Vector3f size) {
        this.size = size;
    }

    public Vector3f getOffset() {
        return offset;
    }

    public void setOffset(Vector3f offset) {
        this.offset = offset;
    }
}
