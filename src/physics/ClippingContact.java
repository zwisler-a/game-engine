package physics;

import org.joml.Vector3f;
import physics.shapes.Body;

import java.util.ArrayList;

public class ClippingContact {

    public static ArrayList<Vector3f> getContactPoints(Body p1, Body p2, Vector3f contactNormal) {
        // http://www.dyn4j.org/2011/11/contact-points-using-clipping/comment-page-1/#cpg-curve

        Vector3f invContactNormal = new Vector3f(-contactNormal.x, -contactNormal.y, -contactNormal.z);

        ArrayList<Vector3f> refFace = p2.getFaceOfNormal(contactNormal);
        ArrayList<Vector3f> incFace = p1.getFaceOfNormal(new Vector3f(-contactNormal.x, -contactNormal.y, -contactNormal.z));
        /*ArrayList<Vector3f> incFace = new ArrayList<>();
        Vector3f[] points = p1.getPoints();
        for (Vector3f p : points) {
            incFace.add(p);
        }*/

        Vector3f[] pointsOfRef = new Vector3f[4];
        Vector3f[] clippingNormals = new Vector3f[4];

        // Generate clipping planes
        refFace = orderVertecies(refFace);
        for (int i = 0; i < refFace.size(); i++) {
            int next;
            if (i == refFace.size() - 1) {
                next = 0;
            } else {
                next = i + 1;
            }
            clippingNormals[i] = new Vector3f(refFace.get(i)).sub(refFace.get(next)).cross(contactNormal).normalize();
            pointsOfRef[i] = refFace.get(i);
        }

        Vector3f refFaceNormal = new Vector3f(refFace.get(0));
        refFaceNormal.sub(refFace.get(2));
        Vector3f refFaceNormalPlane = new Vector3f(refFace.get(1));
        refFaceNormalPlane.sub(refFace.get(3));
        refFaceNormal.cross(refFaceNormalPlane).normalize().mul(-1);

        ArrayList<Vector3f> mergedClippingPoints = new ArrayList<>();
        ArrayList<Vector3f> clippingPoints = orderVertecies(incFace);

        // Do the clipping (clip incFace)
        for (int norm = 0; norm < clippingNormals.length; norm++) {
            sutherlandHodgmanClipping(clippingPoints, clippingNormals[norm], pointsOfRef[norm]);
        }
        sutherlandHodgmanClipping(clippingPoints, refFaceNormal, refFace.get(0));
        mergedClippingPoints.addAll(clippingPoints);


        return mergedClippingPoints;
    }

    private static void sutherlandHodgmanClipping(ArrayList<Vector3f> clippingPoints, Vector3f clippingNormal, Vector3f clippingRef) {
        ArrayList<Vector3f> currentCPoints = new ArrayList<>(clippingPoints);
        clippingPoints.clear();

        for (int i = 0; i < currentCPoints.size(); i++) {
            Vector3f v1 = currentCPoints.get(i);
            Vector3f v2 = currentCPoints.get(i + 1 < currentCPoints.size() ? i + 1 : 0);
            // normal * (p1-p2)
            float dot1 = clippingNormal.dot(new Vector3f(v1).sub(clippingRef));
            float dot2 = clippingNormal.dot(new Vector3f(v2).sub(clippingRef));

            // 3 cases - both in, first in, second in(both out -> nothing happens)
            // both in
            if (dot1 >= 0 && dot2 >= 0) {
                // both verts can be used and next one can get skipped
                clippingPoints.add(v1);
                clippingPoints.add(v2);
            }
            // first in
            if (dot1 >= 0 && dot2 < 0) {
                // first can be used, second has to be determined (adds a vert)
                clippingPoints.add(v1);
                clippingPoints.add(intersection(v1, v2, dot1, dot2));
            }
            // second in
            if (dot1 < 0 && dot2 >= 0) {
                // second can be used, first has to be determined (adds a vert)
                clippingPoints.add(intersection(v2, v1, dot2, dot1));
                clippingPoints.add(v2);
            }
        }
    }

    private static Vector3f intersection(Vector3f v1, Vector3f v2, float d1, float d2) {

        Vector3f e = new Vector3f(v2).sub(v1);
        e.mul(d1 / (d1 - d2));
        e.add(v1);

        return e;
    }


    private static ArrayList<Vector3f> orderVertecies(ArrayList<Vector3f> verts) {
        ArrayList<Vector3f> vertsOrdered = new ArrayList<>();
        Vector3f closest = verts.get(0);
        vertsOrdered.add(closest);
        verts.remove(closest);
        do {
            closest = getClosest(closest, verts);
            verts.remove(closest);
            vertsOrdered.add(closest);
        } while (verts.size() > 0);
        return vertsOrdered;
    }

    private static Vector3f getClosest(Vector3f from, ArrayList<Vector3f> verts) {
        Vector3f minVec = verts.get(0);
        float min = getDistance(from, minVec);
        for (Vector3f vert : verts) {
            if (!from.equals(vert)) {
                float distance = getDistance(from, vert);
                if (distance < min) {
                    min = distance;
                    minVec = vert;
                }
            }
        }
        return minVec;
    }

    private static float getDistance(Vector3f from, Vector3f to) {
        Vector3f distance = new Vector3f(from).sub(to);
        return (float) Math.sqrt(Math.pow(distance.x, 2) + Math.pow(distance.y, 2) + Math.pow(distance.z, 2));
    }


}