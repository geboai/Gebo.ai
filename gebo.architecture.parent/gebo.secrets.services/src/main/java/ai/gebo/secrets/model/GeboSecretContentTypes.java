/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.secrets.model;

import java.util.Map;

/**
 * The {@link GeboSecretType} &rarr; concrete {@link AbstractGeboSecretContent}
 * class mapping.
 *
 * <p>
 * {@link AbstractGeboSecretContent} carries no Jackson type information on
 * purpose: the secrets implementation serialises a content with a plain
 * {@code writeValueAsString} before encrypting it and reads it back with
 * {@code readValue(json, concreteType)}. Adding {@code @JsonTypeInfo} to the
 * model would change that stored representation and make every already-encrypted
 * secret unreadable. The type is therefore carried <b>beside</b> the JSON (see
 * {@link GeboSecretContentEnvelope} and {@link GeboSecretStoreRequest}) and
 * resolved through this map on both ends of the wire.
 * </p>
 *
 * Gebo.ai comment agent
 */
public final class GeboSecretContentTypes {

	private static final Map<GeboSecretType, Class<? extends AbstractGeboSecretContent>> CONTENT_CLASSES = Map.of(
			GeboSecretType.USERNAME_PASSWORD, GeboUsernamePasswordContent.class,
			GeboSecretType.TOKEN, GeboTokenContent.class,
			GeboSecretType.SSH_KEY, GeboSshKeySecretContent.class,
			GeboSecretType.CUSTOM_SECRET, GeboCustomSecretContent.class,
			GeboSecretType.OAUTH2_STANDARD, GeboOauth2SecretContent.class,
			GeboSecretType.OAUTH2_GOOGLE, GeboGoogleOauth2SecretContent.class,
			GeboSecretType.GOOGLE_CLOUD_JSON_CREDENTIALS, GeboGoogleJsonSecretContent.class,
			GeboSecretType.AWS_CONNECTION, GeboAwsConnectionCredentials.class,
			GeboSecretType.OAUTH2_AUTHORIZED_CLIENT, GeboOauth2TokenSecretContent.class);

	private GeboSecretContentTypes() {
		// constants holder
	}

	/**
	 * The concrete content class a secret of the given type deserialises into.
	 *
	 * @param secretType the secret type; may be {@code null}
	 * @return the content class, or {@code null} if the type is {@code null} or has
	 *         no registered content class
	 */
	public static Class<? extends AbstractGeboSecretContent> contentClassFor(GeboSecretType secretType) {
		return secretType == null ? null : CONTENT_CLASSES.get(secretType);
	}
}
