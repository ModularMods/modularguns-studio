package net.modularguns.core.contentpack.pack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import net.modularguns.core.ModularGuns;
import net.modularguns.core.contentpack.pack.data.ContentPack;
import net.modularguns.core.contentpack.pack.data.ErroredJson;
import net.modularguns.core.contentpack.pack.data.PackInfo;
import net.modularguns.core.contentpack.types.BaseType;
import net.modularguns.core.contentpack.types.TypeEntry;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* The Content Pack Manager of ModularGuns */
public class ContentPackManager {

    public Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /* The list of all the content-packs loaded */
    @Getter
    private List<ContentPack> contentPacks = new ArrayList<>();

    /**
     * Function called at the init of the mod, creating one item per ContentTypes
     * And loads all the content-packs in /ModularGuns/*
     *
     * @param MOD_DIR
     */
    public void init(File MOD_DIR) {
        contentPacks.clear();  // Clear any previously loaded packs

        // Filter directories only for content packs
        File[] directories = MOD_DIR.listFiles(File::isDirectory);
        if (directories != null) {
            for (File directory : directories) {
                loadContentPack(directory);
            }
        }
    }

    private void loadContentPack(File contentPackDirectory) {
        File infoFile = new File(contentPackDirectory, "info.json");
        PackInfo packInfo = null;

        try (FileReader reader = new FileReader(infoFile)) {
            packInfo = gson.fromJson(reader, PackInfo.class);
        } catch (Exception e) {
            ModularGuns.getLogger().error("Failed to load pack info from: " + infoFile.getPath(), e);
            return; // Exit this method if the pack info cannot be loaded
        }

        ContentPack pack = new ContentPack(contentPackDirectory, packInfo);
        contentPacks.add(pack);

        ModularGuns.getLogger().debug("Loading content pack: " + packInfo.getName());

        for (TypeEntry entry : ModularGuns.getInstance().getContentTypes().getValues()) {
            File typeDir = new File(contentPackDirectory, entry.getName());
            if (typeDir.exists() && typeDir.isDirectory()) {
                File[] typeFiles = typeDir.listFiles((dir, name) -> name.endsWith(".json"));
                if (typeFiles != null) {
                    for (File typeFile : typeFiles) {
                        try (FileReader typeFileReader = new FileReader(typeFile)) {
                            BaseType type = gson.fromJson(typeFileReader, entry.getTypeClass());
                            type.setContentPack(pack); // Linking the type to its pack
                            pack.addBaseType(type);
                        } catch (JsonSyntaxException e) {
                            ModularGuns.getLogger().error("Failed to load type due to JSON syntax error in file: " + typeFile.getPath() + "; Error: " + e.getMessage(), e);
                            pack.addErroredJson(new ErroredJson(typeFile.getPath(), e.getMessage()));
                            // Here you can choose not to add the type to the pack or take other recovery measures
                        } catch (IOException e) {
                            ModularGuns.getLogger().error("IO error while loading type from: " + typeFile.getPath(), e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the a BaseType from the registered internalName casted as typeClass
     * Example: Get the GunType object from a gun with the internalName "mypack.akm"
     *
     * @param typeClass    The type of the item you want to cast
     * @param internalName The internalName of the item
     * @param <T>          The type of the item you want to cast
     * @return The BaseType casted as GunType/ArmorType... of the item found
     */
    public <T> T getType(Class<T> typeClass, String internalName) {
        for (ContentPack pack : this.contentPacks) {
            for (BaseType type : pack.getBaseTypes()) {
                if (type.getInternalName().equalsIgnoreCase(internalName)) {
                    return typeClass.cast(type);
                }
            }
        }
        return null;
    }
}
