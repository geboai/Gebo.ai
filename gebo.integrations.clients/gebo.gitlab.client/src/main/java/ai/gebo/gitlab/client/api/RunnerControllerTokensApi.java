package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerControllerToken;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerControllerTokenWithToken;
import ai.gebo.gitlab.client.model.PostApiV4RunnerControllersRunnerControllerIdTokensRequest;

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
public class RunnerControllerTokensApi {
    private ApiClient apiClient;

    public RunnerControllerTokensApi() {
        this(new ApiClient());
    }

    public RunnerControllerTokensApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Revoke a runner controller token
     * Revokes an existing runner controller token.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4RunnerControllersRunnerControllerIdTokensIdRequestCreation(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'runnerControllerId' is set
        if (runnerControllerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runnerControllerId' when calling deleteApiV4RunnerControllersRunnerControllerIdTokensId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4RunnerControllersRunnerControllerIdTokensId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("runner_controller_id", runnerControllerId);
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
        return apiClient.invokeAPI("/api/v4/runner_controllers/{runner_controller_id}/tokens/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a runner controller token
     * Revokes an existing runner controller token.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4RunnerControllersRunnerControllerIdTokensId(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4RunnerControllersRunnerControllerIdTokensIdRequestCreation(runnerControllerId, id).body(localVarReturnType);
    }

    /**
     * Revoke a runner controller token
     * Revokes an existing runner controller token.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4RunnerControllersRunnerControllerIdTokensIdWithHttpInfo(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4RunnerControllersRunnerControllerIdTokensIdRequestCreation(runnerControllerId, id).toEntity(localVarReturnType);
    }

    /**
     * Revoke a runner controller token
     * Revokes an existing runner controller token.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4RunnerControllersRunnerControllerIdTokensIdWithResponseSpec(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4RunnerControllersRunnerControllerIdTokensIdRequestCreation(runnerControllerId, id);
    }

    /**
     * List all runner controller tokens
     * Lists all runner controller tokens.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param runnerControllerId ID of the runner controller.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerControllerToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnerControllersRunnerControllerIdTokensRequestCreation(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'runnerControllerId' is set
        if (runnerControllerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runnerControllerId' when calling getApiV4RunnerControllersRunnerControllerIdTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("runner_controller_id", runnerControllerId);

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

        ParameterizedTypeReference<List<APIEntitiesCiRunnerControllerToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers/{runner_controller_id}/tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all runner controller tokens
     * Lists all runner controller tokens.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param runnerControllerId ID of the runner controller.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiRunnerControllerToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiRunnerControllerToken> getApiV4RunnerControllersRunnerControllerIdTokens(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerControllerToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnerControllersRunnerControllerIdTokensRequestCreation(runnerControllerId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all runner controller tokens
     * Lists all runner controller tokens.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param runnerControllerId ID of the runner controller.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiRunnerControllerToken&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiRunnerControllerToken>> getApiV4RunnerControllersRunnerControllerIdTokensWithHttpInfo(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiRunnerControllerToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnerControllersRunnerControllerIdTokensRequestCreation(runnerControllerId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all runner controller tokens
     * Lists all runner controller tokens.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param runnerControllerId ID of the runner controller.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnerControllersRunnerControllerIdTokensWithResponseSpec(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4RunnerControllersRunnerControllerIdTokensRequestCreation(runnerControllerId, page, perPage);
    }

    /**
     * Retrieve a single runner controller token
     * Retrieves details of a specified runner controller token by its ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @return APIEntitiesCiRunnerControllerToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnerControllersRunnerControllerIdTokensIdRequestCreation(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'runnerControllerId' is set
        if (runnerControllerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runnerControllerId' when calling getApiV4RunnerControllersRunnerControllerIdTokensId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4RunnerControllersRunnerControllerIdTokensId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("runner_controller_id", runnerControllerId);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerControllerToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers/{runner_controller_id}/tokens/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a single runner controller token
     * Retrieves details of a specified runner controller token by its ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @return APIEntitiesCiRunnerControllerToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerControllerToken getApiV4RunnerControllersRunnerControllerIdTokensId(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnerControllersRunnerControllerIdTokensIdRequestCreation(runnerControllerId, id).body(localVarReturnType);
    }

    /**
     * Retrieve a single runner controller token
     * Retrieves details of a specified runner controller token by its ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @return ResponseEntity&lt;APIEntitiesCiRunnerControllerToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerControllerToken> getApiV4RunnerControllersRunnerControllerIdTokensIdWithHttpInfo(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnerControllersRunnerControllerIdTokensIdRequestCreation(runnerControllerId, id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a single runner controller token
     * Retrieves details of a specified runner controller token by its ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnerControllersRunnerControllerIdTokensIdWithResponseSpec(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4RunnerControllersRunnerControllerIdTokensIdRequestCreation(runnerControllerId, id);
    }

    /**
     * Create a runner controller token
     * Creates a runner controller token.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param postApiV4RunnerControllersRunnerControllerIdTokensRequest The postApiV4RunnerControllersRunnerControllerIdTokensRequest parameter
     * @return APIEntitiesCiRunnerControllerTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnerControllersRunnerControllerIdTokensRequestCreation(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nullable PostApiV4RunnerControllersRunnerControllerIdTokensRequest postApiV4RunnerControllersRunnerControllerIdTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'runnerControllerId' is set
        if (runnerControllerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runnerControllerId' when calling postApiV4RunnerControllersRunnerControllerIdTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("runner_controller_id", runnerControllerId);

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

        ParameterizedTypeReference<APIEntitiesCiRunnerControllerTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers/{runner_controller_id}/tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4RunnerControllersRunnerControllerIdTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a runner controller token
     * Creates a runner controller token.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param postApiV4RunnerControllersRunnerControllerIdTokensRequest The postApiV4RunnerControllersRunnerControllerIdTokensRequest parameter
     * @return APIEntitiesCiRunnerControllerTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerControllerTokenWithToken postApiV4RunnerControllersRunnerControllerIdTokens(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nullable PostApiV4RunnerControllersRunnerControllerIdTokensRequest postApiV4RunnerControllersRunnerControllerIdTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersRunnerControllerIdTokensRequestCreation(runnerControllerId, postApiV4RunnerControllersRunnerControllerIdTokensRequest).body(localVarReturnType);
    }

    /**
     * Create a runner controller token
     * Creates a runner controller token.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param postApiV4RunnerControllersRunnerControllerIdTokensRequest The postApiV4RunnerControllersRunnerControllerIdTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiRunnerControllerTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerControllerTokenWithToken> postApiV4RunnerControllersRunnerControllerIdTokensWithHttpInfo(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nullable PostApiV4RunnerControllersRunnerControllerIdTokensRequest postApiV4RunnerControllersRunnerControllerIdTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersRunnerControllerIdTokensRequestCreation(runnerControllerId, postApiV4RunnerControllersRunnerControllerIdTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a runner controller token
     * Creates a runner controller token.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param postApiV4RunnerControllersRunnerControllerIdTokensRequest The postApiV4RunnerControllersRunnerControllerIdTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnerControllersRunnerControllerIdTokensWithResponseSpec(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nullable PostApiV4RunnerControllersRunnerControllerIdTokensRequest postApiV4RunnerControllersRunnerControllerIdTokensRequest) throws RestClientResponseException {
        return postApiV4RunnerControllersRunnerControllerIdTokensRequestCreation(runnerControllerId, postApiV4RunnerControllersRunnerControllerIdTokensRequest);
    }

    /**
     * Rotate a runner controller token
     * Rotates an existing runner controller token.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @return APIEntitiesCiRunnerControllerTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnerControllersRunnerControllerIdTokensIdRotateRequestCreation(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'runnerControllerId' is set
        if (runnerControllerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runnerControllerId' when calling postApiV4RunnerControllersRunnerControllerIdTokensIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4RunnerControllersRunnerControllerIdTokensIdRotate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("runner_controller_id", runnerControllerId);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerControllerTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runner_controllers/{runner_controller_id}/tokens/{id}/rotate", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rotate a runner controller token
     * Rotates an existing runner controller token.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @return APIEntitiesCiRunnerControllerTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerControllerTokenWithToken postApiV4RunnerControllersRunnerControllerIdTokensIdRotate(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersRunnerControllerIdTokensIdRotateRequestCreation(runnerControllerId, id).body(localVarReturnType);
    }

    /**
     * Rotate a runner controller token
     * Rotates an existing runner controller token.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @return ResponseEntity&lt;APIEntitiesCiRunnerControllerTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerControllerTokenWithToken> postApiV4RunnerControllersRunnerControllerIdTokensIdRotateWithHttpInfo(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerControllerTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnerControllersRunnerControllerIdTokensIdRotateRequestCreation(runnerControllerId, id).toEntity(localVarReturnType);
    }

    /**
     * Rotate a runner controller token
     * Rotates an existing runner controller token.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not found
     * @param runnerControllerId ID of the runner controller.
     * @param id ID of the runner controller token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnerControllersRunnerControllerIdTokensIdRotateWithResponseSpec(@jakarta.annotation.Nonnull Integer runnerControllerId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4RunnerControllersRunnerControllerIdTokensIdRotateRequestCreation(runnerControllerId, id);
    }
}
