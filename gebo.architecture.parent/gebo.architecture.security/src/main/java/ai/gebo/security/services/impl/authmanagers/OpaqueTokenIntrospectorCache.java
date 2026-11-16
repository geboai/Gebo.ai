/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.RestClientOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringReactiveOpaqueTokenIntrospector;

import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;

/**
 * Thread-safe cache of the introspectors asking a single sign-on provider whether an
 * opaque (non JWT) access token it issued is active, one per configuration.
 *
 * <p>
 * The introspectors are Spring Security 7.2's: {@link RestClientOpaqueTokenIntrospector}
 * for the servlet stack and {@link SpringReactiveOpaqueTokenIntrospector} (WebClient)
 * for the reactive one, built from the configuration's introspection endpoint and
 * client credentials. Both post the token as an
 * {@code application/x-www-form-urlencoded} body ({@code token=...}) with the client's
 * HTTP basic authentication, as RFC 7662 requires. They replace the three-argument
 * constructors Spring Security 7.2 deprecated for removal, the servlet one resting on
 * {@code RestTemplate}, itself deprecated for removal.
 * </p>
 *
 * <p>
 * A configuration's introspector used to be built on every request; it is now built
 * once and reused. The endpoint, the client id and a digest of the client secret make
 * the key, so an edited configuration gets a new introspector from its next request,
 * and the secret itself is never part of a key.
 * </p>
 */
public final class OpaqueTokenIntrospectorCache {

	private final ConcurrentHashMap<String, OpaqueTokenIntrospector> servlet = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, ReactiveOpaqueTokenIntrospector> reactive = new ConcurrentHashMap<>();

	/**
	 * The introspector of a configuration for the servlet stack.
	 *
	 * @throws IllegalArgumentException when the configuration has no introspection
	 *                                  endpoint or no client
	 */
	public OpaqueTokenIntrospector forConfiguration(Oauth2RuntimeConfiguration configuration) {
		return servlet.computeIfAbsent(cacheKey(configuration), k -> build(configuration));
	}

	/**
	 * The introspector of a configuration for the reactive stack.
	 *
	 * @throws IllegalArgumentException when the configuration has no introspection
	 *                                  endpoint or no client
	 */
	public ReactiveOpaqueTokenIntrospector reactiveForConfiguration(Oauth2RuntimeConfiguration configuration) {
		return reactive.computeIfAbsent(cacheKey(configuration), k -> buildReactive(configuration));
	}

	/** A servlet introspector of a configuration, not cached. */
	public static OpaqueTokenIntrospector build(Oauth2RuntimeConfiguration configuration) {
		checkIntrospectable(configuration);
		return RestClientOpaqueTokenIntrospector
				.withIntrospectionUri(configuration.getProviderConfig().getIntrospectionUri())
				.clientId(configuration.getClient().getClientId()).clientSecret(configuration.getClient().getSecret())
				.build();
	}

	/** A reactive introspector of a configuration, not cached. */
	public static ReactiveOpaqueTokenIntrospector buildReactive(Oauth2RuntimeConfiguration configuration) {
		checkIntrospectable(configuration);
		return SpringReactiveOpaqueTokenIntrospector
				.withIntrospectionUri(configuration.getProviderConfig().getIntrospectionUri())
				.clientId(configuration.getClient().getClientId()).clientSecret(configuration.getClient().getSecret())
				.build();
	}

	private static void checkIntrospectable(Oauth2RuntimeConfiguration configuration) {
		if (configuration == null || configuration.getProviderConfig() == null
				|| configuration.getProviderConfig().getIntrospectionUri() == null
				|| configuration.getClient() == null) {
			throw new IllegalArgumentException("The oauth2 configuration "
					+ (configuration != null ? configuration.getRegistrationId() : null)
					+ " has no introspection endpoint or no client");
		}
	}

	// Space-separated: a space is illegal in the endpoint and in a client id
	static String cacheKey(Oauth2RuntimeConfiguration configuration) {
		checkIntrospectable(configuration);
		return configuration.getProviderConfig().getIntrospectionUri() + " "
				+ configuration.getClient().getClientId() + " " + digest(configuration.getClient().getSecret());
	}

	private static String digest(String secret) {
		if (secret == null) {
			return "-";
		}
		try {
			return HexFormat.of()
					.formatHex(MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 is not available", e);
		}
	}
}
