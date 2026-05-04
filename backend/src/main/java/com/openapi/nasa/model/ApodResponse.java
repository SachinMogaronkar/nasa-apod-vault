package com.openapi.nasa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApodResponse {

    private String date;
    private String explanation;

    @JsonProperty("hdurl")
    private String hdUrl;

    private String title;
    private String url;

    @JsonProperty("media_type")
    private String mediaType;

    public String getDate() { return date; }
    public String getExplanation() { return explanation; }
    public String getHdUrl() { return hdUrl; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getMediaType() { return mediaType; }
}