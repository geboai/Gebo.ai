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
 * Class representing the GroupsApi client for interacting with the Jira Cloud Groups API.
 * This class provides methods to manage groups, including creation, listing, searching,
 * adding/removing users, and other group-related operations.
 */
package ai.gebo.jira.cloud.client.api;

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
import ai.gebo.jira.cloud.client.model.FoundGroups;
import ai.gebo.jira.cloud.client.model.Group;
import ai.gebo.jira.cloud.client.model.PageBeanGroupDetails;
import ai.gebo.jira.cloud.client.model.PageBeanUserDetails;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.GroupsApi")
public class GroupsApi {
    private ApiClient apiClient;

    /**
     * Default constructor that initializes with a new ApiClient
     */
    public GroupsApi() {
        this(new ApiClient());
    }

    /**
     * Constructor that allows injection of an ApiClient
     * @param apiClient the ApiClient to use for API calls
     */
    //@Autowired
    public GroupsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the current ApiClient instance
     * @return the ApiClient being used for API calls
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the ApiClient to use for API calls
     * @param apiClient the ApiClient to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add user to group
     * Adds a user to a group.  **[Permissions](#permissions) required:** Site administration (that is, member of the *site-admin* [group](https://confluence.atlassian.com/x/24xjL)).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if:   *  &#x60;groupname&#x60; is not provided.  *  &#x60;accountId&#x60; is missing.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing from the request.
     * <p><b>403</b> - Returned if the calling user does not have the necessary permission.
     * <p><b>404</b> - Returned if the group or user are not found.
     * <p><b>429</b> - Returned if rate limiting is being enforced.
     * @param body The user to add to the group. (required)
     * @param groupname As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended to identify a group.   The name of the group. This parameter cannot be used with the &#x60;groupId&#x60; parameter. (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupName&#x60; parameter. (optional)
     * @return Group
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Group addUserToGroup(Map<String, Object> body, String groupname, String groupId) throws RestClientException {
        return addUserToGroupWithHttpInfo(body, groupname, groupId).getBody();
    }

    /**
     * Add user to group
     * Adds a user to a group.  **[Permissions](#permissions) required:** Site administration (that is, member of the *site-admin* [group](https://confluence.atlassian.com/x/24xjL)).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if:   *  &#x60;groupname&#x60; is not provided.  *  &#x60;accountId&#x60; is missing.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing from the request.
     * <p><b>403</b> - Returned if the calling user does not have the necessary permission.
     * <p><b>404</b> - Returned if the group or user are not found.
     * <p><b>429</b> - Returned if rate limiting is being enforced.
     * @param body The user to add to the group. (required)
     * @param groupname As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended to identify a group.   The name of the group. This parameter cannot be used with the &#x60;groupId&#x60; parameter. (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupName&#x60; parameter. (optional)
     * @return ResponseEntity&lt;Group&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Group> addUserToGroupWithHttpInfo(Map<String, Object> body, String groupname, String groupId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling addUserToGroup");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/group/user").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupname", groupname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupId", groupId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Group> returnType = new ParameterizedTypeReference<Group>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk get groups
     * Returns a [paginated](#pagination) list of groups.  **[Permissions](#permissions) required:** *Browse users and groups* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>500</b> - Returned if the group with the given access level can&#x27;t be retrieved.
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param groupId The ID of a group. To specify multiple IDs, pass multiple &#x60;groupId&#x60; parameters. For example, &#x60;groupId&#x3D;5b10a2844c20165700ede21g&amp;groupId&#x3D;5b10ac8d82e05b22cc7d4ef5&#x60;. (optional)
     * @param groupName The name of a group. To specify multiple names, pass multiple &#x60;groupName&#x60; parameters. For example, &#x60;groupName&#x3D;administrators&amp;groupName&#x3D;jira-software-users&#x60;. (optional)
     * @param accessType The access level of a group. Valid values: &#x27;site-admin&#x27;, &#x27;admin&#x27;, &#x27;user&#x27;. (optional)
     * @param applicationKey The application key of the product user groups to search for. Valid values: &#x27;jira-servicedesk&#x27;, &#x27;jira-software&#x27;, &#x27;jira-product-discovery&#x27;, &#x27;jira-core&#x27;. (optional)
     * @return PageBeanGroupDetails
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanGroupDetails bulkGetGroups(Long startAt, Integer maxResults, List<String> groupId, List<String> groupName, String accessType, String applicationKey) throws RestClientException {
        return bulkGetGroupsWithHttpInfo(startAt, maxResults, groupId, groupName, accessType, applicationKey).getBody();
    }

    /**
     * Bulk get groups
     * Returns a [paginated](#pagination) list of groups.  **[Permissions](#permissions) required:** *Browse users and groups* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>500</b> - Returned if the group with the given access level can&#x27;t be retrieved.
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param groupId The ID of a group. To specify multiple IDs, pass multiple &#x60;groupId&#x60; parameters. For example, &#x60;groupId&#x3D;5b10a2844c20165700ede21g&amp;groupId&#x3D;5b10ac8d82e05b22cc7d4ef5&#x60;. (optional)
     * @param groupName The name of a group. To specify multiple names, pass multiple &#x60;groupName&#x60; parameters. For example, &#x60;groupName&#x3D;administrators&amp;groupName&#x3D;jira-software-users&#x60;. (optional)
     * @param accessType The access level of a group. Valid values: &#x27;site-admin&#x27;, &#x27;admin&#x27;, &#x27;user&#x27;. (optional)
     * @param applicationKey The application key of the product user groups to search for. Valid values: &#x27;jira-servicedesk&#x27;, &#x27;jira-software&#x27;, &#x27;jira-product-discovery&#x27;, &#x27;jira-core&#x27;. (optional)
     * @return ResponseEntity&lt;PageBeanGroupDetails&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanGroupDetails> bulkGetGroupsWithHttpInfo(Long startAt, Integer maxResults, List<String> groupId, List<String> groupName, String accessType, String applicationKey) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/group/bulk").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "groupId", groupId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "groupName", groupName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "accessType", accessType));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "applicationKey", applicationKey));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanGroupDetails> returnType = new ParameterizedTypeReference<PageBeanGroupDetails>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create group
     * Creates a group.  **[Permissions](#permissions) required:** Site administration (that is, member of the *site-admin* [group](https://confluence.atlassian.com/x/24xjL)).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if group name is not specified or the group name is in use.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The name of the group. (required)
     * @return Group
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Group createGroup(Map<String, Object> body) throws RestClientException {
        return createGroupWithHttpInfo(body).getBody();
    }

    /**
     * Create group
     * Creates a group.  **[Permissions](#permissions) required:** Site administration (that is, member of the *site-admin* [group](https://confluence.atlassian.com/x/24xjL)).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if group name is not specified or the group name is in use.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The name of the group. (required)
     * @return ResponseEntity&lt;Group&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Group> createGroupWithHttpInfo(Map<String, Object> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createGroup");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/group").build().toUriString();
        
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

        ParameterizedTypeReference<Group> returnType = new ParameterizedTypeReference<Group>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Find groups
     * Returns a list of groups whose names contain a query string. A list of group names can be provided to exclude groups from the results.  The primary use case for this resource is to populate a group picker suggestions list. To this end, the returned object includes the &#x60;html&#x60; field where the matched query term is highlighted in the group name with the HTML strong tag. Also, the groups list is wrapped in a response object that contains a header for use in the picker, specifically *Showing X of Y matching groups*.  The list returns with the groups sorted. If no groups match the list criteria, an empty list is returned.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg). Anonymous calls and calls by users without the required permission return an empty list.  *Browse users and groups* [global permission](https://confluence.atlassian.com/x/x4dKLg). Without this permission, calls where query is not an exact match to an existing group will return an empty list.
     * <p><b>200</b> - Returned if the request is successful.
     * @param accountId This parameter is deprecated, setting it does not affect the results. To find groups containing a particular user, use [Get user groups](#api-rest-api-3-user-groups-get). (optional)
     * @param query The string to find in group names. (optional)
     * @param exclude As a group&#x27;s name can change, use of &#x60;excludeGroupIds&#x60; is recommended to identify a group.   A group to exclude from the result. To exclude multiple groups, provide an ampersand-separated list. For example, &#x60;exclude&#x3D;group1&amp;exclude&#x3D;group2&#x60;. This parameter cannot be used with the &#x60;excludeGroupIds&#x60; parameter. (optional)
     * @param excludeId A group ID to exclude from the result. To exclude multiple groups, provide an ampersand-separated list. For example, &#x60;excludeId&#x3D;group1-id&amp;excludeId&#x3D;group2-id&#x60;. This parameter cannot be used with the &#x60;excludeGroups&#x60; parameter. (optional)
     * @param maxResults The maximum number of groups to return. The maximum number of groups that can be returned is limited by the system property &#x60;jira.ajax.autocomplete.limit&#x60;. (optional)
     * @param caseInsensitive Whether the search for groups should be case insensitive. (optional, default to false)
     * @param userName This parameter is no longer available. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. (optional)
     * @return FoundGroups
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public FoundGroups findGroups(String accountId, String query, List<String> exclude, List<String> excludeId, Integer maxResults, Boolean caseInsensitive, String userName) throws RestClientException {
        return findGroupsWithHttpInfo(accountId, query, exclude, excludeId, maxResults, caseInsensitive, userName).getBody();
    }

    /**
     * Find groups
     * Returns a list of groups whose names contain a query string. A list of group names can be provided to exclude groups from the results.  The primary use case for this resource is to populate a group picker suggestions list. To this end, the returned object includes the &#x60;html&#x60; field where the matched query term is highlighted in the group name with the HTML strong tag. Also, the groups list is wrapped in a response object that contains a header for use in the picker, specifically *Showing X of Y matching groups*.  The list returns with the groups sorted. If no groups match the list criteria, an empty list is returned.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg). Anonymous calls and calls by users without the required permission return an empty list.  *Browse users and groups* [global permission](https://confluence.atlassian.com/x/x4dKLg). Without this permission, calls where query is not an exact match to an existing group will return an empty list.
     * <p><b>200</b> - Returned if the request is successful.
     * @param accountId This parameter is deprecated, setting it does not affect the results. To find groups containing a particular user, use [Get user groups](#api-rest-api-3-user-groups-get). (optional)
     * @param query The string to find in group names. (optional)
     * @param exclude As a group&#x27;s name can change, use of &#x60;excludeGroupIds&#x60; is recommended to identify a group.   A group to exclude from the result. To exclude multiple groups, provide an ampersand-separated list. For example, &#x60;exclude&#x3D;group1&amp;exclude&#x3D;group2&#x60;. This parameter cannot be used with the &#x60;excludeGroupIds&#x60; parameter. (optional)
     * @param excludeId A group ID to exclude from the result. To exclude multiple groups, provide an ampersand-separated list. For example, &#x60;excludeId&#x3D;group1-id&amp;excludeId&#x3D;group2-id&#x60;. This parameter cannot be used with the &#x60;excludeGroups&#x60; parameter. (optional)
     * @param maxResults The maximum number of groups to return. The maximum number of groups that can be returned is limited by the system property &#x60;jira.ajax.autocomplete.limit&#x60;. (optional)
     * @param caseInsensitive Whether the search for groups should be case insensitive. (optional, default to false)
     * @param userName This parameter is no longer available. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. (optional)
     * @return ResponseEntity&lt;FoundGroups&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<FoundGroups> findGroupsWithHttpInfo(String accountId, String query, List<String> exclude, List<String> excludeId, Integer maxResults, Boolean caseInsensitive, String userName) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/groups/picker").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "accountId", accountId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "exclude", exclude));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "excludeId", excludeId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "caseInsensitive", caseInsensitive));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userName", userName));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<FoundGroups> returnType = new ParameterizedTypeReference<FoundGroups>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get group
     * This operation is deprecated, use [&#x60;group/member&#x60;](#api-rest-api-3-group-member-get).  Returns all users in a group.  **[Permissions](#permissions) required:** either of:   *  *Browse users and groups* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the group name is not specified.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the calling user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the group is not found.
     * @param groupname As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended to identify a group.   The name of the group. This parameter cannot be used with the &#x60;groupId&#x60; parameter. (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupName&#x60; parameter. (optional)
     * @param expand List of fields to expand. (optional)
     * @return Group
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public Group getGroup(String groupname, String groupId, String expand) throws RestClientException {
        return getGroupWithHttpInfo(groupname, groupId, expand).getBody();
    }

    /**
     * Get group
     * This operation is deprecated, use [&#x60;group/member&#x60;](#api-rest-api-3-group-member-get).  Returns all users in a group.  **[Permissions](#permissions) required:** either of:   *  *Browse users and groups* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the group name is not specified.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the calling user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the group is not found.
     * @param groupname As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended to identify a group.   The name of the group. This parameter cannot be used with the &#x60;groupId&#x60; parameter. (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupName&#x60; parameter. (optional)
     * @param expand List of fields to expand. (optional)
     * @return ResponseEntity&lt;Group&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public ResponseEntity<Group> getGroupWithHttpInfo(String groupname, String groupId, String expand) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/group").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupname", groupname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupId", groupId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Group> returnType = new ParameterizedTypeReference<Group>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get users from group
     * Returns a [paginated](#pagination) list of all users in a group.  Note that users are ordered by username, however the username is not returned in the results due to privacy reasons.  **[Permissions](#permissions) required:** either of:   *  *Browse users and groups* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the group name is not specified.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the calling user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the group is not found.
     * @param groupname As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended to identify a group.   The name of the group. This parameter cannot be used with the &#x60;groupId&#x60; parameter. (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupName&#x60; parameter. (optional)
     * @param includeInactiveUsers Include inactive users. (optional, default to false)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page (number should be between 1 and 50). (optional, default to 50)
     * @return PageBeanUserDetails
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanUserDetails getUsersFromGroup(String groupname, String groupId, Boolean includeInactiveUsers, Long startAt, Integer maxResults) throws RestClientException {
        return getUsersFromGroupWithHttpInfo(groupname, groupId, includeInactiveUsers, startAt, maxResults).getBody();
    }

    /**
     * Get users from group
     * Returns a [paginated](#pagination) list of all users in a group.  Note that users are ordered by username, however the username is not returned in the results due to privacy reasons.  **[Permissions](#permissions) required:** either of:   *  *Browse users and groups* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the group name is not specified.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the calling user does not have the Administer Jira global permission.
     * <p><b>404</b> - Returned if the group is not found.
     * @param groupname As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended to identify a group.   The name of the group. This parameter cannot be used with the &#x60;groupId&#x60; parameter. (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupName&#x60; parameter. (optional)
     * @param includeInactiveUsers Include inactive users. (optional, default to false)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page (number should be between 1 and 50). (optional, default to 50)
     * @return ResponseEntity&lt;PageBeanUserDetails&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanUserDetails> getUsersFromGroupWithHttpInfo(String groupname, String groupId, Boolean includeInactiveUsers, Long startAt, Integer maxResults) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/group/member").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupname", groupname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupId", groupId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "includeInactiveUsers", includeInactiveUsers));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanUserDetails> returnType = new ParameterizedTypeReference<PageBeanUserDetails>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Remove group
     * Deletes a group.  **[Permissions](#permissions) required:** Site administration (that is, member of the *site-admin* strategic [group](https://confluence.atlassian.com/x/24xjL)).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the group name is not specified.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing from the request.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the group is not found.
     * @param groupname  (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupname&#x60; parameter. (optional)
     * @param swapGroup As a group&#x27;s name can change, use of &#x60;swapGroupId&#x60; is recommended to identify a group.   The group to transfer restrictions to. Only comments and worklogs are transferred. If restrictions are not transferred, comments and worklogs are inaccessible after the deletion. This parameter cannot be used with the &#x60;swapGroupId&#x60; parameter. (optional)
     * @param swapGroupId The ID of the group to transfer restrictions to. Only comments and worklogs are transferred. If restrictions are not transferred, comments and worklogs are inaccessible after the deletion. This parameter cannot be used with the &#x60;swapGroup&#x60; parameter. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void removeGroup(String groupname, String groupId, String swapGroup, String swapGroupId) throws RestClientException {
        removeGroupWithHttpInfo(groupname, groupId, swapGroup, swapGroupId);
    }

    /**
     * Remove group
     * Deletes a group.  **[Permissions](#permissions) required:** Site administration (that is, member of the *site-admin* strategic [group](https://confluence.atlassian.com/x/24xjL)).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the group name is not specified.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing from the request.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the group is not found.
     * @param groupname  (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupname&#x60; parameter. (optional)
     * @param swapGroup As a group&#x27;s name can change, use of &#x60;swapGroupId&#x60; is recommended to identify a group.   The group to transfer restrictions to. Only comments and worklogs are transferred. If restrictions are not transferred, comments and worklogs are inaccessible after the deletion. This parameter cannot be used with the &#x60;swapGroupId&#x60; parameter. (optional)
     * @param swapGroupId The ID of the group to transfer restrictions to. Only comments and worklogs are transferred. If restrictions are not transferred, comments and worklogs are inaccessible after the deletion. This parameter cannot be used with the &#x60;swapGroup&#x60; parameter. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> removeGroupWithHttpInfo(String groupname, String groupId, String swapGroup, String swapGroupId) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/group").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupname", groupname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupId", groupId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "swapGroup", swapGroup));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "swapGroupId", swapGroupId));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Remove user from group
     * Removes a user from a group.  **[Permissions](#permissions) required:** Site administration (that is, member of the *site-admin* [group](https://confluence.atlassian.com/x/24xjL)).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if:   *  &#x60;groupName&#x60; is missing.  *  &#x60;accountId&#x60; is missing.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing from the request.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the group or user are not found.
     * @param accountId The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. (required)
     * @param groupname As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended to identify a group.   The name of the group. This parameter cannot be used with the &#x60;groupId&#x60; parameter. (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupName&#x60; parameter. (optional)
     * @param username This parameter is no longer available. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void removeUserFromGroup(String accountId, String groupname, String groupId, String username) throws RestClientException {
        removeUserFromGroupWithHttpInfo(accountId, groupname, groupId, username);
    }

    /**
     * Remove user from group
     * Removes a user from a group.  **[Permissions](#permissions) required:** Site administration (that is, member of the *site-admin* [group](https://confluence.atlassian.com/x/24xjL)).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if:   *  &#x60;groupName&#x60; is missing.  *  &#x60;accountId&#x60; is missing.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing from the request.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the group or user are not found.
     * @param accountId The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. (required)
     * @param groupname As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended to identify a group.   The name of the group. This parameter cannot be used with the &#x60;groupId&#x60; parameter. (optional)
     * @param groupId The ID of the group. This parameter cannot be used with the &#x60;groupName&#x60; parameter. (optional)
     * @param username This parameter is no longer available. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> removeUserFromGroupWithHttpInfo(String accountId, String groupname, String groupId, String username) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'accountId' is set
        if (accountId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'accountId' when calling removeUserFromGroup");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/group/user").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupname", groupname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupId", groupId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "username", username));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "accountId", accountId));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}