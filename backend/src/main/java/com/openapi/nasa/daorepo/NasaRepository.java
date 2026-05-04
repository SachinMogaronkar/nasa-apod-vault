package com.openapi.nasa.daorepo;

import com.openapi.nasa.entity.AppUser;
import com.openapi.nasa.entity.NasaApod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NasaRepository extends JpaRepository<NasaApod, Integer> {

    boolean existsByUserAndDate(AppUser user, LocalDate date);

    List<NasaApod> findAllByUserOrderByDateDesc(AppUser user);

    NasaApod findByUserAndDate(AppUser user, LocalDate date);
}