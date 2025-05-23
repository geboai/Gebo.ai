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

import ai.gebo.jira.cloud.client.model.DefaultWorkflow;
import ai.gebo.jira.cloud.client.model.IssueTypeWorkflowMapping;
import ai.gebo.jira.cloud.client.model.IssueTypesWorkflowMapping;
import ai.gebo.jira.cloud.client.model.PublishDraftWorkflowScheme;
import ai.gebo.jira.cloud.client.model.TaskProgressBeanObject;
import ai.gebo.jira.cloud.client.model.WorkflowScheme;

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

public class WorkflowSchemeDraftsApi {
    private ApiClient apiClient;

    public WorkflowSchemeDraftsApi() {
        this(new ApiClient());
    }

    //@Autowired
    public WorkflowSchemeDraftsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create draft workflow scheme
     * Create a draft workflow scheme from an active workflow scheme, by copying the active workflow scheme. Note that an active workflow scheme can only have one draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param id The ID of the active workflow scheme that the draft is created from. (required)
     * @return WorkflowScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowScheme createWorkflowSchemeDraftFromParent(Long id) throws RestClientException {
        return createWorkflowSchemeDraftFromParentWithHttpInfo(id).getBody();
    }

    /**
     * Create draft workflow scheme
     * Create a draft workflow scheme from an active workflow scheme, by copying the active workflow scheme. Note that an active workflow scheme can only have one draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param id The ID of the active workflow scheme that the draft is created from. (required)
     * @return ResponseEntity&lt;WorkflowScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowScheme> createWorkflowSchemeDraftFromParentWithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling createWorkflowSchemeDraftFromParent");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/createdraft").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<WorkflowScheme> returnType = new ParameterizedTypeReference<WorkflowScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete draft default workflow
     * Resets the default workflow for a workflow scheme&#x27;s draft. That is, the default workflow is set to Jira&#x27;s system workflow (the *jira* workflow).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @return WorkflowScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowScheme deleteDraftDefaultWorkflow(Long id) throws RestClientException {
        return deleteDraftDefaultWorkflowWithHttpInfo(id).getBody();
    }

    /**
     * Delete draft default workflow
     * Resets the default workflow for a workflow scheme&#x27;s draft. That is, the default workflow is set to Jira&#x27;s system workflow (the *jira* workflow).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @return ResponseEntity&lt;WorkflowScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowScheme> deleteDraftDefaultWorkflowWithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteDraftDefaultWorkflow");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/default").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<WorkflowScheme> returnType = new ParameterizedTypeReference<WorkflowScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete issue types for workflow in draft workflow scheme
     * Deletes the workflow-issue type mapping for a workflow in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.  *  The workflow is not found.  *  The workflow is not specified.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param workflowName The name of the workflow. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteDraftWorkflowMapping(Long id, String workflowName) throws RestClientException {
        deleteDraftWorkflowMappingWithHttpInfo(id, workflowName);
    }

    /**
     * Delete issue types for workflow in draft workflow scheme
     * Deletes the workflow-issue type mapping for a workflow in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.  *  The workflow is not found.  *  The workflow is not specified.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param workflowName The name of the workflow. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteDraftWorkflowMappingWithHttpInfo(Long id, String workflowName) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteDraftWorkflowMapping");
        }
        // verify the required parameter 'workflowName' is set
        if (workflowName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowName' when calling deleteDraftWorkflowMapping");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/workflow").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowName", workflowName));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete draft workflow scheme
     * Deletes a draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission..
     * <p><b>404</b> - Returned if:   *  the original active workflow scheme is not found.  *  the original active workflow scheme does not have a draft.
     * @param id The ID of the active workflow scheme that the draft was created from. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteWorkflowSchemeDraft(Long id) throws RestClientException {
        deleteWorkflowSchemeDraftWithHttpInfo(id);
    }

    /**
     * Delete draft workflow scheme
     * Deletes a draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission..
     * <p><b>404</b> - Returned if:   *  the original active workflow scheme is not found.  *  the original active workflow scheme does not have a draft.
     * @param id The ID of the active workflow scheme that the draft was created from. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteWorkflowSchemeDraftWithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteWorkflowSchemeDraft");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft").buildAndExpand(uriVariables).toUriString();
        
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
     * Delete workflow for issue type in draft workflow scheme
     * Deletes the issue type-workflow mapping for an issue type in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow scheme or issue type is not found.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param issueType The ID of the issue type. (required)
     * @return WorkflowScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowScheme deleteWorkflowSchemeDraftIssueType(Long id, String issueType) throws RestClientException {
        return deleteWorkflowSchemeDraftIssueTypeWithHttpInfo(id, issueType).getBody();
    }

    /**
     * Delete workflow for issue type in draft workflow scheme
     * Deletes the issue type-workflow mapping for an issue type in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow scheme or issue type is not found.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param issueType The ID of the issue type. (required)
     * @return ResponseEntity&lt;WorkflowScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowScheme> deleteWorkflowSchemeDraftIssueTypeWithHttpInfo(Long id, String issueType) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteWorkflowSchemeDraftIssueType");
        }
        // verify the required parameter 'issueType' is set
        if (issueType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueType' when calling deleteWorkflowSchemeDraftIssueType");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        uriVariables.put("issueType", issueType);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/issuetype/{issueType}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<WorkflowScheme> returnType = new ParameterizedTypeReference<WorkflowScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get draft default workflow
     * Returns the default workflow for a workflow scheme&#x27;s draft. The default workflow is the workflow that is assigned any issue types that have not been mapped to any other workflow. The default workflow has *All Unassigned Issue Types* listed in its issue types for the workflow scheme in Jira.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission..
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @return DefaultWorkflow
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DefaultWorkflow getDraftDefaultWorkflow(Long id) throws RestClientException {
        return getDraftDefaultWorkflowWithHttpInfo(id).getBody();
    }

    /**
     * Get draft default workflow
     * Returns the default workflow for a workflow scheme&#x27;s draft. The default workflow is the workflow that is assigned any issue types that have not been mapped to any other workflow. The default workflow has *All Unassigned Issue Types* listed in its issue types for the workflow scheme in Jira.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission..
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @return ResponseEntity&lt;DefaultWorkflow&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DefaultWorkflow> getDraftDefaultWorkflowWithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getDraftDefaultWorkflow");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/default").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<DefaultWorkflow> returnType = new ParameterizedTypeReference<DefaultWorkflow>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get issue types for workflows in draft workflow scheme
     * Returns the workflow-issue type mappings for a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if either the workflow scheme or workflow (if specified) is not found. session.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param workflowName The name of a workflow in the scheme. Limits the results to the workflow-issue type mapping for the specified workflow. (optional)
     * @return IssueTypesWorkflowMapping
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IssueTypesWorkflowMapping getDraftWorkflow(Long id, String workflowName) throws RestClientException {
        return getDraftWorkflowWithHttpInfo(id, workflowName).getBody();
    }

    /**
     * Get issue types for workflows in draft workflow scheme
     * Returns the workflow-issue type mappings for a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if either the workflow scheme or workflow (if specified) is not found. session.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param workflowName The name of a workflow in the scheme. Limits the results to the workflow-issue type mapping for the specified workflow. (optional)
     * @return ResponseEntity&lt;IssueTypesWorkflowMapping&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IssueTypesWorkflowMapping> getDraftWorkflowWithHttpInfo(Long id, String workflowName) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getDraftWorkflow");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/workflow").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowName", workflowName));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<IssueTypesWorkflowMapping> returnType = new ParameterizedTypeReference<IssueTypesWorkflowMapping>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get draft workflow scheme
     * Returns the draft workflow scheme for an active workflow scheme. Draft workflow schemes allow changes to be made to the active workflow schemes: When an active workflow scheme is updated, a draft copy is created. The draft is modified, then the changes in the draft are copied back to the active workflow scheme. See [Configuring workflow schemes](https://confluence.atlassian.com/x/tohKLg) for more information.   Note that:   *  Only active workflow schemes can have draft workflow schemes.  *  An active workflow scheme can only have one draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if:   *  the original active workflow scheme is not found.  *  the original active workflow scheme does not have a draft.
     * @param id The ID of the active workflow scheme that the draft was created from. (required)
     * @return WorkflowScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowScheme getWorkflowSchemeDraft(Long id) throws RestClientException {
        return getWorkflowSchemeDraftWithHttpInfo(id).getBody();
    }

    /**
     * Get draft workflow scheme
     * Returns the draft workflow scheme for an active workflow scheme. Draft workflow schemes allow changes to be made to the active workflow schemes: When an active workflow scheme is updated, a draft copy is created. The draft is modified, then the changes in the draft are copied back to the active workflow scheme. See [Configuring workflow schemes](https://confluence.atlassian.com/x/tohKLg) for more information.   Note that:   *  Only active workflow schemes can have draft workflow schemes.  *  An active workflow scheme can only have one draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if:   *  the original active workflow scheme is not found.  *  the original active workflow scheme does not have a draft.
     * @param id The ID of the active workflow scheme that the draft was created from. (required)
     * @return ResponseEntity&lt;WorkflowScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowScheme> getWorkflowSchemeDraftWithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getWorkflowSchemeDraft");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<WorkflowScheme> returnType = new ParameterizedTypeReference<WorkflowScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get workflow for issue type in draft workflow scheme
     * Returns the issue type-workflow mapping for an issue type in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow scheme or issue type is not found.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param issueType The ID of the issue type. (required)
     * @return IssueTypeWorkflowMapping
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IssueTypeWorkflowMapping getWorkflowSchemeDraftIssueType(Long id, String issueType) throws RestClientException {
        return getWorkflowSchemeDraftIssueTypeWithHttpInfo(id, issueType).getBody();
    }

    /**
     * Get workflow for issue type in draft workflow scheme
     * Returns the issue type-workflow mapping for an issue type in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow scheme or issue type is not found.
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param issueType The ID of the issue type. (required)
     * @return ResponseEntity&lt;IssueTypeWorkflowMapping&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IssueTypeWorkflowMapping> getWorkflowSchemeDraftIssueTypeWithHttpInfo(Long id, String issueType) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getWorkflowSchemeDraftIssueType");
        }
        // verify the required parameter 'issueType' is set
        if (issueType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueType' when calling getWorkflowSchemeDraftIssueType");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        uriVariables.put("issueType", issueType);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/issuetype/{issueType}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<IssueTypeWorkflowMapping> returnType = new ParameterizedTypeReference<IssueTypeWorkflowMapping>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Publish draft workflow scheme
     * Publishes a draft workflow scheme.  Where the draft workflow includes new workflow statuses for an issue type, mappings are provided to update issues with the original workflow status to the new workflow status.  This operation is [asynchronous](#async). Follow the &#x60;location&#x60; link in the response to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain updates.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is only for validation and is successful.
     * <p><b>303</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of these are true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.  *  A new status in the draft workflow scheme is not found.
     * @param body Details of the status mappings. (required)
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param validateOnly Whether the request only performs a validation. (optional, default to false)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void publishDraftWorkflowScheme(PublishDraftWorkflowScheme body, Long id, Boolean validateOnly) throws RestClientException {
        publishDraftWorkflowSchemeWithHttpInfo(body, id, validateOnly);
    }

    /**
     * Publish draft workflow scheme
     * Publishes a draft workflow scheme.  Where the draft workflow includes new workflow statuses for an issue type, mappings are provided to update issues with the original workflow status to the new workflow status.  This operation is [asynchronous](#async). Follow the &#x60;location&#x60; link in the response to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain updates.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is only for validation and is successful.
     * <p><b>303</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of these are true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.  *  A new status in the draft workflow scheme is not found.
     * @param body Details of the status mappings. (required)
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param validateOnly Whether the request only performs a validation. (optional, default to false)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> publishDraftWorkflowSchemeWithHttpInfo(PublishDraftWorkflowScheme body, Long id, Boolean validateOnly) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling publishDraftWorkflowScheme");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling publishDraftWorkflowScheme");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/publish").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "validateOnly", validateOnly));

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
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Set workflow for issue type in draft workflow scheme
     * Sets the workflow for an issue type in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow scheme or issue type is not found.
     * @param body The issue type-project mapping. (required)
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param issueType The ID of the issue type. (required)
     * @return WorkflowScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowScheme setWorkflowSchemeDraftIssueType(IssueTypeWorkflowMapping body, Long id, String issueType) throws RestClientException {
        return setWorkflowSchemeDraftIssueTypeWithHttpInfo(body, id, issueType).getBody();
    }

    /**
     * Set workflow for issue type in draft workflow scheme
     * Sets the workflow for an issue type in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow scheme or issue type is not found.
     * @param body The issue type-project mapping. (required)
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @param issueType The ID of the issue type. (required)
     * @return ResponseEntity&lt;WorkflowScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowScheme> setWorkflowSchemeDraftIssueTypeWithHttpInfo(IssueTypeWorkflowMapping body, Long id, String issueType) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling setWorkflowSchemeDraftIssueType");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling setWorkflowSchemeDraftIssueType");
        }
        // verify the required parameter 'issueType' is set
        if (issueType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'issueType' when calling setWorkflowSchemeDraftIssueType");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        uriVariables.put("issueType", issueType);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/issuetype/{issueType}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<WorkflowScheme> returnType = new ParameterizedTypeReference<WorkflowScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update draft default workflow
     * Sets the default workflow for a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.
     * @param body The object for the new default workflow. (required)
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @return WorkflowScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowScheme updateDraftDefaultWorkflow(DefaultWorkflow body, Long id) throws RestClientException {
        return updateDraftDefaultWorkflowWithHttpInfo(body, id).getBody();
    }

    /**
     * Update draft default workflow
     * Sets the default workflow for a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.
     * @param body The object for the new default workflow. (required)
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @return ResponseEntity&lt;WorkflowScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowScheme> updateDraftDefaultWorkflowWithHttpInfo(DefaultWorkflow body, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateDraftDefaultWorkflow");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling updateDraftDefaultWorkflow");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/default").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<WorkflowScheme> returnType = new ParameterizedTypeReference<WorkflowScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Set issue types for workflow in workflow scheme
     * Sets the issue types for a workflow in a workflow scheme&#x27;s draft. The workflow can also be set as the default workflow for the draft workflow scheme. Unmapped issues types are mapped to the default workflow.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.  *  The workflow is not found.  *  The workflow is not specified.
     * @param body  (required)
     * @param workflowName The name of the workflow. (required)
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @return WorkflowScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowScheme updateDraftWorkflowMapping(IssueTypesWorkflowMapping body, String workflowName, Long id) throws RestClientException {
        return updateDraftWorkflowMappingWithHttpInfo(body, workflowName, id).getBody();
    }

    /**
     * Set issue types for workflow in workflow scheme
     * Sets the issue types for a workflow in a workflow scheme&#x27;s draft. The workflow can also be set as the default workflow for the draft workflow scheme. Unmapped issues types are mapped to the default workflow.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if any of the following is true:   *  The workflow scheme is not found.  *  The workflow scheme does not have a draft.  *  The workflow is not found.  *  The workflow is not specified.
     * @param body  (required)
     * @param workflowName The name of the workflow. (required)
     * @param id The ID of the workflow scheme that the draft belongs to. (required)
     * @return ResponseEntity&lt;WorkflowScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowScheme> updateDraftWorkflowMappingWithHttpInfo(IssueTypesWorkflowMapping body, String workflowName, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateDraftWorkflowMapping");
        }
        // verify the required parameter 'workflowName' is set
        if (workflowName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowName' when calling updateDraftWorkflowMapping");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling updateDraftWorkflowMapping");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft/workflow").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowName", workflowName));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowScheme> returnType = new ParameterizedTypeReference<WorkflowScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update draft workflow scheme
     * Updates a draft workflow scheme. If a draft workflow scheme does not exist for the active workflow scheme, then a draft is created. Note that an active workflow scheme can only have one draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if:   *  the original active workflow scheme is not found.  *  the original active workflow scheme does not have a draft.
     * @param body  (required)
     * @param id The ID of the active workflow scheme that the draft was created from. (required)
     * @return WorkflowScheme
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowScheme updateWorkflowSchemeDraft(WorkflowScheme body, Long id) throws RestClientException {
        return updateWorkflowSchemeDraftWithHttpInfo(body, id).getBody();
    }

    /**
     * Update draft workflow scheme
     * Updates a draft workflow scheme. If a draft workflow scheme does not exist for the active workflow scheme, then a draft is created. Note that an active workflow scheme can only have one draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if:   *  the original active workflow scheme is not found.  *  the original active workflow scheme does not have a draft.
     * @param body  (required)
     * @param id The ID of the active workflow scheme that the draft was created from. (required)
     * @return ResponseEntity&lt;WorkflowScheme&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowScheme> updateWorkflowSchemeDraftWithHttpInfo(WorkflowScheme body, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateWorkflowSchemeDraft");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling updateWorkflowSchemeDraft");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflowscheme/{id}/draft").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<WorkflowScheme> returnType = new ParameterizedTypeReference<WorkflowScheme>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
