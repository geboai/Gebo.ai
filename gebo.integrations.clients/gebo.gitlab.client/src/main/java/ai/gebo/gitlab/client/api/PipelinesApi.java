package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiBridge;
import ai.gebo.gitlab.client.model.APIEntitiesCiJob;
import ai.gebo.gitlab.client.model.APIEntitiesCiPipeline;
import ai.gebo.gitlab.client.model.APIEntitiesCiPipelineBasic;
import ai.gebo.gitlab.client.model.APIEntitiesCiPipelineWithMetadata;
import ai.gebo.gitlab.client.model.APIEntitiesCiUserPipeline;
import ai.gebo.gitlab.client.model.APIEntitiesCiVariable;
import ai.gebo.gitlab.client.model.GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdPipelineRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPipelinesPipelineIdMetadataRequest;
import ai.gebo.gitlab.client.model.TestReportEntity;
import ai.gebo.gitlab.client.model.TestReportSummaryEntity;

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
public class PipelinesApi {
    private ApiClient apiClient;

    public PipelinesApi() {
        this(new ApiClient());
    }

    public PipelinesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a pipeline
     * Deletes a specified pipeline for a project. Deleting a pipeline expires all pipeline caches and deletes all immediately related objects, such as jobs, logs, artifacts, and triggers. This action cannot be undone. Child pipelines of the pipeline are not deleted. This endpoint requires the Owner role for the project.
     * <p><b>204</b> - Pipeline was deleted
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPipelinesPipelineIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPipelinesPipelineId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling deleteApiV4ProjectsIdPipelinesPipelineId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a pipeline
     * Deletes a specified pipeline for a project. Deleting a pipeline expires all pipeline caches and deletes all immediately related objects, such as jobs, logs, artifacts, and triggers. This action cannot be undone. Child pipelines of the pipeline are not deleted. This endpoint requires the Owner role for the project.
     * <p><b>204</b> - Pipeline was deleted
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPipelinesPipelineId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPipelinesPipelineIdRequestCreation(id, pipelineId).body(localVarReturnType);
    }

    /**
     * Delete a pipeline
     * Deletes a specified pipeline for a project. Deleting a pipeline expires all pipeline caches and deletes all immediately related objects, such as jobs, logs, artifacts, and triggers. This action cannot be undone. Child pipelines of the pipeline are not deleted. This endpoint requires the Owner role for the project.
     * <p><b>204</b> - Pipeline was deleted
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPipelinesPipelineIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPipelinesPipelineIdRequestCreation(id, pipelineId).toEntity(localVarReturnType);
    }

    /**
     * Delete a pipeline
     * Deletes a specified pipeline for a project. Deleting a pipeline expires all pipeline caches and deletes all immediately related objects, such as jobs, logs, artifacts, and triggers. This action cannot be undone. Child pipelines of the pipeline are not deleted. This endpoint requires the Owner role for the project.
     * <p><b>204</b> - Pipeline was deleted
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too Many Requests
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPipelinesPipelineIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPipelinesPipelineIdRequestCreation(id, pipelineId);
    }

    /**
     * List pipelines triggered by the authenticated user
     * Lists recently created pipelines across all projects that were triggered by the authenticated user. By default, child pipelines are not included in the results. To return child pipelines, set &#x60;source&#x60; to &#x60;parent_pipeline&#x60;. Pipelines from projects the user can no longer access are not included. The &#x60;merge_request&#x60; field is included only for merge request pipelines whose merge request is visible to the user. This endpoint only supports keyset pagination, and returns up to 100 records per page, 20 by default. To retrieve the next page of results, use the URL in the &#x60;Link&#x60; response header. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param source The source of pipelines
     * @param createdBefore Return pipelines created before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param createdAfter Return pipelines created after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param orderBy Return pipelines ordered by &#x60;created_at&#x60;
     * @param sort Return pipelines sorted in &#x60;desc&#x60; order
     * @param cursor Cursor for obtaining the next set of records, from the &#x60;Link&#x60; header of a previous response.
     * @param perPage Number of items per page
     * @return List&lt;APIEntitiesCiUserPipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PipelinesRequestCreation(@jakarta.annotation.Nullable String source, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source", source));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cursor", cursor));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiUserPipeline>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/pipelines", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List pipelines triggered by the authenticated user
     * Lists recently created pipelines across all projects that were triggered by the authenticated user. By default, child pipelines are not included in the results. To return child pipelines, set &#x60;source&#x60; to &#x60;parent_pipeline&#x60;. Pipelines from projects the user can no longer access are not included. The &#x60;merge_request&#x60; field is included only for merge request pipelines whose merge request is visible to the user. This endpoint only supports keyset pagination, and returns up to 100 records per page, 20 by default. To retrieve the next page of results, use the URL in the &#x60;Link&#x60; response header. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param source The source of pipelines
     * @param createdBefore Return pipelines created before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param createdAfter Return pipelines created after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param orderBy Return pipelines ordered by &#x60;created_at&#x60;
     * @param sort Return pipelines sorted in &#x60;desc&#x60; order
     * @param cursor Cursor for obtaining the next set of records, from the &#x60;Link&#x60; header of a previous response.
     * @param perPage Number of items per page
     * @return List&lt;APIEntitiesCiUserPipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiUserPipeline> getApiV4Pipelines(@jakarta.annotation.Nullable String source, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiUserPipeline>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PipelinesRequestCreation(source, createdBefore, createdAfter, orderBy, sort, cursor, perPage).body(localVarReturnType);
    }

    /**
     * List pipelines triggered by the authenticated user
     * Lists recently created pipelines across all projects that were triggered by the authenticated user. By default, child pipelines are not included in the results. To return child pipelines, set &#x60;source&#x60; to &#x60;parent_pipeline&#x60;. Pipelines from projects the user can no longer access are not included. The &#x60;merge_request&#x60; field is included only for merge request pipelines whose merge request is visible to the user. This endpoint only supports keyset pagination, and returns up to 100 records per page, 20 by default. To retrieve the next page of results, use the URL in the &#x60;Link&#x60; response header. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param source The source of pipelines
     * @param createdBefore Return pipelines created before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param createdAfter Return pipelines created after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param orderBy Return pipelines ordered by &#x60;created_at&#x60;
     * @param sort Return pipelines sorted in &#x60;desc&#x60; order
     * @param cursor Cursor for obtaining the next set of records, from the &#x60;Link&#x60; header of a previous response.
     * @param perPage Number of items per page
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiUserPipeline&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiUserPipeline>> getApiV4PipelinesWithHttpInfo(@jakarta.annotation.Nullable String source, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiUserPipeline>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PipelinesRequestCreation(source, createdBefore, createdAfter, orderBy, sort, cursor, perPage).toEntity(localVarReturnType);
    }

    /**
     * List pipelines triggered by the authenticated user
     * Lists recently created pipelines across all projects that were triggered by the authenticated user. By default, child pipelines are not included in the results. To return child pipelines, set &#x60;source&#x60; to &#x60;parent_pipeline&#x60;. Pipelines from projects the user can no longer access are not included. The &#x60;merge_request&#x60; field is included only for merge request pipelines whose merge request is visible to the user. This endpoint only supports keyset pagination, and returns up to 100 records per page, 20 by default. To retrieve the next page of results, use the URL in the &#x60;Link&#x60; response header. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param source The source of pipelines
     * @param createdBefore Return pipelines created before the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param createdAfter Return pipelines created after the specified datetime. Format: ISO 8601 YYYY-MM-DDTHH:MM:SSZ
     * @param orderBy Return pipelines ordered by &#x60;created_at&#x60;
     * @param sort Return pipelines sorted in &#x60;desc&#x60; order
     * @param cursor Cursor for obtaining the next set of records, from the &#x60;Link&#x60; header of a previous response.
     * @param perPage Number of items per page
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PipelinesWithResponseSpec(@jakarta.annotation.Nullable String source, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String cursor, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4PipelinesRequestCreation(source, createdBefore, createdAfter, orderBy, sort, cursor, perPage);
    }

    /**
     * List all project pipelines
     * Lists all pipelines in a project. By default, child pipelines are not included in the results. To return child pipelines, set &#x60;source&#x60; to &#x60;parent_pipeline&#x60;. Child pipelines are also returned when requested by &#x60;iids&#x60; or &#x60;ids&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Return pipelines in the specified scope. &#x60;branches&#x60; and &#x60;tags&#x60; return only the latest pipeline for each branch or tag.
     * @param status Return pipelines with the specified status.
     * @param ref Return pipelines for the specified branch or tag.
     * @param sha Return pipelines for the specified commit SHA.
     * @param yamlErrors If &#x60;true&#x60;, returns only pipelines with invalid configurations.
     * @param username Return pipelines triggered by the specified username.
     * @param updatedBefore Return pipelines updated on or before the specified time.
     * @param updatedAfter Return pipelines updated on or after the specified time.
     * @param createdBefore Return pipelines created before the specified time.
     * @param createdAfter Return pipelines created after the specified time.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param source The source of pipelines
     * @param name Return pipelines with the specified name.
     * @param iids Return pipelines with the specified IIDs. Child pipelines are included when requested by IID.
     * @param ids Return pipelines with the specified IDs. Child pipelines are included when requested by ID.
     * @return List&lt;APIEntitiesCiPipelineBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelinesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable Boolean yamlErrors, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String source, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable List<Integer> ids) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sha", sha));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "yaml_errors", yamlErrors));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "username", username));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source", source));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "iids", iids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "ids", ids));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiPipelineBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project pipelines
     * Lists all pipelines in a project. By default, child pipelines are not included in the results. To return child pipelines, set &#x60;source&#x60; to &#x60;parent_pipeline&#x60;. Child pipelines are also returned when requested by &#x60;iids&#x60; or &#x60;ids&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Return pipelines in the specified scope. &#x60;branches&#x60; and &#x60;tags&#x60; return only the latest pipeline for each branch or tag.
     * @param status Return pipelines with the specified status.
     * @param ref Return pipelines for the specified branch or tag.
     * @param sha Return pipelines for the specified commit SHA.
     * @param yamlErrors If &#x60;true&#x60;, returns only pipelines with invalid configurations.
     * @param username Return pipelines triggered by the specified username.
     * @param updatedBefore Return pipelines updated on or before the specified time.
     * @param updatedAfter Return pipelines updated on or after the specified time.
     * @param createdBefore Return pipelines created before the specified time.
     * @param createdAfter Return pipelines created after the specified time.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param source The source of pipelines
     * @param name Return pipelines with the specified name.
     * @param iids Return pipelines with the specified IIDs. Child pipelines are included when requested by IID.
     * @param ids Return pipelines with the specified IDs. Child pipelines are included when requested by ID.
     * @return List&lt;APIEntitiesCiPipelineBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiPipelineBasic> getApiV4ProjectsIdPipelines(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable Boolean yamlErrors, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String source, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable List<Integer> ids) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiPipelineBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesRequestCreation(id, page, perPage, scope, status, ref, sha, yamlErrors, username, updatedBefore, updatedAfter, createdBefore, createdAfter, orderBy, sort, source, name, iids, ids).body(localVarReturnType);
    }

    /**
     * List all project pipelines
     * Lists all pipelines in a project. By default, child pipelines are not included in the results. To return child pipelines, set &#x60;source&#x60; to &#x60;parent_pipeline&#x60;. Child pipelines are also returned when requested by &#x60;iids&#x60; or &#x60;ids&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Return pipelines in the specified scope. &#x60;branches&#x60; and &#x60;tags&#x60; return only the latest pipeline for each branch or tag.
     * @param status Return pipelines with the specified status.
     * @param ref Return pipelines for the specified branch or tag.
     * @param sha Return pipelines for the specified commit SHA.
     * @param yamlErrors If &#x60;true&#x60;, returns only pipelines with invalid configurations.
     * @param username Return pipelines triggered by the specified username.
     * @param updatedBefore Return pipelines updated on or before the specified time.
     * @param updatedAfter Return pipelines updated on or after the specified time.
     * @param createdBefore Return pipelines created before the specified time.
     * @param createdAfter Return pipelines created after the specified time.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param source The source of pipelines
     * @param name Return pipelines with the specified name.
     * @param iids Return pipelines with the specified IIDs. Child pipelines are included when requested by IID.
     * @param ids Return pipelines with the specified IDs. Child pipelines are included when requested by ID.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiPipelineBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiPipelineBasic>> getApiV4ProjectsIdPipelinesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable Boolean yamlErrors, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String source, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable List<Integer> ids) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiPipelineBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesRequestCreation(id, page, perPage, scope, status, ref, sha, yamlErrors, username, updatedBefore, updatedAfter, createdBefore, createdAfter, orderBy, sort, source, name, iids, ids).toEntity(localVarReturnType);
    }

    /**
     * List all project pipelines
     * Lists all pipelines in a project. By default, child pipelines are not included in the results. To return child pipelines, set &#x60;source&#x60; to &#x60;parent_pipeline&#x60;. Child pipelines are also returned when requested by &#x60;iids&#x60; or &#x60;ids&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Return pipelines in the specified scope. &#x60;branches&#x60; and &#x60;tags&#x60; return only the latest pipeline for each branch or tag.
     * @param status Return pipelines with the specified status.
     * @param ref Return pipelines for the specified branch or tag.
     * @param sha Return pipelines for the specified commit SHA.
     * @param yamlErrors If &#x60;true&#x60;, returns only pipelines with invalid configurations.
     * @param username Return pipelines triggered by the specified username.
     * @param updatedBefore Return pipelines updated on or before the specified time.
     * @param updatedAfter Return pipelines updated on or after the specified time.
     * @param createdBefore Return pipelines created before the specified time.
     * @param createdAfter Return pipelines created after the specified time.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param source The source of pipelines
     * @param name Return pipelines with the specified name.
     * @param iids Return pipelines with the specified IIDs. Child pipelines are included when requested by IID.
     * @param ids Return pipelines with the specified IDs. Child pipelines are included when requested by ID.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelinesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String sha, @jakarta.annotation.Nullable Boolean yamlErrors, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String source, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable List<Integer> ids) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelinesRequestCreation(id, page, perPage, scope, status, ref, sha, yamlErrors, username, updatedBefore, updatedAfter, createdBefore, createdAfter, orderBy, sort, source, name, iids, ids);
    }

    /**
     * Retrieve the latest pipeline
     * Retrieves the latest pipeline for the most recent commit on a specified ref in a project. If no pipeline exists for the commit, a &#x60;403&#x60; status code is returned. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param ref Branch or tag to check for the latest pipeline. If omitted, uses the default branch.
     * @return APIEntitiesCiPipelineWithMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelinesLatestRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String ref) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelinesLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesCiPipelineWithMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/latest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the latest pipeline
     * Retrieves the latest pipeline for the most recent commit on a specified ref in a project. If no pipeline exists for the commit, a &#x60;403&#x60; status code is returned. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param ref Branch or tag to check for the latest pipeline. If omitted, uses the default branch.
     * @return APIEntitiesCiPipelineWithMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipelineWithMetadata getApiV4ProjectsIdPipelinesLatest(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String ref) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineWithMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesLatestRequestCreation(id, ref).body(localVarReturnType);
    }

    /**
     * Retrieve the latest pipeline
     * Retrieves the latest pipeline for the most recent commit on a specified ref in a project. If no pipeline exists for the commit, a &#x60;403&#x60; status code is returned. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param ref Branch or tag to check for the latest pipeline. If omitted, uses the default branch.
     * @return ResponseEntity&lt;APIEntitiesCiPipelineWithMetadata&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipelineWithMetadata> getApiV4ProjectsIdPipelinesLatestWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String ref) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineWithMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesLatestRequestCreation(id, ref).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the latest pipeline
     * Retrieves the latest pipeline for the most recent commit on a specified ref in a project. If no pipeline exists for the commit, a &#x60;403&#x60; status code is returned. Use the &#x60;page&#x60; and &#x60;per_page&#x60; pagination parameters to control the pagination of results.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param ref Branch or tag to check for the latest pipeline. If omitted, uses the default branch.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelinesLatestWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable String ref) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelinesLatestRequestCreation(id, ref);
    }

    /**
     * Retrieve a pipeline
     * Retrieves a specified pipeline from a project. You can also get a child pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return APIEntitiesCiPipelineWithMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelinesPipelineId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling getApiV4ProjectsIdPipelinesPipelineId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

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

        ParameterizedTypeReference<APIEntitiesCiPipelineWithMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a pipeline
     * Retrieves a specified pipeline from a project. You can also get a child pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return APIEntitiesCiPipelineWithMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipelineWithMetadata getApiV4ProjectsIdPipelinesPipelineId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineWithMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdRequestCreation(id, pipelineId).body(localVarReturnType);
    }

    /**
     * Retrieve a pipeline
     * Retrieves a specified pipeline from a project. You can also get a child pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseEntity&lt;APIEntitiesCiPipelineWithMetadata&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipelineWithMetadata> getApiV4ProjectsIdPipelinesPipelineIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineWithMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdRequestCreation(id, pipelineId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a pipeline
     * Retrieves a specified pipeline from a project. You can also get a child pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelinesPipelineIdRequestCreation(id, pipelineId);
    }

    /**
     * List all bridge jobs by pipeline
     * Deprecated in GitLab 19.2. Use trigger_jobs endpoint instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiBridge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdBridgesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelinesPipelineIdBridges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling getApiV4ProjectsIdPipelinesPipelineIdBridges", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiBridge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}/bridges", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all bridge jobs by pipeline
     * Deprecated in GitLab 19.2. Use trigger_jobs endpoint instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiBridge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public List<APIEntitiesCiBridge> getApiV4ProjectsIdPipelinesPipelineIdBridges(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiBridge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdBridgesRequestCreation(id, pipelineId, scope, page, perPage).body(localVarReturnType);
    }

    /**
     * List all bridge jobs by pipeline
     * Deprecated in GitLab 19.2. Use trigger_jobs endpoint instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiBridge&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<List<APIEntitiesCiBridge>> getApiV4ProjectsIdPipelinesPipelineIdBridgesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiBridge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdBridgesRequestCreation(id, pipelineId, scope, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all bridge jobs by pipeline
     * Deprecated in GitLab 19.2. Use trigger_jobs endpoint instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdBridgesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelinesPipelineIdBridgesRequestCreation(id, pipelineId, scope, page, perPage);
    }

    /**
     * List all jobs by pipeline
     * Lists all jobs for a specified pipeline, including a child pipeline. Retried jobs are not included by default. Jobs are sorted by ID in descending order.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline. In a CI/CD job, the &#x60;CI_PIPELINE_ID&#x60; predefined variable holds this value.
     * @param includeRetried If &#x60;true&#x60;, includes retried jobs in the response.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJob&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdJobsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable Boolean includeRetried, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelinesPipelineIdJobs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling getApiV4ProjectsIdPipelinesPipelineIdJobs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_retried", includeRetried));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiJob>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}/jobs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all jobs by pipeline
     * Lists all jobs for a specified pipeline, including a child pipeline. Retried jobs are not included by default. Jobs are sorted by ID in descending order.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline. In a CI/CD job, the &#x60;CI_PIPELINE_ID&#x60; predefined variable holds this value.
     * @param includeRetried If &#x60;true&#x60;, includes retried jobs in the response.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiJob&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiJob> getApiV4ProjectsIdPipelinesPipelineIdJobs(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable Boolean includeRetried, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJob>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdJobsRequestCreation(id, pipelineId, includeRetried, scope, page, perPage).body(localVarReturnType);
    }

    /**
     * List all jobs by pipeline
     * Lists all jobs for a specified pipeline, including a child pipeline. Retried jobs are not included by default. Jobs are sorted by ID in descending order.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline. In a CI/CD job, the &#x60;CI_PIPELINE_ID&#x60; predefined variable holds this value.
     * @param includeRetried If &#x60;true&#x60;, includes retried jobs in the response.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiJob&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiJob>> getApiV4ProjectsIdPipelinesPipelineIdJobsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable Boolean includeRetried, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiJob>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdJobsRequestCreation(id, pipelineId, includeRetried, scope, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all jobs by pipeline
     * Lists all jobs for a specified pipeline, including a child pipeline. Retried jobs are not included by default. Jobs are sorted by ID in descending order.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline. In a CI/CD job, the &#x60;CI_PIPELINE_ID&#x60; predefined variable holds this value.
     * @param includeRetried If &#x60;true&#x60;, includes retried jobs in the response.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdJobsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable Boolean includeRetried, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelinesPipelineIdJobsRequestCreation(id, pipelineId, includeRetried, scope, page, perPage);
    }

    /**
     * Retrieve a test report for a pipeline
     * Retrieves a test report for a pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return TestReportEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdTestReportRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelinesPipelineIdTestReport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling getApiV4ProjectsIdPipelinesPipelineIdTestReport", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

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

        ParameterizedTypeReference<TestReportEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}/test_report", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a test report for a pipeline
     * Retrieves a test report for a pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return TestReportEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TestReportEntity getApiV4ProjectsIdPipelinesPipelineIdTestReport(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<TestReportEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdTestReportRequestCreation(id, pipelineId).body(localVarReturnType);
    }

    /**
     * Retrieve a test report for a pipeline
     * Retrieves a test report for a pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseEntity&lt;TestReportEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TestReportEntity> getApiV4ProjectsIdPipelinesPipelineIdTestReportWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<TestReportEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdTestReportRequestCreation(id, pipelineId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a test report for a pipeline
     * Retrieves a test report for a pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdTestReportWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelinesPipelineIdTestReportRequestCreation(id, pipelineId);
    }

    /**
     * Retrieve a test report summary for a pipeline
     * Retrieves a test report summary for a pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return TestReportSummaryEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdTestReportSummaryRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelinesPipelineIdTestReportSummary", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling getApiV4ProjectsIdPipelinesPipelineIdTestReportSummary", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

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

        ParameterizedTypeReference<TestReportSummaryEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}/test_report_summary", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a test report summary for a pipeline
     * Retrieves a test report summary for a pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return TestReportSummaryEntity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TestReportSummaryEntity getApiV4ProjectsIdPipelinesPipelineIdTestReportSummary(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<TestReportSummaryEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdTestReportSummaryRequestCreation(id, pipelineId).body(localVarReturnType);
    }

    /**
     * Retrieve a test report summary for a pipeline
     * Retrieves a test report summary for a pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseEntity&lt;TestReportSummaryEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TestReportSummaryEntity> getApiV4ProjectsIdPipelinesPipelineIdTestReportSummaryWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<TestReportSummaryEntity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdTestReportSummaryRequestCreation(id, pipelineId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a test report summary for a pipeline
     * Retrieves a test report summary for a pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdTestReportSummaryWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelinesPipelineIdTestReportSummaryRequestCreation(id, pipelineId);
    }

    /**
     * List all trigger jobs by pipeline
     * Lists all trigger jobs for a specified pipeline. This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiBridge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdTriggerJobsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelinesPipelineIdTriggerJobs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling getApiV4ProjectsIdPipelinesPipelineIdTriggerJobs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiBridge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}/trigger_jobs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all trigger jobs by pipeline
     * Lists all trigger jobs for a specified pipeline. This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCiBridge&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiBridge> getApiV4ProjectsIdPipelinesPipelineIdTriggerJobs(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiBridge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdTriggerJobsRequestCreation(id, pipelineId, scope, page, perPage).body(localVarReturnType);
    }

    /**
     * List all trigger jobs by pipeline
     * Lists all trigger jobs for a specified pipeline. This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiBridge&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiBridge>> getApiV4ProjectsIdPipelinesPipelineIdTriggerJobsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiBridge>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdTriggerJobsRequestCreation(id, pipelineId, scope, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all trigger jobs by pipeline
     * Lists all trigger jobs for a specified pipeline. This feature was introduced in GitLab 19.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param scope Scope of jobs to show. Accepts a single job status or an array of job statuses. If omitted, returns all jobs.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdTriggerJobsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nullable GetApiV4ProjectsIdPipelinesPipelineIdJobsScopeParameter scope, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelinesPipelineIdTriggerJobsRequestCreation(id, pipelineId, scope, page, perPage);
    }

    /**
     * List all pipeline variables
     * Lists all pipeline variables for a specified pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return List&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdVariablesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelinesPipelineIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling getApiV4ProjectsIdPipelinesPipelineIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

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

        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}/variables", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all pipeline variables
     * Lists all pipeline variables for a specified pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return List&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiVariable> getApiV4ProjectsIdPipelinesPipelineIdVariables(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdVariablesRequestCreation(id, pipelineId).body(localVarReturnType);
    }

    /**
     * List all pipeline variables
     * Lists all pipeline variables for a specified pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiVariable&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiVariable>> getApiV4ProjectsIdPipelinesPipelineIdVariablesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiVariable>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelinesPipelineIdVariablesRequestCreation(id, pipelineId).toEntity(localVarReturnType);
    }

    /**
     * List all pipeline variables
     * Lists all pipeline variables for a specified pipeline.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelinesPipelineIdVariablesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelinesPipelineIdVariablesRequestCreation(id, pipelineId);
    }

    /**
     * Create a pipeline
     * Creates a pipeline in the specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPipelineRequest The postApiV4ProjectsIdPipelineRequest parameter
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPipelineRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineRequest postApiV4ProjectsIdPipelineRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPipeline", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdPipelineRequest' is set
        if (postApiV4ProjectsIdPipelineRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdPipelineRequest' when calling postApiV4ProjectsIdPipeline", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdPipelineRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a pipeline
     * Creates a pipeline in the specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPipelineRequest The postApiV4ProjectsIdPipelineRequest parameter
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipeline postApiV4ProjectsIdPipeline(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineRequest postApiV4ProjectsIdPipelineRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelineRequestCreation(id, postApiV4ProjectsIdPipelineRequest).body(localVarReturnType);
    }

    /**
     * Create a pipeline
     * Creates a pipeline in the specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPipelineRequest The postApiV4ProjectsIdPipelineRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiPipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipeline> postApiV4ProjectsIdPipelineWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineRequest postApiV4ProjectsIdPipelineRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelineRequestCreation(id, postApiV4ProjectsIdPipelineRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a pipeline
     * Creates a pipeline in the specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPipelineRequest The postApiV4ProjectsIdPipelineRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPipelineWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineRequest postApiV4ProjectsIdPipelineRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdPipelineRequestCreation(id, postApiV4ProjectsIdPipelineRequest);
    }

    /**
     * Cancel all jobs for a pipeline
     * Cancels all jobs in a specified pipeline. If the pipeline cancellation rate limit is exceeded, returns &#x60;429 Too Many Requests&#x60; with a &#x60;Retry-After&#x60; header.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPipelinesPipelineIdCancelRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPipelinesPipelineIdCancel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling postApiV4ProjectsIdPipelinesPipelineIdCancel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

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

        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}/cancel", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Cancel all jobs for a pipeline
     * Cancels all jobs in a specified pipeline. If the pipeline cancellation rate limit is exceeded, returns &#x60;429 Too Many Requests&#x60; with a &#x60;Retry-After&#x60; header.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipeline postApiV4ProjectsIdPipelinesPipelineIdCancel(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelinesPipelineIdCancelRequestCreation(id, pipelineId).body(localVarReturnType);
    }

    /**
     * Cancel all jobs for a pipeline
     * Cancels all jobs in a specified pipeline. If the pipeline cancellation rate limit is exceeded, returns &#x60;429 Too Many Requests&#x60; with a &#x60;Retry-After&#x60; header.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseEntity&lt;APIEntitiesCiPipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipeline> postApiV4ProjectsIdPipelinesPipelineIdCancelWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelinesPipelineIdCancelRequestCreation(id, pipelineId).toEntity(localVarReturnType);
    }

    /**
     * Cancel all jobs for a pipeline
     * Cancels all jobs in a specified pipeline. If the pipeline cancellation rate limit is exceeded, returns &#x60;429 Too Many Requests&#x60; with a &#x60;Retry-After&#x60; header.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPipelinesPipelineIdCancelWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        return postApiV4ProjectsIdPipelinesPipelineIdCancelRequestCreation(id, pipelineId);
    }

    /**
     * Retry jobs in a pipeline
     * Retries failed or canceled jobs in a pipeline. If there are no failed or canceled jobs in the pipeline, calling this endpoint has no effect. Only one retry of a pipeline runs at a time. If a retry of the same pipeline is in progress, returns &#x60;409 Conflict&#x60;. Wait for the retry to finish, then send the request again.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPipelinesPipelineIdRetryRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPipelinesPipelineIdRetry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling postApiV4ProjectsIdPipelinesPipelineIdRetry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

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

        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}/retry", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retry jobs in a pipeline
     * Retries failed or canceled jobs in a pipeline. If there are no failed or canceled jobs in the pipeline, calling this endpoint has no effect. Only one retry of a pipeline runs at a time. If a retry of the same pipeline is in progress, returns &#x60;409 Conflict&#x60;. Wait for the retry to finish, then send the request again.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipeline postApiV4ProjectsIdPipelinesPipelineIdRetry(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelinesPipelineIdRetryRequestCreation(id, pipelineId).body(localVarReturnType);
    }

    /**
     * Retry jobs in a pipeline
     * Retries failed or canceled jobs in a pipeline. If there are no failed or canceled jobs in the pipeline, calling this endpoint has no effect. Only one retry of a pipeline runs at a time. If a retry of the same pipeline is in progress, returns &#x60;409 Conflict&#x60;. Wait for the retry to finish, then send the request again.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseEntity&lt;APIEntitiesCiPipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipeline> postApiV4ProjectsIdPipelinesPipelineIdRetryWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelinesPipelineIdRetryRequestCreation(id, pipelineId).toEntity(localVarReturnType);
    }

    /**
     * Retry jobs in a pipeline
     * Retries failed or canceled jobs in a pipeline. If there are no failed or canceled jobs in the pipeline, calling this endpoint has no effect. Only one retry of a pipeline runs at a time. If a retry of the same pipeline is in progress, returns &#x60;409 Conflict&#x60;. Wait for the retry to finish, then send the request again.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPipelinesPipelineIdRetryWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId) throws RestClientResponseException {
        return postApiV4ProjectsIdPipelinesPipelineIdRetryRequestCreation(id, pipelineId);
    }

    /**
     * Update pipeline metadata
     * Updates pipeline metadata. The metadata contains the name of the pipeline. This feature was introduced in GitLab 16.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest The putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest parameter
     * @return APIEntitiesCiPipelineWithMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPipelinesPipelineIdMetadataRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdPipelinesPipelineIdMetadataRequest putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPipelinesPipelineIdMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineId' is set
        if (pipelineId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineId' when calling putApiV4ProjectsIdPipelinesPipelineIdMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest' is set
        if (putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest' when calling putApiV4ProjectsIdPipelinesPipelineIdMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_id", pipelineId);

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

        ParameterizedTypeReference<APIEntitiesCiPipelineWithMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipelines/{pipeline_id}/metadata", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update pipeline metadata
     * Updates pipeline metadata. The metadata contains the name of the pipeline. This feature was introduced in GitLab 16.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest The putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest parameter
     * @return APIEntitiesCiPipelineWithMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipelineWithMetadata putApiV4ProjectsIdPipelinesPipelineIdMetadata(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdPipelinesPipelineIdMetadataRequest putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineWithMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPipelinesPipelineIdMetadataRequestCreation(id, pipelineId, putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest).body(localVarReturnType);
    }

    /**
     * Update pipeline metadata
     * Updates pipeline metadata. The metadata contains the name of the pipeline. This feature was introduced in GitLab 16.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest The putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiPipelineWithMetadata&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipelineWithMetadata> putApiV4ProjectsIdPipelinesPipelineIdMetadataWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdPipelinesPipelineIdMetadataRequest putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineWithMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPipelinesPipelineIdMetadataRequestCreation(id, pipelineId, putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest).toEntity(localVarReturnType);
    }

    /**
     * Update pipeline metadata
     * Updates pipeline metadata. The metadata contains the name of the pipeline. This feature was introduced in GitLab 16.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineId ID of the pipeline.
     * @param putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest The putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPipelinesPipelineIdMetadataWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer pipelineId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdPipelinesPipelineIdMetadataRequest putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPipelinesPipelineIdMetadataRequestCreation(id, pipelineId, putApiV4ProjectsIdPipelinesPipelineIdMetadataRequest);
    }
}
