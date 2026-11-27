package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiPipelineBasic;
import ai.gebo.gitlab.client.model.APIEntitiesCiPipelineSchedule;
import ai.gebo.gitlab.client.model.APIEntitiesCiPipelineScheduleDetails;
import ai.gebo.gitlab.client.model.APIEntitiesCiVariable;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdPipelineSchedulesRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest;

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
public class PipelineSchedulesApi {
    private ApiClient apiClient;

    public PipelineSchedulesApi() {
        this(new ApiClient());
    }

    public PipelineSchedulesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a pipeline schedule
     * Deletes a pipeline schedule for a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a pipeline schedule
     * Deletes a pipeline schedule for a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(id, pipelineScheduleId).body(localVarReturnType);
    }

    /**
     * Delete a pipeline schedule
     * Deletes a pipeline schedule for a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(id, pipelineScheduleId).toEntity(localVarReturnType);
    }

    /**
     * Delete a pipeline schedule
     * Deletes a pipeline schedule for a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(id, pipelineScheduleId);
    }

    /**
     * Delete a variable for a pipeline schedule
     * Deletes a specified variable for a pipeline schedule.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);
        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}/variables/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a variable for a pipeline schedule
     * Deletes a specified variable for a pipeline schedule.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(id, pipelineScheduleId, key).body(localVarReturnType);
    }

    /**
     * Delete a variable for a pipeline schedule
     * Deletes a specified variable for a pipeline schedule.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(id, pipelineScheduleId, key).toEntity(localVarReturnType);
    }

    /**
     * Delete a variable for a pipeline schedule
     * Deletes a specified variable for a pipeline schedule.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(id, pipelineScheduleId, key);
    }

    /**
     * List all pipeline schedules
     * Lists all pipeline schedules for a project. The &#x60;inputs&#x60; field is included only for users with the Maintainer or Owner role, or for the schedule owner.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Scope of pipeline schedules.
     * @return List&lt;APIEntitiesCiPipelineSchedule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelineSchedulesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelineSchedules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiPipelineSchedule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all pipeline schedules
     * Lists all pipeline schedules for a project. The &#x60;inputs&#x60; field is included only for users with the Maintainer or Owner role, or for the schedule owner.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Scope of pipeline schedules.
     * @return List&lt;APIEntitiesCiPipelineSchedule&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiPipelineSchedule> getApiV4ProjectsIdPipelineSchedules(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiPipelineSchedule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelineSchedulesRequestCreation(id, page, perPage, scope).body(localVarReturnType);
    }

    /**
     * List all pipeline schedules
     * Lists all pipeline schedules for a project. The &#x60;inputs&#x60; field is included only for users with the Maintainer or Owner role, or for the schedule owner.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Scope of pipeline schedules.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiPipelineSchedule&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiPipelineSchedule>> getApiV4ProjectsIdPipelineSchedulesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiPipelineSchedule>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelineSchedulesRequestCreation(id, page, perPage, scope).toEntity(localVarReturnType);
    }

    /**
     * List all pipeline schedules
     * Lists all pipeline schedules for a project. The &#x60;inputs&#x60; field is included only for users with the Maintainer or Owner role, or for the schedule owner.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Scope of pipeline schedules.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelineSchedulesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelineSchedulesRequestCreation(id, page, perPage, scope);
    }

    /**
     * Retrieve a pipeline schedule
     * Retrieves a pipeline schedule for a project. The &#x60;inputs&#x60; and &#x60;variables&#x60; fields are included only for users with the Maintainer or Owner role, or for the schedule owner.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return APIEntitiesCiPipelineScheduleDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelineSchedulesPipelineScheduleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling getApiV4ProjectsIdPipelineSchedulesPipelineScheduleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);

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

        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a pipeline schedule
     * Retrieves a pipeline schedule for a project. The &#x60;inputs&#x60; and &#x60;variables&#x60; fields are included only for users with the Maintainer or Owner role, or for the schedule owner.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return APIEntitiesCiPipelineScheduleDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipelineScheduleDetails getApiV4ProjectsIdPipelineSchedulesPipelineScheduleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(id, pipelineScheduleId).body(localVarReturnType);
    }

    /**
     * Retrieve a pipeline schedule
     * Retrieves a pipeline schedule for a project. The &#x60;inputs&#x60; and &#x60;variables&#x60; fields are included only for users with the Maintainer or Owner role, or for the schedule owner.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return ResponseEntity&lt;APIEntitiesCiPipelineScheduleDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipelineScheduleDetails> getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(id, pipelineScheduleId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a pipeline schedule
     * Retrieves a pipeline schedule for a project. The &#x60;inputs&#x60; and &#x60;variables&#x60; fields are included only for users with the Maintainer or Owner role, or for the schedule owner.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(id, pipelineScheduleId);
    }

    /**
     * List all pipelines triggered by a pipeline schedule
     * Lists all pipelines triggered by a pipeline schedule in a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Return pipelines in the specified scope.
     * @param status Return pipelines with the specified status.
     * @param updatedBefore Return pipelines updated on or before the specified time.
     * @param updatedAfter Return pipelines updated on or after the specified time.
     * @param createdBefore Return pipelines created before the specified time.
     * @param createdAfter Return pipelines created after the specified time.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesCiPipelineBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPipelinesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCiPipelineBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}/pipelines", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all pipelines triggered by a pipeline schedule
     * Lists all pipelines triggered by a pipeline schedule in a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Return pipelines in the specified scope.
     * @param status Return pipelines with the specified status.
     * @param updatedBefore Return pipelines updated on or before the specified time.
     * @param updatedAfter Return pipelines updated on or after the specified time.
     * @param createdBefore Return pipelines created before the specified time.
     * @param createdAfter Return pipelines created after the specified time.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesCiPipelineBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCiPipelineBasic> getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPipelines(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiPipelineBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPipelinesRequestCreation(id, pipelineScheduleId, page, perPage, scope, status, updatedBefore, updatedAfter, createdBefore, createdAfter, sort).body(localVarReturnType);
    }

    /**
     * List all pipelines triggered by a pipeline schedule
     * Lists all pipelines triggered by a pipeline schedule in a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Return pipelines in the specified scope.
     * @param status Return pipelines with the specified status.
     * @param updatedBefore Return pipelines updated on or before the specified time.
     * @param updatedAfter Return pipelines updated on or after the specified time.
     * @param createdBefore Return pipelines created before the specified time.
     * @param createdAfter Return pipelines created after the specified time.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCiPipelineBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCiPipelineBasic>> getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPipelinesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCiPipelineBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPipelinesRequestCreation(id, pipelineScheduleId, page, perPage, scope, status, updatedBefore, updatedAfter, createdBefore, createdAfter, sort).toEntity(localVarReturnType);
    }

    /**
     * List all pipelines triggered by a pipeline schedule
     * Lists all pipelines triggered by a pipeline schedule in a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param scope Return pipelines in the specified scope.
     * @param status Return pipelines with the specified status.
     * @param updatedBefore Return pipelines updated on or before the specified time.
     * @param updatedAfter Return pipelines updated on or after the specified time.
     * @param createdBefore Return pipelines created before the specified time.
     * @param createdAfter Return pipelines created after the specified time.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPipelinesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPipelinesRequestCreation(id, pipelineScheduleId, page, perPage, scope, status, updatedBefore, updatedAfter, createdBefore, createdAfter, sort);
    }

    /**
     * Retrieve a variable for a pipeline schedule
     * Retrieves a specified variable for a pipeline schedule. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);
        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}/variables/{key}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a variable for a pipeline schedule
     * Retrieves a specified variable for a pipeline schedule. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(id, pipelineScheduleId, key).body(localVarReturnType);
    }

    /**
     * Retrieve a variable for a pipeline schedule
     * Retrieves a specified variable for a pipeline schedule. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(id, pipelineScheduleId, key).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a variable for a pipeline schedule
     * Retrieves a specified variable for a pipeline schedule. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return getApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(id, pipelineScheduleId, key);
    }

    /**
     * Create a pipeline schedule
     * Creates a pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPipelineSchedulesRequest The postApiV4ProjectsIdPipelineSchedulesRequest parameter
     * @return APIEntitiesCiPipelineScheduleDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPipelineSchedulesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineSchedulesRequest postApiV4ProjectsIdPipelineSchedulesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPipelineSchedules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdPipelineSchedulesRequest' is set
        if (postApiV4ProjectsIdPipelineSchedulesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdPipelineSchedulesRequest' when calling postApiV4ProjectsIdPipelineSchedules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdPipelineSchedulesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a pipeline schedule
     * Creates a pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPipelineSchedulesRequest The postApiV4ProjectsIdPipelineSchedulesRequest parameter
     * @return APIEntitiesCiPipelineScheduleDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipelineScheduleDetails postApiV4ProjectsIdPipelineSchedules(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineSchedulesRequest postApiV4ProjectsIdPipelineSchedulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelineSchedulesRequestCreation(id, postApiV4ProjectsIdPipelineSchedulesRequest).body(localVarReturnType);
    }

    /**
     * Create a pipeline schedule
     * Creates a pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPipelineSchedulesRequest The postApiV4ProjectsIdPipelineSchedulesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiPipelineScheduleDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipelineScheduleDetails> postApiV4ProjectsIdPipelineSchedulesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineSchedulesRequest postApiV4ProjectsIdPipelineSchedulesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelineSchedulesRequestCreation(id, postApiV4ProjectsIdPipelineSchedulesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a pipeline schedule
     * Creates a pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPipelineSchedulesRequest The postApiV4ProjectsIdPipelineSchedulesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPipelineSchedulesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineSchedulesRequest postApiV4ProjectsIdPipelineSchedulesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdPipelineSchedulesRequestCreation(id, postApiV4ProjectsIdPipelineSchedulesRequest);
    }

    /**
     * Run a pipeline schedule
     * Runs a pipeline schedule immediately. The next scheduled run of this pipeline is not affected. This endpoint has a rate limit of one request per minute for each user for a given pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPlayRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPlay", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPlay", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}/play", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Run a pipeline schedule
     * Runs a pipeline schedule immediately. The next scheduled run of this pipeline is not affected. This endpoint has a rate limit of one request per minute for each user for a given pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPlay(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPlayRequestCreation(id, pipelineScheduleId).body(localVarReturnType);
    }

    /**
     * Run a pipeline schedule
     * Runs a pipeline schedule immediately. The next scheduled run of this pipeline is not affected. This endpoint has a rate limit of one request per minute for each user for a given pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPlayWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPlayRequestCreation(id, pipelineScheduleId).toEntity(localVarReturnType);
    }

    /**
     * Run a pipeline schedule
     * Runs a pipeline schedule immediately. The next scheduled run of this pipeline is not affected. This endpoint has a rate limit of one request per minute for each user for a given pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPlayWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        return postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdPlayRequestCreation(id, pipelineScheduleId);
    }

    /**
     * Create or update ownership of a pipeline schedule
     * Creates or updates the owner of a pipeline schedule for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return APIEntitiesCiPipelineScheduleDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdTakeOwnershipRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdTakeOwnership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdTakeOwnership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);

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

        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}/take_ownership", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or update ownership of a pipeline schedule
     * Creates or updates the owner of a pipeline schedule for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return APIEntitiesCiPipelineScheduleDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipelineScheduleDetails postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdTakeOwnership(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdTakeOwnershipRequestCreation(id, pipelineScheduleId).body(localVarReturnType);
    }

    /**
     * Create or update ownership of a pipeline schedule
     * Creates or updates the owner of a pipeline schedule for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return ResponseEntity&lt;APIEntitiesCiPipelineScheduleDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipelineScheduleDetails> postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdTakeOwnershipWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdTakeOwnershipRequestCreation(id, pipelineScheduleId).toEntity(localVarReturnType);
    }

    /**
     * Create or update ownership of a pipeline schedule
     * Creates or updates the owner of a pipeline schedule for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdTakeOwnershipWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId) throws RestClientResponseException {
        return postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdTakeOwnershipRequestCreation(id, pipelineScheduleId);
    }

    /**
     * Create a variable for a pipeline schedule
     * Creates a variable for a pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest The postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest' is set
        if (postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest' when calling postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariables", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}/variables", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a variable for a pipeline schedule
     * Creates a variable for a pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest The postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariables(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequestCreation(id, pipelineScheduleId, postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest).body(localVarReturnType);
    }

    /**
     * Create a variable for a pipeline schedule
     * Creates a variable for a pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest The postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequestCreation(id, pipelineScheduleId, postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a variable for a pipeline schedule
     * Creates a variable for a pipeline schedule.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest The postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequestCreation(id, pipelineScheduleId, postApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesRequest);
    }

    /**
     * Update a pipeline schedule
     * Updates a pipeline schedule for a project. After the update is done, it is rescheduled automatically.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest The putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest parameter
     * @return APIEntitiesCiPipelineScheduleDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPipelineSchedulesPipelineScheduleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling putApiV4ProjectsIdPipelineSchedulesPipelineScheduleId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);

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

        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a pipeline schedule
     * Updates a pipeline schedule for a project. After the update is done, it is rescheduled automatically.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest The putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest parameter
     * @return APIEntitiesCiPipelineScheduleDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipelineScheduleDetails putApiV4ProjectsIdPipelineSchedulesPipelineScheduleId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(id, pipelineScheduleId, putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest).body(localVarReturnType);
    }

    /**
     * Update a pipeline schedule
     * Updates a pipeline schedule for a project. After the update is done, it is rescheduled automatically.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest The putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiPipelineScheduleDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipelineScheduleDetails> putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipelineScheduleDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(id, pipelineScheduleId, putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a pipeline schedule
     * Updates a pipeline schedule for a project. After the update is done, it is rescheduled automatically.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest The putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequestCreation(id, pipelineScheduleId, putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdRequest);
    }

    /**
     * Update a variable for a pipeline schedule
     * Updates a variable for a pipeline schedule.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest The putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pipelineScheduleId' is set
        if (pipelineScheduleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'pipelineScheduleId' when calling putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("pipeline_schedule_id", pipelineScheduleId);
        pathParams.put("key", key);

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

        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pipeline_schedules/{pipeline_schedule_id}/variables/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a variable for a pipeline schedule
     * Updates a variable for a pipeline schedule.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest The putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest parameter
     * @return APIEntitiesCiVariable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiVariable putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKey(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(id, pipelineScheduleId, key, putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a variable for a pipeline schedule
     * Updates a variable for a pipeline schedule.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest The putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiVariable&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiVariable> putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiVariable> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(id, pipelineScheduleId, key, putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a variable for a pipeline schedule
     * Updates a variable for a pipeline schedule.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param pipelineScheduleId ID of the pipeline schedule.
     * @param key Key of the pipeline schedule variable. Must be 255 characters or fewer and contain only &#x60;A-Z&#x60;, &#x60;a-z&#x60;, &#x60;0-9&#x60;, and &#x60;_&#x60;.
     * @param putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest The putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer pipelineScheduleId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nullable PutApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequestCreation(id, pipelineScheduleId, key, putApiV4ProjectsIdPipelineSchedulesPipelineScheduleIdVariablesKeyRequest);
    }
}
