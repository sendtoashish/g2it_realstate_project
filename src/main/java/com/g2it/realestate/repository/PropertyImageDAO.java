package com.g2it.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2it.realestate.model.PropertyImage;

@Repository
public interface PropertyImageDAO extends JpaRepository <PropertyImage, Long> {

}
