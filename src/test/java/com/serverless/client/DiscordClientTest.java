package com.serverless.client;

import static junit.framework.TestCase.fail;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.serverless.nicorepo.client.DiscordClient;
import com.serverless.nicorepo.client.type.NiconicoTopic;
import com.serverless.nicorepo.model.DiscordMessage;
import com.serverless.nicorepo.model.Nicorepo;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.junit.Test;

public class DiscordClientTest {

  @Test
  public void ニコレポの動画を投稿したタイプが1件通知される() throws IOException {
    LocalDateTime localDateTime = LocalDateTime.of(2019, 2, 27, 6, 0, 0);
    JSONObject jsonObject = new JSONObject(createNicorepoJson());

    Nicorepo nicorepo = new Nicorepo(jsonObject, 200);
    DiscordMessage message = nicorepo.createDiscordMessage(localDateTime, NiconicoTopic.UPLOAD);

    DiscordClient client = new DiscordClient(System.getenv("DISCORD_WEBHOOK_ENDPOINT"));

    try {
      client.postMessage(message);
    } catch (UnirestException e) {
      e.printStackTrace();
      fail();
    }
  }

  private String createNicorepoJson() throws IOException {
    return Files.lines(Paths.get("document/nicorepo-response.json"), Charset.forName("UTF-8"))
        .collect(Collectors.joining(System.getProperty("line.separator")));
  }
}
