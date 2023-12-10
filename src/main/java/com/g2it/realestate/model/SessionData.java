package com.g2it.realestate.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * Holds tokens of signed in users.
 */
@Data
@Entity(name = "session_data")
public class SessionData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "token_type")
	private String tokenType;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "login_id")
	private String loginId;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "login_count")
	private int loginCount;

}
