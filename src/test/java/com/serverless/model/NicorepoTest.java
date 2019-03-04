package com.serverless.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.serverless.nicorepo.client.type.NiconicoTopic;
import com.serverless.nicorepo.model.DiscordMessage;
import com.serverless.nicorepo.model.Nicorepo;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.junit.Test;

public class NicorepoTest {
  @Test
  public void 動画が投稿されたのタイプが1件だけ抽出される()
      throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    LocalDateTime localDateTime = LocalDateTime.of(2019, 2, 27, 6, 0, 0);
    JSONObject jsonObject = new JSONObject(createNicorepoJson());

    Nicorepo nicorepo = new Nicorepo(jsonObject, 200);
    Method filterNicorepo =
        Nicorepo.class.getDeclaredMethod(
            "filterNicorepo", LocalDateTime.class, NiconicoTopic.class);
    filterNicorepo.setAccessible(true);
    List<JSONObject> filterdReports =
        (List<JSONObject>) filterNicorepo.invoke(nicorepo, localDateTime, NiconicoTopic.UPLOAD);

    assertThat(filterdReports.size(), is(1));
  }

  @Test
  public void Discord用オブジェクトを生成して中身が1件() throws IOException, UnirestException {
    LocalDateTime localDateTime = LocalDateTime.of(2019, 2, 27, 6, 0, 0);
    JSONObject jsonObject = new JSONObject(createNicorepoJson());

    Nicorepo nicorepo = new Nicorepo(jsonObject, 200);
    DiscordMessage message = nicorepo.createDiscordMessage(localDateTime, NiconicoTopic.UPLOAD);

    assertThat(message.getMessage().has("content"), is(true));
    assertThat(message.getMessage().getJSONArray("embeds").length(), is(1));
  }

  private String createNicorepoJson() throws IOException {
    return Files.lines(Paths.get("document/nicorepo-response.json"), Charset.forName("UTF-8"))
        .collect(Collectors.joining(System.getProperty("line.separator")));
  }
}
