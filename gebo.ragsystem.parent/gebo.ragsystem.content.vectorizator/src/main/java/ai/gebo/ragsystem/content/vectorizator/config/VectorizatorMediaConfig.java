/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.ragsystem.content.vectorizator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * What the vectorizator does with the media the chunking returns among the
 * chunks of a document (see {@code ai.gebo.images.ingestion-enabled} and its
 * like): nothing until turned on here. On, and with a configured model that
 * reads that kind of media, each is turned into text - an image described, a
 * recording or a video transcribed - which is then embedded as the document's
 * texts are, its vectors recorded with the document's.
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.vectorizator")
@Data
public class VectorizatorMediaConfig {
	/** Images are described by a chat model that reads pictures, the description embedded. */
	private boolean embedImages = false;
	/** Recordings are transcribed by the default transcription model, the transcript embedded. */
	private boolean embedRecordings = false;
	/** Videos are transcribed by the default transcription model, the transcript embedded. */
	private boolean embedVideos = false;
	/**
	 * The chat model configuration that describes images; unset: the default chat model
	 * when it reads pictures, else the first configured one that does.
	 */
	private String imageModelCode = null;
	/** A media larger than this, in megabytes, is not given to a model. */
	private int maxMediaMb = 20;

	public long maxMediaBytes() {
		return Math.max(0, maxMediaMb) * 1024L * 1024L;
	}
}
