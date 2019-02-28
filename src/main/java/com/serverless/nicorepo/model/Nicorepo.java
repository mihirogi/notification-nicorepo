package com.serverless.nicorepo.model;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class Nicorepo {
    private final HttpResponse<JsonNode> jsonNode;

    public Nicorepo(HttpResponse<JsonNode> jsonNode) {
        this.jsonNode = jsonNode;
    }


    public JsonNode getReports() {
        return jsonNode.getBody();
    }

    public int getNicorepoStatusCode() {
        return jsonNode.getStatus();
    }
}
