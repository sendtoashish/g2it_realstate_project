package com.g2it.realestate.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.g2it.realestate.exception.ApplicationException;
import com.g2it.realestate.exception.ErrorStatus;
import com.g2it.realestate.model.Company;
import com.g2it.realestate.model.Role;
import com.g2it.realestate.model.User;
import com.g2it.realestate.repository.CompanyDAO;
import com.g2it.realestate.repository.RoleDAO;
import com.g2it.realestate.repository.UserDAO;
import com.g2it.realestate.utils.PasswordUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private UserDAO userDAO;
	
	@Value("${spring.default.password}")
	private String password;

	@Override
	@Transactional("transactionManager")
	public void addCompany(Company company) {
		log.info("Service started: save company details");
		company.setCompanyType("2");
		company.setActive(true);
		Company c = companyDAO.save(company);
		Role r = roleDAO.findByRoleId(2l);
		User u = new User();
		u.setEmail(company.getEmail());
		u.setFirstName(company.getCompanyName());
		u.setPassword(password);
		u.setCompany(c);
		u.setRole(r);
		u.setActive(true);
		u.setSubmittedOn(new Timestamp(System.currentTimeMillis()));
		userDAO.save(u);
		log.info("Service end: Company details saved successfully");
	}

	@Override
	@Transactional("transactionManager")
	public List<Company> getAllCompany() {
		log.info("Service started: get all company details");
		List<Company> companyList = companyDAO.findAll();
		log.info("Service end: company details fetched successfully");
		return companyList;
	}
	
	@Override
	@Transactional("transactionManager")
	public List<Company> getCompanyByStatus(Boolean status) {
		log.info("Service started: get all company details based on status");
		List<Company> companyList = companyDAO.findCompanyByStatus(status);
		log.info("Service end: company details fetched successfully based on status");
		return companyList;	
	}

	@Override
	@Transactional("transactionManager")
	public Company getCompanyByID(Long companyId) {
		log.info("Service started: get company detail by company id: " + companyId);
		Company company = companyDAO.getById(companyId);
		log.info("Service end: company details get successfully by company id: " + companyId);
		return company;
	}
	
	@Override
	@Transactional("transactionManager")
	public Company findByCompanyName(String name) {
		log.info("Service started: get company detail by company name: " + name);
		Company company = companyDAO.findByCompanyName(name);
		log.info("Service end: company details get successfully by company name: " + name);
		return company;
	}
	
	@Override
	@Transactional("transactionManager")
	public Company findByCompanyEmail(String email) {
		log.info("Service started: get company detail by email: " + email);
		Company company = companyDAO.findByCompanyEmail(email);
		log.info("Service end: company details get successfully by email: " + email);
		return company;
	}

	@Override
	@Transactional("transactionManager")
	public void deleteCompanyById(Long companyId) {
		log.info("Service started: delete company details by company id: " + companyId);
		try {
			userDAO.deleteUserbyCompanyId(companyId);
			companyDAO.deleteById(companyId);
		log.info("Service ended: company deleted successfully : " + companyId);
		} catch (ApplicationException e) {
			log.error("Can't delete this Company. Users and Property may be associate with this company");
			throw new ApplicationException(ErrorStatus.NOT_ACCEPTABLE, "Can't delete this Company");
		}

	}

	@Override
	@Transactional("transactionManager")
	public void updateCompany(Company company) {
		log.info("Service started: update company details");
		Optional<Company> com = companyDAO.findById(company.getId());
		if (com.isEmpty()) {
			throw new ApplicationException(ErrorStatus.NOT_FOUND,
					"company details not found by company id: " + company.getId());
		}
		com.get().setCompanyName(company.getCompanyName());
		com.get().setEmail(company.getEmail());
		com.get().setPhone(company.getPhone());
		com.get().setStreet(company.getStreet());
		com.get().setStreetNumber(company.getStreetNumber());
		com.get().setCity(company.getCity());
		com.get().setZip(company.getZip());
		com.get().setVatId(company.getVatId());
		com.get().setTaxId(company.getTaxId());
		com.get().setModifiedOn(new Timestamp(System.currentTimeMillis()));
		companyDAO.save(com.get());
		log.info("Service end: company details updated successfully");
	}

	@Override
	@Transactional("transactionManager")
	public void updateCompanyStatus(Company company) {
		log.info("Service started: update company status");
		Optional<Company> com = companyDAO.findById(company.getId());
		if (com.isEmpty()) {
			throw new ApplicationException(ErrorStatus.NOT_FOUND,
					"company details not found by company id: " + company.getId());
		}
		com.get().setActive(company.isActive());
		companyDAO.save(com.get());
		log.info("Service end: company status updated successfully");
	}

	// Role Related Service..
	@Override
	@Transactional("transactionManager")
	public List<Role> getAllRole() {
		log.info("Service started: get all role");
		List<Role> roleList = roleDAO.findAll();
		log.info("Service end: Role fetched successfully");
		return roleList;
	}

	@Override
	@Transactional("transactionManager")
	public void addRole(Role role) {
		log.info("Service started: save role details");
		roleDAO.save(role);
		log.info("Service end: role saved successfully");
	}

	// User Related Service..
	@Override
	@Transactional("transactionManager")
	public void addUser(User user) {
		// This method will add user only for Broker Role.
		log.info("Service started: save user details");
		Role r = roleDAO.findByRoleId(3l);
		if(r==null) {
            throw new ApplicationException(ErrorStatus.NOT_FOUND, "Role, Broker Not found");
        }else {
        	user.setRole(r);
        	user.setActive(true);
    		userDAO.save(user);
    		log.info("Service end: User saved successfully to DB");	
        }
	}

	@Override
	@Transactional("transactionManager")
	public List<User> getAllUser() {
		log.info("Service started: get all user details");
		List<User> userList = userDAO.findAll();
		log.info("Service end: all user details fetched successfully");
		return userList;
	}

	@Override
	@Transactional("transactionManager")
	public User getUserbyId(Long userId) {
		log.info("Service started: get user by userID" + userId);
		User user = userDAO.getById(userId);
		log.info("Service end: user fetched successfully by id");
		return user;
	}
	
	@Override
	@Transactional("transactionManager")
	public List<User> getUserByStatus(Boolean status) {
		log.info("Service started: get all users based on status");
		List<User> userList = userDAO.findUserByStatus(status);
		log.info("Service end: Users fetched successfully based on status");
		return userList;	
	}

	@Override
	@Transactional("transactionManager")
	public User getUserByEmail(String email) {
		log.info("Service started: get user by email" + email);
		User user = userDAO.findByUserEmail(email);
		log.info("Service end: user fetched successfully by email");
		return user;
	}
	
	@Override
	@Transactional("transactionManager")
	public void deleteUserById(Long userId) {
		log.info("Service started: delete user by user id: " + userId);
		User u =userDAO.getById(userId);
		if(u.getRole().getRole().equals("Broker")) {
			userDAO.deleteById(userId);
			//TODO WHEN USE DELETE THEN ALL RELATED PROPERTY AND CLIENT SHOULD BE DELETE
			log.info("user deleted successfully : " + userId);
		}else {
			log.error("Can't delete Admin/SuperAdmin User");
			throw new ApplicationException(ErrorStatus.INVALID_REQUEST, "Can't delete Admin/SuperAdmin User");			
		}
	}

	@Override
	public void updateUser(User user) {
		log.info("Service started: update user details");
		Optional<User> usr = userDAO.findById(user.getId());
		if (usr.isEmpty()) {
			throw new ApplicationException(ErrorStatus.NOT_FOUND,
					"user not found by id: " + user.getId());
		}
		usr.get().setFirstName(user.getFirstName());
		usr.get().setLastName(user.getLastName());
		usr.get().setEmail(user.getEmail());
		usr.get().setMobileNo(user.getMobileNo());
		usr.get().setAddress(user.getAddress());
		usr.get().setCity(user.getCity());
		usr.get().setZip(user.getZip());
		usr.get().setPassword(PasswordUtils.decrypt(usr.get().getPassword()));
		usr.get().setModifiedOn(new Timestamp(System.currentTimeMillis()));
		userDAO.save(usr.get());
		log.info("Service end: User updated successfully");		
	}

	@Override
	public void updateUserStatus(User user) {
		log.info("Service started: update user status");
		Optional<User> usr = userDAO.findById(user.getId());
		if (usr.isEmpty()) {
			throw new ApplicationException(ErrorStatus.NOT_FOUND,
					"User details not found by id: " + user.getId());
		}
		usr.get().setActive(user.isActive());
		userDAO.save(usr.get());
		log.info("Service end: user status updated successfully");
		
	}

	@Override
	public List<User> getAllUserByUserId(Long id) {
		log.info("Service started: get all user details by user id");
		List<User> userList = null;
		User user = userDAO.getById(id);
		if(user != null && user.getRole().getRoleId() == 1) {
			userList = userDAO.findAll();
		}else if(user != null && user.getRole().getRoleId() == 2) {
			userList = userDAO.findByCompanyId(user.getCompany().getId());
		}else {
			userList = userDAO.findByUserId(user.getId());
		}
		log.info("Service end: client details fetched successfully by user");
		return userList;
	}
}
