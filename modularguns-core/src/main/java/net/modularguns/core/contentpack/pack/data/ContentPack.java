package net.modularguns.core.contentpack.pack.data;

import lombok.Getter;
import net.modularguns.core.contentpack.types.BaseType;

import java.io.File;
import java.util.ArrayList;

@Getter
public class ContentPack {

    private File directory;
    private String directoryName;
    private PackInfo packInfo;

    /* List of all the BaseTypes in the content-pack */
    private transient ArrayList<BaseType> baseTypes = new ArrayList<>();
    /* List of all the errored JSONs in the content-pack */
    private transient ArrayList<ErroredJson> erroredJsons = new ArrayList<>();

    public ContentPack(File file, PackInfo packInfo) {
        this.directory = file;
        this.directoryName = file.getName();

        this.packInfo = packInfo;
    }

    public void addBaseType(BaseType type) {
        baseTypes.add(type);
    }

    public void addErroredJson(ErroredJson erroredJson) {
        erroredJsons.add(erroredJson);
    }

}
