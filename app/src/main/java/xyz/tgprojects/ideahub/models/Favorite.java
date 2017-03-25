package xyz.tgprojects.ideahub.models;

public class Favorite {
    private String userId;
    private String text;
    private String createdAt;
    private String creator;

    public Favorite(String userId, String text, String createdAt, String creator) {
        this.userId = userId;
        this.text = text;
        this.createdAt = createdAt;
        this.creator = creator;
    }

    public String getUserId() {
        return userId;
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
