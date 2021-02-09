package com.example.notesapp;

import com.google.firebase.Timestamp;

public class notes {
    public String text, userId;
    public boolean isCOmpleted;
    public Timestamp created;

    public notes(String text, String userId, boolean isCOmpleted, Timestamp created) {
        this.text = text;
        this.userId = userId;
        this.isCOmpleted = isCOmpleted;
        this.created = created;
    }

    public notes() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getisCOmpleted() {
        return isCOmpleted;
    }

    public void setCOmpleted(boolean COmpleted) {
        isCOmpleted = COmpleted;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "notes{" +
                "text='" + text + '\'' +
                ", userId='" + userId + '\'' +
                ", isCOmpleted=" + isCOmpleted +
                ", created=" + created +
                '}';
    }
}
