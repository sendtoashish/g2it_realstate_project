package com.g2it.realestate.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g2it.realestate.model.Role;
import com.g2it.realestate.service.CompanyService;
import com.g2it.realestate.utils.Secured;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "rolepage", tags = "Role Management")
@RequestMapping(value = "/api/role")
@Slf4j
public class RoleResource {

	@Autowired
	private CompanyService companyService;

	@ApiOperation(value = "Get Role Content", notes = "it will returen all type of roles from the role", response = Role.class)
	@GetMapping("/findAll")
	@Secured
	public ResponseEntity<List<Role>> getAllRole() {
		log.info("Fetching Role content");
		List<Role> role = companyService.getAllRole();
		log.info("Role fetched successfully..");
		return ResponseEntity.ok().body(role);
	}

	@ApiOperation(value = "Add new Role", notes = "It will create a new role type in Role List.", response = Role.class)
	@PostMapping
	@Secured
	public ResponseEntity<String> addRole(@RequestBody Role role) {
		log.info("Adding new Role");
		companyService.addRole(role);
		log.info("New Role added successfully..");
		return ResponseEntity.ok().build();
	}
}
