package com.g2it.realestate.resource;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.g2it.realestate.exception.ApplicationException;
import com.g2it.realestate.model.Property;
import com.g2it.realestate.model.User;
import com.g2it.realestate.service.AuthenticationService;
import com.g2it.realestate.service.CompanyService;
import com.g2it.realestate.utils.Secured;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "user", tags = "User Management")
@RequestMapping(value = "/api/user")
@Slf4j
public class UserResource {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private AuthenticationService authenticationService;

	@ApiOperation(value = "Get User List", notes = "It will return the List of All Users who are able to use our businees unit")
	@GetMapping("/findAll")
	@Secured
	public ResponseEntity<List<User>> getAllusers() {
		log.info("Fetching User List");
		List<User> userList = companyService.getAllUser();
		log.info("User List Fetched successfully");
		return ResponseEntity.ok().body(userList);
	}
	
	@Secured
	@ApiOperation(value = "Find user by User Name", notes = "It will return user of the requesed User Name. you have to pass a vaid user name")
	@GetMapping("/findbyUserName")
	public ResponseEntity<User> findUserByUserName(@RequestParam String userName) {
		log.info("Finding user from requested User Name");
		User user = authenticationService.findUserByUserName(userName);
		log.info("user data fetched successful by requested User Name");
		return ResponseEntity.ok().body(user);
	}
	
	@ApiOperation(value = "Get User by Status", notes = "It will return the List of All Users whose status is Active/Deactive")
	@GetMapping("{status}")
	@Secured
	public ResponseEntity<List<User>> getUserByStatus(@PathVariable("status") Boolean status) {
		log.info("Fetching User List bases on status");
		List<User> userList = companyService.getUserByStatus(status);
		log.info("User List Fetched successfully bases on status");
		return ResponseEntity.ok().body(userList);
	}

	@ApiOperation(value = "Add new Users", notes = "It will create a new User and User role will be BROKER ONLY. Please provide unique User details with valid 'CompanyId' and 'RoleId'.")
	@PostMapping
	@Secured
	public ResponseEntity<String> addUser(@RequestBody User user) {
		log.info("Adding new User");
		User u = companyService.getUserByEmail(user.getEmail());
		if(u == null) {
			user.setSubmittedOn(new Timestamp(System.currentTimeMillis()));
			companyService.addUser(user);
			log.info("User saved Successfully..");
		}else {
			log.error("User is already registerd, " + user);
			return new ResponseEntity<>("{\"error\":\"User is already registerd.\"}",
					HttpStatus.ALREADY_REPORTED);
		}
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Delete Users", notes = "It will delete a user from user List. Please provide valid UserId which you have to delete.")
	@DeleteMapping("{userId}")  
	@Secured
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
		log.info("Deleting User");
		try {
		companyService.deleteUserById(userId);
		}catch(ApplicationException e) {
			log.error("Could not delete this user");
			return new ResponseEntity<>("{\"error\":\"Can't delete Admin User.\"}",
					HttpStatus.METHOD_NOT_ALLOWED);
		}
		log.info("user deleted successfully");
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Update Users", notes = "It will update the existing user which is already in List. Please provide valid updated details of User.")
	@PutMapping
	@Secured
	public ResponseEntity<String> updateUser(@RequestBody User user) {
		log.info("Updating User");
		companyService.updateUser(user);
		log.info("user updated successfully");
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Update User Status", notes = "It will update the user status (Active/Inactive). Please provide valid User id and status.")
	@PutMapping("{id}/{status}")
	@Secured
	public ResponseEntity<String> updateUserStatus(@PathVariable("status") Boolean status, @PathVariable("id") Long id) {
		log.info("Updating User Status");
		User user = new User();
		user.setActive(status);
		user.setId(id);
		companyService.updateUserStatus(user);
		log.info("User status updated successfully");
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Get User by email", notes = "It will return User based on email.", response = User.class)
	@GetMapping("/byEmail")
	@Secured
	public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
		log.info("Resource: Fetching User based on email");
		User user = companyService.getUserByEmail(email);
		log.info("Resource: User fetched successfully based on email");
		return ResponseEntity.ok().body(user);
	}
	
	@ApiOperation(value = "Get Users Content", notes = "It will return all Users List from the User based on User's role.", response = Property.class)
	@GetMapping("/findAllByUserId")
	@Secured
	public ResponseEntity<List<User>> getAllUser(@RequestParam Long id) {
		log.info("Fetching User List");
		List<User> user = companyService.getAllUserByUserId(id);
		log.info("User List fetched successfully");
		return ResponseEntity.ok().body(user);
	}
}
