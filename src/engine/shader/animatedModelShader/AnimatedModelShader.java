package engine.shader.animatedModelShader;

import common.Logger.Logger;
import engine.shader.staticShader.StaticShader;
import org.joml.Matrix4f;

public class AnimatedModelShader extends StaticShader {

    private static int MAX_JOINTS = 50;

    private final static String vertexShaderFile = "src/engine/shader/animatedModelShader/vertexShader.glsl";
    private final static String fragmentShaderFile = "src/engine/shader/animatedModelShader/fragmentShader.glsl";

    private int[] jointTransforms;

    public AnimatedModelShader() {
        super(vertexShaderFile, fragmentShaderFile);
    }

    protected void getAllUniformLocations() {
        super.getAllUniformLocations();

        jointTransforms = new int[MAX_JOINTS];

        for (int i = 0; i < MAX_JOINTS; i++) {
            jointTransforms[i] = super.getUniformLocation("jointTransforms[" + i + "]");
        }

    }

    @Override
    protected void bindAttributes() {
        super.bindAttributes();
        super.bindAttribut(3, "in_jointIds");
        super.bindAttribut(4, "in_weights");
    }

    public void loadJointsMatrix(Matrix4f[] matricies) {
        if (matricies.length > MAX_JOINTS) {
            Logger.error("To many joints!");
        }
        for (int i = 0; i < MAX_JOINTS; i++) {
            if (i < matricies.length) {
                super.loadMatrix(jointTransforms[i], matricies[i]);
            } else {
                super.loadMatrix(jointTransforms[i], new Matrix4f());
            }
        }
    }

}
