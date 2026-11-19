/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Reading pictures, scanned pages, recordings and videos into knowledge bases with a
 * model. Every file read this way costs a model call, on every ingestion of it, so
 * both are off until the installation turns them on.
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.ingestion.media")
@Data
public class IngestionMediaConfig {
	/** Pictures and the pages without text of PDF files are described by a chat model that reads pictures. */
	private boolean describePictures = false;
	/** Recordings and videos are transcribed by the default transcription model. */
	private boolean transcribeRecordings = false;
	/**
	 * The code of the chat model configuration that describes pictures. Unset: the
	 * default chat model when it reads pictures, else the first configured one that does.
	 */
	private String pictureModelCode = null;
	/** The pages without text of one PDF that are described at most. */
	private int maxDescribedPagesPerDocument = 30;
	/** The size in bytes a picture or a recording may have to be given to a model. */
	private long maxFileBytes = 20L * 1024 * 1024;
}
