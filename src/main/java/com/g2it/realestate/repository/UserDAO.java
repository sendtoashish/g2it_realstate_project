package com.g2it.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.g2it.realestate.model.User;

@Repository
public interface UserDAO extends JpaRepository <User, Long> {
	
	@Transactional
	@Modifying
	@Query("DELETE FROM user_details WHERE COMPANY_ID = ?1")
    public void deleteUserbyCompanyId(Long CompanyId);
	
	@Query("SELECT u FROM user_details u WHERE active = ?1 and ROLE_ID>1")
    public List<User> findUserByStatus(Boolean status);
	
	@Query("SELECT u FROM user_details u WHERE u.email = ?1")
    public User findByUserEmail(String email);
	
	@Query("SELECT u FROM user_details u WHERE u.company.id = ?1 and active = true")
    public List<User> findByCompanyId(Long companyId);
	
	@Query("SELECT u FROM user_details u WHERE u.id = ?1 and active = true")
    public List<User> findByUserId(Long userId);
}
