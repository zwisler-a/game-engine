package engine.shader.guiShader;

import common.Maths;
import engine.entity.Camera;
import engine.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GuiShader extends Shader {

    private final static String vertexShaderFile = "src/engine/shader/guiShader/vertexShader.glsl";
    private final static String fragmentShaderFile = "src/engine/shader/guiShader/fragmentShader.glsl";
    private int location_viewMatrix;
    private int location_transformationMatrix;

    public GuiShader() {
        super(vertexShaderFile, fragmentShaderFile);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribut(0, "in_Position");
        super.bindAttribut(1, "in_texCoord");
    }

    @Override
    protected void getAllUniformLocations() {
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }


    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadViewMatrix(Camera c) {
        super.loadMatrix(location_viewMatrix, Camera.createViewMatrixWithoutPosition(c));
    }


}
