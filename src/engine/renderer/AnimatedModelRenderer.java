package engine.renderer;

import common.Maths;
import engine.entity.Entity;
import engine.model.Model;
import engine.model.TexturedModel;
import engine.model.animation.AnimatedModel;
import engine.shader.animatedModelShader.AnimatedModelShader;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

public class AnimatedModelRenderer extends StaticRenderer {

    public AnimatedModelRenderer(Matrix4f projectionMatrix) {
        this.shader = new AnimatedModelShader();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    protected void prepareEntity(Entity entity) {
        ((AnimatedModelShader) shader).loadJointsMatrix(((AnimatedModel) entity.getModel().getModel()).getJointTransforms());
        Matrix4f transMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().x, entity.getRotation().y, entity.getRotation().z, entity.getScale());
        shader.loadTransformationMatrix(transMatrix);
    }

    protected void prepareTexturedModel(TexturedModel model) {
        Model rawModel = model.getModel();
        GL30.glBindVertexArray(rawModel.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);
        GL20.glEnableVertexAttribArray(4);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, rawModel.getIndicesVBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureId());

    }

    protected void unbindTexturedModel() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL20.glDisableVertexAttribArray(4);
        GL30.glBindVertexArray(0);

    }


}
