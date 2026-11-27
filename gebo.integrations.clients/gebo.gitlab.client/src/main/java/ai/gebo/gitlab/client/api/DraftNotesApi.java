package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesDraftNote;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest;

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
public class DraftNotesApi {
    private ApiClient apiClient;

    public DraftNotesApi() {
        this(new ApiClient());
    }

    public DraftNotesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a draft note
     * Deletes a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @return APIEntitiesDraftNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'draftNoteId' is set
        if (draftNoteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'draftNoteId' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("draft_note_id", draftNoteId);

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

        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/draft_notes/{draft_note_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a draft note
     * Deletes a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @return APIEntitiesDraftNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDraftNote deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(id, mergeRequestIid, draftNoteId).body(localVarReturnType);
    }

    /**
     * Delete a draft note
     * Deletes a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @return ResponseEntity&lt;APIEntitiesDraftNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDraftNote> deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(id, mergeRequestIid, draftNoteId).toEntity(localVarReturnType);
    }

    /**
     * Delete a draft note
     * Deletes a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(id, mergeRequestIid, draftNoteId);
    }

    /**
     * List all merge request draft notes
     * Lists all merge request draft notes.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesDraftNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<APIEntitiesDraftNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/draft_notes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge request draft notes
     * Lists all merge request draft notes.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return List&lt;APIEntitiesDraftNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDraftNote> getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDraftNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequestCreation(id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * List all merge request draft notes
     * Lists all merge request draft notes.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDraftNote&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDraftNote>> getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDraftNote>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequestCreation(id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * List all merge request draft notes
     * Lists all merge request draft notes.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequestCreation(id, mergeRequestIid);
    }

    /**
     * Retrieve a draft note
     * Retrieves a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @return APIEntitiesDraftNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'draftNoteId' is set
        if (draftNoteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'draftNoteId' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("draft_note_id", draftNoteId);

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

        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/draft_notes/{draft_note_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a draft note
     * Retrieves a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @return APIEntitiesDraftNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDraftNote getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(id, mergeRequestIid, draftNoteId).body(localVarReturnType);
    }

    /**
     * Retrieve a draft note
     * Retrieves a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @return ResponseEntity&lt;APIEntitiesDraftNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDraftNote> getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(id, mergeRequestIid, draftNoteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a draft note
     * Retrieves a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(id, mergeRequestIid, draftNoteId);
    }

    /**
     * Create a draft note
     * Creates a draft note for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest parameter
     * @return APIEntitiesDraftNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/draft_notes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a draft note
     * Creates a draft note for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest parameter
     * @return APIEntitiesDraftNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDraftNote postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest).body(localVarReturnType);
    }

    /**
     * Create a draft note
     * Creates a draft note for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDraftNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDraftNote> postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a draft note
     * Creates a draft note for a specified merge request.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesRequest);
    }

    /**
     * Publish all pending draft notes
     * Publishes all pending draft notes for the current user on the specified merge request. Optionally sets the reviewer state and posts a summary note.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublish", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublish", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/draft_notes/bulk_publish", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Publish all pending draft notes
     * Publishes all pending draft notes for the current user on the specified merge request. Optionally sets the reviewer state and posts a summary note.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublish(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest).body(localVarReturnType);
    }

    /**
     * Publish all pending draft notes
     * Publishes all pending draft notes for the current user on the specified merge request. Optionally sets the reviewer state and posts a summary note.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest).toEntity(localVarReturnType);
    }

    /**
     * Publish all pending draft notes
     * Publishes all pending draft notes for the current user on the specified merge request. Optionally sets the reviewer state and posts a summary note.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable PostApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesBulkPublishRequest);
    }

    /**
     * Update a draft note
     * Updates a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest parameter
     * @return APIEntitiesDraftNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'draftNoteId' is set
        if (draftNoteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'draftNoteId' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("draft_note_id", draftNoteId);

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

        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/draft_notes/{draft_note_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a draft note
     * Updates a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest parameter
     * @return APIEntitiesDraftNote
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDraftNote putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(id, mergeRequestIid, draftNoteId, putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest).body(localVarReturnType);
    }

    /**
     * Update a draft note
     * Updates a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDraftNote&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDraftNote> putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDraftNote> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(id, mergeRequestIid, draftNoteId, putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a draft note
     * Updates a draft note for a specified merge request.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @param putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest The putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId, @jakarta.annotation.Nullable PutApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequestCreation(id, mergeRequestIid, draftNoteId, putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdRequest);
    }

    /**
     * Publish a draft note
     * Publishes a draft note for a specified merge request.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublishRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublish", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublish", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'draftNoteId' is set
        if (draftNoteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'draftNoteId' when calling putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublish", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("draft_note_id", draftNoteId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/draft_notes/{draft_note_id}/publish", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Publish a draft note
     * Publishes a draft note for a specified merge request.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublish(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublishRequestCreation(id, mergeRequestIid, draftNoteId).body(localVarReturnType);
    }

    /**
     * Publish a draft note
     * Publishes a draft note for a specified merge request.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublishWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublishRequestCreation(id, mergeRequestIid, draftNoteId).toEntity(localVarReturnType);
    }

    /**
     * Publish a draft note
     * Publishes a draft note for a specified merge request.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param draftNoteId ID of the draft note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublishWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer draftNoteId) throws RestClientResponseException {
        return putApiV4ProjectsIdMergeRequestsMergeRequestIidDraftNotesDraftNoteIdPublishRequestCreation(id, mergeRequestIid, draftNoteId);
    }
}
