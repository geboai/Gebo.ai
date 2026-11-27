package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBasicGroupDetails;
import ai.gebo.gitlab.client.model.APIEntitiesBasicProjectDetails;
import ai.gebo.gitlab.client.model.APIEntitiesGroupScopeLink;
import ai.gebo.gitlab.client.model.APIEntitiesProjectJobTokenScope;
import ai.gebo.gitlab.client.model.APIEntitiesProjectScopeLink;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdJobTokenScopeRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdJobTokenScopeAllowlistRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest;

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
public class ProjectsJobTokenScopeApi {
    private ApiClient apiClient;

    public ProjectsJobTokenScopeApi() {
        this(new ApiClient());
    }

    public ProjectsJobTokenScopeApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a project from a CI/CD job token allowlist
     * Deletes a project from the CI/CD job token allowlist of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the project.
     * @param targetProjectId ID of the project to remove from the CI/CD job token inbound allowlist.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdJobTokenScopeAllowlistTargetProjectIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer targetProjectId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdJobTokenScopeAllowlistTargetProjectId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'targetProjectId' is set
        if (targetProjectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'targetProjectId' when calling deleteApiV4ProjectsIdJobTokenScopeAllowlistTargetProjectId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("target_project_id", targetProjectId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/job_token_scope/allowlist/{target_project_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project from a CI/CD job token allowlist
     * Deletes a project from the CI/CD job token allowlist of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the project.
     * @param targetProjectId ID of the project to remove from the CI/CD job token inbound allowlist.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdJobTokenScopeAllowlistTargetProjectId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer targetProjectId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdJobTokenScopeAllowlistTargetProjectIdRequestCreation(id, targetProjectId).body(localVarReturnType);
    }

    /**
     * Delete a project from a CI/CD job token allowlist
     * Deletes a project from the CI/CD job token allowlist of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the project.
     * @param targetProjectId ID of the project to remove from the CI/CD job token inbound allowlist.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdJobTokenScopeAllowlistTargetProjectIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer targetProjectId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdJobTokenScopeAllowlistTargetProjectIdRequestCreation(id, targetProjectId).toEntity(localVarReturnType);
    }

    /**
     * Delete a project from a CI/CD job token allowlist
     * Deletes a project from the CI/CD job token allowlist of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the project.
     * @param targetProjectId ID of the project to remove from the CI/CD job token inbound allowlist.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdJobTokenScopeAllowlistTargetProjectIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer targetProjectId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdJobTokenScopeAllowlistTargetProjectIdRequestCreation(id, targetProjectId);
    }

    /**
     * Delete a group from a CI/CD job token allowlist
     * Deletes a group from the CI/CD job token allowlist of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the project.
     * @param targetGroupId ID of the group to remove from the CI/CD job token groups allowlist.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdJobTokenScopeGroupsAllowlistTargetGroupIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer targetGroupId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdJobTokenScopeGroupsAllowlistTargetGroupId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'targetGroupId' is set
        if (targetGroupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'targetGroupId' when calling deleteApiV4ProjectsIdJobTokenScopeGroupsAllowlistTargetGroupId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("target_group_id", targetGroupId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/job_token_scope/groups_allowlist/{target_group_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group from a CI/CD job token allowlist
     * Deletes a group from the CI/CD job token allowlist of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the project.
     * @param targetGroupId ID of the group to remove from the CI/CD job token groups allowlist.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdJobTokenScopeGroupsAllowlistTargetGroupId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer targetGroupId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdJobTokenScopeGroupsAllowlistTargetGroupIdRequestCreation(id, targetGroupId).body(localVarReturnType);
    }

    /**
     * Delete a group from a CI/CD job token allowlist
     * Deletes a group from the CI/CD job token allowlist of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the project.
     * @param targetGroupId ID of the group to remove from the CI/CD job token groups allowlist.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdJobTokenScopeGroupsAllowlistTargetGroupIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer targetGroupId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdJobTokenScopeGroupsAllowlistTargetGroupIdRequestCreation(id, targetGroupId).toEntity(localVarReturnType);
    }

    /**
     * Delete a group from a CI/CD job token allowlist
     * Deletes a group from the CI/CD job token allowlist of a specified project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID of the project.
     * @param targetGroupId ID of the group to remove from the CI/CD job token groups allowlist.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdJobTokenScopeGroupsAllowlistTargetGroupIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer targetGroupId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdJobTokenScopeGroupsAllowlistTargetGroupIdRequestCreation(id, targetGroupId);
    }

    /**
     * Retrieve the CI/CD job token access settings for a project
     * Retrieves the CI/CD job token access settings (job token scope) of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesProjectJobTokenScope
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdJobTokenScopeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdJobTokenScope", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectJobTokenScope> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/job_token_scope", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the CI/CD job token access settings for a project
     * Retrieves the CI/CD job token access settings (job token scope) of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesProjectJobTokenScope
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectJobTokenScope getApiV4ProjectsIdJobTokenScope(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectJobTokenScope> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobTokenScopeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve the CI/CD job token access settings for a project
     * Retrieves the CI/CD job token access settings (job token scope) of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesProjectJobTokenScope&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectJobTokenScope> getApiV4ProjectsIdJobTokenScopeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectJobTokenScope> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobTokenScopeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the CI/CD job token access settings for a project
     * Retrieves the CI/CD job token access settings (job token scope) of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdJobTokenScopeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdJobTokenScopeRequestCreation(id);
    }

    /**
     * List all projects in a CI/CD job token allowlist
     * Lists all projects in the CI/CD job token allowlist of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBasicProjectDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdJobTokenScopeAllowlistRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdJobTokenScopeAllowlist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/job_token_scope/allowlist", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all projects in a CI/CD job token allowlist
     * Lists all projects in the CI/CD job token allowlist of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBasicProjectDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBasicProjectDetails> getApiV4ProjectsIdJobTokenScopeAllowlist(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBasicProjectDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobTokenScopeAllowlistRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all projects in a CI/CD job token allowlist
     * Lists all projects in the CI/CD job token allowlist of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBasicProjectDetails&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBasicProjectDetails>> getApiV4ProjectsIdJobTokenScopeAllowlistWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBasicProjectDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobTokenScopeAllowlistRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all projects in a CI/CD job token allowlist
     * Lists all projects in the CI/CD job token allowlist of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdJobTokenScopeAllowlistWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdJobTokenScopeAllowlistRequestCreation(id, page, perPage);
    }

    /**
     * List all groups in a CI/CD job token allowlist
     * Lists all groups in the CI/CD job token allowlist of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBasicGroupDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdJobTokenScopeGroupsAllowlist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesBasicGroupDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/job_token_scope/groups_allowlist", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all groups in a CI/CD job token allowlist
     * Lists all groups in the CI/CD job token allowlist of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBasicGroupDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBasicGroupDetails> getApiV4ProjectsIdJobTokenScopeGroupsAllowlist(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBasicGroupDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all groups in a CI/CD job token allowlist
     * Lists all groups in the CI/CD job token allowlist of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBasicGroupDetails&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBasicGroupDetails>> getApiV4ProjectsIdJobTokenScopeGroupsAllowlistWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBasicGroupDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all groups in a CI/CD job token allowlist
     * Lists all groups in the CI/CD job token allowlist of a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdJobTokenScopeGroupsAllowlistWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequestCreation(id, page, perPage);
    }

    /**
     * Update the CI/CD job token access settings for a project
     * Updates the **Authorized groups and projects** setting (job token scope) of a specified project. If the **Enforce job token allowlist** instance setting is enabled, setting &#x60;enabled&#x60; to &#x60;false&#x60; returns &#x60;400 Bad Request&#x60; with an error message.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdJobTokenScopeRequest The patchApiV4ProjectsIdJobTokenScopeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdJobTokenScopeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdJobTokenScopeRequest patchApiV4ProjectsIdJobTokenScopeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdJobTokenScope", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patchApiV4ProjectsIdJobTokenScopeRequest' is set
        if (patchApiV4ProjectsIdJobTokenScopeRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'patchApiV4ProjectsIdJobTokenScopeRequest' when calling patchApiV4ProjectsIdJobTokenScope", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/job_token_scope", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdJobTokenScopeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update the CI/CD job token access settings for a project
     * Updates the **Authorized groups and projects** setting (job token scope) of a specified project. If the **Enforce job token allowlist** instance setting is enabled, setting &#x60;enabled&#x60; to &#x60;false&#x60; returns &#x60;400 Bad Request&#x60; with an error message.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdJobTokenScopeRequest The patchApiV4ProjectsIdJobTokenScopeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void patchApiV4ProjectsIdJobTokenScope(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdJobTokenScopeRequest patchApiV4ProjectsIdJobTokenScopeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        patchApiV4ProjectsIdJobTokenScopeRequestCreation(id, patchApiV4ProjectsIdJobTokenScopeRequest).body(localVarReturnType);
    }

    /**
     * Update the CI/CD job token access settings for a project
     * Updates the **Authorized groups and projects** setting (job token scope) of a specified project. If the **Enforce job token allowlist** instance setting is enabled, setting &#x60;enabled&#x60; to &#x60;false&#x60; returns &#x60;400 Bad Request&#x60; with an error message.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdJobTokenScopeRequest The patchApiV4ProjectsIdJobTokenScopeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> patchApiV4ProjectsIdJobTokenScopeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdJobTokenScopeRequest patchApiV4ProjectsIdJobTokenScopeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdJobTokenScopeRequestCreation(id, patchApiV4ProjectsIdJobTokenScopeRequest).toEntity(localVarReturnType);
    }

    /**
     * Update the CI/CD job token access settings for a project
     * Updates the **Authorized groups and projects** setting (job token scope) of a specified project. If the **Enforce job token allowlist** instance setting is enabled, setting &#x60;enabled&#x60; to &#x60;false&#x60; returns &#x60;400 Bad Request&#x60; with an error message.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdJobTokenScopeRequest The patchApiV4ProjectsIdJobTokenScopeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdJobTokenScopeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdJobTokenScopeRequest patchApiV4ProjectsIdJobTokenScopeRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdJobTokenScopeRequestCreation(id, patchApiV4ProjectsIdJobTokenScopeRequest);
    }

    /**
     * Add a project to a CI/CD job token allowlist
     * Adds a project to the CI/CD job token allowlist of a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the project.
     * @param postApiV4ProjectsIdJobTokenScopeAllowlistRequest The postApiV4ProjectsIdJobTokenScopeAllowlistRequest parameter
     * @return APIEntitiesProjectScopeLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdJobTokenScopeAllowlistRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdJobTokenScopeAllowlistRequest postApiV4ProjectsIdJobTokenScopeAllowlistRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdJobTokenScopeAllowlist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdJobTokenScopeAllowlistRequest' is set
        if (postApiV4ProjectsIdJobTokenScopeAllowlistRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdJobTokenScopeAllowlistRequest' when calling postApiV4ProjectsIdJobTokenScopeAllowlist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectScopeLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/job_token_scope/allowlist", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdJobTokenScopeAllowlistRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a project to a CI/CD job token allowlist
     * Adds a project to the CI/CD job token allowlist of a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the project.
     * @param postApiV4ProjectsIdJobTokenScopeAllowlistRequest The postApiV4ProjectsIdJobTokenScopeAllowlistRequest parameter
     * @return APIEntitiesProjectScopeLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectScopeLink postApiV4ProjectsIdJobTokenScopeAllowlist(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdJobTokenScopeAllowlistRequest postApiV4ProjectsIdJobTokenScopeAllowlistRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectScopeLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdJobTokenScopeAllowlistRequestCreation(id, postApiV4ProjectsIdJobTokenScopeAllowlistRequest).body(localVarReturnType);
    }

    /**
     * Add a project to a CI/CD job token allowlist
     * Adds a project to the CI/CD job token allowlist of a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the project.
     * @param postApiV4ProjectsIdJobTokenScopeAllowlistRequest The postApiV4ProjectsIdJobTokenScopeAllowlistRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectScopeLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectScopeLink> postApiV4ProjectsIdJobTokenScopeAllowlistWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdJobTokenScopeAllowlistRequest postApiV4ProjectsIdJobTokenScopeAllowlistRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectScopeLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdJobTokenScopeAllowlistRequestCreation(id, postApiV4ProjectsIdJobTokenScopeAllowlistRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a project to a CI/CD job token allowlist
     * Adds a project to the CI/CD job token allowlist of a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the project.
     * @param postApiV4ProjectsIdJobTokenScopeAllowlistRequest The postApiV4ProjectsIdJobTokenScopeAllowlistRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdJobTokenScopeAllowlistWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdJobTokenScopeAllowlistRequest postApiV4ProjectsIdJobTokenScopeAllowlistRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdJobTokenScopeAllowlistRequestCreation(id, postApiV4ProjectsIdJobTokenScopeAllowlistRequest);
    }

    /**
     * Add a group to a CI/CD job token allowlist
     * Adds a group to the CI/CD job token allowlist of a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the project.
     * @param postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest The postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest parameter
     * @return APIEntitiesGroupScopeLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdJobTokenScopeGroupsAllowlist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest' is set
        if (postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest' when calling postApiV4ProjectsIdJobTokenScopeGroupsAllowlist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupScopeLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/job_token_scope/groups_allowlist", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a group to a CI/CD job token allowlist
     * Adds a group to the CI/CD job token allowlist of a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the project.
     * @param postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest The postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest parameter
     * @return APIEntitiesGroupScopeLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupScopeLink postApiV4ProjectsIdJobTokenScopeGroupsAllowlist(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupScopeLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequestCreation(id, postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest).body(localVarReturnType);
    }

    /**
     * Add a group to a CI/CD job token allowlist
     * Adds a group to the CI/CD job token allowlist of a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the project.
     * @param postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest The postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupScopeLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupScopeLink> postApiV4ProjectsIdJobTokenScopeGroupsAllowlistWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupScopeLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequestCreation(id, postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a group to a CI/CD job token allowlist
     * Adds a group to the CI/CD job token allowlist of a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the project.
     * @param postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest The postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdJobTokenScopeGroupsAllowlistWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequestCreation(id, postApiV4ProjectsIdJobTokenScopeGroupsAllowlistRequest);
    }
}
