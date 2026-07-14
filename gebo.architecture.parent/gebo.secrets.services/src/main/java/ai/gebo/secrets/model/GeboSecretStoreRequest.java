/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.secrets.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

/**
 * A store / update request as it travels from a caller to the secrets
 * microservice: the content (JSON + its {@link GeboSecretType}, see
 * {@link GeboSecretContentEnvelope} for why the type travels beside the JSON)
 * plus the metadata the write methods of
 * {@code IGeboSecretsAccessService} take.
 *
 * <p>
 * This single shape serves all three write methods; {@link #getSecretId()} is
 * what distinguishes them:
 * </p>
 * <ul>
 * <li>{@code storeSecret(secret, description, contextCode)} - {@code secretId}
 * absent, the server generates one and returns it;</li>
 * <li>{@code storeSecret(secret, description, contextCode, secretId)} -
 * {@code secretId} set, the server stores under that id;</li>
 * <li>{@code updateSecret(secret, description, contextCode, code)} -
 * {@code secretId} carries the {@code code} of the secret being updated.</li>
 * </ul>
 *
 * <p>
 * {@link SecretWrapper} is not reused here because it is generic over the
 * concrete content type: the server would have to know that type to bind the
 * body, which is exactly what the generic
 * {@code storeSecret(SecretType, ...)} signature cannot tell it.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class GeboSecretStoreRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Context the secret is stored under. */
	@NotNull
	private String contextCode = null;

	/** Human-readable description of the secret. */
	private String description = null;

	/**
	 * Id of the secret. Absent on a plain store (the server generates it); set on a
	 * store-with-id and on an update, where it is the code of the target secret.
	 */
	private String secretId = null;

	/** Type of the secret being written, driving how {@link #contentJson} is read. */
	@NotNull
	private GeboSecretType secretType = null;

	/** The secret content to store, serialised as JSON. */
	@NotNull
	private String contentJson = null;

	public GeboSecretStoreRequest() {
	}

	public String getContextCode() {
		return contextCode;
	}

	public void setContextCode(String contextCode) {
		this.contextCode = contextCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	public GeboSecretType getSecretType() {
		return secretType;
	}

	public void setSecretType(GeboSecretType secretType) {
		this.secretType = secretType;
	}

	public String getContentJson() {
		return contentJson;
	}

	public void setContentJson(String contentJson) {
		this.contentJson = contentJson;
	}
}
