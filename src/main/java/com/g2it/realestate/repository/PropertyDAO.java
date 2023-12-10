package com.g2it.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.g2it.realestate.model.Property;

@Repository
public interface PropertyDAO extends JpaRepository <Property, Long> {
	
	@Query("SELECT p FROM property_details p WHERE p.propertyName = ?1")
    public Property findByName(String name);
	
	@Query("SELECT p FROM property_details p WHERE p.user.company.id = ?1")
    public List<Property> findByCompanyId(Long companyId);
	
	@Query("SELECT p FROM property_details p WHERE p.user.id = ?1")
    public List<Property> findByUserId(Long userId);
}
