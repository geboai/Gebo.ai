/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.multilanguage.support.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.multilanguage.support.model.UIComponent;
import ai.gebo.multilanguage.support.services.MultiLanguageService;

/**
 * Gebo.ai comment agent
 * Controller for handling language resource requests.
 * Provides endpoints to get and update UI component labels based on language.
 */
@RestController
@RequestMapping("api/users/LanguageResourcesController")
public class LanguageResourcesController {
	@Autowired
	MultiLanguageService service;  // Service for handling multilingual operations

	/**
	 * Retrieves labels for a specific UI component in a specified language.
	 *
	 * @param id       the identifier of the UI component
	 * @param langCode the language code for the desired translations
	 * @return the translated UI component labels
	 * @throws IOException if an input or output exception occurs
	 */
	@GetMapping(value = "getUIComponentLabels", produces = MediaType.APPLICATION_JSON_VALUE)
	public UIComponent getUIComponentLabels(@RequestParam("id") String id, @RequestParam("langCode") String langCode)
			throws IOException {
		return service.getUIComponentLabels(id, langCode);
	}

	/**
	 * Retrieves all language resources for the specified language.
	 *
	 * @param langCode the language code for the desired translations
	 * @return a list of UI components with translations
	 * @throws IOException if an input or output exception occurs
	 */
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UIComponent> getAllResourcesByLanguage(@RequestParam("langCode") String langCode) throws IOException {
		return service.getAllResources(langCode);
	}

	/**
	 * Updates a UI component with new translation data.
	 *
	 * @param component the UI component containing updated translation data
	 * @return the updated UI component
	 * @throws IOException if an input or output exception occurs
	 */
	@PostMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UIComponent update(@RequestBody UIComponent component) throws IOException {
		return service.update(component);
	}

}