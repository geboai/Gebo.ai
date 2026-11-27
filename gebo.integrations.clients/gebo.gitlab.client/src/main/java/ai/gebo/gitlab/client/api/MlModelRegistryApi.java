package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest;

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
public class MlModelRegistryApi {
    private ApiClient apiClient;

    public MlModelRegistryApi() {
        this(new ApiClient());
    }

    public MlModelRegistryApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Download an ml_model package file
     * This feature was introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'modelVersionId' is set
        if (modelVersionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelVersionId' when calling getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("model_version_id", modelVersionId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/ml_models/{model_version_id}/files/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download an ml_model package file
     * This feature was introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameRequestCreation(id, fileName, modelVersionId, status).body(localVarReturnType);
    }

    /**
     * Download an ml_model package file
     * This feature was introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameRequestCreation(id, fileName, modelVersionId, status).toEntity(localVarReturnType);
    }

    /**
     * Download an ml_model package file
     * This feature was introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param status Package status
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameRequestCreation(id, fileName, modelVersionId, status);
    }

    /**
     * Download an ml_model package file
     * This feature was introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'modelVersionId' is set
        if (modelVersionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelVersionId' when calling getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("path", path);
        pathParams.put("model_version_id", modelVersionId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/ml_models/{model_version_id}/files/{path}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download an ml_model package file
     * This feature was introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameRequestCreation(id, fileName, path, modelVersionId, status).body(localVarReturnType);
    }

    /**
     * Download an ml_model package file
     * This feature was introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameRequestCreation(id, fileName, path, modelVersionId, status).toEntity(localVarReturnType);
    }

    /**
     * Download an ml_model package file
     * This feature was introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param status Package status
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameRequestCreation(id, fileName, path, modelVersionId, status);
    }

    /**
     * Workhorse upload model package file
     * Introduced in GitLab 16.8
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'modelVersionId' is set
        if (modelVersionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelVersionId' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("model_version_id", modelVersionId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (status != null) {
            formParams.add("status", status);
        }
        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/ml_models/{model_version_id}/files/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse upload model package file
     * Introduced in GitLab 16.8
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameRequestCreation(id, fileName, modelVersionId, _file, status).body(localVarReturnType);
    }

    /**
     * Workhorse upload model package file
     * Introduced in GitLab 16.8
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameRequestCreation(id, fileName, modelVersionId, _file, status).toEntity(localVarReturnType);
    }

    /**
     * Workhorse upload model package file
     * Introduced in GitLab 16.8
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param status Package status
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameRequestCreation(id, fileName, modelVersionId, _file, status);
    }

    /**
     * Workhorse authorize model package file
     * Introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'modelVersionId' is set
        if (modelVersionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelVersionId' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("model_version_id", modelVersionId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/ml_models/{model_version_id}/files/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize model package file
     * Introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequestCreation(id, fileName, modelVersionId, putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest).body(localVarReturnType);
    }

    /**
     * Workhorse authorize model package file
     * Introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequestCreation(id, fileName, modelVersionId, putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize model package file
     * Introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param modelVersionId Model version id
     * @param putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequestCreation(id, fileName, modelVersionId, putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesFileNameAuthorizeRequest);
    }

    /**
     * Workhorse upload model package file
     * Introduced in GitLab 16.8
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'modelVersionId' is set
        if (modelVersionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelVersionId' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("path", path);
        pathParams.put("model_version_id", modelVersionId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (status != null) {
            formParams.add("status", status);
        }
        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/ml_models/{model_version_id}/files/{path}/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse upload model package file
     * Introduced in GitLab 16.8
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameRequestCreation(id, fileName, path, modelVersionId, _file, status).body(localVarReturnType);
    }

    /**
     * Workhorse upload model package file
     * Introduced in GitLab 16.8
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param status Package status
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameRequestCreation(id, fileName, path, modelVersionId, _file, status).toEntity(localVarReturnType);
    }

    /**
     * Workhorse upload model package file
     * Introduced in GitLab 16.8
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param status Package status
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameRequestCreation(id, fileName, path, modelVersionId, _file, status);
    }

    /**
     * Workhorse authorize model package file
     * Introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'modelVersionId' is set
        if (modelVersionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'modelVersionId' when calling putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("path", path);
        pathParams.put("model_version_id", modelVersionId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/ml_models/{model_version_id}/files/{path}/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize model package file
     * Introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequestCreation(id, fileName, path, modelVersionId, putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest).body(localVarReturnType);
    }

    /**
     * Workhorse authorize model package file
     * Introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequestCreation(id, fileName, path, modelVersionId, putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize model package file
     * Introduced in GitLab 16.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName File name
     * @param path File directory path
     * @param modelVersionId Model version id
     * @param putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String modelVersionId, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequestCreation(id, fileName, path, modelVersionId, putApiV4ProjectsIdPackagesMlModelsModelVersionIdFilesPathFileNameAuthorizeRequest);
    }
}
