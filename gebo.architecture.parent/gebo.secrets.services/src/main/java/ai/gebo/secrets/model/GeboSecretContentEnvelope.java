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
 * A secret as it travels between services: its {@link GeboSecretType} plus its
 * content <b>still encrypted</b>, byte for byte as the store holds it.
 *
 * <h2>The ciphertext travels, not the secret</h2>
 * <p>
 * The secrets service never decrypts on a caller's behalf. It hands back exactly
 * what it read from the repository, and the caller decrypts locally with its own
 * {@code IGeboCryptingService}. Every Gebo service already has that service and the
 * key material - the keystore is bundled, or pointed at by
 * {@code ai.gebo.crypting.*} in the configuration - so nothing is lost by keeping the
 * plaintext at the edges. What is gained is that <b>no secret is ever in the clear on
 * the network</b>, not even between two services inside the cluster.
 * </p>
 *
 * <p>
 * Symmetrically, a write arrives already encrypted (see {@link GeboSecretStoreRequest}):
 * the owner stores the ciphertext it is given and never sees the plaintext either.
 * </p>
 *
 * <p>
 * The type travels beside the ciphertext because {@link AbstractGeboSecretContent}
 * carries no Jackson type information and must not gain any (see
 * {@link GeboSecretContentTypes}). Once decrypted, the receiver deserialises the JSON
 * into the class {@link GeboSecretContentTypes#contentClassFor(GeboSecretType)} maps
 * the type to - or, for
 * {@code IGeboSecretsAccessService#getCustomSecretContentById(String, Class)}, into the
 * caller-supplied {@link GeboCustomSecretContent} subclass, which the owner could never
 * have known. That the stored JSON arrives verbatim is what makes that subclass
 * round-trip <i>losslessly</i>.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class GeboSecretContentEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Type of the transported secret, driving which class the decrypted JSON is read into. */
	private GeboSecretType secretType = null;

	/**
	 * The secret content <b>as stored</b>: the encrypted form. Decrypt it with the
	 * local {@code IGeboCryptingService} to obtain the content JSON.
	 */
	private String cryptedContent = null;

	public GeboSecretContentEnvelope() {
	}

	public GeboSecretContentEnvelope(GeboSecretType secretType, String cryptedContent) {
		this.secretType = secretType;
		this.cryptedContent = cryptedContent;
	}

	public GeboSecretType getSecretType() {
		return secretType;
	}

	public void setSecretType(GeboSecretType secretType) {
		this.secretType = secretType;
	}

	public String getCryptedContent() {
		return cryptedContent;
	}

	public void setCryptedContent(String cryptedContent) {
		this.cryptedContent = cryptedContent;
	}
}
