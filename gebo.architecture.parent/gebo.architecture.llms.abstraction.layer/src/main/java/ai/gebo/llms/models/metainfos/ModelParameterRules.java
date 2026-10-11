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

import lombok.Getter;
import lombok.Setter;

/**
 * What a provider accepts in a chat request to some of its models: the sampling
 * parameters and the reasoning efforts. Read from the models library
 * ({@code parameterRules} of the {@code bundle-models/<providerId>.yml} segment the module
 * implementing the provider ships, or of
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
@Getter
@Setter
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
		WITHOUT_REASONING,
		/**
		 * Taken, but the provider recommends its own default (Gemini 3: lower
		 * temperatures may loop or degrade reasoning): a configuration setting none
		 * sends none, instead of Gebo's deterministic 0; one setting it is sent.
		 */
		PROVIDER_DEFAULT
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
	/**
	 * The input_type an embedding model takes with a search query (e.g. "query"); null
	 * when it takes none. Set together with {@link #documentInputType}.
	 */
	private String queryInputType = null;
	/**
	 * The input_type an embedding model takes with the documents it embeds to store
	 * (e.g. "passage"); null when it takes none.
	 */
	private String documentInputType = null;
	/**
	 * The only audio format a speech model answers in (e.g. "pcm"); null when it takes the
	 * mp3 asked by default.
	 */
	private String speechFormat = null;
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
		case PROVIDER_DEFAULT:
			return true;
		case WITHOUT_REASONING:
			return reasoningOff;
		case REJECTED:
		default:
			return false;
		}
	}

	/**
	 * Whether a configuration without a temperature is to be left at the provider's
	 * default rather than given Gebo's deterministic 0.
	 */
	public boolean keepsProviderDefaultSampling() {
		return sampling == Sampling.PROVIDER_DEFAULT;
	}

	/**
	 * The rules of a model from what its provider's models api told (live) and from the
	 * models library: the live efforts win when known, a live REJECTED sampling wins (the
	 * api says the model takes no temperature), any other sampling is the library's when
	 * it has one; the embedding and speech fields are the library's. Either may be null.
	 */
	public static ModelParameterRules merge(ModelParameterRules live, ModelParameterRules library) {
		if (live == null)
			return library;
		if (library == null)
			return live;
		final ModelParameterRules merged = new ModelParameterRules();
		merged.providerId = library.providerId != null ? library.providerId : live.providerId;
		merged.models = new ArrayList<>(library.models != null ? library.models : live.models);
		if (live.sampling == Sampling.REJECTED || library.sampling == null)
			merged.sampling = live.sampling;
		else
			merged.sampling = library.sampling;
		merged.reasoningEfforts = new ArrayList<>(live.knowsEfforts() ? live.reasoningEfforts
				: library.reasoningEfforts != null ? library.reasoningEfforts : List.of());
		merged.queryInputType = library.queryInputType;
		merged.documentInputType = library.documentInputType;
		merged.speechFormat = library.speechFormat;
		merged.source = live.source + "; " + library.source;
		return merged;
	}

	/** Whether an embedding model takes an input_type for queries and for documents. */
	public boolean sendsInputTypes() {
		return queryInputType != null && !queryInputType.isBlank() && documentInputType != null
				&& !documentInputType.isBlank();
	}

	@Override
	public String toString() {
		return "ModelParameterRules[providerId=" + providerId + ", models=" + models + ", sampling=" + sampling
				+ ", reasoningEfforts=" + reasoningEfforts + ", queryInputType=" + queryInputType
				+ ", documentInputType=" + documentInputType + ", speechFormat=" + speechFormat + "]";
	}
}
