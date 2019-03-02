package com.serverless.nicorepo.client;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.serverless.nicorepo.model.DiscordMessage;

public class DiscordClient {

    private final String webhookEndpoint;

    public DiscordClient(String webhookEndpoint) {
        this.webhookEndpoint = webhookEndpoint;
    }

    public void postMessage(DiscordMessage message) throws UnirestException {
        Unirest.post(this.webhookEndpoint)
                .header("Content-Type", "application/json")
                .body(message.getMessage())
                .asJson();
    }
}
