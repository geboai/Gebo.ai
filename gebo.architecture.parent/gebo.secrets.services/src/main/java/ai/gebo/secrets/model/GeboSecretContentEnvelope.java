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

/**
 * A decrypted secret content as it travels from the secrets microservice back to
 * a caller: the concrete {@link GeboSecretType} plus the content serialised as
 * JSON.
 *
 * <p>
 * The content is carried as a JSON <b>string</b> rather than as a typed
 * {@link AbstractGeboSecretContent} field because that base class deliberately
 * has no Jackson type information (see {@link GeboSecretContentTypes}). Keeping
 * the payload opaque lets the receiver deserialise it into whichever class it
 * wants: the class {@link GeboSecretContentTypes#contentClassFor(GeboSecretType)}
 * maps the type to, or - for
 * {@code IGeboSecretsAccessService#getCustomSecretContentById(String, Class)} -
 * the caller-supplied {@link GeboCustomSecretContent} subclass, which the server
 * cannot know.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class GeboSecretContentEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Type of the transported secret, driving how {@link #contentJson} is read back. */
	private GeboSecretType secretType = null;

	/** The decrypted secret content, serialised as JSON. */
	private String contentJson = null;

	public GeboSecretContentEnvelope() {
	}

	public GeboSecretContentEnvelope(GeboSecretType secretType, String contentJson) {
		this.secretType = secretType;
		this.contentJson = contentJson;
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
