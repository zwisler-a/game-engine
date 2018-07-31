package engine.renderer.framebuffer;

import engine.WindowManager;
import engine.model.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

/**
 * Representation of a framebuffer object
 */
public class FrameBuffer {

    private final Texture depthTexture;
    private int frameBufferId;
    private Texture texture;

    private int width, height;

    public FrameBuffer(int fboId, int width, int height, Texture texture, Texture depthTexture) {
        this.width = width;
        this.height = height;
        this.frameBufferId = fboId;
        this.texture = texture;
        this.depthTexture = depthTexture;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Texture getDepthTexture() {
        return depthTexture;
    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);//To make sure the texture isn't bound
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, width, height);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, WindowManager.getWidth(), WindowManager.getHeight());
    }
}
