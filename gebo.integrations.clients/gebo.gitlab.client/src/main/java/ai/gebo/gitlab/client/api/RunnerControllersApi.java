package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerController;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerControllerDetail;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerControllerInstanceLevelScoping;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerControllerRunnerLevelScoping;
import ai.gebo.gitlab.client.model.PostApiV4RunnerControllersRequest;
import ai.gebo.gitlab.client.model.PutApiV4RunnerControllersIdRequest;

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
public class RunnerControllersApi {
    private ApiClient apiClient;

    public RunnerControllersApi() {
        this(new ApiClient());
    }

    public RunnerControllersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a runner controller
     * Deletes a specified runner controller. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return APIEntitiesCiRunnerController
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4RunnerControllersIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4RunnerControllersId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerController> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a runner controller
     * Deletes a specified runner controller. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return APIEntitiesCiRunnerController
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerController deleteApiV4RunnerControllersId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerController> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4RunnerControllersIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a runner controller
     * Deletes a specified runner controller. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return ResponseEntity&lt;APIEntitiesCiRunnerController&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerController> deleteApiV4RunnerControllersIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerController> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4RunnerControllersIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a runner controller
     * Deletes a specified runner controller. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4RunnerControllersIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4RunnerControllersIdRequestCreation(id);
    }

    /**
     * Remove instance scope
     * Removes an instance scope from a runner controller.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4RunnerControllersIdScopesInstanceRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4RunnerControllersIdScopesInstance", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/runner_controllers/{id}/scopes/instance", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove instance scope
     * Removes an instance scope from a runner controller.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4RunnerControllersIdScopesInstance(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4RunnerControllersIdScopesInstanceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Remove instance scope
     * Removes an instance scope from a runner controller.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4RunnerControllersIdScopesInstanceWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4RunnerControllersIdScopesInstanceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Remove instance scope
     * Removes an instance scope from a runner controller.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4RunnerControllersIdScopesInstanceWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4RunnerControllersIdScopesInstanceRequestCreation(id);
    }

    /**
     * Remove runner scope
     * Removes a runner scope from a runner controller. This feature was introduced in GitLab 18.10.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param runnerId ID of the runner.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4RunnerControllersIdScopesRunnersRunnerIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4RunnerControllersIdScopesRunnersRunnerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'runnerId' is set
        if (runnerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runnerId' when calling deleteApiV4RunnerControllersIdScopesRunnersRunnerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("runner_id", runnerId);

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
        return apiClient.invokeAPI("/api/v4/runner_controllers/{id}/scopes/runners/{runner_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove runner scope
     * Removes a runner scope from a runner controller. This feature was introduced in GitLab 18.10.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param runnerId ID of the runner.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4RunnerControllersIdScopesRunnersRunnerId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4RunnerControllersIdScopesRunnersRunnerIdRequestCreation(id, runnerId).body(localVarReturnType);
    }

    /**
     * Remove runner scope
     * Removes a runner scope from a runner controller. This feature was introduced in GitLab 18.10.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param runnerId ID of the runner.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4RunnerControllersIdScopesRunnersRunnerIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4RunnerControllersIdScopesRunnersRunnerIdRequestCreation(id, runnerId).toEntity(localVarReturnType);
    }

    /**
     * Remove runner scope
     * Removes a runner scope from a runner controller. This feature was introduced in GitLab 18.10.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param runnerId ID of the runner.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4RunnerControllersIdScopesRunnersRunnerIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        return deleteApiV4RunnerControllersIdScopesRunnersRunnerIdRequestCreation(id, runnerId);
    }

    /**
     * List all runner controllers
     * Lists all runner controllers.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerController&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnerControllersRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesCiRunnerController>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all runner controllers
     * Lists all runner controllers.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerController&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiRunnerController> getApiV4RunnerControllers(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerController>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnerControllersRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all runner controllers
     * Lists all runner controllers.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiRunnerController&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiRunnerController>> getApiV4RunnerControllersWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerController>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnerControllersRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all runner controllers
     * Lists all runner controllers.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnerControllersWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4RunnerControllersRequestCreation(page, perPage);
    }

    /**
     * Retrieve a single runner controller
     * Retrieves details of a specified runner controller.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return APIEntitiesCiRunnerControllerDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnerControllersIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4RunnerControllersId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerControllerDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a single runner controller
     * Retrieves details of a specified runner controller.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return APIEntitiesCiRunnerControllerDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerControllerDetail getApiV4RunnerControllersId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnerControllersIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a single runner controller
     * Retrieves details of a specified runner controller.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return ResponseEntity&lt;APIEntitiesCiRunnerControllerDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerControllerDetail> getApiV4RunnerControllersIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnerControllersIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a single runner controller
     * Retrieves details of a specified runner controller.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnerControllersIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4RunnerControllersIdRequestCreation(id);
    }

    /**
     * List all scopes for a runner controller
     * Lists all scopes for a runner controller.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnerControllersIdScopesRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4RunnerControllersIdScopes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/runner_controllers/{id}/scopes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all scopes for a runner controller
     * Lists all scopes for a runner controller.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4RunnerControllersIdScopes(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4RunnerControllersIdScopesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all scopes for a runner controller
     * Lists all scopes for a runner controller.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4RunnerControllersIdScopesWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnerControllersIdScopesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all scopes for a runner controller
     * Lists all scopes for a runner controller.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnerControllersIdScopesWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4RunnerControllersIdScopesRequestCreation(id);
    }

    /**
     * Register a runner controller
     * Registers a runner controller.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnerControllersRequest The postApiV4RunnerControllersRequest parameter
     * @return APIEntitiesCiRunnerController
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnerControllersRequestCreation(@jakarta.annotation.Nullable PostApiV4RunnerControllersRequest postApiV4RunnerControllersRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesCiRunnerController> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4RunnerControllersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Register a runner controller
     * Registers a runner controller.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnerControllersRequest The postApiV4RunnerControllersRequest parameter
     * @return APIEntitiesCiRunnerController
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerController postApiV4RunnerControllers(@jakarta.annotation.Nullable PostApiV4RunnerControllersRequest postApiV4RunnerControllersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerController> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersRequestCreation(postApiV4RunnerControllersRequest).body(localVarReturnType);
    }

    /**
     * Register a runner controller
     * Registers a runner controller.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnerControllersRequest The postApiV4RunnerControllersRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiRunnerController&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerController> postApiV4RunnerControllersWithHttpInfo(@jakarta.annotation.Nullable PostApiV4RunnerControllersRequest postApiV4RunnerControllersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerController> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersRequestCreation(postApiV4RunnerControllersRequest).toEntity(localVarReturnType);
    }

    /**
     * Register a runner controller
     * Registers a runner controller.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnerControllersRequest The postApiV4RunnerControllersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnerControllersWithResponseSpec(@jakarta.annotation.Nullable PostApiV4RunnerControllersRequest postApiV4RunnerControllersRequest) throws RestClientResponseException {
        return postApiV4RunnerControllersRequestCreation(postApiV4RunnerControllersRequest);
    }

    /**
     * Add instance scope
     * Adds an instance scope to a runner controller. When added, the runner controller evaluates jobs for all runners in the GitLab instance. A runner controller can have only one instance scope. If an instance scope already exists, this operation returns an error.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return APIEntitiesCiRunnerControllerInstanceLevelScoping
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnerControllersIdScopesInstanceRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4RunnerControllersIdScopesInstance", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerControllerInstanceLevelScoping> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers/{id}/scopes/instance", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add instance scope
     * Adds an instance scope to a runner controller. When added, the runner controller evaluates jobs for all runners in the GitLab instance. A runner controller can have only one instance scope. If an instance scope already exists, this operation returns an error.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return APIEntitiesCiRunnerControllerInstanceLevelScoping
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerControllerInstanceLevelScoping postApiV4RunnerControllersIdScopesInstance(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerInstanceLevelScoping> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersIdScopesInstanceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Add instance scope
     * Adds an instance scope to a runner controller. When added, the runner controller evaluates jobs for all runners in the GitLab instance. A runner controller can have only one instance scope. If an instance scope already exists, this operation returns an error.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return ResponseEntity&lt;APIEntitiesCiRunnerControllerInstanceLevelScoping&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerControllerInstanceLevelScoping> postApiV4RunnerControllersIdScopesInstanceWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerInstanceLevelScoping> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersIdScopesInstanceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Add instance scope
     * Adds an instance scope to a runner controller. When added, the runner controller evaluates jobs for all runners in the GitLab instance. A runner controller can have only one instance scope. If an instance scope already exists, this operation returns an error.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnerControllersIdScopesInstanceWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4RunnerControllersIdScopesInstanceRequestCreation(id);
    }

    /**
     * Add runner scope
     * Adds a runner scope to a runner controller. When added, the runner controller evaluates jobs only for a specified runner. A runner controller with an instance scope cannot have runner scopes. Remove the instance scope before adding runner scopes. This feature was introduced in GitLab 18.10.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param runnerId ID of the runner. Must be an instance runner.
     * @return APIEntitiesCiRunnerControllerRunnerLevelScoping
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnerControllersIdScopesRunnersRunnerIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4RunnerControllersIdScopesRunnersRunnerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'runnerId' is set
        if (runnerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runnerId' when calling postApiV4RunnerControllersIdScopesRunnersRunnerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("runner_id", runnerId);

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

        ParameterizedTypeReference<APIEntitiesCiRunnerControllerRunnerLevelScoping> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers/{id}/scopes/runners/{runner_id}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add runner scope
     * Adds a runner scope to a runner controller. When added, the runner controller evaluates jobs only for a specified runner. A runner controller with an instance scope cannot have runner scopes. Remove the instance scope before adding runner scopes. This feature was introduced in GitLab 18.10.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param runnerId ID of the runner. Must be an instance runner.
     * @return APIEntitiesCiRunnerControllerRunnerLevelScoping
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerControllerRunnerLevelScoping postApiV4RunnerControllersIdScopesRunnersRunnerId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerRunnerLevelScoping> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersIdScopesRunnersRunnerIdRequestCreation(id, runnerId).body(localVarReturnType);
    }

    /**
     * Add runner scope
     * Adds a runner scope to a runner controller. When added, the runner controller evaluates jobs only for a specified runner. A runner controller with an instance scope cannot have runner scopes. Remove the instance scope before adding runner scopes. This feature was introduced in GitLab 18.10.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param runnerId ID of the runner. Must be an instance runner.
     * @return ResponseEntity&lt;APIEntitiesCiRunnerControllerRunnerLevelScoping&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerControllerRunnerLevelScoping> postApiV4RunnerControllersIdScopesRunnersRunnerIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerRunnerLevelScoping> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersIdScopesRunnersRunnerIdRequestCreation(id, runnerId).toEntity(localVarReturnType);
    }

    /**
     * Add runner scope
     * Adds a runner scope to a runner controller. When added, the runner controller evaluates jobs only for a specified runner. A runner controller with an instance scope cannot have runner scopes. Remove the instance scope before adding runner scopes. This feature was introduced in GitLab 18.10.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param runnerId ID of the runner. Must be an instance runner.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnerControllersIdScopesRunnersRunnerIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer runnerId) throws RestClientResponseException {
        return postApiV4RunnerControllersIdScopesRunnersRunnerIdRequestCreation(id, runnerId);
    }

    /**
     * Update a runner controller
     * Updates the details of an existing runner controller by its ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param putApiV4RunnerControllersIdRequest The putApiV4RunnerControllersIdRequest parameter
     * @return APIEntitiesCiRunnerController
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4RunnerControllersIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4RunnerControllersIdRequest putApiV4RunnerControllersIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4RunnerControllersId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerController> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4RunnerControllersIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a runner controller
     * Updates the details of an existing runner controller by its ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param putApiV4RunnerControllersIdRequest The putApiV4RunnerControllersIdRequest parameter
     * @return APIEntitiesCiRunnerController
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerController putApiV4RunnerControllersId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4RunnerControllersIdRequest putApiV4RunnerControllersIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerController> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4RunnerControllersIdRequestCreation(id, putApiV4RunnerControllersIdRequest).body(localVarReturnType);
    }

    /**
     * Update a runner controller
     * Updates the details of an existing runner controller by its ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param putApiV4RunnerControllersIdRequest The putApiV4RunnerControllersIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiRunnerController&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerController> putApiV4RunnerControllersIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4RunnerControllersIdRequest putApiV4RunnerControllersIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerController> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4RunnerControllersIdRequestCreation(id, putApiV4RunnerControllersIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a runner controller
     * Updates the details of an existing runner controller by its ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the runner controller.
     * @param putApiV4RunnerControllersIdRequest The putApiV4RunnerControllersIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4RunnerControllersIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4RunnerControllersIdRequest putApiV4RunnerControllersIdRequest) throws RestClientResponseException {
        return putApiV4RunnerControllersIdRequestCreation(id, putApiV4RunnerControllersIdRequest);
    }
}
