package ai.gebo.security.services.impl;

import java.util.Base64;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.security.LocalJwtTokenProvider;
import ai.gebo.security.SecurityHeaderUtil;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGHttpRequestAuthenticationManagerResolver;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.impl.authmanagers.LocalJwtAuthenticationManager;
import ai.gebo.security.services.impl.authmanagers.MultiOauth2ConfigJwtAuthenticationManager;
import ai.gebo.security.services.impl.authmanagers.MultiOauth2ConfigOpaqueTokenAuthenticationManager;
import ai.gebo.security.services.impl.authmanagers.SingleOauth2ConfigJwtAuthenticationManager;
import ai.gebo.security.services.impl.authmanagers.SingleOauth2ConfigOpaqueTokenAuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;

/***
 * AuthenticationManagerResolver to solve local generated jwt or opaque/jwt
 * Oauth2 providers dynamic configurations
 */
public class GHttpRequestAuthenticationManagerResolverImpl implements IGHttpRequestAuthenticationManagerResolver {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager localLoginAuthenticationManager;
	private final IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao;
	private final LocalJwtTokenProvider tokenProvider;
	private final UserDetailsService customUserDetailsService;
	private final GJwtAuthenticationConverter jwtAuthenticationConverter;
	private final GOpaqueTokenAuthenticationConverter tokenAuthenticationConverter;

	public GHttpRequestAuthenticationManagerResolverImpl(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder, IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao,
			LocalJwtTokenProvider tokenProvider, UserDetailsService customUserDetailsService) {

		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.oauth2RuntimeConfigurationDao = oauth2RuntimeConfigurationDao;
		this.tokenProvider = tokenProvider;
		this.customUserDetailsService = customUserDetailsService;
		this.jwtAuthenticationConverter = new GJwtAuthenticationConverter(customUserDetailsService);
		this.tokenAuthenticationConverter = new GOpaqueTokenAuthenticationConverter(customUserDetailsService);
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);

		this.localLoginAuthenticationManager = new AuthenticationManager() {

			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				// Delegate authentication to the provider
				return authProvider.authenticate(authentication);
			}
		};
	}

	@Override
	public AuthenticationManager resolve(HttpServletRequest request) {
		SecurityHeaderData header = SecurityHeaderUtil.getSecurityHeaderData(request);
		if (header.isEmpty())
			return null;
		AuthenticationManager manager = null;
		switch (header.getAuthType()) {
		case LOCAL_JWT: {
			manager = new LocalJwtAuthenticationManager(request, tokenProvider, customUserDetailsService);
		}
			break;
		case OAUTH2: {
			if (header.getAuthProviderId() != null
					&& !header.getAuthProviderId().equals(SecurityHeaderUtil.DEFAULT_PROVIDER_ID)) {
				Oauth2RuntimeConfiguration oauth2Configuration = oauth2RuntimeConfigurationDao
						.findByCode(header.getAuthProviderId());
				if (isJwtFormatStrict(header.getToken())) {
					manager = new SingleOauth2ConfigJwtAuthenticationManager(header, oauth2Configuration,
							jwtAuthenticationConverter);
				} else {
					manager = new SingleOauth2ConfigOpaqueTokenAuthenticationManager(header, oauth2Configuration,
							tokenAuthenticationConverter);
				}
			} else {
				List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs = oauth2RuntimeConfigurationDao
						.findByConfigurationTypesContains(Oauth2ConfigurationType.AUTHENTICATION);
				if (oauth2AuthenticationConfigs.isEmpty())
					throw new RuntimeException(
							"Oauth2 specified in request header but no AUTHENTICATION oauth2 configuration found");
				if (isJwtFormatStrict(header.getToken())) {
					manager = new MultiOauth2ConfigJwtAuthenticationManager(header, oauth2AuthenticationConfigs,
							jwtAuthenticationConverter);
				} else {
					manager = new MultiOauth2ConfigOpaqueTokenAuthenticationManager(header, oauth2AuthenticationConfigs,
							tokenAuthenticationConverter);
				}
			}
		}
			break;
		}
		return manager;
	}

	private static ObjectMapper objectMapper = new ObjectMapper();

	/***
	 * Check if a token is an actual JWT without checking for the crypto part
	 * 
	 * @param token
	 * @return
	 */
	public boolean isJwtFormatStrict(String token) {
		if (token == null || token.chars().filter(ch -> ch == '.').count() != 2) {
			return false;
		}
		try {
			String[] parts = token.split("\\.");
			String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
			String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

			// Validate JSON structure
			objectMapper.readTree(headerJson);
			objectMapper.readTree(payloadJson);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public Authentication authenticateByLocalJWT(
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {

		return localLoginAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
	}
}
