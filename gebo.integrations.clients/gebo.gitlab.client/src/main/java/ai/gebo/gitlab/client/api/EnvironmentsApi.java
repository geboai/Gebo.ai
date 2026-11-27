package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesEnvironment;
import ai.gebo.gitlab.client.model.APIEntitiesEnvironmentBasic;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdEnvironmentsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdEnvironmentsStopStaleRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdEnvironmentsEnvironmentIdRequest;

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
public class EnvironmentsApi {
    private ApiClient apiClient;

    public EnvironmentsApi() {
        this(new ApiClient());
    }

    public EnvironmentsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an environment
     * Deletes an environment from a project. The environment must be stopped first. When the &#x60;environment_delete_in_batches&#x60; feature flag is enabled, a large environment is deleted over several requests: each request returns &#x60;409&#x60; until the environment is gone, and the rows deleted count toward a per-user rate limit (&#x60;environment_delete_rows_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_environment_delete_rows&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden. The user cannot delete the environment, or the environment is not stopped
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdEnvironmentsEnvironmentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'environmentId' is set
        if (environmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'environmentId' when calling deleteApiV4ProjectsIdEnvironmentsEnvironmentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("environment_id", environmentId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/environments/{environment_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an environment
     * Deletes an environment from a project. The environment must be stopped first. When the &#x60;environment_delete_in_batches&#x60; feature flag is enabled, a large environment is deleted over several requests: each request returns &#x60;409&#x60; until the environment is gone, and the rows deleted count toward a per-user rate limit (&#x60;environment_delete_rows_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_environment_delete_rows&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden. The user cannot delete the environment, or the environment is not stopped
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdEnvironmentsEnvironmentId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(id, environmentId).body(localVarReturnType);
    }

    /**
     * Delete an environment
     * Deletes an environment from a project. The environment must be stopped first. When the &#x60;environment_delete_in_batches&#x60; feature flag is enabled, a large environment is deleted over several requests: each request returns &#x60;409&#x60; until the environment is gone, and the rows deleted count toward a per-user rate limit (&#x60;environment_delete_rows_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_environment_delete_rows&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden. The user cannot delete the environment, or the environment is not stopped
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdEnvironmentsEnvironmentIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(id, environmentId).toEntity(localVarReturnType);
    }

    /**
     * Delete an environment
     * Deletes an environment from a project. The environment must be stopped first. When the &#x60;environment_delete_in_batches&#x60; feature flag is enabled, a large environment is deleted over several requests: each request returns &#x60;409&#x60; until the environment is gone, and the rows deleted count toward a per-user rate limit (&#x60;environment_delete_rows_limit_per_user&#x60; application setting). Until the &#x60;rate_limit_environment_delete_rows&#x60; feature flag is enabled, requests over this limit are logged but not blocked.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden. The user cannot delete the environment, or the environment is not stopped
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdEnvironmentsEnvironmentIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(id, environmentId);
    }

    /**
     * Schedule multiple stopped review apps for deletion
     * Schedules multiple stopped review apps for deletion. Only stopped environments in the review app folder are scheduled. The deletion is performed after 1 week. By default, only environments 30 days or older are deleted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * @param id ID or URL-encoded path of the project.
     * @param before Date before which environments can be deleted. Defaults to 30 days ago.
     * @param limit Maximum number of environments to delete.
     * @param dryRun If &#x60;true&#x60;, performs a dry run where no deletion is actually performed. If &#x60;false&#x60;, deletes the environment.
     * @return APIEntitiesEnvironmentBasic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdEnvironmentsReviewAppsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime before, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Boolean dryRun) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdEnvironmentsReviewApps", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "before", before));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "dry_run", dryRun));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesEnvironmentBasic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/environments/review_apps", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Schedule multiple stopped review apps for deletion
     * Schedules multiple stopped review apps for deletion. Only stopped environments in the review app folder are scheduled. The deletion is performed after 1 week. By default, only environments 30 days or older are deleted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * @param id ID or URL-encoded path of the project.
     * @param before Date before which environments can be deleted. Defaults to 30 days ago.
     * @param limit Maximum number of environments to delete.
     * @param dryRun If &#x60;true&#x60;, performs a dry run where no deletion is actually performed. If &#x60;false&#x60;, deletes the environment.
     * @return APIEntitiesEnvironmentBasic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEnvironmentBasic deleteApiV4ProjectsIdEnvironmentsReviewApps(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime before, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Boolean dryRun) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironmentBasic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdEnvironmentsReviewAppsRequestCreation(id, before, limit, dryRun).body(localVarReturnType);
    }

    /**
     * Schedule multiple stopped review apps for deletion
     * Schedules multiple stopped review apps for deletion. Only stopped environments in the review app folder are scheduled. The deletion is performed after 1 week. By default, only environments 30 days or older are deleted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * @param id ID or URL-encoded path of the project.
     * @param before Date before which environments can be deleted. Defaults to 30 days ago.
     * @param limit Maximum number of environments to delete.
     * @param dryRun If &#x60;true&#x60;, performs a dry run where no deletion is actually performed. If &#x60;false&#x60;, deletes the environment.
     * @return ResponseEntity&lt;APIEntitiesEnvironmentBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEnvironmentBasic> deleteApiV4ProjectsIdEnvironmentsReviewAppsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime before, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Boolean dryRun) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironmentBasic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdEnvironmentsReviewAppsRequestCreation(id, before, limit, dryRun).toEntity(localVarReturnType);
    }

    /**
     * Schedule multiple stopped review apps for deletion
     * Schedules multiple stopped review apps for deletion. Only stopped environments in the review app folder are scheduled. The deletion is performed after 1 week. By default, only environments 30 days or older are deleted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * @param id ID or URL-encoded path of the project.
     * @param before Date before which environments can be deleted. Defaults to 30 days ago.
     * @param limit Maximum number of environments to delete.
     * @param dryRun If &#x60;true&#x60;, performs a dry run where no deletion is actually performed. If &#x60;false&#x60;, deletes the environment.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdEnvironmentsReviewAppsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable OffsetDateTime before, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Boolean dryRun) throws RestClientResponseException {
        return deleteApiV4ProjectsIdEnvironmentsReviewAppsRequestCreation(id, before, limit, dryRun);
    }

    /**
     * List all environments
     * Lists all environments for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Return the environment with this name. Mutually exclusive with &#x60;search&#x60;.
     * @param search Return list of environments matching the search criteria. Must be at least 3 characters. Mutually exclusive with &#x60;name&#x60;.
     * @param states Filter environments by state. If omitted, returns all environments.
     * @return List&lt;APIEntitiesEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdEnvironmentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String states) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdEnvironments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "states", states));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesEnvironment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/environments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all environments
     * Lists all environments for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Return the environment with this name. Mutually exclusive with &#x60;search&#x60;.
     * @param search Return list of environments matching the search criteria. Must be at least 3 characters. Mutually exclusive with &#x60;name&#x60;.
     * @param states Filter environments by state. If omitted, returns all environments.
     * @return List&lt;APIEntitiesEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEnvironment> getApiV4ProjectsIdEnvironments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String states) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEnvironment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdEnvironmentsRequestCreation(id, page, perPage, name, search, states).body(localVarReturnType);
    }

    /**
     * List all environments
     * Lists all environments for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Return the environment with this name. Mutually exclusive with &#x60;search&#x60;.
     * @param search Return list of environments matching the search criteria. Must be at least 3 characters. Mutually exclusive with &#x60;name&#x60;.
     * @param states Filter environments by state. If omitted, returns all environments.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEnvironment&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEnvironment>> getApiV4ProjectsIdEnvironmentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String states) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEnvironment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdEnvironmentsRequestCreation(id, page, perPage, name, search, states).toEntity(localVarReturnType);
    }

    /**
     * List all environments
     * Lists all environments for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param name Return the environment with this name. Mutually exclusive with &#x60;search&#x60;.
     * @param search Return list of environments matching the search criteria. Must be at least 3 characters. Mutually exclusive with &#x60;name&#x60;.
     * @param states Filter environments by state. If omitted, returns all environments.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdEnvironmentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String states) throws RestClientResponseException {
        return getApiV4ProjectsIdEnvironmentsRequestCreation(id, page, perPage, name, search, states);
    }

    /**
     * Retrieve an environment
     * Retrieves a specified environment for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @return APIEntitiesEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdEnvironmentsEnvironmentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'environmentId' is set
        if (environmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'environmentId' when calling getApiV4ProjectsIdEnvironmentsEnvironmentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("environment_id", environmentId);

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

        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/environments/{environment_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an environment
     * Retrieves a specified environment for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @return APIEntitiesEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEnvironment getApiV4ProjectsIdEnvironmentsEnvironmentId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(id, environmentId).body(localVarReturnType);
    }

    /**
     * Retrieve an environment
     * Retrieves a specified environment for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @return ResponseEntity&lt;APIEntitiesEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEnvironment> getApiV4ProjectsIdEnvironmentsEnvironmentIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(id, environmentId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an environment
     * Retrieves a specified environment for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdEnvironmentsEnvironmentIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId) throws RestClientResponseException {
        return getApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(id, environmentId);
    }

    /**
     * Create an environment
     * Creates an environment for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdEnvironmentsRequest The postApiV4ProjectsIdEnvironmentsRequest parameter
     * @return APIEntitiesEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdEnvironmentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdEnvironmentsRequest postApiV4ProjectsIdEnvironmentsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdEnvironments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdEnvironmentsRequest' is set
        if (postApiV4ProjectsIdEnvironmentsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdEnvironmentsRequest' when calling postApiV4ProjectsIdEnvironments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/environments", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdEnvironmentsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an environment
     * Creates an environment for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdEnvironmentsRequest The postApiV4ProjectsIdEnvironmentsRequest parameter
     * @return APIEntitiesEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEnvironment postApiV4ProjectsIdEnvironments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdEnvironmentsRequest postApiV4ProjectsIdEnvironmentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdEnvironmentsRequestCreation(id, postApiV4ProjectsIdEnvironmentsRequest).body(localVarReturnType);
    }

    /**
     * Create an environment
     * Creates an environment for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdEnvironmentsRequest The postApiV4ProjectsIdEnvironmentsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEnvironment> postApiV4ProjectsIdEnvironmentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdEnvironmentsRequest postApiV4ProjectsIdEnvironmentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdEnvironmentsRequestCreation(id, postApiV4ProjectsIdEnvironmentsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an environment
     * Creates an environment for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdEnvironmentsRequest The postApiV4ProjectsIdEnvironmentsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdEnvironmentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdEnvironmentsRequest postApiV4ProjectsIdEnvironmentsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdEnvironmentsRequestCreation(id, postApiV4ProjectsIdEnvironmentsRequest);
    }

    /**
     * Stop an environment
     * Stops a specified running environment.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @param postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest The postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest parameter
     * @return APIEntitiesEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId, @jakarta.annotation.Nullable PostApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdEnvironmentsEnvironmentIdStop", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'environmentId' is set
        if (environmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'environmentId' when calling postApiV4ProjectsIdEnvironmentsEnvironmentIdStop", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("environment_id", environmentId);

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

        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/environments/{environment_id}/stop", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Stop an environment
     * Stops a specified running environment.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @param postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest The postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest parameter
     * @return APIEntitiesEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEnvironment postApiV4ProjectsIdEnvironmentsEnvironmentIdStop(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId, @jakarta.annotation.Nullable PostApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequestCreation(id, environmentId, postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest).body(localVarReturnType);
    }

    /**
     * Stop an environment
     * Stops a specified running environment.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @param postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest The postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest parameter
     * @return ResponseEntity&lt;APIEntitiesEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEnvironment> postApiV4ProjectsIdEnvironmentsEnvironmentIdStopWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId, @jakarta.annotation.Nullable PostApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequestCreation(id, environmentId, postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest).toEntity(localVarReturnType);
    }

    /**
     * Stop an environment
     * Stops a specified running environment.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @param postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest The postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdEnvironmentsEnvironmentIdStopWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId, @jakarta.annotation.Nullable PostApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequestCreation(id, environmentId, postApiV4ProjectsIdEnvironmentsEnvironmentIdStopRequest);
    }

    /**
     * Stop stale environments
     * Stops all environments that were last modified or deployed to before a specified date. Excludes protected environments.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdEnvironmentsStopStaleRequest The postApiV4ProjectsIdEnvironmentsStopStaleRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdEnvironmentsStopStaleRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdEnvironmentsStopStaleRequest postApiV4ProjectsIdEnvironmentsStopStaleRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdEnvironmentsStopStale", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdEnvironmentsStopStaleRequest' is set
        if (postApiV4ProjectsIdEnvironmentsStopStaleRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdEnvironmentsStopStaleRequest' when calling postApiV4ProjectsIdEnvironmentsStopStale", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/environments/stop_stale", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdEnvironmentsStopStaleRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Stop stale environments
     * Stops all environments that were last modified or deployed to before a specified date. Excludes protected environments.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdEnvironmentsStopStaleRequest The postApiV4ProjectsIdEnvironmentsStopStaleRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdEnvironmentsStopStale(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdEnvironmentsStopStaleRequest postApiV4ProjectsIdEnvironmentsStopStaleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdEnvironmentsStopStaleRequestCreation(id, postApiV4ProjectsIdEnvironmentsStopStaleRequest).body(localVarReturnType);
    }

    /**
     * Stop stale environments
     * Stops all environments that were last modified or deployed to before a specified date. Excludes protected environments.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdEnvironmentsStopStaleRequest The postApiV4ProjectsIdEnvironmentsStopStaleRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdEnvironmentsStopStaleWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdEnvironmentsStopStaleRequest postApiV4ProjectsIdEnvironmentsStopStaleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdEnvironmentsStopStaleRequestCreation(id, postApiV4ProjectsIdEnvironmentsStopStaleRequest).toEntity(localVarReturnType);
    }

    /**
     * Stop stale environments
     * Stops all environments that were last modified or deployed to before a specified date. Excludes protected environments.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdEnvironmentsStopStaleRequest The postApiV4ProjectsIdEnvironmentsStopStaleRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdEnvironmentsStopStaleWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdEnvironmentsStopStaleRequest postApiV4ProjectsIdEnvironmentsStopStaleRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdEnvironmentsStopStaleRequestCreation(id, postApiV4ProjectsIdEnvironmentsStopStaleRequest);
    }

    /**
     * Update an existing environment
     * Updates an existing environment for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @param putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest The putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest parameter
     * @return APIEntitiesEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId, @jakarta.annotation.Nullable PutApiV4ProjectsIdEnvironmentsEnvironmentIdRequest putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdEnvironmentsEnvironmentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'environmentId' is set
        if (environmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'environmentId' when calling putApiV4ProjectsIdEnvironmentsEnvironmentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("environment_id", environmentId);

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

        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/environments/{environment_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an existing environment
     * Updates an existing environment for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @param putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest The putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest parameter
     * @return APIEntitiesEnvironment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEnvironment putApiV4ProjectsIdEnvironmentsEnvironmentId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId, @jakarta.annotation.Nullable PutApiV4ProjectsIdEnvironmentsEnvironmentIdRequest putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(id, environmentId, putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest).body(localVarReturnType);
    }

    /**
     * Update an existing environment
     * Updates an existing environment for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @param putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest The putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesEnvironment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEnvironment> putApiV4ProjectsIdEnvironmentsEnvironmentIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId, @jakarta.annotation.Nullable PutApiV4ProjectsIdEnvironmentsEnvironmentIdRequest putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEnvironment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(id, environmentId, putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an existing environment
     * Updates an existing environment for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param environmentId ID of the environment.
     * @param putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest The putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdEnvironmentsEnvironmentIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer environmentId, @jakarta.annotation.Nullable PutApiV4ProjectsIdEnvironmentsEnvironmentIdRequest putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdEnvironmentsEnvironmentIdRequestCreation(id, environmentId, putApiV4ProjectsIdEnvironmentsEnvironmentIdRequest);
    }
}
