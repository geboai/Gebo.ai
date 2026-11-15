/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Rejects a resource-server JWT whose intended recipient is not one this Gebo
 * registration accepts.
 *
 * <p>
 * The per-issuer decoder built by {@link JwtDecoderCache} validates signature,
 * issuer and expiry only ({@code JwtDecoders.fromIssuerLocation} +
 * {@code JwtValidators.createDefaultWithIssuer}). Without an audience check, any
 * token minted by <i>any</i> client of the same issuer is accepted - every app
 * client in a Cognito user pool, every client in a Keycloak realm - and because
 * {@code TRUST_EVERY_OAUTH_IDENTITY} auto-provisions on first sight, a token that
 * was never meant for Gebo silently creates a working account. This validator,
 * added only when a registration declares its accepted audiences, closes that:
 * the token must be addressed to this deployment.
 * </p>
 *
 * <p>
 * Three claims are consulted, because providers disagree on where they say which
 * application a token is for:
 * <ul>
 * <li>{@code aud} - the standard audience, a list; carried by OIDC id tokens, by
 * Entra access tokens, and by Keycloak access tokens once the client has an
 * audience mapper.</li>
 * <li>{@code client_id} - the application the token was issued to, where AWS
 * Cognito puts it on its <b>access</b> tokens (their {@code aud} is absent).
 * Consulted so a Cognito access token can be pinned to its app client id.</li>
 * <li>{@code azp} - the same thing under Keycloak's name. A default Keycloak realm
 * addresses a user's access token to its own account service ({@code aud:
 * account}) and names the client only here, so without it the obvious setting,
 * the client id, refused every token of the realm it was meant to accept.</li>
 * </ul>
 * The token passes when any of its {@code aud} values, its {@code client_id} or
 * its {@code azp} is one of the accepted values.
 * </p>
 *
 * <p>
 * Accepting the requesting application is what the {@code client_id} rule already
 * did for Cognito, and it still refuses what this check exists for: a token issued
 * to another application of the same issuer names that application. What it lets
 * through, and a strict reading of {@code aud} would not, is a token this
 * deployment's own client obtained for some other API. A deployment that wants the
 * strict reading gives its tokens an audience of their own, distinct from the
 * client id, and accepts that alone.
 * </p>
 */
public final class GeboOauth2AudienceValidator implements OAuth2TokenValidator<Jwt> {

	private static final String CLIENT_ID_CLAIM = "client_id";
	private static final String AUTHORIZED_PARTY_CLAIM = "azp";

	private final Set<String> acceptedAudiences;
	private final OAuth2Error error;

	/**
	 * @param acceptedAudiences the audiences (or client ids) this registration
	 *                          admits; must be non-null and non-empty - a caller
	 *                          with nothing to enforce must not install this
	 *                          validator at all
	 */
	public GeboOauth2AudienceValidator(Collection<String> acceptedAudiences) {
		if (acceptedAudiences == null || acceptedAudiences.isEmpty()) {
			throw new IllegalArgumentException("acceptedAudiences must not be empty");
		}
		this.acceptedAudiences = new LinkedHashSet<>(acceptedAudiences);
		this.error = new OAuth2Error("invalid_token",
				"The token audience is not accepted by this resource server", null);
	}

	@Override
	public OAuth2TokenValidatorResult validate(Jwt token) {
		List<String> tokenAudiences = token.getAudience();
		if (tokenAudiences != null) {
			for (String audience : tokenAudiences) {
				if (acceptedAudiences.contains(audience)) {
					return OAuth2TokenValidatorResult.success();
				}
			}
		}
		String clientId = token.getClaimAsString(CLIENT_ID_CLAIM);
		if (clientId != null && acceptedAudiences.contains(clientId)) {
			return OAuth2TokenValidatorResult.success();
		}
		String authorizedParty = token.getClaimAsString(AUTHORIZED_PARTY_CLAIM);
		if (authorizedParty != null && acceptedAudiences.contains(authorizedParty)) {
			return OAuth2TokenValidatorResult.success();
		}
		return OAuth2TokenValidatorResult.failure(error);
	}
}
