package net.modularguns.studio;

import net.modularguns.studio.ui.UIManager;
import net.modularguns.studio.window.WindowManager;

/**
 * Main application class that coordinates all subsystems
 */
public class Application {

    private WindowManager windowManager;
    private UIManager uiManager;

    public void run() {
        initialize();
        loop();
        cleanup();
    }

    private void initialize() {
        windowManager = new WindowManager();
        windowManager.initialize();

        uiManager = new UIManager();
        uiManager.initialize(windowManager.getWindow());
    }

    private void loop() {
        while (!windowManager.shouldClose()) {
            windowManager.pollEvents();

            uiManager.newFrame();
            uiManager.render();

            // Prepare OpenGL for rendering
            windowManager.prepareFrame();

            uiManager.endFrame();
            windowManager.swapBuffers();
        }
    }

    private void cleanup() {
        if (uiManager != null) {
            uiManager.dispose();
        }
        if (windowManager != null) {
            windowManager.dispose();
        }
    }
}
