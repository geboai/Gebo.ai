package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesDoraMetric;
import java.time.LocalDate;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;

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
public class DoraMetricsApi {
    private ApiClient apiClient;

    public DoraMetricsApi() {
        this(new ApiClient());
    }

    public DoraMetricsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve group-level DORA metrics
     * Retrieves DORA metrics for a specified group.
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param metric The DORA metric to fetch.
     * @param startDate Start date of the date range. Defaults to 3 months ago.
     * @param endDate End date of the date range. Defaults to the current date.
     * @param interval The bucketing interval.
     * @param environmentTiers The tiers of the environments.
     * @return List&lt;APIEntitiesDoraMetric&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDoraMetricsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String metric, @jakarta.annotation.Nullable LocalDate startDate, @jakarta.annotation.Nullable LocalDate endDate, @jakarta.annotation.Nullable String interval, @jakarta.annotation.Nullable List<String> environmentTiers) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDoraMetrics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metric' is set
        if (metric == null) {
            throw new RestClientResponseException("Missing the required parameter 'metric' when calling getApiV4GroupsIdDoraMetrics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "metric", metric));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_date", startDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "end_date", endDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "interval", interval));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "environment_tiers", environmentTiers));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDoraMetric>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/dora/metrics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve group-level DORA metrics
     * Retrieves DORA metrics for a specified group.
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param metric The DORA metric to fetch.
     * @param startDate Start date of the date range. Defaults to 3 months ago.
     * @param endDate End date of the date range. Defaults to the current date.
     * @param interval The bucketing interval.
     * @param environmentTiers The tiers of the environments.
     * @return List&lt;APIEntitiesDoraMetric&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDoraMetric> getApiV4GroupsIdDoraMetrics(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String metric, @jakarta.annotation.Nullable LocalDate startDate, @jakarta.annotation.Nullable LocalDate endDate, @jakarta.annotation.Nullable String interval, @jakarta.annotation.Nullable List<String> environmentTiers) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDoraMetric>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDoraMetricsRequestCreation(id, metric, startDate, endDate, interval, environmentTiers).body(localVarReturnType);
    }

    /**
     * Retrieve group-level DORA metrics
     * Retrieves DORA metrics for a specified group.
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param metric The DORA metric to fetch.
     * @param startDate Start date of the date range. Defaults to 3 months ago.
     * @param endDate End date of the date range. Defaults to the current date.
     * @param interval The bucketing interval.
     * @param environmentTiers The tiers of the environments.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDoraMetric&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDoraMetric>> getApiV4GroupsIdDoraMetricsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String metric, @jakarta.annotation.Nullable LocalDate startDate, @jakarta.annotation.Nullable LocalDate endDate, @jakarta.annotation.Nullable String interval, @jakarta.annotation.Nullable List<String> environmentTiers) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDoraMetric>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDoraMetricsRequestCreation(id, metric, startDate, endDate, interval, environmentTiers).toEntity(localVarReturnType);
    }

    /**
     * Retrieve group-level DORA metrics
     * Retrieves DORA metrics for a specified group.
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param metric The DORA metric to fetch.
     * @param startDate Start date of the date range. Defaults to 3 months ago.
     * @param endDate End date of the date range. Defaults to the current date.
     * @param interval The bucketing interval.
     * @param environmentTiers The tiers of the environments.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDoraMetricsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String metric, @jakarta.annotation.Nullable LocalDate startDate, @jakarta.annotation.Nullable LocalDate endDate, @jakarta.annotation.Nullable String interval, @jakarta.annotation.Nullable List<String> environmentTiers) throws RestClientResponseException {
        return getApiV4GroupsIdDoraMetricsRequestCreation(id, metric, startDate, endDate, interval, environmentTiers);
    }

    /**
     * Retrieve project-level DORA metrics
     * Retrieves DORA metrics for a specified project.
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param metric The DORA metric to fetch.
     * @param startDate Start date of the date range. Defaults to 3 months ago.
     * @param endDate End date of the date range. Defaults to the current date.
     * @param interval The bucketing interval.
     * @param environmentTiers The tiers of the environments.
     * @return List&lt;APIEntitiesDoraMetric&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDoraMetricsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String metric, @jakarta.annotation.Nullable LocalDate startDate, @jakarta.annotation.Nullable LocalDate endDate, @jakarta.annotation.Nullable String interval, @jakarta.annotation.Nullable List<String> environmentTiers) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDoraMetrics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metric' is set
        if (metric == null) {
            throw new RestClientResponseException("Missing the required parameter 'metric' when calling getApiV4ProjectsIdDoraMetrics", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "metric", metric));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_date", startDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "end_date", endDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "interval", interval));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "environment_tiers", environmentTiers));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDoraMetric>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/dora/metrics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve project-level DORA metrics
     * Retrieves DORA metrics for a specified project.
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param metric The DORA metric to fetch.
     * @param startDate Start date of the date range. Defaults to 3 months ago.
     * @param endDate End date of the date range. Defaults to the current date.
     * @param interval The bucketing interval.
     * @param environmentTiers The tiers of the environments.
     * @return List&lt;APIEntitiesDoraMetric&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDoraMetric> getApiV4ProjectsIdDoraMetrics(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String metric, @jakarta.annotation.Nullable LocalDate startDate, @jakarta.annotation.Nullable LocalDate endDate, @jakarta.annotation.Nullable String interval, @jakarta.annotation.Nullable List<String> environmentTiers) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDoraMetric>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDoraMetricsRequestCreation(id, metric, startDate, endDate, interval, environmentTiers).body(localVarReturnType);
    }

    /**
     * Retrieve project-level DORA metrics
     * Retrieves DORA metrics for a specified project.
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param metric The DORA metric to fetch.
     * @param startDate Start date of the date range. Defaults to 3 months ago.
     * @param endDate End date of the date range. Defaults to the current date.
     * @param interval The bucketing interval.
     * @param environmentTiers The tiers of the environments.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDoraMetric&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDoraMetric>> getApiV4ProjectsIdDoraMetricsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String metric, @jakarta.annotation.Nullable LocalDate startDate, @jakarta.annotation.Nullable LocalDate endDate, @jakarta.annotation.Nullable String interval, @jakarta.annotation.Nullable List<String> environmentTiers) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDoraMetric>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDoraMetricsRequestCreation(id, metric, startDate, endDate, interval, environmentTiers).toEntity(localVarReturnType);
    }

    /**
     * Retrieve project-level DORA metrics
     * Retrieves DORA metrics for a specified project.
     * <p><b>200</b> - successful operation
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param metric The DORA metric to fetch.
     * @param startDate Start date of the date range. Defaults to 3 months ago.
     * @param endDate End date of the date range. Defaults to the current date.
     * @param interval The bucketing interval.
     * @param environmentTiers The tiers of the environments.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDoraMetricsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String metric, @jakarta.annotation.Nullable LocalDate startDate, @jakarta.annotation.Nullable LocalDate endDate, @jakarta.annotation.Nullable String interval, @jakarta.annotation.Nullable List<String> environmentTiers) throws RestClientResponseException {
        return getApiV4ProjectsIdDoraMetricsRequestCreation(id, metric, startDate, endDate, interval, environmentTiers);
    }
}
