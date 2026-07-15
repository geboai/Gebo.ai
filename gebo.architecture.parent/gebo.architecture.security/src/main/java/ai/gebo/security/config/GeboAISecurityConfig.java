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
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.util.ClassUtils;

import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.repository.IOauth2DynamicClientRegistrationRepository;
import ai.gebo.security.repository.Oauth2DynamicClientRegistrationRepository;
import ai.gebo.security.repository.Oauth2DynamicReactiveRegistrationRepository;
import ai.gebo.security.repository.Oauth2RuntimeConfigurationRepository;
import ai.gebo.security.services.IGBackendOauth2LoginSPASupportService;
import ai.gebo.security.services.IGHttpRequestAuthenticationManagerResolver;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.JwtAuthenticationEntryPoint;
import ai.gebo.security.services.impl.GHttpRequestAuthenticationManagerResolverImpl;
import ai.gebo.security.services.impl.GOAuth2AuthenticationSuccessHandler;
import ai.gebo.security.services.impl.GOAuth2UserService;
import ai.gebo.security.services.impl.GOauth2AuthorizedClientService;
import ai.gebo.security.services.impl.GOauth2CustomAuthorizationRequestResolver;
import ai.gebo.security.services.impl.GPasswordEncoder;
import ai.gebo.security.services.impl.GReactiveOauth2AuthorizedClientService;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import ai.gebo.security.services.impl.ReactiveGOAuth2UserService;

/**
 * Configuration class for setting up security in the Gebo AI application. It
 * configures authentication and authorization mechanisms. AI generated comments
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class GeboAISecurityConfig {
	private static Logger LOGGER = LoggerFactory.getLogger(GeboAISecurityConfig.class);
	// URLs that are allowed to be accessed without authentication 
	/*private static final String[] allowedUrls = new String[] { "/", "/index.html", "/assets/**", "/swagger-ui/**",
			"/v3/**", "/media/**", "**.js", "**.ico", "*.map", "**.css", "**.ts", "/login", "/oauth2/**", "/public/**",
			"/auth/**", "/error", "/error/**", "/ui/**", "/login/**" }; */
	/*
	 * Everything the Angular SPA needs in order to boot must be reachable
	 * ANONYMOUSLY, because the browser fetches it before there is any user to
	 * authenticate: the index.html shell, every /ui/<page> client-side route (a
	 * deep link or an F5 on /ui/chat is a plain document request that must return
	 * the shell), and all of the build's static output - the js bundles and lazy
	 * chunks, the stylesheets, the images/fonts/icons, the i18n and config json,
	 * and the source maps used when debugging. Anything left out here answers 401
	 * to the browser and the app fails to start rather than showing its login page.
	 *
	 * Assets are permitted BY DIRECTORY wherever the build gives us one - the
	 * "/assets/**" and "/media/**" rules already cover the bulk of them, whatever
	 * their type (svg, json, ftl, bcmap, fonts, even the extensionless LICENSE
	 * files pdf.js ships). The extension rules exist for what has no directory to
	 * key off: the hashed bundles the Angular build emits at the context ROOT
	 * (main-<hash>.js, styles-<hash>.css, chunk-<hash>.js, favicon.ico). Each such
	 * extension is declared twice, in a nested form matching e.g.
	 * /assets/img/logo.png and in a root form matching e.g. /main-KXODFWVK.js,
	 * keeping this list's existing convention.
	 *
	 * Prefer a directory rule over a new blanket extension rule: an extension rule
	 * matches every path in the application that happens to end that way, API
	 * routes included.
	 */
	private static final String[] allowedUrls = new String[] {
		    "/",
		    "/index.html",
		    "/assets/**",
		    "/media/**",

		    // Scripts, stylesheets and the sources/maps served for debugging.
		    "/**/*.js",
		    "/**/*.mjs",
		    "/**/*.css",
		    "/**/*.map",
		    "/**/*.ts",
		    "/**/*.d.ts",
		    "/*.js",
		    "/*.mjs",
		    "/*.css",
		    "/*.map",
		    "/*.ts",
		    "/*.d.ts",

		    // Images and icons (favicon, logos, inline svg, raster assets).
		    "/**/*.ico",
		    "/**/*.png",
		    "/**/*.jpg",
		    "/**/*.jpeg",
		    "/**/*.gif",
		    "/**/*.svg",
		    "/**/*.webp",
		    "/**/*.avif",
		    "/**/*.bmp",
		    "/*.ico",
		    "/*.png",
		    "/*.jpg",
		    "/*.jpeg",
		    "/*.gif",
		    "/*.svg",
		    "/*.webp",
		    "/*.avif",
		    "/*.bmp",

		    // Web fonts pulled in by the stylesheets.
		    "/**/*.woff",
		    "/**/*.woff2",
		    "/**/*.ttf",
		    "/**/*.otf",
		    "/**/*.eot",
		    "/*.woff",
		    "/*.woff2",
		    "/*.ttf",
		    "/*.otf",
		    "/*.eot",

		    // Static data the shell loads: PWA manifest and friends.
		    //
		    // Deliberately NO blanket "*.json" / "*.html" rule here. The Angular build
		    // keeps every json (i18n bundles, pdf.js/monaco config) and every html
		    // fragment under assets/ - already anonymous via "/assets/**" above - and the
		    // only html at the context root is index.html, permitted explicitly. A
		    // blanket rule would buy nothing and would silently make any future API path
		    // that happens to end in .json or .html (say /api/report/export.json)
		    // anonymous. Extension rules are for the hashed bundles the build emits at
		    // the ROOT (js/css/map/ico), which have no directory to key off.
		    "/**/*.webmanifest",
		    "/**/*.txt",
		    "/**/*.wasm",
		    "/*.webmanifest",
		    "/*.txt",
		    "/*.wasm",

		    "/login",
		    "/oauth2/**",
		    "/public/**",
		    "/auth/**",
		    "/error",
		    "/error/**",
		    "/ui",
		    "/ui/**",
		    "/login/**"
		};

	/*
	 * The Swagger / OpenAPI console. Anonymous like the rest of the UI - the API
	 * console is fetched by the browser before any login - but ONLY when swagger is
	 * actually part of the build.
	 *
	 * Swagger is opt-in for a security reason: it is pulled in by the swagger-on
	 * Maven profile (gebo.architecture.swagger -> springdoc), which production
	 * builds deliberately leave off, so a production deployment ships no API
	 * console. These paths are therefore permitted only when springdoc is on the
	 * classpath (see SWAGGER_PRESENT); otherwise they are left to the
	 * authenticated() catch-all rather than being permanently open. Without this
	 * gate the spec path /v3/api-docs would stay anonymous in production, where it
	 * has no business being reachable at all.
	 */
	private static final String[] swaggerUrls = new String[] {
		    "/swagger-ui.html",
		    "/swagger-ui/**",
		    "/v3/api-docs",
		    "/v3/api-docs/**",
		    "/v3/api-docs.yaml"
		};

	/**
	 * Whether the springdoc/Swagger machinery is on the classpath, i.e. whether
	 * this build activated the swagger-on profile. Detected rather than configured
	 * so the security rules cannot drift from what the build actually ships.
	 */
	private static final boolean SWAGGER_PRESENT = ClassUtils
			.isPresent("org.springdoc.core.configuration.SpringDocConfiguration",
					GeboAISecurityConfig.class.getClassLoader());

	// URLs that forward to index.html
	private static final String forwardToIndexHtmlUrls[] = new String[] { "/", "/ui/*", "/index.html" };

	// Admin-specific URLs
	private static final String[] adminUrls = new String[] { "/api/admin/**" };

	// User-specific URLs
	private static final String[] usersUrls = new String[] { "/api/users/**" };

	// Erogated MCP server endpoints (served at /mcp/<exportedUniqueRelativeUrl>)
	private static final String[] mcpUrls = new String[] { "/mcp/**" };

	public static final String ADMIN_ROLE = "ADMIN";
	public static final String USER_ROLE = "USER";
	public static final String APPLICATION_ROLE = "APPLICATION";

	private final LocalJwtTokenProvider tokenProvider;
	private final Oauth2RuntimeConfigurationRepository oauth2RuntimeConfigurationRepository;
	private final JwtAuthenticationEntryPoint point;
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
	private final GeboSecurityConfig securityConfig;
	private final ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> reactiveOAuth2UserService;
	private final GPasswordEncoder passwordEncoder;
	private final OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver;
	private final IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao;
	private final UserDetailsService userDetailsService;
	private final AuthenticationSuccessHandler authenticationSuccessHandler;

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
			GeboSecurityConfig securityProperties, GPasswordEncoder passwordEncoder,
			IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao, LocalJwtTokenProvider tokenProvider,
			JwtAuthenticationEntryPoint point, IGeboCryptingService cryptService, UserDetailsService userDetailsService,
			IGBackendOauth2LoginSPASupportService backendOauth2LoginSPASupportService) {
		this.oauth2ConfigurationService = oauth2ConfigurationService;
		this.userDetailsService = userDetailsService;
		Oauth2DynamicClientRegistrationRepository dynamicClient = new Oauth2DynamicClientRegistrationRepository(
				oauth2ConfigurationService);
		this.clientRegistrationRepository = dynamicClient;
		this.reactiveClientRegistrationRepository = new Oauth2DynamicReactiveRegistrationRepository(dynamicClient);
		this.oauth2RuntimeConfigurationRepository = oauth2RuntimeConfigurationRepository;
		this.secretsService = secretsService;
		this.userAdminService = userAdminService;
		this.securityConfig = securityProperties;

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
		this.cryptService = cryptService;
		this.authenticationSuccessHandler = new GOAuth2AuthenticationSuccessHandler(
				backendOauth2LoginSPASupportService);

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

	/**
	 * Uses an empty role prefix so that {@code hasRole(...)}/{@code hasAnyRole(...)}
	 * expressions (in HTTP rules and the now-enabled {@code @PreAuthorize} method
	 * security) match the raw authorities issued by {@code UserPrincipal.create}
	 * (e.g. {@code ADMIN}, {@code USER}, {@code APPLICATION}), which are stored
	 * without the default {@code ROLE_} prefix. Declared {@code static} so it is
	 * available before the security infrastructure that consumes it is built.
	 *
	 * @return the granted-authority defaults with an empty role prefix
	 */
	@Bean
	static GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults("");
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
		boolean oauth2LoginEnabled = securityConfig.getOauth2LoginEnabled() != null
				&& securityConfig.getOauth2LoginEnabled();
		boolean oauth2ResourceServerEnabled = securityConfig.getOauth2ResourceServerEnabled() != null
				&& securityConfig.getOauth2ResourceServerEnabled();
		if (SWAGGER_PRESENT) {
			LOGGER.info("Swagger is on the classpath (swagger-on build): serving {} anonymously",
					(Object) swaggerUrls);
		} else {
			LOGGER.info("No Swagger on the classpath: the API console and /v3/api-docs stay closed");
		}
		HttpSecurity configBuilder = http.cors(c -> c.disable()).csrf(csrf -> csrf.disable())
				.addFilterAfter(corsFilter, CsrfFilter.class)
				.authorizeHttpRequests(authorizeRequests -> {
					authorizeRequests.requestMatchers(allowedUrls).permitAll();
					if (SWAGGER_PRESENT) {
						authorizeRequests.requestMatchers(swaggerUrls).permitAll();
					}
					authorizeRequests.requestMatchers(mcpUrls)
							.hasAnyAuthority(USER_ROLE, ADMIN_ROLE, APPLICATION_ROLE).anyRequest().authenticated();
				});
		if (oauth2LoginEnabled) {
			configBuilder = configBuilder.oauth2Login(oauth2 -> oauth2
					.clientRegistrationRepository(clientRegistrationRepository)
					.authorizedClientService(oauth2AuthorizedClientService).successHandler(authenticationSuccessHandler)
					.authorizationEndpoint(
							auth -> auth.authorizationRequestResolver(oAuth2AuthorizationRequestResolver))
					.userInfoEndpoint(userInfo -> userInfo.userService(this.oauth2UserService))
			// Optional: use a custom success handler to issue JWT
			);
		}
		if (oauth2ResourceServerEnabled) {
			configBuilder = configBuilder.oauth2ResourceServer(
					oauth2 -> oauth2.authenticationManagerResolver(authenticationManagerResolver()));
		}
		return configBuilder.userDetailsService(userDetailsService)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
	}

	@Bean
	public IGHttpRequestAuthenticationManagerResolver authenticationManagerResolver() {
		return new GHttpRequestAuthenticationManagerResolverImpl(userDetailsService, passwordEncoder,
				oauth2RuntimeConfigurationDao, tokenProvider, userDetailsService);
	}

}