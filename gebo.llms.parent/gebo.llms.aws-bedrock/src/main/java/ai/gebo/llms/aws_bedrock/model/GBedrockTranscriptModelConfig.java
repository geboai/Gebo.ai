/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.model;

import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;

/**
 * Configuration for the AWS (Amazon Transcribe streaming) speech-to-text
 * provider.
 */
public class GBedrockTranscriptModelConfig extends GBaseTranscriptModelConfig<GBedrockTranscriptModelChoice> {

	/** BCP-47 language code for the audio (e.g. {@code en-US}). */
	private String languageCode = null;
	/** Sample rate (Hz) of the audio stream. Defaults to 16000 when null. */
	private Integer sampleRateHertz = null;
	/** Media encoding: {@code pcm}, {@code ogg-opus} or {@code flac}. */
	private String mediaEncoding = null;

	public GBedrockTranscriptModelConfig() {
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Integer getSampleRateHertz() {
		return sampleRateHertz;
	}

	public void setSampleRateHertz(Integer sampleRateHertz) {
		this.sampleRateHertz = sampleRateHertz;
	}

	public String getMediaEncoding() {
		return mediaEncoding;
	}

	public void setMediaEncoding(String mediaEncoding) {
		this.mediaEncoding = mediaEncoding;
	}
}
