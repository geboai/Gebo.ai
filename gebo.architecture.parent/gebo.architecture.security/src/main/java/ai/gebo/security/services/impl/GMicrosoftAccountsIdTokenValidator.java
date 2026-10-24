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

import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;

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
 *
 * <p>
 * A token sent to the API through the same configuration is held to the same rule,
 * {@link #tenantRefusal(Jwt, Set, String)}, by
 * {@code GeboMicrosoftAccountsTenantValidator}.
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
		String refusal = tenantRefusal(idToken, admittedTenants, registration.getRegistrationId());
		if (refusal != null) {
			return invalid(refusal);
		}
		return GOidcIdTokenValidatorFactory
				.springDefault(ClientRegistration.withClientRegistration(registration)
						.issuerUri(idToken.getClaimAsString(JwtClaimNames.ISS)).build())
				.validate(idToken);
	}

	/**
	 * Why a token of Microsoft's is not admitted on the tenant it comes from, or null
	 * when it is: its issuer must be the one of the tenant it names in {@code tid},
	 * and that tenant must be one of those admitted. The same rule holds for the id
	 * token of a login and for a token sent to the API.
	 *
	 * @param token           the token, its signature already verified
	 * @param admittedTenants the tenant ids admitted, as {@link #admittedTenants(Map)}
	 *                        reads them
	 * @param admittedBy      the registration admitting them, named in the refusal
	 * @return the reason for refusing it, or null
	 */
	public static String tenantRefusal(Jwt token, Set<String> admittedTenants, String admittedBy) {
		String tenant = token.getClaimAsString("tid");
		String issuer = token.getClaimAsString(JwtClaimNames.ISS);
		if (tenant == null || issuer == null || !issuer.equals(ISSUER_PREFIX + tenant + ISSUER_SUFFIX)) {
			return "The token issuer " + issuer + " is not the one of its tenant " + tenant;
		}
		if (!admittedTenants.contains(tenant.toLowerCase(Locale.ROOT))) {
			return "The token was issued for tenant " + tenant + ", which " + admittedBy + " does not admit";
		}
		return null;
	}

	/**
	 * Whether this is the issuer of a tenant admitted, read from the issuer alone:
	 * what an unverified look at a token can tell about which configuration it is
	 * for. Whether the token really comes from that tenant is
	 * {@link #tenantRefusal(Jwt, Set, String)}'s to say, once it is verified.
	 *
	 * @param issuer          a token's {@code iss}, possibly null
	 * @param admittedTenants the tenant ids admitted
	 * @return true when the issuer is {@code https://login.microsoftonline.com/{tid}/v2.0}
	 *         for an admitted {@code tid}
	 */
	public static boolean admitsIssuer(String issuer, Set<String> admittedTenants) {
		if (issuer == null || !issuer.startsWith(ISSUER_PREFIX) || !issuer.endsWith(ISSUER_SUFFIX)
				|| issuer.length() <= ISSUER_PREFIX.length() + ISSUER_SUFFIX.length()) {
			return false;
		}
		String tenant = issuer.substring(ISSUER_PREFIX.length(), issuer.length() - ISSUER_SUFFIX.length());
		return !tenant.contains("/") && admittedTenants.contains(tenant.toLowerCase(Locale.ROOT));
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

	/**
	 * The tenants a stored configuration admits, read from the client saved with it.
	 * One whose client could not be read admits personal accounts only, as one that
	 * lists none does.
	 *
	 * @param configuration a configuration as the store hands it out, its client
	 *                      included
	 * @return the admitted tenant ids, lower case
	 */
	public static Set<String> admittedTenants(Oauth2RuntimeConfiguration configuration) {
		return admittedTenants(
				configuration.getClient() != null ? configuration.getClient().getCustomAttributes() : null);
	}

	private static OAuth2TokenValidatorResult invalid(String description) {
		return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_id_token", description, null));
	}
}
