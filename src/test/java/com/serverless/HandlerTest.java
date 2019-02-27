package com.serverless;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.serverless.nicorepo.client.NiconicoClient;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class HandlerTest {

    @Test
    public void test200okResponse() {
        Handler target = new Handler();
        Map input = new HashMap<String, Object>();

        ApiGatewayResponse response = target.handleRequest(input, createContext());
        assertThat(response, is(notNullValue()));
        assertThat(response.getBody(), equalTo("{\"message\":\"Hello Lambda!\",\"input\":{}}"));
    }

    @Test
    public void ニコニコ動画にログインできる() throws UnirestException {

        //TODO: 環境変数か、外部ファイルから読み込むようにする
        NiconicoClient client = new NiconicoClient("test", "test");
        client.login();
        assertThat(client.isLoggedIn(), is(true));
    }

    @Test
    public void ニコレポを取得できる() throws UnirestException {

        NiconicoClient client = new NiconicoClient("test", "test");
        client.login();
        HttpResponse<JsonNode> nicorepo = client.getNicorepo();
        System.out.println(nicorepo.getBody());
        assertThat(nicorepo.getStatus(), is(200));
        assertThat(nicorepo.getBody(), is(notNullValue()));
    }

    private Context createContext() {
        return new Context() {
            @Override
            public String getAwsRequestId() {
                return null;
            }

            @Override
            public String getLogGroupName() {
                return null;
            }

            @Override
            public String getLogStreamName() {
                return null;
            }

            @Override
            public String getFunctionName() {
                return null;
            }

            @Override
            public String getFunctionVersion() {
                return null;
            }

            @Override
            public String getInvokedFunctionArn() {
                return null;
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return null;
            }
        };
    }
}
