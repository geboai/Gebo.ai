/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl.authmanagers;

import java.util.Set;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidator;

/**
 * Rejects a resource-server JWT of the Microsoft accounts configuration that does
 * not come from a tenant the configuration admits.
 *
 * <p>
 * The people of that configuration belong to several Microsoft tenants, and each
 * tenant signs its tokens under an issuer of its own, so the one-issuer check every
 * other provider's decoder makes cannot hold. This takes its place, with the rule
 * the login applies to the id token: the issuer must be the one of the tenant the
 * token names, and the tenant must be listed in the configuration's
 * {@value GMicrosoftAccountsIdTokenValidator#ALLOWED_TENANTS}.
 * </p>
 */
public final class GeboMicrosoftAccountsTenantValidator implements OAuth2TokenValidator<Jwt> {

	private final Set<String> admittedTenants;
	private final String registrationId;

	/**
	 * @param admittedTenants the tenant ids the configuration admits, as
	 *                        {@link GMicrosoftAccountsIdTokenValidator#admittedTenants}
	 *                        reads them
	 * @param registrationId  the configuration, named when a token is refused
	 */
	public GeboMicrosoftAccountsTenantValidator(Set<String> admittedTenants, String registrationId) {
		this.admittedTenants = Set.copyOf(admittedTenants);
		this.registrationId = registrationId;
	}

	@Override
	public OAuth2TokenValidatorResult validate(Jwt token) {
		String refusal = GMicrosoftAccountsIdTokenValidator.tenantRefusal(token, admittedTenants, registrationId);
		return refusal == null ? OAuth2TokenValidatorResult.success()
				: OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", refusal, null));
	}
}
