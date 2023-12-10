package com.g2it.realestate.model;

import java.io.Serializable;

import lombok.Data;

/**
 * A helper class for handling user credentials payload.
 */
@Data
public class CredentialsPayload implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;

	public CredentialsPayload(String userName, String userPwd) {
		this.userName = userName;
		this.password = userPwd;
	}

}
