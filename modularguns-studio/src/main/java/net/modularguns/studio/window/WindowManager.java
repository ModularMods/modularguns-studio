package net.modularguns.studio.window;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Manages GLFW window creation, OpenGL context, and basic rendering setup
 */
public class WindowManager {

    private long window;
    private int glVao;

    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;
    private static final String WINDOW_TITLE = "Modular Guns Studio";

    public void initialize() {
        setupErrorCallback();
        initializeGLFW();
        createWindow();
        setupOpenGL();
    }

    private void setupErrorCallback() {
        GLFWErrorCallback.createPrint(System.err).set();
    }

    private void initializeGLFW() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    }

    private void createWindow() {
        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // Enable v-sync
    }

    private void setupOpenGL() {
        GL.createCapabilities();

        // Core profile requires a VAO to be bound
        glVao = glGenVertexArrays();
        glBindVertexArray(glVao);

        // Configure OpenGL
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.1f, 0.1f, 0.12f, 1.0f);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public void prepareFrame() {
        int[] fbW = new int[1];
        int[] fbH = new int[1];
        glfwGetFramebufferSize(window, fbW, fbH);
        glViewport(0, 0, fbW[0], fbH[0]);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public long getWindow() {
        return window;
    }

    public void dispose() {
        glfwDestroyWindow(window);
        glfwTerminate();
        GLFWErrorCallback cb = glfwSetErrorCallback(null);
        if (cb != null) {
            cb.free();
        }
    }
}
