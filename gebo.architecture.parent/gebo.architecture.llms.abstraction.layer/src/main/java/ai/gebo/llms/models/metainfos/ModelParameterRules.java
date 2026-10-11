/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.models.metainfos;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * What a provider accepts in a chat request to some of its models: the sampling
 * parameters and the reasoning efforts. Read from the models library
 * ({@code parameterRules} of {@code bundle-models/models.yml}, or of
 * {@code ai.gebo.models.library} for an installation) every time a chat model builds
 * its requests, so a change of the library reaches the models already configured at
 * their next start.
 * <p>
 * A rule names one provider and the models it covers by their ids, where {@code *}
 * stands for any characters ({@code gpt-6-luna*}); the same model id served by two
 * providers takes different parameters, so a rule never applies to another provider.
 * What a rule leaves unset is unknown, and the request is made as it would be without
 * the rule.
 */
public class ModelParameterRules {

	/** Whether the model takes the sampling parameters, temperature and top_p. */
	public static enum Sampling {
		/** Taken. */
		ACCEPTED,
		/** Refused: a request carrying them fails. */
		REJECTED,
		/**
		 * Taken only with the reasoning turned off, its effort {@code none}; refused
		 * otherwise.
		 */
		WITHOUT_REASONING
	}

	/** The effort value that turns the reasoning off. */
	public static final String NO_REASONING_EFFORT = "none";

	/** The provider the rule is for, as the model type declares it (e.g. "openai"). */
	private String providerId = null;
	/** The ids of the models the rule covers, {@code *} standing for any characters. */
	private List<String> models = new ArrayList<>();
	/** Whether the models take the sampling parameters; null when unknown. */
	private Sampling sampling = null;
	/**
	 * The reasoning effort values the models accept, as the provider spells them; empty
	 * when unknown.
	 */
	private List<String> reasoningEfforts = new ArrayList<>();
	/** Where the rule comes from: the provider page it was read on, and when. */
	private String source = null;

	/**
	 * Whether the rule covers the model of the provider.
	 *
	 * @param providerId the provider of the model, as its type declares it
	 * @param modelId    the model id the requests name
	 */
	public boolean covers(String providerId, String modelId) {
		if (this.providerId == null || providerId == null || modelId == null || models == null)
			return false;
		if (!this.providerId.equals(providerId))
			return false;
		for (String model : models) {
			if (model != null && matches(model, modelId))
				return true;
		}
		return false;
	}

	/** Whether the model id matches the pattern, {@code *} standing for any characters. */
	static boolean matches(String pattern, String modelId) {
		if (!pattern.contains("*"))
			return pattern.equals(modelId);
		final String[] parts = pattern.split("\\*", -1);
		final StringBuilder regex = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			if (i > 0)
				regex.append(".*");
			regex.append(Pattern.quote(parts[i]));
		}
		return Pattern.matches(regex.toString(), modelId);
	}

	/** Whether the effort is among the ones the rule lists; false when it lists none. */
	public boolean acceptsEffort(String effort) {
		return effort != null && reasoningEfforts != null && reasoningEfforts.contains(effort);
	}

	/** Whether the rule knows the reasoning efforts the models accept. */
	public boolean knowsEfforts() {
		return reasoningEfforts != null && !reasoningEfforts.isEmpty();
	}

	/**
	 * Whether a request may carry the sampling parameters.
	 *
	 * @param reasoningOff whether the request turns the reasoning off, with effort
	 *                     {@code none}
	 * @return true when the models take them, or when the rule does not know
	 */
	public boolean samplingAllowed(boolean reasoningOff) {
		if (sampling == null)
			return true;
		switch (sampling) {
		case ACCEPTED:
			return true;
		case WITHOUT_REASONING:
			return reasoningOff;
		case REJECTED:
		default:
			return false;
		}
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public List<String> getModels() {
		return models;
	}

	public void setModels(List<String> models) {
		this.models = models;
	}

	public Sampling getSampling() {
		return sampling;
	}

	public void setSampling(Sampling sampling) {
		this.sampling = sampling;
	}

	public List<String> getReasoningEfforts() {
		return reasoningEfforts;
	}

	public void setReasoningEfforts(List<String> reasoningEfforts) {
		this.reasoningEfforts = reasoningEfforts;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "ModelParameterRules[providerId=" + providerId + ", models=" + models + ", sampling=" + sampling
				+ ", reasoningEfforts=" + reasoningEfforts + "]";
	}
}
