package engine.model.collada;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class ColladaSourceLoader {
    private static XPath xpath = XPathFactory.newInstance().newXPath();

    public static float[] readFloatArraySource(NodeList doc, String id) throws Exception {
        if (id.charAt(0) == '#') {
            id = id.substring(1);
        }
        Node source = (Node) xpath.evaluate("//*[@id='" + id + "']/float_array", doc, XPathConstants.NODE);
        if (source == null) {
            throw new Exception("Source " + id + " does not exist!");
        }
        String[] floatStrings = source.getTextContent().split(" ");
        float[] floats = new float[floatStrings.length];
        for (int i = 0; i < floatStrings.length; i++) {
            floats[i] = Float.parseFloat(floatStrings[i]);
        }
        return floats;
    }

    public static int[] readIntArraySource(NodeList doc, String xpath) throws Exception {
        Node source = (Node) ColladaSourceLoader.xpath.evaluate(xpath, doc, XPathConstants.NODE);
        if (source == null) {
            throw new Exception("Source " + xpath + " does not exist!");
        }
        String[] intStrings = source.getTextContent().split(" ");
        int[] ints = new int[intStrings.length];
        for (int i = 0; i < intStrings.length; i++) {
            ints[i] = Integer.parseInt(intStrings[i]);
        }
        return ints;
    }

    public static String[] readNameArraySource(NodeList doc, String id) throws XPathExpressionException {
        if (id.charAt(0) == '#') {
            id = id.substring(1);
        }
        Node source = (Node) xpath.evaluate("//*[@id='" + id + "']/Name_array", doc, XPathConstants.NODE);
        String[] names = source.getTextContent().split(" ");
        return names;
    }

}
