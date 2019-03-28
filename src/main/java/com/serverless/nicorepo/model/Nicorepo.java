package com.serverless.nicorepo.model;

import com.serverless.nicorepo.api.NiconicoAPI;
import com.serverless.nicorepo.client.type.NiconicoTopic;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.json.JSONArray;
import org.json.JSONObject;

public class Nicorepo {
  private final JSONObject reports;
  private final int statusCode;

  public Nicorepo(JSONObject jsonObject, int statusCode) {
    this.reports = jsonObject;
    this.statusCode = statusCode;
  }

  public JSONObject getReports() {
    return this.reports;
  }

  public int getStatusCode() {
    return this.statusCode;
  }

  public DiscordMessage createDiscordMessage(
      LocalDateTime localDateTime, NiconicoTopic niconicoTopic) {
    List<JSONObject> filterdReports = filterNicorepo(localDateTime, niconicoTopic);

    if (filterdReports.size() == 0) {
      return new DiscordMessage(null);
    }

    JSONObject messageRoot = new JSONObject();
    messageRoot.put("content", localDateTime + "の投稿についてのお知らせです！");

    List<JSONObject> embeds =
        filterdReports
            .stream()
            .map(
                report -> {
                  JSONObject node = new JSONObject();

                  node.put("timestamp", report.get("createdAt"));

                  JSONObject author = new JSONObject();
                  author.put("name", report.getJSONObject("senderNiconicoUser").get("nickname"));
                  author.put(
                      "icon_url",
                      report
                          .getJSONObject("senderNiconicoUser")
                          .getJSONObject("icons")
                          .getJSONObject("tags")
                          .getJSONObject("defaultValue")
                          .getJSONObject("urls")
                          .get("s50x50"));
                  node.put("author", author);

                  node.put("title", report.getJSONObject("video").get("title"));

                  node.put(
                      "url",
                      NiconicoAPI.WATCH_PAGE.getUrlText()
                          + report.getJSONObject("video").get("videoWatchPageId"));

                  JSONObject thumbnail = new JSONObject();
                  thumbnail.put(
                      "url",
                      report.getJSONObject("video").getJSONObject("thumbnailUrl").get("normal"));

                  node.put("thumbnail", thumbnail);
                  return node;
                })
            .collect(Collectors.toList());
    messageRoot.put("embeds", embeds);
    return new DiscordMessage(messageRoot);
  }

  private List<JSONObject> filterNicorepo(LocalDateTime datetime, NiconicoTopic niconicoTopic) {
    JSONArray array = (JSONArray) reports.get("data");
    List<JSONObject> filterdReports =
        StreamSupport.stream(array.spliterator(), false)
            .map(JSONObject.class::cast)
            .filter(node -> node.get("topic").equals(niconicoTopic.UPLOAD.getTypeText()))
            .filter(
                node ->
                    LocalDateTime.parse(
                            node.get("createdAt").toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .isAfter(datetime))
            .collect(Collectors.toList());
    return filterdReports;
  }
}
