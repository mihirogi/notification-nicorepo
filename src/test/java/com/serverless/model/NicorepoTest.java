package com.serverless.model;

import com.serverless.nicorepo.model.Nicorepo;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NicorepoTest {
    @Test
    public void ニコレポの21時から22時の間の1件だけをフィルタして取り出せる() throws IOException {
        JSONObject jsonObject = new JSONObject(createNicorepoJson());
        Nicorepo nicorepo = new Nicorepo(jsonObject, 200);
        List<JSONObject> filterdReports = nicorepo.getReportsAfterDatetime(LocalDateTime.of(2019, 2, 27, 21, 0, 0));

        assertThat(filterdReports.size(), is(1));
    }

    private String createNicorepoJson() throws IOException {
        return Files.lines(Paths.get("document/nicorepo-response.json"), Charset.forName("UTF-8"))
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }
}
