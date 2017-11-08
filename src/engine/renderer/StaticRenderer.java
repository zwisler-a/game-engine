package engine.renderer;

import common.Maths;
import engine.entity.Camera;
import engine.entity.Entity;
import engine.entity.LightSource;
import engine.model.Model;
import engine.model.TexturedModel;
import engine.shader.staticShader.StaticShader;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

import java.util.HashMap;
import java.util.LinkedList;

public class StaticRenderer {
    private StaticShader shader;

    public StaticRenderer(Matrix4f projectionMatrix) {
        this.shader = new StaticShader();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();

    }

    public void render(HashMap<TexturedModel, LinkedList<Entity>> entities, LinkedList<LightSource> light, Camera cam, RenderOptions renderOptions) {
        shader.start();
        shader.loadLight(light);
        loadShadowMaps(light);
        shader.loadviewMatrix(cam);
        shader.loadClippingPlane(renderOptions.clippingPlane);
        if(entities == null){
            return;
        }
        for (TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);
            LinkedList<Entity> singleE = entities.get(model);
            for (Entity entity : singleE) {
                prepareEntity(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getIndiciesCount(), GL11.GL_UNSIGNED_INT, 0);
            }
        }
        unbindTexturedModel();
        shader.stop();
    }

    private void loadShadowMaps(LinkedList<LightSource> lights) {
        for(LightSource light:lights){
            if(light.isShadowEnabled()){
                GL13.glActiveTexture(GL13.GL_TEXTURE10);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, light.getFbo().getDepthTexture().getTextureId());
            }
        }
    }

    private void prepareTexturedModel(TexturedModel model) {
        Model rawModel = model.getModel();
        GL30.glBindVertexArray(rawModel.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, rawModel.getIndicesVBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureId());
    }

    private void prepareEntity(Entity entity) {
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
