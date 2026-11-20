/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.system.ingestion.IGMediaTextExtractor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Turns pictures and recordings into text with the models of this
 * installation, for the services that read documents and have no model of
 * their own: in the microservices deployment the documents are read by one
 * service and the models are held by another, which serves this. Where no
 * model is deployed, or the installation turned the readings off, the
 * capabilities say so and the readings answer with no text.
 */
@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER','APPLICATION')")
@RequestMapping("api/users/IngestionMediaController")
public class IngestionMediaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(IngestionMediaController.class);
	private final ObjectProvider<IGMediaTextExtractor> extractor;

	public IngestionMediaController(ObjectProvider<IGMediaTextExtractor> extractor) {
		this.extractor = extractor;
	}

	/** What this installation reads of pictures and recordings while ingesting, and within which limits. */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class IngestionMediaCapabilities {
		private boolean describesPictures = false;
		private boolean transcribes = false;
		private int maxDescribedPagesPerDocument = 0;
		private long maxFileBytes = 0;
	}

	/** The text a model wrote for a picture or a recording; null when it wrote none. */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class IngestionMediaText {
		private String text = null;
	}

	private IGMediaTextExtractor extractor() {
		try {
			return extractor.getIfAvailable();
		} catch (RuntimeException unavailable) {
			return null;
		}
	}

	@GetMapping(value = "getIngestionMediaCapabilities", produces = MediaType.APPLICATION_JSON_VALUE)
	public IngestionMediaCapabilities getIngestionMediaCapabilities() {
		final IGMediaTextExtractor media = extractor();
		final IngestionMediaCapabilities capabilities = media == null ? new IngestionMediaCapabilities()
				: new IngestionMediaCapabilities(media.describesPictures(), media.transcribes(),
						media.maxDescribedPagesPerDocument(), media.maxFileBytes());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getIngestionMediaCapabilities() -> " + capabilities);
		}
		return capabilities;
	}

	@PostMapping(value = "describePicture", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public IngestionMediaText describePicture(@RequestParam(name = "name", required = false) String name,
			@RequestParam("mimeType") String mimeType, @RequestBody byte[] data) throws IOException {
		final IGMediaTextExtractor media = extractor();
		if (media == null || !media.describesPictures() || data == null || data.length > media.maxFileBytes()) {
			LOGGER.warn("describePicture(" + name + ", " + mimeType + "): not described, the reading is off here"
					+ " or the picture is larger than the files a model is given");
			return new IngestionMediaText(null);
		}
		final String text = media.describePicture(name, mimeType, data);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("describePicture(" + name + ", " + mimeType + ", " + data.length + " bytes) -> "
					+ (text != null ? text.length() : 0) + " character(s)");
		}
		return new IngestionMediaText(text);
	}

	@PostMapping(value = "transcribe", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public IngestionMediaText transcribe(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "mimeType", required = false) String mimeType, @RequestBody byte[] data)
			throws IOException {
		final IGMediaTextExtractor media = extractor();
		if (media == null || !media.transcribes() || data == null || data.length > media.maxFileBytes()) {
			LOGGER.warn("transcribe(" + name + ", " + mimeType + "): not transcribed, the reading is off here"
					+ " or the recording is larger than the files a model is given");
			return new IngestionMediaText(null);
		}
		final String text = media.transcribe(name, mimeType, new ByteArrayInputStream(data));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("transcribe(" + name + ", " + mimeType + ", " + data.length + " bytes) -> "
					+ (text != null ? text.length() : 0) + " character(s)");
		}
		return new IngestionMediaText(text);
	}
}
