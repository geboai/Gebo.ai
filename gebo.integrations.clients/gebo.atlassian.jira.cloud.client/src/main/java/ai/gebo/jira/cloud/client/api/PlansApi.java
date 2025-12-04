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
 * Class for interacting with Jira plans through the Jira Cloud REST API.
 * This API provides methods to create, retrieve, update, archive, trash, and duplicate plans in Jira.
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
import ai.gebo.jira.cloud.client.model.CreatePlanRequest;
import ai.gebo.jira.cloud.client.model.DuplicatePlanRequest;
import ai.gebo.jira.cloud.client.model.GetPlanResponse;
import ai.gebo.jira.cloud.client.model.PageWithCursorGetPlanResponseForPage;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.PlansApi")
public class PlansApi {
    private ApiClient apiClient;

    /**
     * Constructor for PlansApi that accepts an ApiClient instance.
     * @param apiClient The API client to use for HTTP requests
     */
    public PlansApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the configured API client.
     * @return The current API client
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to use for HTTP requests.
     * @param apiClient The API client to set
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Archive plan
     * Archives a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object archivePlan(Long planId) throws RestClientException {
        return archivePlanWithHttpInfo(planId).getBody();
    }

    /**
     * Archive plan
     * Archives a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> archivePlanWithHttpInfo(Long planId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling archivePlan");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/archive").buildAndExpand(uriVariables).toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create plan
     * Creates a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * @param body  (required)
     * @param useGroupId Whether to accept group IDs instead of group names. Group names are deprecated. (optional, default to false)
     * @return Long
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Long createPlan(CreatePlanRequest body, Boolean useGroupId) throws RestClientException {
        return createPlanWithHttpInfo(body, useGroupId).getBody();
    }

    /**
     * Create plan
     * Creates a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * @param body  (required)
     * @param useGroupId Whether to accept group IDs instead of group names. Group names are deprecated. (optional, default to false)
     * @return ResponseEntity&lt;Long&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Long> createPlanWithHttpInfo(CreatePlanRequest body, Boolean useGroupId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createPlan");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "useGroupId", useGroupId));

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
     * Duplicate plan
     * Duplicates a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan to duplicate is not found.
     * <p><b>409</b> - Returned if the plan to duplicate is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @return Long
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Long duplicatePlan(DuplicatePlanRequest body, Long planId) throws RestClientException {
        return duplicatePlanWithHttpInfo(body, planId).getBody();
    }

    /**
     * Duplicate plan
     * Duplicates a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan to duplicate is not found.
     * <p><b>409</b> - Returned if the plan to duplicate is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @return ResponseEntity&lt;Long&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Long> duplicatePlanWithHttpInfo(DuplicatePlanRequest body, Long planId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling duplicatePlan");
        }
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling duplicatePlan");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/duplicate").buildAndExpand(uriVariables).toUriString();
        
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
     * Get plan
     * Returns a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * @param planId The ID of the plan. (required)
     * @param useGroupId Whether to return group IDs instead of group names. Group names are deprecated. (optional, default to false)
     * @return GetPlanResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GetPlanResponse getPlan(Long planId, Boolean useGroupId) throws RestClientException {
        return getPlanWithHttpInfo(planId, useGroupId).getBody();
    }

    /**
     * Get plan
     * Returns a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * @param planId The ID of the plan. (required)
     * @param useGroupId Whether to return group IDs instead of group names. Group names are deprecated. (optional, default to false)
     * @return ResponseEntity&lt;GetPlanResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GetPlanResponse> getPlanWithHttpInfo(Long planId, Boolean useGroupId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling getPlan");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "useGroupId", useGroupId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<GetPlanResponse> returnType = new ParameterizedTypeReference<GetPlanResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get plans paginated
     * Returns a [paginated](#pagination) list of plans.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * @param includeTrashed Whether to include trashed plans in the results. (optional, default to false)
     * @param includeArchived Whether to include archived plans in the results. (optional, default to false)
     * @param cursor The cursor to start from. If not provided, the first page will be returned. (optional)
     * @param maxResults The maximum number of plans to return per page. The maximum value is 50. The default value is 50. (optional, default to 50)
     * @return PageWithCursorGetPlanResponseForPage
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageWithCursorGetPlanResponseForPage getPlans(Boolean includeTrashed, Boolean includeArchived, String cursor, Integer maxResults) throws RestClientException {
        return getPlansWithHttpInfo(includeTrashed, includeArchived, cursor, maxResults).getBody();
    }

    /**
     * Get plans paginated
     * Returns a [paginated](#pagination) list of plans.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * @param includeTrashed Whether to include trashed plans in the results. (optional, default to false)
     * @param includeArchived Whether to include archived plans in the results. (optional, default to false)
     * @param cursor The cursor to start from. If not provided, the first page will be returned. (optional)
     * @param maxResults The maximum number of plans to return per page. The maximum value is 50. The default value is 50. (optional, default to 50)
     * @return ResponseEntity&lt;PageWithCursorGetPlanResponseForPage&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageWithCursorGetPlanResponseForPage> getPlansWithHttpInfo(Boolean includeTrashed, Boolean includeArchived, String cursor, Integer maxResults) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "includeTrashed", includeTrashed));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "includeArchived", includeArchived));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "cursor", cursor));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageWithCursorGetPlanResponseForPage> returnType = new ParameterizedTypeReference<PageWithCursorGetPlanResponseForPage>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Trash plan
     * Moves a plan to trash.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object trashPlan(Long planId) throws RestClientException {
        return trashPlanWithHttpInfo(planId).getBody();
    }

    /**
     * Trash plan
     * Moves a plan to trash.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param planId The ID of the plan. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> trashPlanWithHttpInfo(Long planId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling trashPlan");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}/trash").buildAndExpand(uriVariables).toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update plan
     * Updates any of the following details of a plan using [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902).   *  name  *  leadAccountId  *  scheduling           *  estimation with StoryPoints, Days or Hours as possible values      *  startDate                   *  type with DueDate, TargetStartDate, TargetEndDate or DateCustomField as possible values          *  dateCustomFieldId      *  endDate                   *  type with DueDate, TargetStartDate, TargetEndDate or DateCustomField as possible values          *  dateCustomFieldId      *  inferredDates with None, SprintDates or ReleaseDates as possible values      *  dependencies with Sequential or Concurrent as possible values  *  issueSources           *  type with Board, Project or Filter as possible values      *  value  *  exclusionRules           *  numberOfDaysToShowCompletedIssues      *  issueIds      *  workStatusIds      *  workStatusCategoryIds      *  issueTypeIds      *  releaseIds  *  crossProjectReleases           *  name      *  releaseIds  *  customFields           *  customFieldId      *  filter  *  permissions           *  type with View or Edit as possible values      *  holder                   *  type with Group or AccountId as possible values          *  value  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *Note that \&quot;add\&quot; operations do not respect array indexes in target locations. Call the \&quot;Get plan\&quot; endpoint to find out the order of array elements.*
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @param useGroupId Whether to accept group IDs instead of group names. Group names are deprecated. (optional, default to false)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object updatePlan(Object body, Long planId, Boolean useGroupId) throws RestClientException {
        return updatePlanWithHttpInfo(body, planId, useGroupId).getBody();
    }

    /**
     * Update plan
     * Updates any of the following details of a plan using [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902).   *  name  *  leadAccountId  *  scheduling           *  estimation with StoryPoints, Days or Hours as possible values      *  startDate                   *  type with DueDate, TargetStartDate, TargetEndDate or DateCustomField as possible values          *  dateCustomFieldId      *  endDate                   *  type with DueDate, TargetStartDate, TargetEndDate or DateCustomField as possible values          *  dateCustomFieldId      *  inferredDates with None, SprintDates or ReleaseDates as possible values      *  dependencies with Sequential or Concurrent as possible values  *  issueSources           *  type with Board, Project or Filter as possible values      *  value  *  exclusionRules           *  numberOfDaysToShowCompletedIssues      *  issueIds      *  workStatusIds      *  workStatusCategoryIds      *  issueTypeIds      *  releaseIds  *  crossProjectReleases           *  name      *  releaseIds  *  customFields           *  customFieldId      *  filter  *  permissions           *  type with View or Edit as possible values      *  holder                   *  type with Group or AccountId as possible values          *  value  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *Note that \&quot;add\&quot; operations do not respect array indexes in target locations. Call the \&quot;Get plan\&quot; endpoint to find out the order of array elements.*
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the user is not logged in.
     * <p><b>403</b> - Returned if the user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the plan is not found.
     * <p><b>409</b> - Returned if the plan is not active.
     * @param body  (required)
     * @param planId The ID of the plan. (required)
     * @param useGroupId Whether to accept group IDs instead of group names. Group names are deprecated. (optional, default to false)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> updatePlanWithHttpInfo(Object body, Long planId, Boolean useGroupId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updatePlan");
        }
        // verify the required parameter 'planId' is set
        if (planId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'planId' when calling updatePlan");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("planId", planId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/plans/plan/{planId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "useGroupId", useGroupId));

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