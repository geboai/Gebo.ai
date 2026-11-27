package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesMemberRole;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4AdminMemberRolesRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdMemberRolesRequest;
import ai.gebo.gitlab.client.model.PostApiV4MemberRolesRequest;

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
public class MemberRolesApi {
    private ApiClient apiClient;

    public MemberRolesApi() {
        this(new ApiClient());
    }

    public MemberRolesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete Admin Role
     * Deletes a specific custom admin role by its ID.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param memberRoleId ID of the member role.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4AdminMemberRolesMemberRoleIdRequestCreation(@jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        // verify the required parameter 'memberRoleId' is set
        if (memberRoleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'memberRoleId' when calling deleteApiV4AdminMemberRolesMemberRoleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("member_role_id", memberRoleId);

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
        return apiClient.invokeAPI("/api/v4/admin_member_roles/{member_role_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete Admin Role
     * Deletes a specific custom admin role by its ID.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param memberRoleId ID of the member role.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4AdminMemberRolesMemberRoleId(@jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4AdminMemberRolesMemberRoleIdRequestCreation(memberRoleId).body(localVarReturnType);
    }

    /**
     * Delete Admin Role
     * Deletes a specific custom admin role by its ID.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param memberRoleId ID of the member role.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4AdminMemberRolesMemberRoleIdWithHttpInfo(@jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminMemberRolesMemberRoleIdRequestCreation(memberRoleId).toEntity(localVarReturnType);
    }

    /**
     * Delete Admin Role
     * Deletes a specific custom admin role by its ID.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param memberRoleId ID of the member role.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4AdminMemberRolesMemberRoleIdWithResponseSpec(@jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        return deleteApiV4AdminMemberRolesMemberRoleIdRequestCreation(memberRoleId);
    }

    /**
     * Remove member role of a group
     * Removes a specified member role from a group.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param id ID or URL-encoded path of the group.
     * @param memberRoleId ID of the member role.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdMemberRolesMemberRoleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdMemberRolesMemberRoleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'memberRoleId' is set
        if (memberRoleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'memberRoleId' when calling deleteApiV4GroupsIdMemberRolesMemberRoleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("member_role_id", memberRoleId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/member_roles/{member_role_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove member role of a group
     * Removes a specified member role from a group.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param id ID or URL-encoded path of the group.
     * @param memberRoleId ID of the member role.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdMemberRolesMemberRoleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdMemberRolesMemberRoleIdRequestCreation(id, memberRoleId).body(localVarReturnType);
    }

    /**
     * Remove member role of a group
     * Removes a specified member role from a group.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param id ID or URL-encoded path of the group.
     * @param memberRoleId ID of the member role.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdMemberRolesMemberRoleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdMemberRolesMemberRoleIdRequestCreation(id, memberRoleId).toEntity(localVarReturnType);
    }

    /**
     * Remove member role of a group
     * Removes a specified member role from a group.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param id ID or URL-encoded path of the group.
     * @param memberRoleId ID of the member role.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdMemberRolesMemberRoleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        return deleteApiV4GroupsIdMemberRolesMemberRoleIdRequestCreation(id, memberRoleId);
    }

    /**
     * Delete an instance member role
     * Deletes an instance member role.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param memberRoleId ID of the member role.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4MemberRolesMemberRoleIdRequestCreation(@jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        // verify the required parameter 'memberRoleId' is set
        if (memberRoleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'memberRoleId' when calling deleteApiV4MemberRolesMemberRoleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("member_role_id", memberRoleId);

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
        return apiClient.invokeAPI("/api/v4/member_roles/{member_role_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an instance member role
     * Deletes an instance member role.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param memberRoleId ID of the member role.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4MemberRolesMemberRoleId(@jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4MemberRolesMemberRoleIdRequestCreation(memberRoleId).body(localVarReturnType);
    }

    /**
     * Delete an instance member role
     * Deletes an instance member role.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param memberRoleId ID of the member role.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4MemberRolesMemberRoleIdWithHttpInfo(@jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4MemberRolesMemberRoleIdRequestCreation(memberRoleId).toEntity(localVarReturnType);
    }

    /**
     * Delete an instance member role
     * Deletes an instance member role.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - 404 Member Role Not Found
     * @param memberRoleId ID of the member role.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4MemberRolesMemberRoleIdWithResponseSpec(@jakarta.annotation.Nonnull Integer memberRoleId) throws RestClientResponseException {
        return deleteApiV4MemberRolesMemberRoleIdRequestCreation(memberRoleId);
    }

    /**
     * Get Admin Roles for this GitLab instance
     * Retrieves all custom admin roles configured for the instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return List&lt;APIEntitiesMemberRole&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminMemberRolesRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesMemberRole>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin_member_roles", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get Admin Roles for this GitLab instance
     * Retrieves all custom admin roles configured for the instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return List&lt;APIEntitiesMemberRole&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMemberRole> getApiV4AdminMemberRoles() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMemberRole>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminMemberRolesRequestCreation().body(localVarReturnType);
    }

    /**
     * Get Admin Roles for this GitLab instance
     * Retrieves all custom admin roles configured for the instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseEntity&lt;List&lt;APIEntitiesMemberRole&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMemberRole>> getApiV4AdminMemberRolesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMemberRole>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminMemberRolesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get Admin Roles for this GitLab instance
     * Retrieves all custom admin roles configured for the instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminMemberRolesWithResponseSpec() throws RestClientResponseException {
        return getApiV4AdminMemberRolesRequestCreation();
    }

    /**
     * List all group member roles
     * Lists all member roles in a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesMemberRole&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMemberRolesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMemberRoles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesMemberRole>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/member_roles", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group member roles
     * Lists all member roles in a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesMemberRole&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMemberRole> getApiV4GroupsIdMemberRoles(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMemberRole>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMemberRolesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all group member roles
     * Lists all member roles in a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMemberRole&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMemberRole>> getApiV4GroupsIdMemberRolesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMemberRole>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMemberRolesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all group member roles
     * Lists all member roles in a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMemberRolesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdMemberRolesRequestCreation(id);
    }

    /**
     * List all instance member roles
     * Lists all member roles for this GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return List&lt;APIEntitiesMemberRole&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4MemberRolesRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesMemberRole>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/member_roles", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all instance member roles
     * Lists all member roles for this GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return List&lt;APIEntitiesMemberRole&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMemberRole> getApiV4MemberRoles() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMemberRole>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4MemberRolesRequestCreation().body(localVarReturnType);
    }

    /**
     * List all instance member roles
     * Lists all member roles for this GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseEntity&lt;List&lt;APIEntitiesMemberRole&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMemberRole>> getApiV4MemberRolesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMemberRole>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4MemberRolesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all instance member roles
     * Lists all member roles for this GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4MemberRolesWithResponseSpec() throws RestClientResponseException {
        return getApiV4MemberRolesRequestCreation();
    }

    /**
     * Create Admin Role on the GitLab instance
     * Creates a new custom admin role with specified permissions.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4AdminMemberRolesRequest The postApiV4AdminMemberRolesRequest parameter
     * @return APIEntitiesMemberRole
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AdminMemberRolesRequestCreation(@jakarta.annotation.Nullable PostApiV4AdminMemberRolesRequest postApiV4AdminMemberRolesRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesMemberRole> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin_member_roles", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AdminMemberRolesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create Admin Role on the GitLab instance
     * Creates a new custom admin role with specified permissions.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4AdminMemberRolesRequest The postApiV4AdminMemberRolesRequest parameter
     * @return APIEntitiesMemberRole
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMemberRole postApiV4AdminMemberRoles(@jakarta.annotation.Nullable PostApiV4AdminMemberRolesRequest postApiV4AdminMemberRolesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMemberRole> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AdminMemberRolesRequestCreation(postApiV4AdminMemberRolesRequest).body(localVarReturnType);
    }

    /**
     * Create Admin Role on the GitLab instance
     * Creates a new custom admin role with specified permissions.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4AdminMemberRolesRequest The postApiV4AdminMemberRolesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMemberRole&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMemberRole> postApiV4AdminMemberRolesWithHttpInfo(@jakarta.annotation.Nullable PostApiV4AdminMemberRolesRequest postApiV4AdminMemberRolesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMemberRole> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AdminMemberRolesRequestCreation(postApiV4AdminMemberRolesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create Admin Role on the GitLab instance
     * Creates a new custom admin role with specified permissions.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4AdminMemberRolesRequest The postApiV4AdminMemberRolesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AdminMemberRolesWithResponseSpec(@jakarta.annotation.Nullable PostApiV4AdminMemberRolesRequest postApiV4AdminMemberRolesRequest) throws RestClientResponseException {
        return postApiV4AdminMemberRolesRequestCreation(postApiV4AdminMemberRolesRequest);
    }

    /**
     * Add a member role to a group
     * Adds a member role to a group. You can only add member roles at the root level of the group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMemberRolesRequest The postApiV4GroupsIdMemberRolesRequest parameter
     * @return APIEntitiesMemberRole
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdMemberRolesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMemberRolesRequest postApiV4GroupsIdMemberRolesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdMemberRoles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdMemberRolesRequest' is set
        if (postApiV4GroupsIdMemberRolesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdMemberRolesRequest' when calling postApiV4GroupsIdMemberRoles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMemberRole> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/member_roles", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdMemberRolesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a member role to a group
     * Adds a member role to a group. You can only add member roles at the root level of the group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMemberRolesRequest The postApiV4GroupsIdMemberRolesRequest parameter
     * @return APIEntitiesMemberRole
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMemberRole postApiV4GroupsIdMemberRoles(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMemberRolesRequest postApiV4GroupsIdMemberRolesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMemberRole> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdMemberRolesRequestCreation(id, postApiV4GroupsIdMemberRolesRequest).body(localVarReturnType);
    }

    /**
     * Add a member role to a group
     * Adds a member role to a group. You can only add member roles at the root level of the group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMemberRolesRequest The postApiV4GroupsIdMemberRolesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMemberRole&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMemberRole> postApiV4GroupsIdMemberRolesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMemberRolesRequest postApiV4GroupsIdMemberRolesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMemberRole> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdMemberRolesRequestCreation(id, postApiV4GroupsIdMemberRolesRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a member role to a group
     * Adds a member role to a group. You can only add member roles at the root level of the group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMemberRolesRequest The postApiV4GroupsIdMemberRolesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdMemberRolesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMemberRolesRequest postApiV4GroupsIdMemberRolesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdMemberRolesRequestCreation(id, postApiV4GroupsIdMemberRolesRequest);
    }

    /**
     * Create an instance member role
     * Creates an instance member role.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4MemberRolesRequest The postApiV4MemberRolesRequest parameter
     * @return APIEntitiesMemberRole
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4MemberRolesRequestCreation(@jakarta.annotation.Nonnull PostApiV4MemberRolesRequest postApiV4MemberRolesRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4MemberRolesRequest' is set
        if (postApiV4MemberRolesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4MemberRolesRequest' when calling postApiV4MemberRoles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMemberRole> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/member_roles", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4MemberRolesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an instance member role
     * Creates an instance member role.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4MemberRolesRequest The postApiV4MemberRolesRequest parameter
     * @return APIEntitiesMemberRole
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMemberRole postApiV4MemberRoles(@jakarta.annotation.Nonnull PostApiV4MemberRolesRequest postApiV4MemberRolesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMemberRole> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4MemberRolesRequestCreation(postApiV4MemberRolesRequest).body(localVarReturnType);
    }

    /**
     * Create an instance member role
     * Creates an instance member role.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4MemberRolesRequest The postApiV4MemberRolesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMemberRole&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMemberRole> postApiV4MemberRolesWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4MemberRolesRequest postApiV4MemberRolesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMemberRole> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4MemberRolesRequestCreation(postApiV4MemberRolesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an instance member role
     * Creates an instance member role.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4MemberRolesRequest The postApiV4MemberRolesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4MemberRolesWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4MemberRolesRequest postApiV4MemberRolesRequest) throws RestClientResponseException {
        return postApiV4MemberRolesRequestCreation(postApiV4MemberRolesRequest);
    }
}
