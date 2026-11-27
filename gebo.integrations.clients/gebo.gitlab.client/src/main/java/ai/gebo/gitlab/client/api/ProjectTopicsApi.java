package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesProjectsTopic;
import java.io.File;
import ai.gebo.gitlab.client.model.PostApiV4TopicsMergeRequest;

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
public class ProjectTopicsApi {
    private ApiClient apiClient;

    public ProjectTopicsApi() {
        this(new ApiClient());
    }

    public ProjectTopicsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a project topic
     * Deletes a specified project topic. Administrators only. Deleting a topic also deletes its assignment to projects.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4TopicsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4TopicsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/topics/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project topic
     * Deletes a specified project topic. Administrators only. Deleting a topic also deletes its assignment to projects.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4TopicsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4TopicsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a project topic
     * Deletes a specified project topic. Administrators only. Deleting a topic also deletes its assignment to projects.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4TopicsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4TopicsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a project topic
     * Deletes a specified project topic. Administrators only. Deleting a topic also deletes its assignment to projects.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4TopicsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4TopicsIdRequestCreation(id);
    }

    /**
     * List all topics
     * Lists all project topics sorted by the number of associated projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search topics against their &#x60;name&#x60;.
     * @param withoutProjects If &#x60;true&#x60;, returns only topics that have no assigned projects.
     * @param organizationId Filter topics by organization ID.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectsTopic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TopicsRequestCreation(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean withoutProjects, @jakarta.annotation.Nullable Integer organizationId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "without_projects", withoutProjects));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "organization_id", organizationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesProjectsTopic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/topics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all topics
     * Lists all project topics sorted by the number of associated projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search topics against their &#x60;name&#x60;.
     * @param withoutProjects If &#x60;true&#x60;, returns only topics that have no assigned projects.
     * @param organizationId Filter topics by organization ID.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectsTopic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProjectsTopic> getApiV4Topics(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean withoutProjects, @jakarta.annotation.Nullable Integer organizationId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectsTopic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TopicsRequestCreation(search, withoutProjects, organizationId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all topics
     * Lists all project topics sorted by the number of associated projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search topics against their &#x60;name&#x60;.
     * @param withoutProjects If &#x60;true&#x60;, returns only topics that have no assigned projects.
     * @param organizationId Filter topics by organization ID.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProjectsTopic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProjectsTopic>> getApiV4TopicsWithHttpInfo(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean withoutProjects, @jakarta.annotation.Nullable Integer organizationId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectsTopic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TopicsRequestCreation(search, withoutProjects, organizationId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all topics
     * Lists all project topics sorted by the number of associated projects.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search topics against their &#x60;name&#x60;.
     * @param withoutProjects If &#x60;true&#x60;, returns only topics that have no assigned projects.
     * @param organizationId Filter topics by organization ID.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TopicsWithResponseSpec(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean withoutProjects, @jakarta.annotation.Nullable Integer organizationId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4TopicsRequestCreation(search, withoutProjects, organizationId, page, perPage);
    }

    /**
     * Retrieve a topic
     * Retrieves a specified project topic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @return APIEntitiesProjectsTopic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TopicsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4TopicsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/topics/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a topic
     * Retrieves a specified project topic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @return APIEntitiesProjectsTopic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectsTopic getApiV4TopicsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TopicsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a topic
     * Retrieves a specified project topic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @return ResponseEntity&lt;APIEntitiesProjectsTopic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectsTopic> getApiV4TopicsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TopicsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a topic
     * Retrieves a specified project topic.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TopicsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4TopicsIdRequestCreation(id);
    }

    /**
     * Create a project topic
     * Creates a project topic. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param name Slug (name)
     * @param title Title
     * @param description Description
     * @param avatar Avatar image for topic
     * @param organizationId The organization id for the topic. This parameter is experimental and might be changed or removed without notice.
     * @return APIEntitiesProjectsTopic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4TopicsRequestCreation(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer organizationId) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4Topics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'title' is set
        if (title == null) {
            throw new RestClientResponseException("Missing the required parameter 'title' when calling postApiV4Topics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("name", name);
        formParams.add("title", title);
        if (description != null) {
            formParams.add("description", description);
        }
        if (avatar != null) {
            formParams.add("avatar", new FileSystemResource(avatar));
        }
        if (organizationId != null) {
            formParams.add("organization_id", organizationId);
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

        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/topics", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a project topic
     * Creates a project topic. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param name Slug (name)
     * @param title Title
     * @param description Description
     * @param avatar Avatar image for topic
     * @param organizationId The organization id for the topic. This parameter is experimental and might be changed or removed without notice.
     * @return APIEntitiesProjectsTopic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectsTopic postApiV4Topics(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer organizationId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4TopicsRequestCreation(name, title, description, avatar, organizationId).body(localVarReturnType);
    }

    /**
     * Create a project topic
     * Creates a project topic. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param name Slug (name)
     * @param title Title
     * @param description Description
     * @param avatar Avatar image for topic
     * @param organizationId The organization id for the topic. This parameter is experimental and might be changed or removed without notice.
     * @return ResponseEntity&lt;APIEntitiesProjectsTopic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectsTopic> postApiV4TopicsWithHttpInfo(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer organizationId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4TopicsRequestCreation(name, title, description, avatar, organizationId).toEntity(localVarReturnType);
    }

    /**
     * Create a project topic
     * Creates a project topic. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param name Slug (name)
     * @param title Title
     * @param description Description
     * @param avatar Avatar image for topic
     * @param organizationId The organization id for the topic. This parameter is experimental and might be changed or removed without notice.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4TopicsWithResponseSpec(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer organizationId) throws RestClientResponseException {
        return postApiV4TopicsRequestCreation(name, title, description, avatar, organizationId);
    }

    /**
     * Merge topics
     * Merges a source topic into a target topic. This action deletes the source topic and moves all assigned projects to the target topic. Administrators only. The source and target topics must belong to the same organization.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4TopicsMergeRequest The postApiV4TopicsMergeRequest parameter
     * @return APIEntitiesProjectsTopic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4TopicsMergeRequestCreation(@jakarta.annotation.Nonnull PostApiV4TopicsMergeRequest postApiV4TopicsMergeRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4TopicsMergeRequest' is set
        if (postApiV4TopicsMergeRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4TopicsMergeRequest' when calling postApiV4TopicsMerge", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/topics/merge", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4TopicsMergeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Merge topics
     * Merges a source topic into a target topic. This action deletes the source topic and moves all assigned projects to the target topic. Administrators only. The source and target topics must belong to the same organization.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4TopicsMergeRequest The postApiV4TopicsMergeRequest parameter
     * @return APIEntitiesProjectsTopic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectsTopic postApiV4TopicsMerge(@jakarta.annotation.Nonnull PostApiV4TopicsMergeRequest postApiV4TopicsMergeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4TopicsMergeRequestCreation(postApiV4TopicsMergeRequest).body(localVarReturnType);
    }

    /**
     * Merge topics
     * Merges a source topic into a target topic. This action deletes the source topic and moves all assigned projects to the target topic. Administrators only. The source and target topics must belong to the same organization.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4TopicsMergeRequest The postApiV4TopicsMergeRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectsTopic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectsTopic> postApiV4TopicsMergeWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4TopicsMergeRequest postApiV4TopicsMergeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4TopicsMergeRequestCreation(postApiV4TopicsMergeRequest).toEntity(localVarReturnType);
    }

    /**
     * Merge topics
     * Merges a source topic into a target topic. This action deletes the source topic and moves all assigned projects to the target topic. Administrators only. The source and target topics must belong to the same organization.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4TopicsMergeRequest The postApiV4TopicsMergeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4TopicsMergeWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4TopicsMergeRequest postApiV4TopicsMergeRequest) throws RestClientResponseException {
        return postApiV4TopicsMergeRequestCreation(postApiV4TopicsMergeRequest);
    }

    /**
     * Update a project topic
     * Updates a specified project topic. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @param name Slug (name)
     * @param title Title
     * @param description Description
     * @param avatar Avatar image for topic
     * @return APIEntitiesProjectsTopic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4TopicsIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4TopicsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        if (title != null) {
            formParams.add("title", title);
        }
        if (description != null) {
            formParams.add("description", description);
        }
        if (avatar != null) {
            formParams.add("avatar", new FileSystemResource(avatar));
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

        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/topics/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a project topic
     * Updates a specified project topic. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @param name Slug (name)
     * @param title Title
     * @param description Description
     * @param avatar Avatar image for topic
     * @return APIEntitiesProjectsTopic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectsTopic putApiV4TopicsId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4TopicsIdRequestCreation(id, name, title, description, avatar).body(localVarReturnType);
    }

    /**
     * Update a project topic
     * Updates a specified project topic. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @param name Slug (name)
     * @param title Title
     * @param description Description
     * @param avatar Avatar image for topic
     * @return ResponseEntity&lt;APIEntitiesProjectsTopic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectsTopic> putApiV4TopicsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectsTopic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4TopicsIdRequestCreation(id, name, title, description, avatar).toEntity(localVarReturnType);
    }

    /**
     * Update a project topic
     * Updates a specified project topic. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project topic.
     * @param name Slug (name)
     * @param title Title
     * @param description Description
     * @param avatar Avatar image for topic
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4TopicsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        return putApiV4TopicsIdRequestCreation(id, name, title, description, avatar);
    }
}
