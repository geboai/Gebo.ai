/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl.authmanagers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidator;

/**
 * Remembers which configured OAuth2 provider a JWT {@code iss} claim belongs to, so
 * that the configurations are not all scanned for every token presented to the API.
 *
 * <p>
 * It remembers the <b>answer</b>, never the configuration. Every lookup reads the
 * configuration again by its registration id, a primary-key read, and that read is the
 * authority: a provider deleted by an administrator is unknown from the very next
 * request, and one edited, its accepted audiences for instance, is applied from the
 * very next request.
 * </p>
 *
 * <p>
 * It used to hold the configurations themselves for the life of the process. Deleting
 * a provider then left its tokens accepted until a restart, which is the one thing an
 * administrator deleting a provider must be able to count on, and an edit only applied
 * after a restart too.
 * </p>
 *
 * <p>
 * An issuer that matches nothing is not remembered either. Remembering it would leave
 * a provider added for that issuer later unknown until a restart, the same fault the
 * other way round; and a token from an unknown issuer goes on to try every configured
 * provider anyway.
 * </p>
 *
 * <p>
 * Which issuers a configuration answers for is {@link #answersFor}'s to say, here and
 * wherever a configuration is looked up by issuer: its own one, and for the Microsoft
 * accounts configuration that of each tenant it admits.
 * </p>
 */
public final class IssuerConfigCache {

	private final ConcurrentHashMap<String, String> registrationIdsByIssuer = new ConcurrentHashMap<>();
	private final Function<String, Oauth2RuntimeConfiguration> byRegistrationId;

	/**
	 * @param byRegistrationId reads a configuration as it stands now, null when it no
	 *                         longer exists
	 */
	public IssuerConfigCache(Function<String, Oauth2RuntimeConfiguration> byRegistrationId) {
		this.byRegistrationId = byRegistrationId;
	}

	/**
	 * The configuration this issuer belongs to, as it stands now.
	 *
	 * @param issuer the JWT {@code iss} claim; {@code null} always resolves to
	 *               {@link Optional#empty()} without invoking anything
	 * @param loader finds the matching configuration among all of them, or returns
	 *               {@code null}; invoked when the issuer is not known yet, or when the
	 *               configuration it was known by is gone or no longer has this issuer
	 * @return the matching configuration, or empty if none matches this issuer now
	 */
	public Optional<Oauth2RuntimeConfiguration> forIssuer(String issuer,
			Supplier<Oauth2RuntimeConfiguration> loader) {
		if (issuer == null) {
			return Optional.empty();
		}
		String known = registrationIdsByIssuer.get(issuer);
		if (known != null) {
			Oauth2RuntimeConfiguration current = byRegistrationId.apply(known);
			if (current != null && answersFor(current, issuer)) {
				return Optional.of(current);
			}
			// deleted, or edited to another issuer or to no longer admit its tenant:
			// forget it and look again
			registrationIdsByIssuer.remove(issuer, known);
		}
		Oauth2RuntimeConfiguration found = loader.get();
		if (found != null && found.getRegistrationId() != null) {
			registrationIdsByIssuer.put(issuer, found.getRegistrationId());
		}
		return Optional.ofNullable(found);
	}

	/**
	 * Whether a token signed under this issuer belongs to this configuration.
	 *
	 * <p>
	 * For every provider but one that is its own issuer, compared exactly. The
	 * Microsoft accounts configuration also answers for the issuer of each tenant it
	 * admits, {@code https://login.microsoftonline.com/{tid}/v2.0}: its people
	 * belong to several tenants, and each signs under an issuer of its own.
	 * </p>
	 *
	 * <p>
	 * Like the rest of this class, it only says which configuration's checks a token
	 * goes to. It reads an issuer nobody has verified yet, and the decoder of that
	 * configuration then verifies the token in full.
	 * </p>
	 *
	 * @param configuration a configuration as the store hands it out
	 * @param issuer        the {@code iss} of a token, possibly null
	 */
	public static boolean answersFor(Oauth2RuntimeConfiguration configuration, String issuer) {
		if (issuer == null || configuration.getProviderConfig() == null) {
			return false;
		}
		if (issuer.equals(configuration.getProviderConfig().getIssuerUri())) {
			return true;
		}
		return configuration.getProvider() == AuthProvider.microsoft && GMicrosoftAccountsIdTokenValidator
				.admitsIssuer(issuer, GMicrosoftAccountsIdTokenValidator.admittedTenants(configuration));
	}

	/**
	 * The configuration among these that a token signed under this issuer belongs
	 * to, preferring the one whose own issuer it is: an organization configured on
	 * its own is meant more precisely than the Microsoft accounts configuration that
	 * also admits it.
	 *
	 * @param configurations the configurations to look among
	 * @param issuer         the {@code iss} of a token, possibly null
	 * @return the configuration, or null when none answers for the issuer
	 */
	public static Oauth2RuntimeConfiguration answeringFor(List<Oauth2RuntimeConfiguration> configurations,
			String issuer) {
		if (issuer == null) {
			return null;
		}
		Oauth2RuntimeConfiguration admitting = null;
		for (Oauth2RuntimeConfiguration configuration : configurations) {
			if (configuration.getProviderConfig() != null
					&& issuer.equals(configuration.getProviderConfig().getIssuerUri())) {
				return configuration;
			}
			if (admitting == null && answersFor(configuration, issuer)) {
				admitting = configuration;
			}
		}
		return admitting;
	}
}
