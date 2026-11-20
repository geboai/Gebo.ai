/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.microservices.documents.access;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.system.ingestion.IGMediaTextExtractor;

/**
 * Pictures and recordings read by the models of another service: the service
 * reading the documents holds no model, the one holding them serves
 * {@code IngestionMediaController}. What that service reads, and within which
 * limits, is asked of it and remembered for a while: the readers ask at every
 * document. While it cannot be reached nothing is read, as where no model is
 * deployed, and it is asked again later.
 */
public class GMicroserviceMediaTextExtractorClient implements IGMediaTextExtractor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GMicroserviceMediaTextExtractorClient.class);
	static final String CONTROLLER_PATH = "/api/users/IngestionMediaController/";
	/** How long the capabilities told by the models' service are held. */
	static final Duration CAPABILITIES_TTL = Duration.ofMinutes(1);
	/** How long a reading is waited for: a model describing a page or transcribing a recording takes its time. */
	static final Duration READING_TIMEOUT = Duration.ofMinutes(10);

	/** What the models' service reads; the shape of {@code IngestionMediaCapabilities}. */
	public static class Capabilities {
		public boolean describesPictures = false;
		public boolean transcribes = false;
		public int maxDescribedPagesPerDocument = 0;
		public long maxFileBytes = 0;
	}

	/** The text a model wrote; the shape of {@code IngestionMediaText}. */
	public static class MediaText {
		public String text = null;
	}

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final IGeboCallerTokenPropagator tokenPropagator;
	private final String modelsMicroserviceId;
	private volatile Capabilities capabilities = new Capabilities();
	private volatile long capabilitiesReadAt = 0;

	public GMicroserviceMediaTextExtractorClient(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, String modelsMicroserviceId) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.modelsMicroserviceId = modelsMicroserviceId;
	}

	private String baseUrl() throws IOException {
		return urlResolver.baseUrlForMicroserviceId(modelsMicroserviceId).orElseThrow(
				() -> new IOException("No base url for the microservice holding the models: " + modelsMicroserviceId));
	}

	private void applyCallerToken(HttpHeaders headers) {
		final String token = tokenPropagator.currentToken();
		if (StringUtils.hasText(token)) {
			headers.setBearerAuth(token);
		}
	}

	/** What the models' service reads, asked again once {@link #CAPABILITIES_TTL} passed. */
	Capabilities capabilities() {
		final long now = System.currentTimeMillis();
		if (capabilitiesReadAt != 0 && now - capabilitiesReadAt < CAPABILITIES_TTL.toMillis()) {
			return capabilities;
		}
		synchronized (this) {
			if (capabilitiesReadAt != 0 && now - capabilitiesReadAt < CAPABILITIES_TTL.toMillis()) {
				return capabilities;
			}
			Capabilities read = null;
			try {
				read = webClient.get().uri(URI.create(baseUrl() + CONTROLLER_PATH + "getIngestionMediaCapabilities"))
						.accept(MediaType.APPLICATION_JSON).headers(this::applyCallerToken).retrieve()
						.bodyToMono(Capabilities.class).block(Duration.ofSeconds(30));
			} catch (IOException | RuntimeException unreachable) {
				LOGGER.warn("The service holding the models (" + modelsMicroserviceId
						+ ") did not tell what it reads of pictures and recordings: nothing is read for now. "
						+ unreachable);
			}
			capabilities = read != null ? read : new Capabilities();
			capabilitiesReadAt = now;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Ingestion media capabilities of " + modelsMicroserviceId + ": pictures="
						+ capabilities.describesPictures + " recordings=" + capabilities.transcribes);
			}
			return capabilities;
		}
	}

	@Override
	public boolean describesPictures() {
		return capabilities().describesPictures;
	}

	@Override
	public int maxDescribedPagesPerDocument() {
		return capabilities().maxDescribedPagesPerDocument;
	}

	@Override
	public long maxFileBytes() {
		return capabilities().maxFileBytes;
	}

	@Override
	public boolean transcribes() {
		return capabilities().transcribes;
	}

	private String read(String endpoint, String name, String mimeType, byte[] data) throws IOException {
		final UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(baseUrl() + CONTROLLER_PATH + endpoint);
		if (name != null) {
			uri.queryParam("name", name);
		}
		if (mimeType != null) {
			uri.queryParam("mimeType", mimeType);
		}
		try {
			final MediaText answered = webClient.post().uri(uri.build().encode().toUri())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).accept(MediaType.APPLICATION_JSON)
					.headers(this::applyCallerToken).bodyValue(data).retrieve().bodyToMono(MediaText.class)
					.block(READING_TIMEOUT);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("REST " + endpoint + "(" + name + ", " + mimeType + ", " + data.length + " bytes) -> "
						+ (answered != null && answered.text != null ? answered.text.length() : 0) + " character(s)");
			}
			return answered != null ? answered.text : null;
		} catch (RuntimeException failed) {
			throw new IOException("Remote " + endpoint + " of " + name + " failed", failed);
		}
	}

	@Override
	public String describePicture(String name, String mimeType, byte[] data) throws IOException {
		if (data == null || data.length == 0) {
			return null;
		}
		return read("describePicture", name, mimeType, data);
	}

	@Override
	public String transcribe(String name, String mimeType, InputStream content) throws IOException {
		if (content == null) {
			return null;
		}
		final long most = maxFileBytes();
		final byte[] data = content.readNBytes((int) Math.min(Integer.MAX_VALUE - 8L, most + 1));
		if (data.length == 0 || data.length > most) {
			LOGGER.warn("The recording " + name + " is not transcribed: it is empty or larger than " + most + " bytes");
			return null;
		}
		return read("transcribe", name, mimeType, data);
	}
}
