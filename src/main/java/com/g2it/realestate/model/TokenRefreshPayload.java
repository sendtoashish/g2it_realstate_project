package com.g2it.realestate.model;

import java.io.Serializable;

import lombok.Data;

/**
 * A helper class for handling token refresh payload.
 */
@Data
public class TokenRefreshPayload implements Serializable {
    
    private static final long serialVersionUID = 1L;
    public static final String GRANT_TYPE = "refresh_token";
    
    private String refresh_token;
    private String grant_type;
     
}
