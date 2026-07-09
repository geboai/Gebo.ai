/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.model;

import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;

/**
 * Configuration for the AWS (Amazon Polly) text-to-speech provider. The chosen
 * model code maps to a Polly voice id; the engine selects the standard / neural
 * / long-form / generative synthesis engine.
 */
public class GBedrockTextToSpeechModelConfig extends GBaseTextToSpeachModelConfig<GBedrockTextToSpeechModelChoice>
		implements IBedrockRegionAware {

	/** AWS region hosting the Polly endpoint (e.g. {@code us-east-1}). */
	private String region = null;
	/** Polly voice id (e.g. {@code Joanna}). When null the chosen model code is used. */
	private String voice = null;
	/** Polly engine: {@code standard}, {@code neural}, {@code long-form} or {@code generative}. */
	private String engine = null;

	public GBedrockTextToSpeechModelConfig() {
	}

	@Override
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}
}
