package net.modularguns.studio;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL32;

public class GLContext {
    private long windowHandle;

    public GLContext(long windowHandle) {
        this.windowHandle = windowHandle;
        GLFW.glfwMakeContextCurrent(windowHandle);
        GLFW.glfwSwapInterval(1); // Enable v-sync
        GL.createCapabilities();
    }

    public void clear() {
        GL32.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        GL32.glClear(GL32.GL_COLOR_BUFFER_BIT);
    }
}