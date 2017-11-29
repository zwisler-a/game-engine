package engine.renderer;

import common.Maths;
import engine.entity.Camera;
import engine.entity.Entity;
import engine.entity.WaterTile;
import engine.model.Model;
import engine.model.PlaneModel;
import engine.model.Texture;
import engine.model.TexturedModel;
import engine.model.loaders.TextureLoader;
import engine.renderer.queued.QueuedRenderer;
import engine.scene.Scene;
import engine.shader.waterShader.WaterShader;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.*;

import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.*;

public class WaterRenderer {
    private WaterShader shader;
    public static Camera inverted;
    private static RenderOptions waterRenderOptions;
    private Texture waterDuDvMap;

    public WaterRenderer(Matrix4f projectionMatrix) {
        this.shader = new WaterShader();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
        this.waterRenderOptions = new RenderOptions(true, true, false, false, false);
        this.waterDuDvMap = TextureLoader.loadTexture("res/waterDuDv.png");
    }

    public void render(Scene s, Renderer renderer) {

        for (TexturedModel model : s.getEntities(WaterRenderer.class).keySet()) {
            LinkedList<Entity> singleE = s.getEntities(WaterRenderer.class).get(model);
            for (Entity entity : singleE) {
                this.renderReflection(s, renderer, (WaterTile) entity);
            }
        }
        shader.start();
        shader.loadLight(s.getLightSources());
        shader.loadCameraPosition(s.getCamera().getPosition());
        shader.loadViewMatrix(s.getCamera());
        shader.loadReflectionAndRefractionTexture();

        for (TexturedModel model : s.getEntities(WaterRenderer.class).keySet()) {
            LinkedList<Entity> singleE = s.getEntities(WaterRenderer.class).get(model);
            for (Entity entity : singleE) {
                prepareTexturedModel((WaterTile) entity);
                prepareEntity(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, PlaneModel.load().getIndiciesCount(), GL11.GL_UNSIGNED_INT, 0);
            }
        }
        unbindTexturedModel();
        shader.stop();
    }

    private void renderReflection(Scene s, Renderer renderer, WaterTile waterTile) {
        QueuedRenderer.add(() -> {
                    waterTile.getReflectionFbo().bind();
                    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                    Camera orginal = s.getCamera();
                    inverted = new Camera(orginal);
                    inverted.getPosition().y = waterTile.getPosition().y - (inverted.getPosition().y - waterTile.getPosition().y - .5f);
                    inverted.setRotX(orginal.getRotX() * -1);
                    s.setCamera(inverted);
                    WaterRenderer.waterRenderOptions.clippingPlane = new Vector4f(0, 1, 0, -waterTile.getPosition().y);
                    renderer.render(s, WaterRenderer.waterRenderOptions);
                    s.setCamera(orginal);
                    waterTile.getReflectionFbo().unbind();

                    waterTile.getRefractionFbo().bind();
                    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                    WaterRenderer.waterRenderOptions.clippingPlane = new Vector4f(0, -1, 0, waterTile.getPosition().y + .5f);
                    renderer.render(s, WaterRenderer.waterRenderOptions);
                    waterTile.getRefractionFbo().unbind();
                }
        );
    }

    private void prepareTexturedModel(WaterTile model) {
        GL30.glBindVertexArray(PlaneModel.load().getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, PlaneModel.load().getIndicesVBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getReflectionFbo().getTexture().getTextureId());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getRefractionFbo().getTexture().getTextureId());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.waterDuDvMap.getTextureId());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getRefractionFbo().getDepthTexture().getTextureId());
    }

    private void prepareEntity(Entity entity) {
        shader.loadDistortionMultipier(((WaterTile) entity).getWaterDistortion());
        shader.loadTimeFactor(((WaterTile) entity).getTimeFactor());
        Matrix4f transMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().x, entity.getRotation().y, entity.getRotation().z, entity.getScale());
        shader.loadTransformationMatrix(transMatrix);
    }

    private void unbindTexturedModel() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void cleanUp() {
        shader.cleanUp();
    }


}
