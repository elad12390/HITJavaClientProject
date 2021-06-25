package com.hit.dialogs.createGame.models;

public class MetadataItem implements IMetadataItem {
    private final int id;
    private final String name;

    public MetadataItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
