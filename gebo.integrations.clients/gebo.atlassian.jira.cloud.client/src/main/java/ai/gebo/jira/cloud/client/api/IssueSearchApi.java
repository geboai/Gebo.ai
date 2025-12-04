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
 * This class provides an API client for searching and retrieving Jira issues using various search methods.
 * The client interacts with Jira's REST API to perform operations such as JQL searches, issue ID searches, 
 * and issue picking suggestions.
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
import ai.gebo.jira.cloud.client.model.IdSearchRequestBean;
import ai.gebo.jira.cloud.client.model.IdSearchResults;
import ai.gebo.jira.cloud.client.model.IssueMatches;
import ai.gebo.jira.cloud.client.model.IssuePickerSuggestions;
import ai.gebo.jira.cloud.client.model.IssuesAndJQLQueries;
import ai.gebo.jira.cloud.client.model.JQLCountRequestBean;
import ai.gebo.jira.cloud.client.model.JQLCountResultsBean;
import ai.gebo.jira.cloud.client.model.SearchAndReconcileRequestBean;
import ai.gebo.jira.cloud.client.model.SearchAndReconcileResults;
import ai.gebo.jira.cloud.client.model.SearchRequestBean;
import ai.gebo.jira.cloud.client.model.SearchResults;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.IssueSearchApi")
public class IssueSearchApi {
    private ApiClient apiClient;

    /**
     * Default constructor that initializes with a new ApiClient instance
     */
    public IssueSearchApi() {
        this(new ApiClient());
    }

    /**
     * Constructor that accepts a specific ApiClient
     * @param apiClient the API client to use for requests
     */
    //@Autowired
    public IssueSearchApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the current API client
     * @return the current ApiClient instance
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to use for requests
     * @param apiClient the API client to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Count issues using JQL
     * Provide an estimated count of the issues that match the [JQL](https://confluence.atlassian.com/x/egORLQ). Recent updates might not be immediately visible in the returned output. This endpoint requires JQL to be bounded.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the JQL query cannot be parsed.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param body A JSON object containing the search request. (required)
     * @return JQLCountResultsBean
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JQLCountResultsBean countIssues(JQLCountRequestBean body) throws RestClientException {
        return countIssuesWithHttpInfo(body).getBody();
    }

    /**
     * Count issues using JQL
     * Provide an estimated count of the issues that match the [JQL](https://confluence.atlassian.com/x/egORLQ). Recent updates might not be immediately visible in the returned output. This endpoint requires JQL to be bounded.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the JQL query cannot be parsed.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param body A JSON object containing the search request. (required)
     * @return ResponseEntity&lt;JQLCountResultsBean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JQLCountResultsBean> countIssuesWithHttpInfo(JQLCountRequestBean body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling countIssues");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/search/approximate-count").build().toUriString();
        
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

        ParameterizedTypeReference<JQLCountResultsBean> returnType = new ParameterizedTypeReference<JQLCountResultsBean>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get issue picker suggestions
     * Returns lists of issues matching a query string. Use this resource to provide auto-completion suggestions when the user is looking for an issue using a word or string.  This operation returns two lists:   *  &#x60;History Search&#x60; which includes issues from the user&#x27;s history of created, edited, or viewed issues that contain the string in the &#x60;query&#x60; parameter.  *  &#x60;Current Search&#x60; which includes issues that match the JQL expression in &#x60;currentJQL&#x60; and contain the string in the &#x60;query&#x60; parameter.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param query A string to match against text fields in the issue such as title, description, or comments. (optional)
     * @param currentJQL A JQL query defining a list of issues to search for the query term. Note that &#x60;username&#x60; and &#x60;userkey&#x60; cannot be used as search terms for this parameter, due to privacy reasons. Use &#x60;accountId&#x60; instead. (optional)
     * @param currentIssueKey The key of an issue to exclude from search results. For example, the issue the user is viewing when they perform this query. (optional)
     * @param currentProjectId The ID of a project that suggested issues must belong to. (optional)
     * @param showSubTasks Indicate whether to include subtasks in the suggestions list. (optional)
     * @param showSubTaskParent When &#x60;currentIssueKey&#x60; is a subtask, whether to include the parent issue in the suggestions if it matches the query. (optional)
     * @return IssuePickerSuggestions
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IssuePickerSuggestions getIssuePickerResource(String query, String currentJQL, String currentIssueKey, String currentProjectId, Boolean showSubTasks, Boolean showSubTaskParent) throws RestClientException {
        return getIssuePickerResourceWithHttpInfo(query, currentJQL, currentIssueKey, currentProjectId, showSubTasks, showSubTaskParent).getBody();
    }

    /**
     * Get issue picker suggestions
     * Returns lists of issues matching a query string. Use this resource to provide auto-completion suggestions when the user is looking for an issue using a word or string.  This operation returns two lists:   *  &#x60;History Search&#x60; which includes issues from the user&#x27;s history of created, edited, or viewed issues that contain the string in the &#x60;query&#x60; parameter.  *  &#x60;Current Search&#x60; which includes issues that match the JQL expression in &#x60;currentJQL&#x60; and contain the string in the &#x60;query&#x60; parameter.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param query A string to match against text fields in the issue such as title, description, or comments. (optional)
     * @param currentJQL A JQL query defining a list of issues to search for the query term. Note that &#x60;username&#x60; and &#x60;userkey&#x60; cannot be used as search terms for this parameter, due to privacy reasons. Use &#x60;accountId&#x60; instead. (optional)
     * @param currentIssueKey The key of an issue to exclude from search results. For example, the issue the user is viewing when they perform this query. (optional)
     * @param currentProjectId The ID of a project that suggested issues must belong to. (optional)
     * @param showSubTasks Indicate whether to include subtasks in the suggestions list. (optional)
     * @param showSubTaskParent When &#x60;currentIssueKey&#x60; is a subtask, whether to include the parent issue in the suggestions if it matches the query. (optional)
     * @return ResponseEntity&lt;IssuePickerSuggestions&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IssuePickerSuggestions> getIssuePickerResourceWithHttpInfo(String query, String currentJQL, String currentIssueKey, String currentProjectId, Boolean showSubTasks, Boolean showSubTaskParent) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issue/picker").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "currentJQL", currentJQL));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "currentIssueKey", currentIssueKey));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "currentProjectId", currentProjectId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "showSubTasks", showSubTasks));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "showSubTaskParent", showSubTaskParent));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<IssuePickerSuggestions> returnType = new ParameterizedTypeReference<IssuePickerSuggestions>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Check issues against JQL
     * Checks whether one or more issues would be returned by one or more JQL queries.  **[Permissions](#permissions) required:** None, however, issues are only matched against JQL queries where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if &#x60;jqls&#x60; exceeds the maximum number of JQL queries or &#x60;issueIds&#x60; exceeds the maximum number of issue IDs.
     * @param body  (required)
     * @return IssueMatches
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IssueMatches matchIssues(IssuesAndJQLQueries body) throws RestClientException {
        return matchIssuesWithHttpInfo(body).getBody();
    }

    /**
     * Check issues against JQL
     * Checks whether one or more issues would be returned by one or more JQL queries.  **[Permissions](#permissions) required:** None, however, issues are only matched against JQL queries where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if &#x60;jqls&#x60; exceeds the maximum number of JQL queries or &#x60;issueIds&#x60; exceeds the maximum number of issue IDs.
     * @param body  (required)
     * @return ResponseEntity&lt;IssueMatches&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IssueMatches> matchIssuesWithHttpInfo(IssuesAndJQLQueries body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling matchIssues");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/jql/match").build().toUriString();
        
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

        ParameterizedTypeReference<IssueMatches> returnType = new ParameterizedTypeReference<IssueMatches>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Search for issues using JQL enhanced search (GET)
     * Searches for issues using [JQL](https://confluence.atlassian.com/x/egORLQ). Recent updates might not be immediately visible in the returned search results. If you need [read-after-write](https://developer.atlassian.com/cloud/jira/platform/search-and-reconcile/) consistency, you can utilize the &#x60;reconcileIssues&#x60; parameter to ensure stronger consistency assurances. This operation can be accessed anonymously.  If the JQL query expression is too large to be encoded as a query parameter, use the [POST](#api-rest-api-3-search-post) version of this resource.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the search request is invalid
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param jql A [JQL](https://confluence.atlassian.com/x/egORLQ) expression. For performance reasons, this parameter requires a bounded query. A bounded query is a query with a search restriction.   *  Example of an unbounded query: &#x60;order by key desc&#x60;.  *  Example of a bounded query: &#x60;assignee &#x3D; currentUser() order by key&#x60;.  Additionally, &#x60;orderBy&#x60; clause can contain a maximum of 7 fields. (optional)
     * @param nextPageToken The token for a page to fetch that is not the first page. The first page has a &#x60;nextPageToken&#x60; of &#x60;null&#x60;. Use the &#x60;nextPageToken&#x60; to fetch the next page of issues.  Note: The &#x60;nextPageToken&#x60; field is **not included** in the response for the last page, indicating there is no next page. (optional)
     * @param maxResults The maximum number of items to return per page. To manage page size, API may return fewer items per page where a large number of fields or properties are requested. The greatest number of items returned per page is achieved when requesting &#x60;id&#x60; or &#x60;key&#x60; only. It returns max 5000 issues. (optional, default to 50)
     * @param fields A list of fields to return for each issue, use it to retrieve a subset of fields. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;*all&#x60; Returns all fields.  *  &#x60;*navigable&#x60; Returns navigable fields.  *  &#x60;id&#x60; Returns only issue IDs.  *  Any issue field, prefixed with a minus to exclude.  The default is &#x60;id&#x60;.  Examples:   *  &#x60;summary,comment&#x60; Returns only the summary and comments fields only.  *  &#x60;-description&#x60; Returns all navigable (default) fields except description.  *  &#x60;*all,-comment&#x60; Returns all fields except comments.  Multiple &#x60;fields&#x60; parameters can be included in a request.  Note: By default, this resource returns IDs only. This differs from [GET issue](#api-rest-api-3-issue-issueIdOrKey-get) where the default is all fields. (optional)
     * @param expand Use [expand](#expansion) to include additional information about issues in the response. Note that, unlike the majority of instances where &#x60;expand&#x60; is specified, &#x60;expand&#x60; is defined as a comma-delimited string of values. The expand options are:   *  &#x60;renderedFields&#x60; Returns field values rendered in HTML format.  *  &#x60;names&#x60; Returns the display name of each field.  *  &#x60;schema&#x60; Returns the schema describing a field type.  *  &#x60;transitions&#x60; Returns all possible transitions for the issue.  *  &#x60;operations&#x60; Returns all possible operations for the issue.  *  &#x60;editmeta&#x60; Returns information about how each field can be edited.  *  &#x60;changelog&#x60; Returns a list of recent updates to an issue, sorted by date, starting from the most recent.  *  &#x60;versionedRepresentations&#x60; Instead of &#x60;fields&#x60;, returns &#x60;versionedRepresentations&#x60; a JSON array containing each version of a field&#x27;s value, with the highest numbered item representing the most recent version.  Examples: &#x60;\&quot;names,changelog\&quot;&#x60; Returns the display name of each field as well as a list of recent updates to an issue. (optional)
     * @param properties A list of up to 5 issue properties to include in the results. This parameter accepts a comma-separated list. (optional)
     * @param fieldsByKeys Reference fields by their key (rather than ID). The default is &#x60;false&#x60;. (optional, default to false)
     * @param failFast Fail this request early if we can&#x27;t retrieve all field data. (optional, default to false)
     * @param reconcileIssues Strong consistency issue ids to be reconciled with search results. Accepts max 50 ids (optional)
     * @return SearchAndReconcileResults
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SearchAndReconcileResults searchAndReconsileIssuesUsingJql(String jql, String nextPageToken, Integer maxResults, List<String> fields, String expand, List<String> properties, Boolean fieldsByKeys, Boolean failFast, List<Long> reconcileIssues) throws RestClientException {
        return searchAndReconsileIssuesUsingJqlWithHttpInfo(jql, nextPageToken, maxResults, fields, expand, properties, fieldsByKeys, failFast, reconcileIssues).getBody();
    }

    /**
     * Search for issues using JQL enhanced search (GET)
     * Searches for issues using [JQL](https://confluence.atlassian.com/x/egORLQ). Recent updates might not be immediately visible in the returned search results. If you need [read-after-write](https://developer.atlassian.com/cloud/jira/platform/search-and-reconcile/) consistency, you can utilize the &#x60;reconcileIssues&#x60; parameter to ensure stronger consistency assurances. This operation can be accessed anonymously.  If the JQL query expression is too large to be encoded as a query parameter, use the [POST](#api-rest-api-3-search-post) version of this resource.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the search request is invalid
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param jql A [JQL](https://confluence.atlassian.com/x/egORLQ) expression. For performance reasons, this parameter requires a bounded query. A bounded query is a query with a search restriction.   *  Example of an unbounded query: &#x60;order by key desc&#x60;.  *  Example of a bounded query: &#x60;assignee &#x3D; currentUser() order by key&#x60;.  Additionally, &#x60;orderBy&#x60; clause can contain a maximum of 7 fields. (optional)
     * @param nextPageToken The token for a page to fetch that is not the first page. The first page has a &#x60;nextPageToken&#x60; of &#x60;null&#x60;. Use the &#x60;nextPageToken&#x60; to fetch the next page of issues.  Note: The &#x60;nextPageToken&#x60; field is **not included** in the response for the last page, indicating there is no next page. (optional)
     * @param maxResults The maximum number of items to return per page. To manage page size, API may return fewer items per page where a large number of fields or properties are requested. The greatest number of items returned per page is achieved when requesting &#x60;id&#x60; or &#x60;key&#x60; only. It returns max 5000 issues. (optional, default to 50)
     * @param fields A list of fields to return for each issue, use it to retrieve a subset of fields. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;*all&#x60; Returns all fields.  *  &#x60;*navigable&#x60; Returns navigable fields.  *  &#x60;id&#x60; Returns only issue IDs.  *  Any issue field, prefixed with a minus to exclude.  The default is &#x60;id&#x60;.  Examples:   *  &#x60;summary,comment&#x60; Returns only the summary and comments fields only.  *  &#x60;-description&#x60; Returns all navigable (default) fields except description.  *  &#x60;*all,-comment&#x60; Returns all fields except comments.  Multiple &#x60;fields&#x60; parameters can be included in a request.  Note: By default, this resource returns IDs only. This differs from [GET issue](#api-rest-api-3-issue-issueIdOrKey-get) where the default is all fields. (optional)
     * @param expand Use [expand](#expansion) to include additional information about issues in the response. Note that, unlike the majority of instances where &#x60;expand&#x60; is specified, &#x60;expand&#x60; is defined as a comma-delimited string of values. The expand options are:   *  &#x60;renderedFields&#x60; Returns field values rendered in HTML format.  *  &#x60;names&#x60; Returns the display name of each field.  *  &#x60;schema&#x60; Returns the schema describing a field type.  *  &#x60;transitions&#x60; Returns all possible transitions for the issue.  *  &#x60;operations&#x60; Returns all possible operations for the issue.  *  &#x60;editmeta&#x60; Returns information about how each field can be edited.  *  &#x60;changelog&#x60; Returns a list of recent updates to an issue, sorted by date, starting from the most recent.  *  &#x60;versionedRepresentations&#x60; Instead of &#x60;fields&#x60;, returns &#x60;versionedRepresentations&#x60; a JSON array containing each version of a field&#x27;s value, with the highest numbered item representing the most recent version.  Examples: &#x60;\&quot;names,changelog\&quot;&#x60; Returns the display name of each field as well as a list of recent updates to an issue. (optional)
     * @param properties A list of up to 5 issue properties to include in the results. This parameter accepts a comma-separated list. (optional)
     * @param fieldsByKeys Reference fields by their key (rather than ID). The default is &#x60;false&#x60;. (optional, default to false)
     * @param failFast Fail this request early if we can&#x27;t retrieve all field data. (optional, default to false)
     * @param reconcileIssues Strong consistency issue ids to be reconciled with search results. Accepts max 50 ids (optional)
     * @return ResponseEntity&lt;SearchAndReconcileResults&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchAndReconcileResults> searchAndReconsileIssuesUsingJqlWithHttpInfo(String jql, String nextPageToken, Integer maxResults, List<String> fields, String expand, List<String> properties, Boolean fieldsByKeys, Boolean failFast, List<Long> reconcileIssues) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/search/jql").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "jql", jql));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nextPageToken", nextPageToken));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "fields", fields));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "properties", properties));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "fieldsByKeys", fieldsByKeys));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "failFast", failFast));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "reconcileIssues", reconcileIssues));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<SearchAndReconcileResults> returnType = new ParameterizedTypeReference<SearchAndReconcileResults>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Search for issues using JQL enhanced search (POST)
     * Searches for issues using [JQL](https://confluence.atlassian.com/x/egORLQ). Recent updates might not be immediately visible in the returned search results. If you need [read-after-write](https://developer.atlassian.com/cloud/jira/platform/search-and-reconcile/) consistency, you can utilize the &#x60;reconcileIssues&#x60; parameter to ensure stronger consistency assurances. This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the search request is invalid
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param body  (required)
     * @return SearchAndReconcileResults
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SearchAndReconcileResults searchAndReconsileIssuesUsingJqlPost(SearchAndReconcileRequestBean body) throws RestClientException {
        return searchAndReconsileIssuesUsingJqlPostWithHttpInfo(body).getBody();
    }

    /**
     * Search for issues using JQL enhanced search (POST)
     * Searches for issues using [JQL](https://confluence.atlassian.com/x/egORLQ). Recent updates might not be immediately visible in the returned search results. If you need [read-after-write](https://developer.atlassian.com/cloud/jira/platform/search-and-reconcile/) consistency, you can utilize the &#x60;reconcileIssues&#x60; parameter to ensure stronger consistency assurances. This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the search request is invalid
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param body  (required)
     * @return ResponseEntity&lt;SearchAndReconcileResults&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchAndReconcileResults> searchAndReconsileIssuesUsingJqlPostWithHttpInfo(SearchAndReconcileRequestBean body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling searchAndReconsileIssuesUsingJqlPost");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/search/jql").build().toUriString();
        
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

        ParameterizedTypeReference<SearchAndReconcileResults> returnType = new ParameterizedTypeReference<SearchAndReconcileResults>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Search issue IDs using JQL
     * Searches for IDs of issues using [JQL](https://confluence.atlassian.com/x/egORLQ).  Use the [Search](#api-rest-api-3-search-post) endpoint if you need to fetch more than just issue IDs. The Search endpoint returns more information, but may take much longer to respond to requests. This is because it uses a different mechanism for ordering results than this endpoint and doesn&#x27;t provide the total number of results for your query.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the JQL query is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param body A JSON object containing the search request. (required)
     * @return IdSearchResults
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public IdSearchResults searchForIssuesIds(IdSearchRequestBean body) throws RestClientException {
        return searchForIssuesIdsWithHttpInfo(body).getBody();
    }

    /**
     * Search issue IDs using JQL
     * Searches for IDs of issues using [JQL](https://confluence.atlassian.com/x/egORLQ).  Use the [Search](#api-rest-api-3-search-post) endpoint if you need to fetch more than just issue IDs. The Search endpoint returns more information, but may take much longer to respond to requests. This is because it uses a different mechanism for ordering results than this endpoint and doesn&#x27;t provide the total number of results for your query.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the JQL query is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param body A JSON object containing the search request. (required)
     * @return ResponseEntity&lt;IdSearchResults&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public ResponseEntity<IdSearchResults> searchForIssuesIdsWithHttpInfo(IdSearchRequestBean body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling searchForIssuesIds");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/search/id").build().toUriString();
        
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

        ParameterizedTypeReference<IdSearchResults> returnType = new ParameterizedTypeReference<IdSearchResults>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Search for issues using JQL (GET)
     * Searches for issues using [JQL](https://confluence.atlassian.com/x/egORLQ).  If the JQL query expression is too large to be encoded as a query parameter, use the [POST](#api-rest-api-3-search-post) version of this resource.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the JQL query is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param jql The [JQL](https://confluence.atlassian.com/x/egORLQ) that defines the search. Note:   *  If no JQL expression is provided, all issues are returned.  *  &#x60;username&#x60; and &#x60;userkey&#x60; cannot be used as search terms due to privacy reasons. Use &#x60;accountId&#x60; instead.  *  If a user has hidden their email address in their user profile, partial matches of the email address will not find the user. An exact match is required. (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. To manage page size, Jira may return fewer items per page where a large number of fields or properties are requested. The greatest number of items returned per page is achieved when requesting &#x60;id&#x60; or &#x60;key&#x60; only. (optional, default to 50)
     * @param validateQuery Determines how to validate the JQL query and treat the validation results. Supported values are:   *  &#x60;strict&#x60; Returns a 400 response code if any errors are found, along with a list of all errors (and warnings).  *  &#x60;warn&#x60; Returns all errors as warnings.  *  &#x60;none&#x60; No validation is performed.  *  &#x60;true&#x60; *Deprecated* A legacy synonym for &#x60;strict&#x60;.  *  &#x60;false&#x60; *Deprecated* A legacy synonym for &#x60;warn&#x60;.  Note: If the JQL is not correctly formed a 400 response code is returned, regardless of the &#x60;validateQuery&#x60; value. (optional, default to strict)
     * @param fields A list of fields to return for each issue, use it to retrieve a subset of fields. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;*all&#x60; Returns all fields.  *  &#x60;*navigable&#x60; Returns navigable fields.  *  Any issue field, prefixed with a minus to exclude.  Examples:   *  &#x60;summary,comment&#x60; Returns only the summary and comments fields.  *  &#x60;-description&#x60; Returns all navigable (default) fields except description.  *  &#x60;*all,-comment&#x60; Returns all fields except comments.  This parameter may be specified multiple times. For example, &#x60;fields&#x3D;field1,field2&amp;fields&#x3D;field3&#x60;.  Note: All navigable fields are returned by default. This differs from [GET issue](#api-rest-api-3-issue-issueIdOrKey-get) where the default is all fields. (optional)
     * @param expand Use [expand](#expansion) to include additional information about issues in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;renderedFields&#x60; Returns field values rendered in HTML format.  *  &#x60;names&#x60; Returns the display name of each field.  *  &#x60;schema&#x60; Returns the schema describing a field type.  *  &#x60;transitions&#x60; Returns all possible transitions for the issue.  *  &#x60;operations&#x60; Returns all possible operations for the issue.  *  &#x60;editmeta&#x60; Returns information about how each field can be edited.  *  &#x60;changelog&#x60; Returns a list of recent updates to an issue, sorted by date, starting from the most recent.  *  &#x60;versionedRepresentations&#x60; Instead of &#x60;fields&#x60;, returns &#x60;versionedRepresentations&#x60; a JSON array containing each version of a field&#x27;s value, with the highest numbered item representing the most recent version. (optional)
     * @param properties A list of issue property keys for issue properties to include in the results. This parameter accepts a comma-separated list. Multiple properties can also be provided using an ampersand separated list. For example, &#x60;properties&#x3D;prop1,prop2&amp;properties&#x3D;prop3&#x60;. A maximum of 5 issue property keys can be specified. (optional)
     * @param fieldsByKeys Reference fields by their key (rather than ID). (optional, default to false)
     * @param failFast Whether to fail the request quickly in case of an error while loading fields for an issue. For &#x60;failFast&#x3D;true&#x60;, if one field fails, the entire operation fails. For &#x60;failFast&#x3D;false&#x60;, the operation will continue even if a field fails. It will return a valid response, but without values for the failed field(s). (optional, default to false)
     * @return SearchResults
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public SearchResults searchForIssuesUsingJql(String jql, Integer startAt, Integer maxResults, String validateQuery, List<String> fields, String expand, List<String> properties, Boolean fieldsByKeys, Boolean failFast) throws RestClientException {
        return searchForIssuesUsingJqlWithHttpInfo(jql, startAt, maxResults, validateQuery, fields, expand, properties, fieldsByKeys, failFast).getBody();
    }

    /**
     * Search for issues using JQL (GET)
     * Searches for issues using [JQL](https://confluence.atlassian.com/x/egORLQ).  If the JQL query expression is too large to be encoded as a query parameter, use the [POST](#api-rest-api-3-search-post) version of this resource.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the JQL query is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param jql The [JQL](https://confluence.atlassian.com/x/egORLQ) that defines the search. Note:   *  If no JQL expression is provided, all issues are returned.  *  &#x60;username&#x60; and &#x60;userkey&#x60; cannot be used as search terms due to privacy reasons. Use &#x60;accountId&#x60; instead.  *  If a user has hidden their email address in their user profile, partial matches of the email address will not find the user. An exact match is required. (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. To manage page size, Jira may return fewer items per page where a large number of fields or properties are requested. The greatest number of items returned per page is achieved when requesting &#x60;id&#x60; or &#x60;key&#x60; only. (optional, default to 50)
     * @param validateQuery Determines how to validate the JQL query and treat the validation results. Supported values are:   *  &#x60;strict&#x60; Returns a 400 response code if any errors are found, along with a list of all errors (and warnings).  *  &#x60;warn&#x60; Returns all errors as warnings.  *  &#x60;none&#x60; No validation is performed.  *  &#x60;true&#x60; *Deprecated* A legacy synonym for &#x60;strict&#x60;.  *  &#x60;false&#x60; *Deprecated* A legacy synonym for &#x60;warn&#x60;.  Note: If the JQL is not correctly formed a 400 response code is returned, regardless of the &#x60;validateQuery&#x60; value. (optional, default to strict)
     * @param fields A list of fields to return for each issue, use it to retrieve a subset of fields. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;*all&#x60; Returns all fields.  *  &#x60;*navigable&#x60; Returns navigable fields.  *  Any issue field, prefixed with a minus to exclude.  Examples:   *  &#x60;summary,comment&#x60; Returns only the summary and comments fields.  *  &#x60;-description&#x60; Returns all navigable (default) fields except description.  *  &#x60;*all,-comment&#x60; Returns all fields except comments.  This parameter may be specified multiple times. For example, &#x60;fields&#x3D;field1,field2&amp;fields&#x3D;field3&#x60;.  Note: All navigable fields are returned by default. This differs from [GET issue](#api-rest-api-3-issue-issueIdOrKey-get) where the default is all fields. (optional)
     * @param expand Use [expand](#expansion) to include additional information about issues in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;renderedFields&#x60; Returns field values rendered in HTML format.  *  &#x60;names&#x60; Returns the display name of each field.  *  &#x60;schema&#x60; Returns the schema describing a field type.  *  &#x60;transitions&#x60; Returns all possible transitions for the issue.  *  &#x60;operations&#x60; Returns all possible operations for the issue.  *  &#x60;editmeta&#x60; Returns information about how each field can be edited.  *  &#x60;changelog&#x60; Returns a list of recent updates to an issue, sorted by date, starting from the most recent.  *  &#x60;versionedRepresentations&#x60; Instead of &#x60;fields&#x60;, returns &#x60;versionedRepresentations&#x60; a JSON array containing each version of a field&#x27;s value, with the highest numbered item representing the most recent version. (optional)
     * @param properties A list of issue property keys for issue properties to include in the results. This parameter accepts a comma-separated list. Multiple properties can also be provided using an ampersand separated list. For example, &#x60;properties&#x3D;prop1,prop2&amp;properties&#x3D;prop3&#x60;. A maximum of 5 issue property keys can be specified. (optional)
     * @param fieldsByKeys Reference fields by their key (rather than ID). (optional, default to false)
     * @param failFast Whether to fail the request quickly in case of an error while loading fields for an issue. For &#x60;failFast&#x3D;true&#x60;, if one field fails, the entire operation fails. For &#x60;failFast&#x3D;false&#x60;, the operation will continue even if a field fails. It will return a valid response, but without values for the failed field(s). (optional, default to false)
     * @return ResponseEntity&lt;SearchResults&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public ResponseEntity<SearchResults> searchForIssuesUsingJqlWithHttpInfo(String jql, Integer startAt, Integer maxResults, String validateQuery, List<String> fields, String expand, List<String> properties, Boolean fieldsByKeys, Boolean failFast) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "jql", jql));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "validateQuery", validateQuery));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "fields", fields));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "properties", properties));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "fieldsByKeys", fieldsByKeys));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "failFast", failFast));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<SearchResults> returnType = new ParameterizedTypeReference<SearchResults>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Search for issues using JQL (POST)
     * Searches for issues using [JQL](https://confluence.atlassian.com/x/egORLQ).  There is a [GET](#api-rest-api-3-search-get) version of this resource that can be used for smaller JQL query expressions.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the JQL query is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param body A JSON object containing the search request. (required)
     * @return SearchResults
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public SearchResults searchForIssuesUsingJqlPost(SearchRequestBean body) throws RestClientException {
        return searchForIssuesUsingJqlPostWithHttpInfo(body).getBody();
    }

    /**
     * Search for issues using JQL (POST)
     * Searches for issues using [JQL](https://confluence.atlassian.com/x/egORLQ).  There is a [GET](#api-rest-api-3-search-get) version of this resource that can be used for smaller JQL query expressions.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** Issues are included in the response where the user has:   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the issue.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the JQL query is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * @param body A JSON object containing the search request. (required)
     * @return ResponseEntity&lt;SearchResults&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public ResponseEntity<SearchResults> searchForIssuesUsingJqlPostWithHttpInfo(SearchRequestBean body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling searchForIssuesUsingJqlPost");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/search").build().toUriString();
        
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

        ParameterizedTypeReference<SearchResults> returnType = new ParameterizedTypeReference<SearchResults>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}