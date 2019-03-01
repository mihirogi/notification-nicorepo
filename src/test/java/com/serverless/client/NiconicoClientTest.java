package com.serverless.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.serverless.nicorepo.client.NiconicoClient;
import com.serverless.nicorepo.model.Nicorepo;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class NiconicoClientTest {

    @Test
    public void ニコニコ動画にログインできる() throws UnirestException {
        NiconicoClient client = createNiconicoClient();
        client.login();
        assertThat(client.isLoggedIn(), is(true));
    }

    @Test
    public void ニコレポを取得できる() throws UnirestException {
        NiconicoClient client = createNiconicoClient();
        client.login();
        Nicorepo nicorepo = client.getNicorepo();
        assertThat(nicorepo.getStatusCode(), is(200));
        assertThat(nicorepo.getReports(), is(notNullValue()));
    }

    private NiconicoClient createNiconicoClient() {
        //TODO: 環境変数か、外部ファイルから読み込むようにする
        return new NiconicoClient("test", "test");
    }
}
