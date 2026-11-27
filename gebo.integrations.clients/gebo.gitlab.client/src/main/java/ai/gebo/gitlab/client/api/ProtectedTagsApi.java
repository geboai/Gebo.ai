package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesProtectedTag;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdProtectedTagsRequest;

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
public class ProtectedTagsApi {
    private ApiClient apiClient;

    public ProtectedTagsApi() {
        this(new ApiClient());
    }

    public ProtectedTagsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Unprotect repository tags
     * Unprotects a specified protected tag or wildcard protected tag.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the tag or wildcard.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdProtectedTagsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdProtectedTagsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4ProjectsIdProtectedTagsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_tags/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unprotect repository tags
     * Unprotects a specified protected tag or wildcard protected tag.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the tag or wildcard.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdProtectedTagsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdProtectedTagsNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Unprotect repository tags
     * Unprotects a specified protected tag or wildcard protected tag.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the tag or wildcard.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdProtectedTagsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdProtectedTagsNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Unprotect repository tags
     * Unprotects a specified protected tag or wildcard protected tag.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the tag or wildcard.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdProtectedTagsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4ProjectsIdProtectedTagsNameRequestCreation(id, name);
    }

    /**
     * List all protected tags
     * Lists all protected tags for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProtectedTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdProtectedTagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdProtectedTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesProtectedTag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_tags", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all protected tags
     * Lists all protected tags for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProtectedTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProtectedTag> getApiV4ProjectsIdProtectedTags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProtectedTag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedTagsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all protected tags
     * Lists all protected tags for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProtectedTag&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProtectedTag>> getApiV4ProjectsIdProtectedTagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProtectedTag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedTagsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all protected tags
     * Lists all protected tags for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdProtectedTagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdProtectedTagsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a protected tag or wildcard protected tag
     * Retrieves a specified protected tag or wildcard protected tag.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the tag or wildcard.
     * @return APIEntitiesProtectedTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdProtectedTagsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdProtectedTagsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectsIdProtectedTagsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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

        ParameterizedTypeReference<APIEntitiesProtectedTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_tags/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a protected tag or wildcard protected tag
     * Retrieves a specified protected tag or wildcard protected tag.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the tag or wildcard.
     * @return APIEntitiesProtectedTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedTag getApiV4ProjectsIdProtectedTagsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedTagsNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Retrieve a protected tag or wildcard protected tag
     * Retrieves a specified protected tag or wildcard protected tag.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the tag or wildcard.
     * @return ResponseEntity&lt;APIEntitiesProtectedTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedTag> getApiV4ProjectsIdProtectedTagsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedTagsNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a protected tag or wildcard protected tag
     * Retrieves a specified protected tag or wildcard protected tag.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the tag or wildcard.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdProtectedTagsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4ProjectsIdProtectedTagsNameRequestCreation(id, name);
    }

    /**
     * Protect a repository tag
     * Protects a single repository tag, or several project repository tags by using a wildcard protected tag.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedTagsRequest The postApiV4ProjectsIdProtectedTagsRequest parameter
     * @return APIEntitiesProtectedTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdProtectedTagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedTagsRequest postApiV4ProjectsIdProtectedTagsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdProtectedTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdProtectedTagsRequest' is set
        if (postApiV4ProjectsIdProtectedTagsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdProtectedTagsRequest' when calling postApiV4ProjectsIdProtectedTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProtectedTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_tags", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdProtectedTagsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Protect a repository tag
     * Protects a single repository tag, or several project repository tags by using a wildcard protected tag.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedTagsRequest The postApiV4ProjectsIdProtectedTagsRequest parameter
     * @return APIEntitiesProtectedTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedTag postApiV4ProjectsIdProtectedTags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedTagsRequest postApiV4ProjectsIdProtectedTagsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdProtectedTagsRequestCreation(id, postApiV4ProjectsIdProtectedTagsRequest).body(localVarReturnType);
    }

    /**
     * Protect a repository tag
     * Protects a single repository tag, or several project repository tags by using a wildcard protected tag.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedTagsRequest The postApiV4ProjectsIdProtectedTagsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProtectedTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedTag> postApiV4ProjectsIdProtectedTagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedTagsRequest postApiV4ProjectsIdProtectedTagsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdProtectedTagsRequestCreation(id, postApiV4ProjectsIdProtectedTagsRequest).toEntity(localVarReturnType);
    }

    /**
     * Protect a repository tag
     * Protects a single repository tag, or several project repository tags by using a wildcard protected tag.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Unauthenticated
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedTagsRequest The postApiV4ProjectsIdProtectedTagsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdProtectedTagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedTagsRequest postApiV4ProjectsIdProtectedTagsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdProtectedTagsRequestCreation(id, postApiV4ProjectsIdProtectedTagsRequest);
    }
}
