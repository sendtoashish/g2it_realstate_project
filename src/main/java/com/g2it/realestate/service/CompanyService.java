package com.g2it.realestate.service;

import java.util.List;

import com.g2it.realestate.model.Company;
import com.g2it.realestate.model.Role;
import com.g2it.realestate.model.User;

public interface CompanyService {

	//Company
	public void addCompany(Company company);
	
	public List<Company> getAllCompany();
	
	public Company getCompanyByID(Long CompanyId);
	
	public void deleteCompanyById(Long CompanyId);
	
	public void updateCompany(Company company);
	
	public void updateCompanyStatus(Company company);
	
	public List<Company >getCompanyByStatus(Boolean status);
	
	//Role
	public List<Role> getAllRole();
	
	public void addRole(Role role);
	
	//User
	public void addUser(User user);
	
	public List<User> getAllUser();
	
	public User getUserbyId(Long UserId);
	
	public void deleteUserById(Long userId);
	
	public void updateUser(User user);
	
	public void updateUserStatus(User user);
	
	public List<User> getUserByStatus(Boolean status);

	public Company findByCompanyName(String name);

	public Company findByCompanyEmail(String email);

	public User getUserByEmail(String email);
	
	public List<User> getAllUserByUserId(Long id);
}
