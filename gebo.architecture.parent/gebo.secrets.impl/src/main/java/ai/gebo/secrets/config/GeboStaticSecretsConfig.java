/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.secrets.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboAwsConnectionCredentials;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboGoogleJsonSecretContent;
import ai.gebo.secrets.model.GeboGoogleOauth2SecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboSshKeySecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The secrets declared in the configuration, one list per
 * {@link GeboSecretType}, under {@code ai.gebo.secrets.config}:
 *
 * <pre>
 * ai.gebo.secrets.config:
 *   username-password:
 *     - code: my-service-account
 *       description: Service account of the nightly ingestion
 *       context-code: SYSTEMS
 *       secret:
 *         username: ingestion
 *         password: ${INGESTION_PASSWORD}
 *   token:
 *     - code: my-api-token
 *       context-code: LLMS
 *       secret:
 *         token: ${SOME_API_KEY}
 *         user: gebo
 * </pre>
 *
 * <p>
 * A list per type rather than one list with a discriminator, because the binder
 * needs the concrete content class to bind an entry's {@code secret} block: each
 * field's element type supplies it, so {@code username-password} entries bind as
 * {@link GeboUsernamePasswordContent}, {@code token} entries as
 * {@link GeboTokenContent}. It is also what lets {@code @Validated} reach the
 * constraints the content classes already carry: {@code List<@Valid ...>}
 * cascades into each entry and on into its {@code secret}, so a
 * {@link GeboUsernamePasswordContent} declared without a password fails the
 * startup instead of authenticating with half a credential.
 * </p>
 *
 * <p>
 * That cascade is the strongest check available here. {@code ignoreUnknownFields
 * = false} is deliberately NOT set: Spring Boot's unbound-element check skips
 * anything nested inside an indexed collection element - every property in these
 * lists, that is - so it would buy nothing while suggesting a guarantee that
 * does not hold. A mistyped content property with no constraint behind it (say
 * {@code tokenn} for {@code token}) therefore binds to nothing and is reported
 * only when the secret is read.
 * </p>
 *
 * <h2>Why this bean lives in the implementation module</h2>
 * <p>
 * Only the service that OWNS the secrets store - the monolith, or
 * heimdall.gebo.ai - depends on {@code gebo.secrets.impl}; every other service
 * reaches secrets through {@code gebo.microservices.secrets.client}. Declaring
 * the configured secrets here therefore keeps their plaintext in the
 * configuration of exactly one service, the same one that already holds the
 * crypting keys, instead of spreading it across the cluster.
 * </p>
 *
 * <h2>{@code OAUTH2_AUTHORIZED_CLIENT} is deliberately absent</h2>
 * <p>
 * That type's content ({@code GeboOauth2TokenSecretContent}) holds Spring
 * Security's {@code OAuth2AccessToken} / {@code OAuth2RefreshToken}, immutable
 * types with no default constructor that the relaxed binder cannot construct.
 * It is also the one secret type the runtime writes for itself - the authorized
 * client service stores a user's tokens under it and refreshes them - so a
 * read-only declaration of one could never work: the first refresh would be
 * refused. It is a runtime artefact, not a deployment setting.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.secrets.config")
@Validated
@Data
public class GeboStaticSecretsConfig {

	/** {@code ai.gebo.secrets.config.username-password} */
	private List<@Valid GeboStaticSecretEntry<GeboUsernamePasswordContent>> usernamePassword = null;

	/** {@code ai.gebo.secrets.config.token} */
	private List<@Valid GeboStaticSecretEntry<GeboTokenContent>> token = null;

	/** {@code ai.gebo.secrets.config.ssh-key} */
	private List<@Valid GeboStaticSecretEntry<GeboSshKeySecretContent>> sshKey = null;

	/** {@code ai.gebo.secrets.config.custom-secret} */
	private List<@Valid GeboStaticSecretEntry<GeboCustomSecretContent>> customSecret = null;

	/** {@code ai.gebo.secrets.config.oauth2-standard} */
	private List<@Valid GeboStaticSecretEntry<GeboOauth2SecretContent>> oauth2Standard = null;

	/** {@code ai.gebo.secrets.config.oauth2-google} */
	private List<@Valid GeboStaticSecretEntry<GeboGoogleOauth2SecretContent>> oauth2Google = null;

	/** {@code ai.gebo.secrets.config.google-cloud-json-credentials} */
	private List<@Valid GeboStaticSecretEntry<GeboGoogleJsonSecretContent>> googleCloudJsonCredentials = null;

	/** {@code ai.gebo.secrets.config.aws-connection} */
	private List<@Valid GeboStaticSecretEntry<GeboAwsConnectionCredentials>> awsConnection = null;

	/**
	 * Every declared entry, in the order the types are declared above.
	 *
	 * <p>
	 * The entries carry their own content, and a content knows its
	 * {@code GeboSecretType}, so nothing downstream has to remember which list an
	 * entry came from.
	 * </p>
	 *
	 * @return all configured entries; empty when nothing is configured.
	 */
	public List<GeboStaticSecretEntry<? extends AbstractGeboSecretContent>> allEntries() {
		List<GeboStaticSecretEntry<? extends AbstractGeboSecretContent>> all = new ArrayList<>();
		addAll(all, usernamePassword);
		addAll(all, token);
		addAll(all, sshKey);
		addAll(all, customSecret);
		addAll(all, oauth2Standard);
		addAll(all, oauth2Google);
		addAll(all, googleCloudJsonCredentials);
		addAll(all, awsConnection);
		return all;
	}

	private void addAll(List<GeboStaticSecretEntry<? extends AbstractGeboSecretContent>> target,
			List<? extends GeboStaticSecretEntry<? extends AbstractGeboSecretContent>> source) {
		if (source != null) {
			target.addAll(source);
		}
	}
}
