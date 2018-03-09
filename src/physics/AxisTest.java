package physics;

import org.joml.Vector3f;

public class AxisTest {
    private Vector3f axis;
    private float depth;

    public AxisTest(Vector3f axis, float depth) {
        this.axis = axis;
        this.depth = depth;
    }

    public float getDepth() {
        return depth;
    }

    public Vector3f getAxis() {
        return axis;
    }
}
