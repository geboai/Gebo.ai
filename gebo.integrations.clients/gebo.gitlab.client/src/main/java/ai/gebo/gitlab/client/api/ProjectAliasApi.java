package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesProjectAlias;
import ai.gebo.gitlab.client.model.PostApiV4ProjectAliasesRequest;

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
public class ProjectAliasApi {
    private ApiClient apiClient;

    public ProjectAliasApi() {
        this(new ApiClient());
    }

    public ProjectAliasApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a project alias
     * Deletes a specified project alias. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param name Name of the alias. Must be unique.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectAliasesNameRequestCreation(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4ProjectAliasesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/project_aliases/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project alias
     * Deletes a specified project alias. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param name Name of the alias. Must be unique.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectAliasesName(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectAliasesNameRequestCreation(name).body(localVarReturnType);
    }

    /**
     * Delete a project alias
     * Deletes a specified project alias. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param name Name of the alias. Must be unique.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectAliasesNameWithHttpInfo(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectAliasesNameRequestCreation(name).toEntity(localVarReturnType);
    }

    /**
     * Delete a project alias
     * Deletes a specified project alias. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param name Name of the alias. Must be unique.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectAliasesNameWithResponseSpec(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4ProjectAliasesNameRequestCreation(name);
    }

    /**
     * List all project aliases
     * Lists all project aliases for the instance.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectAlias&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectAliasesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesProjectAlias>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/project_aliases", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project aliases
     * Lists all project aliases for the instance.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectAlias&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProjectAlias> getApiV4ProjectAliases(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectAlias>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectAliasesRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all project aliases
     * Lists all project aliases for the instance.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProjectAlias&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProjectAlias>> getApiV4ProjectAliasesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectAlias>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectAliasesRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project aliases
     * Lists all project aliases for the instance.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectAliasesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectAliasesRequestCreation(page, perPage);
    }

    /**
     * Retrieve a project alias
     * Retrieves details of a specified project alias.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param name Name of the alias. Must be unique.
     * @return APIEntitiesProjectAlias
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectAliasesNameRequestCreation(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectAliasesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesProjectAlias> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/project_aliases/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a project alias
     * Retrieves details of a specified project alias.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param name Name of the alias. Must be unique.
     * @return APIEntitiesProjectAlias
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectAlias getApiV4ProjectAliasesName(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectAlias> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectAliasesNameRequestCreation(name).body(localVarReturnType);
    }

    /**
     * Retrieve a project alias
     * Retrieves details of a specified project alias.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param name Name of the alias. Must be unique.
     * @return ResponseEntity&lt;APIEntitiesProjectAlias&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectAlias> getApiV4ProjectAliasesNameWithHttpInfo(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectAlias> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectAliasesNameRequestCreation(name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a project alias
     * Retrieves details of a specified project alias.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param name Name of the alias. Must be unique.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectAliasesNameWithResponseSpec(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4ProjectAliasesNameRequestCreation(name);
    }

    /**
     * Create a project alias
     * Creates a project alias.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4ProjectAliasesRequest The postApiV4ProjectAliasesRequest parameter
     * @return APIEntitiesProjectAlias
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectAliasesRequestCreation(@jakarta.annotation.Nonnull PostApiV4ProjectAliasesRequest postApiV4ProjectAliasesRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4ProjectAliasesRequest' is set
        if (postApiV4ProjectAliasesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectAliasesRequest' when calling postApiV4ProjectAliases", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectAlias> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/project_aliases", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectAliasesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a project alias
     * Creates a project alias.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4ProjectAliasesRequest The postApiV4ProjectAliasesRequest parameter
     * @return APIEntitiesProjectAlias
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectAlias postApiV4ProjectAliases(@jakarta.annotation.Nonnull PostApiV4ProjectAliasesRequest postApiV4ProjectAliasesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectAlias> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectAliasesRequestCreation(postApiV4ProjectAliasesRequest).body(localVarReturnType);
    }

    /**
     * Create a project alias
     * Creates a project alias.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4ProjectAliasesRequest The postApiV4ProjectAliasesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectAlias&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectAlias> postApiV4ProjectAliasesWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4ProjectAliasesRequest postApiV4ProjectAliasesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectAlias> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectAliasesRequestCreation(postApiV4ProjectAliasesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a project alias
     * Creates a project alias.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4ProjectAliasesRequest The postApiV4ProjectAliasesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectAliasesWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4ProjectAliasesRequest postApiV4ProjectAliasesRequest) throws RestClientResponseException {
        return postApiV4ProjectAliasesRequestCreation(postApiV4ProjectAliasesRequest);
    }
}
