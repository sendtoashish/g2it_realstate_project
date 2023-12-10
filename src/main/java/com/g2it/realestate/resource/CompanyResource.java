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
import com.g2it.realestate.model.Company;
import com.g2it.realestate.model.Role;
import com.g2it.realestate.service.CompanyService;
import com.g2it.realestate.utils.Secured;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "company", tags = "Company Management")
@RequestMapping(value = "/api/company")
@Slf4j
public class CompanyResource {

	@Autowired
	private CompanyService companyService;

	@ApiOperation(value = "Get Company Content", notes = "It will return all Company List (Business Units) from the Company.", response = Role.class)
	@GetMapping("/findAll")
	@Secured
	public ResponseEntity<List<Company>> getAllCompany() {
		log.info("Fetching Company List");
		List<Company> company = companyService.getAllCompany();
		log.info("Company List fetched successfully");
		return ResponseEntity.ok().body(company);
	}

	@ApiOperation(value = "Add new Company", notes = "It will create a new Company in Company List. Please provide unique Company details.")
	@PostMapping
	@Secured
	public ResponseEntity<String> addCompany(@RequestBody Company company) {
		log.info("Adding new Company");
		Company c = companyService.findByCompanyEmail(company.getEmail());
		if (c == null) {
			company.setSubmittedOn(new Timestamp(System.currentTimeMillis()));
			companyService.addCompany(company);
			log.info("New Company added successfully");
		} else {
			log.error("Company is already registered, " + company);
			return new ResponseEntity<>("{\"error\":\"Company is already registered.\"}", HttpStatus.ALREADY_REPORTED);
		}
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Delete Company", notes = "It will delete a Company from Company List. Please provide valid companyId which you have to delete.")
	@DeleteMapping("{companyId}")
	@Secured
	public ResponseEntity<String> deleteCompany(@PathVariable("companyId") Long companyId) {
		log.info("Deleting Company");
		try {
			companyService.deleteCompanyById(companyId);
			log.info("Company deleted successfully");
		} catch (ApplicationException e) {
			log.error("Can't delete this Company. Users and Property may be associate with this company");
			return new ResponseEntity<>("{\"error\":\"Can't delete: Delete associated users first.\"}",
					HttpStatus.METHOD_NOT_ALLOWED);
		}
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Update Company", notes = "It will update the existing company which is already in List. Please provide valid updated details of Company.")
	@PutMapping
	@Secured
	public ResponseEntity<String> updateCompany(@RequestBody Company company) {
		log.info("udpating Company");
		try {
			companyService.updateCompany(company);
		} catch (ApplicationException ae) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		log.info("company details updated successfully");
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Update Company Status", notes = "It will update the company status. Please provide valid status of Company.")
	@PutMapping("{id}/{status}")
	@Secured
	public ResponseEntity<String> updateCompanyStatus(@PathVariable("status") Boolean status,
			@PathVariable("id") Long id) {
		log.info("Resource: udpating Company status");
		try {
			Company company = new Company();
			company.setActive(status);
			company.setId(id);
			company.setSubmittedOn(new Timestamp(System.currentTimeMillis()));
			companyService.updateCompanyStatus(company);
		} catch (ApplicationException ae) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		log.info("Resource: company status updated successfully");
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Get Company by Status", notes = "It will return all Company List based on Active/Deactive company.", response = Company.class)
	@GetMapping("{status}")
	@Secured
	public ResponseEntity<List<Company>> getCompanyByStatus(@PathVariable("status") Boolean status) {
		log.info("Resource: Fetching Company List based on status");
		List<Company> company = companyService.getCompanyByStatus(status);
		log.info("Resource: Company List fetched successfully based on status");
		return ResponseEntity.ok().body(company);
	}

	@ApiOperation(value = "Get Company by name", notes = "It will return all Company List based on company name.", response = Company.class)
	@GetMapping("/byName")
	@Secured
	public ResponseEntity<Company> getCompanyByName(@RequestParam String name) {
		log.info("Resource: Fetching Company List based on name");
		Company company = companyService.findByCompanyName(name);
		log.info("Resource: Company List fetched successfully based on name");
		return ResponseEntity.ok().body(company);
	}

	@ApiOperation(value = "Get Company by email", notes = "It will return all Company List based on company email.", response = Company.class)
	@GetMapping("/byEmail")
	@Secured
	public ResponseEntity<Company> getCompanyByEmail(@RequestParam String email) {
		log.info("Resource: Fetching Company List based on email");
		Company company = companyService.findByCompanyEmail(email);
		log.info("Resource: Company List fetched successfully based on email");
		return ResponseEntity.ok().body(company);
	}
}
