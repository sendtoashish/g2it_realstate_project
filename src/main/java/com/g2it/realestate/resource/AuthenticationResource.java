package com.g2it.realestate.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g2it.realestate.model.CredentialsPayload;
import com.g2it.realestate.model.SessionData;
import com.g2it.realestate.model.Tokens;
import com.g2it.realestate.service.AuthenticationService;
import com.g2it.realestate.utils.Secured;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "authentication", tags = "Authentication Management")
@RequestMapping(value = "/api/auth")
@Slf4j
public class AuthenticationResource {

	@Autowired
	private AuthenticationService authenticationService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Login and Get Token", notes = "It will return the token after successful login.")
	@PostMapping("/login")
	public ResponseEntity<Tokens> login(@RequestBody CredentialsPayload credentialsPayload) {
		log.info("Trying to login..");
		Tokens token = authenticationService.login(credentialsPayload);
		if(token == null) {
			log.error("Problem to authenticate, user not found");
			return new ResponseEntity("{\"error\":\"Problem to authenticate, user not found.\"}", HttpStatus.BAD_GATEWAY);
		
		}
		log.info("login successful and token returend..");
		return ResponseEntity.ok().body(token);
	}

	@ApiOperation(value = "Refresh Token", notes = "It will refresh the existing token.")
	@GetMapping("/refreshToken")
	@Secured
	public ResponseEntity<Tokens> refreshToken() {
		log.info("Refreshing token");
		Tokens token = authenticationService.refreshToken();
		log.info("token refreshed and returned.");
		return ResponseEntity.ok().body(token);
	}

	@Secured
	@ApiOperation(value = "Find by Token", notes = "It will return session data of the requesed token. you have to pass a vaid token")
	@GetMapping("/findbyToken")
	public ResponseEntity<SessionData> findByToken() {
		log.info("Finding session data from requested token");
		SessionData sessionData = authenticationService.findByToken();
		log.info("session data fetched successful by requested token");
		return ResponseEntity.ok().body(sessionData);
	}
}
