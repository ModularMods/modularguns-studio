package net.modularguns.core.contentpack.types;

import lombok.Getter;
import lombok.Setter;
import net.modularguns.core.contentpack.pack.data.ContentPack;

public abstract class BaseType {

    /* Protected from GSON */
    transient int id;
    @Setter
    @Getter
    transient ContentPack contentPack;
    @Getter
    private String internalName;
    @Getter
    private String displayName;

    protected abstract void initValues();

    protected abstract String getAssetDir();
}
