/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.secrets.model;

import lombok.Data;

/**
 * Gebo.ai comment agent
 *
 * This abstract class serves as a base for all secret content types within the
 * Gebo.ai application. It mandates that any subclass must implement the method
 * to return the type of secret.
 */
@Data
public abstract class AbstractGeboSecretContent {

	/**
	 * This method must be implemented by any concrete subclass to return the
	 * specific type of secret.
	 *
	 * @return a GeboSecretType representing the type of secret content.
	 */
	public abstract GeboSecretType type();

	/**
	 * Marks a secret that may be read but never written.
	 *
	 * <p>
	 * It is {@code true} only on the contents the secrets implementation builds
	 * from the declarative configuration - the {@code ai.gebo.secrets.config.*}
	 * lists of {@code application.yml} - and it is set by that loader, never by a
	 * caller. A secret declared there is owned by the deployment's configuration:
	 * the store is not its source of truth and must not pretend to be, so every
	 * write path of {@code IGeboSecretsAccessService} refuses it.
	 * </p>
	 *
	 * <p>
	 * {@code null} - not {@code false} - is the default, so that the flag reads the
	 * same on a content deserialised from a secret written before the flag existed
	 * as on a freshly built one. Test it with
	 * {@link #isReadOnlySecret(AbstractGeboSecretContent)} rather than by hand.
	 * </p>
	 */
	private Boolean readOnly = null;

	/**
	 * Whether the given content is a read-only (configuration-declared) secret.
	 *
	 * <p>
	 * Deliberately {@code static}: an instance method named {@code isXxx()} would
	 * be picked up as a bean property and would start appearing in the JSON that
	 * gets encrypted into the store, which is exactly the kind of change to the
	 * stored representation {@link GeboSecretContentTypes} warns against.
	 * </p>
	 *
	 * @param content the content to test; may be {@code null}
	 * @return {@code true} only if the content carries {@code readOnly == true}
	 */
	public static boolean isReadOnlySecret(AbstractGeboSecretContent content) {
		return content != null && content.getReadOnly() != null && content.getReadOnly();
	}
}