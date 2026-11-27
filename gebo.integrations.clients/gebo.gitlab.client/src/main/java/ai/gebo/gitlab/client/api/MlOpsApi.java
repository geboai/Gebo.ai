package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowGetDownload;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowGetExperiment;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowGetLatestVersions;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowGetMetricHistory;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowGetModelVersion;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowGetRegisteredModel;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowListExperiment;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowListRegisteredModels;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowNewExperiment;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowRegisteredModel;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowRun;
import ai.gebo.gitlab.client.model.APIEntitiesMlMlflowUpdateRun;
import ai.gebo.gitlab.client.model.GetApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchOrderByParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest;

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
public class MlOpsApi {
    private ApiClient apiClient;

    public MlOpsApi() {
        this(new ApiClient());
    }

    public MlOpsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a Registered Model by Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @return APIEntitiesMlMlflowRegisteredModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsDeleteRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/registered-models/delete", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a Registered Model by Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @return APIEntitiesMlMlflowRegisteredModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowRegisteredModel deleteApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsDelete(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsDeleteRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Delete a Registered Model by Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowRegisteredModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowRegisteredModel> deleteApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsDeleteWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsDeleteRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Delete a Registered Model by Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsDeleteWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        return deleteApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsDeleteRequestCreation(id, name);
    }

    /**
     * MLflow artifact API
     * MLflow artifacts mapping to GitLab artifacts
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param path Model version ID, optionally followed by &#x60;/&#x60; and the path to the artifact, for example &#x60;15/MLmodel&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String path) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifacts", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "path", path));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow-artifacts/artifacts", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * MLflow artifact API
     * MLflow artifacts mapping to GitLab artifacts
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param path Model version ID, optionally followed by &#x60;/&#x60; and the path to the artifact, for example &#x60;15/MLmodel&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifacts(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String path) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsRequestCreation(id, path).body(localVarReturnType);
    }

    /**
     * MLflow artifact API
     * MLflow artifacts mapping to GitLab artifacts
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param path Model version ID, optionally followed by &#x60;/&#x60; and the path to the artifact, for example &#x60;15/MLmodel&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String path) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsRequestCreation(id, path).toEntity(localVarReturnType);
    }

    /**
     * MLflow artifact API
     * MLflow artifacts mapping to GitLab artifacts
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param path Model version ID, optionally followed by &#x60;/&#x60; and the path to the artifact, for example &#x60;15/MLmodel&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String path) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsRequestCreation(id, path);
    }

    /**
     * Get MLflow artifact file
     * Retrieves an MLflow artifact file
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param modelVersion The model version
     * @param id ID or URL-encoded path of the project.
     * @param filePath The filePath parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePathRequestCreation(@jakarta.annotation.Nonnull String modelVersion, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Object filePath) throws RestClientResponseException {
        // verify the required parameter 'modelVersion' is set
        if (modelVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelVersion' when calling getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filePath' is set
        if (filePath == null) {
            throw new RestClientResponseException("Missing the required parameter 'filePath' when calling getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("model_version", modelVersion);
        pathParams.put("id", id);
        pathParams.put("file_path", filePath);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow-artifacts/artifacts/{model_version}/{file_path}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get MLflow artifact file
     * Retrieves an MLflow artifact file
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param modelVersion The model version
     * @param id ID or URL-encoded path of the project.
     * @param filePath The filePath parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePath(@jakarta.annotation.Nonnull String modelVersion, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Object filePath) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePathRequestCreation(modelVersion, id, filePath).body(localVarReturnType);
    }

    /**
     * Get MLflow artifact file
     * Retrieves an MLflow artifact file
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param modelVersion The model version
     * @param id ID or URL-encoded path of the project.
     * @param filePath The filePath parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePathWithHttpInfo(@jakarta.annotation.Nonnull String modelVersion, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Object filePath) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePathRequestCreation(modelVersion, id, filePath).toEntity(localVarReturnType);
    }

    /**
     * Get MLflow artifact file
     * Retrieves an MLflow artifact file
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param modelVersion The model version
     * @param id ID or URL-encoded path of the project.
     * @param filePath The filePath parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePathWithResponseSpec(@jakarta.annotation.Nonnull String modelVersion, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Object filePath) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowArtifactsArtifactsModelVersionFilePathRequestCreation(modelVersion, id, filePath);
    }

    /**
     * Fetch experiment by experiment_id
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-experiment
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param experimentId ID of the experiment, relative to the project.
     * @return APIEntitiesMlMlflowGetExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String experimentId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "experiment_id", experimentId));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowGetExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/experiments/get", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Fetch experiment by experiment_id
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-experiment
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param experimentId ID of the experiment, relative to the project.
     * @return APIEntitiesMlMlflowGetExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetExperiment getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGet(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String experimentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetRequestCreation(id, experimentId).body(localVarReturnType);
    }

    /**
     * Fetch experiment by experiment_id
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-experiment
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param experimentId ID of the experiment, relative to the project.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetExperiment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetExperiment> getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String experimentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetRequestCreation(id, experimentId).toEntity(localVarReturnType);
    }

    /**
     * Fetch experiment by experiment_id
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-experiment
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param experimentId ID of the experiment, relative to the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String experimentId) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetRequestCreation(id, experimentId);
    }

    /**
     * Fetch experiment by experiment_name
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-experiment-by-name
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param experimentName Name of the experiment.
     * @return APIEntitiesMlMlflowGetExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetByNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String experimentName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetByName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "experiment_name", experimentName));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowGetExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/experiments/get-by-name", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Fetch experiment by experiment_name
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-experiment-by-name
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param experimentName Name of the experiment.
     * @return APIEntitiesMlMlflowGetExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetExperiment getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetByName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String experimentName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetByNameRequestCreation(id, experimentName).body(localVarReturnType);
    }

    /**
     * Fetch experiment by experiment_name
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-experiment-by-name
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param experimentName Name of the experiment.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetExperiment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetExperiment> getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetByNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String experimentName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetByNameRequestCreation(id, experimentName).toEntity(localVarReturnType);
    }

    /**
     * Fetch experiment by experiment_name
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-experiment-by-name
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param experimentName Name of the experiment.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetByNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String experimentName) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsGetByNameRequestCreation(id, experimentName);
    }

    /**
     * List experiments
     * https://mlflow.org/docs/1.28.0/rest-api.html#list-experiments
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param maxResults Maximum number of experiments to fetch in a page. The maximum is 1000.
     * @param pageToken Token for pagination.
     * @return APIEntitiesMlMlflowListExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsListRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsList", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_results", maxResults));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page_token", pageToken));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowListExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/experiments/list", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List experiments
     * https://mlflow.org/docs/1.28.0/rest-api.html#list-experiments
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param maxResults Maximum number of experiments to fetch in a page. The maximum is 1000.
     * @param pageToken Token for pagination.
     * @return APIEntitiesMlMlflowListExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowListExperiment getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsList(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowListExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsListRequestCreation(id, maxResults, pageToken).body(localVarReturnType);
    }

    /**
     * List experiments
     * https://mlflow.org/docs/1.28.0/rest-api.html#list-experiments
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param maxResults Maximum number of experiments to fetch in a page. The maximum is 1000.
     * @param pageToken Token for pagination.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowListExperiment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowListExperiment> getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsListWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowListExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsListRequestCreation(id, maxResults, pageToken).toEntity(localVarReturnType);
    }

    /**
     * List experiments
     * https://mlflow.org/docs/1.28.0/rest-api.html#list-experiments
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param maxResults Maximum number of experiments to fetch in a page. The maximum is 1000.
     * @param pageToken Token for pagination.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsListWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowExperimentsListRequestCreation(id, maxResults, pageToken);
    }

    /**
     * Gets metric history for a run
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-metric-history
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param runId UUID of the run.
     * @param metricKey Name of the metric.
     * @param id ID or URL-encoded path of the project.
     * @param maxResults Maximum number of metrics to return.
     * @param pageToken Token for pagination.
     * @return APIEntitiesMlMlflowGetMetricHistory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistoryRequestCreation(@jakarta.annotation.Nullable String runId, @jakarta.annotation.Nullable String metricKey, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        // verify the required parameter 'runId' is set
        if (runId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runId' when calling getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metricKey' is set
        if (metricKey == null) {
            throw new RestClientResponseException("Missing the required parameter 'metricKey' when calling getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "run_id", runId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "metric_key", metricKey));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_results", maxResults));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page_token", pageToken));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowGetMetricHistory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/metrics/get-history", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets metric history for a run
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-metric-history
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param runId UUID of the run.
     * @param metricKey Name of the metric.
     * @param id ID or URL-encoded path of the project.
     * @param maxResults Maximum number of metrics to return.
     * @param pageToken Token for pagination.
     * @return APIEntitiesMlMlflowGetMetricHistory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetMetricHistory getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistory(@jakarta.annotation.Nullable String runId, @jakarta.annotation.Nullable String metricKey, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetMetricHistory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistoryRequestCreation(runId, metricKey, id, maxResults, pageToken).body(localVarReturnType);
    }

    /**
     * Gets metric history for a run
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-metric-history
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param runId UUID of the run.
     * @param metricKey Name of the metric.
     * @param id ID or URL-encoded path of the project.
     * @param maxResults Maximum number of metrics to return.
     * @param pageToken Token for pagination.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetMetricHistory&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetMetricHistory> getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistoryWithHttpInfo(@jakarta.annotation.Nullable String runId, @jakarta.annotation.Nullable String metricKey, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetMetricHistory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistoryRequestCreation(runId, metricKey, id, maxResults, pageToken).toEntity(localVarReturnType);
    }

    /**
     * Gets metric history for a run
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-metric-history
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param runId UUID of the run.
     * @param metricKey Name of the metric.
     * @param id ID or URL-encoded path of the project.
     * @param maxResults Maximum number of metrics to return.
     * @param pageToken Token for pagination.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistoryWithResponseSpec(@jakarta.annotation.Nullable String runId, @jakarta.annotation.Nullable String metricKey, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowMetricsGetHistoryRequestCreation(runId, metricKey, id, maxResults, pageToken);
    }

    /**
     * Fetch model version by name and version
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-modelversion
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the registered model.
     * @param version Version of the model.
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesMlMlflowGetModelVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetRequestCreation(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new RestClientResponseException("Missing the required parameter 'version' when calling getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/model-versions/get", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Fetch model version by name and version
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-modelversion
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the registered model.
     * @param version Version of the model.
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesMlMlflowGetModelVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetModelVersion getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGet(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetRequestCreation(name, version, id).body(localVarReturnType);
    }

    /**
     * Fetch model version by name and version
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-modelversion
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the registered model.
     * @param version Version of the model.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetModelVersion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetModelVersion> getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetWithHttpInfo(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetRequestCreation(name, version, id).toEntity(localVarReturnType);
    }

    /**
     * Fetch model version by name and version
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-modelversion
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the registered model.
     * @param version Version of the model.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetWithResponseSpec(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetRequestCreation(name, version, id);
    }

    /**
     * Fetch the download URI for the model version.
     * Returns version in MLflow format \&quot;mlflow-artifacts:&lt;version&gt;\&quot; https://mlflow.org/docs/2.19.0/rest-api.html#get-download-uri-for-modelversion-artifacts
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the registered model.
     * @param version ID of the model version.
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesMlMlflowGetDownload
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUriRequestCreation(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable Integer version, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUri", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new RestClientResponseException("Missing the required parameter 'version' when calling getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUri", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUri", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowGetDownload> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/model-versions/get-download-uri", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Fetch the download URI for the model version.
     * Returns version in MLflow format \&quot;mlflow-artifacts:&lt;version&gt;\&quot; https://mlflow.org/docs/2.19.0/rest-api.html#get-download-uri-for-modelversion-artifacts
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the registered model.
     * @param version ID of the model version.
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesMlMlflowGetDownload
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetDownload getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUri(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable Integer version, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetDownload> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUriRequestCreation(name, version, id).body(localVarReturnType);
    }

    /**
     * Fetch the download URI for the model version.
     * Returns version in MLflow format \&quot;mlflow-artifacts:&lt;version&gt;\&quot; https://mlflow.org/docs/2.19.0/rest-api.html#get-download-uri-for-modelversion-artifacts
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the registered model.
     * @param version ID of the model version.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetDownload&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetDownload> getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUriWithHttpInfo(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable Integer version, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetDownload> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUriRequestCreation(name, version, id).toEntity(localVarReturnType);
    }

    /**
     * Fetch the download URI for the model version.
     * Returns version in MLflow format \&quot;mlflow-artifacts:&lt;version&gt;\&quot; https://mlflow.org/docs/2.19.0/rest-api.html#get-download-uri-for-modelversion-artifacts
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the registered model.
     * @param version ID of the model version.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUriWithResponseSpec(@jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable Integer version, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsGetDownloadUriRequestCreation(name, version, id);
    }

    /**
     * Gets a Model Version by alias
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-model-version-by-alias
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @param alias Alias of the model version, for example &#x60;1.0.0&#x60;.
     * @return APIEntitiesMlMlflowGetModelVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsAliasRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String alias) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsAlias", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "alias", alias));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/registered-models/alias", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets a Model Version by alias
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-model-version-by-alias
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @param alias Alias of the model version, for example &#x60;1.0.0&#x60;.
     * @return APIEntitiesMlMlflowGetModelVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetModelVersion getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsAlias(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String alias) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsAliasRequestCreation(id, name, alias).body(localVarReturnType);
    }

    /**
     * Gets a Model Version by alias
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-model-version-by-alias
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @param alias Alias of the model version, for example &#x60;1.0.0&#x60;.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetModelVersion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetModelVersion> getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsAliasWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String alias) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsAliasRequestCreation(id, name, alias).toEntity(localVarReturnType);
    }

    /**
     * Gets a Model Version by alias
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-model-version-by-alias
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @param alias Alias of the model version, for example &#x60;1.0.0&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsAliasWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String alias) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsAliasRequestCreation(id, name, alias);
    }

    /**
     * Fetch a Registered Model by Name
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @return APIEntitiesMlMlflowGetRegisteredModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowGetRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/registered-models/get", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Fetch a Registered Model by Name
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @return APIEntitiesMlMlflowGetRegisteredModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetRegisteredModel getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGet(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Fetch a Registered Model by Name
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetRegisteredModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetRegisteredModel> getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Fetch a Registered Model by Name
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#get-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the registered model.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String name) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetRequestCreation(id, name);
    }

    /**
     * Search Registered Models within a project
     * https://mlflow.org/docs/2.19.0/rest-api.html#search-registeredmodels
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filter Filter for the model search, in the format &#x60;name&#x3D;&#39;value&#39;&#x60;. Only filtering by name is supported.
     * @param maxResults Maximum number of models to fetch in a page. The maximum is 1000.
     * @param orderBy Column and direction to sort registered models by, for example &#x60;name&#x60;, &#x60;name DESC&#x60;, or &#x60;last_updated_timestamp DESC&#x60;. Supported columns are &#x60;name&#x60;, &#x60;created_at&#x60;, &#x60;updated_at&#x60;, &#x60;last_updated_timestamp&#x60; and &#x60;id&#x60;. A list uses its first entry. Sorting by model metadata is not supported.
     * @param pageToken Token for pagination.
     * @return APIEntitiesMlMlflowListRegisteredModels
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable GetApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchOrderByParameter orderBy, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_results", maxResults));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page_token", pageToken));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowListRegisteredModels> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/registered-models/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search Registered Models within a project
     * https://mlflow.org/docs/2.19.0/rest-api.html#search-registeredmodels
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filter Filter for the model search, in the format &#x60;name&#x3D;&#39;value&#39;&#x60;. Only filtering by name is supported.
     * @param maxResults Maximum number of models to fetch in a page. The maximum is 1000.
     * @param orderBy Column and direction to sort registered models by, for example &#x60;name&#x60;, &#x60;name DESC&#x60;, or &#x60;last_updated_timestamp DESC&#x60;. Supported columns are &#x60;name&#x60;, &#x60;created_at&#x60;, &#x60;updated_at&#x60;, &#x60;last_updated_timestamp&#x60; and &#x60;id&#x60;. A list uses its first entry. Sorting by model metadata is not supported.
     * @param pageToken Token for pagination.
     * @return APIEntitiesMlMlflowListRegisteredModels
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowListRegisteredModels getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable GetApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchOrderByParameter orderBy, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowListRegisteredModels> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchRequestCreation(id, filter, maxResults, orderBy, pageToken).body(localVarReturnType);
    }

    /**
     * Search Registered Models within a project
     * https://mlflow.org/docs/2.19.0/rest-api.html#search-registeredmodels
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filter Filter for the model search, in the format &#x60;name&#x3D;&#39;value&#39;&#x60;. Only filtering by name is supported.
     * @param maxResults Maximum number of models to fetch in a page. The maximum is 1000.
     * @param orderBy Column and direction to sort registered models by, for example &#x60;name&#x60;, &#x60;name DESC&#x60;, or &#x60;last_updated_timestamp DESC&#x60;. Supported columns are &#x60;name&#x60;, &#x60;created_at&#x60;, &#x60;updated_at&#x60;, &#x60;last_updated_timestamp&#x60; and &#x60;id&#x60;. A list uses its first entry. Sorting by model metadata is not supported.
     * @param pageToken Token for pagination.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowListRegisteredModels&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowListRegisteredModels> getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable GetApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchOrderByParameter orderBy, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowListRegisteredModels> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchRequestCreation(id, filter, maxResults, orderBy, pageToken).toEntity(localVarReturnType);
    }

    /**
     * Search Registered Models within a project
     * https://mlflow.org/docs/2.19.0/rest-api.html#search-registeredmodels
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param filter Filter for the model search, in the format &#x60;name&#x3D;&#39;value&#39;&#x60;. Only filtering by name is supported.
     * @param maxResults Maximum number of models to fetch in a page. The maximum is 1000.
     * @param orderBy Column and direction to sort registered models by, for example &#x60;name&#x60;, &#x60;name DESC&#x60;, or &#x60;last_updated_timestamp DESC&#x60;. Supported columns are &#x60;name&#x60;, &#x60;created_at&#x60;, &#x60;updated_at&#x60;, &#x60;last_updated_timestamp&#x60; and &#x60;id&#x60;. A list uses its first entry. Sorting by model metadata is not supported.
     * @param pageToken Token for pagination.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Integer maxResults, @jakarta.annotation.Nullable GetApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchOrderByParameter orderBy, @jakarta.annotation.Nullable String pageToken) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsSearchRequestCreation(id, filter, maxResults, orderBy, pageToken);
    }

    /**
     * Gets an MLFlow Run, which maps to GitLab Candidates
     * https://www.mlflow.org/docs/1.28.0/rest-api.html#get-run
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param runId UUID of the candidate.
     * @param id ID or URL-encoded path of the project.
     * @param runUuid Any value provided is ignored.
     * @return APIEntitiesMlMlflowRun
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowRunsGetRequestCreation(@jakarta.annotation.Nullable String runId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String runUuid) throws RestClientResponseException {
        // verify the required parameter 'runId' is set
        if (runId == null) {
            throw new RestClientResponseException("Missing the required parameter 'runId' when calling getApiV4ProjectsIdMlMlflowApi20MlflowRunsGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMlMlflowApi20MlflowRunsGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "run_id", runId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "run_uuid", runUuid));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMlMlflowRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/runs/get", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets an MLFlow Run, which maps to GitLab Candidates
     * https://www.mlflow.org/docs/1.28.0/rest-api.html#get-run
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param runId UUID of the candidate.
     * @param id ID or URL-encoded path of the project.
     * @param runUuid Any value provided is ignored.
     * @return APIEntitiesMlMlflowRun
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowRun getApiV4ProjectsIdMlMlflowApi20MlflowRunsGet(@jakarta.annotation.Nullable String runId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String runUuid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowRunsGetRequestCreation(runId, id, runUuid).body(localVarReturnType);
    }

    /**
     * Gets an MLFlow Run, which maps to GitLab Candidates
     * https://www.mlflow.org/docs/1.28.0/rest-api.html#get-run
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param runId UUID of the candidate.
     * @param id ID or URL-encoded path of the project.
     * @param runUuid Any value provided is ignored.
     * @return ResponseEntity&lt;APIEntitiesMlMlflowRun&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowRun> getApiV4ProjectsIdMlMlflowApi20MlflowRunsGetWithHttpInfo(@jakarta.annotation.Nullable String runId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String runUuid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMlMlflowApi20MlflowRunsGetRequestCreation(runId, id, runUuid).toEntity(localVarReturnType);
    }

    /**
     * Gets an MLFlow Run, which maps to GitLab Candidates
     * https://www.mlflow.org/docs/1.28.0/rest-api.html#get-run
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param runId UUID of the candidate.
     * @param id ID or URL-encoded path of the project.
     * @param runUuid Any value provided is ignored.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMlMlflowApi20MlflowRunsGetWithResponseSpec(@jakarta.annotation.Nullable String runId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String runUuid) throws RestClientResponseException {
        return getApiV4ProjectsIdMlMlflowApi20MlflowRunsGetRequestCreation(runId, id, runUuid);
    }

    /**
     * Updates a Model Version.
     * https://mlflow.org/docs/2.19.0/rest-api.html#update-modelversion
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest The patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest parameter
     * @return APIEntitiesMlMlflowGetModelVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/model-versions/update", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Updates a Model Version.
     * https://mlflow.org/docs/2.19.0/rest-api.html#update-modelversion
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest The patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest parameter
     * @return APIEntitiesMlMlflowGetModelVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetModelVersion patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequestCreation(id, patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest).body(localVarReturnType);
    }

    /**
     * Updates a Model Version.
     * https://mlflow.org/docs/2.19.0/rest-api.html#update-modelversion
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest The patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetModelVersion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetModelVersion> patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequestCreation(id, patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest).toEntity(localVarReturnType);
    }

    /**
     * Updates a Model Version.
     * https://mlflow.org/docs/2.19.0/rest-api.html#update-modelversion
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest The patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequestCreation(id, patchApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsUpdateRequest);
    }

    /**
     * Update a Registered Model by Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#update-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest The patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest parameter
     * @return APIEntitiesMlMlflowGetRegisteredModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowGetRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/registered-models/update", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a Registered Model by Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#update-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest The patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest parameter
     * @return APIEntitiesMlMlflowGetRegisteredModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetRegisteredModel patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequestCreation(id, patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest).body(localVarReturnType);
    }

    /**
     * Update a Registered Model by Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#update-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest The patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetRegisteredModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetRegisteredModel> patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequestCreation(id, patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a Registered Model by Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#update-registeredmodel
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest The patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequestCreation(id, patchApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsUpdateRequest);
    }

    /**
     * Create experiment
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#create-experiment
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest parameter
     * @return APIEntitiesMlMlflowNewExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowNewExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/experiments/create", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create experiment
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#create-experiment
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest parameter
     * @return APIEntitiesMlMlflowNewExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowNewExperiment postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowNewExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest).body(localVarReturnType);
    }

    /**
     * Create experiment
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#create-experiment
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowNewExperiment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowNewExperiment> postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowNewExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest).toEntity(localVarReturnType);
    }

    /**
     * Create experiment
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#create-experiment
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsCreateRequest);
    }

    /**
     * Delete an experiment.
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-experiment
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/experiments/delete", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an experiment.
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-experiment
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDelete(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest).body(localVarReturnType);
    }

    /**
     * Delete an experiment.
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-experiment
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest).toEntity(localVarReturnType);
    }

    /**
     * Delete an experiment.
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-experiment
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsDeleteRequest);
    }

    /**
     * Search experiments
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#search-experiments
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest parameter
     * @return APIEntitiesMlMlflowListExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowListExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/experiments/search", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search experiments
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#search-experiments
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest parameter
     * @return APIEntitiesMlMlflowListExperiment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowListExperiment postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowListExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest).body(localVarReturnType);
    }

    /**
     * Search experiments
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#search-experiments
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowListExperiment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowListExperiment> postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowListExperiment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest).toEntity(localVarReturnType);
    }

    /**
     * Search experiments
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#search-experiments
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSearchRequest);
    }

    /**
     * Sets a tag for an experiment.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#set-experiment-tag
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/experiments/set-experiment-tag", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Sets a tag for an experiment.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#set-experiment-tag
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTag(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest).body(localVarReturnType);
    }

    /**
     * Sets a tag for an experiment.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#set-experiment-tag
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest).toEntity(localVarReturnType);
    }

    /**
     * Sets a tag for an experiment.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#set-experiment-tag
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest The postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowExperimentsSetExperimentTagRequest);
    }

    /**
     * Creates a Model Version.
     * MLFlow Model Versions map to GitLab Model Versions. https://mlflow.org/docs/2.19.0/rest-api.html#create-modelversion
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest parameter
     * @return APIEntitiesMlMlflowGetModelVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/model-versions/create", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates a Model Version.
     * MLFlow Model Versions map to GitLab Model Versions. https://mlflow.org/docs/2.19.0/rest-api.html#create-modelversion
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest parameter
     * @return APIEntitiesMlMlflowGetModelVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetModelVersion postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest).body(localVarReturnType);
    }

    /**
     * Creates a Model Version.
     * MLFlow Model Versions map to GitLab Model Versions. https://mlflow.org/docs/2.19.0/rest-api.html#create-modelversion
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetModelVersion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetModelVersion> postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetModelVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest).toEntity(localVarReturnType);
    }

    /**
     * Creates a Model Version.
     * MLFlow Model Versions map to GitLab Model Versions. https://mlflow.org/docs/2.19.0/rest-api.html#create-modelversion
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowModelVersionsCreateRequest);
    }

    /**
     * Creates a Registered Model.
     * MLFlow Registered Models map to GitLab Models. https://mlflow.org/docs/2.19.0/rest-api.html#create-registeredmodel
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest parameter
     * @return APIEntitiesMlMlflowGetRegisteredModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowGetRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/registered-models/create", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates a Registered Model.
     * MLFlow Registered Models map to GitLab Models. https://mlflow.org/docs/2.19.0/rest-api.html#create-registeredmodel
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest parameter
     * @return APIEntitiesMlMlflowGetRegisteredModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetRegisteredModel postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest).body(localVarReturnType);
    }

    /**
     * Creates a Registered Model.
     * MLFlow Registered Models map to GitLab Models. https://mlflow.org/docs/2.19.0/rest-api.html#create-registeredmodel
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetRegisteredModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetRegisteredModel> postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetRegisteredModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest).toEntity(localVarReturnType);
    }

    /**
     * Creates a Registered Model.
     * MLFlow Registered Models map to GitLab Models. https://mlflow.org/docs/2.19.0/rest-api.html#create-registeredmodel
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsCreateRequest);
    }

    /**
     * Fetch the latest Model Version for the given Registered Model Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-latest-modelversions
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest parameter
     * @return APIEntitiesMlMlflowGetLatestVersions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowGetLatestVersions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/registered-models/get-latest-versions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Fetch the latest Model Version for the given Registered Model Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-latest-modelversions
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest parameter
     * @return APIEntitiesMlMlflowGetLatestVersions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowGetLatestVersions postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetLatestVersions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest).body(localVarReturnType);
    }

    /**
     * Fetch the latest Model Version for the given Registered Model Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-latest-modelversions
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowGetLatestVersions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowGetLatestVersions> postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowGetLatestVersions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Fetch the latest Model Version for the given Registered Model Name
     * https://mlflow.org/docs/2.19.0/rest-api.html#get-latest-modelversions
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRegisteredModelsGetLatestVersionsRequest);
    }

    /**
     * Creates a Run.
     * MLFlow Runs map to GitLab Candidates. https://www.mlflow.org/docs/2.19.0/rest-api.html#create-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest parameter
     * @return APIEntitiesMlMlflowRun
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/runs/create", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates a Run.
     * MLFlow Runs map to GitLab Candidates. https://www.mlflow.org/docs/2.19.0/rest-api.html#create-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest parameter
     * @return APIEntitiesMlMlflowRun
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowRun postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest).body(localVarReturnType);
    }

    /**
     * Creates a Run.
     * MLFlow Runs map to GitLab Candidates. https://www.mlflow.org/docs/2.19.0/rest-api.html#create-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowRun&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowRun> postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest).toEntity(localVarReturnType);
    }

    /**
     * Creates a Run.
     * MLFlow Runs map to GitLab Candidates. https://www.mlflow.org/docs/2.19.0/rest-api.html#create-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsCreateRequest);
    }

    /**
     * Delete a run.
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/runs/delete", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a run.
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMlMlflowApi20MlflowRunsDelete(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest).body(localVarReturnType);
    }

    /**
     * Delete a run.
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest).toEntity(localVarReturnType);
    }

    /**
     * Delete a run.
     * https://mlflow.org/docs/2.19.0/rest-api.html#delete-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsDeleteRequest);
    }

    /**
     * Logs multiple parameters and metrics.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#log-param
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/runs/log-batch", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Logs multiple parameters and metrics.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#log-param
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest).body(localVarReturnType);
    }

    /**
     * Logs multiple parameters and metrics.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#log-param
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest).toEntity(localVarReturnType);
    }

    /**
     * Logs multiple parameters and metrics.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#log-param
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogBatchRequest);
    }

    /**
     * Logs a metric to a run.
     * https://www.mlflow.org/docs/1.28.0/rest-api.html#log-metric
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetric", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetric", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/runs/log-metric", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Logs a metric to a run.
     * https://www.mlflow.org/docs/1.28.0/rest-api.html#log-metric
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetric(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest).body(localVarReturnType);
    }

    /**
     * Logs a metric to a run.
     * https://www.mlflow.org/docs/1.28.0/rest-api.html#log-metric
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest).toEntity(localVarReturnType);
    }

    /**
     * Logs a metric to a run.
     * https://www.mlflow.org/docs/1.28.0/rest-api.html#log-metric
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogMetricRequest);
    }

    /**
     * Logs a parameter to a run.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#log-param
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameter", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameter", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/runs/log-parameter", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Logs a parameter to a run.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#log-param
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameter(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest).body(localVarReturnType);
    }

    /**
     * Logs a parameter to a run.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#log-param
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest).toEntity(localVarReturnType);
    }

    /**
     * Logs a parameter to a run.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#log-param
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsLogParameterRequest);
    }

    /**
     * Searches runs/candidates within a project
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#search-runsexperiment_ids supports only a single experiment ID.Introduced in GitLab 16.4
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest parameter
     * @return APIEntitiesMlMlflowRun
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/runs/search", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Searches runs/candidates within a project
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#search-runsexperiment_ids supports only a single experiment ID.Introduced in GitLab 16.4
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest parameter
     * @return APIEntitiesMlMlflowRun
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowRun postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest).body(localVarReturnType);
    }

    /**
     * Searches runs/candidates within a project
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#search-runsexperiment_ids supports only a single experiment ID.Introduced in GitLab 16.4
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowRun&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowRun> postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest).toEntity(localVarReturnType);
    }

    /**
     * Searches runs/candidates within a project
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#search-runsexperiment_ids supports only a single experiment ID.Introduced in GitLab 16.4
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsSearchRequest);
    }

    /**
     * Sets a tag for a run.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#set-tag
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/runs/set-tag", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Sets a tag for a run.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#set-tag
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTag(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest).body(localVarReturnType);
    }

    /**
     * Sets a tag for a run.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#set-tag
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest).toEntity(localVarReturnType);
    }

    /**
     * Sets a tag for a run.
     * https://www.mlflow.org/docs/2.19.0/rest-api.html#set-tag
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsSetTagRequest);
    }

    /**
     * Updates a Run.
     * MLFlow Runs map to GitLab Candidates. https://www.mlflow.org/docs/2.19.0/rest-api.html#update-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest parameter
     * @return APIEntitiesMlMlflowUpdateRun
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest' is set
        if (postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest' when calling postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMlMlflowUpdateRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ml/mlflow/api/2.0/mlflow/runs/update", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Updates a Run.
     * MLFlow Runs map to GitLab Candidates. https://www.mlflow.org/docs/2.19.0/rest-api.html#update-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest parameter
     * @return APIEntitiesMlMlflowUpdateRun
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMlMlflowUpdateRun postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowUpdateRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest).body(localVarReturnType);
    }

    /**
     * Updates a Run.
     * MLFlow Runs map to GitLab Candidates. https://www.mlflow.org/docs/2.19.0/rest-api.html#update-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMlMlflowUpdateRun&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMlMlflowUpdateRun> postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMlMlflowUpdateRun> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest).toEntity(localVarReturnType);
    }

    /**
     * Updates a Run.
     * MLFlow Runs map to GitLab Candidates. https://www.mlflow.org/docs/2.19.0/rest-api.html#update-run
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest The postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequestCreation(id, postApiV4ProjectsIdMlMlflowApi20MlflowRunsUpdateRequest);
    }
}
