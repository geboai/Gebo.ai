/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import org.springframework.ai.decision.DecisionModel;
import org.springframework.ai.decision.DecisionResponse;

import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableDecisionModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.model.ModelType;

/**
 * A configurable decision model whose every call is recorded as usage, with the token
 * counts the provider reports in the response metadata; the delegate does the work.
 *
 * @param <ModelConfig> the configuration type of the wrapped model
 */
public class UsageRecordingDecisionModel<ModelConfig extends GBaseDecisionModelConfig>
		implements IGConfigurableDecisionModel<ModelConfig> {

	private final IGConfigurableDecisionModel<ModelConfig> delegate;
	private final LLMUsageRecorder recorder;

	public UsageRecordingDecisionModel(IGConfigurableDecisionModel<ModelConfig> delegate, LLMUsageRecorder recorder) {
		this.delegate = delegate;
		this.recorder = recorder;
	}

	public IGConfigurableDecisionModel<ModelConfig> getDelegate() {
		return delegate;
	}

	@Override
	public DecisionModel getDecisionModel() {
		final DecisionModel model = delegate.getDecisionModel();
		if (model == null) {
			return null;
		}
		return prompt -> {
			LLMUsageRecorder.Call call = recorder.begin(delegate.getConfig(),
					LLMUsageRecorder.safeProviderId(delegate::getProviderId), ModelType.DECISION,
					delegate::getPricingConditions);
			try {
				final DecisionResponse response = model.call(prompt);
				call.successReading(() -> response.getMetadata().getUsage());
				return response;
			} catch (RuntimeException e) {
				call.failure();
				throw e;
			}
		};
	}

	@Override
	public String getCode() {
		return delegate.getCode();
	}

	@Override
	public String getDescription() {
		return delegate.getDescription();
	}

	@Override
	public GDecisionModelType getType() {
		return delegate.getType();
	}

	@Override
	public void initialize(ModelConfig config, GDecisionModelType type) throws LLMConfigException {
		delegate.initialize(config, type);
	}

	@Override
	public void reconfigure(ModelConfig config) throws LLMConfigException {
		delegate.reconfigure(config);
	}

	@Override
	public ModelConfig getConfig() {
		return delegate.getConfig();
	}

	@Override
	public GModelPricingConditions getPricingConditions() {
		return delegate.getPricingConditions();
	}

	@Override
	public GModelPricingConditions getProviderApiPricingConditions() {
		return delegate.getProviderApiPricingConditions();
	}

	@Override
	public void testCall() throws LLMConfigException {
		delegate.testCall();
	}

	@Override
	public void delete() throws LLMConfigException {
		delegate.delete();
	}
}
