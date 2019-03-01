package com.serverless.nicorepo.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public List<JSONObject> getReportsAfterDatetime(LocalDateTime datetime) {
        JSONArray array = (JSONArray) reports.get("data");
        List<JSONObject> filterdReports = StreamSupport.stream(array.spliterator(), false)
                .map(JSONObject.class::cast)
                .filter(node ->
                        LocalDateTime.parse(
                                node.get("createdAt").toString(),
                                DateTimeFormatter.ISO_ZONED_DATE_TIME
                        )
                        .isAfter(datetime)
                )
                .collect(Collectors.toList());
        return filterdReports;
    }
}
