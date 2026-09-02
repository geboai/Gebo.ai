/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.introspection.SpringReactiveOpaqueTokenIntrospector;
import org.springframework.web.server.ServerWebExchange;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.impl.authmanagers.GOauth2ResourceServerUserProvisioner;
import ai.gebo.security.services.impl.authmanagers.GReactiveJwtAuthenticationConverter;
import ai.gebo.security.services.impl.authmanagers.GReactiveOpaqueTokenAuthenticationConverter;
import ai.gebo.security.services.impl.authmanagers.ReactiveJwtDecoderCache;
import ai.gebo.security.services.impl.authmanagers.ReactiveSecurityHeaderUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import tools.jackson.databind.ObjectMapper;

/**
 * Reactive (WebFlux) counterpart of
 * {@link GHttpRequestAuthenticationManagerResolverImpl}: resolves, per exchange,
 * a {@link ReactiveAuthenticationManager} for a local JWT or a dynamically
 * configured OAuth2 provider (JWT or opaque token), applying the same
 * header-based routing rules.
 *
 * <p>
 * The OAuth2 managers use the provisioning converters
 * ({@link GReactiveJwtAuthenticationConverter} /
 * {@link GReactiveOpaqueTokenAuthenticationConverter}), so - exactly as on the
 * servlet path - a validated token whose user does not exist yet triggers a
 * policy-gated create/sync, while an unauthenticated token never can.
 * </p>
 *
 * <p>
 * This is the reactive parallel provided for a WebFlux deployment; wire it into a
 * {@code SecurityWebFilterChain}'s {@code oauth2ResourceServer(...)} the way the
 * servlet chain wires {@code authenticationManagerResolver()}. Provider-config
 * lookups (blocking DAO) and the blocking user-details load are kept off the
 * event loop via the bounded-elastic scheduler.
 * </p>
 */
public class GReactiveHttpRequestAuthenticationManagerResolverImpl
		implements ReactiveAuthenticationManagerResolver<ServerWebExchange> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GReactiveHttpRequestAuthenticationManagerResolverImpl.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private final UserDetailsService customUserDetailsService;
	private final IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao;
	private final LocalJwtTokenProvider tokenProvider;
	// Nullable: policy-gated resource-server provisioner (as on the servlet path).
	private final GOauth2ResourceServerUserProvisioner provisioner;
	private final ReactiveJwtDecoderCache reactiveJwtDecoderCache = new ReactiveJwtDecoderCache();

	public GReactiveHttpRequestAuthenticationManagerResolverImpl(UserDetailsService customUserDetailsService,
			IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao, LocalJwtTokenProvider tokenProvider,
			GOauth2ResourceServerUserProvisioner provisioner) {
		this.customUserDetailsService = customUserDetailsService;
		this.oauth2RuntimeConfigurationDao = oauth2RuntimeConfigurationDao;
		this.tokenProvider = tokenProvider;
		this.provisioner = provisioner;
	}

	@Override
	public Mono<ReactiveAuthenticationManager> resolve(ServerWebExchange exchange) {
		SecurityHeaderData header = ReactiveSecurityHeaderUtil.getSecurityHeaderData(exchange);
		if (header.isEmpty())
			return Mono.empty();
		switch (header.getAuthType()) {
		case LOCAL_JWT:
			return Mono.just(localJwtManager());
		case OAUTH2:
			// Building an OAuth2 manager reads provider config (blocking DAO) and, for JWT,
			// performs OIDC discovery; keep that off the event loop.
			return Mono.fromCallable(() -> oauth2Manager(header)).subscribeOn(Schedulers.boundedElastic());
		default:
			return Mono.empty();
		}
	}

	private ReactiveAuthenticationManager oauth2Manager(SecurityHeaderData header) {
		if (header.getAuthProviderId() != null
				&& !header.getAuthProviderId().equals(SecurityHeaderUtil.DEFAULT_PROVIDER_ID)) {
			Oauth2RuntimeConfiguration oauth2Configuration = oauth2RuntimeConfigurationDao
					.findByCode(header.getAuthProviderId());
			return isJwtFormatStrict(header.getToken()) ? singleJwt(oauth2Configuration)
					: singleOpaque(oauth2Configuration);
		}
		List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs = oauth2RuntimeConfigurationDao
				.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
		if (oauth2AuthenticationConfigs.isEmpty())
			throw new RuntimeException(
					"Oauth2 specified in request header but no AUTHENTICATION oauth2 configuration found");
		return isJwtFormatStrict(header.getToken()) ? multiJwt(oauth2AuthenticationConfigs)
				: multiOpaque(oauth2AuthenticationConfigs);
	}

	private ReactiveAuthenticationManager singleJwt(Oauth2RuntimeConfiguration cfg) {
		ReactiveJwtDecoder decoder = reactiveJwtDecoderCache.forIssuerLocation(cfg.getProviderConfig().getIssuerUri());
		JwtReactiveAuthenticationManager manager = new JwtReactiveAuthenticationManager(decoder);
		manager.setJwtAuthenticationConverter(
				new GReactiveJwtAuthenticationConverter(customUserDetailsService, provisioner, cfg));
		return manager;
	}

	private ReactiveAuthenticationManager singleOpaque(Oauth2RuntimeConfiguration cfg) {
		SpringReactiveOpaqueTokenIntrospector introspector = new SpringReactiveOpaqueTokenIntrospector(
				cfg.getProviderConfig().getIntrospectionUri(), cfg.getClient().getClientId(),
				cfg.getClient().getSecret());
		OpaqueTokenReactiveAuthenticationManager manager = new OpaqueTokenReactiveAuthenticationManager(introspector);
		manager.setAuthenticationConverter(
				new GReactiveOpaqueTokenAuthenticationConverter(customUserDetailsService, provisioner, cfg));
		return manager;
	}

	private ReactiveAuthenticationManager multiJwt(List<Oauth2RuntimeConfiguration> configs) {
		List<ReactiveAuthenticationManager> managers = configs.stream().map(this::singleJwt).toList();
		return authentication -> Flux.fromIterable(managers)
				.concatMap(m -> m.authenticate(authentication).onErrorResume(ex -> {
					LOGGER.debug("Reactive JWT not accepted by a configured provider: {}", ex.getMessage());
					return Mono.empty();
				})).next()
				.switchIfEmpty(Mono.error(new BadCredentialsException("JWT not recognized by any configured provider")));
	}

	private ReactiveAuthenticationManager multiOpaque(List<Oauth2RuntimeConfiguration> configs) {
		List<ReactiveAuthenticationManager> managers = configs.stream().map(this::singleOpaque).toList();
		return authentication -> Flux.fromIterable(managers)
				.concatMap(m -> m.authenticate(authentication).onErrorResume(ex -> {
					LOGGER.debug("Reactive opaque token not accepted by a configured provider: {}", ex.getMessage());
					return Mono.empty();
				})).next().switchIfEmpty(
						Mono.error(new BadCredentialsException("Opaque Token not recognized by any configured provider")));
	}

	private ReactiveAuthenticationManager localJwtManager() {
		return authentication -> Mono.fromCallable(() -> {
			if (authentication instanceof BearerTokenAuthenticationToken auth) {
				String token = auth.getToken();
				if (!tokenProvider.validateToken(token))
					throw new BadCredentialsException("invalid local jwt value");
				String userId = tokenProvider.getUserIdFromToken(token);
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
				return (Authentication) new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
			}
			return authentication;
		}).subscribeOn(Schedulers.boundedElastic());
	}

	/***
	 * Check if a token is an actual JWT without checking the crypto part.
	 */
	public boolean isJwtFormatStrict(String token) {
		if (token == null || token.chars().filter(ch -> ch == '.').count() != 2) {
			return false;
		}
		try {
			String[] parts = token.split("\\.");
			String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
			String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
			OBJECT_MAPPER.readTree(headerJson);
			OBJECT_MAPPER.readTree(payloadJson);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
}
