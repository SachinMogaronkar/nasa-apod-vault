package com.openapi.nasa.service;

import com.openapi.nasa.daorepo.NasaRepository;
import com.openapi.nasa.daorepo.UserRepository;
import com.openapi.nasa.entity.AppUser;
import com.openapi.nasa.entity.NasaApod;
import com.openapi.nasa.exceptions.NasaNotFoundException;
import com.openapi.nasa.responsedto.NasaApodResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApodServiceImp implements ApodService {

    private final NasaRepository repository;
    private final UserRepository userRepository;

    public ApodServiceImp(NasaRepository repository,
                          UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<NasaApodResponse> getAll() {

        AppUser user = getCurrentUser();

        List<NasaApod> list =
                repository.findAllByUserOrderByDateDesc(user);

        if (list.isEmpty()) {
            throw new NasaNotFoundException("No saved APODs found");
        }

        return list.stream().map(this::map).toList();
    }

    @Override
    public void deleteById(Integer id) {

        NasaApod apod = repository.findById(id)
                .orElseThrow(() -> new NasaNotFoundException("APOD not found: " + id));

        AppUser user = getCurrentUser();

        if (apod.getUser().getId()!= user.getId()) {
            throw new RuntimeException("Unauthorized delete");
        }

        repository.delete(apod);
    }

    @Override
    public void deleteAll() {

        AppUser user = getCurrentUser();

        List<NasaApod> list = repository.findAllByUserOrderByDateDesc(user);

        repository.deleteAll(list);
    }

    private NasaApodResponse map(NasaApod e) {
        return new NasaApodResponse(
                e.getId(),
                e.getDate().toString(),
                e.getExplanation(),
                e.getHdUrl(),
                e.getTitle(),
                e.getUrl()
        );
    }
}