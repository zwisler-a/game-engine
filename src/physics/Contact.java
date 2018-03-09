package physics;

import org.joml.Vector3f;

public class Contact {
    private Vector3f point;
    private float depth;

    public Contact(Vector3f point, float depth) {
        this.point = point;
        this.depth = depth;
    }
}
