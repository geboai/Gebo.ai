/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.anthropic.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelChoice;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelConfig;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Service class for looking up available Anthropic models.
 * This service is only activated when the property 'ai.gebo.llms.config.anthropicEnabled' is set to 'true'.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "anthropicEnabled", havingValue = "true")
@Service
public class AnthropicModelsLookupService {
	/** Service to enrich model choices with additional metadata */
	@Autowired
	IGModelChoiceMetaInfoEnricherService metaEnricher;
	
	/** Static list of all available Anthropic chat models */
	public static List<GAnthropicChatModelChoice> models;
	static {
		models = new ArrayList<>();
		for (String modelCode : new String[] {
				"claude-opus-4-5",
				"claude-sonnet-4-5",
				"claude-haiku-4-5",
				"claude-3-7-sonnet-20250219",
				"claude-3-5-sonnet-20241022",
				"claude-3-5-haiku-20241022",
				"claude-3-opus-20240229" }) {
			GAnthropicChatModelChoice choice = new GAnthropicChatModelChoice();
			choice.setCode(modelCode);
			choice.setDescription(modelCode);
			models.add(choice);
		}
	}

	/**
	 * Default constructor for AnthropicModelsLookupService.
	 */
	public AnthropicModelsLookupService() {

	}

	/**
	 * Retrieves all available Anthropic chat models with enriched metadata.
	 * 
	 * @param config The Anthropic configuration to use
	 * @return An OperationStatus containing the list of available Anthropic chat models
	 */
	public OperationStatus<List<GAnthropicChatModelChoice>> getChatModels(GAnthropicChatModelConfig config) {
		metaEnricher.enrichChatModelMetaInfos("anthropic", models, (choice) -> {
			// Create basic metadata for each model with informative URL to Anthropic documentation
			ModelMetaInfo meta = new ModelMetaInfo();
			meta.setInformativeUrl("https://docs.anthropic.com/en/docs/about-claude/models");
			return meta;
		});
		return OperationStatus.of(models);
	}
}