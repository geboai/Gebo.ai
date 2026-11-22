/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services.config;

import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery;
import ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.Limits;
import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import jakarta.annotation.PostConstruct;

/**
 * The settings of the files that go to the chat models as they are, in a chat with
 * files and when a tool shows a file to its model: a switch and the limits of each
 * kind. Every kind is off when not set, and its limits are low: a picture, a
 * recording or a video is counted by each provider its own way and no provider is
 * asked beforehand, so what an installation sends is what it chose to.
 */
@Configuration
public class ModelFilesDeliveryConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ModelFilesDeliveryConfig.class);

	@Value("${ai.gebo.images.chat-enabled:false}")
	boolean imagesEnabled;
	@Value("${ai.gebo.images.chat-max-images:2}")
	int maxImages;
	@Value("${ai.gebo.images.chat-max-image-mb:2}")
	int maxImageMb;
	@Value("${ai.gebo.images.chat-max-total-mb:4}")
	int maxImagesTotalMb;
	@Value("${ai.gebo.images.chat-estimated-tokens-per-image:1500}")
	int tokensPerImage;

	@Value("${ai.gebo.recordings.chat-enabled:false}")
	boolean recordingsEnabled;
	@Value("${ai.gebo.recordings.chat-max-recordings:1}")
	int maxRecordings;
	@Value("${ai.gebo.recordings.chat-max-recording-mb:5}")
	int maxRecordingMb;
	@Value("${ai.gebo.recordings.chat-max-total-mb:5}")
	int maxRecordingsTotalMb;
	@Value("${ai.gebo.recordings.chat-estimated-tokens-per-recording:5000}")
	int tokensPerRecording;

	@Value("${ai.gebo.videos.chat-enabled:false}")
	boolean videosEnabled;
	@Value("${ai.gebo.videos.chat-max-videos:1}")
	int maxVideos;
	@Value("${ai.gebo.videos.chat-max-video-mb:10}")
	int maxVideoMb;
	@Value("${ai.gebo.videos.chat-max-total-mb:10}")
	int maxVideosTotalMb;
	@Value("${ai.gebo.videos.chat-estimated-tokens-per-video:10000}")
	int tokensPerVideo;

	@Value("${ai.gebo.documents.chat-enabled:false}")
	boolean documentsEnabled;
	@Value("${ai.gebo.documents.chat-max-documents:1}")
	int maxDocuments;
	@Value("${ai.gebo.documents.chat-max-document-mb:2}")
	int maxDocumentMb;
	@Value("${ai.gebo.documents.chat-max-total-mb:2}")
	int maxDocumentsTotalMb;
	@Value("${ai.gebo.documents.chat-estimated-tokens-per-document:5000}")
	int tokensPerDocument;

	@PostConstruct
	public void apply() {
		final Map<GModelInputModality, Limits> limits = new EnumMap<>(GModelInputModality.class);
		limits.put(GModelInputModality.IMAGE,
				Limits.of(imagesEnabled, maxImages, maxImageMb, maxImagesTotalMb, tokensPerImage));
		limits.put(GModelInputModality.AUDIO, Limits.of(recordingsEnabled, maxRecordings, maxRecordingMb,
				maxRecordingsTotalMb, tokensPerRecording));
		limits.put(GModelInputModality.VIDEO,
				Limits.of(videosEnabled, maxVideos, maxVideoMb, maxVideosTotalMb, tokensPerVideo));
		limits.put(GModelInputModality.DOCUMENT, Limits.of(documentsEnabled, maxDocuments, maxDocumentMb,
				maxDocumentsTotalMb, tokensPerDocument));
		GModelFilesDelivery.set(limits);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Files sent to the chat models as they are: " + limits);
		}
	}
}
