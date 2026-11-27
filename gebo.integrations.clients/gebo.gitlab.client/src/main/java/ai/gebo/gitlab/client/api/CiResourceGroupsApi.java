package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiJobBasic;
import ai.gebo.gitlab.client.model.APIEntitiesCiResourceGroup;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdResourceGroupsKeyRequest;

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
public class CiResourceGroupsApi {
    private ApiClient apiClient;

    public CiResourceGroupsApi() {
        this(new ApiClient());
    }

    public CiResourceGroupsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all resource groups
     * Lists all resource groups for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiResourceGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdResourceGroupsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdResourceGroups", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCiResourceGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/resource_groups", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all resource groups
     * Lists all resource groups for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiResourceGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiResourceGroup> getApiV4ProjectsIdResourceGroups(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiResourceGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdResourceGroupsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all resource groups
     * Lists all resource groups for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiResourceGroup&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiResourceGroup>> getApiV4ProjectsIdResourceGroupsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiResourceGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdResourceGroupsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all resource groups
     * Lists all resource groups for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdResourceGroupsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdResourceGroupsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a resource group
     * Retrieves a specified resource group for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @return APIEntitiesCiResourceGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdResourceGroupsKeyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdResourceGroupsKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4ProjectsIdResourceGroupsKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiResourceGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/resource_groups/{key}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a resource group
     * Retrieves a specified resource group for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @return APIEntitiesCiResourceGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiResourceGroup getApiV4ProjectsIdResourceGroupsKey(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResourceGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdResourceGroupsKeyRequestCreation(id, key).body(localVarReturnType);
    }

    /**
     * Retrieve a resource group
     * Retrieves a specified resource group for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @return ResponseEntity&lt;APIEntitiesCiResourceGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiResourceGroup> getApiV4ProjectsIdResourceGroupsKeyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResourceGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdResourceGroupsKeyRequestCreation(id, key).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a resource group
     * Retrieves a specified resource group for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdResourceGroupsKeyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return getApiV4ProjectsIdResourceGroupsKeyRequestCreation(id, key);
    }

    /**
     * Retrieve current job for a resource group
     * Retrieves the current job for a specified resource group in a project. This feature was introduced in GitLab 18.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @return APIEntitiesCiJobBasic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdResourceGroupsKeyCurrentJobRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdResourceGroupsKeyCurrentJob", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4ProjectsIdResourceGroupsKeyCurrentJob", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiJobBasic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/resource_groups/{key}/current_job", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve current job for a resource group
     * Retrieves the current job for a specified resource group in a project. This feature was introduced in GitLab 18.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @return APIEntitiesCiJobBasic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiJobBasic getApiV4ProjectsIdResourceGroupsKeyCurrentJob(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiJobBasic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdResourceGroupsKeyCurrentJobRequestCreation(id, key).body(localVarReturnType);
    }

    /**
     * Retrieve current job for a resource group
     * Retrieves the current job for a specified resource group in a project. This feature was introduced in GitLab 18.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @return ResponseEntity&lt;APIEntitiesCiJobBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiJobBasic> getApiV4ProjectsIdResourceGroupsKeyCurrentJobWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiJobBasic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdResourceGroupsKeyCurrentJobRequestCreation(id, key).toEntity(localVarReturnType);
    }

    /**
     * Retrieve current job for a resource group
     * Retrieves the current job for a specified resource group in a project. This feature was introduced in GitLab 18.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdResourceGroupsKeyCurrentJobWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return getApiV4ProjectsIdResourceGroupsKeyCurrentJobRequestCreation(id, key);
    }

    /**
     * List all upcoming jobs for a resource group
     * Lists all upcoming jobs for a specified resource group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJobBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdResourceGroupsKeyUpcomingJobsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdResourceGroupsKeyUpcomingJobs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4ProjectsIdResourceGroupsKeyUpcomingJobs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key", key);

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

        ParameterizedTypeReference<List<APIEntitiesCiJobBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/resource_groups/{key}/upcoming_jobs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all upcoming jobs for a resource group
     * Lists all upcoming jobs for a specified resource group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJobBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiJobBasic> getApiV4ProjectsIdResourceGroupsKeyUpcomingJobs(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJobBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdResourceGroupsKeyUpcomingJobsRequestCreation(id, key, page, perPage).body(localVarReturnType);
    }

    /**
     * List all upcoming jobs for a resource group
     * Lists all upcoming jobs for a specified resource group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiJobBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiJobBasic>> getApiV4ProjectsIdResourceGroupsKeyUpcomingJobsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJobBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdResourceGroupsKeyUpcomingJobsRequestCreation(id, key, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all upcoming jobs for a resource group
     * Lists all upcoming jobs for a specified resource group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdResourceGroupsKeyUpcomingJobsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdResourceGroupsKeyUpcomingJobsRequestCreation(id, key, page, perPage);
    }

    /**
     * Update a resource group
     * Updates the properties for a specified resource group. It returns &#x60;200&#x60; if the resource group was successfully updated. In case of an error, a status code &#x60;400&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @param putApiV4ProjectsIdResourceGroupsKeyRequest The putApiV4ProjectsIdResourceGroupsKeyRequest parameter
     * @return APIEntitiesCiResourceGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdResourceGroupsKeyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdResourceGroupsKeyRequest putApiV4ProjectsIdResourceGroupsKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdResourceGroupsKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4ProjectsIdResourceGroupsKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiResourceGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/resource_groups/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdResourceGroupsKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a resource group
     * Updates the properties for a specified resource group. It returns &#x60;200&#x60; if the resource group was successfully updated. In case of an error, a status code &#x60;400&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @param putApiV4ProjectsIdResourceGroupsKeyRequest The putApiV4ProjectsIdResourceGroupsKeyRequest parameter
     * @return APIEntitiesCiResourceGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiResourceGroup putApiV4ProjectsIdResourceGroupsKey(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdResourceGroupsKeyRequest putApiV4ProjectsIdResourceGroupsKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResourceGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdResourceGroupsKeyRequestCreation(id, key, putApiV4ProjectsIdResourceGroupsKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a resource group
     * Updates the properties for a specified resource group. It returns &#x60;200&#x60; if the resource group was successfully updated. In case of an error, a status code &#x60;400&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @param putApiV4ProjectsIdResourceGroupsKeyRequest The putApiV4ProjectsIdResourceGroupsKeyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiResourceGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiResourceGroup> putApiV4ProjectsIdResourceGroupsKeyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdResourceGroupsKeyRequest putApiV4ProjectsIdResourceGroupsKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResourceGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdResourceGroupsKeyRequestCreation(id, key, putApiV4ProjectsIdResourceGroupsKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a resource group
     * Updates the properties for a specified resource group. It returns &#x60;200&#x60; if the resource group was successfully updated. In case of an error, a status code &#x60;400&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param key URL-encoded key of the resource group, for example &#x60;resource%5Fa&#x60; instead of &#x60;resource_a&#x60;.
     * @param putApiV4ProjectsIdResourceGroupsKeyRequest The putApiV4ProjectsIdResourceGroupsKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdResourceGroupsKeyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdResourceGroupsKeyRequest putApiV4ProjectsIdResourceGroupsKeyRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdResourceGroupsKeyRequestCreation(id, key, putApiV4ProjectsIdResourceGroupsKeyRequest);
    }
}
