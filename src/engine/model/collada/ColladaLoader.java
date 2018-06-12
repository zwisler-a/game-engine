package engine.model.collada;

import engine.model.Model;
import engine.model.animation.AnimatedModel;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class ColladaLoader {

    private static XPath xpath = XPathFactory.newInstance().newXPath();


    public static Model loadColladaFile(String path) {
        return loadColladaFile(path, false);
    }

    public static AnimatedModel loadColladaFileAnimated(String path) {
        return (AnimatedModel) loadColladaFile(path, true);
    }

    public static Model loadColladaFile(String path, boolean withSkeleton) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            NodeList document = builder.parse(path).getChildNodes();

            NodeList geometrys = (NodeList) xpath.evaluate("//library_geometries/geometry", document, XPathConstants.NODESET);
            String mesh = geometrys.item(0).getAttributes().getNamedItem("id").getTextContent();

            UnboundModel model;
            if (withSkeleton) {
                model = new UnboundAnimatedModel(ColladaArmatureLoader.readArmature(document));
            } else {
                model = new UnboundModel();
            }
            return ColladaGeometriesLoader.readGeometry(document, mesh, model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
