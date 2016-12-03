package xyz.tgprojects.ideahub.models;

public class Favorite {
    private String userId;
    private String title;
    private String description;
    private String creatorId;

    public Favorite(String userId, String title, String description, String creatorId) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
    }

    public String getUserId() {
        return userId;
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
