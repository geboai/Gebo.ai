/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.secrets.config;

import ai.gebo.secrets.model.AbstractGeboSecretContent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * One secret declared under {@code ai.gebo.secrets.config.<secret type>}: the
 * metadata a stored secret keeps on its {@code GeboSecret} record - code,
 * description, context - plus the content itself, nested under {@code secret}.
 *
 * <p>
 * The metadata cannot live on the content: {@link AbstractGeboSecretContent}
 * carries none of it (it is the encrypted payload, not the record), and the code
 * in particular is what
 * {@code IGSecretsStaticConfigurationDao#findByCode(String)} resolves - so it
 * has to be declared beside the content rather than inside it.
 * </p>
 *
 * <p>
 * The content type is a type parameter so that {@code GeboStaticSecretsConfig}
 * can declare one list per {@code GeboSecretType} and let Spring's relaxed
 * binder bind each entry straight into its concrete content class. There is
 * therefore no type discriminator inside an entry: the YAML key the list hangs
 * off <i>is</i> the type.
 * </p>
 *
 * Gebo.ai comment agent
 *
 * @param <SecretContentType> the concrete content type of the declared secret
 */
@Data
public class GeboStaticSecretEntry<SecretContentType extends AbstractGeboSecretContent> {

	/**
	 * The code the secret is resolved by - the same identifier a stored secret
	 * would have. Required, and unique across every list of the configuration.
	 */
	@NotBlank
	private String code = null;

	/** Human-readable description, as shown by the admin surface. */
	private String description = null;

	/** Context the secret belongs to, as for a stored secret. */
	private String contextCode = null;

	/**
	 * The declared secret content. {@code @Valid} is what carries the startup
	 * validation into the content's own constraints - a
	 * {@code GeboUsernamePasswordContent} with no password, say.
	 */
	@NotNull
	@Valid
	private SecretContentType secret = null;
}
