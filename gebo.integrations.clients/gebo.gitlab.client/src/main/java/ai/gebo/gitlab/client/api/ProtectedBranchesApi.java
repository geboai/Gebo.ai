package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGroupProtectedBranch;
import ai.gebo.gitlab.client.model.APIEntitiesProtectedBranch;
import ai.gebo.gitlab.client.model.PatchApiV4GroupsIdProtectedBranchesNameRequest;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdProtectedBranchesNameRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdProtectedBranchesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdProtectedBranchesRequest;

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
public class ProtectedBranchesApi {
    private ApiClient apiClient;

    public ProtectedBranchesApi() {
        this(new ApiClient());
    }

    public ProtectedBranchesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Unprotect repository branches
     * Unprotects a specified protected branch Unprotect multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdProtectedBranchesNameRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4GroupsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_branches/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unprotect repository branches
     * Unprotects a specified protected branch Unprotect multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdProtectedBranchesName(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdProtectedBranchesNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Unprotect repository branches
     * Unprotects a specified protected branch Unprotect multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdProtectedBranchesNameWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdProtectedBranchesNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Unprotect repository branches
     * Unprotects a specified protected branch Unprotect multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdProtectedBranchesNameWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4GroupsIdProtectedBranchesNameRequestCreation(id, name);
    }

    /**
     * Unprotect repository branches
     * Unprotects a specified protected branch or wildcard protected branch.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdProtectedBranchesNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4ProjectsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_branches/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unprotect repository branches
     * Unprotects a specified protected branch or wildcard protected branch.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdProtectedBranchesName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdProtectedBranchesNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Unprotect repository branches
     * Unprotects a specified protected branch or wildcard protected branch.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdProtectedBranchesNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdProtectedBranchesNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Unprotect repository branches
     * Unprotects a specified protected branch or wildcard protected branch.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdProtectedBranchesNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4ProjectsIdProtectedBranchesNameRequestCreation(id, name);
    }

    /**
     * List all protected branches
     * Lists all protected branches from a group. If a wildcard is set, it is returned instead of the exact name of the branches that match that wildcard.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Name or part of the name of protected branches to search for.
     * @return List&lt;APIEntitiesGroupProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdProtectedBranchesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdProtectedBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesGroupProtectedBranch>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_branches", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all protected branches
     * Lists all protected branches from a group. If a wildcard is set, it is returned instead of the exact name of the branches that match that wildcard.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Name or part of the name of protected branches to search for.
     * @return List&lt;APIEntitiesGroupProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroupProtectedBranch> getApiV4GroupsIdProtectedBranches(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupProtectedBranch>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProtectedBranchesRequestCreation(id, page, perPage, search).body(localVarReturnType);
    }

    /**
     * List all protected branches
     * Lists all protected branches from a group. If a wildcard is set, it is returned instead of the exact name of the branches that match that wildcard.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Name or part of the name of protected branches to search for.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroupProtectedBranch&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroupProtectedBranch>> getApiV4GroupsIdProtectedBranchesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupProtectedBranch>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProtectedBranchesRequestCreation(id, page, perPage, search).toEntity(localVarReturnType);
    }

    /**
     * List all protected branches
     * Lists all protected branches from a group. If a wildcard is set, it is returned instead of the exact name of the branches that match that wildcard.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Name or part of the name of protected branches to search for.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdProtectedBranchesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        return getApiV4GroupsIdProtectedBranchesRequestCreation(id, page, perPage, search);
    }

    /**
     * Retrieve a protected branch
     * Retrieves a specified protected branch. Retrieve multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @return APIEntitiesGroupProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdProtectedBranchesNameRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4GroupsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_branches/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a protected branch
     * Retrieves a specified protected branch. Retrieve multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @return APIEntitiesGroupProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupProtectedBranch getApiV4GroupsIdProtectedBranchesName(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProtectedBranchesNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Retrieve a protected branch
     * Retrieves a specified protected branch. Retrieve multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @return ResponseEntity&lt;APIEntitiesGroupProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupProtectedBranch> getApiV4GroupsIdProtectedBranchesNameWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdProtectedBranchesNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a protected branch
     * Retrieves a specified protected branch. Retrieve multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdProtectedBranchesNameWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4GroupsIdProtectedBranchesNameRequestCreation(id, name);
    }

    /**
     * List all protected branches
     * Lists all protected branches for a specified project. If a wildcard is set, it is returned instead of the exact name of the branches that match that wildcard.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Name or part of the name of protected branches to search for.
     * @return List&lt;APIEntitiesProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdProtectedBranchesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdProtectedBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesProtectedBranch>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_branches", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all protected branches
     * Lists all protected branches for a specified project. If a wildcard is set, it is returned instead of the exact name of the branches that match that wildcard.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Name or part of the name of protected branches to search for.
     * @return List&lt;APIEntitiesProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProtectedBranch> getApiV4ProjectsIdProtectedBranches(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProtectedBranch>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedBranchesRequestCreation(id, page, perPage, search).body(localVarReturnType);
    }

    /**
     * List all protected branches
     * Lists all protected branches for a specified project. If a wildcard is set, it is returned instead of the exact name of the branches that match that wildcard.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Name or part of the name of protected branches to search for.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProtectedBranch&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProtectedBranch>> getApiV4ProjectsIdProtectedBranchesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProtectedBranch>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedBranchesRequestCreation(id, page, perPage, search).toEntity(localVarReturnType);
    }

    /**
     * List all protected branches
     * Lists all protected branches for a specified project. If a wildcard is set, it is returned instead of the exact name of the branches that match that wildcard.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param search Name or part of the name of protected branches to search for.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdProtectedBranchesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        return getApiV4ProjectsIdProtectedBranchesRequestCreation(id, page, perPage, search);
    }

    /**
     * Retrieve a protected branch or wildcard protected branch
     * Retrieves a specified protected branch or wildcard protected branch.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @return APIEntitiesProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdProtectedBranchesNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_branches/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a protected branch or wildcard protected branch
     * Retrieves a specified protected branch or wildcard protected branch.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @return APIEntitiesProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedBranch getApiV4ProjectsIdProtectedBranchesName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedBranchesNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Retrieve a protected branch or wildcard protected branch
     * Retrieves a specified protected branch or wildcard protected branch.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @return ResponseEntity&lt;APIEntitiesProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedBranch> getApiV4ProjectsIdProtectedBranchesNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdProtectedBranchesNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a protected branch or wildcard protected branch
     * Retrieves a specified protected branch or wildcard protected branch.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdProtectedBranchesNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4ProjectsIdProtectedBranchesNameRequestCreation(id, name);
    }

    /**
     * Update a protected branch
     * Updates a protected branch for a specified group. Each element of &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, and &#x60;allowed_to_unprotect&#x60; takes the form &#x60;{access_level: integer}&#x60; or &#x60;{member_role_id: integer}&#x60;. Each access level must be a valid access level. The custom role referenced by &#x60;member_role_id&#x60; must belong to the same top-level group as the protected branch. To update an access level, include its &#x60;id&#x60; in the hash. To delete an access level, include its &#x60;id&#x60; and set &#x60;_destroy&#x60; to &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>422</b> - Push access levels access level has already been taken
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @param patchApiV4GroupsIdProtectedBranchesNameRequest The patchApiV4GroupsIdProtectedBranchesNameRequest parameter
     * @return APIEntitiesGroupProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4GroupsIdProtectedBranchesNameRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PatchApiV4GroupsIdProtectedBranchesNameRequest patchApiV4GroupsIdProtectedBranchesNameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4GroupsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling patchApiV4GroupsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_branches/{name}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4GroupsIdProtectedBranchesNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a protected branch
     * Updates a protected branch for a specified group. Each element of &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, and &#x60;allowed_to_unprotect&#x60; takes the form &#x60;{access_level: integer}&#x60; or &#x60;{member_role_id: integer}&#x60;. Each access level must be a valid access level. The custom role referenced by &#x60;member_role_id&#x60; must belong to the same top-level group as the protected branch. To update an access level, include its &#x60;id&#x60; in the hash. To delete an access level, include its &#x60;id&#x60; and set &#x60;_destroy&#x60; to &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>422</b> - Push access levels access level has already been taken
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @param patchApiV4GroupsIdProtectedBranchesNameRequest The patchApiV4GroupsIdProtectedBranchesNameRequest parameter
     * @return APIEntitiesGroupProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupProtectedBranch patchApiV4GroupsIdProtectedBranchesName(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PatchApiV4GroupsIdProtectedBranchesNameRequest patchApiV4GroupsIdProtectedBranchesNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdProtectedBranchesNameRequestCreation(id, name, patchApiV4GroupsIdProtectedBranchesNameRequest).body(localVarReturnType);
    }

    /**
     * Update a protected branch
     * Updates a protected branch for a specified group. Each element of &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, and &#x60;allowed_to_unprotect&#x60; takes the form &#x60;{access_level: integer}&#x60; or &#x60;{member_role_id: integer}&#x60;. Each access level must be a valid access level. The custom role referenced by &#x60;member_role_id&#x60; must belong to the same top-level group as the protected branch. To update an access level, include its &#x60;id&#x60; in the hash. To delete an access level, include its &#x60;id&#x60; and set &#x60;_destroy&#x60; to &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>422</b> - Push access levels access level has already been taken
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @param patchApiV4GroupsIdProtectedBranchesNameRequest The patchApiV4GroupsIdProtectedBranchesNameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupProtectedBranch> patchApiV4GroupsIdProtectedBranchesNameWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PatchApiV4GroupsIdProtectedBranchesNameRequest patchApiV4GroupsIdProtectedBranchesNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdProtectedBranchesNameRequestCreation(id, name, patchApiV4GroupsIdProtectedBranchesNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a protected branch
     * Updates a protected branch for a specified group. Each element of &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, and &#x60;allowed_to_unprotect&#x60; takes the form &#x60;{access_level: integer}&#x60; or &#x60;{member_role_id: integer}&#x60;. Each access level must be a valid access level. The custom role referenced by &#x60;member_role_id&#x60; must belong to the same top-level group as the protected branch. To update an access level, include its &#x60;id&#x60; in the hash. To delete an access level, include its &#x60;id&#x60; and set &#x60;_destroy&#x60; to &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>422</b> - Push access levels access level has already been taken
     * <p><b>404</b> - 404 ProtectedBranch Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param name Name of the branch or wildcard.
     * @param patchApiV4GroupsIdProtectedBranchesNameRequest The patchApiV4GroupsIdProtectedBranchesNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4GroupsIdProtectedBranchesNameWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PatchApiV4GroupsIdProtectedBranchesNameRequest patchApiV4GroupsIdProtectedBranchesNameRequest) throws RestClientResponseException {
        return patchApiV4GroupsIdProtectedBranchesNameRequestCreation(id, name, patchApiV4GroupsIdProtectedBranchesNameRequest);
    }

    /**
     * Update a protected branch
     * Updates a protected branch for a specified project. Each element of &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, and &#x60;allowed_to_unprotect&#x60; takes the form &#x60;{user_id: integer}&#x60;, &#x60;{group_id: integer}&#x60;, &#x60;{member_role_id: integer}&#x60;, or &#x60;{access_level: integer}&#x60;. &#x60;allowed_to_push&#x60; also accepts &#x60;{deploy_key_id: integer}&#x60;. To update an existing access level record, include its &#x60;id&#x60; in the hash. To delete one, include its &#x60;id&#x60; and set &#x60;_destroy&#x60; to &#x60;true&#x60;. A user must have access to the project, and a group must have the project shared with it. A deploy key must be enabled for the project and have write access to the project repository.
     * <p><b>200</b> - OK
     * <p><b>422</b> - Push access levels access level has already been taken
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - 400 Bad request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @param patchApiV4ProjectsIdProtectedBranchesNameRequest The patchApiV4ProjectsIdProtectedBranchesNameRequest parameter
     * @return APIEntitiesProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdProtectedBranchesNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PatchApiV4ProjectsIdProtectedBranchesNameRequest patchApiV4ProjectsIdProtectedBranchesNameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling patchApiV4ProjectsIdProtectedBranchesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_branches/{name}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdProtectedBranchesNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a protected branch
     * Updates a protected branch for a specified project. Each element of &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, and &#x60;allowed_to_unprotect&#x60; takes the form &#x60;{user_id: integer}&#x60;, &#x60;{group_id: integer}&#x60;, &#x60;{member_role_id: integer}&#x60;, or &#x60;{access_level: integer}&#x60;. &#x60;allowed_to_push&#x60; also accepts &#x60;{deploy_key_id: integer}&#x60;. To update an existing access level record, include its &#x60;id&#x60; in the hash. To delete one, include its &#x60;id&#x60; and set &#x60;_destroy&#x60; to &#x60;true&#x60;. A user must have access to the project, and a group must have the project shared with it. A deploy key must be enabled for the project and have write access to the project repository.
     * <p><b>200</b> - OK
     * <p><b>422</b> - Push access levels access level has already been taken
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - 400 Bad request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @param patchApiV4ProjectsIdProtectedBranchesNameRequest The patchApiV4ProjectsIdProtectedBranchesNameRequest parameter
     * @return APIEntitiesProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedBranch patchApiV4ProjectsIdProtectedBranchesName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PatchApiV4ProjectsIdProtectedBranchesNameRequest patchApiV4ProjectsIdProtectedBranchesNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdProtectedBranchesNameRequestCreation(id, name, patchApiV4ProjectsIdProtectedBranchesNameRequest).body(localVarReturnType);
    }

    /**
     * Update a protected branch
     * Updates a protected branch for a specified project. Each element of &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, and &#x60;allowed_to_unprotect&#x60; takes the form &#x60;{user_id: integer}&#x60;, &#x60;{group_id: integer}&#x60;, &#x60;{member_role_id: integer}&#x60;, or &#x60;{access_level: integer}&#x60;. &#x60;allowed_to_push&#x60; also accepts &#x60;{deploy_key_id: integer}&#x60;. To update an existing access level record, include its &#x60;id&#x60; in the hash. To delete one, include its &#x60;id&#x60; and set &#x60;_destroy&#x60; to &#x60;true&#x60;. A user must have access to the project, and a group must have the project shared with it. A deploy key must be enabled for the project and have write access to the project repository.
     * <p><b>200</b> - OK
     * <p><b>422</b> - Push access levels access level has already been taken
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - 400 Bad request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @param patchApiV4ProjectsIdProtectedBranchesNameRequest The patchApiV4ProjectsIdProtectedBranchesNameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedBranch> patchApiV4ProjectsIdProtectedBranchesNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PatchApiV4ProjectsIdProtectedBranchesNameRequest patchApiV4ProjectsIdProtectedBranchesNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdProtectedBranchesNameRequestCreation(id, name, patchApiV4ProjectsIdProtectedBranchesNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a protected branch
     * Updates a protected branch for a specified project. Each element of &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, and &#x60;allowed_to_unprotect&#x60; takes the form &#x60;{user_id: integer}&#x60;, &#x60;{group_id: integer}&#x60;, &#x60;{member_role_id: integer}&#x60;, or &#x60;{access_level: integer}&#x60;. &#x60;allowed_to_push&#x60; also accepts &#x60;{deploy_key_id: integer}&#x60;. To update an existing access level record, include its &#x60;id&#x60; in the hash. To delete one, include its &#x60;id&#x60; and set &#x60;_destroy&#x60; to &#x60;true&#x60;. A user must have access to the project, and a group must have the project shared with it. A deploy key must be enabled for the project and have write access to the project repository.
     * <p><b>200</b> - OK
     * <p><b>422</b> - Push access levels access level has already been taken
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - 400 Bad request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the branch or wildcard.
     * @param patchApiV4ProjectsIdProtectedBranchesNameRequest The patchApiV4ProjectsIdProtectedBranchesNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdProtectedBranchesNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PatchApiV4ProjectsIdProtectedBranchesNameRequest patchApiV4ProjectsIdProtectedBranchesNameRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdProtectedBranchesNameRequestCreation(id, name, patchApiV4ProjectsIdProtectedBranchesNameRequest);
    }

    /**
     * Protect a repository branch
     * Protects a specified repository branch. Protect multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>201</b> - Created
     * <p><b>422</b> - name is missing
     * <p><b>409</b> - Protected branch &#39;main&#39; already exists
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdProtectedBranchesRequest The postApiV4GroupsIdProtectedBranchesRequest parameter
     * @return APIEntitiesGroupProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdProtectedBranchesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdProtectedBranchesRequest postApiV4GroupsIdProtectedBranchesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdProtectedBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdProtectedBranchesRequest' is set
        if (postApiV4GroupsIdProtectedBranchesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdProtectedBranchesRequest' when calling postApiV4GroupsIdProtectedBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/protected_branches", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdProtectedBranchesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Protect a repository branch
     * Protects a specified repository branch. Protect multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>201</b> - Created
     * <p><b>422</b> - name is missing
     * <p><b>409</b> - Protected branch &#39;main&#39; already exists
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdProtectedBranchesRequest The postApiV4GroupsIdProtectedBranchesRequest parameter
     * @return APIEntitiesGroupProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupProtectedBranch postApiV4GroupsIdProtectedBranches(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdProtectedBranchesRequest postApiV4GroupsIdProtectedBranchesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdProtectedBranchesRequestCreation(id, postApiV4GroupsIdProtectedBranchesRequest).body(localVarReturnType);
    }

    /**
     * Protect a repository branch
     * Protects a specified repository branch. Protect multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>201</b> - Created
     * <p><b>422</b> - name is missing
     * <p><b>409</b> - Protected branch &#39;main&#39; already exists
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdProtectedBranchesRequest The postApiV4GroupsIdProtectedBranchesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupProtectedBranch> postApiV4GroupsIdProtectedBranchesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdProtectedBranchesRequest postApiV4GroupsIdProtectedBranchesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdProtectedBranchesRequestCreation(id, postApiV4GroupsIdProtectedBranchesRequest).toEntity(localVarReturnType);
    }

    /**
     * Protect a repository branch
     * Protects a specified repository branch. Protect multiple branches by using a wildcard in the &#x60;name&#x60;.
     * <p><b>201</b> - Created
     * <p><b>422</b> - name is missing
     * <p><b>409</b> - Protected branch &#39;main&#39; already exists
     * <p><b>404</b> - 404 Group Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdProtectedBranchesRequest The postApiV4GroupsIdProtectedBranchesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdProtectedBranchesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdProtectedBranchesRequest postApiV4GroupsIdProtectedBranchesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdProtectedBranchesRequestCreation(id, postApiV4GroupsIdProtectedBranchesRequest);
    }

    /**
     * Protect repository branches
     * Protects a specified repository branch or several project repository branches using a wildcard protected branch. You can set multiple access levels for &#x60;allowed_to_push&#x60; and &#x60;allowed_to_merge&#x60;. The most permissive access level determines who can perform the action. Do not include &#x60;id&#x60; in the &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, or &#x60;allowed_to_unprotect&#x60; arrays. An &#x60;id&#x60; that does not match an existing record returns &#x60;404 Not Found&#x60;. Unlike the UI, the API does not clear other access levels when an element sets &#x60;access_level&#x60; to &#x60;0&#x60; (No access).
     * <p><b>201</b> - Created
     * <p><b>422</b> - name is missing
     * <p><b>409</b> - Protected branch &#39;main&#39; already exists
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedBranchesRequest The postApiV4ProjectsIdProtectedBranchesRequest parameter
     * @return APIEntitiesProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdProtectedBranchesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedBranchesRequest postApiV4ProjectsIdProtectedBranchesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdProtectedBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdProtectedBranchesRequest' is set
        if (postApiV4ProjectsIdProtectedBranchesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdProtectedBranchesRequest' when calling postApiV4ProjectsIdProtectedBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/protected_branches", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdProtectedBranchesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Protect repository branches
     * Protects a specified repository branch or several project repository branches using a wildcard protected branch. You can set multiple access levels for &#x60;allowed_to_push&#x60; and &#x60;allowed_to_merge&#x60;. The most permissive access level determines who can perform the action. Do not include &#x60;id&#x60; in the &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, or &#x60;allowed_to_unprotect&#x60; arrays. An &#x60;id&#x60; that does not match an existing record returns &#x60;404 Not Found&#x60;. Unlike the UI, the API does not clear other access levels when an element sets &#x60;access_level&#x60; to &#x60;0&#x60; (No access).
     * <p><b>201</b> - Created
     * <p><b>422</b> - name is missing
     * <p><b>409</b> - Protected branch &#39;main&#39; already exists
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedBranchesRequest The postApiV4ProjectsIdProtectedBranchesRequest parameter
     * @return APIEntitiesProtectedBranch
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProtectedBranch postApiV4ProjectsIdProtectedBranches(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedBranchesRequest postApiV4ProjectsIdProtectedBranchesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdProtectedBranchesRequestCreation(id, postApiV4ProjectsIdProtectedBranchesRequest).body(localVarReturnType);
    }

    /**
     * Protect repository branches
     * Protects a specified repository branch or several project repository branches using a wildcard protected branch. You can set multiple access levels for &#x60;allowed_to_push&#x60; and &#x60;allowed_to_merge&#x60;. The most permissive access level determines who can perform the action. Do not include &#x60;id&#x60; in the &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, or &#x60;allowed_to_unprotect&#x60; arrays. An &#x60;id&#x60; that does not match an existing record returns &#x60;404 Not Found&#x60;. Unlike the UI, the API does not clear other access levels when an element sets &#x60;access_level&#x60; to &#x60;0&#x60; (No access).
     * <p><b>201</b> - Created
     * <p><b>422</b> - name is missing
     * <p><b>409</b> - Protected branch &#39;main&#39; already exists
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedBranchesRequest The postApiV4ProjectsIdProtectedBranchesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProtectedBranch&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProtectedBranch> postApiV4ProjectsIdProtectedBranchesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedBranchesRequest postApiV4ProjectsIdProtectedBranchesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProtectedBranch> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdProtectedBranchesRequestCreation(id, postApiV4ProjectsIdProtectedBranchesRequest).toEntity(localVarReturnType);
    }

    /**
     * Protect repository branches
     * Protects a specified repository branch or several project repository branches using a wildcard protected branch. You can set multiple access levels for &#x60;allowed_to_push&#x60; and &#x60;allowed_to_merge&#x60;. The most permissive access level determines who can perform the action. Do not include &#x60;id&#x60; in the &#x60;allowed_to_push&#x60;, &#x60;allowed_to_merge&#x60;, or &#x60;allowed_to_unprotect&#x60; arrays. An &#x60;id&#x60; that does not match an existing record returns &#x60;404 Not Found&#x60;. Unlike the UI, the API does not clear other access levels when an element sets &#x60;access_level&#x60; to &#x60;0&#x60; (No access).
     * <p><b>201</b> - Created
     * <p><b>422</b> - name is missing
     * <p><b>409</b> - Protected branch &#39;main&#39; already exists
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdProtectedBranchesRequest The postApiV4ProjectsIdProtectedBranchesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdProtectedBranchesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdProtectedBranchesRequest postApiV4ProjectsIdProtectedBranchesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdProtectedBranchesRequestCreation(id, postApiV4ProjectsIdProtectedBranchesRequest);
    }
}
