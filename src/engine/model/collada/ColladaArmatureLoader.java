package engine.model.collada;

import engine.model.animation.Joint;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;

public class ColladaArmatureLoader {

    private static final Matrix4f CORRECTION =
            new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));

    private final static int MAX_WEIGHTS = 3;
    private static XPath xpath = XPathFactory.newInstance().newXPath();

    public static Skeleton readArmature(NodeList doc) throws Exception {
        Skeleton skeleton = new Skeleton();

        NodeList controllers = (NodeList) xpath.evaluate("//library_controllers/controller", doc, XPathConstants.NODESET);
        Node controller = controllers.item(0);

        // Read joints and Hierarchy

        List<Joint> joints = readJoints(controller, skeleton);
        skeleton.setJoints(joints);
        readWeightData(controller, skeleton);
        return skeleton;
    }


    /**
     * Reads Joints with id and Name
     *
     * @param controller
     * @return
     * @throws XPathExpressionException
     */
    private static List<Joint> readJoints(Node controller, Skeleton skeleton) throws Exception {
        Node jointsNode = (Node) xpath.evaluate("skin/joints", controller, XPathConstants.NODE);
        String jointsSourceId = ((Node) xpath.evaluate("input[@semantic='JOINT']", jointsNode, XPathConstants.NODE))
                .getAttributes().getNamedItem("source").getTextContent();

        String invBindMatrixId = ((Node) xpath.evaluate("input[@semantic='INV_BIND_MATRIX']", jointsNode, XPathConstants.NODE))
                .getAttributes().getNamedItem("source").getTextContent();

        String armatureName = controller.getAttributes().getNamedItem("name").getTextContent();

        String[] jointNames = ColladaSourceLoader.readNameArraySource((NodeList) controller, jointsSourceId);
        List<Joint> joints = new ArrayList<>();
        for (int i = 0; i < jointNames.length; i++) {
            joints.add(new Joint(i, jointNames[i]));
        }
        Joint root = readJointNodeHierarchy(joints, (Node) xpath.evaluate("//visual_scene/node[@id='" + armatureName + "']/node", controller, XPathConstants.NODE));
        skeleton.setRootJoint(root);
        readInverseBindMatrix(controller, joints, invBindMatrixId);
        skeleton.setJoints(joints);
        return joints;
    }

    private static void readInverseBindMatrix(Node controller, List<Joint> joints, String invBindMatrixId) throws Exception {
        float[] fv = ColladaSourceLoader.readFloatArraySource((NodeList) controller, invBindMatrixId);
        for (int i = 0; i < joints.size() * 16; i += 16) {
            Matrix4f invBindMatrix = new Matrix4f(
                    fv[i], fv[i + 1], fv[i + 2], fv[i + 3],
                    fv[i + 4], fv[i + 5], fv[i + 6], fv[i + 7],
                    fv[i + 8], fv[i + 9], fv[i + 10], fv[i + 11],
                    fv[i + 12], fv[i + 13], fv[i + 14], fv[i + 15]
            );
            invBindMatrix.transpose();
            invBindMatrix.invert();
            CORRECTION.mul(invBindMatrix, invBindMatrix);
            invBindMatrix.invert();
            joints.get(i / 16).setInverseBindTransform(invBindMatrix);
        }
    }

    /**
     * Goes through the Hierarchy and sets the Child elements of the Joints
     *
     * @param joints
     * @param node
     * @return
     * @throws XPathExpressionException
     */
    private static Joint readJointNodeHierarchy(List<Joint> joints, Node node) throws XPathExpressionException {
        NodeList childs = (NodeList) xpath.evaluate("node", node, XPathConstants.NODESET);
        Joint parent = getJointByName(joints, node.getAttributes().getNamedItem("id").getTextContent());
        for (int i = 0; i < childs.getLength(); i++) {
            Node child = childs.item(i);
            parent.getChildren().add(getJointByName(joints, child.getAttributes().getNamedItem("id").getTextContent()));
            readJointNodeHierarchy(joints, child);
        }
        return parent;
    }

    /**
     * Reads the weight data and sets it in the skeleton. It creates two array (weights and jointIds)
     * It will be MAX_WEIGHTS for each vertex long. It normalizes the weight to be a total of 1 for each vertex.
     *
     * @param skeleton
     * @throws Exception
     */
    private static void readWeightData(Node controller, Skeleton skeleton) throws Exception {
        Node node = (Node) xpath.evaluate("skin/vertex_weights", controller, XPathConstants.NODE);
        String weightId = ((Node) xpath.evaluate("//input[@semantic='WEIGHT']", node, XPathConstants.NODE))
                .getAttributes().getNamedItem("source").getTextContent();
        int vertexSize = Integer.parseInt(node.getAttributes().getNamedItem("count").getTextContent());
        int[] vcount = ColladaSourceLoader.readIntArraySource((NodeList) node, "vcount");
        int[] v = ColladaSourceLoader.readIntArraySource((NodeList) node, "v");
        float[] weights = ColladaSourceLoader.readFloatArraySource((NodeList) node, weightId);

        float[] processedWeights = new float[vertexSize * MAX_WEIGHTS];
        int[] processedJointIds = new int[vertexSize * MAX_WEIGHTS];
        int processedPointer = 0;
        int pointer = 0;
        for (int step : vcount) {
            float ratio = 0;
            int countedValues = 0;
            for (int i = 0; i < MAX_WEIGHTS; i++) {
                if (step > i) {
                    int jointId = v[pointer];
                    float weightValue = weights[v[pointer + 1]];
                    processedJointIds[processedPointer] = jointId;
                    processedWeights[processedPointer++] = weightValue;
                    ratio += weightValue;
                    pointer += 2;
                    countedValues++;
                } else {
                    processedJointIds[processedPointer] = 0;
                    processedWeights[processedPointer++] = 0;
                }
            }
            pointer += Math.max(0, (step - MAX_WEIGHTS)) * 2;
            ratio /= countedValues;
            // Normalize
            for (int i = 0; i < MAX_WEIGHTS; i++) {
                processedWeights[(processedPointer - (1 + i))] *= ratio;
            }
        }
        skeleton.setJointIds(processedJointIds);
        skeleton.setJointWights(processedWeights);
    }


    /**
     * Helper to get a joint by name
     *
     * @param joints
     * @param id
     * @return
     */
    private static Joint getJointByName(List<Joint> joints, String id) {
        for (Joint j : joints) {
            if (j.getName().equals(id)) {
                return j;
            }
        }
        throw new Error("Cant find Joint " + id + "!");
    }
}
