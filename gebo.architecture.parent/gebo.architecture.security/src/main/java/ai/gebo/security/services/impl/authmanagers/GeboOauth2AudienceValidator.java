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
 * Two claims are consulted, because OIDC providers disagree on where the
 * recipient lands:
 * <ul>
 * <li>{@code aud} - the standard audience, a list; carried by OIDC id tokens and
 * by Keycloak/Entra access tokens.</li>
 * <li>{@code client_id} - where AWS Cognito puts the recipient on its
 * <b>access</b> tokens (their {@code aud} is absent). Consulted so a Cognito
 * access token can be pinned to its app client id.</li>
 * </ul>
 * The token passes when any of its {@code aud} values, or its {@code client_id},
 * is one of the accepted values.
 * </p>
 */
public final class GeboOauth2AudienceValidator implements OAuth2TokenValidator<Jwt> {

	private static final String CLIENT_ID_CLAIM = "client_id";

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
		return OAuth2TokenValidatorResult.failure(error);
	}
}
