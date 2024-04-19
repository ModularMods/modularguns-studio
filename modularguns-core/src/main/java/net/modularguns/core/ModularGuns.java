package net.modularguns.core;

import lombok.Getter;
import net.modularguns.core.contentpack.pack.ContentPackManager;
import net.modularguns.core.contentpack.types.ContentTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModularGuns {

    @Getter
    private static final Logger logger = LogManager.getLogger(ModularGuns.class);
    @Getter
    private static ModularGuns instance;
    @Getter
    private ContentTypes contentTypes;
    @Getter
    private ContentPackManager contentPackManager;

    public ModularGuns() {
        super();
        instance = this;


        ModularGuns.getInstance().contentTypes = new ContentTypes();
        ModularGuns.getInstance().contentPackManager = new ContentPackManager();

        ModularGuns.getInstance().getContentTypes().registerCoreTypes();
    }


}
