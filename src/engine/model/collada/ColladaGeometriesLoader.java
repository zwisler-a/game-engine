package engine.model.collada;

import engine.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class ColladaGeometriesLoader {
    private static XPath xpath = XPathFactory.newInstance().newXPath();

    public static Model readGeometry(NodeList doc, String name, UnboundModel model) throws Exception {
        Node polylist = (Node) xpath.evaluate("//geometry[@id='" + name + "']/mesh/polylist", doc, XPathConstants.NODE);

        NodeList inputs = (NodeList) xpath.evaluate("//geometry[@id='" + name + "']/mesh/polylist/input", polylist, XPathConstants.NODESET);


        int vertexPos = -1;
        int normalPos = -1;
        int uvPos = -1;

        for (int i = 0; i < inputs.getLength(); i++) {
            NamedNodeMap attribs = inputs.item(i).getAttributes();
            String type = attribs.getNamedItem("semantic").getTextContent();
            switch (type) {
                case "VERTEX":
                    String id = attribs.getNamedItem("source").getTextContent();
                    id = id.charAt(0) == '#' ? id.substring(1) : id;
                    NamedNodeMap source = ((Node) xpath.evaluate("//*[@id='" + id + "']/input",
                            doc, XPathConstants.NODE)).getAttributes();
                    vertexPos = Integer.parseInt(attribs.getNamedItem("offset").getTextContent());
                    model.setVeticies(ColladaSourceLoader.readFloatArraySource(doc, source.getNamedItem("source").getTextContent()));
                    break;
                case "NORMAL":
                    model.setNormals(ColladaSourceLoader.readFloatArraySource(doc, attribs.getNamedItem("source").getTextContent()));
                    normalPos = Integer.parseInt(attribs.getNamedItem("offset").getTextContent());
                    break;
                case "TEXCOORD":
                    model.setUvCoords(ColladaSourceLoader.readFloatArraySource(doc, attribs.getNamedItem("source").getTextContent()));
                    uvPos = Integer.parseInt(attribs.getNamedItem("offset").getTextContent());
                    break;
            }
        }

        int indicieCount = Integer.parseInt(polylist.getAttributes().getNamedItem("count").getTextContent());

        int[] order = ColladaSourceLoader.readIntArraySource(doc, "//*[@id='" + name + "']/mesh/polylist/p");
        model.order(indicieCount, order, vertexPos, normalPos, uvPos, inputs.getLength());

        return model.load();
    }

}
