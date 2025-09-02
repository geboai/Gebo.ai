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
 * Represents the API for managing teams in Jira Cloud planning. This class provides
 * methods to create, retrieve, update, and delete teams (both Atlassian teams and plan-only teams)
 * that are associated with plans.
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
import ai.gebo.jira.cloud.client.model.AddAtlassianTeamRequest;
import ai.gebo.jira.cloud.client.model.CreatePlanOnlyTeamRequest;
import ai.gebo.jira.cloud.client.model.GetAtlassianTeamResponse;
import ai.gebo.jira.cloud.client.model.GetPlanOnlyTeamResponse;
import ai.gebo.jira.cloud.client.model.PageWithCursorGetTeamResponseForPage;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

public class TeamsInPlanApi {
    private ApiClient apiClient;

    /**
     * Constructs a new TeamsInPlanApi instance with a default API client.
     */
    public TeamsInPlanApi() {
        this(new ApiClient());
    }

    /**
     * Constructs a new TeamsInPlanApi instance with the provided API client.
     * 
     * @param apiClient The API client to use for HTTP requests
     */
    //@Autowired
    public TeamsInPlanApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the current API client instance.
     * 
     * @return The current API client
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to use for HTTP requests.
     * 
     * @param apiClient The API client to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add Atlassian team to plan
     * Adds an existing Atlassian team to a plan and configures their plannning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or Atlassian team is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object addAtlassianTeam(AddAtlassianTeamRequest body, Long planId) throws RestClientException {
        return addAtlassianTeamWithHttpInfo(body, planId).getBody();
    }

    /**
     * Add Atlassian team to plan
     * Adds an existing Atlassian team to a plan and configures their plannning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or Atlassian team is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> addAtlassianTeamWithHttpInfo(AddAtlassianTeamRequest body, Long planId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling addAtlassianTeam");
        }
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling addAtlassianTeam");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/team/atlassian").buildAndExpand(uriVariables).toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create plan-only team
     * Creates a plan-only team and configures their planning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @return Long
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Long createPlanOnlyTeam(CreatePlanOnlyTeamRequest body, Long planId) throws RestClientException {
        return createPlanOnlyTeamWithHttpInfo(body, planId).getBody();
    }

    /**
     * Create plan-only team
     * Creates a plan-only team and configures their planning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @return ResponseEntity&lt;Long&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Long> createPlanOnlyTeamWithHttpInfo(CreatePlanOnlyTeamRequest body, Long planId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createPlanOnlyTeam");
        }
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling createPlanOnlyTeam");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/team/planonly").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Long> returnType = new ParameterizedTypeReference<Long>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete plan-only team
     * Deletes a plan-only team and their planning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or plan-only team is not found, or the plan-only team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @param planOnlyTeamId The ID of the plan-only team. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object deletePlanOnlyTeam(Long planId, Long planOnlyTeamId) throws RestClientException {
        return deletePlanOnlyTeamWithHttpInfo(planId, planOnlyTeamId).getBody();
    }

    /**
     * Delete plan-only team
     * Deletes a plan-only team and their planning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or plan-only team is not found, or the plan-only team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @param planOnlyTeamId The ID of the plan-only team. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deletePlanOnlyTeamWithHttpInfo(Long planId, Long planOnlyTeamId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling deletePlanOnlyTeam");
        }
        // verify the required parameter 'planOnlyTeamId' is set
        if (planOnlyTeamId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planOnlyTeamId' when calling deletePlanOnlyTeam");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        uriVariables.put("planOnlyTeamId", planOnlyTeamId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/team/planonly/{planOnlyTeamId}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get Atlassian team in plan
     * Returns planning settings for an Atlassian team in a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or Atlassian team is not found, or the Atlassian team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @param atlassianTeamId The ID of the Atlassian team. (required)
     * @return GetAtlassianTeamResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GetAtlassianTeamResponse getAtlassianTeam(Long planId, String atlassianTeamId) throws RestClientException {
        return getAtlassianTeamWithHttpInfo(planId, atlassianTeamId).getBody();
    }

    /**
     * Get Atlassian team in plan
     * Returns planning settings for an Atlassian team in a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or Atlassian team is not found, or the Atlassian team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @param atlassianTeamId The ID of the Atlassian team. (required)
     * @return ResponseEntity&lt;GetAtlassianTeamResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GetAtlassianTeamResponse> getAtlassianTeamWithHttpInfo(Long planId, String atlassianTeamId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling getAtlassianTeam");
        }
        // verify the required parameter 'atlassianTeamId' is set
        if (atlassianTeamId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'atlassianTeamId' when calling getAtlassianTeam");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        uriVariables.put("atlassianTeamId", atlassianTeamId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/team/atlassian/{atlassianTeamId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<GetAtlassianTeamResponse> returnType = new ParameterizedTypeReference<GetAtlassianTeamResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get plan-only team
     * Returns planning settings for a plan-only team.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or plan-only team is not found, or the plan-only team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @param planOnlyTeamId The ID of the plan-only team. (required)
     * @return GetPlanOnlyTeamResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GetPlanOnlyTeamResponse getPlanOnlyTeam(Long planId, Long planOnlyTeamId) throws RestClientException {
        return getPlanOnlyTeamWithHttpInfo(planId, planOnlyTeamId).getBody();
    }

    /**
     * Get plan-only team
     * Returns planning settings for a plan-only team.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or plan-only team is not found, or the plan-only team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @param planOnlyTeamId The ID of the plan-only team. (required)
     * @return ResponseEntity&lt;GetPlanOnlyTeamResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GetPlanOnlyTeamResponse> getPlanOnlyTeamWithHttpInfo(Long planId, Long planOnlyTeamId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling getPlanOnlyTeam");
        }
        // verify the required parameter 'planOnlyTeamId' is set
        if (planOnlyTeamId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planOnlyTeamId' when calling getPlanOnlyTeam");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        uriVariables.put("planOnlyTeamId", planOnlyTeamId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/team/planonly/{planOnlyTeamId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<GetPlanOnlyTeamResponse> returnType = new ParameterizedTypeReference<GetPlanOnlyTeamResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get teams in plan paginated
     * Returns a [paginated](#pagination) list of plan-only and Atlassian teams in a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * @param planId The ID of the plan. (required)
     * @param cursor The cursor to start from. If not provided, the first page will be returned. (optional)
     * @param maxResults The maximum number of plan teams to return per page. The maximum value is 50. The default value is 50. (optional, default to 50)
     * @return PageWithCursorGetTeamResponseForPage
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageWithCursorGetTeamResponseForPage getTeams(Long planId, String cursor, Integer maxResults) throws RestClientException {
        return getTeamsWithHttpInfo(planId, cursor, maxResults).getBody();
    }

    /**
     * Get teams in plan paginated
     * Returns a [paginated](#pagination) list of plan-only and Atlassian teams in a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * @param planId The ID of the plan. (required)
     * @param cursor The cursor to start from. If not provided, the first page will be returned. (optional)
     * @param maxResults The maximum number of plan teams to return per page. The maximum value is 50. The default value is 50. (optional, default to 50)
     * @return ResponseEntity&lt;PageWithCursorGetTeamResponseForPage&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageWithCursorGetTeamResponseForPage> getTeamsWithHttpInfo(Long planId, String cursor, Integer maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling getTeams");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/team").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cursor", cursor));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageWithCursorGetTeamResponseForPage> returnType = new ParameterizedTypeReference<PageWithCursorGetTeamResponseForPage>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Remove Atlassian team from plan
     * Removes an Atlassian team from a plan and deletes their planning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or Atlassian team is not found, or the Atlassian team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @param atlassianTeamId The ID of the Atlassian team. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object removeAtlassianTeam(Long planId, String atlassianTeamId) throws RestClientException {
        return removeAtlassianTeamWithHttpInfo(planId, atlassianTeamId).getBody();
    }

    /**
     * Remove Atlassian team from plan
     * Removes an Atlassian team from a plan and deletes their planning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or Atlassian team is not found, or the Atlassian team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @param atlassianTeamId The ID of the Atlassian team. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> removeAtlassianTeamWithHttpInfo(Long planId, String atlassianTeamId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling removeAtlassianTeam");
        }
        // verify the required parameter 'atlassianTeamId' is set
        if (atlassianTeamId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'atlassianTeamId' when calling removeAtlassianTeam");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        uriVariables.put("atlassianTeamId", atlassianTeamId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/team/atlassian/{atlassianTeamId}").buildAndExpand(uriVariables).toUriString();
        
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
     * Update Atlassian team in plan
     * Updates any of the following planning settings of an Atlassian team in a plan using [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902).   *  planningStyle  *  issueSourceId  *  sprintLength  *  capacity  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *Note that \&quot;add\&quot; operations do not respect array indexes in target locations. Call the \&quot;Get Atlassian team in plan\&quot; endpoint to find out the order of array elements.*
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or Atlassian team is not found, or the Atlassian team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @param atlassianTeamId The ID of the Atlassian team. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object updateAtlassianTeam(Object body, Long planId, String atlassianTeamId) throws RestClientException {
        return updateAtlassianTeamWithHttpInfo(body, planId, atlassianTeamId).getBody();
    }

    /**
     * Update Atlassian team in plan
     * Updates any of the following planning settings of an Atlassian team in a plan using [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902).   *  planningStyle  *  issueSourceId  *  sprintLength  *  capacity  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *Note that \&quot;add\&quot; operations do not respect array indexes in target locations. Call the \&quot;Get Atlassian team in plan\&quot; endpoint to find out the order of array elements.*
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or Atlassian team is not found, or the Atlassian team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @param atlassianTeamId The ID of the Atlassian team. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> updateAtlassianTeamWithHttpInfo(Object body, Long planId, String atlassianTeamId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateAtlassianTeam");
        }
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling updateAtlassianTeam");
        }
        // verify the required parameter 'atlassianTeamId' is set
        if (atlassianTeamId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'atlassianTeamId' when calling updateAtlassianTeam");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        uriVariables.put("atlassianTeamId", atlassianTeamId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/team/atlassian/{atlassianTeamId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json-patch+json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update plan-only team
     * Updates any of the following planning settings of a plan-only team using [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902).   *  name  *  planningStyle  *  issueSourceId  *  sprintLength  *  capacity  *  memberAccountIds  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *Note that \&quot;add\&quot; operations do not respect array indexes in target locations. Call the \&quot;Get plan-only team\&quot; endpoint to find out the order of array elements.*
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or plan-only team is not found, or the plan-only team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @param planOnlyTeamId The ID of the plan-only team. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object updatePlanOnlyTeam(Object body, Long planId, Long planOnlyTeamId) throws RestClientException {
        return updatePlanOnlyTeamWithHttpInfo(body, planId, planOnlyTeamId).getBody();
    }

    /**
     * Update plan-only team
     * Updates any of the following planning settings of a plan-only team using [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902).   *  name  *  planningStyle  *  issueSourceId  *  sprintLength  *  capacity  *  memberAccountIds  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *Note that \&quot;add\&quot; operations do not respect array indexes in target locations. Call the \&quot;Get plan-only team\&quot; endpoint to find out the order of array elements.*
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan or plan-only team is not found, or the plan-only team is not associated with the plan.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @param planOnlyTeamId The ID of the plan-only team. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> updatePlanOnlyTeamWithHttpInfo(Object body, Long planId, Long planOnlyTeamId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updatePlanOnlyTeam");
        }
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling updatePlanOnlyTeam");
        }
        // verify the required parameter 'planOnlyTeamId' is set
        if (planOnlyTeamId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planOnlyTeamId' when calling updatePlanOnlyTeam");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        uriVariables.put("planOnlyTeamId", planOnlyTeamId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/team/planonly/{planOnlyTeamId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json-patch+json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}