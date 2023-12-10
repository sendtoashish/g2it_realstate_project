package com.g2it.realestate.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.g2it.realestate.model.CredentialsPayload;
import com.g2it.realestate.model.SessionData;
import com.g2it.realestate.model.Tokens;
import com.g2it.realestate.model.User;
import com.g2it.realestate.repository.AuthenticationDAO;
import com.g2it.realestate.repository.SessionDataDAO;
import com.g2it.realestate.utils.JwtTokenUtil;
import com.g2it.realestate.utils.PasswordUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationDAO authenticationDAO;

	@Autowired
	private SessionDataDAO sessionDataDAO;
	
	@Override
	@Transactional("transactionManager")
	public Tokens login(CredentialsPayload cp) {
		log.info("Trying to login..");
		User user = authenticationDAO.findByUserName(cp.getUserName());
		String encryptedPwd = PasswordUtils.encrypt(cp.getPassword());
		log.debug("user fetched by User Name");
		if (user != null && encryptedPwd.equals(user.getPassword())) {
			log.debug("User Verified and trying to Generate Token..");
			return generateToken(cp);
		}
		log.info("NULL Token Returned..");
		return null;
	}
	
	@Override
	@Transactional("transactionManager")
	public User findUserByUserName(String UserName) {
		log.info("getting user by UserName");
		User user = authenticationDAO.findByUserName(UserName);
		log.info("User fetched by userName");
		return user;
	}
	
	@Override
	@Transactional("transactionManager")
	public SessionData findByToken() {
		log.info("getting sessionData by Token");
		String token = getBearerTokenHeader();
		SessionData sessionData = sessionDataDAO.findByToken(token);
		log.info("sessionData fetched successfully by Token");
		return sessionData;
	}
	
	@Override
	@Transactional("transactionManager")
	public Tokens refreshToken() {
		log.info("Trying to refresh Token..");
		String accessToken = getBearerTokenHeader();
		Tokens token = JwtTokenUtil.refreshToken();
		log.debug("token refreshed..");
		SessionData sessionData = sessionDataDAO.findByToken(accessToken);
		sessionData.setAccessToken(token.getAccess_token());
		sessionData.setCreatedOn(LocalDateTime.now());
		sessionData.setLoginCount(sessionData.getLoginCount() + 1);
		sessionDataDAO.save(sessionData);
		log.info("Token refreshed, save to sessionData and return");
		return token;
	}
	
	@Transactional("transactionManager")
	private Tokens generateToken(CredentialsPayload cp) {
		log.info("Generating Token");
		Tokens token = JwtTokenUtil.generateTokens(cp);
		log.debug("token generated..");
		SessionData sessionData = new SessionData();
		sessionData.setTokenType(token.getToken_type());
		sessionData.setAccessToken(token.getAccess_token());
		sessionData.setLoginId(cp.getUserName());
		sessionData.setCreatedOn(LocalDateTime.now());
		sessionData.setLoginCount(1);
		SessionData sd = sessionDataDAO.findByLoginId(cp.getUserName());
		if(sd != null) {
			log.debug("deleting previous sessionData if exist, before save new sessionData");
			sessionDataDAO.delete(sd);
		}
		log.debug("saving token detail to sessionData..");
		sessionDataDAO.save(sessionData);
		log.info("Token Generated and saved to sessionData");
		return token;
	}
	
	private String getBearerTokenHeader() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization");
	}
}
