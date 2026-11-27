package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PostApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest;
import ai.gebo.gitlab.client.model.PostApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest;

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
public class UnleashApi {
    private ApiClient apiClient;

    public UnleashApi() {
        this(new ApiClient());
    }

    public UnleashApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get Unleash features
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4FeatureFlagsUnleashProjectIdRequestCreation(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getApiV4FeatureFlagsUnleashProjectId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "instance_id", instanceId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "app_name", appName));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/feature_flags/unleash/{project_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get Unleash features
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4FeatureFlagsUnleashProjectId(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4FeatureFlagsUnleashProjectIdRequestCreation(projectId, instanceId, appName).body(localVarReturnType);
    }

    /**
     * Get Unleash features
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4FeatureFlagsUnleashProjectIdWithHttpInfo(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4FeatureFlagsUnleashProjectIdRequestCreation(projectId, instanceId, appName).toEntity(localVarReturnType);
    }

    /**
     * Get Unleash features
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4FeatureFlagsUnleashProjectIdWithResponseSpec(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        return getApiV4FeatureFlagsUnleashProjectIdRequestCreation(projectId, instanceId, appName);
    }

    /**
     * Get a list of features
     * Returns feature flags in the Unleash client features format used by GitLab feature flag clients.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4FeatureFlagsUnleashProjectIdClientFeaturesRequestCreation(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getApiV4FeatureFlagsUnleashProjectIdClientFeatures", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "instance_id", instanceId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "app_name", appName));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/feature_flags/unleash/{project_id}/client/features", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a list of features
     * Returns feature flags in the Unleash client features format used by GitLab feature flag clients.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4FeatureFlagsUnleashProjectIdClientFeatures(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4FeatureFlagsUnleashProjectIdClientFeaturesRequestCreation(projectId, instanceId, appName).body(localVarReturnType);
    }

    /**
     * Get a list of features
     * Returns feature flags in the Unleash client features format used by GitLab feature flag clients.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4FeatureFlagsUnleashProjectIdClientFeaturesWithHttpInfo(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4FeatureFlagsUnleashProjectIdClientFeaturesRequestCreation(projectId, instanceId, appName).toEntity(localVarReturnType);
    }

    /**
     * Get a list of features
     * Returns feature flags in the Unleash client features format used by GitLab feature flag clients.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4FeatureFlagsUnleashProjectIdClientFeaturesWithResponseSpec(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        return getApiV4FeatureFlagsUnleashProjectIdClientFeaturesRequestCreation(projectId, instanceId, appName);
    }

    /**
     * Get a list of features (v2 client support)
     * Deprecated in GitLab 15.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4FeatureFlagsUnleashProjectIdFeaturesRequestCreation(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getApiV4FeatureFlagsUnleashProjectIdFeatures", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "instance_id", instanceId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "app_name", appName));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/feature_flags/unleash/{project_id}/features", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a list of features (v2 client support)
     * Deprecated in GitLab 15.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public void getApiV4FeatureFlagsUnleashProjectIdFeatures(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4FeatureFlagsUnleashProjectIdFeaturesRequestCreation(projectId, instanceId, appName).body(localVarReturnType);
    }

    /**
     * Get a list of features (v2 client support)
     * Deprecated in GitLab 15.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<Void> getApiV4FeatureFlagsUnleashProjectIdFeaturesWithHttpInfo(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4FeatureFlagsUnleashProjectIdFeaturesRequestCreation(projectId, instanceId, appName).toEntity(localVarReturnType);
    }

    /**
     * Get a list of features (v2 client support)
     * Deprecated in GitLab 15.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param instanceId Instance ID of Unleash Client.
     * @param appName Application name of Unleash Client.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec getApiV4FeatureFlagsUnleashProjectIdFeaturesWithResponseSpec(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable String instanceId, @jakarta.annotation.Nullable String appName) throws RestClientResponseException {
        return getApiV4FeatureFlagsUnleashProjectIdFeaturesRequestCreation(projectId, instanceId, appName);
    }

    /**
     * Report Unleash client metrics
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest The postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequestCreation(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable PostApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling postApiV4FeatureFlagsUnleashProjectIdClientMetrics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("project_id", projectId);

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
        return apiClient.invokeAPI("/api/v4/feature_flags/unleash/{project_id}/client/metrics", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Report Unleash client metrics
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest The postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4FeatureFlagsUnleashProjectIdClientMetrics(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable PostApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequestCreation(projectId, postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest).body(localVarReturnType);
    }

    /**
     * Report Unleash client metrics
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest The postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4FeatureFlagsUnleashProjectIdClientMetricsWithHttpInfo(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable PostApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequestCreation(projectId, postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest).toEntity(localVarReturnType);
    }

    /**
     * Report Unleash client metrics
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest The postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4FeatureFlagsUnleashProjectIdClientMetricsWithResponseSpec(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable PostApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest) throws RestClientResponseException {
        return postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequestCreation(projectId, postApiV4FeatureFlagsUnleashProjectIdClientMetricsRequest);
    }

    /**
     * Register Unleash client
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest The postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequestCreation(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable PostApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling postApiV4FeatureFlagsUnleashProjectIdClientRegister", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("project_id", projectId);

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
        return apiClient.invokeAPI("/api/v4/feature_flags/unleash/{project_id}/client/register", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Register Unleash client
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest The postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4FeatureFlagsUnleashProjectIdClientRegister(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable PostApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequestCreation(projectId, postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest).body(localVarReturnType);
    }

    /**
     * Register Unleash client
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest The postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4FeatureFlagsUnleashProjectIdClientRegisterWithHttpInfo(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable PostApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequestCreation(projectId, postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest).toEntity(localVarReturnType);
    }

    /**
     * Register Unleash client
     * This endpoint is a no-op and always returns a 200 OK response.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest The postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4FeatureFlagsUnleashProjectIdClientRegisterWithResponseSpec(@jakarta.annotation.Nonnull String projectId, @jakarta.annotation.Nullable PostApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest) throws RestClientResponseException {
        return postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequestCreation(projectId, postApiV4FeatureFlagsUnleashProjectIdClientRegisterRequest);
    }
}
