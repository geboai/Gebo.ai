package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAuditEvent;
import ai.gebo.gitlab.client.model.APIEntitiesCiResetTokenResult;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerWithUuid;
import ai.gebo.gitlab.client.model.APIEntitiesGroup;
import ai.gebo.gitlab.client.model.APIEntitiesGroupDetail;
import ai.gebo.gitlab.client.model.APIEntitiesGroupUpload;
import ai.gebo.gitlab.client.model.APIEntitiesIssue;
import ai.gebo.gitlab.client.model.APIEntitiesMarkdownUploadAdmin;
import ai.gebo.gitlab.client.model.APIEntitiesMember;
import ai.gebo.gitlab.client.model.APIEntitiesProject;
import ai.gebo.gitlab.client.model.APIEntitiesPublicGroupDetails;
import ai.gebo.gitlab.client.model.APIEntitiesUserPublic;
import java.io.File;
import ai.gebo.gitlab.client.model.GetApiV4MergeRequestsAssigneeIdParameter;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdShareRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdTransferRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdTransferToOrganizationRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsRequestAiSettingsAttributes;
import ai.gebo.gitlab.client.model.PostApiV4GroupsRequestDefaultBranchProtectionDefaults;
import ai.gebo.gitlab.client.model.PostApiV4GroupsRequestFoundationalAgentsStatusesInner;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdRequestDuoNamespaceAccessRulesInner;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdSecuritySettingsRequest;

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
public class GroupsApi {
    private ApiClient apiClient;

    public GroupsApi() {
        this(new ApiClient());
    }

    public GroupsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Schedule a group for deletion
     * Schedules a group for deletion. Groups are deleted at the end of the retention period (30 days by default). Use the &#x60;permanently_remove&#x60; param to override the retention period. On GitLab.com, groups are retained for 30 days. On GitLab Self-Managed, the retention period is set in the instance settings. A group that is linked to a subscription cannot be deleted. You must be an administrator or have the Owner role for the group.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Schedule a group for deletion
     * Schedules a group for deletion. Groups are deleted at the end of the retention period (30 days by default). Use the &#x60;permanently_remove&#x60; param to override the retention period. On GitLab.com, groups are retained for 30 days. On GitLab Self-Managed, the retention period is set in the instance settings. A group that is linked to a subscription cannot be deleted. You must be an administrator or have the Owner role for the group.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsId(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Schedule a group for deletion
     * Schedules a group for deletion. Groups are deleted at the end of the retention period (30 days by default). Use the &#x60;permanently_remove&#x60; param to override the retention period. On GitLab.com, groups are retained for 30 days. On GitLab Self-Managed, the retention period is set in the instance settings. A group that is linked to a subscription cannot be deleted. You must be an administrator or have the Owner role for the group.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Schedule a group for deletion
     * Schedules a group for deletion. Groups are deleted at the end of the retention period (30 days by default). Use the &#x60;permanently_remove&#x60; param to override the retention period. On GitLab.com, groups are retained for 30 days. On GitLab Self-Managed, the retention period is set in the instance settings. A group that is linked to a subscription cannot be deleted. You must be an administrator or have the Owner role for the group.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return deleteApiV4GroupsIdRequestCreation(id);
    }

    /**
     * Remove a group from a group
     * Removes a group from a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param groupId ID of the shared group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdShareGroupIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer groupId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdShareGroupId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupId' when calling deleteApiV4GroupsIdShareGroupId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("group_id", groupId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/share/{group_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove a group from a group
     * Removes a group from a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param groupId ID of the shared group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdShareGroupId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer groupId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdShareGroupIdRequestCreation(id, groupId).body(localVarReturnType);
    }

    /**
     * Remove a group from a group
     * Removes a group from a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param groupId ID of the shared group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdShareGroupIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer groupId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdShareGroupIdRequestCreation(id, groupId).toEntity(localVarReturnType);
    }

    /**
     * Remove a group from a group
     * Removes a group from a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param groupId ID of the shared group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdShareGroupIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer groupId) throws RestClientResponseException {
        return deleteApiV4GroupsIdShareGroupIdRequestCreation(id, groupId);
    }

    /**
     * Remove a shared project from a group
     * Removes a shared project targeting this group. The group Owner can remove a shared project without access to the source project. You must be an administrator or have the Owner role for the group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID of the shared project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdSharedProjectsProjectIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer projectId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdSharedProjectsProjectId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling deleteApiV4GroupsIdSharedProjectsProjectId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("project_id", projectId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/shared_projects/{project_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove a shared project from a group
     * Removes a shared project targeting this group. The group Owner can remove a shared project without access to the source project. You must be an administrator or have the Owner role for the group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID of the shared project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdSharedProjectsProjectId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer projectId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdSharedProjectsProjectIdRequestCreation(id, projectId).body(localVarReturnType);
    }

    /**
     * Remove a shared project from a group
     * Removes a shared project targeting this group. The group Owner can remove a shared project without access to the source project. You must be an administrator or have the Owner role for the group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID of the shared project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdSharedProjectsProjectIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer projectId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdSharedProjectsProjectIdRequestCreation(id, projectId).toEntity(localVarReturnType);
    }

    /**
     * Remove a shared project from a group
     * Removes a shared project targeting this group. The group Owner can remove a shared project without access to the source project. You must be an administrator or have the Owner role for the group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID of the shared project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdSharedProjectsProjectIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer projectId) throws RestClientResponseException {
        return deleteApiV4GroupsIdSharedProjectsProjectIdRequestCreation(id, projectId);
    }

    /**
     * Delete an uploaded file by secret and filename
     * Deletes an uploaded file with a specified secret and filename. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.4.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param secret 32-character secret of the upload.
     * @param filename Filename of the upload.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdUploadsSecretFilenameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String secret, @jakarta.annotation.Nonnull String filename) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdUploadsSecretFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'secret' is set
        if (secret == null) {
            throw new RestClientResponseException("Missing the required parameter 'secret' when calling deleteApiV4GroupsIdUploadsSecretFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filename' is set
        if (filename == null) {
            throw new RestClientResponseException("Missing the required parameter 'filename' when calling deleteApiV4GroupsIdUploadsSecretFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("secret", secret);
        pathParams.put("filename", filename);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/uploads/{secret}/{filename}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an uploaded file by secret and filename
     * Deletes an uploaded file with a specified secret and filename. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.4.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param secret 32-character secret of the upload.
     * @param filename Filename of the upload.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdUploadsSecretFilename(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String secret, @jakarta.annotation.Nonnull String filename) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdUploadsSecretFilenameRequestCreation(id, secret, filename).body(localVarReturnType);
    }

    /**
     * Delete an uploaded file by secret and filename
     * Deletes an uploaded file with a specified secret and filename. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.4.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param secret 32-character secret of the upload.
     * @param filename Filename of the upload.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdUploadsSecretFilenameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String secret, @jakarta.annotation.Nonnull String filename) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdUploadsSecretFilenameRequestCreation(id, secret, filename).toEntity(localVarReturnType);
    }

    /**
     * Delete an uploaded file by secret and filename
     * Deletes an uploaded file with a specified secret and filename. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.4.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param secret 32-character secret of the upload.
     * @param filename Filename of the upload.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdUploadsSecretFilenameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String secret, @jakarta.annotation.Nonnull String filename) throws RestClientResponseException {
        return deleteApiV4GroupsIdUploadsSecretFilenameRequestCreation(id, secret, filename);
    }

    /**
     * Delete an uploaded file by ID
     * Deletes an uploaded file with a specified ID. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param uploadId ID of the upload.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdUploadsUploadIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer uploadId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdUploadsUploadId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'uploadId' is set
        if (uploadId == null) {
            throw new RestClientResponseException("Missing the required parameter 'uploadId' when calling deleteApiV4GroupsIdUploadsUploadId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("upload_id", uploadId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/uploads/{upload_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an uploaded file by ID
     * Deletes an uploaded file with a specified ID. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param uploadId ID of the upload.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdUploadsUploadId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer uploadId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdUploadsUploadIdRequestCreation(id, uploadId).body(localVarReturnType);
    }

    /**
     * Delete an uploaded file by ID
     * Deletes an uploaded file with a specified ID. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param uploadId ID of the upload.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdUploadsUploadIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer uploadId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdUploadsUploadIdRequestCreation(id, uploadId).toEntity(localVarReturnType);
    }

    /**
     * Delete an uploaded file by ID
     * Deletes an uploaded file with a specified ID. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param uploadId ID of the upload.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdUploadsUploadIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer uploadId) throws RestClientResponseException {
        return deleteApiV4GroupsIdUploadsUploadIdRequestCreation(id, uploadId);
    }

    /**
     * List all groups
     * Lists all visible groups for the authenticated user. Unauthenticated requests return only public groups. Unauthenticated requests also support keyset pagination. For unauthenticated requests, offset pagination is unavailable beyond the maximum allowed offset. Use keyset pagination to request consecutive pages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsRequestCreation(@jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "statistics", statistics));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "archived", archived));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "skip_groups", skipGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "all_available", allAvailable));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "visibility", visibility));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "owned", owned));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_access_level", minAccessLevel));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "top_level_only", topLevelOnly));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "marked_for_deletion_on", markedForDeletionOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "repository_storage", repositoryStorage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all groups
     * Lists all visible groups for the authenticated user. Unauthenticated requests return only public groups. Unauthenticated requests also support keyset pagination. For unauthenticated requests, offset pagination is unavailable beyond the maximum allowed offset. Use keyset pagination to request consecutive pages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroup> getApiV4Groups(@jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsRequestCreation(statistics, archived, skipGroups, allAvailable, visibility, search, owned, orderBy, sort, minAccessLevel, topLevelOnly, markedForDeletionOn, active, repositoryStorage, page, perPage, withCustomAttributes, customAttributes).body(localVarReturnType);
    }

    /**
     * List all groups
     * Lists all visible groups for the authenticated user. Unauthenticated requests return only public groups. Unauthenticated requests also support keyset pagination. For unauthenticated requests, offset pagination is unavailable beyond the maximum allowed offset. Use keyset pagination to request consecutive pages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroup&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroup>> getApiV4GroupsWithHttpInfo(@jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsRequestCreation(statistics, archived, skipGroups, allAvailable, visibility, search, owned, orderBy, sort, minAccessLevel, topLevelOnly, markedForDeletionOn, active, repositoryStorage, page, perPage, withCustomAttributes, customAttributes).toEntity(localVarReturnType);
    }

    /**
     * List all groups
     * Lists all visible groups for the authenticated user. Unauthenticated requests return only public groups. Unauthenticated requests also support keyset pagination. For unauthenticated requests, offset pagination is unavailable beyond the maximum allowed offset. Use keyset pagination to request consecutive pages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsWithResponseSpec(@jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        return getApiV4GroupsRequestCreation(statistics, archived, skipGroups, allAvailable, visibility, search, owned, orderBy, sort, minAccessLevel, topLevelOnly, markedForDeletionOn, active, repositoryStorage, page, perPage, withCustomAttributes, customAttributes);
    }

    /**
     * Retrieve a group
     * Retrieves a specified group by ID or path. Public groups can be retrieved without authentication. The &#x60;runners_token&#x60; attribute is returned only to administrators and users with the Owner role for the group. The &#x60;enabled_git_access_protocol&#x60; attribute is returned only to those users, and only for top-level groups. The &#x60;projects&#x60; and &#x60;shared_projects&#x60; attributes are deprecated and scheduled for removal in API v5. Each returns at most 100 projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param withProjects If &#x60;true&#x60;, includes details of the projects that belong to the group. Deprecated and [scheduled for removal in API v5](https://gitlab.com/gitlab-org/gitlab/-/issues/213797). To list the projects in a group, use the List all projects in a group operation instead.
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean withProjects) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_projects", withProjects));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group
     * Retrieves a specified group by ID or path. Public groups can be retrieved without authentication. The &#x60;runners_token&#x60; attribute is returned only to administrators and users with the Owner role for the group. The &#x60;enabled_git_access_protocol&#x60; attribute is returned only to those users, and only for top-level groups. The &#x60;projects&#x60; and &#x60;shared_projects&#x60; attributes are deprecated and scheduled for removal in API v5. Each returns at most 100 projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param withProjects If &#x60;true&#x60;, includes details of the projects that belong to the group. Deprecated and [scheduled for removal in API v5](https://gitlab.com/gitlab-org/gitlab/-/issues/213797). To list the projects in a group, use the List all projects in a group operation instead.
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupDetail getApiV4GroupsId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean withProjects) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRequestCreation(id, withCustomAttributes, customAttributes, withProjects).body(localVarReturnType);
    }

    /**
     * Retrieve a group
     * Retrieves a specified group by ID or path. Public groups can be retrieved without authentication. The &#x60;runners_token&#x60; attribute is returned only to administrators and users with the Owner role for the group. The &#x60;enabled_git_access_protocol&#x60; attribute is returned only to those users, and only for top-level groups. The &#x60;projects&#x60; and &#x60;shared_projects&#x60; attributes are deprecated and scheduled for removal in API v5. Each returns at most 100 projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param withProjects If &#x60;true&#x60;, includes details of the projects that belong to the group. Deprecated and [scheduled for removal in API v5](https://gitlab.com/gitlab-org/gitlab/-/issues/213797). To list the projects in a group, use the List all projects in a group operation instead.
     * @return ResponseEntity&lt;APIEntitiesGroupDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupDetail> getApiV4GroupsIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean withProjects) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRequestCreation(id, withCustomAttributes, customAttributes, withProjects).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group
     * Retrieves a specified group by ID or path. Public groups can be retrieved without authentication. The &#x60;runners_token&#x60; attribute is returned only to administrators and users with the Owner role for the group. The &#x60;enabled_git_access_protocol&#x60; attribute is returned only to those users, and only for top-level groups. The &#x60;projects&#x60; and &#x60;shared_projects&#x60; attributes are deprecated and scheduled for removal in API v5. Each returns at most 100 projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param withProjects If &#x60;true&#x60;, includes details of the projects that belong to the group. Deprecated and [scheduled for removal in API v5](https://gitlab.com/gitlab-org/gitlab/-/issues/213797). To list the projects in a group, use the List all projects in a group operation instead.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean withProjects) throws RestClientResponseException {
        return getApiV4GroupsIdRequestCreation(id, withCustomAttributes, customAttributes, withProjects);
    }

    /**
     * Retrieve a group audit event
     * Retrieves an audit event for a specified group. Only available to group Owners and administrators.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param auditEventId ID of the audit event.
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesAuditEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdAuditEventsAuditEventIdRequestCreation(@jakarta.annotation.Nonnull Integer auditEventId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'auditEventId' is set
        if (auditEventId == null) {
            throw new RestClientResponseException("Missing the required parameter 'auditEventId' when calling getApiV4GroupsIdAuditEventsAuditEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdAuditEventsAuditEventId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("audit_event_id", auditEventId);
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

        ParameterizedTypeReference<APIEntitiesAuditEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/audit_events/{audit_event_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group audit event
     * Retrieves an audit event for a specified group. Only available to group Owners and administrators.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param auditEventId ID of the audit event.
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesAuditEvent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAuditEvent getApiV4GroupsIdAuditEventsAuditEventId(@jakarta.annotation.Nonnull Integer auditEventId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAuditEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAuditEventsAuditEventIdRequestCreation(auditEventId, id).body(localVarReturnType);
    }

    /**
     * Retrieve a group audit event
     * Retrieves an audit event for a specified group. Only available to group Owners and administrators.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param auditEventId ID of the audit event.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;APIEntitiesAuditEvent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAuditEvent> getApiV4GroupsIdAuditEventsAuditEventIdWithHttpInfo(@jakarta.annotation.Nonnull Integer auditEventId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAuditEvent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAuditEventsAuditEventIdRequestCreation(auditEventId, id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group audit event
     * Retrieves an audit event for a specified group. Only available to group Owners and administrators.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param auditEventId ID of the audit event.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdAuditEventsAuditEventIdWithResponseSpec(@jakarta.annotation.Nonnull Integer auditEventId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdAuditEventsAuditEventIdRequestCreation(auditEventId, id);
    }

    /**
     * List all billable group members
     * Lists all billable members of a specified top-level group. Includes members in any subgroups or projects. You must have the Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Query string to search for group members by name, username, or public email.
     * @param sort Sort results by the specified field and direction.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBillableMembersRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBillableMembers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/billable_members", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all billable group members
     * Lists all billable members of a specified top-level group. Includes members in any subgroups or projects. You must have the Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Query string to search for group members by name, username, or public email.
     * @param sort Sort results by the specified field and direction.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMember> getApiV4GroupsIdBillableMembers(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBillableMembersRequestCreation(id, page, perPage, search, sort).body(localVarReturnType);
    }

    /**
     * List all billable group members
     * Lists all billable members of a specified top-level group. Includes members in any subgroups or projects. You must have the Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Query string to search for group members by name, username, or public email.
     * @param sort Sort results by the specified field and direction.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMember&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMember>> getApiV4GroupsIdBillableMembersWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBillableMembersRequestCreation(id, page, perPage, search, sort).toEntity(localVarReturnType);
    }

    /**
     * List all billable group members
     * Lists all billable members of a specified top-level group. Includes members in any subgroups or projects. You must have the Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Query string to search for group members by name, username, or public email.
     * @param sort Sort results by the specified field and direction.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBillableMembersWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4GroupsIdBillableMembersRequestCreation(id, page, perPage, search, sort);
    }

    /**
     * List all descendant groups
     * Lists all descendant groups for a specified group. Unauthenticated requests return only public groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDescendantGroupsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDescendantGroups", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "statistics", statistics));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "archived", archived));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "skip_groups", skipGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "all_available", allAvailable));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "visibility", visibility));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "owned", owned));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_access_level", minAccessLevel));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "top_level_only", topLevelOnly));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "marked_for_deletion_on", markedForDeletionOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "repository_storage", repositoryStorage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/descendant_groups", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all descendant groups
     * Lists all descendant groups for a specified group. Unauthenticated requests return only public groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroup> getApiV4GroupsIdDescendantGroups(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDescendantGroupsRequestCreation(id, statistics, archived, skipGroups, allAvailable, visibility, search, owned, orderBy, sort, minAccessLevel, topLevelOnly, markedForDeletionOn, active, repositoryStorage, page, perPage, withCustomAttributes, customAttributes).body(localVarReturnType);
    }

    /**
     * List all descendant groups
     * Lists all descendant groups for a specified group. Unauthenticated requests return only public groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroup&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroup>> getApiV4GroupsIdDescendantGroupsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDescendantGroupsRequestCreation(id, statistics, archived, skipGroups, allAvailable, visibility, search, owned, orderBy, sort, minAccessLevel, topLevelOnly, markedForDeletionOn, active, repositoryStorage, page, perPage, withCustomAttributes, customAttributes).toEntity(localVarReturnType);
    }

    /**
     * List all descendant groups
     * Lists all descendant groups for a specified group. Unauthenticated requests return only public groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDescendantGroupsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        return getApiV4GroupsIdDescendantGroupsRequestCreation(id, statistics, archived, skipGroups, allAvailable, visibility, search, owned, orderBy, sort, minAccessLevel, topLevelOnly, markedForDeletionOn, active, repositoryStorage, page, perPage, withCustomAttributes, customAttributes);
    }

    /**
     * List all shared groups
     * Lists all groups shared with a specified group. Unauthenticated requests return only public shared groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param skipGroups Skip the specified group IDs.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdGroupsSharedRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdGroupsShared", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "skip_groups", skipGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "visibility", visibility));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_access_level", minAccessLevel));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/groups/shared", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all shared groups
     * Lists all groups shared with a specified group. Unauthenticated requests return only public shared groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param skipGroups Skip the specified group IDs.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroup> getApiV4GroupsIdGroupsShared(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdGroupsSharedRequestCreation(id, skipGroups, visibility, search, minAccessLevel, orderBy, sort, page, perPage, withCustomAttributes, customAttributes).body(localVarReturnType);
    }

    /**
     * List all shared groups
     * Lists all groups shared with a specified group. Unauthenticated requests return only public shared groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param skipGroups Skip the specified group IDs.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroup&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroup>> getApiV4GroupsIdGroupsSharedWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdGroupsSharedRequestCreation(id, skipGroups, visibility, search, minAccessLevel, orderBy, sort, page, perPage, withCustomAttributes, customAttributes).toEntity(localVarReturnType);
    }

    /**
     * List all shared groups
     * Lists all groups shared with a specified group. Unauthenticated requests return only public shared groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param skipGroups Skip the specified group IDs.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdGroupsSharedWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        return getApiV4GroupsIdGroupsSharedRequestCreation(id, skipGroups, visibility, search, minAccessLevel, orderBy, sort, page, perPage, withCustomAttributes, customAttributes);
    }

    /**
     * List all invited groups
     * Lists all groups invited to a specified group. Unauthenticated requests return only public invited groups. By default, this endpoint is rate-limited to 60 requests per minute for each user, or for each IP address for unauthenticated requests.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Filter groups by relation.
     * @param search Return the list of authorized groups matching the search criteria.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdInvitedGroupsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<String> relation, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdInvitedGroups", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "relation", relation));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_access_level", minAccessLevel));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/invited_groups", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all invited groups
     * Lists all groups invited to a specified group. Unauthenticated requests return only public invited groups. By default, this endpoint is rate-limited to 60 requests per minute for each user, or for each IP address for unauthenticated requests.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Filter groups by relation.
     * @param search Return the list of authorized groups matching the search criteria.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroup> getApiV4GroupsIdInvitedGroups(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<String> relation, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdInvitedGroupsRequestCreation(id, relation, search, minAccessLevel, page, perPage, withCustomAttributes, customAttributes).body(localVarReturnType);
    }

    /**
     * List all invited groups
     * Lists all groups invited to a specified group. Unauthenticated requests return only public invited groups. By default, this endpoint is rate-limited to 60 requests per minute for each user, or for each IP address for unauthenticated requests.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Filter groups by relation.
     * @param search Return the list of authorized groups matching the search criteria.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroup&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroup>> getApiV4GroupsIdInvitedGroupsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<String> relation, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdInvitedGroupsRequestCreation(id, relation, search, minAccessLevel, page, perPage, withCustomAttributes, customAttributes).toEntity(localVarReturnType);
    }

    /**
     * List all invited groups
     * Lists all groups invited to a specified group. Unauthenticated requests return only public invited groups. By default, this endpoint is rate-limited to 60 requests per minute for each user, or for each IP address for unauthenticated requests.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relation Filter groups by relation.
     * @param search Return the list of authorized groups matching the search criteria.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdInvitedGroupsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<String> relation, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        return getApiV4GroupsIdInvitedGroupsRequestCreation(id, relation, search, minAccessLevel, page, perPage, withCustomAttributes, customAttributes);
    }

    /**
     * List all issues for a group
     * Lists all issues for a specified group. If the group is private, you must provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param state Filter issues by state.
     * @param closedById Return issues closed by the user with the given ID.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param dueDate Return issues that have no due date, are overdue, or whose due date is this week, this month, or between two weeks ago and next month.
     * @param issueType The type of the issue. Accepts: issue, incident, test_case, requirement, task, ticket
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns only issues from non-archived projects. If &#x60;false&#x60;, returns issues from both archived and non-archived projects.
     * @return List&lt;APIEntitiesIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdIssuesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer closedById, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String issueType, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_labels_details", withLabelsDetails));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "closed_by_id", closedById));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "due_date", dueDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue_type", issueType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone", milestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone_id", milestoneId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "iids", iids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assignee_id", assigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "assignee_username", assigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone]", notMilestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone_id]", notMilestoneId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[iids]", notIids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[assignee_id]", notAssigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[assignee_username]", notAssigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[weight]", notWeight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[iteration_id]", notIterationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[iteration_title]", notIterationTitle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "confidential", confidential));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "weight", weight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "epic_id", epicId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "health_status", healthStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "iteration_id", iterationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "iteration_title", iterationTitle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "non_archived", nonArchived));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issues for a group
     * Lists all issues for a specified group. If the group is private, you must provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param state Filter issues by state.
     * @param closedById Return issues closed by the user with the given ID.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param dueDate Return issues that have no due date, are overdue, or whose due date is this week, this month, or between two weeks ago and next month.
     * @param issueType The type of the issue. Accepts: issue, incident, test_case, requirement, task, ticket
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns only issues from non-archived projects. If &#x60;false&#x60;, returns issues from both archived and non-archived projects.
     * @return List&lt;APIEntitiesIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesIssue> getApiV4GroupsIdIssues(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer closedById, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String issueType, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdIssuesRequestCreation(id, withLabelsDetails, state, closedById, orderBy, sort, dueDate, issueType, labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle, page, perPage, nonArchived).body(localVarReturnType);
    }

    /**
     * List all issues for a group
     * Lists all issues for a specified group. If the group is private, you must provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param state Filter issues by state.
     * @param closedById Return issues closed by the user with the given ID.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param dueDate Return issues that have no due date, are overdue, or whose due date is this week, this month, or between two weeks ago and next month.
     * @param issueType The type of the issue. Accepts: issue, incident, test_case, requirement, task, ticket
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns only issues from non-archived projects. If &#x60;false&#x60;, returns issues from both archived and non-archived projects.
     * @return ResponseEntity&lt;List&lt;APIEntitiesIssue&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesIssue>> getApiV4GroupsIdIssuesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer closedById, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String issueType, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdIssuesRequestCreation(id, withLabelsDetails, state, closedById, orderBy, sort, dueDate, issueType, labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle, page, perPage, nonArchived).toEntity(localVarReturnType);
    }

    /**
     * List all issues for a group
     * Lists all issues for a specified group. If the group is private, you must provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param state Filter issues by state.
     * @param closedById Return issues closed by the user with the given ID.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param dueDate Return issues that have no due date, are overdue, or whose due date is this week, this month, or between two weeks ago and next month.
     * @param issueType The type of the issue. Accepts: issue, incident, test_case, requirement, task, ticket
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns only issues from non-archived projects. If &#x60;false&#x60;, returns issues from both archived and non-archived projects.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdIssuesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer closedById, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String issueType, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        return getApiV4GroupsIdIssuesRequestCreation(id, withLabelsDetails, state, closedById, orderBy, sort, dueDate, issueType, labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle, page, perPage, nonArchived);
    }

    /**
     * Retrieve issues statistics for a group
     * Retrieves statistics for issues in a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdIssuesStatisticsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdIssuesStatistics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone", milestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone_id", milestoneId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "iids", iids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assignee_id", assigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "assignee_username", assigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone]", notMilestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone_id]", notMilestoneId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[iids]", notIids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[assignee_id]", notAssigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[assignee_username]", notAssigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[weight]", notWeight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[iteration_id]", notIterationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[iteration_title]", notIterationTitle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "confidential", confidential));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "weight", weight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "epic_id", epicId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "health_status", healthStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "iteration_id", iterationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "iteration_title", iterationTitle));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/issues_statistics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve issues statistics for a group
     * Retrieves statistics for issues in a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdIssuesStatistics(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdIssuesStatisticsRequestCreation(id, labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle).body(localVarReturnType);
    }

    /**
     * Retrieve issues statistics for a group
     * Retrieves statistics for issues in a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdIssuesStatisticsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdIssuesStatisticsRequestCreation(id, labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle).toEntity(localVarReturnType);
    }

    /**
     * Retrieve issues statistics for a group
     * Retrieves statistics for issues in a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdIssuesStatisticsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle) throws RestClientResponseException {
        return getApiV4GroupsIdIssuesStatisticsRequestCreation(id, labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle);
    }

    /**
     * Retrieve pending reassignments
     * Retrieves a CSV file with a list of pending reassignments. The CSV file has the columns &#x60;Source host&#x60;, &#x60;Import type&#x60;, &#x60;Source user identifier&#x60;, &#x60;Source user name&#x60;, &#x60;Source username&#x60;, &#x60;GitLab username&#x60;, and &#x60;GitLab public email&#x60;. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdPlaceholderReassignmentsRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdPlaceholderReassignments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "text/csv"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/placeholder_reassignments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve pending reassignments
     * Retrieves a CSV file with a list of pending reassignments. The CSV file has the columns &#x60;Source host&#x60;, &#x60;Import type&#x60;, &#x60;Source user identifier&#x60;, &#x60;Source user name&#x60;, &#x60;Source username&#x60;, &#x60;GitLab username&#x60;, and &#x60;GitLab public email&#x60;. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public String getApiV4GroupsIdPlaceholderReassignments(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdPlaceholderReassignmentsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve pending reassignments
     * Retrieves a CSV file with a list of pending reassignments. The CSV file has the columns &#x60;Source host&#x60;, &#x60;Import type&#x60;, &#x60;Source user identifier&#x60;, &#x60;Source user name&#x60;, &#x60;Source username&#x60;, &#x60;GitLab username&#x60;, and &#x60;GitLab public email&#x60;. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> getApiV4GroupsIdPlaceholderReassignmentsWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdPlaceholderReassignmentsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve pending reassignments
     * Retrieves a CSV file with a list of pending reassignments. The CSV file has the columns &#x60;Source host&#x60;, &#x60;Import type&#x60;, &#x60;Source user identifier&#x60;, &#x60;Source user name&#x60;, &#x60;Source username&#x60;, &#x60;GitLab username&#x60;, and &#x60;GitLab public email&#x60;. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdPlaceholderReassignmentsWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4GroupsIdPlaceholderReassignmentsRequestCreation(id);
    }

    /**
     * List all projects in a group
     * Lists all projects in a specified group accessible to the authenticated user. Unauthenticated requests return only public projects with a limited subset of attributes.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param active If &#x60;true&#x60;, returns active projects. If &#x60;false&#x60;, returns projects that are archived or marked for deletion. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/218053) in GitLab 18.8.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects.
     * @param visibility Return projects with the specified visibility.
     * @param search Return the list of authorized projects matching the search criteria.
     * @param orderBy Sort results by the specified field. With &#x60;similarity&#x60;, results are ordered by similarity to &#x60;search&#x60; and &#x60;sort&#x60; is ignored. Without &#x60;search&#x60;, &#x60;similarity&#x60; orders results by &#x60;name&#x60;.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, returns only limited fields for each project. Without authentication, only limited fields are returned regardless of this setting.
     * @param owned If &#x60;true&#x60;, returns only projects owned by the current user.
     * @param starred If &#x60;true&#x60;, returns only projects starred by the current user.
     * @param withIssuesEnabled If &#x60;true&#x60;, returns only projects that have the issues feature enabled.
     * @param withMergeRequestsEnabled If &#x60;true&#x60;, returns only projects that have the merge requests feature enabled.
     * @param withShared If &#x60;true&#x60;, includes projects shared with the group.
     * @param includeSubgroups If &#x60;true&#x60;, includes projects in subgroups of this group.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes projects in the ancestors of this group.
     * @param minAccessLevel Return projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param withSecurityReports If &#x60;true&#x60;, returns only projects that have security report artifacts present in any of their builds. Ultimate only.
     * @return List&lt;APIEntitiesProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdProjectsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable Boolean starred, @jakarta.annotation.Nullable Boolean withIssuesEnabled, @jakarta.annotation.Nullable Boolean withMergeRequestsEnabled, @jakarta.annotation.Nullable Boolean withShared, @jakarta.annotation.Nullable Boolean includeSubgroups, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean withSecurityReports) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdProjects", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "archived", archived));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "visibility", visibility));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "simple", simple));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "owned", owned));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "starred", starred));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_issues_enabled", withIssuesEnabled));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_merge_requests_enabled", withMergeRequestsEnabled));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_shared", withShared));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_subgroups", includeSubgroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestor_groups", includeAncestorGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_access_level", minAccessLevel));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_security_reports", withSecurityReports));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all projects in a group
     * Lists all projects in a specified group accessible to the authenticated user. Unauthenticated requests return only public projects with a limited subset of attributes.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param active If &#x60;true&#x60;, returns active projects. If &#x60;false&#x60;, returns projects that are archived or marked for deletion. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/218053) in GitLab 18.8.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects.
     * @param visibility Return projects with the specified visibility.
     * @param search Return the list of authorized projects matching the search criteria.
     * @param orderBy Sort results by the specified field. With &#x60;similarity&#x60;, results are ordered by similarity to &#x60;search&#x60; and &#x60;sort&#x60; is ignored. Without &#x60;search&#x60;, &#x60;similarity&#x60; orders results by &#x60;name&#x60;.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, returns only limited fields for each project. Without authentication, only limited fields are returned regardless of this setting.
     * @param owned If &#x60;true&#x60;, returns only projects owned by the current user.
     * @param starred If &#x60;true&#x60;, returns only projects starred by the current user.
     * @param withIssuesEnabled If &#x60;true&#x60;, returns only projects that have the issues feature enabled.
     * @param withMergeRequestsEnabled If &#x60;true&#x60;, returns only projects that have the merge requests feature enabled.
     * @param withShared If &#x60;true&#x60;, includes projects shared with the group.
     * @param includeSubgroups If &#x60;true&#x60;, includes projects in subgroups of this group.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes projects in the ancestors of this group.
     * @param minAccessLevel Return projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param withSecurityReports If &#x60;true&#x60;, returns only projects that have security report artifacts present in any of their builds. Ultimate only.
     * @return List&lt;APIEntitiesProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProject> getApiV4GroupsIdProjects(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable Boolean starred, @jakarta.annotation.Nullable Boolean withIssuesEnabled, @jakarta.annotation.Nullable Boolean withMergeRequestsEnabled, @jakarta.annotation.Nullable Boolean withShared, @jakarta.annotation.Nullable Boolean includeSubgroups, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean withSecurityReports) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProjectsRequestCreation(id, active, archived, visibility, search, orderBy, sort, simple, owned, starred, withIssuesEnabled, withMergeRequestsEnabled, withShared, includeSubgroups, includeAncestorGroups, minAccessLevel, page, perPage, withCustomAttributes, customAttributes, withSecurityReports).body(localVarReturnType);
    }

    /**
     * List all projects in a group
     * Lists all projects in a specified group accessible to the authenticated user. Unauthenticated requests return only public projects with a limited subset of attributes.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param active If &#x60;true&#x60;, returns active projects. If &#x60;false&#x60;, returns projects that are archived or marked for deletion. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/218053) in GitLab 18.8.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects.
     * @param visibility Return projects with the specified visibility.
     * @param search Return the list of authorized projects matching the search criteria.
     * @param orderBy Sort results by the specified field. With &#x60;similarity&#x60;, results are ordered by similarity to &#x60;search&#x60; and &#x60;sort&#x60; is ignored. Without &#x60;search&#x60;, &#x60;similarity&#x60; orders results by &#x60;name&#x60;.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, returns only limited fields for each project. Without authentication, only limited fields are returned regardless of this setting.
     * @param owned If &#x60;true&#x60;, returns only projects owned by the current user.
     * @param starred If &#x60;true&#x60;, returns only projects starred by the current user.
     * @param withIssuesEnabled If &#x60;true&#x60;, returns only projects that have the issues feature enabled.
     * @param withMergeRequestsEnabled If &#x60;true&#x60;, returns only projects that have the merge requests feature enabled.
     * @param withShared If &#x60;true&#x60;, includes projects shared with the group.
     * @param includeSubgroups If &#x60;true&#x60;, includes projects in subgroups of this group.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes projects in the ancestors of this group.
     * @param minAccessLevel Return projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param withSecurityReports If &#x60;true&#x60;, returns only projects that have security report artifacts present in any of their builds. Ultimate only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProject&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProject>> getApiV4GroupsIdProjectsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable Boolean starred, @jakarta.annotation.Nullable Boolean withIssuesEnabled, @jakarta.annotation.Nullable Boolean withMergeRequestsEnabled, @jakarta.annotation.Nullable Boolean withShared, @jakarta.annotation.Nullable Boolean includeSubgroups, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean withSecurityReports) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProjectsRequestCreation(id, active, archived, visibility, search, orderBy, sort, simple, owned, starred, withIssuesEnabled, withMergeRequestsEnabled, withShared, includeSubgroups, includeAncestorGroups, minAccessLevel, page, perPage, withCustomAttributes, customAttributes, withSecurityReports).toEntity(localVarReturnType);
    }

    /**
     * List all projects in a group
     * Lists all projects in a specified group accessible to the authenticated user. Unauthenticated requests return only public projects with a limited subset of attributes.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param active If &#x60;true&#x60;, returns active projects. If &#x60;false&#x60;, returns projects that are archived or marked for deletion. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/218053) in GitLab 18.8.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects.
     * @param visibility Return projects with the specified visibility.
     * @param search Return the list of authorized projects matching the search criteria.
     * @param orderBy Sort results by the specified field. With &#x60;similarity&#x60;, results are ordered by similarity to &#x60;search&#x60; and &#x60;sort&#x60; is ignored. Without &#x60;search&#x60;, &#x60;similarity&#x60; orders results by &#x60;name&#x60;.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, returns only limited fields for each project. Without authentication, only limited fields are returned regardless of this setting.
     * @param owned If &#x60;true&#x60;, returns only projects owned by the current user.
     * @param starred If &#x60;true&#x60;, returns only projects starred by the current user.
     * @param withIssuesEnabled If &#x60;true&#x60;, returns only projects that have the issues feature enabled.
     * @param withMergeRequestsEnabled If &#x60;true&#x60;, returns only projects that have the merge requests feature enabled.
     * @param withShared If &#x60;true&#x60;, includes projects shared with the group.
     * @param includeSubgroups If &#x60;true&#x60;, includes projects in subgroups of this group.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes projects in the ancestors of this group.
     * @param minAccessLevel Return projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param withSecurityReports If &#x60;true&#x60;, returns only projects that have security report artifacts present in any of their builds. Ultimate only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdProjectsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable Boolean starred, @jakarta.annotation.Nullable Boolean withIssuesEnabled, @jakarta.annotation.Nullable Boolean withMergeRequestsEnabled, @jakarta.annotation.Nullable Boolean withShared, @jakarta.annotation.Nullable Boolean includeSubgroups, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean withSecurityReports) throws RestClientResponseException {
        return getApiV4GroupsIdProjectsRequestCreation(id, active, archived, visibility, search, orderBy, sort, simple, owned, starred, withIssuesEnabled, withMergeRequestsEnabled, withShared, includeSubgroups, includeAncestorGroups, minAccessLevel, page, perPage, withCustomAttributes, customAttributes, withSecurityReports);
    }

    /**
     * List all shared projects
     * Lists all projects shared with a specified group. Unauthenticated requests return only public shared projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects.
     * @param visibility Return projects with the specified visibility.
     * @param search Return the list of authorized projects matching the search criteria.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, returns only limited fields for each project. Without authentication, only limited fields are returned regardless of this setting.
     * @param starred If &#x60;true&#x60;, returns only projects starred by the current user.
     * @param withIssuesEnabled If &#x60;true&#x60;, returns only projects that have the issues feature enabled.
     * @param withMergeRequestsEnabled If &#x60;true&#x60;, returns only projects that have the merge requests feature enabled.
     * @param minAccessLevel Return projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdProjectsSharedRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Boolean starred, @jakarta.annotation.Nullable Boolean withIssuesEnabled, @jakarta.annotation.Nullable Boolean withMergeRequestsEnabled, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdProjectsShared", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "archived", archived));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "visibility", visibility));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "simple", simple));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "starred", starred));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_issues_enabled", withIssuesEnabled));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_merge_requests_enabled", withMergeRequestsEnabled));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_access_level", minAccessLevel));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/projects/shared", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all shared projects
     * Lists all projects shared with a specified group. Unauthenticated requests return only public shared projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects.
     * @param visibility Return projects with the specified visibility.
     * @param search Return the list of authorized projects matching the search criteria.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, returns only limited fields for each project. Without authentication, only limited fields are returned regardless of this setting.
     * @param starred If &#x60;true&#x60;, returns only projects starred by the current user.
     * @param withIssuesEnabled If &#x60;true&#x60;, returns only projects that have the issues feature enabled.
     * @param withMergeRequestsEnabled If &#x60;true&#x60;, returns only projects that have the merge requests feature enabled.
     * @param minAccessLevel Return projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProject> getApiV4GroupsIdProjectsShared(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Boolean starred, @jakarta.annotation.Nullable Boolean withIssuesEnabled, @jakarta.annotation.Nullable Boolean withMergeRequestsEnabled, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProjectsSharedRequestCreation(id, archived, visibility, search, orderBy, sort, simple, starred, withIssuesEnabled, withMergeRequestsEnabled, minAccessLevel, page, perPage, withCustomAttributes, customAttributes).body(localVarReturnType);
    }

    /**
     * List all shared projects
     * Lists all projects shared with a specified group. Unauthenticated requests return only public shared projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects.
     * @param visibility Return projects with the specified visibility.
     * @param search Return the list of authorized projects matching the search criteria.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, returns only limited fields for each project. Without authentication, only limited fields are returned regardless of this setting.
     * @param starred If &#x60;true&#x60;, returns only projects starred by the current user.
     * @param withIssuesEnabled If &#x60;true&#x60;, returns only projects that have the issues feature enabled.
     * @param withMergeRequestsEnabled If &#x60;true&#x60;, returns only projects that have the merge requests feature enabled.
     * @param minAccessLevel Return projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProject&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProject>> getApiV4GroupsIdProjectsSharedWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Boolean starred, @jakarta.annotation.Nullable Boolean withIssuesEnabled, @jakarta.annotation.Nullable Boolean withMergeRequestsEnabled, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProjectsSharedRequestCreation(id, archived, visibility, search, orderBy, sort, simple, starred, withIssuesEnabled, withMergeRequestsEnabled, minAccessLevel, page, perPage, withCustomAttributes, customAttributes).toEntity(localVarReturnType);
    }

    /**
     * List all shared projects
     * Lists all projects shared with a specified group. Unauthenticated requests return only public shared projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects.
     * @param visibility Return projects with the specified visibility.
     * @param search Return the list of authorized projects matching the search criteria.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, returns only limited fields for each project. Without authentication, only limited fields are returned regardless of this setting.
     * @param starred If &#x60;true&#x60;, returns only projects starred by the current user.
     * @param withIssuesEnabled If &#x60;true&#x60;, returns only projects that have the issues feature enabled.
     * @param withMergeRequestsEnabled If &#x60;true&#x60;, returns only projects that have the merge requests feature enabled.
     * @param minAccessLevel Return projects where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdProjectsSharedWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Boolean starred, @jakarta.annotation.Nullable Boolean withIssuesEnabled, @jakarta.annotation.Nullable Boolean withMergeRequestsEnabled, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        return getApiV4GroupsIdProjectsSharedRequestCreation(id, archived, visibility, search, orderBy, sort, simple, starred, withIssuesEnabled, withMergeRequestsEnabled, minAccessLevel, page, perPage, withCustomAttributes, customAttributes);
    }

    /**
     * List all provisioned users
     * Lists all provisioned users for a specified group. Subgroups are not included. Requires the Maintainer or Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Search users by name, email, or username.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserPublic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdProvisionedUsersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdProvisionedUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "username", username));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "blocked", blocked));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesUserPublic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/provisioned_users", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all provisioned users
     * Lists all provisioned users for a specified group. Subgroups are not included. Requires the Maintainer or Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Search users by name, email, or username.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserPublic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesUserPublic> getApiV4GroupsIdProvisionedUsers(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserPublic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProvisionedUsersRequestCreation(id, username, search, active, blocked, createdAfter, createdBefore, page, perPage).body(localVarReturnType);
    }

    /**
     * List all provisioned users
     * Lists all provisioned users for a specified group. Subgroups are not included. Requires the Maintainer or Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Search users by name, email, or username.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesUserPublic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesUserPublic>> getApiV4GroupsIdProvisionedUsersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserPublic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProvisionedUsersRequestCreation(id, username, search, active, blocked, createdAfter, createdBefore, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all provisioned users
     * Lists all provisioned users for a specified group. Subgroups are not included. Requires the Maintainer or Owner role for the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Search users by name, email, or username.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdProvisionedUsersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdProvisionedUsersRequestCreation(id, username, search, active, blocked, createdAfter, createdBefore, page, perPage);
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
     * List all SAML users
     * Lists all SAML users for a specified top-level group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Return users with a matching name, email, or username. Use partial values to increase results.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserPublic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdSamlUsersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdSamlUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "username", username));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "blocked", blocked));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesUserPublic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/saml_users", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all SAML users
     * Lists all SAML users for a specified top-level group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Return users with a matching name, email, or username. Use partial values to increase results.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserPublic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesUserPublic> getApiV4GroupsIdSamlUsers(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserPublic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlUsersRequestCreation(id, username, search, active, blocked, createdAfter, createdBefore, page, perPage).body(localVarReturnType);
    }

    /**
     * List all SAML users
     * Lists all SAML users for a specified top-level group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Return users with a matching name, email, or username. Use partial values to increase results.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesUserPublic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesUserPublic>> getApiV4GroupsIdSamlUsersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserPublic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlUsersRequestCreation(id, username, search, active, blocked, createdAfter, createdBefore, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all SAML users
     * Lists all SAML users for a specified top-level group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to filter the results. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param username Return a user with the given username.
     * @param search Return users with a matching name, email, or username. Use partial values to increase results.
     * @param active If &#x60;true&#x60;, returns only active users.
     * @param blocked If &#x60;true&#x60;, returns only blocked users.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdSamlUsersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdSamlUsersRequestCreation(id, username, search, active, blocked, createdAfter, createdBefore, page, perPage);
    }

    /**
     * List all subgroups
     * Lists the visible direct subgroups of a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdSubgroupsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdSubgroups", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "statistics", statistics));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "archived", archived));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "skip_groups", skipGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "all_available", allAvailable));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "visibility", visibility));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "owned", owned));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_access_level", minAccessLevel));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "top_level_only", topLevelOnly));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "marked_for_deletion_on", markedForDeletionOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "repository_storage", repositoryStorage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/subgroups", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all subgroups
     * Lists the visible direct subgroups of a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroup> getApiV4GroupsIdSubgroups(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSubgroupsRequestCreation(id, statistics, archived, skipGroups, allAvailable, visibility, search, owned, orderBy, sort, minAccessLevel, topLevelOnly, markedForDeletionOn, active, repositoryStorage, page, perPage, withCustomAttributes, customAttributes).body(localVarReturnType);
    }

    /**
     * List all subgroups
     * Lists the visible direct subgroups of a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroup&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroup>> getApiV4GroupsIdSubgroupsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSubgroupsRequestCreation(id, statistics, archived, skipGroups, allAvailable, visibility, search, owned, orderBy, sort, minAccessLevel, topLevelOnly, markedForDeletionOn, active, repositoryStorage, page, perPage, withCustomAttributes, customAttributes).toEntity(localVarReturnType);
    }

    /**
     * List all subgroups
     * Lists the visible direct subgroups of a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param statistics If &#x60;true&#x60;, includes group statistics. Administrators only. For top-level groups, the response also includes &#x60;root_storage_statistics&#x60;. &#x60;root_storage_statistics&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/469254) in GitLab 17.4.
     * @param archived If &#x60;true&#x60;, returns only archived groups or projects. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/519587) in GitLab 18.2.
     * @param skipGroups Skip the specified group IDs.
     * @param allAvailable If &#x60;true&#x60;, returns all accessible groups. If &#x60;false&#x60;, returns only groups where the user is a member. When omitted, administrators receive all groups and other authenticated users receive only their own. Unauthenticated requests always return all public groups. The &#x60;owned&#x60; and &#x60;min_access_level&#x60; attributes take precedence.
     * @param visibility Return groups with the specified visibility.
     * @param search Return the list of authorized groups matching the search criteria. Matches group names and paths. When listing subgroups or descendant groups, only short paths are searched, not full paths.
     * @param owned If &#x60;true&#x60;, returns only groups explicitly owned by the current user.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param minAccessLevel Return groups where the current user has at least the specified access level. For a list of values, see [roles](https://docs.gitlab.com/user/permissions/#default-roles).
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level groups, excluding subgroups.
     * @param markedForDeletionOn Return groups that are marked for deletion on this date. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/429315) in GitLab 17.1.
     * @param active If &#x60;true&#x60;, returns only groups that are not archived and not marked for deletion.
     * @param repositoryStorage Filter by repository storage used by the group. Administrators only. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdSubgroupsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Boolean statistics, @jakarta.annotation.Nullable Boolean archived, @jakarta.annotation.Nullable List<Integer> skipGroups, @jakarta.annotation.Nullable Boolean allAvailable, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean owned, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer minAccessLevel, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable LocalDate markedForDeletionOn, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable String repositoryStorage, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        return getApiV4GroupsIdSubgroupsRequestCreation(id, statistics, archived, skipGroups, allAvailable, visibility, search, owned, orderBy, sort, minAccessLevel, topLevelOnly, markedForDeletionOn, active, repositoryStorage, page, perPage, withCustomAttributes, customAttributes);
    }

    /**
     * List all transfer locations for a group
     * Lists all groups that a specified source group can be transferred to.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param search Group name to search for.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdTransferLocationsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdTransferLocations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/transfer_locations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all transfer locations for a group
     * Lists all groups that a specified source group can be transferred to.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param search Group name to search for.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroup> getApiV4GroupsIdTransferLocations(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdTransferLocationsRequestCreation(id, search, page, perPage).body(localVarReturnType);
    }

    /**
     * List all transfer locations for a group
     * Lists all groups that a specified source group can be transferred to.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param search Group name to search for.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroup&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroup>> getApiV4GroupsIdTransferLocationsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdTransferLocationsRequestCreation(id, search, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all transfer locations for a group
     * Lists all groups that a specified source group can be transferred to.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param search Group name to search for.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdTransferLocationsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdTransferLocationsRequestCreation(id, search, page, perPage);
    }

    /**
     * List all uploads for a group
     * Lists all uploads for a specified group sorted by &#x60;created_at&#x60; in descending order. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMarkdownUploadAdmin&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdUploadsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdUploads", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesMarkdownUploadAdmin>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/uploads", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all uploads for a group
     * Lists all uploads for a specified group sorted by &#x60;created_at&#x60; in descending order. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMarkdownUploadAdmin&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMarkdownUploadAdmin> getApiV4GroupsIdUploads(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMarkdownUploadAdmin>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdUploadsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all uploads for a group
     * Lists all uploads for a specified group sorted by &#x60;created_at&#x60; in descending order. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMarkdownUploadAdmin&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMarkdownUploadAdmin>> getApiV4GroupsIdUploadsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMarkdownUploadAdmin>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdUploadsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all uploads for a group
     * Lists all uploads for a specified group sorted by &#x60;created_at&#x60; in descending order. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdUploadsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdUploadsRequestCreation(id, page, perPage);
    }

    /**
     * Download an uploaded file by secret and filename
     * Downloads an uploaded file with a specified secret and filename. You must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for the group. For a file linked from a description or comment, take the secret and filename from the link. On a rendered page, the link has the format &#x60;https://gitlab.example.com/-/group/&lt;group_id&gt;/uploads/&lt;secret&gt;/&lt;filename&gt;&#x60;. To get the API URL, replace &#x60;/-/group/&#x60; with &#x60;/api/v4/groups/&#x60;. In raw Markdown, the link is &#x60;/uploads/&lt;secret&gt;/&lt;filename&gt;&#x60; and does not include the project or group. Files that belong to a project, for example in an issue or merge request, use the project uploads endpoint. Use the filename from the link, not from the original file. GitLab can change the filename on upload, for example by replacing spaces with underscores. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param secret 32-character secret of the upload.
     * @param filename Filename of the upload.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdUploadsSecretFilenameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String secret, @jakarta.annotation.Nonnull String filename) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdUploadsSecretFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'secret' is set
        if (secret == null) {
            throw new RestClientResponseException("Missing the required parameter 'secret' when calling getApiV4GroupsIdUploadsSecretFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filename' is set
        if (filename == null) {
            throw new RestClientResponseException("Missing the required parameter 'filename' when calling getApiV4GroupsIdUploadsSecretFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("secret", secret);
        pathParams.put("filename", filename);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/uploads/{secret}/{filename}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download an uploaded file by secret and filename
     * Downloads an uploaded file with a specified secret and filename. You must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for the group. For a file linked from a description or comment, take the secret and filename from the link. On a rendered page, the link has the format &#x60;https://gitlab.example.com/-/group/&lt;group_id&gt;/uploads/&lt;secret&gt;/&lt;filename&gt;&#x60;. To get the API URL, replace &#x60;/-/group/&#x60; with &#x60;/api/v4/groups/&#x60;. In raw Markdown, the link is &#x60;/uploads/&lt;secret&gt;/&lt;filename&gt;&#x60; and does not include the project or group. Files that belong to a project, for example in an issue or merge request, use the project uploads endpoint. Use the filename from the link, not from the original file. GitLab can change the filename on upload, for example by replacing spaces with underscores. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param secret 32-character secret of the upload.
     * @param filename Filename of the upload.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4GroupsIdUploadsSecretFilename(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String secret, @jakarta.annotation.Nonnull String filename) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdUploadsSecretFilenameRequestCreation(id, secret, filename).body(localVarReturnType);
    }

    /**
     * Download an uploaded file by secret and filename
     * Downloads an uploaded file with a specified secret and filename. You must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for the group. For a file linked from a description or comment, take the secret and filename from the link. On a rendered page, the link has the format &#x60;https://gitlab.example.com/-/group/&lt;group_id&gt;/uploads/&lt;secret&gt;/&lt;filename&gt;&#x60;. To get the API URL, replace &#x60;/-/group/&#x60; with &#x60;/api/v4/groups/&#x60;. In raw Markdown, the link is &#x60;/uploads/&lt;secret&gt;/&lt;filename&gt;&#x60; and does not include the project or group. Files that belong to a project, for example in an issue or merge request, use the project uploads endpoint. Use the filename from the link, not from the original file. GitLab can change the filename on upload, for example by replacing spaces with underscores. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param secret 32-character secret of the upload.
     * @param filename Filename of the upload.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4GroupsIdUploadsSecretFilenameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String secret, @jakarta.annotation.Nonnull String filename) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdUploadsSecretFilenameRequestCreation(id, secret, filename).toEntity(localVarReturnType);
    }

    /**
     * Download an uploaded file by secret and filename
     * Downloads an uploaded file with a specified secret and filename. You must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for the group. For a file linked from a description or comment, take the secret and filename from the link. On a rendered page, the link has the format &#x60;https://gitlab.example.com/-/group/&lt;group_id&gt;/uploads/&lt;secret&gt;/&lt;filename&gt;&#x60;. To get the API URL, replace &#x60;/-/group/&#x60; with &#x60;/api/v4/groups/&#x60;. In raw Markdown, the link is &#x60;/uploads/&lt;secret&gt;/&lt;filename&gt;&#x60; and does not include the project or group. Files that belong to a project, for example in an issue or merge request, use the project uploads endpoint. Use the filename from the link, not from the original file. GitLab can change the filename on upload, for example by replacing spaces with underscores. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param secret 32-character secret of the upload.
     * @param filename Filename of the upload.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdUploadsSecretFilenameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String secret, @jakarta.annotation.Nonnull String filename) throws RestClientResponseException {
        return getApiV4GroupsIdUploadsSecretFilenameRequestCreation(id, secret, filename);
    }

    /**
     * Download an uploaded file by ID
     * Downloads an uploaded file with a specified ID. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uploadId ID of the upload.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdUploadsUploadIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer uploadId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdUploadsUploadId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'uploadId' is set
        if (uploadId == null) {
            throw new RestClientResponseException("Missing the required parameter 'uploadId' when calling getApiV4GroupsIdUploadsUploadId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("upload_id", uploadId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/uploads/{upload_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download an uploaded file by ID
     * Downloads an uploaded file with a specified ID. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uploadId ID of the upload.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4GroupsIdUploadsUploadId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer uploadId) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdUploadsUploadIdRequestCreation(id, uploadId).body(localVarReturnType);
    }

    /**
     * Download an uploaded file by ID
     * Downloads an uploaded file with a specified ID. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uploadId ID of the upload.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4GroupsIdUploadsUploadIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer uploadId) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdUploadsUploadIdRequestCreation(id, uploadId).toEntity(localVarReturnType);
    }

    /**
     * Download an uploaded file by ID
     * Downloads an uploaded file with a specified ID. You must have the Maintainer or Owner role for the group. This feature was introduced in GitLab 17.2.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uploadId ID of the upload.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdUploadsUploadIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer uploadId) throws RestClientResponseException {
        return getApiV4GroupsIdUploadsUploadIdRequestCreation(id, uploadId);
    }

    /**
     * List all groups available to invite to a project
     * Lists all groups that can be invited to a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param search Return groups matching the search criteria.
     * @return List&lt;APIEntitiesPublicGroupDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdShareLocationsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdShareLocations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPublicGroupDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/share_locations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all groups available to invite to a project
     * Lists all groups that can be invited to a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param search Return groups matching the search criteria.
     * @return List&lt;APIEntitiesPublicGroupDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPublicGroupDetails> getApiV4ProjectsIdShareLocations(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPublicGroupDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdShareLocationsRequestCreation(id, search).body(localVarReturnType);
    }

    /**
     * List all groups available to invite to a project
     * Lists all groups that can be invited to a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param search Return groups matching the search criteria.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPublicGroupDetails&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPublicGroupDetails>> getApiV4ProjectsIdShareLocationsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPublicGroupDetails>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdShareLocationsRequestCreation(id, search).toEntity(localVarReturnType);
    }

    /**
     * List all groups available to invite to a project
     * Lists all groups that can be invited to a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param search Return groups matching the search criteria.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdShareLocationsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        return getApiV4ProjectsIdShareLocationsRequestCreation(id, search);
    }

    /**
     * Create a group
     * Creates a project group. Available only for users who can create groups. On GitLab.com, top-level groups cannot be created with the API.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param name The name of the group
     * @param path The path of the group
     * @param parentId The parent group id for creating nested group
     * @param organizationId The organization id for the group. Defaults to the organization of the parent group if &#x60;parent_id&#x60; is provided, otherwise the current organization.
     * @param description The description of the group
     * @param visibility The visibility of the group
     * @param avatar Avatar image for the group. On update, a blank value removes the avatar.
     * @param shareWithGroupLock Prevent sharing a project with another group within this group
     * @param requireTwoFactorAuthentication Require all users in this group to setup Two-factor authentication
     * @param twoFactorGracePeriod Time before Two-factor authentication is enforced, in hours.
     * @param projectCreationLevel Determine if developers can create projects in the group
     * @param autoDevopsEnabled Default to Auto DevOps pipeline for all projects within this group
     * @param subgroupCreationLevel Allowed to create subgroups
     * @param emailsDisabled Disable email notifications. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/127899) in GitLab 16.5. Use &#x60;emails_enabled&#x60; instead.
     * @param emailsEnabled Enable email notifications
     * @param showDiffPreviewInEmail Include the code diff preview in merge request notification emails
     * @param mentionsDisabled Disable a group from getting mentioned
     * @param lfsEnabled Enable/disable LFS for the projects in this group
     * @param requestAccessEnabled Allow users to request member access
     * @param defaultBranch The default branch of group&#39;s projects
     * @param defaultBranchProtection Determine if developers can push to default branch. &#x60;0&#x60;: no protection. Users with the Developer or Maintainer role can push new commits, force push, and delete the branch. &#x60;1&#x60;: partial protection. Users with the Developer or Maintainer role can push new commits. &#x60;2&#x60;: full protection. Only users with the Maintainer role can push new commits. &#x60;3&#x60;: protected against pushes. Users with the Maintainer role can push new commits, force push, and accept merge requests. Users with the Developer role can accept merge requests. &#x60;4&#x60;: full protection after initial push. Users with the Developer role can push commits to an empty repository. Users with the Maintainer role can push new commits and accept merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/408314) in GitLab 17.0. Use &#x60;default_branch_protection_defaults&#x60; instead.
     * @param defaultBranchProtectionDefaults The defaultBranchProtectionDefaults parameter
     * @param enabledGitAccessProtocol Allow only the selected protocols to be used for Git access.
     * @param crmEnabled Enable Customer Relations Management for this group
     * @param resourceAccessTokenNotifyInherited Send access token expiry notifications to all inherited members of the group
     * @param lockResourceAccessTokenNotifyInherited Prevent subgroups from overriding the access token expiry notification setting
     * @param membershipLock Prevent adding new members to projects within this group. Premium and Ultimate only.
     * @param ldapCn LDAP Common Name. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param ldapAccess A valid access level. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param sharedRunnersMinutesLimit (admin-only) Maximum number of monthly compute minutes for this group. &#x60;nil&#x60; (default) inherits the instance default, and &#x60;0&#x60; means unlimited. GitLab Self-Managed, Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this group. GitLab Self-Managed, Premium and Ultimate only.
     * @param wikiAccessLevel Wiki access level. One of &#x60;disabled&#x60;, &#x60;private&#x60; or &#x60;enabled&#x60;. Premium and Ultimate only.
     * @param duoAvailability Duo availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60;, &#x60;never_on&#x60; or &#x60;always_on&#x60;. In the UI, &#x60;never_on&#x60; is displayed as Always Off.
     * @param duoRemoteFlowsAvailability Enable GitLab Duo remote flows for this group
     * @param duoFoundationalFlowsAvailability Enable GitLab foundational Duo flows for this group
     * @param duoScheduledAutomationsAvailability Allow GitLab Duo flows in this group to run on a schedule
     * @param duoCodeSuggestionsAvailability Enable GitLab Duo Code Suggestions for this group
     * @param duoCustomAgentsAvailability Enable GitLab Duo custom agents for this group
     * @param duoCustomFlowsAvailability Enable GitLab Duo custom flows for this group
     * @param duoExternalAgentsAvailability Enable GitLab Duo external agents for this group
     * @param toolApprovalForSessionAvailability Tool approval for session availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param duoAutoModeAvailability Duo auto mode availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param amazonQAutoReviewEnabled Enable Amazon Q auto review for merge request
     * @param experimentFeaturesEnabled Enable experiment features for this group
     * @param modelPromptCacheEnabled Enable model prompt cache for this group
     * @param foundationalAgentsStatuses Whether each foundational agent has been enabled or disabled.
     * @param aiSettingsAttributes The aiSettingsAttributes parameter
     * @return APIEntitiesGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsRequestCreation(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Integer parentId, @jakarta.annotation.Nullable Integer organizationId, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Boolean shareWithGroupLock, @jakarta.annotation.Nullable Boolean requireTwoFactorAuthentication, @jakarta.annotation.Nullable Integer twoFactorGracePeriod, @jakarta.annotation.Nullable String projectCreationLevel, @jakarta.annotation.Nullable Boolean autoDevopsEnabled, @jakarta.annotation.Nullable String subgroupCreationLevel, @jakarta.annotation.Nullable Boolean emailsDisabled, @jakarta.annotation.Nullable Boolean emailsEnabled, @jakarta.annotation.Nullable Boolean showDiffPreviewInEmail, @jakarta.annotation.Nullable Boolean mentionsDisabled, @jakarta.annotation.Nullable Boolean lfsEnabled, @jakarta.annotation.Nullable Boolean requestAccessEnabled, @jakarta.annotation.Nullable String defaultBranch, @jakarta.annotation.Nullable Integer defaultBranchProtection, @jakarta.annotation.Nullable PostApiV4GroupsRequestDefaultBranchProtectionDefaults defaultBranchProtectionDefaults, @jakarta.annotation.Nullable String enabledGitAccessProtocol, @jakarta.annotation.Nullable Boolean crmEnabled, @jakarta.annotation.Nullable Boolean resourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean lockResourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean membershipLock, @jakarta.annotation.Nullable String ldapCn, @jakarta.annotation.Nullable Integer ldapAccess, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable String wikiAccessLevel, @jakarta.annotation.Nullable String duoAvailability, @jakarta.annotation.Nullable Boolean duoRemoteFlowsAvailability, @jakarta.annotation.Nullable Boolean duoFoundationalFlowsAvailability, @jakarta.annotation.Nullable Boolean duoScheduledAutomationsAvailability, @jakarta.annotation.Nullable Boolean duoCodeSuggestionsAvailability, @jakarta.annotation.Nullable Boolean duoCustomAgentsAvailability, @jakarta.annotation.Nullable Boolean duoCustomFlowsAvailability, @jakarta.annotation.Nullable Boolean duoExternalAgentsAvailability, @jakarta.annotation.Nullable String toolApprovalForSessionAvailability, @jakarta.annotation.Nullable String duoAutoModeAvailability, @jakarta.annotation.Nullable Boolean amazonQAutoReviewEnabled, @jakarta.annotation.Nullable Boolean experimentFeaturesEnabled, @jakarta.annotation.Nullable Boolean modelPromptCacheEnabled, @jakarta.annotation.Nullable List<PostApiV4GroupsRequestFoundationalAgentsStatusesInner> foundationalAgentsStatuses, @jakarta.annotation.Nullable PostApiV4GroupsRequestAiSettingsAttributes aiSettingsAttributes) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4Groups", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling postApiV4Groups", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("name", name);
        formParams.add("path", path);
        if (parentId != null) {
            formParams.add("parent_id", parentId);
        }
        if (organizationId != null) {
            formParams.add("organization_id", organizationId);
        }
        if (description != null) {
            formParams.add("description", description);
        }
        if (visibility != null) {
            formParams.add("visibility", visibility);
        }
        if (avatar != null) {
            formParams.add("avatar", new FileSystemResource(avatar));
        }
        if (shareWithGroupLock != null) {
            formParams.add("share_with_group_lock", shareWithGroupLock);
        }
        if (requireTwoFactorAuthentication != null) {
            formParams.add("require_two_factor_authentication", requireTwoFactorAuthentication);
        }
        if (twoFactorGracePeriod != null) {
            formParams.add("two_factor_grace_period", twoFactorGracePeriod);
        }
        if (projectCreationLevel != null) {
            formParams.add("project_creation_level", projectCreationLevel);
        }
        if (autoDevopsEnabled != null) {
            formParams.add("auto_devops_enabled", autoDevopsEnabled);
        }
        if (subgroupCreationLevel != null) {
            formParams.add("subgroup_creation_level", subgroupCreationLevel);
        }
        if (emailsDisabled != null) {
            formParams.add("emails_disabled", emailsDisabled);
        }
        if (emailsEnabled != null) {
            formParams.add("emails_enabled", emailsEnabled);
        }
        if (showDiffPreviewInEmail != null) {
            formParams.add("show_diff_preview_in_email", showDiffPreviewInEmail);
        }
        if (mentionsDisabled != null) {
            formParams.add("mentions_disabled", mentionsDisabled);
        }
        if (lfsEnabled != null) {
            formParams.add("lfs_enabled", lfsEnabled);
        }
        if (requestAccessEnabled != null) {
            formParams.add("request_access_enabled", requestAccessEnabled);
        }
        if (defaultBranch != null) {
            formParams.add("default_branch", defaultBranch);
        }
        if (defaultBranchProtection != null) {
            formParams.add("default_branch_protection", defaultBranchProtection);
        }
        if (defaultBranchProtectionDefaults != null) {
            formParams.add("default_branch_protection_defaults", defaultBranchProtectionDefaults);
        }
        if (enabledGitAccessProtocol != null) {
            formParams.add("enabled_git_access_protocol", enabledGitAccessProtocol);
        }
        if (crmEnabled != null) {
            formParams.add("crm_enabled", crmEnabled);
        }
        if (resourceAccessTokenNotifyInherited != null) {
            formParams.add("resource_access_token_notify_inherited", resourceAccessTokenNotifyInherited);
        }
        if (lockResourceAccessTokenNotifyInherited != null) {
            formParams.add("lock_resource_access_token_notify_inherited", lockResourceAccessTokenNotifyInherited);
        }
        if (membershipLock != null) {
            formParams.add("membership_lock", membershipLock);
        }
        if (ldapCn != null) {
            formParams.add("ldap_cn", ldapCn);
        }
        if (ldapAccess != null) {
            formParams.add("ldap_access", ldapAccess);
        }
        if (sharedRunnersMinutesLimit != null) {
            formParams.add("shared_runners_minutes_limit", sharedRunnersMinutesLimit);
        }
        if (extraSharedRunnersMinutesLimit != null) {
            formParams.add("extra_shared_runners_minutes_limit", extraSharedRunnersMinutesLimit);
        }
        if (wikiAccessLevel != null) {
            formParams.add("wiki_access_level", wikiAccessLevel);
        }
        if (duoAvailability != null) {
            formParams.add("duo_availability", duoAvailability);
        }
        if (duoRemoteFlowsAvailability != null) {
            formParams.add("duo_remote_flows_availability", duoRemoteFlowsAvailability);
        }
        if (duoFoundationalFlowsAvailability != null) {
            formParams.add("duo_foundational_flows_availability", duoFoundationalFlowsAvailability);
        }
        if (duoScheduledAutomationsAvailability != null) {
            formParams.add("duo_scheduled_automations_availability", duoScheduledAutomationsAvailability);
        }
        if (duoCodeSuggestionsAvailability != null) {
            formParams.add("duo_code_suggestions_availability", duoCodeSuggestionsAvailability);
        }
        if (duoCustomAgentsAvailability != null) {
            formParams.add("duo_custom_agents_availability", duoCustomAgentsAvailability);
        }
        if (duoCustomFlowsAvailability != null) {
            formParams.add("duo_custom_flows_availability", duoCustomFlowsAvailability);
        }
        if (duoExternalAgentsAvailability != null) {
            formParams.add("duo_external_agents_availability", duoExternalAgentsAvailability);
        }
        if (toolApprovalForSessionAvailability != null) {
            formParams.add("tool_approval_for_session_availability", toolApprovalForSessionAvailability);
        }
        if (duoAutoModeAvailability != null) {
            formParams.add("duo_auto_mode_availability", duoAutoModeAvailability);
        }
        if (amazonQAutoReviewEnabled != null) {
            formParams.add("amazon_q_auto_review_enabled", amazonQAutoReviewEnabled);
        }
        if (experimentFeaturesEnabled != null) {
            formParams.add("experiment_features_enabled", experimentFeaturesEnabled);
        }
        if (modelPromptCacheEnabled != null) {
            formParams.add("model_prompt_cache_enabled", modelPromptCacheEnabled);
        }
        if (foundationalAgentsStatuses != null) {
            formParams.addAll("foundational_agents_statuses", foundationalAgentsStatuses);
        }
        if (aiSettingsAttributes != null) {
            formParams.add("ai_settings_attributes", aiSettingsAttributes);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group
     * Creates a project group. Available only for users who can create groups. On GitLab.com, top-level groups cannot be created with the API.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param name The name of the group
     * @param path The path of the group
     * @param parentId The parent group id for creating nested group
     * @param organizationId The organization id for the group. Defaults to the organization of the parent group if &#x60;parent_id&#x60; is provided, otherwise the current organization.
     * @param description The description of the group
     * @param visibility The visibility of the group
     * @param avatar Avatar image for the group. On update, a blank value removes the avatar.
     * @param shareWithGroupLock Prevent sharing a project with another group within this group
     * @param requireTwoFactorAuthentication Require all users in this group to setup Two-factor authentication
     * @param twoFactorGracePeriod Time before Two-factor authentication is enforced, in hours.
     * @param projectCreationLevel Determine if developers can create projects in the group
     * @param autoDevopsEnabled Default to Auto DevOps pipeline for all projects within this group
     * @param subgroupCreationLevel Allowed to create subgroups
     * @param emailsDisabled Disable email notifications. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/127899) in GitLab 16.5. Use &#x60;emails_enabled&#x60; instead.
     * @param emailsEnabled Enable email notifications
     * @param showDiffPreviewInEmail Include the code diff preview in merge request notification emails
     * @param mentionsDisabled Disable a group from getting mentioned
     * @param lfsEnabled Enable/disable LFS for the projects in this group
     * @param requestAccessEnabled Allow users to request member access
     * @param defaultBranch The default branch of group&#39;s projects
     * @param defaultBranchProtection Determine if developers can push to default branch. &#x60;0&#x60;: no protection. Users with the Developer or Maintainer role can push new commits, force push, and delete the branch. &#x60;1&#x60;: partial protection. Users with the Developer or Maintainer role can push new commits. &#x60;2&#x60;: full protection. Only users with the Maintainer role can push new commits. &#x60;3&#x60;: protected against pushes. Users with the Maintainer role can push new commits, force push, and accept merge requests. Users with the Developer role can accept merge requests. &#x60;4&#x60;: full protection after initial push. Users with the Developer role can push commits to an empty repository. Users with the Maintainer role can push new commits and accept merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/408314) in GitLab 17.0. Use &#x60;default_branch_protection_defaults&#x60; instead.
     * @param defaultBranchProtectionDefaults The defaultBranchProtectionDefaults parameter
     * @param enabledGitAccessProtocol Allow only the selected protocols to be used for Git access.
     * @param crmEnabled Enable Customer Relations Management for this group
     * @param resourceAccessTokenNotifyInherited Send access token expiry notifications to all inherited members of the group
     * @param lockResourceAccessTokenNotifyInherited Prevent subgroups from overriding the access token expiry notification setting
     * @param membershipLock Prevent adding new members to projects within this group. Premium and Ultimate only.
     * @param ldapCn LDAP Common Name. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param ldapAccess A valid access level. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param sharedRunnersMinutesLimit (admin-only) Maximum number of monthly compute minutes for this group. &#x60;nil&#x60; (default) inherits the instance default, and &#x60;0&#x60; means unlimited. GitLab Self-Managed, Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this group. GitLab Self-Managed, Premium and Ultimate only.
     * @param wikiAccessLevel Wiki access level. One of &#x60;disabled&#x60;, &#x60;private&#x60; or &#x60;enabled&#x60;. Premium and Ultimate only.
     * @param duoAvailability Duo availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60;, &#x60;never_on&#x60; or &#x60;always_on&#x60;. In the UI, &#x60;never_on&#x60; is displayed as Always Off.
     * @param duoRemoteFlowsAvailability Enable GitLab Duo remote flows for this group
     * @param duoFoundationalFlowsAvailability Enable GitLab foundational Duo flows for this group
     * @param duoScheduledAutomationsAvailability Allow GitLab Duo flows in this group to run on a schedule
     * @param duoCodeSuggestionsAvailability Enable GitLab Duo Code Suggestions for this group
     * @param duoCustomAgentsAvailability Enable GitLab Duo custom agents for this group
     * @param duoCustomFlowsAvailability Enable GitLab Duo custom flows for this group
     * @param duoExternalAgentsAvailability Enable GitLab Duo external agents for this group
     * @param toolApprovalForSessionAvailability Tool approval for session availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param duoAutoModeAvailability Duo auto mode availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param amazonQAutoReviewEnabled Enable Amazon Q auto review for merge request
     * @param experimentFeaturesEnabled Enable experiment features for this group
     * @param modelPromptCacheEnabled Enable model prompt cache for this group
     * @param foundationalAgentsStatuses Whether each foundational agent has been enabled or disabled.
     * @param aiSettingsAttributes The aiSettingsAttributes parameter
     * @return APIEntitiesGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroup postApiV4Groups(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Integer parentId, @jakarta.annotation.Nullable Integer organizationId, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Boolean shareWithGroupLock, @jakarta.annotation.Nullable Boolean requireTwoFactorAuthentication, @jakarta.annotation.Nullable Integer twoFactorGracePeriod, @jakarta.annotation.Nullable String projectCreationLevel, @jakarta.annotation.Nullable Boolean autoDevopsEnabled, @jakarta.annotation.Nullable String subgroupCreationLevel, @jakarta.annotation.Nullable Boolean emailsDisabled, @jakarta.annotation.Nullable Boolean emailsEnabled, @jakarta.annotation.Nullable Boolean showDiffPreviewInEmail, @jakarta.annotation.Nullable Boolean mentionsDisabled, @jakarta.annotation.Nullable Boolean lfsEnabled, @jakarta.annotation.Nullable Boolean requestAccessEnabled, @jakarta.annotation.Nullable String defaultBranch, @jakarta.annotation.Nullable Integer defaultBranchProtection, @jakarta.annotation.Nullable PostApiV4GroupsRequestDefaultBranchProtectionDefaults defaultBranchProtectionDefaults, @jakarta.annotation.Nullable String enabledGitAccessProtocol, @jakarta.annotation.Nullable Boolean crmEnabled, @jakarta.annotation.Nullable Boolean resourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean lockResourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean membershipLock, @jakarta.annotation.Nullable String ldapCn, @jakarta.annotation.Nullable Integer ldapAccess, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable String wikiAccessLevel, @jakarta.annotation.Nullable String duoAvailability, @jakarta.annotation.Nullable Boolean duoRemoteFlowsAvailability, @jakarta.annotation.Nullable Boolean duoFoundationalFlowsAvailability, @jakarta.annotation.Nullable Boolean duoScheduledAutomationsAvailability, @jakarta.annotation.Nullable Boolean duoCodeSuggestionsAvailability, @jakarta.annotation.Nullable Boolean duoCustomAgentsAvailability, @jakarta.annotation.Nullable Boolean duoCustomFlowsAvailability, @jakarta.annotation.Nullable Boolean duoExternalAgentsAvailability, @jakarta.annotation.Nullable String toolApprovalForSessionAvailability, @jakarta.annotation.Nullable String duoAutoModeAvailability, @jakarta.annotation.Nullable Boolean amazonQAutoReviewEnabled, @jakarta.annotation.Nullable Boolean experimentFeaturesEnabled, @jakarta.annotation.Nullable Boolean modelPromptCacheEnabled, @jakarta.annotation.Nullable List<PostApiV4GroupsRequestFoundationalAgentsStatusesInner> foundationalAgentsStatuses, @jakarta.annotation.Nullable PostApiV4GroupsRequestAiSettingsAttributes aiSettingsAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsRequestCreation(name, path, parentId, organizationId, description, visibility, avatar, shareWithGroupLock, requireTwoFactorAuthentication, twoFactorGracePeriod, projectCreationLevel, autoDevopsEnabled, subgroupCreationLevel, emailsDisabled, emailsEnabled, showDiffPreviewInEmail, mentionsDisabled, lfsEnabled, requestAccessEnabled, defaultBranch, defaultBranchProtection, defaultBranchProtectionDefaults, enabledGitAccessProtocol, crmEnabled, resourceAccessTokenNotifyInherited, lockResourceAccessTokenNotifyInherited, membershipLock, ldapCn, ldapAccess, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, wikiAccessLevel, duoAvailability, duoRemoteFlowsAvailability, duoFoundationalFlowsAvailability, duoScheduledAutomationsAvailability, duoCodeSuggestionsAvailability, duoCustomAgentsAvailability, duoCustomFlowsAvailability, duoExternalAgentsAvailability, toolApprovalForSessionAvailability, duoAutoModeAvailability, amazonQAutoReviewEnabled, experimentFeaturesEnabled, modelPromptCacheEnabled, foundationalAgentsStatuses, aiSettingsAttributes).body(localVarReturnType);
    }

    /**
     * Create a group
     * Creates a project group. Available only for users who can create groups. On GitLab.com, top-level groups cannot be created with the API.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param name The name of the group
     * @param path The path of the group
     * @param parentId The parent group id for creating nested group
     * @param organizationId The organization id for the group. Defaults to the organization of the parent group if &#x60;parent_id&#x60; is provided, otherwise the current organization.
     * @param description The description of the group
     * @param visibility The visibility of the group
     * @param avatar Avatar image for the group. On update, a blank value removes the avatar.
     * @param shareWithGroupLock Prevent sharing a project with another group within this group
     * @param requireTwoFactorAuthentication Require all users in this group to setup Two-factor authentication
     * @param twoFactorGracePeriod Time before Two-factor authentication is enforced, in hours.
     * @param projectCreationLevel Determine if developers can create projects in the group
     * @param autoDevopsEnabled Default to Auto DevOps pipeline for all projects within this group
     * @param subgroupCreationLevel Allowed to create subgroups
     * @param emailsDisabled Disable email notifications. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/127899) in GitLab 16.5. Use &#x60;emails_enabled&#x60; instead.
     * @param emailsEnabled Enable email notifications
     * @param showDiffPreviewInEmail Include the code diff preview in merge request notification emails
     * @param mentionsDisabled Disable a group from getting mentioned
     * @param lfsEnabled Enable/disable LFS for the projects in this group
     * @param requestAccessEnabled Allow users to request member access
     * @param defaultBranch The default branch of group&#39;s projects
     * @param defaultBranchProtection Determine if developers can push to default branch. &#x60;0&#x60;: no protection. Users with the Developer or Maintainer role can push new commits, force push, and delete the branch. &#x60;1&#x60;: partial protection. Users with the Developer or Maintainer role can push new commits. &#x60;2&#x60;: full protection. Only users with the Maintainer role can push new commits. &#x60;3&#x60;: protected against pushes. Users with the Maintainer role can push new commits, force push, and accept merge requests. Users with the Developer role can accept merge requests. &#x60;4&#x60;: full protection after initial push. Users with the Developer role can push commits to an empty repository. Users with the Maintainer role can push new commits and accept merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/408314) in GitLab 17.0. Use &#x60;default_branch_protection_defaults&#x60; instead.
     * @param defaultBranchProtectionDefaults The defaultBranchProtectionDefaults parameter
     * @param enabledGitAccessProtocol Allow only the selected protocols to be used for Git access.
     * @param crmEnabled Enable Customer Relations Management for this group
     * @param resourceAccessTokenNotifyInherited Send access token expiry notifications to all inherited members of the group
     * @param lockResourceAccessTokenNotifyInherited Prevent subgroups from overriding the access token expiry notification setting
     * @param membershipLock Prevent adding new members to projects within this group. Premium and Ultimate only.
     * @param ldapCn LDAP Common Name. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param ldapAccess A valid access level. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param sharedRunnersMinutesLimit (admin-only) Maximum number of monthly compute minutes for this group. &#x60;nil&#x60; (default) inherits the instance default, and &#x60;0&#x60; means unlimited. GitLab Self-Managed, Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this group. GitLab Self-Managed, Premium and Ultimate only.
     * @param wikiAccessLevel Wiki access level. One of &#x60;disabled&#x60;, &#x60;private&#x60; or &#x60;enabled&#x60;. Premium and Ultimate only.
     * @param duoAvailability Duo availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60;, &#x60;never_on&#x60; or &#x60;always_on&#x60;. In the UI, &#x60;never_on&#x60; is displayed as Always Off.
     * @param duoRemoteFlowsAvailability Enable GitLab Duo remote flows for this group
     * @param duoFoundationalFlowsAvailability Enable GitLab foundational Duo flows for this group
     * @param duoScheduledAutomationsAvailability Allow GitLab Duo flows in this group to run on a schedule
     * @param duoCodeSuggestionsAvailability Enable GitLab Duo Code Suggestions for this group
     * @param duoCustomAgentsAvailability Enable GitLab Duo custom agents for this group
     * @param duoCustomFlowsAvailability Enable GitLab Duo custom flows for this group
     * @param duoExternalAgentsAvailability Enable GitLab Duo external agents for this group
     * @param toolApprovalForSessionAvailability Tool approval for session availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param duoAutoModeAvailability Duo auto mode availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param amazonQAutoReviewEnabled Enable Amazon Q auto review for merge request
     * @param experimentFeaturesEnabled Enable experiment features for this group
     * @param modelPromptCacheEnabled Enable model prompt cache for this group
     * @param foundationalAgentsStatuses Whether each foundational agent has been enabled or disabled.
     * @param aiSettingsAttributes The aiSettingsAttributes parameter
     * @return ResponseEntity&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroup> postApiV4GroupsWithHttpInfo(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Integer parentId, @jakarta.annotation.Nullable Integer organizationId, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Boolean shareWithGroupLock, @jakarta.annotation.Nullable Boolean requireTwoFactorAuthentication, @jakarta.annotation.Nullable Integer twoFactorGracePeriod, @jakarta.annotation.Nullable String projectCreationLevel, @jakarta.annotation.Nullable Boolean autoDevopsEnabled, @jakarta.annotation.Nullable String subgroupCreationLevel, @jakarta.annotation.Nullable Boolean emailsDisabled, @jakarta.annotation.Nullable Boolean emailsEnabled, @jakarta.annotation.Nullable Boolean showDiffPreviewInEmail, @jakarta.annotation.Nullable Boolean mentionsDisabled, @jakarta.annotation.Nullable Boolean lfsEnabled, @jakarta.annotation.Nullable Boolean requestAccessEnabled, @jakarta.annotation.Nullable String defaultBranch, @jakarta.annotation.Nullable Integer defaultBranchProtection, @jakarta.annotation.Nullable PostApiV4GroupsRequestDefaultBranchProtectionDefaults defaultBranchProtectionDefaults, @jakarta.annotation.Nullable String enabledGitAccessProtocol, @jakarta.annotation.Nullable Boolean crmEnabled, @jakarta.annotation.Nullable Boolean resourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean lockResourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean membershipLock, @jakarta.annotation.Nullable String ldapCn, @jakarta.annotation.Nullable Integer ldapAccess, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable String wikiAccessLevel, @jakarta.annotation.Nullable String duoAvailability, @jakarta.annotation.Nullable Boolean duoRemoteFlowsAvailability, @jakarta.annotation.Nullable Boolean duoFoundationalFlowsAvailability, @jakarta.annotation.Nullable Boolean duoScheduledAutomationsAvailability, @jakarta.annotation.Nullable Boolean duoCodeSuggestionsAvailability, @jakarta.annotation.Nullable Boolean duoCustomAgentsAvailability, @jakarta.annotation.Nullable Boolean duoCustomFlowsAvailability, @jakarta.annotation.Nullable Boolean duoExternalAgentsAvailability, @jakarta.annotation.Nullable String toolApprovalForSessionAvailability, @jakarta.annotation.Nullable String duoAutoModeAvailability, @jakarta.annotation.Nullable Boolean amazonQAutoReviewEnabled, @jakarta.annotation.Nullable Boolean experimentFeaturesEnabled, @jakarta.annotation.Nullable Boolean modelPromptCacheEnabled, @jakarta.annotation.Nullable List<PostApiV4GroupsRequestFoundationalAgentsStatusesInner> foundationalAgentsStatuses, @jakarta.annotation.Nullable PostApiV4GroupsRequestAiSettingsAttributes aiSettingsAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsRequestCreation(name, path, parentId, organizationId, description, visibility, avatar, shareWithGroupLock, requireTwoFactorAuthentication, twoFactorGracePeriod, projectCreationLevel, autoDevopsEnabled, subgroupCreationLevel, emailsDisabled, emailsEnabled, showDiffPreviewInEmail, mentionsDisabled, lfsEnabled, requestAccessEnabled, defaultBranch, defaultBranchProtection, defaultBranchProtectionDefaults, enabledGitAccessProtocol, crmEnabled, resourceAccessTokenNotifyInherited, lockResourceAccessTokenNotifyInherited, membershipLock, ldapCn, ldapAccess, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, wikiAccessLevel, duoAvailability, duoRemoteFlowsAvailability, duoFoundationalFlowsAvailability, duoScheduledAutomationsAvailability, duoCodeSuggestionsAvailability, duoCustomAgentsAvailability, duoCustomFlowsAvailability, duoExternalAgentsAvailability, toolApprovalForSessionAvailability, duoAutoModeAvailability, amazonQAutoReviewEnabled, experimentFeaturesEnabled, modelPromptCacheEnabled, foundationalAgentsStatuses, aiSettingsAttributes).toEntity(localVarReturnType);
    }

    /**
     * Create a group
     * Creates a project group. Available only for users who can create groups. On GitLab.com, top-level groups cannot be created with the API.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param name The name of the group
     * @param path The path of the group
     * @param parentId The parent group id for creating nested group
     * @param organizationId The organization id for the group. Defaults to the organization of the parent group if &#x60;parent_id&#x60; is provided, otherwise the current organization.
     * @param description The description of the group
     * @param visibility The visibility of the group
     * @param avatar Avatar image for the group. On update, a blank value removes the avatar.
     * @param shareWithGroupLock Prevent sharing a project with another group within this group
     * @param requireTwoFactorAuthentication Require all users in this group to setup Two-factor authentication
     * @param twoFactorGracePeriod Time before Two-factor authentication is enforced, in hours.
     * @param projectCreationLevel Determine if developers can create projects in the group
     * @param autoDevopsEnabled Default to Auto DevOps pipeline for all projects within this group
     * @param subgroupCreationLevel Allowed to create subgroups
     * @param emailsDisabled Disable email notifications. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/127899) in GitLab 16.5. Use &#x60;emails_enabled&#x60; instead.
     * @param emailsEnabled Enable email notifications
     * @param showDiffPreviewInEmail Include the code diff preview in merge request notification emails
     * @param mentionsDisabled Disable a group from getting mentioned
     * @param lfsEnabled Enable/disable LFS for the projects in this group
     * @param requestAccessEnabled Allow users to request member access
     * @param defaultBranch The default branch of group&#39;s projects
     * @param defaultBranchProtection Determine if developers can push to default branch. &#x60;0&#x60;: no protection. Users with the Developer or Maintainer role can push new commits, force push, and delete the branch. &#x60;1&#x60;: partial protection. Users with the Developer or Maintainer role can push new commits. &#x60;2&#x60;: full protection. Only users with the Maintainer role can push new commits. &#x60;3&#x60;: protected against pushes. Users with the Maintainer role can push new commits, force push, and accept merge requests. Users with the Developer role can accept merge requests. &#x60;4&#x60;: full protection after initial push. Users with the Developer role can push commits to an empty repository. Users with the Maintainer role can push new commits and accept merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/408314) in GitLab 17.0. Use &#x60;default_branch_protection_defaults&#x60; instead.
     * @param defaultBranchProtectionDefaults The defaultBranchProtectionDefaults parameter
     * @param enabledGitAccessProtocol Allow only the selected protocols to be used for Git access.
     * @param crmEnabled Enable Customer Relations Management for this group
     * @param resourceAccessTokenNotifyInherited Send access token expiry notifications to all inherited members of the group
     * @param lockResourceAccessTokenNotifyInherited Prevent subgroups from overriding the access token expiry notification setting
     * @param membershipLock Prevent adding new members to projects within this group. Premium and Ultimate only.
     * @param ldapCn LDAP Common Name. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param ldapAccess A valid access level. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param sharedRunnersMinutesLimit (admin-only) Maximum number of monthly compute minutes for this group. &#x60;nil&#x60; (default) inherits the instance default, and &#x60;0&#x60; means unlimited. GitLab Self-Managed, Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this group. GitLab Self-Managed, Premium and Ultimate only.
     * @param wikiAccessLevel Wiki access level. One of &#x60;disabled&#x60;, &#x60;private&#x60; or &#x60;enabled&#x60;. Premium and Ultimate only.
     * @param duoAvailability Duo availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60;, &#x60;never_on&#x60; or &#x60;always_on&#x60;. In the UI, &#x60;never_on&#x60; is displayed as Always Off.
     * @param duoRemoteFlowsAvailability Enable GitLab Duo remote flows for this group
     * @param duoFoundationalFlowsAvailability Enable GitLab foundational Duo flows for this group
     * @param duoScheduledAutomationsAvailability Allow GitLab Duo flows in this group to run on a schedule
     * @param duoCodeSuggestionsAvailability Enable GitLab Duo Code Suggestions for this group
     * @param duoCustomAgentsAvailability Enable GitLab Duo custom agents for this group
     * @param duoCustomFlowsAvailability Enable GitLab Duo custom flows for this group
     * @param duoExternalAgentsAvailability Enable GitLab Duo external agents for this group
     * @param toolApprovalForSessionAvailability Tool approval for session availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param duoAutoModeAvailability Duo auto mode availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param amazonQAutoReviewEnabled Enable Amazon Q auto review for merge request
     * @param experimentFeaturesEnabled Enable experiment features for this group
     * @param modelPromptCacheEnabled Enable model prompt cache for this group
     * @param foundationalAgentsStatuses Whether each foundational agent has been enabled or disabled.
     * @param aiSettingsAttributes The aiSettingsAttributes parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsWithResponseSpec(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable Integer parentId, @jakarta.annotation.Nullable Integer organizationId, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Boolean shareWithGroupLock, @jakarta.annotation.Nullable Boolean requireTwoFactorAuthentication, @jakarta.annotation.Nullable Integer twoFactorGracePeriod, @jakarta.annotation.Nullable String projectCreationLevel, @jakarta.annotation.Nullable Boolean autoDevopsEnabled, @jakarta.annotation.Nullable String subgroupCreationLevel, @jakarta.annotation.Nullable Boolean emailsDisabled, @jakarta.annotation.Nullable Boolean emailsEnabled, @jakarta.annotation.Nullable Boolean showDiffPreviewInEmail, @jakarta.annotation.Nullable Boolean mentionsDisabled, @jakarta.annotation.Nullable Boolean lfsEnabled, @jakarta.annotation.Nullable Boolean requestAccessEnabled, @jakarta.annotation.Nullable String defaultBranch, @jakarta.annotation.Nullable Integer defaultBranchProtection, @jakarta.annotation.Nullable PostApiV4GroupsRequestDefaultBranchProtectionDefaults defaultBranchProtectionDefaults, @jakarta.annotation.Nullable String enabledGitAccessProtocol, @jakarta.annotation.Nullable Boolean crmEnabled, @jakarta.annotation.Nullable Boolean resourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean lockResourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean membershipLock, @jakarta.annotation.Nullable String ldapCn, @jakarta.annotation.Nullable Integer ldapAccess, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable String wikiAccessLevel, @jakarta.annotation.Nullable String duoAvailability, @jakarta.annotation.Nullable Boolean duoRemoteFlowsAvailability, @jakarta.annotation.Nullable Boolean duoFoundationalFlowsAvailability, @jakarta.annotation.Nullable Boolean duoScheduledAutomationsAvailability, @jakarta.annotation.Nullable Boolean duoCodeSuggestionsAvailability, @jakarta.annotation.Nullable Boolean duoCustomAgentsAvailability, @jakarta.annotation.Nullable Boolean duoCustomFlowsAvailability, @jakarta.annotation.Nullable Boolean duoExternalAgentsAvailability, @jakarta.annotation.Nullable String toolApprovalForSessionAvailability, @jakarta.annotation.Nullable String duoAutoModeAvailability, @jakarta.annotation.Nullable Boolean amazonQAutoReviewEnabled, @jakarta.annotation.Nullable Boolean experimentFeaturesEnabled, @jakarta.annotation.Nullable Boolean modelPromptCacheEnabled, @jakarta.annotation.Nullable List<PostApiV4GroupsRequestFoundationalAgentsStatusesInner> foundationalAgentsStatuses, @jakarta.annotation.Nullable PostApiV4GroupsRequestAiSettingsAttributes aiSettingsAttributes) throws RestClientResponseException {
        return postApiV4GroupsRequestCreation(name, path, parentId, organizationId, description, visibility, avatar, shareWithGroupLock, requireTwoFactorAuthentication, twoFactorGracePeriod, projectCreationLevel, autoDevopsEnabled, subgroupCreationLevel, emailsDisabled, emailsEnabled, showDiffPreviewInEmail, mentionsDisabled, lfsEnabled, requestAccessEnabled, defaultBranch, defaultBranchProtection, defaultBranchProtectionDefaults, enabledGitAccessProtocol, crmEnabled, resourceAccessTokenNotifyInherited, lockResourceAccessTokenNotifyInherited, membershipLock, ldapCn, ldapAccess, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, wikiAccessLevel, duoAvailability, duoRemoteFlowsAvailability, duoFoundationalFlowsAvailability, duoScheduledAutomationsAvailability, duoCodeSuggestionsAvailability, duoCustomAgentsAvailability, duoCustomFlowsAvailability, duoExternalAgentsAvailability, toolApprovalForSessionAvailability, duoAutoModeAvailability, amazonQAutoReviewEnabled, experimentFeaturesEnabled, modelPromptCacheEnabled, foundationalAgentsStatuses, aiSettingsAttributes);
    }

    /**
     * Archive a group
     * Archives a specified group. You must be an administrator or have the Owner role for the group. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdArchiveRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdArchive", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/archive", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Archive a group
     * Archives a specified group. You must be an administrator or have the Owner role for the group. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroup postApiV4GroupsIdArchive(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdArchiveRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Archive a group
     * Archives a specified group. You must be an administrator or have the Owner role for the group. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroup> postApiV4GroupsIdArchiveWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdArchiveRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Archive a group
     * Archives a specified group. You must be an administrator or have the Owner role for the group. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdArchiveWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4GroupsIdArchiveRequestCreation(id);
    }

    /**
     * Reassign placeholders
     * Reassigns placeholder users with an uploaded CSV file.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param _file The CSV file containing the reassignments
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdPlaceholderReassignmentsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdPlaceholderReassignments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4GroupsIdPlaceholderReassignments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/placeholder_reassignments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reassign placeholders
     * Reassigns placeholder users with an uploaded CSV file.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param _file The CSV file containing the reassignments
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdPlaceholderReassignments(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdPlaceholderReassignmentsRequestCreation(id, _file).body(localVarReturnType);
    }

    /**
     * Reassign placeholders
     * Reassigns placeholder users with an uploaded CSV file.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param _file The CSV file containing the reassignments
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdPlaceholderReassignmentsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdPlaceholderReassignmentsRequestCreation(id, _file).toEntity(localVarReturnType);
    }

    /**
     * Reassign placeholders
     * Reassigns placeholder users with an uploaded CSV file.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param _file The CSV file containing the reassignments
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdPlaceholderReassignmentsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return postApiV4GroupsIdPlaceholderReassignmentsRequestCreation(id, _file);
    }

    /**
     * Workhorse authorization for the reassignment CSV file
     * Authorizes Workhorse to handle CSV file uploads for placeholder reassignments.           This feature was introduced in GitLab 17.10
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdPlaceholderReassignmentsAuthorizeRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdPlaceholderReassignmentsAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/placeholder_reassignments/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorization for the reassignment CSV file
     * Authorizes Workhorse to handle CSV file uploads for placeholder reassignments.           This feature was introduced in GitLab 17.10
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdPlaceholderReassignmentsAuthorize(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdPlaceholderReassignmentsAuthorizeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Workhorse authorization for the reassignment CSV file
     * Authorizes Workhorse to handle CSV file uploads for placeholder reassignments.           This feature was introduced in GitLab 17.10
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdPlaceholderReassignmentsAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdPlaceholderReassignmentsAuthorizeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorization for the reassignment CSV file
     * Authorizes Workhorse to handle CSV file uploads for placeholder reassignments.           This feature was introduced in GitLab 17.10
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdPlaceholderReassignmentsAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4GroupsIdPlaceholderReassignmentsAuthorizeRequestCreation(id);
    }

    /**
     * Transfer a project to a group
     * Transfers a specified project to another group. Administrators only. To transfer a project to any namespace, use &#x60;PUT /projects/:id/transfer&#x60; instead. The transfer can fail if image tags are present in the project&#39;s container registry.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID or URL-encoded path of the project.
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdProjectsProjectIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String projectId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdProjectsProjectId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling postApiV4GroupsIdProjectsProjectId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("project_id", projectId);

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

        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/projects/{project_id}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Transfer a project to a group
     * Transfers a specified project to another group. Administrators only. To transfer a project to any namespace, use &#x60;PUT /projects/:id/transfer&#x60; instead. The transfer can fail if image tags are present in the project&#39;s container registry.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID or URL-encoded path of the project.
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupDetail postApiV4GroupsIdProjectsProjectId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String projectId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdProjectsProjectIdRequestCreation(id, projectId).body(localVarReturnType);
    }

    /**
     * Transfer a project to a group
     * Transfers a specified project to another group. Administrators only. To transfer a project to any namespace, use &#x60;PUT /projects/:id/transfer&#x60; instead. The transfer can fail if image tags are present in the project&#39;s container registry.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesGroupDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupDetail> postApiV4GroupsIdProjectsProjectIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String projectId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdProjectsProjectIdRequestCreation(id, projectId).toEntity(localVarReturnType);
    }

    /**
     * Transfer a project to a group
     * Transfers a specified project to another group. Administrators only. To transfer a project to any namespace, use &#x60;PUT /projects/:id/transfer&#x60; instead. The transfer can fail if image tags are present in the project&#39;s container registry.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdProjectsProjectIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String projectId) throws RestClientResponseException {
        return postApiV4GroupsIdProjectsProjectIdRequestCreation(id, projectId);
    }

    /**
     * Restore a group
     * Restores a specified group that was previously scheduled for deletion. Can not restore groups outside of the retention period (30 days by default).
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdRestoreRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdRestore", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/restore", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Restore a group
     * Restores a specified group that was previously scheduled for deletion. Can not restore groups outside of the retention period (30 days by default).
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdRestore(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdRestoreRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Restore a group
     * Restores a specified group that was previously scheduled for deletion. Can not restore groups outside of the retention period (30 days by default).
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdRestoreWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdRestoreRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Restore a group
     * Restores a specified group that was previously scheduled for deletion. Can not restore groups outside of the retention period (30 days by default).
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdRestoreWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4GroupsIdRestoreRequestCreation(id);
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
     * Add a group to a group
     * Adds a group to a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdShareRequest The postApiV4GroupsIdShareRequest parameter
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdShareRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdShareRequest postApiV4GroupsIdShareRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdShare", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdShareRequest' is set
        if (postApiV4GroupsIdShareRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdShareRequest' when calling postApiV4GroupsIdShare", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/share", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdShareRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a group to a group
     * Adds a group to a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdShareRequest The postApiV4GroupsIdShareRequest parameter
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupDetail postApiV4GroupsIdShare(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdShareRequest postApiV4GroupsIdShareRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdShareRequestCreation(id, postApiV4GroupsIdShareRequest).body(localVarReturnType);
    }

    /**
     * Add a group to a group
     * Adds a group to a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdShareRequest The postApiV4GroupsIdShareRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupDetail> postApiV4GroupsIdShareWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdShareRequest postApiV4GroupsIdShareRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdShareRequestCreation(id, postApiV4GroupsIdShareRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a group to a group
     * Adds a group to a group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdShareRequest The postApiV4GroupsIdShareRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdShareWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdShareRequest postApiV4GroupsIdShareRequest) throws RestClientResponseException {
        return postApiV4GroupsIdShareRequestCreation(id, postApiV4GroupsIdShareRequest);
    }

    /**
     * Transfer a group
     * Transfers a group to another parent group or transforms a subgroup into a top-level group. You must be an administrator or have the Owner role for the group. To transfer the group to a new parent group, you must be able to create subgroups in it. To make a subgroup a top-level group, you must be able to create top-level groups. The transfer runs asynchronously. To confirm it, retrieve the group until its &#x60;parent_id&#x60; matches the ID of the new parent group, or is &#x60;null&#x60; for a top-level group. If the transfer fails, the user who requested it gets a to-do item with the &#x60;transfer_failed&#x60; action.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdTransferRequest The postApiV4GroupsIdTransferRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdTransferRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdTransferRequest postApiV4GroupsIdTransferRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdTransfer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/transfer", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdTransferRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Transfer a group
     * Transfers a group to another parent group or transforms a subgroup into a top-level group. You must be an administrator or have the Owner role for the group. To transfer the group to a new parent group, you must be able to create subgroups in it. To make a subgroup a top-level group, you must be able to create top-level groups. The transfer runs asynchronously. To confirm it, retrieve the group until its &#x60;parent_id&#x60; matches the ID of the new parent group, or is &#x60;null&#x60; for a top-level group. If the transfer fails, the user who requested it gets a to-do item with the &#x60;transfer_failed&#x60; action.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdTransferRequest The postApiV4GroupsIdTransferRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdTransfer(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdTransferRequest postApiV4GroupsIdTransferRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdTransferRequestCreation(id, postApiV4GroupsIdTransferRequest).body(localVarReturnType);
    }

    /**
     * Transfer a group
     * Transfers a group to another parent group or transforms a subgroup into a top-level group. You must be an administrator or have the Owner role for the group. To transfer the group to a new parent group, you must be able to create subgroups in it. To make a subgroup a top-level group, you must be able to create top-level groups. The transfer runs asynchronously. To confirm it, retrieve the group until its &#x60;parent_id&#x60; matches the ID of the new parent group, or is &#x60;null&#x60; for a top-level group. If the transfer fails, the user who requested it gets a to-do item with the &#x60;transfer_failed&#x60; action.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdTransferRequest The postApiV4GroupsIdTransferRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdTransferWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdTransferRequest postApiV4GroupsIdTransferRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdTransferRequestCreation(id, postApiV4GroupsIdTransferRequest).toEntity(localVarReturnType);
    }

    /**
     * Transfer a group
     * Transfers a group to another parent group or transforms a subgroup into a top-level group. You must be an administrator or have the Owner role for the group. To transfer the group to a new parent group, you must be able to create subgroups in it. To make a subgroup a top-level group, you must be able to create top-level groups. The transfer runs asynchronously. To confirm it, retrieve the group until its &#x60;parent_id&#x60; matches the ID of the new parent group, or is &#x60;null&#x60; for a top-level group. If the transfer fails, the user who requested it gets a to-do item with the &#x60;transfer_failed&#x60; action.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdTransferRequest The postApiV4GroupsIdTransferRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdTransferWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4GroupsIdTransferRequest postApiV4GroupsIdTransferRequest) throws RestClientResponseException {
        return postApiV4GroupsIdTransferRequestCreation(id, postApiV4GroupsIdTransferRequest);
    }

    /**
     * Transfer a group to an organization
     * Transfers a group to another organization asynchronously. Requires the Owner role.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Group or Organization not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdTransferToOrganizationRequest The postApiV4GroupsIdTransferToOrganizationRequest parameter
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdTransferToOrganizationRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdTransferToOrganizationRequest postApiV4GroupsIdTransferToOrganizationRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdTransferToOrganization", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdTransferToOrganizationRequest' is set
        if (postApiV4GroupsIdTransferToOrganizationRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdTransferToOrganizationRequest' when calling postApiV4GroupsIdTransferToOrganization", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/transfer_to_organization", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdTransferToOrganizationRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Transfer a group to an organization
     * Transfers a group to another organization asynchronously. Requires the Owner role.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Group or Organization not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdTransferToOrganizationRequest The postApiV4GroupsIdTransferToOrganizationRequest parameter
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupDetail postApiV4GroupsIdTransferToOrganization(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdTransferToOrganizationRequest postApiV4GroupsIdTransferToOrganizationRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdTransferToOrganizationRequestCreation(id, postApiV4GroupsIdTransferToOrganizationRequest).body(localVarReturnType);
    }

    /**
     * Transfer a group to an organization
     * Transfers a group to another organization asynchronously. Requires the Owner role.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Group or Organization not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdTransferToOrganizationRequest The postApiV4GroupsIdTransferToOrganizationRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupDetail> postApiV4GroupsIdTransferToOrganizationWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdTransferToOrganizationRequest postApiV4GroupsIdTransferToOrganizationRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdTransferToOrganizationRequestCreation(id, postApiV4GroupsIdTransferToOrganizationRequest).toEntity(localVarReturnType);
    }

    /**
     * Transfer a group to an organization
     * Transfers a group to another organization asynchronously. Requires the Owner role.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Group or Organization not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdTransferToOrganizationRequest The postApiV4GroupsIdTransferToOrganizationRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdTransferToOrganizationWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdTransferToOrganizationRequest postApiV4GroupsIdTransferToOrganizationRequest) throws RestClientResponseException {
        return postApiV4GroupsIdTransferToOrganizationRequestCreation(id, postApiV4GroupsIdTransferToOrganizationRequest);
    }

    /**
     * Unarchive a group
     * Unarchives a specified group. You must be an administrator or have the Owner role for the group. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdUnarchiveRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdUnarchive", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/unarchive", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unarchive a group
     * Unarchives a specified group. You must be an administrator or have the Owner role for the group. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroup postApiV4GroupsIdUnarchive(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdUnarchiveRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unarchive a group
     * Unarchives a specified group. You must be an administrator or have the Owner role for the group. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;APIEntitiesGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroup> postApiV4GroupsIdUnarchiveWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdUnarchiveRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unarchive a group
     * Unarchives a specified group. You must be an administrator or have the Owner role for the group. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdUnarchiveWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4GroupsIdUnarchiveRequestCreation(id);
    }

    /**
     * Upload a file to a group
     * Uploads a file to the specified group. Returns a markdown-formatted link to the file. This feature was introduced in GitLab 19.0.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param _file The file to upload
     * @return APIEntitiesGroupUpload
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdUploadsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdUploads", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4GroupsIdUploads", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGroupUpload> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/uploads", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a file to a group
     * Uploads a file to the specified group. Returns a markdown-formatted link to the file. This feature was introduced in GitLab 19.0.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param _file The file to upload
     * @return APIEntitiesGroupUpload
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupUpload postApiV4GroupsIdUploads(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull File _file) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupUpload> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdUploadsRequestCreation(id, _file).body(localVarReturnType);
    }

    /**
     * Upload a file to a group
     * Uploads a file to the specified group. Returns a markdown-formatted link to the file. This feature was introduced in GitLab 19.0.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param _file The file to upload
     * @return ResponseEntity&lt;APIEntitiesGroupUpload&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupUpload> postApiV4GroupsIdUploadsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull File _file) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupUpload> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdUploadsRequestCreation(id, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload a file to a group
     * Uploads a file to the specified group. Returns a markdown-formatted link to the file. This feature was introduced in GitLab 19.0.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param _file The file to upload
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdUploadsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull File _file) throws RestClientResponseException {
        return postApiV4GroupsIdUploadsRequestCreation(id, _file);
    }

    /**
     * Workhorse authorize the file upload
     * This feature was introduced in GitLab 19.0
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdUploadsAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdUploadsAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/uploads/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the file upload
     * This feature was introduced in GitLab 19.0
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdUploadsAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdUploadsAuthorizeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Workhorse authorize the file upload
     * This feature was introduced in GitLab 19.0
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdUploadsAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdUploadsAuthorizeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the file upload
     * This feature was introduced in GitLab 19.0
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdUploadsAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4GroupsIdUploadsAuthorizeRequestCreation(id);
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
     * Update group attributes
     * Updates the attributes for a specified group. You must be an administrator or have the Owner role for the group. The &#x60;projects&#x60; and &#x60;shared_projects&#x60; attributes in the response are deprecated and scheduled for removal in API v5. Each returns at most 100 projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name The name of the group
     * @param path The path of the group
     * @param sharedRunnersSetting Enable/disable shared runners for the group and its subgroups and projects. &#x60;enabled&#x60; enables instance runners for all projects and subgroups in the group. &#x60;disabled_and_overridable&#x60; disables them, but subgroups can override the setting. &#x60;disabled_and_unoverridable&#x60; disables them, and subgroups cannot override the setting.
     * @param description The description of the group
     * @param visibility The visibility of the group
     * @param avatar Avatar image for the group. On update, a blank value removes the avatar.
     * @param shareWithGroupLock Prevent sharing a project with another group within this group
     * @param requireTwoFactorAuthentication Require all users in this group to setup Two-factor authentication
     * @param twoFactorGracePeriod Time before Two-factor authentication is enforced, in hours.
     * @param projectCreationLevel Determine if developers can create projects in the group
     * @param autoDevopsEnabled Default to Auto DevOps pipeline for all projects within this group
     * @param subgroupCreationLevel Allowed to create subgroups
     * @param emailsDisabled Disable email notifications. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/127899) in GitLab 16.5. Use &#x60;emails_enabled&#x60; instead.
     * @param emailsEnabled Enable email notifications
     * @param showDiffPreviewInEmail Include the code diff preview in merge request notification emails
     * @param mentionsDisabled Disable a group from getting mentioned
     * @param lfsEnabled Enable/disable LFS for the projects in this group
     * @param requestAccessEnabled Allow users to request member access
     * @param defaultBranch The default branch of group&#39;s projects
     * @param defaultBranchProtection Determine if developers can push to default branch. &#x60;0&#x60;: no protection. Users with the Developer or Maintainer role can push new commits, force push, and delete the branch. &#x60;1&#x60;: partial protection. Users with the Developer or Maintainer role can push new commits. &#x60;2&#x60;: full protection. Only users with the Maintainer role can push new commits. &#x60;3&#x60;: protected against pushes. Users with the Maintainer role can push new commits, force push, and accept merge requests. Users with the Developer role can accept merge requests. &#x60;4&#x60;: full protection after initial push. Users with the Developer role can push commits to an empty repository. Users with the Maintainer role can push new commits and accept merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/408314) in GitLab 17.0. Use &#x60;default_branch_protection_defaults&#x60; instead.
     * @param defaultBranchProtectionDefaults The defaultBranchProtectionDefaults parameter
     * @param enabledGitAccessProtocol Allow only the selected protocols to be used for Git access.
     * @param crmEnabled Enable Customer Relations Management for this group
     * @param resourceAccessTokenNotifyInherited Send access token expiry notifications to all inherited members of the group
     * @param lockResourceAccessTokenNotifyInherited Prevent subgroups from overriding the access token expiry notification setting
     * @param membershipLock Prevent adding new members to projects within this group. Premium and Ultimate only.
     * @param ldapCn LDAP Common Name. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param ldapAccess A valid access level. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param sharedRunnersMinutesLimit (admin-only) Maximum number of monthly compute minutes for this group. &#x60;nil&#x60; (default) inherits the instance default, and &#x60;0&#x60; means unlimited. GitLab Self-Managed, Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this group. GitLab Self-Managed, Premium and Ultimate only.
     * @param wikiAccessLevel Wiki access level. One of &#x60;disabled&#x60;, &#x60;private&#x60; or &#x60;enabled&#x60;. Premium and Ultimate only.
     * @param duoAvailability Duo availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60;, &#x60;never_on&#x60; or &#x60;always_on&#x60;. In the UI, &#x60;never_on&#x60; is displayed as Always Off.
     * @param duoRemoteFlowsAvailability Enable GitLab Duo remote flows for this group
     * @param duoFoundationalFlowsAvailability Enable GitLab foundational Duo flows for this group
     * @param duoScheduledAutomationsAvailability Allow GitLab Duo flows in this group to run on a schedule
     * @param duoCodeSuggestionsAvailability Enable GitLab Duo Code Suggestions for this group
     * @param duoCustomAgentsAvailability Enable GitLab Duo custom agents for this group
     * @param duoCustomFlowsAvailability Enable GitLab Duo custom flows for this group
     * @param duoExternalAgentsAvailability Enable GitLab Duo external agents for this group
     * @param toolApprovalForSessionAvailability Tool approval for session availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param duoAutoModeAvailability Duo auto mode availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param amazonQAutoReviewEnabled Enable Amazon Q auto review for merge request
     * @param experimentFeaturesEnabled Enable experiment features for this group
     * @param modelPromptCacheEnabled Enable model prompt cache for this group
     * @param foundationalAgentsStatuses Whether each foundational agent has been enabled or disabled.
     * @param aiSettingsAttributes The aiSettingsAttributes parameter
     * @param preventSharingGroupsOutsideHierarchy Prevent sharing groups within this namespace with any groups outside the namespace. Only available on top-level groups.
     * @param stepUpAuthRequiredOauthProvider OAuth provider required for step-up authentication. Pass empty string to disable. Behind a feature flag named &#x60;omniauth_step_up_auth_for_namespace&#x60;, disabled by default. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/556943) in GitLab 18.4.
     * @param lockMathRenderingLimitsEnabled Indicates if math rendering limits are locked for all descendent groups.
     * @param mathRenderingLimitsEnabled Indicates if math rendering limits are used for this group.
     * @param requireShaForMerge Indicates whether merge requests in this group require a valid commit SHA for calls to the merge a merge request endpoint. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/256966) in GitLab 19.5.
     * @param lockRequireShaForMerge Enforce the require_sha_for_merge setting for all descendant groups. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/256966) in GitLab 19.5.
     * @param maxArtifactsSize Set the maximum file size, in MB, for each job&#39;s artifacts
     * @param fileTemplateProjectId The ID of a project to use for custom templates in this group. Premium and Ultimate only.
     * @param preventForkingOutsideGroup Prevent forking projects inside this group to external namespaces. Premium and Ultimate only.
     * @param uniqueProjectDownloadLimit Maximum number of unique projects a user can download in the specified time period before they are banned. Available only on top-level groups. Default is &#x60;0&#x60;. Maximum is &#x60;10000&#x60;. Ultimate only.
     * @param uniqueProjectDownloadLimitIntervalInSeconds Time period, in seconds, during which a user can download a maximum amount of projects before they are banned. Available only on top-level groups. Default is &#x60;0&#x60;. Maximum is &#x60;864000&#x60; (10 days). Ultimate only.
     * @param uniqueProjectDownloadLimitAllowlist List of usernames excluded from the unique project download limit. Available only on top-level groups. Maximum of 100 usernames. Ultimate only.
     * @param uniqueProjectDownloadLimitAlertlist List of user ids who will be emailed when Git abuse rate limit is exceeded. Available only on top-level groups. Maximum of 100 user IDs. When empty, the active Owners of the group are emailed. Ultimate only.
     * @param autoBanUserOnExcessiveProjectsDownload Ban users from the group when they exceed maximum number of unique projects download in the specified time period, as set by &#x60;unique_project_download_limit&#x60; and &#x60;unique_project_download_limit_interval_in_seconds&#x60;. Available only on top-level groups. Ultimate only.
     * @param ipRestrictionRanges Comma-separated list of IP addresses or subnet masks to restrict group access. Premium and Ultimate only.
     * @param allowedEmailDomainsList Comma-separated list of email address domains to allow group access. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/351494) in GitLab 17.4.
     * @param serviceAccessTokensExpirationEnforced To enforce token expiration for Service accounts users for group
     * @param duoCoreFeaturesEnabled [Experimental] Indicates whether GitLab Duo Core features are enabled for the group
     * @param duoFeaturesEnabled Indicates whether GitLab Duo features are enabled for the group. GitLab Self-Managed, Premium and Ultimate only.
     * @param lockDuoFeaturesEnabled Indicates if the GitLab Duo features enabled setting is enforced for all subgroups. GitLab Self-Managed, Premium and Ultimate only.
     * @param autoDuoCodeReviewEnabled Enable automatic reviews by GitLab Duo on merge requests
     * @param duoCodeReviewDecisionsEnabled Enable approval and change requests by GitLab Duo on merge requests
     * @param duoCodeReviewLevelAllowed [Beta] Allow Standard reviews by GitLab Duo Code Review Flow
     * @param duoCodeReviewDefaultLevel [Beta] Default review level for GitLab Duo Code Review Flow. One of &#x60;lite&#x60; or &#x60;standard&#x60;
     * @param aiAuditEventsStorageEnabled Indicates whether AI audit events are stored for the group. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/245843) in GitLab 19.2.
     * @param lockAiAuditEventsStorageEnabled Indicates whether AI audit events storage is enforced for all subgroups and projects. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/245843) in GitLab 19.2.
     * @param aiGovernanceEnabled Indicates whether AI Governance is turned on for the group, its subgroups, and its projects. Default is &#x60;false&#x60;. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/261994) in GitLab 19.6.
     * @param lockAiGovernanceEnabled Indicates whether the AI Governance setting is enforced for all subgroups and projects. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/261994) in GitLab 19.6.
     * @param webBasedCommitSigningEnabled Enable web based commit signing for commits created from the GitLab UI. When enabled for a group, applies to all projects in the group. GitLab.com only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/193928) in GitLab 18.2.
     * @param onlyAllowMergeIfPipelineSucceeds Only allow to merge if builds succeed. When enabled for a group, applies to all projects in the group. Premium and Ultimate only.
     * @param allowMergeOnSkippedPipeline Allow to merge if pipeline is skipped. Can be &#x60;true&#x60; only when &#x60;only_allow_merge_if_pipeline_succeeds&#x60; is &#x60;true&#x60;. Premium and Ultimate only.
     * @param onlyAllowMergeIfAllDiscussionsAreResolved Only allow to merge if all threads are resolved. When enabled for a group, applies to all projects in the group. Premium and Ultimate only.
     * @param enabledFoundationalFlows References of enabled foundational flows
     * @param duoTemplateProjectId The ID of a project to use as the Duo Code Review custom instructions template for this group
     * @param allowPersonalSnippets Allow creation of personal snippets for enterprise users of this group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/200575) in GitLab 18.5.
     * @param duoNamespaceAccessRules AI entity access rules for controlling Duo feature access
     * @param builtInProjectTemplatesEnabled Enable built-in project templates for project creation. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/235504) in GitLab 19.0.
     * @param lockBuiltInProjectTemplatesEnabled Enforce the built-in project templates setting for all subgroups. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/235504) in GitLab 19.0.
     * @param createCodeReviewFlowConsent When true, records explicit namespace consent for DAP-based Code Review routing
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String sharedRunnersSetting, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Boolean shareWithGroupLock, @jakarta.annotation.Nullable Boolean requireTwoFactorAuthentication, @jakarta.annotation.Nullable Integer twoFactorGracePeriod, @jakarta.annotation.Nullable String projectCreationLevel, @jakarta.annotation.Nullable Boolean autoDevopsEnabled, @jakarta.annotation.Nullable String subgroupCreationLevel, @jakarta.annotation.Nullable Boolean emailsDisabled, @jakarta.annotation.Nullable Boolean emailsEnabled, @jakarta.annotation.Nullable Boolean showDiffPreviewInEmail, @jakarta.annotation.Nullable Boolean mentionsDisabled, @jakarta.annotation.Nullable Boolean lfsEnabled, @jakarta.annotation.Nullable Boolean requestAccessEnabled, @jakarta.annotation.Nullable String defaultBranch, @jakarta.annotation.Nullable Integer defaultBranchProtection, @jakarta.annotation.Nullable PostApiV4GroupsRequestDefaultBranchProtectionDefaults defaultBranchProtectionDefaults, @jakarta.annotation.Nullable String enabledGitAccessProtocol, @jakarta.annotation.Nullable Boolean crmEnabled, @jakarta.annotation.Nullable Boolean resourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean lockResourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean membershipLock, @jakarta.annotation.Nullable String ldapCn, @jakarta.annotation.Nullable Integer ldapAccess, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable String wikiAccessLevel, @jakarta.annotation.Nullable String duoAvailability, @jakarta.annotation.Nullable Boolean duoRemoteFlowsAvailability, @jakarta.annotation.Nullable Boolean duoFoundationalFlowsAvailability, @jakarta.annotation.Nullable Boolean duoScheduledAutomationsAvailability, @jakarta.annotation.Nullable Boolean duoCodeSuggestionsAvailability, @jakarta.annotation.Nullable Boolean duoCustomAgentsAvailability, @jakarta.annotation.Nullable Boolean duoCustomFlowsAvailability, @jakarta.annotation.Nullable Boolean duoExternalAgentsAvailability, @jakarta.annotation.Nullable String toolApprovalForSessionAvailability, @jakarta.annotation.Nullable String duoAutoModeAvailability, @jakarta.annotation.Nullable Boolean amazonQAutoReviewEnabled, @jakarta.annotation.Nullable Boolean experimentFeaturesEnabled, @jakarta.annotation.Nullable Boolean modelPromptCacheEnabled, @jakarta.annotation.Nullable List<PostApiV4GroupsRequestFoundationalAgentsStatusesInner> foundationalAgentsStatuses, @jakarta.annotation.Nullable PostApiV4GroupsRequestAiSettingsAttributes aiSettingsAttributes, @jakarta.annotation.Nullable Boolean preventSharingGroupsOutsideHierarchy, @jakarta.annotation.Nullable String stepUpAuthRequiredOauthProvider, @jakarta.annotation.Nullable Boolean lockMathRenderingLimitsEnabled, @jakarta.annotation.Nullable Boolean mathRenderingLimitsEnabled, @jakarta.annotation.Nullable Boolean requireShaForMerge, @jakarta.annotation.Nullable Boolean lockRequireShaForMerge, @jakarta.annotation.Nullable Integer maxArtifactsSize, @jakarta.annotation.Nullable Integer fileTemplateProjectId, @jakarta.annotation.Nullable Boolean preventForkingOutsideGroup, @jakarta.annotation.Nullable Integer uniqueProjectDownloadLimit, @jakarta.annotation.Nullable Integer uniqueProjectDownloadLimitIntervalInSeconds, @jakarta.annotation.Nullable List<String> uniqueProjectDownloadLimitAllowlist, @jakarta.annotation.Nullable List<Integer> uniqueProjectDownloadLimitAlertlist, @jakarta.annotation.Nullable Boolean autoBanUserOnExcessiveProjectsDownload, @jakarta.annotation.Nullable String ipRestrictionRanges, @jakarta.annotation.Nullable String allowedEmailDomainsList, @jakarta.annotation.Nullable Boolean serviceAccessTokensExpirationEnforced, @jakarta.annotation.Nullable Boolean duoCoreFeaturesEnabled, @jakarta.annotation.Nullable Boolean duoFeaturesEnabled, @jakarta.annotation.Nullable Boolean lockDuoFeaturesEnabled, @jakarta.annotation.Nullable Boolean autoDuoCodeReviewEnabled, @jakarta.annotation.Nullable Boolean duoCodeReviewDecisionsEnabled, @jakarta.annotation.Nullable Boolean duoCodeReviewLevelAllowed, @jakarta.annotation.Nullable String duoCodeReviewDefaultLevel, @jakarta.annotation.Nullable Boolean aiAuditEventsStorageEnabled, @jakarta.annotation.Nullable Boolean lockAiAuditEventsStorageEnabled, @jakarta.annotation.Nullable Boolean aiGovernanceEnabled, @jakarta.annotation.Nullable Boolean lockAiGovernanceEnabled, @jakarta.annotation.Nullable Boolean webBasedCommitSigningEnabled, @jakarta.annotation.Nullable Boolean onlyAllowMergeIfPipelineSucceeds, @jakarta.annotation.Nullable Boolean allowMergeOnSkippedPipeline, @jakarta.annotation.Nullable Boolean onlyAllowMergeIfAllDiscussionsAreResolved, @jakarta.annotation.Nullable List<String> enabledFoundationalFlows, @jakarta.annotation.Nullable Integer duoTemplateProjectId, @jakarta.annotation.Nullable Boolean allowPersonalSnippets, @jakarta.annotation.Nullable List<PutApiV4GroupsIdRequestDuoNamespaceAccessRulesInner> duoNamespaceAccessRules, @jakarta.annotation.Nullable Boolean builtInProjectTemplatesEnabled, @jakarta.annotation.Nullable Boolean lockBuiltInProjectTemplatesEnabled, @jakarta.annotation.Nullable Boolean createCodeReviewFlowConsent) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (name != null) {
            formParams.add("name", name);
        }
        if (path != null) {
            formParams.add("path", path);
        }
        if (sharedRunnersSetting != null) {
            formParams.add("shared_runners_setting", sharedRunnersSetting);
        }
        if (description != null) {
            formParams.add("description", description);
        }
        if (visibility != null) {
            formParams.add("visibility", visibility);
        }
        if (avatar != null) {
            formParams.add("avatar", new FileSystemResource(avatar));
        }
        if (shareWithGroupLock != null) {
            formParams.add("share_with_group_lock", shareWithGroupLock);
        }
        if (requireTwoFactorAuthentication != null) {
            formParams.add("require_two_factor_authentication", requireTwoFactorAuthentication);
        }
        if (twoFactorGracePeriod != null) {
            formParams.add("two_factor_grace_period", twoFactorGracePeriod);
        }
        if (projectCreationLevel != null) {
            formParams.add("project_creation_level", projectCreationLevel);
        }
        if (autoDevopsEnabled != null) {
            formParams.add("auto_devops_enabled", autoDevopsEnabled);
        }
        if (subgroupCreationLevel != null) {
            formParams.add("subgroup_creation_level", subgroupCreationLevel);
        }
        if (emailsDisabled != null) {
            formParams.add("emails_disabled", emailsDisabled);
        }
        if (emailsEnabled != null) {
            formParams.add("emails_enabled", emailsEnabled);
        }
        if (showDiffPreviewInEmail != null) {
            formParams.add("show_diff_preview_in_email", showDiffPreviewInEmail);
        }
        if (mentionsDisabled != null) {
            formParams.add("mentions_disabled", mentionsDisabled);
        }
        if (lfsEnabled != null) {
            formParams.add("lfs_enabled", lfsEnabled);
        }
        if (requestAccessEnabled != null) {
            formParams.add("request_access_enabled", requestAccessEnabled);
        }
        if (defaultBranch != null) {
            formParams.add("default_branch", defaultBranch);
        }
        if (defaultBranchProtection != null) {
            formParams.add("default_branch_protection", defaultBranchProtection);
        }
        if (defaultBranchProtectionDefaults != null) {
            formParams.add("default_branch_protection_defaults", defaultBranchProtectionDefaults);
        }
        if (enabledGitAccessProtocol != null) {
            formParams.add("enabled_git_access_protocol", enabledGitAccessProtocol);
        }
        if (crmEnabled != null) {
            formParams.add("crm_enabled", crmEnabled);
        }
        if (resourceAccessTokenNotifyInherited != null) {
            formParams.add("resource_access_token_notify_inherited", resourceAccessTokenNotifyInherited);
        }
        if (lockResourceAccessTokenNotifyInherited != null) {
            formParams.add("lock_resource_access_token_notify_inherited", lockResourceAccessTokenNotifyInherited);
        }
        if (membershipLock != null) {
            formParams.add("membership_lock", membershipLock);
        }
        if (ldapCn != null) {
            formParams.add("ldap_cn", ldapCn);
        }
        if (ldapAccess != null) {
            formParams.add("ldap_access", ldapAccess);
        }
        if (sharedRunnersMinutesLimit != null) {
            formParams.add("shared_runners_minutes_limit", sharedRunnersMinutesLimit);
        }
        if (extraSharedRunnersMinutesLimit != null) {
            formParams.add("extra_shared_runners_minutes_limit", extraSharedRunnersMinutesLimit);
        }
        if (wikiAccessLevel != null) {
            formParams.add("wiki_access_level", wikiAccessLevel);
        }
        if (duoAvailability != null) {
            formParams.add("duo_availability", duoAvailability);
        }
        if (duoRemoteFlowsAvailability != null) {
            formParams.add("duo_remote_flows_availability", duoRemoteFlowsAvailability);
        }
        if (duoFoundationalFlowsAvailability != null) {
            formParams.add("duo_foundational_flows_availability", duoFoundationalFlowsAvailability);
        }
        if (duoScheduledAutomationsAvailability != null) {
            formParams.add("duo_scheduled_automations_availability", duoScheduledAutomationsAvailability);
        }
        if (duoCodeSuggestionsAvailability != null) {
            formParams.add("duo_code_suggestions_availability", duoCodeSuggestionsAvailability);
        }
        if (duoCustomAgentsAvailability != null) {
            formParams.add("duo_custom_agents_availability", duoCustomAgentsAvailability);
        }
        if (duoCustomFlowsAvailability != null) {
            formParams.add("duo_custom_flows_availability", duoCustomFlowsAvailability);
        }
        if (duoExternalAgentsAvailability != null) {
            formParams.add("duo_external_agents_availability", duoExternalAgentsAvailability);
        }
        if (toolApprovalForSessionAvailability != null) {
            formParams.add("tool_approval_for_session_availability", toolApprovalForSessionAvailability);
        }
        if (duoAutoModeAvailability != null) {
            formParams.add("duo_auto_mode_availability", duoAutoModeAvailability);
        }
        if (amazonQAutoReviewEnabled != null) {
            formParams.add("amazon_q_auto_review_enabled", amazonQAutoReviewEnabled);
        }
        if (experimentFeaturesEnabled != null) {
            formParams.add("experiment_features_enabled", experimentFeaturesEnabled);
        }
        if (modelPromptCacheEnabled != null) {
            formParams.add("model_prompt_cache_enabled", modelPromptCacheEnabled);
        }
        if (foundationalAgentsStatuses != null) {
            formParams.addAll("foundational_agents_statuses", foundationalAgentsStatuses);
        }
        if (aiSettingsAttributes != null) {
            formParams.add("ai_settings_attributes", aiSettingsAttributes);
        }
        if (preventSharingGroupsOutsideHierarchy != null) {
            formParams.add("prevent_sharing_groups_outside_hierarchy", preventSharingGroupsOutsideHierarchy);
        }
        if (stepUpAuthRequiredOauthProvider != null) {
            formParams.add("step_up_auth_required_oauth_provider", stepUpAuthRequiredOauthProvider);
        }
        if (lockMathRenderingLimitsEnabled != null) {
            formParams.add("lock_math_rendering_limits_enabled", lockMathRenderingLimitsEnabled);
        }
        if (mathRenderingLimitsEnabled != null) {
            formParams.add("math_rendering_limits_enabled", mathRenderingLimitsEnabled);
        }
        if (requireShaForMerge != null) {
            formParams.add("require_sha_for_merge", requireShaForMerge);
        }
        if (lockRequireShaForMerge != null) {
            formParams.add("lock_require_sha_for_merge", lockRequireShaForMerge);
        }
        if (maxArtifactsSize != null) {
            formParams.add("max_artifacts_size", maxArtifactsSize);
        }
        if (fileTemplateProjectId != null) {
            formParams.add("file_template_project_id", fileTemplateProjectId);
        }
        if (preventForkingOutsideGroup != null) {
            formParams.add("prevent_forking_outside_group", preventForkingOutsideGroup);
        }
        if (uniqueProjectDownloadLimit != null) {
            formParams.add("unique_project_download_limit", uniqueProjectDownloadLimit);
        }
        if (uniqueProjectDownloadLimitIntervalInSeconds != null) {
            formParams.add("unique_project_download_limit_interval_in_seconds", uniqueProjectDownloadLimitIntervalInSeconds);
        }
        if (uniqueProjectDownloadLimitAllowlist != null) {
            formParams.addAll("unique_project_download_limit_allowlist", uniqueProjectDownloadLimitAllowlist);
        }
        if (uniqueProjectDownloadLimitAlertlist != null) {
            formParams.addAll("unique_project_download_limit_alertlist", uniqueProjectDownloadLimitAlertlist);
        }
        if (autoBanUserOnExcessiveProjectsDownload != null) {
            formParams.add("auto_ban_user_on_excessive_projects_download", autoBanUserOnExcessiveProjectsDownload);
        }
        if (ipRestrictionRanges != null) {
            formParams.add("ip_restriction_ranges", ipRestrictionRanges);
        }
        if (allowedEmailDomainsList != null) {
            formParams.add("allowed_email_domains_list", allowedEmailDomainsList);
        }
        if (serviceAccessTokensExpirationEnforced != null) {
            formParams.add("service_access_tokens_expiration_enforced", serviceAccessTokensExpirationEnforced);
        }
        if (duoCoreFeaturesEnabled != null) {
            formParams.add("duo_core_features_enabled", duoCoreFeaturesEnabled);
        }
        if (duoFeaturesEnabled != null) {
            formParams.add("duo_features_enabled", duoFeaturesEnabled);
        }
        if (lockDuoFeaturesEnabled != null) {
            formParams.add("lock_duo_features_enabled", lockDuoFeaturesEnabled);
        }
        if (autoDuoCodeReviewEnabled != null) {
            formParams.add("auto_duo_code_review_enabled", autoDuoCodeReviewEnabled);
        }
        if (duoCodeReviewDecisionsEnabled != null) {
            formParams.add("duo_code_review_decisions_enabled", duoCodeReviewDecisionsEnabled);
        }
        if (duoCodeReviewLevelAllowed != null) {
            formParams.add("duo_code_review_level_allowed", duoCodeReviewLevelAllowed);
        }
        if (duoCodeReviewDefaultLevel != null) {
            formParams.add("duo_code_review_default_level", duoCodeReviewDefaultLevel);
        }
        if (aiAuditEventsStorageEnabled != null) {
            formParams.add("ai_audit_events_storage_enabled", aiAuditEventsStorageEnabled);
        }
        if (lockAiAuditEventsStorageEnabled != null) {
            formParams.add("lock_ai_audit_events_storage_enabled", lockAiAuditEventsStorageEnabled);
        }
        if (aiGovernanceEnabled != null) {
            formParams.add("ai_governance_enabled", aiGovernanceEnabled);
        }
        if (lockAiGovernanceEnabled != null) {
            formParams.add("lock_ai_governance_enabled", lockAiGovernanceEnabled);
        }
        if (webBasedCommitSigningEnabled != null) {
            formParams.add("web_based_commit_signing_enabled", webBasedCommitSigningEnabled);
        }
        if (onlyAllowMergeIfPipelineSucceeds != null) {
            formParams.add("only_allow_merge_if_pipeline_succeeds", onlyAllowMergeIfPipelineSucceeds);
        }
        if (allowMergeOnSkippedPipeline != null) {
            formParams.add("allow_merge_on_skipped_pipeline", allowMergeOnSkippedPipeline);
        }
        if (onlyAllowMergeIfAllDiscussionsAreResolved != null) {
            formParams.add("only_allow_merge_if_all_discussions_are_resolved", onlyAllowMergeIfAllDiscussionsAreResolved);
        }
        if (enabledFoundationalFlows != null) {
            formParams.addAll("enabled_foundational_flows", enabledFoundationalFlows);
        }
        if (duoTemplateProjectId != null) {
            formParams.add("duo_template_project_id", duoTemplateProjectId);
        }
        if (allowPersonalSnippets != null) {
            formParams.add("allow_personal_snippets", allowPersonalSnippets);
        }
        if (duoNamespaceAccessRules != null) {
            formParams.addAll("duo_namespace_access_rules", duoNamespaceAccessRules);
        }
        if (builtInProjectTemplatesEnabled != null) {
            formParams.add("built_in_project_templates_enabled", builtInProjectTemplatesEnabled);
        }
        if (lockBuiltInProjectTemplatesEnabled != null) {
            formParams.add("lock_built_in_project_templates_enabled", lockBuiltInProjectTemplatesEnabled);
        }
        if (createCodeReviewFlowConsent != null) {
            formParams.add("create_code_review_flow_consent", createCodeReviewFlowConsent);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update group attributes
     * Updates the attributes for a specified group. You must be an administrator or have the Owner role for the group. The &#x60;projects&#x60; and &#x60;shared_projects&#x60; attributes in the response are deprecated and scheduled for removal in API v5. Each returns at most 100 projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name The name of the group
     * @param path The path of the group
     * @param sharedRunnersSetting Enable/disable shared runners for the group and its subgroups and projects. &#x60;enabled&#x60; enables instance runners for all projects and subgroups in the group. &#x60;disabled_and_overridable&#x60; disables them, but subgroups can override the setting. &#x60;disabled_and_unoverridable&#x60; disables them, and subgroups cannot override the setting.
     * @param description The description of the group
     * @param visibility The visibility of the group
     * @param avatar Avatar image for the group. On update, a blank value removes the avatar.
     * @param shareWithGroupLock Prevent sharing a project with another group within this group
     * @param requireTwoFactorAuthentication Require all users in this group to setup Two-factor authentication
     * @param twoFactorGracePeriod Time before Two-factor authentication is enforced, in hours.
     * @param projectCreationLevel Determine if developers can create projects in the group
     * @param autoDevopsEnabled Default to Auto DevOps pipeline for all projects within this group
     * @param subgroupCreationLevel Allowed to create subgroups
     * @param emailsDisabled Disable email notifications. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/127899) in GitLab 16.5. Use &#x60;emails_enabled&#x60; instead.
     * @param emailsEnabled Enable email notifications
     * @param showDiffPreviewInEmail Include the code diff preview in merge request notification emails
     * @param mentionsDisabled Disable a group from getting mentioned
     * @param lfsEnabled Enable/disable LFS for the projects in this group
     * @param requestAccessEnabled Allow users to request member access
     * @param defaultBranch The default branch of group&#39;s projects
     * @param defaultBranchProtection Determine if developers can push to default branch. &#x60;0&#x60;: no protection. Users with the Developer or Maintainer role can push new commits, force push, and delete the branch. &#x60;1&#x60;: partial protection. Users with the Developer or Maintainer role can push new commits. &#x60;2&#x60;: full protection. Only users with the Maintainer role can push new commits. &#x60;3&#x60;: protected against pushes. Users with the Maintainer role can push new commits, force push, and accept merge requests. Users with the Developer role can accept merge requests. &#x60;4&#x60;: full protection after initial push. Users with the Developer role can push commits to an empty repository. Users with the Maintainer role can push new commits and accept merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/408314) in GitLab 17.0. Use &#x60;default_branch_protection_defaults&#x60; instead.
     * @param defaultBranchProtectionDefaults The defaultBranchProtectionDefaults parameter
     * @param enabledGitAccessProtocol Allow only the selected protocols to be used for Git access.
     * @param crmEnabled Enable Customer Relations Management for this group
     * @param resourceAccessTokenNotifyInherited Send access token expiry notifications to all inherited members of the group
     * @param lockResourceAccessTokenNotifyInherited Prevent subgroups from overriding the access token expiry notification setting
     * @param membershipLock Prevent adding new members to projects within this group. Premium and Ultimate only.
     * @param ldapCn LDAP Common Name. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param ldapAccess A valid access level. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param sharedRunnersMinutesLimit (admin-only) Maximum number of monthly compute minutes for this group. &#x60;nil&#x60; (default) inherits the instance default, and &#x60;0&#x60; means unlimited. GitLab Self-Managed, Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this group. GitLab Self-Managed, Premium and Ultimate only.
     * @param wikiAccessLevel Wiki access level. One of &#x60;disabled&#x60;, &#x60;private&#x60; or &#x60;enabled&#x60;. Premium and Ultimate only.
     * @param duoAvailability Duo availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60;, &#x60;never_on&#x60; or &#x60;always_on&#x60;. In the UI, &#x60;never_on&#x60; is displayed as Always Off.
     * @param duoRemoteFlowsAvailability Enable GitLab Duo remote flows for this group
     * @param duoFoundationalFlowsAvailability Enable GitLab foundational Duo flows for this group
     * @param duoScheduledAutomationsAvailability Allow GitLab Duo flows in this group to run on a schedule
     * @param duoCodeSuggestionsAvailability Enable GitLab Duo Code Suggestions for this group
     * @param duoCustomAgentsAvailability Enable GitLab Duo custom agents for this group
     * @param duoCustomFlowsAvailability Enable GitLab Duo custom flows for this group
     * @param duoExternalAgentsAvailability Enable GitLab Duo external agents for this group
     * @param toolApprovalForSessionAvailability Tool approval for session availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param duoAutoModeAvailability Duo auto mode availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param amazonQAutoReviewEnabled Enable Amazon Q auto review for merge request
     * @param experimentFeaturesEnabled Enable experiment features for this group
     * @param modelPromptCacheEnabled Enable model prompt cache for this group
     * @param foundationalAgentsStatuses Whether each foundational agent has been enabled or disabled.
     * @param aiSettingsAttributes The aiSettingsAttributes parameter
     * @param preventSharingGroupsOutsideHierarchy Prevent sharing groups within this namespace with any groups outside the namespace. Only available on top-level groups.
     * @param stepUpAuthRequiredOauthProvider OAuth provider required for step-up authentication. Pass empty string to disable. Behind a feature flag named &#x60;omniauth_step_up_auth_for_namespace&#x60;, disabled by default. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/556943) in GitLab 18.4.
     * @param lockMathRenderingLimitsEnabled Indicates if math rendering limits are locked for all descendent groups.
     * @param mathRenderingLimitsEnabled Indicates if math rendering limits are used for this group.
     * @param requireShaForMerge Indicates whether merge requests in this group require a valid commit SHA for calls to the merge a merge request endpoint. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/256966) in GitLab 19.5.
     * @param lockRequireShaForMerge Enforce the require_sha_for_merge setting for all descendant groups. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/256966) in GitLab 19.5.
     * @param maxArtifactsSize Set the maximum file size, in MB, for each job&#39;s artifacts
     * @param fileTemplateProjectId The ID of a project to use for custom templates in this group. Premium and Ultimate only.
     * @param preventForkingOutsideGroup Prevent forking projects inside this group to external namespaces. Premium and Ultimate only.
     * @param uniqueProjectDownloadLimit Maximum number of unique projects a user can download in the specified time period before they are banned. Available only on top-level groups. Default is &#x60;0&#x60;. Maximum is &#x60;10000&#x60;. Ultimate only.
     * @param uniqueProjectDownloadLimitIntervalInSeconds Time period, in seconds, during which a user can download a maximum amount of projects before they are banned. Available only on top-level groups. Default is &#x60;0&#x60;. Maximum is &#x60;864000&#x60; (10 days). Ultimate only.
     * @param uniqueProjectDownloadLimitAllowlist List of usernames excluded from the unique project download limit. Available only on top-level groups. Maximum of 100 usernames. Ultimate only.
     * @param uniqueProjectDownloadLimitAlertlist List of user ids who will be emailed when Git abuse rate limit is exceeded. Available only on top-level groups. Maximum of 100 user IDs. When empty, the active Owners of the group are emailed. Ultimate only.
     * @param autoBanUserOnExcessiveProjectsDownload Ban users from the group when they exceed maximum number of unique projects download in the specified time period, as set by &#x60;unique_project_download_limit&#x60; and &#x60;unique_project_download_limit_interval_in_seconds&#x60;. Available only on top-level groups. Ultimate only.
     * @param ipRestrictionRanges Comma-separated list of IP addresses or subnet masks to restrict group access. Premium and Ultimate only.
     * @param allowedEmailDomainsList Comma-separated list of email address domains to allow group access. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/351494) in GitLab 17.4.
     * @param serviceAccessTokensExpirationEnforced To enforce token expiration for Service accounts users for group
     * @param duoCoreFeaturesEnabled [Experimental] Indicates whether GitLab Duo Core features are enabled for the group
     * @param duoFeaturesEnabled Indicates whether GitLab Duo features are enabled for the group. GitLab Self-Managed, Premium and Ultimate only.
     * @param lockDuoFeaturesEnabled Indicates if the GitLab Duo features enabled setting is enforced for all subgroups. GitLab Self-Managed, Premium and Ultimate only.
     * @param autoDuoCodeReviewEnabled Enable automatic reviews by GitLab Duo on merge requests
     * @param duoCodeReviewDecisionsEnabled Enable approval and change requests by GitLab Duo on merge requests
     * @param duoCodeReviewLevelAllowed [Beta] Allow Standard reviews by GitLab Duo Code Review Flow
     * @param duoCodeReviewDefaultLevel [Beta] Default review level for GitLab Duo Code Review Flow. One of &#x60;lite&#x60; or &#x60;standard&#x60;
     * @param aiAuditEventsStorageEnabled Indicates whether AI audit events are stored for the group. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/245843) in GitLab 19.2.
     * @param lockAiAuditEventsStorageEnabled Indicates whether AI audit events storage is enforced for all subgroups and projects. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/245843) in GitLab 19.2.
     * @param aiGovernanceEnabled Indicates whether AI Governance is turned on for the group, its subgroups, and its projects. Default is &#x60;false&#x60;. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/261994) in GitLab 19.6.
     * @param lockAiGovernanceEnabled Indicates whether the AI Governance setting is enforced for all subgroups and projects. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/261994) in GitLab 19.6.
     * @param webBasedCommitSigningEnabled Enable web based commit signing for commits created from the GitLab UI. When enabled for a group, applies to all projects in the group. GitLab.com only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/193928) in GitLab 18.2.
     * @param onlyAllowMergeIfPipelineSucceeds Only allow to merge if builds succeed. When enabled for a group, applies to all projects in the group. Premium and Ultimate only.
     * @param allowMergeOnSkippedPipeline Allow to merge if pipeline is skipped. Can be &#x60;true&#x60; only when &#x60;only_allow_merge_if_pipeline_succeeds&#x60; is &#x60;true&#x60;. Premium and Ultimate only.
     * @param onlyAllowMergeIfAllDiscussionsAreResolved Only allow to merge if all threads are resolved. When enabled for a group, applies to all projects in the group. Premium and Ultimate only.
     * @param enabledFoundationalFlows References of enabled foundational flows
     * @param duoTemplateProjectId The ID of a project to use as the Duo Code Review custom instructions template for this group
     * @param allowPersonalSnippets Allow creation of personal snippets for enterprise users of this group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/200575) in GitLab 18.5.
     * @param duoNamespaceAccessRules AI entity access rules for controlling Duo feature access
     * @param builtInProjectTemplatesEnabled Enable built-in project templates for project creation. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/235504) in GitLab 19.0.
     * @param lockBuiltInProjectTemplatesEnabled Enforce the built-in project templates setting for all subgroups. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/235504) in GitLab 19.0.
     * @param createCodeReviewFlowConsent When true, records explicit namespace consent for DAP-based Code Review routing
     * @return APIEntitiesGroupDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupDetail putApiV4GroupsId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String sharedRunnersSetting, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Boolean shareWithGroupLock, @jakarta.annotation.Nullable Boolean requireTwoFactorAuthentication, @jakarta.annotation.Nullable Integer twoFactorGracePeriod, @jakarta.annotation.Nullable String projectCreationLevel, @jakarta.annotation.Nullable Boolean autoDevopsEnabled, @jakarta.annotation.Nullable String subgroupCreationLevel, @jakarta.annotation.Nullable Boolean emailsDisabled, @jakarta.annotation.Nullable Boolean emailsEnabled, @jakarta.annotation.Nullable Boolean showDiffPreviewInEmail, @jakarta.annotation.Nullable Boolean mentionsDisabled, @jakarta.annotation.Nullable Boolean lfsEnabled, @jakarta.annotation.Nullable Boolean requestAccessEnabled, @jakarta.annotation.Nullable String defaultBranch, @jakarta.annotation.Nullable Integer defaultBranchProtection, @jakarta.annotation.Nullable PostApiV4GroupsRequestDefaultBranchProtectionDefaults defaultBranchProtectionDefaults, @jakarta.annotation.Nullable String enabledGitAccessProtocol, @jakarta.annotation.Nullable Boolean crmEnabled, @jakarta.annotation.Nullable Boolean resourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean lockResourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean membershipLock, @jakarta.annotation.Nullable String ldapCn, @jakarta.annotation.Nullable Integer ldapAccess, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable String wikiAccessLevel, @jakarta.annotation.Nullable String duoAvailability, @jakarta.annotation.Nullable Boolean duoRemoteFlowsAvailability, @jakarta.annotation.Nullable Boolean duoFoundationalFlowsAvailability, @jakarta.annotation.Nullable Boolean duoScheduledAutomationsAvailability, @jakarta.annotation.Nullable Boolean duoCodeSuggestionsAvailability, @jakarta.annotation.Nullable Boolean duoCustomAgentsAvailability, @jakarta.annotation.Nullable Boolean duoCustomFlowsAvailability, @jakarta.annotation.Nullable Boolean duoExternalAgentsAvailability, @jakarta.annotation.Nullable String toolApprovalForSessionAvailability, @jakarta.annotation.Nullable String duoAutoModeAvailability, @jakarta.annotation.Nullable Boolean amazonQAutoReviewEnabled, @jakarta.annotation.Nullable Boolean experimentFeaturesEnabled, @jakarta.annotation.Nullable Boolean modelPromptCacheEnabled, @jakarta.annotation.Nullable List<PostApiV4GroupsRequestFoundationalAgentsStatusesInner> foundationalAgentsStatuses, @jakarta.annotation.Nullable PostApiV4GroupsRequestAiSettingsAttributes aiSettingsAttributes, @jakarta.annotation.Nullable Boolean preventSharingGroupsOutsideHierarchy, @jakarta.annotation.Nullable String stepUpAuthRequiredOauthProvider, @jakarta.annotation.Nullable Boolean lockMathRenderingLimitsEnabled, @jakarta.annotation.Nullable Boolean mathRenderingLimitsEnabled, @jakarta.annotation.Nullable Boolean requireShaForMerge, @jakarta.annotation.Nullable Boolean lockRequireShaForMerge, @jakarta.annotation.Nullable Integer maxArtifactsSize, @jakarta.annotation.Nullable Integer fileTemplateProjectId, @jakarta.annotation.Nullable Boolean preventForkingOutsideGroup, @jakarta.annotation.Nullable Integer uniqueProjectDownloadLimit, @jakarta.annotation.Nullable Integer uniqueProjectDownloadLimitIntervalInSeconds, @jakarta.annotation.Nullable List<String> uniqueProjectDownloadLimitAllowlist, @jakarta.annotation.Nullable List<Integer> uniqueProjectDownloadLimitAlertlist, @jakarta.annotation.Nullable Boolean autoBanUserOnExcessiveProjectsDownload, @jakarta.annotation.Nullable String ipRestrictionRanges, @jakarta.annotation.Nullable String allowedEmailDomainsList, @jakarta.annotation.Nullable Boolean serviceAccessTokensExpirationEnforced, @jakarta.annotation.Nullable Boolean duoCoreFeaturesEnabled, @jakarta.annotation.Nullable Boolean duoFeaturesEnabled, @jakarta.annotation.Nullable Boolean lockDuoFeaturesEnabled, @jakarta.annotation.Nullable Boolean autoDuoCodeReviewEnabled, @jakarta.annotation.Nullable Boolean duoCodeReviewDecisionsEnabled, @jakarta.annotation.Nullable Boolean duoCodeReviewLevelAllowed, @jakarta.annotation.Nullable String duoCodeReviewDefaultLevel, @jakarta.annotation.Nullable Boolean aiAuditEventsStorageEnabled, @jakarta.annotation.Nullable Boolean lockAiAuditEventsStorageEnabled, @jakarta.annotation.Nullable Boolean aiGovernanceEnabled, @jakarta.annotation.Nullable Boolean lockAiGovernanceEnabled, @jakarta.annotation.Nullable Boolean webBasedCommitSigningEnabled, @jakarta.annotation.Nullable Boolean onlyAllowMergeIfPipelineSucceeds, @jakarta.annotation.Nullable Boolean allowMergeOnSkippedPipeline, @jakarta.annotation.Nullable Boolean onlyAllowMergeIfAllDiscussionsAreResolved, @jakarta.annotation.Nullable List<String> enabledFoundationalFlows, @jakarta.annotation.Nullable Integer duoTemplateProjectId, @jakarta.annotation.Nullable Boolean allowPersonalSnippets, @jakarta.annotation.Nullable List<PutApiV4GroupsIdRequestDuoNamespaceAccessRulesInner> duoNamespaceAccessRules, @jakarta.annotation.Nullable Boolean builtInProjectTemplatesEnabled, @jakarta.annotation.Nullable Boolean lockBuiltInProjectTemplatesEnabled, @jakarta.annotation.Nullable Boolean createCodeReviewFlowConsent) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdRequestCreation(id, name, path, sharedRunnersSetting, description, visibility, avatar, shareWithGroupLock, requireTwoFactorAuthentication, twoFactorGracePeriod, projectCreationLevel, autoDevopsEnabled, subgroupCreationLevel, emailsDisabled, emailsEnabled, showDiffPreviewInEmail, mentionsDisabled, lfsEnabled, requestAccessEnabled, defaultBranch, defaultBranchProtection, defaultBranchProtectionDefaults, enabledGitAccessProtocol, crmEnabled, resourceAccessTokenNotifyInherited, lockResourceAccessTokenNotifyInherited, membershipLock, ldapCn, ldapAccess, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, wikiAccessLevel, duoAvailability, duoRemoteFlowsAvailability, duoFoundationalFlowsAvailability, duoScheduledAutomationsAvailability, duoCodeSuggestionsAvailability, duoCustomAgentsAvailability, duoCustomFlowsAvailability, duoExternalAgentsAvailability, toolApprovalForSessionAvailability, duoAutoModeAvailability, amazonQAutoReviewEnabled, experimentFeaturesEnabled, modelPromptCacheEnabled, foundationalAgentsStatuses, aiSettingsAttributes, preventSharingGroupsOutsideHierarchy, stepUpAuthRequiredOauthProvider, lockMathRenderingLimitsEnabled, mathRenderingLimitsEnabled, requireShaForMerge, lockRequireShaForMerge, maxArtifactsSize, fileTemplateProjectId, preventForkingOutsideGroup, uniqueProjectDownloadLimit, uniqueProjectDownloadLimitIntervalInSeconds, uniqueProjectDownloadLimitAllowlist, uniqueProjectDownloadLimitAlertlist, autoBanUserOnExcessiveProjectsDownload, ipRestrictionRanges, allowedEmailDomainsList, serviceAccessTokensExpirationEnforced, duoCoreFeaturesEnabled, duoFeaturesEnabled, lockDuoFeaturesEnabled, autoDuoCodeReviewEnabled, duoCodeReviewDecisionsEnabled, duoCodeReviewLevelAllowed, duoCodeReviewDefaultLevel, aiAuditEventsStorageEnabled, lockAiAuditEventsStorageEnabled, aiGovernanceEnabled, lockAiGovernanceEnabled, webBasedCommitSigningEnabled, onlyAllowMergeIfPipelineSucceeds, allowMergeOnSkippedPipeline, onlyAllowMergeIfAllDiscussionsAreResolved, enabledFoundationalFlows, duoTemplateProjectId, allowPersonalSnippets, duoNamespaceAccessRules, builtInProjectTemplatesEnabled, lockBuiltInProjectTemplatesEnabled, createCodeReviewFlowConsent).body(localVarReturnType);
    }

    /**
     * Update group attributes
     * Updates the attributes for a specified group. You must be an administrator or have the Owner role for the group. The &#x60;projects&#x60; and &#x60;shared_projects&#x60; attributes in the response are deprecated and scheduled for removal in API v5. Each returns at most 100 projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name The name of the group
     * @param path The path of the group
     * @param sharedRunnersSetting Enable/disable shared runners for the group and its subgroups and projects. &#x60;enabled&#x60; enables instance runners for all projects and subgroups in the group. &#x60;disabled_and_overridable&#x60; disables them, but subgroups can override the setting. &#x60;disabled_and_unoverridable&#x60; disables them, and subgroups cannot override the setting.
     * @param description The description of the group
     * @param visibility The visibility of the group
     * @param avatar Avatar image for the group. On update, a blank value removes the avatar.
     * @param shareWithGroupLock Prevent sharing a project with another group within this group
     * @param requireTwoFactorAuthentication Require all users in this group to setup Two-factor authentication
     * @param twoFactorGracePeriod Time before Two-factor authentication is enforced, in hours.
     * @param projectCreationLevel Determine if developers can create projects in the group
     * @param autoDevopsEnabled Default to Auto DevOps pipeline for all projects within this group
     * @param subgroupCreationLevel Allowed to create subgroups
     * @param emailsDisabled Disable email notifications. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/127899) in GitLab 16.5. Use &#x60;emails_enabled&#x60; instead.
     * @param emailsEnabled Enable email notifications
     * @param showDiffPreviewInEmail Include the code diff preview in merge request notification emails
     * @param mentionsDisabled Disable a group from getting mentioned
     * @param lfsEnabled Enable/disable LFS for the projects in this group
     * @param requestAccessEnabled Allow users to request member access
     * @param defaultBranch The default branch of group&#39;s projects
     * @param defaultBranchProtection Determine if developers can push to default branch. &#x60;0&#x60;: no protection. Users with the Developer or Maintainer role can push new commits, force push, and delete the branch. &#x60;1&#x60;: partial protection. Users with the Developer or Maintainer role can push new commits. &#x60;2&#x60;: full protection. Only users with the Maintainer role can push new commits. &#x60;3&#x60;: protected against pushes. Users with the Maintainer role can push new commits, force push, and accept merge requests. Users with the Developer role can accept merge requests. &#x60;4&#x60;: full protection after initial push. Users with the Developer role can push commits to an empty repository. Users with the Maintainer role can push new commits and accept merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/408314) in GitLab 17.0. Use &#x60;default_branch_protection_defaults&#x60; instead.
     * @param defaultBranchProtectionDefaults The defaultBranchProtectionDefaults parameter
     * @param enabledGitAccessProtocol Allow only the selected protocols to be used for Git access.
     * @param crmEnabled Enable Customer Relations Management for this group
     * @param resourceAccessTokenNotifyInherited Send access token expiry notifications to all inherited members of the group
     * @param lockResourceAccessTokenNotifyInherited Prevent subgroups from overriding the access token expiry notification setting
     * @param membershipLock Prevent adding new members to projects within this group. Premium and Ultimate only.
     * @param ldapCn LDAP Common Name. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param ldapAccess A valid access level. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param sharedRunnersMinutesLimit (admin-only) Maximum number of monthly compute minutes for this group. &#x60;nil&#x60; (default) inherits the instance default, and &#x60;0&#x60; means unlimited. GitLab Self-Managed, Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this group. GitLab Self-Managed, Premium and Ultimate only.
     * @param wikiAccessLevel Wiki access level. One of &#x60;disabled&#x60;, &#x60;private&#x60; or &#x60;enabled&#x60;. Premium and Ultimate only.
     * @param duoAvailability Duo availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60;, &#x60;never_on&#x60; or &#x60;always_on&#x60;. In the UI, &#x60;never_on&#x60; is displayed as Always Off.
     * @param duoRemoteFlowsAvailability Enable GitLab Duo remote flows for this group
     * @param duoFoundationalFlowsAvailability Enable GitLab foundational Duo flows for this group
     * @param duoScheduledAutomationsAvailability Allow GitLab Duo flows in this group to run on a schedule
     * @param duoCodeSuggestionsAvailability Enable GitLab Duo Code Suggestions for this group
     * @param duoCustomAgentsAvailability Enable GitLab Duo custom agents for this group
     * @param duoCustomFlowsAvailability Enable GitLab Duo custom flows for this group
     * @param duoExternalAgentsAvailability Enable GitLab Duo external agents for this group
     * @param toolApprovalForSessionAvailability Tool approval for session availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param duoAutoModeAvailability Duo auto mode availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param amazonQAutoReviewEnabled Enable Amazon Q auto review for merge request
     * @param experimentFeaturesEnabled Enable experiment features for this group
     * @param modelPromptCacheEnabled Enable model prompt cache for this group
     * @param foundationalAgentsStatuses Whether each foundational agent has been enabled or disabled.
     * @param aiSettingsAttributes The aiSettingsAttributes parameter
     * @param preventSharingGroupsOutsideHierarchy Prevent sharing groups within this namespace with any groups outside the namespace. Only available on top-level groups.
     * @param stepUpAuthRequiredOauthProvider OAuth provider required for step-up authentication. Pass empty string to disable. Behind a feature flag named &#x60;omniauth_step_up_auth_for_namespace&#x60;, disabled by default. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/556943) in GitLab 18.4.
     * @param lockMathRenderingLimitsEnabled Indicates if math rendering limits are locked for all descendent groups.
     * @param mathRenderingLimitsEnabled Indicates if math rendering limits are used for this group.
     * @param requireShaForMerge Indicates whether merge requests in this group require a valid commit SHA for calls to the merge a merge request endpoint. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/256966) in GitLab 19.5.
     * @param lockRequireShaForMerge Enforce the require_sha_for_merge setting for all descendant groups. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/256966) in GitLab 19.5.
     * @param maxArtifactsSize Set the maximum file size, in MB, for each job&#39;s artifacts
     * @param fileTemplateProjectId The ID of a project to use for custom templates in this group. Premium and Ultimate only.
     * @param preventForkingOutsideGroup Prevent forking projects inside this group to external namespaces. Premium and Ultimate only.
     * @param uniqueProjectDownloadLimit Maximum number of unique projects a user can download in the specified time period before they are banned. Available only on top-level groups. Default is &#x60;0&#x60;. Maximum is &#x60;10000&#x60;. Ultimate only.
     * @param uniqueProjectDownloadLimitIntervalInSeconds Time period, in seconds, during which a user can download a maximum amount of projects before they are banned. Available only on top-level groups. Default is &#x60;0&#x60;. Maximum is &#x60;864000&#x60; (10 days). Ultimate only.
     * @param uniqueProjectDownloadLimitAllowlist List of usernames excluded from the unique project download limit. Available only on top-level groups. Maximum of 100 usernames. Ultimate only.
     * @param uniqueProjectDownloadLimitAlertlist List of user ids who will be emailed when Git abuse rate limit is exceeded. Available only on top-level groups. Maximum of 100 user IDs. When empty, the active Owners of the group are emailed. Ultimate only.
     * @param autoBanUserOnExcessiveProjectsDownload Ban users from the group when they exceed maximum number of unique projects download in the specified time period, as set by &#x60;unique_project_download_limit&#x60; and &#x60;unique_project_download_limit_interval_in_seconds&#x60;. Available only on top-level groups. Ultimate only.
     * @param ipRestrictionRanges Comma-separated list of IP addresses or subnet masks to restrict group access. Premium and Ultimate only.
     * @param allowedEmailDomainsList Comma-separated list of email address domains to allow group access. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/351494) in GitLab 17.4.
     * @param serviceAccessTokensExpirationEnforced To enforce token expiration for Service accounts users for group
     * @param duoCoreFeaturesEnabled [Experimental] Indicates whether GitLab Duo Core features are enabled for the group
     * @param duoFeaturesEnabled Indicates whether GitLab Duo features are enabled for the group. GitLab Self-Managed, Premium and Ultimate only.
     * @param lockDuoFeaturesEnabled Indicates if the GitLab Duo features enabled setting is enforced for all subgroups. GitLab Self-Managed, Premium and Ultimate only.
     * @param autoDuoCodeReviewEnabled Enable automatic reviews by GitLab Duo on merge requests
     * @param duoCodeReviewDecisionsEnabled Enable approval and change requests by GitLab Duo on merge requests
     * @param duoCodeReviewLevelAllowed [Beta] Allow Standard reviews by GitLab Duo Code Review Flow
     * @param duoCodeReviewDefaultLevel [Beta] Default review level for GitLab Duo Code Review Flow. One of &#x60;lite&#x60; or &#x60;standard&#x60;
     * @param aiAuditEventsStorageEnabled Indicates whether AI audit events are stored for the group. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/245843) in GitLab 19.2.
     * @param lockAiAuditEventsStorageEnabled Indicates whether AI audit events storage is enforced for all subgroups and projects. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/245843) in GitLab 19.2.
     * @param aiGovernanceEnabled Indicates whether AI Governance is turned on for the group, its subgroups, and its projects. Default is &#x60;false&#x60;. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/261994) in GitLab 19.6.
     * @param lockAiGovernanceEnabled Indicates whether the AI Governance setting is enforced for all subgroups and projects. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/261994) in GitLab 19.6.
     * @param webBasedCommitSigningEnabled Enable web based commit signing for commits created from the GitLab UI. When enabled for a group, applies to all projects in the group. GitLab.com only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/193928) in GitLab 18.2.
     * @param onlyAllowMergeIfPipelineSucceeds Only allow to merge if builds succeed. When enabled for a group, applies to all projects in the group. Premium and Ultimate only.
     * @param allowMergeOnSkippedPipeline Allow to merge if pipeline is skipped. Can be &#x60;true&#x60; only when &#x60;only_allow_merge_if_pipeline_succeeds&#x60; is &#x60;true&#x60;. Premium and Ultimate only.
     * @param onlyAllowMergeIfAllDiscussionsAreResolved Only allow to merge if all threads are resolved. When enabled for a group, applies to all projects in the group. Premium and Ultimate only.
     * @param enabledFoundationalFlows References of enabled foundational flows
     * @param duoTemplateProjectId The ID of a project to use as the Duo Code Review custom instructions template for this group
     * @param allowPersonalSnippets Allow creation of personal snippets for enterprise users of this group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/200575) in GitLab 18.5.
     * @param duoNamespaceAccessRules AI entity access rules for controlling Duo feature access
     * @param builtInProjectTemplatesEnabled Enable built-in project templates for project creation. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/235504) in GitLab 19.0.
     * @param lockBuiltInProjectTemplatesEnabled Enforce the built-in project templates setting for all subgroups. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/235504) in GitLab 19.0.
     * @param createCodeReviewFlowConsent When true, records explicit namespace consent for DAP-based Code Review routing
     * @return ResponseEntity&lt;APIEntitiesGroupDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupDetail> putApiV4GroupsIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String sharedRunnersSetting, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Boolean shareWithGroupLock, @jakarta.annotation.Nullable Boolean requireTwoFactorAuthentication, @jakarta.annotation.Nullable Integer twoFactorGracePeriod, @jakarta.annotation.Nullable String projectCreationLevel, @jakarta.annotation.Nullable Boolean autoDevopsEnabled, @jakarta.annotation.Nullable String subgroupCreationLevel, @jakarta.annotation.Nullable Boolean emailsDisabled, @jakarta.annotation.Nullable Boolean emailsEnabled, @jakarta.annotation.Nullable Boolean showDiffPreviewInEmail, @jakarta.annotation.Nullable Boolean mentionsDisabled, @jakarta.annotation.Nullable Boolean lfsEnabled, @jakarta.annotation.Nullable Boolean requestAccessEnabled, @jakarta.annotation.Nullable String defaultBranch, @jakarta.annotation.Nullable Integer defaultBranchProtection, @jakarta.annotation.Nullable PostApiV4GroupsRequestDefaultBranchProtectionDefaults defaultBranchProtectionDefaults, @jakarta.annotation.Nullable String enabledGitAccessProtocol, @jakarta.annotation.Nullable Boolean crmEnabled, @jakarta.annotation.Nullable Boolean resourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean lockResourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean membershipLock, @jakarta.annotation.Nullable String ldapCn, @jakarta.annotation.Nullable Integer ldapAccess, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable String wikiAccessLevel, @jakarta.annotation.Nullable String duoAvailability, @jakarta.annotation.Nullable Boolean duoRemoteFlowsAvailability, @jakarta.annotation.Nullable Boolean duoFoundationalFlowsAvailability, @jakarta.annotation.Nullable Boolean duoScheduledAutomationsAvailability, @jakarta.annotation.Nullable Boolean duoCodeSuggestionsAvailability, @jakarta.annotation.Nullable Boolean duoCustomAgentsAvailability, @jakarta.annotation.Nullable Boolean duoCustomFlowsAvailability, @jakarta.annotation.Nullable Boolean duoExternalAgentsAvailability, @jakarta.annotation.Nullable String toolApprovalForSessionAvailability, @jakarta.annotation.Nullable String duoAutoModeAvailability, @jakarta.annotation.Nullable Boolean amazonQAutoReviewEnabled, @jakarta.annotation.Nullable Boolean experimentFeaturesEnabled, @jakarta.annotation.Nullable Boolean modelPromptCacheEnabled, @jakarta.annotation.Nullable List<PostApiV4GroupsRequestFoundationalAgentsStatusesInner> foundationalAgentsStatuses, @jakarta.annotation.Nullable PostApiV4GroupsRequestAiSettingsAttributes aiSettingsAttributes, @jakarta.annotation.Nullable Boolean preventSharingGroupsOutsideHierarchy, @jakarta.annotation.Nullable String stepUpAuthRequiredOauthProvider, @jakarta.annotation.Nullable Boolean lockMathRenderingLimitsEnabled, @jakarta.annotation.Nullable Boolean mathRenderingLimitsEnabled, @jakarta.annotation.Nullable Boolean requireShaForMerge, @jakarta.annotation.Nullable Boolean lockRequireShaForMerge, @jakarta.annotation.Nullable Integer maxArtifactsSize, @jakarta.annotation.Nullable Integer fileTemplateProjectId, @jakarta.annotation.Nullable Boolean preventForkingOutsideGroup, @jakarta.annotation.Nullable Integer uniqueProjectDownloadLimit, @jakarta.annotation.Nullable Integer uniqueProjectDownloadLimitIntervalInSeconds, @jakarta.annotation.Nullable List<String> uniqueProjectDownloadLimitAllowlist, @jakarta.annotation.Nullable List<Integer> uniqueProjectDownloadLimitAlertlist, @jakarta.annotation.Nullable Boolean autoBanUserOnExcessiveProjectsDownload, @jakarta.annotation.Nullable String ipRestrictionRanges, @jakarta.annotation.Nullable String allowedEmailDomainsList, @jakarta.annotation.Nullable Boolean serviceAccessTokensExpirationEnforced, @jakarta.annotation.Nullable Boolean duoCoreFeaturesEnabled, @jakarta.annotation.Nullable Boolean duoFeaturesEnabled, @jakarta.annotation.Nullable Boolean lockDuoFeaturesEnabled, @jakarta.annotation.Nullable Boolean autoDuoCodeReviewEnabled, @jakarta.annotation.Nullable Boolean duoCodeReviewDecisionsEnabled, @jakarta.annotation.Nullable Boolean duoCodeReviewLevelAllowed, @jakarta.annotation.Nullable String duoCodeReviewDefaultLevel, @jakarta.annotation.Nullable Boolean aiAuditEventsStorageEnabled, @jakarta.annotation.Nullable Boolean lockAiAuditEventsStorageEnabled, @jakarta.annotation.Nullable Boolean aiGovernanceEnabled, @jakarta.annotation.Nullable Boolean lockAiGovernanceEnabled, @jakarta.annotation.Nullable Boolean webBasedCommitSigningEnabled, @jakarta.annotation.Nullable Boolean onlyAllowMergeIfPipelineSucceeds, @jakarta.annotation.Nullable Boolean allowMergeOnSkippedPipeline, @jakarta.annotation.Nullable Boolean onlyAllowMergeIfAllDiscussionsAreResolved, @jakarta.annotation.Nullable List<String> enabledFoundationalFlows, @jakarta.annotation.Nullable Integer duoTemplateProjectId, @jakarta.annotation.Nullable Boolean allowPersonalSnippets, @jakarta.annotation.Nullable List<PutApiV4GroupsIdRequestDuoNamespaceAccessRulesInner> duoNamespaceAccessRules, @jakarta.annotation.Nullable Boolean builtInProjectTemplatesEnabled, @jakarta.annotation.Nullable Boolean lockBuiltInProjectTemplatesEnabled, @jakarta.annotation.Nullable Boolean createCodeReviewFlowConsent) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdRequestCreation(id, name, path, sharedRunnersSetting, description, visibility, avatar, shareWithGroupLock, requireTwoFactorAuthentication, twoFactorGracePeriod, projectCreationLevel, autoDevopsEnabled, subgroupCreationLevel, emailsDisabled, emailsEnabled, showDiffPreviewInEmail, mentionsDisabled, lfsEnabled, requestAccessEnabled, defaultBranch, defaultBranchProtection, defaultBranchProtectionDefaults, enabledGitAccessProtocol, crmEnabled, resourceAccessTokenNotifyInherited, lockResourceAccessTokenNotifyInherited, membershipLock, ldapCn, ldapAccess, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, wikiAccessLevel, duoAvailability, duoRemoteFlowsAvailability, duoFoundationalFlowsAvailability, duoScheduledAutomationsAvailability, duoCodeSuggestionsAvailability, duoCustomAgentsAvailability, duoCustomFlowsAvailability, duoExternalAgentsAvailability, toolApprovalForSessionAvailability, duoAutoModeAvailability, amazonQAutoReviewEnabled, experimentFeaturesEnabled, modelPromptCacheEnabled, foundationalAgentsStatuses, aiSettingsAttributes, preventSharingGroupsOutsideHierarchy, stepUpAuthRequiredOauthProvider, lockMathRenderingLimitsEnabled, mathRenderingLimitsEnabled, requireShaForMerge, lockRequireShaForMerge, maxArtifactsSize, fileTemplateProjectId, preventForkingOutsideGroup, uniqueProjectDownloadLimit, uniqueProjectDownloadLimitIntervalInSeconds, uniqueProjectDownloadLimitAllowlist, uniqueProjectDownloadLimitAlertlist, autoBanUserOnExcessiveProjectsDownload, ipRestrictionRanges, allowedEmailDomainsList, serviceAccessTokensExpirationEnforced, duoCoreFeaturesEnabled, duoFeaturesEnabled, lockDuoFeaturesEnabled, autoDuoCodeReviewEnabled, duoCodeReviewDecisionsEnabled, duoCodeReviewLevelAllowed, duoCodeReviewDefaultLevel, aiAuditEventsStorageEnabled, lockAiAuditEventsStorageEnabled, aiGovernanceEnabled, lockAiGovernanceEnabled, webBasedCommitSigningEnabled, onlyAllowMergeIfPipelineSucceeds, allowMergeOnSkippedPipeline, onlyAllowMergeIfAllDiscussionsAreResolved, enabledFoundationalFlows, duoTemplateProjectId, allowPersonalSnippets, duoNamespaceAccessRules, builtInProjectTemplatesEnabled, lockBuiltInProjectTemplatesEnabled, createCodeReviewFlowConsent).toEntity(localVarReturnType);
    }

    /**
     * Update group attributes
     * Updates the attributes for a specified group. You must be an administrator or have the Owner role for the group. The &#x60;projects&#x60; and &#x60;shared_projects&#x60; attributes in the response are deprecated and scheduled for removal in API v5. Each returns at most 100 projects.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name The name of the group
     * @param path The path of the group
     * @param sharedRunnersSetting Enable/disable shared runners for the group and its subgroups and projects. &#x60;enabled&#x60; enables instance runners for all projects and subgroups in the group. &#x60;disabled_and_overridable&#x60; disables them, but subgroups can override the setting. &#x60;disabled_and_unoverridable&#x60; disables them, and subgroups cannot override the setting.
     * @param description The description of the group
     * @param visibility The visibility of the group
     * @param avatar Avatar image for the group. On update, a blank value removes the avatar.
     * @param shareWithGroupLock Prevent sharing a project with another group within this group
     * @param requireTwoFactorAuthentication Require all users in this group to setup Two-factor authentication
     * @param twoFactorGracePeriod Time before Two-factor authentication is enforced, in hours.
     * @param projectCreationLevel Determine if developers can create projects in the group
     * @param autoDevopsEnabled Default to Auto DevOps pipeline for all projects within this group
     * @param subgroupCreationLevel Allowed to create subgroups
     * @param emailsDisabled Disable email notifications. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/127899) in GitLab 16.5. Use &#x60;emails_enabled&#x60; instead.
     * @param emailsEnabled Enable email notifications
     * @param showDiffPreviewInEmail Include the code diff preview in merge request notification emails
     * @param mentionsDisabled Disable a group from getting mentioned
     * @param lfsEnabled Enable/disable LFS for the projects in this group
     * @param requestAccessEnabled Allow users to request member access
     * @param defaultBranch The default branch of group&#39;s projects
     * @param defaultBranchProtection Determine if developers can push to default branch. &#x60;0&#x60;: no protection. Users with the Developer or Maintainer role can push new commits, force push, and delete the branch. &#x60;1&#x60;: partial protection. Users with the Developer or Maintainer role can push new commits. &#x60;2&#x60;: full protection. Only users with the Maintainer role can push new commits. &#x60;3&#x60;: protected against pushes. Users with the Maintainer role can push new commits, force push, and accept merge requests. Users with the Developer role can accept merge requests. &#x60;4&#x60;: full protection after initial push. Users with the Developer role can push commits to an empty repository. Users with the Maintainer role can push new commits and accept merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/issues/408314) in GitLab 17.0. Use &#x60;default_branch_protection_defaults&#x60; instead.
     * @param defaultBranchProtectionDefaults The defaultBranchProtectionDefaults parameter
     * @param enabledGitAccessProtocol Allow only the selected protocols to be used for Git access.
     * @param crmEnabled Enable Customer Relations Management for this group
     * @param resourceAccessTokenNotifyInherited Send access token expiry notifications to all inherited members of the group
     * @param lockResourceAccessTokenNotifyInherited Prevent subgroups from overriding the access token expiry notification setting
     * @param membershipLock Prevent adding new members to projects within this group. Premium and Ultimate only.
     * @param ldapCn LDAP Common Name. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param ldapAccess A valid access level. All or none of &#x60;ldap_cn&#x60;, &#x60;ldap_access&#x60; must be provided.
     * @param sharedRunnersMinutesLimit (admin-only) Maximum number of monthly compute minutes for this group. &#x60;nil&#x60; (default) inherits the instance default, and &#x60;0&#x60; means unlimited. GitLab Self-Managed, Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this group. GitLab Self-Managed, Premium and Ultimate only.
     * @param wikiAccessLevel Wiki access level. One of &#x60;disabled&#x60;, &#x60;private&#x60; or &#x60;enabled&#x60;. Premium and Ultimate only.
     * @param duoAvailability Duo availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60;, &#x60;never_on&#x60; or &#x60;always_on&#x60;. In the UI, &#x60;never_on&#x60; is displayed as Always Off.
     * @param duoRemoteFlowsAvailability Enable GitLab Duo remote flows for this group
     * @param duoFoundationalFlowsAvailability Enable GitLab foundational Duo flows for this group
     * @param duoScheduledAutomationsAvailability Allow GitLab Duo flows in this group to run on a schedule
     * @param duoCodeSuggestionsAvailability Enable GitLab Duo Code Suggestions for this group
     * @param duoCustomAgentsAvailability Enable GitLab Duo custom agents for this group
     * @param duoCustomFlowsAvailability Enable GitLab Duo custom flows for this group
     * @param duoExternalAgentsAvailability Enable GitLab Duo external agents for this group
     * @param toolApprovalForSessionAvailability Tool approval for session availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param duoAutoModeAvailability Duo auto mode availability. One of &#x60;default_on&#x60;, &#x60;default_off&#x60; or &#x60;never_on&#x60;
     * @param amazonQAutoReviewEnabled Enable Amazon Q auto review for merge request
     * @param experimentFeaturesEnabled Enable experiment features for this group
     * @param modelPromptCacheEnabled Enable model prompt cache for this group
     * @param foundationalAgentsStatuses Whether each foundational agent has been enabled or disabled.
     * @param aiSettingsAttributes The aiSettingsAttributes parameter
     * @param preventSharingGroupsOutsideHierarchy Prevent sharing groups within this namespace with any groups outside the namespace. Only available on top-level groups.
     * @param stepUpAuthRequiredOauthProvider OAuth provider required for step-up authentication. Pass empty string to disable. Behind a feature flag named &#x60;omniauth_step_up_auth_for_namespace&#x60;, disabled by default. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/556943) in GitLab 18.4.
     * @param lockMathRenderingLimitsEnabled Indicates if math rendering limits are locked for all descendent groups.
     * @param mathRenderingLimitsEnabled Indicates if math rendering limits are used for this group.
     * @param requireShaForMerge Indicates whether merge requests in this group require a valid commit SHA for calls to the merge a merge request endpoint. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/256966) in GitLab 19.5.
     * @param lockRequireShaForMerge Enforce the require_sha_for_merge setting for all descendant groups. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/256966) in GitLab 19.5.
     * @param maxArtifactsSize Set the maximum file size, in MB, for each job&#39;s artifacts
     * @param fileTemplateProjectId The ID of a project to use for custom templates in this group. Premium and Ultimate only.
     * @param preventForkingOutsideGroup Prevent forking projects inside this group to external namespaces. Premium and Ultimate only.
     * @param uniqueProjectDownloadLimit Maximum number of unique projects a user can download in the specified time period before they are banned. Available only on top-level groups. Default is &#x60;0&#x60;. Maximum is &#x60;10000&#x60;. Ultimate only.
     * @param uniqueProjectDownloadLimitIntervalInSeconds Time period, in seconds, during which a user can download a maximum amount of projects before they are banned. Available only on top-level groups. Default is &#x60;0&#x60;. Maximum is &#x60;864000&#x60; (10 days). Ultimate only.
     * @param uniqueProjectDownloadLimitAllowlist List of usernames excluded from the unique project download limit. Available only on top-level groups. Maximum of 100 usernames. Ultimate only.
     * @param uniqueProjectDownloadLimitAlertlist List of user ids who will be emailed when Git abuse rate limit is exceeded. Available only on top-level groups. Maximum of 100 user IDs. When empty, the active Owners of the group are emailed. Ultimate only.
     * @param autoBanUserOnExcessiveProjectsDownload Ban users from the group when they exceed maximum number of unique projects download in the specified time period, as set by &#x60;unique_project_download_limit&#x60; and &#x60;unique_project_download_limit_interval_in_seconds&#x60;. Available only on top-level groups. Ultimate only.
     * @param ipRestrictionRanges Comma-separated list of IP addresses or subnet masks to restrict group access. Premium and Ultimate only.
     * @param allowedEmailDomainsList Comma-separated list of email address domains to allow group access. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/351494) in GitLab 17.4.
     * @param serviceAccessTokensExpirationEnforced To enforce token expiration for Service accounts users for group
     * @param duoCoreFeaturesEnabled [Experimental] Indicates whether GitLab Duo Core features are enabled for the group
     * @param duoFeaturesEnabled Indicates whether GitLab Duo features are enabled for the group. GitLab Self-Managed, Premium and Ultimate only.
     * @param lockDuoFeaturesEnabled Indicates if the GitLab Duo features enabled setting is enforced for all subgroups. GitLab Self-Managed, Premium and Ultimate only.
     * @param autoDuoCodeReviewEnabled Enable automatic reviews by GitLab Duo on merge requests
     * @param duoCodeReviewDecisionsEnabled Enable approval and change requests by GitLab Duo on merge requests
     * @param duoCodeReviewLevelAllowed [Beta] Allow Standard reviews by GitLab Duo Code Review Flow
     * @param duoCodeReviewDefaultLevel [Beta] Default review level for GitLab Duo Code Review Flow. One of &#x60;lite&#x60; or &#x60;standard&#x60;
     * @param aiAuditEventsStorageEnabled Indicates whether AI audit events are stored for the group. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/245843) in GitLab 19.2.
     * @param lockAiAuditEventsStorageEnabled Indicates whether AI audit events storage is enforced for all subgroups and projects. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/245843) in GitLab 19.2.
     * @param aiGovernanceEnabled Indicates whether AI Governance is turned on for the group, its subgroups, and its projects. Default is &#x60;false&#x60;. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/261994) in GitLab 19.6.
     * @param lockAiGovernanceEnabled Indicates whether the AI Governance setting is enforced for all subgroups and projects. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/261994) in GitLab 19.6.
     * @param webBasedCommitSigningEnabled Enable web based commit signing for commits created from the GitLab UI. When enabled for a group, applies to all projects in the group. GitLab.com only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/193928) in GitLab 18.2.
     * @param onlyAllowMergeIfPipelineSucceeds Only allow to merge if builds succeed. When enabled for a group, applies to all projects in the group. Premium and Ultimate only.
     * @param allowMergeOnSkippedPipeline Allow to merge if pipeline is skipped. Can be &#x60;true&#x60; only when &#x60;only_allow_merge_if_pipeline_succeeds&#x60; is &#x60;true&#x60;. Premium and Ultimate only.
     * @param onlyAllowMergeIfAllDiscussionsAreResolved Only allow to merge if all threads are resolved. When enabled for a group, applies to all projects in the group. Premium and Ultimate only.
     * @param enabledFoundationalFlows References of enabled foundational flows
     * @param duoTemplateProjectId The ID of a project to use as the Duo Code Review custom instructions template for this group
     * @param allowPersonalSnippets Allow creation of personal snippets for enterprise users of this group. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/200575) in GitLab 18.5.
     * @param duoNamespaceAccessRules AI entity access rules for controlling Duo feature access
     * @param builtInProjectTemplatesEnabled Enable built-in project templates for project creation. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/235504) in GitLab 19.0.
     * @param lockBuiltInProjectTemplatesEnabled Enforce the built-in project templates setting for all subgroups. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/235504) in GitLab 19.0.
     * @param createCodeReviewFlowConsent When true, records explicit namespace consent for DAP-based Code Review routing
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String path, @jakarta.annotation.Nullable String sharedRunnersSetting, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String visibility, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Boolean shareWithGroupLock, @jakarta.annotation.Nullable Boolean requireTwoFactorAuthentication, @jakarta.annotation.Nullable Integer twoFactorGracePeriod, @jakarta.annotation.Nullable String projectCreationLevel, @jakarta.annotation.Nullable Boolean autoDevopsEnabled, @jakarta.annotation.Nullable String subgroupCreationLevel, @jakarta.annotation.Nullable Boolean emailsDisabled, @jakarta.annotation.Nullable Boolean emailsEnabled, @jakarta.annotation.Nullable Boolean showDiffPreviewInEmail, @jakarta.annotation.Nullable Boolean mentionsDisabled, @jakarta.annotation.Nullable Boolean lfsEnabled, @jakarta.annotation.Nullable Boolean requestAccessEnabled, @jakarta.annotation.Nullable String defaultBranch, @jakarta.annotation.Nullable Integer defaultBranchProtection, @jakarta.annotation.Nullable PostApiV4GroupsRequestDefaultBranchProtectionDefaults defaultBranchProtectionDefaults, @jakarta.annotation.Nullable String enabledGitAccessProtocol, @jakarta.annotation.Nullable Boolean crmEnabled, @jakarta.annotation.Nullable Boolean resourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean lockResourceAccessTokenNotifyInherited, @jakarta.annotation.Nullable Boolean membershipLock, @jakarta.annotation.Nullable String ldapCn, @jakarta.annotation.Nullable Integer ldapAccess, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable String wikiAccessLevel, @jakarta.annotation.Nullable String duoAvailability, @jakarta.annotation.Nullable Boolean duoRemoteFlowsAvailability, @jakarta.annotation.Nullable Boolean duoFoundationalFlowsAvailability, @jakarta.annotation.Nullable Boolean duoScheduledAutomationsAvailability, @jakarta.annotation.Nullable Boolean duoCodeSuggestionsAvailability, @jakarta.annotation.Nullable Boolean duoCustomAgentsAvailability, @jakarta.annotation.Nullable Boolean duoCustomFlowsAvailability, @jakarta.annotation.Nullable Boolean duoExternalAgentsAvailability, @jakarta.annotation.Nullable String toolApprovalForSessionAvailability, @jakarta.annotation.Nullable String duoAutoModeAvailability, @jakarta.annotation.Nullable Boolean amazonQAutoReviewEnabled, @jakarta.annotation.Nullable Boolean experimentFeaturesEnabled, @jakarta.annotation.Nullable Boolean modelPromptCacheEnabled, @jakarta.annotation.Nullable List<PostApiV4GroupsRequestFoundationalAgentsStatusesInner> foundationalAgentsStatuses, @jakarta.annotation.Nullable PostApiV4GroupsRequestAiSettingsAttributes aiSettingsAttributes, @jakarta.annotation.Nullable Boolean preventSharingGroupsOutsideHierarchy, @jakarta.annotation.Nullable String stepUpAuthRequiredOauthProvider, @jakarta.annotation.Nullable Boolean lockMathRenderingLimitsEnabled, @jakarta.annotation.Nullable Boolean mathRenderingLimitsEnabled, @jakarta.annotation.Nullable Boolean requireShaForMerge, @jakarta.annotation.Nullable Boolean lockRequireShaForMerge, @jakarta.annotation.Nullable Integer maxArtifactsSize, @jakarta.annotation.Nullable Integer fileTemplateProjectId, @jakarta.annotation.Nullable Boolean preventForkingOutsideGroup, @jakarta.annotation.Nullable Integer uniqueProjectDownloadLimit, @jakarta.annotation.Nullable Integer uniqueProjectDownloadLimitIntervalInSeconds, @jakarta.annotation.Nullable List<String> uniqueProjectDownloadLimitAllowlist, @jakarta.annotation.Nullable List<Integer> uniqueProjectDownloadLimitAlertlist, @jakarta.annotation.Nullable Boolean autoBanUserOnExcessiveProjectsDownload, @jakarta.annotation.Nullable String ipRestrictionRanges, @jakarta.annotation.Nullable String allowedEmailDomainsList, @jakarta.annotation.Nullable Boolean serviceAccessTokensExpirationEnforced, @jakarta.annotation.Nullable Boolean duoCoreFeaturesEnabled, @jakarta.annotation.Nullable Boolean duoFeaturesEnabled, @jakarta.annotation.Nullable Boolean lockDuoFeaturesEnabled, @jakarta.annotation.Nullable Boolean autoDuoCodeReviewEnabled, @jakarta.annotation.Nullable Boolean duoCodeReviewDecisionsEnabled, @jakarta.annotation.Nullable Boolean duoCodeReviewLevelAllowed, @jakarta.annotation.Nullable String duoCodeReviewDefaultLevel, @jakarta.annotation.Nullable Boolean aiAuditEventsStorageEnabled, @jakarta.annotation.Nullable Boolean lockAiAuditEventsStorageEnabled, @jakarta.annotation.Nullable Boolean aiGovernanceEnabled, @jakarta.annotation.Nullable Boolean lockAiGovernanceEnabled, @jakarta.annotation.Nullable Boolean webBasedCommitSigningEnabled, @jakarta.annotation.Nullable Boolean onlyAllowMergeIfPipelineSucceeds, @jakarta.annotation.Nullable Boolean allowMergeOnSkippedPipeline, @jakarta.annotation.Nullable Boolean onlyAllowMergeIfAllDiscussionsAreResolved, @jakarta.annotation.Nullable List<String> enabledFoundationalFlows, @jakarta.annotation.Nullable Integer duoTemplateProjectId, @jakarta.annotation.Nullable Boolean allowPersonalSnippets, @jakarta.annotation.Nullable List<PutApiV4GroupsIdRequestDuoNamespaceAccessRulesInner> duoNamespaceAccessRules, @jakarta.annotation.Nullable Boolean builtInProjectTemplatesEnabled, @jakarta.annotation.Nullable Boolean lockBuiltInProjectTemplatesEnabled, @jakarta.annotation.Nullable Boolean createCodeReviewFlowConsent) throws RestClientResponseException {
        return putApiV4GroupsIdRequestCreation(id, name, path, sharedRunnersSetting, description, visibility, avatar, shareWithGroupLock, requireTwoFactorAuthentication, twoFactorGracePeriod, projectCreationLevel, autoDevopsEnabled, subgroupCreationLevel, emailsDisabled, emailsEnabled, showDiffPreviewInEmail, mentionsDisabled, lfsEnabled, requestAccessEnabled, defaultBranch, defaultBranchProtection, defaultBranchProtectionDefaults, enabledGitAccessProtocol, crmEnabled, resourceAccessTokenNotifyInherited, lockResourceAccessTokenNotifyInherited, membershipLock, ldapCn, ldapAccess, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, wikiAccessLevel, duoAvailability, duoRemoteFlowsAvailability, duoFoundationalFlowsAvailability, duoScheduledAutomationsAvailability, duoCodeSuggestionsAvailability, duoCustomAgentsAvailability, duoCustomFlowsAvailability, duoExternalAgentsAvailability, toolApprovalForSessionAvailability, duoAutoModeAvailability, amazonQAutoReviewEnabled, experimentFeaturesEnabled, modelPromptCacheEnabled, foundationalAgentsStatuses, aiSettingsAttributes, preventSharingGroupsOutsideHierarchy, stepUpAuthRequiredOauthProvider, lockMathRenderingLimitsEnabled, mathRenderingLimitsEnabled, requireShaForMerge, lockRequireShaForMerge, maxArtifactsSize, fileTemplateProjectId, preventForkingOutsideGroup, uniqueProjectDownloadLimit, uniqueProjectDownloadLimitIntervalInSeconds, uniqueProjectDownloadLimitAllowlist, uniqueProjectDownloadLimitAlertlist, autoBanUserOnExcessiveProjectsDownload, ipRestrictionRanges, allowedEmailDomainsList, serviceAccessTokensExpirationEnforced, duoCoreFeaturesEnabled, duoFeaturesEnabled, lockDuoFeaturesEnabled, autoDuoCodeReviewEnabled, duoCodeReviewDecisionsEnabled, duoCodeReviewLevelAllowed, duoCodeReviewDefaultLevel, aiAuditEventsStorageEnabled, lockAiAuditEventsStorageEnabled, aiGovernanceEnabled, lockAiGovernanceEnabled, webBasedCommitSigningEnabled, onlyAllowMergeIfPipelineSucceeds, allowMergeOnSkippedPipeline, onlyAllowMergeIfAllDiscussionsAreResolved, enabledFoundationalFlows, duoTemplateProjectId, allowPersonalSnippets, duoNamespaceAccessRules, builtInProjectTemplatesEnabled, lockBuiltInProjectTemplatesEnabled, createCodeReviewFlowConsent);
    }

    /**
     * Update group security settings
     * Updates group security settings for a specified group. You must have the Security Manager, Maintainer, or Owner role for the group. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdSecuritySettingsRequest The putApiV4GroupsIdSecuritySettingsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdSecuritySettingsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdSecuritySettingsRequest putApiV4GroupsIdSecuritySettingsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdSecuritySettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4GroupsIdSecuritySettingsRequest' is set
        if (putApiV4GroupsIdSecuritySettingsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4GroupsIdSecuritySettingsRequest' when calling putApiV4GroupsIdSecuritySettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/security_settings", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdSecuritySettingsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update group security settings
     * Updates group security settings for a specified group. You must have the Security Manager, Maintainer, or Owner role for the group. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdSecuritySettingsRequest The putApiV4GroupsIdSecuritySettingsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4GroupsIdSecuritySettings(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdSecuritySettingsRequest putApiV4GroupsIdSecuritySettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4GroupsIdSecuritySettingsRequestCreation(id, putApiV4GroupsIdSecuritySettingsRequest).body(localVarReturnType);
    }

    /**
     * Update group security settings
     * Updates group security settings for a specified group. You must have the Security Manager, Maintainer, or Owner role for the group. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdSecuritySettingsRequest The putApiV4GroupsIdSecuritySettingsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4GroupsIdSecuritySettingsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdSecuritySettingsRequest putApiV4GroupsIdSecuritySettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdSecuritySettingsRequestCreation(id, putApiV4GroupsIdSecuritySettingsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update group security settings
     * Updates group security settings for a specified group. You must have the Security Manager, Maintainer, or Owner role for the group. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdSecuritySettingsRequest The putApiV4GroupsIdSecuritySettingsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdSecuritySettingsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdSecuritySettingsRequest putApiV4GroupsIdSecuritySettingsRequest) throws RestClientResponseException {
        return putApiV4GroupsIdSecuritySettingsRequestCreation(id, putApiV4GroupsIdSecuritySettingsRequest);
    }
}
