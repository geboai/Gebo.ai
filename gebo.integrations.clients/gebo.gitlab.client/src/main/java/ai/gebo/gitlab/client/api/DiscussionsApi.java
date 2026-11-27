package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesDiscussion;
import ai.gebo.gitlab.client.model.APIEntitiesNote;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdEpicsNoteableIdDiscussionsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest;

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
public class DiscussionsApi {
    private ApiClient apiClient;

    public DiscussionsApi() {
        this(new ApiClient());
    }

    public DiscussionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a comment from an epic discussion
     * Deletes a specified comment from a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a comment from an epic discussion
     * Deletes a specified comment from a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a comment from an epic discussion
     * Deletes a specified comment from a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a comment from an epic discussion
     * Deletes a specified comment from a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * Delete a comment from an issue discussion
     * Deletes a specified comment from a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a comment from an issue discussion
     * Deletes a specified comment from a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a comment from an issue discussion
     * Deletes a specified comment from a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a comment from an issue discussion
     * Deletes a specified comment from a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * Delete a comment from a merge request discussion
     * Deletes a specified comment from a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a comment from a merge request discussion
     * Deletes a specified comment from a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a comment from a merge request discussion
     * Deletes a specified comment from a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a comment from a merge request discussion
     * Deletes a specified comment from a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * Delete a comment from a commit discussion
     * Deletes a specified comment from a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a comment from a commit discussion
     * Deletes a specified comment from a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a comment from a commit discussion
     * Deletes a specified comment from a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a comment from a commit discussion
     * Deletes a specified comment from a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * Delete a comment from a snippet discussion
     * Deletes a specified comment from a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a comment from a snippet discussion
     * Deletes a specified comment from a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a comment from a snippet discussion
     * Deletes a specified comment from a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a comment from a snippet discussion
     * Deletes a specified comment from a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * List all discussions for an epic
     * Lists all discussions for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4GroupsIdEpicsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/discussions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all discussions for an epic
     * Lists all discussions for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDiscussion> getApiV4GroupsIdEpicsNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all discussions for an epic
     * Lists all discussions for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDiscussion&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDiscussion>> getApiV4GroupsIdEpicsNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all discussions for an epic
     * Lists all discussions for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage);
    }

    /**
     * Retrieve a discussion for an epic
     * Retrieves a specified discussion for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the epic.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/discussions/{discussion_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a discussion for an epic
     * Retrieves a specified discussion for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the epic.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a discussion for an epic
     * Retrieves a specified discussion for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the epic.
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a discussion for an epic
     * Retrieves a specified discussion for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId);
    }

    /**
     * List comments in an epic discussion
     * Lists the comments in a specified epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the epic.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List comments in an epic discussion
     * Lists the comments in a specified epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the epic.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * List comments in an epic discussion
     * Lists the comments in a specified epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the epic.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * List comments in an epic discussion
     * Lists the comments in a specified epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId);
    }

    /**
     * Retrieve a comment from an epic discussion
     * Retrieves a specified comment from a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a comment from an epic discussion
     * Retrieves a specified comment from a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Retrieve a comment from an epic discussion
     * Retrieves a specified comment from a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a comment from an epic discussion
     * Retrieves a specified comment from a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * List all discussions for an issue
     * Lists all discussions for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/discussions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all discussions for an issue
     * Lists all discussions for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDiscussion> getApiV4ProjectsIdIssuesNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all discussions for an issue
     * Lists all discussions for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDiscussion&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDiscussion>> getApiV4ProjectsIdIssuesNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all discussions for an issue
     * Lists all discussions for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage);
    }

    /**
     * Retrieve a discussion for an issue
     * Retrieves a specified discussion for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the issue.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/discussions/{discussion_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a discussion for an issue
     * Retrieves a specified discussion for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the issue.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a discussion for an issue
     * Retrieves a specified discussion for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the issue.
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a discussion for an issue
     * Retrieves a specified discussion for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId);
    }

    /**
     * List comments in an issue discussion
     * Lists the comments in a specified issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the issue.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List comments in an issue discussion
     * Lists the comments in a specified issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the issue.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * List comments in an issue discussion
     * Lists the comments in a specified issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the issue.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * List comments in an issue discussion
     * Lists the comments in a specified issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId);
    }

    /**
     * Retrieve a comment from an issue discussion
     * Retrieves a specified comment from a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a comment from an issue discussion
     * Retrieves a specified comment from a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Retrieve a comment from an issue discussion
     * Retrieves a specified comment from a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a comment from an issue discussion
     * Retrieves a specified comment from a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * List all discussions for a merge request
     * Lists all discussions for a specified merge request. Diff comments also contain position information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/discussions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all discussions for a merge request
     * Lists all discussions for a specified merge request. Diff comments also contain position information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDiscussion> getApiV4ProjectsIdMergeRequestsNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all discussions for a merge request
     * Lists all discussions for a specified merge request. Diff comments also contain position information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDiscussion&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDiscussion>> getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all discussions for a merge request
     * Lists all discussions for a specified merge request. Diff comments also contain position information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage);
    }

    /**
     * Retrieve a discussion for a merge request
     * Retrieves a specified discussion for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the merge request.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/discussions/{discussion_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a discussion for a merge request
     * Retrieves a specified discussion for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the merge request.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a discussion for a merge request
     * Retrieves a specified discussion for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the merge request.
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a discussion for a merge request
     * Retrieves a specified discussion for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId);
    }

    /**
     * List comments in a merge request discussion
     * Lists the comments in a specified merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the merge request.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List comments in a merge request discussion
     * Lists the comments in a specified merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the merge request.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * List comments in a merge request discussion
     * Lists the comments in a specified merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the merge request.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * List comments in a merge request discussion
     * Lists the comments in a specified merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId IID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId);
    }

    /**
     * Retrieve a comment from a merge request discussion
     * Retrieves a specified comment from a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a comment from a merge request discussion
     * Retrieves a specified comment from a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Retrieve a comment from a merge request discussion
     * Retrieves a specified comment from a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a comment from a merge request discussion
     * Retrieves a specified comment from a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * List all discussions for a commit
     * Lists all discussions for a specified commit. Diff comments also contain position information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{noteable_id}/discussions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all discussions for a commit
     * Lists all discussions for a specified commit. Diff comments also contain position information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDiscussion> getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all discussions for a commit
     * Lists all discussions for a specified commit. Diff comments also contain position information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDiscussion&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDiscussion>> getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all discussions for a commit
     * Lists all discussions for a specified commit. Diff comments also contain position information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage);
    }

    /**
     * Retrieve a discussion for a commit
     * Retrieves a specified discussion for a commit.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull String noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{noteable_id}/discussions/{discussion_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a discussion for a commit
     * Retrieves a specified discussion for a commit.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull String noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a discussion for a commit
     * Retrieves a specified discussion for a commit.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull String noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a discussion for a commit
     * Retrieves a specified discussion for a commit.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull String noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId);
    }

    /**
     * List comments in a commit discussion
     * Lists the comments in a specified commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull String noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List comments in a commit discussion
     * Lists the comments in a specified commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull String noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * List comments in a commit discussion
     * Lists the comments in a specified commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull String noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * List comments in a commit discussion
     * Lists the comments in a specified commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull String noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId);
    }

    /**
     * Retrieve a comment from a commit discussion
     * Retrieves a specified comment from a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a comment from a commit discussion
     * Retrieves a specified comment from a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Retrieve a comment from a commit discussion
     * Retrieves a specified comment from a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a comment from a commit discussion
     * Retrieves a specified comment from a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * List all discussions for a snippet
     * Lists all discussions for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/discussions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all discussions for a snippet
     * Lists all discussions for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDiscussion> getApiV4ProjectsIdSnippetsNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all discussions for a snippet
     * Lists all discussions for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDiscussion&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDiscussion>> getApiV4ProjectsIdSnippetsNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDiscussion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all discussions for a snippet
     * Lists all discussions for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequestCreation(id, noteableId, page, perPage);
    }

    /**
     * Retrieve a discussion for a snippet
     * Retrieves a specified discussion for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the snippet.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/discussions/{discussion_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a discussion for a snippet
     * Retrieves a specified discussion for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the snippet.
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a discussion for a snippet
     * Retrieves a specified discussion for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the snippet.
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a discussion for a snippet
     * Retrieves a specified discussion for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdRequestCreation(id, discussionId, noteableId);
    }

    /**
     * List comments in a snippet discussion
     * Lists the comments in a specified snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the snippet.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List comments in a snippet discussion
     * Lists the comments in a specified snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the snippet.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).body(localVarReturnType);
    }

    /**
     * List comments in a snippet discussion
     * Lists the comments in a specified snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the snippet.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * List comments in a snippet discussion
     * Lists the comments in a specified snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param discussionId ID of the discussion.
     * @param noteableId ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, discussionId, noteableId);
    }

    /**
     * Retrieve a comment from a snippet discussion
     * Retrieves a specified comment from a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a comment from a snippet discussion
     * Retrieves a specified comment from a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).body(localVarReturnType);
    }

    /**
     * Retrieve a comment from a snippet discussion
     * Retrieves a specified comment from a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a comment from a snippet discussion
     * Retrieves a specified comment from a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId);
    }

    /**
     * Create an epic discussion
     * Creates a discussion for a specified epic. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest The postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdEpicsNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdDiscussionsRequest postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdEpicsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4GroupsIdEpicsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest' is set
        if (postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest' when calling postApiV4GroupsIdEpicsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/discussions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an epic discussion
     * Creates a discussion for a specified epic. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest The postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion postApiV4GroupsIdEpicsNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdDiscussionsRequest postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest).body(localVarReturnType);
    }

    /**
     * Create an epic discussion
     * Creates a discussion for a specified epic. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest The postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> postApiV4GroupsIdEpicsNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdDiscussionsRequest postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an epic discussion
     * Creates a discussion for a specified epic. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest The postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdEpicsNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdDiscussionsRequest postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdEpicsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4GroupsIdEpicsNoteableIdDiscussionsRequest);
    }

    /**
     * Add a comment to an epic discussion
     * Adds a comment to a specified epic discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest' is set
        if (postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest' when calling postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a comment to an epic discussion
     * Adds a comment to a specified epic discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Add a comment to an epic discussion
     * Adds a comment to a specified epic discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a comment to an epic discussion
     * Adds a comment to a specified epic discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesRequest);
    }

    /**
     * Create an issue discussion
     * Creates a discussion for a specified issue. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest The postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdIssuesNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest' is set
        if (postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest' when calling postApiV4ProjectsIdIssuesNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/discussions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an issue discussion
     * Creates a discussion for a specified issue. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest The postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion postApiV4ProjectsIdIssuesNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest).body(localVarReturnType);
    }

    /**
     * Create an issue discussion
     * Creates a discussion for a specified issue. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest The postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> postApiV4ProjectsIdIssuesNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an issue discussion
     * Creates a discussion for a specified issue. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest The postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdIssuesNoteableIdDiscussionsRequest);
    }

    /**
     * Add a comment to an issue discussion
     * Adds a comment to a specified issue discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest' is set
        if (postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest' when calling postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a comment to an issue discussion
     * Adds a comment to a specified issue discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Add a comment to an issue discussion
     * Adds a comment to a specified issue discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a comment to an issue discussion
     * Adds a comment to a specified issue discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesRequest);
    }

    /**
     * Create a merge request discussion
     * Creates a discussion for a specified merge request. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest The postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdMergeRequestsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest' is set
        if (postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest' when calling postApiV4ProjectsIdMergeRequestsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/discussions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a merge request discussion
     * Creates a discussion for a specified merge request. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest The postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion postApiV4ProjectsIdMergeRequestsNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest).body(localVarReturnType);
    }

    /**
     * Create a merge request discussion
     * Creates a discussion for a specified merge request. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest The postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a merge request discussion
     * Creates a discussion for a specified merge request. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest The postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsRequest);
    }

    /**
     * Add a comment to a merge request discussion
     * Adds a comment to a specified merge request discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest' is set
        if (postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest' when calling postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a comment to a merge request discussion
     * Adds a comment to a specified merge request discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Add a comment to a merge request discussion
     * Adds a comment to a specified merge request discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a comment to a merge request discussion
     * Adds a comment to a specified merge request discussion. If the discussion is a single comment, the comment becomes a thread.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesRequest);
    }

    /**
     * Create a commit discussion
     * Creates a discussion for a specified commit. Replies can be added to it later. The position rules are the same as for merge request diff threads, except for &#x60;position[base_sha]&#x60;, &#x60;position[head_sha]&#x60;, and &#x60;position[start_sha]&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest The postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest' is set
        if (postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest' when calling postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{noteable_id}/discussions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a commit discussion
     * Creates a discussion for a specified commit. Replies can be added to it later. The position rules are the same as for merge request diff threads, except for &#x60;position[base_sha]&#x60;, &#x60;position[head_sha]&#x60;, and &#x60;position[start_sha]&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest The postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest).body(localVarReturnType);
    }

    /**
     * Create a commit discussion
     * Creates a discussion for a specified commit. Replies can be added to it later. The position rules are the same as for merge request diff threads, except for &#x60;position[base_sha]&#x60;, &#x60;position[head_sha]&#x60;, and &#x60;position[start_sha]&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest The postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a commit discussion
     * Creates a discussion for a specified commit. Replies can be added to it later. The position rules are the same as for merge request diff threads, except for &#x60;position[base_sha]&#x60;, &#x60;position[head_sha]&#x60;, and &#x60;position[start_sha]&#x60;.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest The postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsRequest);
    }

    /**
     * Add a comment to a commit discussion
     * Adds a comment to a specified commit discussion.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest' is set
        if (postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest' when calling postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a comment to a commit discussion
     * Adds a comment to a specified commit discussion.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Add a comment to a commit discussion
     * Adds a comment to a specified commit discussion.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a comment to a commit discussion
     * Adds a comment to a specified commit discussion.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesRequest);
    }

    /**
     * Create a snippet discussion
     * Creates a discussion for a specified snippet. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest The postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdSnippetsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdSnippetsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest' is set
        if (postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest' when calling postApiV4ProjectsIdSnippetsNoteableIdDiscussions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/discussions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a snippet discussion
     * Creates a discussion for a specified snippet. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest The postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion postApiV4ProjectsIdSnippetsNoteableIdDiscussions(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest).body(localVarReturnType);
    }

    /**
     * Create a snippet discussion
     * Creates a discussion for a specified snippet. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest The postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> postApiV4ProjectsIdSnippetsNoteableIdDiscussionsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a snippet discussion
     * Creates a discussion for a specified snippet. Replies can be added to it later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest The postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdSnippetsNoteableIdDiscussionsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequestCreation(id, noteableId, postApiV4ProjectsIdSnippetsNoteableIdDiscussionsRequest);
    }

    /**
     * Add a comment to a snippet discussion
     * Adds a comment to a specified snippet discussion.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest' is set
        if (postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest' when calling postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/discussions/{discussion_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a comment to a snippet discussion
     * Adds a comment to a specified snippet discussion.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Add a comment to a snippet discussion
     * Adds a comment to a specified snippet discussion.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a comment to a snippet discussion
     * Adds a comment to a specified snippet discussion.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest The postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequestCreation(id, noteableId, discussionId, postApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesRequest);
    }

    /**
     * Resolve or unresolve an epic discussion
     * Resolves or unresolves a specified epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest The putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest' is set
        if (putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest' when calling putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/discussions/{discussion_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Resolve or unresolve an epic discussion
     * Resolves or unresolves a specified epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest The putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequestCreation(id, noteableId, discussionId, putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest).body(localVarReturnType);
    }

    /**
     * Resolve or unresolve an epic discussion
     * Resolves or unresolves a specified epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest The putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequestCreation(id, noteableId, discussionId, putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Resolve or unresolve an epic discussion
     * Resolves or unresolves a specified epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest The putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequestCreation(id, noteableId, discussionId, putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdRequest);
    }

    /**
     * Update a comment in an epic discussion
     * Updates or changes the status of a specified comment in a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a comment in an epic discussion
     * Updates or changes the status of a specified comment in a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a comment in an epic discussion
     * Updates or changes the status of a specified comment in a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a comment in an epic discussion
     * Updates or changes the status of a specified comment in a epic discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4GroupsIdEpicsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest);
    }

    /**
     * Resolve or unresolve an issue discussion
     * Resolves or unresolves a specified issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest The putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest' is set
        if (putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest' when calling putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/discussions/{discussion_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Resolve or unresolve an issue discussion
     * Resolves or unresolves a specified issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest The putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequestCreation(id, noteableId, discussionId, putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest).body(localVarReturnType);
    }

    /**
     * Resolve or unresolve an issue discussion
     * Resolves or unresolves a specified issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest The putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequestCreation(id, noteableId, discussionId, putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Resolve or unresolve an issue discussion
     * Resolves or unresolves a specified issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest The putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequestCreation(id, noteableId, discussionId, putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdRequest);
    }

    /**
     * Update a comment in an issue discussion
     * Updates or changes the status of a specified comment in a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a comment in an issue discussion
     * Updates or changes the status of a specified comment in a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a comment in an issue discussion
     * Updates or changes the status of a specified comment in a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a comment in an issue discussion
     * Updates or changes the status of a specified comment in a issue discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdIssuesNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest);
    }

    /**
     * Resolve or unresolve a merge request discussion
     * Resolves or unresolves a specified merge request discussion. Requires at least the Developer role for the project. The merge request author can also resolve discussions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest' is set
        if (putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest' when calling putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);

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

        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/discussions/{discussion_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Resolve or unresolve a merge request discussion
     * Resolves or unresolves a specified merge request discussion. Requires at least the Developer role for the project. The merge request author can also resolve discussions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return APIEntitiesDiscussion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscussion putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequestCreation(id, noteableId, discussionId, putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest).body(localVarReturnType);
    }

    /**
     * Resolve or unresolve a merge request discussion
     * Resolves or unresolves a specified merge request discussion. Requires at least the Developer role for the project. The merge request author can also resolve discussions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDiscussion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscussion> putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscussion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequestCreation(id, noteableId, discussionId, putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Resolve or unresolve a merge request discussion
     * Resolves or unresolves a specified merge request discussion. Requires at least the Developer role for the project. The merge request author can also resolve discussions.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequestCreation(id, noteableId, discussionId, putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdRequest);
    }

    /**
     * Update a comment in a merge request discussion
     * Updates or changes the status of a specified comment in a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a comment in a merge request discussion
     * Updates or changes the status of a specified comment in a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a comment in a merge request discussion
     * Updates or changes the status of a specified comment in a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a comment in a merge request discussion
     * Updates or changes the status of a specified comment in a merge request discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdMergeRequestsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest);
    }

    /**
     * Update a comment in a commit discussion
     * Updates or changes the status of a specified comment in a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/commits/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a comment in a commit discussion
     * Updates or changes the status of a specified comment in a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a comment in a commit discussion
     * Updates or changes the status of a specified comment in a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a comment in a commit discussion
     * Updates or changes the status of a specified comment in a commit discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId SHA of the commit, or name of a repository branch or tag.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdRepositoryCommitsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest);
    }

    /**
     * Update a comment in a snippet discussion
     * Updates or changes the status of a specified comment in a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'discussionId' is set
        if (discussionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'discussionId' when calling putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
        pathParams.put("discussion_id", discussionId);
        pathParams.put("note_id", noteId);

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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/discussions/{discussion_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a comment in a snippet discussion
     * Updates or changes the status of a specified comment in a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a comment in a snippet discussion
     * Updates or changes the status of a specified comment in a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a comment in a snippet discussion
     * Updates or changes the status of a specified comment in a snippet discussion.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the snippet.
     * @param discussionId ID of the discussion.
     * @param noteId ID of the discussion note.
     * @param putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest The putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull String discussionId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequestCreation(id, noteableId, discussionId, noteId, putApiV4ProjectsIdSnippetsNoteableIdDiscussionsDiscussionIdNotesNoteIdRequest);
    }
}
