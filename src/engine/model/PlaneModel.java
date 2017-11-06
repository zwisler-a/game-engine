package engine.model;

import engine.model.Loader;
import engine.model.Model;

public class PlaneModel {

    private static float[] vertexBufferData = {
            -1, 0, -1,  // p1
            1, 0, -1,   // p2
            1, 0, 1,    // p3
            -1, 0, 1    // p4
    };

    private static int[] indiciesData = {
            2,1,0,
            3,2,0
    };

    private static float[] uvMappingData = {
            0, 1,
            1, 1,
            1, 0,
            0, 0
    };

    private static float[] normalData = {
            0, 1, 0,
            0, 1, 0,
            0, 1, 0,
            0, 1, 0
    };
    private static Model model;

    public static Model load() {
        if (model == null) {
            model = Loader.loadToVAO(vertexBufferData,indiciesData,uvMappingData,normalData);
        }
        return model;
    }
}
