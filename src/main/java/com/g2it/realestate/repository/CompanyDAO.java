package com.g2it.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.g2it.realestate.model.Company;

@Repository
public interface CompanyDAO extends JpaRepository <Company, Long> {
	
	@Query("SELECT c FROM company_details c WHERE c.companyName = ?1")
    public Company findByCompanyName(String name);
	
	@Query("SELECT c FROM company_details c WHERE c.active = ?1 and c.companyType = 2")
    public List<Company> findCompanyByStatus(Boolean status);
	
	@Query("SELECT c FROM company_details c WHERE c.email = ?1")
    public Company findByCompanyEmail(String email);
}
