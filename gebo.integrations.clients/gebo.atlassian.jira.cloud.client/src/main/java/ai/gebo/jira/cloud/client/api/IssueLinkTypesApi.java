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
 * This class represents the Jira Cloud API client for managing issue link types.
 * It provides methods to create, retrieve, update, and delete issue link types.
 * All operations require that issue linking be enabled on the Jira instance.
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
import ai.gebo.jira.cloud.client.model.IssueLinkType;
import ai.gebo.jira.cloud.client.model.IssueLinkTypes;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.IssueLinkTypesApi")
public class IssueLinkTypesApi {
    /**
     * The API client for making requests
     */
    private ApiClient apiClient;

    /**
     * Default constructor that initializes a new API client
     */
    public IssueLinkTypesApi() {
        this(new ApiClient());
    }

    /**
     * Constructor that accepts a specific API client
     * @param apiClient the client to use for API requests
     */
    //@Autowired
    public IssueLinkTypesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the API client being used by this API
     * @return the current API client
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to be used by this API
     * @param apiClient the API client to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create issue link type
     * Creates an issue link type. Use this operation to create descriptions of the reasons why issues are linked. The issue link type consists of a name and descriptions for a link&#x27;s inward and outward relationships.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  issue linking is disabled.  *  the issue link type name is in use.  *  the user does not have the required permissions.
     * @param body  (required)
     * @return IssueLinkType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IssueLinkType createIssueLinkType(IssueLinkType body) throws RestClientException {
        return createIssueLinkTypeWithHttpInfo(body).getBody();
    }

    /**
     * Create issue link type
     * Creates an issue link type. Use this operation to create descriptions of the reasons why issues are linked. The issue link type consists of a name and descriptions for a link&#x27;s inward and outward relationships.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  issue linking is disabled.  *  the issue link type name is in use.  *  the user does not have the required permissions.
     * @param body  (required)
     * @return ResponseEntity&lt;IssueLinkType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IssueLinkType> createIssueLinkTypeWithHttpInfo(IssueLinkType body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createIssueLinkType");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issueLinkType").build().toUriString();
        
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

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<IssueLinkType> returnType = new ParameterizedTypeReference<IssueLinkType>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete issue link type
     * Deletes an issue link type.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the issue link type ID is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  issue linking is disabled.  *  the issue link type is not found.  *  the user does not have the required permissions.
     * @param issueLinkTypeId The ID of the issue link type. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteIssueLinkType(String issueLinkTypeId) throws RestClientException {
        deleteIssueLinkTypeWithHttpInfo(issueLinkTypeId);
    }

    /**
     * Delete issue link type
     * Deletes an issue link type.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the issue link type ID is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  issue linking is disabled.  *  the issue link type is not found.  *  the user does not have the required permissions.
     * @param issueLinkTypeId The ID of the issue link type. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteIssueLinkTypeWithHttpInfo(String issueLinkTypeId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'issueLinkTypeId' is set
        if (issueLinkTypeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueLinkTypeId' when calling deleteIssueLinkType");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("issueLinkTypeId", issueLinkTypeId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issueLinkType/{issueLinkTypeId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get issue link type
     * Returns an issue link type.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for a project in the site.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the issue link type ID is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  issue linking is disabled.  *  the issue link type is not found.  *  the user does not have the required permissions.
     * @param issueLinkTypeId The ID of the issue link type. (required)
     * @return IssueLinkType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IssueLinkType getIssueLinkType(String issueLinkTypeId) throws RestClientException {
        return getIssueLinkTypeWithHttpInfo(issueLinkTypeId).getBody();
    }

    /**
     * Get issue link type
     * Returns an issue link type.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for a project in the site.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the issue link type ID is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  issue linking is disabled.  *  the issue link type is not found.  *  the user does not have the required permissions.
     * @param issueLinkTypeId The ID of the issue link type. (required)
     * @return ResponseEntity&lt;IssueLinkType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IssueLinkType> getIssueLinkTypeWithHttpInfo(String issueLinkTypeId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'issueLinkTypeId' is set
        if (issueLinkTypeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueLinkTypeId' when calling getIssueLinkType");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("issueLinkTypeId", issueLinkTypeId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issueLinkType/{issueLinkTypeId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<IssueLinkType> returnType = new ParameterizedTypeReference<IssueLinkType>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get issue link types
     * Returns a list of all issue link types.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for a project in the site.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if issue linking is disabled.
     * @return IssueLinkTypes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IssueLinkTypes getIssueLinkTypes() throws RestClientException {
        return getIssueLinkTypesWithHttpInfo().getBody();
    }

    /**
     * Get issue link types
     * Returns a list of all issue link types.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for a project in the site.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if issue linking is disabled.
     * @return ResponseEntity&lt;IssueLinkTypes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IssueLinkTypes> getIssueLinkTypesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issueLinkType").build().toUriString();
        
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

        ParameterizedTypeReference<IssueLinkTypes> returnType = new ParameterizedTypeReference<IssueLinkTypes>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update issue link type
     * Updates an issue link type.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the issue link type ID or the request body are invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  issue linking is disabled.  *  the issue link type is not found.  *  the user does not have the required permissions.
     * @param body  (required)
     * @param issueLinkTypeId The ID of the issue link type. (required)
     * @return IssueLinkType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IssueLinkType updateIssueLinkType(IssueLinkType body, String issueLinkTypeId) throws RestClientException {
        return updateIssueLinkTypeWithHttpInfo(body, issueLinkTypeId).getBody();
    }

    /**
     * Update issue link type
     * Updates an issue link type.  To use this operation, the site must have [issue linking](https://confluence.atlassian.com/x/yoXKM) enabled.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the issue link type ID or the request body are invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  issue linking is disabled.  *  the issue link type is not found.  *  the user does not have the required permissions.
     * @param body  (required)
     * @param issueLinkTypeId The ID of the issue link type. (required)
     * @return ResponseEntity&lt;IssueLinkType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IssueLinkType> updateIssueLinkTypeWithHttpInfo(IssueLinkType body, String issueLinkTypeId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateIssueLinkType");
        }
        // verify the required parameter 'issueLinkTypeId' is set
        if (issueLinkTypeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueLinkTypeId' when calling updateIssueLinkType");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("issueLinkTypeId", issueLinkTypeId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issueLinkType/{issueLinkTypeId}").buildAndExpand(uriVariables).toUriString();
        
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

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<IssueLinkType> returnType = new ParameterizedTypeReference<IssueLinkType>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}