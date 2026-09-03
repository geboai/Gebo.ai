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
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.SecurityAuditTaxonomy;
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
 * <b>Deliberately not delegated to
 * {@code ai.gebo.security.services.impl.authmanagers.GOauth2ResourceServerUserProvisioner}</b>
 * (the mechanism opaque tokens use): that provisioner calls {@code IGUsersAdminService}
 * directly, which is backed by a plain Mongo-repository implementation with no
 * microservices-safe counterpart (unlike {@link IGSecurityDirectory}, which
 * {@code RestSecurityDirectory} proxies to heimdall for every non-owning service). JWT
 * resource-server auth runs on every microservice, so it must stay on the
 * {@link IGSecurityDirectory} seam to keep working correctly off the monolith/heimdall.
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
 *
 * <h2>Auditing</h2>
 * <p>
 * The provisioning decision is audited here as {@code oauth2IdentityProvision} - this
 * converter's own decision, which the {@code userAutoProvision} raised further down by
 * the security directory does not replace: only this one names the issuer, the resolved
 * provider and the policy. It is recorded in both directions: an unknown identity admitted under
 * {@link GeboLoginPolicy#TRUST_EVERY_OAUTH_IDENTITY}, and an unknown identity refused
 * under the other policies. The refusal matters as much as the admission - it is a
 * validated token from a configured issuer being turned away, which without an event
 * of its own is just another 401 in the access log.
 * </p>
 *
 * <p>
 * Unlike the opaque-token path this converter has no throttle cache in front of it, so
 * the refusal branch would fire on every request carrying the same unknown token. It
 * is therefore audited only where an event is genuinely new information - see the
 * comment on that branch.
 * </p>
 */
@AllArgsConstructor
public class GJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
	private final UserDetailsService userDetailsService;
	private final GeboSecurityConfig securityConfig;
	private final IGOauth2RuntimeConfigurationDao oauth2RuntimeConfigurationDao;
	private final IGSecurityDirectory securityDirectory;
	private final IssuerConfigCache issuerConfigCache;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	/** {@code resourceType} of the events raised here: an external identity. */
	private static final String RESOURCE_TYPE_USER = "user";

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
				// Not audited, for the same reason the opaque-token provisioner does not audit
				// its policy gate: with no throttle in front of this converter, a client
				// retrying one unknown token would write an event per request. The refusal is
				// a standing consequence of the configured policy, and the 401 the caller
				// gets already records the attempt.
				throw notFound;
			}
			SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
			event.getDetails().put("flow", "oauth2ResourceServerJwt");
			event.getDetails().put("issuer", source.getIssuer() != null ? source.getIssuer().toString() : null);
			event.getDetails().put("loginPolicy", String.valueOf(securityConfig.getLoginPolicy()));
			AuthProvider provider = resolveAuthProvider(source);
			event.getDetails().put("authProvider", String.valueOf(provider));
			// Claim NAMES, never their values: a token's claims can carry anything the
			// issuer chose to put in them.
			event.getDetails().put("usernameFromClaim", source.getClaim("email") != null ? "email" : "sub");
			try {
				securityDirectory.createUserIfNotExists(email, source.getClaims(), provider);
				// Re-fetch rather than build UserDetails from the freshly created UserInfos
				// directly: userDetailsService may be the DirectoryBackedUserDetailsService
				// (system-user check, disabled check, UserPrincipal construction) or a
				// different implementation entirely - going back through it keeps this
				// converter agnostic to which one, exactly as the pre-provisioning lookup
				// above already was.
				user = userDetailsService.loadUserByUsername(email);
			} catch (RuntimeException e) {
				// Includes the re-fetch: a provisioning call that "succeeded" but left the
				// user still unloadable (a disabled account, the system identity) is a
				// failure of this decision, and the most interesting one to see.
				event.getDetails().put("error", e.getMessage());
				logProvisioningEvent(event, email, SecurityAuditTaxonomy.Outcome.FAILURE);
				throw e;
			}
			logProvisioningEvent(event, email, SecurityAuditTaxonomy.Outcome.SUCCESS);
		}
		return new JwtAuthenticationToken(source, user.getAuthorities(), user.getUsername());
	}

	/**
	 * Fills in and emits a provisioning event. Takes an already-created
	 * {@link SecurityEvent} so the caller-stack captured by {@code newSecurityEvent()}
	 * points at {@code convert}, not at this helper.
	 */
	private void logProvisioningEvent(SecurityEvent event, String username, String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION);
		event.setResourceType(RESOURCE_TYPE_USER);
		event.setResourceId(username);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
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
