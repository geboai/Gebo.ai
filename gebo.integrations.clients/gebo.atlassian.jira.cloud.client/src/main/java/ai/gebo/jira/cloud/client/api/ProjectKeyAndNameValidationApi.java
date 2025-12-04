/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * 
 * This class provides API endpoints for validating and retrieving valid project keys and names in Jira Cloud.
 * It communicates with the Jira REST API to check if keys and names are valid and available for use.
 */
package ai.gebo.jira.cloud.client.api;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.ErrorCollection;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.ProjectKeyAndNameValidationApi")
public class ProjectKeyAndNameValidationApi {
	/**
	 * The API client instance used to make HTTP requests to the Jira API
	 */
	private ApiClient apiClient;

	/**
	 * Constructor that initializes a new instance with the provided API client
	 * 
	 * @param apiClient The API client to use for making requests
	 */
	public ProjectKeyAndNameValidationApi(ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Gets the API client currently being used by this instance
	 * 
	 * @return The current API client
	 */
	public ApiClient getApiClient() {
		return apiClient;
	}

	/**
	 * Sets the API client to be used by this instance
	 * 
	 * @param apiClient The API client to use
	 */
	public void setApiClient(ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Get valid project key Validates a project key and, if the key is invalid or
	 * in use, generates a valid random string for the project key.
	 * **[Permissions](#permissions) required:** None.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * 
	 * @param key The project key. (optional)
	 * @return String
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public String getValidProjectKey(String key) throws RestClientException {
		return getValidProjectKeyWithHttpInfo(key).getBody();
	}

	/**
	 * Get valid project key Validates a project key and, if the key is invalid or
	 * in use, generates a valid random string for the project key.
	 * **[Permissions](#permissions) required:** None.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * 
	 * @param key The project key. (optional)
	 * @return ResponseEntity&lt;String&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<String> getValidProjectKeyWithHttpInfo(String key) throws RestClientException {
		Object postBody = null;
		String path = UriComponentsBuilder.fromPath("/rest/api/3/projectvalidate/validProjectKey").build()
				.toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "key", key));

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = new String[] { "basicAuth" };

		ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

	/**
	 * Get valid project name Checks that a project name isn&#x27;t in use. If the
	 * name isn&#x27;t in use, the passed string is returned. If the name is in use,
	 * this operation attempts to generate a valid project name based on the one
	 * supplied, usually by adding a sequence number. If a valid project name cannot
	 * be generated, a 404 response is returned. **[Permissions](#permissions)
	 * required:** None.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>400</b> - Returned if the request is invalid.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * <p>
	 * <b>404</b> - Returned if a valid project name cannot be generated.
	 * 
	 * @param name The project name. (required)
	 * @return String
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public String getValidProjectName(String name) throws RestClientException {
		return getValidProjectNameWithHttpInfo(name).getBody();
	}

	/**
	 * Get valid project name Checks that a project name isn&#x27;t in use. If the
	 * name isn&#x27;t in use, the passed string is returned. If the name is in use,
	 * this operation attempts to generate a valid project name based on the one
	 * supplied, usually by adding a sequence number. If a valid project name cannot
	 * be generated, a 404 response is returned. **[Permissions](#permissions)
	 * required:** None.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>400</b> - Returned if the request is invalid.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * <p>
	 * <b>404</b> - Returned if a valid project name cannot be generated.
	 * 
	 * @param name The project name. (required)
	 * @return ResponseEntity&lt;String&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<String> getValidProjectNameWithHttpInfo(String name) throws RestClientException {
		Object postBody = null;
		// verify the required parameter 'name' is set
		if (name == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"Missing the required parameter 'name' when calling getValidProjectName");
		}
		String path = UriComponentsBuilder.fromPath("/rest/api/3/projectvalidate/validProjectName").build()
				.toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = new String[] { "basicAuth" };

		ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

	/**
	 * Validate project key Validates a project key by confirming the key is a valid
	 * string and not in use. **[Permissions](#permissions) required:** None.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * 
	 * @param key The project key. (optional)
	 * @return ErrorCollection
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ErrorCollection validateProjectKey(String key) throws RestClientException {
		return validateProjectKeyWithHttpInfo(key).getBody();
	}

	/**
	 * Validate project key Validates a project key by confirming the key is a valid
	 * string and not in use. **[Permissions](#permissions) required:** None.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * 
	 * @param key The project key. (optional)
	 * @return ResponseEntity&lt;ErrorCollection&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<ErrorCollection> validateProjectKeyWithHttpInfo(String key) throws RestClientException {
		Object postBody = null;
		String path = UriComponentsBuilder.fromPath("/rest/api/3/projectvalidate/key").build().toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "key", key));

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames =ApiClient.AUTH_NAMES;

		ParameterizedTypeReference<ErrorCollection> returnType = new ParameterizedTypeReference<ErrorCollection>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}
}