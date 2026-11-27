package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAnalyticsCodeReviewMergeRequest;

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
public class CodeReviewAnalyticsApi {
    private ApiClient apiClient;

    public CodeReviewAnalyticsApi() {
        this(new ApiClient());
    }

    public CodeReviewAnalyticsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List code review information about project
     * Retrieves code review analytics data for a specific project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param labelName Array of label names to filter by
     * @param milestoneTitle Milestone title to filter by
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return merge requests that do not match the specified parameters.
     * @param notLabelName Array of label names to filter by
     * @param notMilestoneTitle Milestone title to filter by
     * @return List&lt;APIEntitiesAnalyticsCodeReviewMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AnalyticsCodeReviewRequestCreation(@jakarta.annotation.Nullable Integer projectId, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable String milestoneTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabelName, @jakarta.annotation.Nullable String notMilestoneTitle) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getApiV4AnalyticsCodeReview", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project_id", projectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "label_name", labelName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone_title", milestoneTitle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[label_name]", notLabelName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone_title]", notMilestoneTitle));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAnalyticsCodeReviewMergeRequest>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/analytics/code_review", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List code review information about project
     * Retrieves code review analytics data for a specific project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param labelName Array of label names to filter by
     * @param milestoneTitle Milestone title to filter by
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return merge requests that do not match the specified parameters.
     * @param notLabelName Array of label names to filter by
     * @param notMilestoneTitle Milestone title to filter by
     * @return List&lt;APIEntitiesAnalyticsCodeReviewMergeRequest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAnalyticsCodeReviewMergeRequest> getApiV4AnalyticsCodeReview(@jakarta.annotation.Nullable Integer projectId, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable String milestoneTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabelName, @jakarta.annotation.Nullable String notMilestoneTitle) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAnalyticsCodeReviewMergeRequest>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AnalyticsCodeReviewRequestCreation(projectId, labelName, milestoneTitle, page, perPage, not, notLabelName, notMilestoneTitle).body(localVarReturnType);
    }

    /**
     * List code review information about project
     * Retrieves code review analytics data for a specific project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param labelName Array of label names to filter by
     * @param milestoneTitle Milestone title to filter by
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return merge requests that do not match the specified parameters.
     * @param notLabelName Array of label names to filter by
     * @param notMilestoneTitle Milestone title to filter by
     * @return ResponseEntity&lt;List&lt;APIEntitiesAnalyticsCodeReviewMergeRequest&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAnalyticsCodeReviewMergeRequest>> getApiV4AnalyticsCodeReviewWithHttpInfo(@jakarta.annotation.Nullable Integer projectId, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable String milestoneTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabelName, @jakarta.annotation.Nullable String notMilestoneTitle) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAnalyticsCodeReviewMergeRequest>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AnalyticsCodeReviewRequestCreation(projectId, labelName, milestoneTitle, page, perPage, not, notLabelName, notMilestoneTitle).toEntity(localVarReturnType);
    }

    /**
     * List code review information about project
     * Retrieves code review analytics data for a specific project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param labelName Array of label names to filter by
     * @param milestoneTitle Milestone title to filter by
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param not Return merge requests that do not match the specified parameters.
     * @param notLabelName Array of label names to filter by
     * @param notMilestoneTitle Milestone title to filter by
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AnalyticsCodeReviewWithResponseSpec(@jakarta.annotation.Nullable Integer projectId, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable String milestoneTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabelName, @jakarta.annotation.Nullable String notMilestoneTitle) throws RestClientResponseException {
        return getApiV4AnalyticsCodeReviewRequestCreation(projectId, labelName, milestoneTitle, page, perPage, not, notLabelName, notMilestoneTitle);
    }
}
