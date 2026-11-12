/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.cluster.GAbstractClusteredModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.cluster.GLlmModelClusterCategory;
import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableDecisionModel;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

/**
 * The decision models running in this instance, created at startup from every
 * persisted {@link GBaseDecisionModelConfig} by the provider's support service, each
 * wrapped so its calls are recorded as usage and priced by its provider deal.
 */
@Service
public class GDecisionModelRuntimeConfigurationDaoImpl
		extends GAbstractClusteredModelRuntimeConfigurationDao<IGConfigurableDecisionModel, GBaseDecisionModelConfig>
		implements IGDecisionModelRuntimeConfigurationDao {

	static final Logger LOGGER = LoggerFactory.getLogger(GDecisionModelRuntimeConfigurationDaoImpl.class);
	static final ObjectMapper mapper = new ObjectMapper();

	private final IGDecisionModelConfigurationSupportServiceRepositoryPattern supportRepoPattern;
	private final IGPersistentObjectManager persistentObjectManager;

	// Accounts every decision call; optional so a context without usage tracking still
	// registers its models, unrecorded.
	@Autowired(required = false)
	private LLMUsageRecorder usageRecorder;

	public GDecisionModelRuntimeConfigurationDaoImpl(
			IGDecisionModelConfigurationSupportServiceRepositoryPattern supportRepoPattern,
			IGPersistentObjectManager persistentObjectManager) {
		super(new ArrayList<>(), null);
		this.supportRepoPattern = supportRepoPattern;
		this.persistentObjectManager = persistentObjectManager;
	}

	@Override
	protected GLlmModelClusterCategory getClusterCategory() {
		return GLlmModelClusterCategory.DECISION;
	}

	@Override
	public void add(IGConfigurableDecisionModel element) {
		this.staticConfigs.add(withUsageRecording(element));
	}

	private IGConfigurableDecisionModel withUsageRecording(IGConfigurableDecisionModel model) {
		attachProviderDealPricing(model);
		if (model == null || usageRecorder == null || model instanceof UsageRecordingDecisionModel) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Decision model code=" + (model != null ? model.getCode() : null)
						+ " registered without a new usage wrapper: recorder="
						+ (usageRecorder != null ? "present" : "absent"));
			}
			return model;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Decision model code=" + model.getCode() + " wrapped for usage recording");
		}
		return new UsageRecordingDecisionModel<>(model, usageRecorder);
	}

	@Override
	public void deleteByCode(String code) throws LLMConfigException {
		IGConfigurableDecisionModel item = this.findByCode(code);
		if (item == null) {
			LOGGER.warn("No decision model with code=" + code + " is running, nothing to delete");
			return;
		}
		staticConfigs.remove(item);
		item.delete();
	}

	@Override
	public void addRuntimeByConfig(GBaseDecisionModelConfig config) throws LLMConfigException {
		IGDecisionModelConfigurationSupportService handler = supportRepoPattern
				.findImplementation(x -> x.getType().getCode().equals(config.getModelTypeCode()));
		if (handler == null) {
			LOGGER.error("Received in configuration a decision model with type=>" + config.getModelTypeCode()
					+ " that is not found");
			throw new LLMConfigException("Cannot find handler for code=>" + config.getModelTypeCode());
		}
		if (LOGGER.isDebugEnabled()) {
			try {
				LOGGER.debug("Initializing decision model with configuration:" + mapper.writeValueAsString(config));
			} catch (JacksonException e) {
				LOGGER.debug("Initializing decision model code=" + config.getCode());
			}
		}
		IGConfigurableDecisionModel model = handler.create(config);
		LOGGER.info("Initialized decision model code=" + config.getCode() + " successfully");
		this.staticConfigs.add(withUsageRecording(model));
	}

	@Override
	public IGConfigurableDecisionModel findByCode(String code) {
		return this.findByPredicate(x -> x.getCode() != null && code != null && x.getCode().equals(code));
	}

	@Override
	protected void initializeRuntimeModels() {
		LOGGER.info("Begin initializing decision models dinamically");
		try {
			List<GBaseDecisionModelConfig> configs = persistentObjectManager
					.findAllExtendingType(GBaseDecisionModelConfig.class);
			for (GBaseDecisionModelConfig config : configs) {
				try {
					this.addRuntimeByConfig(config);
				} catch (Throwable e) {
					// A single model that cannot be allocated (revoked key, provider down, stale
					// configuration) must never keep the whole application from starting.
					LOGGER.error("Cannot initialize the decision model with code=>" + config.getCode(), e);
				}
			}
		} catch (GeboPersistenceException e) {
			LOGGER.error("Cannot read the decision models configuration", e);
		}
		LOGGER.info("End initializing decision models dinamically");
	}
}
