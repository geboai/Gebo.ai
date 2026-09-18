package ai.gebo.security.services.impl;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.model.SecurityHeaderUtil.XAuthType;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGHttpRequestAuthenticationManagerResolver;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.impl.authmanagers.GOauth2ResourceServerUserProvisioner;
import ai.gebo.security.services.impl.authmanagers.IssuerConfigCache;
import ai.gebo.security.services.impl.authmanagers.JwtDecoderCache;
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
	static private final Logger LOGGER = LoggerFactory.getLogger(GHttpRequestAuthenticationManagerResolverImpl.class);
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager localLoginAuthenticationManager;
	private final IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao;
	private final LocalJwtTokenProvider tokenProvider;
	private final UserDetailsService customUserDetailsService;
	private final GJwtAuthenticationConverter jwtAuthenticationConverter;
	private final GOpaqueTokenAuthenticationConverter tokenAuthenticationConverter;
	// Nullable: policy-gated resource-server user provisioning/sync collaborator,
	// used on the OAUTH2/AUTO paths where GJwtAuthenticationConverter's own
	// TRUST_EVERY_OAUTH_IDENTITY provisioning (JWT path) does not apply, and as
	// the sole provisioning path for opaque tokens (GOpaqueTokenAuthenticationConverter
	// has none of its own).
	private final GOauth2ResourceServerUserProvisioner provisioner;
	// Shared per-issuer JwtDecoder cache. The resolver is a singleton, so this cache
	// lives for the application lifetime and is reused across all per-request managers.
	private final JwtDecoderCache jwtDecoderCache = new JwtDecoderCache();
	// Shared per-issuer -> Oauth2RuntimeConfiguration cache, used both to pick a
	// manager when X-AuthType is AUTO (absent) and, inside jwtAuthenticationConverter,
	// to resolve the AuthProvider for auto-provisioning - one cache, one cost per
	// issuer, instead of scanning every configured provider on every request either
	// way needs it.
	private final IssuerConfigCache issuerConfigCache = new IssuerConfigCache();

	public GHttpRequestAuthenticationManagerResolverImpl(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder, IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao,
			LocalJwtTokenProvider tokenProvider, UserDetailsService customUserDetailsService,
			GeboSecurityConfig securityConfig, IGSecurityDirectory securityDirectory,
			GOauth2ResourceServerUserProvisioner provisioner,
			IGSecurityAuditLoggerService securityAuditLoggerService) {

		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.oauth2RuntimeConfigurationDao = oauth2RuntimeConfigurationDao;
		this.tokenProvider = tokenProvider;
		this.customUserDetailsService = customUserDetailsService;
		this.provisioner = provisioner;
		this.jwtAuthenticationConverter = new GJwtAuthenticationConverter(customUserDetailsService, securityConfig,
				oauth2RuntimeConfigurationDao, securityDirectory, issuerConfigCache, securityAuditLoggerService);
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin resolve(...)");
		}
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
							jwtAuthenticationConverter, jwtDecoderCache, provisioner);
				} else {
					manager = new SingleOauth2ConfigOpaqueTokenAuthenticationManager(header, oauth2Configuration,
							tokenAuthenticationConverter, provisioner);
				}
			} else {
				List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs = oauth2RuntimeConfigurationDao
						.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
				if (oauth2AuthenticationConfigs.isEmpty())
					throw new RuntimeException(
							"Oauth2 specified in request header but no AUTHENTICATION oauth2 configuration found");
				if (isJwtFormatStrict(header.getToken())) {
					manager = new MultiOauth2ConfigJwtAuthenticationManager(header, oauth2AuthenticationConfigs,
							jwtAuthenticationConverter, jwtDecoderCache, provisioner);
				} else {
					manager = new MultiOauth2ConfigOpaqueTokenAuthenticationManager(header, oauth2AuthenticationConfigs,
							tokenAuthenticationConverter, provisioner);
				}
			}
		}
			break;
		case AUTO: {
			manager = resolveAuto(request, header);
		}
			break;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End resolve(...) returning manager of class "
					+ (manager != null ? manager.getClass().getName() : "NULL"));
		}
		return manager;
	}

	/**
	 * X-AuthType was absent: sniff the token itself instead of assuming LOCAL_JWT.
	 *
	 * <p>
	 * Safe by construction, not by care: this only ever picks <i>which</i> validator
	 * to try next, and every branch below still independently and fully verifies the
	 * token (the HMAC check in {@link LocalJwtAuthenticationManager}, or the JWT
	 * decoder's signature check against the <i>matched config's own</i> issuer JWKS -
	 * never a claim taken at face value). A forged claim in the unverified peek below
	 * can at worst route to the wrong validator, which then correctly rejects it.
	 * </p>
	 */
	private AuthenticationManager resolveAuto(HttpServletRequest request, SecurityHeaderData header) {
		String token = header.getToken();
		JsonNode payload = decodeUnverifiedPayload(token);
		if (payload == null) {
			// Not JWT-shaped at all: Gebo's own LOCAL_JWT is always a compact HMAC JWT, so
			// this can only be an opaque OAuth2 access token (or garbage, which the
			// introspection call below will then correctly reject).
			List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs = oauth2RuntimeConfigurationDao
					.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
			if (oauth2AuthenticationConfigs.isEmpty()) {
				throw new RuntimeException(
						"Bearer token is not a recognizable JWT and no AUTHENTICATION oauth2 configuration is "
								+ "configured to try it as an opaque token against");
			}
			return new MultiOauth2ConfigOpaqueTokenAuthenticationManager(header, oauth2AuthenticationConfigs,
					tokenAuthenticationConverter, provisioner);
		}
		// Gebo's own LOCAL_JWT self-identifies with these claims (see
		// SecurityHeaderUtil.createSelfsignedJwtSecurityHeader and LocalJwtTokenProvider) -
		// an external IdP's JWT will not carry them.
		if (payload.has("AUTH_TYPE") || payload.has("AUTH_PROVIDER")) {
			return new LocalJwtAuthenticationManager(request, tokenProvider, customUserDetailsService);
		}
		String issuer = payload.has("iss") ? payload.get("iss").asText() : null;
		if (issuer != null) {
			Oauth2RuntimeConfiguration matched = issuerConfigCache.forIssuer(issuer, () -> oauth2RuntimeConfigurationDao
					.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION).stream()
					.filter(config -> config.getProviderConfig() != null
							&& issuer.equals(config.getProviderConfig().getIssuerUri()))
					.findFirst().orElse(null))
					.orElse(null);
			if (matched != null) {
				// Unambiguous: this issuer matches exactly one configured provider, so there is
				// no need for X-Authprovider-id at all to disambiguate.
				return new SingleOauth2ConfigJwtAuthenticationManager(header, matched, jwtAuthenticationConverter,
						jwtDecoderCache, provisioner);
			}
			List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs = oauth2RuntimeConfigurationDao
					.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
			if (!oauth2AuthenticationConfigs.isEmpty()) {
				// An iss claim is present but doesn't match any known issuerUri exactly (e.g. a
				// trailing slash or path difference) - fall back to trying every configured
				// provider rather than giving up outright.
				return new MultiOauth2ConfigJwtAuthenticationManager(header, oauth2AuthenticationConfigs,
						jwtAuthenticationConverter, jwtDecoderCache, provisioner);
			}
		}
		// JWT-shaped but neither self-identifies as LOCAL_JWT nor carries a recognized
		// issuer: fall back to LOCAL_JWT, today's default - tokenProvider.validateToken()
		// will correctly reject it (fails closed) if it isn't really one.
		return new LocalJwtAuthenticationManager(request, tokenProvider, customUserDetailsService);
	}

	private static ObjectMapper objectMapper = new ObjectMapper();

	/***
	 * Check if a token is an actual JWT without checking for the crypto part
	 *
	 * @param token
	 * @return
	 */
	public boolean isJwtFormatStrict(String token) {
		return decodeUnverifiedPayload(token) != null;
	}

	/**
	 * Decodes and JSON-parses a compact JWT's payload segment <b>without checking the
	 * signature</b> - i.e. this proves the token is JWT-<i>shaped</i>, never that it is
	 * authentic. Used only to decide which real, signature-verifying validator to try
	 * (see {@link #resolveAuto}); never to make an authentication or authorization
	 * decision by itself.
	 *
	 * @param token the raw bearer token
	 * @return the parsed payload, or {@code null} if the token is not a
	 *         three-segment, base64url/JSON-shaped compact JWT
	 */
	private JsonNode decodeUnverifiedPayload(String token) {
		if (token == null || token.chars().filter(ch -> ch == '.').count() != 2) {
			return null;
		}
		try {
			String[] parts = token.split("\\.");
			String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
			String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

			// Validate JSON structure
			objectMapper.readTree(headerJson);
			return objectMapper.readTree(payloadJson);
		} catch (Throwable e) {
			return null;
		}
	}

	@Override
	public Authentication authenticateByLocalJWT(
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {

		return localLoginAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
	}
}
