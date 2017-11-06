package engine.shader.textShader;

import engine.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class TextShader extends Shader {

    private final static String vertexShaderFile = "src/engine/shader/textShader/vertexShader.glsl";
    private final static String fragmentShaderFile = "src/engine/shader/textShader/fragmentShader.glsl";
    private int location_transformationMatrix;
    private int location_color;
    private int location_charOffset;

    public TextShader() {
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
        location_charOffset = super.getUniformLocation("charOffset");
        location_color = super.getUniformLocation("text_Color");
    }


    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadCharOffset(Vector4f offset) {
        super.loadVector(location_charOffset, offset);
    }

    public void loadColor(Vector4f color) {
        super.loadVector(location_color, color);
    }
}
