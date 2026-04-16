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
 * This class represents a model choice for embeddings which extends the base
 * model choice. It includes an additional parameter for optimal tokenization.
 *
 * Gebo.ai comment agent
 */
@Data
public class GBaseEmbeddingModelChoice extends GBaseModelChoice {

	// Holds the optimal tokenization parameter for the embedding model.
	private Integer optimalTokenizationParam = null;

}