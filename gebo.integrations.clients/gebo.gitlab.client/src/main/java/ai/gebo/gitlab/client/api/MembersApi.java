package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBillableMembership;
import ai.gebo.gitlab.client.model.APIEntitiesMember;
import ai.gebo.gitlab.client.model.APIEntitiesPendingMember;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdMembersRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMembersRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdMembersUserIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdMembersUserIdStateRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMembersUserIdRequest;

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
public class MembersApi {
    private ApiClient apiClient;

    public MembersApi() {
        this(new ApiClient());
    }

    public MembersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Remove a billable member from a group
     * Removes a specified billable member from a group and its subgroups and projects. The user does not need to be a group member to qualify for removal. For example, if the user was added directly to a project in the group, you can still remove them using this operation. Removal is asynchronous and completes in a few minutes.
     * <p><b>204</b> - Resource deleted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdBillableMembersUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdBillableMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4GroupsIdBillableMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/billable_members/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove a billable member from a group
     * Removes a specified billable member from a group and its subgroups and projects. The user does not need to be a group member to qualify for removal. For example, if the user was added directly to a project in the group, you can still remove them using this operation. Removal is asynchronous and completes in a few minutes.
     * <p><b>204</b> - Resource deleted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdBillableMembersUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdBillableMembersUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Remove a billable member from a group
     * Removes a specified billable member from a group and its subgroups and projects. The user does not need to be a group member to qualify for removal. For example, if the user was added directly to a project in the group, you can still remove them using this operation. Removal is asynchronous and completes in a few minutes.
     * <p><b>204</b> - Resource deleted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdBillableMembersUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdBillableMembersUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Remove a billable member from a group
     * Removes a specified billable member from a group and its subgroups and projects. The user does not need to be a group member to qualify for removal. For example, if the user was added directly to a project in the group, you can still remove them using this operation. Removal is asynchronous and completes in a few minutes.
     * <p><b>204</b> - Resource deleted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdBillableMembersUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return deleteApiV4GroupsIdBillableMembersUserIdRequestCreation(id, userId);
    }

    /**
     * Remove a member from a group
     * Removes a specified user from a group. The user must be a direct member.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @param skipSubresources If &#x60;true&#x60;, skips deleting direct memberships of the removed member in subgroups and projects.
     * @param unassignIssuables If &#x60;true&#x60;, unassigns the member from any issues or merge requests in the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdMembersUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean skipSubresources, @jakarta.annotation.Nullable Boolean unassignIssuables) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4GroupsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "skip_subresources", skipSubresources));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "unassign_issuables", unassignIssuables));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove a member from a group
     * Removes a specified user from a group. The user must be a direct member.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @param skipSubresources If &#x60;true&#x60;, skips deleting direct memberships of the removed member in subgroups and projects.
     * @param unassignIssuables If &#x60;true&#x60;, unassigns the member from any issues or merge requests in the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdMembersUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean skipSubresources, @jakarta.annotation.Nullable Boolean unassignIssuables) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdMembersUserIdRequestCreation(id, userId, skipSubresources, unassignIssuables).body(localVarReturnType);
    }

    /**
     * Remove a member from a group
     * Removes a specified user from a group. The user must be a direct member.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @param skipSubresources If &#x60;true&#x60;, skips deleting direct memberships of the removed member in subgroups and projects.
     * @param unassignIssuables If &#x60;true&#x60;, unassigns the member from any issues or merge requests in the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdMembersUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean skipSubresources, @jakarta.annotation.Nullable Boolean unassignIssuables) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdMembersUserIdRequestCreation(id, userId, skipSubresources, unassignIssuables).toEntity(localVarReturnType);
    }

    /**
     * Remove a member from a group
     * Removes a specified user from a group. The user must be a direct member.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @param skipSubresources If &#x60;true&#x60;, skips deleting direct memberships of the removed member in subgroups and projects.
     * @param unassignIssuables If &#x60;true&#x60;, unassigns the member from any issues or merge requests in the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdMembersUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean skipSubresources, @jakarta.annotation.Nullable Boolean unassignIssuables) throws RestClientResponseException {
        return deleteApiV4GroupsIdMembersUserIdRequestCreation(id, userId, skipSubresources, unassignIssuables);
    }

    /**
     * Remove an LDAP access level override
     * Removes an LDAP access level override for a member of a group. Sets the override flag to &#x60;false&#x60; and allows LDAP Group Sync to reset the access level to the LDAP-prescribed value.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdMembersUserIdOverrideRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdMembersUserIdOverride", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4GroupsIdMembersUserIdOverride", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/{user_id}/override", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove an LDAP access level override
     * Removes an LDAP access level override for a member of a group. Sets the override flag to &#x60;false&#x60; and allows LDAP Group Sync to reset the access level to the LDAP-prescribed value.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember deleteApiV4GroupsIdMembersUserIdOverride(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdMembersUserIdOverrideRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Remove an LDAP access level override
     * Removes an LDAP access level override for a member of a group. Sets the override flag to &#x60;false&#x60; and allows LDAP Group Sync to reset the access level to the LDAP-prescribed value.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> deleteApiV4GroupsIdMembersUserIdOverrideWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdMembersUserIdOverrideRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Remove an LDAP access level override
     * Removes an LDAP access level override for a member of a group. Sets the override flag to &#x60;false&#x60; and allows LDAP Group Sync to reset the access level to the LDAP-prescribed value.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdMembersUserIdOverrideWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return deleteApiV4GroupsIdMembersUserIdOverrideRequestCreation(id, userId);
    }

    /**
     * Remove a member from a project
     * Removes a specified user from a project. The user must be a direct member.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @param skipSubresources If &#x60;true&#x60;, skips deleting direct memberships of the removed member in subgroups and projects.
     * @param unassignIssuables If &#x60;true&#x60;, unassigns the member from any issues or merge requests in the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMembersUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean skipSubresources, @jakarta.annotation.Nullable Boolean unassignIssuables) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteApiV4ProjectsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "skip_subresources", skipSubresources));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "unassign_issuables", unassignIssuables));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/members/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove a member from a project
     * Removes a specified user from a project. The user must be a direct member.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @param skipSubresources If &#x60;true&#x60;, skips deleting direct memberships of the removed member in subgroups and projects.
     * @param unassignIssuables If &#x60;true&#x60;, unassigns the member from any issues or merge requests in the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdMembersUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean skipSubresources, @jakarta.annotation.Nullable Boolean unassignIssuables) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdMembersUserIdRequestCreation(id, userId, skipSubresources, unassignIssuables).body(localVarReturnType);
    }

    /**
     * Remove a member from a project
     * Removes a specified user from a project. The user must be a direct member.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @param skipSubresources If &#x60;true&#x60;, skips deleting direct memberships of the removed member in subgroups and projects.
     * @param unassignIssuables If &#x60;true&#x60;, unassigns the member from any issues or merge requests in the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdMembersUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean skipSubresources, @jakarta.annotation.Nullable Boolean unassignIssuables) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMembersUserIdRequestCreation(id, userId, skipSubresources, unassignIssuables).toEntity(localVarReturnType);
    }

    /**
     * Remove a member from a project
     * Removes a specified user from a project. The user must be a direct member.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @param skipSubresources If &#x60;true&#x60;, skips deleting direct memberships of the removed member in subgroups and projects.
     * @param unassignIssuables If &#x60;true&#x60;, unassigns the member from any issues or merge requests in the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMembersUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Boolean skipSubresources, @jakarta.annotation.Nullable Boolean unassignIssuables) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMembersUserIdRequestCreation(id, userId, skipSubresources, unassignIssuables);
    }

    /**
     * List all indirect memberships for a billable group member
     * Lists all indirect memberships for a billable member of a group. Indirect memberships are the projects and groups that the user is a member of and that were invited to the requested top-level group. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBillableMembership&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBillableMembersUserIdIndirectRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBillableMembersUserIdIndirect", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4GroupsIdBillableMembersUserIdIndirect", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<List<APIEntitiesBillableMembership>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/billable_members/{user_id}/indirect", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all indirect memberships for a billable group member
     * Lists all indirect memberships for a billable member of a group. Indirect memberships are the projects and groups that the user is a member of and that were invited to the requested top-level group. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBillableMembership&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBillableMembership> getApiV4GroupsIdBillableMembersUserIdIndirect(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBillableMembership>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBillableMembersUserIdIndirectRequestCreation(id, userId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all indirect memberships for a billable group member
     * Lists all indirect memberships for a billable member of a group. Indirect memberships are the projects and groups that the user is a member of and that were invited to the requested top-level group. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBillableMembership&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBillableMembership>> getApiV4GroupsIdBillableMembersUserIdIndirectWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBillableMembership>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBillableMembersUserIdIndirectRequestCreation(id, userId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all indirect memberships for a billable group member
     * Lists all indirect memberships for a billable member of a group. Indirect memberships are the projects and groups that the user is a member of and that were invited to the requested top-level group. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBillableMembersUserIdIndirectWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdBillableMembersUserIdIndirectRequestCreation(id, userId, page, perPage);
    }

    /**
     * List all memberships for a billable group member
     * Lists all projects and groups that a specified billable member of a group is a member of. Only projects and groups in the group hierarchy are included. The response represents only direct memberships. Inherited memberships are not included. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBillableMembership&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBillableMembersUserIdMembershipsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBillableMembersUserIdMemberships", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4GroupsIdBillableMembersUserIdMemberships", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<List<APIEntitiesBillableMembership>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/billable_members/{user_id}/memberships", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all memberships for a billable group member
     * Lists all projects and groups that a specified billable member of a group is a member of. Only projects and groups in the group hierarchy are included. The response represents only direct memberships. Inherited memberships are not included. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesBillableMembership&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBillableMembership> getApiV4GroupsIdBillableMembersUserIdMemberships(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBillableMembership>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBillableMembersUserIdMembershipsRequestCreation(id, userId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all memberships for a billable group member
     * Lists all projects and groups that a specified billable member of a group is a member of. Only projects and groups in the group hierarchy are included. The response represents only direct memberships. Inherited memberships are not included. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBillableMembership&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBillableMembership>> getApiV4GroupsIdBillableMembersUserIdMembershipsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBillableMembership>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBillableMembersUserIdMembershipsRequestCreation(id, userId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all memberships for a billable group member
     * Lists all projects and groups that a specified billable member of a group is a member of. Only projects and groups in the group hierarchy are included. The response represents only direct memberships. Inherited memberships are not included. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBillableMembersUserIdMembershipsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdBillableMembersUserIdMembershipsRequestCreation(id, userId, page, perPage);
    }

    /**
     * List all direct members of a group
     * Lists all direct members of a specified group viewable by the authenticated user. Does not return inherited members from ancestor groups or invited groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param skipUsers Filter skipped users out of the results.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param withSamlIdentity If &#x60;true&#x60;, returns only members with a linked SAML identity.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMembersRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable List<Integer> skipUsers, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable Boolean withSamlIdentity, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMembers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "user_ids", userIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "skip_users", skipUsers));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "show_seat_info", showSeatInfo));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_saml_identity", withSamlIdentity));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/members", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all direct members of a group
     * Lists all direct members of a specified group viewable by the authenticated user. Does not return inherited members from ancestor groups or invited groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param skipUsers Filter skipped users out of the results.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param withSamlIdentity If &#x60;true&#x60;, returns only members with a linked SAML identity.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMember> getApiV4GroupsIdMembers(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable List<Integer> skipUsers, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable Boolean withSamlIdentity, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMembersRequestCreation(id, query, userIds, skipUsers, showSeatInfo, withSamlIdentity, page, perPage).body(localVarReturnType);
    }

    /**
     * List all direct members of a group
     * Lists all direct members of a specified group viewable by the authenticated user. Does not return inherited members from ancestor groups or invited groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param skipUsers Filter skipped users out of the results.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param withSamlIdentity If &#x60;true&#x60;, returns only members with a linked SAML identity.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMember&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMember>> getApiV4GroupsIdMembersWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable List<Integer> skipUsers, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable Boolean withSamlIdentity, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMembersRequestCreation(id, query, userIds, skipUsers, showSeatInfo, withSamlIdentity, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all direct members of a group
     * Lists all direct members of a specified group viewable by the authenticated user. Does not return inherited members from ancestor groups or invited groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param skipUsers Filter skipped users out of the results.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param withSamlIdentity If &#x60;true&#x60;, returns only members with a linked SAML identity.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMembersWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable List<Integer> skipUsers, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable Boolean withSamlIdentity, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdMembersRequestCreation(id, query, userIds, skipUsers, showSeatInfo, withSamlIdentity, page, perPage);
    }

    /**
     * List all members of a group
     * Lists all members of a specified group viewable by the authenticated user. Also returns inherited members from ancestor groups or invited groups. If a user is a member of this group and one or more ancestor groups, only returns the highest &#x60;access_level&#x60;. Members from an invited group are returned if the invited group is public, the requester is a member of an invited group, or the requester is a member of the shared group or project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param state Filter results by member state. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMembersAllRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMembersAll", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "user_ids", userIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "show_seat_info", showSeatInfo));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/all", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all members of a group
     * Lists all members of a specified group viewable by the authenticated user. Also returns inherited members from ancestor groups or invited groups. If a user is a member of this group and one or more ancestor groups, only returns the highest &#x60;access_level&#x60;. Members from an invited group are returned if the invited group is public, the requester is a member of an invited group, or the requester is a member of the shared group or project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param state Filter results by member state. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMember> getApiV4GroupsIdMembersAll(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMembersAllRequestCreation(id, query, userIds, showSeatInfo, state, page, perPage).body(localVarReturnType);
    }

    /**
     * List all members of a group
     * Lists all members of a specified group viewable by the authenticated user. Also returns inherited members from ancestor groups or invited groups. If a user is a member of this group and one or more ancestor groups, only returns the highest &#x60;access_level&#x60;. Members from an invited group are returned if the invited group is public, the requester is a member of an invited group, or the requester is a member of the shared group or project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param state Filter results by member state. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMember&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMember>> getApiV4GroupsIdMembersAllWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMembersAllRequestCreation(id, query, userIds, showSeatInfo, state, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all members of a group
     * Lists all members of a specified group viewable by the authenticated user. Also returns inherited members from ancestor groups or invited groups. If a user is a member of this group and one or more ancestor groups, only returns the highest &#x60;access_level&#x60;. Members from an invited group are returned if the invited group is public, the requester is a member of an invited group, or the requester is a member of the shared group or project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param state Filter results by member state. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMembersAllWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdMembersAllRequestCreation(id, query, userIds, showSeatInfo, state, page, perPage);
    }

    /**
     * Retrieve a group member
     * Retrieves a specified member of a group. Returns direct members and members inherited or invited through ancestor groups. Members of an invited private group are returned if the requester is a member of the shared group or project, even if the requester is not a member of the invited private group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMembersAllUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMembersAllUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4GroupsIdMembersAllUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/all/{user_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group member
     * Retrieves a specified member of a group. Returns direct members and members inherited or invited through ancestor groups. Members of an invited private group are returned if the requester is a member of the shared group or project, even if the requester is not a member of the invited private group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember getApiV4GroupsIdMembersAllUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMembersAllUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Retrieve a group member
     * Retrieves a specified member of a group. Returns direct members and members inherited or invited through ancestor groups. Members of an invited private group are returned if the requester is a member of the shared group or project, even if the requester is not a member of the invited private group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> getApiV4GroupsIdMembersAllUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMembersAllUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group member
     * Retrieves a specified member of a group. Returns direct members and members inherited or invited through ancestor groups. Members of an invited private group are returned if the requester is a member of the shared group or project, even if the requester is not a member of the invited private group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMembersAllUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return getApiV4GroupsIdMembersAllUserIdRequestCreation(id, userId);
    }

    /**
     * Retrieve a direct group member
     * Retrieves a specified member of a group. Returns only direct members and not inherited members through ancestor groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdMembersUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4GroupsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/{user_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a direct group member
     * Retrieves a specified member of a group. Returns only direct members and not inherited members through ancestor groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember getApiV4GroupsIdMembersUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMembersUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Retrieve a direct group member
     * Retrieves a specified member of a group. Returns only direct members and not inherited members through ancestor groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> getApiV4GroupsIdMembersUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdMembersUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a direct group member
     * Retrieves a specified member of a group. Returns only direct members and not inherited members through ancestor groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdMembersUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return getApiV4GroupsIdMembersUserIdRequestCreation(id, userId);
    }

    /**
     * List all pending group members
     * Lists all members in an &#x60;awaiting&#x60; state and those who are invited but do not have a GitLab account for a specified group and any subgroups and projects. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group. For an invited user who has not signed up for a GitLab account yet, the invited email address is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPendingMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdPendingMembersRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdPendingMembers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesPendingMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/pending_members", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all pending group members
     * Lists all members in an &#x60;awaiting&#x60; state and those who are invited but do not have a GitLab account for a specified group and any subgroups and projects. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group. For an invited user who has not signed up for a GitLab account yet, the invited email address is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPendingMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPendingMember> getApiV4GroupsIdPendingMembers(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPendingMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdPendingMembersRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all pending group members
     * Lists all members in an &#x60;awaiting&#x60; state and those who are invited but do not have a GitLab account for a specified group and any subgroups and projects. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group. For an invited user who has not signed up for a GitLab account yet, the invited email address is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPendingMember&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPendingMember>> getApiV4GroupsIdPendingMembersWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPendingMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdPendingMembersRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all pending group members
     * Lists all members in an &#x60;awaiting&#x60; state and those who are invited but do not have a GitLab account for a specified group and any subgroups and projects. This operation works on top-level groups only. It does not work on subgroups. You must have permission to administer members of the group. For an invited user who has not signed up for a GitLab account yet, the invited email address is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdPendingMembersWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdPendingMembersRequestCreation(id, page, perPage);
    }

    /**
     * List all direct members of a project
     * Lists all direct members of a specified project viewable by the authenticated user. Does not return inherited members from ancestor groups or invited groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param skipUsers Filter skipped users out of the results.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param withSamlIdentity If &#x60;true&#x60;, returns only members with a linked SAML identity.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMembersRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable List<Integer> skipUsers, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable Boolean withSamlIdentity, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMembers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "user_ids", userIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "skip_users", skipUsers));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "show_seat_info", showSeatInfo));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_saml_identity", withSamlIdentity));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/members", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all direct members of a project
     * Lists all direct members of a specified project viewable by the authenticated user. Does not return inherited members from ancestor groups or invited groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param skipUsers Filter skipped users out of the results.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param withSamlIdentity If &#x60;true&#x60;, returns only members with a linked SAML identity.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMember> getApiV4ProjectsIdMembers(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable List<Integer> skipUsers, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable Boolean withSamlIdentity, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMembersRequestCreation(id, query, userIds, skipUsers, showSeatInfo, withSamlIdentity, page, perPage).body(localVarReturnType);
    }

    /**
     * List all direct members of a project
     * Lists all direct members of a specified project viewable by the authenticated user. Does not return inherited members from ancestor groups or invited groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param skipUsers Filter skipped users out of the results.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param withSamlIdentity If &#x60;true&#x60;, returns only members with a linked SAML identity.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMember&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMember>> getApiV4ProjectsIdMembersWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable List<Integer> skipUsers, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable Boolean withSamlIdentity, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMembersRequestCreation(id, query, userIds, skipUsers, showSeatInfo, withSamlIdentity, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all direct members of a project
     * Lists all direct members of a specified project viewable by the authenticated user. Does not return inherited members from ancestor groups or invited groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param skipUsers Filter skipped users out of the results.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param withSamlIdentity If &#x60;true&#x60;, returns only members with a linked SAML identity.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMembersWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable List<Integer> skipUsers, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable Boolean withSamlIdentity, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMembersRequestCreation(id, query, userIds, skipUsers, showSeatInfo, withSamlIdentity, page, perPage);
    }

    /**
     * List all members of a project
     * Lists all members of a specified project viewable by the authenticated user. Also returns inherited members from ancestor groups or invited groups. If a user is a member of this project and one or more ancestor groups, only returns the highest &#x60;access_level&#x60;. Members from an invited group are returned if the invited group is public, the requester is a member of an invited group, or the requester is a member of the shared group or project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param state Filter results by member state. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMembersAllRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMembersAll", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "user_ids", userIds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "show_seat_info", showSeatInfo));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/members/all", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all members of a project
     * Lists all members of a specified project viewable by the authenticated user. Also returns inherited members from ancestor groups or invited groups. If a user is a member of this project and one or more ancestor groups, only returns the highest &#x60;access_level&#x60;. Members from an invited group are returned if the invited group is public, the requester is a member of an invited group, or the requester is a member of the shared group or project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param state Filter results by member state. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMember> getApiV4ProjectsIdMembersAll(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMembersAllRequestCreation(id, query, userIds, showSeatInfo, state, page, perPage).body(localVarReturnType);
    }

    /**
     * List all members of a project
     * Lists all members of a specified project viewable by the authenticated user. Also returns inherited members from ancestor groups or invited groups. If a user is a member of this project and one or more ancestor groups, only returns the highest &#x60;access_level&#x60;. Members from an invited group are returned if the invited group is public, the requester is a member of an invited group, or the requester is a member of the shared group or project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param state Filter results by member state. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMember&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMember>> getApiV4ProjectsIdMembersAllWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMember>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMembersAllRequestCreation(id, query, userIds, showSeatInfo, state, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all members of a project
     * Lists all members of a specified project viewable by the authenticated user. Also returns inherited members from ancestor groups or invited groups. If a user is a member of this project and one or more ancestor groups, only returns the highest &#x60;access_level&#x60;. Members from an invited group are returned if the invited group is public, the requester is a member of an invited group, or the requester is a member of the shared group or project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param query Filter results by name, email, or username. Use partial values to widen the scope of the query.
     * @param userIds Filter results by the given user IDs.
     * @param showSeatInfo If &#x60;true&#x60;, shows seat information for members.
     * @param state Filter results by member state. Premium and Ultimate only.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMembersAllWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable List<Integer> userIds, @jakarta.annotation.Nullable Boolean showSeatInfo, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMembersAllRequestCreation(id, query, userIds, showSeatInfo, state, page, perPage);
    }

    /**
     * Retrieve a project member
     * Retrieves a specified member of a project. Returns direct members and members inherited or invited through ancestor groups. Members of an invited private group are returned if the requester is a member of the shared group or project, even if the requester is not a member of the invited private group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMembersAllUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMembersAllUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4ProjectsIdMembersAllUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/members/all/{user_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a project member
     * Retrieves a specified member of a project. Returns direct members and members inherited or invited through ancestor groups. Members of an invited private group are returned if the requester is a member of the shared group or project, even if the requester is not a member of the invited private group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember getApiV4ProjectsIdMembersAllUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMembersAllUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Retrieve a project member
     * Retrieves a specified member of a project. Returns direct members and members inherited or invited through ancestor groups. Members of an invited private group are returned if the requester is a member of the shared group or project, even if the requester is not a member of the invited private group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> getApiV4ProjectsIdMembersAllUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMembersAllUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a project member
     * Retrieves a specified member of a project. Returns direct members and members inherited or invited through ancestor groups. Members of an invited private group are returned if the requester is a member of the shared group or project, even if the requester is not a member of the invited private group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMembersAllUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return getApiV4ProjectsIdMembersAllUserIdRequestCreation(id, userId);
    }

    /**
     * Retrieve a direct project member
     * Retrieves a specified member of a project. Returns only direct members and not inherited members through ancestor groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMembersUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4ProjectsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/members/{user_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a direct project member
     * Retrieves a specified member of a project. Returns only direct members and not inherited members through ancestor groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember getApiV4ProjectsIdMembersUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMembersUserIdRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Retrieve a direct project member
     * Retrieves a specified member of a project. Returns only direct members and not inherited members through ancestor groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> getApiV4ProjectsIdMembersUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMembersUserIdRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a direct project member
     * Retrieves a specified member of a project. Returns only direct members and not inherited members through ancestor groups.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMembersUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return getApiV4ProjectsIdMembersUserIdRequestCreation(id, userId);
    }

    /**
     * Add a member to a group
     * Adds a direct member to a specified group. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When a request for a single user is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;. When requests for multiple users are queued, the response has a &#x60;queued_users&#x60; object with the same mapping and a &#x60;status&#x60; of &#x60;success&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMembersRequest The postApiV4GroupsIdMembersRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdMembersRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMembersRequest postApiV4GroupsIdMembersRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdMembers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdMembersRequest' is set
        if (postApiV4GroupsIdMembersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdMembersRequest' when calling postApiV4GroupsIdMembers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/members", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdMembersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a member to a group
     * Adds a direct member to a specified group. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When a request for a single user is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;. When requests for multiple users are queued, the response has a &#x60;queued_users&#x60; object with the same mapping and a &#x60;status&#x60; of &#x60;success&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMembersRequest The postApiV4GroupsIdMembersRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember postApiV4GroupsIdMembers(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMembersRequest postApiV4GroupsIdMembersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdMembersRequestCreation(id, postApiV4GroupsIdMembersRequest).body(localVarReturnType);
    }

    /**
     * Add a member to a group
     * Adds a direct member to a specified group. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When a request for a single user is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;. When requests for multiple users are queued, the response has a &#x60;queued_users&#x60; object with the same mapping and a &#x60;status&#x60; of &#x60;success&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMembersRequest The postApiV4GroupsIdMembersRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> postApiV4GroupsIdMembersWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMembersRequest postApiV4GroupsIdMembersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdMembersRequestCreation(id, postApiV4GroupsIdMembersRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a member to a group
     * Adds a direct member to a specified group. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When a request for a single user is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;. When requests for multiple users are queued, the response has a &#x60;queued_users&#x60; object with the same mapping and a &#x60;status&#x60; of &#x60;success&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdMembersRequest The postApiV4GroupsIdMembersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdMembersWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdMembersRequest postApiV4GroupsIdMembersRequest) throws RestClientResponseException {
        return postApiV4GroupsIdMembersRequestCreation(id, postApiV4GroupsIdMembersRequest);
    }

    /**
     * Approve all pending group members
     * Approves all pending users for a specified top-level group and any subgroups or projects.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdMembersApproveAllRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdMembersApproveAll", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/approve_all", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Approve all pending group members
     * Approves all pending users for a specified top-level group and any subgroups or projects.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdMembersApproveAll(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdMembersApproveAllRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Approve all pending group members
     * Approves all pending users for a specified top-level group and any subgroups or projects.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdMembersApproveAllWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdMembersApproveAllRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Approve all pending group members
     * Approves all pending users for a specified top-level group and any subgroups or projects.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdMembersApproveAllWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4GroupsIdMembersApproveAllRequestCreation(id);
    }

    /**
     * Set override flag for a member of a group
     * Sets the override flag for a member of a group. By default, the access level of LDAP group members is set to the value specified by LDAP through Group Sync.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdMembersUserIdOverrideRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdMembersUserIdOverride", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling postApiV4GroupsIdMembersUserIdOverride", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/{user_id}/override", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Set override flag for a member of a group
     * Sets the override flag for a member of a group. By default, the access level of LDAP group members is set to the value specified by LDAP through Group Sync.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember postApiV4GroupsIdMembersUserIdOverride(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdMembersUserIdOverrideRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Set override flag for a member of a group
     * Sets the override flag for a member of a group. By default, the access level of LDAP group members is set to the value specified by LDAP through Group Sync.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> postApiV4GroupsIdMembersUserIdOverrideWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdMembersUserIdOverrideRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Set override flag for a member of a group
     * Sets the override flag for a member of a group. By default, the access level of LDAP group members is set to the value specified by LDAP through Group Sync.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdMembersUserIdOverrideWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return postApiV4GroupsIdMembersUserIdOverrideRequestCreation(id, userId);
    }

    /**
     * Add a member to a project
     * Adds a direct member to a specified project. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When a request for a single user is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;. When requests for multiple users are queued, the response has a &#x60;queued_users&#x60; object with the same mapping and a &#x60;status&#x60; of &#x60;success&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMembersRequest The postApiV4ProjectsIdMembersRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMembersRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMembersRequest postApiV4ProjectsIdMembersRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMembers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMembersRequest' is set
        if (postApiV4ProjectsIdMembersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMembersRequest' when calling postApiV4ProjectsIdMembers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/members", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMembersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a member to a project
     * Adds a direct member to a specified project. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When a request for a single user is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;. When requests for multiple users are queued, the response has a &#x60;queued_users&#x60; object with the same mapping and a &#x60;status&#x60; of &#x60;success&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMembersRequest The postApiV4ProjectsIdMembersRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember postApiV4ProjectsIdMembers(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMembersRequest postApiV4ProjectsIdMembersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMembersRequestCreation(id, postApiV4ProjectsIdMembersRequest).body(localVarReturnType);
    }

    /**
     * Add a member to a project
     * Adds a direct member to a specified project. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When a request for a single user is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;. When requests for multiple users are queued, the response has a &#x60;queued_users&#x60; object with the same mapping and a &#x60;status&#x60; of &#x60;success&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMembersRequest The postApiV4ProjectsIdMembersRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> postApiV4ProjectsIdMembersWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMembersRequest postApiV4ProjectsIdMembersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMembersRequestCreation(id, postApiV4ProjectsIdMembersRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a member to a project
     * Adds a direct member to a specified project. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When a request for a single user is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;. When requests for multiple users are queued, the response has a &#x60;queued_users&#x60; object with the same mapping and a &#x60;status&#x60; of &#x60;success&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMembersRequest The postApiV4ProjectsIdMembersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMembersWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMembersRequest postApiV4ProjectsIdMembersRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMembersRequestCreation(id, postApiV4ProjectsIdMembersRequest);
    }

    /**
     * Approve a group member
     * Approves a specified pending user for a top-level group and any subgroups or projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param memberId ID of the member.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdMembersMemberIdApproveRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer memberId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdMembersMemberIdApprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'memberId' is set
        if (memberId == null) {
            throw new RestClientResponseException("Missing the required parameter 'memberId' when calling putApiV4GroupsIdMembersMemberIdApprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("member_id", memberId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/{member_id}/approve", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Approve a group member
     * Approves a specified pending user for a top-level group and any subgroups or projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param memberId ID of the member.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4GroupsIdMembersMemberIdApprove(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer memberId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4GroupsIdMembersMemberIdApproveRequestCreation(id, memberId).body(localVarReturnType);
    }

    /**
     * Approve a group member
     * Approves a specified pending user for a top-level group and any subgroups or projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param memberId ID of the member.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4GroupsIdMembersMemberIdApproveWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer memberId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdMembersMemberIdApproveRequestCreation(id, memberId).toEntity(localVarReturnType);
    }

    /**
     * Approve a group member
     * Approves a specified pending user for a top-level group and any subgroups or projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param memberId ID of the member.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdMembersMemberIdApproveWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer memberId) throws RestClientResponseException {
        return putApiV4GroupsIdMembersMemberIdApproveRequestCreation(id, memberId);
    }

    /**
     * Update a group member
     * Updates a specified member of a group. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When the request is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @param putApiV4GroupsIdMembersUserIdRequest The putApiV4GroupsIdMembersUserIdRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdMembersUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4GroupsIdMembersUserIdRequest putApiV4GroupsIdMembersUserIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling putApiV4GroupsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4GroupsIdMembersUserIdRequest' is set
        if (putApiV4GroupsIdMembersUserIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4GroupsIdMembersUserIdRequest' when calling putApiV4GroupsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/{user_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdMembersUserIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group member
     * Updates a specified member of a group. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When the request is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @param putApiV4GroupsIdMembersUserIdRequest The putApiV4GroupsIdMembersUserIdRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember putApiV4GroupsIdMembersUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4GroupsIdMembersUserIdRequest putApiV4GroupsIdMembersUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdMembersUserIdRequestCreation(id, userId, putApiV4GroupsIdMembersUserIdRequest).body(localVarReturnType);
    }

    /**
     * Update a group member
     * Updates a specified member of a group. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When the request is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @param putApiV4GroupsIdMembersUserIdRequest The putApiV4GroupsIdMembersUserIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> putApiV4GroupsIdMembersUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4GroupsIdMembersUserIdRequest putApiV4GroupsIdMembersUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdMembersUserIdRequestCreation(id, userId, putApiV4GroupsIdMembersUserIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group member
     * Updates a specified member of a group. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When the request is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param userId User ID of the member.
     * @param putApiV4GroupsIdMembersUserIdRequest The putApiV4GroupsIdMembersUserIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdMembersUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4GroupsIdMembersUserIdRequest putApiV4GroupsIdMembersUserIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdMembersUserIdRequestCreation(id, userId, putApiV4GroupsIdMembersUserIdRequest);
    }

    /**
     * Update group membership state for a user
     * Updates the membership state for a specified user in a group. When a user is over the free user limit, changing their membership state to &#x60;awaiting&#x60; or &#x60;active&#x60; can allow them to access the group or project. The change is applied to all subgroups and projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param putApiV4GroupsIdMembersUserIdStateRequest The putApiV4GroupsIdMembersUserIdStateRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdMembersUserIdStateRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4GroupsIdMembersUserIdStateRequest putApiV4GroupsIdMembersUserIdStateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdMembersUserIdState", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling putApiV4GroupsIdMembersUserIdState", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4GroupsIdMembersUserIdStateRequest' is set
        if (putApiV4GroupsIdMembersUserIdStateRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4GroupsIdMembersUserIdStateRequest' when calling putApiV4GroupsIdMembersUserIdState", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/members/{user_id}/state", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdMembersUserIdStateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update group membership state for a user
     * Updates the membership state for a specified user in a group. When a user is over the free user limit, changing their membership state to &#x60;awaiting&#x60; or &#x60;active&#x60; can allow them to access the group or project. The change is applied to all subgroups and projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param putApiV4GroupsIdMembersUserIdStateRequest The putApiV4GroupsIdMembersUserIdStateRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4GroupsIdMembersUserIdState(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4GroupsIdMembersUserIdStateRequest putApiV4GroupsIdMembersUserIdStateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4GroupsIdMembersUserIdStateRequestCreation(id, userId, putApiV4GroupsIdMembersUserIdStateRequest).body(localVarReturnType);
    }

    /**
     * Update group membership state for a user
     * Updates the membership state for a specified user in a group. When a user is over the free user limit, changing their membership state to &#x60;awaiting&#x60; or &#x60;active&#x60; can allow them to access the group or project. The change is applied to all subgroups and projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param putApiV4GroupsIdMembersUserIdStateRequest The putApiV4GroupsIdMembersUserIdStateRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4GroupsIdMembersUserIdStateWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4GroupsIdMembersUserIdStateRequest putApiV4GroupsIdMembersUserIdStateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdMembersUserIdStateRequestCreation(id, userId, putApiV4GroupsIdMembersUserIdStateRequest).toEntity(localVarReturnType);
    }

    /**
     * Update group membership state for a user
     * Updates the membership state for a specified user in a group. When a user is over the free user limit, changing their membership state to &#x60;awaiting&#x60; or &#x60;active&#x60; can allow them to access the group or project. The change is applied to all subgroups and projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param userId ID of the user.
     * @param putApiV4GroupsIdMembersUserIdStateRequest The putApiV4GroupsIdMembersUserIdStateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdMembersUserIdStateWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4GroupsIdMembersUserIdStateRequest putApiV4GroupsIdMembersUserIdStateRequest) throws RestClientResponseException {
        return putApiV4GroupsIdMembersUserIdStateRequestCreation(id, userId, putApiV4GroupsIdMembersUserIdStateRequest);
    }

    /**
     * Update a project member
     * Updates a specified member of a project. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When the request is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @param putApiV4ProjectsIdMembersUserIdRequest The putApiV4ProjectsIdMembersUserIdRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMembersUserIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdMembersUserIdRequest putApiV4ProjectsIdMembersUserIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling putApiV4ProjectsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdMembersUserIdRequest' is set
        if (putApiV4ProjectsIdMembersUserIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdMembersUserIdRequest' when calling putApiV4ProjectsIdMembersUserId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/members/{user_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMembersUserIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a project member
     * Updates a specified member of a project. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When the request is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @param putApiV4ProjectsIdMembersUserIdRequest The putApiV4ProjectsIdMembersUserIdRequest parameter
     * @return APIEntitiesMember
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMember putApiV4ProjectsIdMembersUserId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdMembersUserIdRequest putApiV4ProjectsIdMembersUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMembersUserIdRequestCreation(id, userId, putApiV4ProjectsIdMembersUserIdRequest).body(localVarReturnType);
    }

    /**
     * Update a project member
     * Updates a specified member of a project. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When the request is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @param putApiV4ProjectsIdMembersUserIdRequest The putApiV4ProjectsIdMembersUserIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMember&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMember> putApiV4ProjectsIdMembersUserIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdMembersUserIdRequest putApiV4ProjectsIdMembersUserIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMember> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMembersUserIdRequestCreation(id, userId, putApiV4ProjectsIdMembersUserIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a project member
     * Updates a specified member of a project. If administrator approval for role promotions is turned on, requests that promote existing users into a billable role require administrator approval. Administrator approval for role promotions can be turned on only when the &#x60;enable_member_promotion_management&#x60; application setting is enabled. When the request is queued, the response has a &#x60;message&#x60; object that maps the username to &#x60;Request queued for administrator approval.&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param userId User ID of the member.
     * @param putApiV4ProjectsIdMembersUserIdRequest The putApiV4ProjectsIdMembersUserIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMembersUserIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdMembersUserIdRequest putApiV4ProjectsIdMembersUserIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMembersUserIdRequestCreation(id, userId, putApiV4ProjectsIdMembersUserIdRequest);
    }
}
