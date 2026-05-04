package com.openapi.nasa.service;

import com.openapi.nasa.responsedto.NasaApodResponse;

public interface NasaApiService {

    NasaApodResponse fetchAndSaveToday();

    NasaApodResponse getApod();

    NasaApodResponse getApodByDate(String date);

    NasaApodResponse fetchAndSaveByDate(String date);
}