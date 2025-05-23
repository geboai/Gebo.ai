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

import ai.gebo.jira.cloud.client.model.ServiceRegistry;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

/**
 * API client for interacting with Jira Cloud's Service Registry endpoints.
 * AI generated comments
 * This class facilitates the retrieval of service registry attributes
 * through REST API calls to Jira's service registry endpoint.
 */
public class ServiceRegistryApi {
    /**
     * The API client used for making HTTP requests to Jira Cloud
     */
    private ApiClient apiClient;

    /**
     * Constructs a new ServiceRegistryApi with the specified API client
     * 
     * @param apiClient The client used to handle HTTP communications with Jira
     */
    public ServiceRegistryApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the API client currently used by this instance
     * 
     * @return The current API client
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to be used by this instance
     * 
     * @param apiClient The API client to use for requests
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve the attributes of service registries
     * Retrieve the attributes of given service registries.  **[Permissions](#permissions) required:** Only Connect apps can make this request and the servicesIds belong to the tenant you are requesting
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - The request needs to be authenticated.
     * <p><b>403</b> - The request isn&#x27;t authorized.
     * <p><b>500</b> - The endpoint failed internally.
     * <p><b>501</b> - The endpoint isn&#x27;t ready for receiving requests.
     * <p><b>504</b> - The upstream service is busy.
     * @param serviceIds The ID of the services (the strings starting with \&quot;b:\&quot; need to be decoded in Base64). (required)
     * @return List&lt;ServiceRegistry&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ServiceRegistry> serviceRegistryResourceServicesGet(List<String> serviceIds) throws RestClientException {
        return serviceRegistryResourceServicesGetWithHttpInfo(serviceIds).getBody();
    }

    /**
     * Retrieve the attributes of service registries
     * Retrieve the attributes of given service registries.  **[Permissions](#permissions) required:** Only Connect apps can make this request and the servicesIds belong to the tenant you are requesting
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - The request needs to be authenticated.
     * <p><b>403</b> - The request isn&#x27;t authorized.
     * <p><b>500</b> - The endpoint failed internally.
     * <p><b>501</b> - The endpoint isn&#x27;t ready for receiving requests.
     * <p><b>504</b> - The upstream service is busy.
     * @param serviceIds The ID of the services (the strings starting with \&quot;b:\&quot; need to be decoded in Base64). (required)
     * @return ResponseEntity&lt;List&lt;ServiceRegistry&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ServiceRegistry>> serviceRegistryResourceServicesGetWithHttpInfo(List<String> serviceIds) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'serviceIds' is set
        if (serviceIds == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'serviceIds' when calling serviceRegistryResourceServicesGet");
        }
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/service-registry").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "serviceIds", serviceIds));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<ServiceRegistry>> returnType = new ParameterizedTypeReference<List<ServiceRegistry>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}