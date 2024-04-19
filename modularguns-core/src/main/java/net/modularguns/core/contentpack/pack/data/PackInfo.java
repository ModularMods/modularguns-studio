package net.modularguns.core.contentpack.pack.data;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class PackInfo {

    String description = "";
    List<String> authors = Arrays.asList("John Doe", "Jane Doe");
    private String name = "";
    private String version = "";

}
