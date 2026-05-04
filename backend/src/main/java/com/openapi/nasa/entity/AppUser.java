package com.openapi.nasa.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<NasaApod> apods;

    public AppUser() {}

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // getters + setters
    public int getId() { return id; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getRole() { return role; }

    public List<NasaApod> getApods() { return apods; }

    public void setId(int id) { this.id = id; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setRole(String role) { this.role = role; }

    public void setApods(List<NasaApod> apods) { this.apods = apods; }
}