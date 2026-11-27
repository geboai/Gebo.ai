package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesEpic;
import ai.gebo.gitlab.client.model.APIEntitiesEpicBoard;
import ai.gebo.gitlab.client.model.APIEntitiesEpicBoardsList;
import ai.gebo.gitlab.client.model.APIEntitiesEpicIssue;
import ai.gebo.gitlab.client.model.APIEntitiesEpicIssueLink;
import ai.gebo.gitlab.client.model.APIEntitiesRelatedEpic;
import ai.gebo.gitlab.client.model.APIEntitiesRelatedEpicLink;
import ai.gebo.gitlab.client.model.APIEntitiesTodo;
import ai.gebo.gitlab.client.model.GetApiV4DependencyListExportsExportIdExportIdParameter;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdDashEpicsEpicIidEpicsRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdDashEpicsRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdDashEpicsEpicIidRequest;

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
public class EpicsApi {
    private ApiClient apiClient;

    public EpicsApi() {
        this(new ApiClient());
    }

    public EpicsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Destroy an epic
     * Deletes an epic
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdDashEpicsEpicIidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdDashEpicsEpicIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling deleteApiV4GroupsIdDashEpicsEpicIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Destroy an epic
     * Deletes an epic
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdDashEpicsEpicIid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdDashEpicsEpicIidRequestCreation(id, epicIid).body(localVarReturnType);
    }

    /**
     * Destroy an epic
     * Deletes an epic
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdDashEpicsEpicIidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdDashEpicsEpicIidRequestCreation(id, epicIid).toEntity(localVarReturnType);
    }

    /**
     * Destroy an epic
     * Deletes an epic
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdDashEpicsEpicIidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        return deleteApiV4GroupsIdDashEpicsEpicIidRequestCreation(id, epicIid);
    }

    /**
     * Remove epics relation
     * Unassigns a child epic from a parent epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'childEpicId' is set
        if (childEpicId == null) {
            throw new RestClientResponseException("Missing the required parameter 'childEpicId' when calling deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);
        pathParams.put("child_epic_id", childEpicId);

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

        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}/epics/{child_epic_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove epics relation
     * Unassigns a child epic from a parent epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpic deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(id, epicIid, childEpicId).body(localVarReturnType);
    }

    /**
     * Remove epics relation
     * Unassigns a child epic from a parent epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @return ResponseEntity&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpic> deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(id, epicIid, childEpicId).toEntity(localVarReturnType);
    }

    /**
     * Remove epics relation
     * Unassigns a child epic from a parent epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId) throws RestClientResponseException {
        return deleteApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(id, epicIid, childEpicId);
    }

    /**
     * Remove an issue from the epic
     * Removes an issue from the epic. Does not delete the issue.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param epicIssueId ID of the association.
     * @return APIEntitiesEpicIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIssueId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIssueId' is set
        if (epicIssueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIssueId' when calling deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);
        pathParams.put("epic_issue_id", epicIssueId);

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

        ParameterizedTypeReference<APIEntitiesEpicIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}/issues/{epic_issue_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove an issue from the epic
     * Removes an issue from the epic. Does not delete the issue.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param epicIssueId ID of the association.
     * @return APIEntitiesEpicIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpicIssueLink deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueId(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIssueId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpicIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequestCreation(id, epicIid, epicIssueId).body(localVarReturnType);
    }

    /**
     * Remove an issue from the epic
     * Removes an issue from the epic. Does not delete the issue.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param epicIssueId ID of the association.
     * @return ResponseEntity&lt;APIEntitiesEpicIssueLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpicIssueLink> deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIssueId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpicIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequestCreation(id, epicIid, epicIssueId).toEntity(localVarReturnType);
    }

    /**
     * Remove an issue from the epic
     * Removes an issue from the epic. Does not delete the issue.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param epicIssueId ID of the association.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIssueId) throws RestClientResponseException {
        return deleteApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequestCreation(id, epicIid, epicIssueId);
    }

    /**
     * Delete a related epic link
     * Deletes a two-way relation between two specified epics. The user must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for both groups.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relatedEpicLinkId ID of the related epic link.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesRelatedEpicLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer relatedEpicLinkId, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'relatedEpicLinkId' is set
        if (relatedEpicLinkId == null) {
            throw new RestClientResponseException("Missing the required parameter 'relatedEpicLinkId' when calling deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("related_epic_link_id", relatedEpicLinkId);
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

        ParameterizedTypeReference<APIEntitiesRelatedEpicLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/related_epics/{related_epic_link_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a related epic link
     * Deletes a two-way relation between two specified epics. The user must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for both groups.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relatedEpicLinkId ID of the related epic link.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesRelatedEpicLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRelatedEpicLink deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer relatedEpicLinkId, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelatedEpicLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkIdRequestCreation(id, relatedEpicLinkId, epicIid).body(localVarReturnType);
    }

    /**
     * Delete a related epic link
     * Deletes a two-way relation between two specified epics. The user must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for both groups.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relatedEpicLinkId ID of the related epic link.
     * @param epicIid Internal ID of the epic.
     * @return ResponseEntity&lt;APIEntitiesRelatedEpicLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRelatedEpicLink> deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer relatedEpicLinkId, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelatedEpicLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkIdRequestCreation(id, relatedEpicLinkId, epicIid).toEntity(localVarReturnType);
    }

    /**
     * Delete a related epic link
     * Deletes a two-way relation between two specified epics. The user must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for both groups.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param relatedEpicLinkId ID of the related epic link.
     * @param epicIid Internal ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer relatedEpicLinkId, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        return deleteApiV4GroupsIdEpicsEpicIidRelatedEpicsRelatedEpicLinkIdRequestCreation(id, relatedEpicLinkId, epicIid);
    }

    /**
     * List all group epics
     * Lists all epics for a specified group and its subgroups. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param search Search epics for text present in the title or description.
     * @param state Filter epics by state.
     * @param authorId Return epics authored by the user with the given ID. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return epics authored by the given username. Mutually exclusive with &#x60;author_id&#x60;.
     * @param labels Comma-separated list of label names. Label names from the epic group or a parent group can be used.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param createdAfter Return epics created on or after the specified time.
     * @param createdBefore Return epics created on or before the specified time.
     * @param updatedAfter Return epics updated on or after the specified time.
     * @param updatedBefore Return epics updated on or before the specified time.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s ancestors.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s descendants.
     * @param myReactionEmoji Return epics reacted to by the authenticated user with the given emoji. &#x60;None&#x60; returns epics with no reaction and &#x60;Any&#x60; returns epics with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential epics. If &#x60;false&#x60;, returns only non-confidential epics.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return epics that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notAuthorId Return epics not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return epics not authored by the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @return List&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashEpicsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_labels_details", withLabelsDetails));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestor_groups", includeAncestorGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_descendant_groups", includeDescendantGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "confidential", confidential));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/epics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group epics
     * Lists all epics for a specified group and its subgroups. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param search Search epics for text present in the title or description.
     * @param state Filter epics by state.
     * @param authorId Return epics authored by the user with the given ID. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return epics authored by the given username. Mutually exclusive with &#x60;author_id&#x60;.
     * @param labels Comma-separated list of label names. Label names from the epic group or a parent group can be used.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param createdAfter Return epics created on or after the specified time.
     * @param createdBefore Return epics created on or before the specified time.
     * @param updatedAfter Return epics updated on or after the specified time.
     * @param updatedBefore Return epics updated on or before the specified time.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s ancestors.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s descendants.
     * @param myReactionEmoji Return epics reacted to by the authenticated user with the given emoji. &#x60;None&#x60; returns epics with no reaction and &#x60;Any&#x60; returns epics with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential epics. If &#x60;false&#x60;, returns only non-confidential epics.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return epics that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notAuthorId Return epics not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return epics not authored by the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @return List&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEpic> getApiV4GroupsIdDashEpics(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashEpicsRequestCreation(id, orderBy, sort, search, state, authorId, authorUsername, labels, withLabelsDetails, createdAfter, createdBefore, updatedAfter, updatedBefore, includeAncestorGroups, includeDescendantGroups, myReactionEmoji, confidential, page, perPage, not, notLabels, notAuthorId, notAuthorUsername).body(localVarReturnType);
    }

    /**
     * List all group epics
     * Lists all epics for a specified group and its subgroups. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param search Search epics for text present in the title or description.
     * @param state Filter epics by state.
     * @param authorId Return epics authored by the user with the given ID. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return epics authored by the given username. Mutually exclusive with &#x60;author_id&#x60;.
     * @param labels Comma-separated list of label names. Label names from the epic group or a parent group can be used.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param createdAfter Return epics created on or after the specified time.
     * @param createdBefore Return epics created on or before the specified time.
     * @param updatedAfter Return epics updated on or after the specified time.
     * @param updatedBefore Return epics updated on or before the specified time.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s ancestors.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s descendants.
     * @param myReactionEmoji Return epics reacted to by the authenticated user with the given emoji. &#x60;None&#x60; returns epics with no reaction and &#x60;Any&#x60; returns epics with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential epics. If &#x60;false&#x60;, returns only non-confidential epics.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return epics that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notAuthorId Return epics not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return epics not authored by the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEpic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEpic>> getApiV4GroupsIdDashEpicsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashEpicsRequestCreation(id, orderBy, sort, search, state, authorId, authorUsername, labels, withLabelsDetails, createdAfter, createdBefore, updatedAfter, updatedBefore, includeAncestorGroups, includeDescendantGroups, myReactionEmoji, confidential, page, perPage, not, notLabels, notAuthorId, notAuthorUsername).toEntity(localVarReturnType);
    }

    /**
     * List all group epics
     * Lists all epics for a specified group and its subgroups. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param search Search epics for text present in the title or description.
     * @param state Filter epics by state.
     * @param authorId Return epics authored by the user with the given ID. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return epics authored by the given username. Mutually exclusive with &#x60;author_id&#x60;.
     * @param labels Comma-separated list of label names. Label names from the epic group or a parent group can be used.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param createdAfter Return epics created on or after the specified time.
     * @param createdBefore Return epics created on or before the specified time.
     * @param updatedAfter Return epics updated on or after the specified time.
     * @param updatedBefore Return epics updated on or before the specified time.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s ancestors.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s descendants.
     * @param myReactionEmoji Return epics reacted to by the authenticated user with the given emoji. &#x60;None&#x60; returns epics with no reaction and &#x60;Any&#x60; returns epics with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential epics. If &#x60;false&#x60;, returns only non-confidential epics.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return epics that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notAuthorId Return epics not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return epics not authored by the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashEpicsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername) throws RestClientResponseException {
        return getApiV4GroupsIdDashEpicsRequestCreation(id, orderBy, sort, search, state, authorId, authorUsername, labels, withLabelsDetails, createdAfter, createdBefore, updatedAfter, updatedBefore, includeAncestorGroups, includeDescendantGroups, myReactionEmoji, confidential, page, perPage, not, notLabels, notAuthorId, notAuthorUsername);
    }

    /**
     * Get details of an epic
     * Gets a single epic
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashEpicsEpicIidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashEpicsEpicIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdDashEpicsEpicIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/epics/{epic_iid}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get details of an epic
     * Gets a single epic
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpic getApiV4GroupsIdDashEpicsEpicIid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashEpicsEpicIidRequestCreation(id, epicIid).body(localVarReturnType);
    }

    /**
     * Get details of an epic
     * Gets a single epic
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseEntity&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpic> getApiV4GroupsIdDashEpicsEpicIidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashEpicsEpicIidRequestCreation(id, epicIid).toEntity(localVarReturnType);
    }

    /**
     * Get details of an epic
     * Gets a single epic
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashEpicsEpicIidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        return getApiV4GroupsIdDashEpicsEpicIidRequestCreation(id, epicIid);
    }

    /**
     * Get related epics
     * Lists all child epics of an epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return List&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashEpicsEpicIidEpicsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashEpicsEpicIidEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdDashEpicsEpicIidEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}/epics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get related epics
     * Lists all child epics of an epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return List&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEpic> getApiV4GroupsIdDashEpicsEpicIidEpics(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashEpicsEpicIidEpicsRequestCreation(id, epicIid).body(localVarReturnType);
    }

    /**
     * Get related epics
     * Lists all child epics of an epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEpic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEpic>> getApiV4GroupsIdDashEpicsEpicIidEpicsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashEpicsEpicIidEpicsRequestCreation(id, epicIid).toEntity(localVarReturnType);
    }

    /**
     * Get related epics
     * Lists all child epics of an epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashEpicsEpicIidEpicsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        return getApiV4GroupsIdDashEpicsEpicIidEpicsRequestCreation(id, epicIid);
    }

    /**
     * List all issues for an epic
     * Lists all issues assigned to a specified epic that are available to the authenticated user. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEpicIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashEpicsEpicIidIssuesRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashEpicsEpicIidIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdDashEpicsEpicIidIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesEpicIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/epics/{epic_iid}/issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issues for an epic
     * Lists all issues assigned to a specified epic that are available to the authenticated user. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEpicIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEpicIssue> getApiV4GroupsIdDashEpicsEpicIidIssues(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashEpicsEpicIidIssuesRequestCreation(id, epicIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all issues for an epic
     * Lists all issues assigned to a specified epic that are available to the authenticated user. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEpicIssue&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEpicIssue>> getApiV4GroupsIdDashEpicsEpicIidIssuesWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashEpicsEpicIidIssuesRequestCreation(id, epicIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all issues for an epic
     * Lists all issues assigned to a specified epic that are available to the authenticated user. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashEpicsEpicIidIssuesWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdDashEpicsEpicIidIssuesRequestCreation(id, epicIid, page, perPage);
    }

    /**
     * List all epic boards in a group
     * Lists all epic boards for a specified group. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEpicBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicBoardsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicBoards", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesEpicBoard>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epic_boards", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all epic boards in a group
     * Lists all epic boards for a specified group. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEpicBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEpicBoard> getApiV4GroupsIdEpicBoards(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicBoard>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicBoardsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all epic boards in a group
     * Lists all epic boards for a specified group. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEpicBoard&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEpicBoard>> getApiV4GroupsIdEpicBoardsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicBoard>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicBoardsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all epic boards in a group
     * Lists all epic boards for a specified group. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicBoardsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEpicBoardsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a group epic board
     * Retrieves a specified group epic board. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @return APIEntitiesEpicBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicBoardsBoardIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling getApiV4GroupsIdEpicBoardsBoardId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<APIEntitiesEpicBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epic_boards/{board_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group epic board
     * Retrieves a specified group epic board. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @return APIEntitiesEpicBoard
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpicBoard getApiV4GroupsIdEpicBoardsBoardId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpicBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicBoardsBoardIdRequestCreation(id, boardId).body(localVarReturnType);
    }

    /**
     * Retrieve a group epic board
     * Retrieves a specified group epic board. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @return ResponseEntity&lt;APIEntitiesEpicBoard&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpicBoard> getApiV4GroupsIdEpicBoardsBoardIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpicBoard> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicBoardsBoardIdRequestCreation(id, boardId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group epic board
     * Retrieves a specified group epic board. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicBoardsBoardIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId) throws RestClientResponseException {
        return getApiV4GroupsIdEpicBoardsBoardIdRequestCreation(id, boardId);
    }

    /**
     * List all group epic board lists
     * Lists all group epic board lists for a specified board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEpicBoardsList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicBoardsBoardIdListsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling getApiV4GroupsIdEpicBoardsBoardIdLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);

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

        ParameterizedTypeReference<List<APIEntitiesEpicBoardsList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epic_boards/{board_id}/lists", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group epic board lists
     * Lists all group epic board lists for a specified board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEpicBoardsList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEpicBoardsList> getApiV4GroupsIdEpicBoardsBoardIdLists(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicBoardsList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicBoardsBoardIdListsRequestCreation(id, boardId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group epic board lists
     * Lists all group epic board lists for a specified board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEpicBoardsList&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEpicBoardsList>> getApiV4GroupsIdEpicBoardsBoardIdListsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicBoardsList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicBoardsBoardIdListsRequestCreation(id, boardId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group epic board lists
     * Lists all group epic board lists for a specified board. Does not include &#x60;open&#x60; and &#x60;closed&#x60; lists. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicBoardsBoardIdListsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEpicBoardsBoardIdListsRequestCreation(id, boardId, page, perPage);
    }

    /**
     * Retrieve a group epic board list
     * Retrieves a specified group epic board list. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @param listId ID of the epic board&#39;s list.
     * @return APIEntitiesEpicBoardsList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicBoardsBoardIdListsListIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'boardId' is set
        if (boardId == null) {
            throw new RestClientResponseException("Missing the required parameter 'boardId' when calling getApiV4GroupsIdEpicBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'listId' is set
        if (listId == null) {
            throw new RestClientResponseException("Missing the required parameter 'listId' when calling getApiV4GroupsIdEpicBoardsBoardIdListsListId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("board_id", boardId);
        pathParams.put("list_id", listId);

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

        ParameterizedTypeReference<APIEntitiesEpicBoardsList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epic_boards/{board_id}/lists/{list_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group epic board list
     * Retrieves a specified group epic board list. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @param listId ID of the epic board&#39;s list.
     * @return APIEntitiesEpicBoardsList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpicBoardsList getApiV4GroupsIdEpicBoardsBoardIdListsListId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpicBoardsList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).body(localVarReturnType);
    }

    /**
     * Retrieve a group epic board list
     * Retrieves a specified group epic board list. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @param listId ID of the epic board&#39;s list.
     * @return ResponseEntity&lt;APIEntitiesEpicBoardsList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpicBoardsList> getApiV4GroupsIdEpicBoardsBoardIdListsListIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpicBoardsList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicBoardsBoardIdListsListIdRequestCreation(id, boardId, listId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group epic board list
     * Retrieves a specified group epic board list. This feature was introduced in GitLab 15.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param boardId ID of the epic board.
     * @param listId ID of the epic board&#39;s list.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicBoardsBoardIdListsListIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer boardId, @jakarta.annotation.Nonnull Integer listId) throws RestClientResponseException {
        return getApiV4GroupsIdEpicBoardsBoardIdListsListIdRequestCreation(id, boardId, listId);
    }

    /**
     * List all group epics
     * Lists all epics for a specified group and its subgroups. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param search Search epics for text present in the title or description.
     * @param state Filter epics by state.
     * @param authorId Return epics authored by the user with the given ID. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return epics authored by the given username. Mutually exclusive with &#x60;author_id&#x60;.
     * @param labels Comma-separated list of label names. Label names from the epic group or a parent group can be used.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param createdAfter Return epics created on or after the specified time.
     * @param createdBefore Return epics created on or before the specified time.
     * @param updatedAfter Return epics updated on or after the specified time.
     * @param updatedBefore Return epics updated on or before the specified time.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s ancestors.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s descendants.
     * @param myReactionEmoji Return epics reacted to by the authenticated user with the given emoji. &#x60;None&#x60; returns epics with no reaction and &#x60;Any&#x60; returns epics with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential epics. If &#x60;false&#x60;, returns only non-confidential epics.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return epics that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notAuthorId Return epics not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return epics not authored by the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @return List&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_labels_details", withLabelsDetails));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_ancestor_groups", includeAncestorGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_descendant_groups", includeDescendantGroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "confidential", confidential));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group epics
     * Lists all epics for a specified group and its subgroups. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param search Search epics for text present in the title or description.
     * @param state Filter epics by state.
     * @param authorId Return epics authored by the user with the given ID. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return epics authored by the given username. Mutually exclusive with &#x60;author_id&#x60;.
     * @param labels Comma-separated list of label names. Label names from the epic group or a parent group can be used.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param createdAfter Return epics created on or after the specified time.
     * @param createdBefore Return epics created on or before the specified time.
     * @param updatedAfter Return epics updated on or after the specified time.
     * @param updatedBefore Return epics updated on or before the specified time.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s ancestors.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s descendants.
     * @param myReactionEmoji Return epics reacted to by the authenticated user with the given emoji. &#x60;None&#x60; returns epics with no reaction and &#x60;Any&#x60; returns epics with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential epics. If &#x60;false&#x60;, returns only non-confidential epics.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return epics that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notAuthorId Return epics not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return epics not authored by the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @return List&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEpic> getApiV4GroupsIdEpics(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsRequestCreation(id, orderBy, sort, search, state, authorId, authorUsername, labels, withLabelsDetails, createdAfter, createdBefore, updatedAfter, updatedBefore, includeAncestorGroups, includeDescendantGroups, myReactionEmoji, confidential, page, perPage, not, notLabels, notAuthorId, notAuthorUsername).body(localVarReturnType);
    }

    /**
     * List all group epics
     * Lists all epics for a specified group and its subgroups. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param search Search epics for text present in the title or description.
     * @param state Filter epics by state.
     * @param authorId Return epics authored by the user with the given ID. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return epics authored by the given username. Mutually exclusive with &#x60;author_id&#x60;.
     * @param labels Comma-separated list of label names. Label names from the epic group or a parent group can be used.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param createdAfter Return epics created on or after the specified time.
     * @param createdBefore Return epics created on or before the specified time.
     * @param updatedAfter Return epics updated on or after the specified time.
     * @param updatedBefore Return epics updated on or before the specified time.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s ancestors.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s descendants.
     * @param myReactionEmoji Return epics reacted to by the authenticated user with the given emoji. &#x60;None&#x60; returns epics with no reaction and &#x60;Any&#x60; returns epics with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential epics. If &#x60;false&#x60;, returns only non-confidential epics.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return epics that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notAuthorId Return epics not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return epics not authored by the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEpic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEpic>> getApiV4GroupsIdEpicsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsRequestCreation(id, orderBy, sort, search, state, authorId, authorUsername, labels, withLabelsDetails, createdAfter, createdBefore, updatedAfter, updatedBefore, includeAncestorGroups, includeDescendantGroups, myReactionEmoji, confidential, page, perPage, not, notLabels, notAuthorId, notAuthorUsername).toEntity(localVarReturnType);
    }

    /**
     * List all group epics
     * Lists all epics for a specified group and its subgroups. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param search Search epics for text present in the title or description.
     * @param state Filter epics by state.
     * @param authorId Return epics authored by the user with the given ID. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return epics authored by the given username. Mutually exclusive with &#x60;author_id&#x60;.
     * @param labels Comma-separated list of label names. Label names from the epic group or a parent group can be used.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param createdAfter Return epics created on or after the specified time.
     * @param createdBefore Return epics created on or before the specified time.
     * @param updatedAfter Return epics updated on or after the specified time.
     * @param updatedBefore Return epics updated on or before the specified time.
     * @param includeAncestorGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s ancestors.
     * @param includeDescendantGroups If &#x60;true&#x60;, includes epics from the requested group&#39;s descendants.
     * @param myReactionEmoji Return epics reacted to by the authenticated user with the given emoji. &#x60;None&#x60; returns epics with no reaction and &#x60;Any&#x60; returns epics with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential epics. If &#x60;false&#x60;, returns only non-confidential epics.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return epics that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notAuthorId Return epics not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return epics not authored by the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Boolean includeAncestorGroups, @jakarta.annotation.Nullable Boolean includeDescendantGroups, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsRequestCreation(id, orderBy, sort, search, state, authorId, authorUsername, labels, withLabelsDetails, createdAfter, createdBefore, updatedAfter, updatedBefore, includeAncestorGroups, includeDescendantGroups, myReactionEmoji, confidential, page, perPage, not, notLabels, notAuthorId, notAuthorUsername);
    }

    /**
     * Get details of an epic
     * Gets a single epic
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEpicIidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEpicIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdEpicsEpicIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get details of an epic
     * Gets a single epic
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpic getApiV4GroupsIdEpicsEpicIid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidRequestCreation(id, epicIid).body(localVarReturnType);
    }

    /**
     * Get details of an epic
     * Gets a single epic
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseEntity&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpic> getApiV4GroupsIdEpicsEpicIidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidRequestCreation(id, epicIid).toEntity(localVarReturnType);
    }

    /**
     * Get details of an epic
     * Gets a single epic
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEpicIidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEpicIidRequestCreation(id, epicIid);
    }

    /**
     * List all issues for an epic
     * Lists all issues assigned to a specified epic that are available to the authenticated user. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEpicIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEpicIidIssuesRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEpicIidIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdEpicsEpicIidIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesEpicIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issues for an epic
     * Lists all issues assigned to a specified epic that are available to the authenticated user. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEpicIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEpicIssue> getApiV4GroupsIdEpicsEpicIidIssues(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidIssuesRequestCreation(id, epicIid, page, perPage).body(localVarReturnType);
    }

    /**
     * List all issues for an epic
     * Lists all issues assigned to a specified epic that are available to the authenticated user. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEpicIssue&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEpicIssue>> getApiV4GroupsIdEpicsEpicIidIssuesWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidIssuesRequestCreation(id, epicIid, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all issues for an epic
     * Lists all issues assigned to a specified epic that are available to the authenticated user. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEpicIidIssuesWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEpicIidIssuesRequestCreation(id, epicIid, page, perPage);
    }

    /**
     * List all linked epics for an epic
     * Lists all linked epics for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return List&lt;APIEntitiesRelatedEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdEpicsEpicIidRelatedEpicsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdEpicsEpicIidRelatedEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling getApiV4GroupsIdEpicsEpicIidRelatedEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesRelatedEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/related_epics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all linked epics for an epic
     * Lists all linked epics for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return List&lt;APIEntitiesRelatedEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesRelatedEpic> getApiV4GroupsIdEpicsEpicIidRelatedEpics(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelatedEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidRelatedEpicsRequestCreation(id, epicIid).body(localVarReturnType);
    }

    /**
     * List all linked epics for an epic
     * Lists all linked epics for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseEntity&lt;List&lt;APIEntitiesRelatedEpic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesRelatedEpic>> getApiV4GroupsIdEpicsEpicIidRelatedEpicsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelatedEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdEpicsEpicIidRelatedEpicsRequestCreation(id, epicIid).toEntity(localVarReturnType);
    }

    /**
     * List all linked epics for an epic
     * Lists all linked epics for a specified epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdEpicsEpicIidRelatedEpicsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        return getApiV4GroupsIdEpicsEpicIidRelatedEpicsRequestCreation(id, epicIid);
    }

    /**
     * List all related epic links for a group
     * Lists all related epic links for a specified group and its subgroups. The user needs to have access to both the &#x60;source_epic&#x60; and &#x60;target_epic&#x60; to view the related epic link.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param updatedBefore Return related epic links updated on or before the specified time.
     * @param updatedAfter Return related epic links updated on or after the specified time.
     * @param createdBefore Return related epic links created on or before the specified time.
     * @param createdAfter Return related epic links created on or after the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesRelatedEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdRelatedEpicLinksRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdRelatedEpicLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesRelatedEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/related_epic_links", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all related epic links for a group
     * Lists all related epic links for a specified group and its subgroups. The user needs to have access to both the &#x60;source_epic&#x60; and &#x60;target_epic&#x60; to view the related epic link.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param updatedBefore Return related epic links updated on or before the specified time.
     * @param updatedAfter Return related epic links updated on or after the specified time.
     * @param createdBefore Return related epic links created on or before the specified time.
     * @param createdAfter Return related epic links created on or after the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesRelatedEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesRelatedEpic> getApiV4GroupsIdRelatedEpicLinks(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelatedEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRelatedEpicLinksRequestCreation(id, updatedBefore, updatedAfter, createdBefore, createdAfter, page, perPage).body(localVarReturnType);
    }

    /**
     * List all related epic links for a group
     * Lists all related epic links for a specified group and its subgroups. The user needs to have access to both the &#x60;source_epic&#x60; and &#x60;target_epic&#x60; to view the related epic link.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param updatedBefore Return related epic links updated on or before the specified time.
     * @param updatedAfter Return related epic links updated on or after the specified time.
     * @param createdBefore Return related epic links created on or before the specified time.
     * @param createdAfter Return related epic links created on or after the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesRelatedEpic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesRelatedEpic>> getApiV4GroupsIdRelatedEpicLinksWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelatedEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRelatedEpicLinksRequestCreation(id, updatedBefore, updatedAfter, createdBefore, createdAfter, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all related epic links for a group
     * Lists all related epic links for a specified group and its subgroups. The user needs to have access to both the &#x60;source_epic&#x60; and &#x60;target_epic&#x60; to view the related epic link.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param updatedBefore Return related epic links updated on or before the specified time.
     * @param updatedAfter Return related epic links updated on or after the specified time.
     * @param createdBefore Return related epic links created on or before the specified time.
     * @param createdAfter Return related epic links created on or after the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdRelatedEpicLinksWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdRelatedEpicLinksRequestCreation(id, updatedBefore, updatedAfter, createdBefore, createdAfter, page, perPage);
    }

    /**
     * Create a new epic
     * Creates a new epic
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDashEpicsRequest The postApiV4GroupsIdDashEpicsRequest parameter
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdDashEpicsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashEpicsRequest postApiV4GroupsIdDashEpicsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdDashEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdDashEpicsRequest' is set
        if (postApiV4GroupsIdDashEpicsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdDashEpicsRequest' when calling postApiV4GroupsIdDashEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdDashEpicsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a new epic
     * Creates a new epic
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDashEpicsRequest The postApiV4GroupsIdDashEpicsRequest parameter
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpic postApiV4GroupsIdDashEpics(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashEpicsRequest postApiV4GroupsIdDashEpicsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashEpicsRequestCreation(id, postApiV4GroupsIdDashEpicsRequest).body(localVarReturnType);
    }

    /**
     * Create a new epic
     * Creates a new epic
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDashEpicsRequest The postApiV4GroupsIdDashEpicsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpic> postApiV4GroupsIdDashEpicsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashEpicsRequest postApiV4GroupsIdDashEpicsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashEpicsRequestCreation(id, postApiV4GroupsIdDashEpicsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a new epic
     * Creates a new epic
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDashEpicsRequest The postApiV4GroupsIdDashEpicsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdDashEpicsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashEpicsRequest postApiV4GroupsIdDashEpicsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdDashEpicsRequestCreation(id, postApiV4GroupsIdDashEpicsRequest);
    }

    /**
     * Create and relate epic to a parent
     * Creates a new epic and associates it with the provided parent epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdDashEpicsEpicIidEpicsRequest The postApiV4GroupsIdDashEpicsEpicIidEpicsRequest parameter
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdDashEpicsEpicIidEpicsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashEpicsEpicIidEpicsRequest postApiV4GroupsIdDashEpicsEpicIidEpicsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdDashEpicsEpicIidEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling postApiV4GroupsIdDashEpicsEpicIidEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdDashEpicsEpicIidEpicsRequest' is set
        if (postApiV4GroupsIdDashEpicsEpicIidEpicsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdDashEpicsEpicIidEpicsRequest' when calling postApiV4GroupsIdDashEpicsEpicIidEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}/epics", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdDashEpicsEpicIidEpicsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create and relate epic to a parent
     * Creates a new epic and associates it with the provided parent epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdDashEpicsEpicIidEpicsRequest The postApiV4GroupsIdDashEpicsEpicIidEpicsRequest parameter
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpic postApiV4GroupsIdDashEpicsEpicIidEpics(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashEpicsEpicIidEpicsRequest postApiV4GroupsIdDashEpicsEpicIidEpicsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashEpicsEpicIidEpicsRequestCreation(id, epicIid, postApiV4GroupsIdDashEpicsEpicIidEpicsRequest).body(localVarReturnType);
    }

    /**
     * Create and relate epic to a parent
     * Creates a new epic and associates it with the provided parent epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdDashEpicsEpicIidEpicsRequest The postApiV4GroupsIdDashEpicsEpicIidEpicsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpic> postApiV4GroupsIdDashEpicsEpicIidEpicsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashEpicsEpicIidEpicsRequest postApiV4GroupsIdDashEpicsEpicIidEpicsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashEpicsEpicIidEpicsRequestCreation(id, epicIid, postApiV4GroupsIdDashEpicsEpicIidEpicsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create and relate epic to a parent
     * Creates a new epic and associates it with the provided parent epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdDashEpicsEpicIidEpicsRequest The postApiV4GroupsIdDashEpicsEpicIidEpicsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdDashEpicsEpicIidEpicsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashEpicsEpicIidEpicsRequest postApiV4GroupsIdDashEpicsEpicIidEpicsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdDashEpicsEpicIidEpicsRequestCreation(id, epicIid, postApiV4GroupsIdDashEpicsEpicIidEpicsRequest);
    }

    /**
     * Relate epics
     * Creates an association between two epics, designating one as the parent epic and the other as the child epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'childEpicId' is set
        if (childEpicId == null) {
            throw new RestClientResponseException("Missing the required parameter 'childEpicId' when calling postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);
        pathParams.put("child_epic_id", childEpicId);

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

        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}/epics/{child_epic_id}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Relate epics
     * Creates an association between two epics, designating one as the parent epic and the other as the child epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpic postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(id, epicIid, childEpicId).body(localVarReturnType);
    }

    /**
     * Relate epics
     * Creates an association between two epics, designating one as the parent epic and the other as the child epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @return ResponseEntity&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpic> postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(id, epicIid, childEpicId).toEntity(localVarReturnType);
    }

    /**
     * Relate epics
     * Creates an association between two epics, designating one as the parent epic and the other as the child epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId) throws RestClientResponseException {
        return postApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(id, epicIid, childEpicId);
    }

    /**
     * Add an issue to an epic
     * Adds a specified issue to an epic. If the issue already belongs to another epic, it is removed from the original epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - No matching issue found
     * <p><b>409</b> - Issue already assigned
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param issueId ID of the issue.
     * @return APIEntitiesEpicIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdDashEpicsEpicIidIssuesIssueIdRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter issueId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdDashEpicsEpicIidIssuesIssueId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling postApiV4GroupsIdDashEpicsEpicIidIssuesIssueId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueId' is set
        if (issueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueId' when calling postApiV4GroupsIdDashEpicsEpicIidIssuesIssueId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);
        pathParams.put("issue_id", issueId);

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

        ParameterizedTypeReference<APIEntitiesEpicIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}/issues/{epic_issue_id}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an issue to an epic
     * Adds a specified issue to an epic. If the issue already belongs to another epic, it is removed from the original epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - No matching issue found
     * <p><b>409</b> - Issue already assigned
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param issueId ID of the issue.
     * @return APIEntitiesEpicIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpicIssueLink postApiV4GroupsIdDashEpicsEpicIidIssuesIssueId(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter issueId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpicIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashEpicsEpicIidIssuesIssueIdRequestCreation(id, epicIid, issueId).body(localVarReturnType);
    }

    /**
     * Add an issue to an epic
     * Adds a specified issue to an epic. If the issue already belongs to another epic, it is removed from the original epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - No matching issue found
     * <p><b>409</b> - Issue already assigned
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param issueId ID of the issue.
     * @return ResponseEntity&lt;APIEntitiesEpicIssueLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpicIssueLink> postApiV4GroupsIdDashEpicsEpicIidIssuesIssueIdWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter issueId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpicIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashEpicsEpicIidIssuesIssueIdRequestCreation(id, epicIid, issueId).toEntity(localVarReturnType);
    }

    /**
     * Add an issue to an epic
     * Adds a specified issue to an epic. If the issue already belongs to another epic, it is removed from the original epic.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - No matching issue found
     * <p><b>409</b> - Issue already assigned
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param issueId ID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdDashEpicsEpicIidIssuesIssueIdWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter issueId) throws RestClientResponseException {
        return postApiV4GroupsIdDashEpicsEpicIidIssuesIssueIdRequestCreation(id, epicIid, issueId);
    }

    /**
     * Create a related epic link
     * Creates a two-way relation between two epics. The user must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for both groups.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest The postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest parameter
     * @return APIEntitiesRelatedEpicLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdEpicsEpicIidRelatedEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling postApiV4GroupsIdEpicsEpicIidRelatedEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest' is set
        if (postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest' when calling postApiV4GroupsIdEpicsEpicIidRelatedEpics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesRelatedEpicLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/related_epics", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a related epic link
     * Creates a two-way relation between two epics. The user must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for both groups.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest The postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest parameter
     * @return APIEntitiesRelatedEpicLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRelatedEpicLink postApiV4GroupsIdEpicsEpicIidRelatedEpics(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelatedEpicLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequestCreation(id, epicIid, postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest).body(localVarReturnType);
    }

    /**
     * Create a related epic link
     * Creates a two-way relation between two epics. The user must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for both groups.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest The postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesRelatedEpicLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRelatedEpicLink> postApiV4GroupsIdEpicsEpicIidRelatedEpicsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelatedEpicLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequestCreation(id, epicIid, postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a related epic link
     * Creates a two-way relation between two epics. The user must have the Guest, Planner, Reporter, Developer, Maintainer, or Owner role for both groups.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest The postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdEpicsEpicIidRelatedEpicsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull PostApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequestCreation(id, epicIid, postApiV4GroupsIdEpicsEpicIidRelatedEpicsRequest);
    }

    /**
     * Create a to-do item for an epic
     * Creates a to-do item for the current user on a specified epic. If a to-do item already exists for the user on that epic, status code 304 is returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesTodo
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdEpicsEpicIidTodoRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdEpicsEpicIidTodo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling postApiV4GroupsIdEpicsEpicIidTodo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/epics/{epic_iid}/todo", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a to-do item for an epic
     * Creates a to-do item for the current user on a specified epic. If a to-do item already exists for the user on that epic, status code 304 is returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return APIEntitiesTodo
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTodo postApiV4GroupsIdEpicsEpicIidTodo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsEpicIidTodoRequestCreation(id, epicIid).body(localVarReturnType);
    }

    /**
     * Create a to-do item for an epic
     * Creates a to-do item for the current user on a specified epic. If a to-do item already exists for the user on that epic, status code 304 is returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseEntity&lt;APIEntitiesTodo&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTodo> postApiV4GroupsIdEpicsEpicIidTodoWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTodo> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdEpicsEpicIidTodoRequestCreation(id, epicIid).toEntity(localVarReturnType);
    }

    /**
     * Create a to-do item for an epic
     * Creates a to-do item for the current user on a specified epic. If a to-do item already exists for the user on that epic, status code 304 is returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdEpicsEpicIidTodoWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid) throws RestClientResponseException {
        return postApiV4GroupsIdEpicsEpicIidTodoRequestCreation(id, epicIid);
    }

    /**
     * Update an epic
     * Updates an epic
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param putApiV4GroupsIdDashEpicsEpicIidRequest The putApiV4GroupsIdDashEpicsEpicIidRequest parameter
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdDashEpicsEpicIidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidRequest putApiV4GroupsIdDashEpicsEpicIidRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdDashEpicsEpicIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling putApiV4GroupsIdDashEpicsEpicIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdDashEpicsEpicIidRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an epic
     * Updates an epic
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param putApiV4GroupsIdDashEpicsEpicIidRequest The putApiV4GroupsIdDashEpicsEpicIidRequest parameter
     * @return APIEntitiesEpic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEpic putApiV4GroupsIdDashEpicsEpicIid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidRequest putApiV4GroupsIdDashEpicsEpicIidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdDashEpicsEpicIidRequestCreation(id, epicIid, putApiV4GroupsIdDashEpicsEpicIidRequest).body(localVarReturnType);
    }

    /**
     * Update an epic
     * Updates an epic
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param putApiV4GroupsIdDashEpicsEpicIidRequest The putApiV4GroupsIdDashEpicsEpicIidRequest parameter
     * @return ResponseEntity&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEpic> putApiV4GroupsIdDashEpicsEpicIidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidRequest putApiV4GroupsIdDashEpicsEpicIidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEpic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdDashEpicsEpicIidRequestCreation(id, epicIid, putApiV4GroupsIdDashEpicsEpicIidRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an epic
     * Updates an epic
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param putApiV4GroupsIdDashEpicsEpicIidRequest The putApiV4GroupsIdDashEpicsEpicIidRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdDashEpicsEpicIidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidRequest putApiV4GroupsIdDashEpicsEpicIidRequest) throws RestClientResponseException {
        return putApiV4GroupsIdDashEpicsEpicIidRequestCreation(id, epicIid, putApiV4GroupsIdDashEpicsEpicIidRequest);
    }

    /**
     * Reorder child epics
     * Reorders a child epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @param putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest The putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest parameter
     * @return List&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'childEpicId' is set
        if (childEpicId == null) {
            throw new RestClientResponseException("Missing the required parameter 'childEpicId' when calling putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);
        pathParams.put("child_epic_id", childEpicId);

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

        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}/epics/{child_epic_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reorder child epics
     * Reorders a child epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @param putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest The putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest parameter
     * @return List&lt;APIEntitiesEpic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEpic> putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(id, epicIid, childEpicId, putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest).body(localVarReturnType);
    }

    /**
     * Reorder child epics
     * Reorders a child epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @param putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest The putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest parameter
     * @return ResponseEntity&lt;List&lt;APIEntitiesEpic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEpic>> putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(id, epicIid, childEpicId, putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Reorder child epics
     * Reorders a child epic.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param childEpicId Global ID of the child epic. The internal ID can&#39;t be used because internal IDs can conflict with epics from other groups.
     * @param putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest The putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer epicIid, @jakarta.annotation.Nonnull Integer childEpicId, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequestCreation(id, epicIid, childEpicId, putApiV4GroupsIdDashEpicsEpicIidEpicsChildEpicIdRequest);
    }

    /**
     * Update a related epic link
     * Updates the relation of a specified issue to an epic.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Issue could not be moved!
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param epicIssueId ID of the epic-issue association to update.
     * @param putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest The putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest parameter
     * @return List&lt;APIEntitiesEpicIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIssueId, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIid' is set
        if (epicIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIid' when calling putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'epicIssueId' is set
        if (epicIssueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'epicIssueId' when calling putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("epic_iid", epicIid);
        pathParams.put("epic_issue_id", epicIssueId);

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

        ParameterizedTypeReference<List<APIEntitiesEpicIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)epics/{epic_iid}/issues/{epic_issue_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a related epic link
     * Updates the relation of a specified issue to an epic.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Issue could not be moved!
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param epicIssueId ID of the epic-issue association to update.
     * @param putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest The putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest parameter
     * @return List&lt;APIEntitiesEpicIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEpicIssue> putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueId(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIssueId, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequestCreation(id, epicIid, epicIssueId, putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest).body(localVarReturnType);
    }

    /**
     * Update a related epic link
     * Updates the relation of a specified issue to an epic.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Issue could not be moved!
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param epicIssueId ID of the epic-issue association to update.
     * @param putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest The putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest parameter
     * @return ResponseEntity&lt;List&lt;APIEntitiesEpicIssue&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEpicIssue>> putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIssueId, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEpicIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequestCreation(id, epicIid, epicIssueId, putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a related epic link
     * Updates the relation of a specified issue to an epic.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Issue could not be moved!
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param epicIid Internal ID of the epic.
     * @param epicIssueId ID of the epic-issue association to update.
     * @param putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest The putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIid, @jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter epicIssueId, @jakarta.annotation.Nullable PutApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequestCreation(id, epicIid, epicIssueId, putApiV4GroupsIdDashEpicsEpicIidIssuesEpicIssueIdRequest);
    }
}
