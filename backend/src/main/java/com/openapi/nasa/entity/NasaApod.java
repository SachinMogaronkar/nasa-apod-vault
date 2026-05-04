package com.openapi.nasa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "nasa_apod",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "date"})
        }
)
public class NasaApod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 1000)
    private String title;

    @Column(length = 5000)
    private String explanation;

    private String url;

    private String hdUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getHdUrl() { return hdUrl; }
    public void setHdUrl(String hdUrl) { this.hdUrl = hdUrl; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
}