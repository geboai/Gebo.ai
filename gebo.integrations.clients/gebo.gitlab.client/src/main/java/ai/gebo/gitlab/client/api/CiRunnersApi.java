package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiJobRouterDiscoveryInformation;
import ai.gebo.gitlab.client.model.APIEntitiesCiResetTokenResult;
import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerRegistrationDetails;
import ai.gebo.gitlab.client.model.PostApiV4RunnersRequest;
import ai.gebo.gitlab.client.model.PostApiV4RunnersResetAuthenticationTokenRequest;
import ai.gebo.gitlab.client.model.PostApiV4RunnersVerifyRequest;

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
public class CiRunnersApi {
    private ApiClient apiClient;

    public CiRunnersApi() {
        this(new ApiClient());
    }

    public CiRunnersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a registered runner
     * Deletes a registered runner by using its authentication token.
     * <p><b>204</b> - Resource deleted
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param token Authentication token of the runner.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4RunnersRequestCreation(@jakarta.annotation.Nullable String token) throws RestClientResponseException {
        // verify the required parameter 'token' is set
        if (token == null) {
            throw new RestClientResponseException("Missing the required parameter 'token' when calling deleteApiV4Runners", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "token", token));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a registered runner
     * Deletes a registered runner by using its authentication token.
     * <p><b>204</b> - Resource deleted
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param token Authentication token of the runner.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4Runners(@jakarta.annotation.Nullable String token) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4RunnersRequestCreation(token).body(localVarReturnType);
    }

    /**
     * Delete a registered runner
     * Deletes a registered runner by using its authentication token.
     * <p><b>204</b> - Resource deleted
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param token Authentication token of the runner.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4RunnersWithHttpInfo(@jakarta.annotation.Nullable String token) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4RunnersRequestCreation(token).toEntity(localVarReturnType);
    }

    /**
     * Delete a registered runner
     * Deletes a registered runner by using its authentication token.
     * <p><b>204</b> - Resource deleted
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param token Authentication token of the runner.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4RunnersWithResponseSpec(@jakarta.annotation.Nullable String token) throws RestClientResponseException {
        return deleteApiV4RunnersRequestCreation(token);
    }

    /**
     * Delete a registered runner manager
     * Deletes a runner manager identified by the runner authentication token and system ID.
     * <p><b>204</b> - Runner manager was deleted
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param token Authentication token of the runner.
     * @param systemId System identifier of the runner.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4RunnersManagersRequestCreation(@jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable String systemId) throws RestClientResponseException {
        // verify the required parameter 'token' is set
        if (token == null) {
            throw new RestClientResponseException("Missing the required parameter 'token' when calling deleteApiV4RunnersManagers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'systemId' is set
        if (systemId == null) {
            throw new RestClientResponseException("Missing the required parameter 'systemId' when calling deleteApiV4RunnersManagers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "token", token));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "system_id", systemId));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/managers", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a registered runner manager
     * Deletes a runner manager identified by the runner authentication token and system ID.
     * <p><b>204</b> - Runner manager was deleted
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param token Authentication token of the runner.
     * @param systemId System identifier of the runner.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4RunnersManagers(@jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable String systemId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4RunnersManagersRequestCreation(token, systemId).body(localVarReturnType);
    }

    /**
     * Delete a registered runner manager
     * Deletes a runner manager identified by the runner authentication token and system ID.
     * <p><b>204</b> - Runner manager was deleted
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param token Authentication token of the runner.
     * @param systemId System identifier of the runner.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4RunnersManagersWithHttpInfo(@jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable String systemId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4RunnersManagersRequestCreation(token, systemId).toEntity(localVarReturnType);
    }

    /**
     * Delete a registered runner manager
     * Deletes a runner manager identified by the runner authentication token and system ID.
     * <p><b>204</b> - Runner manager was deleted
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param token Authentication token of the runner.
     * @param systemId System identifier of the runner.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4RunnersManagersWithResponseSpec(@jakarta.annotation.Nullable String token, @jakarta.annotation.Nullable String systemId) throws RestClientResponseException {
        return deleteApiV4RunnersManagersRequestCreation(token, systemId);
    }

    /**
     * Discover Job Router information
     * Discovers Job Router information for a runner. You must provide a valid runner authentication token. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>403</b> - 403 Forbidden
     * <p><b>501</b> - Job Router is not available
     * @return APIEntitiesCiJobRouterDiscoveryInformation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RunnersRouterDiscoveryRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCiJobRouterDiscoveryInformation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/router/discovery", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Discover Job Router information
     * Discovers Job Router information for a runner. You must provide a valid runner authentication token. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>403</b> - 403 Forbidden
     * <p><b>501</b> - Job Router is not available
     * @return APIEntitiesCiJobRouterDiscoveryInformation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiJobRouterDiscoveryInformation getApiV4RunnersRouterDiscovery() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiJobRouterDiscoveryInformation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersRouterDiscoveryRequestCreation().body(localVarReturnType);
    }

    /**
     * Discover Job Router information
     * Discovers Job Router information for a runner. You must provide a valid runner authentication token. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>403</b> - 403 Forbidden
     * <p><b>501</b> - Job Router is not available
     * @return ResponseEntity&lt;APIEntitiesCiJobRouterDiscoveryInformation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiJobRouterDiscoveryInformation> getApiV4RunnersRouterDiscoveryWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiJobRouterDiscoveryInformation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RunnersRouterDiscoveryRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Discover Job Router information
     * Discovers Job Router information for a runner. You must provide a valid runner authentication token. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>403</b> - 403 Forbidden
     * <p><b>501</b> - Job Router is not available
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RunnersRouterDiscoveryWithResponseSpec() throws RestClientResponseException {
        return getApiV4RunnersRouterDiscoveryRequestCreation();
    }

    /**
     * Create a runner
     * Creates a runner with a runner registration token. Runner registration tokens are deprecated and disabled by default in GitLab 17.0 and later. If registration with runner registration tokens is disabled in the instance, project, or group settings, returns &#x60;410 Gone&#x60;. Use &#x60;POST /user/runners&#x60; to create runners instead.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Invalid runner registration token
     * <p><b>410</b> - Runner registration disabled
     * @param postApiV4RunnersRequest The postApiV4RunnersRequest parameter
     * @return APIEntitiesCiRunnerRegistrationDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnersRequestCreation(@jakarta.annotation.Nonnull PostApiV4RunnersRequest postApiV4RunnersRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4RunnersRequest' is set
        if (postApiV4RunnersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4RunnersRequest' when calling postApiV4Runners", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4RunnersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a runner
     * Creates a runner with a runner registration token. Runner registration tokens are deprecated and disabled by default in GitLab 17.0 and later. If registration with runner registration tokens is disabled in the instance, project, or group settings, returns &#x60;410 Gone&#x60;. Use &#x60;POST /user/runners&#x60; to create runners instead.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Invalid runner registration token
     * <p><b>410</b> - Runner registration disabled
     * @param postApiV4RunnersRequest The postApiV4RunnersRequest parameter
     * @return APIEntitiesCiRunnerRegistrationDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerRegistrationDetails postApiV4Runners(@jakarta.annotation.Nonnull PostApiV4RunnersRequest postApiV4RunnersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersRequestCreation(postApiV4RunnersRequest).body(localVarReturnType);
    }

    /**
     * Create a runner
     * Creates a runner with a runner registration token. Runner registration tokens are deprecated and disabled by default in GitLab 17.0 and later. If registration with runner registration tokens is disabled in the instance, project, or group settings, returns &#x60;410 Gone&#x60;. Use &#x60;POST /user/runners&#x60; to create runners instead.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Invalid runner registration token
     * <p><b>410</b> - Runner registration disabled
     * @param postApiV4RunnersRequest The postApiV4RunnersRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiRunnerRegistrationDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerRegistrationDetails> postApiV4RunnersWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4RunnersRequest postApiV4RunnersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersRequestCreation(postApiV4RunnersRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a runner
     * Creates a runner with a runner registration token. Runner registration tokens are deprecated and disabled by default in GitLab 17.0 and later. If registration with runner registration tokens is disabled in the instance, project, or group settings, returns &#x60;410 Gone&#x60;. Use &#x60;POST /user/runners&#x60; to create runners instead.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Invalid runner registration token
     * <p><b>410</b> - Runner registration disabled
     * @param postApiV4RunnersRequest The postApiV4RunnersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnersWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4RunnersRequest postApiV4RunnersRequest) throws RestClientResponseException {
        return postApiV4RunnersRequestCreation(postApiV4RunnersRequest);
    }

    /**
     * Reset a runner authentication token with the current token
     * Resets a runner authentication token with the token used to authenticate the request.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnersResetAuthenticationTokenRequest The postApiV4RunnersResetAuthenticationTokenRequest parameter
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnersResetAuthenticationTokenRequestCreation(@jakarta.annotation.Nonnull PostApiV4RunnersResetAuthenticationTokenRequest postApiV4RunnersResetAuthenticationTokenRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4RunnersResetAuthenticationTokenRequest' is set
        if (postApiV4RunnersResetAuthenticationTokenRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4RunnersResetAuthenticationTokenRequest' when calling postApiV4RunnersResetAuthenticationToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/reset_authentication_token", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4RunnersResetAuthenticationTokenRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset a runner authentication token with the current token
     * Resets a runner authentication token with the token used to authenticate the request.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnersResetAuthenticationTokenRequest The postApiV4RunnersResetAuthenticationTokenRequest parameter
     * @return APIEntitiesCiResetTokenResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiResetTokenResult postApiV4RunnersResetAuthenticationToken(@jakarta.annotation.Nonnull PostApiV4RunnersResetAuthenticationTokenRequest postApiV4RunnersResetAuthenticationTokenRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersResetAuthenticationTokenRequestCreation(postApiV4RunnersResetAuthenticationTokenRequest).body(localVarReturnType);
    }

    /**
     * Reset a runner authentication token with the current token
     * Resets a runner authentication token with the token used to authenticate the request.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnersResetAuthenticationTokenRequest The postApiV4RunnersResetAuthenticationTokenRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiResetTokenResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiResetTokenResult> postApiV4RunnersResetAuthenticationTokenWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4RunnersResetAuthenticationTokenRequest postApiV4RunnersResetAuthenticationTokenRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiResetTokenResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersResetAuthenticationTokenRequestCreation(postApiV4RunnersResetAuthenticationTokenRequest).toEntity(localVarReturnType);
    }

    /**
     * Reset a runner authentication token with the current token
     * Resets a runner authentication token with the token used to authenticate the request.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable Entity
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnersResetAuthenticationTokenRequest The postApiV4RunnersResetAuthenticationTokenRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnersResetAuthenticationTokenWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4RunnersResetAuthenticationTokenRequest postApiV4RunnersResetAuthenticationTokenRequest) throws RestClientResponseException {
        return postApiV4RunnersResetAuthenticationTokenRequestCreation(postApiV4RunnersResetAuthenticationTokenRequest);
    }

    /**
     * Verify authentication for a registered runner
     * Verifies authentication for a registered runner.
     * <p><b>200</b> - Credentials are valid
     * <p><b>403</b> - Credentials are invalid
     * <p><b>422</b> - Runner is orphaned
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnersVerifyRequest The postApiV4RunnersVerifyRequest parameter
     * @return APIEntitiesCiRunnerRegistrationDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RunnersVerifyRequestCreation(@jakarta.annotation.Nonnull PostApiV4RunnersVerifyRequest postApiV4RunnersVerifyRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4RunnersVerifyRequest' is set
        if (postApiV4RunnersVerifyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4RunnersVerifyRequest' when calling postApiV4RunnersVerify", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/runners/verify", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4RunnersVerifyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Verify authentication for a registered runner
     * Verifies authentication for a registered runner.
     * <p><b>200</b> - Credentials are valid
     * <p><b>403</b> - Credentials are invalid
     * <p><b>422</b> - Runner is orphaned
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnersVerifyRequest The postApiV4RunnersVerifyRequest parameter
     * @return APIEntitiesCiRunnerRegistrationDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerRegistrationDetails postApiV4RunnersVerify(@jakarta.annotation.Nonnull PostApiV4RunnersVerifyRequest postApiV4RunnersVerifyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersVerifyRequestCreation(postApiV4RunnersVerifyRequest).body(localVarReturnType);
    }

    /**
     * Verify authentication for a registered runner
     * Verifies authentication for a registered runner.
     * <p><b>200</b> - Credentials are valid
     * <p><b>403</b> - Credentials are invalid
     * <p><b>422</b> - Runner is orphaned
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnersVerifyRequest The postApiV4RunnersVerifyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiRunnerRegistrationDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerRegistrationDetails> postApiV4RunnersVerifyWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4RunnersVerifyRequest postApiV4RunnersVerifyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RunnersVerifyRequestCreation(postApiV4RunnersVerifyRequest).toEntity(localVarReturnType);
    }

    /**
     * Verify authentication for a registered runner
     * Verifies authentication for a registered runner.
     * <p><b>200</b> - Credentials are valid
     * <p><b>403</b> - Credentials are invalid
     * <p><b>422</b> - Runner is orphaned
     * <p><b>400</b> - Bad Request
     * @param postApiV4RunnersVerifyRequest The postApiV4RunnersVerifyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RunnersVerifyWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4RunnersVerifyRequest postApiV4RunnersVerifyRequest) throws RestClientResponseException {
        return postApiV4RunnersVerifyRequestCreation(postApiV4RunnersVerifyRequest);
    }
}
