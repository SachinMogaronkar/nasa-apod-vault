package com.openapi.nasa.restcontroller;

import com.openapi.nasa.responsedto.NasaApodResponse;
import com.openapi.nasa.service.ApodService;
import com.openapi.nasa.service.NasaApiService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nasa")
public class NasaApiController {

    private final NasaApiService nasaApiService;
    private final ApodService apodService;

    public NasaApiController(NasaApiService nasaApiService,
                             ApodService apodService) {
        this.nasaApiService = nasaApiService;
        this.apodService = apodService;
    }

    // ================= TODAY =================
    @GetMapping("/apod")
    public ResponseEntity<NasaApodResponse> getToday() {
        return ResponseEntity.ok(nasaApiService.getApod());
    }

    // ================= BY DATE =================
    @GetMapping("/apod/date")
    public ResponseEntity<NasaApodResponse> getByDate(@RequestParam String date) {
        return ResponseEntity.ok(nasaApiService.getApodByDate(date));
    }

    // ================= SAVE TODAY =================
    @PostMapping("/apod/save/today")
    public ResponseEntity<NasaApodResponse> saveToday() {
        return ResponseEntity.ok(nasaApiService.fetchAndSaveToday());
    }

    // ================= SAVE BY DATE =================
    @PostMapping("/apod/save")
    public ResponseEntity<NasaApodResponse> saveByDate(@RequestParam String date) {
        return ResponseEntity.ok(nasaApiService.fetchAndSaveByDate(date));
    }

    // ================= GET SAVED =================
    @GetMapping("/apod/saved")
    public ResponseEntity<List<NasaApodResponse>> getSaved() {
        return ResponseEntity.ok(apodService.getAll());
    }

    // ================= DELETE =================
    @DeleteMapping("/apod/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        apodService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}