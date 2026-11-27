package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;


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
public class MetricsApi {
    private ApiClient apiClient;

    public MetricsApi() {
        this(new ApiClient());
    }

    public MetricsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Download metric definitions
     * Downloads all metric definitions as a single YAML file, similar to the [Metrics Dictionary](https://metrics.gitlab.com/), for easier importing.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param includePaths If &#x60;true&#x60;, includes file paths in the metric definitions.
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsageDataMetricDefinitionsRequestCreation(@jakarta.annotation.Nullable Boolean includePaths) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_paths", includePaths));

        final String[] localVarAccepts = { 
            "application/yaml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/usage_data/metric_definitions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download metric definitions
     * Downloads all metric definitions as a single YAML file, similar to the [Metrics Dictionary](https://metrics.gitlab.com/), for easier importing.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param includePaths If &#x60;true&#x60;, includes file paths in the metric definitions.
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public String getApiV4UsageDataMetricDefinitions(@jakarta.annotation.Nullable Boolean includePaths) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsageDataMetricDefinitionsRequestCreation(includePaths).body(localVarReturnType);
    }

    /**
     * Download metric definitions
     * Downloads all metric definitions as a single YAML file, similar to the [Metrics Dictionary](https://metrics.gitlab.com/), for easier importing.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param includePaths If &#x60;true&#x60;, includes file paths in the metric definitions.
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> getApiV4UsageDataMetricDefinitionsWithHttpInfo(@jakarta.annotation.Nullable Boolean includePaths) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsageDataMetricDefinitionsRequestCreation(includePaths).toEntity(localVarReturnType);
    }

    /**
     * Download metric definitions
     * Downloads all metric definitions as a single YAML file, similar to the [Metrics Dictionary](https://metrics.gitlab.com/), for easier importing.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param includePaths If &#x60;true&#x60;, includes file paths in the metric definitions.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsageDataMetricDefinitionsWithResponseSpec(@jakarta.annotation.Nullable Boolean includePaths) throws RestClientResponseException {
        return getApiV4UsageDataMetricDefinitionsRequestCreation(includePaths);
    }
}
