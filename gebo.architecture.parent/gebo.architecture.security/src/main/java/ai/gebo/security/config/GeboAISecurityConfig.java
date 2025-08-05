/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

import ai.gebo.config.GeboConfig;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.LocalJwtTokenProvider;
import ai.gebo.security.repository.IOauth2DynamicClientRegistrationRepository;
import ai.gebo.security.repository.Oauth2DynamicClientRegistrationRepository;
import ai.gebo.security.repository.Oauth2DynamicReactiveRegistrationRepository;
import ai.gebo.security.repository.Oauth2RuntimeConfigurationRepository;
import ai.gebo.security.services.IGHttpRequestAuthenticationManagerResolver;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.JwtAuthenticationEntryPoint;
import ai.gebo.security.services.impl.GHttpRequestAuthenticationManagerResolverImpl;
import ai.gebo.security.services.impl.GOAuth2UserService;
import ai.gebo.security.services.impl.GOauth2AuthorizedClientService;
import ai.gebo.security.services.impl.GOauth2CustomAuthorizationRequestResolver;
import ai.gebo.security.services.impl.GPasswordEncoder;
import ai.gebo.security.services.impl.GReactiveOauth2AuthorizedClientService;
import ai.gebo.security.services.impl.ReactiveGOAuth2UserService;

/**
 * Configuration class for setting up security in the Gebo AI application. It
 * configures authentication and authorization mechanisms. AI generated comments
 */
@Configuration
@EnableWebSecurity
public class GeboAISecurityConfig {
	private static Logger LOGGER = LoggerFactory.getLogger(GeboAISecurityConfig.class);
	// URLs that are allowed to be accessed without authentication
	private static final String[] allowedUrls = new String[] { "/", "/index.html", "/assets/**", "/swagger-ui/**",
			"/v3/**", "/media/**", "**.js", "**.ico", "**.css", "**.ts", "/login", "/oauth2/**", "/public/**",
			"/auth/**", "/error", "/error/**", "/ui/**", "/login/**" };

	// URLs that forward to index.html
	private static final String forwardToIndexHtmlUrls[] = new String[] { "/", "/ui/*", "/index.html" };

	// Admin-specific URLs
	private static final String[] adminUrls = new String[] { "/api/admin/**" };

	// User-specific URLs
	private static final String[] usersUrls = new String[] { "/api/users/**" };

	public static final String ADMIN_ROLE = "ADMIN";
	public static final String USER_ROLE = "USER";

	private final LocalJwtTokenProvider tokenProvider;
	private final Oauth2RuntimeConfigurationRepository oauth2RuntimeConfigurationRepository;
	private final JwtAuthenticationEntryPoint point;
	private final GeboConfig geboConfig;
	private final IGeboCryptingService cryptService;
	private final GeboAICorsFilter corsFilter = new GeboAICorsFilter();
	private final IGOauth2ConfigurationService oauth2ConfigurationService;
	private final IOauth2DynamicClientRegistrationRepository clientRegistrationRepository;
	private final ReactiveClientRegistrationRepository reactiveClientRegistrationRepository;
	private final IGeboSecretsAccessService secretsService;
	private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;
	private final ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService;
	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService;
	private final IGUsersAdminService userAdminService;
	private final GeboAppSecurityProperties securityProperties;
	private final ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> reactiveOAuth2UserService;
	private final GPasswordEncoder passwordEncoder;
	private final OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver;
	private final IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao;
	private final UserDetailsService userDetailsService;

	/**************************************************************************************************
	 * Building the dynamic oauth2 management in the constructor
	 * 
	 * @param oauth2ConfigurationService
	 * @param oauth2RuntimeConfigurationRepository
	 * @param oauth2InitializationRepository
	 */
	public GeboAISecurityConfig(IGOauth2ConfigurationService oauth2ConfigurationService,
			Oauth2RuntimeConfigurationRepository oauth2RuntimeConfigurationRepository,
			IGeboSecretsAccessService secretsService, IGUsersAdminService userAdminService,
			GeboAppSecurityProperties securityProperties, GPasswordEncoder passwordEncoder,
			IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao, LocalJwtTokenProvider tokenProvider,
			JwtAuthenticationEntryPoint point, GeboConfig geboConfig, IGeboCryptingService cryptService,
			UserDetailsService userDetailsService) {
		this.oauth2ConfigurationService = oauth2ConfigurationService;
		this.userDetailsService = userDetailsService;
		Oauth2DynamicClientRegistrationRepository dynamicClient = new Oauth2DynamicClientRegistrationRepository(
				oauth2ConfigurationService);
		this.clientRegistrationRepository = dynamicClient;
		this.reactiveClientRegistrationRepository = new Oauth2DynamicReactiveRegistrationRepository(dynamicClient);
		this.oauth2RuntimeConfigurationRepository = oauth2RuntimeConfigurationRepository;
		this.secretsService = secretsService;
		this.userAdminService = userAdminService;
		this.securityProperties = securityProperties;

		this.oauth2AuthorizedClientService = new GOauth2AuthorizedClientService(dynamicClient, secretsService);
		this.reactiveOAuth2AuthorizedClientService = new GReactiveOauth2AuthorizedClientService(
				reactiveClientRegistrationRepository, secretsService);
		this.oauth2UserService = new GOAuth2UserService(oauth2ConfigurationService, userAdminService,
				securityProperties);
		this.reactiveOAuth2UserService = new ReactiveGOAuth2UserService(oauth2ConfigurationService, userAdminService,
				securityProperties);
		this.passwordEncoder = passwordEncoder;

		this.oAuth2AuthorizationRequestResolver = new GOauth2CustomAuthorizationRequestResolver(dynamicClient);
		this.oauth2RuntimeConfigurationDao = oauth2RuntimeConfigurationDao;
		this.tokenProvider = tokenProvider;
		this.point = point;
		this.geboConfig = geboConfig;
		this.cryptService = cryptService;

	}

	/**
	 * Bean definition for GeboAICorsFilter. This filter is responsible for handling
	 * CORS.
	 *
	 * @return An instance of GeboAICorsFilter.
	 */
	@Bean
	public GeboAICorsFilter geboAICorsFilter() {
		return corsFilter;
	}

	// @Autowired
	// private TokenAuthenticationFilter filter;

	/**
	 * Register ReactiveClientRegistrationRepository bean
	 * 
	 * @return
	 */
	@Bean
	@Scope("singleton")
	public ReactiveClientRegistrationRepository reactiveClientRegistrationRepository() {
		return this.reactiveClientRegistrationRepository;
	}

	/**
	 * Register ClientRegistrationRepository bean
	 * 
	 * @return
	 */
	@Bean
	@Scope("singleton")
	public IOauth2DynamicClientRegistrationRepository clientRegistrationRepository() {
		return this.clientRegistrationRepository;
	}

	/**
	 * Register OAuth2AuthorizedClientService bean
	 * 
	 * @return
	 */
	@Bean
	@Scope("singleton")
	public OAuth2AuthorizedClientService oauth2AuthorizedClientService() {
		return this.oauth2AuthorizedClientService;
	}

	/**
	 * Register ReactiveOAuth2AuthorizedClientService bean
	 * 
	 * @return
	 */
	@Bean
	@Scope("singleton")
	public ReactiveOAuth2AuthorizedClientService reactiveOauth2AuthorizedClientService() {
		return this.reactiveOAuth2AuthorizedClientService;
	}

	@Bean
	@Scope("singleton")
	public ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> reactiveOAuth2UserService() {
		return this.reactiveOAuth2UserService;
	}

	@Bean
	@Scope("singleton")
	public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
		return this.oauth2UserService;
	}

	/**
	 * Bean definition for SecurityFilterChain to configure HTTP security.
	 *
	 * @param http The HttpSecurity object to be configured.
	 * @return A configured instance of SecurityFilterChain.
	 * @throws Exception If an error occurs while configuring HTTP security.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		boolean oauth2Enabled=geboConfig.getOauth2Enabled()!=null && geboConfig.getOauth2Enabled();
		HttpSecurity configBuilder = http.cors(c -> c.disable()).csrf(csrf -> csrf.disable()).addFilterAfter(corsFilter, CsrfFilter.class)
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(allowedUrls).permitAll()
						.anyRequest().authenticated());
		if (oauth2Enabled) {
			configBuilder=configBuilder.oauth2Login(oauth2 -> oauth2.clientRegistrationRepository(clientRegistrationRepository)
					.authorizedClientService(oauth2AuthorizedClientService)
					// .successHandler(authenticationSuccessHandler)
					.authorizationEndpoint(
							auth -> auth.authorizationRequestResolver(oAuth2AuthorizationRequestResolver))
					.userInfoEndpoint(userInfo -> userInfo.userService(this.oauth2UserService))
			// Optional: use a custom success handler to issue JWT
			).oauth2ResourceServer(oauth2 -> oauth2.authenticationManagerResolver(authenticationManagerResolver()));
		}
		return configBuilder.userDetailsService(userDetailsService)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
		// Configure security filter chain
		/*
		return http.cors(c -> c.disable()).csrf(csrf -> csrf.disable()).addFilterAfter(corsFilter, CsrfFilter.class)
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(allowedUrls).permitAll()
						.anyRequest().authenticated())
				.oauth2Login(oauth2 -> oauth2.clientRegistrationRepository(clientRegistrationRepository)
						.authorizedClientService(oauth2AuthorizedClientService)
						// .successHandler(authenticationSuccessHandler)
						.authorizationEndpoint(
								auth -> auth.authorizationRequestResolver(oAuth2AuthorizationRequestResolver))
						.userInfoEndpoint(userInfo -> userInfo.userService(this.oauth2UserService))
				// Optional: use a custom success handler to issue JWT
				).oauth2ResourceServer(oauth2 -> oauth2.authenticationManagerResolver(authenticationManagerResolver()))
				.userDetailsService(userDetailsService)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();*/
	}

	@Bean
	public IGHttpRequestAuthenticationManagerResolver authenticationManagerResolver() {
		return new GHttpRequestAuthenticationManagerResolverImpl(userDetailsService, passwordEncoder,
				oauth2RuntimeConfigurationDao, tokenProvider, userDetailsService);
	}

}