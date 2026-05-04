package com.openapi.nasa.daorepo;

import java.util.Optional;

import com.openapi.nasa.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);
}