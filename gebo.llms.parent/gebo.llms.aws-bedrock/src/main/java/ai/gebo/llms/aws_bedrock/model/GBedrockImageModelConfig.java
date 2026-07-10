/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.model;

import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;

/**
 * Configuration for AWS Bedrock image generation models. Image generation on
 * Bedrock is served through the AWS SDK {@code InvokeModel} operation (Spring AI
 * has no dedicated Bedrock image model), so the height/width/quality/seed knobs
 * live here.
 */
public class GBedrockImageModelConfig extends GBaseImageModelConfig<GBedrockImageModelChoice> {

	/** Generated image height in pixels. */
	private Integer height = null;
	/** Generated image width in pixels. */
	private Integer width = null;
	/** Optional CFG scale (prompt adherence). */
	private Double cfgScale = null;
	/** Optional deterministic generation seed. */
	private Long seed = null;

	public GBedrockImageModelConfig() {
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Double getCfgScale() {
		return cfgScale;
	}

	public void setCfgScale(Double cfgScale) {
		this.cfgScale = cfgScale;
	}

	public Long getSeed() {
		return seed;
	}

	public void setSeed(Long seed) {
		this.seed = seed;
	}
}
