package net.modularguns.core.contentpack.pack.data;

import lombok.Getter;

public class ErroredJson {

    @Getter
    private String path;
    @Getter
    private String errorMessage;

    public ErroredJson(String path, String errorMessage) {
        this.path = path;
        this.errorMessage = errorMessage;
    }
}
