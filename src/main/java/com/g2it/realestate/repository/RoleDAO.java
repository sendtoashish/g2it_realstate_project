package com.g2it.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.g2it.realestate.model.Role;

@Repository
public interface RoleDAO extends JpaRepository <Role, Long> {
	
	@Query("SELECT r FROM role r WHERE r.role = ?1 and r.active = true")
    public Role findByRoleName(String name);
	
	@Query("SELECT r FROM role r WHERE r.roleId = ?1 and r.active = true")
    public Role findByRoleId(Long roleId);
}
