/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.architecture.persistence.GDynamicConfigurationSourceAdapter;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatPromptsConfigs;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.repository.PromptConfigRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.model.base.GObjectRef;

@Service
public class GPromptConfigDaoImpl extends GAbstractRuntimeConfigurationDao<GPromptConfig> implements IGPromptConfigDao {

    /**
     * AI generated comments
     * A dynamic configuration source for GPromptConfig entities integrated with PromptConfigRepository.
     */
	@Service
	public static class GPromptConfigDynamicSource
			extends GDynamicConfigurationSourceAdapter<GPromptConfig, PromptConfigRepository>
			implements IGDynamicConfigurationSource<GPromptConfig> {
		// Inherits all functionality from GDynamicConfigurationSourceAdapter
	}

	private PromptConfigRepository directRepo = null;

    /**
     * Constructs a new GPromptConfigDaoImpl.
     * 
     * @param configs GeboChatPromptsConfigs providing prompt configurations
     * @param source  GPromptConfigDynamicSource providing dynamic configurations
     * @param directRepo PromptConfigRepository for direct repository access
     */
	public GPromptConfigDaoImpl(@Autowired GeboChatPromptsConfigs configs, GPromptConfigDynamicSource source,
			PromptConfigRepository directRepo) {
		super(configs != null ? configs.getPromptDefaults() : new ArrayList<GPromptConfig>(), source);
		this.directRepo = directRepo;
	}

    /**
     * Finds a GPromptConfig by its code.
     * 
     * @param code The code of the prompt configuration
     * @return The corresponding GPromptConfig or null if not found
     */
	@Override
	public GPromptConfig findByCode(String code) {
		if (code == null || code.trim().length() == 0)
			return null;
		GPromptConfig dynamicFound = super.dynamic.findByCode(code);
		if (dynamicFound != null)
			return dynamicFound;
		GPromptConfig staticFound = null;
		for (GPromptConfig c : staticConfigs) {
			if (c.getCode() != null && c.getCode().equals(code)) {
				return c;
			}
		}
		return null;

	}

    /**
     * Helper class for ranking configurations.
     */
	class ConfigurationRankings {
		int rank = 0; // Ranking value based on matching criteria
		GPromptConfig config = null; // Associated configuration
	}

    /**
     * Finds a GPromptConfig by the given reference and optional RAG prompt flag.
     * 
     * @param ref       Reference to the base chat model configuration
     * @param ragPrompt Flag indicating the RAG (retrieve and generate) component of the prompt
     * @return The corresponding GPromptConfig or null if not found
     */
	private GPromptConfig findByReference(GObjectRef<GBaseChatModelConfig> ref, Boolean ragPrompt) {
		List<GPromptConfig> list = this.directRepo
				.findByModelConfigurationReferenceCodeAndModelConfigurationReferenceClassName(ref.getCode(),
						ref.getClassName());
		list = list.stream().filter(x -> {
			return ragPrompt == null
					|| (ragPrompt != null && x.getRagPrompt() != null && ragPrompt.equals(x.getRagPrompt()));
		}).toList();
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

    /**
     * Provides the default prompt for a given chat configuration, optionally filtered by RAG prompt.
     * 
     * @param chatConfiguration The chat model configuration
     * @param ragPrompt Flag indicating the RAG component of the prompt
     * @return The default GPromptConfig
     */
	@Override
	public GPromptConfig defaultPrompt(GBaseChatModelConfig chatConfiguration, Boolean ragPrompt) {
		GObjectRef<GBaseChatModelConfig> reference = GObjectRef.of(chatConfiguration);
		GPromptConfig prompt = findByReference(reference, ragPrompt);
		if (prompt != null)
			return prompt;
		if (chatConfiguration.getDefaultModelPrompt() != null
				&& chatConfiguration.getDefaultModelPrompt().trim().length() > 0) {
			prompt = new GPromptConfig();
			prompt.setModelConfigurationReference(reference);
			prompt.setModelProvider(chatConfiguration.getModelTypeCode());
			prompt.setPrompt(chatConfiguration.getDefaultModelPrompt());
			prompt.setRagPrompt(ragPrompt);
			if (chatConfiguration.getChoosedModel() != null) {
				prompt.setModelName(chatConfiguration.getChoosedModel().getCode());
			}
			prompt.setDescription("Specific model configuration default prompt");
			return prompt;
		}
        // Stream to rank configurations based on the provided chat configuration
		Stream<ConfigurationRankings> stream = getConfigurations().stream().map(x -> {
			ConfigurationRankings rank = new ConfigurationRankings();
			rank.config = x;
			if (chatConfiguration.getChoosedModel() != null) {
				if (x.getModelName() != null && chatConfiguration.getChoosedModel().getCode() != null
						&& x.getModelName().equals(chatConfiguration.getChoosedModel().getCode())) {
					rank.rank += 100; // High priority match
				}
			}
			if (chatConfiguration.getModelTypeCode() != null && x.getModelProvider() != null
					&& chatConfiguration.getModelTypeCode().equals(x.getModelProvider())) {
				rank.rank += 10; // Medium priority match
			}
			return rank;
		});
		TreeMap<Integer, ConfigurationRankings> rankingsSet = new TreeMap<Integer, GPromptConfigDaoImpl.ConfigurationRankings>();
		stream.forEach(x -> {
			if (x.rank > 0) {
				rankingsSet.put(x.rank, x);
			}
		});
		Entry<Integer, ConfigurationRankings> lastEntry = rankingsSet.lastEntry();
		return lastEntry != null ? lastEntry.getValue().config : defaultPrompt(ragPrompt);
	}

    /**
     * Provides the default prompt not associated with any specific model configuration, filtered by RAG prompt.
     * 
     * @param ragPrompt Flag indicating the RAG component of the prompt
     * @return The default GPromptConfig
     */
	@Override
	public GPromptConfig defaultPrompt(Boolean ragPrompt) {

		return findByPredicate(x -> {
			return !StringUtils.hasLength(x.getModelName()) && !StringUtils.hasLength(x.getModelProvider())
					&& !StringUtils.hasLength(x.getPromptCategory()) && x.getModelConfigurationReference() == null
					&& (ragPrompt == null || (x.getRagPrompt() != null && x.getRagPrompt().equals(ragPrompt)));
		});
	}

}