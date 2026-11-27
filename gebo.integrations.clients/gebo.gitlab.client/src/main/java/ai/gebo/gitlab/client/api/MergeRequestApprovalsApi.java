package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesApprovalState;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestApprovalRule;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestApprovalSetting;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestApprovalState;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestApprovals;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdMergeRequestApprovalSettingRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMergeRequestApprovalSettingRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient.ResponseSpec;
import org.springframework.web.client.RestClientResponseException;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class MergeRequestApprovalsApi {
    private ApiClient apiClient;

    public MergeRequestApprovalsApi() {
        this(new ApiClient());
    }

    public MergeRequestApprovalsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an approval rule for a merge request
     * Deletes a specified approval rule for a merge request. The &#x60;report_approver&#x60; or &#x60;code_owner&#x60; rules are system-generated, and you cannot edit them.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'approvalRuleId' is set
        if (approvalRuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'approvalRuleId' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("approval_rule_id", approvalRuleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/approval_rules/{approval_rule_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an approval rule for a merge request
     * Deletes a specified approval rule for a merge request. The &#x60;report_approver&#x60; or &#x60;code_owner&#x60; rules are system-generated, and you cannot edit them.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(id, mergeRequestIid, approvalRuleId).body(localVarReturnType);
    }

    /**
     * Delete an approval rule for a merge request
     * Deletes a specified approval rule for a merge request. The &#x60;report_approver&#x60; or &#x60;code_owner&#x60; rules are system-generated, and you cannot edit them.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(id, mergeRequestIid, approvalRuleId).toEntity(localVarReturnType);
    }

    /**
     * Delete an approval rule for a merge request
     * Deletes a specified approval rule for a merge request. The &#x60;report_approver&#x60; or &#x60;code_owner&#x60; rules are system-generated, and you cannot edit them.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(id, mergeRequestIid, approvalRuleId);
    }

    /**
     * Retrieve MR approval settings for a group
     * Retrieves the merge request approval settings for a specified group.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesMergeRequestApprovalSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMergeRequestApprovalSettingRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMergeRequestApprovalSetting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/merge_request_approval_setting", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve MR approval settings for a group
     * Retrieves the merge request approval settings for a specified group.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesMergeRequestApprovalSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovalSetting getApiV4GroupsIdMergeRequestApprovalSetting(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMergeRequestApprovalSettingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve MR approval settings for a group
     * Retrieves the merge request approval settings for a specified group.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovalSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovalSetting> getApiV4GroupsIdMergeRequestApprovalSettingWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMergeRequestApprovalSettingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve MR approval settings for a group
     * Retrieves the merge request approval settings for a specified group.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMergeRequestApprovalSettingWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4GroupsIdMergeRequestApprovalSettingRequestCreation(id);
    }

    /**
     * Retrieve MR approval settings for a project
     * Retrieves the merge request approval settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesMergeRequestApprovalSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestApprovalSettingRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestApprovalSetting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_request_approval_setting", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve MR approval settings for a project
     * Retrieves the merge request approval settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesMergeRequestApprovalSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovalSetting getApiV4ProjectsIdMergeRequestApprovalSetting(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestApprovalSettingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve MR approval settings for a project
     * Retrieves the merge request approval settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovalSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovalSetting> getApiV4ProjectsIdMergeRequestApprovalSettingWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestApprovalSettingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve MR approval settings for a project
     * Retrieves the merge request approval settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestApprovalSettingWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestApprovalSettingRequestCreation(id);
    }

    /**
     * List all approval rules for a merge request
     * Lists all approval rules and any associated details for a specified merge request. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to restrict the list of approval rules.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMergeRequestApprovalRule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/approval_rules", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all approval rules for a merge request
     * Lists all approval rules and any associated details for a specified merge request. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to restrict the list of approval rules.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMergeRequestApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestApprovalRule> getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRules(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestApprovalRule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequestCreation(id, mergeRequestIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all approval rules for a merge request
     * Lists all approval rules and any associated details for a specified merge request. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to restrict the list of approval rules.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestApprovalRule&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestApprovalRule>> getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestApprovalRule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequestCreation(id, mergeRequestIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all approval rules for a merge request
     * Lists all approval rules and any associated details for a specified merge request. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to restrict the list of approval rules.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequestCreation(id, mergeRequestIid, page, perPage);
    }

    /**
     * Retrieve an approval rule for a specific merge request
     * Retrieves information about an approval rule for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @return APIEntitiesMergeRequestApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'approvalRuleId' is set
        if (approvalRuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'approvalRuleId' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("approval_rule_id", approvalRuleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/approval_rules/{approval_rule_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an approval rule for a specific merge request
     * Retrieves information about an approval rule for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @return APIEntitiesMergeRequestApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovalRule getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(id, mergeRequestIid, approvalRuleId).body(localVarReturnType);
    }

    /**
     * Retrieve an approval rule for a specific merge request
     * Retrieves information about an approval rule for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovalRule> getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(id, mergeRequestIid, approvalRuleId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an approval rule for a specific merge request
     * Retrieves information about an approval rule for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(id, mergeRequestIid, approvalRuleId);
    }

    /**
     * Retrieve approval details for a merge request
     * Retrieves approval details for a specified merge request. If a user has modified the approval rules for the merge request, the response includes &#x60;approval_rules_overwritten&#x60;. In each rule, &#x60;approved&#x60; is &#x60;true&#x60; when the rule is approved, and &#x60;approved_by&#x60; lists the users who approved it. Users who do not match an approval rule are not returned. To get all approving users, use &#x60;GET /projects/:id/merge_requests/:merge_request_iid/approvals&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeRequestApprovalState
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalStateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalState", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalState", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalState> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/approval_state", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve approval details for a merge request
     * Retrieves approval details for a specified merge request. If a user has modified the approval rules for the merge request, the response includes &#x60;approval_rules_overwritten&#x60;. In each rule, &#x60;approved&#x60; is &#x60;true&#x60; when the rule is approved, and &#x60;approved_by&#x60; lists the users who approved it. Users who do not match an approval rule are not returned. To get all approving users, use &#x60;GET /projects/:id/merge_requests/:merge_request_iid/approvals&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeRequestApprovalState
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovalState getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalState(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalState> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalStateRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Retrieve approval details for a merge request
     * Retrieves approval details for a specified merge request. If a user has modified the approval rules for the merge request, the response includes &#x60;approval_rules_overwritten&#x60;. In each rule, &#x60;approved&#x60; is &#x60;true&#x60; when the rule is approved, and &#x60;approved_by&#x60; lists the users who approved it. Users who do not match an approval rule are not returned. To get all approving users, use &#x60;GET /projects/:id/merge_requests/:merge_request_iid/approvals&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovalState&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovalState> getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalStateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalState> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalStateRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve approval details for a merge request
     * Retrieves approval details for a specified merge request. If a user has modified the approval rules for the merge request, the response includes &#x60;approval_rules_overwritten&#x60;. In each rule, &#x60;approved&#x60; is &#x60;true&#x60; when the rule is approved, and &#x60;approved_by&#x60; lists the users who approved it. Users who do not match an approval rule are not returned. To get all approving users, use &#x60;GET /projects/:id/merge_requests/:merge_request_iid/approvals&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalStateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalStateRequestCreation(id, mergeRequestIid);
    }

    /**
     * Retrieve approval state for a merge request
     * Retrieves the approval state for a specified merge request. In the response, &#x60;approved_by&#x60; contains information about all approvers of the merge request, regardless of whether those approvals satisfy any approval rule. In GitLab Community Edition, the response contains only &#x60;approved&#x60;, &#x60;approved_by&#x60;, &#x60;user_can_approve&#x60;, and &#x60;user_has_approved&#x60;, and &#x60;approved&#x60; is &#x60;true&#x60; only when at least one approval exists. Otherwise, &#x60;approved&#x60; is &#x60;true&#x60; when the approval rules are satisfied, including when no approval rules apply and &#x60;approved_by&#x60; is empty.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeRequestApprovals
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovals", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovals", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovals> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/approvals", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve approval state for a merge request
     * Retrieves the approval state for a specified merge request. In the response, &#x60;approved_by&#x60; contains information about all approvers of the merge request, regardless of whether those approvals satisfy any approval rule. In GitLab Community Edition, the response contains only &#x60;approved&#x60;, &#x60;approved_by&#x60;, &#x60;user_can_approve&#x60;, and &#x60;user_has_approved&#x60;, and &#x60;approved&#x60; is &#x60;true&#x60; only when at least one approval exists. Otherwise, &#x60;approved&#x60; is &#x60;true&#x60; when the approval rules are satisfied, including when no approval rules apply and &#x60;approved_by&#x60; is empty.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeRequestApprovals
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovals getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovals(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovals> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Retrieve approval state for a merge request
     * Retrieves the approval state for a specified merge request. In the response, &#x60;approved_by&#x60; contains information about all approvers of the merge request, regardless of whether those approvals satisfy any approval rule. In GitLab Community Edition, the response contains only &#x60;approved&#x60;, &#x60;approved_by&#x60;, &#x60;user_can_approve&#x60;, and &#x60;user_has_approved&#x60;, and &#x60;approved&#x60; is &#x60;true&#x60; only when at least one approval exists. Otherwise, &#x60;approved&#x60; is &#x60;true&#x60; when the approval rules are satisfied, including when no approval rules apply and &#x60;approved_by&#x60; is empty.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovals&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovals> getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovals> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve approval state for a merge request
     * Retrieves the approval state for a specified merge request. In the response, &#x60;approved_by&#x60; contains information about all approvers of the merge request, regardless of whether those approvals satisfy any approval rule. In GitLab Community Edition, the response contains only &#x60;approved&#x60;, &#x60;approved_by&#x60;, &#x60;user_can_approve&#x60;, and &#x60;user_has_approved&#x60;, and &#x60;approved&#x60; is &#x60;true&#x60; only when at least one approval exists. Otherwise, &#x60;approved&#x60; is &#x60;true&#x60; when the approval rules are satisfied, including when no approval rules apply and &#x60;approved_by&#x60; is empty.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequestCreation(id, mergeRequestIid);
    }

    /**
     * Create an approval rule for a merge request
     * Creates an approval rule for a specified merge request. If &#x60;approval_project_rule_id&#x60; is set with the ID of an existing approval rule from the project, this endpoint copies the values for &#x60;name&#x60;, &#x60;users&#x60;, and &#x60;groups&#x60; from the project rule, and uses the &#x60;approvals_required&#x60; value you specify.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest parameter
     * @return APIEntitiesMergeRequestApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/approval_rules", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an approval rule for a merge request
     * Creates an approval rule for a specified merge request. If &#x60;approval_project_rule_id&#x60; is set with the ID of an existing approval rule from the project, this endpoint copies the values for &#x60;name&#x60;, &#x60;users&#x60;, and &#x60;groups&#x60; from the project rule, and uses the &#x60;approvals_required&#x60; value you specify.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest parameter
     * @return APIEntitiesMergeRequestApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovalRule postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRules(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest).body(localVarReturnType);
    }

    /**
     * Create an approval rule for a merge request
     * Creates an approval rule for a specified merge request. If &#x60;approval_project_rule_id&#x60; is set with the ID of an existing approval rule from the project, this endpoint copies the values for &#x60;name&#x60;, &#x60;users&#x60;, and &#x60;groups&#x60; from the project rule, and uses the &#x60;approvals_required&#x60; value you specify.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovalRule> postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an approval rule for a merge request
     * Creates an approval rule for a specified merge request. If &#x60;approval_project_rule_id&#x60; is set with the ID of an existing approval rule from the project, this endpoint copies the values for &#x60;name&#x60;, &#x60;users&#x60;, and &#x60;groups&#x60; from the project rule, and uses the &#x60;approvals_required&#x60; value you specify.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesRequest);
    }

    /**
     * Change approval-related configuration
     * Deprecated in 16.0. Use the merge request approvals API instead.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest parameter
     * @return APIEntitiesApprovalState
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovals", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovals", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovals", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesApprovalState> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/approvals", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Change approval-related configuration
     * Deprecated in 16.0. Use the merge request approvals API instead.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest parameter
     * @return APIEntitiesApprovalState
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public APIEntitiesApprovalState postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovals(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApprovalState> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest).body(localVarReturnType);
    }

    /**
     * Change approval-related configuration
     * Deprecated in 16.0. Use the merge request approvals API instead.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesApprovalState&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<APIEntitiesApprovalState> postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApprovalState> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest).toEntity(localVarReturnType);
    }

    /**
     * Change approval-related configuration
     * Deprecated in 16.0. Use the merge request approvals API instead.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalsRequest);
    }

    /**
     * Approve merge request
     * Approves a specified merge request. The currently authenticated user must be an eligible approver. The &#x60;sha&#x60; parameter ensures you are approving the current version of the merge request. If defined, the value must match the merge request’s HEAD commit SHA, and the diff must not have changed since a version with that SHA, for example after a target branch change. Otherwise returns a &#x60;409 Conflict&#x60; response. An approval sent immediately after the merge request is created can come before its commit is processed, and a new commit can then reset the approval. To avoid this, wait until &#x60;detailed_merge_status&#x60; is not &#x60;checking&#x60; or &#x60;approvals_syncing&#x60; and the merge request diff has a &#x60;patch_id_sha&#x60;, or send &#x60;sha&#x60;. With &#x60;sha&#x60;, GitLab records the approved version even before a diff exists, so a later push does not reset the approval. The response has the same attributes as retrieving the approval state, which depend on the edition.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest parameter
     * @return APIEntitiesMergeRequestApprovals
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidApprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidApprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovals> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/approve", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Approve merge request
     * Approves a specified merge request. The currently authenticated user must be an eligible approver. The &#x60;sha&#x60; parameter ensures you are approving the current version of the merge request. If defined, the value must match the merge request’s HEAD commit SHA, and the diff must not have changed since a version with that SHA, for example after a target branch change. Otherwise returns a &#x60;409 Conflict&#x60; response. An approval sent immediately after the merge request is created can come before its commit is processed, and a new commit can then reset the approval. To avoid this, wait until &#x60;detailed_merge_status&#x60; is not &#x60;checking&#x60; or &#x60;approvals_syncing&#x60; and the merge request diff has a &#x60;patch_id_sha&#x60;, or send &#x60;sha&#x60;. With &#x60;sha&#x60;, GitLab records the approved version even before a diff exists, so a later push does not reset the approval. The response has the same attributes as retrieving the approval state, which depend on the edition.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest parameter
     * @return APIEntitiesMergeRequestApprovals
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovals postApiV4ProjectsIdMergeRequestsMergeRequestIidApprove(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovals> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest).body(localVarReturnType);
    }

    /**
     * Approve merge request
     * Approves a specified merge request. The currently authenticated user must be an eligible approver. The &#x60;sha&#x60; parameter ensures you are approving the current version of the merge request. If defined, the value must match the merge request’s HEAD commit SHA, and the diff must not have changed since a version with that SHA, for example after a target branch change. Otherwise returns a &#x60;409 Conflict&#x60; response. An approval sent immediately after the merge request is created can come before its commit is processed, and a new commit can then reset the approval. To avoid this, wait until &#x60;detailed_merge_status&#x60; is not &#x60;checking&#x60; or &#x60;approvals_syncing&#x60; and the merge request diff has a &#x60;patch_id_sha&#x60;, or send &#x60;sha&#x60;. With &#x60;sha&#x60;, GitLab records the approved version even before a diff exists, so a later push does not reset the approval. The response has the same attributes as retrieving the approval state, which depend on the edition.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovals&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovals> postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovals> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest).toEntity(localVarReturnType);
    }

    /**
     * Approve merge request
     * Approves a specified merge request. The currently authenticated user must be an eligible approver. The &#x60;sha&#x60; parameter ensures you are approving the current version of the merge request. If defined, the value must match the merge request’s HEAD commit SHA, and the diff must not have changed since a version with that SHA, for example after a target branch change. Otherwise returns a &#x60;409 Conflict&#x60; response. An approval sent immediately after the merge request is created can come before its commit is processed, and a new commit can then reset the approval. To avoid this, wait until &#x60;detailed_merge_status&#x60; is not &#x60;checking&#x60; or &#x60;approvals_syncing&#x60; and the merge request diff has a &#x60;patch_id_sha&#x60;, or send &#x60;sha&#x60;. With &#x60;sha&#x60;, GitLab records the approved version even before a diff exists, so a later push does not reset the approval. The response has the same attributes as retrieving the approval state, which depend on the edition.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidApproveRequest);
    }

    /**
     * Unapprove a merge request
     * Unapproves a merge request. Removes the approval for the currently authenticated user from a specified merge request. The response has the same attributes as retrieving the approval state, which depend on the edition.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeRequestApprovals
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidUnapproveRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidUnapprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidUnapprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovals> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/unapprove", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unapprove a merge request
     * Unapproves a merge request. Removes the approval for the currently authenticated user from a specified merge request. The response has the same attributes as retrieving the approval state, which depend on the edition.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesMergeRequestApprovals
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovals postApiV4ProjectsIdMergeRequestsMergeRequestIidUnapprove(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovals> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidUnapproveRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Unapprove a merge request
     * Unapproves a merge request. Removes the approval for the currently authenticated user from a specified merge request. The response has the same attributes as retrieving the approval state, which depend on the edition.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovals&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovals> postApiV4ProjectsIdMergeRequestsMergeRequestIidUnapproveWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovals> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidUnapproveRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Unapprove a merge request
     * Unapproves a merge request. Removes the approval for the currently authenticated user from a specified merge request. The response has the same attributes as retrieving the approval state, which depend on the edition.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidUnapproveWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidUnapproveRequestCreation(id, mergeRequestIid);
    }

    /**
     * Update MR approval settings for a group
     * Updates the merge request approval settings for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdMergeRequestApprovalSettingRequest The putApiV4GroupsIdMergeRequestApprovalSettingRequest parameter
     * @return APIEntitiesMergeRequestApprovalSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdMergeRequestApprovalSettingRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdMergeRequestApprovalSettingRequest putApiV4GroupsIdMergeRequestApprovalSettingRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdMergeRequestApprovalSetting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/merge_request_approval_setting", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdMergeRequestApprovalSettingRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update MR approval settings for a group
     * Updates the merge request approval settings for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdMergeRequestApprovalSettingRequest The putApiV4GroupsIdMergeRequestApprovalSettingRequest parameter
     * @return APIEntitiesMergeRequestApprovalSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovalSetting putApiV4GroupsIdMergeRequestApprovalSetting(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdMergeRequestApprovalSettingRequest putApiV4GroupsIdMergeRequestApprovalSettingRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdMergeRequestApprovalSettingRequestCreation(id, putApiV4GroupsIdMergeRequestApprovalSettingRequest).body(localVarReturnType);
    }

    /**
     * Update MR approval settings for a group
     * Updates the merge request approval settings for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdMergeRequestApprovalSettingRequest The putApiV4GroupsIdMergeRequestApprovalSettingRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovalSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovalSetting> putApiV4GroupsIdMergeRequestApprovalSettingWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdMergeRequestApprovalSettingRequest putApiV4GroupsIdMergeRequestApprovalSettingRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdMergeRequestApprovalSettingRequestCreation(id, putApiV4GroupsIdMergeRequestApprovalSettingRequest).toEntity(localVarReturnType);
    }

    /**
     * Update MR approval settings for a group
     * Updates the merge request approval settings for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdMergeRequestApprovalSettingRequest The putApiV4GroupsIdMergeRequestApprovalSettingRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdMergeRequestApprovalSettingWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdMergeRequestApprovalSettingRequest putApiV4GroupsIdMergeRequestApprovalSettingRequest) throws RestClientResponseException {
        return putApiV4GroupsIdMergeRequestApprovalSettingRequestCreation(id, putApiV4GroupsIdMergeRequestApprovalSettingRequest);
    }

    /**
     * Update MR approval settings for a project
     * Updates the merge request approval settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdMergeRequestApprovalSettingRequest The putApiV4ProjectsIdMergeRequestApprovalSettingRequest parameter
     * @return APIEntitiesMergeRequestApprovalSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestApprovalSettingRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestApprovalSettingRequest putApiV4ProjectsIdMergeRequestApprovalSettingRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestApprovalSetting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_request_approval_setting", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMergeRequestApprovalSettingRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update MR approval settings for a project
     * Updates the merge request approval settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdMergeRequestApprovalSettingRequest The putApiV4ProjectsIdMergeRequestApprovalSettingRequest parameter
     * @return APIEntitiesMergeRequestApprovalSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovalSetting putApiV4ProjectsIdMergeRequestApprovalSetting(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestApprovalSettingRequest putApiV4ProjectsIdMergeRequestApprovalSettingRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestApprovalSettingRequestCreation(id, putApiV4ProjectsIdMergeRequestApprovalSettingRequest).body(localVarReturnType);
    }

    /**
     * Update MR approval settings for a project
     * Updates the merge request approval settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdMergeRequestApprovalSettingRequest The putApiV4ProjectsIdMergeRequestApprovalSettingRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovalSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovalSetting> putApiV4ProjectsIdMergeRequestApprovalSettingWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestApprovalSettingRequest putApiV4ProjectsIdMergeRequestApprovalSettingRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestApprovalSettingRequestCreation(id, putApiV4ProjectsIdMergeRequestApprovalSettingRequest).toEntity(localVarReturnType);
    }

    /**
     * Update MR approval settings for a project
     * Updates the merge request approval settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdMergeRequestApprovalSettingRequest The putApiV4ProjectsIdMergeRequestApprovalSettingRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestApprovalSettingWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestApprovalSettingRequest putApiV4ProjectsIdMergeRequestApprovalSettingRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestApprovalSettingRequestCreation(id, putApiV4ProjectsIdMergeRequestApprovalSettingRequest);
    }

    /**
     * Update an approval rule for a merge request
     * Updates a specified approval rule for a merge request. This endpoint removes any approvers and groups not included in the &#x60;group_ids&#x60;, &#x60;user_ids&#x60;, or &#x60;usernames&#x60; attributes. The &#x60;report_approver&#x60; or &#x60;code_owner&#x60; rules are system-generated, and you cannot edit them.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest parameter
     * @return APIEntitiesMergeRequestApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'approvalRuleId' is set
        if (approvalRuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'approvalRuleId' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("approval_rule_id", approvalRuleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/approval_rules/{approval_rule_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an approval rule for a merge request
     * Updates a specified approval rule for a merge request. This endpoint removes any approvers and groups not included in the &#x60;group_ids&#x60;, &#x60;user_ids&#x60;, or &#x60;usernames&#x60; attributes. The &#x60;report_approver&#x60; or &#x60;code_owner&#x60; rules are system-generated, and you cannot edit them.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest parameter
     * @return APIEntitiesMergeRequestApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequestApprovalRule putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(id, mergeRequestIid, approvalRuleId, putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest).body(localVarReturnType);
    }

    /**
     * Update an approval rule for a merge request
     * Updates a specified approval rule for a merge request. This endpoint removes any approvers and groups not included in the &#x60;group_ids&#x60;, &#x60;user_ids&#x60;, or &#x60;usernames&#x60; attributes. The &#x60;report_approver&#x60; or &#x60;code_owner&#x60; rules are system-generated, and you cannot edit them.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMergeRequestApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequestApprovalRule> putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequestApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(id, mergeRequestIid, approvalRuleId, putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an approval rule for a merge request
     * Updates a specified approval rule for a merge request. This endpoint removes any approvers and groups not included in the &#x60;group_ids&#x60;, &#x60;user_ids&#x60;, or &#x60;usernames&#x60; attributes. The &#x60;report_approver&#x60; or &#x60;code_owner&#x60; rules are system-generated, and you cannot edit them.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param approvalRuleId ID of the merge request approval rule.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequestCreation(id, mergeRequestIid, approvalRuleId, putApiV4ProjectsIdMergeRequestsMergeRequestIidApprovalRulesApprovalRuleIdRequest);
    }

    /**
     * Reset approvals for a merge request
     * Resets all approvals for a specified merge request. Available only to bot users with a valid project or group token. Human users receive a &#x60;401 Unauthorized&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidResetApprovalsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidResetApprovals", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidResetApprovals", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/reset_approvals", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset approvals for a merge request
     * Resets all approvals for a specified merge request. Available only to bot users with a valid project or group token. Human users receive a &#x60;401 Unauthorized&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdMergeRequestsMergeRequestIidResetApprovals(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdMergeRequestsMergeRequestIidResetApprovalsRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Reset approvals for a merge request
     * Resets all approvals for a specified merge request. Available only to bot users with a valid project or group token. Human users receive a &#x60;401 Unauthorized&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdMergeRequestsMergeRequestIidResetApprovalsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidResetApprovalsRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Reset approvals for a merge request
     * Resets all approvals for a specified merge request. Available only to bot users with a valid project or group token. Human users receive a &#x60;401 Unauthorized&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidResetApprovalsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidResetApprovalsRequestCreation(id, mergeRequestIid);
    }
}
