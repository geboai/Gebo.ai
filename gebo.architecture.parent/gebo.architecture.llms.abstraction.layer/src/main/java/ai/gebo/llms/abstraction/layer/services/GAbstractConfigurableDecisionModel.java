/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.decision.DecisionModel;
import org.springframework.ai.decision.DecisionPrompt;
import org.springframework.ai.decision.DecisionResponse;
import org.springframework.ai.decision.question.YesNoQuestion;

import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import io.micrometer.observation.ObservationRegistry;

/**
 * The common part of a provider's configurable decision model: keeps the configuration
 * and the type, builds the provider's Spring AI {@link DecisionModel} through
 * {@link #configureModel(GBaseDecisionModelConfig, GDecisionModelType)} on every
 * (re)configuration, prices the model by its provider deal, and tests a new
 * configuration with one short question so a wrong key, url or model name is reported
 * when the model is set rather than at its first real use.
 *
 * @param <ModelConfig>       the provider's configuration type
 * @param <DecisionModelType> the provider's Spring AI decision model type
 */
public abstract class GAbstractConfigurableDecisionModel<ModelConfig extends GBaseDecisionModelConfig, DecisionModelType extends DecisionModel>
		implements IGConfigurableDecisionModel<ModelConfig>, IGProviderDealPricedModel {

	/** The question of the test call: a trivial yes, whichever the model. */
	static final String TEST_CALL_TEXT = "The sky is blue and the grass is green.";
	static final String TEST_CALL_QUESTION = "Does the text mention a colour?";

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * Prices this model by its provider deal; attached by the runtime DAO, null leaving
	 * it priced by the provider API only.
	 */
	private volatile IGProviderDealService providerDealService = null;

	protected ModelConfig config = null;
	protected GDecisionModelType type = null;
	protected DecisionModelType model = null;
	protected final ObservationRegistry observationRegistry;

	public GAbstractConfigurableDecisionModel(ObservationRegistry observationRegistry) {
		this.observationRegistry = observationRegistry;
	}

	@Override
	public void setProviderDealService(IGProviderDealService providerDealService) {
		this.providerDealService = providerDealService;
	}

	/**
	 * The price the provider deal covering this model's API key gives to its model code,
	 * else the provider API's one; read from an in-memory snapshot.
	 */
	@Override
	public GModelPricingConditions getPricingConditions() {
		return IGProviderDealPricedModel.dealOrProviderApiPricing(providerDealService, this);
	}

	@Override
	public String getCode() {
		return this.config != null ? this.config.getCode() : null;
	}

	@Override
	public String getDescription() {
		return this.config != null ? this.config.getDescription() : null;
	}

	@Override
	public GDecisionModelType getType() {
		return type;
	}

	@Override
	public void initialize(ModelConfig config, GDecisionModelType type) throws LLMConfigException {
		this.config = config;
		this.type = type;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Configuring the decision model code=" + config.getCode() + " of type="
					+ (type != null ? type.getCode() : null) + " on model=" + safeGetModelCode());
		}
		this.model = this.configureModel(config, type);
	}

	@Override
	public void reconfigure(ModelConfig config) throws LLMConfigException {
		this.initialize(config, this.type);
	}

	/**
	 * Builds the provider's Spring AI decision model for the configuration.
	 *
	 * @throws LLMConfigException when the configuration cannot be turned into a model (a
	 *                            missing key, an unreadable secret)
	 */
	protected abstract DecisionModelType configureModel(ModelConfig config, GDecisionModelType type)
			throws LLMConfigException;

	@Override
	public ModelConfig getConfig() {
		return config;
	}

	@Override
	public void delete() throws LLMConfigException {
		this.model = null;
	}

	@Override
	public DecisionModel getDecisionModel() {
		return model;
	}

	/**
	 * One yes/no question to the provider, the way the services ask theirs: a model the
	 * provider does not serve (not pulled, retired, mistyped), a wrong key or url is
	 * refused here, when the model is set.
	 */
	@Override
	public void testCall() throws LLMConfigException {
		final DecisionModel decisionModel = getDecisionModel();
		if (decisionModel == null) {
			throw new LLMConfigException("The decision model code=" + getCode() + " is not configured");
		}
		try {
			final DecisionResponse response = decisionModel.call(DecisionPrompt.builder().text(TEST_CALL_TEXT)
					.question("colour", YesNoQuestion.of(TEST_CALL_QUESTION)).build());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Decision model code=" + getCode() + " answered its test call: " + response.getAnswers());
			}
		} catch (RuntimeException e) {
			throw new LLMConfigException("The decision model code=" + getCode() + " on model=" + safeGetModelCode()
					+ " refused its test call: " + e.getMessage(), e);
		}
	}
}
