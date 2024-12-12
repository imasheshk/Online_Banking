package com.bank.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByMpinAndId(String mpin, Long id); // For MPIN validation
}
