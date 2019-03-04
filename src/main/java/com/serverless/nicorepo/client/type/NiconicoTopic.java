package com.serverless.nicorepo.client.type;

public enum NiconicoTopic {
    UPLOAD("nicovideo.user.video.upload");

    private String type;

    private NiconicoTopic(String type) {
        this.type = type;
    }

    public String getTypeText(){
        return this.type;
    }
}
