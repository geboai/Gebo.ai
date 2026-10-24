/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl.authmanagers;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;

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
			if (current != null && current.getProviderConfig() != null
					&& issuer.equals(current.getProviderConfig().getIssuerUri())) {
				return Optional.of(current);
			}
			// deleted, or edited to another issuer: forget it and look again
			registrationIdsByIssuer.remove(issuer, known);
		}
		Oauth2RuntimeConfiguration found = loader.get();
		if (found != null && found.getRegistrationId() != null) {
			registrationIdsByIssuer.put(issuer, found.getRegistrationId());
		}
		return Optional.ofNullable(found);
	}
}
