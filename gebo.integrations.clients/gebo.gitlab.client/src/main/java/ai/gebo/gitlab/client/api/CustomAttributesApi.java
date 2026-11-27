package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCustomAttribute;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdCustomAttributesKeyRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdCustomAttributesKeyRequest;
import ai.gebo.gitlab.client.model.PutApiV4UsersIdCustomAttributesKeyRequest;

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
public class CustomAttributesApi {
    private ApiClient apiClient;

    public CustomAttributesApi() {
        this(new ApiClient());
    }

    public CustomAttributesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a custom attribute for a group
     * Deletes a specified custom attribute for a group.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdCustomAttributesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4GroupsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);
        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/custom_attributes/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a custom attribute for a group
     * Deletes a specified custom attribute for a group.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdCustomAttributesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdCustomAttributesKeyRequestCreation(key, id).body(localVarReturnType);
    }

    /**
     * Delete a custom attribute for a group
     * Deletes a specified custom attribute for a group.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdCustomAttributesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdCustomAttributesKeyRequestCreation(key, id).toEntity(localVarReturnType);
    }

    /**
     * Delete a custom attribute for a group
     * Deletes a specified custom attribute for a group.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdCustomAttributesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4GroupsIdCustomAttributesKeyRequestCreation(key, id);
    }

    /**
     * Delete a custom attribute for a project
     * Deletes a specified custom attribute for a project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdCustomAttributesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4ProjectsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);
        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/custom_attributes/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a custom attribute for a project
     * Deletes a specified custom attribute for a project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdCustomAttributesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdCustomAttributesKeyRequestCreation(key, id).body(localVarReturnType);
    }

    /**
     * Delete a custom attribute for a project
     * Deletes a specified custom attribute for a project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdCustomAttributesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdCustomAttributesKeyRequestCreation(key, id).toEntity(localVarReturnType);
    }

    /**
     * Delete a custom attribute for a project
     * Deletes a specified custom attribute for a project.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdCustomAttributesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4ProjectsIdCustomAttributesKeyRequestCreation(key, id);
    }

    /**
     * Delete a custom attribute for a user
     * Deletes a specified custom attribute for a user.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UsersIdCustomAttributesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4UsersIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4UsersIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);
        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/custom_attributes/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a custom attribute for a user
     * Deletes a specified custom attribute for a user.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4UsersIdCustomAttributesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4UsersIdCustomAttributesKeyRequestCreation(key, id).body(localVarReturnType);
    }

    /**
     * Delete a custom attribute for a user
     * Deletes a specified custom attribute for a user.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4UsersIdCustomAttributesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdCustomAttributesKeyRequestCreation(key, id).toEntity(localVarReturnType);
    }

    /**
     * Delete a custom attribute for a user
     * Deletes a specified custom attribute for a user.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UsersIdCustomAttributesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4UsersIdCustomAttributesKeyRequestCreation(key, id);
    }

    /**
     * List all custom attributes for a group
     * Lists all custom attributes for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdCustomAttributesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdCustomAttributes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCustomAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/custom_attributes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all custom attributes for a group
     * Lists all custom attributes for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCustomAttribute> getApiV4GroupsIdCustomAttributes(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCustomAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdCustomAttributesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all custom attributes for a group
     * Lists all custom attributes for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCustomAttribute&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCustomAttribute>> getApiV4GroupsIdCustomAttributesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCustomAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdCustomAttributesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all custom attributes for a group
     * Lists all custom attributes for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdCustomAttributesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdCustomAttributesRequestCreation(id);
    }

    /**
     * Retrieve a custom attribute for a group
     * Retrieves a specified custom attribute for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdCustomAttributesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4GroupsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);
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

        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/custom_attributes/{key}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a custom attribute for a group
     * Retrieves a specified custom attribute for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCustomAttribute getApiV4GroupsIdCustomAttributesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdCustomAttributesKeyRequestCreation(key, id).body(localVarReturnType);
    }

    /**
     * Retrieve a custom attribute for a group
     * Retrieves a specified custom attribute for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCustomAttribute> getApiV4GroupsIdCustomAttributesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdCustomAttributesKeyRequestCreation(key, id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a custom attribute for a group
     * Retrieves a specified custom attribute for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdCustomAttributesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdCustomAttributesKeyRequestCreation(key, id);
    }

    /**
     * List all custom attributes for a project
     * Lists all custom attributes for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return List&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdCustomAttributesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdCustomAttributes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCustomAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/custom_attributes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all custom attributes for a project
     * Lists all custom attributes for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return List&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCustomAttribute> getApiV4ProjectsIdCustomAttributes(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCustomAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdCustomAttributesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all custom attributes for a project
     * Lists all custom attributes for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCustomAttribute&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCustomAttribute>> getApiV4ProjectsIdCustomAttributesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCustomAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdCustomAttributesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all custom attributes for a project
     * Lists all custom attributes for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdCustomAttributesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdCustomAttributesRequestCreation(id);
    }

    /**
     * Retrieve a custom attribute for a project
     * Retrieves a specified custom attribute for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdCustomAttributesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4ProjectsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);
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

        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/custom_attributes/{key}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a custom attribute for a project
     * Retrieves a specified custom attribute for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCustomAttribute getApiV4ProjectsIdCustomAttributesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdCustomAttributesKeyRequestCreation(key, id).body(localVarReturnType);
    }

    /**
     * Retrieve a custom attribute for a project
     * Retrieves a specified custom attribute for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCustomAttribute> getApiV4ProjectsIdCustomAttributesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdCustomAttributesKeyRequestCreation(key, id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a custom attribute for a project
     * Retrieves a specified custom attribute for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdCustomAttributesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdCustomAttributesKeyRequestCreation(key, id);
    }

    /**
     * List all custom attributes for a user
     * Lists all custom attributes for a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the user.
     * @return List&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdCustomAttributesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdCustomAttributes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesCustomAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/custom_attributes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all custom attributes for a user
     * Lists all custom attributes for a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the user.
     * @return List&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCustomAttribute> getApiV4UsersIdCustomAttributes(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCustomAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdCustomAttributesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all custom attributes for a user
     * Lists all custom attributes for a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the user.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCustomAttribute&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCustomAttribute>> getApiV4UsersIdCustomAttributesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCustomAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdCustomAttributesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all custom attributes for a user
     * Lists all custom attributes for a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdCustomAttributesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4UsersIdCustomAttributesRequestCreation(id);
    }

    /**
     * Retrieve a custom attribute for a user
     * Retrieves a specified custom attribute for a user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdCustomAttributesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling getApiV4UsersIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);
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

        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/custom_attributes/{key}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a custom attribute for a user
     * Retrieves a specified custom attribute for a user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCustomAttribute getApiV4UsersIdCustomAttributesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdCustomAttributesKeyRequestCreation(key, id).body(localVarReturnType);
    }

    /**
     * Retrieve a custom attribute for a user
     * Retrieves a specified custom attribute for a user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @return ResponseEntity&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCustomAttribute> getApiV4UsersIdCustomAttributesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdCustomAttributesKeyRequestCreation(key, id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a custom attribute for a user
     * Retrieves a specified custom attribute for a user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdCustomAttributesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4UsersIdCustomAttributesKeyRequestCreation(key, id);
    }

    /**
     * Creates or updates a custom attribute for a group
     * Creates or updates a custom attribute for a specified group. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdCustomAttributesKeyRequest The putApiV4GroupsIdCustomAttributesKeyRequest parameter
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdCustomAttributesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdCustomAttributesKeyRequest putApiV4GroupsIdCustomAttributesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4GroupsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4GroupsIdCustomAttributesKeyRequest' is set
        if (putApiV4GroupsIdCustomAttributesKeyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4GroupsIdCustomAttributesKeyRequest' when calling putApiV4GroupsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);
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

        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/custom_attributes/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdCustomAttributesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates or updates a custom attribute for a group
     * Creates or updates a custom attribute for a specified group. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdCustomAttributesKeyRequest The putApiV4GroupsIdCustomAttributesKeyRequest parameter
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCustomAttribute putApiV4GroupsIdCustomAttributesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdCustomAttributesKeyRequest putApiV4GroupsIdCustomAttributesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdCustomAttributesKeyRequestCreation(key, id, putApiV4GroupsIdCustomAttributesKeyRequest).body(localVarReturnType);
    }

    /**
     * Creates or updates a custom attribute for a group
     * Creates or updates a custom attribute for a specified group. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdCustomAttributesKeyRequest The putApiV4GroupsIdCustomAttributesKeyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCustomAttribute> putApiV4GroupsIdCustomAttributesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdCustomAttributesKeyRequest putApiV4GroupsIdCustomAttributesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdCustomAttributesKeyRequestCreation(key, id, putApiV4GroupsIdCustomAttributesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Creates or updates a custom attribute for a group
     * Creates or updates a custom attribute for a specified group. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdCustomAttributesKeyRequest The putApiV4GroupsIdCustomAttributesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdCustomAttributesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdCustomAttributesKeyRequest putApiV4GroupsIdCustomAttributesKeyRequest) throws RestClientResponseException {
        return putApiV4GroupsIdCustomAttributesKeyRequestCreation(key, id, putApiV4GroupsIdCustomAttributesKeyRequest);
    }

    /**
     * Creates or updates a custom attribute for a project
     * Creates or updates a custom attribute for a specified project. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdCustomAttributesKeyRequest The putApiV4ProjectsIdCustomAttributesKeyRequest parameter
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdCustomAttributesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdCustomAttributesKeyRequest putApiV4ProjectsIdCustomAttributesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4ProjectsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdCustomAttributesKeyRequest' is set
        if (putApiV4ProjectsIdCustomAttributesKeyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdCustomAttributesKeyRequest' when calling putApiV4ProjectsIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);
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

        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/custom_attributes/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdCustomAttributesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates or updates a custom attribute for a project
     * Creates or updates a custom attribute for a specified project. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdCustomAttributesKeyRequest The putApiV4ProjectsIdCustomAttributesKeyRequest parameter
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCustomAttribute putApiV4ProjectsIdCustomAttributesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdCustomAttributesKeyRequest putApiV4ProjectsIdCustomAttributesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdCustomAttributesKeyRequestCreation(key, id, putApiV4ProjectsIdCustomAttributesKeyRequest).body(localVarReturnType);
    }

    /**
     * Creates or updates a custom attribute for a project
     * Creates or updates a custom attribute for a specified project. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdCustomAttributesKeyRequest The putApiV4ProjectsIdCustomAttributesKeyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCustomAttribute> putApiV4ProjectsIdCustomAttributesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdCustomAttributesKeyRequest putApiV4ProjectsIdCustomAttributesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdCustomAttributesKeyRequestCreation(key, id, putApiV4ProjectsIdCustomAttributesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Creates or updates a custom attribute for a project
     * Creates or updates a custom attribute for a specified project. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdCustomAttributesKeyRequest The putApiV4ProjectsIdCustomAttributesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdCustomAttributesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdCustomAttributesKeyRequest putApiV4ProjectsIdCustomAttributesKeyRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdCustomAttributesKeyRequestCreation(key, id, putApiV4ProjectsIdCustomAttributesKeyRequest);
    }

    /**
     * Creates or updates a custom attribute for a user
     * Creates or updates a custom attribute for a specified user. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @param putApiV4UsersIdCustomAttributesKeyRequest The putApiV4UsersIdCustomAttributesKeyRequest parameter
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4UsersIdCustomAttributesKeyRequestCreation(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4UsersIdCustomAttributesKeyRequest putApiV4UsersIdCustomAttributesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4UsersIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4UsersIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4UsersIdCustomAttributesKeyRequest' is set
        if (putApiV4UsersIdCustomAttributesKeyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4UsersIdCustomAttributesKeyRequest' when calling putApiV4UsersIdCustomAttributesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key", key);
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

        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/custom_attributes/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4UsersIdCustomAttributesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates or updates a custom attribute for a user
     * Creates or updates a custom attribute for a specified user. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @param putApiV4UsersIdCustomAttributesKeyRequest The putApiV4UsersIdCustomAttributesKeyRequest parameter
     * @return APIEntitiesCustomAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCustomAttribute putApiV4UsersIdCustomAttributesKey(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4UsersIdCustomAttributesKeyRequest putApiV4UsersIdCustomAttributesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UsersIdCustomAttributesKeyRequestCreation(key, id, putApiV4UsersIdCustomAttributesKeyRequest).body(localVarReturnType);
    }

    /**
     * Creates or updates a custom attribute for a user
     * Creates or updates a custom attribute for a specified user. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @param putApiV4UsersIdCustomAttributesKeyRequest The putApiV4UsersIdCustomAttributesKeyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCustomAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCustomAttribute> putApiV4UsersIdCustomAttributesKeyWithHttpInfo(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4UsersIdCustomAttributesKeyRequest putApiV4UsersIdCustomAttributesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCustomAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UsersIdCustomAttributesKeyRequestCreation(key, id, putApiV4UsersIdCustomAttributesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Creates or updates a custom attribute for a user
     * Creates or updates a custom attribute for a specified user. If the attribute already exists, it is updated, otherwise a new attribute is created.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param key Key of the custom attribute.
     * @param id ID or URL-encoded path of the user.
     * @param putApiV4UsersIdCustomAttributesKeyRequest The putApiV4UsersIdCustomAttributesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4UsersIdCustomAttributesKeyWithResponseSpec(@jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4UsersIdCustomAttributesKeyRequest putApiV4UsersIdCustomAttributesKeyRequest) throws RestClientResponseException {
        return putApiV4UsersIdCustomAttributesKeyRequestCreation(key, id, putApiV4UsersIdCustomAttributesKeyRequest);
    }
}
