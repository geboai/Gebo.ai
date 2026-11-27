package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGitlabSubscription;
import ai.gebo.gitlab.client.model.APIEntitiesNamespace;
import ai.gebo.gitlab.client.model.APIEntitiesNamespaceExistence;
import ai.gebo.gitlab.client.model.APIEntitiesNamespacesStorageLimitExclusion;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4NamespacesIdStorageLimitExclusionRequest;
import ai.gebo.gitlab.client.model.PutApiV4NamespacesIdRequest;

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
public class NamespacesApi {
    private ApiClient apiClient;

    public NamespacesApi() {
        this(new ApiClient());
    }

    public NamespacesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Removes a storage limit exclusion for a Namespace
     * Removes a Namespaces::Storage::LimitExclusion
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the namespace.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4NamespacesIdStorageLimitExclusionRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4NamespacesIdStorageLimitExclusion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/namespaces/{id}/storage/limit_exclusion", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Removes a storage limit exclusion for a Namespace
     * Removes a Namespaces::Storage::LimitExclusion
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the namespace.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4NamespacesIdStorageLimitExclusion(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4NamespacesIdStorageLimitExclusionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Removes a storage limit exclusion for a Namespace
     * Removes a Namespaces::Storage::LimitExclusion
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the namespace.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4NamespacesIdStorageLimitExclusionWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4NamespacesIdStorageLimitExclusionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Removes a storage limit exclusion for a Namespace
     * Removes a Namespaces::Storage::LimitExclusion
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the namespace.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4NamespacesIdStorageLimitExclusionWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4NamespacesIdStorageLimitExclusionRequestCreation(id);
    }

    /**
     * List all namespaces
     * Lists all namespaces available to the current user. If the user is an administrator, this endpoint returns all namespaces in the instance. The response can include additional attributes for group owners or on GitLab.com. The &#x60;shared_runners_minutes_limit&#x60;, &#x60;extra_shared_runners_minutes_limit&#x60;, &#x60;additional_purchased_storage_size&#x60;, and &#x60;additional_purchased_storage_ends_on&#x60; attributes are returned only to administrators on GitLab.com.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param search Return only namespaces that contain the specified value in their name or path.
     * @param ownedOnly If &#x60;true&#x60;, returns only namespaces owned by the current user.
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level namespaces.
     * @param fullPathSearch If &#x60;true&#x60;, the &#x60;search&#x60; parameter is matched against the full path of the namespaces.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param requestedHostedPlan Name of the hosted plan requested by the customer.
     * @return List&lt;APIEntitiesNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4NamespacesRequestCreation(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean ownedOnly, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable Boolean fullPathSearch, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String requestedHostedPlan) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "owned_only", ownedOnly));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "top_level_only", topLevelOnly));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full_path_search", fullPathSearch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "requested_hosted_plan", requestedHostedPlan));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesNamespace>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/namespaces", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all namespaces
     * Lists all namespaces available to the current user. If the user is an administrator, this endpoint returns all namespaces in the instance. The response can include additional attributes for group owners or on GitLab.com. The &#x60;shared_runners_minutes_limit&#x60;, &#x60;extra_shared_runners_minutes_limit&#x60;, &#x60;additional_purchased_storage_size&#x60;, and &#x60;additional_purchased_storage_ends_on&#x60; attributes are returned only to administrators on GitLab.com.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param search Return only namespaces that contain the specified value in their name or path.
     * @param ownedOnly If &#x60;true&#x60;, returns only namespaces owned by the current user.
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level namespaces.
     * @param fullPathSearch If &#x60;true&#x60;, the &#x60;search&#x60; parameter is matched against the full path of the namespaces.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param requestedHostedPlan Name of the hosted plan requested by the customer.
     * @return List&lt;APIEntitiesNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNamespace> getApiV4Namespaces(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean ownedOnly, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable Boolean fullPathSearch, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String requestedHostedPlan) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNamespace>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesRequestCreation(search, ownedOnly, topLevelOnly, fullPathSearch, page, perPage, requestedHostedPlan).body(localVarReturnType);
    }

    /**
     * List all namespaces
     * Lists all namespaces available to the current user. If the user is an administrator, this endpoint returns all namespaces in the instance. The response can include additional attributes for group owners or on GitLab.com. The &#x60;shared_runners_minutes_limit&#x60;, &#x60;extra_shared_runners_minutes_limit&#x60;, &#x60;additional_purchased_storage_size&#x60;, and &#x60;additional_purchased_storage_ends_on&#x60; attributes are returned only to administrators on GitLab.com.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param search Return only namespaces that contain the specified value in their name or path.
     * @param ownedOnly If &#x60;true&#x60;, returns only namespaces owned by the current user.
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level namespaces.
     * @param fullPathSearch If &#x60;true&#x60;, the &#x60;search&#x60; parameter is matched against the full path of the namespaces.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param requestedHostedPlan Name of the hosted plan requested by the customer.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNamespace&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNamespace>> getApiV4NamespacesWithHttpInfo(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean ownedOnly, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable Boolean fullPathSearch, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String requestedHostedPlan) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNamespace>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesRequestCreation(search, ownedOnly, topLevelOnly, fullPathSearch, page, perPage, requestedHostedPlan).toEntity(localVarReturnType);
    }

    /**
     * List all namespaces
     * Lists all namespaces available to the current user. If the user is an administrator, this endpoint returns all namespaces in the instance. The response can include additional attributes for group owners or on GitLab.com. The &#x60;shared_runners_minutes_limit&#x60;, &#x60;extra_shared_runners_minutes_limit&#x60;, &#x60;additional_purchased_storage_size&#x60;, and &#x60;additional_purchased_storage_ends_on&#x60; attributes are returned only to administrators on GitLab.com.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param search Return only namespaces that contain the specified value in their name or path.
     * @param ownedOnly If &#x60;true&#x60;, returns only namespaces owned by the current user.
     * @param topLevelOnly If &#x60;true&#x60;, returns only top-level namespaces.
     * @param fullPathSearch If &#x60;true&#x60;, the &#x60;search&#x60; parameter is matched against the full path of the namespaces.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param requestedHostedPlan Name of the hosted plan requested by the customer.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4NamespacesWithResponseSpec(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean ownedOnly, @jakarta.annotation.Nullable Boolean topLevelOnly, @jakarta.annotation.Nullable Boolean fullPathSearch, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String requestedHostedPlan) throws RestClientResponseException {
        return getApiV4NamespacesRequestCreation(search, ownedOnly, topLevelOnly, fullPathSearch, page, perPage, requestedHostedPlan);
    }

    /**
     * Retrieve namespace details
     * Retrieves a specified namespace.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the namespace.
     * @return APIEntitiesNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4NamespacesIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4NamespacesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/namespaces/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve namespace details
     * Retrieves a specified namespace.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the namespace.
     * @return APIEntitiesNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNamespace getApiV4NamespacesId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve namespace details
     * Retrieves a specified namespace.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the namespace.
     * @return ResponseEntity&lt;APIEntitiesNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNamespace> getApiV4NamespacesIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve namespace details
     * Retrieves a specified namespace.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the namespace.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4NamespacesIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4NamespacesIdRequestCreation(id);
    }

    /**
     * Verify namespace availability
     * Verifies that a namespace is available for use. If the namespace exists, the response suggests an alternate name in &#x60;suggests&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id Path of the namespace.
     * @param parentId ID of the parent namespace. If omitted, only top-level namespaces are considered.
     * @return APIEntitiesNamespaceExistence
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4NamespacesIdExistsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer parentId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4NamespacesIdExists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "parent_id", parentId));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesNamespaceExistence> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/namespaces/{id}/exists", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Verify namespace availability
     * Verifies that a namespace is available for use. If the namespace exists, the response suggests an alternate name in &#x60;suggests&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id Path of the namespace.
     * @param parentId ID of the parent namespace. If omitted, only top-level namespaces are considered.
     * @return APIEntitiesNamespaceExistence
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNamespaceExistence getApiV4NamespacesIdExists(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer parentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNamespaceExistence> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesIdExistsRequestCreation(id, parentId).body(localVarReturnType);
    }

    /**
     * Verify namespace availability
     * Verifies that a namespace is available for use. If the namespace exists, the response suggests an alternate name in &#x60;suggests&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id Path of the namespace.
     * @param parentId ID of the parent namespace. If omitted, only top-level namespaces are considered.
     * @return ResponseEntity&lt;APIEntitiesNamespaceExistence&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNamespaceExistence> getApiV4NamespacesIdExistsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer parentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNamespaceExistence> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesIdExistsRequestCreation(id, parentId).toEntity(localVarReturnType);
    }

    /**
     * Verify namespace availability
     * Verifies that a namespace is available for use. If the namespace exists, the response suggests an alternate name in &#x60;suggests&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id Path of the namespace.
     * @param parentId ID of the parent namespace. If omitted, only top-level namespaces are considered.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4NamespacesIdExistsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer parentId) throws RestClientResponseException {
        return getApiV4NamespacesIdExistsRequestCreation(id, parentId);
    }

    /**
     * Retrieve namespace subscription
     * Retrieves GitLab subscription details for a specified namespace.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the namespace.
     * @return APIEntitiesGitlabSubscription
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4NamespacesIdGitlabSubscriptionRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4NamespacesIdGitlabSubscription", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGitlabSubscription> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/namespaces/{id}/gitlab_subscription", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve namespace subscription
     * Retrieves GitLab subscription details for a specified namespace.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the namespace.
     * @return APIEntitiesGitlabSubscription
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGitlabSubscription getApiV4NamespacesIdGitlabSubscription(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGitlabSubscription> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesIdGitlabSubscriptionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve namespace subscription
     * Retrieves GitLab subscription details for a specified namespace.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the namespace.
     * @return ResponseEntity&lt;APIEntitiesGitlabSubscription&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGitlabSubscription> getApiV4NamespacesIdGitlabSubscriptionWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGitlabSubscription> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesIdGitlabSubscriptionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve namespace subscription
     * Retrieves GitLab subscription details for a specified namespace.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the namespace.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4NamespacesIdGitlabSubscriptionWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4NamespacesIdGitlabSubscriptionRequestCreation(id);
    }

    /**
     * Retrieve all limit exclusions
     * Gets all records for namespaces that have been excluded
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNamespacesStorageLimitExclusion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4NamespacesStorageLimitExclusionsRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesNamespacesStorageLimitExclusion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/namespaces/storage/limit_exclusions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve all limit exclusions
     * Gets all records for namespaces that have been excluded
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesNamespacesStorageLimitExclusion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesNamespacesStorageLimitExclusion> getApiV4NamespacesStorageLimitExclusions(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNamespacesStorageLimitExclusion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesStorageLimitExclusionsRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * Retrieve all limit exclusions
     * Gets all records for namespaces that have been excluded
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesNamespacesStorageLimitExclusion&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesNamespacesStorageLimitExclusion>> getApiV4NamespacesStorageLimitExclusionsWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesNamespacesStorageLimitExclusion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NamespacesStorageLimitExclusionsRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Retrieve all limit exclusions
     * Gets all records for namespaces that have been excluded
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4NamespacesStorageLimitExclusionsWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4NamespacesStorageLimitExclusionsRequestCreation(page, perPage);
    }

    /**
     * Creates a storage limit exclusion for a Namespace
     * Creates a Namespaces::Storage::LimitExclusion
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param postApiV4NamespacesIdStorageLimitExclusionRequest The postApiV4NamespacesIdStorageLimitExclusionRequest parameter
     * @return APIEntitiesNamespacesStorageLimitExclusion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4NamespacesIdStorageLimitExclusionRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4NamespacesIdStorageLimitExclusionRequest postApiV4NamespacesIdStorageLimitExclusionRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4NamespacesIdStorageLimitExclusion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4NamespacesIdStorageLimitExclusionRequest' is set
        if (postApiV4NamespacesIdStorageLimitExclusionRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4NamespacesIdStorageLimitExclusionRequest' when calling postApiV4NamespacesIdStorageLimitExclusion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNamespacesStorageLimitExclusion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/namespaces/{id}/storage/limit_exclusion", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4NamespacesIdStorageLimitExclusionRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates a storage limit exclusion for a Namespace
     * Creates a Namespaces::Storage::LimitExclusion
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param postApiV4NamespacesIdStorageLimitExclusionRequest The postApiV4NamespacesIdStorageLimitExclusionRequest parameter
     * @return APIEntitiesNamespacesStorageLimitExclusion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNamespacesStorageLimitExclusion postApiV4NamespacesIdStorageLimitExclusion(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4NamespacesIdStorageLimitExclusionRequest postApiV4NamespacesIdStorageLimitExclusionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNamespacesStorageLimitExclusion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4NamespacesIdStorageLimitExclusionRequestCreation(id, postApiV4NamespacesIdStorageLimitExclusionRequest).body(localVarReturnType);
    }

    /**
     * Creates a storage limit exclusion for a Namespace
     * Creates a Namespaces::Storage::LimitExclusion
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param postApiV4NamespacesIdStorageLimitExclusionRequest The postApiV4NamespacesIdStorageLimitExclusionRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNamespacesStorageLimitExclusion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNamespacesStorageLimitExclusion> postApiV4NamespacesIdStorageLimitExclusionWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4NamespacesIdStorageLimitExclusionRequest postApiV4NamespacesIdStorageLimitExclusionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNamespacesStorageLimitExclusion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4NamespacesIdStorageLimitExclusionRequestCreation(id, postApiV4NamespacesIdStorageLimitExclusionRequest).toEntity(localVarReturnType);
    }

    /**
     * Creates a storage limit exclusion for a Namespace
     * Creates a Namespaces::Storage::LimitExclusion
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param postApiV4NamespacesIdStorageLimitExclusionRequest The postApiV4NamespacesIdStorageLimitExclusionRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4NamespacesIdStorageLimitExclusionWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4NamespacesIdStorageLimitExclusionRequest postApiV4NamespacesIdStorageLimitExclusionRequest) throws RestClientResponseException {
        return postApiV4NamespacesIdStorageLimitExclusionRequestCreation(id, postApiV4NamespacesIdStorageLimitExclusionRequest);
    }

    /**
     * Update a namespace
     * Deprecated in 17.8
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param putApiV4NamespacesIdRequest The putApiV4NamespacesIdRequest parameter
     * @return APIEntitiesNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4NamespacesIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4NamespacesIdRequest putApiV4NamespacesIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4NamespacesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/namespaces/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4NamespacesIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a namespace
     * Deprecated in 17.8
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param putApiV4NamespacesIdRequest The putApiV4NamespacesIdRequest parameter
     * @return APIEntitiesNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNamespace putApiV4NamespacesId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4NamespacesIdRequest putApiV4NamespacesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4NamespacesIdRequestCreation(id, putApiV4NamespacesIdRequest).body(localVarReturnType);
    }

    /**
     * Update a namespace
     * Deprecated in 17.8
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param putApiV4NamespacesIdRequest The putApiV4NamespacesIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNamespace> putApiV4NamespacesIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4NamespacesIdRequest putApiV4NamespacesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4NamespacesIdRequestCreation(id, putApiV4NamespacesIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a namespace
     * Deprecated in 17.8
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param putApiV4NamespacesIdRequest The putApiV4NamespacesIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4NamespacesIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4NamespacesIdRequest putApiV4NamespacesIdRequest) throws RestClientResponseException {
        return putApiV4NamespacesIdRequestCreation(id, putApiV4NamespacesIdRequest);
    }
}
