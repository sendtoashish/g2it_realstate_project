package com.g2it.realestate.service;

import com.g2it.realestate.model.CredentialsPayload;
import com.g2it.realestate.model.SessionData;
import com.g2it.realestate.model.Tokens;
import com.g2it.realestate.model.User;

public interface AuthenticationService {

	public Tokens login(CredentialsPayload cp);

	public User findUserByUserName(String UserName);

	public SessionData findByToken();

	public Tokens refreshToken();

}
