package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIVsCodeSettingsEntitiesVsCodeManifest;
import ai.gebo.gitlab.client.model.APIVsCodeSettingsEntitiesVsCodeSetting;
import ai.gebo.gitlab.client.model.APIVsCodeSettingsEntitiesVsCodeSettingReference;

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
public class VsCodeApi {
    private ApiClient apiClient;

    public VsCodeApi() {
        this(new ApiClient());
    }

    public VsCodeApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete all VS Code setting resources for a user
     * Deletes all VS Code setting resources of the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param settingsContextHash Settings context hash.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4VscodeSettingsSyncSettingsContextHashV1CollectionRequestCreation(@jakarta.annotation.Nonnull String settingsContextHash) throws RestClientResponseException {
        // verify the required parameter 'settingsContextHash' is set
        if (settingsContextHash == null) {
            throw new RestClientResponseException("Missing the required parameter 'settingsContextHash' when calling deleteApiV4VscodeSettingsSyncSettingsContextHashV1Collection", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("settings_context_hash", settingsContextHash);

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
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/{settings_context_hash}/v1/collection", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete all VS Code setting resources for a user
     * Deletes all VS Code setting resources of the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param settingsContextHash Settings context hash.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4VscodeSettingsSyncSettingsContextHashV1Collection(@jakarta.annotation.Nonnull String settingsContextHash) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4VscodeSettingsSyncSettingsContextHashV1CollectionRequestCreation(settingsContextHash).body(localVarReturnType);
    }

    /**
     * Delete all VS Code setting resources for a user
     * Deletes all VS Code setting resources of the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param settingsContextHash Settings context hash.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4VscodeSettingsSyncSettingsContextHashV1CollectionWithHttpInfo(@jakarta.annotation.Nonnull String settingsContextHash) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4VscodeSettingsSyncSettingsContextHashV1CollectionRequestCreation(settingsContextHash).toEntity(localVarReturnType);
    }

    /**
     * Delete all VS Code setting resources for a user
     * Deletes all VS Code setting resources of the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param settingsContextHash Settings context hash.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4VscodeSettingsSyncSettingsContextHashV1CollectionWithResponseSpec(@jakarta.annotation.Nonnull String settingsContextHash) throws RestClientResponseException {
        return deleteApiV4VscodeSettingsSyncSettingsContextHashV1CollectionRequestCreation(settingsContextHash);
    }

    /**
     * Delete all VS Code setting resources for a user
     * Deletes all VS Code setting resources of the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4VscodeSettingsSyncV1CollectionRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/v1/collection", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete all VS Code setting resources for a user
     * Deletes all VS Code setting resources of the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4VscodeSettingsSyncV1Collection() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4VscodeSettingsSyncV1CollectionRequestCreation().body(localVarReturnType);
    }

    /**
     * Delete all VS Code setting resources for a user
     * Deletes all VS Code setting resources of the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4VscodeSettingsSyncV1CollectionWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4VscodeSettingsSyncV1CollectionRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Delete all VS Code setting resources for a user
     * Deletes all VS Code setting resources of the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4VscodeSettingsSyncV1CollectionWithResponseSpec() throws RestClientResponseException {
        return deleteApiV4VscodeSettingsSyncV1CollectionRequestCreation();
    }

    /**
     * Retrieve the settings manifest for Settings Sync
     * Retrieves the manifest of the settings stored for VS Code Settings Sync.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param settingsContextHash Settings context hash.
     * @return APIVsCodeSettingsEntitiesVsCodeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4VscodeSettingsSyncSettingsContextHashV1ManifestRequestCreation(@jakarta.annotation.Nonnull String settingsContextHash) throws RestClientResponseException {
        // verify the required parameter 'settingsContextHash' is set
        if (settingsContextHash == null) {
            throw new RestClientResponseException("Missing the required parameter 'settingsContextHash' when calling getApiV4VscodeSettingsSyncSettingsContextHashV1Manifest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("settings_context_hash", settingsContextHash);

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

        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/{settings_context_hash}/v1/manifest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the settings manifest for Settings Sync
     * Retrieves the manifest of the settings stored for VS Code Settings Sync.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param settingsContextHash Settings context hash.
     * @return APIVsCodeSettingsEntitiesVsCodeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIVsCodeSettingsEntitiesVsCodeManifest getApiV4VscodeSettingsSyncSettingsContextHashV1Manifest(@jakarta.annotation.Nonnull String settingsContextHash) throws RestClientResponseException {
        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncSettingsContextHashV1ManifestRequestCreation(settingsContextHash).body(localVarReturnType);
    }

    /**
     * Retrieve the settings manifest for Settings Sync
     * Retrieves the manifest of the settings stored for VS Code Settings Sync.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param settingsContextHash Settings context hash.
     * @return ResponseEntity&lt;APIVsCodeSettingsEntitiesVsCodeManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIVsCodeSettingsEntitiesVsCodeManifest> getApiV4VscodeSettingsSyncSettingsContextHashV1ManifestWithHttpInfo(@jakarta.annotation.Nonnull String settingsContextHash) throws RestClientResponseException {
        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncSettingsContextHashV1ManifestRequestCreation(settingsContextHash).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the settings manifest for Settings Sync
     * Retrieves the manifest of the settings stored for VS Code Settings Sync.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param settingsContextHash Settings context hash.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4VscodeSettingsSyncSettingsContextHashV1ManifestWithResponseSpec(@jakarta.annotation.Nonnull String settingsContextHash) throws RestClientResponseException {
        return getApiV4VscodeSettingsSyncSettingsContextHashV1ManifestRequestCreation(settingsContextHash);
    }

    /**
     * List all references to a VS Code setting resource
     * Lists all references to the stored versions of a VS Code setting resource.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @return List&lt;APIVsCodeSettingsEntitiesVsCodeSettingReference&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameRequestCreation(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        // verify the required parameter 'settingsContextHash' is set
        if (settingsContextHash == null) {
            throw new RestClientResponseException("Missing the required parameter 'settingsContextHash' when calling getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'resourceName' is set
        if (resourceName == null) {
            throw new RestClientResponseException("Missing the required parameter 'resourceName' when calling getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("settings_context_hash", settingsContextHash);
        pathParams.put("resource_name", resourceName);

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

        ParameterizedTypeReference<List<APIVsCodeSettingsEntitiesVsCodeSettingReference>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/{settings_context_hash}/v1/resource/{resource_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all references to a VS Code setting resource
     * Lists all references to the stored versions of a VS Code setting resource.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @return List&lt;APIVsCodeSettingsEntitiesVsCodeSettingReference&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIVsCodeSettingsEntitiesVsCodeSettingReference> getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceName(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIVsCodeSettingsEntitiesVsCodeSettingReference>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameRequestCreation(settingsContextHash, resourceName).body(localVarReturnType);
    }

    /**
     * List all references to a VS Code setting resource
     * Lists all references to the stored versions of a VS Code setting resource.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @return ResponseEntity&lt;List&lt;APIVsCodeSettingsEntitiesVsCodeSettingReference&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIVsCodeSettingsEntitiesVsCodeSettingReference>> getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameWithHttpInfo(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIVsCodeSettingsEntitiesVsCodeSettingReference>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameRequestCreation(settingsContextHash, resourceName).toEntity(localVarReturnType);
    }

    /**
     * List all references to a VS Code setting resource
     * Lists all references to the stored versions of a VS Code setting resource.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameWithResponseSpec(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        return getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameRequestCreation(settingsContextHash, resourceName);
    }

    /**
     * Retrieve a specific setting resource
     * Retrieves a single VS Code setting resource. Returns 204 when the resource does not exist.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No content
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @param id ID of the resource to retrieve.
     * @return APIVsCodeSettingsEntitiesVsCodeSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameIdRequestCreation(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'settingsContextHash' is set
        if (settingsContextHash == null) {
            throw new RestClientResponseException("Missing the required parameter 'settingsContextHash' when calling getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'resourceName' is set
        if (resourceName == null) {
            throw new RestClientResponseException("Missing the required parameter 'resourceName' when calling getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("settings_context_hash", settingsContextHash);
        pathParams.put("resource_name", resourceName);
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

        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/{settings_context_hash}/v1/resource/{resource_name}/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a specific setting resource
     * Retrieves a single VS Code setting resource. Returns 204 when the resource does not exist.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No content
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @param id ID of the resource to retrieve.
     * @return APIVsCodeSettingsEntitiesVsCodeSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIVsCodeSettingsEntitiesVsCodeSetting getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameId(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameIdRequestCreation(settingsContextHash, resourceName, id).body(localVarReturnType);
    }

    /**
     * Retrieve a specific setting resource
     * Retrieves a single VS Code setting resource. Returns 204 when the resource does not exist.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No content
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @param id ID of the resource to retrieve.
     * @return ResponseEntity&lt;APIVsCodeSettingsEntitiesVsCodeSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIVsCodeSettingsEntitiesVsCodeSetting> getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameIdWithHttpInfo(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameIdRequestCreation(settingsContextHash, resourceName, id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a specific setting resource
     * Retrieves a single VS Code setting resource. Returns 204 when the resource does not exist.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No content
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @param id ID of the resource to retrieve.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameIdWithResponseSpec(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameIdRequestCreation(settingsContextHash, resourceName, id);
    }

    /**
     * Retrieve the settings manifest for Settings Sync
     * Retrieves the manifest of the settings stored for VS Code Settings Sync.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @return APIVsCodeSettingsEntitiesVsCodeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4VscodeSettingsSyncV1ManifestRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/v1/manifest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the settings manifest for Settings Sync
     * Retrieves the manifest of the settings stored for VS Code Settings Sync.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @return APIVsCodeSettingsEntitiesVsCodeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIVsCodeSettingsEntitiesVsCodeManifest getApiV4VscodeSettingsSyncV1Manifest() throws RestClientResponseException {
        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncV1ManifestRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve the settings manifest for Settings Sync
     * Retrieves the manifest of the settings stored for VS Code Settings Sync.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @return ResponseEntity&lt;APIVsCodeSettingsEntitiesVsCodeManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIVsCodeSettingsEntitiesVsCodeManifest> getApiV4VscodeSettingsSyncV1ManifestWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncV1ManifestRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve the settings manifest for Settings Sync
     * Retrieves the manifest of the settings stored for VS Code Settings Sync.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4VscodeSettingsSyncV1ManifestWithResponseSpec() throws RestClientResponseException {
        return getApiV4VscodeSettingsSyncV1ManifestRequestCreation();
    }

    /**
     * List all references to a VS Code setting resource
     * Lists all references to the stored versions of a VS Code setting resource.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @return List&lt;APIVsCodeSettingsEntitiesVsCodeSettingReference&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4VscodeSettingsSyncV1ResourceResourceNameRequestCreation(@jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        // verify the required parameter 'resourceName' is set
        if (resourceName == null) {
            throw new RestClientResponseException("Missing the required parameter 'resourceName' when calling getApiV4VscodeSettingsSyncV1ResourceResourceName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("resource_name", resourceName);

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

        ParameterizedTypeReference<List<APIVsCodeSettingsEntitiesVsCodeSettingReference>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/v1/resource/{resource_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all references to a VS Code setting resource
     * Lists all references to the stored versions of a VS Code setting resource.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @return List&lt;APIVsCodeSettingsEntitiesVsCodeSettingReference&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIVsCodeSettingsEntitiesVsCodeSettingReference> getApiV4VscodeSettingsSyncV1ResourceResourceName(@jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIVsCodeSettingsEntitiesVsCodeSettingReference>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncV1ResourceResourceNameRequestCreation(resourceName).body(localVarReturnType);
    }

    /**
     * List all references to a VS Code setting resource
     * Lists all references to the stored versions of a VS Code setting resource.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @return ResponseEntity&lt;List&lt;APIVsCodeSettingsEntitiesVsCodeSettingReference&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIVsCodeSettingsEntitiesVsCodeSettingReference>> getApiV4VscodeSettingsSyncV1ResourceResourceNameWithHttpInfo(@jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIVsCodeSettingsEntitiesVsCodeSettingReference>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncV1ResourceResourceNameRequestCreation(resourceName).toEntity(localVarReturnType);
    }

    /**
     * List all references to a VS Code setting resource
     * Lists all references to the stored versions of a VS Code setting resource.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4VscodeSettingsSyncV1ResourceResourceNameWithResponseSpec(@jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        return getApiV4VscodeSettingsSyncV1ResourceResourceNameRequestCreation(resourceName);
    }

    /**
     * Retrieve a specific setting resource
     * Retrieves a single VS Code setting resource. Returns 204 when the resource does not exist.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No content
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @param id ID of the resource to retrieve.
     * @return APIVsCodeSettingsEntitiesVsCodeSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4VscodeSettingsSyncV1ResourceResourceNameIdRequestCreation(@jakarta.annotation.Nonnull String resourceName, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'resourceName' is set
        if (resourceName == null) {
            throw new RestClientResponseException("Missing the required parameter 'resourceName' when calling getApiV4VscodeSettingsSyncV1ResourceResourceNameId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4VscodeSettingsSyncV1ResourceResourceNameId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("resource_name", resourceName);
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

        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/v1/resource/{resource_name}/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a specific setting resource
     * Retrieves a single VS Code setting resource. Returns 204 when the resource does not exist.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No content
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @param id ID of the resource to retrieve.
     * @return APIVsCodeSettingsEntitiesVsCodeSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIVsCodeSettingsEntitiesVsCodeSetting getApiV4VscodeSettingsSyncV1ResourceResourceNameId(@jakarta.annotation.Nonnull String resourceName, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncV1ResourceResourceNameIdRequestCreation(resourceName, id).body(localVarReturnType);
    }

    /**
     * Retrieve a specific setting resource
     * Retrieves a single VS Code setting resource. Returns 204 when the resource does not exist.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No content
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @param id ID of the resource to retrieve.
     * @return ResponseEntity&lt;APIVsCodeSettingsEntitiesVsCodeSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIVsCodeSettingsEntitiesVsCodeSetting> getApiV4VscodeSettingsSyncV1ResourceResourceNameIdWithHttpInfo(@jakarta.annotation.Nonnull String resourceName, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIVsCodeSettingsEntitiesVsCodeSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VscodeSettingsSyncV1ResourceResourceNameIdRequestCreation(resourceName, id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a specific setting resource
     * Retrieves a single VS Code setting resource. Returns 204 when the resource does not exist.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No content
     * <p><b>400</b> - 400 bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @param id ID of the resource to retrieve.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4VscodeSettingsSyncV1ResourceResourceNameIdWithResponseSpec(@jakarta.annotation.Nonnull String resourceName, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4VscodeSettingsSyncV1ResourceResourceNameIdRequestCreation(resourceName, id);
    }

    /**
     * Create or update a specific setting
     * Creates a VS Code setting resource, or updates it if it already exists.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameRequestCreation(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        // verify the required parameter 'settingsContextHash' is set
        if (settingsContextHash == null) {
            throw new RestClientResponseException("Missing the required parameter 'settingsContextHash' when calling postApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'resourceName' is set
        if (resourceName == null) {
            throw new RestClientResponseException("Missing the required parameter 'resourceName' when calling postApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("settings_context_hash", settingsContextHash);
        pathParams.put("resource_name", resourceName);

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
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/{settings_context_hash}/v1/resource/{resource_name}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or update a specific setting
     * Creates a VS Code setting resource, or updates it if it already exists.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceName(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameRequestCreation(settingsContextHash, resourceName).body(localVarReturnType);
    }

    /**
     * Create or update a specific setting
     * Creates a VS Code setting resource, or updates it if it already exists.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameWithHttpInfo(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameRequestCreation(settingsContextHash, resourceName).toEntity(localVarReturnType);
    }

    /**
     * Create or update a specific setting
     * Creates a VS Code setting resource, or updates it if it already exists.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param settingsContextHash Settings context hash.
     * @param resourceName Name of the resource such as settings.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameWithResponseSpec(@jakarta.annotation.Nonnull String settingsContextHash, @jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        return postApiV4VscodeSettingsSyncSettingsContextHashV1ResourceResourceNameRequestCreation(settingsContextHash, resourceName);
    }

    /**
     * Create or update a specific setting
     * Creates a VS Code setting resource, or updates it if it already exists.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4VscodeSettingsSyncV1ResourceResourceNameRequestCreation(@jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        // verify the required parameter 'resourceName' is set
        if (resourceName == null) {
            throw new RestClientResponseException("Missing the required parameter 'resourceName' when calling postApiV4VscodeSettingsSyncV1ResourceResourceName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("resource_name", resourceName);

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
        return apiClient.invokeAPI("/api/v4/vscode/settings_sync/v1/resource/{resource_name}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or update a specific setting
     * Creates a VS Code setting resource, or updates it if it already exists.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4VscodeSettingsSyncV1ResourceResourceName(@jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4VscodeSettingsSyncV1ResourceResourceNameRequestCreation(resourceName).body(localVarReturnType);
    }

    /**
     * Create or update a specific setting
     * Creates a VS Code setting resource, or updates it if it already exists.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4VscodeSettingsSyncV1ResourceResourceNameWithHttpInfo(@jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VscodeSettingsSyncV1ResourceResourceNameRequestCreation(resourceName).toEntity(localVarReturnType);
    }

    /**
     * Create or update a specific setting
     * Creates a VS Code setting resource, or updates it if it already exists.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - Not Found
     * @param resourceName Name of the resource such as settings.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4VscodeSettingsSyncV1ResourceResourceNameWithResponseSpec(@jakarta.annotation.Nonnull String resourceName) throws RestClientResponseException {
        return postApiV4VscodeSettingsSyncV1ResourceResourceNameRequestCreation(resourceName);
    }
}
