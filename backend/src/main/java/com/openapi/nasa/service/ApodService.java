package com.openapi.nasa.service;

import com.openapi.nasa.entity.NasaApod;
import com.openapi.nasa.responsedto.NasaApodResponse;

import java.util.List;

public interface ApodService {

    List<NasaApodResponse> getAll();

    void deleteById(Integer id);

    void deleteAll();
}