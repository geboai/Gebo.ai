package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAwardEmoji;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdEpicsEpicIidAwardEmojiRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest;

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
public class AwardEmojiApi {
    private ApiClient apiClient;

    public AwardEmojiApi() {
        this(new ApiClient());
    }

    public AwardEmojiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an emoji reaction from an epic
     * Deletes a specified emoji reaction from an epic. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/award_emoji/{award_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an epic
     * Deletes a specified emoji reaction from an epic. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdRequestCreation(awardId, id, epicIid).body(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an epic
     * Deletes a specified emoji reaction from an epic. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdRequestCreation(awardId, id, epicIid).toEntity(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an epic
     * Deletes a specified emoji reaction from an epic. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        return deleteApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdRequestCreation(awardId, id, epicIid);
    }

    /**
     * Delete an emoji reaction from an epic comment
     * Deletes a specified emoji reaction from a comment on an epic. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);
        pathParams.put("note_id", noteId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/notes/{note_id}/award_emoji/{award_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an epic comment
     * Deletes a specified emoji reaction from a comment on an epic. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, epicIid, noteId).body(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an epic comment
     * Deletes a specified emoji reaction from a comment on an epic. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, epicIid, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an epic comment
     * Deletes a specified emoji reaction from a comment on an epic. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, epicIid, noteId);
    }

    /**
     * Delete an emoji reaction from an issue
     * Deletes a specified emoji reaction from an issue. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/award_emoji/{award_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an issue
     * Deletes a specified emoji reaction from an issue. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdRequestCreation(awardId, id, issueIid).body(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an issue
     * Deletes a specified emoji reaction from an issue. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdRequestCreation(awardId, id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an issue
     * Deletes a specified emoji reaction from an issue. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return deleteApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdRequestCreation(awardId, id, issueIid);
    }

    /**
     * Delete an emoji reaction from an issue comment
     * Deletes a specified emoji reaction from a comment on an issue. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);
        pathParams.put("note_id", noteId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/notes/{note_id}/award_emoji/{award_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an issue comment
     * Deletes a specified emoji reaction from a comment on an issue. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, issueIid, noteId).body(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an issue comment
     * Deletes a specified emoji reaction from a comment on an issue. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, issueIid, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from an issue comment
     * Deletes a specified emoji reaction from a comment on an issue. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, issueIid, noteId);
    }

    /**
     * Delete an emoji reaction from a merge request
     * Deletes a specified emoji reaction from a merge request. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/award_emoji/{award_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a merge request
     * Deletes a specified emoji reaction from a merge request. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a merge request
     * Deletes a specified emoji reaction from a merge request. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a merge request
     * Deletes a specified emoji reaction from a merge request. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid);
    }

    /**
     * Delete an emoji reaction from a merge request comment
     * Deletes a specified emoji reaction from a comment on a merge request. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
        pathParams.put("note_id", noteId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/notes/{note_id}/award_emoji/{award_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a merge request comment
     * Deletes a specified emoji reaction from a comment on a merge request. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid, noteId).body(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a merge request comment
     * Deletes a specified emoji reaction from a comment on a merge request. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a merge request comment
     * Deletes a specified emoji reaction from a comment on a merge request. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid, noteId);
    }

    /**
     * Delete an emoji reaction from a snippet
     * Deletes a specified emoji reaction from a snippet. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("snippet_id", snippetId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/award_emoji/{award_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a snippet
     * Deletes a specified emoji reaction from a snippet. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId).body(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a snippet
     * Deletes a specified emoji reaction from a snippet. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId).toEntity(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a snippet
     * Deletes a specified emoji reaction from a snippet. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId);
    }

    /**
     * Delete an emoji reaction from a snippet comment
     * Deletes a specified emoji reaction from a comment on a snippet. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("snippet_id", snippetId);
        pathParams.put("note_id", noteId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/notes/{note_id}/award_emoji/{award_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a snippet comment
     * Deletes a specified emoji reaction from a comment on a snippet. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId, noteId).body(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a snippet comment
     * Deletes a specified emoji reaction from a comment on a snippet. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Delete an emoji reaction from a snippet comment
     * Deletes a specified emoji reaction from a comment on a snippet. Only an administrator or the user who added the reaction can delete it.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId, noteId);
    }

    /**
     * List all emoji reactions for an epic
     * Lists all emoji reactions for a specified epic. This endpoint can be accessed without authentication if the epic is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEpicIidAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEpicIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdEpicsEpicIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);

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

        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/award_emoji", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all emoji reactions for an epic
     * Lists all emoji reactions for a specified epic. This endpoint can be accessed without authentication if the epic is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAwardEmoji> getApiV4GroupsIdEpicsEpicIidAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidAwardEmojiRequestCreation(id, epicIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all emoji reactions for an epic
     * Lists all emoji reactions for a specified epic. This endpoint can be accessed without authentication if the epic is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAwardEmoji&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAwardEmoji>> getApiV4GroupsIdEpicsEpicIidAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidAwardEmojiRequestCreation(id, epicIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all emoji reactions for an epic
     * Lists all emoji reactions for a specified epic. This endpoint can be accessed without authentication if the epic is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEpicIidAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEpicIidAwardEmojiRequestCreation(id, epicIid, page, perPage);
    }

    /**
     * Retrieve an emoji reaction from an epic
     * Retrieves a specified emoji reaction from an epic. This endpoint can be accessed without authentication if the epic is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);

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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/award_emoji/{award_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an epic
     * Retrieves a specified emoji reaction from an epic. This endpoint can be accessed without authentication if the epic is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdRequestCreation(awardId, id, epicIid).body(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an epic
     * Retrieves a specified emoji reaction from an epic. This endpoint can be accessed without authentication if the epic is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdRequestCreation(awardId, id, epicIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an epic
     * Retrieves a specified emoji reaction from an epic. This endpoint can be accessed without authentication if the epic is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEpicIidAwardEmojiAwardIdRequestCreation(awardId, id, epicIid);
    }

    /**
     * List all emoji reactions for an epic comment
     * Lists all emoji reactions for a specified comment on an epic. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("note_id", noteId);
        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);

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

        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/notes/{note_id}/award_emoji", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all emoji reactions for an epic comment
     * Lists all emoji reactions for a specified comment on an epic. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAwardEmoji> getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmoji(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequestCreation(noteId, id, epicIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all emoji reactions for an epic comment
     * Lists all emoji reactions for a specified comment on an epic. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAwardEmoji&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAwardEmoji>> getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequestCreation(noteId, id, epicIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all emoji reactions for an epic comment
     * Lists all emoji reactions for a specified comment on an epic. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequestCreation(noteId, id, epicIid, page, perPage);
    }

    /**
     * Retrieve an emoji reaction from an epic comment
     * Retrieves a specified emoji reaction from a comment on an epic. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/notes/{note_id}/award_emoji/{award_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an epic comment
     * Retrieves a specified emoji reaction from a comment on an epic. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, epicIid, noteId).body(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an epic comment
     * Retrieves a specified emoji reaction from a comment on an epic. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, epicIid, noteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an epic comment
     * Retrieves a specified emoji reaction from a comment on an epic. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, epicIid, noteId);
    }

    /**
     * List all emoji reactions for an issue
     * Lists all emoji reactions for a specified issue. This endpoint can be accessed without authentication if the issue is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);

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

        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/award_emoji", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all emoji reactions for an issue
     * Lists all emoji reactions for a specified issue. This endpoint can be accessed without authentication if the issue is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAwardEmoji> getApiV4ProjectsIdIssuesIssueIidAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidAwardEmojiRequestCreation(id, issueIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all emoji reactions for an issue
     * Lists all emoji reactions for a specified issue. This endpoint can be accessed without authentication if the issue is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAwardEmoji&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAwardEmoji>> getApiV4ProjectsIdIssuesIssueIidAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidAwardEmojiRequestCreation(id, issueIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all emoji reactions for an issue
     * Lists all emoji reactions for a specified issue. This endpoint can be accessed without authentication if the issue is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidAwardEmojiRequestCreation(id, issueIid, page, perPage);
    }

    /**
     * Retrieve an emoji reaction from an issue
     * Retrieves a specified emoji reaction from an issue. This endpoint can be accessed without authentication if the issue is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);

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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/award_emoji/{award_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an issue
     * Retrieves a specified emoji reaction from an issue. This endpoint can be accessed without authentication if the issue is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdRequestCreation(awardId, id, issueIid).body(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an issue
     * Retrieves a specified emoji reaction from an issue. This endpoint can be accessed without authentication if the issue is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdRequestCreation(awardId, id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an issue
     * Retrieves a specified emoji reaction from an issue. This endpoint can be accessed without authentication if the issue is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidAwardEmojiAwardIdRequestCreation(awardId, id, issueIid);
    }

    /**
     * List all emoji reactions for an issue comment
     * Lists all emoji reactions for a specified comment on an issue. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("note_id", noteId);
        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);

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

        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/notes/{note_id}/award_emoji", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all emoji reactions for an issue comment
     * Lists all emoji reactions for a specified comment on an issue. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAwardEmoji> getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmoji(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequestCreation(noteId, id, issueIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all emoji reactions for an issue comment
     * Lists all emoji reactions for a specified comment on an issue. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAwardEmoji&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAwardEmoji>> getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequestCreation(noteId, id, issueIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all emoji reactions for an issue comment
     * Lists all emoji reactions for a specified comment on an issue. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequestCreation(noteId, id, issueIid, page, perPage);
    }

    /**
     * Retrieve an emoji reaction from an issue comment
     * Retrieves a specified emoji reaction from a comment on an issue. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/notes/{note_id}/award_emoji/{award_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an issue comment
     * Retrieves a specified emoji reaction from a comment on an issue. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, issueIid, noteId).body(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an issue comment
     * Retrieves a specified emoji reaction from a comment on an issue. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, issueIid, noteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from an issue comment
     * Retrieves a specified emoji reaction from a comment on an issue. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, issueIid, noteId);
    }

    /**
     * List all emoji reactions for a merge request
     * Lists all emoji reactions for a specified merge request. This endpoint can be accessed without authentication if the merge request is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/award_emoji", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all emoji reactions for a merge request
     * Lists all emoji reactions for a specified merge request. This endpoint can be accessed without authentication if the merge request is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAwardEmoji> getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequestCreation(id, mergeRequestIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all emoji reactions for a merge request
     * Lists all emoji reactions for a specified merge request. This endpoint can be accessed without authentication if the merge request is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAwardEmoji&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAwardEmoji>> getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequestCreation(id, mergeRequestIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all emoji reactions for a merge request
     * Lists all emoji reactions for a specified merge request. This endpoint can be accessed without authentication if the merge request is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequestCreation(id, mergeRequestIid, page, perPage);
    }

    /**
     * Retrieve an emoji reaction from a merge request
     * Retrieves a specified emoji reaction from a merge request. This endpoint can be accessed without authentication if the merge request is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/award_emoji/{award_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a merge request
     * Retrieves a specified emoji reaction from a merge request. This endpoint can be accessed without authentication if the merge request is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid).body(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a merge request
     * Retrieves a specified emoji reaction from a merge request. This endpoint can be accessed without authentication if the merge request is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a merge request
     * Retrieves a specified emoji reaction from a merge request. This endpoint can be accessed without authentication if the merge request is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid);
    }

    /**
     * List all emoji reactions for a merge request comment
     * Lists all emoji reactions for a specified comment on a merge request. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("note_id", noteId);
        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);

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

        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/notes/{note_id}/award_emoji", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all emoji reactions for a merge request comment
     * Lists all emoji reactions for a specified comment on a merge request. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAwardEmoji> getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmoji(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequestCreation(noteId, id, mergeRequestIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all emoji reactions for a merge request comment
     * Lists all emoji reactions for a specified comment on a merge request. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAwardEmoji&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAwardEmoji>> getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequestCreation(noteId, id, mergeRequestIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all emoji reactions for a merge request comment
     * Lists all emoji reactions for a specified comment on a merge request. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequestCreation(noteId, id, mergeRequestIid, page, perPage);
    }

    /**
     * Retrieve an emoji reaction from a merge request comment
     * Retrieves a specified emoji reaction from a comment on a merge request. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/notes/{note_id}/award_emoji/{award_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a merge request comment
     * Retrieves a specified emoji reaction from a comment on a merge request. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid, noteId).body(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a merge request comment
     * Retrieves a specified emoji reaction from a comment on a merge request. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid, noteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a merge request comment
     * Retrieves a specified emoji reaction from a comment on a merge request. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return getApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, mergeRequestIid, noteId);
    }

    /**
     * List all emoji reactions for a snippet
     * Lists all emoji reactions for a specified snippet. This endpoint can be accessed without authentication if the snippet is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsSnippetIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling getApiV4ProjectsIdSnippetsSnippetIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("snippet_id", snippetId);

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

        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/award_emoji", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all emoji reactions for a snippet
     * Lists all emoji reactions for a specified snippet. This endpoint can be accessed without authentication if the snippet is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAwardEmoji> getApiV4ProjectsIdSnippetsSnippetIdAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequestCreation(id, snippetId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all emoji reactions for a snippet
     * Lists all emoji reactions for a specified snippet. This endpoint can be accessed without authentication if the snippet is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAwardEmoji&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAwardEmoji>> getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequestCreation(id, snippetId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all emoji reactions for a snippet
     * Lists all emoji reactions for a specified snippet. This endpoint can be accessed without authentication if the snippet is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID (&#x60;iid&#x60; for merge requests/issues/epics, &#x60;id&#x60; for snippets) of an awardable.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequestCreation(id, snippetId, page, perPage);
    }

    /**
     * Retrieve an emoji reaction from a snippet
     * Retrieves a specified emoji reaction from a snippet. This endpoint can be accessed without authentication if the snippet is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("snippet_id", snippetId);

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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/award_emoji/{award_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a snippet
     * Retrieves a specified emoji reaction from a snippet. This endpoint can be accessed without authentication if the snippet is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId).body(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a snippet
     * Retrieves a specified emoji reaction from a snippet. This endpoint can be accessed without authentication if the snippet is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a snippet
     * Retrieves a specified emoji reaction from a snippet. This endpoint can be accessed without authentication if the snippet is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsSnippetIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId);
    }

    /**
     * List all emoji reactions for a snippet comment
     * Lists all emoji reactions for a specified comment on a snippet. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("note_id", noteId);
        pathParams.put("id", id);
        pathParams.put("snippet_id", snippetId);

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

        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/notes/{note_id}/award_emoji", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all emoji reactions for a snippet comment
     * Lists all emoji reactions for a specified comment on a snippet. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAwardEmoji> getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmoji(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequestCreation(noteId, id, snippetId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all emoji reactions for a snippet comment
     * Lists all emoji reactions for a specified comment on a snippet. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAwardEmoji&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAwardEmoji>> getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAwardEmoji>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequestCreation(noteId, id, snippetId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all emoji reactions for a snippet comment
     * Lists all emoji reactions for a specified comment on a snippet. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param noteId ID of the note.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequestCreation(noteId, id, snippetId, page, perPage);
    }

    /**
     * Retrieve an emoji reaction from a snippet comment
     * Retrieves a specified emoji reaction from a comment on a snippet. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdRequestCreation(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        // verify the required parameter 'awardId' is set
        if (awardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'awardId' when calling getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("award_id", awardId);
        pathParams.put("id", id);
        pathParams.put("snippet_id", snippetId);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/notes/{note_id}/award_emoji/{award_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a snippet comment
     * Retrieves a specified emoji reaction from a comment on a snippet. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardId(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId, noteId).body(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a snippet comment
     * Retrieves a specified emoji reaction from a comment on a snippet. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdWithHttpInfo(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId, noteId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an emoji reaction from a snippet comment
     * Retrieves a specified emoji reaction from a comment on a snippet. This endpoint can be accessed without authentication if the comment is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param awardId ID of the emoji reaction.
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdWithResponseSpec(@jakarta.annotation.Nonnull Integer awardId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiAwardIdRequestCreation(awardId, id, snippetId, noteId);
    }

    /**
     * Add an emoji reaction to an epic
     * Adds an emoji reaction to an epic.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest The postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdEpicsEpicIidAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidAwardEmojiRequest postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdEpicsEpicIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling postApiV4GroupsIdEpicsEpicIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest' is set
        if (postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest' when calling postApiV4GroupsIdEpicsEpicIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);

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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/award_emoji", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an emoji reaction to an epic
     * Adds an emoji reaction to an epic.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest The postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji postApiV4GroupsIdEpicsEpicIidAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidAwardEmojiRequest postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsEpicIidAwardEmojiRequestCreation(id, epicIid, postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest).body(localVarReturnType);
    }

    /**
     * Add an emoji reaction to an epic
     * Adds an emoji reaction to an epic.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest The postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> postApiV4GroupsIdEpicsEpicIidAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidAwardEmojiRequest postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsEpicIidAwardEmojiRequestCreation(id, epicIid, postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an emoji reaction to an epic
     * Adds an emoji reaction to an epic.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest The postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdEpicsEpicIidAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidAwardEmojiRequest postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest) throws RestClientResponseException {
        return postApiV4GroupsIdEpicsEpicIidAwardEmojiRequestCreation(id, epicIid, postApiV4GroupsIdEpicsEpicIidAwardEmojiRequest);
    }

    /**
     * Add an emoji reaction to an epic comment
     * Adds an emoji reaction to a comment on an epic.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @param postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest The postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest' is set
        if (postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest' when calling postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/notes/{note_id}/award_emoji", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an emoji reaction to an epic comment
     * Adds an emoji reaction to a comment on an epic.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @param postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest The postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequestCreation(id, epicIid, noteId, postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest).body(localVarReturnType);
    }

    /**
     * Add an emoji reaction to an epic comment
     * Adds an emoji reaction to a comment on an epic.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @param postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest The postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequestCreation(id, epicIid, noteId, postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an emoji reaction to an epic comment
     * Adds an emoji reaction to a comment on an epic.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param noteId ID of the note.
     * @param postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest The postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        return postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequestCreation(id, epicIid, noteId, postApiV4GroupsIdEpicsEpicIidNotesNoteIdAwardEmojiRequest);
    }

    /**
     * Add an emoji reaction to an issue
     * Adds an emoji reaction to an issue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest The postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest' is set
        if (postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest' when calling postApiV4ProjectsIdIssuesIssueIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);

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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/award_emoji", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an emoji reaction to an issue
     * Adds an emoji reaction to an issue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest The postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji postApiV4ProjectsIdIssuesIssueIidAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest).body(localVarReturnType);
    }

    /**
     * Add an emoji reaction to an issue
     * Adds an emoji reaction to an issue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest The postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> postApiV4ProjectsIdIssuesIssueIidAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an emoji reaction to an issue
     * Adds an emoji reaction to an issue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest The postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidAwardEmojiRequest);
    }

    /**
     * Add an emoji reaction to an issue comment
     * Adds an emoji reaction to a comment on an issue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest' is set
        if (postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest' when calling postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/notes/{note_id}/award_emoji", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an emoji reaction to an issue comment
     * Adds an emoji reaction to a comment on an issue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequestCreation(id, issueIid, noteId, postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest).body(localVarReturnType);
    }

    /**
     * Add an emoji reaction to an issue comment
     * Adds an emoji reaction to a comment on an issue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequestCreation(id, issueIid, noteId, postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an emoji reaction to an issue comment
     * Adds an emoji reaction to a comment on an issue.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequestCreation(id, issueIid, noteId, postApiV4ProjectsIdIssuesIssueIidNotesNoteIdAwardEmojiRequest);
    }

    /**
     * Add an emoji reaction to a merge request
     * Adds an emoji reaction to a merge request.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/award_emoji", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an emoji reaction to a merge request
     * Adds an emoji reaction to a merge request.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest).body(localVarReturnType);
    }

    /**
     * Add an emoji reaction to a merge request
     * Adds an emoji reaction to a merge request.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an emoji reaction to a merge request
     * Adds an emoji reaction to a merge request.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequestCreation(id, mergeRequestIid, postApiV4ProjectsIdMergeRequestsMergeRequestIidAwardEmojiRequest);
    }

    /**
     * Add an emoji reaction to a merge request comment
     * Adds an emoji reaction to a comment on a merge request.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mergeRequestIid' is set
        if (mergeRequestIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'mergeRequestIid' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest' is set
        if (postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest' when calling postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("merge_request_iid", mergeRequestIid);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{merge_request_iid}/notes/{note_id}/award_emoji", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an emoji reaction to a merge request comment
     * Adds an emoji reaction to a comment on a merge request.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequestCreation(id, mergeRequestIid, noteId, postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest).body(localVarReturnType);
    }

    /**
     * Add an emoji reaction to a merge request comment
     * Adds an emoji reaction to a comment on a merge request.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequestCreation(id, mergeRequestIid, noteId, postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an emoji reaction to a merge request comment
     * Adds an emoji reaction to a comment on a merge request.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param mergeRequestIid Internal ID of the merge request.
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer mergeRequestIid, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequestCreation(id, mergeRequestIid, noteId, postApiV4ProjectsIdMergeRequestsMergeRequestIidNotesNoteIdAwardEmojiRequest);
    }

    /**
     * Add an emoji reaction to a snippet
     * Adds an emoji reaction to a snippet.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest The postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdSnippetsSnippetIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling postApiV4ProjectsIdSnippetsSnippetIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest' is set
        if (postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest' when calling postApiV4ProjectsIdSnippetsSnippetIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("snippet_id", snippetId);

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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/award_emoji", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an emoji reaction to a snippet
     * Adds an emoji reaction to a snippet.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest The postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji postApiV4ProjectsIdSnippetsSnippetIdAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequestCreation(id, snippetId, postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest).body(localVarReturnType);
    }

    /**
     * Add an emoji reaction to a snippet
     * Adds an emoji reaction to a snippet.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest The postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequestCreation(id, snippetId, postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an emoji reaction to a snippet
     * Adds an emoji reaction to a snippet.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest The postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequestCreation(id, snippetId, postApiV4ProjectsIdSnippetsSnippetIdAwardEmojiRequest);
    }

    /**
     * Add an emoji reaction to a snippet comment
     * Adds an emoji reaction to a comment on a snippet.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'noteId' is set
        if (noteId == null) {
            throw new RestClientResponseException("Missing the required parameter 'noteId' when calling postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest' is set
        if (postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest' when calling postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmoji", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("snippet_id", snippetId);
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

        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/notes/{note_id}/award_emoji", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an emoji reaction to a snippet comment
     * Adds an emoji reaction to a comment on a snippet.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest parameter
     * @return APIEntitiesAwardEmoji
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAwardEmoji postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmoji(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequestCreation(id, snippetId, noteId, postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest).body(localVarReturnType);
    }

    /**
     * Add an emoji reaction to a snippet comment
     * Adds an emoji reaction to a comment on a snippet.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAwardEmoji&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAwardEmoji> postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAwardEmoji> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequestCreation(id, snippetId, noteId, postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an emoji reaction to a snippet comment
     * Adds an emoji reaction to a comment on a snippet.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param noteId ID of the note.
     * @param postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest The postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nonnull Integer noteId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequestCreation(id, snippetId, noteId, postApiV4ProjectsIdSnippetsSnippetIdNotesNoteIdAwardEmojiRequest);
    }
}
