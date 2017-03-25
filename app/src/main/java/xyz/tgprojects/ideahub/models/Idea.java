package xyz.tgprojects.ideahub.models;

public class Idea {
    private String text;
    private String createdAt;
    private String creator;

    public Idea(String text, String createdAt, String creator) {
        this.text = text;
        this.createdAt = createdAt;
        this.creator = creator;
    }

    public String getText() {
        return text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCreator() {
        return creator;
    }
}
