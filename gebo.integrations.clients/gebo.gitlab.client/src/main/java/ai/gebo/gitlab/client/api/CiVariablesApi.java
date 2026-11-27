package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiVariable;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4AdminCiVariablesRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdVariablesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdVariablesRequest;
import ai.gebo.gitlab.client.model.PutApiV4AdminCiVariablesKeyRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdVariablesKeyRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdVariablesKeyRequest;

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
public class CiVariablesApi {
    private ApiClient apiClient;

    public CiVariablesApi() {
        this(new ApiClient());
    }

    public CiVariablesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete instance variable
     * Deletes a specified instance variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4AdminCiVariablesKeyRequestCreation(@jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4AdminCiVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/ci/variables/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete instance variable
     * Deletes a specified instance variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable deleteApiV4AdminCiVariablesKey(@jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminCiVariablesKeyRequestCreation(key).body(localVarReturnType);
    }

    /**
     * Delete instance variable
     * Deletes a specified instance variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> deleteApiV4AdminCiVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminCiVariablesKeyRequestCreation(key).toEntity(localVarReturnType);
    }

    /**
     * Delete instance variable
     * Deletes a specified instance variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4AdminCiVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return deleteApiV4AdminCiVariablesKeyRequestCreation(key);
    }

    /**
     * Delete a group variable
     * Deletes a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Group Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdVariablesKeyRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4GroupsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/variables/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group variable
     * Deletes a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Group Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable deleteApiV4GroupsIdVariablesKey(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdVariablesKeyRequestCreation(id, key).body(localVarReturnType);
    }

    /**
     * Delete a group variable
     * Deletes a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Group Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> deleteApiV4GroupsIdVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdVariablesKeyRequestCreation(id, key).toEntity(localVarReturnType);
    }

    /**
     * Delete a group variable
     * Deletes a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Group Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return deleteApiV4GroupsIdVariablesKeyRequestCreation(id, key);
    }

    /**
     * Delete a variable
     * Deletes a project variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param filter Filter results when multiple variables share the same key. Supports &#x60;filter[environment_scope]&#x60;.
     * @param filterEnvironmentScope Environment scope of the variable.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdVariablesKeyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Object filter, @jakarta.annotation.Nullable String filterEnvironmentScope) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4ProjectsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key", key);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter[environment_scope]", filterEnvironmentScope));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/variables/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a variable
     * Deletes a project variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param filter Filter results when multiple variables share the same key. Supports &#x60;filter[environment_scope]&#x60;.
     * @param filterEnvironmentScope Environment scope of the variable.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable deleteApiV4ProjectsIdVariablesKey(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Object filter, @jakarta.annotation.Nullable String filterEnvironmentScope) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdVariablesKeyRequestCreation(id, key, filter, filterEnvironmentScope).body(localVarReturnType);
    }

    /**
     * Delete a variable
     * Deletes a project variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param filter Filter results when multiple variables share the same key. Supports &#x60;filter[environment_scope]&#x60;.
     * @param filterEnvironmentScope Environment scope of the variable.
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> deleteApiV4ProjectsIdVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Object filter, @jakarta.annotation.Nullable String filterEnvironmentScope) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdVariablesKeyRequestCreation(id, key, filter, filterEnvironmentScope).toEntity(localVarReturnType);
    }

    /**
     * Delete a variable
     * Deletes a project variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param filter Filter results when multiple variables share the same key. Supports &#x60;filter[environment_scope]&#x60;.
     * @param filterEnvironmentScope Environment scope of the variable.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Object filter, @jakarta.annotation.Nullable String filterEnvironmentScope) throws RestClientResponseException {
        return deleteApiV4ProjectsIdVariablesKeyRequestCreation(id, key, filter, filterEnvironmentScope);
    }

    /**
     * List all instance variables
     * Lists all instance-level variables. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminCiVariablesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/ci/variables", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all instance variables
     * Lists all instance-level variables. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiVariable> getApiV4AdminCiVariables(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminCiVariablesRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all instance variables
     * Lists all instance-level variables. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiVariable&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiVariable>> getApiV4AdminCiVariablesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminCiVariablesRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all instance variables
     * Lists all instance-level variables. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminCiVariablesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4AdminCiVariablesRequestCreation(page, perPage);
    }

    /**
     * Retrieve instance variable details
     * Retrieves details of a specified instance-level variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminCiVariablesKeyRequestCreation(@jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4AdminCiVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/ci/variables/{key}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve instance variable details
     * Retrieves details of a specified instance-level variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable getApiV4AdminCiVariablesKey(@jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminCiVariablesKeyRequestCreation(key).body(localVarReturnType);
    }

    /**
     * Retrieve instance variable details
     * Retrieves details of a specified instance-level variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> getApiV4AdminCiVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminCiVariablesKeyRequestCreation(key).toEntity(localVarReturnType);
    }

    /**
     * Retrieve instance variable details
     * Retrieves details of a specified instance-level variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminCiVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return getApiV4AdminCiVariablesKeyRequestCreation(key);
    }

    /**
     * List all group variables
     * Lists all variables for a specified group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdVariablesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/variables", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group variables
     * Lists all variables for a specified group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiVariable> getApiV4GroupsIdVariables(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdVariablesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group variables
     * Lists all variables for a specified group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiVariable&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiVariable>> getApiV4GroupsIdVariablesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdVariablesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group variables
     * Lists all variables for a specified group. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdVariablesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdVariablesRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve details of a group variable
     * Retrieves details of a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Group Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdVariablesKeyRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4GroupsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/variables/{key}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve details of a group variable
     * Retrieves details of a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Group Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable getApiV4GroupsIdVariablesKey(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdVariablesKeyRequestCreation(id, key).body(localVarReturnType);
    }

    /**
     * Retrieve details of a group variable
     * Retrieves details of a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Group Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> getApiV4GroupsIdVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdVariablesKeyRequestCreation(id, key).toEntity(localVarReturnType);
    }

    /**
     * Retrieve details of a group variable
     * Retrieves details of a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Group Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return getApiV4GroupsIdVariablesKeyRequestCreation(id, key);
    }

    /**
     * List all project variables
     * Lists all variables for a project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdVariablesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/variables", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project variables
     * Lists all variables for a project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiVariable> getApiV4ProjectsIdVariables(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVariablesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project variables
     * Lists all variables for a project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiVariable&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiVariable>> getApiV4ProjectsIdVariablesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVariablesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project variables
     * Lists all variables for a project. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdVariablesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdVariablesRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a single variable
     * Retrieves details of a specified variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param filter Filter results when multiple variables share the same key. Supports &#x60;filter[environment_scope]&#x60;.
     * @param filterEnvironmentScope Environment scope of the variable.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdVariablesKeyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Object filter, @jakarta.annotation.Nullable String filterEnvironmentScope) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4ProjectsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key", key);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter[environment_scope]", filterEnvironmentScope));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/variables/{key}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a single variable
     * Retrieves details of a specified variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param filter Filter results when multiple variables share the same key. Supports &#x60;filter[environment_scope]&#x60;.
     * @param filterEnvironmentScope Environment scope of the variable.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable getApiV4ProjectsIdVariablesKey(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Object filter, @jakarta.annotation.Nullable String filterEnvironmentScope) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVariablesKeyRequestCreation(id, key, filter, filterEnvironmentScope).body(localVarReturnType);
    }

    /**
     * Retrieve a single variable
     * Retrieves details of a specified variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param filter Filter results when multiple variables share the same key. Supports &#x60;filter[environment_scope]&#x60;.
     * @param filterEnvironmentScope Environment scope of the variable.
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> getApiV4ProjectsIdVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Object filter, @jakarta.annotation.Nullable String filterEnvironmentScope) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVariablesKeyRequestCreation(id, key, filter, filterEnvironmentScope).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a single variable
     * Retrieves details of a specified variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param filter Filter results when multiple variables share the same key. Supports &#x60;filter[environment_scope]&#x60;.
     * @param filterEnvironmentScope Environment scope of the variable.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Object filter, @jakarta.annotation.Nullable String filterEnvironmentScope) throws RestClientResponseException {
        return getApiV4ProjectsIdVariablesKeyRequestCreation(id, key, filter, filterEnvironmentScope);
    }

    /**
     * Create instance variable
     * Creates a instance-level variable. The maximum number of instance-level variables can be changed.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * @param postApiV4AdminCiVariablesRequest The postApiV4AdminCiVariablesRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AdminCiVariablesRequestCreation(@jakarta.annotation.Nonnull PostApiV4AdminCiVariablesRequest postApiV4AdminCiVariablesRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4AdminCiVariablesRequest' is set
        if (postApiV4AdminCiVariablesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AdminCiVariablesRequest' when calling postApiV4AdminCiVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/ci/variables", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AdminCiVariablesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create instance variable
     * Creates a instance-level variable. The maximum number of instance-level variables can be changed.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * @param postApiV4AdminCiVariablesRequest The postApiV4AdminCiVariablesRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable postApiV4AdminCiVariables(@jakarta.annotation.Nonnull PostApiV4AdminCiVariablesRequest postApiV4AdminCiVariablesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AdminCiVariablesRequestCreation(postApiV4AdminCiVariablesRequest).body(localVarReturnType);
    }

    /**
     * Create instance variable
     * Creates a instance-level variable. The maximum number of instance-level variables can be changed.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * @param postApiV4AdminCiVariablesRequest The postApiV4AdminCiVariablesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> postApiV4AdminCiVariablesWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4AdminCiVariablesRequest postApiV4AdminCiVariablesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AdminCiVariablesRequestCreation(postApiV4AdminCiVariablesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create instance variable
     * Creates a instance-level variable. The maximum number of instance-level variables can be changed.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * @param postApiV4AdminCiVariablesRequest The postApiV4AdminCiVariablesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AdminCiVariablesWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4AdminCiVariablesRequest postApiV4AdminCiVariablesRequest) throws RestClientResponseException {
        return postApiV4AdminCiVariablesRequestCreation(postApiV4AdminCiVariablesRequest);
    }

    /**
     * Create a group variable
     * Creates a group variable.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdVariablesRequest The postApiV4GroupsIdVariablesRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdVariablesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdVariablesRequest postApiV4GroupsIdVariablesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdVariablesRequest' is set
        if (postApiV4GroupsIdVariablesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdVariablesRequest' when calling postApiV4GroupsIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/variables", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdVariablesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group variable
     * Creates a group variable.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdVariablesRequest The postApiV4GroupsIdVariablesRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable postApiV4GroupsIdVariables(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdVariablesRequest postApiV4GroupsIdVariablesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdVariablesRequestCreation(id, postApiV4GroupsIdVariablesRequest).body(localVarReturnType);
    }

    /**
     * Create a group variable
     * Creates a group variable.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdVariablesRequest The postApiV4GroupsIdVariablesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> postApiV4GroupsIdVariablesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdVariablesRequest postApiV4GroupsIdVariablesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdVariablesRequestCreation(id, postApiV4GroupsIdVariablesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group variable
     * Creates a group variable.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdVariablesRequest The postApiV4GroupsIdVariablesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdVariablesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdVariablesRequest postApiV4GroupsIdVariablesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdVariablesRequestCreation(id, postApiV4GroupsIdVariablesRequest);
    }

    /**
     * Create a variable
     * Creates a variable. If a variable with the same &#x60;key&#x60; already exists, the variable must have a different &#x60;environment_scope&#x60;. Otherwise, GitLab returns a message similar to: &#x60;VARIABLE_NAME has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdVariablesRequest The postApiV4ProjectsIdVariablesRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdVariablesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVariablesRequest postApiV4ProjectsIdVariablesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdVariablesRequest' is set
        if (postApiV4ProjectsIdVariablesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdVariablesRequest' when calling postApiV4ProjectsIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/variables", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdVariablesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a variable
     * Creates a variable. If a variable with the same &#x60;key&#x60; already exists, the variable must have a different &#x60;environment_scope&#x60;. Otherwise, GitLab returns a message similar to: &#x60;VARIABLE_NAME has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdVariablesRequest The postApiV4ProjectsIdVariablesRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable postApiV4ProjectsIdVariables(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVariablesRequest postApiV4ProjectsIdVariablesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdVariablesRequestCreation(id, postApiV4ProjectsIdVariablesRequest).body(localVarReturnType);
    }

    /**
     * Create a variable
     * Creates a variable. If a variable with the same &#x60;key&#x60; already exists, the variable must have a different &#x60;environment_scope&#x60;. Otherwise, GitLab returns a message similar to: &#x60;VARIABLE_NAME has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdVariablesRequest The postApiV4ProjectsIdVariablesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> postApiV4ProjectsIdVariablesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVariablesRequest postApiV4ProjectsIdVariablesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdVariablesRequestCreation(id, postApiV4ProjectsIdVariablesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a variable
     * Creates a variable. If a variable with the same &#x60;key&#x60; already exists, the variable must have a different &#x60;environment_scope&#x60;. Otherwise, GitLab returns a message similar to: &#x60;VARIABLE_NAME has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdVariablesRequest The postApiV4ProjectsIdVariablesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdVariablesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVariablesRequest postApiV4ProjectsIdVariablesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdVariablesRequestCreation(id, postApiV4ProjectsIdVariablesRequest);
    }

    /**
     * Update an instance variable
     * Updates a specified instance variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4AdminCiVariablesKeyRequest The putApiV4AdminCiVariablesKeyRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminCiVariablesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4AdminCiVariablesKeyRequest putApiV4AdminCiVariablesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4AdminCiVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/ci/variables/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminCiVariablesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an instance variable
     * Updates a specified instance variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4AdminCiVariablesKeyRequest The putApiV4AdminCiVariablesKeyRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable putApiV4AdminCiVariablesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4AdminCiVariablesKeyRequest putApiV4AdminCiVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminCiVariablesKeyRequestCreation(key, putApiV4AdminCiVariablesKeyRequest).body(localVarReturnType);
    }

    /**
     * Update an instance variable
     * Updates a specified instance variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4AdminCiVariablesKeyRequest The putApiV4AdminCiVariablesKeyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> putApiV4AdminCiVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4AdminCiVariablesKeyRequest putApiV4AdminCiVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminCiVariablesKeyRequestCreation(key, putApiV4AdminCiVariablesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an instance variable
     * Updates a specified instance variable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Instance Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the instance variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4AdminCiVariablesKeyRequest The putApiV4AdminCiVariablesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminCiVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4AdminCiVariablesKeyRequest putApiV4AdminCiVariablesKeyRequest) throws RestClientResponseException {
        return putApiV4AdminCiVariablesKeyRequestCreation(key, putApiV4AdminCiVariablesKeyRequest);
    }

    /**
     * Update a group variable
     * Updates a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;. If the &#x60;environment_scope&#x60; in &#x60;filter&#x60; does not exist, the endpoint updates a variable with the same key but a different environment scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Group Variable Not Found
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4GroupsIdVariablesKeyRequest The putApiV4GroupsIdVariablesKeyRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdVariablesKeyRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4GroupsIdVariablesKeyRequest putApiV4GroupsIdVariablesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4GroupsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/variables/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdVariablesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group variable
     * Updates a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;. If the &#x60;environment_scope&#x60; in &#x60;filter&#x60; does not exist, the endpoint updates a variable with the same key but a different environment scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Group Variable Not Found
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4GroupsIdVariablesKeyRequest The putApiV4GroupsIdVariablesKeyRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable putApiV4GroupsIdVariablesKey(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4GroupsIdVariablesKeyRequest putApiV4GroupsIdVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdVariablesKeyRequestCreation(id, key, putApiV4GroupsIdVariablesKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a group variable
     * Updates a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;. If the &#x60;environment_scope&#x60; in &#x60;filter&#x60; does not exist, the endpoint updates a variable with the same key but a different environment scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Group Variable Not Found
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4GroupsIdVariablesKeyRequest The putApiV4GroupsIdVariablesKeyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> putApiV4GroupsIdVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4GroupsIdVariablesKeyRequest putApiV4GroupsIdVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdVariablesKeyRequestCreation(id, key, putApiV4GroupsIdVariablesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group variable
     * Updates a specified group variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;. If the &#x60;environment_scope&#x60; in &#x60;filter&#x60; does not exist, the endpoint updates a variable with the same key but a different environment scope.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>404</b> - Group Variable Not Found
     * @param id ID or URL-encoded path of the group.
     * @param key Key of the group variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4GroupsIdVariablesKeyRequest The putApiV4GroupsIdVariablesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4GroupsIdVariablesKeyRequest putApiV4GroupsIdVariablesKeyRequest) throws RestClientResponseException {
        return putApiV4GroupsIdVariablesKeyRequestCreation(id, key, putApiV4GroupsIdVariablesKeyRequest);
    }

    /**
     * Update a variable
     * Updates a project variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4ProjectsIdVariablesKeyRequest The putApiV4ProjectsIdVariablesKeyRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdVariablesKeyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdVariablesKeyRequest putApiV4ProjectsIdVariablesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4ProjectsIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/variables/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdVariablesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a variable
     * Updates a project variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4ProjectsIdVariablesKeyRequest The putApiV4ProjectsIdVariablesKeyRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable putApiV4ProjectsIdVariablesKey(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdVariablesKeyRequest putApiV4ProjectsIdVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdVariablesKeyRequestCreation(id, key, putApiV4ProjectsIdVariablesKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a variable
     * Updates a project variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4ProjectsIdVariablesKeyRequest The putApiV4ProjectsIdVariablesKeyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> putApiV4ProjectsIdVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdVariablesKeyRequest putApiV4ProjectsIdVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdVariablesKeyRequestCreation(id, key, putApiV4ProjectsIdVariablesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a variable
     * Updates a project variable. If there are multiple variables with the same key, use &#x60;filter&#x60; to select the correct &#x60;environment_scope&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Variable Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key Key of the project variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4ProjectsIdVariablesKeyRequest The putApiV4ProjectsIdVariablesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdVariablesKeyRequest putApiV4ProjectsIdVariablesKeyRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdVariablesKeyRequestCreation(id, key, putApiV4ProjectsIdVariablesKeyRequest);
    }
}
