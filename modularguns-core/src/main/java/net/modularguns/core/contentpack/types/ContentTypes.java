package net.modularguns.core.contentpack.types;

import net.modularguns.core.api.IItem;
import net.modularguns.core.guns.GunType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ContentTypes {

    private int typeId = 0;

    private ArrayList<TypeEntry> values = new ArrayList<>();

    public void registerCoreTypes() {
        registerType("guns", IItem.class, GunType.class, typeId++);
    }

    /**
     * Main function to register new types
     *
     * @param name      Name of the type
     * @param typeClass Class of the type (must extend BaseType)
     * @param typeId    ID number of the type, must be unique
     */
    public void registerType(String name, Class<? extends IItem> itemClass, Class<? extends BaseType> typeClass, int typeId) {
        values.add(new TypeEntry(name, typeClass, typeId));
    }

    public Collection<TypeEntry> getValues() {
        return Collections.unmodifiableList(values);
    }
}