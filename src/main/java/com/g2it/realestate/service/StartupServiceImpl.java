package com.g2it.realestate.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.g2it.realestate.model.Company;
import com.g2it.realestate.model.Role;
import com.g2it.realestate.model.User;
import com.g2it.realestate.repository.CompanyDAO;
import com.g2it.realestate.repository.RoleDAO;
import com.g2it.realestate.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StartupServiceImpl implements StartupService{

	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired 
	private CompanyDAO companyDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Value("${spring.default.password}")
	private String password;
	
	@Override
	@Transactional("transactionManager")
	public void saveDefaultRoles() {
		log.info("Service Started: Save Default Role");
		Role role1 = new Role();
		role1.setRoleId(1l);
		role1.setRole("Super_Admin");
		role1.setActive(true);

		Role role2 = new Role();
		role2.setRoleId(2l);
		role2.setRole("Admin");
		role2.setActive(true);
		
		Role role3 = new Role();
		role3.setRoleId(3l);
		role3.setRole("Broker");
		role3.setActive(true);
		
		List<Role> defaultRoleList = new ArrayList<Role>();
		defaultRoleList.add(role1);
		defaultRoleList.add(role2);
		defaultRoleList.add(role3);
		log.debug("Default Role created");
		log.debug("Service: fetching all saved role list");
		List<Role> roleList = roleDAO.findAll();
		log.debug("Service: fetched all role list");
		if (roleList != null) {
			for(Role dr: roleList) {
			defaultRoleList.removeIf(x -> dr.getRole().equals(x.getRole()));
			}
			if(!defaultRoleList.isEmpty()) {
				roleDAO.saveAll(defaultRoleList );	
			}
		} else {
			roleDAO.saveAll(defaultRoleList );
		}
		log.info("Service Ended: Default Role Saved successfully");
	}

	@Override
	@Transactional("transactionManager")
	public void saveDefaultCompany() {
		log.info("Service Started: Save Default Company");
		Company company1 = new Company();
		company1.setCompanyId("1010");
		company1.setCompanyName("Afines Group");
		company1.setEmail("afines@gmail.com");
		company1.setTaxId("T-010101");
		company1.setVatId("V-010101");
		company1.setActive(true);
		company1.setCompanyType("1");
		company1.setSubmittedOn(new Timestamp(System.currentTimeMillis()));

		List<Company> defaultCompany = new ArrayList<Company>();
		defaultCompany.add(company1);
		log.debug("Default Company created");
		log.debug("Service: fetching all saved compnay list");
		List<Company> companyList = companyDAO.findAll();
		log.debug("Service: fetched all compnay list");
		if (companyList != null) {
			for(Company dr: companyList) {
				defaultCompany.removeIf(x -> dr.getCompanyName().equals(x.getCompanyName()));
			}
			if(!defaultCompany.isEmpty()) {
				companyDAO.saveAll(defaultCompany );	
			}
		} else {
			companyDAO.saveAll(defaultCompany );
		}	
		log.info("Service Ended: Default Company Saved successfully");
	}

	@Override
	@Transactional("transactionManager")
	public void saveDefaultUser() {
		log.info("Service Started: Save Default User");
		Company defaultCompany = companyDAO.findByCompanyName("Afines Group");
		User user1 = new User();
		user1.setFirstName(defaultCompany.getCompanyName());
		user1.setCompany(defaultCompany);
		Role superAdminRole = roleDAO.findByRoleName("Super_Admin");
		user1.setRole(superAdminRole);
		user1.setEmail(defaultCompany.getEmail());
		user1.setPassword(password);
		user1.setActive(true);
		user1.setSubmittedOn(new Timestamp(System.currentTimeMillis()));
	
		List<User> defaultUser = new ArrayList<User>();
		defaultUser.add(user1);
		log.debug("Default User created");
		log.debug("Service: fetching all saved user list");
		List<User> userList = userDAO.findAll();
		log.debug("Service: fetched all user list");
		if(userList != null) {
			for(User du: userList) {
				defaultUser.removeIf(x -> du.getEmail().equals(x.getEmail()));
			}
			if(!defaultUser.isEmpty()) {
				userDAO.saveAll(defaultUser);	
			}
		}else {
			userDAO.saveAll(defaultUser);
		}
		log.info("Service Ended: Default User Saved successfully");
	}

}
