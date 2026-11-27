package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PostApiV4UsageDataIncrementCounterRequest;
import ai.gebo.gitlab.client.model.PostApiV4UsageDataIncrementUniqueUsersRequest;
import ai.gebo.gitlab.client.model.PostApiV4UsageDataTrackEventRequest;
import ai.gebo.gitlab.client.model.PostApiV4UsageDataTrackEventsRequest;

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
public class UsageDataApi {
    private ApiClient apiClient;

    public UsageDataApi() {
        this(new ApiClient());
    }

    public UsageDataApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all non-SQL metrics
     * Lists all non-SQL metrics data used in the Service ping. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsageDataNonSqlMetricsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/usage_data/non_sql_metrics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all non-SQL metrics
     * Lists all non-SQL metrics data used in the Service ping. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4UsageDataNonSqlMetrics() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4UsageDataNonSqlMetricsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all non-SQL metrics
     * Lists all non-SQL metrics data used in the Service ping. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4UsageDataNonSqlMetricsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsageDataNonSqlMetricsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all non-SQL metrics
     * Lists all non-SQL metrics data used in the Service ping. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsageDataNonSqlMetricsWithResponseSpec() throws RestClientResponseException {
        return getApiV4UsageDataNonSqlMetricsRequestCreation();
    }

    /**
     * List all Service Ping SQL queries
     * Lists all raw SQL queries used to compute Service Ping. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsageDataQueriesRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/usage_data/queries", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Service Ping SQL queries
     * Lists all raw SQL queries used to compute Service Ping. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4UsageDataQueries() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4UsageDataQueriesRequestCreation().body(localVarReturnType);
    }

    /**
     * List all Service Ping SQL queries
     * Lists all raw SQL queries used to compute Service Ping. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4UsageDataQueriesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsageDataQueriesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all Service Ping SQL queries
     * Lists all raw SQL queries used to compute Service Ping. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsageDataQueriesWithResponseSpec() throws RestClientResponseException {
        return getApiV4UsageDataQueriesRequestCreation();
    }

    /**
     * Retrieve Service Ping payload
     * Retrieves the Service Ping payload from the application cache as JSON. If no cached payload is available, returns an empty response. If the payload is empty, check that Service Ping is enabled and wait for the cron job to run, or generate the payload manually. Requires a personal access token with the &#x60;read_service_ping&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsageDataServicePingRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/usage_data/service_ping", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve Service Ping payload
     * Retrieves the Service Ping payload from the application cache as JSON. If no cached payload is available, returns an empty response. If the payload is empty, check that Service Ping is enabled and wait for the cron job to run, or generate the payload manually. Requires a personal access token with the &#x60;read_service_ping&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4UsageDataServicePing() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4UsageDataServicePingRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve Service Ping payload
     * Retrieves the Service Ping payload from the application cache as JSON. If no cached payload is available, returns an empty response. If the payload is empty, check that Service Ping is enabled and wait for the cron job to run, or generate the payload manually. Requires a personal access token with the &#x60;read_service_ping&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4UsageDataServicePingWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsageDataServicePingRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve Service Ping payload
     * Retrieves the Service Ping payload from the application cache as JSON. If no cached payload is available, returns an empty response. If the payload is empty, check that Service Ping is enabled and wait for the cron job to run, or generate the payload manually. Requires a personal access token with the &#x60;read_service_ping&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsageDataServicePingWithResponseSpec() throws RestClientResponseException {
        return getApiV4UsageDataServicePingRequestCreation();
    }

    /**
     * Track usage data event
     * This feature was introduced in GitLab 13.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataIncrementCounterRequest The postApiV4UsageDataIncrementCounterRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsageDataIncrementCounterRequestCreation(@jakarta.annotation.Nonnull PostApiV4UsageDataIncrementCounterRequest postApiV4UsageDataIncrementCounterRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UsageDataIncrementCounterRequest' is set
        if (postApiV4UsageDataIncrementCounterRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UsageDataIncrementCounterRequest' when calling postApiV4UsageDataIncrementCounter", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/usage_data/increment_counter", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UsageDataIncrementCounterRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Track usage data event
     * This feature was introduced in GitLab 13.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataIncrementCounterRequest The postApiV4UsageDataIncrementCounterRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsageDataIncrementCounter(@jakarta.annotation.Nonnull PostApiV4UsageDataIncrementCounterRequest postApiV4UsageDataIncrementCounterRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsageDataIncrementCounterRequestCreation(postApiV4UsageDataIncrementCounterRequest).body(localVarReturnType);
    }

    /**
     * Track usage data event
     * This feature was introduced in GitLab 13.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataIncrementCounterRequest The postApiV4UsageDataIncrementCounterRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsageDataIncrementCounterWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UsageDataIncrementCounterRequest postApiV4UsageDataIncrementCounterRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsageDataIncrementCounterRequestCreation(postApiV4UsageDataIncrementCounterRequest).toEntity(localVarReturnType);
    }

    /**
     * Track usage data event
     * This feature was introduced in GitLab 13.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataIncrementCounterRequest The postApiV4UsageDataIncrementCounterRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsageDataIncrementCounterWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UsageDataIncrementCounterRequest postApiV4UsageDataIncrementCounterRequest) throws RestClientResponseException {
        return postApiV4UsageDataIncrementCounterRequestCreation(postApiV4UsageDataIncrementCounterRequest);
    }

    /**
     * Track usage data event for the current user
     * Tracks a unique-user event for the current user in Service Ping.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataIncrementUniqueUsersRequest The postApiV4UsageDataIncrementUniqueUsersRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsageDataIncrementUniqueUsersRequestCreation(@jakarta.annotation.Nonnull PostApiV4UsageDataIncrementUniqueUsersRequest postApiV4UsageDataIncrementUniqueUsersRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UsageDataIncrementUniqueUsersRequest' is set
        if (postApiV4UsageDataIncrementUniqueUsersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UsageDataIncrementUniqueUsersRequest' when calling postApiV4UsageDataIncrementUniqueUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/usage_data/increment_unique_users", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UsageDataIncrementUniqueUsersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Track usage data event for the current user
     * Tracks a unique-user event for the current user in Service Ping.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataIncrementUniqueUsersRequest The postApiV4UsageDataIncrementUniqueUsersRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsageDataIncrementUniqueUsers(@jakarta.annotation.Nonnull PostApiV4UsageDataIncrementUniqueUsersRequest postApiV4UsageDataIncrementUniqueUsersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsageDataIncrementUniqueUsersRequestCreation(postApiV4UsageDataIncrementUniqueUsersRequest).body(localVarReturnType);
    }

    /**
     * Track usage data event for the current user
     * Tracks a unique-user event for the current user in Service Ping.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataIncrementUniqueUsersRequest The postApiV4UsageDataIncrementUniqueUsersRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsageDataIncrementUniqueUsersWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UsageDataIncrementUniqueUsersRequest postApiV4UsageDataIncrementUniqueUsersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsageDataIncrementUniqueUsersRequestCreation(postApiV4UsageDataIncrementUniqueUsersRequest).toEntity(localVarReturnType);
    }

    /**
     * Track usage data event for the current user
     * Tracks a unique-user event for the current user in Service Ping.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataIncrementUniqueUsersRequest The postApiV4UsageDataIncrementUniqueUsersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsageDataIncrementUniqueUsersWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UsageDataIncrementUniqueUsersRequest postApiV4UsageDataIncrementUniqueUsersRequest) throws RestClientResponseException {
        return postApiV4UsageDataIncrementUniqueUsersRequestCreation(postApiV4UsageDataIncrementUniqueUsersRequest);
    }

    /**
     * Track an internal GitLab event
     * Tracks a GitLab internal event. This action increments Service Ping counters in Redis and is optionally sent to Snowplow. To track multiple events in one request, send an array of events to the &#x60;/track_events&#x60; endpoint. The &#x60;/track_event&#x60; and &#x60;/track_events&#x60; endpoints share a rate limit of 200 events per minute for each user. Each event counts toward the limit, so a &#x60;/track_events&#x60; request counts one unit for each event in the array. The rate limit is behind a feature flag named &#x60;rate_limit_track_internal_events&#x60;. Disabled by default. Requires a personal access token with the &#x60;api&#x60; or &#x60;ai_workflows&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests, or an AI usage events write buffer is nearly full
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataTrackEventRequest The postApiV4UsageDataTrackEventRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsageDataTrackEventRequestCreation(@jakarta.annotation.Nonnull PostApiV4UsageDataTrackEventRequest postApiV4UsageDataTrackEventRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UsageDataTrackEventRequest' is set
        if (postApiV4UsageDataTrackEventRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UsageDataTrackEventRequest' when calling postApiV4UsageDataTrackEvent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/usage_data/track_event", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UsageDataTrackEventRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Track an internal GitLab event
     * Tracks a GitLab internal event. This action increments Service Ping counters in Redis and is optionally sent to Snowplow. To track multiple events in one request, send an array of events to the &#x60;/track_events&#x60; endpoint. The &#x60;/track_event&#x60; and &#x60;/track_events&#x60; endpoints share a rate limit of 200 events per minute for each user. Each event counts toward the limit, so a &#x60;/track_events&#x60; request counts one unit for each event in the array. The rate limit is behind a feature flag named &#x60;rate_limit_track_internal_events&#x60;. Disabled by default. Requires a personal access token with the &#x60;api&#x60; or &#x60;ai_workflows&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests, or an AI usage events write buffer is nearly full
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataTrackEventRequest The postApiV4UsageDataTrackEventRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsageDataTrackEvent(@jakarta.annotation.Nonnull PostApiV4UsageDataTrackEventRequest postApiV4UsageDataTrackEventRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsageDataTrackEventRequestCreation(postApiV4UsageDataTrackEventRequest).body(localVarReturnType);
    }

    /**
     * Track an internal GitLab event
     * Tracks a GitLab internal event. This action increments Service Ping counters in Redis and is optionally sent to Snowplow. To track multiple events in one request, send an array of events to the &#x60;/track_events&#x60; endpoint. The &#x60;/track_event&#x60; and &#x60;/track_events&#x60; endpoints share a rate limit of 200 events per minute for each user. Each event counts toward the limit, so a &#x60;/track_events&#x60; request counts one unit for each event in the array. The rate limit is behind a feature flag named &#x60;rate_limit_track_internal_events&#x60;. Disabled by default. Requires a personal access token with the &#x60;api&#x60; or &#x60;ai_workflows&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests, or an AI usage events write buffer is nearly full
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataTrackEventRequest The postApiV4UsageDataTrackEventRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsageDataTrackEventWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UsageDataTrackEventRequest postApiV4UsageDataTrackEventRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsageDataTrackEventRequestCreation(postApiV4UsageDataTrackEventRequest).toEntity(localVarReturnType);
    }

    /**
     * Track an internal GitLab event
     * Tracks a GitLab internal event. This action increments Service Ping counters in Redis and is optionally sent to Snowplow. To track multiple events in one request, send an array of events to the &#x60;/track_events&#x60; endpoint. The &#x60;/track_event&#x60; and &#x60;/track_events&#x60; endpoints share a rate limit of 200 events per minute for each user. Each event counts toward the limit, so a &#x60;/track_events&#x60; request counts one unit for each event in the array. The rate limit is behind a feature flag named &#x60;rate_limit_track_internal_events&#x60;. Disabled by default. Requires a personal access token with the &#x60;api&#x60; or &#x60;ai_workflows&#x60; scope.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests, or an AI usage events write buffer is nearly full
     * <p><b>400</b> - Bad Request
     * @param postApiV4UsageDataTrackEventRequest The postApiV4UsageDataTrackEventRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsageDataTrackEventWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UsageDataTrackEventRequest postApiV4UsageDataTrackEventRequest) throws RestClientResponseException {
        return postApiV4UsageDataTrackEventRequestCreation(postApiV4UsageDataTrackEventRequest);
    }

    /**
     * Track multiple internal GitLab events
     * Tracks one or more GitLab internal events in a single request. Each event increments Service Ping counters in Redis and is optionally sent to Snowplow. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Unauthorized
     * <p><b>429</b> - Too many requests, or an AI usage events write buffer is nearly full
     * @param postApiV4UsageDataTrackEventsRequest The postApiV4UsageDataTrackEventsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsageDataTrackEventsRequestCreation(@jakarta.annotation.Nonnull PostApiV4UsageDataTrackEventsRequest postApiV4UsageDataTrackEventsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UsageDataTrackEventsRequest' is set
        if (postApiV4UsageDataTrackEventsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UsageDataTrackEventsRequest' when calling postApiV4UsageDataTrackEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/usage_data/track_events", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UsageDataTrackEventsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Track multiple internal GitLab events
     * Tracks one or more GitLab internal events in a single request. Each event increments Service Ping counters in Redis and is optionally sent to Snowplow. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Unauthorized
     * <p><b>429</b> - Too many requests, or an AI usage events write buffer is nearly full
     * @param postApiV4UsageDataTrackEventsRequest The postApiV4UsageDataTrackEventsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsageDataTrackEvents(@jakarta.annotation.Nonnull PostApiV4UsageDataTrackEventsRequest postApiV4UsageDataTrackEventsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsageDataTrackEventsRequestCreation(postApiV4UsageDataTrackEventsRequest).body(localVarReturnType);
    }

    /**
     * Track multiple internal GitLab events
     * Tracks one or more GitLab internal events in a single request. Each event increments Service Ping counters in Redis and is optionally sent to Snowplow. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Unauthorized
     * <p><b>429</b> - Too many requests, or an AI usage events write buffer is nearly full
     * @param postApiV4UsageDataTrackEventsRequest The postApiV4UsageDataTrackEventsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsageDataTrackEventsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UsageDataTrackEventsRequest postApiV4UsageDataTrackEventsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsageDataTrackEventsRequestCreation(postApiV4UsageDataTrackEventsRequest).toEntity(localVarReturnType);
    }

    /**
     * Track multiple internal GitLab events
     * Tracks one or more GitLab internal events in a single request. Each event increments Service Ping counters in Redis and is optionally sent to Snowplow. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Unauthorized
     * <p><b>429</b> - Too many requests, or an AI usage events write buffer is nearly full
     * @param postApiV4UsageDataTrackEventsRequest The postApiV4UsageDataTrackEventsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsageDataTrackEventsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UsageDataTrackEventsRequest postApiV4UsageDataTrackEventsRequest) throws RestClientResponseException {
        return postApiV4UsageDataTrackEventsRequestCreation(postApiV4UsageDataTrackEventsRequest);
    }
}
