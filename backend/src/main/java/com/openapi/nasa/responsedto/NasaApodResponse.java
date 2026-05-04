package com.openapi.nasa.responsedto;

public class NasaApodResponse {

    private Integer id;
    private String date;
    private String explanation;
    private String hdUrl;
    private String title;
    private String url;

    public NasaApodResponse(Integer id, String date, String explanation,
                            String hdUrl, String title, String url) {
        this.id = id;
        this.date = date;
        this.explanation = explanation;
        this.hdUrl = hdUrl;
        this.title = title;
        this.url = url;
    }

    public Integer getId() { return id; }
    public String getDate() { return date; }
    public String getExplanation() { return explanation; }
    public String getHdUrl() { return hdUrl; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
}