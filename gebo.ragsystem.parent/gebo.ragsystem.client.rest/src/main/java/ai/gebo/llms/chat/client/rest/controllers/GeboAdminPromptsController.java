/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.ai.config.GPromptTemplateEditingConfig;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.GPromptTemplateLightView;
import ai.gebo.architecture.ai.model.GPromptUseInfo;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGPromptUseInfoDao;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AI generated comments
 * 
 * REST controller for managing prompt configurations. This controller provides
 * endpoints for CRUD operations on GPromptConfig objects. Access is restricted
 * to users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GeboAdminPromptsController")
@AllArgsConstructor
public class GeboAdminPromptsController {
	/**
	 * Persistence manager for database operations.
	 */

	final IGPromptConfigDao promptConfigDao;

	/**
	 * Catalog (description/module/placeholders metadata) of the prompt uses.
	 */
	final IGPromptUseInfoDao promptUseInfoDao;

	/**
	 * Deployment switch telling whether prompt templates may be edited from the UI.
	 */
	final GPromptTemplateEditingConfig editingConfig;

	/**
	 * Tells the UI whether prompt template editing (save/delete) is enabled for
	 * this deployment. When false the admin editor keeps save and delete disabled
	 * regardless of the other rules.
	 *
	 * @return true when {@code ai.gebo.prompt-templates.editingEnabled} is set
	 */
	@GetMapping(value = "isPromptTemplateEditingEnabled", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean isPromptTemplateEditingEnabled() {
		return editingConfig.isEditingEnabled();
	}

	/**
	 * Returns a lightweight view (use code, language, description) of every prompt
	 * template known at runtime (static library ones plus their mongo overrides),
	 * for the prompt templates list. The full template texts are not shipped.
	 *
	 * @return the light views of all prompt templates
	 */
	@GetMapping(value = "getAllPromptConfigsLightList", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GPromptTemplateLightView> getAllPromptConfigsLightList() {
		// getConfigurations() unions the static library templates with their mongo
		// copies; a static template and its override share the same code, and the
		// override is the one that actually resolves, so collapse duplicates by code
		// keeping the non-static (mongo) one when both are present.
		java.util.LinkedHashMap<String, GPromptTemplateConfig> byCode = new java.util.LinkedHashMap<>();
		for (GPromptTemplateConfig config : promptConfigDao.getConfigurations()) {
			GPromptTemplateConfig existing = byCode.get(config.getCode());
			boolean isDynamic = config.getConfigDeclarated() == null || !config.getConfigDeclarated();
			if (existing == null || isDynamic) {
				byCode.put(config.getCode(), config);
			}
		}
		return byCode.values().stream().map(GPromptTemplateLightView::of).toList();
	}

	/**
	 * Retrieves a prompt configuration by its code.
	 *
	 * @param code The unique identifier for the prompt configuration
	 * @return The found GPromptConfig object
	 * @throws GeboPersistenceException If there's an error during the database
	 *                                  operation
	 */
	@GetMapping(value = "findPromptConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptTemplateConfig findPromptConfigByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return promptConfigDao.findByCode(code);
	}

	/**
	 * Retrieves the {@link GPromptUseInfo} catalog entry (description, owning
	 * module and documented placeholders) for a given prompt use code, so the
	 * prompt-editing UI can render the placeholders reference and validate that
	 * every documented placeholder is present in the edited templates.
	 *
	 * @param useCode The prompt use code (the {@code promptUse} of a template)
	 * @return The matching GPromptUseInfo, or {@code null} if none is declared
	 */
	@GetMapping(value = "findGPromptUseInfoByUseCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptUseInfo findGPromptUseInfoByUseCode(@RequestParam("useCode") String useCode) {
		return promptUseInfoDao.findByCode(useCode);
	}

	/**
	 * Creates a new prompt configuration.
	 * 
	 * @param Config The prompt configuration to insert
	 * @return The inserted GPromptConfig object with generated ID
	 * @throws GeboPersistenceException If there's an error during the database
	 *                                  operation
	 */
	@PostMapping(value = "insertPromptConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptTemplateConfig insertPromptConfig(@RequestBody GPromptTemplateConfig Config) throws GeboPersistenceException {
		return promptConfigDao.insert(Config);
	}

	/**
	 * Updates an existing prompt configuration.
	 * 
	 * @param Config The prompt configuration to update
	 * @return The updated GPromptConfig object
	 * @throws GeboPersistenceException If there's an error during the database
	 *                                  operation
	 */
	@PostMapping(value = "updatePromptConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptTemplateConfig updatePromptConfig(@RequestBody GPromptTemplateConfig Config) throws GeboPersistenceException {
		return promptConfigDao.update(Config);
	}

	/**
	 * Deletes a prompt configuration.
	 * 
	 * @param Config The prompt configuration to delete
	 * @throws GeboPersistenceException If there's an error during the database
	 *                                  operation
	 */
	@PostMapping(value = "deletePromptConfig", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deletePromptConfig(@RequestBody GPromptTemplateConfig Config) throws GeboPersistenceException {
		promptConfigDao.delete(Config);
	}

	/**
	 * Retrieves all prompt configurations with pagination.
	 * 
	 * @param page The pagination parameters
	 * @return A page of GPromptConfig objects
	 * @throws GeboPersistenceException If there's an error during the database
	 *                                  operation
	 */

	@Data
	public static class PromptFilter {
		private String modelUse = null;
		private String langCode = null;
		private String modelProvider = null;
		private String modelCode = null;

	}

	/**
	 * Retrieves prompt configurations that match the given example with pagination.
	 * 
	 * @param param Contains pagination information and the filter example
	 * @return A page of GPromptConfig objects matching the criteria
	 * @throws GeboPersistenceException If there's an error during the database
	 *                                  operation
	 */
	@PostMapping(value = "getPromptConfigByFilter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptTemplateConfig getPromptConfigByFilter(@RequestBody PromptFilter param) throws GeboPersistenceException {
		return promptConfigDao.exactFindByPromptUse(param.modelUse, param.langCode, param.modelProvider,
				param.modelCode);
	}

	/**
	 * Retrieves all unique prompt categories from existing configurations.
	 * 
	 * @return A sorted list of all unique prompt categories
	 * @throws GeboPersistenceException If there's an error during the database
	 *                                  operation
	 */
	@GetMapping(value = "getPromptCategories", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getPromptCategories() throws GeboPersistenceException {
		Stream<GPromptTemplateConfig> stream = promptConfigDao.getConfigurations().stream();
		final TreeMap<String, Boolean> cats = new TreeMap<String, Boolean>();
		stream.forEach(x -> {
			if (x.getPromptCategory() != null) {
				cats.put(x.getPromptCategory(), true);
			}
		});
		return new ArrayList<String>(cats.keySet());
	}
}