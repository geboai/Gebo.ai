package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesPersonalSnippet;
import ai.gebo.gitlab.client.model.APIEntitiesProjectSnippet;
import ai.gebo.gitlab.client.model.APIEntitiesSnippet;
import ai.gebo.gitlab.client.model.APIEntitiesUserAgentDetail;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdSnippetsRequest;
import ai.gebo.gitlab.client.model.PostApiV4SnippetsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdSnippetsSnippetIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4SnippetsIdRequest;

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
public class SnippetsApi {
    private ApiClient apiClient;

    public SnippetsApi() {
        this(new ApiClient());
    }

    public SnippetsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a project snippet
     * Deletes a specified project snippet.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdSnippetsSnippetIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdSnippetsSnippetId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling deleteApiV4ProjectsIdSnippetsSnippetId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project snippet
     * Deletes a specified project snippet.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdSnippetsSnippetId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdSnippetsSnippetIdRequestCreation(id, snippetId).body(localVarReturnType);
    }

    /**
     * Delete a project snippet
     * Deletes a specified project snippet.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdSnippetsSnippetIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdSnippetsSnippetIdRequestCreation(id, snippetId).toEntity(localVarReturnType);
    }

    /**
     * Delete a project snippet
     * Deletes a specified project snippet.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdSnippetsSnippetIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdSnippetsSnippetIdRequestCreation(id, snippetId);
    }

    /**
     * Delete snippet
     * Deletes a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID of the snippet.
     * @return APIEntitiesPersonalSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4SnippetsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4SnippetsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete snippet
     * Deletes a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID of the snippet.
     * @return APIEntitiesPersonalSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalSnippet deleteApiV4SnippetsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4SnippetsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete snippet
     * Deletes a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID of the snippet.
     * @return ResponseEntity&lt;APIEntitiesPersonalSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalSnippet> deleteApiV4SnippetsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4SnippetsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete snippet
     * Deletes a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4SnippetsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4SnippetsIdRequestCreation(id);
    }

    /**
     * List all snippets for a project
     * Lists all snippets for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippets", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesProjectSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all snippets for a project
     * Lists all snippets for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProjectSnippet> getApiV4ProjectsIdSnippets(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all snippets for a project
     * Lists all snippets for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProjectSnippet&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProjectSnippet>> getApiV4ProjectsIdSnippetsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all snippets for a project
     * Lists all snippets for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a project snippet
     * Retrieves a specified project snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsSnippetId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling getApiV4ProjectsIdSnippetsSnippetId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a project snippet
     * Retrieves a specified project snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectSnippet getApiV4ProjectsIdSnippetsSnippetId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdRequestCreation(id, snippetId).body(localVarReturnType);
    }

    /**
     * Retrieve a project snippet
     * Retrieves a specified project snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseEntity&lt;APIEntitiesProjectSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectSnippet> getApiV4ProjectsIdSnippetsSnippetIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdRequestCreation(id, snippetId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a project snippet
     * Retrieves a specified project snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsSnippetIdRequestCreation(id, snippetId);
    }

    /**
     * Retrieve snippet repository file content
     * Retrieves the raw file content from a snippet repository as plain text.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param ref The name of branch, tag or commit
     * @param filePath The URL-encoded path to the file, like lib%2Fclass%2Erb
     * @param snippetId ID of the snippet.
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRawRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ref' is set
        if (ref == null) {
            throw new RestClientResponseException("Missing the required parameter 'ref' when calling getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("ref", ref);
        pathParams.put("file_path", filePath);
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

        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/files/{ref}/{file_path}/raw", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve snippet repository file content
     * Retrieves the raw file content from a snippet repository as plain text.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param ref The name of branch, tag or commit
     * @param filePath The URL-encoded path to the file, like lib%2Fclass%2Erb
     * @param snippetId ID of the snippet.
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectSnippet getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRaw(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRawRequestCreation(id, ref, filePath, snippetId).body(localVarReturnType);
    }

    /**
     * Retrieve snippet repository file content
     * Retrieves the raw file content from a snippet repository as plain text.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param ref The name of branch, tag or commit
     * @param filePath The URL-encoded path to the file, like lib%2Fclass%2Erb
     * @param snippetId ID of the snippet.
     * @return ResponseEntity&lt;APIEntitiesProjectSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectSnippet> getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRawWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRawRequestCreation(id, ref, filePath, snippetId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve snippet repository file content
     * Retrieves the raw file content from a snippet repository as plain text.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param ref The name of branch, tag or commit
     * @param filePath The URL-encoded path to the file, like lib%2Fclass%2Erb
     * @param snippetId ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRawWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsSnippetIdFilesRefFilePathRawRequestCreation(id, ref, filePath, snippetId);
    }

    /**
     * Retrieve a raw project snippet
     * Retrieves the raw contents of a specified project snippet as plain text
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdRawRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsSnippetIdRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling getApiV4ProjectsIdSnippetsSnippetIdRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/raw", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a raw project snippet
     * Retrieves the raw contents of a specified project snippet as plain text
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectSnippet getApiV4ProjectsIdSnippetsSnippetIdRaw(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdRawRequestCreation(id, snippetId).body(localVarReturnType);
    }

    /**
     * Retrieve a raw project snippet
     * Retrieves the raw contents of a specified project snippet as plain text
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseEntity&lt;APIEntitiesProjectSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectSnippet> getApiV4ProjectsIdSnippetsSnippetIdRawWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdRawRequestCreation(id, snippetId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a raw project snippet
     * Retrieves the raw contents of a specified project snippet as plain text
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdRawWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsSnippetIdRawRequestCreation(id, snippetId);
    }

    /**
     * Retrieve user agent details for a project snippet
     * Retrieves user agent details for a specified snippet. Available only for administrators.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return APIEntitiesUserAgentDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdUserAgentDetailRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdSnippetsSnippetIdUserAgentDetail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling getApiV4ProjectsIdSnippetsSnippetIdUserAgentDetail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserAgentDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}/user_agent_detail", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve user agent details for a project snippet
     * Retrieves user agent details for a specified snippet. Available only for administrators.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return APIEntitiesUserAgentDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserAgentDetail getApiV4ProjectsIdSnippetsSnippetIdUserAgentDetail(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserAgentDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdUserAgentDetailRequestCreation(id, snippetId).body(localVarReturnType);
    }

    /**
     * Retrieve user agent details for a project snippet
     * Retrieves user agent details for a specified snippet. Available only for administrators.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseEntity&lt;APIEntitiesUserAgentDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserAgentDetail> getApiV4ProjectsIdSnippetsSnippetIdUserAgentDetailWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserAgentDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdSnippetsSnippetIdUserAgentDetailRequestCreation(id, snippetId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve user agent details for a project snippet
     * Retrieves user agent details for a specified snippet. Available only for administrators.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdSnippetsSnippetIdUserAgentDetailWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId) throws RestClientResponseException {
        return getApiV4ProjectsIdSnippetsSnippetIdUserAgentDetailRequestCreation(id, snippetId);
    }

    /**
     * List all snippets for current user
     * Lists all snippets for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetsRequestCreation(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all snippets for current user
     * Lists all snippets for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSnippet> getApiV4Snippets(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsRequestCreation(createdAfter, createdBefore, page, perPage).body(localVarReturnType);
    }

    /**
     * List all snippets for current user
     * Lists all snippets for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSnippet&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSnippet>> getApiV4SnippetsWithHttpInfo(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsRequestCreation(createdAfter, createdBefore, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all snippets for current user
     * Lists all snippets for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetsWithResponseSpec(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4SnippetsRequestCreation(createdAfter, createdBefore, page, perPage);
    }

    /**
     * List all snippets
     * Lists all snippets available to the currently authenticated user. Users with Administrator or Auditor access levels can see all snippets (both personal and project). This feature was introduced in GitLab 16.3.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param repositoryStorage Filter by repository storage used by the snippet. Administrators only.
     * @return List&lt;APIEntitiesSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetsAllRequestCreation(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String repositoryStorage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "repository_storage", repositoryStorage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets/all", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all snippets
     * Lists all snippets available to the currently authenticated user. Users with Administrator or Auditor access levels can see all snippets (both personal and project). This feature was introduced in GitLab 16.3.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param repositoryStorage Filter by repository storage used by the snippet. Administrators only.
     * @return List&lt;APIEntitiesSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSnippet> getApiV4SnippetsAll(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String repositoryStorage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsAllRequestCreation(createdAfter, createdBefore, page, perPage, repositoryStorage).body(localVarReturnType);
    }

    /**
     * List all snippets
     * Lists all snippets available to the currently authenticated user. Users with Administrator or Auditor access levels can see all snippets (both personal and project). This feature was introduced in GitLab 16.3.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param repositoryStorage Filter by repository storage used by the snippet. Administrators only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSnippet&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSnippet>> getApiV4SnippetsAllWithHttpInfo(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String repositoryStorage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsAllRequestCreation(createdAfter, createdBefore, page, perPage, repositoryStorage).toEntity(localVarReturnType);
    }

    /**
     * List all snippets
     * Lists all snippets available to the currently authenticated user. Users with Administrator or Auditor access levels can see all snippets (both personal and project). This feature was introduced in GitLab 16.3.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param repositoryStorage Filter by repository storage used by the snippet. Administrators only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetsAllWithResponseSpec(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String repositoryStorage) throws RestClientResponseException {
        return getApiV4SnippetsAllRequestCreation(createdAfter, createdBefore, page, perPage, repositoryStorage);
    }

    /**
     * Retrieve a snippet
     * Retrieves a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @return APIEntitiesPersonalSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SnippetsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a snippet
     * Retrieves a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @return APIEntitiesPersonalSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalSnippet getApiV4SnippetsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a snippet
     * Retrieves a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @return ResponseEntity&lt;APIEntitiesPersonalSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalSnippet> getApiV4SnippetsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a snippet
     * Retrieves a specified snippet.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4SnippetsIdRequestCreation(id);
    }

    /**
     * Retrieve snippet file content
     * Retrieves the raw file content from a snippet as plain text.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param ref The name of branch, tag or commit
     * @param filePath The URL-encoded path to the file, like lib%2Fclass%2Erb
     * @param id ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetsIdFilesRefFilePathRawRequestCreation(@jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'ref' is set
        if (ref == null) {
            throw new RestClientResponseException("Missing the required parameter 'ref' when calling getApiV4SnippetsIdFilesRefFilePathRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling getApiV4SnippetsIdFilesRefFilePathRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SnippetsIdFilesRefFilePathRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ref", ref);
        pathParams.put("file_path", filePath);
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
        return apiClient.invokeAPI("/api/v4/snippets/{id}/files/{ref}/{file_path}/raw", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve snippet file content
     * Retrieves the raw file content from a snippet as plain text.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param ref The name of branch, tag or commit
     * @param filePath The URL-encoded path to the file, like lib%2Fclass%2Erb
     * @param id ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4SnippetsIdFilesRefFilePathRaw(@jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4SnippetsIdFilesRefFilePathRawRequestCreation(ref, filePath, id).body(localVarReturnType);
    }

    /**
     * Retrieve snippet file content
     * Retrieves the raw file content from a snippet as plain text.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param ref The name of branch, tag or commit
     * @param filePath The URL-encoded path to the file, like lib%2Fclass%2Erb
     * @param id ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4SnippetsIdFilesRefFilePathRawWithHttpInfo(@jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdFilesRefFilePathRawRequestCreation(ref, filePath, id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve snippet file content
     * Retrieves the raw file content from a snippet as plain text.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param ref The name of branch, tag or commit
     * @param filePath The URL-encoded path to the file, like lib%2Fclass%2Erb
     * @param id ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetsIdFilesRefFilePathRawWithResponseSpec(@jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull String filePath, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4SnippetsIdFilesRefFilePathRawRequestCreation(ref, filePath, id);
    }

    /**
     * Retrieve a raw snippet
     * Retrieves the raw contents of a specified snippet as plain text
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetsIdRawRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SnippetsIdRaw", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/snippets/{id}/raw", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a raw snippet
     * Retrieves the raw contents of a specified snippet as plain text
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4SnippetsIdRaw(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4SnippetsIdRawRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a raw snippet
     * Retrieves the raw contents of a specified snippet as plain text
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4SnippetsIdRawWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdRawRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a raw snippet
     * Retrieves the raw contents of a specified snippet as plain text
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetsIdRawWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4SnippetsIdRawRequestCreation(id);
    }

    /**
     * Retrieve user agent details for a snippet
     * Retrieves user agent details for a specified snippet. Available only for administrators.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @return APIEntitiesUserAgentDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetsIdUserAgentDetailRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SnippetsIdUserAgentDetail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesUserAgentDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets/{id}/user_agent_detail", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve user agent details for a snippet
     * Retrieves user agent details for a specified snippet. Available only for administrators.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @return APIEntitiesUserAgentDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserAgentDetail getApiV4SnippetsIdUserAgentDetail(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserAgentDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdUserAgentDetailRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve user agent details for a snippet
     * Retrieves user agent details for a specified snippet. Available only for administrators.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @return ResponseEntity&lt;APIEntitiesUserAgentDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserAgentDetail> getApiV4SnippetsIdUserAgentDetailWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserAgentDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsIdUserAgentDetailRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve user agent details for a snippet
     * Retrieves user agent details for a specified snippet. Available only for administrators.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the snippet.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetsIdUserAgentDetailWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4SnippetsIdUserAgentDetailRequestCreation(id);
    }

    /**
     * List all public snippets
     * Lists all public snippets accessible to the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPersonalSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SnippetsPublicRequestCreation(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPersonalSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets/public", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all public snippets
     * Lists all public snippets accessible to the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPersonalSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPersonalSnippet> getApiV4SnippetsPublic(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsPublicRequestCreation(createdAfter, createdBefore, page, perPage).body(localVarReturnType);
    }

    /**
     * List all public snippets
     * Lists all public snippets accessible to the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPersonalSnippet&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPersonalSnippet>> getApiV4SnippetsPublicWithHttpInfo(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPersonalSnippet>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SnippetsPublicRequestCreation(createdAfter, createdBefore, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all public snippets
     * Lists all public snippets accessible to the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param createdAfter Return snippets created on or after the specified time.
     * @param createdBefore Return snippets created on or before the specified time.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SnippetsPublicWithResponseSpec(@jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4SnippetsPublicRequestCreation(createdAfter, createdBefore, page, perPage);
    }

    /**
     * Create a project snippet
     * Creates a project snippet. The user must have permission to create snippets. Creating snippets has a rate limit of 300 requests every hour for each authenticated user. The limit is shared between this endpoint, the endpoint that creates a personal snippet, and the &#x60;createSnippet&#x60; GraphQL mutation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdSnippetsRequest The postApiV4ProjectsIdSnippetsRequest parameter
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdSnippetsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsRequest postApiV4ProjectsIdSnippetsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdSnippets", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdSnippetsRequest' is set
        if (postApiV4ProjectsIdSnippetsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdSnippetsRequest' when calling postApiV4ProjectsIdSnippets", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdSnippetsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a project snippet
     * Creates a project snippet. The user must have permission to create snippets. Creating snippets has a rate limit of 300 requests every hour for each authenticated user. The limit is shared between this endpoint, the endpoint that creates a personal snippet, and the &#x60;createSnippet&#x60; GraphQL mutation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdSnippetsRequest The postApiV4ProjectsIdSnippetsRequest parameter
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectSnippet postApiV4ProjectsIdSnippets(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsRequest postApiV4ProjectsIdSnippetsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsRequestCreation(id, postApiV4ProjectsIdSnippetsRequest).body(localVarReturnType);
    }

    /**
     * Create a project snippet
     * Creates a project snippet. The user must have permission to create snippets. Creating snippets has a rate limit of 300 requests every hour for each authenticated user. The limit is shared between this endpoint, the endpoint that creates a personal snippet, and the &#x60;createSnippet&#x60; GraphQL mutation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdSnippetsRequest The postApiV4ProjectsIdSnippetsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectSnippet> postApiV4ProjectsIdSnippetsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsRequest postApiV4ProjectsIdSnippetsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSnippetsRequestCreation(id, postApiV4ProjectsIdSnippetsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a project snippet
     * Creates a project snippet. The user must have permission to create snippets. Creating snippets has a rate limit of 300 requests every hour for each authenticated user. The limit is shared between this endpoint, the endpoint that creates a personal snippet, and the &#x60;createSnippet&#x60; GraphQL mutation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdSnippetsRequest The postApiV4ProjectsIdSnippetsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdSnippetsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSnippetsRequest postApiV4ProjectsIdSnippetsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdSnippetsRequestCreation(id, postApiV4ProjectsIdSnippetsRequest);
    }

    /**
     * Create a snippet
     * Creates a snippet. The user must have permission to create snippets. Creating snippets is rate limited to 300 requests every hour for each authenticated user. The limit is shared between this endpoint, creating a project snippet, and the &#x60;createSnippet&#x60; GraphQL mutation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param postApiV4SnippetsRequest The postApiV4SnippetsRequest parameter
     * @return APIEntitiesPersonalSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4SnippetsRequestCreation(@jakarta.annotation.Nonnull PostApiV4SnippetsRequest postApiV4SnippetsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4SnippetsRequest' is set
        if (postApiV4SnippetsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4SnippetsRequest' when calling postApiV4Snippets", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4SnippetsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a snippet
     * Creates a snippet. The user must have permission to create snippets. Creating snippets is rate limited to 300 requests every hour for each authenticated user. The limit is shared between this endpoint, creating a project snippet, and the &#x60;createSnippet&#x60; GraphQL mutation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param postApiV4SnippetsRequest The postApiV4SnippetsRequest parameter
     * @return APIEntitiesPersonalSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalSnippet postApiV4Snippets(@jakarta.annotation.Nonnull PostApiV4SnippetsRequest postApiV4SnippetsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SnippetsRequestCreation(postApiV4SnippetsRequest).body(localVarReturnType);
    }

    /**
     * Create a snippet
     * Creates a snippet. The user must have permission to create snippets. Creating snippets is rate limited to 300 requests every hour for each authenticated user. The limit is shared between this endpoint, creating a project snippet, and the &#x60;createSnippet&#x60; GraphQL mutation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param postApiV4SnippetsRequest The postApiV4SnippetsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalSnippet> postApiV4SnippetsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4SnippetsRequest postApiV4SnippetsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SnippetsRequestCreation(postApiV4SnippetsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a snippet
     * Creates a snippet. The user must have permission to create snippets. Creating snippets is rate limited to 300 requests every hour for each authenticated user. The limit is shared between this endpoint, creating a project snippet, and the &#x60;createSnippet&#x60; GraphQL mutation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param postApiV4SnippetsRequest The postApiV4SnippetsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4SnippetsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4SnippetsRequest postApiV4SnippetsRequest) throws RestClientResponseException {
        return postApiV4SnippetsRequestCreation(postApiV4SnippetsRequest);
    }

    /**
     * Update a project snippet
     * Updates a specified project snippet. The user must have permission to modify snippets. Updates to snippets with multiple files must use the &#x60;files&#x60; attribute.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param putApiV4ProjectsIdSnippetsSnippetIdRequest The putApiV4ProjectsIdSnippetsSnippetIdRequest parameter
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdSnippetsSnippetIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsSnippetIdRequest putApiV4ProjectsIdSnippetsSnippetIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdSnippetsSnippetId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'snippetId' is set
        if (snippetId == null) {
            throw new RestClientResponseException("Missing the required parameter 'snippetId' when calling putApiV4ProjectsIdSnippetsSnippetId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/snippets/{snippet_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdSnippetsSnippetIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a project snippet
     * Updates a specified project snippet. The user must have permission to modify snippets. Updates to snippets with multiple files must use the &#x60;files&#x60; attribute.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param putApiV4ProjectsIdSnippetsSnippetIdRequest The putApiV4ProjectsIdSnippetsSnippetIdRequest parameter
     * @return APIEntitiesProjectSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectSnippet putApiV4ProjectsIdSnippetsSnippetId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsSnippetIdRequest putApiV4ProjectsIdSnippetsSnippetIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdSnippetsSnippetIdRequestCreation(id, snippetId, putApiV4ProjectsIdSnippetsSnippetIdRequest).body(localVarReturnType);
    }

    /**
     * Update a project snippet
     * Updates a specified project snippet. The user must have permission to modify snippets. Updates to snippets with multiple files must use the &#x60;files&#x60; attribute.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param putApiV4ProjectsIdSnippetsSnippetIdRequest The putApiV4ProjectsIdSnippetsSnippetIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectSnippet> putApiV4ProjectsIdSnippetsSnippetIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsSnippetIdRequest putApiV4ProjectsIdSnippetsSnippetIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdSnippetsSnippetIdRequestCreation(id, snippetId, putApiV4ProjectsIdSnippetsSnippetIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a project snippet
     * Updates a specified project snippet. The user must have permission to modify snippets. Updates to snippets with multiple files must use the &#x60;files&#x60; attribute.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param snippetId ID of the snippet.
     * @param putApiV4ProjectsIdSnippetsSnippetIdRequest The putApiV4ProjectsIdSnippetsSnippetIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdSnippetsSnippetIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer snippetId, @jakarta.annotation.Nullable PutApiV4ProjectsIdSnippetsSnippetIdRequest putApiV4ProjectsIdSnippetsSnippetIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdSnippetsSnippetIdRequestCreation(id, snippetId, putApiV4ProjectsIdSnippetsSnippetIdRequest);
    }

    /**
     * Update snippet
     * Updates a specified snippet. The user must have permission to change the snippet.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the snippet.
     * @param putApiV4SnippetsIdRequest The putApiV4SnippetsIdRequest parameter
     * @return APIEntitiesPersonalSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4SnippetsIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4SnippetsIdRequest putApiV4SnippetsIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4SnippetsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/snippets/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4SnippetsIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update snippet
     * Updates a specified snippet. The user must have permission to change the snippet.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the snippet.
     * @param putApiV4SnippetsIdRequest The putApiV4SnippetsIdRequest parameter
     * @return APIEntitiesPersonalSnippet
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPersonalSnippet putApiV4SnippetsId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4SnippetsIdRequest putApiV4SnippetsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4SnippetsIdRequestCreation(id, putApiV4SnippetsIdRequest).body(localVarReturnType);
    }

    /**
     * Update snippet
     * Updates a specified snippet. The user must have permission to change the snippet.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the snippet.
     * @param putApiV4SnippetsIdRequest The putApiV4SnippetsIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPersonalSnippet&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPersonalSnippet> putApiV4SnippetsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4SnippetsIdRequest putApiV4SnippetsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPersonalSnippet> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4SnippetsIdRequestCreation(id, putApiV4SnippetsIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update snippet
     * Updates a specified snippet. The user must have permission to change the snippet.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID of the snippet.
     * @param putApiV4SnippetsIdRequest The putApiV4SnippetsIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4SnippetsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4SnippetsIdRequest putApiV4SnippetsIdRequest) throws RestClientResponseException {
        return putApiV4SnippetsIdRequestCreation(id, putApiV4SnippetsIdRequest);
    }
}
