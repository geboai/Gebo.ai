package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesNote;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdEpicsNoteableIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdWikiPagesNoteableIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdIssuesNoteableIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdSnippetsNoteableIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdWikiPagesNoteableIdNotesRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest;

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
public class NotesApi {
    private ApiClient apiClient;

    public NotesApi() {
        this(new ApiClient());
    }

    public NotesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an epic note
     * Deletes a specified note from an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdEpicsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4GroupsIdEpicsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4GroupsIdEpicsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an epic note
     * Deletes a specified note from an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4GroupsIdEpicsNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).body(localVarReturnType);
    }

    /**
     * Delete an epic note
     * Deletes a specified note from an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4GroupsIdEpicsNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete an epic note
     * Deletes a specified note from an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdEpicsNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId);
    }

    /**
     * Delete a wiki page meta note
     * Deletes a specified note from a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/wiki_pages/{noteable_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a wiki page meta note
     * Deletes a specified note from a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a wiki page meta note
     * Deletes a specified note from a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a wiki page meta note
     * Deletes a specified note from a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId);
    }

    /**
     * Delete an issue note
     * Deletes a specified note from an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an issue note
     * Deletes a specified note from an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).body(localVarReturnType);
    }

    /**
     * Delete an issue note
     * Deletes a specified note from an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete an issue note
     * Deletes a specified note from an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId);
    }

    /**
     * Delete a merge request note
     * Deletes a specified note from a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a merge request note
     * Deletes a specified note from a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a merge request note
     * Deletes a specified note from a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a merge request note
     * Deletes a specified note from a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId);
    }

    /**
     * Delete a snippet note
     * Deletes a specified note from a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a snippet note
     * Deletes a specified note from a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a snippet note
     * Deletes a specified note from a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a snippet note
     * Deletes a specified note from a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId);
    }

    /**
     * Delete a vulnerability note
     * Deletes a specified note from a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/vulnerabilities/{noteable_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a vulnerability note
     * Deletes a specified note from a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a vulnerability note
     * Deletes a specified note from a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a vulnerability note
     * Deletes a specified note from a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId);
    }

    /**
     * Delete a wiki page meta note
     * Deletes a specified note from a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/wiki_pages/{noteable_id}/notes/{note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a wiki page meta note
     * Deletes a specified note from a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).body(localVarReturnType);
    }

    /**
     * Delete a wiki page meta note
     * Deletes a specified note from a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a wiki page meta note
     * Deletes a specified note from a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId);
    }

    /**
     * List all epic notes
     * Lists all notes for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4GroupsIdEpicsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activity_filter", activityFilter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all epic notes
     * Lists all notes for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4GroupsIdEpicsNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all epic notes
     * Lists all notes for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4GroupsIdEpicsNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all epic notes
     * Lists all notes for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage);
    }

    /**
     * Retrieve an epic note
     * Retrieves a specified note for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteId ID of the note.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4GroupsIdEpicsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4GroupsIdEpicsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("note_id", noteId);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an epic note
     * Retrieves a specified note for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteId ID of the note.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4GroupsIdEpicsNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve an epic note
     * Retrieves a specified note for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteId ID of the note.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4GroupsIdEpicsNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an epic note
     * Retrieves a specified note for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteId ID of the note.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId);
    }

    /**
     * List all wiki page meta notes
     * Lists all notes for a specified wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdWikiPagesNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4GroupsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activity_filter", activityFilter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/wiki_pages/{noteable_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all wiki page meta notes
     * Lists all notes for a specified wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4GroupsIdWikiPagesNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all wiki page meta notes
     * Lists all notes for a specified wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4GroupsIdWikiPagesNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all wiki page meta notes
     * Lists all notes for a specified wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdWikiPagesNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage);
    }

    /**
     * Retrieve a wiki page meta note
     * Retrieves a specified note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteId ID of the note.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4GroupsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4GroupsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("note_id", noteId);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/wiki_pages/{noteable_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a wiki page meta note
     * Retrieves a specified note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteId ID of the note.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4GroupsIdWikiPagesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a wiki page meta note
     * Retrieves a specified note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteId ID of the note.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a wiki page meta note
     * Retrieves a specified note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteId ID of the note.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId);
    }

    /**
     * List all issue notes
     * Lists all notes for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdIssuesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activity_filter", activityFilter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issue notes
     * Lists all notes for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4ProjectsIdIssuesNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all issue notes
     * Lists all notes for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4ProjectsIdIssuesNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all issue notes
     * Lists all notes for a specified issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage);
    }

    /**
     * Retrieve an issue note
     * Retrieves a specified note for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId IID of the issue.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdIssuesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdIssuesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("note_id", noteId);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an issue note
     * Retrieves a specified note for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId IID of the issue.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4ProjectsIdIssuesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve an issue note
     * Retrieves a specified note for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId IID of the issue.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4ProjectsIdIssuesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an issue note
     * Retrieves a specified note for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId IID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId);
    }

    /**
     * List all merge request notes
     * Lists all notes for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activity_filter", activityFilter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge request notes
     * Lists all notes for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4ProjectsIdMergeRequestsNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all merge request notes
     * Lists all notes for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4ProjectsIdMergeRequestsNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all merge request notes
     * Lists all notes for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage);
    }

    /**
     * Retrieve a merge request note
     * Retrieves a specified note for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId IID of the merge request.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("note_id", noteId);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a merge request note
     * Retrieves a specified note for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId IID of the merge request.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a merge request note
     * Retrieves a specified note for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId IID of the merge request.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a merge request note
     * Retrieves a specified note for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId IID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId);
    }

    /**
     * List all snippet notes
     * Lists all notes for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdSnippetsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activity_filter", activityFilter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all snippet notes
     * Lists all notes for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4ProjectsIdSnippetsNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all snippet notes
     * Lists all notes for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4ProjectsIdSnippetsNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all snippet notes
     * Lists all notes for a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage);
    }

    /**
     * Retrieve a snippet note
     * Retrieves a specified note for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the resource the note is attached to.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdSnippetsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdSnippetsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("note_id", noteId);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a snippet note
     * Retrieves a specified note for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the resource the note is attached to.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4ProjectsIdSnippetsNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a snippet note
     * Retrieves a specified note for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the resource the note is attached to.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a snippet note
     * Retrieves a specified note for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the resource the note is attached to.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId);
    }

    /**
     * List all vulnerability notes
     * Lists all notes for a specified vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdVulnerabilitiesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdVulnerabilitiesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activity_filter", activityFilter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/vulnerabilities/{noteable_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all vulnerability notes
     * Lists all notes for a specified vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4ProjectsIdVulnerabilitiesNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all vulnerability notes
     * Lists all notes for a specified vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all vulnerability notes
     * Lists all notes for a specified vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage);
    }

    /**
     * Retrieve a vulnerability note
     * Retrieves a specified note for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the resource the note is attached to.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("note_id", noteId);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/vulnerabilities/{noteable_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a vulnerability note
     * Retrieves a specified note for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the resource the note is attached to.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a vulnerability note
     * Retrieves a specified note for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the resource the note is attached to.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a vulnerability note
     * Retrieves a specified note for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the resource the note is attached to.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId);
    }

    /**
     * List all wiki page meta notes
     * Lists all notes for a specified wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdWikiPagesNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activity_filter", activityFilter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/wiki_pages/{noteable_id}/notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all wiki page meta notes
     * Lists all notes for a specified wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNote> getApiV4ProjectsIdWikiPagesNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all wiki page meta notes
     * Lists all notes for a specified wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNote>> getApiV4ProjectsIdWikiPagesNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all wiki page meta notes
     * Lists all notes for a specified wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param activityFilter Filter notes by activity type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdWikiPagesNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String activityFilter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, orderBy, sort, activityFilter, page, perPage);
    }

    /**
     * Retrieve a wiki page meta note
     * Retrieves a specified note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("note_id", noteId);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/wiki_pages/{noteable_id}/notes/{note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a wiki page meta note
     * Retrieves a specified note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).body(localVarReturnType);
    }

    /**
     * Retrieve a wiki page meta note
     * Retrieves a specified note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a wiki page meta note
     * Retrieves a specified note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteId ID of the note.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull Integer noteableId) throws RestClientResponseException {
        return getApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteId, noteableId);
    }

    /**
     * Create an epic note
     * Creates a note for an epic.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param postApiV4GroupsIdEpicsNoteableIdNotesRequest The postApiV4GroupsIdEpicsNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdEpicsNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdNotesRequest postApiV4GroupsIdEpicsNoteableIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdEpicsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4GroupsIdEpicsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdEpicsNoteableIdNotesRequest' is set
        if (postApiV4GroupsIdEpicsNoteableIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdEpicsNoteableIdNotesRequest' when calling postApiV4GroupsIdEpicsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdEpicsNoteableIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an epic note
     * Creates a note for an epic.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param postApiV4GroupsIdEpicsNoteableIdNotesRequest The postApiV4GroupsIdEpicsNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4GroupsIdEpicsNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdNotesRequest postApiV4GroupsIdEpicsNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsNoteableIdNotesRequestCreation(id, noteableId, postApiV4GroupsIdEpicsNoteableIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Create an epic note
     * Creates a note for an epic.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param postApiV4GroupsIdEpicsNoteableIdNotesRequest The postApiV4GroupsIdEpicsNoteableIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4GroupsIdEpicsNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdNotesRequest postApiV4GroupsIdEpicsNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsNoteableIdNotesRequestCreation(id, noteableId, postApiV4GroupsIdEpicsNoteableIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an epic note
     * Creates a note for an epic.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param postApiV4GroupsIdEpicsNoteableIdNotesRequest The postApiV4GroupsIdEpicsNoteableIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdEpicsNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsNoteableIdNotesRequest postApiV4GroupsIdEpicsNoteableIdNotesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdEpicsNoteableIdNotesRequestCreation(id, noteableId, postApiV4GroupsIdEpicsNoteableIdNotesRequest);
    }

    /**
     * Create a wiki page meta note
     * Creates a note for a wiki page meta.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param postApiV4GroupsIdWikiPagesNoteableIdNotesRequest The postApiV4GroupsIdWikiPagesNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdWikiPagesNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdWikiPagesNoteableIdNotesRequest postApiV4GroupsIdWikiPagesNoteableIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4GroupsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdWikiPagesNoteableIdNotesRequest' is set
        if (postApiV4GroupsIdWikiPagesNoteableIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdWikiPagesNoteableIdNotesRequest' when calling postApiV4GroupsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/wiki_pages/{noteable_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdWikiPagesNoteableIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a wiki page meta note
     * Creates a note for a wiki page meta.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param postApiV4GroupsIdWikiPagesNoteableIdNotesRequest The postApiV4GroupsIdWikiPagesNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4GroupsIdWikiPagesNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdWikiPagesNoteableIdNotesRequest postApiV4GroupsIdWikiPagesNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, postApiV4GroupsIdWikiPagesNoteableIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Create a wiki page meta note
     * Creates a note for a wiki page meta.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param postApiV4GroupsIdWikiPagesNoteableIdNotesRequest The postApiV4GroupsIdWikiPagesNoteableIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4GroupsIdWikiPagesNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdWikiPagesNoteableIdNotesRequest postApiV4GroupsIdWikiPagesNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, postApiV4GroupsIdWikiPagesNoteableIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a wiki page meta note
     * Creates a note for a wiki page meta.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param postApiV4GroupsIdWikiPagesNoteableIdNotesRequest The postApiV4GroupsIdWikiPagesNoteableIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdWikiPagesNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4GroupsIdWikiPagesNoteableIdNotesRequest postApiV4GroupsIdWikiPagesNoteableIdNotesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, postApiV4GroupsIdWikiPagesNoteableIdNotesRequest);
    }

    /**
     * Create an issue note
     * Creates a note for an issue.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param postApiV4ProjectsIdIssuesNoteableIdNotesRequest The postApiV4ProjectsIdIssuesNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdNotesRequest postApiV4ProjectsIdIssuesNoteableIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdIssuesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdIssuesNoteableIdNotesRequest' is set
        if (postApiV4ProjectsIdIssuesNoteableIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdIssuesNoteableIdNotesRequest' when calling postApiV4ProjectsIdIssuesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdIssuesNoteableIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an issue note
     * Creates a note for an issue.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param postApiV4ProjectsIdIssuesNoteableIdNotesRequest The postApiV4ProjectsIdIssuesNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4ProjectsIdIssuesNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdNotesRequest postApiV4ProjectsIdIssuesNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdIssuesNoteableIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Create an issue note
     * Creates a note for an issue.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param postApiV4ProjectsIdIssuesNoteableIdNotesRequest The postApiV4ProjectsIdIssuesNoteableIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4ProjectsIdIssuesNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdNotesRequest postApiV4ProjectsIdIssuesNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdIssuesNoteableIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an issue note
     * Creates a note for an issue.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param postApiV4ProjectsIdIssuesNoteableIdNotesRequest The postApiV4ProjectsIdIssuesNoteableIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesNoteableIdNotesRequest postApiV4ProjectsIdIssuesNoteableIdNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdIssuesNoteableIdNotesRequest);
    }

    /**
     * Create a merge request note
     * Creates a note for a merge request. The note is not attached to a specific line of the merge request. To comment on a line, create a discussion thread or comment on a commit instead.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest The postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdMergeRequestsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest' is set
        if (postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest' when calling postApiV4ProjectsIdMergeRequestsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a merge request note
     * Creates a note for a merge request. The note is not attached to a specific line of the merge request. To comment on a line, create a discussion thread or comment on a commit instead.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest The postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4ProjectsIdMergeRequestsNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Create a merge request note
     * Creates a note for a merge request. The note is not attached to a specific line of the merge request. To comment on a line, create a discussion thread or comment on a commit instead.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest The postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4ProjectsIdMergeRequestsNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a merge request note
     * Creates a note for a merge request. The note is not attached to a specific line of the merge request. To comment on a line, create a discussion thread or comment on a commit instead.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest The postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdMergeRequestsNoteableIdNotesRequest);
    }

    /**
     * Create a snippet note
     * Creates a note for a snippet.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param postApiV4ProjectsIdSnippetsNoteableIdNotesRequest The postApiV4ProjectsIdSnippetsNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdSnippetsNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdNotesRequest postApiV4ProjectsIdSnippetsNoteableIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdSnippetsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdSnippetsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdSnippetsNoteableIdNotesRequest' is set
        if (postApiV4ProjectsIdSnippetsNoteableIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdSnippetsNoteableIdNotesRequest' when calling postApiV4ProjectsIdSnippetsNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdSnippetsNoteableIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a snippet note
     * Creates a note for a snippet.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param postApiV4ProjectsIdSnippetsNoteableIdNotesRequest The postApiV4ProjectsIdSnippetsNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4ProjectsIdSnippetsNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdNotesRequest postApiV4ProjectsIdSnippetsNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdSnippetsNoteableIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Create a snippet note
     * Creates a note for a snippet.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param postApiV4ProjectsIdSnippetsNoteableIdNotesRequest The postApiV4ProjectsIdSnippetsNoteableIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4ProjectsIdSnippetsNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdNotesRequest postApiV4ProjectsIdSnippetsNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdSnippetsNoteableIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a snippet note
     * Creates a note for a snippet.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param postApiV4ProjectsIdSnippetsNoteableIdNotesRequest The postApiV4ProjectsIdSnippetsNoteableIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdSnippetsNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsNoteableIdNotesRequest postApiV4ProjectsIdSnippetsNoteableIdNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdSnippetsNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdSnippetsNoteableIdNotesRequest);
    }

    /**
     * Create a vulnerability note
     * Creates a note for a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest The postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdVulnerabilitiesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdVulnerabilitiesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest' is set
        if (postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest' when calling postApiV4ProjectsIdVulnerabilitiesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/vulnerabilities/{noteable_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a vulnerability note
     * Creates a note for a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest The postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4ProjectsIdVulnerabilitiesNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Create a vulnerability note
     * Creates a note for a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest The postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a vulnerability note
     * Creates a note for a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest The postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdVulnerabilitiesNoteableIdNotesRequest);
    }

    /**
     * Create a wiki page meta note
     * Creates a note for a wiki page meta.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest The postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdWikiPagesNoteableIdNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdWikiPagesNoteableIdNotesRequest postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling postApiV4ProjectsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest' is set
        if (postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest' when calling postApiV4ProjectsIdWikiPagesNoteableIdNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/wiki_pages/{noteable_id}/notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a wiki page meta note
     * Creates a note for a wiki page meta.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest The postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote postApiV4ProjectsIdWikiPagesNoteableIdNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdWikiPagesNoteableIdNotesRequest postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest).body(localVarReturnType);
    }

    /**
     * Create a wiki page meta note
     * Creates a note for a wiki page meta.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest The postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> postApiV4ProjectsIdWikiPagesNoteableIdNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdWikiPagesNoteableIdNotesRequest postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a wiki page meta note
     * Creates a note for a wiki page meta.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest The postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdWikiPagesNoteableIdNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdWikiPagesNoteableIdNotesRequest postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdWikiPagesNoteableIdNotesRequestCreation(id, noteableId, postApiV4ProjectsIdWikiPagesNoteableIdNotesRequest);
    }

    /**
     * Update an epic note
     * Updates an existing note for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param noteId ID of the note.
     * @param putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest The putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdEpicsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4GroupsIdEpicsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4GroupsIdEpicsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{noteable_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an epic note
     * Updates an existing note for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param noteId ID of the note.
     * @param putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest The putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4GroupsIdEpicsNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update an epic note
     * Updates an existing note for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param noteId ID of the note.
     * @param putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest The putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4GroupsIdEpicsNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an epic note
     * Updates an existing note for an epic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the epic. Use the epic ID, not the IID. An IID returns a &#x60;404&#x60; error or the notes of a different epic.
     * @param noteId ID of the note.
     * @param putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest The putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdEpicsNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4GroupsIdEpicsNoteableIdNotesNoteIdRequest);
    }

    /**
     * Update a wiki page meta note
     * Updates an existing note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @param putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest The putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4GroupsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4GroupsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/wiki_pages/{noteable_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a wiki page meta note
     * Updates an existing note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @param putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest The putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4GroupsIdWikiPagesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a wiki page meta note
     * Updates an existing note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @param putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest The putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a wiki page meta note
     * Updates an existing note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @param putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest The putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4GroupsIdWikiPagesNoteableIdNotesNoteIdRequest);
    }

    /**
     * Update an issue note
     * Updates an existing note for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdIssuesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdIssuesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4ProjectsIdIssuesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{noteable_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an issue note
     * Updates an existing note for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4ProjectsIdIssuesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update an issue note
     * Updates an existing note for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an issue note
     * Updates an existing note for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the issue.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdIssuesNoteableIdNotesNoteIdRequest);
    }

    /**
     * Update a merge request note
     * Updates an existing note for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{noteable_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a merge request note
     * Updates an existing note for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a merge request note
     * Updates an existing note for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a merge request note
     * Updates an existing note for a merge request.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId IID of the merge request.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdMergeRequestsNoteableIdNotesNoteIdRequest);
    }

    /**
     * Update a snippet note
     * Updates an existing note for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdSnippetsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdSnippetsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4ProjectsIdSnippetsNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{noteable_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a snippet note
     * Updates an existing note for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4ProjectsIdSnippetsNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a snippet note
     * Updates an existing note for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a snippet note
     * Updates an existing note for a snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdSnippetsNoteableIdNotesNoteIdRequest);
    }

    /**
     * Update a vulnerability note
     * Updates an existing note for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/vulnerabilities/{noteable_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a vulnerability note
     * Updates an existing note for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a vulnerability note
     * Updates an existing note for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a vulnerability note
     * Updates an existing note for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the resource the note is attached to.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdVulnerabilitiesNoteableIdNotesNoteIdRequest);
    }

    /**
     * Update a wiki page meta note
     * Updates an existing note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteableId' is set
        if (noteableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteableId' when calling putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("noteable_id", noteableId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/wiki_pages/{noteable_id}/notes/{note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a wiki page meta note
     * Updates an existing note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest parameter
     * @return APIEntitiesNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNote putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a wiki page meta note
     * Updates an existing note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNote> putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a wiki page meta note
     * Updates an existing note for a wiki page meta.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param noteableId ID of the wiki page meta. Use the meta ID, not the page slug. A slug returns a &#x60;404&#x60; error.
     * @param noteId ID of the note.
     * @param putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest The putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer noteableId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequestCreation(id, noteableId, noteId, putApiV4ProjectsIdWikiPagesNoteableIdNotesNoteIdRequest);
    }
}
