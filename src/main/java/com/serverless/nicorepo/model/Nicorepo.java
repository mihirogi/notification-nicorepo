package com.serverless.nicorepo.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<JSONObject> getReportsAfterDatetime(String datetime) {
        JSONArray array = (JSONArray) reports.get("data");
        SimpleDateFormat stringParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        List<JSONObject> filterdReports = StreamSupport.stream(array.spliterator(), false)
                .map(JSONObject.class::cast)
                .filter(node -> {
                    try {
                        return stringParser.parse(node.get("createdAt").toString())
                                .after(stringParser.parse(datetime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
        return filterdReports;
    }
}
