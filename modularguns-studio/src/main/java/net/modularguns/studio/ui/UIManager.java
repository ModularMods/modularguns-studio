package net.modularguns.studio.ui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Manages ImGui initialization, rendering coordination, and UI subsystems
 */
public class UIManager {

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private long window;
    private ImBoolean showDemoWindow = new ImBoolean(true);

    public void initialize(long window) {
        this.window = window;

        initializeImGui();
    }

    private void initializeImGui() {
        ImGui.createContext();
        final ImGuiIO io = ImGui.getIO();

        // Configure ImGui
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);

        // Setup style
        ImGui.styleColorsDark();
        final ImGuiStyle style = ImGui.getStyle();
        if ((io.getConfigFlags() & ImGuiConfigFlags.ViewportsEnable) != 0) {
            style.setWindowRounding(6.0f);
        }

        // Configure clipboard
        io.setIniFilename(null); // Keep settings in memory
        io.setGetClipboardTextFn(new ImStrSupplier() {
            @Override public String get() { return glfwGetClipboardString(window); }
        });
        io.setSetClipboardTextFn(new ImStrConsumer() {
            @Override public void accept(final String s) { glfwSetClipboardString(window, s); }
        });

        // Initialize backends
        imGuiGlfw.init(window, true);
        imGuiGl3.init("#version 150");
    }

    public void newFrame() {
        imGuiGlfw.newFrame();
        imGuiGl3.newFrame();
        ImGui.newFrame();
    }

    private void renderMainMenu() {
        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("File")) {
                if (ImGui.menuItem("New Project")) {
                    // TODO: New Project action
                }
                if (ImGui.menuItem("Open project")) {
                    // TODO: Open Project action
                }
                if (ImGui.menuItem("Import content-pack", "Ctrl+I")) {
                    // TODO: Import content-pack action
                }
                if (ImGui.menuItem("Exit", "Alt+F4")) {
                    // TODO: Exit action
                }
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Edit")) {
                if (ImGui.menuItem("Undo", "Ctrl+Z")) {
                    // TODO: Undo action
                }
                if (ImGui.menuItem("Redo", "Ctrl+Y")) {
                    // TODO: Redo action
                }
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Tools")) {
                if (ImGui.beginMenu("Model converter")) {
                    if (ImGui.menuItem("glTF to .rmod")) {
                        // TODO: Conversion action
                    }
                    ImGui.endMenu();
                }
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("View")) {
                if (ImGui.menuItem("Default layout")) {
                    // TODO: Reset layout action
                }
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Help")) {
                if (ImGui.menuItem("About ModularGuns")) {
                    // TODO: About dialog
                }
                if (ImGui.menuItem("Check for updates")) {
                    // TODO: Update check
                }
                ImGui.endMenu();
            }
            ImGui.endMainMenuBar();
        }
    }

    public void render() {
        renderMainMenu();

        // Render demo window
        if (showDemoWindow.get()) {
            ImGui.showDemoWindow(showDemoWindow);
        }

        // Add a simple control window
        if (ImGui.begin("UI Controls")) {
            if (ImGui.button("Toggle Demo Window")) {
                showDemoWindow.set(!showDemoWindow.get());
            }
            ImGui.text("Demo window visible: " + showDemoWindow.get());
        }
        ImGui.end();
    }

    public void endFrame() {
        ImGui.render();

        // Render ImGui
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        // Handle multi-viewport
        final ImGuiIO io = ImGui.getIO();
        if ((io.getConfigFlags() & ImGuiConfigFlags.ViewportsEnable) != 0) {
            final long backup = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backup);
        }
    }

    public void dispose() {
        imGuiGl3.shutdown();
        imGuiGlfw.shutdown();
        ImGui.destroyContext();
    }
}