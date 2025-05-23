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
 * Class for interacting with Jira project permission schemes through the Jira REST API.
 * This class provides methods to assign, get, and manage permission schemes for Jira projects.
 */
package ai.gebo.jira.cloud.client.api;

import ai.gebo.jira.cloud.client.invoker.ApiClient;

import ai.gebo.jira.cloud.client.model.IdBean;
import ai.gebo.jira.cloud.client.model.PermissionScheme;
import ai.gebo.jira.cloud.client.model.ProjectIssueSecurityLevels;
import ai.gebo.jira.cloud.client.model.SecurityScheme;

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

public class ProjectPermissionSchemesApi {
    private ApiClient apiClient;

    /**
     * Constructor for ProjectPermissionSchemesApi that takes an ApiClient instance.
     * 
     * @param apiClient The API client to use for making API calls
     */
    public ProjectPermissionSchemesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the current API client instance.
     * 
     * @return The current ApiClient instance
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client instance to use for making API calls.
     * 
     * @param apiClient The API client to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Assign permission scheme
     * Assigns a permission scheme with a project. See [Managing project permissions](https://confluence.atlassian.com/x/yodKLg) for more information about permission schemes.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if:   *  the user does not have the necessary permission to edit the project&#x27;s configuration.  *  the Jira instance is Jira Core Free or Jira Software Free. Permission schemes cannot be assigned to projects on free plans.
     * <p><b>404</b> - Returned if the project or permission scheme is not found.
     * @param body  (required)
     * @param projectKeyOrId The project ID or project key (case sensitive). (required)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Note that permissions are included when you specify any value. Expand options include:   *  &#x60;all&#x60; Returns all expandable information.  *  &#x60;field&#x60; Returns information about the custom field granted the permission.  *  &#x60;group&#x60; Returns information about the group that is granted the permission.  *  &#x60;permissions&#x60; Returns all permission grants for each permission scheme.  *  &#x60;projectRole&#x60; Returns information about the project role granted the permission.  *  &#x60;user&#x60; Returns information about the user who is granted the permission. (optional)
     * @return PermissionScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PermissionScheme assignPermissionScheme(IdBean body, String projectKeyOrId, String expand) throws RestClientException {
        return assignPermissionSchemeWithHttpInfo(body, projectKeyOrId, expand).getBody();
    }

    /**
     * Assign permission scheme
     * Assigns a permission scheme with a project. See [Managing project permissions](https://confluence.atlassian.com/x/yodKLg) for more information about permission schemes.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if:   *  the user does not have the necessary permission to edit the project&#x27;s configuration.  *  the Jira instance is Jira Core Free or Jira Software Free. Permission schemes cannot be assigned to projects on free plans.
     * <p><b>404</b> - Returned if the project or permission scheme is not found.
     * @param body  (required)
     * @param projectKeyOrId The project ID or project key (case sensitive). (required)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Note that permissions are included when you specify any value. Expand options include:   *  &#x60;all&#x60; Returns all expandable information.  *  &#x60;field&#x60; Returns information about the custom field granted the permission.  *  &#x60;group&#x60; Returns information about the group that is granted the permission.  *  &#x60;permissions&#x60; Returns all permission grants for each permission scheme.  *  &#x60;projectRole&#x60; Returns information about the project role granted the permission.  *  &#x60;user&#x60; Returns information about the user who is granted the permission. (optional)
     * @return ResponseEntity&lt;PermissionScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PermissionScheme> assignPermissionSchemeWithHttpInfo(IdBean body, String projectKeyOrId, String expand) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling assignPermissionScheme");
        }
        // verify the required parameter 'projectKeyOrId' is set
        if (projectKeyOrId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectKeyOrId' when calling assignPermissionScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("projectKeyOrId", projectKeyOrId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/project/{projectKeyOrId}/permissionscheme").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PermissionScheme> returnType = new ParameterizedTypeReference<PermissionScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get assigned permission scheme
     * Gets the [permission scheme](https://confluence.atlassian.com/x/yodKLg) associated with the project.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg) or *Administer projects* [project permission](https://confluence.atlassian.com/x/yodKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to view the project&#x27;s configuration.
     * <p><b>404</b> - Returned if the project is not found or the user does not have permission to view the project.
     * @param projectKeyOrId The project ID or project key (case sensitive). (required)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Note that permissions are included when you specify any value. Expand options include:   *  &#x60;all&#x60; Returns all expandable information.  *  &#x60;field&#x60; Returns information about the custom field granted the permission.  *  &#x60;group&#x60; Returns information about the group that is granted the permission.  *  &#x60;permissions&#x60; Returns all permission grants for each permission scheme.  *  &#x60;projectRole&#x60; Returns information about the project role granted the permission.  *  &#x60;user&#x60; Returns information about the user who is granted the permission. (optional)
     * @return PermissionScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PermissionScheme getAssignedPermissionScheme(String projectKeyOrId, String expand) throws RestClientException {
        return getAssignedPermissionSchemeWithHttpInfo(projectKeyOrId, expand).getBody();
    }

    /**
     * Get assigned permission scheme
     * Gets the [permission scheme](https://confluence.atlassian.com/x/yodKLg) associated with the project.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg) or *Administer projects* [project permission](https://confluence.atlassian.com/x/yodKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to view the project&#x27;s configuration.
     * <p><b>404</b> - Returned if the project is not found or the user does not have permission to view the project.
     * @param projectKeyOrId The project ID or project key (case sensitive). (required)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Note that permissions are included when you specify any value. Expand options include:   *  &#x60;all&#x60; Returns all expandable information.  *  &#x60;field&#x60; Returns information about the custom field granted the permission.  *  &#x60;group&#x60; Returns information about the group that is granted the permission.  *  &#x60;permissions&#x60; Returns all permission grants for each permission scheme.  *  &#x60;projectRole&#x60; Returns information about the project role granted the permission.  *  &#x60;user&#x60; Returns information about the user who is granted the permission. (optional)
     * @return ResponseEntity&lt;PermissionScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PermissionScheme> getAssignedPermissionSchemeWithHttpInfo(String projectKeyOrId, String expand) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'projectKeyOrId' is set
        if (projectKeyOrId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectKeyOrId' when calling getAssignedPermissionScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("projectKeyOrId", projectKeyOrId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/project/{projectKeyOrId}/permissionscheme").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PermissionScheme> returnType = new ParameterizedTypeReference<PermissionScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get project issue security scheme
     * Returns the [issue security scheme](https://confluence.atlassian.com/x/J4lKLg) associated with the project.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg) or the *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the project is visible to the user but the user doesn&#x27;t have administrative permissions.
     * <p><b>404</b> - Returned if the project is not found or the user does not have permission to view it.
     * @param projectKeyOrId The project ID or project key (case sensitive). (required)
     * @return SecurityScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecurityScheme getProjectIssueSecurityScheme(String projectKeyOrId) throws RestClientException {
        return getProjectIssueSecuritySchemeWithHttpInfo(projectKeyOrId).getBody();
    }

    /**
     * Get project issue security scheme
     * Returns the [issue security scheme](https://confluence.atlassian.com/x/J4lKLg) associated with the project.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg) or the *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the project is visible to the user but the user doesn&#x27;t have administrative permissions.
     * <p><b>404</b> - Returned if the project is not found or the user does not have permission to view it.
     * @param projectKeyOrId The project ID or project key (case sensitive). (required)
     * @return ResponseEntity&lt;SecurityScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecurityScheme> getProjectIssueSecuritySchemeWithHttpInfo(String projectKeyOrId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'projectKeyOrId' is set
        if (projectKeyOrId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectKeyOrId' when calling getProjectIssueSecurityScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("projectKeyOrId", projectKeyOrId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/project/{projectKeyOrId}/issuesecuritylevelscheme").buildAndExpand(uriVariables).toUriString();
        
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
     * Get project issue security levels
     * Returns all [issue security](https://confluence.atlassian.com/x/J4lKLg) levels for the project that the user has access to.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [global permission](https://confluence.atlassian.com/x/x4dKLg) for the project, however, issue security levels are only returned for authenticated user with *Set Issue Security* [global permission](https://confluence.atlassian.com/x/x4dKLg) for the project.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>404</b> - Returned if the project is not found or the user does not have permission to view it.
     * @param projectKeyOrId The project ID or project key (case sensitive). (required)
     * @return ProjectIssueSecurityLevels
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ProjectIssueSecurityLevels getSecurityLevelsForProject(String projectKeyOrId) throws RestClientException {
        return getSecurityLevelsForProjectWithHttpInfo(projectKeyOrId).getBody();
    }

    /**
     * Get project issue security levels
     * Returns all [issue security](https://confluence.atlassian.com/x/J4lKLg) levels for the project that the user has access to.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [global permission](https://confluence.atlassian.com/x/x4dKLg) for the project, however, issue security levels are only returned for authenticated user with *Set Issue Security* [global permission](https://confluence.atlassian.com/x/x4dKLg) for the project.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>404</b> - Returned if the project is not found or the user does not have permission to view it.
     * @param projectKeyOrId The project ID or project key (case sensitive). (required)
     * @return ResponseEntity&lt;ProjectIssueSecurityLevels&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectIssueSecurityLevels> getSecurityLevelsForProjectWithHttpInfo(String projectKeyOrId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'projectKeyOrId' is set
        if (projectKeyOrId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectKeyOrId' when calling getSecurityLevelsForProject");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("projectKeyOrId", projectKeyOrId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/project/{projectKeyOrId}/securitylevel").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<ProjectIssueSecurityLevels> returnType = new ParameterizedTypeReference<ProjectIssueSecurityLevels>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}