/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.controllers.model.ConfigurationEntry;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;

/**
 * AI generated comments
 * Controller class responsible for handling transcript model configurations.
 * Only accessible by users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/TranscriptModelsController")
public class TranscriptModelsController {

	@Autowired
	IGTranscriptModelConfigurationSupportServiceRepositoryPattern transcriptModelsTypesRepo;

	@Autowired
	IGTranscriptModelRuntimeConfigurationDao runtimeDao;

	public TranscriptModelsController() {
	}

	@GetMapping(value = "getTranscriptModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GTranscriptModelType> getTranscriptModelTypes() {
		List<GModelType> list = transcriptModelsTypesRepo.map((x) -> {
			return x.getType();
		});
		return new ArrayList(list);
	}

	@GetMapping(value = "getRuntimeConfiguredTranscriptModels", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConfigurationEntry<GBaseTranscriptModelConfig>> getRuntimeConfiguredTranscriptModels(
			@RequestParam(name = "modelTypeCode", required = false) String modelTypeCode) {
		if (modelTypeCode != null) {
			List<ConfigurationEntry<GBaseTranscriptModelConfig>> configs = new ArrayList<ConfigurationEntry<GBaseTranscriptModelConfig>>();
			List<IGConfigurableTranscriptModel> elements = this.runtimeDao.findListByPredicate(x -> {
				return x.getType().getCode().equals(modelTypeCode);
			});
			if (elements != null) {
				configs = new ArrayList(elements.stream().map(x -> {
					return new ConfigurationEntry(x.getConfig());
				}).toList());
			}
			return configs;
		} else {
			return new ArrayList(this.runtimeDao.getConfigurations().stream().map(x -> {
				return new ConfigurationEntry(x.getConfig());
			}).toList());
		}
	}
}
