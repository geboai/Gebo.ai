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
 * This class implements the Priority Schemes API for Jira Cloud.
 * It provides methods to manage priority schemes, priorities, and their associations with projects.
 */
package ai.gebo.jira.cloud.client.api;

import ai.gebo.jira.cloud.client.invoker.ApiClient;

import ai.gebo.jira.cloud.client.model.CreatePrioritySchemeDetails;
import ai.gebo.jira.cloud.client.model.PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects;
import ai.gebo.jira.cloud.client.model.PageBeanPriorityWithSequence;
import ai.gebo.jira.cloud.client.model.PageBeanProject;
import ai.gebo.jira.cloud.client.model.PrioritySchemeId;
import ai.gebo.jira.cloud.client.model.SuggestedMappingsRequestBean;
import ai.gebo.jira.cloud.client.model.UpdatePrioritySchemeRequestBean;
import ai.gebo.jira.cloud.client.model.UpdatePrioritySchemeResponseBean;

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
//@Component("ai.gebo.jira.cloud.client.api.PrioritySchemesApi")
public class PrioritySchemesApi {
    private ApiClient apiClient;

    /**
     * Constructor for PrioritySchemesApi that takes an ApiClient parameter.
     * 
     * @param apiClient The API client to use for making requests
     */
    public PrioritySchemesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the API client instance.
     * 
     * @return The current ApiClient instance
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client instance.
     * 
     * @param apiClient The ApiClient instance to set
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create priority scheme
     * Creates a new priority scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is completed.
     * <p><b>202</b> - Returned if the request is accepted.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.  **Mappings Validation Errors**   *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] require mapping. Please provide mappings in the &#x27;in&#x27; mappings object, where these priorities are the keys with corresponding values.&#x60;&#x60; The listed priority ID(s) have not been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but are required, add them to the mappings object.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permissions.
     * <p><b>409</b> - Returned if an action with this priority scheme is still in progress.
     * @param body  (required)
     * @return PrioritySchemeId
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PrioritySchemeId createPriorityScheme(CreatePrioritySchemeDetails body) throws RestClientException {
        return createPrioritySchemeWithHttpInfo(body).getBody();
    }

    /**
     * Create priority scheme
     * Creates a new priority scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is completed.
     * <p><b>202</b> - Returned if the request is accepted.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.  **Mappings Validation Errors**   *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] require mapping. Please provide mappings in the &#x27;in&#x27; mappings object, where these priorities are the keys with corresponding values.&#x60;&#x60; The listed priority ID(s) have not been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but are required, add them to the mappings object.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permissions.
     * <p><b>409</b> - Returned if an action with this priority scheme is still in progress.
     * @param body  (required)
     * @return ResponseEntity&lt;PrioritySchemeId&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PrioritySchemeId> createPrioritySchemeWithHttpInfo(CreatePrioritySchemeDetails body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createPriorityScheme");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/priorityscheme").build().toUriString();
        
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

        ParameterizedTypeReference<PrioritySchemeId> returnType = new ParameterizedTypeReference<PrioritySchemeId>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete priority scheme
     * Deletes a priority scheme.  This operation is only available for priority schemes without any associated projects. Any associated projects must be removed from the priority scheme before this operation can be performed.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permissions.
     * @param schemeId The priority scheme ID. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object deletePriorityScheme(Long schemeId) throws RestClientException {
        return deletePrioritySchemeWithHttpInfo(schemeId).getBody();
    }

    /**
     * Delete priority scheme
     * Deletes a priority scheme.  This operation is only available for priority schemes without any associated projects. Any associated projects must be removed from the priority scheme before this operation can be performed.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permissions.
     * @param schemeId The priority scheme ID. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deletePrioritySchemeWithHttpInfo(Long schemeId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling deletePriorityScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/priorityscheme/{schemeId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get available priorities by priority scheme
     * Returns a [paginated](#pagination) list of priorities available for adding to a priority scheme.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param schemeId The priority scheme ID. (required)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param query The string to query priorities on by name. (optional)
     * @param exclude A list of priority IDs to exclude from the results. (optional)
     * @return PageBeanPriorityWithSequence
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanPriorityWithSequence getAvailablePrioritiesByPriorityScheme(String schemeId, String startAt, String maxResults, String query, List<String> exclude) throws RestClientException {
        return getAvailablePrioritiesByPrioritySchemeWithHttpInfo(schemeId, startAt, maxResults, query, exclude).getBody();
    }

    /**
     * Get available priorities by priority scheme
     * Returns a [paginated](#pagination) list of priorities available for adding to a priority scheme.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param schemeId The priority scheme ID. (required)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param query The string to query priorities on by name. (optional)
     * @param exclude A list of priority IDs to exclude from the results. (optional)
     * @return ResponseEntity&lt;PageBeanPriorityWithSequence&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanPriorityWithSequence> getAvailablePrioritiesByPrioritySchemeWithHttpInfo(String schemeId, String startAt, String maxResults, String query, List<String> exclude) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling getAvailablePrioritiesByPriorityScheme");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/priorityscheme/priorities/available").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "schemeId", schemeId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "exclude", exclude));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanPriorityWithSequence> returnType = new ParameterizedTypeReference<PageBeanPriorityWithSequence>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get priorities by priority scheme
     * Returns a [paginated](#pagination) list of priorities by scheme.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param schemeId The priority scheme ID. (required)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @return PageBeanPriorityWithSequence
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanPriorityWithSequence getPrioritiesByPriorityScheme(String schemeId, String startAt, String maxResults) throws RestClientException {
        return getPrioritiesByPrioritySchemeWithHttpInfo(schemeId, startAt, maxResults).getBody();
    }

    /**
     * Get priorities by priority scheme
     * Returns a [paginated](#pagination) list of priorities by scheme.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param schemeId The priority scheme ID. (required)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @return ResponseEntity&lt;PageBeanPriorityWithSequence&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanPriorityWithSequence> getPrioritiesByPrioritySchemeWithHttpInfo(String schemeId, String startAt, String maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling getPrioritiesByPriorityScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/priorityscheme/{schemeId}/priorities").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanPriorityWithSequence> returnType = new ParameterizedTypeReference<PageBeanPriorityWithSequence>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get priority schemes
     * Returns a [paginated](#pagination) list of priority schemes.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param priorityId A set of priority IDs to filter by. To include multiple IDs, provide an ampersand-separated list. For example, &#x60;priorityId&#x3D;10000&amp;priorityId&#x3D;10001&#x60;. (optional)
     * @param schemeId A set of priority scheme IDs. To include multiple IDs, provide an ampersand-separated list. For example, &#x60;schemeId&#x3D;10000&amp;schemeId&#x3D;10001&#x60;. (optional)
     * @param schemeName The name of scheme to search for. (optional)
     * @param onlyDefault Whether only the default priority is returned. (optional, default to false)
     * @param orderBy The ordering to return the priority schemes by. (optional, default to +name)
     * @param expand A comma separated list of additional information to return. \&quot;priorities\&quot; will return priorities associated with the priority scheme. \&quot;projects\&quot; will return projects associated with the priority scheme. &#x60;expand&#x3D;priorities,projects&#x60;. (optional)
     * @return PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects getPrioritySchemes(String startAt, String maxResults, List<Long> priorityId, List<Long> schemeId, String schemeName, Boolean onlyDefault, String orderBy, String expand) throws RestClientException {
        return getPrioritySchemesWithHttpInfo(startAt, maxResults, priorityId, schemeId, schemeName, onlyDefault, orderBy, expand).getBody();
    }

    /**
     * Get priority schemes
     * Returns a [paginated](#pagination) list of priority schemes.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param priorityId A set of priority IDs to filter by. To include multiple IDs, provide an ampersand-separated list. For example, &#x60;priorityId&#x3D;10000&amp;priorityId&#x3D;10001&#x60;. (optional)
     * @param schemeId A set of priority scheme IDs. To include multiple IDs, provide an ampersand-separated list. For example, &#x60;schemeId&#x3D;10000&amp;schemeId&#x3D;10001&#x60;. (optional)
     * @param schemeName The name of scheme to search for. (optional)
     * @param onlyDefault Whether only the default priority is returned. (optional, default to false)
     * @param orderBy The ordering to return the priority schemes by. (optional, default to +name)
     * @param expand A comma separated list of additional information to return. \&quot;priorities\&quot; will return priorities associated with the priority scheme. \&quot;projects\&quot; will return projects associated with the priority scheme. &#x60;expand&#x3D;priorities,projects&#x60;. (optional)
     * @return ResponseEntity&lt;PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects> getPrioritySchemesWithHttpInfo(String startAt, String maxResults, List<Long> priorityId, List<Long> schemeId, String schemeName, Boolean onlyDefault, String orderBy, String expand) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/priorityscheme").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "priorityId", priorityId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "schemeId", schemeId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "schemeName", schemeName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "onlyDefault", onlyDefault));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "orderBy", orderBy));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects> returnType = new ParameterizedTypeReference<PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get projects by priority scheme
     * Returns a [paginated](#pagination) list of projects by scheme.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param schemeId The priority scheme ID. (required)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param projectId The project IDs to filter by. For example, &#x60;projectId&#x3D;10000&amp;projectId&#x3D;10001&#x60;. (optional)
     * @param query The string to query projects on by name. (optional)
     * @return PageBeanProject
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanProject getProjectsByPriorityScheme(String schemeId, String startAt, String maxResults, List<Long> projectId, String query) throws RestClientException {
        return getProjectsByPrioritySchemeWithHttpInfo(schemeId, startAt, maxResults, projectId, query).getBody();
    }

    /**
     * Get projects by priority scheme
     * Returns a [paginated](#pagination) list of projects by scheme.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param schemeId The priority scheme ID. (required)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param projectId The project IDs to filter by. For example, &#x60;projectId&#x3D;10000&amp;projectId&#x3D;10001&#x60;. (optional)
     * @param query The string to query projects on by name. (optional)
     * @return ResponseEntity&lt;PageBeanProject&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanProject> getProjectsByPrioritySchemeWithHttpInfo(String schemeId, String startAt, String maxResults, List<Long> projectId, String query) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling getProjectsByPriorityScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/priorityscheme/{schemeId}/projects").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "projectId", projectId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanProject> returnType = new ParameterizedTypeReference<PageBeanProject>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Suggested priorities for mappings
     * Returns a [paginated](#pagination) list of priorities that would require mapping, given a change in priorities or projects associated with a priority scheme.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param body  (required)
     * @return PageBeanPriorityWithSequence
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanPriorityWithSequence suggestedPrioritiesForMappings(SuggestedMappingsRequestBean body) throws RestClientException {
        return suggestedPrioritiesForMappingsWithHttpInfo(body).getBody();
    }

    /**
     * Suggested priorities for mappings
     * Returns a [paginated](#pagination) list of priorities that would require mapping, given a change in priorities or projects associated with a priority scheme.  **[Permissions](#permissions) required:** Permission to access Jira.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param body  (required)
     * @return ResponseEntity&lt;PageBeanPriorityWithSequence&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanPriorityWithSequence> suggestedPrioritiesForMappingsWithHttpInfo(SuggestedMappingsRequestBean body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling suggestedPrioritiesForMappings");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/priorityscheme/mappings").build().toUriString();
        
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

        ParameterizedTypeReference<PageBeanPriorityWithSequence> returnType = new ParameterizedTypeReference<PageBeanPriorityWithSequence>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update priority scheme
     * Updates a priority scheme. This includes its details, the lists of priorities and projects in it  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>202</b> - Returned if the request is accepted.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.  **Mappings Validation Errors**   *  &#x60;&#x60;The changes to priority schemes require mapping of priorities. Please provide a value for the &#x27;in&#x27; mappings object.&#x60;&#x60; Priorities are being removed and/or projects are being added to the scheme, but &#x60;&#x60;in&#x60;&#x60; mappings are not provided.  *  &#x60;&#x60;The changes to priority schemes require mapping of priorities. Please provide a value for the &#x27;out&#x27; mappings object.&#x60;&#x60; Projects are being removed from the scheme, but &#x60;&#x60;out&#x60;&#x60; mappings are not provided.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] provided as keys for the &#x27;in&#x27; mappings object do not exist. Please provide existing priority IDs.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but do not exist. Please confirm the correct priority ID(s) have been provided, they should be priorities that exist on the Jira site which are used by projects being added to the current scheme, but are not in use by the current scheme.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] provided as values for the &#x27;in&#x27; mappings object do not exist. Please provide existing priority IDs used by the current priority scheme.&#x60;&#x60; The listed priority ID(s) have been provided as values for &#x60;&#x60;in&#x60;&#x60; mappings but do not exist. Please confirm the correct priority ID(s) have been provided, they should be priorities that exist on the Jira site and are in use by the current scheme.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] provided as keys for the &#x27;out&#x27; mappings object do not exist. Please provide existing priority IDs used by the current priority scheme.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;out&#x60;&#x60; mappings but are invalid. Please confirm the correct priority ID(s) have been provided, they should be priorities that exist on the Jira site and are in use by the current scheme.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] provided as values for the &#x27;out&#x27; mappings object do not exist. Please provide existing priority IDs used by the default scheme.&#x60;&#x60; The listed priority ID(s) have been provided as values for &#x60;&#x60;out&#x60;&#x60; mappings but are invalid. Please confirm the correct priority ID(s) have been provided, they should be priorities that exist on the Jira site and are in use by the Default Priority Scheme, but are not in use by the current scheme.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] do not require mapping. Please remove these keys and their corresponding values from the &#x27;in&#x27; mappings object.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but are not required, they can be removed from the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] require mapping. Please provide mappings in the &#x27;in&#x27; mappings object, where these priorities are the keys with corresponding values.&#x60;&#x60; The listed priority ID(s) have not been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but are required, add them to the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] being mapped to are not in the current scheme. Please remove these values and their corresponding keys from the &#x27;in&#x27; mappings object.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but are not in use by the current scheme, they can be removed from the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] do not require mapping. Please remove these keys and their corresponding values from the &#x27;out&#x27; mappings object.&#x60;&#x60; The listed priority ID(s) hve been provided as keys for &#x60;&#x60;out&#x60;&#x60; mappings but are not required, they can be removed from the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] require mapping. Please provide mappings in the &#x27;out&#x27; mappings object, where these priorities are the keys with corresponding values.&#x60;&#x60; The listed priority ID(s) have not been provided as keys for &#x60;&#x60;out&#x60;&#x60; mappings but are required, add them to the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] being mapped to are not in the default scheme. Please remove these values and their corresponding keys from the &#x27;out&#x27; mappings object.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;out&#x60;&#x60; mappings but are not in use by the Default Priority Scheme, they can be removed from the mappings object.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permissions.
     * <p><b>409</b> - Returned if an action with this priority scheme is still in progress.
     * @param body  (required)
     * @param schemeId The ID of the priority scheme. (required)
     * @return UpdatePrioritySchemeResponseBean
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UpdatePrioritySchemeResponseBean updatePriorityScheme(UpdatePrioritySchemeRequestBean body, Long schemeId) throws RestClientException {
        return updatePrioritySchemeWithHttpInfo(body, schemeId).getBody();
    }

    /**
     * Update priority scheme
     * Updates a priority scheme. This includes its details, the lists of priorities and projects in it  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>202</b> - Returned if the request is accepted.
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.  **Mappings Validation Errors**   *  &#x60;&#x60;The changes to priority schemes require mapping of priorities. Please provide a value for the &#x27;in&#x27; mappings object.&#x60;&#x60; Priorities are being removed and/or projects are being added to the scheme, but &#x60;&#x60;in&#x60;&#x60; mappings are not provided.  *  &#x60;&#x60;The changes to priority schemes require mapping of priorities. Please provide a value for the &#x27;out&#x27; mappings object.&#x60;&#x60; Projects are being removed from the scheme, but &#x60;&#x60;out&#x60;&#x60; mappings are not provided.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] provided as keys for the &#x27;in&#x27; mappings object do not exist. Please provide existing priority IDs.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but do not exist. Please confirm the correct priority ID(s) have been provided, they should be priorities that exist on the Jira site which are used by projects being added to the current scheme, but are not in use by the current scheme.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] provided as values for the &#x27;in&#x27; mappings object do not exist. Please provide existing priority IDs used by the current priority scheme.&#x60;&#x60; The listed priority ID(s) have been provided as values for &#x60;&#x60;in&#x60;&#x60; mappings but do not exist. Please confirm the correct priority ID(s) have been provided, they should be priorities that exist on the Jira site and are in use by the current scheme.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] provided as keys for the &#x27;out&#x27; mappings object do not exist. Please provide existing priority IDs used by the current priority scheme.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;out&#x60;&#x60; mappings but are invalid. Please confirm the correct priority ID(s) have been provided, they should be priorities that exist on the Jira site and are in use by the current scheme.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] provided as values for the &#x27;out&#x27; mappings object do not exist. Please provide existing priority IDs used by the default scheme.&#x60;&#x60; The listed priority ID(s) have been provided as values for &#x60;&#x60;out&#x60;&#x60; mappings but are invalid. Please confirm the correct priority ID(s) have been provided, they should be priorities that exist on the Jira site and are in use by the Default Priority Scheme, but are not in use by the current scheme.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] do not require mapping. Please remove these keys and their corresponding values from the &#x27;in&#x27; mappings object.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but are not required, they can be removed from the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] require mapping. Please provide mappings in the &#x27;in&#x27; mappings object, where these priorities are the keys with corresponding values.&#x60;&#x60; The listed priority ID(s) have not been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but are required, add them to the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] being mapped to are not in the current scheme. Please remove these values and their corresponding keys from the &#x27;in&#x27; mappings object.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;in&#x60;&#x60; mappings but are not in use by the current scheme, they can be removed from the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] do not require mapping. Please remove these keys and their corresponding values from the &#x27;out&#x27; mappings object.&#x60;&#x60; The listed priority ID(s) hve been provided as keys for &#x60;&#x60;out&#x60;&#x60; mappings but are not required, they can be removed from the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] require mapping. Please provide mappings in the &#x27;out&#x27; mappings object, where these priorities are the keys with corresponding values.&#x60;&#x60; The listed priority ID(s) have not been provided as keys for &#x60;&#x60;out&#x60;&#x60; mappings but are required, add them to the mappings object.  *  &#x60;&#x60;The priorities with IDs [ID 1, ID 2, ...] being mapped to are not in the default scheme. Please remove these values and their corresponding keys from the &#x27;out&#x27; mappings object.&#x60;&#x60; The listed priority ID(s) have been provided as keys for &#x60;&#x60;out&#x60;&#x60; mappings but are not in use by the Default Priority Scheme, they can be removed from the mappings object.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permissions.
     * <p><b>409</b> - Returned if an action with this priority scheme is still in progress.
     * @param body  (required)
     * @param schemeId The ID of the priority scheme. (required)
     * @return ResponseEntity&lt;UpdatePrioritySchemeResponseBean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UpdatePrioritySchemeResponseBean> updatePrioritySchemeWithHttpInfo(UpdatePrioritySchemeRequestBean body, Long schemeId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updatePriorityScheme");
        }
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling updatePriorityScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/priorityscheme/{schemeId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<UpdatePrioritySchemeResponseBean> returnType = new ParameterizedTypeReference<UpdatePrioritySchemeResponseBean>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}