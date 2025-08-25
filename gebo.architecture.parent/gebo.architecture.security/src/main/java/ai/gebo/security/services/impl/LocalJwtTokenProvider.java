/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.services.impl;

import java.time.Instant;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.AuthProviderType;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil.XAuthType;
import ai.gebo.security.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
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

	public static final String AUTH_PROVIDER_REGISTRATION_ID = "AUTH_PROVIDER_REGISTRATION_ID";
	public static final String AUTH_PROVIDER_CLAIM = "AUTH_PROVIDER";
	public static final String AUTH_TYPE_CLAIM = "AUTH_TYPE";
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
		JwtBuilder builder = Jwts.builder();
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(AUTH_TYPE_CLAIM, AuthProviderType.LOCAL_JWT.name());
		claims.put(AUTH_PROVIDER_CLAIM, AuthProvider.local.name());
		builder.addClaims(claims);
		// Build and sign the JWT
		return builder.setSubject(userPrincipal.getUsername()).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret()).compact();
	}

	public String createToken(String subject, AuthProviderType providerType, AuthProvider authProvider,
			String registrationId, long expirationTime) {

		// Define token expiration date
		Date expiryDate = new Date(expirationTime);
		JwtBuilder builder = Jwts.builder();
		Map<String, Object> claims = new HashMap<String, Object>();
		if (providerType != null)
			claims.put(AUTH_TYPE_CLAIM, providerType.name());
		if (authProvider != null)
			claims.put(AUTH_PROVIDER_CLAIM, authProvider.name());
		if (registrationId != null)
			claims.put(AUTH_PROVIDER_REGISTRATION_ID, registrationId);
		builder.addClaims(claims);
		// Build and sign the JWT
		return builder.setSubject(subject).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret()).compact();
	}

	public String createToken(DefaultOidcUser principal, AuthProvider authProvider, String registrationId) {
		Date now = new Date();
		// Define token expiration date
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
		Instant expiring = principal.getExpiresAt();
		if (expiring != null) {
			expiryDate = Date.from(expiring);
		}
		JwtBuilder builder = Jwts.builder();
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(AUTH_TYPE_CLAIM, AuthProviderType.OAUTH2.name());
		claims.put(AUTH_PROVIDER_CLAIM, authProvider != null ? authProvider.name() : "UNKNOWN");
		if (registrationId != null)
			claims.put(AUTH_PROVIDER_REGISTRATION_ID, registrationId);
		builder.addClaims(claims);
		// Build and sign the JWT
		return builder.setSubject(principal.getEmail()).setIssuedAt(new Date()).setExpiration(expiryDate)
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

	public SecurityHeaderData renew(SecurityHeaderData data) {
		if (data.getAuthType() == XAuthType.LOCAL_JWT && validateToken(data.getToken())) {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(GregorianCalendar.SECOND, -120);
			Jws<Claims> jwtData = Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret())
					.parseClaimsJws(data.getToken());
			if (jwtData.getBody().getExpiration().before(calendar.getTime())) {
				return data;
			}
			String authProviderType = jwtData.getBody().get(AUTH_TYPE_CLAIM, String.class);
			String authProvider = jwtData.getBody().get(AUTH_PROVIDER_CLAIM, String.class);
			String registrationId = jwtData.getBody().get(AUTH_PROVIDER_REGISTRATION_ID, String.class);
			AuthProviderType providerType = authProviderType != null ? AuthProviderType.valueOf(authProviderType)
					: null;
			AuthProvider provider = authProvider != null ? AuthProvider.valueOf(authProvider) : null;
			Date now = new Date();
			String token = createToken(getUserIdFromToken(data.getToken()), providerType, provider, registrationId,
					now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
			return new SecurityHeaderData(token, data.getAuthType(), data.getAuthProviderId(), data.getAuthTenantId(),
					false);
		} else
			return data;
	}

}