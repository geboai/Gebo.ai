/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.model;

import lombok.Data;

/**
 * The configuration of a decision model: the provider's model type, the chosen model,
 * the API key and base url, as every model kind; the default one is the decision model
 * the services use when none is indicated.
 *
 * @param <ModelChoiceType> the provider's model choice
 */
@Data
public class GBaseDecisionModelConfig<ModelChoiceType extends GBaseDecisionModelChoice>
		extends GBaseModelConfig<ModelChoiceType> {

}
