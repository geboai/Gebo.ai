package ai.gebo.security.services.impl;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.GeboLoginPolicy;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.impl.authmanagers.IssuerConfigCache;
import lombok.AllArgsConstructor;

/**
 * Converts a validated external OAuth2 {@link Jwt} into a Gebo authentication,
 * resolving the token's {@code email} (falling back to {@code sub}) to an existing
 * Gebo user via {@code userDetailsService}.
 *
 * <p>
 * <b>Auto-provisioning</b>: when that lookup finds no such user, this used to always
 * fail the request ({@link UsernameNotFoundException} -> 401) regardless of
 * configuration - the interactive {@code oauth2Login} redirect flow already honored
 * {@code ai.gebo.security.loginPolicy}, but only it, via
 * {@code GOAuth2UserService}/{@code IGSecurityDirectory#createUserIfNotExists}. This
 * path now does the same: when the policy is
 * {@link GeboLoginPolicy#TRUST_EVERY_OAUTH_IDENTITY}, an unknown identity is
 * provisioned (through the same {@link IGSecurityDirectory} seam - so it lands on the
 * one real user store regardless of which service validated the token, see
 * {@code MongoSecurityDirectory}/{@code RestSecurityDirectory}) instead of rejected.
 * {@link GeboLoginPolicy#REQUIRE_INVITATION}/{@link GeboLoginPolicy#USER_SELF_REGISTERS}
 * keep today's behavior: unknown identities are refused.
 * </p>
 *
 * <p>
 * Provisioning needs an {@link AuthProvider} to record against the new user.
 * Unlike the redirect flow (which already knows which OAuth2 client registration is
 * logging in), this converter only ever sees the validated {@link Jwt} - so the
 * provider is re-derived from the token's own {@code iss} claim, matched against the
 * configured {@code AUTHENTICATION} oauth2configs and cached per issuer via
 * {@link IssuerConfigCache} (the same cache the authentication-manager resolver uses
 * to avoid scanning every configured provider on every request).
 * </p>
 */
@AllArgsConstructor
public class GJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
	private final UserDetailsService userDetailsService;
	private final GeboSecurityConfig securityConfig;
	private final IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao;
	private final IGSecurityDirectory securityDirectory;
	private final IssuerConfigCache issuerConfigCache;

	@Override
	@Nullable
	public AbstractAuthenticationToken convert(Jwt source) {
		String email = source.getClaim("email");
		if (email == null) {
			email = source.getSubject();
		}
		UserDetails user;
		try {
			user = userDetailsService.loadUserByUsername(email);
		} catch (UsernameNotFoundException notFound) {
			if (securityConfig.getLoginPolicy() != GeboLoginPolicy.TRUST_EVERY_OAUTH_IDENTITY) {
				throw notFound;
			}
			securityDirectory.createUserIfNotExists(email, source.getClaims(), resolveAuthProvider(source));
			// Re-fetch rather than build UserDetails from the freshly created UserInfos
			// directly: userDetailsService may be the DirectoryBackedUserDetailsService
			// (system-user check, disabled check, UserPrincipal construction) or a
			// different implementation entirely - going back through it keeps this
			// converter agnostic to which one, exactly as the pre-provisioning lookup
			// above already was.
			user = userDetailsService.loadUserByUsername(email);
		}
		return new JwtAuthenticationToken(source, user.getAuthorities(), user.getUsername());
	}

	private AuthProvider resolveAuthProvider(Jwt source) {
		String issuer = source.getIssuer() != null ? source.getIssuer().toString() : null;
		Oauth2RuntimeConfiguration matched = issuerConfigCache.forIssuer(issuer, () -> findByIssuer(issuer))
				.orElse(null);
		return matched != null && matched.getProvider() != null ? matched.getProvider() : AuthProvider.oauth2_generic;
	}

	private Oauth2RuntimeConfiguration findByIssuer(String issuer) {
		if (issuer == null) {
			return null;
		}
		return oauth2RuntimeConfigurationDao.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION).stream()
				.filter(config -> config.getProviderConfig() != null
						&& issuer.equals(config.getProviderConfig().getIssuerUri()))
				.findFirst().orElse(null);
	}
}
