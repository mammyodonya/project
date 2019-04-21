package com.imejadevs.diagnosis.Model;

public class VideosDetails {
    String id,media,disease;

    public VideosDetails() {
    }

    public VideosDetails(String id, String media, String disease) {
        this.id = id;
        this.media = media;
        this.disease = disease;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
