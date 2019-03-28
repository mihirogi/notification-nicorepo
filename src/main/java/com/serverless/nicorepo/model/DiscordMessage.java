package com.serverless.nicorepo.model;

import org.json.JSONObject;

public class DiscordMessage {

  private JSONObject message;

  public DiscordMessage(JSONObject message) {
    this.message = message;
  }

  public JSONObject getMessage() {
    return message;
  }

  // TODO: 名前がいけてない
  public boolean hasMessage() {
    return message != null;
  }
}
