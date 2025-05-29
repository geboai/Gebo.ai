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

import ai.gebo.jira.cloud.client.model.AddSecuritySchemeLevelsRequestBean;
import ai.gebo.jira.cloud.client.model.AssociateSecuritySchemeWithProjectDetails;
import ai.gebo.jira.cloud.client.model.ErrorCollection;
import ai.gebo.jira.cloud.client.model.PageBeanIssueSecuritySchemeToProjectMapping;
import ai.gebo.jira.cloud.client.model.PageBeanSecurityLevel;
import ai.gebo.jira.cloud.client.model.PageBeanSecurityLevelMember;
import ai.gebo.jira.cloud.client.model.PageBeanSecuritySchemeWithProjects;
import ai.gebo.jira.cloud.client.model.SecurityScheme;
import ai.gebo.jira.cloud.client.model.SecuritySchemeId;
import ai.gebo.jira.cloud.client.model.SecuritySchemeMembersRequest;
import ai.gebo.jira.cloud.client.model.SecuritySchemes;
import ai.gebo.jira.cloud.client.model.TaskProgressBeanObject;
import ai.gebo.jira.cloud.client.model.UpdateIssueSecuritySchemeRequestBean;

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
//@Component("ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi")
public class IssueSecuritySchemesApi {
    private ApiClient apiClient;

    public IssueSecuritySchemesApi() {
        this(new ApiClient());
    }

    //@Autowired
    public IssueSecuritySchemesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add issue security levels
     * Adds levels and levels&#x27; members to the issue security scheme. You can add up to 100 levels per request.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the security scheme isn&#x27;t found.</p>
     * @param body  (required)
     * @param schemeId The ID of the issue security scheme. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object addSecurityLevel(AddSecuritySchemeLevelsRequestBean body, String schemeId) throws RestClientException {
        return addSecurityLevelWithHttpInfo(body, schemeId).getBody();
    }

    /**
     * Add issue security levels
     * Adds levels and levels&#x27; members to the issue security scheme. You can add up to 100 levels per request.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the security scheme isn&#x27;t found.
     * @param body  (required)
     * @param schemeId The ID of the issue security scheme. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> addSecurityLevelWithHttpInfo(AddSecuritySchemeLevelsRequestBean body, String schemeId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling addSecurityLevel");
        }
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling addSecurityLevel");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/{schemeId}/level").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Add issue security level members
     * Adds members to the issue security level. You can add up to 100 members per request.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the security scheme isn&#x27;t found.</p>
     * @param body  (required)
     * @param schemeId The ID of the issue security scheme. (required)
     * @param levelId The ID of the issue security level. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object addSecurityLevelMembers(SecuritySchemeMembersRequest body, String schemeId, String levelId) throws RestClientException {
        return addSecurityLevelMembersWithHttpInfo(body, schemeId, levelId).getBody();
    }

    /**
     * Add issue security level members
     * Adds members to the issue security level. You can add up to 100 members per request.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the security scheme isn&#x27;t found.</p>
     * @param body  (required)
     * @param schemeId The ID of the issue security scheme. (required)
     * @param levelId The ID of the issue security level. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> addSecurityLevelMembersWithHttpInfo(SecuritySchemeMembersRequest body, String schemeId, String levelId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling addSecurityLevelMembers");
        }
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling addSecurityLevelMembers");
        }
        // verify the required parameter 'levelId' is set
        if (levelId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'levelId' when calling addSecurityLevelMembers");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        uriVariables.put("levelId", levelId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/{schemeId}/level/{levelId}/member").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Associate security scheme to project
     * Associates an issue security scheme with a project and remaps security levels of issues to the new levels, if provided.  This operation is [asynchronous](#async). Follow the &#x60;location&#x60; link in the response to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain subsequent updates.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>303</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the security scheme isn&#x27;t found.</p>
     * <p><b>409</b> - Returned if a task to remove the issue security level is already running.</p>
     * @param body  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void associateSchemesToProjects(AssociateSecuritySchemeWithProjectDetails body) throws RestClientException {
        associateSchemesToProjectsWithHttpInfo(body);
    }

    /**
     * Associate security scheme to project
     * Associates an issue security scheme with a project and remaps security levels of issues to the new levels, if provided.  This operation is [asynchronous](#async). Follow the &#x60;location&#x60; link in the response to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain subsequent updates.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>303</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the security scheme isn&#x27;t found.</p>
     * <p><b>409</b> - Returned if a task to remove the issue security level is already running.</p>
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> associateSchemesToProjectsWithHttpInfo(AssociateSecuritySchemeWithProjectDetails body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling associateSchemesToProjects");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/project").build().toUriString();
        
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

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create issue security scheme
     * Creates a security scheme with security scheme levels and levels&#x27; members. You can create up to 100 security scheme levels and security scheme levels&#x27; members per request.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param body  (required)
     * @return SecuritySchemeId
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecuritySchemeId createIssueSecurityScheme(Map<String, Object> body) throws RestClientException {
        return createIssueSecuritySchemeWithHttpInfo(body).getBody();
    }

    /**
     * Create issue security scheme
     * Creates a security scheme with security scheme levels and levels&#x27; members. You can create up to 100 security scheme levels and security scheme levels&#x27; members per request.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param body  (required)
     * @return ResponseEntity&lt;SecuritySchemeId&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecuritySchemeId> createIssueSecuritySchemeWithHttpInfo(Map<String, Object> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createIssueSecurityScheme");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes").build().toUriString();
        
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

        ParameterizedTypeReference<SecuritySchemeId> returnType = new ParameterizedTypeReference<SecuritySchemeId>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete issue security scheme
     * Deletes an issue security scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue security scheme isn&#x27;t found.</p>
     * @param schemeId The ID of the issue security scheme. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object deleteSecurityScheme(String schemeId) throws RestClientException {
        return deleteSecuritySchemeWithHttpInfo(schemeId).getBody();
    }

    /**
     * Delete issue security scheme
     * Deletes an issue security scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue security scheme isn&#x27;t found.</p>
     * @param schemeId The ID of the issue security scheme. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteSecuritySchemeWithHttpInfo(String schemeId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling deleteSecurityScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/{schemeId}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get issue security scheme
     * Returns an issue security scheme along with its security levels.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for a project that uses the requested issue security scheme.
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user does not have the administrator permission and the scheme is not used in any project where the user has administrative permission.</p>
     * @param id The ID of the issue security scheme. Use the [Get issue security schemes](#api-rest-api-3-issuesecurityschemes-get) operation to get a list of issue security scheme IDs. (required)
     * @return SecurityScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecurityScheme getIssueSecurityScheme(Long id) throws RestClientException {
        return getIssueSecuritySchemeWithHttpInfo(id).getBody();
    }

    /**
     * Get issue security scheme
     * Returns an issue security scheme along with its security levels.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for a project that uses the requested issue security scheme.
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user does not have the administrator permission and the scheme is not used in any project where the user has administrative permission.</p>
     * @param id The ID of the issue security scheme. Use the [Get issue security schemes](#api-rest-api-3-issuesecurityschemes-get) operation to get a list of issue security scheme IDs. (required)
     * @return ResponseEntity&lt;SecurityScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecurityScheme> getIssueSecuritySchemeWithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getIssueSecurityScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<SecurityScheme> returnType = new ParameterizedTypeReference<SecurityScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get issue security schemes
     * Returns all [issue security schemes](https://confluence.atlassian.com/x/J4lKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.</p>
     * <p><b>403</b> - Returned if the user does not have permission to administer issue security schemes.</p>
     * @return SecuritySchemes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecuritySchemes getIssueSecuritySchemes() throws RestClientException {
        return getIssueSecuritySchemesWithHttpInfo().getBody();
    }

    /**
     * Get issue security schemes
     * Returns all [issue security schemes](https://confluence.atlassian.com/x/J4lKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.</p>
     * <p><b>403</b> - Returned if the user does not have permission to administer issue security schemes.</p>
     * @return ResponseEntity&lt;SecuritySchemes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecuritySchemes> getIssueSecuritySchemesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes").build().toUriString();
        
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

        ParameterizedTypeReference<SecuritySchemes> returnType = new ParameterizedTypeReference<SecuritySchemes>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get issue security level members
     * Returns a [paginated](#pagination) list of issue security level members.  Only issue security level members in the context of classic projects are returned.  Filtering using parameters is inclusive: if you specify both security scheme IDs and level IDs, the result will include all issue security level members from the specified schemes and levels.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param id The list of issue security level member IDs. To include multiple issue security level members separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. (optional)
     * @param schemeId The list of issue security scheme IDs. To include multiple issue security schemes separate IDs with an ampersand: &#x60;schemeId&#x3D;10000&amp;schemeId&#x3D;10001&#x60;. (optional)
     * @param levelId The list of issue security level IDs. To include multiple issue security levels separate IDs with an ampersand: &#x60;levelId&#x3D;10000&amp;levelId&#x3D;10001&#x60;. (optional)
     * @param expand Use expand to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;all&#x60; Returns all expandable information  *  &#x60;field&#x60; Returns information about the custom field granted the permission  *  &#x60;group&#x60; Returns information about the group that is granted the permission  *  &#x60;projectRole&#x60; Returns information about the project role granted the permission  *  &#x60;user&#x60; Returns information about the user who is granted the permission (optional)
     * @return PageBeanSecurityLevelMember
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanSecurityLevelMember getSecurityLevelMembers(String startAt, String maxResults, List<String> id, List<String> schemeId, List<String> levelId, String expand) throws RestClientException {
        return getSecurityLevelMembersWithHttpInfo(startAt, maxResults, id, schemeId, levelId, expand).getBody();
    }

    /**
     * Get issue security level members
     * Returns a [paginated](#pagination) list of issue security level members.  Only issue security level members in the context of classic projects are returned.  Filtering using parameters is inclusive: if you specify both security scheme IDs and level IDs, the result will include all issue security level members from the specified schemes and levels.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param id The list of issue security level member IDs. To include multiple issue security level members separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. (optional)
     * @param schemeId The list of issue security scheme IDs. To include multiple issue security schemes separate IDs with an ampersand: &#x60;schemeId&#x3D;10000&amp;schemeId&#x3D;10001&#x60;. (optional)
     * @param levelId The list of issue security level IDs. To include multiple issue security levels separate IDs with an ampersand: &#x60;levelId&#x3D;10000&amp;levelId&#x3D;10001&#x60;. (optional)
     * @param expand Use expand to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;all&#x60; Returns all expandable information  *  &#x60;field&#x60; Returns information about the custom field granted the permission  *  &#x60;group&#x60; Returns information about the group that is granted the permission  *  &#x60;projectRole&#x60; Returns information about the project role granted the permission  *  &#x60;user&#x60; Returns information about the user who is granted the permission (optional)
     * @return ResponseEntity&lt;PageBeanSecurityLevelMember&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanSecurityLevelMember> getSecurityLevelMembersWithHttpInfo(String startAt, String maxResults, List<String> id, List<String> schemeId, List<String> levelId, String expand) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/level/member").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "id", id));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "schemeId", schemeId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "levelId", levelId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanSecurityLevelMember> returnType = new ParameterizedTypeReference<PageBeanSecurityLevelMember>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get issue security levels
     * Returns a [paginated](#pagination) list of issue security levels.  Only issue security levels in the context of classic projects are returned.  Filtering using IDs is inclusive: if you specify both security scheme IDs and level IDs, the result will include both specified issue security levels and all issue security levels from the specified schemes.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param id The list of issue security scheme level IDs. To include multiple issue security levels, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. (optional)
     * @param schemeId The list of issue security scheme IDs. To include multiple issue security schemes, separate IDs with an ampersand: &#x60;schemeId&#x3D;10000&amp;schemeId&#x3D;10001&#x60;. (optional)
     * @param onlyDefault When set to true, returns multiple default levels for each security scheme containing a default. If you provide scheme and level IDs not associated with the default, returns an empty page. The default value is false. (optional, default to false)
     * @return PageBeanSecurityLevel
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanSecurityLevel getSecurityLevels(String startAt, String maxResults, List<String> id, List<String> schemeId, Boolean onlyDefault) throws RestClientException {
        return getSecurityLevelsWithHttpInfo(startAt, maxResults, id, schemeId, onlyDefault).getBody();
    }

    /**
     * Get issue security levels
     * Returns a [paginated](#pagination) list of issue security levels.  Only issue security levels in the context of classic projects are returned.  Filtering using IDs is inclusive: if you specify both security scheme IDs and level IDs, the result will include both specified issue security levels and all issue security levels from the specified schemes.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param id The list of issue security scheme level IDs. To include multiple issue security levels, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. (optional)
     * @param schemeId The list of issue security scheme IDs. To include multiple issue security schemes, separate IDs with an ampersand: &#x60;schemeId&#x3D;10000&amp;schemeId&#x3D;10001&#x60;. (optional)
     * @param onlyDefault When set to true, returns multiple default levels for each security scheme containing a default. If you provide scheme and level IDs not associated with the default, returns an empty page. The default value is false. (optional, default to false)
     * @return ResponseEntity&lt;PageBeanSecurityLevel&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanSecurityLevel> getSecurityLevelsWithHttpInfo(String startAt, String maxResults, List<String> id, List<String> schemeId, Boolean onlyDefault) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/level").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "id", id));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "schemeId", schemeId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "onlyDefault", onlyDefault));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanSecurityLevel> returnType = new ParameterizedTypeReference<PageBeanSecurityLevel>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Remove issue security level
     * Deletes an issue security level.  This operation is [asynchronous](#async). Follow the &#x60;location&#x60; link in the response to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain subsequent updates.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>303</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue security level isn&#x27;t found.</p>
     * <p><b>409</b> - Returned if a task to remove the issue security level is already running.</p>
     * @param schemeId The ID of the issue security scheme. (required)
     * @param levelId The ID of the issue security level to remove. (required)
     * @param replaceWith The ID of the issue security level that will replace the currently selected level. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void removeLevel(String schemeId, String levelId, String replaceWith) throws RestClientException {
        removeLevelWithHttpInfo(schemeId, levelId, replaceWith);
    }

    /**
     * Remove issue security level
     * Deletes an issue security level.  This operation is [asynchronous](#async). Follow the &#x60;location&#x60; link in the response to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain subsequent updates.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>303</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue security level isn&#x27;t found.</p>
     * <p><b>409</b> - Returned if a task to remove the issue security level is already running.</p>
     * @param schemeId The ID of the issue security scheme. (required)
     * @param levelId The ID of the issue security level to remove. (required)
     * @param replaceWith The ID of the issue security level that will replace the currently selected level. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> removeLevelWithHttpInfo(String schemeId, String levelId, String replaceWith) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling removeLevel");
        }
        // verify the required parameter 'levelId' is set
        if (levelId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'levelId' when calling removeLevel");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        uriVariables.put("levelId", levelId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/{schemeId}/level/{levelId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "replaceWith", replaceWith));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Remove member from issue security level
     * Removes an issue security level member from an issue security scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the security scheme isn&#x27;t found.</p>
     * @param schemeId The ID of the issue security scheme. (required)
     * @param levelId The ID of the issue security level. (required)
     * @param memberId The ID of the issue security level member to be removed. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object removeMemberFromSecurityLevel(String schemeId, String levelId, String memberId) throws RestClientException {
        return removeMemberFromSecurityLevelWithHttpInfo(schemeId, levelId, memberId).getBody();
    }

    /**
     * Remove member from issue security level
     * Removes an issue security level member from an issue security scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the security scheme isn&#x27;t found.</p>
     * @param schemeId The ID of the issue security scheme. (required)
     * @param levelId The ID of the issue security level. (required)
     * @param memberId The ID of the issue security level member to be removed. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> removeMemberFromSecurityLevelWithHttpInfo(String schemeId, String levelId, String memberId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling removeMemberFromSecurityLevel");
        }
        // verify the required parameter 'levelId' is set
        if (levelId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'levelId' when calling removeMemberFromSecurityLevel");
        }
        // verify the required parameter 'memberId' is set
        if (memberId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'memberId' when calling removeMemberFromSecurityLevel");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        uriVariables.put("levelId", levelId);
        uriVariables.put("memberId", memberId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/{schemeId}/level/{levelId}/member/{memberId}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get projects using issue security schemes
     * Returns a [paginated](#pagination) mapping of projects that are using security schemes. You can provide either one or multiple security scheme IDs or project IDs to filter by. If you don&#x27;t provide any, this will return a list of all mappings. Only issue security schemes in the context of classic projects are supported. **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the search criteria is invalid.If you specify the project ID parameter</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param issueSecuritySchemeId The list of security scheme IDs to be filtered out. (optional)
     * @param projectId The list of project IDs to be filtered out. (optional)
     * @return PageBeanIssueSecuritySchemeToProjectMapping
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanIssueSecuritySchemeToProjectMapping searchProjectsUsingSecuritySchemes(String startAt, String maxResults, List<String> issueSecuritySchemeId, List<String> projectId) throws RestClientException {
        return searchProjectsUsingSecuritySchemesWithHttpInfo(startAt, maxResults, issueSecuritySchemeId, projectId).getBody();
    }

    /**
     * Get projects using issue security schemes
     * Returns a [paginated](#pagination) mapping of projects that are using security schemes. You can provide either one or multiple security scheme IDs or project IDs to filter by. If you don&#x27;t provide any, this will return a list of all mappings. Only issue security schemes in the context of classic projects are supported. **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the search criteria is invalid.If you specify the project ID parameter</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param issueSecuritySchemeId The list of security scheme IDs to be filtered out. (optional)
     * @param projectId The list of project IDs to be filtered out. (optional)
     * @return ResponseEntity&lt;PageBeanIssueSecuritySchemeToProjectMapping&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanIssueSecuritySchemeToProjectMapping> searchProjectsUsingSecuritySchemesWithHttpInfo(String startAt, String maxResults, List<String> issueSecuritySchemeId, List<String> projectId) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/project").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "issueSecuritySchemeId", issueSecuritySchemeId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "projectId", projectId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanIssueSecuritySchemeToProjectMapping> returnType = new ParameterizedTypeReference<PageBeanIssueSecuritySchemeToProjectMapping>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Search issue security schemes
     * Returns a [paginated](#pagination) list of issue security schemes.   If you specify the project ID parameter, the result will contain issue security schemes and related project IDs you filter by. Use \\{link IssueSecuritySchemeResource\\#searchProjectsUsingSecuritySchemes(String, String, Set, Set)\\} to obtain all projects related to scheme.  Only issue security schemes in the context of classic projects are returned.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param id The list of issue security scheme IDs. To include multiple issue security scheme IDs, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. (optional)
     * @param projectId The list of project IDs. To include multiple project IDs, separate IDs with an ampersand: &#x60;projectId&#x3D;10000&amp;projectId&#x3D;10001&#x60;. (optional)
     * @return PageBeanSecuritySchemeWithProjects
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanSecuritySchemeWithProjects searchSecuritySchemes(String startAt, String maxResults, List<String> id, List<String> projectId) throws RestClientException {
        return searchSecuritySchemesWithHttpInfo(startAt, maxResults, id, projectId).getBody();
    }

    /**
     * Search issue security schemes
     * Returns a [paginated](#pagination) list of issue security schemes.   If you specify the project ID parameter, the result will contain issue security schemes and related project IDs you filter by. Use 
     * {link IssueSecuritySchemeResource\\#searchProjectsUsingSecuritySchemes(String, String, Set, Set)\\} to obtain all projects related to scheme.  Only issue security schemes in the context of classic projects are returned.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param id The list of issue security scheme IDs. To include multiple issue security scheme IDs, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. (optional)
     * @param projectId The list of project IDs. To include multiple project IDs, separate IDs with an ampersand: &#x60;projectId&#x3D;10000&amp;projectId&#x3D;10001&#x60;. (optional)
     * @return ResponseEntity&lt;PageBeanSecuritySchemeWithProjects&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanSecuritySchemeWithProjects> searchSecuritySchemesWithHttpInfo(String startAt, String maxResults, List<String> id, List<String> projectId) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "id", id));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "projectId", projectId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanSecuritySchemeWithProjects> returnType = new ParameterizedTypeReference<PageBeanSecuritySchemeWithProjects>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Set default issue security levels
     * Sets default issue security levels for schemes.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue resolution isn&#x27;t found.
     * @param body  (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object setDefaultLevels(Map<String, Object> body) throws RestClientException {
        return setDefaultLevelsWithHttpInfo(body).getBody();
    }

    /**
     * Set default issue security levels
     * Sets default issue security levels for schemes.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue resolution isn&#x27;t found.
     * @param body  (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> setDefaultLevelsWithHttpInfo(Map<String, Object> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling setDefaultLevels");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/level/default").build().toUriString();
        
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

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update issue security scheme
     * Updates the issue security scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue security scheme isn&#x27;t found.
     * @param body  (required)
     * @param id The ID of the issue security scheme. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object updateIssueSecurityScheme(UpdateIssueSecuritySchemeRequestBean body, String id) throws RestClientException {
        return updateIssueSecuritySchemeWithHttpInfo(body, id).getBody();
    }

    /**
     * Update issue security scheme
     * Updates the issue security scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request is invalid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue security scheme isn&#x27;t found.
     * @param body  (required)
     * @param id The ID of the issue security scheme. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> updateIssueSecuritySchemeWithHttpInfo(UpdateIssueSecuritySchemeRequestBean body, String id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateIssueSecurityScheme");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling updateIssueSecurityScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update issue security level
     * Updates the issue security level.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue security level isn&#x27;t found.
     * @param body  (required)
     * @param schemeId The ID of the issue security scheme level belongs to. (required)
     * @param levelId The ID of the issue security level to update. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object updateSecurityLevel(Map<String, Object> body, String schemeId, String levelId) throws RestClientException {
        return updateSecurityLevelWithHttpInfo(body, schemeId, levelId).getBody();
    }

    /**
     * Update issue security level
     * Updates the issue security level.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.</p>
     * <p><b>400</b> - Returned if the request isn&#x27;t valid.</p>
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.</p>
     * <p><b>403</b> - Returned if the user doesn&#x27;t have the necessary permission.</p>
     * <p><b>404</b> - Returned if the issue security level isn&#x27;t found.
     * @param body  (required)
     * @param schemeId The ID of the issue security scheme level belongs to. (required)
     * @param levelId The ID of the issue security level to update. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> updateSecurityLevelWithHttpInfo(Map<String, Object> body, String schemeId, String levelId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateSecurityLevel");
        }
        // verify the required parameter 'schemeId' is set
        if (schemeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'schemeId' when calling updateSecurityLevel");
        }
        // verify the required parameter 'levelId' is set
        if (levelId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'levelId' when calling updateSecurityLevel");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("schemeId", schemeId);
        uriVariables.put("levelId", levelId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issuesecurityschemes/{schemeId}/level/{levelId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
