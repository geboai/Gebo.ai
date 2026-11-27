package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ConditionSearchRequest;
import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.Role;
import ai.gebo.zammad.client.model.RoleCreate;
import ai.gebo.zammad.client.model.RoleUpdate;
import ai.gebo.zammad.client.model.SearchGroupsOrderByParameter;
import ai.gebo.zammad.client.model.SearchRoles200Response;

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
public class RolesApi {
    private ApiClient apiClient;

    public RolesApi() {
        this(new ApiClient());
    }

    public RolesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create role
     * 
     * <p><b>201</b> - Resource created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleCreate The roleCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Role
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createRoleRequestCreation(@jakarta.annotation.Nonnull RoleCreate roleCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'roleCreate' is set
        if (roleCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'roleCreate' when calling createRole", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Role> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/roles", HttpMethod.POST, pathParams, localVarQueryParams, roleCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create role
     * 
     * <p><b>201</b> - Resource created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleCreate The roleCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Role
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Role createRole(@jakarta.annotation.Nonnull RoleCreate roleCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Role> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createRoleRequestCreation(roleCreate, from).body(localVarReturnType);
    }

    /**
     * Create role
     * 
     * <p><b>201</b> - Resource created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleCreate The roleCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Role&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Role> createRoleWithHttpInfo(@jakarta.annotation.Nonnull RoleCreate roleCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Role> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createRoleRequestCreation(roleCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create role
     * 
     * <p><b>201</b> - Resource created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleCreate The roleCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createRoleWithResponseSpec(@jakarta.annotation.Nonnull RoleCreate roleCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createRoleRequestCreation(roleCreate, from);
    }

    /**
     * Show role
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleId Role ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return Role
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRoleRequestCreation(@jakarta.annotation.Nonnull Long roleId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        // verify the required parameter 'roleId' is set
        if (roleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'roleId' when calling getRole", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("roleId", roleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Role> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/roles/{roleId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show role
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleId Role ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return Role
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Role getRole(@jakarta.annotation.Nonnull Long roleId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        ParameterizedTypeReference<Role> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRoleRequestCreation(roleId, from, expand).body(localVarReturnType);
    }

    /**
     * Show role
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleId Role ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return ResponseEntity&lt;Role&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Role> getRoleWithHttpInfo(@jakarta.annotation.Nonnull Long roleId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        ParameterizedTypeReference<Role> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRoleRequestCreation(roleId, from, expand).toEntity(localVarReturnType);
    }

    /**
     * Show role
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleId Role ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRoleWithResponseSpec(@jakarta.annotation.Nonnull Long roleId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        return getRoleRequestCreation(roleId, from, expand);
    }

    /**
     * List roles
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;Role&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listRolesRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<List<Role>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/roles", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List roles
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;Role&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<Role> listRoles(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<Role>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRolesRequestCreation(from, expand, page, perPage).body(localVarReturnType);
    }

    /**
     * List roles
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;List&lt;Role&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Role>> listRolesWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<Role>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRolesRequestCreation(from, expand, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List roles
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listRolesWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return listRolesRequestCreation(from, expand, page, perPage);
    }

    /**
     * Search roles
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return SearchRoles200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchRolesRequestCreation(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new RestClientResponseException("Missing the required parameter 'query' when calling searchRoles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort_by", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<SearchRoles200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/roles/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search roles
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return SearchRoles200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SearchRoles200Response searchRoles(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<SearchRoles200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchRolesRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).body(localVarReturnType);
    }

    /**
     * Search roles
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseEntity&lt;SearchRoles200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchRoles200Response> searchRolesWithHttpInfo(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<SearchRoles200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchRolesRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).toEntity(localVarReturnType);
    }

    /**
     * Search roles
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchRolesWithResponseSpec(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        return searchRolesRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy);
    }

    /**
     * Search roles by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return SearchRoles200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchRolesByConditionRequestCreation(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'conditionSearchRequest' is set
        if (conditionSearchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'conditionSearchRequest' when calling searchRolesByCondition", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<SearchRoles200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/roles/search", HttpMethod.POST, pathParams, localVarQueryParams, conditionSearchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search roles by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return SearchRoles200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SearchRoles200Response searchRolesByCondition(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<SearchRoles200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchRolesByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).body(localVarReturnType);
    }

    /**
     * Search roles by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;SearchRoles200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchRoles200Response> searchRolesByConditionWithHttpInfo(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<SearchRoles200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchRolesByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Search roles by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchRolesByConditionWithResponseSpec(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return searchRolesByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage);
    }

    /**
     * Update role
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleId Role ID.
     * @param roleUpdate The roleUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Role
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateRoleRequestCreation(@jakarta.annotation.Nonnull Long roleId, @jakarta.annotation.Nonnull RoleUpdate roleUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'roleId' is set
        if (roleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'roleId' when calling updateRole", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'roleUpdate' is set
        if (roleUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'roleUpdate' when calling updateRole", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("roleId", roleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Role> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/roles/{roleId}", HttpMethod.PUT, pathParams, localVarQueryParams, roleUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update role
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleId Role ID.
     * @param roleUpdate The roleUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Role
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Role updateRole(@jakarta.annotation.Nonnull Long roleId, @jakarta.annotation.Nonnull RoleUpdate roleUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Role> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateRoleRequestCreation(roleId, roleUpdate, from).body(localVarReturnType);
    }

    /**
     * Update role
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleId Role ID.
     * @param roleUpdate The roleUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Role&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Role> updateRoleWithHttpInfo(@jakarta.annotation.Nonnull Long roleId, @jakarta.annotation.Nonnull RoleUpdate roleUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Role> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateRoleRequestCreation(roleId, roleUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update role
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param roleId Role ID.
     * @param roleUpdate The roleUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateRoleWithResponseSpec(@jakarta.annotation.Nonnull Long roleId, @jakarta.annotation.Nonnull RoleUpdate roleUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateRoleRequestCreation(roleId, roleUpdate, from);
    }
}
