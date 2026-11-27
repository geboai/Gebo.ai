package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGroupApprovalRule;
import ai.gebo.gitlab.client.model.APIEntitiesProjectApprovalRule;
import ai.gebo.gitlab.client.model.APIEntitiesProjectApprovalSettingRule;
import ai.gebo.gitlab.client.model.APIEntitiesProjectApprovalSettings;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdApprovalRulesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdApprovalRulesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdApprovalSettingsRulesRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdApprovalRulesApprovalRuleIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest;

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
public class ApprovalRulesApi {
    private ApiClient apiClient;

    public ApprovalRulesApi() {
        this(new ApiClient());
    }

    public ApprovalRulesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an approval rule for a project
     * Deletes an approval rule for a specified project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'approvalRuleId' is set
        if (approvalRuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'approvalRuleId' when calling deleteApiV4ProjectsIdApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/approval_rules/{approval_rule_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an approval rule for a project
     * Deletes an approval rule for a specified project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdApprovalRulesApprovalRuleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId).body(localVarReturnType);
    }

    /**
     * Delete an approval rule for a project
     * Deletes an approval rule for a specified project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdApprovalRulesApprovalRuleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId).toEntity(localVarReturnType);
    }

    /**
     * Delete an approval rule for a project
     * Deletes an approval rule for a specified project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdApprovalRulesApprovalRuleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId);
    }

    /**
     * Delete an approval rule
     * Private API subject to change
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdApprovalSettingsRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'approvalRuleId' is set
        if (approvalRuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'approvalRuleId' when calling deleteApiV4ProjectsIdApprovalSettingsRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/approval_settings/rules/{approval_rule_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an approval rule
     * Private API subject to change
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdApprovalSettingsRulesApprovalRuleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequestCreation(id, approvalRuleId).body(localVarReturnType);
    }

    /**
     * Delete an approval rule
     * Private API subject to change
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequestCreation(id, approvalRuleId).toEntity(localVarReturnType);
    }

    /**
     * Delete an approval rule
     * Private API subject to change
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequestCreation(id, approvalRuleId);
    }

    /**
     * List all approval rules for a group
     * Lists all approval rules and any associated details for a specified group. Restricted to group administrators. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to restrict the list of approval rules. This feature was introduced in GitLab 16.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdApprovalRulesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<List<APIEntitiesGroupApprovalRule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/approval_rules", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all approval rules for a group
     * Lists all approval rules and any associated details for a specified group. Restricted to group administrators. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to restrict the list of approval rules. This feature was introduced in GitLab 16.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroupApprovalRule> getApiV4GroupsIdApprovalRules(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupApprovalRule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdApprovalRulesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all approval rules for a group
     * Lists all approval rules and any associated details for a specified group. Restricted to group administrators. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to restrict the list of approval rules. This feature was introduced in GitLab 16.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroupApprovalRule&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroupApprovalRule>> getApiV4GroupsIdApprovalRulesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupApprovalRule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdApprovalRulesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all approval rules for a group
     * Lists all approval rules and any associated details for a specified group. Restricted to group administrators. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to restrict the list of approval rules. This feature was introduced in GitLab 16.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdApprovalRulesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdApprovalRulesRequestCreation(id, page, perPage);
    }

    /**
     * List all approval rules for a project
     * Lists all approval rules and any associated details for a specified project. To restrict the list of approval rules, use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters. Each rule in the response includes &#x60;eligible_approvers&#x60;, the users who can approve a merge request the rule applies to. Eligible approvers depend on the rule configuration and on project and group membership.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdApprovalRulesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<List<APIEntitiesProjectApprovalRule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/approval_rules", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all approval rules for a project
     * Lists all approval rules and any associated details for a specified project. To restrict the list of approval rules, use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters. Each rule in the response includes &#x60;eligible_approvers&#x60;, the users who can approve a merge request the rule applies to. Eligible approvers depend on the rule configuration and on project and group membership.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProjectApprovalRule> getApiV4ProjectsIdApprovalRules(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectApprovalRule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdApprovalRulesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all approval rules for a project
     * Lists all approval rules and any associated details for a specified project. To restrict the list of approval rules, use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters. Each rule in the response includes &#x60;eligible_approvers&#x60;, the users who can approve a merge request the rule applies to. Eligible approvers depend on the rule configuration and on project and group membership.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProjectApprovalRule&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProjectApprovalRule>> getApiV4ProjectsIdApprovalRulesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectApprovalRule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdApprovalRulesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all approval rules for a project
     * Lists all approval rules and any associated details for a specified project. To restrict the list of approval rules, use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters. Each rule in the response includes &#x60;eligible_approvers&#x60;, the users who can approve a merge request the rule applies to. Eligible approvers depend on the rule configuration and on project and group membership.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdApprovalRulesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdApprovalRulesRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve an approval rule for a project
     * Retrieves information about a specified approval rule for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @return APIEntitiesProjectApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'approvalRuleId' is set
        if (approvalRuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'approvalRuleId' when calling getApiV4ProjectsIdApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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

        ParameterizedTypeReference<APIEntitiesProjectApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/approval_rules/{approval_rule_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an approval rule for a project
     * Retrieves information about a specified approval rule for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @return APIEntitiesProjectApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectApprovalRule getApiV4ProjectsIdApprovalRulesApprovalRuleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId).body(localVarReturnType);
    }

    /**
     * Retrieve an approval rule for a project
     * Retrieves information about a specified approval rule for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @return ResponseEntity&lt;APIEntitiesProjectApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectApprovalRule> getApiV4ProjectsIdApprovalRulesApprovalRuleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an approval rule for a project
     * Retrieves information about a specified approval rule for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdApprovalRulesApprovalRuleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId) throws RestClientResponseException {
        return getApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId);
    }

    /**
     * Get all project approval rules
     * Private API subject to change
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param targetBranch Branch that scoped approval rules apply to.
     * @return APIEntitiesProjectApprovalSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdApprovalSettingsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String targetBranch) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdApprovalSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_branch", targetBranch));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProjectApprovalSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/approval_settings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get all project approval rules
     * Private API subject to change
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param targetBranch Branch that scoped approval rules apply to.
     * @return APIEntitiesProjectApprovalSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectApprovalSettings getApiV4ProjectsIdApprovalSettings(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String targetBranch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdApprovalSettingsRequestCreation(id, targetBranch).body(localVarReturnType);
    }

    /**
     * Get all project approval rules
     * Private API subject to change
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param targetBranch Branch that scoped approval rules apply to.
     * @return ResponseEntity&lt;APIEntitiesProjectApprovalSettings&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectApprovalSettings> getApiV4ProjectsIdApprovalSettingsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String targetBranch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdApprovalSettingsRequestCreation(id, targetBranch).toEntity(localVarReturnType);
    }

    /**
     * Get all project approval rules
     * Private API subject to change
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param targetBranch Branch that scoped approval rules apply to.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdApprovalSettingsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String targetBranch) throws RestClientResponseException {
        return getApiV4ProjectsIdApprovalSettingsRequestCreation(id, targetBranch);
    }

    /**
     * Create an approval rule for a group
     * Creates an approval rule for a group. Restricted to group administrators. Do not use the &#x60;rule_type&#x60; field when building approval rules from the API. The field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdApprovalRulesRequest The postApiV4GroupsIdApprovalRulesRequest parameter
     * @return APIEntitiesGroupApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdApprovalRulesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdApprovalRulesRequest postApiV4GroupsIdApprovalRulesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdApprovalRulesRequest' is set
        if (postApiV4GroupsIdApprovalRulesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdApprovalRulesRequest' when calling postApiV4GroupsIdApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/approval_rules", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdApprovalRulesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an approval rule for a group
     * Creates an approval rule for a group. Restricted to group administrators. Do not use the &#x60;rule_type&#x60; field when building approval rules from the API. The field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdApprovalRulesRequest The postApiV4GroupsIdApprovalRulesRequest parameter
     * @return APIEntitiesGroupApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupApprovalRule postApiV4GroupsIdApprovalRules(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdApprovalRulesRequest postApiV4GroupsIdApprovalRulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdApprovalRulesRequestCreation(id, postApiV4GroupsIdApprovalRulesRequest).body(localVarReturnType);
    }

    /**
     * Create an approval rule for a group
     * Creates an approval rule for a group. Restricted to group administrators. Do not use the &#x60;rule_type&#x60; field when building approval rules from the API. The field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdApprovalRulesRequest The postApiV4GroupsIdApprovalRulesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupApprovalRule> postApiV4GroupsIdApprovalRulesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdApprovalRulesRequest postApiV4GroupsIdApprovalRulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdApprovalRulesRequestCreation(id, postApiV4GroupsIdApprovalRulesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an approval rule for a group
     * Creates an approval rule for a group. Restricted to group administrators. Do not use the &#x60;rule_type&#x60; field when building approval rules from the API. The field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdApprovalRulesRequest The postApiV4GroupsIdApprovalRulesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdApprovalRulesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdApprovalRulesRequest postApiV4GroupsIdApprovalRulesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdApprovalRulesRequestCreation(id, postApiV4GroupsIdApprovalRulesRequest);
    }

    /**
     * Create an approval rule for a project
     * Creates an approval rule for a specified project. The &#x60;rule_type&#x60; field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies. Do not use &#x60;report_approver&#x60; when creating approval rules with this API.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdApprovalRulesRequest The postApiV4ProjectsIdApprovalRulesRequest parameter
     * @return APIEntitiesProjectApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdApprovalRulesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdApprovalRulesRequest postApiV4ProjectsIdApprovalRulesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdApprovalRulesRequest' is set
        if (postApiV4ProjectsIdApprovalRulesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdApprovalRulesRequest' when calling postApiV4ProjectsIdApprovalRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/approval_rules", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdApprovalRulesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an approval rule for a project
     * Creates an approval rule for a specified project. The &#x60;rule_type&#x60; field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies. Do not use &#x60;report_approver&#x60; when creating approval rules with this API.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdApprovalRulesRequest The postApiV4ProjectsIdApprovalRulesRequest parameter
     * @return APIEntitiesProjectApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectApprovalRule postApiV4ProjectsIdApprovalRules(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdApprovalRulesRequest postApiV4ProjectsIdApprovalRulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdApprovalRulesRequestCreation(id, postApiV4ProjectsIdApprovalRulesRequest).body(localVarReturnType);
    }

    /**
     * Create an approval rule for a project
     * Creates an approval rule for a specified project. The &#x60;rule_type&#x60; field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies. Do not use &#x60;report_approver&#x60; when creating approval rules with this API.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdApprovalRulesRequest The postApiV4ProjectsIdApprovalRulesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectApprovalRule> postApiV4ProjectsIdApprovalRulesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdApprovalRulesRequest postApiV4ProjectsIdApprovalRulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdApprovalRulesRequestCreation(id, postApiV4ProjectsIdApprovalRulesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an approval rule for a project
     * Creates an approval rule for a specified project. The &#x60;rule_type&#x60; field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies. Do not use &#x60;report_approver&#x60; when creating approval rules with this API.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdApprovalRulesRequest The postApiV4ProjectsIdApprovalRulesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdApprovalRulesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdApprovalRulesRequest postApiV4ProjectsIdApprovalRulesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdApprovalRulesRequestCreation(id, postApiV4ProjectsIdApprovalRulesRequest);
    }

    /**
     * Create new approval rule
     * Private API subject to change
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdApprovalSettingsRulesRequest The postApiV4ProjectsIdApprovalSettingsRulesRequest parameter
     * @return APIEntitiesProjectApprovalSettingRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdApprovalSettingsRulesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdApprovalSettingsRulesRequest postApiV4ProjectsIdApprovalSettingsRulesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdApprovalSettingsRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdApprovalSettingsRulesRequest' is set
        if (postApiV4ProjectsIdApprovalSettingsRulesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdApprovalSettingsRulesRequest' when calling postApiV4ProjectsIdApprovalSettingsRules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectApprovalSettingRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/approval_settings/rules", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdApprovalSettingsRulesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create new approval rule
     * Private API subject to change
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdApprovalSettingsRulesRequest The postApiV4ProjectsIdApprovalSettingsRulesRequest parameter
     * @return APIEntitiesProjectApprovalSettingRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectApprovalSettingRule postApiV4ProjectsIdApprovalSettingsRules(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdApprovalSettingsRulesRequest postApiV4ProjectsIdApprovalSettingsRulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalSettingRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdApprovalSettingsRulesRequestCreation(id, postApiV4ProjectsIdApprovalSettingsRulesRequest).body(localVarReturnType);
    }

    /**
     * Create new approval rule
     * Private API subject to change
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdApprovalSettingsRulesRequest The postApiV4ProjectsIdApprovalSettingsRulesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectApprovalSettingRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectApprovalSettingRule> postApiV4ProjectsIdApprovalSettingsRulesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdApprovalSettingsRulesRequest postApiV4ProjectsIdApprovalSettingsRulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalSettingRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdApprovalSettingsRulesRequestCreation(id, postApiV4ProjectsIdApprovalSettingsRulesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create new approval rule
     * Private API subject to change
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdApprovalSettingsRulesRequest The postApiV4ProjectsIdApprovalSettingsRulesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdApprovalSettingsRulesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdApprovalSettingsRulesRequest postApiV4ProjectsIdApprovalSettingsRulesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdApprovalSettingsRulesRequestCreation(id, postApiV4ProjectsIdApprovalSettingsRulesRequest);
    }

    /**
     * Update group approval rule
     * Updates an approval rule for a group. Restricted to group administrators. Do not use the &#x60;rule_type&#x60; field when building approval rules from the API. The field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies. This feature was introduced in GitLab 16.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param approvalRuleId ID of the group approval rule.
     * @param putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest The putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest parameter
     * @return APIEntitiesGroupApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdApprovalRulesApprovalRuleIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4GroupsIdApprovalRulesApprovalRuleIdRequest putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'approvalRuleId' is set
        if (approvalRuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'approvalRuleId' when calling putApiV4GroupsIdApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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

        ParameterizedTypeReference<APIEntitiesGroupApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/approval_rules/{approval_rule_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update group approval rule
     * Updates an approval rule for a group. Restricted to group administrators. Do not use the &#x60;rule_type&#x60; field when building approval rules from the API. The field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies. This feature was introduced in GitLab 16.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param approvalRuleId ID of the group approval rule.
     * @param putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest The putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest parameter
     * @return APIEntitiesGroupApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupApprovalRule putApiV4GroupsIdApprovalRulesApprovalRuleId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4GroupsIdApprovalRulesApprovalRuleIdRequest putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId, putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest).body(localVarReturnType);
    }

    /**
     * Update group approval rule
     * Updates an approval rule for a group. Restricted to group administrators. Do not use the &#x60;rule_type&#x60; field when building approval rules from the API. The field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies. This feature was introduced in GitLab 16.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param approvalRuleId ID of the group approval rule.
     * @param putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest The putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupApprovalRule> putApiV4GroupsIdApprovalRulesApprovalRuleIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4GroupsIdApprovalRulesApprovalRuleIdRequest putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId, putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update group approval rule
     * Updates an approval rule for a group. Restricted to group administrators. Do not use the &#x60;rule_type&#x60; field when building approval rules from the API. The field supports these rule types: &#x60;any_approver&#x60;, a pre-configured default rule with &#x60;approvals_required&#x60; set to &#x60;0&#x60;; &#x60;regular&#x60;, for regular merge request approval rules; and &#x60;report_approver&#x60;, used when GitLab creates an approval rule from configured and enabled merge request approval policies. This feature was introduced in GitLab 16.10.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param approvalRuleId ID of the group approval rule.
     * @param putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest The putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdApprovalRulesApprovalRuleIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4GroupsIdApprovalRulesApprovalRuleIdRequest putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId, putApiV4GroupsIdApprovalRulesApprovalRuleIdRequest);
    }

    /**
     * Update an approval rule for a project
     * Updates a specified approval rule for a project. This endpoint removes any approvers and groups not defined in the &#x60;group_ids&#x60;, &#x60;user_ids&#x60;, or &#x60;usernames&#x60; attributes. Hidden groups (private groups the user does not have permission to view) that are not in the &#x60;users&#x60; or &#x60;groups&#x60; parameters are preserved by default. To remove them, set &#x60;remove_hidden_groups&#x60; to &#x60;true&#x60;. This ensures hidden groups are not removed unintentionally when a user updates an approval rule.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @param putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest The putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest parameter
     * @return APIEntitiesProjectApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'approvalRuleId' is set
        if (approvalRuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'approvalRuleId' when calling putApiV4ProjectsIdApprovalRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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

        ParameterizedTypeReference<APIEntitiesProjectApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/approval_rules/{approval_rule_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an approval rule for a project
     * Updates a specified approval rule for a project. This endpoint removes any approvers and groups not defined in the &#x60;group_ids&#x60;, &#x60;user_ids&#x60;, or &#x60;usernames&#x60; attributes. Hidden groups (private groups the user does not have permission to view) that are not in the &#x60;users&#x60; or &#x60;groups&#x60; parameters are preserved by default. To remove them, set &#x60;remove_hidden_groups&#x60; to &#x60;true&#x60;. This ensures hidden groups are not removed unintentionally when a user updates an approval rule.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @param putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest The putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest parameter
     * @return APIEntitiesProjectApprovalRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectApprovalRule putApiV4ProjectsIdApprovalRulesApprovalRuleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId, putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest).body(localVarReturnType);
    }

    /**
     * Update an approval rule for a project
     * Updates a specified approval rule for a project. This endpoint removes any approvers and groups not defined in the &#x60;group_ids&#x60;, &#x60;user_ids&#x60;, or &#x60;usernames&#x60; attributes. Hidden groups (private groups the user does not have permission to view) that are not in the &#x60;users&#x60; or &#x60;groups&#x60; parameters are preserved by default. To remove them, set &#x60;remove_hidden_groups&#x60; to &#x60;true&#x60;. This ensures hidden groups are not removed unintentionally when a user updates an approval rule.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @param putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest The putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectApprovalRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectApprovalRule> putApiV4ProjectsIdApprovalRulesApprovalRuleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId, putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an approval rule for a project
     * Updates a specified approval rule for a project. This endpoint removes any approvers and groups not defined in the &#x60;group_ids&#x60;, &#x60;user_ids&#x60;, or &#x60;usernames&#x60; attributes. Hidden groups (private groups the user does not have permission to view) that are not in the &#x60;users&#x60; or &#x60;groups&#x60; parameters are preserved by default. To remove them, set &#x60;remove_hidden_groups&#x60; to &#x60;true&#x60;. This ensures hidden groups are not removed unintentionally when a user updates an approval rule.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @param putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest The putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdApprovalRulesApprovalRuleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequestCreation(id, approvalRuleId, putApiV4ProjectsIdApprovalRulesApprovalRuleIdRequest);
    }

    /**
     * Update approval rule
     * Private API subject to change
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @param putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest The putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest parameter
     * @return APIEntitiesProjectApprovalSettingRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'approvalRuleId' is set
        if (approvalRuleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'approvalRuleId' when calling putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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

        ParameterizedTypeReference<APIEntitiesProjectApprovalSettingRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/approval_settings/rules/{approval_rule_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update approval rule
     * Private API subject to change
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @param putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest The putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest parameter
     * @return APIEntitiesProjectApprovalSettingRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectApprovalSettingRule putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalSettingRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequestCreation(id, approvalRuleId, putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest).body(localVarReturnType);
    }

    /**
     * Update approval rule
     * Private API subject to change
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @param putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest The putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectApprovalSettingRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectApprovalSettingRule> putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectApprovalSettingRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequestCreation(id, approvalRuleId, putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update approval rule
     * Private API subject to change
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param approvalRuleId ID of the approval rule.
     * @param putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest The putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer approvalRuleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequestCreation(id, approvalRuleId, putApiV4ProjectsIdApprovalSettingsRulesApprovalRuleIdRequest);
    }
}
