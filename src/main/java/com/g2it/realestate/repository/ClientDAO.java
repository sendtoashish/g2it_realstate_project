package com.g2it.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.g2it.realestate.model.Client;

@Repository
public interface ClientDAO extends JpaRepository <Client, Long>  {

	@Query("SELECT c FROM client_details c WHERE c.email = ?1")
    public Client findByEmail(String email);
	
	@Query("SELECT c FROM client_details c WHERE c.user.company.id = ?1")
    public List<Client> findByCompanyId(Long companyId);
	
	@Query("SELECT c FROM client_details c WHERE c.user.id = ?1")
    public List<Client> findByUserId(Long userId);
}
