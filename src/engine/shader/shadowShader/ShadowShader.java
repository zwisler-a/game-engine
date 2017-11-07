package engine.shader.shadowShader;

import engine.entity.Camera;
import engine.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class ShadowShader extends Shader {

    private final static String vertexShaderFile = "src/engine/shader/shadowShader/vertexShader.glsl";
    private final static String fragmentShaderFile = "src/engine/shader/shadowShader/fragmentShader.glsl";
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public ShadowShader() {
        super(vertexShaderFile, fragmentShaderFile);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribut(0, "in_Position");
        super.bindAttribut(1, "in_TextureCoord");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }


    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, Camera.createViewMatrix(camera));
    }

}
