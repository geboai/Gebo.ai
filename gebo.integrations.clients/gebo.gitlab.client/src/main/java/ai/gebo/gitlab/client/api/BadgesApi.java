package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBadge;
import ai.gebo.gitlab.client.model.APIEntitiesBasicBadgeDetails;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdBadgesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdBadgesRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdBadgesBadgeIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdBadgesBadgeIdRequest;

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
public class BadgesApi {
    private ApiClient apiClient;

    public BadgesApi() {
        this(new ApiClient());
    }

    public BadgesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a badge from a group
     * Deletes a specified badge from a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdBadgesBadgeIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'badgeId' is set
        if (badgeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'badgeId' when calling deleteApiV4GroupsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("badge_id", badgeId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/badges/{badge_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a badge from a group
     * Deletes a specified badge from a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdBadgesBadgeId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdBadgesBadgeIdRequestCreation(id, badgeId).body(localVarReturnType);
    }

    /**
     * Delete a badge from a group
     * Deletes a specified badge from a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdBadgesBadgeIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdBadgesBadgeIdRequestCreation(id, badgeId).toEntity(localVarReturnType);
    }

    /**
     * Delete a badge from a group
     * Deletes a specified badge from a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdBadgesBadgeIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        return deleteApiV4GroupsIdBadgesBadgeIdRequestCreation(id, badgeId);
    }

    /**
     * Delete a badge from a project
     * Deletes a specified badge from a project. To delete group badges, use the group badges endpoint instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdBadgesBadgeIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'badgeId' is set
        if (badgeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'badgeId' when calling deleteApiV4ProjectsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("badge_id", badgeId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/badges/{badge_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a badge from a project
     * Deletes a specified badge from a project. To delete group badges, use the group badges endpoint instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdBadgesBadgeId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdBadgesBadgeIdRequestCreation(id, badgeId).body(localVarReturnType);
    }

    /**
     * Delete a badge from a project
     * Deletes a specified badge from a project. To delete group badges, use the group badges endpoint instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdBadgesBadgeIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdBadgesBadgeIdRequestCreation(id, badgeId).toEntity(localVarReturnType);
    }

    /**
     * Delete a badge from a project
     * Deletes a specified badge from a project. To delete group badges, use the group badges endpoint instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdBadgesBadgeIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdBadgesBadgeIdRequestCreation(id, badgeId);
    }

    /**
     * List all badges for a group
     * Lists all badges for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Name of the badges to return. Case-sensitive.
     * @return List&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBadgesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBadges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesBadge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/badges", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all badges for a group
     * Lists all badges for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Name of the badges to return. Case-sensitive.
     * @return List&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBadge> getApiV4GroupsIdBadges(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBadge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBadgesRequestCreation(id, page, perPage, name).body(localVarReturnType);
    }

    /**
     * List all badges for a group
     * Lists all badges for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Name of the badges to return. Case-sensitive.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBadge&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBadge>> getApiV4GroupsIdBadgesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBadge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBadgesRequestCreation(id, page, perPage, name).toEntity(localVarReturnType);
    }

    /**
     * List all badges for a group
     * Lists all badges for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Name of the badges to return. Case-sensitive.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBadgesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        return getApiV4GroupsIdBadgesRequestCreation(id, page, perPage, name);
    }

    /**
     * Retrieve a badge for a group
     * Retrieves a specified badge for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBadgesBadgeIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'badgeId' is set
        if (badgeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'badgeId' when calling getApiV4GroupsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("badge_id", badgeId);

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

        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/badges/{badge_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a badge for a group
     * Retrieves a specified badge for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBadge getApiV4GroupsIdBadgesBadgeId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBadgesBadgeIdRequestCreation(id, badgeId).body(localVarReturnType);
    }

    /**
     * Retrieve a badge for a group
     * Retrieves a specified badge for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @return ResponseEntity&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBadge> getApiV4GroupsIdBadgesBadgeIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBadgesBadgeIdRequestCreation(id, badgeId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a badge for a group
     * Retrieves a specified badge for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBadgesBadgeIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        return getApiV4GroupsIdBadgesBadgeIdRequestCreation(id, badgeId);
    }

    /**
     * Retrieve a badge preview for a group
     * Previews the final &#x60;link_url&#x60; and &#x60;image_url&#x60; for a specified group after resolving the placeholder interpolation.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param linkUrl URL of the badge link.
     * @param imageUrl URL of the badge image.
     * @return APIEntitiesBasicBadgeDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdBadgesRenderRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String linkUrl, @jakarta.annotation.Nullable String imageUrl) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdBadgesRender", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'linkUrl' is set
        if (linkUrl == null) {
            throw new RestClientResponseException("Missing the required parameter 'linkUrl' when calling getApiV4GroupsIdBadgesRender", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'imageUrl' is set
        if (imageUrl == null) {
            throw new RestClientResponseException("Missing the required parameter 'imageUrl' when calling getApiV4GroupsIdBadgesRender", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "link_url", linkUrl));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "image_url", imageUrl));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesBasicBadgeDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/badges/render", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a badge preview for a group
     * Previews the final &#x60;link_url&#x60; and &#x60;image_url&#x60; for a specified group after resolving the placeholder interpolation.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param linkUrl URL of the badge link.
     * @param imageUrl URL of the badge image.
     * @return APIEntitiesBasicBadgeDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicBadgeDetails getApiV4GroupsIdBadgesRender(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String linkUrl, @jakarta.annotation.Nullable String imageUrl) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicBadgeDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBadgesRenderRequestCreation(id, linkUrl, imageUrl).body(localVarReturnType);
    }

    /**
     * Retrieve a badge preview for a group
     * Previews the final &#x60;link_url&#x60; and &#x60;image_url&#x60; for a specified group after resolving the placeholder interpolation.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param linkUrl URL of the badge link.
     * @param imageUrl URL of the badge image.
     * @return ResponseEntity&lt;APIEntitiesBasicBadgeDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicBadgeDetails> getApiV4GroupsIdBadgesRenderWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String linkUrl, @jakarta.annotation.Nullable String imageUrl) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicBadgeDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdBadgesRenderRequestCreation(id, linkUrl, imageUrl).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a badge preview for a group
     * Previews the final &#x60;link_url&#x60; and &#x60;image_url&#x60; for a specified group after resolving the placeholder interpolation.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param linkUrl URL of the badge link.
     * @param imageUrl URL of the badge image.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdBadgesRenderWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String linkUrl, @jakarta.annotation.Nullable String imageUrl) throws RestClientResponseException {
        return getApiV4GroupsIdBadgesRenderRequestCreation(id, linkUrl, imageUrl);
    }

    /**
     * List all badges for a project
     * Lists all badges for a specified project, including group badges.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Name of the badges to return. Case-sensitive.
     * @return List&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdBadgesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdBadges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesBadge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/badges", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all badges for a project
     * Lists all badges for a specified project, including group badges.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Name of the badges to return. Case-sensitive.
     * @return List&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesBadge> getApiV4ProjectsIdBadges(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBadge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBadgesRequestCreation(id, page, perPage, name).body(localVarReturnType);
    }

    /**
     * List all badges for a project
     * Lists all badges for a specified project, including group badges.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Name of the badges to return. Case-sensitive.
     * @return ResponseEntity&lt;List&lt;APIEntitiesBadge&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesBadge>> getApiV4ProjectsIdBadgesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesBadge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBadgesRequestCreation(id, page, perPage, name).toEntity(localVarReturnType);
    }

    /**
     * List all badges for a project
     * Lists all badges for a specified project, including group badges.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Name of the badges to return. Case-sensitive.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdBadgesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        return getApiV4ProjectsIdBadgesRequestCreation(id, page, perPage, name);
    }

    /**
     * Retrieve a badge for a project
     * Retrieves a specified badge for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdBadgesBadgeIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'badgeId' is set
        if (badgeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'badgeId' when calling getApiV4ProjectsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("badge_id", badgeId);

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

        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/badges/{badge_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a badge for a project
     * Retrieves a specified badge for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBadge getApiV4ProjectsIdBadgesBadgeId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBadgesBadgeIdRequestCreation(id, badgeId).body(localVarReturnType);
    }

    /**
     * Retrieve a badge for a project
     * Retrieves a specified badge for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @return ResponseEntity&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBadge> getApiV4ProjectsIdBadgesBadgeIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBadgesBadgeIdRequestCreation(id, badgeId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a badge for a project
     * Retrieves a specified badge for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdBadgesBadgeIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId) throws RestClientResponseException {
        return getApiV4ProjectsIdBadgesBadgeIdRequestCreation(id, badgeId);
    }

    /**
     * Retrieve a badge preview for a project
     * Previews the final &#x60;link_url&#x60; and &#x60;image_url&#x60; for a specified project after resolving the placeholder interpolation.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param linkUrl URL of the badge link.
     * @param imageUrl URL of the badge image.
     * @return APIEntitiesBasicBadgeDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdBadgesRenderRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String linkUrl, @jakarta.annotation.Nullable String imageUrl) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdBadgesRender", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'linkUrl' is set
        if (linkUrl == null) {
            throw new RestClientResponseException("Missing the required parameter 'linkUrl' when calling getApiV4ProjectsIdBadgesRender", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'imageUrl' is set
        if (imageUrl == null) {
            throw new RestClientResponseException("Missing the required parameter 'imageUrl' when calling getApiV4ProjectsIdBadgesRender", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "link_url", linkUrl));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "image_url", imageUrl));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesBasicBadgeDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/badges/render", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a badge preview for a project
     * Previews the final &#x60;link_url&#x60; and &#x60;image_url&#x60; for a specified project after resolving the placeholder interpolation.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param linkUrl URL of the badge link.
     * @param imageUrl URL of the badge image.
     * @return APIEntitiesBasicBadgeDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicBadgeDetails getApiV4ProjectsIdBadgesRender(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String linkUrl, @jakarta.annotation.Nullable String imageUrl) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicBadgeDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBadgesRenderRequestCreation(id, linkUrl, imageUrl).body(localVarReturnType);
    }

    /**
     * Retrieve a badge preview for a project
     * Previews the final &#x60;link_url&#x60; and &#x60;image_url&#x60; for a specified project after resolving the placeholder interpolation.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param linkUrl URL of the badge link.
     * @param imageUrl URL of the badge image.
     * @return ResponseEntity&lt;APIEntitiesBasicBadgeDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicBadgeDetails> getApiV4ProjectsIdBadgesRenderWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String linkUrl, @jakarta.annotation.Nullable String imageUrl) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicBadgeDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdBadgesRenderRequestCreation(id, linkUrl, imageUrl).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a badge preview for a project
     * Previews the final &#x60;link_url&#x60; and &#x60;image_url&#x60; for a specified project after resolving the placeholder interpolation.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param linkUrl URL of the badge link.
     * @param imageUrl URL of the badge image.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdBadgesRenderWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String linkUrl, @jakarta.annotation.Nullable String imageUrl) throws RestClientResponseException {
        return getApiV4ProjectsIdBadgesRenderRequestCreation(id, linkUrl, imageUrl);
    }

    /**
     * Create a badge for a group
     * Creates a badge for a specified group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdBadgesRequest The postApiV4GroupsIdBadgesRequest parameter
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdBadgesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdBadgesRequest postApiV4GroupsIdBadgesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdBadges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdBadgesRequest' is set
        if (postApiV4GroupsIdBadgesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdBadgesRequest' when calling postApiV4GroupsIdBadges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/badges", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdBadgesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a badge for a group
     * Creates a badge for a specified group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdBadgesRequest The postApiV4GroupsIdBadgesRequest parameter
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBadge postApiV4GroupsIdBadges(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdBadgesRequest postApiV4GroupsIdBadgesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdBadgesRequestCreation(id, postApiV4GroupsIdBadgesRequest).body(localVarReturnType);
    }

    /**
     * Create a badge for a group
     * Creates a badge for a specified group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdBadgesRequest The postApiV4GroupsIdBadgesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBadge> postApiV4GroupsIdBadgesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdBadgesRequest postApiV4GroupsIdBadgesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdBadgesRequestCreation(id, postApiV4GroupsIdBadgesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a badge for a group
     * Creates a badge for a specified group.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdBadgesRequest The postApiV4GroupsIdBadgesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdBadgesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdBadgesRequest postApiV4GroupsIdBadgesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdBadgesRequestCreation(id, postApiV4GroupsIdBadgesRequest);
    }

    /**
     * Create a badge for a project
     * Creates a badge for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdBadgesRequest The postApiV4ProjectsIdBadgesRequest parameter
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdBadgesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdBadgesRequest postApiV4ProjectsIdBadgesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdBadges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdBadgesRequest' is set
        if (postApiV4ProjectsIdBadgesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdBadgesRequest' when calling postApiV4ProjectsIdBadges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/badges", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdBadgesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a badge for a project
     * Creates a badge for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdBadgesRequest The postApiV4ProjectsIdBadgesRequest parameter
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBadge postApiV4ProjectsIdBadges(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdBadgesRequest postApiV4ProjectsIdBadgesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdBadgesRequestCreation(id, postApiV4ProjectsIdBadgesRequest).body(localVarReturnType);
    }

    /**
     * Create a badge for a project
     * Creates a badge for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdBadgesRequest The postApiV4ProjectsIdBadgesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBadge> postApiV4ProjectsIdBadgesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdBadgesRequest postApiV4ProjectsIdBadgesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdBadgesRequestCreation(id, postApiV4ProjectsIdBadgesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a badge for a project
     * Creates a badge for a specified project.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdBadgesRequest The postApiV4ProjectsIdBadgesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdBadgesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdBadgesRequest postApiV4ProjectsIdBadgesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdBadgesRequestCreation(id, postApiV4ProjectsIdBadgesRequest);
    }

    /**
     * Update a badge for a group
     * Updates a specified badge for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @param putApiV4GroupsIdBadgesBadgeIdRequest The putApiV4GroupsIdBadgesBadgeIdRequest parameter
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdBadgesBadgeIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId, @jakarta.annotation.Nullable PutApiV4GroupsIdBadgesBadgeIdRequest putApiV4GroupsIdBadgesBadgeIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'badgeId' is set
        if (badgeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'badgeId' when calling putApiV4GroupsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("badge_id", badgeId);

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

        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/badges/{badge_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdBadgesBadgeIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a badge for a group
     * Updates a specified badge for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @param putApiV4GroupsIdBadgesBadgeIdRequest The putApiV4GroupsIdBadgesBadgeIdRequest parameter
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBadge putApiV4GroupsIdBadgesBadgeId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId, @jakarta.annotation.Nullable PutApiV4GroupsIdBadgesBadgeIdRequest putApiV4GroupsIdBadgesBadgeIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdBadgesBadgeIdRequestCreation(id, badgeId, putApiV4GroupsIdBadgesBadgeIdRequest).body(localVarReturnType);
    }

    /**
     * Update a badge for a group
     * Updates a specified badge for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @param putApiV4GroupsIdBadgesBadgeIdRequest The putApiV4GroupsIdBadgesBadgeIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBadge> putApiV4GroupsIdBadgesBadgeIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId, @jakarta.annotation.Nullable PutApiV4GroupsIdBadgesBadgeIdRequest putApiV4GroupsIdBadgesBadgeIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdBadgesBadgeIdRequestCreation(id, badgeId, putApiV4GroupsIdBadgesBadgeIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a badge for a group
     * Updates a specified badge for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param badgeId ID of the badge.
     * @param putApiV4GroupsIdBadgesBadgeIdRequest The putApiV4GroupsIdBadgesBadgeIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdBadgesBadgeIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId, @jakarta.annotation.Nullable PutApiV4GroupsIdBadgesBadgeIdRequest putApiV4GroupsIdBadgesBadgeIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdBadgesBadgeIdRequestCreation(id, badgeId, putApiV4GroupsIdBadgesBadgeIdRequest);
    }

    /**
     * Update a badge for a project
     * Updates a specified badge for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @param putApiV4ProjectsIdBadgesBadgeIdRequest The putApiV4ProjectsIdBadgesBadgeIdRequest parameter
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdBadgesBadgeIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId, @jakarta.annotation.Nullable PutApiV4ProjectsIdBadgesBadgeIdRequest putApiV4ProjectsIdBadgesBadgeIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'badgeId' is set
        if (badgeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'badgeId' when calling putApiV4ProjectsIdBadgesBadgeId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("badge_id", badgeId);

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

        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/badges/{badge_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdBadgesBadgeIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a badge for a project
     * Updates a specified badge for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @param putApiV4ProjectsIdBadgesBadgeIdRequest The putApiV4ProjectsIdBadgesBadgeIdRequest parameter
     * @return APIEntitiesBadge
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBadge putApiV4ProjectsIdBadgesBadgeId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId, @jakarta.annotation.Nullable PutApiV4ProjectsIdBadgesBadgeIdRequest putApiV4ProjectsIdBadgesBadgeIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdBadgesBadgeIdRequestCreation(id, badgeId, putApiV4ProjectsIdBadgesBadgeIdRequest).body(localVarReturnType);
    }

    /**
     * Update a badge for a project
     * Updates a specified badge for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @param putApiV4ProjectsIdBadgesBadgeIdRequest The putApiV4ProjectsIdBadgesBadgeIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBadge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBadge> putApiV4ProjectsIdBadgesBadgeIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId, @jakarta.annotation.Nullable PutApiV4ProjectsIdBadgesBadgeIdRequest putApiV4ProjectsIdBadgesBadgeIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBadge> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdBadgesBadgeIdRequestCreation(id, badgeId, putApiV4ProjectsIdBadgesBadgeIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a badge for a project
     * Updates a specified badge for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param badgeId ID of the badge.
     * @param putApiV4ProjectsIdBadgesBadgeIdRequest The putApiV4ProjectsIdBadgesBadgeIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdBadgesBadgeIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer badgeId, @jakarta.annotation.Nullable PutApiV4ProjectsIdBadgesBadgeIdRequest putApiV4ProjectsIdBadgesBadgeIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdBadgesBadgeIdRequestCreation(id, badgeId, putApiV4ProjectsIdBadgesBadgeIdRequest);
    }
}
