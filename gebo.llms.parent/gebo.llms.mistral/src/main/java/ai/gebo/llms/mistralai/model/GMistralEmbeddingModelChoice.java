/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.mistralai.model;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import lombok.Data;

/**
 * AI generated comments
 * 
 * Represents a model choice for Mistral AI embedding models.
 * This class extends the base embedding model choice functionality
 * from the abstraction layer, specifically tailored for Mistral AI's
 * embedding model offerings.
 */
@Data
public class GMistralEmbeddingModelChoice extends GBaseEmbeddingModelChoice {

	private MistralBaseModelCard modelCard = null;

}