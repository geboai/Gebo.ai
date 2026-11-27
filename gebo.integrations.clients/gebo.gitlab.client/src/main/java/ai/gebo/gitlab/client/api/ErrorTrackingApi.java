package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesErrorTrackingClientKey;
import ai.gebo.gitlab.client.model.APIEntitiesErrorTrackingProjectSetting;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdErrorTrackingSettingsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdErrorTrackingSettingsRequest;

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
public class ErrorTrackingApi {
    private ApiClient apiClient;

    public ErrorTrackingApi() {
        this(new ApiClient());
    }

    public ErrorTrackingApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a client key
     * Deletes an integrated error tracking client key from a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the client key.
     * @return APIEntitiesErrorTrackingClientKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdErrorTrackingClientKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdErrorTrackingClientKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling deleteApiV4ProjectsIdErrorTrackingClientKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesErrorTrackingClientKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/error_tracking/client_keys/{key_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a client key
     * Deletes an integrated error tracking client key from a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the client key.
     * @return APIEntitiesErrorTrackingClientKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesErrorTrackingClientKey deleteApiV4ProjectsIdErrorTrackingClientKeysKeyId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingClientKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdErrorTrackingClientKeysKeyIdRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Delete a client key
     * Deletes an integrated error tracking client key from a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the client key.
     * @return ResponseEntity&lt;APIEntitiesErrorTrackingClientKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesErrorTrackingClientKey> deleteApiV4ProjectsIdErrorTrackingClientKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingClientKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdErrorTrackingClientKeysKeyIdRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Delete a client key
     * Deletes an integrated error tracking client key from a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the client key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdErrorTrackingClientKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdErrorTrackingClientKeysKeyIdRequestCreation(id, keyId);
    }

    /**
     * List all project client keys
     * Lists all integrated error tracking client keys for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return List&lt;APIEntitiesErrorTrackingClientKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdErrorTrackingClientKeysRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdErrorTrackingClientKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesErrorTrackingClientKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/error_tracking/client_keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project client keys
     * Lists all integrated error tracking client keys for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return List&lt;APIEntitiesErrorTrackingClientKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesErrorTrackingClientKey> getApiV4ProjectsIdErrorTrackingClientKeys(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesErrorTrackingClientKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdErrorTrackingClientKeysRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all project client keys
     * Lists all integrated error tracking client keys for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;List&lt;APIEntitiesErrorTrackingClientKey&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesErrorTrackingClientKey>> getApiV4ProjectsIdErrorTrackingClientKeysWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesErrorTrackingClientKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdErrorTrackingClientKeysRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all project client keys
     * Lists all integrated error tracking client keys for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdErrorTrackingClientKeysWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdErrorTrackingClientKeysRequestCreation(id);
    }

    /**
     * Retrieve Error Tracking settings for a project
     * Retrieves the Error Tracking settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesErrorTrackingProjectSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdErrorTrackingSettingsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdErrorTrackingSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesErrorTrackingProjectSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/error_tracking/settings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve Error Tracking settings for a project
     * Retrieves the Error Tracking settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesErrorTrackingProjectSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesErrorTrackingProjectSetting getApiV4ProjectsIdErrorTrackingSettings(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingProjectSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdErrorTrackingSettingsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve Error Tracking settings for a project
     * Retrieves the Error Tracking settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesErrorTrackingProjectSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesErrorTrackingProjectSetting> getApiV4ProjectsIdErrorTrackingSettingsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingProjectSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdErrorTrackingSettingsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve Error Tracking settings for a project
     * Retrieves the Error Tracking settings for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdErrorTrackingSettingsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdErrorTrackingSettingsRequestCreation(id);
    }

    /**
     * Update Error Tracking settings for a project
     * Updates Error Tracking settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdErrorTrackingSettingsRequest The patchApiV4ProjectsIdErrorTrackingSettingsRequest parameter
     * @return APIEntitiesErrorTrackingProjectSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdErrorTrackingSettingsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdErrorTrackingSettingsRequest patchApiV4ProjectsIdErrorTrackingSettingsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdErrorTrackingSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patchApiV4ProjectsIdErrorTrackingSettingsRequest' is set
        if (patchApiV4ProjectsIdErrorTrackingSettingsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'patchApiV4ProjectsIdErrorTrackingSettingsRequest' when calling patchApiV4ProjectsIdErrorTrackingSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesErrorTrackingProjectSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/error_tracking/settings", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdErrorTrackingSettingsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update Error Tracking settings for a project
     * Updates Error Tracking settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdErrorTrackingSettingsRequest The patchApiV4ProjectsIdErrorTrackingSettingsRequest parameter
     * @return APIEntitiesErrorTrackingProjectSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesErrorTrackingProjectSetting patchApiV4ProjectsIdErrorTrackingSettings(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdErrorTrackingSettingsRequest patchApiV4ProjectsIdErrorTrackingSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingProjectSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdErrorTrackingSettingsRequestCreation(id, patchApiV4ProjectsIdErrorTrackingSettingsRequest).body(localVarReturnType);
    }

    /**
     * Update Error Tracking settings for a project
     * Updates Error Tracking settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdErrorTrackingSettingsRequest The patchApiV4ProjectsIdErrorTrackingSettingsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesErrorTrackingProjectSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesErrorTrackingProjectSetting> patchApiV4ProjectsIdErrorTrackingSettingsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdErrorTrackingSettingsRequest patchApiV4ProjectsIdErrorTrackingSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingProjectSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdErrorTrackingSettingsRequestCreation(id, patchApiV4ProjectsIdErrorTrackingSettingsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update Error Tracking settings for a project
     * Updates Error Tracking settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdErrorTrackingSettingsRequest The patchApiV4ProjectsIdErrorTrackingSettingsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdErrorTrackingSettingsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdErrorTrackingSettingsRequest patchApiV4ProjectsIdErrorTrackingSettingsRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdErrorTrackingSettingsRequestCreation(id, patchApiV4ProjectsIdErrorTrackingSettingsRequest);
    }

    /**
     * Create a client key
     * Creates a client key for integrated error tracking in a specified project. The public key attribute is generated automatically.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesErrorTrackingClientKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdErrorTrackingClientKeysRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdErrorTrackingClientKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesErrorTrackingClientKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/error_tracking/client_keys", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a client key
     * Creates a client key for integrated error tracking in a specified project. The public key attribute is generated automatically.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesErrorTrackingClientKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesErrorTrackingClientKey postApiV4ProjectsIdErrorTrackingClientKeys(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingClientKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdErrorTrackingClientKeysRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Create a client key
     * Creates a client key for integrated error tracking in a specified project. The public key attribute is generated automatically.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesErrorTrackingClientKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesErrorTrackingClientKey> postApiV4ProjectsIdErrorTrackingClientKeysWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingClientKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdErrorTrackingClientKeysRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Create a client key
     * Creates a client key for integrated error tracking in a specified project. The public key attribute is generated automatically.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdErrorTrackingClientKeysWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4ProjectsIdErrorTrackingClientKeysRequestCreation(id);
    }

    /**
     * Create Error Tracking settings for a project
     * Creates Error Tracking settings for a specified project. Available only with integrated error tracking. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdErrorTrackingSettingsRequest The putApiV4ProjectsIdErrorTrackingSettingsRequest parameter
     * @return APIEntitiesErrorTrackingProjectSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdErrorTrackingSettingsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdErrorTrackingSettingsRequest putApiV4ProjectsIdErrorTrackingSettingsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdErrorTrackingSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdErrorTrackingSettingsRequest' is set
        if (putApiV4ProjectsIdErrorTrackingSettingsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdErrorTrackingSettingsRequest' when calling putApiV4ProjectsIdErrorTrackingSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesErrorTrackingProjectSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/error_tracking/settings", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdErrorTrackingSettingsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create Error Tracking settings for a project
     * Creates Error Tracking settings for a specified project. Available only with integrated error tracking. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdErrorTrackingSettingsRequest The putApiV4ProjectsIdErrorTrackingSettingsRequest parameter
     * @return APIEntitiesErrorTrackingProjectSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesErrorTrackingProjectSetting putApiV4ProjectsIdErrorTrackingSettings(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdErrorTrackingSettingsRequest putApiV4ProjectsIdErrorTrackingSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingProjectSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdErrorTrackingSettingsRequestCreation(id, putApiV4ProjectsIdErrorTrackingSettingsRequest).body(localVarReturnType);
    }

    /**
     * Create Error Tracking settings for a project
     * Creates Error Tracking settings for a specified project. Available only with integrated error tracking. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdErrorTrackingSettingsRequest The putApiV4ProjectsIdErrorTrackingSettingsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesErrorTrackingProjectSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesErrorTrackingProjectSetting> putApiV4ProjectsIdErrorTrackingSettingsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdErrorTrackingSettingsRequest putApiV4ProjectsIdErrorTrackingSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesErrorTrackingProjectSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdErrorTrackingSettingsRequestCreation(id, putApiV4ProjectsIdErrorTrackingSettingsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create Error Tracking settings for a project
     * Creates Error Tracking settings for a specified project. Available only with integrated error tracking. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdErrorTrackingSettingsRequest The putApiV4ProjectsIdErrorTrackingSettingsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdErrorTrackingSettingsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdErrorTrackingSettingsRequest putApiV4ProjectsIdErrorTrackingSettingsRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdErrorTrackingSettingsRequestCreation(id, putApiV4ProjectsIdErrorTrackingSettingsRequest);
    }
}
