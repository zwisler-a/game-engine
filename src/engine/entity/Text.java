package engine.entity;

import common.Logger.Logger;
import engine.model.FontAtlas;
import engine.model.PlaneModel;
import engine.model.Texture;
import engine.renderer.framebuffer.FrameBuffer;
import engine.renderer.framebuffer.FrameBufferBuilder;
import engine.renderer.queued.QueuedRenderer;
import engine.shader.textShader.TextShader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.*;

public class Text {

    private final FontAtlas fontAtlas;
    private FrameBuffer fbo;
    private String text = "";
    private static TextShader shader;
    private float preferedSize = 1;
    private Vector4f color;

    public Text(FontAtlas fontAtlas) {
        this.fbo = new FrameBufferBuilder()
                .setDimensions(300, 300)
                .addTexture()
                .addDepthBuffer()
                .create();
        this.fbo.unbind();
        if (shader == null) {
            shader = new TextShader();
        }
        this.fontAtlas = fontAtlas;
        this.color = new Vector4f(0, 0, 0, 1);
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public void setText(String text) {
        if(!this.text.equals(text)){
            this.text = text;
            Logger.debug("Render: " + text);
            this.render();
        }
    }

    public Texture getTexture() {
        return this.fbo.getTexture();
    }

    public void render() {
        QueuedRenderer.add(() -> {
            this.preferedSize = this.fontAtlas.getSize(this.text);
            this.fbo.bind();

            float planeScale = 1f / this.text.length();

            shader.start();
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            shader.loadColor(this.color);

            GL30.glBindVertexArray(PlaneModel.load().getVaoId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, PlaneModel.load().getIndicesVBO());

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.fontAtlas.fontAtlasTexture.getTextureId());

            for (int i = 0; i < this.text.length(); i++) {
                shader.loadTransformationMatrix(this.createTransformationMatrix(new Vector2f(i * (planeScale * 2), 0), planeScale));
                shader.loadCharOffset(this.fontAtlas.getCharOffset(this.text.charAt(i)));
                GL11.glDrawElements(GL11.GL_TRIANGLES, PlaneModel.load().getIndiciesCount(), GL11.GL_UNSIGNED_INT, 0);
            }


            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL30.glBindVertexArray(0);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_CULL_FACE);
            shader.stop();
            this.fbo.unbind();
        });
    }

    private Matrix4f createTransformationMatrix(Vector2f position, float planeScale) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(new Vector3f(-1, 0, 0));
        matrix.translate(new Vector3f(position.x + planeScale, position.y, 0));
        matrix.rotate((float) Math.toRadians((double) 90), new Vector3f(1.0F, 0.0F, 0.0F));
        matrix.rotate((float) Math.toRadians((double) 0), new Vector3f(0.0F, 1.0F, 0.0F));
        matrix.rotate((float) Math.toRadians((double) 0), new Vector3f(0.0F, 0.0F, 1.0F));
        matrix.scale(new Vector3f(planeScale, 1, 1f));
        return matrix;
    }

    public FrameBuffer getFbo() {
        return this.fbo;
    }

    public float getPreferedSize() {
        return preferedSize;
    }
}
