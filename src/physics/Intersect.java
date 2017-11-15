package physics;

import org.joml.Vector3f;

public class Intersect {

    private float tolerance;

    public Intersect(float tolerance) {
        this.tolerance = tolerance;
    }

    public Intersect() {
        this.tolerance = 0;
    }


    /**
     * Determine if two cubes intersect
     * @param c1p Position of Cube1
     * @param c1s Size of Cube1
     * @param c2p Position of Cube2
     * @param c2s Size of Cube2
     * @return true, if collision
     */
    public boolean CubeCube(Vector3f c1p, Vector3f c1s, Vector3f c2p, Vector3f c2s) {
        return (c1p.x <= c2p.x + c2s.x && c1p.x + c1s.x >= c2p.x) &&
                (c1p.y >= c2p.y + c2s.y && c1p.y + c1s.y <= c2p.y) &&
                (c1p.z <= c2p.z + c2s.z && c1p.z + c1s.z >= c2p.z);
    }

}
