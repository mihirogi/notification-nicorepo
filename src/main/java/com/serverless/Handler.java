package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.serverless.nicorepo.client.DiscordClient;
import com.serverless.nicorepo.client.NiconicoClient;
import com.serverless.nicorepo.client.type.NiconicoTopic;
import com.serverless.nicorepo.model.DiscordMessage;
import com.serverless.nicorepo.model.Nicorepo;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import org.apache.log4j.Logger;

public class Handler implements RequestHandler<Map<String, Object>, String> {

  private static final Logger LOG = Logger.getLogger(Handler.class);

  public String handleRequest(Map<String, Object> input, Context context) {
    LOG.info("START");
    NiconicoClient niconicoClient =
        new NiconicoClient(
            System.getenv("NICONICO_MAILADDRESS"), System.getenv("NICONICO_PASSWORD"));
    DiscordClient discordClient = new DiscordClient(System.getenv("DISCORD_WEBHOOK_ENDPOINT"));
    try {
      niconicoClient.login();
      Nicorepo nicorepo = niconicoClient.getNicorepo();
      DiscordMessage message =
          nicorepo.createDiscordMessage(
              LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusHours(1),
              NiconicoTopic.UPLOAD);
      if (message.hasMessage()) {
        discordClient.postMessage(message);
      }

    } catch (UnirestException e) {
      e.printStackTrace();
    }

    return "200";
  }
}
