package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGroupLabel;
import ai.gebo.gitlab.client.model.APIEntitiesIssue;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequest;
import ai.gebo.gitlab.client.model.APIEntitiesProjectLabel;

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
public class ResourceSubscriptionsApi {
    private ApiClient apiClient;

    public ResourceSubscriptionsApi() {
        this(new ApiClient());
    }

    public ResourceSubscriptionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Subscribe to a group label
     * Subscribes the currently authenticated user to a specified group label. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdLabelsSubscribableIdSubscribeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdLabelsSubscribableIdSubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'subscribableId' is set
        if (subscribableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'subscribableId' when calling postApiV4GroupsIdLabelsSubscribableIdSubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("subscribable_id", subscribableId);

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

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/labels/{subscribable_id}/subscribe", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Subscribe to a group label
     * Subscribes the currently authenticated user to a specified group label. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupLabel postApiV4GroupsIdLabelsSubscribableIdSubscribe(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdLabelsSubscribableIdSubscribeRequestCreation(id, subscribableId).body(localVarReturnType);
    }

    /**
     * Subscribe to a group label
     * Subscribes the currently authenticated user to a specified group label. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupLabel> postApiV4GroupsIdLabelsSubscribableIdSubscribeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdLabelsSubscribableIdSubscribeRequestCreation(id, subscribableId).toEntity(localVarReturnType);
    }

    /**
     * Subscribe to a group label
     * Subscribes the currently authenticated user to a specified group label. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdLabelsSubscribableIdSubscribeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        return postApiV4GroupsIdLabelsSubscribableIdSubscribeRequestCreation(id, subscribableId);
    }

    /**
     * Unsubscribe to a group label
     * Unsubscribes the currently authenticated user from a specified group label. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdLabelsSubscribableIdUnsubscribeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdLabelsSubscribableIdUnsubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'subscribableId' is set
        if (subscribableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'subscribableId' when calling postApiV4GroupsIdLabelsSubscribableIdUnsubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("subscribable_id", subscribableId);

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

        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/labels/{subscribable_id}/unsubscribe", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unsubscribe to a group label
     * Unsubscribes the currently authenticated user from a specified group label. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesGroupLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupLabel postApiV4GroupsIdLabelsSubscribableIdUnsubscribe(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdLabelsSubscribableIdUnsubscribeRequestCreation(id, subscribableId).body(localVarReturnType);
    }

    /**
     * Unsubscribe to a group label
     * Unsubscribes the currently authenticated user from a specified group label. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseEntity&lt;APIEntitiesGroupLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupLabel> postApiV4GroupsIdLabelsSubscribableIdUnsubscribeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdLabelsSubscribableIdUnsubscribeRequestCreation(id, subscribableId).toEntity(localVarReturnType);
    }

    /**
     * Unsubscribe to a group label
     * Unsubscribes the currently authenticated user from a specified group label. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdLabelsSubscribableIdUnsubscribeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        return postApiV4GroupsIdLabelsSubscribableIdUnsubscribeRequestCreation(id, subscribableId);
    }

    /**
     * Subscribe to an issue
     * Subscribes the currently authenticated user to a specified issue. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesIssue
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesSubscribableIdSubscribeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesSubscribableIdSubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'subscribableId' is set
        if (subscribableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'subscribableId' when calling postApiV4ProjectsIdIssuesSubscribableIdSubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("subscribable_id", subscribableId);

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

        ParameterizedTypeReference<APIEntitiesIssue> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{subscribable_id}/subscribe", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Subscribe to an issue
     * Subscribes the currently authenticated user to a specified issue. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesIssue
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssue postApiV4ProjectsIdIssuesSubscribableIdSubscribe(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssue> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesSubscribableIdSubscribeRequestCreation(id, subscribableId).body(localVarReturnType);
    }

    /**
     * Subscribe to an issue
     * Subscribes the currently authenticated user to a specified issue. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseEntity&lt;APIEntitiesIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssue> postApiV4ProjectsIdIssuesSubscribableIdSubscribeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssue> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesSubscribableIdSubscribeRequestCreation(id, subscribableId).toEntity(localVarReturnType);
    }

    /**
     * Subscribe to an issue
     * Subscribes the currently authenticated user to a specified issue. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesSubscribableIdSubscribeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesSubscribableIdSubscribeRequestCreation(id, subscribableId);
    }

    /**
     * Unsubscribe to an issue
     * Unsubscribes the currently authenticated user from a specified issue. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesIssue
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesSubscribableIdUnsubscribeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesSubscribableIdUnsubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'subscribableId' is set
        if (subscribableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'subscribableId' when calling postApiV4ProjectsIdIssuesSubscribableIdUnsubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("subscribable_id", subscribableId);

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

        ParameterizedTypeReference<APIEntitiesIssue> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{subscribable_id}/unsubscribe", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unsubscribe to an issue
     * Unsubscribes the currently authenticated user from a specified issue. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesIssue
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssue postApiV4ProjectsIdIssuesSubscribableIdUnsubscribe(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssue> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesSubscribableIdUnsubscribeRequestCreation(id, subscribableId).body(localVarReturnType);
    }

    /**
     * Unsubscribe to an issue
     * Unsubscribes the currently authenticated user from a specified issue. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseEntity&lt;APIEntitiesIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssue> postApiV4ProjectsIdIssuesSubscribableIdUnsubscribeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssue> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesSubscribableIdUnsubscribeRequestCreation(id, subscribableId).toEntity(localVarReturnType);
    }

    /**
     * Unsubscribe to an issue
     * Unsubscribes the currently authenticated user from a specified issue. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesSubscribableIdUnsubscribeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesSubscribableIdUnsubscribeRequestCreation(id, subscribableId);
    }

    /**
     * Subscribe to a project label
     * Subscribes the currently authenticated user to a specified project label. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdLabelsSubscribableIdSubscribeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdLabelsSubscribableIdSubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'subscribableId' is set
        if (subscribableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'subscribableId' when calling postApiV4ProjectsIdLabelsSubscribableIdSubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("subscribable_id", subscribableId);

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

        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels/{subscribable_id}/subscribe", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Subscribe to a project label
     * Subscribes the currently authenticated user to a specified project label. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectLabel postApiV4ProjectsIdLabelsSubscribableIdSubscribe(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdLabelsSubscribableIdSubscribeRequestCreation(id, subscribableId).body(localVarReturnType);
    }

    /**
     * Subscribe to a project label
     * Subscribes the currently authenticated user to a specified project label. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseEntity&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectLabel> postApiV4ProjectsIdLabelsSubscribableIdSubscribeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdLabelsSubscribableIdSubscribeRequestCreation(id, subscribableId).toEntity(localVarReturnType);
    }

    /**
     * Subscribe to a project label
     * Subscribes the currently authenticated user to a specified project label. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdLabelsSubscribableIdSubscribeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        return postApiV4ProjectsIdLabelsSubscribableIdSubscribeRequestCreation(id, subscribableId);
    }

    /**
     * Unsubscribe to a project label
     * Unsubscribes the currently authenticated user from a specified project label. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdLabelsSubscribableIdUnsubscribeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdLabelsSubscribableIdUnsubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'subscribableId' is set
        if (subscribableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'subscribableId' when calling postApiV4ProjectsIdLabelsSubscribableIdUnsubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("subscribable_id", subscribableId);

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

        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/labels/{subscribable_id}/unsubscribe", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unsubscribe to a project label
     * Unsubscribes the currently authenticated user from a specified project label. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesProjectLabel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectLabel postApiV4ProjectsIdLabelsSubscribableIdUnsubscribe(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdLabelsSubscribableIdUnsubscribeRequestCreation(id, subscribableId).body(localVarReturnType);
    }

    /**
     * Unsubscribe to a project label
     * Unsubscribes the currently authenticated user from a specified project label. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseEntity&lt;APIEntitiesProjectLabel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectLabel> postApiV4ProjectsIdLabelsSubscribableIdUnsubscribeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectLabel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdLabelsSubscribableIdUnsubscribeRequestCreation(id, subscribableId).toEntity(localVarReturnType);
    }

    /**
     * Unsubscribe to a project label
     * Unsubscribes the currently authenticated user from a specified project label. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdLabelsSubscribableIdUnsubscribeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        return postApiV4ProjectsIdLabelsSubscribableIdUnsubscribeRequestCreation(id, subscribableId);
    }

    /**
     * Subscribe to a merge request
     * Subscribes the currently authenticated user to a specified merge request. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsSubscribableIdSubscribeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsSubscribableIdSubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'subscribableId' is set
        if (subscribableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'subscribableId' when calling postApiV4ProjectsIdMergeRequestsSubscribableIdSubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("subscribable_id", subscribableId);

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

        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{subscribable_id}/subscribe", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Subscribe to a merge request
     * Subscribes the currently authenticated user to a specified merge request. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequest postApiV4ProjectsIdMergeRequestsSubscribableIdSubscribe(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsSubscribableIdSubscribeRequestCreation(id, subscribableId).body(localVarReturnType);
    }

    /**
     * Subscribe to a merge request
     * Subscribes the currently authenticated user to a specified merge request. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseEntity&lt;APIEntitiesMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequest> postApiV4ProjectsIdMergeRequestsSubscribableIdSubscribeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsSubscribableIdSubscribeRequestCreation(id, subscribableId).toEntity(localVarReturnType);
    }

    /**
     * Subscribe to a merge request
     * Subscribes the currently authenticated user to a specified merge request. They will receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsSubscribableIdSubscribeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsSubscribableIdSubscribeRequestCreation(id, subscribableId);
    }

    /**
     * Unsubscribe to a merge request
     * Unsubscribes the currently authenticated user from a specified merge request. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMergeRequestsSubscribableIdUnsubscribeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMergeRequestsSubscribableIdUnsubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'subscribableId' is set
        if (subscribableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'subscribableId' when calling postApiV4ProjectsIdMergeRequestsSubscribableIdUnsubscribe", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("subscribable_id", subscribableId);

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

        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/merge_requests/{subscribable_id}/unsubscribe", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unsubscribe to a merge request
     * Unsubscribes the currently authenticated user from a specified merge request. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return APIEntitiesMergeRequest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMergeRequest postApiV4ProjectsIdMergeRequestsSubscribableIdUnsubscribe(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsSubscribableIdUnsubscribeRequestCreation(id, subscribableId).body(localVarReturnType);
    }

    /**
     * Unsubscribe to a merge request
     * Unsubscribes the currently authenticated user from a specified merge request. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseEntity&lt;APIEntitiesMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMergeRequest> postApiV4ProjectsIdMergeRequestsSubscribableIdUnsubscribeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMergeRequest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMergeRequestsSubscribableIdUnsubscribeRequestCreation(id, subscribableId).toEntity(localVarReturnType);
    }

    /**
     * Unsubscribe to a merge request
     * Unsubscribes the currently authenticated user from a specified merge request. They will no longer receive notifications on changes to this item.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subscribableId Internal ID of the issue or merge request, or ID or title of the label.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMergeRequestsSubscribableIdUnsubscribeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String subscribableId) throws RestClientResponseException {
        return postApiV4ProjectsIdMergeRequestsSubscribableIdUnsubscribeRequestCreation(id, subscribableId);
    }
}
