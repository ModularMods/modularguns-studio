package net.modularguns.studio;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class App {
    public static void main(String[] args) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Unable to initialize GLFW.");
        }

        Window window = new Window(1280, 720, "ModularGuns Studio");
        GLContext glContext = new GLContext(window.getWindowHandle());
        IMGuiHUD hud = new IMGuiHUD(window.getWindowHandle());

        while (!GLFW.glfwWindowShouldClose(window.getWindowHandle())) {
            glContext.clear();

            hud.startFrame();
            hud.render();

            GLFW.glfwSwapBuffers(window.getWindowHandle());
            GLFW.glfwPollEvents();
        }

        hud.cleanup();
        GLFW.glfwDestroyWindow(window.getWindowHandle());
        GLFW.glfwTerminate();
    }
}
