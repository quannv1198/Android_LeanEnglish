package com.example.quan.english.fragment.video;

public class Video {
    String name;
    String path;
    String nameType;

    public Video(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Video(String name, String path, String nameType) {
        this.name = name;
        this.path = path;
        this.nameType = nameType;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getNameType() {
        return nameType;
    }
}