package com.kostikum.timeoverseer.entity;

import androidx.annotation.NonNull;

public class Project {

    private String mName;
    private String mColor;

    public Project(@NonNull String name, @NonNull String color) {
        this.mName = name;
        this.mColor = color;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }
}
