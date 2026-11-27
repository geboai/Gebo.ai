/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.models.metainfos.ModelParameterRules.ValueRange;
import lombok.Getter;
import lombok.Setter;

/**
 * What a chat model takes of the parameters its configuration sets - temperature, top_p,
 * thinking - as its provider's integration sends them: the same decisions the
 * integration makes building the model's requests, from the models library and what the
 * provider's models api told of the model. The chat model and agent editors enable their
 * controls and offer their values by it.
 * <p>
 * An unknown one ({@link #isKnown()} false) says nothing: the editors keep every control
 * as it is, which is what a provider integration that does not tell gets.
 */
@Getter
@Setter
public class GChatModelParametersSupport {
	/** The order the thinking options are offered in, as the editors list them. */
	static final List<ChatModelThinkingOption> THINKING_OPTIONS = List.of(ChatModelThinkingOption.NO_THINKING,
			ChatModelThinkingOption.AUTO, ChatModelThinkingOption.LOW_THINKING,
			ChatModelThinkingOption.MEDIUM_THINKING, ChatModelThinkingOption.HIGH_THINKING);

	private String providerId = null;
	private String modelCode = null;
	/** Whether the integration told: false leaves every control as it is. */
	private boolean known = false;
	private GSamplingParameterSupport temperature = new GSamplingParameterSupport();
	private GSamplingParameterSupport topP = new GSamplingParameterSupport();
	/** Whether a thinking option reaches the model: false when the model does not reason, or its integration sends no thinking. */
	private boolean thinkingAvailable = true;
	/** The thinking options that send something, and Automatic, in the editors' order. */
	private List<GThinkingOptionSupport> thinkingOptions = new ArrayList<>();
	/** Where what is told comes from (the models library rule, the provider's models api, the integration). */
	private List<String> sources = new ArrayList<>();

	public GChatModelParametersSupport() {
	}

	/** One that says nothing: every control stays as it is. */
	public static GChatModelParametersSupport unknown(String providerId, String modelCode) {
		final GChatModelParametersSupport support = new GChatModelParametersSupport();
		support.providerId = providerId;
		support.modelCode = modelCode;
		for (ChatModelThinkingOption option : THINKING_OPTIONS) {
			support.thinkingOptions.add(new GThinkingOptionSupport(option, null));
		}
		return support;
	}

	/**
	 * When a model with these parameter rules gets the sampling parameters, as
	 * {@link ModelParameterRules#samplingAllowed(boolean)} lets them go.
	 */
	public static GSamplingAvailability samplingAvailabilityOf(ModelParameterRules rules) {
		if (rules == null || rules.getSampling() == null)
			return GSamplingAvailability.ALWAYS;
		switch (rules.getSampling()) {
		case ACCEPTED:
		case PROVIDER_DEFAULT:
			return GSamplingAvailability.ALWAYS;
		case WITHOUT_REASONING:
			return rules.acceptsEffort(ModelParameterRules.NO_REASONING_EFFORT) ? GSamplingAvailability.WITHOUT_THINKING
					: GSamplingAvailability.NEVER;
		case REJECTED:
		case IGNORED:
		default:
			return GSamplingAvailability.NEVER;
		}
	}

	/**
	 * The support of a model whose integration sends the sampling parameters as its
	 * parameter rules let them (GAbstractConfigurableChatModel: temperature 0 when none is
	 * set, unless the provider's default is kept) and its thinking as {@code sentAs}
	 * tells.
	 *
	 * @param rules             the model's parameter rules, may be null
	 * @param temperatureRange  the temperature range, may be null
	 * @param topPRange         the top_p range, may be null
	 * @param thinkingAvailable false when no thinking option reaches the model
	 * @param sentAs            what the requests send for an option, null when nothing
	 */
	public static GChatModelParametersSupport of(String providerId, String modelCode, ModelParameterRules rules,
			ValueRange temperatureRange, ValueRange topPRange, boolean thinkingAvailable,
			Function<ChatModelThinkingOption, String> sentAs) {
		final GChatModelParametersSupport support = new GChatModelParametersSupport();
		support.providerId = providerId;
		support.modelCode = modelCode;
		support.known = true;
		final GSamplingAvailability sampling = samplingAvailabilityOf(rules);
		support.temperature = sampling(sampling, temperatureRange,
				rules != null && rules.keepsProviderDefaultSampling());
		support.topP = sampling(sampling, topPRange, true);
		support.thinkingAvailable = thinkingAvailable;
		if (thinkingAvailable) {
			for (ChatModelThinkingOption option : THINKING_OPTIONS) {
				final String sent = sentAs != null ? sentAs.apply(option) : null;
				if (option == ChatModelThinkingOption.AUTO || sent != null) {
					support.thinkingOptions.add(new GThinkingOptionSupport(option, sent));
				}
			}
			// no option sends anything: the thinking never reaches the model
			if (support.thinkingOptions.stream().allMatch(o -> o.getSentAs() == null)) {
				support.thinkingAvailable = false;
				support.thinkingOptions.clear();
			}
		}
		if (rules != null && rules.getSource() != null) {
			support.sources.add(rules.getSource());
		}
		return support;
	}

	private static GSamplingParameterSupport sampling(GSamplingAvailability availability, ValueRange range,
			boolean providerDefaultWhenEmpty) {
		final GSamplingParameterSupport parameter = new GSamplingParameterSupport(availability);
		if (range != null) {
			parameter.setMin(range.getMin());
			parameter.setMax(range.getMax());
		}
		parameter.setProviderDefaultWhenEmpty(providerDefaultWhenEmpty);
		return parameter;
	}

	/** Whether the thinking option is among the ones offered. */
	public boolean offers(ChatModelThinkingOption option) {
		return thinkingOptions.stream().anyMatch(o -> o.getOption() == option);
	}

	/** What the requests send for the option, null when nothing or not offered. */
	public String sentAs(ChatModelThinkingOption option) {
		for (GThinkingOptionSupport offered : thinkingOptions) {
			if (offered.getOption() == option)
				return offered.getSentAs();
		}
		return null;
	}
}
