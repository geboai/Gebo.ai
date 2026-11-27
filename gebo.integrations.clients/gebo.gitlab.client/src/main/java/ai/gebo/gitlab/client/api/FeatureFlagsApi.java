package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesFeatureFlag;
import ai.gebo.gitlab.client.model.APIEntitiesFeatureFlagSettings;
import ai.gebo.gitlab.client.model.APIEntitiesFeatureFlagUserList;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdFeatureFlagsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdFeatureFlagsUserListsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdFeatureFlagsSettingsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdFeatureFlagsUserListsIidRequest;

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
public class FeatureFlagsApi {
    private ApiClient apiClient;

    public FeatureFlagsApi() {
        this(new ApiClient());
    }

    public FeatureFlagsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a feature flag
     * Deletes a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @return APIEntitiesFeatureFlag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdFeatureFlagsFeatureFlagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'featureFlagName' is set
        if (featureFlagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'featureFlagName' when calling deleteApiV4ProjectsIdFeatureFlagsFeatureFlagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("feature_flag_name", featureFlagName);

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

        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags/{feature_flag_name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a feature flag
     * Deletes a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @return APIEntitiesFeatureFlag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeatureFlag deleteApiV4ProjectsIdFeatureFlagsFeatureFlagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(id, featureFlagName).body(localVarReturnType);
    }

    /**
     * Delete a feature flag
     * Deletes a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @return ResponseEntity&lt;APIEntitiesFeatureFlag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeatureFlag> deleteApiV4ProjectsIdFeatureFlagsFeatureFlagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(id, featureFlagName).toEntity(localVarReturnType);
    }

    /**
     * Delete a feature flag
     * Deletes a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdFeatureFlagsFeatureFlagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName) throws RestClientResponseException {
        return deleteApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(id, featureFlagName);
    }

    /**
     * Delete feature flag user list
     * Deletes a specified feature flag user list.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdFeatureFlagsUserListsIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'iid' is set
        if (iid == null) {
            throw new RestClientResponseException("Missing the required parameter 'iid' when calling deleteApiV4ProjectsIdFeatureFlagsUserListsIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("iid", iid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags_user_lists/{iid}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete feature flag user list
     * Deletes a specified feature flag user list.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdFeatureFlagsUserListsIid(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(id, iid).body(localVarReturnType);
    }

    /**
     * Delete feature flag user list
     * Deletes a specified feature flag user list.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdFeatureFlagsUserListsIidWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(id, iid).toEntity(localVarReturnType);
    }

    /**
     * Delete feature flag user list
     * Deletes a specified feature flag user list.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdFeatureFlagsUserListsIidWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid) throws RestClientResponseException {
        return deleteApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(id, iid);
    }

    /**
     * List all feature flags for a project
     * Lists all feature flags of the requested project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param scope Filter feature flags by condition.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesFeatureFlag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdFeatureFlagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdFeatureFlags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesFeatureFlag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all feature flags for a project
     * Lists all feature flags of the requested project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param scope Filter feature flags by condition.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesFeatureFlag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesFeatureFlag> getApiV4ProjectsIdFeatureFlags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFeatureFlag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsRequestCreation(id, scope, page, perPage).body(localVarReturnType);
    }

    /**
     * List all feature flags for a project
     * Lists all feature flags of the requested project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param scope Filter feature flags by condition.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesFeatureFlag&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesFeatureFlag>> getApiV4ProjectsIdFeatureFlagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFeatureFlag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsRequestCreation(id, scope, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all feature flags for a project
     * Lists all feature flags of the requested project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param scope Filter feature flags by condition.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdFeatureFlagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdFeatureFlagsRequestCreation(id, scope, page, perPage);
    }

    /**
     * Retrieve a feature flag
     * Retrieves a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @return APIEntitiesFeatureFlag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdFeatureFlagsFeatureFlagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'featureFlagName' is set
        if (featureFlagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'featureFlagName' when calling getApiV4ProjectsIdFeatureFlagsFeatureFlagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("feature_flag_name", featureFlagName);

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

        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags/{feature_flag_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a feature flag
     * Retrieves a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @return APIEntitiesFeatureFlag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeatureFlag getApiV4ProjectsIdFeatureFlagsFeatureFlagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(id, featureFlagName).body(localVarReturnType);
    }

    /**
     * Retrieve a feature flag
     * Retrieves a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @return ResponseEntity&lt;APIEntitiesFeatureFlag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeatureFlag> getApiV4ProjectsIdFeatureFlagsFeatureFlagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(id, featureFlagName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a feature flag
     * Retrieves a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdFeatureFlagsFeatureFlagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName) throws RestClientResponseException {
        return getApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(id, featureFlagName);
    }

    /**
     * Retrieve feature flag settings
     * Retrieves the feature flag settings of a specified project. Enforcement of &#x60;minimum_role&#x60; is behind a feature flag named &#x60;feature_flag_management_permissions&#x60;, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesFeatureFlagSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdFeatureFlagsSettingsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdFeatureFlagsSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesFeatureFlagSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags_settings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve feature flag settings
     * Retrieves the feature flag settings of a specified project. Enforcement of &#x60;minimum_role&#x60; is behind a feature flag named &#x60;feature_flag_management_permissions&#x60;, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesFeatureFlagSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeatureFlagSettings getApiV4ProjectsIdFeatureFlagsSettings(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsSettingsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve feature flag settings
     * Retrieves the feature flag settings of a specified project. Enforcement of &#x60;minimum_role&#x60; is behind a feature flag named &#x60;feature_flag_management_permissions&#x60;, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesFeatureFlagSettings&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeatureFlagSettings> getApiV4ProjectsIdFeatureFlagsSettingsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsSettingsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve feature flag settings
     * Retrieves the feature flag settings of a specified project. Enforcement of &#x60;minimum_role&#x60; is behind a feature flag named &#x60;feature_flag_management_permissions&#x60;, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdFeatureFlagsSettingsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdFeatureFlagsSettingsRequestCreation(id);
    }

    /**
     * List all feature flag user lists for a project
     * Lists all feature flag user lists for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param search Return user lists matching the search criteria.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesFeatureFlagUserList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdFeatureFlagsUserListsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdFeatureFlagsUserLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesFeatureFlagUserList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags_user_lists", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all feature flag user lists for a project
     * Lists all feature flag user lists for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param search Return user lists matching the search criteria.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesFeatureFlagUserList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesFeatureFlagUserList> getApiV4ProjectsIdFeatureFlagsUserLists(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFeatureFlagUserList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsUserListsRequestCreation(id, search, page, perPage).body(localVarReturnType);
    }

    /**
     * List all feature flag user lists for a project
     * Lists all feature flag user lists for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param search Return user lists matching the search criteria.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesFeatureFlagUserList&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesFeatureFlagUserList>> getApiV4ProjectsIdFeatureFlagsUserListsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFeatureFlagUserList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsUserListsRequestCreation(id, search, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all feature flag user lists for a project
     * Lists all feature flag user lists for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param search Return user lists matching the search criteria.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdFeatureFlagsUserListsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdFeatureFlagsUserListsRequestCreation(id, search, page, perPage);
    }

    /**
     * Retrieve a feature flag user list
     * Retrieves a specified feature flag user list.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @return APIEntitiesFeatureFlagUserList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdFeatureFlagsUserListsIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'iid' is set
        if (iid == null) {
            throw new RestClientResponseException("Missing the required parameter 'iid' when calling getApiV4ProjectsIdFeatureFlagsUserListsIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("iid", iid);

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

        ParameterizedTypeReference<APIEntitiesFeatureFlagUserList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags_user_lists/{iid}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a feature flag user list
     * Retrieves a specified feature flag user list.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @return APIEntitiesFeatureFlagUserList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeatureFlagUserList getApiV4ProjectsIdFeatureFlagsUserListsIid(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagUserList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(id, iid).body(localVarReturnType);
    }

    /**
     * Retrieve a feature flag user list
     * Retrieves a specified feature flag user list.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @return ResponseEntity&lt;APIEntitiesFeatureFlagUserList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeatureFlagUserList> getApiV4ProjectsIdFeatureFlagsUserListsIidWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagUserList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(id, iid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a feature flag user list
     * Retrieves a specified feature flag user list.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdFeatureFlagsUserListsIidWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid) throws RestClientResponseException {
        return getApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(id, iid);
    }

    /**
     * Create a feature flag
     * Creates a feature flag for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFeatureFlagsRequest The postApiV4ProjectsIdFeatureFlagsRequest parameter
     * @return APIEntitiesFeatureFlag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdFeatureFlagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFeatureFlagsRequest postApiV4ProjectsIdFeatureFlagsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdFeatureFlags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdFeatureFlagsRequest' is set
        if (postApiV4ProjectsIdFeatureFlagsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdFeatureFlagsRequest' when calling postApiV4ProjectsIdFeatureFlags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdFeatureFlagsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a feature flag
     * Creates a feature flag for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFeatureFlagsRequest The postApiV4ProjectsIdFeatureFlagsRequest parameter
     * @return APIEntitiesFeatureFlag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeatureFlag postApiV4ProjectsIdFeatureFlags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFeatureFlagsRequest postApiV4ProjectsIdFeatureFlagsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdFeatureFlagsRequestCreation(id, postApiV4ProjectsIdFeatureFlagsRequest).body(localVarReturnType);
    }

    /**
     * Create a feature flag
     * Creates a feature flag for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFeatureFlagsRequest The postApiV4ProjectsIdFeatureFlagsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesFeatureFlag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeatureFlag> postApiV4ProjectsIdFeatureFlagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFeatureFlagsRequest postApiV4ProjectsIdFeatureFlagsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdFeatureFlagsRequestCreation(id, postApiV4ProjectsIdFeatureFlagsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a feature flag
     * Creates a feature flag for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFeatureFlagsRequest The postApiV4ProjectsIdFeatureFlagsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdFeatureFlagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFeatureFlagsRequest postApiV4ProjectsIdFeatureFlagsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdFeatureFlagsRequestCreation(id, postApiV4ProjectsIdFeatureFlagsRequest);
    }

    /**
     * Create a feature flag user list
     * Creates a feature flag user list in a specified project. The bytes of &#x60;user_xids&#x60; written count toward a per-user rate limit (&#x60;feature_flag_user_list_write_bytes_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_feature_flag_user_list_write_bytes&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFeatureFlagsUserListsRequest The postApiV4ProjectsIdFeatureFlagsUserListsRequest parameter
     * @return APIEntitiesFeatureFlagUserList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdFeatureFlagsUserListsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFeatureFlagsUserListsRequest postApiV4ProjectsIdFeatureFlagsUserListsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdFeatureFlagsUserLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdFeatureFlagsUserListsRequest' is set
        if (postApiV4ProjectsIdFeatureFlagsUserListsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdFeatureFlagsUserListsRequest' when calling postApiV4ProjectsIdFeatureFlagsUserLists", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesFeatureFlagUserList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags_user_lists", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdFeatureFlagsUserListsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a feature flag user list
     * Creates a feature flag user list in a specified project. The bytes of &#x60;user_xids&#x60; written count toward a per-user rate limit (&#x60;feature_flag_user_list_write_bytes_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_feature_flag_user_list_write_bytes&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFeatureFlagsUserListsRequest The postApiV4ProjectsIdFeatureFlagsUserListsRequest parameter
     * @return APIEntitiesFeatureFlagUserList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeatureFlagUserList postApiV4ProjectsIdFeatureFlagsUserLists(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFeatureFlagsUserListsRequest postApiV4ProjectsIdFeatureFlagsUserListsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagUserList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdFeatureFlagsUserListsRequestCreation(id, postApiV4ProjectsIdFeatureFlagsUserListsRequest).body(localVarReturnType);
    }

    /**
     * Create a feature flag user list
     * Creates a feature flag user list in a specified project. The bytes of &#x60;user_xids&#x60; written count toward a per-user rate limit (&#x60;feature_flag_user_list_write_bytes_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_feature_flag_user_list_write_bytes&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFeatureFlagsUserListsRequest The postApiV4ProjectsIdFeatureFlagsUserListsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesFeatureFlagUserList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeatureFlagUserList> postApiV4ProjectsIdFeatureFlagsUserListsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFeatureFlagsUserListsRequest postApiV4ProjectsIdFeatureFlagsUserListsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagUserList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdFeatureFlagsUserListsRequestCreation(id, postApiV4ProjectsIdFeatureFlagsUserListsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a feature flag user list
     * Creates a feature flag user list in a specified project. The bytes of &#x60;user_xids&#x60; written count toward a per-user rate limit (&#x60;feature_flag_user_list_write_bytes_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_feature_flag_user_list_write_bytes&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFeatureFlagsUserListsRequest The postApiV4ProjectsIdFeatureFlagsUserListsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdFeatureFlagsUserListsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFeatureFlagsUserListsRequest postApiV4ProjectsIdFeatureFlagsUserListsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdFeatureFlagsUserListsRequestCreation(id, postApiV4ProjectsIdFeatureFlagsUserListsRequest);
    }

    /**
     * Update a feature flag
     * Updates a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @param putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest The putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest parameter
     * @return APIEntitiesFeatureFlag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName, @jakarta.annotation.Nullable PutApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdFeatureFlagsFeatureFlagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'featureFlagName' is set
        if (featureFlagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'featureFlagName' when calling putApiV4ProjectsIdFeatureFlagsFeatureFlagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("feature_flag_name", featureFlagName);

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

        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags/{feature_flag_name}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a feature flag
     * Updates a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @param putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest The putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest parameter
     * @return APIEntitiesFeatureFlag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeatureFlag putApiV4ProjectsIdFeatureFlagsFeatureFlagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName, @jakarta.annotation.Nullable PutApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(id, featureFlagName, putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest).body(localVarReturnType);
    }

    /**
     * Update a feature flag
     * Updates a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @param putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest The putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesFeatureFlag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeatureFlag> putApiV4ProjectsIdFeatureFlagsFeatureFlagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName, @jakarta.annotation.Nullable PutApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(id, featureFlagName, putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a feature flag
     * Updates a specified feature flag.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param featureFlagName Name of the feature flag.
     * @param putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest The putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdFeatureFlagsFeatureFlagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String featureFlagName, @jakarta.annotation.Nullable PutApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequestCreation(id, featureFlagName, putApiV4ProjectsIdFeatureFlagsFeatureFlagNameRequest);
    }

    /**
     * Update feature flag settings
     * Updates the feature flag settings of a specified project. You must have the Maintainer or Owner role for the project. To change &#x60;minimum_role&#x60; from &#x60;owner&#x60; or &#x60;no_one_allowed&#x60;, you must have the Owner role. Enforcement of &#x60;minimum_role&#x60; is behind a feature flag named &#x60;feature_flag_management_permissions&#x60;, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdFeatureFlagsSettingsRequest The putApiV4ProjectsIdFeatureFlagsSettingsRequest parameter
     * @return APIEntitiesFeatureFlagSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdFeatureFlagsSettingsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdFeatureFlagsSettingsRequest putApiV4ProjectsIdFeatureFlagsSettingsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdFeatureFlagsSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdFeatureFlagsSettingsRequest' is set
        if (putApiV4ProjectsIdFeatureFlagsSettingsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdFeatureFlagsSettingsRequest' when calling putApiV4ProjectsIdFeatureFlagsSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesFeatureFlagSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags_settings", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdFeatureFlagsSettingsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update feature flag settings
     * Updates the feature flag settings of a specified project. You must have the Maintainer or Owner role for the project. To change &#x60;minimum_role&#x60; from &#x60;owner&#x60; or &#x60;no_one_allowed&#x60;, you must have the Owner role. Enforcement of &#x60;minimum_role&#x60; is behind a feature flag named &#x60;feature_flag_management_permissions&#x60;, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdFeatureFlagsSettingsRequest The putApiV4ProjectsIdFeatureFlagsSettingsRequest parameter
     * @return APIEntitiesFeatureFlagSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeatureFlagSettings putApiV4ProjectsIdFeatureFlagsSettings(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdFeatureFlagsSettingsRequest putApiV4ProjectsIdFeatureFlagsSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdFeatureFlagsSettingsRequestCreation(id, putApiV4ProjectsIdFeatureFlagsSettingsRequest).body(localVarReturnType);
    }

    /**
     * Update feature flag settings
     * Updates the feature flag settings of a specified project. You must have the Maintainer or Owner role for the project. To change &#x60;minimum_role&#x60; from &#x60;owner&#x60; or &#x60;no_one_allowed&#x60;, you must have the Owner role. Enforcement of &#x60;minimum_role&#x60; is behind a feature flag named &#x60;feature_flag_management_permissions&#x60;, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdFeatureFlagsSettingsRequest The putApiV4ProjectsIdFeatureFlagsSettingsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesFeatureFlagSettings&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeatureFlagSettings> putApiV4ProjectsIdFeatureFlagsSettingsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdFeatureFlagsSettingsRequest putApiV4ProjectsIdFeatureFlagsSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdFeatureFlagsSettingsRequestCreation(id, putApiV4ProjectsIdFeatureFlagsSettingsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update feature flag settings
     * Updates the feature flag settings of a specified project. You must have the Maintainer or Owner role for the project. To change &#x60;minimum_role&#x60; from &#x60;owner&#x60; or &#x60;no_one_allowed&#x60;, you must have the Owner role. Enforcement of &#x60;minimum_role&#x60; is behind a feature flag named &#x60;feature_flag_management_permissions&#x60;, disabled by default. This feature was introduced in GitLab 19.4.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdFeatureFlagsSettingsRequest The putApiV4ProjectsIdFeatureFlagsSettingsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdFeatureFlagsSettingsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdFeatureFlagsSettingsRequest putApiV4ProjectsIdFeatureFlagsSettingsRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdFeatureFlagsSettingsRequestCreation(id, putApiV4ProjectsIdFeatureFlagsSettingsRequest);
    }

    /**
     * Update a feature flag user list
     * Updates a specified feature flag user list. The bytes of &#x60;user_xids&#x60; written count toward a per-user rate limit (&#x60;feature_flag_user_list_write_bytes_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_feature_flag_user_list_write_bytes&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @param putApiV4ProjectsIdFeatureFlagsUserListsIidRequest The putApiV4ProjectsIdFeatureFlagsUserListsIidRequest parameter
     * @return APIEntitiesFeatureFlagUserList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid, @jakarta.annotation.Nullable PutApiV4ProjectsIdFeatureFlagsUserListsIidRequest putApiV4ProjectsIdFeatureFlagsUserListsIidRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdFeatureFlagsUserListsIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'iid' is set
        if (iid == null) {
            throw new RestClientResponseException("Missing the required parameter 'iid' when calling putApiV4ProjectsIdFeatureFlagsUserListsIid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("iid", iid);

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

        ParameterizedTypeReference<APIEntitiesFeatureFlagUserList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/feature_flags_user_lists/{iid}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdFeatureFlagsUserListsIidRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a feature flag user list
     * Updates a specified feature flag user list. The bytes of &#x60;user_xids&#x60; written count toward a per-user rate limit (&#x60;feature_flag_user_list_write_bytes_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_feature_flag_user_list_write_bytes&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @param putApiV4ProjectsIdFeatureFlagsUserListsIidRequest The putApiV4ProjectsIdFeatureFlagsUserListsIidRequest parameter
     * @return APIEntitiesFeatureFlagUserList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeatureFlagUserList putApiV4ProjectsIdFeatureFlagsUserListsIid(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid, @jakarta.annotation.Nullable PutApiV4ProjectsIdFeatureFlagsUserListsIidRequest putApiV4ProjectsIdFeatureFlagsUserListsIidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagUserList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(id, iid, putApiV4ProjectsIdFeatureFlagsUserListsIidRequest).body(localVarReturnType);
    }

    /**
     * Update a feature flag user list
     * Updates a specified feature flag user list. The bytes of &#x60;user_xids&#x60; written count toward a per-user rate limit (&#x60;feature_flag_user_list_write_bytes_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_feature_flag_user_list_write_bytes&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @param putApiV4ProjectsIdFeatureFlagsUserListsIidRequest The putApiV4ProjectsIdFeatureFlagsUserListsIidRequest parameter
     * @return ResponseEntity&lt;APIEntitiesFeatureFlagUserList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeatureFlagUserList> putApiV4ProjectsIdFeatureFlagsUserListsIidWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid, @jakarta.annotation.Nullable PutApiV4ProjectsIdFeatureFlagsUserListsIidRequest putApiV4ProjectsIdFeatureFlagsUserListsIidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeatureFlagUserList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(id, iid, putApiV4ProjectsIdFeatureFlagsUserListsIidRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a feature flag user list
     * Updates a specified feature flag user list. The bytes of &#x60;user_xids&#x60; written count toward a per-user rate limit (&#x60;feature_flag_user_list_write_bytes_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_feature_flag_user_list_write_bytes&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param iid Internal ID of the project&#39;s feature flag user list.
     * @param putApiV4ProjectsIdFeatureFlagsUserListsIidRequest The putApiV4ProjectsIdFeatureFlagsUserListsIidRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdFeatureFlagsUserListsIidWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter iid, @jakarta.annotation.Nullable PutApiV4ProjectsIdFeatureFlagsUserListsIidRequest putApiV4ProjectsIdFeatureFlagsUserListsIidRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdFeatureFlagsUserListsIidRequestCreation(id, iid, putApiV4ProjectsIdFeatureFlagsUserListsIidRequest);
    }
}
