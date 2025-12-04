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
 * This class provides API operations for managing project role actors in Jira Cloud.
 * It interfaces with Jira's REST API to handle actors (users and groups) associated with project roles.
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
import ai.gebo.jira.cloud.client.model.ActorInputBean;
import ai.gebo.jira.cloud.client.model.ActorsMap;
import ai.gebo.jira.cloud.client.model.ProjectRole;
import ai.gebo.jira.cloud.client.model.ProjectRoleActorsUpdateBean;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

public class ProjectRoleActorsApi {
    private ApiClient apiClient;

    /**
     * Constructor that initializes the API client.
     * 
     * @param apiClient The API client to use for making requests
     */
    public ProjectRoleActorsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the current API client.
     * 
     * @return The current API client
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to use for requests.
     * 
     * @param apiClient The new API client to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add actors to project role
     * Adds actors to a project role for the project.  To replace all actors for the project, use [Set actors for project role](#api-rest-api-3-project-projectIdOrKey-role-id-put).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project or *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful. The complete list of actors for the project is returned.  For example, the cURL request above adds a group, *jira-developers*. For the response below to be returned as a result of that request, the user *Mia Krystof* would have previously been added as a &#x60;user&#x60; actor for this project.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing or if the calling user lacks administrative permissions for the project.
     * <p><b>404</b> - Returned if:   *  the project is not found.  *  the user or group is not found.  *  the group or user is not active.
     * @param body The groups or users to associate with the project role for this project. Provide the user account ID, group name, or group ID. As a group&#x27;s name can change, use of group ID is recommended. (required)
     * @param projectIdOrKey The project ID or project key (case sensitive). (required)
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @return ProjectRole
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ProjectRole addActorUsers(ActorsMap body, String projectIdOrKey, Long id) throws RestClientException {
        return addActorUsersWithHttpInfo(body, projectIdOrKey, id).getBody();
    }

    /**
     * Add actors to project role
     * Adds actors to a project role for the project.  To replace all actors for the project, use [Set actors for project role](#api-rest-api-3-project-projectIdOrKey-role-id-put).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project or *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful. The complete list of actors for the project is returned.  For example, the cURL request above adds a group, *jira-developers*. For the response below to be returned as a result of that request, the user *Mia Krystof* would have previously been added as a &#x60;user&#x60; actor for this project.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing or if the calling user lacks administrative permissions for the project.
     * <p><b>404</b> - Returned if:   *  the project is not found.  *  the user or group is not found.  *  the group or user is not active.
     * @param body The groups or users to associate with the project role for this project. Provide the user account ID, group name, or group ID. As a group&#x27;s name can change, use of group ID is recommended. (required)
     * @param projectIdOrKey The project ID or project key (case sensitive). (required)
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @return ResponseEntity&lt;ProjectRole&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectRole> addActorUsersWithHttpInfo(ActorsMap body, String projectIdOrKey, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling addActorUsers");
        }
        // verify the required parameter 'projectIdOrKey' is set
        if (projectIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectIdOrKey' when calling addActorUsers");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling addActorUsers");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("projectIdOrKey", projectIdOrKey);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/project/{projectIdOrKey}/role/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<ProjectRole> returnType = new ParameterizedTypeReference<ProjectRole>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Add default actors to project role
     * Adds [default actors](#api-rest-api-3-resolution-get) to a role. You may add groups or users, but you cannot add groups and users in the same request.  Changing a project role&#x27;s default actors does not affect project role members for projects already created.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have administrative permissions.
     * <p><b>404</b> - Returned if the project role is not found.
     * @param body  (required)
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @return ProjectRole
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ProjectRole addProjectRoleActorsToRole(ActorInputBean body, Long id) throws RestClientException {
        return addProjectRoleActorsToRoleWithHttpInfo(body, id).getBody();
    }

    /**
     * Add default actors to project role
     * Adds [default actors](#api-rest-api-3-resolution-get) to a role. You may add groups or users, but you cannot add groups and users in the same request.  Changing a project role&#x27;s default actors does not affect project role members for projects already created.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have administrative permissions.
     * <p><b>404</b> - Returned if the project role is not found.
     * @param body  (required)
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @return ResponseEntity&lt;ProjectRole&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectRole> addProjectRoleActorsToRoleWithHttpInfo(ActorInputBean body, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling addProjectRoleActorsToRole");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling addProjectRoleActorsToRole");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/role/{id}/actors").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<ProjectRole> returnType = new ParameterizedTypeReference<ProjectRole>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete actors from project role
     * Deletes actors from a project role for the project.  To remove default actors from the project role, use [Delete default actors from project role](#api-rest-api-3-role-id-actors-delete).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project or *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>404</b> - Returned if:   *  the project or project role is not found.  *  the calling user does not have administrative permission.
     * @param projectIdOrKey The project ID or project key (case sensitive). (required)
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @param user The user account ID of the user to remove from the project role. (optional)
     * @param group The name of the group to remove from the project role. This parameter cannot be used with the &#x60;groupId&#x60; parameter. As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended. (optional)
     * @param groupId The ID of the group to remove from the project role. This parameter cannot be used with the &#x60;group&#x60; parameter. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteActor(String projectIdOrKey, Long id, String user, String group, String groupId) throws RestClientException {
        deleteActorWithHttpInfo(projectIdOrKey, id, user, group, groupId);
    }

    /**
     * Delete actors from project role
     * Deletes actors from a project role for the project.  To remove default actors from the project role, use [Delete default actors from project role](#api-rest-api-3-role-id-actors-delete).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project or *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>404</b> - Returned if:   *  the project or project role is not found.  *  the calling user does not have administrative permission.
     * @param projectIdOrKey The project ID or project key (case sensitive). (required)
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @param user The user account ID of the user to remove from the project role. (optional)
     * @param group The name of the group to remove from the project role. This parameter cannot be used with the &#x60;groupId&#x60; parameter. As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended. (optional)
     * @param groupId The ID of the group to remove from the project role. This parameter cannot be used with the &#x60;group&#x60; parameter. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteActorWithHttpInfo(String projectIdOrKey, Long id, String user, String group, String groupId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'projectIdOrKey' is set
        if (projectIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectIdOrKey' when calling deleteActor");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteActor");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("projectIdOrKey", projectIdOrKey);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/project/{projectIdOrKey}/role/{id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "user", user));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "group", group));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupId", groupId));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete default actors from project role
     * Deletes the [default actors](#api-rest-api-3-resolution-get) from a project role. You may delete a group or user, but you cannot delete a group and a user in the same request.  Changing a project role&#x27;s default actors does not affect project role members for projects already created.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have administrative permissions.
     * <p><b>404</b> - Returned if the project role is not found.
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @param user The user account ID of the user to remove as a default actor. (optional)
     * @param groupId The group ID of the group to be removed as a default actor. This parameter cannot be used with the &#x60;group&#x60; parameter. (optional)
     * @param group The group name of the group to be removed as a default actor.This parameter cannot be used with the &#x60;groupId&#x60; parameter. As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended. (optional)
     * @return ProjectRole
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ProjectRole deleteProjectRoleActorsFromRole(Long id, String user, String groupId, String group) throws RestClientException {
        return deleteProjectRoleActorsFromRoleWithHttpInfo(id, user, groupId, group).getBody();
    }

    /**
     * Delete default actors from project role
     * Deletes the [default actors](#api-rest-api-3-resolution-get) from a project role. You may delete a group or user, but you cannot delete a group and a user in the same request.  Changing a project role&#x27;s default actors does not affect project role members for projects already created.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have administrative permissions.
     * <p><b>404</b> - Returned if the project role is not found.
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @param user The user account ID of the user to remove as a default actor. (optional)
     * @param groupId The group ID of the group to be removed as a default actor. This parameter cannot be used with the &#x60;group&#x60; parameter. (optional)
     * @param group The group name of the group to be removed as a default actor.This parameter cannot be used with the &#x60;groupId&#x60; parameter. As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended. (optional)
     * @return ResponseEntity&lt;ProjectRole&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectRole> deleteProjectRoleActorsFromRoleWithHttpInfo(Long id, String user, String groupId, String group) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteProjectRoleActorsFromRole");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/role/{id}/actors").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "user", user));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupId", groupId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "group", group));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<ProjectRole> returnType = new ParameterizedTypeReference<ProjectRole>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get default actors for project role
     * Returns the [default actors](#api-rest-api-3-resolution-get) for the project role.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have administrative permissions.
     * <p><b>404</b> - Returned if the project role is not found.
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @return ProjectRole
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ProjectRole getProjectRoleActorsForRole(Long id) throws RestClientException {
        return getProjectRoleActorsForRoleWithHttpInfo(id).getBody();
    }

    /**
     * Get default actors for project role
     * Returns the [default actors](#api-rest-api-3-resolution-get) for the project role.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have administrative permissions.
     * <p><b>404</b> - Returned if the project role is not found.
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @return ResponseEntity&lt;ProjectRole&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectRole> getProjectRoleActorsForRoleWithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getProjectRoleActorsForRole");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/role/{id}/actors").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<ProjectRole> returnType = new ParameterizedTypeReference<ProjectRole>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Set actors for project role
     * Sets the actors for a project role for a project, replacing all existing actors.  To add actors to the project without overwriting the existing list, use [Add actors to project role](#api-rest-api-3-project-projectIdOrKey-role-id-post).  **[Permissions](#permissions) required:** *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project or *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful. The complete list of actors for the project is returned.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing or if the calling user lacks administrative permissions for the project.
     * <p><b>404</b> - Returned if:   *  the project is not found.  *  a user or group is not found.  *  a group or user is not active.
     * @param body The groups or users to associate with the project role for this project. Provide the user account ID, group name, or group ID. As a group&#x27;s name can change, use of group ID is recommended. (required)
     * @param projectIdOrKey The project ID or project key (case sensitive). (required)
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @return ProjectRole
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ProjectRole setActors(ProjectRoleActorsUpdateBean body, String projectIdOrKey, Long id) throws RestClientException {
        return setActorsWithHttpInfo(body, projectIdOrKey, id).getBody();
    }

    /**
     * Set actors for project role
     * Sets the actors for a project role for a project, replacing all existing actors.  To add actors to the project without overwriting the existing list, use [Add actors to project role](#api-rest-api-3-project-projectIdOrKey-role-id-post).  **[Permissions](#permissions) required:** *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project or *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful. The complete list of actors for the project is returned.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing or if the calling user lacks administrative permissions for the project.
     * <p><b>404</b> - Returned if:   *  the project is not found.  *  a user or group is not found.  *  a group or user is not active.
     * @param body The groups or users to associate with the project role for this project. Provide the user account ID, group name, or group ID. As a group&#x27;s name can change, use of group ID is recommended. (required)
     * @param projectIdOrKey The project ID or project key (case sensitive). (required)
     * @param id The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. (required)
     * @return ResponseEntity&lt;ProjectRole&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectRole> setActorsWithHttpInfo(ProjectRoleActorsUpdateBean body, String projectIdOrKey, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling setActors");
        }
        // verify the required parameter 'projectIdOrKey' is set
        if (projectIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectIdOrKey' when calling setActors");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling setActors");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("projectIdOrKey", projectIdOrKey);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/project/{projectIdOrKey}/role/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<ProjectRole> returnType = new ParameterizedTypeReference<ProjectRole>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}