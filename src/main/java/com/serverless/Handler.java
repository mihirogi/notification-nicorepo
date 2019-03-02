package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.serverless.nicorepo.client.DiscordClient;
import com.serverless.nicorepo.client.NiconicoClient;
import com.serverless.nicorepo.client.type.NiconicoTopic;
import com.serverless.nicorepo.model.Nicorepo;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Handler implements RequestHandler<String, String> {

    private static final Logger LOG = Logger.getLogger(Handler.class);

    public String handleRequest(String input, Context context) {
        NiconicoClient niconicoClient = new NiconicoClient(System.getenv("NICONICO_MAILADDRESS"),
                System.getenv("NICONICO_PASSWORD"));
        DiscordClient discordClient = new DiscordClient(System.getenv("DISCORD_WEBHOOK_ENDPOINT"));
        try {
            niconicoClient.login();
            Nicorepo nicorepo = niconicoClient.getNicorepo();
            discordClient.postMessage(nicorepo.createDiscordMessage(
                    LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusHours(2),
                    NiconicoTopic.UPLOAD
            ));
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return "200";
    }
}
