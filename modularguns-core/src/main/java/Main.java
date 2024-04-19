import net.modularguns.core.ModularGuns;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        ModularGuns modularGuns = new ModularGuns();
        modularGuns.getContentPackManager().init(new File("../run/ModularGuns"));

        modularGuns.getContentPackManager().getContentPacks().get(0).getErroredJsons().forEach(erroredJson -> {
            ModularGuns.getLogger().error("Errored JSON: " + erroredJson.getPath());
            ModularGuns.getLogger().error("Error: " + erroredJson.getErrorMessage());
        });
        ModularGuns.getLogger().debug("Content packsloaded");
    }
}
