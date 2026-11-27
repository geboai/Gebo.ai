package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesInvitation;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdInvitationsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdInvitationsRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdInvitationsEmailRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdInvitationsEmailRequest;

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
public class InvitationsApi {
    private ApiClient apiClient;

    public InvitationsApi() {
        this(new ApiClient());
    }

    public InvitationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an invitation to a group
     * Deletes a pending invitation to a specified email address for a group.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Could not delete invitation
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param email Email address the invitation was previously sent to.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdInvitationsEmailRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdInvitationsEmail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'email' is set
        if (email == null) {
            throw new RestClientResponseException("Missing the required parameter 'email' when calling deleteApiV4GroupsIdInvitationsEmail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("email", email);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/invitations/{email}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an invitation to a group
     * Deletes a pending invitation to a specified email address for a group.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Could not delete invitation
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param email Email address the invitation was previously sent to.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdInvitationsEmail(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdInvitationsEmailRequestCreation(id, email).body(localVarReturnType);
    }

    /**
     * Delete an invitation to a group
     * Deletes a pending invitation to a specified email address for a group.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Could not delete invitation
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param email Email address the invitation was previously sent to.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdInvitationsEmailWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdInvitationsEmailRequestCreation(id, email).toEntity(localVarReturnType);
    }

    /**
     * Delete an invitation to a group
     * Deletes a pending invitation to a specified email address for a group.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Could not delete invitation
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param email Email address the invitation was previously sent to.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdInvitationsEmailWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email) throws RestClientResponseException {
        return deleteApiV4GroupsIdInvitationsEmailRequestCreation(id, email);
    }

    /**
     * Delete an invitation to a project
     * Deletes a pending invitation to a specified email address for a project.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Could not delete invitation
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param email Email address the invitation was previously sent to.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdInvitationsEmailRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdInvitationsEmail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'email' is set
        if (email == null) {
            throw new RestClientResponseException("Missing the required parameter 'email' when calling deleteApiV4ProjectsIdInvitationsEmail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("email", email);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/invitations/{email}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an invitation to a project
     * Deletes a pending invitation to a specified email address for a project.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Could not delete invitation
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param email Email address the invitation was previously sent to.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdInvitationsEmail(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdInvitationsEmailRequestCreation(id, email).body(localVarReturnType);
    }

    /**
     * Delete an invitation to a project
     * Deletes a pending invitation to a specified email address for a project.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Could not delete invitation
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param email Email address the invitation was previously sent to.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdInvitationsEmailWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdInvitationsEmailRequestCreation(id, email).toEntity(localVarReturnType);
    }

    /**
     * Delete an invitation to a project
     * Deletes a pending invitation to a specified email address for a project.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Could not delete invitation
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param email Email address the invitation was previously sent to.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdInvitationsEmailWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email) throws RestClientResponseException {
        return deleteApiV4ProjectsIdInvitationsEmailRequestCreation(id, email);
    }

    /**
     * List all pending invitations for a group
     * Lists all pending invitations for a specified group viewable by the authenticated user. Returns invitations to direct members only, and not through inherited ancestor groups. This function takes pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; to restrict the list of members.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param query Return only invitations sent to this email address. Must match exactly. If omitted, returns all invitations.
     * @return List&lt;APIEntitiesInvitation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdInvitationsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String query) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdInvitations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesInvitation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/invitations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all pending invitations for a group
     * Lists all pending invitations for a specified group viewable by the authenticated user. Returns invitations to direct members only, and not through inherited ancestor groups. This function takes pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; to restrict the list of members.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param query Return only invitations sent to this email address. Must match exactly. If omitted, returns all invitations.
     * @return List&lt;APIEntitiesInvitation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesInvitation> getApiV4GroupsIdInvitations(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String query) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesInvitation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdInvitationsRequestCreation(id, page, perPage, query).body(localVarReturnType);
    }

    /**
     * List all pending invitations for a group
     * Lists all pending invitations for a specified group viewable by the authenticated user. Returns invitations to direct members only, and not through inherited ancestor groups. This function takes pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; to restrict the list of members.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param query Return only invitations sent to this email address. Must match exactly. If omitted, returns all invitations.
     * @return ResponseEntity&lt;List&lt;APIEntitiesInvitation&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesInvitation>> getApiV4GroupsIdInvitationsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String query) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesInvitation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdInvitationsRequestCreation(id, page, perPage, query).toEntity(localVarReturnType);
    }

    /**
     * List all pending invitations for a group
     * Lists all pending invitations for a specified group viewable by the authenticated user. Returns invitations to direct members only, and not through inherited ancestor groups. This function takes pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; to restrict the list of members.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param query Return only invitations sent to this email address. Must match exactly. If omitted, returns all invitations.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdInvitationsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String query) throws RestClientResponseException {
        return getApiV4GroupsIdInvitationsRequestCreation(id, page, perPage, query);
    }

    /**
     * List all pending invitations for a project
     * Lists all pending invitations for a specified project viewable by the authenticated user. Returns invitations to direct members only, and not through inherited ancestor groups. This function takes pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; to restrict the list of members.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param query Return only invitations sent to this email address. Must match exactly. If omitted, returns all invitations.
     * @return List&lt;APIEntitiesInvitation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdInvitationsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String query) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdInvitations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesInvitation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/invitations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all pending invitations for a project
     * Lists all pending invitations for a specified project viewable by the authenticated user. Returns invitations to direct members only, and not through inherited ancestor groups. This function takes pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; to restrict the list of members.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param query Return only invitations sent to this email address. Must match exactly. If omitted, returns all invitations.
     * @return List&lt;APIEntitiesInvitation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesInvitation> getApiV4ProjectsIdInvitations(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String query) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesInvitation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdInvitationsRequestCreation(id, page, perPage, query).body(localVarReturnType);
    }

    /**
     * List all pending invitations for a project
     * Lists all pending invitations for a specified project viewable by the authenticated user. Returns invitations to direct members only, and not through inherited ancestor groups. This function takes pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; to restrict the list of members.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param query Return only invitations sent to this email address. Must match exactly. If omitted, returns all invitations.
     * @return ResponseEntity&lt;List&lt;APIEntitiesInvitation&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesInvitation>> getApiV4ProjectsIdInvitationsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String query) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesInvitation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdInvitationsRequestCreation(id, page, perPage, query).toEntity(localVarReturnType);
    }

    /**
     * List all pending invitations for a project
     * Lists all pending invitations for a specified project viewable by the authenticated user. Returns invitations to direct members only, and not through inherited ancestor groups. This function takes pagination parameters &#x60;page&#x60; and &#x60;per_page&#x60; to restrict the list of members.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param query Return only invitations sent to this email address. Must match exactly. If omitted, returns all invitations.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdInvitationsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String query) throws RestClientResponseException {
        return getApiV4ProjectsIdInvitationsRequestCreation(id, page, perPage, query);
    }

    /**
     * Add a member to a group
     * Adds a member to a group. You can specify a user ID or invite a user by email. You must have the Owner role for the group. On GitLab Self-Managed, an administrator must add the user if new user accounts or user invitations are not allowed, and must approve the invitation if administrator approval for role promotions is enabled. If an invitation fails, the response has a &#x60;status&#x60; of &#x60;error&#x60; and a &#x60;message&#x60; object that maps each email address or username to its error. To enable Manage non-billable promotions, enable the &#x60;enable_member_promotion_management&#x60; application setting. Users whose requests are queued for administrator approval are listed in &#x60;queued_users&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdInvitationsRequest The postApiV4GroupsIdInvitationsRequest parameter
     * @return APIEntitiesInvitation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdInvitationsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdInvitationsRequest postApiV4GroupsIdInvitationsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdInvitations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdInvitationsRequest' is set
        if (postApiV4GroupsIdInvitationsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdInvitationsRequest' when calling postApiV4GroupsIdInvitations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/invitations", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdInvitationsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a member to a group
     * Adds a member to a group. You can specify a user ID or invite a user by email. You must have the Owner role for the group. On GitLab Self-Managed, an administrator must add the user if new user accounts or user invitations are not allowed, and must approve the invitation if administrator approval for role promotions is enabled. If an invitation fails, the response has a &#x60;status&#x60; of &#x60;error&#x60; and a &#x60;message&#x60; object that maps each email address or username to its error. To enable Manage non-billable promotions, enable the &#x60;enable_member_promotion_management&#x60; application setting. Users whose requests are queued for administrator approval are listed in &#x60;queued_users&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdInvitationsRequest The postApiV4GroupsIdInvitationsRequest parameter
     * @return APIEntitiesInvitation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesInvitation postApiV4GroupsIdInvitations(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdInvitationsRequest postApiV4GroupsIdInvitationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdInvitationsRequestCreation(id, postApiV4GroupsIdInvitationsRequest).body(localVarReturnType);
    }

    /**
     * Add a member to a group
     * Adds a member to a group. You can specify a user ID or invite a user by email. You must have the Owner role for the group. On GitLab Self-Managed, an administrator must add the user if new user accounts or user invitations are not allowed, and must approve the invitation if administrator approval for role promotions is enabled. If an invitation fails, the response has a &#x60;status&#x60; of &#x60;error&#x60; and a &#x60;message&#x60; object that maps each email address or username to its error. To enable Manage non-billable promotions, enable the &#x60;enable_member_promotion_management&#x60; application setting. Users whose requests are queued for administrator approval are listed in &#x60;queued_users&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdInvitationsRequest The postApiV4GroupsIdInvitationsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesInvitation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesInvitation> postApiV4GroupsIdInvitationsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdInvitationsRequest postApiV4GroupsIdInvitationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdInvitationsRequestCreation(id, postApiV4GroupsIdInvitationsRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a member to a group
     * Adds a member to a group. You can specify a user ID or invite a user by email. You must have the Owner role for the group. On GitLab Self-Managed, an administrator must add the user if new user accounts or user invitations are not allowed, and must approve the invitation if administrator approval for role promotions is enabled. If an invitation fails, the response has a &#x60;status&#x60; of &#x60;error&#x60; and a &#x60;message&#x60; object that maps each email address or username to its error. To enable Manage non-billable promotions, enable the &#x60;enable_member_promotion_management&#x60; application setting. Users whose requests are queued for administrator approval are listed in &#x60;queued_users&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdInvitationsRequest The postApiV4GroupsIdInvitationsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdInvitationsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdInvitationsRequest postApiV4GroupsIdInvitationsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdInvitationsRequestCreation(id, postApiV4GroupsIdInvitationsRequest);
    }

    /**
     * Add a member to a project
     * Adds a member to a project. You can specify a user ID or invite a user by email. You must have the Maintainer or Owner role for the project, and group membership lock must be disabled. On GitLab Self-Managed, an administrator must add the user if new user accounts or user invitations are not allowed, and must approve the invitation if administrator approval for role promotions is enabled. If an invitation fails, the response has a &#x60;status&#x60; of &#x60;error&#x60; and a &#x60;message&#x60; object that maps each email address or username to its error. To enable Manage non-billable promotions, enable the &#x60;enable_member_promotion_management&#x60; application setting. Users whose requests are queued for administrator approval are listed in &#x60;queued_users&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdInvitationsRequest The postApiV4ProjectsIdInvitationsRequest parameter
     * @return APIEntitiesInvitation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdInvitationsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdInvitationsRequest postApiV4ProjectsIdInvitationsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdInvitations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdInvitationsRequest' is set
        if (postApiV4ProjectsIdInvitationsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdInvitationsRequest' when calling postApiV4ProjectsIdInvitations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/invitations", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdInvitationsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a member to a project
     * Adds a member to a project. You can specify a user ID or invite a user by email. You must have the Maintainer or Owner role for the project, and group membership lock must be disabled. On GitLab Self-Managed, an administrator must add the user if new user accounts or user invitations are not allowed, and must approve the invitation if administrator approval for role promotions is enabled. If an invitation fails, the response has a &#x60;status&#x60; of &#x60;error&#x60; and a &#x60;message&#x60; object that maps each email address or username to its error. To enable Manage non-billable promotions, enable the &#x60;enable_member_promotion_management&#x60; application setting. Users whose requests are queued for administrator approval are listed in &#x60;queued_users&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdInvitationsRequest The postApiV4ProjectsIdInvitationsRequest parameter
     * @return APIEntitiesInvitation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesInvitation postApiV4ProjectsIdInvitations(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdInvitationsRequest postApiV4ProjectsIdInvitationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdInvitationsRequestCreation(id, postApiV4ProjectsIdInvitationsRequest).body(localVarReturnType);
    }

    /**
     * Add a member to a project
     * Adds a member to a project. You can specify a user ID or invite a user by email. You must have the Maintainer or Owner role for the project, and group membership lock must be disabled. On GitLab Self-Managed, an administrator must add the user if new user accounts or user invitations are not allowed, and must approve the invitation if administrator approval for role promotions is enabled. If an invitation fails, the response has a &#x60;status&#x60; of &#x60;error&#x60; and a &#x60;message&#x60; object that maps each email address or username to its error. To enable Manage non-billable promotions, enable the &#x60;enable_member_promotion_management&#x60; application setting. Users whose requests are queued for administrator approval are listed in &#x60;queued_users&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdInvitationsRequest The postApiV4ProjectsIdInvitationsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesInvitation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesInvitation> postApiV4ProjectsIdInvitationsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdInvitationsRequest postApiV4ProjectsIdInvitationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdInvitationsRequestCreation(id, postApiV4ProjectsIdInvitationsRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a member to a project
     * Adds a member to a project. You can specify a user ID or invite a user by email. You must have the Maintainer or Owner role for the project, and group membership lock must be disabled. On GitLab Self-Managed, an administrator must add the user if new user accounts or user invitations are not allowed, and must approve the invitation if administrator approval for role promotions is enabled. If an invitation fails, the response has a &#x60;status&#x60; of &#x60;error&#x60; and a &#x60;message&#x60; object that maps each email address or username to its error. To enable Manage non-billable promotions, enable the &#x60;enable_member_promotion_management&#x60; application setting. Users whose requests are queued for administrator approval are listed in &#x60;queued_users&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdInvitationsRequest The postApiV4ProjectsIdInvitationsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdInvitationsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdInvitationsRequest postApiV4ProjectsIdInvitationsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdInvitationsRequestCreation(id, postApiV4ProjectsIdInvitationsRequest);
    }

    /**
     * Update an invitation to a group
     * Updates a pending invitation to a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param email Email address the invitation was previously sent to.
     * @param putApiV4GroupsIdInvitationsEmailRequest The putApiV4GroupsIdInvitationsEmailRequest parameter
     * @return APIEntitiesInvitation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdInvitationsEmailRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email, @jakarta.annotation.Nullable PutApiV4GroupsIdInvitationsEmailRequest putApiV4GroupsIdInvitationsEmailRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdInvitationsEmail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'email' is set
        if (email == null) {
            throw new RestClientResponseException("Missing the required parameter 'email' when calling putApiV4GroupsIdInvitationsEmail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("email", email);

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

        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/invitations/{email}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdInvitationsEmailRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an invitation to a group
     * Updates a pending invitation to a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param email Email address the invitation was previously sent to.
     * @param putApiV4GroupsIdInvitationsEmailRequest The putApiV4GroupsIdInvitationsEmailRequest parameter
     * @return APIEntitiesInvitation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesInvitation putApiV4GroupsIdInvitationsEmail(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email, @jakarta.annotation.Nullable PutApiV4GroupsIdInvitationsEmailRequest putApiV4GroupsIdInvitationsEmailRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdInvitationsEmailRequestCreation(id, email, putApiV4GroupsIdInvitationsEmailRequest).body(localVarReturnType);
    }

    /**
     * Update an invitation to a group
     * Updates a pending invitation to a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param email Email address the invitation was previously sent to.
     * @param putApiV4GroupsIdInvitationsEmailRequest The putApiV4GroupsIdInvitationsEmailRequest parameter
     * @return ResponseEntity&lt;APIEntitiesInvitation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesInvitation> putApiV4GroupsIdInvitationsEmailWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email, @jakarta.annotation.Nullable PutApiV4GroupsIdInvitationsEmailRequest putApiV4GroupsIdInvitationsEmailRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdInvitationsEmailRequestCreation(id, email, putApiV4GroupsIdInvitationsEmailRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an invitation to a group
     * Updates a pending invitation to a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param email Email address the invitation was previously sent to.
     * @param putApiV4GroupsIdInvitationsEmailRequest The putApiV4GroupsIdInvitationsEmailRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdInvitationsEmailWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email, @jakarta.annotation.Nullable PutApiV4GroupsIdInvitationsEmailRequest putApiV4GroupsIdInvitationsEmailRequest) throws RestClientResponseException {
        return putApiV4GroupsIdInvitationsEmailRequestCreation(id, email, putApiV4GroupsIdInvitationsEmailRequest);
    }

    /**
     * Update an invitation to a project
     * Updates a pending invitation to a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param email Email address the invitation was previously sent to.
     * @param putApiV4ProjectsIdInvitationsEmailRequest The putApiV4ProjectsIdInvitationsEmailRequest parameter
     * @return APIEntitiesInvitation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdInvitationsEmailRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email, @jakarta.annotation.Nullable PutApiV4ProjectsIdInvitationsEmailRequest putApiV4ProjectsIdInvitationsEmailRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdInvitationsEmail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'email' is set
        if (email == null) {
            throw new RestClientResponseException("Missing the required parameter 'email' when calling putApiV4ProjectsIdInvitationsEmail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("email", email);

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

        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/invitations/{email}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdInvitationsEmailRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an invitation to a project
     * Updates a pending invitation to a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param email Email address the invitation was previously sent to.
     * @param putApiV4ProjectsIdInvitationsEmailRequest The putApiV4ProjectsIdInvitationsEmailRequest parameter
     * @return APIEntitiesInvitation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesInvitation putApiV4ProjectsIdInvitationsEmail(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email, @jakarta.annotation.Nullable PutApiV4ProjectsIdInvitationsEmailRequest putApiV4ProjectsIdInvitationsEmailRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdInvitationsEmailRequestCreation(id, email, putApiV4ProjectsIdInvitationsEmailRequest).body(localVarReturnType);
    }

    /**
     * Update an invitation to a project
     * Updates a pending invitation to a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param email Email address the invitation was previously sent to.
     * @param putApiV4ProjectsIdInvitationsEmailRequest The putApiV4ProjectsIdInvitationsEmailRequest parameter
     * @return ResponseEntity&lt;APIEntitiesInvitation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesInvitation> putApiV4ProjectsIdInvitationsEmailWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email, @jakarta.annotation.Nullable PutApiV4ProjectsIdInvitationsEmailRequest putApiV4ProjectsIdInvitationsEmailRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesInvitation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdInvitationsEmailRequestCreation(id, email, putApiV4ProjectsIdInvitationsEmailRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an invitation to a project
     * Updates a pending invitation to a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param email Email address the invitation was previously sent to.
     * @param putApiV4ProjectsIdInvitationsEmailRequest The putApiV4ProjectsIdInvitationsEmailRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdInvitationsEmailWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String email, @jakarta.annotation.Nullable PutApiV4ProjectsIdInvitationsEmailRequest putApiV4ProjectsIdInvitationsEmailRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdInvitationsEmailRequestCreation(id, email, putApiV4ProjectsIdInvitationsEmailRequest);
    }
}
