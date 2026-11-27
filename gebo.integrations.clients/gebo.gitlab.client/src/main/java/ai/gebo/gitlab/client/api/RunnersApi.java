package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBasicProjectDetails;
import ai.gebo.gitlab.client.model.APIEntitiesCiJobBasicWithProject;
import ai.gebo.gitlab.client.model.APIEntitiesCiResetTokenResult;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunner;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerDetails;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerManager;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerRegistrationDetails;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerWithUuid;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRunnersRequest;
import ai.gebo.gitlab.client.model.PostApiV4UserRunnersRequest;
import ai.gebo.gitlab.client.model.PutApiV4RunnersIdRequest;

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
public class RunnersApi {
    private ApiClient apiClient;

    public RunnersApi() {
        this(new ApiClient());
    }

    public RunnersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Unassign a runner from a project
     * Unassigns a specified project runner from a project. You cannot unassign a runner from the owner project. Use the delete a runner operation instead. If the runner is locked, only administrators can unassign it. You must have the Maintainer or Owner role in the project, or a custom role with the &#x60;admin_runners&#x60; permission. Requires a token with the &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - You cannot unassign a runner from the owner project. Delete the runner instead
     * <p><b>404</b> - Runner not found
     * <p><b>412</b> - Precondition Failed
     * @param id ID or URL-encoded path of the project.
     * @param runnerId ID of the runner.
     * @return APIEntitiesCiRunner
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRunnersRunnerIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRunnersRunnerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'runnerId' is set
        if (runnerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runnerId' when calling deleteApiV4ProjectsIdRunnersRunnerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("runner_id", runnerId);

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

        ParameterizedTypeReference<APIEntitiesCiRunner> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/runners/{runner_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unassign a runner from a project
     * Unassigns a specified project runner from a project. You cannot unassign a runner from the owner project. Use the delete a runner operation instead. If the runner is locked, only administrators can unassign it. You must have the Maintainer or Owner role in the project, or a custom role with the &#x60;admin_runners&#x60; permission. Requires a token with the &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - You cannot unassign a runner from the owner project. Delete the runner instead
     * <p><b>404</b> - Runner not found
     * <p><b>412</b> - Precondition Failed
     * @param id ID or URL-encoded path of the project.
     * @param runnerId ID of the runner.
     * @return APIEntitiesCiRunner
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunner deleteApiV4ProjectsIdRunnersRunnerId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunner> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRunnersRunnerIdRequestCreation(id, runnerId).body(localVarReturnType);
    }

    /**
     * Unassign a runner from a project
     * Unassigns a specified project runner from a project. You cannot unassign a runner from the owner project. Use the delete a runner operation instead. If the runner is locked, only administrators can unassign it. You must have the Maintainer or Owner role in the project, or a custom role with the &#x60;admin_runners&#x60; permission. Requires a token with the &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - You cannot unassign a runner from the owner project. Delete the runner instead
     * <p><b>404</b> - Runner not found
     * <p><b>412</b> - Precondition Failed
     * @param id ID or URL-encoded path of the project.
     * @param runnerId ID of the runner.
     * @return ResponseEntity&lt;APIEntitiesCiRunner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunner> deleteApiV4ProjectsIdRunnersRunnerIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunner> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRunnersRunnerIdRequestCreation(id, runnerId).toEntity(localVarReturnType);
    }

    /**
     * Unassign a runner from a project
     * Unassigns a specified project runner from a project. You cannot unassign a runner from the owner project. Use the delete a runner operation instead. If the runner is locked, only administrators can unassign it. You must have the Maintainer or Owner role in the project, or a custom role with the &#x60;admin_runners&#x60; permission. Requires a token with the &#x60;api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - You cannot unassign a runner from the owner project. Delete the runner instead
     * <p><b>404</b> - Runner not found
     * <p><b>412</b> - Precondition Failed
     * @param id ID or URL-encoded path of the project.
     * @param runnerId ID of the runner.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRunnersRunnerIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRunnersRunnerIdRequestCreation(id, runnerId);
    }

    /**
     * Delete a runner
     * Deletes a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in the project that owns the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Runner associated with more than one project
     * <p><b>404</b> - Runner not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return APIEntitiesCiRunner
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4RunnersIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4RunnersId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiRunner> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a runner
     * Deletes a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in the project that owns the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Runner associated with more than one project
     * <p><b>404</b> - Runner not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return APIEntitiesCiRunner
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunner deleteApiV4RunnersId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunner> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4RunnersIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a runner
     * Deletes a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in the project that owns the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Runner associated with more than one project
     * <p><b>404</b> - Runner not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return ResponseEntity&lt;APIEntitiesCiRunner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunner> deleteApiV4RunnersIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunner> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4RunnersIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a runner
     * Deletes a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in the project that owns the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Runner associated with more than one project
     * <p><b>404</b> - Runner not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4RunnersIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4RunnersIdRequestCreation(id);
    }

    /**
     * List all runners in a group
     * Lists all runners available in a specified group and any ancestor groups, including any allowed instance runners. You must be an administrator or auditor, or have the Security Manager, Maintainer, or Owner role in the group, or a custom role with the &#x60;admin_runners&#x60; permission in the group. Requires a token with the &#x60;api&#x60; or &#x60;read_api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerWithUuid&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdRunnersRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdRunners", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "paused", paused));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "tag_list", tagList));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version_prefix", versionPrefix));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/runners", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all runners in a group
     * Lists all runners available in a specified group and any ancestor groups, including any allowed instance runners. You must be an administrator or auditor, or have the Security Manager, Maintainer, or Owner role in the group, or a custom role with the &#x60;admin_runners&#x60; permission in the group. Requires a token with the &#x60;api&#x60; or &#x60;read_api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerWithUuid&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiRunnerWithUuid> getApiV4GroupsIdRunners(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRunnersRequestCreation(id, type, paused, status, tagList, versionPrefix, page, perPage).body(localVarReturnType);
    }

    /**
     * List all runners in a group
     * Lists all runners available in a specified group and any ancestor groups, including any allowed instance runners. You must be an administrator or auditor, or have the Security Manager, Maintainer, or Owner role in the group, or a custom role with the &#x60;admin_runners&#x60; permission in the group. Requires a token with the &#x60;api&#x60; or &#x60;read_api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiRunnerWithUuid&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiRunnerWithUuid>> getApiV4GroupsIdRunnersWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRunnersRequestCreation(id, type, paused, status, tagList, versionPrefix, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all runners in a group
     * Lists all runners available in a specified group and any ancestor groups, including any allowed instance runners. You must be an administrator or auditor, or have the Security Manager, Maintainer, or Owner role in the group, or a custom role with the &#x60;admin_runners&#x60; permission in the group. Requires a token with the &#x60;api&#x60; or &#x60;read_api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdRunnersWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdRunnersRequestCreation(id, type, paused, status, tagList, versionPrefix, page, perPage);
    }

    /**
     * List all runners for a project
     * Lists all runners available in the project, including from ancestor groups and any allowed instance runners. You must be an administrator of the GitLab instance or have at least the Maintainer or Auditor role for the target project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerWithUuid&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRunnersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRunners", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "paused", paused));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "tag_list", tagList));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version_prefix", versionPrefix));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/runners", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all runners for a project
     * Lists all runners available in the project, including from ancestor groups and any allowed instance runners. You must be an administrator of the GitLab instance or have at least the Maintainer or Auditor role for the target project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerWithUuid&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiRunnerWithUuid> getApiV4ProjectsIdRunners(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRunnersRequestCreation(id, scope, type, paused, status, tagList, versionPrefix, page, perPage).body(localVarReturnType);
    }

    /**
     * List all runners for a project
     * Lists all runners available in the project, including from ancestor groups and any allowed instance runners. You must be an administrator of the GitLab instance or have at least the Maintainer or Auditor role for the target project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiRunnerWithUuid&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiRunnerWithUuid>> getApiV4ProjectsIdRunnersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRunnersRequestCreation(id, scope, type, paused, status, tagList, versionPrefix, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all runners for a project
     * Lists all runners available in the project, including from ancestor groups and any allowed instance runners. You must be an administrator of the GitLab instance or have at least the Maintainer or Auditor role for the target project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRunnersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRunnersRequestCreation(id, scope, type, paused, status, tagList, versionPrefix, page, perPage);
    }

    /**
     * List all available runners
     * Lists all runners available to the user. For group runners, you must have the Owner role in the owner namespace. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. Requires a token with the &#x60;api&#x60; or &#x60;read_api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>401</b> - Unauthorized
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerWithUuid&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnersRequestCreation(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "paused", paused));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "tag_list", tagList));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version_prefix", versionPrefix));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all available runners
     * Lists all runners available to the user. For group runners, you must have the Owner role in the owner namespace. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. Requires a token with the &#x60;api&#x60; or &#x60;read_api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>401</b> - Unauthorized
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerWithUuid&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiRunnerWithUuid> getApiV4Runners(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersRequestCreation(scope, type, paused, status, tagList, versionPrefix, page, perPage).body(localVarReturnType);
    }

    /**
     * List all available runners
     * Lists all runners available to the user. For group runners, you must have the Owner role in the owner namespace. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. Requires a token with the &#x60;api&#x60; or &#x60;read_api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>401</b> - Unauthorized
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiRunnerWithUuid&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiRunnerWithUuid>> getApiV4RunnersWithHttpInfo(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersRequestCreation(scope, type, paused, status, tagList, versionPrefix, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all available runners
     * Lists all runners available to the user. For group runners, you must have the Owner role in the owner namespace. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. Requires a token with the &#x60;api&#x60; or &#x60;read_api&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>401</b> - Unauthorized
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnersWithResponseSpec(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4RunnersRequestCreation(scope, type, paused, status, tagList, versionPrefix, page, perPage);
    }

    /**
     * List all runners
     * Lists all runners in the GitLab instance (project and shared). You must have either administrator access or auditor access.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>401</b> - Unauthorized
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerWithUuid&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnersAllRequestCreation(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "paused", paused));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "tag_list", tagList));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version_prefix", versionPrefix));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/all", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all runners
     * Lists all runners in the GitLab instance (project and shared). You must have either administrator access or auditor access.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>401</b> - Unauthorized
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerWithUuid&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiRunnerWithUuid> getApiV4RunnersAll(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersAllRequestCreation(scope, type, paused, status, tagList, versionPrefix, page, perPage).body(localVarReturnType);
    }

    /**
     * List all runners
     * Lists all runners in the GitLab instance (project and shared). You must have either administrator access or auditor access.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>401</b> - Unauthorized
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiRunnerWithUuid&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiRunnerWithUuid>> getApiV4RunnersAllWithHttpInfo(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerWithUuid>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersAllRequestCreation(scope, type, paused, status, tagList, versionPrefix, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all runners
     * Lists all runners in the GitLab instance (project and shared). You must have either administrator access or auditor access.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Scope contains invalid value
     * <p><b>401</b> - Unauthorized
     * @param scope Scope of runners to return. If omitted, all runners are returned. Deprecated. Use &#x60;type&#x60; or &#x60;status&#x60; instead.
     * @param type Type of runners to return.
     * @param paused If &#x60;true&#x60;, returns only runners that are ignoring new jobs. If &#x60;false&#x60;, returns only runners that are accepting new jobs.
     * @param status Status of runners to return. The &#x60;active&#x60; and &#x60;paused&#x60; values are deprecated. Use the &#x60;paused&#x60; parameter instead. Requesting &#x60;offline&#x60; runners might also return &#x60;stale&#x60; runners, because &#x60;stale&#x60; is included in &#x60;offline&#x60;.
     * @param tagList List of runner tags.
     * @param versionPrefix Version prefix of runners to return.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnersAllWithResponseSpec(@jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Boolean paused, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable List<String> tagList, @jakarta.annotation.Nullable String versionPrefix, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4RunnersAllRequestCreation(scope, type, paused, status, tagList, versionPrefix, page, perPage);
    }

    /**
     * Retrieve details on a runner
     * Retrieves details of a runner. Instance runner details are available to all authenticated users through this endpoint. For groups and projects, you must have the Maintainer or Owner role for the associated project or group. The Security Manager role in the associated project or group, or a custom role with the &#x60;admin_runners&#x60; permission in the owner group or project, also grants access. Requires a token with the &#x60;api&#x60;, &#x60;read_api&#x60;, or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param includeProjects If &#x60;true&#x60;, includes projects in the response. If &#x60;false&#x60;, improves performance for runners with many projects.
     * @return APIEntitiesCiRunnerDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnersIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean includeProjects) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4RunnersId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_projects", includeProjects));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCiRunnerDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve details on a runner
     * Retrieves details of a runner. Instance runner details are available to all authenticated users through this endpoint. For groups and projects, you must have the Maintainer or Owner role for the associated project or group. The Security Manager role in the associated project or group, or a custom role with the &#x60;admin_runners&#x60; permission in the owner group or project, also grants access. Requires a token with the &#x60;api&#x60;, &#x60;read_api&#x60;, or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param includeProjects If &#x60;true&#x60;, includes projects in the response. If &#x60;false&#x60;, improves performance for runners with many projects.
     * @return APIEntitiesCiRunnerDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerDetails getApiV4RunnersId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean includeProjects) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdRequestCreation(id, includeProjects).body(localVarReturnType);
    }

    /**
     * Retrieve details on a runner
     * Retrieves details of a runner. Instance runner details are available to all authenticated users through this endpoint. For groups and projects, you must have the Maintainer or Owner role for the associated project or group. The Security Manager role in the associated project or group, or a custom role with the &#x60;admin_runners&#x60; permission in the owner group or project, also grants access. Requires a token with the &#x60;api&#x60;, &#x60;read_api&#x60;, or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param includeProjects If &#x60;true&#x60;, includes projects in the response. If &#x60;false&#x60;, improves performance for runners with many projects.
     * @return ResponseEntity&lt;APIEntitiesCiRunnerDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerDetails> getApiV4RunnersIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean includeProjects) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdRequestCreation(id, includeProjects).toEntity(localVarReturnType);
    }

    /**
     * Retrieve details on a runner
     * Retrieves details of a runner. Instance runner details are available to all authenticated users through this endpoint. For groups and projects, you must have the Maintainer or Owner role for the associated project or group. The Security Manager role in the associated project or group, or a custom role with the &#x60;admin_runners&#x60; permission in the owner group or project, also grants access. Requires a token with the &#x60;api&#x60;, &#x60;read_api&#x60;, or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param includeProjects If &#x60;true&#x60;, includes projects in the response. If &#x60;false&#x60;, improves performance for runners with many projects.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnersIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean includeProjects) throws RestClientResponseException {
        return getApiV4RunnersIdRequestCreation(id, includeProjects);
    }

    /**
     * List all jobs processed by a runner
     * Lists all jobs that are being processed or were processed by a specified runner. The list of jobs is limited to projects where the user has the Reporter, Developer, Maintainer, or Owner role. When the &#x60;ci_rate_limit_rest_runner_job_list_completed_statuses&#x60; feature flag is enabled, requests filtered by &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60; are rate limited per user (&#x60;runner_job_list_completed_statuses_limit_per_user&#x60; application setting), and statuses a runner&#39;s jobs cannot have, such as &#x60;pending&#x60;, match no jobs. Until the &#x60;ci_enforce_runner_job_list_completed_statuses_limit&#x60; feature flag is enabled, requests over this limit are logged but not blocked. Requests that list only jobs from the user&#39;s projects, which is every user except administrators in Admin Mode and auditors, are rate limited per user (&#x60;runner_job_list_project_filtered_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_job_list_project_filtered&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param systemId System ID associated with the runner manager.
     * @param status Status of the job.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order. Specify &#x60;order_by&#x60; as well, including for &#x60;id&#x60;.
     * @param cursor Cursor for obtaining the next set of records.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJobBasicWithProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnersIdJobsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String systemId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4RunnersIdJobs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "system_id", systemId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cursor", cursor));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiJobBasicWithProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/{id}/jobs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all jobs processed by a runner
     * Lists all jobs that are being processed or were processed by a specified runner. The list of jobs is limited to projects where the user has the Reporter, Developer, Maintainer, or Owner role. When the &#x60;ci_rate_limit_rest_runner_job_list_completed_statuses&#x60; feature flag is enabled, requests filtered by &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60; are rate limited per user (&#x60;runner_job_list_completed_statuses_limit_per_user&#x60; application setting), and statuses a runner&#39;s jobs cannot have, such as &#x60;pending&#x60;, match no jobs. Until the &#x60;ci_enforce_runner_job_list_completed_statuses_limit&#x60; feature flag is enabled, requests over this limit are logged but not blocked. Requests that list only jobs from the user&#39;s projects, which is every user except administrators in Admin Mode and auditors, are rate limited per user (&#x60;runner_job_list_project_filtered_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_job_list_project_filtered&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param systemId System ID associated with the runner manager.
     * @param status Status of the job.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order. Specify &#x60;order_by&#x60; as well, including for &#x60;id&#x60;.
     * @param cursor Cursor for obtaining the next set of records.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJobBasicWithProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiJobBasicWithProject> getApiV4RunnersIdJobs(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String systemId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJobBasicWithProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdJobsRequestCreation(id, systemId, status, orderBy, sort, cursor, page, perPage).body(localVarReturnType);
    }

    /**
     * List all jobs processed by a runner
     * Lists all jobs that are being processed or were processed by a specified runner. The list of jobs is limited to projects where the user has the Reporter, Developer, Maintainer, or Owner role. When the &#x60;ci_rate_limit_rest_runner_job_list_completed_statuses&#x60; feature flag is enabled, requests filtered by &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60; are rate limited per user (&#x60;runner_job_list_completed_statuses_limit_per_user&#x60; application setting), and statuses a runner&#39;s jobs cannot have, such as &#x60;pending&#x60;, match no jobs. Until the &#x60;ci_enforce_runner_job_list_completed_statuses_limit&#x60; feature flag is enabled, requests over this limit are logged but not blocked. Requests that list only jobs from the user&#39;s projects, which is every user except administrators in Admin Mode and auditors, are rate limited per user (&#x60;runner_job_list_project_filtered_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_job_list_project_filtered&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param systemId System ID associated with the runner manager.
     * @param status Status of the job.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order. Specify &#x60;order_by&#x60; as well, including for &#x60;id&#x60;.
     * @param cursor Cursor for obtaining the next set of records.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiJobBasicWithProject&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiJobBasicWithProject>> getApiV4RunnersIdJobsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String systemId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJobBasicWithProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdJobsRequestCreation(id, systemId, status, orderBy, sort, cursor, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all jobs processed by a runner
     * Lists all jobs that are being processed or were processed by a specified runner. The list of jobs is limited to projects where the user has the Reporter, Developer, Maintainer, or Owner role. When the &#x60;ci_rate_limit_rest_runner_job_list_completed_statuses&#x60; feature flag is enabled, requests filtered by &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60; are rate limited per user (&#x60;runner_job_list_completed_statuses_limit_per_user&#x60; application setting), and statuses a runner&#39;s jobs cannot have, such as &#x60;pending&#x60;, match no jobs. Until the &#x60;ci_enforce_runner_job_list_completed_statuses_limit&#x60; feature flag is enabled, requests over this limit are logged but not blocked. Requests that list only jobs from the user&#39;s projects, which is every user except administrators in Admin Mode and auditors, are rate limited per user (&#x60;runner_job_list_project_filtered_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_job_list_project_filtered&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param systemId System ID associated with the runner manager.
     * @param status Status of the job.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order. Specify &#x60;order_by&#x60; as well, including for &#x60;id&#x60;.
     * @param cursor Cursor for obtaining the next set of records.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnersIdJobsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String systemId, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4RunnersIdJobsRequestCreation(id, systemId, status, orderBy, sort, cursor, page, perPage);
    }

    /**
     * List all managers for a runner
     * List all managers for a specified runner. Each request counts the runner&#39;s managers toward a per-user rate limit (&#x60;runner_manager_list_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_manager_list&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return List&lt;APIEntitiesCiRunnerManager&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnersIdManagersRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4RunnersIdManagers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCiRunnerManager>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/{id}/managers", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all managers for a runner
     * List all managers for a specified runner. Each request counts the runner&#39;s managers toward a per-user rate limit (&#x60;runner_manager_list_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_manager_list&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return List&lt;APIEntitiesCiRunnerManager&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiRunnerManager> getApiV4RunnersIdManagers(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerManager>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdManagersRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all managers for a runner
     * List all managers for a specified runner. Each request counts the runner&#39;s managers toward a per-user rate limit (&#x60;runner_manager_list_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_manager_list&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiRunnerManager&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiRunnerManager>> getApiV4RunnersIdManagersWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerManager>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdManagersRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all managers for a runner
     * List all managers for a specified runner. Each request counts the runner&#39;s managers toward a per-user rate limit (&#x60;runner_manager_list_limit_per_user&#x60; application setting). Until the &#x60;ci_rate_limit_rest_runner_manager_list&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too many requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnersIdManagersWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4RunnersIdManagersRequestCreation(id);
    }

    /**
     * Get projects associated with a runner
     * Get a paginated list of all projects associated with the specified runner. Access is restricted based on user permissions.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBasicProjectDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnersIdProjectsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4RunnersIdProjects", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesBasicProjectDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/{id}/projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get projects associated with a runner
     * Get a paginated list of all projects associated with the specified runner. Access is restricted based on user permissions.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBasicProjectDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBasicProjectDetails> getApiV4RunnersIdProjects(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBasicProjectDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdProjectsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * Get projects associated with a runner
     * Get a paginated list of all projects associated with the specified runner. Access is restricted based on user permissions.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBasicProjectDetails&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBasicProjectDetails>> getApiV4RunnersIdProjectsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBasicProjectDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersIdProjectsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Get projects associated with a runner
     * Get a paginated list of all projects associated with the specified runner. Access is restricted based on user permissions.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnersIdProjectsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4RunnersIdProjectsRequestCreation(id, page, perPage);
    }

    /**
     * Reset the runner registration token for a group
     * Resets the runner registration token for a specified group. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdRunnersResetRegistrationTokenRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdRunnersResetRegistrationToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/runners/reset_registration_token", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset the runner registration token for a group
     * Resets the runner registration token for a specified group. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiResetTokenResult postApiV4GroupsIdRunnersResetRegistrationToken(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdRunnersResetRegistrationTokenRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Reset the runner registration token for a group
     * Resets the runner registration token for a specified group. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;APIEntitiesCiResetTokenResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiResetTokenResult> postApiV4GroupsIdRunnersResetRegistrationTokenWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdRunnersResetRegistrationTokenRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Reset the runner registration token for a group
     * Resets the runner registration token for a specified group. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdRunnersResetRegistrationTokenWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4GroupsIdRunnersResetRegistrationTokenRequestCreation(id);
    }

    /**
     * Assign a runner to a project
     * Assigns an available project runner to a project. You must have the Maintainer or Owner role in the target project and in a project assigned to the runner, or a custom role with the &#x60;admin_runners&#x60; permission. Requires a token with the &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Runner is locked
     * <p><b>404</b> - Runner not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRunnersRequest The postApiV4ProjectsIdRunnersRequest parameter
     * @return APIEntitiesCiRunnerWithUuid
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRunnersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRunnersRequest postApiV4ProjectsIdRunnersRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRunners", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRunnersRequest' is set
        if (postApiV4ProjectsIdRunnersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRunnersRequest' when calling postApiV4ProjectsIdRunners", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerWithUuid> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/runners", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRunnersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Assign a runner to a project
     * Assigns an available project runner to a project. You must have the Maintainer or Owner role in the target project and in a project assigned to the runner, or a custom role with the &#x60;admin_runners&#x60; permission. Requires a token with the &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Runner is locked
     * <p><b>404</b> - Runner not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRunnersRequest The postApiV4ProjectsIdRunnersRequest parameter
     * @return APIEntitiesCiRunnerWithUuid
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerWithUuid postApiV4ProjectsIdRunners(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRunnersRequest postApiV4ProjectsIdRunnersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerWithUuid> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRunnersRequestCreation(id, postApiV4ProjectsIdRunnersRequest).body(localVarReturnType);
    }

    /**
     * Assign a runner to a project
     * Assigns an available project runner to a project. You must have the Maintainer or Owner role in the target project and in a project assigned to the runner, or a custom role with the &#x60;admin_runners&#x60; permission. Requires a token with the &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Runner is locked
     * <p><b>404</b> - Runner not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRunnersRequest The postApiV4ProjectsIdRunnersRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiRunnerWithUuid&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerWithUuid> postApiV4ProjectsIdRunnersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRunnersRequest postApiV4ProjectsIdRunnersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerWithUuid> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRunnersRequestCreation(id, postApiV4ProjectsIdRunnersRequest).toEntity(localVarReturnType);
    }

    /**
     * Assign a runner to a project
     * Assigns an available project runner to a project. You must have the Maintainer or Owner role in the target project and in a project assigned to the runner, or a custom role with the &#x60;admin_runners&#x60; permission. Requires a token with the &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Runner is locked
     * <p><b>404</b> - Runner not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRunnersRequest The postApiV4ProjectsIdRunnersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRunnersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRunnersRequest postApiV4ProjectsIdRunnersRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRunnersRequestCreation(id, postApiV4ProjectsIdRunnersRequest);
    }

    /**
     * Reset the runner registration token for a project
     * Resets the runner registration token for a specified project. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRunnersResetRegistrationTokenRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRunnersResetRegistrationToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/runners/reset_registration_token", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset the runner registration token for a project
     * Resets the runner registration token for a specified project. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiResetTokenResult postApiV4ProjectsIdRunnersResetRegistrationToken(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRunnersResetRegistrationTokenRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Reset the runner registration token for a project
     * Resets the runner registration token for a specified project. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesCiResetTokenResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiResetTokenResult> postApiV4ProjectsIdRunnersResetRegistrationTokenWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRunnersResetRegistrationTokenRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Reset the runner registration token for a project
     * Resets the runner registration token for a specified project. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRunnersResetRegistrationTokenWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4ProjectsIdRunnersResetRegistrationTokenRequestCreation(id);
    }

    /**
     * Reset an authentication token for a runner
     * Resets the authentication token for a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnersIdResetAuthenticationTokenRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4RunnersIdResetAuthenticationToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/{id}/reset_authentication_token", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset an authentication token for a runner
     * Resets the authentication token for a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiResetTokenResult postApiV4RunnersIdResetAuthenticationToken(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersIdResetAuthenticationTokenRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Reset an authentication token for a runner
     * Resets the authentication token for a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return ResponseEntity&lt;APIEntitiesCiResetTokenResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiResetTokenResult> postApiV4RunnersIdResetAuthenticationTokenWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersIdResetAuthenticationTokenRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Reset an authentication token for a runner
     * Resets the authentication token for a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnersIdResetAuthenticationTokenWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4RunnersIdResetAuthenticationTokenRequestCreation(id);
    }

    /**
     * Reset the runner registration token for the instance
     * Resets the runner registration token for the GitLab instance. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnersResetRegistrationTokenRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/reset_registration_token", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset the runner registration token for the instance
     * Resets the runner registration token for the GitLab instance. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiResetTokenResult postApiV4RunnersResetRegistrationToken() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersResetRegistrationTokenRequestCreation().body(localVarReturnType);
    }

    /**
     * Reset the runner registration token for the instance
     * Resets the runner registration token for the GitLab instance. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @return ResponseEntity&lt;APIEntitiesCiResetTokenResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiResetTokenResult> postApiV4RunnersResetRegistrationTokenWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersResetRegistrationTokenRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Reset the runner registration token for the instance
     * Resets the runner registration token for the GitLab instance. Registering runners with a registration token is deprecated. Create runners with &#x60;POST /user/runners&#x60; and use their authentication token instead.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnersResetRegistrationTokenWithResponseSpec() throws RestClientResponseException {
        return postApiV4RunnersResetRegistrationTokenRequestCreation();
    }

    /**
     * Create a runner owned by currently authenticated user
     * Creates a runner linked to the current user. The user is listed as the owner for audit purposes, but runner availability is based on &#x60;runner_type&#x60;. The &#x60;token&#x60; value is included in the response, but cannot be retrieved later. For &#x60;instance_type&#x60;, requires an administrator. For &#x60;group_type&#x60;, requires an administrator, the Owner role for the group, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;project_type&#x60;, requires an administrator, at least the Maintainer role for the project, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;group_type&#x60; and &#x60;project_type&#x60;, users who are not administrators can create the runner only when runner registration is allowed for the group or project. Requires a token with the &#x60;create_runner&#x60; or &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * @param postApiV4UserRunnersRequest The postApiV4UserRunnersRequest parameter
     * @return APIEntitiesCiRunnerRegistrationDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserRunnersRequestCreation(@jakarta.annotation.Nonnull PostApiV4UserRunnersRequest postApiV4UserRunnersRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UserRunnersRequest' is set
        if (postApiV4UserRunnersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UserRunnersRequest' when calling postApiV4UserRunners", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/runners", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UserRunnersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a runner owned by currently authenticated user
     * Creates a runner linked to the current user. The user is listed as the owner for audit purposes, but runner availability is based on &#x60;runner_type&#x60;. The &#x60;token&#x60; value is included in the response, but cannot be retrieved later. For &#x60;instance_type&#x60;, requires an administrator. For &#x60;group_type&#x60;, requires an administrator, the Owner role for the group, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;project_type&#x60;, requires an administrator, at least the Maintainer role for the project, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;group_type&#x60; and &#x60;project_type&#x60;, users who are not administrators can create the runner only when runner registration is allowed for the group or project. Requires a token with the &#x60;create_runner&#x60; or &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * @param postApiV4UserRunnersRequest The postApiV4UserRunnersRequest parameter
     * @return APIEntitiesCiRunnerRegistrationDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerRegistrationDetails postApiV4UserRunners(@jakarta.annotation.Nonnull PostApiV4UserRunnersRequest postApiV4UserRunnersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserRunnersRequestCreation(postApiV4UserRunnersRequest).body(localVarReturnType);
    }

    /**
     * Create a runner owned by currently authenticated user
     * Creates a runner linked to the current user. The user is listed as the owner for audit purposes, but runner availability is based on &#x60;runner_type&#x60;. The &#x60;token&#x60; value is included in the response, but cannot be retrieved later. For &#x60;instance_type&#x60;, requires an administrator. For &#x60;group_type&#x60;, requires an administrator, the Owner role for the group, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;project_type&#x60;, requires an administrator, at least the Maintainer role for the project, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;group_type&#x60; and &#x60;project_type&#x60;, users who are not administrators can create the runner only when runner registration is allowed for the group or project. Requires a token with the &#x60;create_runner&#x60; or &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * @param postApiV4UserRunnersRequest The postApiV4UserRunnersRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiRunnerRegistrationDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerRegistrationDetails> postApiV4UserRunnersWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UserRunnersRequest postApiV4UserRunnersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserRunnersRequestCreation(postApiV4UserRunnersRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a runner owned by currently authenticated user
     * Creates a runner linked to the current user. The user is listed as the owner for audit purposes, but runner availability is based on &#x60;runner_type&#x60;. The &#x60;token&#x60; value is included in the response, but cannot be retrieved later. For &#x60;instance_type&#x60;, requires an administrator. For &#x60;group_type&#x60;, requires an administrator, the Owner role for the group, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;project_type&#x60;, requires an administrator, at least the Maintainer role for the project, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;group_type&#x60; and &#x60;project_type&#x60;, users who are not administrators can create the runner only when runner registration is allowed for the group or project. Requires a token with the &#x60;create_runner&#x60; or &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * @param postApiV4UserRunnersRequest The postApiV4UserRunnersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserRunnersWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UserRunnersRequest postApiV4UserRunnersRequest) throws RestClientResponseException {
        return postApiV4UserRunnersRequestCreation(postApiV4UserRunnersRequest);
    }

    /**
     * Update a runner
     * Updates a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * @param id ID of the runner.
     * @param putApiV4RunnersIdRequest The putApiV4RunnersIdRequest parameter
     * @return APIEntitiesCiRunnerDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4RunnersIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4RunnersIdRequest putApiV4RunnersIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4RunnersId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4RunnersIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a runner
     * Updates a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * @param id ID of the runner.
     * @param putApiV4RunnersIdRequest The putApiV4RunnersIdRequest parameter
     * @return APIEntitiesCiRunnerDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerDetails putApiV4RunnersId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4RunnersIdRequest putApiV4RunnersIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4RunnersIdRequestCreation(id, putApiV4RunnersIdRequest).body(localVarReturnType);
    }

    /**
     * Update a runner
     * Updates a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * @param id ID of the runner.
     * @param putApiV4RunnersIdRequest The putApiV4RunnersIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiRunnerDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerDetails> putApiV4RunnersIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4RunnersIdRequest putApiV4RunnersIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4RunnersIdRequestCreation(id, putApiV4RunnersIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a runner
     * Updates a specified runner. For instance runners, you must be an administrator. For group runners, you must have the Owner role in the owner group. For project runners, you must have the Maintainer or Owner role in a project assigned to the runner. A custom role with the &#x60;admin_runners&#x60; permission in the owner group or project also grants access. Requires a token with the &#x60;api&#x60; or &#x60;manage_runner&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - No access granted
     * <p><b>404</b> - Runner not found
     * @param id ID of the runner.
     * @param putApiV4RunnersIdRequest The putApiV4RunnersIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4RunnersIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4RunnersIdRequest putApiV4RunnersIdRequest) throws RestClientResponseException {
        return putApiV4RunnersIdRequestCreation(id, putApiV4RunnersIdRequest);
    }
}
