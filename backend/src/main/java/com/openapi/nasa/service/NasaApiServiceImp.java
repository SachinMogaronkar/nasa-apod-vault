package com.openapi.nasa.service;

import com.openapi.nasa.daorepo.NasaRepository;
import com.openapi.nasa.daorepo.UserRepository;
import com.openapi.nasa.entity.AppUser;
import com.openapi.nasa.entity.NasaApod;
import com.openapi.nasa.exceptions.NasaNotFoundException;
import com.openapi.nasa.model.ApodResponse;
import com.openapi.nasa.responsedto.NasaApodResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class NasaApiServiceImp implements NasaApiService {

    @Value("${nasa.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final NasaRepository repository;
    private final UserRepository userRepository;

    public NasaApiServiceImp(RestTemplate restTemplate,
                             NasaRepository repository,
                             UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // ================= GET CURRENT USER =================
    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ================= TODAY APOD =================
    @Override
    public NasaApodResponse getApod() {

        String url = "https://api.nasa.gov/planetary/apod?api_key=" + apiKey;

        ApodResponse res = restTemplate.getForObject(url, ApodResponse.class);

        if (res == null) {
            throw new NasaNotFoundException("Failed to fetch APOD");
        }

        if (!"image".equalsIgnoreCase(res.getMediaType())) {
            throw new NasaNotFoundException("APOD is not an image");
        }

        return mapToDto(res);
    }

    // ================= APOD BY DATE =================
    @Override
    public NasaApodResponse getApodByDate(String date) {

        String url = "https://api.nasa.gov/planetary/apod?date="
                + date + "&api_key=" + apiKey;

        ApodResponse res = restTemplate.getForObject(url, ApodResponse.class);

        if (res == null) {
            throw new NasaNotFoundException("APOD not found for date: " + date);
        }

        if (!"image".equalsIgnoreCase(res.getMediaType())) {
            throw new NasaNotFoundException("APOD is not an image");
        }

        return mapToDto(res);
    }

    // ================= SAVE BY DATE =================
    @Override
    public NasaApodResponse fetchAndSaveByDate(String date) {

        String url = "https://api.nasa.gov/planetary/apod?date="
                + date + "&api_key=" + apiKey;

        ApodResponse res = restTemplate.getForObject(url, ApodResponse.class);

        if (res == null) {
            throw new NasaNotFoundException("APOD not found");
        }

        AppUser user = getCurrentUser();
        LocalDate apodDate = LocalDate.parse(res.getDate());

        if (repository.existsByUserAndDate(user, apodDate)) {
            NasaApod existing = repository.findByUserAndDate(user, apodDate);
            return mapEntityToDto(existing);
        }

        NasaApod entity = buildEntity(res, user, apodDate);

        if (!"image".equalsIgnoreCase(res.getMediaType())) {
            throw new NasaNotFoundException("APOD is not an image");
        }

        return mapEntityToDto(repository.save(entity));
    }

    // ================= SAVE TODAY =================
    @Override
    public NasaApodResponse fetchAndSaveToday() {

        String url = "https://api.nasa.gov/planetary/apod?api_key=" + apiKey;

        ApodResponse res = restTemplate.getForObject(url, ApodResponse.class);

        if (res == null) {
            throw new NasaNotFoundException("Failed to fetch APOD");
        }

        AppUser user = getCurrentUser();
        LocalDate apodDate = LocalDate.parse(res.getDate());

        if (repository.existsByUserAndDate(user, apodDate)) {
            NasaApod existing = repository.findByUserAndDate(user, apodDate);
            return mapEntityToDto(existing);
        }

        NasaApod entity = buildEntity(res, user, apodDate);

        if (!"image".equalsIgnoreCase(res.getMediaType())) {
            throw new NasaNotFoundException("APOD is not an image");
        }

        return mapEntityToDto(repository.save(entity));
    }

    // ================= COMMON BUILDER =================
    private NasaApod buildEntity(ApodResponse res, AppUser user, LocalDate date) {
        NasaApod entity = new NasaApod();
        entity.setDate(date);
        entity.setTitle(res.getTitle());
        entity.setExplanation(res.getExplanation());
        entity.setUrl(res.getUrl());
        entity.setHdUrl(res.getHdUrl());
        entity.setUser(user);
        return entity;
    }

    // ================= MAPPERS =================

    private NasaApodResponse mapToDto(ApodResponse res) {
        return new NasaApodResponse(
                null,
                res.getDate(),
                res.getExplanation(),
                res.getHdUrl(),
                res.getTitle(),
                res.getUrl()
        );
    }

    private NasaApodResponse mapEntityToDto(NasaApod apod) {
        return new NasaApodResponse(
                apod.getId(),
                apod.getDate().toString(),
                apod.getExplanation(),
                apod.getHdUrl(),
                apod.getTitle(),
                apod.getUrl()
        );
    }
}