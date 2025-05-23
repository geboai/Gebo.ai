/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.restintegration.abstraction.layer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import ai.gebo.model.GUserMessage;

/**
 * AI generated comments
 * Service class that wraps the functionality of RestTemplate for HTTP requests.
 */
@Service
public class RestTemplateWrapperService {
    // Logger for this class
	private static Logger LOGGER = LoggerFactory.getLogger(RestTemplateWrapperService.class);
	
    // RestTemplate instance to perform HTTP requests
	RestTemplate restTemplate = new RestTemplate();

	/**
	 * Default constructor for RestTemplateWrapperService
	 */
	public RestTemplateWrapperService() {

	}

	/**
	 * Exchanges HTTP requests with specified URL, method, and request entity,
	 * returning a response entity of the specified type.
	 * 
	 * @param url           URL to send the request to
	 * @param method        HTTP method to use
	 * @param requestEntity HTTP entity with the request data
	 * @param responseType  Class type of the response expected
	 * @param <T>           Type of the response body
	 * @return ResponseEntity of the given response type
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
			Class<T> responseType) throws GeboRestIntegrationException {
		return this.exchange(url, method, requestEntity, responseType, new Object[0]);
	}

	/**
	 * Exchanges HTTP requests and returns the response body as an object.
	 * 
	 * @param url           URL to send the request to
	 * @param method        HTTP method to use
	 * @param requestEntity HTTP entity with the request data
	 * @param responseType  Class type of the response expected
	 * @param <T>           Type of the response body
	 * @return Response body as an object or null if there's no body
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> T exchangeAndReturn(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
			Class<T> responseType) throws GeboRestIntegrationException {
		ResponseEntity<T> returned = exchange(url, method, requestEntity, responseType);
		return returned.hasBody() ? returned.getBody() : null;
	}

	/**
	 * Handles HTTP error responses by throwing appropriate exceptions based on the
	 * status code.
	 * 
	 * @param statusCode HTTP status code of the error response
	 * @throws GeboRestIntegrationException when an error status code is encountered
	 */
	private void handleError(HttpStatusCode statusCode) throws GeboRestIntegrationException {
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
	 * Converts HttpClientErrorException into GUserMessage to provide user-friendly
	 * error messages.
	 * 
	 * @param exc        Exception thrown during HTTP client operation
	 * @param serviceName Name of the service being accessed
	 * @param content     Content or resource being accessed
	 * @return GUserMessage with error details
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
	 * Exchanges HTTP requests with specified URL, method, request entity, and
	 * variables, returning a response entity of the specified type.
	 * 
	 * @param url          URL to send the request to
	 * @param method       HTTP method to use
	 * @param requestEntity HTTP entity with the request data
	 * @param responseType Class type of the response expected
	 * @param uriVariables Variables for URI template
	 * @param <T>          Type of the response body
	 * @return ResponseEntity of the given response type
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
			Class<T> responseType, Object... uriVariables) throws GeboRestIntegrationException {
		try {
			ResponseEntity<T> value = restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);

			if (value.getStatusCode().is2xxSuccessful()) {
				return value;
			} else {
				if (value.getStatusCode().is3xxRedirection()) {
					List<String> redirection = value.getHeaders().getValuesAsList("Location");
					if (redirection != null && !redirection.isEmpty()) {
						return this.exchange(redirection.get(0), method, requestEntity, responseType, uriVariables);
					}
				}

				handleError(value.getStatusCode());
				throw new GeboRestIntegrationException(value.getStatusCode().toString());
			}
		} catch (HttpClientErrorException exc) {
			LOGGER.error("Error in exchange(" + url + ",...)", exc);
			if (exc.getStatusCode() != null) {
				handleError(exc.getStatusCode());
			}
			throw new GeboRestIntegrationException("Exception accessing => " + url, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in exchange(" + url + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + url, exc);
		}
	}

	/**
	 * Converts GeboRestIntegrationException into GUserMessage to provide
	 * user-friendly error messages.
	 * 
	 * @param exc         Exception thrown during HTTP client operation
	 * @param serviceName Name of the service being accessed
	 * @param content     Content or resource being accessed
	 * @return GUserMessage with error details
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

	/**
	 * Converts exception to a string message.
	 * 
	 * @param exc Exception to be converted
	 * @return String message from the exception
	 */
	private static String toString(Throwable exc) {
		
		return exc.getMessage(); 
	}

	/**
	 * Executes a general HTTP request using the RestTemplate, by providing URI,
	 * method, request callback, and response extractor.
	 * 
	 * @param uri        URI to send the request to
	 * @param method     HTTP method to use
	 * @param callback   Callback to prepare the request
	 * @param extractor  Extractor to process the response
	 * @param <T>        Type of the response body
	 * @return The processed response
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> T execute(URI uri, HttpMethod method, RequestCallback callback, ResponseExtractor<T> extractor)
			throws GeboRestIntegrationException {
		try {
			return restTemplate.execute(uri, method, callback, extractor);
		} catch (HttpClientErrorException exc) {
			LOGGER.error("Error in execute(" + uri + ",...)", exc);
			if (exc.getStatusCode() != null) {
				handleError(exc.getStatusCode());
			}
			throw new GeboRestIntegrationException("Exception accessing => " + uri, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in execute(" + uri + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + uri, exc);
		}

	}

	/**
	 * Executes a general HTTP request using the RestTemplate by providing URL,
	 * method, request callback, and response extractor.
	 * 
	 * @param url        URL to send the request to
	 * @param method     HTTP method to use
	 * @param callback   Callback to prepare the request
	 * @param extractor  Extractor to process the response
	 * @param <T>        Type of the response body
	 * @return The processed response
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> T execute(String url, HttpMethod method, RequestCallback callback, ResponseExtractor<T> extractor)
			throws GeboRestIntegrationException {

		try {
			return execute(new URI(url), method, callback, extractor);
		} catch (URISyntaxException e) {
			LOGGER.error("Error in execute(" + url + ",...)", e);
			throw new GeboRestIntegrationException("url=>" + url + " is not a valid URI", e);
		}
	}

	/**
	 * Processes HTTP POST requests and returns response entity for given URI,
	 * request object, and response type.
	 * 
	 * @param uri           URI to send the POST request to
	 * @param requestObject Object containing request data
	 * @param responseType  Class type of the response expected
	 * @param <T>           Type of the response body
	 * @return ResponseEntity of the given response type
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> postForEntity(URI uri, Object requestObject, Class<T> responseType)
			throws GeboRestIntegrationException {
		try {
			ResponseEntity<T> retValue = restTemplate.postForEntity(uri, requestObject, responseType);
			if (retValue.getStatusCode().is2xxSuccessful())
				return retValue;
			else
				handleError(retValue.getStatusCode());
			return null;
		} catch (HttpClientErrorException exc) {
			LOGGER.error("Error in postForEntity(" + uri + ",...)", exc);
			if (exc.getStatusCode() != null) {
				handleError(exc.getStatusCode());
			}
			throw new GeboRestIntegrationException("Exception accessing => " + uri, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in postForEntity(" + uri + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + uri, exc);
		}
	}

	/**
	 * Processes HTTP POST requests and returns response entity for given URL,
	 * request object, and response type with URI variables.
	 * 
	 * @param uri           URL to send the POST request to
	 * @param requestObject Object containing request data
	 * @param responseType  Class type of the response expected
	 * @param uriVariables  Variables for URI template
	 * @param <T>           Type of the response body
	 * @return ResponseEntity of the given response type
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> postForEntity(String uri, Object requestObject, Class<T> responseType,
			Map<String, ?> uriVariables) throws GeboRestIntegrationException {
		try {
			ResponseEntity<T> retValue = restTemplate.postForEntity(uri, requestObject, responseType, uriVariables);
			if (retValue.getStatusCode().is2xxSuccessful())
				return retValue;
			else
				handleError(retValue.getStatusCode());
			return null;
		} catch (HttpClientErrorException exc) {
			LOGGER.error("Error in postForEntity(" + uri + ",...)", exc);
			if (exc.getStatusCode() != null) {
				handleError(exc.getStatusCode());
			}
			throw new GeboRestIntegrationException("Exception accessing => " + uri, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in postForEntity(" + uri + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + uri, exc);
		}
	}

	/**
	 * Processes HTTP POST requests and returns response entity for given URL,
	 * request object, and response type with URI variables.
	 * 
	 * @param uri           URL to send the POST request to
	 * @param requestObject Object containing request data
	 * @param responseType  Class type of the response expected
	 * @param uriVariables  Variables for URI template
	 * @param <T>           Type of the response body
	 * @return ResponseEntity of the given response type
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> postForEntity(String uri, Object requestObject, Class<T> responseType,
			Object... uriVariables) throws GeboRestIntegrationException {
		try {
			ResponseEntity<T> retValue = restTemplate.postForEntity(uri, requestObject, responseType, uriVariables);
			if (retValue.getStatusCode().is2xxSuccessful())
				return retValue;
			else
				handleError(retValue.getStatusCode());
			return null;
		} catch (HttpClientErrorException exc) {
			LOGGER.error("Error in postForEntity(" + uri + ",...)", exc);
			if (exc.getStatusCode() != null) {
				handleError(exc.getStatusCode());
			}
			throw new GeboRestIntegrationException("Exception accessing => " + uri, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in postForEntity(" + uri + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + uri, exc);
		}
	}

	/**
	 * Processes HTTP GET requests and returns response entity for given URL and
	 * response type with URI variables.
	 * 
	 * @param url          URL to send the GET request to
	 * @param responseType Class type of the response expected
	 * @param uriVariables Variables for URI template
	 * @param <T>          Type of the response body
	 * @return ResponseEntity of the given response type
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables)
			throws GeboRestIntegrationException {
		try {
			ResponseEntity<T> retValue = restTemplate.getForEntity(url, responseType, uriVariables);
			if (retValue.getStatusCode().is2xxSuccessful())
				return retValue;
			else
				handleError(retValue.getStatusCode());
			return null;
		} catch (HttpClientErrorException exc) {
			LOGGER.error("Error in getForEntity(" + url + ",...)", exc);
			if (exc.getStatusCode() != null) {
				handleError(exc.getStatusCode());
			}
			throw new GeboRestIntegrationException("Exception accessing => " + url, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in getForEntity(" + url + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + url, exc);
		}
	}

	/**
	 * Processes HTTP GET requests and returns response entity for given URL and
	 * response type with URI variables.
	 * 
	 * @param url          URL to send the GET request to
	 * @param responseType Class type of the response expected
	 * @param uriVariables Variables for URI template
	 * @param <T>          Type of the response body
	 * @return ResponseEntity of the given response type
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)
			throws GeboRestIntegrationException {
		try {
			ResponseEntity<T> retValue = restTemplate.getForEntity(url, responseType, uriVariables);
			if (retValue.getStatusCode().is2xxSuccessful())
				return retValue;
			else
				handleError(retValue.getStatusCode());
			return null;
		} catch (HttpClientErrorException exc) {
			LOGGER.error("Error in getForEntity(" + url + ",...)", exc);
			if (exc.getStatusCode() != null) {
				handleError(exc.getStatusCode());
			}
			throw new GeboRestIntegrationException("Exception accessing => " + url, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in getForEntity(" + url + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + url, exc);
		}
	}

	/**
	 * Processes HTTP GET requests and returns response entity for given URI and
	 * response type.
	 * 
	 * @param uri          URI to send the GET request to
	 * @param responseType Class type of the response expected
	 * @param <T>          Type of the response body
	 * @return ResponseEntity of the given response type
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> getForEntity(URI uri, Class<T> responseType) throws GeboRestIntegrationException {
		try {
			ResponseEntity<T> retValue = restTemplate.getForEntity(uri, responseType);
			if (retValue.getStatusCode().is2xxSuccessful())
				return retValue;
			else
				handleError(retValue.getStatusCode());
			return null;
		} catch (HttpClientErrorException exc) {
			LOGGER.error("Error in getForEntity(" + uri + ",...)", exc);
			if (exc.getStatusCode() != null) {
				handleError(exc.getStatusCode());
			}
			throw new GeboRestIntegrationException("Exception accessing => " + uri, exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in getForEntity(" + uri + ",...)", exc);
			throw new GeboRestIntegrationException("Error working with url:" + uri, exc);
		}
	}

	/**
	 * Sets a custom ClientHttpRequestFactory to the rest template.
	 * 
	 * @param bufferingClientHttpRequestFactory Request factory to use
	 */
	public void setRequestFactory(ClientHttpRequestFactory bufferingClientHttpRequestFactory) {
		restTemplate.setRequestFactory(bufferingClientHttpRequestFactory);

	}

	/**
	 * Retrieves the currently configured ClientHttpRequestFactory.
	 * 
	 * @return Current ClientHttpRequestFactory used
	 */
	public ClientHttpRequestFactory getRequestFactory() {

		return restTemplate.getRequestFactory();
	}

	/**
	 * Sets a list of ClientHttpRequestInterceptors to the rest template.
	 * 
	 * @param currentInterceptors List of interceptors
	 */
	public void setInterceptors(List<ClientHttpRequestInterceptor> currentInterceptors) {
		restTemplate.setInterceptors(currentInterceptors);

	}

	/**
	 * Retrieves the list of ClientHttpRequestInterceptors currently associated with
	 * the rest template.
	 * 
	 * @return List of current interceptors
	 */
	public List<ClientHttpRequestInterceptor> getInterceptors() {
		return restTemplate.getInterceptors();
	}

	/**
	 * Exchanges HTTP requests with specified RequestEntity and ParameterizedTypeReference,
	 * returning a response entity of the specified type.
	 * 
	 * @param requestEntity Request entity to send
	 * @param returnType    Expected return type with parameterized type reference
	 * @param <T>           Type of the response body
	 * @return ResponseEntity of the given response type
	 * @throws GeboRestIntegrationException when HTTP request fails
	 */
	public <T> ResponseEntity<T> exchange(RequestEntity<Object> requestEntity, ParameterizedTypeReference<T> returnType)
			throws GeboRestIntegrationException {
		try {
			return restTemplate.exchange(requestEntity, returnType);
		} catch (HttpClientErrorException exc) {
			LOGGER.error("Error in exchange(...)", exc);
			if (exc.getStatusCode() != null) {
				handleError(exc.getStatusCode());
			}
			throw new GeboRestIntegrationException("Error in exchange(...)", exc);
		} catch (Throwable exc) {
			LOGGER.error("Error in exchange(...)", exc);
			throw new GeboRestIntegrationException("Error in exchange(...)", exc);
		}
	}
}