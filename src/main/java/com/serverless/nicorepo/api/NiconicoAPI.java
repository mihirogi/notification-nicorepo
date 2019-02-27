package com.serverless.nicorepo.api;

public enum NiconicoAPI {

    LOGIN("https://secure.nicovideo.jp/secure/login?site=niconico"),
    NICOREPO("http://www.nicovideo.jp/api/nicorepo/timeline/my/all?client_app=pc_myrepo");

    private final String url;


    private NiconicoAPI(String url) {
        this.url = url;
    }

    public String getUrlText() {
        return this.url;
    }
}
