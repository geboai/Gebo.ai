package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGroupPushRule;
import ai.gebo.gitlab.client.model.APIEntitiesProjectPushRule;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdPushRuleRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdPushRuleRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdPushRuleRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPushRuleRequest;

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
public class PushRulesApi {
    private ApiClient apiClient;

    public PushRulesApi() {
        this(new ApiClient());
    }

    public PushRulesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete the push rules of a group
     * Deletes all the push rules of a specified group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdPushRuleRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdPushRule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/push_rule", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete the push rules of a group
     * Deletes all the push rules of a specified group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdPushRule(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdPushRuleRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete the push rules of a group
     * Deletes all the push rules of a specified group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdPushRuleWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdPushRuleRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete the push rules of a group
     * Deletes all the push rules of a specified group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdPushRuleWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return deleteApiV4GroupsIdPushRuleRequestCreation(id);
    }

    /**
     * Delete the push rules of a project
     * Deletes all the push rules of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPushRuleRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPushRule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/push_rule", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete the push rules of a project
     * Deletes all the push rules of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPushRule(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPushRuleRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete the push rules of a project
     * Deletes all the push rules of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPushRuleWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPushRuleRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete the push rules of a project
     * Deletes all the push rules of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPushRuleWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPushRuleRequestCreation(id);
    }

    /**
     * Retrieve the push rules of a group
     * Retrieves the push rules of a specified group. When disabled, the &#x60;commit_committer_check&#x60;, &#x60;reject_unsigned_commits&#x60;, and &#x60;reject_non_dco_commits&#x60; attributes can return &#x60;null&#x60; instead of &#x60;false&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesGroupPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdPushRuleRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdPushRule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/push_rule", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the push rules of a group
     * Retrieves the push rules of a specified group. When disabled, the &#x60;commit_committer_check&#x60;, &#x60;reject_unsigned_commits&#x60;, and &#x60;reject_non_dco_commits&#x60; attributes can return &#x60;null&#x60; instead of &#x60;false&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesGroupPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupPushRule getApiV4GroupsIdPushRule(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdPushRuleRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve the push rules of a group
     * Retrieves the push rules of a specified group. When disabled, the &#x60;commit_committer_check&#x60;, &#x60;reject_unsigned_commits&#x60;, and &#x60;reject_non_dco_commits&#x60; attributes can return &#x60;null&#x60; instead of &#x60;false&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;APIEntitiesGroupPushRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupPushRule> getApiV4GroupsIdPushRuleWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdPushRuleRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the push rules of a group
     * Retrieves the push rules of a specified group. When disabled, the &#x60;commit_committer_check&#x60;, &#x60;reject_unsigned_commits&#x60;, and &#x60;reject_non_dco_commits&#x60; attributes can return &#x60;null&#x60; instead of &#x60;false&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdPushRuleWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4GroupsIdPushRuleRequestCreation(id);
    }

    /**
     * Retrieve the push rules of a project
     * Retrieves the push rules of a specified project. If push rules were never configured for the project, returns &#x60;200 OK&#x60; with the literal string &#x60;\&quot;null\&quot;&#x60; (4 characters) as the response body, not a JSON &#x60;null&#x60; value. If &#x60;commit_committer_check&#x60;, &#x60;reject_unsigned_commits&#x60;, or &#x60;reject_non_dco_commits&#x60; are disabled, they return &#x60;null&#x60; instead of &#x60;false&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesProjectPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPushRuleRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPushRule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/push_rule", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the push rules of a project
     * Retrieves the push rules of a specified project. If push rules were never configured for the project, returns &#x60;200 OK&#x60; with the literal string &#x60;\&quot;null\&quot;&#x60; (4 characters) as the response body, not a JSON &#x60;null&#x60; value. If &#x60;commit_committer_check&#x60;, &#x60;reject_unsigned_commits&#x60;, or &#x60;reject_non_dco_commits&#x60; are disabled, they return &#x60;null&#x60; instead of &#x60;false&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesProjectPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectPushRule getApiV4ProjectsIdPushRule(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPushRuleRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve the push rules of a project
     * Retrieves the push rules of a specified project. If push rules were never configured for the project, returns &#x60;200 OK&#x60; with the literal string &#x60;\&quot;null\&quot;&#x60; (4 characters) as the response body, not a JSON &#x60;null&#x60; value. If &#x60;commit_committer_check&#x60;, &#x60;reject_unsigned_commits&#x60;, or &#x60;reject_non_dco_commits&#x60; are disabled, they return &#x60;null&#x60; instead of &#x60;false&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesProjectPushRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectPushRule> getApiV4ProjectsIdPushRuleWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPushRuleRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the push rules of a project
     * Retrieves the push rules of a specified project. If push rules were never configured for the project, returns &#x60;200 OK&#x60; with the literal string &#x60;\&quot;null\&quot;&#x60; (4 characters) as the response body, not a JSON &#x60;null&#x60; value. If &#x60;commit_committer_check&#x60;, &#x60;reject_unsigned_commits&#x60;, or &#x60;reject_non_dco_commits&#x60; are disabled, they return &#x60;null&#x60; instead of &#x60;false&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPushRuleWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPushRuleRequestCreation(id);
    }

    /**
     * Add push rules to a group
     * Adds push rules to a specified group. Use only if you have not defined any push rules so far.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdPushRuleRequest The postApiV4GroupsIdPushRuleRequest parameter
     * @return APIEntitiesGroupPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdPushRuleRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdPushRuleRequest postApiV4GroupsIdPushRuleRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdPushRule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/push_rule", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdPushRuleRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add push rules to a group
     * Adds push rules to a specified group. Use only if you have not defined any push rules so far.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdPushRuleRequest The postApiV4GroupsIdPushRuleRequest parameter
     * @return APIEntitiesGroupPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupPushRule postApiV4GroupsIdPushRule(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdPushRuleRequest postApiV4GroupsIdPushRuleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdPushRuleRequestCreation(id, postApiV4GroupsIdPushRuleRequest).body(localVarReturnType);
    }

    /**
     * Add push rules to a group
     * Adds push rules to a specified group. Use only if you have not defined any push rules so far.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdPushRuleRequest The postApiV4GroupsIdPushRuleRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupPushRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupPushRule> postApiV4GroupsIdPushRuleWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdPushRuleRequest postApiV4GroupsIdPushRuleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdPushRuleRequestCreation(id, postApiV4GroupsIdPushRuleRequest).toEntity(localVarReturnType);
    }

    /**
     * Add push rules to a group
     * Adds push rules to a specified group. Use only if you have not defined any push rules so far.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdPushRuleRequest The postApiV4GroupsIdPushRuleRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdPushRuleWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdPushRuleRequest postApiV4GroupsIdPushRuleRequest) throws RestClientResponseException {
        return postApiV4GroupsIdPushRuleRequestCreation(id, postApiV4GroupsIdPushRuleRequest);
    }

    /**
     * Add push rules to a project
     * Adds push rules to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPushRuleRequest The postApiV4ProjectsIdPushRuleRequest parameter
     * @return APIEntitiesProjectPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPushRuleRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdPushRuleRequest postApiV4ProjectsIdPushRuleRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPushRule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/push_rule", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdPushRuleRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add push rules to a project
     * Adds push rules to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPushRuleRequest The postApiV4ProjectsIdPushRuleRequest parameter
     * @return APIEntitiesProjectPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectPushRule postApiV4ProjectsIdPushRule(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdPushRuleRequest postApiV4ProjectsIdPushRuleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPushRuleRequestCreation(id, postApiV4ProjectsIdPushRuleRequest).body(localVarReturnType);
    }

    /**
     * Add push rules to a project
     * Adds push rules to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPushRuleRequest The postApiV4ProjectsIdPushRuleRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectPushRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectPushRule> postApiV4ProjectsIdPushRuleWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdPushRuleRequest postApiV4ProjectsIdPushRuleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPushRuleRequestCreation(id, postApiV4ProjectsIdPushRuleRequest).toEntity(localVarReturnType);
    }

    /**
     * Add push rules to a project
     * Adds push rules to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPushRuleRequest The postApiV4ProjectsIdPushRuleRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPushRuleWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdPushRuleRequest postApiV4ProjectsIdPushRuleRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdPushRuleRequestCreation(id, postApiV4ProjectsIdPushRuleRequest);
    }

    /**
     * Update push rules of a group
     * Updates the push rules for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdPushRuleRequest The putApiV4GroupsIdPushRuleRequest parameter
     * @return APIEntitiesGroupPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdPushRuleRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdPushRuleRequest putApiV4GroupsIdPushRuleRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdPushRule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/push_rule", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdPushRuleRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update push rules of a group
     * Updates the push rules for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdPushRuleRequest The putApiV4GroupsIdPushRuleRequest parameter
     * @return APIEntitiesGroupPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupPushRule putApiV4GroupsIdPushRule(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdPushRuleRequest putApiV4GroupsIdPushRuleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdPushRuleRequestCreation(id, putApiV4GroupsIdPushRuleRequest).body(localVarReturnType);
    }

    /**
     * Update push rules of a group
     * Updates the push rules for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdPushRuleRequest The putApiV4GroupsIdPushRuleRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupPushRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupPushRule> putApiV4GroupsIdPushRuleWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdPushRuleRequest putApiV4GroupsIdPushRuleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdPushRuleRequestCreation(id, putApiV4GroupsIdPushRuleRequest).toEntity(localVarReturnType);
    }

    /**
     * Update push rules of a group
     * Updates the push rules for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdPushRuleRequest The putApiV4GroupsIdPushRuleRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdPushRuleWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdPushRuleRequest putApiV4GroupsIdPushRuleRequest) throws RestClientResponseException {
        return putApiV4GroupsIdPushRuleRequestCreation(id, putApiV4GroupsIdPushRuleRequest);
    }

    /**
     * Update push rules of a project
     * Updates the push rules for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdPushRuleRequest The putApiV4ProjectsIdPushRuleRequest parameter
     * @return APIEntitiesProjectPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPushRuleRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdPushRuleRequest putApiV4ProjectsIdPushRuleRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPushRule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/push_rule", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPushRuleRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update push rules of a project
     * Updates the push rules for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdPushRuleRequest The putApiV4ProjectsIdPushRuleRequest parameter
     * @return APIEntitiesProjectPushRule
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectPushRule putApiV4ProjectsIdPushRule(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdPushRuleRequest putApiV4ProjectsIdPushRuleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPushRuleRequestCreation(id, putApiV4ProjectsIdPushRuleRequest).body(localVarReturnType);
    }

    /**
     * Update push rules of a project
     * Updates the push rules for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdPushRuleRequest The putApiV4ProjectsIdPushRuleRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectPushRule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectPushRule> putApiV4ProjectsIdPushRuleWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdPushRuleRequest putApiV4ProjectsIdPushRuleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectPushRule> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPushRuleRequestCreation(id, putApiV4ProjectsIdPushRuleRequest).toEntity(localVarReturnType);
    }

    /**
     * Update push rules of a project
     * Updates the push rules for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdPushRuleRequest The putApiV4ProjectsIdPushRuleRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPushRuleWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdPushRuleRequest putApiV4ProjectsIdPushRuleRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPushRuleRequestCreation(id, putApiV4ProjectsIdPushRuleRequest);
    }
}
