package com.bank.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.demo.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByMpinAndId(String mpin, Long id); // For MPIN validation
}
