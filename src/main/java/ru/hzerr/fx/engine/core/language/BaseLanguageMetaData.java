package ru.hzerr.fx.engine.core.language;

public abstract class BaseLanguageMetaData {

    private String name;
    private String relativePath;

    protected BaseLanguageMetaData(String name, String relativePath) {
        this.name = name;
        this.relativePath = relativePath;
    }

    public String getName() {
        return name;
    }

    public String getRelativePath() {
        return relativePath;
    }
}
