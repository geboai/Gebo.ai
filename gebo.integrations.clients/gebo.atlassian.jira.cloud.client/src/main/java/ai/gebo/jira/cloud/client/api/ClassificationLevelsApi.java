/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.api;

import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.DataClassificationLevelsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * API class for managing classification levels in Jira Cloud.
 * It provides methods to fetch classification levels via REST API calls.
 * AI generated comments
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
public class ClassificationLevelsApi {
    
    // ApiClient instance used to perform the API calls
    private ApiClient apiClient;

    /**
     * Default constructor initializes the API client with default settings.
     */
    public ClassificationLevelsApi() {
        this(new ApiClient());
    }

    /**
     * Constructor allowing injection of a custom ApiClient instance.
     * 
     * @param apiClient ApiClient instance to be used for API interactions.
     */
    public ClassificationLevelsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Getter for the ApiClient instance.
     * 
     * @return the current ApiClient instance.
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Setter for the ApiClient instance.
     * 
     * @param apiClient the ApiClient instance to be set.
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get all classification levels.
     * Returns all classification levels. **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * 
     * @param status Optional set of statuses to filter by. (optional)
     * @param orderBy Ordering of the results by a given field. If not provided, values will not be sorted. (optional)
     * @return DataClassificationLevelsBean containing the classification levels data.
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DataClassificationLevelsBean getAllUserDataClassificationLevels(List<String> status, String orderBy) throws RestClientException {
        return getAllUserDataClassificationLevelsWithHttpInfo(status, orderBy).getBody();
    }

    /**
     * Get all classification levels with HTTP information.
     * This method provides the complete HTTP response information including headers and status codes.
     * Returns all classification levels. **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * 
     * @param status Optional set of statuses to filter by. (optional)
     * @param orderBy Ordering of the results by a given field. If not provided, values will not be sorted. (optional)
     * @return ResponseEntity&lt;DataClassificationLevelsBean&gt; containing the classification levels data and HTTP response details.
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DataClassificationLevelsBean> getAllUserDataClassificationLevelsWithHttpInfo(List<String> status, String orderBy) throws RestClientException {
        Object postBody = null; // No body content required for GET request
        String path = UriComponentsBuilder.fromPath("/rest/api/3/classification-levels").build().toUriString(); // API endpoint path
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        // Convert status and orderBy parameters to query parameters
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "status", status));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "orderBy", orderBy));

        final String[] accepts = { "application/json" }; // Accepted response content types
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  }; // No content types as it is a GET request
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        // Authentication names used in this request
        String[] authNames = ApiClient.AUTH_NAMES;

        // Type reference for deserializing the response body into
        ParameterizedTypeReference<DataClassificationLevelsBean> returnType = new ParameterizedTypeReference<DataClassificationLevelsBean>() {};
        
        // Make the API call using the ApiClient and return the response entity
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}