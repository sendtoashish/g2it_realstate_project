package com.g2it.realestate.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.g2it.realestate.utils.JWTConstant;

import lombok.Data;

/**
 * Class for handling authentication tokens
 *
 */

@Data
public class Tokens implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token_type = JWTConstant.JWT.name() ;
    
    private String access_token;
    
    private LocalDateTime access_token_validity;

    public Tokens(String token, LocalDateTime tokenValidity) {
        this.access_token = token;
        this.access_token_validity = tokenValidity;
    }
}
