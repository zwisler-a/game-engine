package engine.model.collada;

import common.Logger.Logger;
import engine.entity.AnimatedEntity;
import engine.model.AnimatedModel;
import engine.model.Model;
import engine.model.animation.Animation;
import engine.model.store.ModelStore;
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
            Model modelStoreModel = ModelStore.getInstance().get(path);
            if (modelStoreModel != null) {
                Logger.debug("Retrieved model: " + path);
                if (modelStoreModel instanceof AnimatedModel) {
                    Logger.debug("Instanciate model: " + path);
                    return new AnimatedModel((AnimatedModel) modelStoreModel);
                }
                return modelStoreModel;
            }
            ModelStore.getInstance().get(path);

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
            Model boundModel = ColladaGeometriesLoader.readGeometry(document, mesh, model);
            ModelStore.getInstance().add(path, boundModel);
            return boundModel;
        } catch (Exception e) {
            Logger.error("Error while loading Collada file! " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static Animation loadAnimation(String path, AnimatedEntity model) {
        return loadAnimation(path, (AnimatedModel) model.getModel().getModel());
    }

    public static Animation loadAnimation(String path, AnimatedModel model) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            NodeList document = builder.parse(path).getChildNodes();


            return ColladaAnimationLoader.readAnimation(document, model.getRootJoint());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
