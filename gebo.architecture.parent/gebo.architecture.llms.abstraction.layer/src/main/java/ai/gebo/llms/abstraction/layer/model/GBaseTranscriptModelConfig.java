/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.model;

/**
 * Gebo.ai comment agent
 * 
 * GBaseTranscriptModelConfig is a class that extends GBaseModelConfig
 * to provide configuration settings specifically for transcript models.
 * This class is parameterized with a type parameter that extends 
 * GBaseTranscriptModelChoice, ensuring that only the appropriate 
 * model choices are used.
 *
 * @param <ModelChoiceType> Specifies the type of model choice 
 *                          that is allowed, which must extend 
 *                          GBaseTranscriptModelChoice.
 */
public class GBaseTranscriptModelConfig<ModelChoiceType extends GBaseTranscriptModelChoice>
		extends GBaseModelConfig<ModelChoiceType> {

}