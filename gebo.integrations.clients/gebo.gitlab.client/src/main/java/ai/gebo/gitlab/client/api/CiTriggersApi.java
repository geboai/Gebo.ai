package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiPipeline;
import ai.gebo.gitlab.client.model.APIEntitiesTrigger;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRefRefTriggerPipelineRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdTriggerPipelineRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdTriggersRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdTriggersTriggerIdRequest;

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
public class CiTriggersApi {
    private ApiClient apiClient;

    public CiTriggersApi() {
        this(new ApiClient());
    }

    public CiTriggersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a pipeline trigger token
     * Deletes a pipeline trigger token for a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdTriggersTriggerIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdTriggersTriggerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'triggerId' is set
        if (triggerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'triggerId' when calling deleteApiV4ProjectsIdTriggersTriggerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("trigger_id", triggerId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/triggers/{trigger_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a pipeline trigger token
     * Deletes a pipeline trigger token for a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdTriggersTriggerId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdTriggersTriggerIdRequestCreation(id, triggerId).body(localVarReturnType);
    }

    /**
     * Delete a pipeline trigger token
     * Deletes a pipeline trigger token for a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdTriggersTriggerIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdTriggersTriggerIdRequestCreation(id, triggerId).toEntity(localVarReturnType);
    }

    /**
     * Delete a pipeline trigger token
     * Deletes a pipeline trigger token for a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>412</b> - Precondition Failed
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdTriggersTriggerIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdTriggersTriggerIdRequestCreation(id, triggerId);
    }

    /**
     * List all project trigger tokens
     * Lists all pipeline trigger tokens for a specified project. The trigger token is displayed in full if the trigger token was created by the authenticated user. Trigger tokens created by other users are shortened to four characters.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTrigger&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdTriggersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdTriggers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesTrigger>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/triggers", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project trigger tokens
     * Lists all pipeline trigger tokens for a specified project. The trigger token is displayed in full if the trigger token was created by the authenticated user. Trigger tokens created by other users are shortened to four characters.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTrigger&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesTrigger> getApiV4ProjectsIdTriggers(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTrigger>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTriggersRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project trigger tokens
     * Lists all pipeline trigger tokens for a specified project. The trigger token is displayed in full if the trigger token was created by the authenticated user. Trigger tokens created by other users are shortened to four characters.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesTrigger&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesTrigger>> getApiV4ProjectsIdTriggersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTrigger>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTriggersRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project trigger tokens
     * Lists all pipeline trigger tokens for a specified project. The trigger token is displayed in full if the trigger token was created by the authenticated user. Trigger tokens created by other users are shortened to four characters.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdTriggersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdTriggersRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve trigger token details
     * Retrieves details of a pipeline trigger token for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @return APIEntitiesTrigger
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdTriggersTriggerIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdTriggersTriggerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'triggerId' is set
        if (triggerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'triggerId' when calling getApiV4ProjectsIdTriggersTriggerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("trigger_id", triggerId);

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

        ParameterizedTypeReference<APIEntitiesTrigger> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/triggers/{trigger_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve trigger token details
     * Retrieves details of a pipeline trigger token for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @return APIEntitiesTrigger
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTrigger getApiV4ProjectsIdTriggersTriggerId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTrigger> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTriggersTriggerIdRequestCreation(id, triggerId).body(localVarReturnType);
    }

    /**
     * Retrieve trigger token details
     * Retrieves details of a pipeline trigger token for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @return ResponseEntity&lt;APIEntitiesTrigger&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTrigger> getApiV4ProjectsIdTriggersTriggerIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTrigger> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTriggersTriggerIdRequestCreation(id, triggerId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve trigger token details
     * Retrieves details of a pipeline trigger token for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdTriggersTriggerIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId) throws RestClientResponseException {
        return getApiV4ProjectsIdTriggersTriggerIdRequestCreation(id, triggerId);
    }

    /**
     * Trigger a pipeline with a token
     * Triggers a pipeline with a token. With a CI/CD job token, the triggered pipeline is a multi-project pipeline. The job that authenticates the request becomes associated with the upstream pipeline, which is visible on the pipeline graph. If you use a trigger token in a job, the job is not associated with the upstream pipeline.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param ref Name of the branch or tag to run the pipeline on.
     * @param postApiV4ProjectsIdRefRefTriggerPipelineRequest The postApiV4ProjectsIdRefRefTriggerPipelineRequest parameter
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRefRefTriggerPipelineRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRefRefTriggerPipelineRequest postApiV4ProjectsIdRefRefTriggerPipelineRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRefRefTriggerPipeline", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ref' is set
        if (ref == null) {
            throw new RestClientResponseException("Missing the required parameter 'ref' when calling postApiV4ProjectsIdRefRefTriggerPipeline", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRefRefTriggerPipelineRequest' is set
        if (postApiV4ProjectsIdRefRefTriggerPipelineRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRefRefTriggerPipelineRequest' when calling postApiV4ProjectsIdRefRefTriggerPipeline", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("ref", ref);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/ref/{ref}/trigger/pipeline", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRefRefTriggerPipelineRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Trigger a pipeline with a token
     * Triggers a pipeline with a token. With a CI/CD job token, the triggered pipeline is a multi-project pipeline. The job that authenticates the request becomes associated with the upstream pipeline, which is visible on the pipeline graph. If you use a trigger token in a job, the job is not associated with the upstream pipeline.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param ref Name of the branch or tag to run the pipeline on.
     * @param postApiV4ProjectsIdRefRefTriggerPipelineRequest The postApiV4ProjectsIdRefRefTriggerPipelineRequest parameter
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipeline postApiV4ProjectsIdRefRefTriggerPipeline(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRefRefTriggerPipelineRequest postApiV4ProjectsIdRefRefTriggerPipelineRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRefRefTriggerPipelineRequestCreation(id, ref, postApiV4ProjectsIdRefRefTriggerPipelineRequest).body(localVarReturnType);
    }

    /**
     * Trigger a pipeline with a token
     * Triggers a pipeline with a token. With a CI/CD job token, the triggered pipeline is a multi-project pipeline. The job that authenticates the request becomes associated with the upstream pipeline, which is visible on the pipeline graph. If you use a trigger token in a job, the job is not associated with the upstream pipeline.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param ref Name of the branch or tag to run the pipeline on.
     * @param postApiV4ProjectsIdRefRefTriggerPipelineRequest The postApiV4ProjectsIdRefRefTriggerPipelineRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiPipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipeline> postApiV4ProjectsIdRefRefTriggerPipelineWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRefRefTriggerPipelineRequest postApiV4ProjectsIdRefRefTriggerPipelineRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRefRefTriggerPipelineRequestCreation(id, ref, postApiV4ProjectsIdRefRefTriggerPipelineRequest).toEntity(localVarReturnType);
    }

    /**
     * Trigger a pipeline with a token
     * Triggers a pipeline with a token. With a CI/CD job token, the triggered pipeline is a multi-project pipeline. The job that authenticates the request becomes associated with the upstream pipeline, which is visible on the pipeline graph. If you use a trigger token in a job, the job is not associated with the upstream pipeline.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param ref Name of the branch or tag to run the pipeline on.
     * @param postApiV4ProjectsIdRefRefTriggerPipelineRequest The postApiV4ProjectsIdRefRefTriggerPipelineRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRefRefTriggerPipelineWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String ref, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRefRefTriggerPipelineRequest postApiV4ProjectsIdRefRefTriggerPipelineRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRefRefTriggerPipelineRequestCreation(id, ref, postApiV4ProjectsIdRefRefTriggerPipelineRequest);
    }

    /**
     * Trigger a pipeline with a token
     * Triggers a pipeline with a token. With a CI/CD job token, the triggered pipeline is a multi-project pipeline. The job that authenticates the request becomes associated with the upstream pipeline, which is visible on the pipeline graph. If you use a trigger token in a job, the job is not associated with the upstream pipeline.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdTriggerPipelineRequest The postApiV4ProjectsIdTriggerPipelineRequest parameter
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdTriggerPipelineRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTriggerPipelineRequest postApiV4ProjectsIdTriggerPipelineRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdTriggerPipeline", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdTriggerPipelineRequest' is set
        if (postApiV4ProjectsIdTriggerPipelineRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdTriggerPipelineRequest' when calling postApiV4ProjectsIdTriggerPipeline", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/trigger/pipeline", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdTriggerPipelineRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Trigger a pipeline with a token
     * Triggers a pipeline with a token. With a CI/CD job token, the triggered pipeline is a multi-project pipeline. The job that authenticates the request becomes associated with the upstream pipeline, which is visible on the pipeline graph. If you use a trigger token in a job, the job is not associated with the upstream pipeline.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdTriggerPipelineRequest The postApiV4ProjectsIdTriggerPipelineRequest parameter
     * @return APIEntitiesCiPipeline
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiPipeline postApiV4ProjectsIdTriggerPipeline(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTriggerPipelineRequest postApiV4ProjectsIdTriggerPipelineRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdTriggerPipelineRequestCreation(id, postApiV4ProjectsIdTriggerPipelineRequest).body(localVarReturnType);
    }

    /**
     * Trigger a pipeline with a token
     * Triggers a pipeline with a token. With a CI/CD job token, the triggered pipeline is a multi-project pipeline. The job that authenticates the request becomes associated with the upstream pipeline, which is visible on the pipeline graph. If you use a trigger token in a job, the job is not associated with the upstream pipeline.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdTriggerPipelineRequest The postApiV4ProjectsIdTriggerPipelineRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiPipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiPipeline> postApiV4ProjectsIdTriggerPipelineWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTriggerPipelineRequest postApiV4ProjectsIdTriggerPipelineRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiPipeline> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdTriggerPipelineRequestCreation(id, postApiV4ProjectsIdTriggerPipelineRequest).toEntity(localVarReturnType);
    }

    /**
     * Trigger a pipeline with a token
     * Triggers a pipeline with a token. With a CI/CD job token, the triggered pipeline is a multi-project pipeline. The job that authenticates the request becomes associated with the upstream pipeline, which is visible on the pipeline graph. If you use a trigger token in a job, the job is not associated with the upstream pipeline.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdTriggerPipelineRequest The postApiV4ProjectsIdTriggerPipelineRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdTriggerPipelineWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTriggerPipelineRequest postApiV4ProjectsIdTriggerPipelineRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdTriggerPipelineRequestCreation(id, postApiV4ProjectsIdTriggerPipelineRequest);
    }

    /**
     * Create a trigger token
     * Creates a pipeline trigger token for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdTriggersRequest The postApiV4ProjectsIdTriggersRequest parameter
     * @return APIEntitiesTrigger
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdTriggersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTriggersRequest postApiV4ProjectsIdTriggersRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdTriggers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdTriggersRequest' is set
        if (postApiV4ProjectsIdTriggersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdTriggersRequest' when calling postApiV4ProjectsIdTriggers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesTrigger> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/triggers", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdTriggersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a trigger token
     * Creates a pipeline trigger token for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdTriggersRequest The postApiV4ProjectsIdTriggersRequest parameter
     * @return APIEntitiesTrigger
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTrigger postApiV4ProjectsIdTriggers(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTriggersRequest postApiV4ProjectsIdTriggersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTrigger> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdTriggersRequestCreation(id, postApiV4ProjectsIdTriggersRequest).body(localVarReturnType);
    }

    /**
     * Create a trigger token
     * Creates a pipeline trigger token for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdTriggersRequest The postApiV4ProjectsIdTriggersRequest parameter
     * @return ResponseEntity&lt;APIEntitiesTrigger&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTrigger> postApiV4ProjectsIdTriggersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTriggersRequest postApiV4ProjectsIdTriggersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTrigger> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdTriggersRequestCreation(id, postApiV4ProjectsIdTriggersRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a trigger token
     * Creates a pipeline trigger token for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdTriggersRequest The postApiV4ProjectsIdTriggersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdTriggersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTriggersRequest postApiV4ProjectsIdTriggersRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdTriggersRequestCreation(id, postApiV4ProjectsIdTriggersRequest);
    }

    /**
     * Update a pipeline trigger token
     * Updates a pipeline trigger token for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @param putApiV4ProjectsIdTriggersTriggerIdRequest The putApiV4ProjectsIdTriggersTriggerIdRequest parameter
     * @return APIEntitiesTrigger
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdTriggersTriggerIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId, @jakarta.annotation.Nullable PutApiV4ProjectsIdTriggersTriggerIdRequest putApiV4ProjectsIdTriggersTriggerIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdTriggersTriggerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'triggerId' is set
        if (triggerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'triggerId' when calling putApiV4ProjectsIdTriggersTriggerId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("trigger_id", triggerId);

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

        ParameterizedTypeReference<APIEntitiesTrigger> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/triggers/{trigger_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdTriggersTriggerIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a pipeline trigger token
     * Updates a pipeline trigger token for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @param putApiV4ProjectsIdTriggersTriggerIdRequest The putApiV4ProjectsIdTriggersTriggerIdRequest parameter
     * @return APIEntitiesTrigger
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTrigger putApiV4ProjectsIdTriggersTriggerId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId, @jakarta.annotation.Nullable PutApiV4ProjectsIdTriggersTriggerIdRequest putApiV4ProjectsIdTriggersTriggerIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTrigger> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdTriggersTriggerIdRequestCreation(id, triggerId, putApiV4ProjectsIdTriggersTriggerIdRequest).body(localVarReturnType);
    }

    /**
     * Update a pipeline trigger token
     * Updates a pipeline trigger token for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @param putApiV4ProjectsIdTriggersTriggerIdRequest The putApiV4ProjectsIdTriggersTriggerIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesTrigger&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTrigger> putApiV4ProjectsIdTriggersTriggerIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId, @jakarta.annotation.Nullable PutApiV4ProjectsIdTriggersTriggerIdRequest putApiV4ProjectsIdTriggersTriggerIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTrigger> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdTriggersTriggerIdRequestCreation(id, triggerId, putApiV4ProjectsIdTriggersTriggerIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a pipeline trigger token
     * Updates a pipeline trigger token for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too Many Requests
     * @param id ID or URL-encoded path of the project.
     * @param triggerId ID of the trigger token.
     * @param putApiV4ProjectsIdTriggersTriggerIdRequest The putApiV4ProjectsIdTriggersTriggerIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdTriggersTriggerIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer triggerId, @jakarta.annotation.Nullable PutApiV4ProjectsIdTriggersTriggerIdRequest putApiV4ProjectsIdTriggersTriggerIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdTriggersTriggerIdRequestCreation(id, triggerId, putApiV4ProjectsIdTriggersTriggerIdRequest);
    }
}
