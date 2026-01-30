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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.repository.PromptConfigRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.abstraction.layer.services.IGStaticPromptsProvider;
import lombok.AllArgsConstructor;

@Service
public class GPromptConfigDaoImpl extends GAbstractRuntimeConfigurationDao<GPromptConfig> implements IGPromptConfigDao {

	/**
	 * AI generated comments A dynamic configuration source for GPromptConfig
	 * entities integrated with PromptConfigRepository.
	 */

	@AllArgsConstructor
	public static class GPromptConfigDynamicSource

			implements IGDynamicConfigurationSource<GPromptConfig> {
		final PromptConfigRepository directRepo;

		@Override
		public List<GPromptConfig> getConfigurations() {
			return directRepo.findAll();
		}

		@Override
		public GPromptConfig findByCode(String code) {
			Optional<GPromptConfig> opt = directRepo.findById(code);
			return opt.isPresent() ? opt.get() : null;
		}
		// Inherits all functionality from GDynamicConfigurationSourceAdapter
	}

	private final PromptConfigRepository directRepo;

	/**
	 * Constructs a new GPromptConfigDaoImpl.
	 * 
	 * @param configs    GeboChatPromptsConfigs providing prompt configurations
	 * @param source     GPromptConfigDynamicSource providing dynamic configurations
	 * @param directRepo PromptConfigRepository for direct repository access
	 */
	public GPromptConfigDaoImpl(@Autowired(required = false) List<IGStaticPromptsProvider> configs,
			PromptConfigRepository directRepo) {
		super(listPrompts(configs), new GPromptConfigDynamicSource(directRepo));
		this.directRepo = directRepo;
	}

	private static List<GPromptConfig> listPrompts(List<IGStaticPromptsProvider> configs) {
		if (configs == null)
			return List.of();
		List<GPromptConfig> out = new ArrayList<GPromptConfig>();
		for (IGStaticPromptsProvider provider : configs) {
			List<GPromptConfig> prompts = provider.promptsList();
			for (GPromptConfig p : prompts) {
				if (p.getPromptUse() == null || p.getPromptUse().trim().length() == 0) {
					throw new IllegalStateException("The provider:"+provider.getId()+" exposes a prompt with no promptUse field value cannot be managed " + p);
				} else {
					p = p.copy();
					p.setConfigDeclarated(true);
					out.add(p);
				}
			}
		}
		return out;
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
	 * Provides the default prompt for a given chat configuration, optionally
	 * filtered by RAG prompt.
	 * 
	 * @param chatConfiguration The chat model configuration
	 * @param ragPrompt         Flag indicating the RAG component of the prompt
	 * @return The default GPromptConfig
	 */
	@Override
	public GPromptConfig defaultChatPrompt(GBaseChatModelConfig chatConfiguration, Boolean ragPrompt) {
		String promptUse = ragPrompt != null && ragPrompt ? GPromptConfig.PROMPT_USE_STANDARD_RAG_PROMPT
				: GPromptConfig.PROMPT_USE_STANDARD_CHAT_PROMPT;
		return findByPromptUse(promptUse, chatConfiguration);
	}

	/**
	 * Provides the default prompt not associated with any specific model
	 * configuration, filtered by RAG prompt
	 * 
	 * @param ragPrompt Flag indicating the RAG component of the prompt
	 * @return The default GPromptConfig
	 */
	@Override
	public GPromptConfig defaultChatPrompt(Boolean ragPrompt) {
		String promptUse = ragPrompt != null && ragPrompt ? GPromptConfig.PROMPT_USE_STANDARD_RAG_PROMPT
				: GPromptConfig.PROMPT_USE_STANDARD_CHAT_PROMPT;
		return findByPromptUse(promptUse);
	}

	@Override
	public GPromptConfig findByPromptUse(String promptUse, String langCode, String modelProvider, String modelCode) {
		if (langCode == null)
			langCode = "en";
		GPromptConfig prompt = exactFindByPromptUse(promptUse, langCode, modelProvider, modelCode);
		if (prompt != null)
			return prompt;
		if (modelProvider == null && modelCode == null && !langCode.equals("en")) {
			langCode = "en";
			prompt = exactFindByPromptUse(promptUse, langCode, modelProvider, modelCode);
			if (prompt != null)
				return prompt;
		}
		if (modelProvider != null) {
			modelProvider = null;
		}
		prompt = exactFindByPromptUse(promptUse, langCode, modelProvider, modelCode);
		if (prompt != null)
			return prompt;
		if (modelCode != null) {
			modelCode = null;
		}
		prompt = exactFindByPromptUse(promptUse, langCode, modelProvider, modelCode);
		if (prompt != null)
			return prompt;
		if (!langCode.equals("en")) {
			langCode = "en";
			prompt = exactFindByPromptUse(promptUse, langCode, modelProvider, modelCode);
			if (prompt != null)
				return prompt;
		}
		return null;
	}
	@Override
	public GPromptConfig exactFindByPromptUse(String promptUse, String langCode, String modelProvider,
			String modelCode) {
		if (promptUse == null || promptUse.trim().length() == 0)
			throw new IllegalStateException("Cannot search without a promptUse");
		final String usedLangCode = langCode == null || langCode.trim().length() == 0 ? "en" : langCode;
		List<GPromptConfig> matching = this.findListByPredicate(x -> {
			boolean matches = false;
			matches = promptUse.equals(x.getPromptUse()) && usedLangCode.equals(x.getLangCode());
			matches = matches
					&& ((modelProvider == null && modelProvider == x.getModelProvider()) || (modelProvider != null
							&& x.getModelProvider() != null && modelProvider.equals(x.getModelProvider())));
			matches = matches && ((modelCode == null && modelCode == x.getModelCode())
					|| (modelCode != null && x.getModelCode() != null && modelCode.equals(x.getModelCode())));
			return matches;
		});

		if (matching.size() == 1)
			return matching.get(0);
		if (matching.size() > 1) {
			Optional<GPromptConfig> findNonStatic = matching.stream()
					.filter(x -> x.getConfigDeclarated() == null || !x.getConfigDeclarated()).findFirst();
			if (findNonStatic.isPresent())
				return findNonStatic.get();
			return matching.get(0);
		}
		return null;
	}

	@Override
	public GPromptConfig insert(GPromptConfig config) {
		if (config.getConfigDeclarated() != null && config.getConfigDeclarated())
			throw new IllegalStateException("Cannot persist a static declared promptConfig");
		return this.directRepo.insert(config);
	}

	@Override
	public GPromptConfig update(GPromptConfig config) {
		if (config.getConfigDeclarated() != null && config.getConfigDeclarated())
			throw new IllegalStateException("Cannot persist a static declared promptConfig");
		return this.directRepo.save(config);
	}

	@Override
	public void delete(GPromptConfig config) {
		if (config.getConfigDeclarated() != null && config.getConfigDeclarated())
			throw new IllegalStateException("Cannot persist a static declared promptConfig");
		this.directRepo.delete(config);
	}

}