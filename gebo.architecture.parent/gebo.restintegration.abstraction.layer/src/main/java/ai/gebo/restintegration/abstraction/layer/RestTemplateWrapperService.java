/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.restintegration.abstraction.layer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

import ai.gebo.model.GUserMessage;
import io.micrometer.observation.ObservationRegistry;

/**
 * The HTTP calls of the integrations (the models lookups, the search services, the
 * content management systems): get, post and exchange, their status mapped to Gebo's
 * exceptions (404 {@link GeboNotFoundException}, 401-403
 * {@link GeboInvalidAccessException}, 5xx {@link GeboRemoteBackendErrorException},
 * anything else {@link GeboRestIntegrationException}).
 * <p>
 * It calls through Spring's {@link RestClient}: {@code RestTemplate}, which it wrapped
 * before, is deprecated for removal in Spring Framework 7.1. The class keeps its name and
 * its methods, so its callers did not change; the urls are expanded and encoded the way
 * {@code RestTemplate} did ({@link EncodingMode#URI_COMPONENT}).
 * <p>
 * The shared bean's calls, and those of the wrappers it derives
 * ({@link #timingOut(Duration, Duration)}), are observed in the application's registry
 * (see {@link GeboHttpClientObservations}).
 */
@Service
public class RestTemplateWrapperService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateWrapperService.class);

	/** Reads a response as it comes, its body a stream (a file download). */
	@FunctionalInterface
	public interface ResponseReader<T> {
		T read(ClientHttpResponse response) throws IOException;
	}

	private final RestClient restClient;
	private final ObservationRegistry observationRegistry;

	/**
	 * A wrapper calling with no timeout, as the shared bean always did, over the JDK's
	 * HttpURLConnection as RestTemplate did (its proxy settings, its redirections). Its
	 * calls are not observed.
	 */
	public RestTemplateWrapperService() {
		this(ObservationRegistry.NOOP);
	}

	/**
	 * The shared bean: as {@link #RestTemplateWrapperService()}, its calls observed in the
	 * application's registry (see {@link GeboHttpClientObservations}).
	 */
	@Autowired
	public RestTemplateWrapperService(ObjectProvider<ObservationRegistry> observationRegistry) {
		this(GeboHttpClientObservations.registryOf(observationRegistry));
	}

	private RestTemplateWrapperService(ObservationRegistry observationRegistry) {
		this(RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory()), observationRegistry);
	}

	/** A wrapper calling through a client built from the builder, urls encoded as RestTemplate did. */
	public RestTemplateWrapperService(RestClient.Builder builder) {
		this(builder, ObservationRegistry.NOOP);
	}

	/**
	 * A wrapper calling through a client built from the builder, urls encoded as
	 * RestTemplate did, its calls observed in the registry.
	 */
	public RestTemplateWrapperService(RestClient.Builder builder, ObservationRegistry observationRegistry) {
		final DefaultUriBuilderFactory uris = new DefaultUriBuilderFactory();
		uris.setEncodingMode(EncodingMode.URI_COMPONENT);
		this.observationRegistry = observationRegistry != null ? observationRegistry : ObservationRegistry.NOOP;
		this.restClient = GeboHttpClientObservations.observed(builder, this.observationRegistry)
				.uriBuilderFactory(uris).build();
	}

	/**
	 * A wrapper of its own whose calls time out: for callers that must not wait forever
	 * on a provider (the shared bean has no timeout, and the other users of it keep that).
	 * Its calls are not observed: a Spring bean derives its own with
	 * {@link #timingOut(Duration, Duration)} from the shared one.
	 *
	 * @param connectTimeout time a call may take to connect
	 * @param readTimeout    time a call may wait for the answer
	 */
	public static RestTemplateWrapperService withTimeouts(Duration connectTimeout, Duration readTimeout) {
		return new RestTemplateWrapperService(RestClient.builder().requestFactory(timingOutFactory(connectTimeout,
				readTimeout)), ObservationRegistry.NOOP);
	}

	/**
	 * A wrapper of its own whose calls time out, observed as this one's are: for callers
	 * that must not wait forever on a provider.
	 *
	 * @param connectTimeout time a call may take to connect
	 * @param readTimeout    time a call may wait for the answer
	 */
	public RestTemplateWrapperService timingOut(Duration connectTimeout, Duration readTimeout) {
		return new RestTemplateWrapperService(
				RestClient.builder().requestFactory(timingOutFactory(connectTimeout, readTimeout)),
				observationRegistry);
	}

	private static SimpleClientHttpRequestFactory timingOutFactory(Duration connectTimeout, Duration readTimeout) {
		final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(connectTimeout);
		requestFactory.setReadTimeout(readTimeout);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("timing out, connect:" + connectTimeout + " read:" + readTimeout);
		}
		return requestFactory;
	}

	/**
	 * Exchanges HTTP requests with specified URL, method, and request entity,
	 * returning a response entity of the specified type.
	 *
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
			Class<T> responseType) throws GeboRestIntegrationException {
		return this.exchange(url, method, requestEntity, responseType, new Object[0]);
	}

	/**
	 * Exchanges HTTP requests and returns the response body, null when there is none.
	 *
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> T exchangeAndReturn(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
			Class<T> responseType) throws GeboRestIntegrationException {
		ResponseEntity<T> returned = exchange(url, method, requestEntity, responseType);
		return returned.hasBody() ? returned.getBody() : null;
	}

	/**
	 * Throws the exception of an error status: 5xx {@link GeboRemoteBackendErrorException},
	 * 404 {@link GeboNotFoundException}, 401-403 {@link GeboInvalidAccessException}, any
	 * other error {@link GeboRestIntegrationException}.
	 */
	private static void handleError(HttpStatusCode statusCode) throws GeboRestIntegrationException {
		if (statusCode.is5xxServerError()) {
			throw new GeboRemoteBackendErrorException(statusCode.toString());
		}
		if (statusCode.value() == 404)
			throw new GeboNotFoundException(statusCode.toString());
		if (statusCode.value() >= 401 && statusCode.value() <= 403) {
			throw new GeboInvalidAccessException(statusCode.toString());
		}
		if (statusCode.isError())
			throw new GeboRestIntegrationException(statusCode.toString());

	}

	/**
	 * Converts an HTTP client error into a user-friendly message based on the status code.
	 */
	public static GUserMessage toMessage(HttpClientErrorException exc, String serviceName, String content) {
		HttpStatusCode statusCode = exc.getStatusCode();
		if (statusCode.is5xxServerError()) {
			return GUserMessage.errorMessage("Error comunicating with " + serviceName + " accessing content " + content,
					"The remote system seems to be down or having troubles\n" + toString(exc));
		}
		if (statusCode.value() == 404) {
			return GUserMessage.errorMessage(
					"The resource " + content + " on service: " + serviceName + " does not exist",
					"The remote system resource " + content + " does not exist\n" + toString(exc));
		}
		if (statusCode.value() >= 401 && statusCode.value() <= 403) {
			return GUserMessage.errorMessage(
					"The resource " + content + " on service: " + serviceName
							+ " seems to be unaccessible for the given credentials",
					"Inserted credentials maybe wrong or incompatible with the acessed content\n" + toString(exc));
		}

		return GUserMessage.errorMessage("Error comunicating with " + serviceName,
				"Netork error comunicating to the remote system accessing content\n" + content + toString(exc));

	}

	/**
	 * Converts a Gebo REST integration exception into a user-friendly message.
	 */
	public static GUserMessage toMessage(GeboRestIntegrationException exc, String serviceName, String content) {
		if (exc != null) {
			if (exc instanceof GeboRemoteBackendErrorException) {
				return GUserMessage.errorMessage(
						"Error comunicating with " + serviceName + " accessing content " + content,
						"The remote system seems to be down or having troubles\n" + toString(exc));
			}
			if (exc instanceof GeboNotFoundException) {
				return GUserMessage.errorMessage(
						"The resource " + content + " on service: " + serviceName + " does not exist",
						"The remote system resource " + content + " does not exist\n" + toString(exc));
			}
			if (exc instanceof GeboInvalidAccessException) {
				return GUserMessage.errorMessage(
						"The resource " + content + " on service: " + serviceName
								+ " seems to be unaccessible for the given credentials",
						"Inserted credentials maybe wrong or incompatible with the acessed content\n" + toString(exc));
			}
		}
		return GUserMessage.errorMessage("Error comunicating with " + serviceName,
				"Netork error comunicating to the remote system accessing content\n" + content + toString(exc));
	}

	private static String toString(Throwable exc) {
		return exc.getMessage();
	}

	/** The headers and the body of a request object, an HttpEntity or a plain body. */
	private static RestClient.RequestBodySpec withRequest(RestClient.RequestBodySpec spec, @Nullable Object request) {
		if (request instanceof HttpEntity<?> entity) {
			spec.headers(headers -> headers.addAll(entity.getHeaders()));
			if (entity.getBody() != null) {
				spec.body(entity.getBody());
			}
		} else if (request != null) {
			spec.body(request);
		}
		return spec;
	}

	/**
	 * Runs a call, its error status turned into Gebo's exceptions: a 2xx entity is
	 * returned, any other status thrown as its exception.
	 */
	private static <T> ResponseEntity<T> call(String what, Supplier<ResponseEntity<T>> call)
			throws GeboRestIntegrationException {
		final ResponseEntity<T> value;
		try {
			value = call.get();
		} catch (HttpStatusCodeException exc) {
			LOGGER.error("Error calling " + what, exc);
			handleError(exc.getStatusCode());
			throw new GeboRestIntegrationException("Exception accessing => " + what, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error calling " + what, exc);
			throw new GeboRestIntegrationException("Error working with url:" + what, exc);
		}
		if (!value.getStatusCode().is2xxSuccessful()) {
			handleError(value.getStatusCode());
			throw new GeboRestIntegrationException(what + " answered " + value.getStatusCode());
		}
		return value;
	}

	/**
	 * Exchanges HTTP requests with specified URL, method, request entity and url
	 * variables; a redirection is followed.
	 *
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
			Class<T> responseType, Object... uriVariables) throws GeboRestIntegrationException {
		final ResponseEntity<T> value;
		try {
			value = withRequest(restClient.method(method).uri(url, uriVariables), requestEntity).retrieve()
					.toEntity(responseType);
		} catch (HttpStatusCodeException exc) {
			LOGGER.error("Error in exchange(" + url + ",...)", exc);
			handleError(exc.getStatusCode());
			throw new GeboRestIntegrationException("Exception accessing => " + url, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in exchange(" + url + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + url, exc);
		}
		if (value.getStatusCode().is2xxSuccessful()) {
			return value;
		}
		if (value.getStatusCode().is3xxRedirection()) {
			List<String> redirection = value.getHeaders().getValuesAsList(HttpHeaders.LOCATION);
			if (redirection != null && !redirection.isEmpty()) {
				return this.exchange(redirection.get(0), method, requestEntity, responseType, uriVariables);
			}
		}
		handleError(value.getStatusCode());
		throw new GeboRestIntegrationException(value.getStatusCode().toString());
	}

	/**
	 * Calls the url and reads the response as it comes (its body a stream, for a file
	 * download); an error status is thrown as its exception before anything is read.
	 *
	 * @param headers the headers of the request, null for none
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> T read(URI uri, HttpMethod method, @Nullable HttpHeaders headers, ResponseReader<T> reader)
			throws GeboRestIntegrationException {
		try {
			return restClient.method(method).uri(uri).headers(h -> {
				if (headers != null) {
					h.addAll(headers);
				}
			}).exchange((request, response) -> {
				if (response.getStatusCode().isError()) {
					throw statusException(response);
				}
				return reader.read(response);
			});
		} catch (HttpStatusCodeException exc) {
			LOGGER.error("Error in read(" + uri + ",...)", exc);
			handleError(exc.getStatusCode());
			throw new GeboRestIntegrationException("Exception accessing => " + uri, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in read(" + uri + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + uri, exc);
		}
	}

	/** The same, the url a string. */
	public <T> T read(String url, HttpMethod method, @Nullable HttpHeaders headers, ResponseReader<T> reader)
			throws GeboRestIntegrationException {
		try {
			return read(new URI(url), method, headers, reader);
		} catch (URISyntaxException e) {
			LOGGER.error("Error in read(" + url + ",...)", e);
			throw new GeboRestIntegrationException("url=>" + url + " is not a valid URI", e);
		}
	}

	private static HttpStatusCodeException statusException(ClientHttpResponse response) throws IOException {
		final byte[] body = StreamUtils.copyToByteArray(response.getBody());
		return response.getStatusCode().is5xxServerError()
				? HttpServerErrorException.create(response.getStatusCode(), response.getStatusText(),
						response.getHeaders(), body, StandardCharsets.UTF_8)
				: HttpClientErrorException.create(response.getStatusCode(), response.getStatusText(),
						response.getHeaders(), body, StandardCharsets.UTF_8);
	}

	/**
	 * Posts the request (an HttpEntity, its headers sent too, or a plain body) to the uri.
	 *
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> postForEntity(URI uri, Object requestObject, Class<T> responseType)
			throws GeboRestIntegrationException {
		return call("postForEntity(" + uri + ")",
				() -> withRequest(restClient.post().uri(uri), requestObject).retrieve().toEntity(responseType));
	}

	/** The same, the url a template expanded with the variables. */
	public <T> ResponseEntity<T> postForEntity(String uri, Object requestObject, Class<T> responseType,
			Map<String, ?> uriVariables) throws GeboRestIntegrationException {
		return call("postForEntity(" + uri + ")", () -> withRequest(restClient.post().uri(uri, uriVariables),
				requestObject).retrieve().toEntity(responseType));
	}

	/** The same, the url a template expanded with the variables. */
	public <T> ResponseEntity<T> postForEntity(String uri, Object requestObject, Class<T> responseType,
			Object... uriVariables) throws GeboRestIntegrationException {
		return call("postForEntity(" + uri + ")", () -> withRequest(restClient.post().uri(uri, uriVariables),
				requestObject).retrieve().toEntity(responseType));
	}

	/**
	 * Gets the url, a template expanded with the variables.
	 *
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables)
			throws GeboRestIntegrationException {
		return call("getForEntity(" + url + ")",
				() -> restClient.get().uri(url, uriVariables).retrieve().toEntity(responseType));
	}

	/** The same, the variables in order. */
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)
			throws GeboRestIntegrationException {
		return call("getForEntity(" + url + ")",
				() -> restClient.get().uri(url, uriVariables).retrieve().toEntity(responseType));
	}

	/** The same, the uri as it is. */
	public <T> ResponseEntity<T> getForEntity(URI uri, Class<T> responseType) throws GeboRestIntegrationException {
		return call("getForEntity(" + uri + ")", () -> restClient.get().uri(uri).retrieve().toEntity(responseType));
	}

	/**
	 * Exchanges a request entity (method, url, headers, body), the response read as the
	 * given type.
	 *
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> exchange(RequestEntity<Object> requestEntity, ParameterizedTypeReference<T> returnType)
			throws GeboRestIntegrationException {
		return call("exchange(" + requestEntity.getUrl() + ")",
				() -> withRequest(restClient.method(requestEntity.getMethod()).uri(requestEntity.getUrl()),
						requestEntity).retrieve().toEntity(returnType));
	}

	public static String urlConcat(String... partialAddresses) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < partialAddresses.length; i++) {
			String current = partialAddresses[i];
			if (current.startsWith("/")) {
				if (current.length() > 1)
					current = current.substring(1);
				else
					current = "";
			}
			if (i < (partialAddresses.length - 1)) {
				if (current.endsWith("/")) {
					current = current.substring(0, current.length() - 1);
				}
			}
			sb.append(current);
			if (i < (partialAddresses.length - 1)) {
				sb.append("/");
			}
		}
		return sb.toString();
	}
}
