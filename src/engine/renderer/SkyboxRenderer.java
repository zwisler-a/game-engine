package engine.renderer;

import engine.entity.Camera;
import engine.model.CubeModel;
import engine.model.Model;
import engine.model.Texture;
import engine.model.loaders.TextureLoader;
import engine.shader.skyboxShader.SkyboxShader;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

public class SkyboxRenderer {

    private SkyboxShader shader;
    private Model skybox;
    public static Texture texture;
    public static int scale = 100;

    public SkyboxRenderer(Matrix4f projectionMatrix) {
        GL11.glCullFace(GL11.GL_BACK);
        this.shader = new SkyboxShader();
        this.shader.start();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.skybox = CubeModel.load();
        this.texture = TextureLoader.loadTexture("res/skybox1.png");
        this.shader.stop();
        this.loadVertexBufferData();
    }

    public void render(Camera c) {
        shader.start();
        shader.loadScale(this.scale);
        shader.loadViewMatrix(c);
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL30.glBindVertexArray(this.skybox.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.skybox.getIndicesVBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture.getTextureId());

        GL11.glDrawElements(GL11.GL_TRIANGLES, this.skybox.getIndiciesCount(), GL11.GL_UNSIGNED_INT, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);

        GL11.glEnable(GL11.GL_CULL_FACE);
        shader.stop();
    }

    private void loadVertexBufferData() {

    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
