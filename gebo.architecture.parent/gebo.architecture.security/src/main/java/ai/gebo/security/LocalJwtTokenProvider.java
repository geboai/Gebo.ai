/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import ai.gebo.security.config.GeboSecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Service responsible for creating and validating JSON Web Tokens (JWT) for the
 * application. It utilizes JWT for secure communication between client and
 * server by verifying the authenticity of tokens.
 * 
 * AI generated comments
 */
@Service
public class LocalJwtTokenProvider {

	// Logger instance for logging events
	private static final Logger logger = LoggerFactory.getLogger(LocalJwtTokenProvider.class);
	public static final String TEMPORARY_OAUTH2_COOKIENAME = "GeboTemporaryAcquired";
	// Configuration properties for security settings
	private GeboSecurityConfig appProperties;

	/**
	 * Constructs a TokenProvider with specified application security properties.
	 *
	 * @param appProperties the Gebo application security properties
	 */
	public LocalJwtTokenProvider(GeboSecurityConfig appProperties) {
		this.appProperties = appProperties;
	}

	/**
	 * Generates a JWT for authenticated users.
	 *
	 * @param authentication the authentication object containing user's details
	 * @return a signed JWT as a String
	 */
	public String createToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		// Define token expiration date
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

		// Build and sign the JWT
		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret()).compact();
	}

	/**
	 * Extracts the user ID from the JWT.
	 *
	 * @param token the JWT as a String
	 * @return the user ID contained in the token
	 */
	public String getUserIdFromToken(String token) {
		// Parse the JWT to get claims
		Claims claims = Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}

	/**
	 * Validates the given JWT to ensure it is correct and not expired or tampered
	 * with.
	 *
	 * @param authToken the JWT as a String
	 * @return true if the token is valid, false otherwise
	 */
	public boolean validateToken(String authToken) {
		try {
			// Parse JWT to validate it
			Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature", ex);
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token", ex);
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token", ex);
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token", ex);
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.", ex);
		}
		return false;
	}

	public String createToken(DefaultOidcUser principal) {
		Date now = new Date();
		// Define token expiration date
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
		Instant expiring = principal.getExpiresAt();
		if (expiring != null) {
			expiryDate = Date.from(expiring);
		}
		// Build and sign the JWT
		return Jwts.builder().setSubject(principal.getEmail()).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret()).compact();
	}

}