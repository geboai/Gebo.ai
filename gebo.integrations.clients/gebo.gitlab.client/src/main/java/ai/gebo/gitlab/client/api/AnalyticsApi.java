package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAnalyticsDeploymentFrequency;
import ai.gebo.gitlab.client.model.APIEntitiesAnalyticsGroupActivityIssuesCount;
import ai.gebo.gitlab.client.model.APIEntitiesAnalyticsGroupActivityMergeRequestsCount;
import ai.gebo.gitlab.client.model.APIEntitiesAnalyticsGroupActivityNewMembersCount;
import java.time.OffsetDateTime;
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
public class AnalyticsApi {
    private ApiClient apiClient;

    public AnalyticsApi() {
        this(new ApiClient());
    }

    public AnalyticsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve count of recently created issues for a group
     * Retrieves the count of recently created issues for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return APIEntitiesAnalyticsGroupActivityIssuesCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AnalyticsGroupActivityIssuesCountRequestCreation(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        // verify the required parameter 'groupPath' is set
        if (groupPath == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupPath' when calling getApiV4AnalyticsGroupActivityIssuesCount", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "group_path", groupPath));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAnalyticsGroupActivityIssuesCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/analytics/group_activity/issues_count", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve count of recently created issues for a group
     * Retrieves the count of recently created issues for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return APIEntitiesAnalyticsGroupActivityIssuesCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAnalyticsGroupActivityIssuesCount getApiV4AnalyticsGroupActivityIssuesCount(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAnalyticsGroupActivityIssuesCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AnalyticsGroupActivityIssuesCountRequestCreation(groupPath).body(localVarReturnType);
    }

    /**
     * Retrieve count of recently created issues for a group
     * Retrieves the count of recently created issues for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return ResponseEntity&lt;APIEntitiesAnalyticsGroupActivityIssuesCount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAnalyticsGroupActivityIssuesCount> getApiV4AnalyticsGroupActivityIssuesCountWithHttpInfo(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAnalyticsGroupActivityIssuesCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AnalyticsGroupActivityIssuesCountRequestCreation(groupPath).toEntity(localVarReturnType);
    }

    /**
     * Retrieve count of recently created issues for a group
     * Retrieves the count of recently created issues for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AnalyticsGroupActivityIssuesCountWithResponseSpec(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        return getApiV4AnalyticsGroupActivityIssuesCountRequestCreation(groupPath);
    }

    /**
     * Retrieve count of recently created merge requests for a group
     * Retrieves the count of recently created merge requests for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return APIEntitiesAnalyticsGroupActivityMergeRequestsCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AnalyticsGroupActivityMergeRequestsCountRequestCreation(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        // verify the required parameter 'groupPath' is set
        if (groupPath == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupPath' when calling getApiV4AnalyticsGroupActivityMergeRequestsCount", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "group_path", groupPath));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAnalyticsGroupActivityMergeRequestsCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/analytics/group_activity/merge_requests_count", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve count of recently created merge requests for a group
     * Retrieves the count of recently created merge requests for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return APIEntitiesAnalyticsGroupActivityMergeRequestsCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAnalyticsGroupActivityMergeRequestsCount getApiV4AnalyticsGroupActivityMergeRequestsCount(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAnalyticsGroupActivityMergeRequestsCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AnalyticsGroupActivityMergeRequestsCountRequestCreation(groupPath).body(localVarReturnType);
    }

    /**
     * Retrieve count of recently created merge requests for a group
     * Retrieves the count of recently created merge requests for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return ResponseEntity&lt;APIEntitiesAnalyticsGroupActivityMergeRequestsCount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAnalyticsGroupActivityMergeRequestsCount> getApiV4AnalyticsGroupActivityMergeRequestsCountWithHttpInfo(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAnalyticsGroupActivityMergeRequestsCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AnalyticsGroupActivityMergeRequestsCountRequestCreation(groupPath).toEntity(localVarReturnType);
    }

    /**
     * Retrieve count of recently created merge requests for a group
     * Retrieves the count of recently created merge requests for a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AnalyticsGroupActivityMergeRequestsCountWithResponseSpec(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        return getApiV4AnalyticsGroupActivityMergeRequestsCountRequestCreation(groupPath);
    }

    /**
     * Retrieve count of members recently added to a group
     * Retrieves the count of members recently added to a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return APIEntitiesAnalyticsGroupActivityNewMembersCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AnalyticsGroupActivityNewMembersCountRequestCreation(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        // verify the required parameter 'groupPath' is set
        if (groupPath == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupPath' when calling getApiV4AnalyticsGroupActivityNewMembersCount", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "group_path", groupPath));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAnalyticsGroupActivityNewMembersCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/analytics/group_activity/new_members_count", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve count of members recently added to a group
     * Retrieves the count of members recently added to a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return APIEntitiesAnalyticsGroupActivityNewMembersCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAnalyticsGroupActivityNewMembersCount getApiV4AnalyticsGroupActivityNewMembersCount(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAnalyticsGroupActivityNewMembersCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AnalyticsGroupActivityNewMembersCountRequestCreation(groupPath).body(localVarReturnType);
    }

    /**
     * Retrieve count of members recently added to a group
     * Retrieves the count of members recently added to a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return ResponseEntity&lt;APIEntitiesAnalyticsGroupActivityNewMembersCount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAnalyticsGroupActivityNewMembersCount> getApiV4AnalyticsGroupActivityNewMembersCountWithHttpInfo(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAnalyticsGroupActivityNewMembersCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AnalyticsGroupActivityNewMembersCountRequestCreation(groupPath).toEntity(localVarReturnType);
    }

    /**
     * Retrieve count of members recently added to a group
     * Retrieves the count of members recently added to a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param groupPath Group path.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AnalyticsGroupActivityNewMembersCountWithResponseSpec(@jakarta.annotation.Nullable String groupPath) throws RestClientResponseException {
        return getApiV4AnalyticsGroupActivityNewMembersCountRequestCreation(groupPath);
    }

    /**
     * List deployment frequencies for the project
     * Retrieves deployment frequency analytics data for a specific project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environment Name of the environment to filter by.
     * @param from Datetime to start from, inclusive.
     * @param to Datetime to end at, exclusive. If omitted, defaults to the current time.
     * @param interval Interval to roll up data by. If omitted, defaults to &#x60;all&#x60;.
     * @return List&lt;APIEntitiesAnalyticsDeploymentFrequency&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAnalyticsDeploymentFrequencyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable OffsetDateTime from, @jakarta.annotation.Nullable OffsetDateTime to, @jakarta.annotation.Nullable String interval) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAnalyticsDeploymentFrequency", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'environment' is set
        if (environment == null) {
            throw new RestClientResponseException("Missing the required parameter 'environment' when calling getApiV4ProjectsIdAnalyticsDeploymentFrequency", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'from' is set
        if (from == null) {
            throw new RestClientResponseException("Missing the required parameter 'from' when calling getApiV4ProjectsIdAnalyticsDeploymentFrequency", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "environment", environment));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "to", to));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "interval", interval));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAnalyticsDeploymentFrequency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/analytics/deployment_frequency", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List deployment frequencies for the project
     * Retrieves deployment frequency analytics data for a specific project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environment Name of the environment to filter by.
     * @param from Datetime to start from, inclusive.
     * @param to Datetime to end at, exclusive. If omitted, defaults to the current time.
     * @param interval Interval to roll up data by. If omitted, defaults to &#x60;all&#x60;.
     * @return List&lt;APIEntitiesAnalyticsDeploymentFrequency&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAnalyticsDeploymentFrequency> getApiV4ProjectsIdAnalyticsDeploymentFrequency(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable OffsetDateTime from, @jakarta.annotation.Nullable OffsetDateTime to, @jakarta.annotation.Nullable String interval) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAnalyticsDeploymentFrequency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAnalyticsDeploymentFrequencyRequestCreation(id, environment, from, to, interval).body(localVarReturnType);
    }

    /**
     * List deployment frequencies for the project
     * Retrieves deployment frequency analytics data for a specific project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environment Name of the environment to filter by.
     * @param from Datetime to start from, inclusive.
     * @param to Datetime to end at, exclusive. If omitted, defaults to the current time.
     * @param interval Interval to roll up data by. If omitted, defaults to &#x60;all&#x60;.
     * @return ResponseEntity&lt;List&lt;APIEntitiesAnalyticsDeploymentFrequency&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAnalyticsDeploymentFrequency>> getApiV4ProjectsIdAnalyticsDeploymentFrequencyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable OffsetDateTime from, @jakarta.annotation.Nullable OffsetDateTime to, @jakarta.annotation.Nullable String interval) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAnalyticsDeploymentFrequency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAnalyticsDeploymentFrequencyRequestCreation(id, environment, from, to, interval).toEntity(localVarReturnType);
    }

    /**
     * List deployment frequencies for the project
     * Retrieves deployment frequency analytics data for a specific project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param environment Name of the environment to filter by.
     * @param from Datetime to start from, inclusive.
     * @param to Datetime to end at, exclusive. If omitted, defaults to the current time.
     * @param interval Interval to roll up data by. If omitted, defaults to &#x60;all&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAnalyticsDeploymentFrequencyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable OffsetDateTime from, @jakarta.annotation.Nullable OffsetDateTime to, @jakarta.annotation.Nullable String interval) throws RestClientResponseException {
        return getApiV4ProjectsIdAnalyticsDeploymentFrequencyRequestCreation(id, environment, from, to, interval);
    }
}
