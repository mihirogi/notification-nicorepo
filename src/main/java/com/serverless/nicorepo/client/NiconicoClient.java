package com.serverless.nicorepo.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.serverless.nicorepo.api.NiconicoAPI;
import com.serverless.nicorepo.model.Nicorepo;

public class NiconicoClient {

  private final String mail;
  private final String password;
  private String cookie;

  public NiconicoClient(String mail, String password) {
    this.mail = mail;
    this.password = password;
  }

  public void login() throws UnirestException {

    HttpResponse<JsonNode> jsonResponse =
        Unirest.post(NiconicoAPI.LOGIN.getUrlText())
            .field("mail", this.mail)
            .field("password", this.password)
            .asJson();
    this.cookie =
        jsonResponse
            .getHeaders()
            .get("Set-Cookie")
            .stream()
            .filter(val -> val.contains("user_session=") && !val.contains("deleted"))
            .findFirst()
            .orElse(null);
  }

  public boolean isLoggedIn() {
    return cookie != null;
  }

  public Nicorepo getNicorepo() throws UnirestException {
    HttpResponse<JsonNode> jsonNodeHttpResponse =
        Unirest.get(NiconicoAPI.NICOREPO.getUrlText()).header("Cookie", cookie).asJson();
    return new Nicorepo(
        jsonNodeHttpResponse.getBody().getObject(), jsonNodeHttpResponse.getStatus());
  }
}
