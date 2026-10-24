/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

/**
 * Checks the id token of a login through the Microsoft accounts configuration,
 * whose people come from more than one Microsoft tenant.
 *
 * <p>
 * Microsoft's shared ("common") endpoints sign each id token under the issuer of the
 * tenant the person belongs to, {@code https://login.microsoftonline.com/{tid}/v2.0},
 * and publish no issuer of their own, so Spring's check against the one issuer of
 * the registration cannot hold. This takes its place, as Microsoft documents for an
 * application that admits several tenants: the issuer must be the one of the tenant
 * the token names in {@code tid}, and that tenant must be one the configuration
 * admits. Every other check Spring makes on an id token, its audience, authorized
 * party, subject and times, then runs as usual against that issuer.
 * </p>
 *
 * <p>
 * Admitting tenants one by one matters beyond the signature: Gebo.ai finds the user
 * by the token's {@code email}, and an organization's administrators decide what the
 * e-mail addresses of their people are, so admitting every tenant would let any of
 * them sign in as an invited user. Personal accounts all belong to one tenant of
 * Microsoft's, admitted with {@value #PERSONAL_ACCOUNTS}.
 * </p>
 */
public class GMicrosoftAccountsIdTokenValidator implements OAuth2TokenValidator<Jwt> {

	/** The custom attribute listing the tenants a configuration admits. */
	public static final String ALLOWED_TENANTS = "allowedTenants";
	/** How personal Microsoft accounts are named in that list. */
	public static final String PERSONAL_ACCOUNTS = "consumers";
	/** The tenant every personal Microsoft account belongs to. */
	public static final String PERSONAL_ACCOUNTS_TENANT = "9188040d-6c67-4c5b-b112-36a304b66dad";

	private static final String ISSUER_PREFIX = "https://login.microsoftonline.com/";
	private static final String ISSUER_SUFFIX = "/v2.0";

	private final ClientRegistration registration;
	private final Set<String> admittedTenants;

	/**
	 * @param registration    the registration logging in
	 * @param admittedTenants the tenant ids it admits, lower case, as
	 *                        {@link #admittedTenants(Map)} reads them
	 */
	public GMicrosoftAccountsIdTokenValidator(ClientRegistration registration, Set<String> admittedTenants) {
		this.registration = registration;
		this.admittedTenants = admittedTenants;
	}

	@Override
	public OAuth2TokenValidatorResult validate(Jwt idToken) {
		String tenant = idToken.getClaimAsString("tid");
		String issuer = idToken.getClaimAsString(JwtClaimNames.ISS);
		if (tenant == null || issuer == null || !issuer.equals(ISSUER_PREFIX + tenant + ISSUER_SUFFIX)) {
			return invalid("The ID Token issuer " + issuer + " is not the one of its tenant " + tenant);
		}
		if (!admittedTenants.contains(tenant.toLowerCase(Locale.ROOT))) {
			return invalid("The ID Token was issued for tenant " + tenant + ", which "
					+ registration.getRegistrationId() + " does not admit");
		}
		return GOidcIdTokenValidatorFactory
				.springDefault(ClientRegistration.withClientRegistration(registration).issuerUri(issuer).build())
				.validate(idToken);
	}

	/**
	 * The tenants a configuration admits, from its {@value #ALLOWED_TENANTS} custom
	 * attribute: entries separated by commas, semicolons or blanks, each a tenant id
	 * or {@value #PERSONAL_ACCOUNTS}. A configuration that lists none, as one saved
	 * before the attribute existed, admits personal accounts only, which is all that
	 * one was meant for.
	 *
	 * @param customAttributes the custom attributes of the configuration, possibly
	 *                         null
	 * @return the admitted tenant ids, lower case
	 */
	public static Set<String> admittedTenants(Map<String, String> customAttributes) {
		String listed = customAttributes != null ? customAttributes.get(ALLOWED_TENANTS) : null;
		Set<String> tenants = listed == null ? Set.of()
				: Arrays.stream(listed.split("[,;\\s]+")).filter(entry -> !entry.isEmpty())
						.map(entry -> entry.toLowerCase(Locale.ROOT))
						.map(entry -> entry.equals(PERSONAL_ACCOUNTS) ? PERSONAL_ACCOUNTS_TENANT : entry)
						.collect(Collectors.toUnmodifiableSet());
		return tenants.isEmpty() ? Set.of(PERSONAL_ACCOUNTS_TENANT) : tenants;
	}

	private static OAuth2TokenValidatorResult invalid(String description) {
		return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_id_token", description, null));
	}
}
