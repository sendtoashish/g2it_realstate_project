package com.g2it.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.g2it.realestate.model.User;

@Repository
public interface AuthenticationDAO extends JpaRepository <User, Long> {

	@Query("SELECT u FROM user_details u WHERE u.email = ?1 and u.active = true")
    public User findByUserName(String email);
}
