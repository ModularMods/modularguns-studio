package net.modularguns.studio;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

public class IMGuiHUD {
    private ImGuiImplGlfw imGuiGlfw;
    private ImGuiImplGl3 imGuiGl3;
    private long windowHandle;

    public IMGuiHUD(long windowHandle) {
        this.windowHandle = windowHandle;

        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        //io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable); // Enable multi-viewport / platform windows

        imGuiGlfw = new ImGuiImplGlfw();
        imGuiGlfw.init(windowHandle, true);

        imGuiGl3 = new ImGuiImplGl3();
        imGuiGl3.init("#version 150");
    }

    public void startFrame() {
        imGuiGlfw.newFrame();
        ImGui.newFrame();
        process();  // Call process here to include UI content like "Hello, World!"
    }

    public void render() {
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(windowHandle); // Restore the original OpenGL context
        }
    }

    public void cleanup() {
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();
    }

    private void process() {
        ImGui.beginMainMenuBar();
        if (ImGui.beginMenu("File")) {
            //New
            if(ImGui.menuItem("New Project")){
                ImGui.endMenu();
            }
            if(ImGui.menuItem("Open project")){
                ImGui.endMenu();
            }
            if(ImGui.menuItem("Import content-pack", "Ctrl+I")){
                ImGui.endMenu();
            }

            if (ImGui.menuItem("Exit", "Alt+F4")) {

            }
            ImGui.endMenu();
        }
        if (ImGui.beginMenu("Edit")) {
            //Undo
            if(ImGui.menuItem("Undo", "Ctrl+Z")){
                ImGui.endMenu();
            }
            //Redo
            if(ImGui.menuItem("Redo", "Ctrl+Y")){
                ImGui.endMenu();
            }

            ImGui.endMenu();
        }
        if(ImGui.beginMenu("Tools")){
            //Model converter
            if(ImGui.beginMenu("Model converter")){
                if(ImGui.menuItem("glTF to .rmod")){
                    ImGui.endMenu();
                }
                ImGui.endMenu();
            }

            ImGui.endMenu();
        }
        if(ImGui.beginMenu("View")){
            //Default layout
            if(ImGui.menuItem("Default layout")){
                ImGui.endMenu();
            }
            ImGui.endMenu();
        }
        if(ImGui.beginMenu("Help")){
            //About ModularGuns
            if(ImGui.menuItem("About ModularGuns")){
                ImGui.endMenu();
            }
            //Check for updates
            if(ImGui.menuItem("Check for updates")){
                ImGui.endMenu();
            }
            ImGui.endMenu();
        }
        ImGui.endMainMenuBar();
    }
}
