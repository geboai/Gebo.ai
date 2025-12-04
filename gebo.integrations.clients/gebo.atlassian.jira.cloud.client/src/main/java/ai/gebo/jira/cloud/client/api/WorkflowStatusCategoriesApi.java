/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ai.gebo.jira.cloud.client.model.StatusCategory;

/**
 * API client for interacting with Jira workflow status categories.
 * This class provides methods to retrieve status categories from Jira Cloud.
 * AI generated comments
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

public class WorkflowStatusCategoriesApi {
    private ApiClient apiClient;

    /**
     * Default constructor that initializes a new API client.
     */
    public WorkflowStatusCategoriesApi() {
        this(new ApiClient());
    }

    /**
     * Constructor with a specified API client.
     * @param apiClient The API client to use for communication with Jira.
     */
    //@Autowired
    public WorkflowStatusCategoriesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the current API client.
     * @return The current API client instance.
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to use for communication.
     * @param apiClient The API client to set.
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get all status categories
     * Returns a list of all status categories.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @return List&lt;StatusCategory&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<StatusCategory> getStatusCategories() throws RestClientException {
        return getStatusCategoriesWithHttpInfo().getBody();
    }

    /**
     * Get all status categories
     * Returns a list of all status categories.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @return ResponseEntity&lt;List&lt;StatusCategory&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<StatusCategory>> getStatusCategoriesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuscategory").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<List<StatusCategory>> returnType = new ParameterizedTypeReference<List<StatusCategory>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get status category
     * Returns a status category. Status categories provided a mechanism for categorizing [statuses](#api-rest-api-3-status-idOrName-get).  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if the status category is not found.
     * @param idOrKey The ID or key of the status category. (required)
     * @return StatusCategory
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StatusCategory getStatusCategory(String idOrKey) throws RestClientException {
        return getStatusCategoryWithHttpInfo(idOrKey).getBody();
    }

    /**
     * Get status category
     * Returns a status category. Status categories provided a mechanism for categorizing [statuses](#api-rest-api-3-status-idOrName-get).  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if the status category is not found.
     * @param idOrKey The ID or key of the status category. (required)
     * @return ResponseEntity&lt;StatusCategory&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StatusCategory> getStatusCategoryWithHttpInfo(String idOrKey) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idOrKey' is set
        if (idOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idOrKey' when calling getStatusCategory");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("idOrKey", idOrKey);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuscategory/{idOrKey}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<StatusCategory> returnType = new ParameterizedTypeReference<StatusCategory>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}