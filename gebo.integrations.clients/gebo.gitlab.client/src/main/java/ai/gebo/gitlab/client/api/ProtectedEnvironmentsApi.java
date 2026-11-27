package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesProtectedEnvironment;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdProtectedEnvironmentsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdProtectedEnvironmentsRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdProtectedEnvironmentsNameRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdProtectedEnvironmentsNameRequest;

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
public class ProtectedEnvironmentsApi {
    private ApiClient apiClient;

    public ProtectedEnvironmentsApi() {
        this(new ApiClient());
    }

    public ProtectedEnvironmentsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Unprotect a group environment
     * Unprotects a specified protected environment for a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name [Deployment tier](https://docs.gitlab.com/ci/environments/#deployment-tier-of-environments) of the protected environment. Possible values are &#x60;production&#x60;, &#x60;staging&#x60;, &#x60;testing&#x60;, &#x60;development&#x60;, and &#x60;other&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4GroupsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_environments/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unprotect a group environment
     * Unprotects a specified protected environment for a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name [Deployment tier](https://docs.gitlab.com/ci/environments/#deployment-tier-of-environments) of the protected environment. Possible values are &#x60;production&#x60;, &#x60;staging&#x60;, &#x60;testing&#x60;, &#x60;development&#x60;, and &#x60;other&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdProtectedEnvironmentsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Unprotect a group environment
     * Unprotects a specified protected environment for a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name [Deployment tier](https://docs.gitlab.com/ci/environments/#deployment-tier-of-environments) of the protected environment. Possible values are &#x60;production&#x60;, &#x60;staging&#x60;, &#x60;testing&#x60;, &#x60;development&#x60;, and &#x60;other&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdProtectedEnvironmentsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Unprotect a group environment
     * Unprotects a specified protected environment for a group.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name [Deployment tier](https://docs.gitlab.com/ci/environments/#deployment-tier-of-environments) of the protected environment. Possible values are &#x60;production&#x60;, &#x60;staging&#x60;, &#x60;testing&#x60;, &#x60;development&#x60;, and &#x60;other&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdProtectedEnvironmentsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(id, name);
    }

    /**
     * Unprotect a protected environment for a project
     * Unprotects a specified protected environment for a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the protected environment.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4ProjectsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_environments/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unprotect a protected environment for a project
     * Unprotects a specified protected environment for a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the protected environment.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdProtectedEnvironmentsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Unprotect a protected environment for a project
     * Unprotects a specified protected environment for a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the protected environment.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdProtectedEnvironmentsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Unprotect a protected environment for a project
     * Unprotects a specified protected environment for a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the protected environment.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdProtectedEnvironmentsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(id, name);
    }

    /**
     * List all protected environments for a group
     * Lists all protected environments for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdProtectedEnvironmentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdProtectedEnvironments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesProtectedEnvironment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_environments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all protected environments for a group
     * Lists all protected environments for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProtectedEnvironment> getApiV4GroupsIdProtectedEnvironments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProtectedEnvironment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProtectedEnvironmentsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all protected environments for a group
     * Lists all protected environments for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProtectedEnvironment&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProtectedEnvironment>> getApiV4GroupsIdProtectedEnvironmentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProtectedEnvironment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProtectedEnvironmentsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all protected environments for a group
     * Lists all protected environments for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdProtectedEnvironmentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdProtectedEnvironmentsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a protected environment for a group
     * Retrieves a specified protected environment for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name The deployment tier of the protected environment.
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4GroupsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_environments/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a protected environment for a group
     * Retrieves a specified protected environment for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name The deployment tier of the protected environment.
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedEnvironment getApiV4GroupsIdProtectedEnvironmentsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Retrieve a protected environment for a group
     * Retrieves a specified protected environment for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name The deployment tier of the protected environment.
     * @return ResponseEntity&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedEnvironment> getApiV4GroupsIdProtectedEnvironmentsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a protected environment for a group
     * Retrieves a specified protected environment for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name The deployment tier of the protected environment.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdProtectedEnvironmentsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(id, name);
    }

    /**
     * List all protected environments for a project
     * Lists all protected environments for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdProtectedEnvironmentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdProtectedEnvironments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesProtectedEnvironment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_environments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all protected environments for a project
     * Lists all protected environments for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProtectedEnvironment> getApiV4ProjectsIdProtectedEnvironments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProtectedEnvironment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedEnvironmentsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all protected environments for a project
     * Lists all protected environments for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProtectedEnvironment&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProtectedEnvironment>> getApiV4ProjectsIdProtectedEnvironmentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProtectedEnvironment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedEnvironmentsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all protected environments for a project
     * Lists all protected environments for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdProtectedEnvironmentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdProtectedEnvironmentsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a protected environment for a project
     * Retrieves a specified protected environment for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the protected environment.
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_environments/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a protected environment for a project
     * Retrieves a specified protected environment for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the protected environment.
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedEnvironment getApiV4ProjectsIdProtectedEnvironmentsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Retrieve a protected environment for a project
     * Retrieves a specified protected environment for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the protected environment.
     * @return ResponseEntity&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedEnvironment> getApiV4ProjectsIdProtectedEnvironmentsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a protected environment for a project
     * Retrieves a specified protected environment for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the protected environment.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdProtectedEnvironmentsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(id, name);
    }

    /**
     * Protect an environment for a group
     * Protects a specified environment for a group. Each &#x60;user_id&#x60; must belong to a user with at least the Maintainer role for the group. Each &#x60;group_id&#x60; must belong to the group, one of its subgroups, or a group it is shared with.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param postApiV4GroupsIdProtectedEnvironmentsRequest The postApiV4GroupsIdProtectedEnvironmentsRequest parameter
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdProtectedEnvironmentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdProtectedEnvironmentsRequest postApiV4GroupsIdProtectedEnvironmentsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdProtectedEnvironments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdProtectedEnvironmentsRequest' is set
        if (postApiV4GroupsIdProtectedEnvironmentsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdProtectedEnvironmentsRequest' when calling postApiV4GroupsIdProtectedEnvironments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_environments", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdProtectedEnvironmentsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Protect an environment for a group
     * Protects a specified environment for a group. Each &#x60;user_id&#x60; must belong to a user with at least the Maintainer role for the group. Each &#x60;group_id&#x60; must belong to the group, one of its subgroups, or a group it is shared with.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param postApiV4GroupsIdProtectedEnvironmentsRequest The postApiV4GroupsIdProtectedEnvironmentsRequest parameter
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedEnvironment postApiV4GroupsIdProtectedEnvironments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdProtectedEnvironmentsRequest postApiV4GroupsIdProtectedEnvironmentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdProtectedEnvironmentsRequestCreation(id, postApiV4GroupsIdProtectedEnvironmentsRequest).body(localVarReturnType);
    }

    /**
     * Protect an environment for a group
     * Protects a specified environment for a group. Each &#x60;user_id&#x60; must belong to a user with at least the Maintainer role for the group. Each &#x60;group_id&#x60; must belong to the group, one of its subgroups, or a group it is shared with.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param postApiV4GroupsIdProtectedEnvironmentsRequest The postApiV4GroupsIdProtectedEnvironmentsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedEnvironment> postApiV4GroupsIdProtectedEnvironmentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdProtectedEnvironmentsRequest postApiV4GroupsIdProtectedEnvironmentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdProtectedEnvironmentsRequestCreation(id, postApiV4GroupsIdProtectedEnvironmentsRequest).toEntity(localVarReturnType);
    }

    /**
     * Protect an environment for a group
     * Protects a specified environment for a group. Each &#x60;user_id&#x60; must belong to a user with at least the Maintainer role for the group. Each &#x60;group_id&#x60; must belong to the group, one of its subgroups, or a group it is shared with.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param postApiV4GroupsIdProtectedEnvironmentsRequest The postApiV4GroupsIdProtectedEnvironmentsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdProtectedEnvironmentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdProtectedEnvironmentsRequest postApiV4GroupsIdProtectedEnvironmentsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdProtectedEnvironmentsRequestCreation(id, postApiV4GroupsIdProtectedEnvironmentsRequest);
    }

    /**
     * Protect an environment for a project
     * Protects a specified environment for a project. Each &#x60;user_id&#x60; must belong to a user with at least the Developer role for the project. Each &#x60;group_id&#x60; must belong to a group the project is shared with.
     * <p><b>201</b> - Created
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedEnvironmentsRequest The postApiV4ProjectsIdProtectedEnvironmentsRequest parameter
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdProtectedEnvironmentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedEnvironmentsRequest postApiV4ProjectsIdProtectedEnvironmentsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdProtectedEnvironments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdProtectedEnvironmentsRequest' is set
        if (postApiV4ProjectsIdProtectedEnvironmentsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdProtectedEnvironmentsRequest' when calling postApiV4ProjectsIdProtectedEnvironments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_environments", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdProtectedEnvironmentsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Protect an environment for a project
     * Protects a specified environment for a project. Each &#x60;user_id&#x60; must belong to a user with at least the Developer role for the project. Each &#x60;group_id&#x60; must belong to a group the project is shared with.
     * <p><b>201</b> - Created
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedEnvironmentsRequest The postApiV4ProjectsIdProtectedEnvironmentsRequest parameter
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedEnvironment postApiV4ProjectsIdProtectedEnvironments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedEnvironmentsRequest postApiV4ProjectsIdProtectedEnvironmentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdProtectedEnvironmentsRequestCreation(id, postApiV4ProjectsIdProtectedEnvironmentsRequest).body(localVarReturnType);
    }

    /**
     * Protect an environment for a project
     * Protects a specified environment for a project. Each &#x60;user_id&#x60; must belong to a user with at least the Developer role for the project. Each &#x60;group_id&#x60; must belong to a group the project is shared with.
     * <p><b>201</b> - Created
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedEnvironmentsRequest The postApiV4ProjectsIdProtectedEnvironmentsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedEnvironment> postApiV4ProjectsIdProtectedEnvironmentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedEnvironmentsRequest postApiV4ProjectsIdProtectedEnvironmentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdProtectedEnvironmentsRequestCreation(id, postApiV4ProjectsIdProtectedEnvironmentsRequest).toEntity(localVarReturnType);
    }

    /**
     * Protect an environment for a project
     * Protects a specified environment for a project. Each &#x60;user_id&#x60; must belong to a user with at least the Developer role for the project. Each &#x60;group_id&#x60; must belong to a group the project is shared with.
     * <p><b>201</b> - Created
     * <p><b>409</b> - Conflict
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedEnvironmentsRequest The postApiV4ProjectsIdProtectedEnvironmentsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdProtectedEnvironmentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedEnvironmentsRequest postApiV4ProjectsIdProtectedEnvironmentsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdProtectedEnvironmentsRequestCreation(id, postApiV4ProjectsIdProtectedEnvironmentsRequest);
    }

    /**
     * Update a protected environment for a group
     * Updates a specified protected environment for a group. Each &#x60;user_id&#x60; must belong to a user with at least the Maintainer role for the group. Each &#x60;group_id&#x60; must belong to the group, one of its subgroups, or a group it is shared with. To update an existing entry, pass its &#x60;id&#x60; in the &#x60;deploy_access_levels&#x60; or &#x60;approval_rules&#x60; hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name The deployment tier of the protected environment.
     * @param putApiV4GroupsIdProtectedEnvironmentsNameRequest The putApiV4GroupsIdProtectedEnvironmentsNameRequest parameter
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4GroupsIdProtectedEnvironmentsNameRequest putApiV4GroupsIdProtectedEnvironmentsNameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling putApiV4GroupsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_environments/{name}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdProtectedEnvironmentsNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a protected environment for a group
     * Updates a specified protected environment for a group. Each &#x60;user_id&#x60; must belong to a user with at least the Maintainer role for the group. Each &#x60;group_id&#x60; must belong to the group, one of its subgroups, or a group it is shared with. To update an existing entry, pass its &#x60;id&#x60; in the &#x60;deploy_access_levels&#x60; or &#x60;approval_rules&#x60; hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name The deployment tier of the protected environment.
     * @param putApiV4GroupsIdProtectedEnvironmentsNameRequest The putApiV4GroupsIdProtectedEnvironmentsNameRequest parameter
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedEnvironment putApiV4GroupsIdProtectedEnvironmentsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4GroupsIdProtectedEnvironmentsNameRequest putApiV4GroupsIdProtectedEnvironmentsNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(id, name, putApiV4GroupsIdProtectedEnvironmentsNameRequest).body(localVarReturnType);
    }

    /**
     * Update a protected environment for a group
     * Updates a specified protected environment for a group. Each &#x60;user_id&#x60; must belong to a user with at least the Maintainer role for the group. Each &#x60;group_id&#x60; must belong to the group, one of its subgroups, or a group it is shared with. To update an existing entry, pass its &#x60;id&#x60; in the &#x60;deploy_access_levels&#x60; or &#x60;approval_rules&#x60; hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name The deployment tier of the protected environment.
     * @param putApiV4GroupsIdProtectedEnvironmentsNameRequest The putApiV4GroupsIdProtectedEnvironmentsNameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedEnvironment> putApiV4GroupsIdProtectedEnvironmentsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4GroupsIdProtectedEnvironmentsNameRequest putApiV4GroupsIdProtectedEnvironmentsNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(id, name, putApiV4GroupsIdProtectedEnvironmentsNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a protected environment for a group
     * Updates a specified protected environment for a group. Each &#x60;user_id&#x60; must belong to a user with at least the Maintainer role for the group. Each &#x60;group_id&#x60; must belong to the group, one of its subgroups, or a group it is shared with. To update an existing entry, pass its &#x60;id&#x60; in the &#x60;deploy_access_levels&#x60; or &#x60;approval_rules&#x60; hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of a group maintained by the authenticated user.
     * @param name The deployment tier of the protected environment.
     * @param putApiV4GroupsIdProtectedEnvironmentsNameRequest The putApiV4GroupsIdProtectedEnvironmentsNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdProtectedEnvironmentsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4GroupsIdProtectedEnvironmentsNameRequest putApiV4GroupsIdProtectedEnvironmentsNameRequest) throws RestClientResponseException {
        return putApiV4GroupsIdProtectedEnvironmentsNameRequestCreation(id, name, putApiV4GroupsIdProtectedEnvironmentsNameRequest);
    }

    /**
     * Update a protected environment for a project
     * Updates a specified protected environment for a project. Each &#x60;user_id&#x60; must belong to a user with at least the Developer role for the project. Each &#x60;group_id&#x60; must belong to a group the project is shared with. To update an existing entry, pass its &#x60;id&#x60; in the &#x60;deploy_access_levels&#x60; or &#x60;approval_rules&#x60; hash.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the environment.
     * @param putApiV4ProjectsIdProtectedEnvironmentsNameRequest The putApiV4ProjectsIdProtectedEnvironmentsNameRequest parameter
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4ProjectsIdProtectedEnvironmentsNameRequest putApiV4ProjectsIdProtectedEnvironmentsNameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling putApiV4ProjectsIdProtectedEnvironmentsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_environments/{name}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdProtectedEnvironmentsNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a protected environment for a project
     * Updates a specified protected environment for a project. Each &#x60;user_id&#x60; must belong to a user with at least the Developer role for the project. Each &#x60;group_id&#x60; must belong to a group the project is shared with. To update an existing entry, pass its &#x60;id&#x60; in the &#x60;deploy_access_levels&#x60; or &#x60;approval_rules&#x60; hash.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the environment.
     * @param putApiV4ProjectsIdProtectedEnvironmentsNameRequest The putApiV4ProjectsIdProtectedEnvironmentsNameRequest parameter
     * @return APIEntitiesProtectedEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedEnvironment putApiV4ProjectsIdProtectedEnvironmentsName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4ProjectsIdProtectedEnvironmentsNameRequest putApiV4ProjectsIdProtectedEnvironmentsNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(id, name, putApiV4ProjectsIdProtectedEnvironmentsNameRequest).body(localVarReturnType);
    }

    /**
     * Update a protected environment for a project
     * Updates a specified protected environment for a project. Each &#x60;user_id&#x60; must belong to a user with at least the Developer role for the project. Each &#x60;group_id&#x60; must belong to a group the project is shared with. To update an existing entry, pass its &#x60;id&#x60; in the &#x60;deploy_access_levels&#x60; or &#x60;approval_rules&#x60; hash.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the environment.
     * @param putApiV4ProjectsIdProtectedEnvironmentsNameRequest The putApiV4ProjectsIdProtectedEnvironmentsNameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProtectedEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedEnvironment> putApiV4ProjectsIdProtectedEnvironmentsNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4ProjectsIdProtectedEnvironmentsNameRequest putApiV4ProjectsIdProtectedEnvironmentsNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(id, name, putApiV4ProjectsIdProtectedEnvironmentsNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a protected environment for a project
     * Updates a specified protected environment for a project. Each &#x60;user_id&#x60; must belong to a user with at least the Developer role for the project. Each &#x60;group_id&#x60; must belong to a group the project is shared with. To update an existing entry, pass its &#x60;id&#x60; in the &#x60;deploy_access_levels&#x60; or &#x60;approval_rules&#x60; hash.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the environment.
     * @param putApiV4ProjectsIdProtectedEnvironmentsNameRequest The putApiV4ProjectsIdProtectedEnvironmentsNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdProtectedEnvironmentsNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PutApiV4ProjectsIdProtectedEnvironmentsNameRequest putApiV4ProjectsIdProtectedEnvironmentsNameRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdProtectedEnvironmentsNameRequestCreation(id, name, putApiV4ProjectsIdProtectedEnvironmentsNameRequest);
    }
}
