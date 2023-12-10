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
import com.g2it.realestate.model.Client;
import com.g2it.realestate.service.PropertyService;
import com.g2it.realestate.utils.Secured;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "client", tags = "Client Management")
@RequestMapping(value = "/api/client")
@Slf4j
public class ClientResource {

	@Autowired
	private PropertyService propertyService;

	@ApiOperation(value = "Get Client Content", notes = "It will return all Client List from the Client.", response = Client.class)
	@GetMapping("/findAll")
	@Secured
	public ResponseEntity<List<Client>> getAllClient() {
		log.info("Fetching Client List");
		List<Client> client = propertyService.getAllClient();
		log.info("Client List fetched successfully");
		return ResponseEntity.ok().body(client);
	}
	
	@ApiOperation(value = "Get Client Content", notes = "It will return all Client List from the Client.", response = Client.class)
	@GetMapping("/findAllByUserId")
	@Secured
	public ResponseEntity<List<Client>> getAllClient(@RequestParam Long id) {
		log.info("Fetching Client List");
		List<Client> client = propertyService.getAllClientByUser(id);
		log.info("Client List fetched successfully");
		return ResponseEntity.ok().body(client);
	}

	@ApiOperation(value = "Add new Client", notes = "It will create a new Client in Client List. Please provide unique Client details.")
	@PostMapping
	@Secured
	public ResponseEntity<String> addClient(@RequestBody Client client) {
		log.info("Adding new Client");
		Client c = propertyService.findClientByEmail(client.getEmail());
		if (c == null) {
			client.setSubmittedOn(new Timestamp(System.currentTimeMillis()));
			propertyService.addClient(client);
			log.info("New Client added successfully");
		} else {
			log.error("Client is already registerd, " + client);
			return new ResponseEntity<>("{\"error\":\"Client is already registerd.\"}", HttpStatus.ALREADY_REPORTED);
		}
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Delete Client", notes = "It will delete a Client from Client List. Please provide valid clientId which you have to delete.")
	@DeleteMapping("{clientId}")
	@Secured
	public ResponseEntity<String> deleteClient(@PathVariable Long clientId) {
		log.info("Deleting Client");
		propertyService.deleteClientById(clientId);
		log.info("Client deleted successfully");
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Update Client", notes = "It will update the existing client which is already in List. Please provide valid updated details of Client.")
	@PutMapping
	@Secured
	public ResponseEntity<String> updateClient(@RequestBody Client client) {
		log.info("udpating Client");
		try {
			propertyService.updateClient(client);
		} catch (ApplicationException ae) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		log.info("client details updated successfully");
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Get Client by email", notes = "It will return Client based on email.", response = Client.class)
	@GetMapping("/byEmail")
	@Secured
	public ResponseEntity<Client> getClientByEmail(@RequestParam String email) {
		log.info("Resource: Fetching Client based on email");
		Client client = propertyService.findClientByEmail(email);
		log.info("Resource: Client fetched successfully based on email");
		return ResponseEntity.ok().body(client);
	}
}
