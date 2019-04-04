package com.cognizant.outreach.microservices.security.config;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;

/**
 * Config JWT.
 * Only one property 'greenapp.security.jwt.secret' is mandatory.
 *
 * @author greenapp
 */
@Getter
@ToString
public class JwtAuthenticationConfig {

    @Value("${greenapp.security.jwt.url:/security/login}")
    private String url;

    @Value("${greenapp.security.jwt.header:apiToken}")
    private String header;

    @Value("${greenapp.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${greenapp.security.jwt.expiration:#{24*60*60}}")
    private int expiration; // default 24 hours

    @Value("${greenapp.security.jwt.secret}")
    private String secret;
}
