package physics;

import org.joml.Vector3f;
import physics.shapes.Body;

import java.util.ArrayList;
import java.util.LinkedList;

public class Collision {
    private final Body poly1;
    private final Body poly2;
    private LinkedList<AxisTest> axes;
    private ArrayList<Vector3f> contactPoints;


    public Collision(Body p1, Body p2) {
        this.poly1 = p1;
        this.poly2 = p2;

        this.axes = new LinkedList<>();
    }

    public void addAxis(Vector3f axis, float depth) {
        this.axes.add(new AxisTest(axis, depth));
    }

    public AxisTest getMinimalDepth() {
        AxisTest min = this.axes.getFirst();
        for (AxisTest axis : this.axes) {
            if (Math.abs(axis.getDepth()) < Math.abs(min.getDepth())) {
                min = axis;
            }
        }
        return min;
    }

    public Body getPoly1() {
        return poly1;
    }

    public Body getPoly2() {
        return poly2;
    }

    public ArrayList<Vector3f> getContactPoints() {
        return contactPoints;
    }

    public void addContacts(ArrayList<Vector3f> contactPoints) {
        this.contactPoints = contactPoints;
    }
}
