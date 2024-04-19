package net.modularguns.core.contentpack.types;


import lombok.Getter;

public class TypeEntry {

    @Getter
    private String name;
    @Getter
    private Class<? extends BaseType> typeClass;
    private int id;

    public TypeEntry(String name, Class<? extends BaseType> typeClass, int id) {
        this.name = name;
        this.typeClass = typeClass;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

}
