/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.ai.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.repository.PromptConfigRepository;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import ai.gebo.architecture.ai.service.IStaticPromptsProviderRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;

public class GPromptConfigDaoImpl extends GAbstractRuntimeConfigurationDao<GPromptTemplateConfig>
		implements IGPromptConfigDao {

	private final PromptConfigRepository directRepo;
	private final IStaticPromptsProviderRepositoryPattern implementationsRepoPattern;

	/**
	 * Constructs a new GPromptConfigDaoImpl.
	 * 
	 * @param configs    GeboChatPromptsConfigs providing prompt configurations
	 * @param source     GPromptConfigDynamicSource providing dynamic configurations
	 * @param directRepo PromptConfigRepository for direct repository access
	 * @throws IOException
	 */
	public GPromptConfigDaoImpl(PromptConfigRepository directRepo,
			IStaticPromptsProviderRepositoryPattern implementationsRepoPattern,
			GPromptConfigDynamicSource dynamicSource) throws IOException {
		super(new ArrayList(), dynamicSource);
		this.directRepo = directRepo;
		this.implementationsRepoPattern = implementationsRepoPattern;
		List<IGStaticPromptsProvider> impls = implementationsRepoPattern.getImplementations();
		staticConfigs = listPrompts(impls);
	}

	private List<GPromptTemplateConfig> listPrompts(List<IGStaticPromptsProvider> configs) throws IOException {
		if (configs == null)
			return List.of();
		boolean hasInvalidPromptTemplates = false;
		List<GPromptTemplateConfig> out = new ArrayList<GPromptTemplateConfig>();
		for (IGStaticPromptsProvider provider : configs) {
			List<GPromptTemplateConfig> prompts = provider.promptsList();
			for (GPromptTemplateConfig p : prompts) {
				if (p.getPromptUse() == null || p.getPromptUse().trim().length() == 0) {
					throw new IllegalStateException("The provider:" + provider.getId()
							+ " exposes a prompt with no promptUse field value cannot be managed " + p);
				} else {
					if (p.getSystemPromptTemplate() == null || p.getUserPromptTemplate() == null) {
						hasInvalidPromptTemplates = true;
						LOGGER.error("Null template=>" + p);
					}
					p = p.copy();
					p.setConfigDeclarated(true);
					out.add(p);
				}
			}
		}
		if (hasInvalidPromptTemplates)
			throw new RuntimeException("Invalid prompt templates in the system");
		return out;
	}

	/**
	 * Finds a GPromptConfig by its code.
	 * 
	 * @param code The code of the prompt configuration
	 * @return The corresponding GPromptConfig or null if not found
	 */
	@Override
	public GPromptTemplateConfig findByCode(String code) {
		if (code == null || code.trim().length() == 0)
			return null;
		GPromptTemplateConfig dynamicFound = super.dynamic.findByCode(code);
		if (dynamicFound != null)
			return dynamicFound;
		GPromptTemplateConfig staticFound = null;
		for (GPromptTemplateConfig c : staticConfigs) {
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
		GPromptTemplateConfig config = null; // Associated configuration
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
	public GPromptTemplateConfig defaultChatPrompt(String modelCode, Boolean ragPrompt) {
		String promptUse = ragPrompt != null && ragPrompt ? PROMPT_USE_STANDARD_RAG_PROMPT
				: PROMPT_USE_STANDARD_CHAT_PROMPT;
		return findByPromptUse(promptUse, "en", modelCode);
	}

	/**
	 * Provides the default prompt not associated with any specific model
	 * configuration, filtered by RAG prompt
	 * 
	 * @param ragPrompt Flag indicating the RAG component of the prompt
	 * @return The default GPromptConfig
	 */
	@Override
	public GPromptTemplateConfig defaultChatPrompt(Boolean ragPrompt) {
		String promptUse = ragPrompt != null && ragPrompt ? PROMPT_USE_STANDARD_RAG_PROMPT
				: PROMPT_USE_STANDARD_CHAT_PROMPT;
		return findByPromptUse(promptUse);
	}

	@Override
	public GPromptTemplateConfig findByPromptUse(String promptUse, String langCode, String modelProvider,
			String modelCode) {
		if (langCode == null)
			langCode = "en";
		GPromptTemplateConfig prompt = exactFindByPromptUse(promptUse, langCode, modelProvider, modelCode);
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
	public GPromptTemplateConfig exactFindByPromptUse(String promptUse, String langCode, String modelProvider,
			String modelCode) {
		if (promptUse == null || promptUse.trim().length() == 0)
			throw new IllegalStateException("Cannot search without a promptUse");
		final String usedLangCode = langCode == null || langCode.trim().length() == 0 ? "en" : langCode;
		List<GPromptTemplateConfig> matching = this.findListByPredicate(x -> {
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
			Optional<GPromptTemplateConfig> findNonStatic = matching.stream()
					.filter(x -> x.getConfigDeclarated() == null || !x.getConfigDeclarated()).findFirst();
			if (findNonStatic.isPresent())
				return findNonStatic.get();
			return matching.get(0);
		}
		return null;
	}

	@Override
	public GPromptTemplateConfig insert(GPromptTemplateConfig config) {
		if (config.getConfigDeclarated() != null && config.getConfigDeclarated())
			throw new IllegalStateException("Cannot persist a static declared promptConfig");
		return this.directRepo.insert(config);
	}

	@Override
	public GPromptTemplateConfig update(GPromptTemplateConfig config) {
		if (config.getConfigDeclarated() != null && config.getConfigDeclarated())
			throw new IllegalStateException("Cannot persist a static declared promptConfig");
		return this.directRepo.save(config);
	}

	@Override
	public void delete(GPromptTemplateConfig config) {
		if (config.getConfigDeclarated() != null && config.getConfigDeclarated())
			throw new IllegalStateException("Cannot persist a static declared promptConfig");
		this.directRepo.delete(config);
	}

}