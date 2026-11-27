package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.MonitoringAmount;
import ai.gebo.zammad.client.model.MonitoringHealth;
import ai.gebo.zammad.client.model.MonitoringStatus;
import ai.gebo.zammad.client.model.MonitoringToken;

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
public class MonitoringApi {
    private ApiClient apiClient;

    public MonitoringApi() {
        this(new ApiClient());
    }

    public MonitoringApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Check created ticket amount
     * Self-hosted instances only.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param periode Time window such as &#x60;30m&#x60;, &#x60;1h&#x60;, or &#x60;1d&#x60;. The endpoint uses Zammad&#39;s historical parameter spelling.
     * @param maxWarning The maxWarning parameter
     * @param maxCritical The maxCritical parameter
     * @param minWarning The minWarning parameter
     * @param minCritical The minCritical parameter
     * @return MonitoringAmount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMonitoringAmountRequestCreation(@jakarta.annotation.Nonnull String periode, @jakarta.annotation.Nullable Integer maxWarning, @jakarta.annotation.Nullable Integer maxCritical, @jakarta.annotation.Nullable Integer minWarning, @jakarta.annotation.Nullable Integer minCritical) throws RestClientResponseException {
        // verify the required parameter 'periode' is set
        if (periode == null) {
            throw new RestClientResponseException("Missing the required parameter 'periode' when calling getMonitoringAmount", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_warning", maxWarning));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_critical", maxCritical));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_warning", minWarning));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "min_critical", minCritical));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "periode", periode));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "monitoringToken", "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<MonitoringAmount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/monitoring/amount_check", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Check created ticket amount
     * Self-hosted instances only.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param periode Time window such as &#x60;30m&#x60;, &#x60;1h&#x60;, or &#x60;1d&#x60;. The endpoint uses Zammad&#39;s historical parameter spelling.
     * @param maxWarning The maxWarning parameter
     * @param maxCritical The maxCritical parameter
     * @param minWarning The minWarning parameter
     * @param minCritical The minCritical parameter
     * @return MonitoringAmount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MonitoringAmount getMonitoringAmount(@jakarta.annotation.Nonnull String periode, @jakarta.annotation.Nullable Integer maxWarning, @jakarta.annotation.Nullable Integer maxCritical, @jakarta.annotation.Nullable Integer minWarning, @jakarta.annotation.Nullable Integer minCritical) throws RestClientResponseException {
        ParameterizedTypeReference<MonitoringAmount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMonitoringAmountRequestCreation(periode, maxWarning, maxCritical, minWarning, minCritical).body(localVarReturnType);
    }

    /**
     * Check created ticket amount
     * Self-hosted instances only.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param periode Time window such as &#x60;30m&#x60;, &#x60;1h&#x60;, or &#x60;1d&#x60;. The endpoint uses Zammad&#39;s historical parameter spelling.
     * @param maxWarning The maxWarning parameter
     * @param maxCritical The maxCritical parameter
     * @param minWarning The minWarning parameter
     * @param minCritical The minCritical parameter
     * @return ResponseEntity&lt;MonitoringAmount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MonitoringAmount> getMonitoringAmountWithHttpInfo(@jakarta.annotation.Nonnull String periode, @jakarta.annotation.Nullable Integer maxWarning, @jakarta.annotation.Nullable Integer maxCritical, @jakarta.annotation.Nullable Integer minWarning, @jakarta.annotation.Nullable Integer minCritical) throws RestClientResponseException {
        ParameterizedTypeReference<MonitoringAmount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMonitoringAmountRequestCreation(periode, maxWarning, maxCritical, minWarning, minCritical).toEntity(localVarReturnType);
    }

    /**
     * Check created ticket amount
     * Self-hosted instances only.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param periode Time window such as &#x60;30m&#x60;, &#x60;1h&#x60;, or &#x60;1d&#x60;. The endpoint uses Zammad&#39;s historical parameter spelling.
     * @param maxWarning The maxWarning parameter
     * @param maxCritical The maxCritical parameter
     * @param minWarning The minWarning parameter
     * @param minCritical The minCritical parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMonitoringAmountWithResponseSpec(@jakarta.annotation.Nonnull String periode, @jakarta.annotation.Nullable Integer maxWarning, @jakarta.annotation.Nullable Integer maxCritical, @jakarta.annotation.Nullable Integer minWarning, @jakarta.annotation.Nullable Integer minCritical) throws RestClientResponseException {
        return getMonitoringAmountRequestCreation(periode, maxWarning, maxCritical, minWarning, minCritical);
    }

    /**
     * Check instance health
     * Self-hosted instances only. Use the monitoring query token or an authorized API credential.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @return MonitoringHealth
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMonitoringHealthRequestCreation() throws RestClientResponseException {
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

        String[] localVarAuthNames = new String[] { "monitoringToken", "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<MonitoringHealth> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/monitoring/health_check", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Check instance health
     * Self-hosted instances only. Use the monitoring query token or an authorized API credential.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @return MonitoringHealth
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MonitoringHealth getMonitoringHealth() throws RestClientResponseException {
        ParameterizedTypeReference<MonitoringHealth> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMonitoringHealthRequestCreation().body(localVarReturnType);
    }

    /**
     * Check instance health
     * Self-hosted instances only. Use the monitoring query token or an authorized API credential.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @return ResponseEntity&lt;MonitoringHealth&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MonitoringHealth> getMonitoringHealthWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<MonitoringHealth> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMonitoringHealthRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Check instance health
     * Self-hosted instances only. Use the monitoring query token or an authorized API credential.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMonitoringHealthWithResponseSpec() throws RestClientResponseException {
        return getMonitoringHealthRequestCreation();
    }

    /**
     * Get instance statistics
     * Self-hosted instances only.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @return MonitoringStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMonitoringStatusRequestCreation() throws RestClientResponseException {
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

        String[] localVarAuthNames = new String[] { "monitoringToken", "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<MonitoringStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/monitoring/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get instance statistics
     * Self-hosted instances only.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @return MonitoringStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MonitoringStatus getMonitoringStatus() throws RestClientResponseException {
        ParameterizedTypeReference<MonitoringStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMonitoringStatusRequestCreation().body(localVarReturnType);
    }

    /**
     * Get instance statistics
     * Self-hosted instances only.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @return ResponseEntity&lt;MonitoringStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MonitoringStatus> getMonitoringStatusWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<MonitoringStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMonitoringStatusRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get instance statistics
     * Self-hosted instances only.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMonitoringStatusWithResponseSpec() throws RestClientResponseException {
        return getMonitoringStatusRequestCreation();
    }

    /**
     * Reset monitoring token
     * 
     * <p><b>201</b> - New monitoring token created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return MonitoringToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec resetMonitoringTokenRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<MonitoringToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/monitoring/token", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset monitoring token
     * 
     * <p><b>201</b> - New monitoring token created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return MonitoringToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MonitoringToken resetMonitoringToken(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<MonitoringToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return resetMonitoringTokenRequestCreation(from, body).body(localVarReturnType);
    }

    /**
     * Reset monitoring token
     * 
     * <p><b>201</b> - New monitoring token created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseEntity&lt;MonitoringToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MonitoringToken> resetMonitoringTokenWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<MonitoringToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return resetMonitoringTokenRequestCreation(from, body).toEntity(localVarReturnType);
    }

    /**
     * Reset monitoring token
     * 
     * <p><b>201</b> - New monitoring token created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec resetMonitoringTokenWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return resetMonitoringTokenRequestCreation(from, body);
    }

    /**
     * Restart failed scheduler jobs
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec restartFailedSchedulerJobsRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/monitoring/restart_failed_jobs", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Restart failed scheduler jobs
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object restartFailedSchedulerJobs(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return restartFailedSchedulerJobsRequestCreation(from, body).body(localVarReturnType);
    }

    /**
     * Restart failed scheduler jobs
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> restartFailedSchedulerJobsWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return restartFailedSchedulerJobsRequestCreation(from, body).toEntity(localVarReturnType);
    }

    /**
     * Restart failed scheduler jobs
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec restartFailedSchedulerJobsWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return restartFailedSchedulerJobsRequestCreation(from, body);
    }
}
