/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * The switches and the limits of the recordings and of the videos the ingestion gives
 * as they are, beside {@link ImagesIngestionConfig} for the images: the same settings
 * the reader of recording and video files obeys, for the readers that meet such a
 * media inside another document (a web page, a Gebo document).
 */
@Configuration
@Data
public class RecordingsAndVideosIngestionConfig {
	@Value("${ai.gebo.recordings.ingestion-enabled:false}")
	private boolean recordingsEnabled = false;
	@Value("${ai.gebo.videos.ingestion-enabled:false}")
	private boolean videosEnabled = false;
	/** A recording larger than this, in megabytes, is passed over. */
	@Value("${ai.gebo.recordings.max-recording-mb:20}")
	private int maxRecordingMb = 20;
	/** A video larger than this, in megabytes, is passed over. */
	@Value("${ai.gebo.videos.max-video-mb:20}")
	private int maxVideoMb = 20;

	public long getMaxRecordingBytes() {
		return Math.max(0, maxRecordingMb) * 1024L * 1024L;
	}

	public long getMaxVideoBytes() {
		return Math.max(0, maxVideoMb) * 1024L * 1024L;
	}
}
