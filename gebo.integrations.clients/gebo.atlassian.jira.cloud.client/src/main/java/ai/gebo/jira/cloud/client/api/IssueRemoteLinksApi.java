/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
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
import ai.gebo.jira.cloud.client.model.RemoteIssueLink;
import ai.gebo.jira.cloud.client.model.RemoteIssueLinkIdentifies;

/**
 * AI generated comments
 * This class provides REST API client operations for managing remote issue links in Jira Cloud.
 * It allows creating, retrieving, updating, and deleting remote links associated with Jira issues,
 * which are references to external systems integrated with Jira.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.IssueRemoteLinksApi")
public class IssueRemoteLinksApi {
    private ApiClient apiClient;

    /**
     * Default constructor creating an instance with a new ApiClient
     */
    public IssueRemoteLinksApi() {
        this(new ApiClient());
    }

    /**
     * Constructor that allows for dependency injection of ApiClient
     * @param apiClient The API client to use for REST operations
     */
    //@Autowired
    public IssueRemoteLinksApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Returns the current API client instance
     * @return The API client currently in use
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to use for REST operations
     * @param apiClient The API client to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create or update remote issue link
     * Creates or updates a remote issue link for an issue.  If a &#x60;globalId&#x60; is provided and a remote issue link with that global ID is found it is updated. Any fields without values in the request are set to null. Otherwise, the remote issue link is created.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the remote issue link is updated.
     * <p><b>201</b> - Returned if the remote issue link is created.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to link issues.
     * <p><b>404</b> - Returned if the issue is not found or the user does not have permission to view the issue.
     * @param body  (required)
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @return RemoteIssueLinkIdentifies
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public RemoteIssueLinkIdentifies createOrUpdateRemoteIssueLink(Map<String, Object> body, String issueIdOrKey) throws RestClientException {
        return createOrUpdateRemoteIssueLinkWithHttpInfo(body, issueIdOrKey).getBody();
    }

    /**
     * Create or update remote issue link
     * Creates or updates a remote issue link for an issue.  If a &#x60;globalId&#x60; is provided and a remote issue link with that global ID is found it is updated. Any fields without values in the request are set to null. Otherwise, the remote issue link is created.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the remote issue link is updated.
     * <p><b>201</b> - Returned if the remote issue link is created.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to link issues.
     * <p><b>404</b> - Returned if the issue is not found or the user does not have permission to view the issue.
     * @param body  (required)
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @return ResponseEntity&lt;RemoteIssueLinkIdentifies&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RemoteIssueLinkIdentifies> createOrUpdateRemoteIssueLinkWithHttpInfo(Map<String, Object> body, String issueIdOrKey) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createOrUpdateRemoteIssueLink");
        }
        // verify the required parameter 'issueIdOrKey' is set
        if (issueIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueIdOrKey' when calling createOrUpdateRemoteIssueLink");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("issueIdOrKey", issueIdOrKey);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issue/{issueIdOrKey}/remotelink").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<RemoteIssueLinkIdentifies> returnType = new ParameterizedTypeReference<RemoteIssueLinkIdentifies>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete remote issue link by global ID
     * Deletes the remote issue link from the issue using the link&#x27;s global ID. Where the global ID includes reserved URL characters these must be escaped in the request. For example, pass &#x60;system&#x3D;http://www.mycompany.com/support&amp;id&#x3D;1&#x60; as &#x60;system%3Dhttp%3A%2F%2Fwww.mycompany.com%2Fsupport%26id%3D1&#x60;.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is implemented, issue-level security permission to view the issue.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if a global ID isn&#x27;t provided.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to link issues.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param globalId The global ID of a remote issue link. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteRemoteIssueLinkByGlobalId(String issueIdOrKey, String globalId) throws RestClientException {
        deleteRemoteIssueLinkByGlobalIdWithHttpInfo(issueIdOrKey, globalId);
    }

    /**
     * Delete remote issue link by global ID
     * Deletes the remote issue link from the issue using the link&#x27;s global ID. Where the global ID includes reserved URL characters these must be escaped in the request. For example, pass &#x60;system&#x3D;http://www.mycompany.com/support&amp;id&#x3D;1&#x60; as &#x60;system%3Dhttp%3A%2F%2Fwww.mycompany.com%2Fsupport%26id%3D1&#x60;.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is implemented, issue-level security permission to view the issue.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if a global ID isn&#x27;t provided.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to link issues.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param globalId The global ID of a remote issue link. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteRemoteIssueLinkByGlobalIdWithHttpInfo(String issueIdOrKey, String globalId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'issueIdOrKey' is set
        if (issueIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueIdOrKey' when calling deleteRemoteIssueLinkByGlobalId");
        }
        // verify the required parameter 'globalId' is set
        if (globalId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'globalId' when calling deleteRemoteIssueLinkByGlobalId");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("issueIdOrKey", issueIdOrKey);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issue/{issueIdOrKey}/remotelink").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "globalId", globalId));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete remote issue link by ID
     * Deletes a remote issue link from an issue.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects*, *Edit issues*, and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the link ID is invalid or the remote issue link does not belong to the issue.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to link issues.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param linkId The ID of a remote issue link. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteRemoteIssueLinkById(String issueIdOrKey, String linkId) throws RestClientException {
        deleteRemoteIssueLinkByIdWithHttpInfo(issueIdOrKey, linkId);
    }

    /**
     * Delete remote issue link by ID
     * Deletes a remote issue link from an issue.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects*, *Edit issues*, and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the link ID is invalid or the remote issue link does not belong to the issue.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to link issues.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param linkId The ID of a remote issue link. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteRemoteIssueLinkByIdWithHttpInfo(String issueIdOrKey, String linkId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'issueIdOrKey' is set
        if (issueIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueIdOrKey' when calling deleteRemoteIssueLinkById");
        }
        // verify the required parameter 'linkId' is set
        if (linkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'linkId' when calling deleteRemoteIssueLinkById");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("issueIdOrKey", issueIdOrKey);
        uriVariables.put("linkId", linkId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issue/{issueIdOrKey}/remotelink/{linkId}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get remote issue link by ID
     * Returns a remote issue link for an issue.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the link ID is invalid or the remote issue link does not belong to the issue.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if issue linking is disabled.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param linkId The ID of the remote issue link. (required)
     * @return RemoteIssueLink
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public RemoteIssueLink getRemoteIssueLinkById(String issueIdOrKey, String linkId) throws RestClientException {
        return getRemoteIssueLinkByIdWithHttpInfo(issueIdOrKey, linkId).getBody();
    }

    /**
     * Get remote issue link by ID
     * Returns a remote issue link for an issue.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the link ID is invalid or the remote issue link does not belong to the issue.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if issue linking is disabled.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param linkId The ID of the remote issue link. (required)
     * @return ResponseEntity&lt;RemoteIssueLink&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RemoteIssueLink> getRemoteIssueLinkByIdWithHttpInfo(String issueIdOrKey, String linkId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'issueIdOrKey' is set
        if (issueIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueIdOrKey' when calling getRemoteIssueLinkById");
        }
        // verify the required parameter 'linkId' is set
        if (linkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'linkId' when calling getRemoteIssueLinkById");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("issueIdOrKey", issueIdOrKey);
        uriVariables.put("linkId", linkId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issue/{issueIdOrKey}/remotelink/{linkId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<RemoteIssueLink> returnType = new ParameterizedTypeReference<RemoteIssueLink>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get remote issue links
     * Returns the remote issue links for an issue. When a remote issue link global ID is provided the record with that global ID is returned, otherwise all remote issue links are returned. Where a global ID includes reserved URL characters these must be escaped in the request. For example, pass &#x60;system&#x3D;http://www.mycompany.com/support&amp;id&#x3D;1&#x60; as &#x60;system%3Dhttp%3A%2F%2Fwww.mycompany.com%2Fsupport%26id%3D1&#x60;.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if issue linking is disabled.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * <p><b>413</b> - Returned if the per-issue limit for remote links has been breached.
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param globalId The global ID of the remote issue link. (optional)
     * @return RemoteIssueLink
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public RemoteIssueLink getRemoteIssueLinks(String issueIdOrKey, String globalId) throws RestClientException {
        return getRemoteIssueLinksWithHttpInfo(issueIdOrKey, globalId).getBody();
    }

    /**
     * Get remote issue links
     * Returns the remote issue links for an issue. When a remote issue link global ID is provided the record with that global ID is returned, otherwise all remote issue links are returned. Where a global ID includes reserved URL characters these must be escaped in the request. For example, pass &#x60;system&#x3D;http://www.mycompany.com/support&amp;id&#x3D;1&#x60; as &#x60;system%3Dhttp%3A%2F%2Fwww.mycompany.com%2Fsupport%26id%3D1&#x60;.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if issue linking is disabled.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * <p><b>413</b> - Returned if the per-issue limit for remote links has been breached.
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param globalId The global ID of the remote issue link. (optional)
     * @return ResponseEntity&lt;RemoteIssueLink&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RemoteIssueLink> getRemoteIssueLinksWithHttpInfo(String issueIdOrKey, String globalId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'issueIdOrKey' is set
        if (issueIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueIdOrKey' when calling getRemoteIssueLinks");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("issueIdOrKey", issueIdOrKey);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issue/{issueIdOrKey}/remotelink").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "globalId", globalId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<RemoteIssueLink> returnType = new ParameterizedTypeReference<RemoteIssueLink>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update remote issue link by ID
     * Updates a remote issue link for an issue.  Note: Fields without values in the request are set to null.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if:   *  the link ID is invalid.  *  the remote issue link does not belong to the issue.  *  the request body is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to link issues.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * @param body  (required)
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param linkId The ID of the remote issue link. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object updateRemoteIssueLink(Map<String, Object> body, String issueIdOrKey, String linkId) throws RestClientException {
        return updateRemoteIssueLinkWithHttpInfo(body, issueIdOrKey, linkId).getBody();
    }

    /**
     * Update remote issue link by ID
     * Updates a remote issue link for an issue.  Note: Fields without values in the request are set to null.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if:   *  the link ID is invalid.  *  the remote issue link does not belong to the issue.  *  the request body is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have permission to link issues.
     * <p><b>404</b> - Returned if the issue or remote issue link is not found or the user does not have permission to view the issue.
     * @param body  (required)
     * @param issueIdOrKey The ID or key of the issue. (required)
     * @param linkId The ID of the remote issue link. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> updateRemoteIssueLinkWithHttpInfo(Map<String, Object> body, String issueIdOrKey, String linkId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateRemoteIssueLink");
        }
        // verify the required parameter 'issueIdOrKey' is set
        if (issueIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueIdOrKey' when calling updateRemoteIssueLink");
        }
        // verify the required parameter 'linkId' is set
        if (linkId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'linkId' when calling updateRemoteIssueLink");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("issueIdOrKey", issueIdOrKey);
        uriVariables.put("linkId", linkId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/issue/{issueIdOrKey}/remotelink/{linkId}").buildAndExpand(uriVariables).toUriString();
        
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