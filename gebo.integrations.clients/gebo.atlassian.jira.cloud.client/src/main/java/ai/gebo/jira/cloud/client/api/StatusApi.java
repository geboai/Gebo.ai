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
 * Represents the REST API for managing Jira status resources.
 * This class provides methods to create, update, delete, and retrieve statuses
 * as well as examine how statuses are used across projects and workflows.
 */
package ai.gebo.jira.cloud.client.api;

import ai.gebo.jira.cloud.client.invoker.ApiClient;

import ai.gebo.jira.cloud.client.model.JiraStatus;
import ai.gebo.jira.cloud.client.model.PageOfStatuses;
import ai.gebo.jira.cloud.client.model.StatusCreateRequest;
import ai.gebo.jira.cloud.client.model.StatusProjectIssueTypeUsageDTO;
import ai.gebo.jira.cloud.client.model.StatusProjectUsageDTO;
import ai.gebo.jira.cloud.client.model.StatusUpdateRequest;
import ai.gebo.jira.cloud.client.model.StatusWorkflowUsageDTO;

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

public class StatusApi {
    private ApiClient apiClient;

    /**
     * Constructor that initializes the API client.
     * @param apiClient The API client to use for making requests.
     */
    public StatusApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the API client used by this API.
     * @return The API client instance.
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to be used by this API.
     * @param apiClient The API client to use.
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Bulk create statuses
     * Creates statuses for a global or project scope.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>409</b> - Returned if another workflow configuration update task is ongoing.
     * @param body Details of the statuses being created and their scope. (required)
     * @return List&lt;JiraStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<JiraStatus> createStatuses(StatusCreateRequest body) throws RestClientException {
        return createStatusesWithHttpInfo(body).getBody();
    }

    /**
     * Bulk create statuses
     * Creates statuses for a global or project scope.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>409</b> - Returned if another workflow configuration update task is ongoing.
     * @param body Details of the statuses being created and their scope. (required)
     * @return ResponseEntity&lt;List&lt;JiraStatus&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<JiraStatus>> createStatusesWithHttpInfo(StatusCreateRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createStatuses");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuses").build().toUriString();
        
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

        ParameterizedTypeReference<List<JiraStatus>> returnType = new ParameterizedTypeReference<List<JiraStatus>>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk delete Statuses
     * Deletes statuses by ID.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param id The list of status IDs. To include multiple IDs, provide an ampersand-separated list. For example, id&#x3D;10000&amp;id&#x3D;10001.  Min items &#x60;1&#x60;, Max items &#x60;50&#x60; (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object deleteStatusesById(List<String> id) throws RestClientException {
        return deleteStatusesByIdWithHttpInfo(id).getBody();
    }

    /**
     * Bulk delete Statuses
     * Deletes statuses by ID.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param id The list of status IDs. To include multiple IDs, provide an ampersand-separated list. For example, id&#x3D;10000&amp;id&#x3D;10001.  Min items &#x60;1&#x60;, Max items &#x60;50&#x60; (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteStatusesByIdWithHttpInfo(List<String> id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteStatusesById");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuses").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "id", id));

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
     * Get issue type usages by status and project
     * Returns a page of issue types in a project using a given status.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the status with the given ID does not exist.
     * @param statusId The statusId to fetch issue type usages for (required)
     * @param projectId The projectId to fetch issue type usages for (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return StatusProjectIssueTypeUsageDTO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StatusProjectIssueTypeUsageDTO getProjectIssueTypeUsagesForStatus(String statusId, String projectId, String nextPageToken, Integer maxResults) throws RestClientException {
        return getProjectIssueTypeUsagesForStatusWithHttpInfo(statusId, projectId, nextPageToken, maxResults).getBody();
    }

    /**
     * Get issue type usages by status and project
     * Returns a page of issue types in a project using a given status.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the status with the given ID does not exist.
     * @param statusId The statusId to fetch issue type usages for (required)
     * @param projectId The projectId to fetch issue type usages for (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return ResponseEntity&lt;StatusProjectIssueTypeUsageDTO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StatusProjectIssueTypeUsageDTO> getProjectIssueTypeUsagesForStatusWithHttpInfo(String statusId, String projectId, String nextPageToken, Integer maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'statusId' is set
        if (statusId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'statusId' when calling getProjectIssueTypeUsagesForStatus");
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectId' when calling getProjectIssueTypeUsagesForStatus");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("statusId", statusId);
        uriVariables.put("projectId", projectId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuses/{statusId}/project/{projectId}/issueTypeUsages").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nextPageToken", nextPageToken));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<StatusProjectIssueTypeUsageDTO> returnType = new ParameterizedTypeReference<StatusProjectIssueTypeUsageDTO>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get project usages by status
     * Returns a page of projects using a given status.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the status with the given ID does not exist.
     * @param statusId The statusId to fetch project usages for (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return StatusProjectUsageDTO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StatusProjectUsageDTO getProjectUsagesForStatus(String statusId, String nextPageToken, Integer maxResults) throws RestClientException {
        return getProjectUsagesForStatusWithHttpInfo(statusId, nextPageToken, maxResults).getBody();
    }

    /**
     * Get project usages by status
     * Returns a page of projects using a given status.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the status with the given ID does not exist.
     * @param statusId The statusId to fetch project usages for (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return ResponseEntity&lt;StatusProjectUsageDTO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StatusProjectUsageDTO> getProjectUsagesForStatusWithHttpInfo(String statusId, String nextPageToken, Integer maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'statusId' is set
        if (statusId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'statusId' when calling getProjectUsagesForStatus");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("statusId", statusId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuses/{statusId}/projectUsages").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nextPageToken", nextPageToken));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<StatusProjectUsageDTO> returnType = new ParameterizedTypeReference<StatusProjectUsageDTO>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk get statuses
     * Returns a list of the statuses specified by one or more status IDs.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param id The list of status IDs. To include multiple IDs, provide an ampersand-separated list. For example, id&#x3D;10000&amp;id&#x3D;10001.  Min items &#x60;1&#x60;, Max items &#x60;50&#x60; (required)
     * @param expand Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;usages&#x60; Returns the project and issue types that use the status in their workflow.  *  &#x60;workflowUsages&#x60; Returns the workflows that use the status. (optional)
     * @return List&lt;JiraStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<JiraStatus> getStatusesById(List<String> id, String expand) throws RestClientException {
        return getStatusesByIdWithHttpInfo(id, expand).getBody();
    }

    /**
     * Bulk get statuses
     * Returns a list of the statuses specified by one or more status IDs.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param id The list of status IDs. To include multiple IDs, provide an ampersand-separated list. For example, id&#x3D;10000&amp;id&#x3D;10001.  Min items &#x60;1&#x60;, Max items &#x60;50&#x60; (required)
     * @param expand Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;usages&#x60; Returns the project and issue types that use the status in their workflow.  *  &#x60;workflowUsages&#x60; Returns the workflows that use the status. (optional)
     * @return ResponseEntity&lt;List&lt;JiraStatus&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<JiraStatus>> getStatusesByIdWithHttpInfo(List<String> id, String expand) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getStatusesById");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuses").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "id", id));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<List<JiraStatus>> returnType = new ParameterizedTypeReference<List<JiraStatus>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get workflow usages by status
     * Returns a page of workflows using a given status.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the status with the given ID does not exist.
     * @param statusId The statusId to fetch workflow usages for (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return StatusWorkflowUsageDTO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StatusWorkflowUsageDTO getWorkflowUsagesForStatus(String statusId, String nextPageToken, Integer maxResults) throws RestClientException {
        return getWorkflowUsagesForStatusWithHttpInfo(statusId, nextPageToken, maxResults).getBody();
    }

    /**
     * Get workflow usages by status
     * Returns a page of workflows using a given status.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the status with the given ID does not exist.
     * @param statusId The statusId to fetch workflow usages for (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return ResponseEntity&lt;StatusWorkflowUsageDTO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StatusWorkflowUsageDTO> getWorkflowUsagesForStatusWithHttpInfo(String statusId, String nextPageToken, Integer maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'statusId' is set
        if (statusId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'statusId' when calling getWorkflowUsagesForStatus");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("statusId", statusId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuses/{statusId}/workflowUsages").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nextPageToken", nextPageToken));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<StatusWorkflowUsageDTO> returnType = new ParameterizedTypeReference<StatusWorkflowUsageDTO>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Search statuses paginated
     * Returns a [paginated](https://developer.atlassian.com/cloud/jira/platform/rest/v3/intro/#pagination) list of statuses that match a search on name or project.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param expand Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;usages&#x60; Returns the project and issue types that use the status in their workflow.  *  &#x60;workflowUsages&#x60; Returns the workflows that use the status. (optional)
     * @param projectId The project the status is part of or null for global statuses. (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 200)
     * @param searchString Term to match status names against or null to search for all statuses in the search scope. (optional)
     * @param statusCategory Category of the status to filter by. The supported values are: &#x60;TODO&#x60;, &#x60;IN_PROGRESS&#x60;, and &#x60;DONE&#x60;. (optional)
     * @return PageOfStatuses
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageOfStatuses search(String expand, String projectId, Long startAt, Integer maxResults, String searchString, String statusCategory) throws RestClientException {
        return searchWithHttpInfo(expand, projectId, startAt, maxResults, searchString, statusCategory).getBody();
    }

    /**
     * Search statuses paginated
     * Returns a [paginated](https://developer.atlassian.com/cloud/jira/platform/rest/v3/intro/#pagination) list of statuses that match a search on name or project.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param expand Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;usages&#x60; Returns the project and issue types that use the status in their workflow.  *  &#x60;workflowUsages&#x60; Returns the workflows that use the status. (optional)
     * @param projectId The project the status is part of or null for global statuses. (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 200)
     * @param searchString Term to match status names against or null to search for all statuses in the search scope. (optional)
     * @param statusCategory Category of the status to filter by. The supported values are: &#x60;TODO&#x60;, &#x60;IN_PROGRESS&#x60;, and &#x60;DONE&#x60;. (optional)
     * @return ResponseEntity&lt;PageOfStatuses&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageOfStatuses> searchWithHttpInfo(String expand, String projectId, Long startAt, Integer maxResults, String searchString, String statusCategory) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuses/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "projectId", projectId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "searchString", searchString));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "statusCategory", statusCategory));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageOfStatuses> returnType = new ParameterizedTypeReference<PageOfStatuses>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk update statuses
     * Updates statuses by ID.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>409</b> - Returned if another workflow configuration update task is ongoing.
     * @param body The list of statuses that will be updated. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object updateStatuses(StatusUpdateRequest body) throws RestClientException {
        return updateStatusesWithHttpInfo(body).getBody();
    }

    /**
     * Bulk update statuses
     * Updates statuses by ID.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>409</b> - Returned if another workflow configuration update task is ongoing.
     * @param body The list of statuses that will be updated. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> updateStatusesWithHttpInfo(StatusUpdateRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateStatuses");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/statuses").build().toUriString();
        
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