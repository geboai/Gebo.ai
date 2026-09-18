/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.secrets.services;

import java.util.List;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.SecretInfo;

/**
 * The secrets declared in the configuration, read through the same runtime
 * configuration DAO contract every other {@code ai.gebo.*} static/dynamic
 * configuration uses.
 *
 * <p>
 * {@link #findByCode(String)} is the method that matters:
 * {@code GeboSecretsAccessServiceImpl} consults it <b>before</b> the external
 * storage and the Mongo repository, so a configured secret shadows a stored one
 * of the same code. It returns a <i>fresh</i> content on every call - see the
 * implementation - so a caller may mutate what it gets without corrupting the
 * configuration for the next reader.
 * </p>
 *
 * <p>
 * Everything this DAO hands back carries
 * {@code AbstractGeboSecretContent#getReadOnly() == true}, which is what makes
 * the write paths refuse it. The metadata accessors exist because a configured
 * secret has no {@code GeboSecret} record to build a {@link SecretInfo} from,
 * and the admin surface lists secrets by metadata alone.
 * </p>
 *
 * <p>
 * There is no dynamic source behind it and no write method: the configuration is
 * the source of truth, and it changes by redeploying the configuration.
 * </p>
 *
 * Gebo.ai comment agent
 */
public interface IGSecretsStaticConfigurationDao extends IGRuntimeConfigurationDao<AbstractGeboSecretContent> {

	/**
	 * The codes of every configured secret.
	 *
	 * @return the configured codes; empty when nothing is configured.
	 */
	public List<String> getAllSecretsId();

	/**
	 * Whether a secret is declared in the configuration under this code.
	 *
	 * <p>
	 * Cheaper than {@link #findByCode(String)} and, unlike it, meaningful on the
	 * write paths: a store, an update or a delete only needs to know that the code
	 * is taken by the configuration, never the content behind it.
	 * </p>
	 *
	 * @param code the code to test; may be {@code null}
	 * @return {@code true} if the configuration declares that code.
	 */
	public boolean isConfiguredCode(String code);

	/**
	 * The metadata of the configured secret with the given code.
	 *
	 * @param code the code of the secret; may be {@code null}
	 * @return the metadata, or {@code null} if no configured secret has that code.
	 */
	public SecretInfo findInfoByCode(String code);

	/**
	 * The metadata of every configured secret declared in the given context.
	 *
	 * @param contextCode the context to filter by; may be {@code null}
	 * @return the matching metadata; empty when none matches.
	 */
	public List<SecretInfo> findInfoByContextCode(String contextCode);
}
