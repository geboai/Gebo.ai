/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.controller;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.model.SecurityHeaderUtil.XAuthType;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import ai.gebo.security.services.impl.authmanagers.JwtDecoderCache;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

/**
 * Reconstructs the {@link SecurityHeaderData} the security filter chain actually
 * recognized for the caller's bearer token, rather than the naive header-echo that
 * {@link SecurityHeaderUtil#getSecurityHeaderData(HttpServletRequest)} produces.
 *
 * <p>
 * The token is returned unchanged; {@code authType} and {@code authProviderId} are
 * completed by mirroring, step for step, the exact "AUTO" recognition logic in
 * {@code GHttpRequestAuthenticationManagerResolverImpl#resolveAuto} (unverified JWT
 * payload peek, Gebo self-signed claim check, issuer match against configured
 * AUTHENTICATION oauth2 configs, decoder/introspection fallback loop). By the time this
 * endpoint runs, the request has already passed through that same resolver and been
 * authenticated, so the classification below only reports what already happened - it
 * never makes a trust decision on its own (the unverified peek is used exactly as it is
 * in the resolver: to pick which check to run next, never to accept a claim at face
 * value).
 * </p>
 *
 * <p>
 * {@code authTenantId} has no chain-recognition semantics anywhere in this module -
 * neither the resolver, {@link LocalJwtTokenProvider}, nor any {@code AuthenticationManager}
 * ever derives a tenant from a token - so it is echoed from the request exactly as
 * {@link SecurityHeaderUtil#getSecurityHeaderData} would produce it.
 * </p>
 */
@RestController
@PreAuthorize("hasAnyRole('USER','ADMIN','APPLICATION')")
@RequestMapping("/api/users/SecurityHeaderDataCompletionController")
@AllArgsConstructor
public class SecurityHeaderDataCompletionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHeaderDataCompletionController.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private final IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;
	// Per-issuer JwtDecoder cache. This endpoint is not on the hot authentication path, so
	// unlike the resolver it does not need an application-lifetime shared cache - a fresh
	// one per controller instance is enough to avoid rebuilding a decoder for every config
	// tried within a single request.
	private final JwtDecoderCache jwtDecoderCache = new JwtDecoderCache();

	@GetMapping(value = "complete", produces = MediaType.APPLICATION_JSON_VALUE)
	public SecurityHeaderData complete(HttpServletRequest request) {
		SecurityHeaderData raw = SecurityHeaderUtil.getSecurityHeaderData(request);
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.SESSION_MANAGEMENT);
		event.setCategory(SecurityAuditTaxonomy.Category.SESSION_MANAGEMENT);
		event.setAction(SecurityAuditTaxonomy.Action.SESSION_HEADER_DATA_COMPLETE);
		try {
			SecurityHeaderData completed = classify(raw);
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
			return completed;
		} catch (RuntimeException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		} finally {
			securityAuditLoggerService.log(event);
		}
	}

	private SecurityHeaderData classify(SecurityHeaderData raw) {
		if (raw.isEmpty()) {
			return raw;
		}
		String token = raw.getToken();
		JsonNode payload = decodeUnverifiedPayload(token);
		if (payload == null) {
			// Not JWT-shaped: Gebo's own LOCAL_JWT is always a compact HMAC JWT, so this can
			// only be an opaque OAuth2 access token - same reasoning as resolveAuto().
			return classifyOpaque(raw);
		}
		if (payload.has(LocalJwtTokenProvider.AUTH_TYPE_CLAIM) || payload.has(LocalJwtTokenProvider.AUTH_PROVIDER_CLAIM)) {
			// Gebo self-signed LOCAL_JWT. When it wraps an OAuth2-login-derived identity it
			// also carries AUTH_PROVIDER_REGISTRATION_ID (see LocalJwtTokenProvider) - the real
			// originating provider - otherwise it is a purely local identity, the sentinel
			// DEFAULT_PROVIDER_ID SecurityHeaderUtil itself uses for that case.
			String registrationId = payload.has(LocalJwtTokenProvider.AUTH_PROVIDER_REGISTRATION_ID)
					? payload.get(LocalJwtTokenProvider.AUTH_PROVIDER_REGISTRATION_ID).asText()
					: SecurityHeaderUtil.DEFAULT_PROVIDER_ID;
			return new SecurityHeaderData(token, XAuthType.LOCAL_JWT, registrationId, raw.getAuthTenantId(), false);
		}
		String issuer = payload.has("iss") ? payload.get("iss").asText() : null;
		if (issuer != null) {
			List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs = oauth2RuntimeConfigurationDao
					.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
			Oauth2RuntimeConfiguration matched = oauth2AuthenticationConfigs.stream()
					.filter(config -> config.getProviderConfig() != null
							&& issuer.equals(config.getProviderConfig().getIssuerUri()))
					.findFirst().orElse(null);
			if (matched != null) {
				// Unambiguous issuer match, exactly as resolveAuto()'s single-config branch.
				return new SecurityHeaderData(token, XAuthType.OAUTH2, matched.getRegistrationId(),
						raw.getAuthTenantId(), false);
			}
			// No exact issuer match (e.g. a trailing-slash/path difference): resolveAuto()
			// falls back to trying every configured provider rather than giving up, via a
			// manager that verifies against each candidate's decoder in turn. Mirror that here.
			for (Oauth2RuntimeConfiguration config : oauth2AuthenticationConfigs) {
				if (config.getProviderConfig() == null || config.getProviderConfig().getIssuerUri() == null) {
					continue;
				}
				try {
					JwtDecoder decoder = jwtDecoderCache.forIssuerLocation(config.getProviderConfig().getIssuerUri());
					decoder.decode(token);
					return new SecurityHeaderData(token, XAuthType.OAUTH2, config.getRegistrationId(),
							raw.getAuthTenantId(), false);
				} catch (RuntimeException ex) {
					LOGGER.debug("Token not accepted by decoder for issuer [{}]: {}",
							config.getProviderConfig().getIssuerUri(), ex.getMessage());
				}
			}
		}
		// JWT-shaped but neither self-identifies as LOCAL_JWT nor matches any configured
		// issuer: resolveAuto()'s own final fallback, which tokenProvider.validateToken()
		// then fails closed on if it truly isn't a LOCAL_JWT.
		return new SecurityHeaderData(token, XAuthType.LOCAL_JWT, SecurityHeaderUtil.DEFAULT_PROVIDER_ID,
				raw.getAuthTenantId(), false);
	}

	/**
	 * Finds which configured AUTHENTICATION provider's introspection endpoint recognizes
	 * this opaque token - the same try-each-provider approach as
	 * {@code MultiOauth2ConfigOpaqueTokenAuthenticationManager}.
	 */
	private SecurityHeaderData classifyOpaque(SecurityHeaderData raw) {
		List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs = oauth2RuntimeConfigurationDao
				.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
		for (Oauth2RuntimeConfiguration config : oauth2AuthenticationConfigs) {
			if (config.getProviderConfig() == null || config.getProviderConfig().getIntrospectionUri() == null
					|| config.getClient() == null) {
				continue;
			}
			try {
				SpringOpaqueTokenIntrospector introspector = new SpringOpaqueTokenIntrospector(
						config.getProviderConfig().getIntrospectionUri(), config.getClient().getClientId(),
						config.getClient().getSecret());
				OAuth2AuthenticatedPrincipal principal = introspector.introspect(raw.getToken());
				if (principal != null) {
					return new SecurityHeaderData(raw.getToken(), XAuthType.OAUTH2, config.getRegistrationId(),
							raw.getAuthTenantId(), false);
				}
			} catch (RuntimeException ex) {
				LOGGER.debug("Opaque token not accepted by provider introspection [{}]: {}",
						config.getProviderConfig().getIntrospectionUri(), ex.getMessage());
			}
		}
		// Not confirmed by any configured provider's introspection - still not JWT-shaped,
		// so it cannot be LOCAL_JWT; echo the caller-declared/default provider id since none
		// could be verified.
		return new SecurityHeaderData(raw.getToken(), XAuthType.OAUTH2, raw.getAuthProviderId(),
				raw.getAuthTenantId(), false);
	}

	/**
	 * Unverified JWT payload peek, identical to
	 * {@code GHttpRequestAuthenticationManagerResolverImpl#decodeUnverifiedPayload}: proves
	 * the token is JWT-<i>shaped</i> only, never that it is authentic. Safe here for the
	 * same reason it is safe there - it only ever picks which already-established fact to
	 * report, never a trust decision (the token was already fully verified by the resolver
	 * before this request reached the controller).
	 */
	private JsonNode decodeUnverifiedPayload(String token) {
		if (token == null || token.chars().filter(ch -> ch == '.').count() != 2) {
			return null;
		}
		try {
			String[] parts = token.split("\\.");
			String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
			String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
			MAPPER.readTree(headerJson);
			return MAPPER.readTree(payloadJson);
		} catch (Throwable e) {
			return null;
		}
	}
}
