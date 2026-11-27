package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesTag;
import ai.gebo.gitlab.client.model.APIEntitiesTagSignature;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRepositoryTagsRequest;

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
public class TagsApi {
    private ApiClient apiClient;

    public TagsApi() {
        this(new ApiClient());
    }

    public TagsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a tag
     * Deletes a specified repository tag.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRepositoryTagsTagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRepositoryTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling deleteApiV4ProjectsIdRepositoryTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/tags/{tag_name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a tag
     * Deletes a specified repository tag.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdRepositoryTagsTagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdRepositoryTagsTagNameRequestCreation(id, tagName).body(localVarReturnType);
    }

    /**
     * Delete a tag
     * Deletes a specified repository tag.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdRepositoryTagsTagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRepositoryTagsTagNameRequestCreation(id, tagName).toEntity(localVarReturnType);
    }

    /**
     * Delete a tag
     * Deletes a specified repository tag.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition failed
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRepositoryTagsTagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRepositoryTagsTagNameRequestCreation(id, tagName);
    }

    /**
     * List all project repository tags
     * Lists all repository tags from a project, sorted by update date and time in descending order. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sort Sort results in ascending or descending order.
     * @param orderBy Sort results by the specified field. Sorting by version uses the semantic version number.
     * @param search Return only tags matching the search criteria. Use &#x60;^term&#x60; to find tags that begin with &#x60;term&#x60;, and &#x60;term$&#x60; to find tags that end with &#x60;term&#x60;. No other regular expressions are supported.
     * @param pageToken Name of the tag to start the pagination from. Used for keyset pagination.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryTagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String pageToken, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page_token", pageToken));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesTag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/tags", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project repository tags
     * Lists all repository tags from a project, sorted by update date and time in descending order. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sort Sort results in ascending or descending order.
     * @param orderBy Sort results by the specified field. Sorting by version uses the semantic version number.
     * @param search Return only tags matching the search criteria. Use &#x60;^term&#x60; to find tags that begin with &#x60;term&#x60;, and &#x60;term$&#x60; to find tags that end with &#x60;term&#x60;. No other regular expressions are supported.
     * @param pageToken Name of the tag to start the pagination from. Used for keyset pagination.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesTag> getApiV4ProjectsIdRepositoryTags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String pageToken, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryTagsRequestCreation(id, sort, orderBy, search, pageToken, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project repository tags
     * Lists all repository tags from a project, sorted by update date and time in descending order. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sort Sort results in ascending or descending order.
     * @param orderBy Sort results by the specified field. Sorting by version uses the semantic version number.
     * @param search Return only tags matching the search criteria. Use &#x60;^term&#x60; to find tags that begin with &#x60;term&#x60;, and &#x60;term$&#x60; to find tags that end with &#x60;term&#x60;. No other regular expressions are supported.
     * @param pageToken Name of the tag to start the pagination from. Used for keyset pagination.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesTag&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesTag>> getApiV4ProjectsIdRepositoryTagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String pageToken, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryTagsRequestCreation(id, sort, orderBy, search, pageToken, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project repository tags
     * Lists all repository tags from a project, sorted by update date and time in descending order. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param sort Sort results in ascending or descending order.
     * @param orderBy Sort results by the specified field. Sorting by version uses the semantic version number.
     * @param search Return only tags matching the search criteria. Use &#x60;^term&#x60; to find tags that begin with &#x60;term&#x60;, and &#x60;term$&#x60; to find tags that end with &#x60;term&#x60;. No other regular expressions are supported.
     * @param pageToken Name of the tag to start the pagination from. Used for keyset pagination.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryTagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String pageToken, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryTagsRequestCreation(id, sort, orderBy, search, pageToken, page, perPage);
    }

    /**
     * Retrieve a single repository tag
     * Retrieves a repository tag with a specified name. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @return APIEntitiesTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryTagsTagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling getApiV4ProjectsIdRepositoryTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);

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

        ParameterizedTypeReference<APIEntitiesTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/tags/{tag_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a single repository tag
     * Retrieves a repository tag with a specified name. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @return APIEntitiesTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTag getApiV4ProjectsIdRepositoryTagsTagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryTagsTagNameRequestCreation(id, tagName).body(localVarReturnType);
    }

    /**
     * Retrieve a single repository tag
     * Retrieves a repository tag with a specified name. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @return ResponseEntity&lt;APIEntitiesTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTag> getApiV4ProjectsIdRepositoryTagsTagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryTagsTagNameRequestCreation(id, tagName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a single repository tag
     * Retrieves a repository tag with a specified name. This endpoint can be accessed without authentication if the repository is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryTagsTagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryTagsTagNameRequestCreation(id, tagName);
    }

    /**
     * Retrieve X.509 signature of a tag
     * Retrieves the X.509 signature from a signed tag. Unsigned tags return a &#x60;404 Not Found&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @return APIEntitiesTagSignature
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRepositoryTagsTagNameSignatureRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRepositoryTagsTagNameSignature", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling getApiV4ProjectsIdRepositoryTagsTagNameSignature", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);

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

        ParameterizedTypeReference<APIEntitiesTagSignature> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/tags/{tag_name}/signature", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve X.509 signature of a tag
     * Retrieves the X.509 signature from a signed tag. Unsigned tags return a &#x60;404 Not Found&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @return APIEntitiesTagSignature
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTagSignature getApiV4ProjectsIdRepositoryTagsTagNameSignature(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTagSignature> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryTagsTagNameSignatureRequestCreation(id, tagName).body(localVarReturnType);
    }

    /**
     * Retrieve X.509 signature of a tag
     * Retrieves the X.509 signature from a signed tag. Unsigned tags return a &#x60;404 Not Found&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @return ResponseEntity&lt;APIEntitiesTagSignature&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTagSignature> getApiV4ProjectsIdRepositoryTagsTagNameSignatureWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTagSignature> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRepositoryTagsTagNameSignatureRequestCreation(id, tagName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve X.509 signature of a tag
     * Retrieves the X.509 signature from a signed tag. Unsigned tags return a &#x60;404 Not Found&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Name of the tag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRepositoryTagsTagNameSignatureWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        return getApiV4ProjectsIdRepositoryTagsTagNameSignatureRequestCreation(id, tagName);
    }

    /**
     * Create a tag
     * Creates a tag in the repository that points to a specified reference. This endpoint is rate-limited for each project. The default is 100 requests every 30 minutes, configurable with the &#x60;tags_create_limit&#x60; application setting. Set the limit to &#x60;0&#x60; to disable it. The limit is shared between this endpoint, the GraphQL &#x60;tagCreate&#x60; mutation, tag creation in the UI, and the &#x60;/tag&#x60; quick action. The limit applies to the project, so you can receive &#x60;429 Too Many Requests&#x60; after other users of the project reach the limit. The type of tag created determines the contents of &#x60;created_at&#x60;, &#x60;target&#x60;, and &#x60;message&#x60;. For annotated tags, &#x60;created_at&#x60; contains the timestamp of tag creation, &#x60;message&#x60; contains the annotation, and &#x60;target&#x60; contains the ID of the tag object. For lightweight tags, &#x60;created_at&#x60; and &#x60;message&#x60; are null, and &#x60;target&#x60; contains the commit ID.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryTagsRequest The postApiV4ProjectsIdRepositoryTagsRequest parameter
     * @return APIEntitiesTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRepositoryTagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryTagsRequest postApiV4ProjectsIdRepositoryTagsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRepositoryTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRepositoryTagsRequest' is set
        if (postApiV4ProjectsIdRepositoryTagsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRepositoryTagsRequest' when calling postApiV4ProjectsIdRepositoryTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/tags", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRepositoryTagsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a tag
     * Creates a tag in the repository that points to a specified reference. This endpoint is rate-limited for each project. The default is 100 requests every 30 minutes, configurable with the &#x60;tags_create_limit&#x60; application setting. Set the limit to &#x60;0&#x60; to disable it. The limit is shared between this endpoint, the GraphQL &#x60;tagCreate&#x60; mutation, tag creation in the UI, and the &#x60;/tag&#x60; quick action. The limit applies to the project, so you can receive &#x60;429 Too Many Requests&#x60; after other users of the project reach the limit. The type of tag created determines the contents of &#x60;created_at&#x60;, &#x60;target&#x60;, and &#x60;message&#x60;. For annotated tags, &#x60;created_at&#x60; contains the timestamp of tag creation, &#x60;message&#x60; contains the annotation, and &#x60;target&#x60; contains the ID of the tag object. For lightweight tags, &#x60;created_at&#x60; and &#x60;message&#x60; are null, and &#x60;target&#x60; contains the commit ID.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryTagsRequest The postApiV4ProjectsIdRepositoryTagsRequest parameter
     * @return APIEntitiesTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTag postApiV4ProjectsIdRepositoryTags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryTagsRequest postApiV4ProjectsIdRepositoryTagsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryTagsRequestCreation(id, postApiV4ProjectsIdRepositoryTagsRequest).body(localVarReturnType);
    }

    /**
     * Create a tag
     * Creates a tag in the repository that points to a specified reference. This endpoint is rate-limited for each project. The default is 100 requests every 30 minutes, configurable with the &#x60;tags_create_limit&#x60; application setting. Set the limit to &#x60;0&#x60; to disable it. The limit is shared between this endpoint, the GraphQL &#x60;tagCreate&#x60; mutation, tag creation in the UI, and the &#x60;/tag&#x60; quick action. The limit applies to the project, so you can receive &#x60;429 Too Many Requests&#x60; after other users of the project reach the limit. The type of tag created determines the contents of &#x60;created_at&#x60;, &#x60;target&#x60;, and &#x60;message&#x60;. For annotated tags, &#x60;created_at&#x60; contains the timestamp of tag creation, &#x60;message&#x60; contains the annotation, and &#x60;target&#x60; contains the ID of the tag object. For lightweight tags, &#x60;created_at&#x60; and &#x60;message&#x60; are null, and &#x60;target&#x60; contains the commit ID.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryTagsRequest The postApiV4ProjectsIdRepositoryTagsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTag> postApiV4ProjectsIdRepositoryTagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryTagsRequest postApiV4ProjectsIdRepositoryTagsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRepositoryTagsRequestCreation(id, postApiV4ProjectsIdRepositoryTagsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a tag
     * Creates a tag in the repository that points to a specified reference. This endpoint is rate-limited for each project. The default is 100 requests every 30 minutes, configurable with the &#x60;tags_create_limit&#x60; application setting. Set the limit to &#x60;0&#x60; to disable it. The limit is shared between this endpoint, the GraphQL &#x60;tagCreate&#x60; mutation, tag creation in the UI, and the &#x60;/tag&#x60; quick action. The limit applies to the project, so you can receive &#x60;429 Too Many Requests&#x60; after other users of the project reach the limit. The type of tag created determines the contents of &#x60;created_at&#x60;, &#x60;target&#x60;, and &#x60;message&#x60;. For annotated tags, &#x60;created_at&#x60; contains the timestamp of tag creation, &#x60;message&#x60; contains the annotation, and &#x60;target&#x60; contains the ID of the tag object. For lightweight tags, &#x60;created_at&#x60; and &#x60;message&#x60; are null, and &#x60;target&#x60; contains the commit ID.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRepositoryTagsRequest The postApiV4ProjectsIdRepositoryTagsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRepositoryTagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRepositoryTagsRequest postApiV4ProjectsIdRepositoryTagsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRepositoryTagsRequestCreation(id, postApiV4ProjectsIdRepositoryTagsRequest);
    }
}
