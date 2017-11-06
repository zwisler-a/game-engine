package engine.renderer;

import common.Logger;
import common.Maths;
import engine.entity.GuiElement;
import engine.model.PlaneModel;
import engine.shader.guiShader.GuiShader;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

import java.util.LinkedList;

public class GuiRenderer {

    private GuiShader shader;

    public GuiRenderer(Matrix4f projectionMatrix) {

        this.shader = new GuiShader();
        this.shader.start();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.stop();
    }

    public void render(LinkedList<GuiElement> elements) {
        shader.start();
        GL11.glDisable(GL11.GL_CULL_FACE);


        GL30.glBindVertexArray(PlaneModel.load().getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, PlaneModel.load().getIndicesVBO());
        for (GuiElement element : elements) {

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, element.getTexture().getTextureId());
            shader.loadProjectionMatrix(element.createProjectionMatrix());
            GL11.glDrawElements(GL11.GL_TRIANGLES, PlaneModel.load().getIndiciesCount(), GL11.GL_UNSIGNED_INT, 0);

        }
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);

        GL11.glEnable(GL11.GL_CULL_FACE);
        shader.stop();
    }


    public void cleanUp() {
        shader.cleanUp();
    }

}
