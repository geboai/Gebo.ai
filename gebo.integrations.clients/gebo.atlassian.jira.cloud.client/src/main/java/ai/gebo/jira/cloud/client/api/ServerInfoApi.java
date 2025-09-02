/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.api;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.ServerInformation;

/**
 * API client for accessing Jira server information endpoints.
 * This class provides methods to retrieve information about the Jira instance.
 * AI generated comments
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

public class ServerInfoApi {
    /**
     * The API client used to make HTTP requests
     */
    private ApiClient apiClient;

    /**
     * Constructor for ServerInfoApi that takes an ApiClient.
     * 
     * @param apiClient the API client to use for making HTTP requests
     */
    public ServerInfoApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the API client used by this API.
     * 
     * @return the API client instance
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to be used by this API.
     * 
     * @param apiClient the API client to set
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get Jira instance info
     * Returns information about the Jira instance.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @return ServerInformation
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ServerInformation getServerInfo() throws RestClientException {
        return getServerInfoWithHttpInfo().getBody();
    }

    /**
     * Get Jira instance info
     * Returns information about the Jira instance.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @return ResponseEntity&lt;ServerInformation&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ServerInformation> getServerInfoWithHttpInfo() throws RestClientException {
        Object postBody = null;
        // Build the URL path for the API request
        String path = UriComponentsBuilder.fromPath("/rest/api/3/serverInfo").build().toUriString();
        
        // Initialize request parameters
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        // Set accepted content types
        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        // Use standard authentication names
        String[] authNames =ApiClient.AUTH_NAMES;

        // Define the return type and make the API call
        ParameterizedTypeReference<ServerInformation> returnType = new ParameterizedTypeReference<ServerInformation>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}