package engine.model.collada;


import engine.model.animation.*;
import org.joml.Matrix4f;
import org.joml.Quaterniond;
import org.joml.Vector3f;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;

public class ColladaAnimationLoader {

    private static final Matrix4f CORRECTION =
            new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));

    private static XPath xpath = XPathFactory.newInstance().newXPath();

    private static float maxTimestamp = 0;

    public static Animation readAnimation(NodeList doc, Joint rootJoint) throws Exception {

        NodeList animations = (NodeList) xpath.evaluate("//library_animations/animation", doc, XPathConstants.NODESET);

        HashMap<String, KeyFrame[]> animationInfo = new HashMap<>();
        maxTimestamp = 0;
        for (int i = 0; i < animations.getLength(); i++) {
            String jointName = readAnimatedJointName(animations.item(i));
            animationInfo.put(
                    jointName,
                    readJointAnimation(animations.item(i), rootJoint.getName().equals(jointName))
            );
        }

        return new Animation(maxTimestamp, animationInfo);
    }

    private static KeyFrame[] readJointAnimation(Node animation, boolean isRoot) throws Exception {
        String timestampsId = ((Node) xpath.evaluate("sampler/input[@semantic=\"INPUT\"]", animation, XPathConstants.NODE))
                .getAttributes().getNamedItem("source").getTextContent();
        String matrixId = ((Node) xpath.evaluate("sampler/input[@semantic=\"OUTPUT\"]", animation, XPathConstants.NODE))
                .getAttributes().getNamedItem("source").getTextContent();

        float[] timestamps = ColladaSourceLoader.readFloatArraySource((NodeList) animation, timestampsId);
        float[] matrixValues = ColladaSourceLoader.readFloatArraySource((NodeList) animation, matrixId);

        KeyFrame[] keyFrames = new KeyFrame[timestamps.length];

        for (int i = 0; i < timestamps.length * 16; i += 16) {
            Matrix4f transform = new Matrix4f(
                    matrixValues[i], matrixValues[i + 1], matrixValues[i + 2], matrixValues[i + 3],
                    matrixValues[i + 4], matrixValues[i + 5], matrixValues[i + 6], matrixValues[i + 7],
                    matrixValues[i + 8], matrixValues[i + 9], matrixValues[i + 10], matrixValues[i + 11],
                    matrixValues[i + 12], matrixValues[i + 13], matrixValues[i + 14], matrixValues[i + 15]
            );
            transform.transpose();
            if (isRoot) {
                CORRECTION.mul(transform, transform);
            }
            keyFrames[i / 16] = new KeyFrame(timestamps[i / 16], createTransform(transform));
            if (maxTimestamp < timestamps[i / 16]) {
                maxTimestamp = timestamps[i / 16];
            }
        }
        return keyFrames;
    }

    private static String readAnimatedJointName(Node node) throws XPathExpressionException {
        return ((Node) xpath.evaluate("channel", node, XPathConstants.NODE))
                .getAttributes().getNamedItem("target").getTextContent().split("/")[0];
    }


    private static JointTransform createTransform(Matrix4f mat) {
        Vector3f translation = new Vector3f(mat.m30(), mat.m31(), mat.m32());
        Quaternion rotation = Quaternion.fromMatrix(mat);
        return new JointTransform(translation, rotation);
    }
}
