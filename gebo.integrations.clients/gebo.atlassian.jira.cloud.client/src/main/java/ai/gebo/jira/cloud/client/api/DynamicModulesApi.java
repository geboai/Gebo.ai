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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.ConnectModules;

/**
 * API client for interacting with Jira Cloud's Dynamic Modules API.
 * This class provides methods to get, register, and remove dynamic modules for Connect apps.
 * AI generated comments
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.DynamicModulesApi")
public class DynamicModulesApi {
    /**
     * The API client instance used for making HTTP requests
     */
    private ApiClient apiClient;

    /**
     * Default constructor that initializes with a new ApiClient
     */
    public DynamicModulesApi() {
        this(new ApiClient());
    }

    /**
     * Constructor that accepts a custom ApiClient
     * 
     * @param apiClient the client to use for API requests
     */
    //@Autowired
    public DynamicModulesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the current API client
     * 
     * @return the current ApiClient instance
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to use for requests
     * 
     * @param apiClient the client to use for API requests
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get modules
     * Returns all modules registered dynamically by the calling app.  **[Permissions](#permissions) required:** Only Connect apps can make this request.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the call is not from a Connect app.
     * @return ConnectModules
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ConnectModules dynamicModulesResourceGetModulesGet() throws RestClientException {
        return dynamicModulesResourceGetModulesGetWithHttpInfo().getBody();
    }

    /**
     * Get modules
     * Returns all modules registered dynamically by the calling app.  **[Permissions](#permissions) required:** Only Connect apps can make this request.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the call is not from a Connect app.
     * @return ResponseEntity&lt;ConnectModules&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ConnectModules> dynamicModulesResourceGetModulesGetWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/app/module/dynamic").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ConnectModules> returnType = new ParameterizedTypeReference<ConnectModules>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Register modules
     * Registers a list of modules.  **[Permissions](#permissions) required:** Only Connect apps can make this request.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if: * any of the provided modules is invalid. For example, required properties are missing. * any of the modules conflict with registered dynamic modules or modules defined in the app descriptor. For example, there are duplicate keys.  Details of the issues encountered are included in the error message.
     * <p><b>401</b> - Returned if the call is not from a Connect app.
     * @param body  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void dynamicModulesResourceRegisterModulesPost(ConnectModules body) throws RestClientException {
        dynamicModulesResourceRegisterModulesPostWithHttpInfo(body);
    }

    /**
     * Register modules
     * Registers a list of modules.  **[Permissions](#permissions) required:** Only Connect apps can make this request.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if: * any of the provided modules is invalid. For example, required properties are missing. * any of the modules conflict with registered dynamic modules or modules defined in the app descriptor. For example, there are duplicate keys.  Details of the issues encountered are included in the error message.
     * <p><b>401</b> - Returned if the call is not from a Connect app.
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> dynamicModulesResourceRegisterModulesPostWithHttpInfo(ConnectModules body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling dynamicModulesResourceRegisterModulesPost");
        }
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/app/module/dynamic").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Remove modules
     * Remove all or a list of modules registered by the calling app.  **[Permissions](#permissions) required:** Only Connect apps can make this request.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the call is not from a Connect app.
     * @param moduleKey The key of the module to remove. To include multiple module keys, provide multiple copies of this parameter. For example, &#x60;moduleKey&#x3D;dynamic-attachment-entity-property&amp;moduleKey&#x3D;dynamic-select-field&#x60;. Nonexistent keys are ignored. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void dynamicModulesResourceRemoveModulesDelete(List<String> moduleKey) throws RestClientException {
        dynamicModulesResourceRemoveModulesDeleteWithHttpInfo(moduleKey);
    }

    /**
     * Remove modules
     * Remove all or a list of modules registered by the calling app.  **[Permissions](#permissions) required:** Only Connect apps can make this request.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the call is not from a Connect app.
     * @param moduleKey The key of the module to remove. To include multiple module keys, provide multiple copies of this parameter. For example, &#x60;moduleKey&#x3D;dynamic-attachment-entity-property&amp;moduleKey&#x3D;dynamic-select-field&#x60;. Nonexistent keys are ignored. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> dynamicModulesResourceRemoveModulesDeleteWithHttpInfo(List<String> moduleKey) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/app/module/dynamic").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "moduleKey", moduleKey));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}