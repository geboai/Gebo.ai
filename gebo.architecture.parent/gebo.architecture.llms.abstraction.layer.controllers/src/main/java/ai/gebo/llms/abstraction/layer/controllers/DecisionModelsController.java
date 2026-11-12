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

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.controllers.model.ConfigurationEntry;
import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableDecisionModel;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelRuntimeConfigurationDao;

/**
 * The decision model kinds the providers present offer, and the decision models
 * configured and running: what the LLMs admin panel lists.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/DecisionModelsController")
public class DecisionModelsController {

	private final IGDecisionModelConfigurationSupportServiceRepositoryPattern decisionModelsTypesRepo;
	private final IGDecisionModelRuntimeConfigurationDao runtimeDao;

	public DecisionModelsController(IGDecisionModelConfigurationSupportServiceRepositoryPattern decisionModelsTypesRepo,
			IGDecisionModelRuntimeConfigurationDao runtimeDao) {
		this.decisionModelsTypesRepo = decisionModelsTypesRepo;
		this.runtimeDao = runtimeDao;
	}

	@GetMapping(value = "getDecisionModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GDecisionModelType> getDecisionModelTypes() {
		List<GModelType> list = decisionModelsTypesRepo.map(x -> x.getType());
		List<GDecisionModelType> out = new ArrayList<>();
		for (GModelType type : list) {
			if (type instanceof GDecisionModelType decisionType) {
				out.add(decisionType);
			}
		}
		return out;
	}

	@GetMapping(value = "getRuntimeConfiguredDecisionModels", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConfigurationEntry<GBaseDecisionModelConfig>> getRuntimeConfiguredDecisionModels(
			@RequestParam(name = "modelTypeCode", required = false) String modelTypeCode) {
		final List<IGConfigurableDecisionModel> elements = modelTypeCode != null
				? this.runtimeDao.findListByPredicate(x -> x.getType().getCode().equals(modelTypeCode))
				: this.runtimeDao.getConfigurations();
		List<ConfigurationEntry<GBaseDecisionModelConfig>> configs = new ArrayList<>();
		if (elements != null) {
			for (IGConfigurableDecisionModel element : elements) {
				configs.add(new ConfigurationEntry<GBaseDecisionModelConfig>(element.getConfig()));
			}
		}
		return configs;
	}
}
