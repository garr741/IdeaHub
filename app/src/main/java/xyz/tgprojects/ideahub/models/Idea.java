package xyz.tgprojects.ideahub.models;

public class Idea {
    private String title;
    private String description;
    private String creatorId;

    public Idea(String title, String description, String creatorId) {
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatorId() {
        return creatorId;
    }
}
