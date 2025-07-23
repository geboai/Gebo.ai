/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import ai.gebo.config.GeboConfig;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.TokenAuthenticationFilter;
import ai.gebo.security.repository.IOauth2DynamicClientRegistrationRepository;
import ai.gebo.security.repository.Oauth2DynamicClientRegistrationRepository;
import ai.gebo.security.repository.Oauth2DynamicReactiveRegistrationRepository;
import ai.gebo.security.repository.Oauth2InitializationInfoRepository;
import ai.gebo.security.repository.Oauth2RuntimeConfigurationRepository;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2InitializationService;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.JwtAuthenticationEntryPoint;
import ai.gebo.security.services.impl.GOAuth2SuccessHandler;
import ai.gebo.security.services.impl.GOAuth2UserService;
import ai.gebo.security.services.impl.GOauth2AuthorizedClientService;
import ai.gebo.security.services.impl.GOauth2CustomAuthorizationRequestResolver;
import ai.gebo.security.services.impl.GOauth2InitializationServiceImpl;
import ai.gebo.security.services.impl.GPasswordEncoder;
import ai.gebo.security.services.impl.GReactiveOauth2AuthorizedClientService;
import ai.gebo.security.services.impl.ReactiveGOAuth2UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Configuration class for setting up security in the Gebo AI application. It
 * configures authentication and authorization mechanisms. AI generated comments
 */
@Configuration
@EnableWebSecurity
public class GeboAISecurityConfig {

	private final Oauth2RuntimeConfigurationRepository oauth2RuntimeConfigurationRepository;
	static Logger LOGGER = LoggerFactory.getLogger(GeboAISecurityConfig.class);

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Autowired
	private GeboConfig geboConfig;

	@Autowired
	private IGeboCryptingService cryptService = null;

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

	/**
	 * A filter implementation to handle CORS (Cross-Origin Resource Sharing)
	 * settings.
	 */
	public static class GeboAICorsFilter extends OncePerRequestFilter {

		@Override
		protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
				FilterChain filterChain) throws ServletException, IOException {
			// Set CORS headers
			httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
			httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
			httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
			httpServletResponse.addHeader("Access-Control-Allow-Headers",
					"Access-Control-Allow-Headers, Origin , Accept , Authorization , X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
			if (httpServletRequest.getMethod().equals("OPTIONS")) {
				httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
			}
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
	}

	private GeboAICorsFilter corsFilter = new GeboAICorsFilter();
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
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver;
	private final IGOauth2InitializationService oauth2InitializationService;
	private final Oauth2InitializationInfoRepository oauth2InitializationRepository;

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
			Oauth2InitializationInfoRepository oauth2InitializationRepository) {
		this.oauth2ConfigurationService = oauth2ConfigurationService;
		Oauth2DynamicClientRegistrationRepository dynamicClient = new Oauth2DynamicClientRegistrationRepository(
				oauth2ConfigurationService);
		this.clientRegistrationRepository = dynamicClient;
		this.reactiveClientRegistrationRepository = new Oauth2DynamicReactiveRegistrationRepository(dynamicClient);
		this.oauth2RuntimeConfigurationRepository = oauth2RuntimeConfigurationRepository;
		this.secretsService = secretsService;
		this.userAdminService = userAdminService;
		this.securityProperties = securityProperties;
		this.oauth2InitializationRepository = oauth2InitializationRepository;
		this.oauth2AuthorizedClientService = new GOauth2AuthorizedClientService(dynamicClient, secretsService);
		this.reactiveOAuth2AuthorizedClientService = new GReactiveOauth2AuthorizedClientService(
				reactiveClientRegistrationRepository, secretsService);
		this.oauth2UserService = new GOAuth2UserService(oauth2ConfigurationService, userAdminService,
				securityProperties);
		this.reactiveOAuth2UserService = new ReactiveGOAuth2UserService(oauth2ConfigurationService, userAdminService,
				securityProperties);
		this.passwordEncoder = passwordEncoder;
		this.oauth2InitializationService = new GOauth2InitializationServiceImpl(dynamicClient,
				oauth2InitializationRepository);
		this.authenticationSuccessHandler = new GOAuth2SuccessHandler(oauth2AuthorizedClientService,
				oauth2InitializationService);
		this.oAuth2AuthorizationRequestResolver = new GOauth2CustomAuthorizationRequestResolver(dynamicClient);
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

	@Autowired
	TokenAuthenticationFilter filter;

	@Autowired
	UserDetailsService userDetailsService;

	/**
	 * Bean definition for AuthenticationProvider based on the configuration.
	 *
	 * @return An instance of AuthenticationProvider.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		if (geboConfig.getUseLdap() == null || !geboConfig.getUseLdap()) {
			// If LDAP is not used, configure a DaoAuthenticationProvider
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService);
			authProvider.setPasswordEncoder(passwordEncoder);
			return authProvider;
		} else if (geboConfig.getLdapConfig() != null && geboConfig.getLdapConfig().getDomain() != null
				&& geboConfig.getLdapConfig().getUrl() != null) {
			// If LDAP is used, configure an ActiveDirectoryLdapAuthenticationProvider
			ActiveDirectoryLdapAuthenticationProvider adProvider = new ActiveDirectoryLdapAuthenticationProvider(
					geboConfig.getLdapConfig().getDomain(), geboConfig.getLdapConfig().getUrl(),
					geboConfig.getLdapConfig().getRootDn());
			adProvider.setConvertSubErrorCodesToExceptions(true);
			adProvider.setUseAuthenticationRequestCredentials(true);
			return adProvider;
		} else {
			LOGGER.error("No ldap complete configuration but ldap enabled");
			throw new RuntimeException("No ldap complete configuration but ldap enabled");
		}
	}

	@Autowired
	AuthenticationConfiguration config;

	/**
	 * Bean definition for AuthenticationManager.
	 *
	 * @param provider The AuthenticationProvider to be used by the
	 *                 AuthenticationManager.
	 * @return An instance of AuthenticationManager.
	 * @throws Exception If an error occurs while creating the
	 *                   AuthenticationManager.
	 */
	@Bean

	public AuthenticationManager authenticationManager(@Autowired AuthenticationProvider provider) throws Exception {
		return new AuthenticationManager() {

			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				// Delegate authentication to the provider
				return provider.authenticate(authentication);
			}
		};
	}

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

	@Bean
	@Scope("singleton")
	public IGOauth2InitializationService oauth2InitializationService() {
		return oauth2InitializationService;
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
		
		// Configure security filter chain
		return http.addFilterBefore(filter, CorsFilter.class).cors(c -> c.disable()).csrf(csrf -> csrf.disable())
				.addFilterAfter(corsFilter, CsrfFilter.class)
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(allowedUrls).permitAll()
						.anyRequest().authenticated())
				.oauth2Login(oauth2 -> oauth2.clientRegistrationRepository(clientRegistrationRepository)
						.authorizedClientService(oauth2AuthorizedClientService)
						.successHandler(authenticationSuccessHandler)
						.authorizationEndpoint(
								auth -> auth.authorizationRequestResolver(oAuth2AuthorizationRequestResolver))
						.userInfoEndpoint(userInfo -> userInfo.userService(this.oauth2UserService))
				// Optional: use a custom success handler to issue JWT
				)
				
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
	}
}