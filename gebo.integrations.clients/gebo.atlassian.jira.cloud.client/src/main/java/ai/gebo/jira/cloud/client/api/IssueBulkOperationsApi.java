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
import ai.gebo.jira.cloud.client.model.BulkEditGetFields;
import ai.gebo.jira.cloud.client.model.BulkOperationProgress;
import ai.gebo.jira.cloud.client.model.BulkTransitionGetAvailableTransitions;
import ai.gebo.jira.cloud.client.model.IssueBulkDeletePayload;
import ai.gebo.jira.cloud.client.model.IssueBulkEditPayload;
import ai.gebo.jira.cloud.client.model.IssueBulkMovePayload;
import ai.gebo.jira.cloud.client.model.IssueBulkTransitionPayload;
import ai.gebo.jira.cloud.client.model.IssueBulkWatchOrUnwatchPayload;
import ai.gebo.jira.cloud.client.model.SubmittedBulkOperation;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.IssueBulkOperationsApi")
public class IssueBulkOperationsApi {
    private ApiClient apiClient;

    public IssueBulkOperationsApi() {
        this(new ApiClient());
    }

    //@Autowired
    public IssueBulkOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get available transitions
     * Use this API to retrieve a list of transitions available for the specified issues that can be used or bulk transition operations. You can submit either single or multiple issues in the query to obtain the available transitions.  The response will provide the available transitions for issues, organized by their respective workflows. **Only the transitions that are common among the issues within that workflow and do not involve any additional field updates will be included.** For bulk transitions that require additional field updates, please utilise the Jira Cloud UI.  You can request available transitions for up to 1,000 issues in a single operation. This API uses pagination to return responses, delivering 50 workflows at a time.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Transition [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/permissions-for-company-managed-projects/#Transition-issues/) in all projects that contain the selected issues.  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid. For example, if a provided issue ID or key is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param issueIdsOrKeys Comma (,) separated Ids or keys of the issues to get transitions available for them. (required)
     * @param endingBefore (Optional)The end cursor for use in pagination. (optional)
     * @param startingAfter (Optional)The start cursor for use in pagination. (optional)
     * @return BulkTransitionGetAvailableTransitions
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public BulkTransitionGetAvailableTransitions getAvailableTransitions(String issueIdsOrKeys, String endingBefore, String startingAfter) throws RestClientException {
        return getAvailableTransitionsWithHttpInfo(issueIdsOrKeys, endingBefore, startingAfter).getBody();
    }

    /**
     * Get available transitions
     * Use this API to retrieve a list of transitions available for the specified issues that can be used or bulk transition operations. You can submit either single or multiple issues in the query to obtain the available transitions.  The response will provide the available transitions for issues, organized by their respective workflows. **Only the transitions that are common among the issues within that workflow and do not involve any additional field updates will be included.** For bulk transitions that require additional field updates, please utilise the Jira Cloud UI.  You can request available transitions for up to 1,000 issues in a single operation. This API uses pagination to return responses, delivering 50 workflows at a time.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Transition [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/permissions-for-company-managed-projects/#Transition-issues/) in all projects that contain the selected issues.  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid. For example, if a provided issue ID or key is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param issueIdsOrKeys Comma (,) separated Ids or keys of the issues to get transitions available for them. (required)
     * @param endingBefore (Optional)The end cursor for use in pagination. (optional)
     * @param startingAfter (Optional)The start cursor for use in pagination. (optional)
     * @return ResponseEntity&lt;BulkTransitionGetAvailableTransitions&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<BulkTransitionGetAvailableTransitions> getAvailableTransitionsWithHttpInfo(String issueIdsOrKeys, String endingBefore, String startingAfter) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'issueIdsOrKeys' is set
        if (issueIdsOrKeys == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueIdsOrKeys' when calling getAvailableTransitions");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/bulk/issues/transition").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "issueIdsOrKeys", issueIdsOrKeys));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endingBefore", endingBefore));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startingAfter", startingAfter));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<BulkTransitionGetAvailableTransitions> returnType = new ParameterizedTypeReference<BulkTransitionGetAvailableTransitions>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get bulk editable fields
     * Use this API to get a list of fields visible to the user to perform bulk edit operations. You can pass single or multiple issues in the query to get eligible editable fields. This API uses pagination to return responses, delivering 50 fields at a time.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.  *  Depending on the field, any field-specific permissions required to edit it.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if no editable fields are found for the provided issue IDs.
     * @param issueIdsOrKeys The IDs or keys of the issues to get editable fields from. (required)
     * @param searchText (Optional)The text to search for in the editable fields. (optional)
     * @param endingBefore (Optional)The end cursor for use in pagination. (optional)
     * @param startingAfter (Optional)The start cursor for use in pagination. (optional)
     * @return BulkEditGetFields
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public BulkEditGetFields getBulkEditableFields(String issueIdsOrKeys, String searchText, String endingBefore, String startingAfter) throws RestClientException {
        return getBulkEditableFieldsWithHttpInfo(issueIdsOrKeys, searchText, endingBefore, startingAfter).getBody();
    }

    /**
     * Get bulk editable fields
     * Use this API to get a list of fields visible to the user to perform bulk edit operations. You can pass single or multiple issues in the query to get eligible editable fields. This API uses pagination to return responses, delivering 50 fields at a time.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.  *  Depending on the field, any field-specific permissions required to edit it.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if no editable fields are found for the provided issue IDs.
     * @param issueIdsOrKeys The IDs or keys of the issues to get editable fields from. (required)
     * @param searchText (Optional)The text to search for in the editable fields. (optional)
     * @param endingBefore (Optional)The end cursor for use in pagination. (optional)
     * @param startingAfter (Optional)The start cursor for use in pagination. (optional)
     * @return ResponseEntity&lt;BulkEditGetFields&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<BulkEditGetFields> getBulkEditableFieldsWithHttpInfo(String issueIdsOrKeys, String searchText, String endingBefore, String startingAfter) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'issueIdsOrKeys' is set
        if (issueIdsOrKeys == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueIdsOrKeys' when calling getBulkEditableFields");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/bulk/issues/fields").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "issueIdsOrKeys", issueIdsOrKeys));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "searchText", searchText));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endingBefore", endingBefore));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startingAfter", startingAfter));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<BulkEditGetFields> returnType = new ParameterizedTypeReference<BulkEditGetFields>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get bulk issue operation progress
     * Use this to get the progress state for the specified bulk operation &#x60;taskId&#x60;.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  If the task is running, this resource will return:      {\&quot;taskId\&quot;:\&quot;10779\&quot;,\&quot;status\&quot;:\&quot;RUNNING\&quot;,\&quot;progressPercent\&quot;:65,\&quot;submittedBy\&quot;:{\&quot;accountId\&quot;:\&quot;5b10a2844c20165700ede21g\&quot;},\&quot;created\&quot;:1690180055963,\&quot;started\&quot;:1690180056206,\&quot;updated\&quot;:169018005829}  If the task has completed, then this resource will return:      {\&quot;processedAccessibleIssues\&quot;:[10001,10002],\&quot;created\&quot;:1709189449954,\&quot;progressPercent\&quot;:100,\&quot;started\&quot;:1709189450154,\&quot;status\&quot;:\&quot;COMPLETE\&quot;,\&quot;submittedBy\&quot;:{\&quot;accountId\&quot;:\&quot;5b10a2844c20165700ede21g\&quot;},\&quot;invalidOrInaccessibleIssueCount\&quot;:0,\&quot;taskId\&quot;:\&quot;10000\&quot;,\&quot;totalIssueCount\&quot;:2,\&quot;updated\&quot;:1709189450354}  **Note:** You can view task progress for up to 14 days from creation.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param taskId The ID of the task. (required)
     * @return BulkOperationProgress
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public BulkOperationProgress getBulkOperationProgress(String taskId) throws RestClientException {
        return getBulkOperationProgressWithHttpInfo(taskId).getBody();
    }

    /**
     * Get bulk issue operation progress
     * Use this to get the progress state for the specified bulk operation &#x60;taskId&#x60;.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  If the task is running, this resource will return:      {\&quot;taskId\&quot;:\&quot;10779\&quot;,\&quot;status\&quot;:\&quot;RUNNING\&quot;,\&quot;progressPercent\&quot;:65,\&quot;submittedBy\&quot;:{\&quot;accountId\&quot;:\&quot;5b10a2844c20165700ede21g\&quot;},\&quot;created\&quot;:1690180055963,\&quot;started\&quot;:1690180056206,\&quot;updated\&quot;:169018005829}  If the task has completed, then this resource will return:      {\&quot;processedAccessibleIssues\&quot;:[10001,10002],\&quot;created\&quot;:1709189449954,\&quot;progressPercent\&quot;:100,\&quot;started\&quot;:1709189450154,\&quot;status\&quot;:\&quot;COMPLETE\&quot;,\&quot;submittedBy\&quot;:{\&quot;accountId\&quot;:\&quot;5b10a2844c20165700ede21g\&quot;},\&quot;invalidOrInaccessibleIssueCount\&quot;:0,\&quot;taskId\&quot;:\&quot;10000\&quot;,\&quot;totalIssueCount\&quot;:2,\&quot;updated\&quot;:1709189450354}  **Note:** You can view task progress for up to 14 days from creation.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param taskId The ID of the task. (required)
     * @return ResponseEntity&lt;BulkOperationProgress&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<BulkOperationProgress> getBulkOperationProgressWithHttpInfo(String taskId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'taskId' is set
        if (taskId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'taskId' when calling getBulkOperationProgress");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("taskId", taskId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/bulk/queue/{taskId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<BulkOperationProgress> returnType = new ParameterizedTypeReference<BulkOperationProgress>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk delete issues
     * Use this API to submit a bulk delete request. You can delete up to 1,000 issues in a single operation.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Delete [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/permissions-for-company-managed-projects/#Delete-issues/) in all projects that contain the selected issues.  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The request body containing the issues to be deleted. (required)
     * @return SubmittedBulkOperation
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SubmittedBulkOperation submitBulkDelete(IssueBulkDeletePayload body) throws RestClientException {
        return submitBulkDeleteWithHttpInfo(body).getBody();
    }

    /**
     * Bulk delete issues
     * Use this API to submit a bulk delete request. You can delete up to 1,000 issues in a single operation.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Delete [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/permissions-for-company-managed-projects/#Delete-issues/) in all projects that contain the selected issues.  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The request body containing the issues to be deleted. (required)
     * @return ResponseEntity&lt;SubmittedBulkOperation&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SubmittedBulkOperation> submitBulkDeleteWithHttpInfo(IssueBulkDeletePayload body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling submitBulkDelete");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/bulk/issues/delete").build().toUriString();
        
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

        ParameterizedTypeReference<SubmittedBulkOperation> returnType = new ParameterizedTypeReference<SubmittedBulkOperation>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk edit issues
     * Use this API to submit a bulk edit request and simultaneously edit multiple issues. There are limits applied to the number of issues and fields that can be edited. A single request can accommodate a maximum of 1000 issues (including subtasks) and 200 fields.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  Edit [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param body The request body containing the issues to be edited and the new field values. (required)
     * @return SubmittedBulkOperation
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SubmittedBulkOperation submitBulkEdit(IssueBulkEditPayload body) throws RestClientException {
        return submitBulkEditWithHttpInfo(body).getBody();
    }

    /**
     * Bulk edit issues
     * Use this API to submit a bulk edit request and simultaneously edit multiple issues. There are limits applied to the number of issues and fields that can be edited. A single request can accommodate a maximum of 1000 issues (including subtasks) and 200 fields.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  Edit [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param body The request body containing the issues to be edited and the new field values. (required)
     * @return ResponseEntity&lt;SubmittedBulkOperation&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SubmittedBulkOperation> submitBulkEditWithHttpInfo(IssueBulkEditPayload body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling submitBulkEdit");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/bulk/issues/fields").build().toUriString();
        
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

        ParameterizedTypeReference<SubmittedBulkOperation> returnType = new ParameterizedTypeReference<SubmittedBulkOperation>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk move issues
     * Use this API to submit a bulk issue move request. You can move multiple issues, but they must all be moved to and from a single project, issue type, and parent. You can&#x27;t move more than 1000 issues (including subtasks) at once.  #### Scenarios: ####  This is an early version of the API and it doesn&#x27;t have full feature parity with the Bulk Move UI experience.   *  Moving issue of type A to issue of type B in the same project or a different project: &#x60;SUPPORTED&#x60;  *  Moving multiple issues of type A in one project to multiple issues of type B in the same project or a different project: **&#x60;SUPPORTED&#x60;**  *  Moving a standard parent issue of type A with its multiple subtask issue types in one project to standard issue of type B and multiple subtask issue types in the same project or a different project: &#x60;SUPPORTED&#x60;  *  Moving an epic issue with its child issues to a different project without losing their relation: &#x60;NOT SUPPORTED&#x60;       (Workaround: Move them individually and stitch the relationship back with the Bulk Edit API)  #### Limits applied to bulk issue moves: ####  When using the bulk move, keep in mind that there are limits on the number of issues and fields you can include.   *  You can move up to 1,000 issues in a single operation, including any subtasks.  *  All issues must originate from the same project and share the same issue type and parent.  *  The total combined number of fields across all issues must not exceed 1,500,000. For example, if each issue includes 15,000 fields, then the maximum number of issues that can be moved is 100.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Move [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in source projects.  *  Create [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in destination projects.  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in destination projects, if moving subtasks only.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param body  (required)
     * @return SubmittedBulkOperation
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SubmittedBulkOperation submitBulkMove(IssueBulkMovePayload body) throws RestClientException {
        return submitBulkMoveWithHttpInfo(body).getBody();
    }

    /**
     * Bulk move issues
     * Use this API to submit a bulk issue move request. You can move multiple issues, but they must all be moved to and from a single project, issue type, and parent. You can&#x27;t move more than 1000 issues (including subtasks) at once.  #### Scenarios: ####  This is an early version of the API and it doesn&#x27;t have full feature parity with the Bulk Move UI experience.   *  Moving issue of type A to issue of type B in the same project or a different project: &#x60;SUPPORTED&#x60;  *  Moving multiple issues of type A in one project to multiple issues of type B in the same project or a different project: **&#x60;SUPPORTED&#x60;**  *  Moving a standard parent issue of type A with its multiple subtask issue types in one project to standard issue of type B and multiple subtask issue types in the same project or a different project: &#x60;SUPPORTED&#x60;  *  Moving an epic issue with its child issues to a different project without losing their relation: &#x60;NOT SUPPORTED&#x60;       (Workaround: Move them individually and stitch the relationship back with the Bulk Edit API)  #### Limits applied to bulk issue moves: ####  When using the bulk move, keep in mind that there are limits on the number of issues and fields you can include.   *  You can move up to 1,000 issues in a single operation, including any subtasks.  *  All issues must originate from the same project and share the same issue type and parent.  *  The total combined number of fields across all issues must not exceed 1,500,000. For example, if each issue includes 15,000 fields, then the maximum number of issues that can be moved is 100.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Move [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in source projects.  *  Create [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in destination projects.  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in destination projects, if moving subtasks only.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param body  (required)
     * @return ResponseEntity&lt;SubmittedBulkOperation&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SubmittedBulkOperation> submitBulkMoveWithHttpInfo(IssueBulkMovePayload body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling submitBulkMove");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/bulk/issues/move").build().toUriString();
        
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

        ParameterizedTypeReference<SubmittedBulkOperation> returnType = new ParameterizedTypeReference<SubmittedBulkOperation>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk transition issue statuses
     * Use this API to submit a bulk issue status transition request. You can transition multiple issues, alongside with their valid transition Ids. You can transition up to 1,000 issues in a single operation.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Transition [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/permissions-for-company-managed-projects/#Transition-issues/) in all projects that contain the selected issues.  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The request body containing the issues to be transitioned. (required)
     * @return SubmittedBulkOperation
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SubmittedBulkOperation submitBulkTransition(IssueBulkTransitionPayload body) throws RestClientException {
        return submitBulkTransitionWithHttpInfo(body).getBody();
    }

    /**
     * Bulk transition issue statuses
     * Use this API to submit a bulk issue status transition request. You can transition multiple issues, alongside with their valid transition Ids. You can transition up to 1,000 issues in a single operation.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Transition [issues permission](https://support.atlassian.com/jira-cloud-administration/docs/permissions-for-company-managed-projects/#Transition-issues/) in all projects that contain the selected issues.  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The request body containing the issues to be transitioned. (required)
     * @return ResponseEntity&lt;SubmittedBulkOperation&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SubmittedBulkOperation> submitBulkTransitionWithHttpInfo(IssueBulkTransitionPayload body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling submitBulkTransition");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/bulk/issues/transition").build().toUriString();
        
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

        ParameterizedTypeReference<SubmittedBulkOperation> returnType = new ParameterizedTypeReference<SubmittedBulkOperation>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk unwatch issues
     * Use this API to submit a bulk unwatch request. You can unwatch up to 1,000 issues in a single operation.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The request body containing the issues to be unwatched. (required)
     * @return SubmittedBulkOperation
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SubmittedBulkOperation submitBulkUnwatch(IssueBulkWatchOrUnwatchPayload body) throws RestClientException {
        return submitBulkUnwatchWithHttpInfo(body).getBody();
    }

    /**
     * Bulk unwatch issues
     * Use this API to submit a bulk unwatch request. You can unwatch up to 1,000 issues in a single operation.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The request body containing the issues to be unwatched. (required)
     * @return ResponseEntity&lt;SubmittedBulkOperation&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SubmittedBulkOperation> submitBulkUnwatchWithHttpInfo(IssueBulkWatchOrUnwatchPayload body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling submitBulkUnwatch");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/bulk/issues/unwatch").build().toUriString();
        
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

        ParameterizedTypeReference<SubmittedBulkOperation> returnType = new ParameterizedTypeReference<SubmittedBulkOperation>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk watch issues
     * Use this API to submit a bulk watch request. You can watch up to 1,000 issues in a single operation.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The request body containing the issues to be watched. (required)
     * @return SubmittedBulkOperation
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SubmittedBulkOperation submitBulkWatch(IssueBulkWatchOrUnwatchPayload body) throws RestClientException {
        return submitBulkWatchWithHttpInfo(body).getBody();
    }

    /**
     * Bulk watch issues
     * Use this API to submit a bulk watch request. You can watch up to 1,000 issues in a single operation.  **[Permissions](#permissions) required:**   *  Global bulk change [permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-global-permissions/).  *  Browse [project permission](https://support.atlassian.com/jira-cloud-administration/docs/manage-project-permissions/) in all projects that contain the selected issues.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param body The request body containing the issues to be watched. (required)
     * @return ResponseEntity&lt;SubmittedBulkOperation&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SubmittedBulkOperation> submitBulkWatchWithHttpInfo(IssueBulkWatchOrUnwatchPayload body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling submitBulkWatch");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/bulk/issues/watch").build().toUriString();
        
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

        ParameterizedTypeReference<SubmittedBulkOperation> returnType = new ParameterizedTypeReference<SubmittedBulkOperation>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
