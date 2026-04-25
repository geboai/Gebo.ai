/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.model;

import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;

/**
 * AI generated comments Represents a base configuration for a model, storing
 * essential information such as model type, API secret, and more.
 *
 * @param <ModelChoiceType> The type of model choice extending GBaseModelChoice
 *                          that this configuration can handle.
 */
@Data
public class GBaseModelConfig<ModelChoiceType extends GBaseModelChoice> extends GBaseObject {
	/**
	 * The code representing the model type. Annotated to reference GModelType.
	 */
	@GObjectReference(referencedType = GModelType.class)
	protected String modelTypeCode = null;

	/**
	 * Indicates whether this configuration is for the default model.
	 */
	protected Boolean defaultModel = null;

	/**
	 * Secret code used for API authentication.
	 */
	protected String apiSecretCode = null;

	/**
	 * The specific model choice selected, represented by a type extending
	 * GBaseModelChoice.
	 */
	protected ModelChoiceType choosedModel = null;

	/**
	 * Base URL for connecting to the model's API.
	 */
	protected String baseUrl = null;
	
	/**
	 * The context length setting of the model, null by default.
	 */
	protected Integer contextLength = null;

}
