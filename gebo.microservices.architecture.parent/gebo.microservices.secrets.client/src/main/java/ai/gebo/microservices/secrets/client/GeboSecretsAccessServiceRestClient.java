/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.client;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboSecretContentEnvelope;
import ai.gebo.secrets.model.GeboSecretContentTypes;
import ai.gebo.secrets.model.GeboSecretStoreRequest;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

/**
 * {@link IGeboSecretsAccessService} backed by the security microservice: every
 * call becomes a request to the {@code SecretsClusterController} of
 * heimdall.gebo.ai. Consumers keep depending on the interface and cannot tell
 * whether the secrets live in-process or behind the network.
 *
 * <p>
 * <b>Addressing.</b> The base url is resolved per call through the
 * {@link GeboMicroserviceUrlResolver} rather than configured, so the same build
 * works whether the deployment addresses services through the load balancer, the
 * gateway or a pinned direct url. An unresolvable microservice id fails loudly
 * instead of producing a dead url.
 * </p>
 *
 * <p>
 * <b>Authentication.</b> The caller's own token is forwarded (see
 * {@link IGeboCallerTokenPropagator}), so heimdall authorises the request
 * against the identity that originated it. The token is read on the
 * <i>calling</i> thread and pinned into the request headers before the exchange
 * is subscribed - resolving it inside a WebClient filter would read the
 * {@code SecurityContext} of whichever thread Reactor happens to run the filter
 * on, which is not necessarily the caller's.
 * </p>
 *
 * <h2>Reads are cached, and safely so</h2>
 * <p>
 * A secret's content is <b>immutable under its id</b>: the admin surface creates and
 * deletes, it never updates. Rotating a key means a NEW secret with a NEW id, which is a
 * different cache key - so a cache can never return superseded content for an id, because
 * the id whose content changed does not exist. That is what makes caching sound here even
 * though a resolved key then lives for hours inside an in-memory chat model (an LLM key is
 * read once, at {@code configureModel(...)}, not per call).
 * </p>
 *
 * <p>
 * What is held is the <b>ciphertext</b> off the wire; the plaintext is never retained. A
 * write through this client clears the cache at once.
 * </p>
 *
 * <p>
 * <b>The one exception:</b> {@code GOauth2ConfigurationServiceImpl} rewrites an OAuth2
 * client secret <i>in place, under the same id</i>, and it does so on heimdall - so this
 * cache never learns of it. That rotation must be propagated by the same event that
 * reloads the OAuth2 runtime configuration, not waited out on this TTL.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class GeboSecretsAccessServiceRestClient implements IGeboSecretsAccessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboSecretsAccessServiceRestClient.class);

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final IGeboCallerTokenPropagator tokenPropagator;
	private final ObjectMapper mapper;
	/**
	 * Decryption and encryption happen HERE, not on the secrets service. The wire only
	 * ever carries ciphertext; this is what turns it back into a secret.
	 */
	private final IGeboCryptingService cryptService;
	private final String microserviceId;
	private final String basePath;
	/** Caches the CIPHERTEXT as it came off the wire; plaintext is never retained. */
	private final GeboTtlCache cache;

	public GeboSecretsAccessServiceRestClient(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, ObjectMapper mapper, IGeboCryptingService cryptService,
			String microserviceId, String basePath, GeboTtlCache cache) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.mapper = mapper;
		this.cryptService = cryptService;
		this.microserviceId = microserviceId;
		this.basePath = trimSlashes(basePath);
		this.cache = cache;
	}

	@Override
	public AbstractGeboSecretContent getSecretContentById(String id) throws GeboCryptSecretException {
		GeboSecretContentEnvelope envelope = fetchContent(id);
		Class<? extends AbstractGeboSecretContent> contentClass = GeboSecretContentTypes
				.contentClassFor(envelope.getSecretType());
		if (contentClass == null) {
			throw new GeboCryptSecretException("Unkown value of SecretType " + envelope.getSecretType());
		}
		return readContent(envelope, contentClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The concrete {@link GeboCustomSecretContent} subclass is a <i>caller-side</i>
	 * type - the secrets service has never heard of it. Because the ciphertext arrives
	 * untouched, decrypting it yields the stored JSON <b>verbatim</b>, so deserialising
	 * into {@code type} recovers every subclass field. A service that decrypted on the
	 * caller's behalf would have had to pick a class to hand back, and any choice but
	 * this one silently drops those fields.
	 * </p>
	 */
	@Override
	public <T extends GeboCustomSecretContent> T getCustomSecretContentById(String id, Class<T> type)
			throws GeboCryptSecretException {
		GeboSecretContentEnvelope envelope = fetchContent(id);
		if (envelope.getSecretType() != GeboSecretType.CUSTOM_SECRET) {
			throw new GeboCryptSecretException("SecretType must be CUSTOM_SECRET");
		}
		return readContent(envelope, type);
	}

	@Override
	public List<String> getAllSecretsId() {
		try {
			String[] ids = cache.getChecked("allIds", () -> call("getAllSecretsId",
					() -> webClient.get().uri(uri("getAllSecretsId")).headers(this::applyCallerToken)
							.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(String[].class).block()));
			return ids == null ? List.of() : List.of(ids);
		} catch (GeboCryptSecretException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <SecretType extends AbstractGeboSecretContent> String storeSecret(SecretType secret, String description,
			String contextCode) throws GeboCryptSecretException {
		return store(secret, description, contextCode, null);
	}

	@Override
	public <SecretType extends AbstractGeboSecretContent> void storeSecret(SecretType secret, String description,
			String contextCode, String secretId) throws GeboCryptSecretException {
		store(secret, description, contextCode, secretId);
	}

	@Override
	public <SecretType extends AbstractGeboSecretContent> void updateSecret(SecretType secret, String description,
			String contextCode, String code) throws GeboCryptSecretException {
		GeboSecretStoreRequest request = storeRequest(secret, description, contextCode, code);
		call("updateSecret", () -> webClient.post().uri(uri("updateSecret")).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).bodyValue(request).retrieve().toBodilessEntity().block());
		// Our own write must be visible to us at once: waiting out the TTL to see a secret
		// we just changed would be indefensible.
		cache.clear();
	}

	@Override
	public void deleteSecret(String code) throws GeboCryptSecretException {
		call("deleteSecret", () -> webClient.delete().uri(uri("deleteSecret", "code", code))
				.headers(this::applyCallerToken).retrieve().toBodilessEntity().block());
		cache.clear();
	}

	@Override
	public List<SecretInfo> getSecretInfoByContextCode(String contextCode) throws GeboCryptSecretException {
		SecretInfo[] infos = cache.getChecked("infoByContext:" + contextCode, () -> call("getSecretInfoByContextCode",
				() -> webClient.get().uri(uri("getSecretInfoByContextCode", "contextCode", contextCode))
						.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
						.bodyToMono(SecretInfo[].class).block()));
		return infos == null ? List.of() : List.of(infos);
	}

	@Override
	public SecretInfo getSecretInfoById(String code) throws GeboCryptSecretException {
		// Mirrors the local implementation: an unknown id yields null, it is not an error.
		return cache.getChecked("infoById:" + code, () -> call("getSecretInfoById",
				() -> webClient.get().uri(uri("getSecretInfoById", "code", code)).headers(this::applyCallerToken)
						.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(SecretInfo.class).block()));
	}

	// --- Internals ----------------------------------------------------------

	private GeboSecretContentEnvelope fetchContent(String id) throws GeboCryptSecretException {
		// The cached value is the ENCRYPTED envelope, not the secret: decryption happens
		// per call, below, and the plaintext is never held.
		GeboSecretContentEnvelope envelope = cache.getChecked("content:" + id, () -> call("getSecretContentById",
				() -> webClient.get().uri(uri("getSecretContentById", "id", id)).headers(this::applyCallerToken)
						.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(GeboSecretContentEnvelope.class)
						.block()));
		if (envelope == null || envelope.getCryptedContent() == null) {
			throw new GeboCryptSecretException("Unkown secret with code=>" + id);
		}
		return envelope;
	}

	/** Decrypts the transported ciphertext, then reads the JSON it yields into {@code type}. */
	private <T> T readContent(GeboSecretContentEnvelope envelope, Class<T> type) throws GeboCryptSecretException {
		String json = cryptService.decrypt(envelope.getCryptedContent());
		try {
			return mapper.readValue(json, type);
		} catch (JacksonException e) {
			throw new GeboCryptSecretException("Cannot read from secret json format", e);
		}
	}

	private <SecretType extends AbstractGeboSecretContent> String store(SecretType secret, String description,
			String contextCode, String secretId) throws GeboCryptSecretException {
		GeboSecretStoreRequest request = storeRequest(secret, description, contextCode, secretId);
		String stored = call("storeSecret", () -> webClient.post().uri(uri("storeSecret"))
				.headers(this::applyCallerToken).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.TEXT_PLAIN).bodyValue(request).retrieve().bodyToMono(String.class).block());
		cache.clear();
		return stored;
	}

	private <SecretType extends AbstractGeboSecretContent> GeboSecretStoreRequest storeRequest(SecretType secret,
			String description, String contextCode, String secretId) throws GeboCryptSecretException {
		if (secret == null) {
			throw new GeboCryptSecretException("Cannot store a null secret content");
		}
		GeboSecretStoreRequest request = new GeboSecretStoreRequest();
		request.setContextCode(contextCode);
		request.setDescription(description);
		request.setSecretId(secretId);
		request.setSecretType(secret.type());
		try {
			// Sealed here, before it leaves: the owner stores what we send and never sees
			// the plaintext.
			request.setCryptedContent(cryptService.crypt(mapper.writeValueAsString(secret)));
		} catch (JacksonException e) {
			throw new GeboCryptSecretException("exception while running storeSecret(...)", e);
		}
		return request;
	}

	/** Adds the caller's bearer token, read on this - the calling - thread. */
	private void applyCallerToken(HttpHeaders headers) {
		String token = tokenPropagator.currentToken();
		if (StringUtils.hasText(token)) {
			headers.setBearerAuth(token);
		} else {
			LOGGER.debug("No caller token to forward to the secrets microservice; the call goes out unauthenticated");
		}
	}

	private URI uri(String endpoint) {
		return UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint).build().encode().toUri();
	}

	private URI uri(String endpoint, String paramName, String paramValue) {
		return UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint)
				.queryParam(paramName, paramValue).build().encode().toUri();
	}

	private String baseUrl() {
		Optional<String> baseUrl = urlResolver.baseUrlForMicroserviceId(microserviceId);
		return baseUrl.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of the secrets "
				+ "microservice '" + microserviceId + "': it is not a member of the topology and has no 'direct' "
				+ "entry (gebo.microservices.topology.url.direct)."));
	}

	/**
	 * Runs a remote call, turning any transport or remote failure into the
	 * {@link GeboCryptSecretException} the interface declares, so a consumer written
	 * against the local implementation needs no change.
	 */
	private <T> T call(String operation, RemoteCall<T> remoteCall) throws GeboCryptSecretException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST {} on secrets microservice '{}'", operation, microserviceId);
		}
		try {
			return remoteCall.execute();
		} catch (WebClientResponseException ex) {
			// The response body may quote the failing secret id but never its content:
			// the cluster controller only ever returns content in a 2xx envelope.
			throw new GeboCryptSecretException("Remote " + operation + " failed: " + ex.getStatusCode() + " "
					+ ex.getResponseBodyAsString(), ex);
		} catch (RuntimeException ex) {
			throw new GeboCryptSecretException("Remote " + operation + " failed", ex);
		}
	}

	@FunctionalInterface
	private interface RemoteCall<T> {
		T execute();
	}

	private static String trimSlashes(String path) {
		String trimmed = path == null ? "" : path.trim();
		while (trimmed.startsWith("/")) {
			trimmed = trimmed.substring(1);
		}
		while (trimmed.endsWith("/")) {
			trimmed = trimmed.substring(0, trimmed.length() - 1);
		}
		return trimmed;
	}
}
