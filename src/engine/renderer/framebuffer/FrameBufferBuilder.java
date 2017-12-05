package engine.renderer.framebuffer;

import common.Logger.Logger;
import engine.WindowManager;
import engine.model.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;

public class FrameBufferBuilder {

    private int frameBufferId;
    private int textureId;
    private int depthTextureId;

    public FrameBufferBuilder() {
        this.frameBufferId = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.frameBufferId);
    }

    public FrameBuffer create(int width, int height) {
        int fboStatus = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        if (fboStatus != GL_FRAMEBUFFER_COMPLETE) {
            Logger.error("FBO-Status-Error: " + fboStatus);
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, WindowManager.getWidth(), WindowManager.getHeight());
        return new FrameBuffer(this.frameBufferId, width, height, new Texture(this.textureId), new Texture(this.depthTextureId));
    }

    public FixedDimensionsFrameBufferBuilder setDimensions(int width, int height) {
        return new FixedDimensionsFrameBufferBuilder(width, height, this);
    }

    public FrameBufferBuilder addTexture(int width, int height) {
        this.textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height,
                0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, this.textureId, 0);
        return this;
    }

    public FrameBufferBuilder addDepthTexture(int width, int height) {
        this.depthTextureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.depthTextureId);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32, width, height,
                0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
                this.depthTextureId, 0);
        return this;
    }

    public FrameBufferBuilder addDepthBuffer(int width, int height) {
        int depthBuffer = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width,
                height);
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
                GL30.GL_RENDERBUFFER, depthBuffer);
        return this;
    }


    public class FixedDimensionsFrameBufferBuilder {

        private final int width;
        private final int height;
        private final FrameBufferBuilder frameBufferBuilder;

        public FixedDimensionsFrameBufferBuilder(int width, int height, FrameBufferBuilder frameBufferBuilder) {
            this.width = width;
            this.height = height;
            this.frameBufferBuilder = frameBufferBuilder;
        }

        public FixedDimensionsFrameBufferBuilder addTexture() {
            this.frameBufferBuilder.addTexture(width, height);
            return this;
        }

        public FixedDimensionsFrameBufferBuilder addDepthTexture() {
            this.frameBufferBuilder.addDepthTexture(width, height);
            return this;
        }

        public FixedDimensionsFrameBufferBuilder addDepthBuffer() {
            this.frameBufferBuilder.addDepthBuffer(width, height);
            return this;
        }

        public FrameBuffer create() {
            return this.frameBufferBuilder.create(width, height);
        }

    }

}
