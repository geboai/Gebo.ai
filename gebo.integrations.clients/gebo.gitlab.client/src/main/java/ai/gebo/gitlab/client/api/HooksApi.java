package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGroupHook;
import ai.gebo.gitlab.client.model.APIEntitiesHook;
import ai.gebo.gitlab.client.model.APIEntitiesProjectHook;
import ai.gebo.gitlab.client.model.APIEntitiesWebHookLog;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdHooksRequest;
import ai.gebo.gitlab.client.model.PostApiV4HooksRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdHooksRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdHooksHookIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest;
import ai.gebo.gitlab.client.model.PutApiV4HooksHookIdCustomHeadersKeyRequest;
import ai.gebo.gitlab.client.model.PutApiV4HooksHookIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4HooksHookIdUrlVariablesKeyRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdHooksHookIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest;

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
public class HooksApi {
    private ApiClient apiClient;

    public HooksApi() {
        this(new ApiClient());
    }

    public HooksApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a group hook
     * Deletes a hook from a group. This is an idempotent method and can be called multiple times. Either the hook is available or not.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdHooksHookIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling deleteApiV4GroupsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("hook_id", hookId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group hook
     * Deletes a hook from a group. This is an idempotent method and can be called multiple times. Either the hook is available or not.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdHooksHookId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdHooksHookIdRequestCreation(id, hookId).body(localVarReturnType);
    }

    /**
     * Delete a group hook
     * Deletes a hook from a group. This is an idempotent method and can be called multiple times. Either the hook is available or not.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdHooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdHooksHookIdRequestCreation(id, hookId).toEntity(localVarReturnType);
    }

    /**
     * Delete a group hook
     * Deletes a hook from a group. This is an idempotent method and can be called multiple times. Either the hook is available or not.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdHooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        return deleteApiV4GroupsIdHooksHookIdRequestCreation(id, hookId);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdHooksHookIdCustomHeadersKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling deleteApiV4GroupsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4GroupsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}/custom_headers/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdHooksHookIdCustomHeadersKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id).body(localVarReturnType);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdHooksHookIdCustomHeadersKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id).toEntity(localVarReturnType);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdHooksHookIdCustomHeadersKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4GroupsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdHooksHookIdUrlVariablesKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling deleteApiV4GroupsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4GroupsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}/url_variables/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdHooksHookIdUrlVariablesKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id).body(localVarReturnType);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdHooksHookIdUrlVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id).toEntity(localVarReturnType);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdHooksHookIdUrlVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4GroupsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id);
    }

    /**
     * Delete a system hook
     * Deletes a specified system hook. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @return APIEntitiesHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4HooksHookIdRequestCreation(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling deleteApiV4HooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);

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

        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/hooks/{hook_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a system hook
     * Deletes a specified system hook. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @return APIEntitiesHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesHook deleteApiV4HooksHookId(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4HooksHookIdRequestCreation(hookId).body(localVarReturnType);
    }

    /**
     * Delete a system hook
     * Deletes a specified system hook. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @return ResponseEntity&lt;APIEntitiesHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesHook> deleteApiV4HooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4HooksHookIdRequestCreation(hookId).toEntity(localVarReturnType);
    }

    /**
     * Delete a system hook
     * Deletes a specified system hook. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4HooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        return deleteApiV4HooksHookIdRequestCreation(hookId);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4HooksHookIdCustomHeadersKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling deleteApiV4HooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4HooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("key", key);

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
        return apiClient.invokeAPI("/api/v4/hooks/{hook_id}/custom_headers/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4HooksHookIdCustomHeadersKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4HooksHookIdCustomHeadersKeyRequestCreation(hookId, key).body(localVarReturnType);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4HooksHookIdCustomHeadersKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4HooksHookIdCustomHeadersKeyRequestCreation(hookId, key).toEntity(localVarReturnType);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4HooksHookIdCustomHeadersKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return deleteApiV4HooksHookIdCustomHeadersKeyRequestCreation(hookId, key);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4HooksHookIdUrlVariablesKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling deleteApiV4HooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4HooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("key", key);

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
        return apiClient.invokeAPI("/api/v4/hooks/{hook_id}/url_variables/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4HooksHookIdUrlVariablesKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4HooksHookIdUrlVariablesKeyRequestCreation(hookId, key).body(localVarReturnType);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4HooksHookIdUrlVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4HooksHookIdUrlVariablesKeyRequestCreation(hookId, key).toEntity(localVarReturnType);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4HooksHookIdUrlVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key) throws RestClientResponseException {
        return deleteApiV4HooksHookIdUrlVariablesKeyRequestCreation(hookId, key);
    }

    /**
     * Delete a project webhook
     * Deletes a specified webhook for a project. If the webhook was already deleted, returns &#x60;404 Not found&#x60;.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdHooksHookIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling deleteApiV4ProjectsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("hook_id", hookId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project webhook
     * Deletes a specified webhook for a project. If the webhook was already deleted, returns &#x60;404 Not found&#x60;.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdHooksHookId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdHooksHookIdRequestCreation(id, hookId).body(localVarReturnType);
    }

    /**
     * Delete a project webhook
     * Deletes a specified webhook for a project. If the webhook was already deleted, returns &#x60;404 Not found&#x60;.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdHooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdHooksHookIdRequestCreation(id, hookId).toEntity(localVarReturnType);
    }

    /**
     * Delete a project webhook
     * Deletes a specified webhook for a project. If the webhook was already deleted, returns &#x60;404 Not found&#x60;.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdHooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdHooksHookIdRequestCreation(id, hookId);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling deleteApiV4ProjectsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4ProjectsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}/custom_headers/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdHooksHookIdCustomHeadersKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id).body(localVarReturnType);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdHooksHookIdCustomHeadersKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id).toEntity(localVarReturnType);
    }

    /**
     * Delete a custom header
     * Deletes a custom header from a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdHooksHookIdCustomHeadersKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling deleteApiV4ProjectsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling deleteApiV4ProjectsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}/url_variables/{key}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdHooksHookIdUrlVariablesKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id).body(localVarReturnType);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdHooksHookIdUrlVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id).toEntity(localVarReturnType);
    }

    /**
     * Delete a URL variable
     * Deletes a URL variable from a specified webhook.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdHooksHookIdUrlVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id);
    }

    /**
     * List all group hooks
     * Lists all group hooks.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdHooksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdHooks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesGroupHook>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group hooks
     * Lists all group hooks.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGroupHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGroupHook> getApiV4GroupsIdHooks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupHook>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdHooksRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group hooks
     * Lists all group hooks.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGroupHook&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGroupHook>> getApiV4GroupsIdHooksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGroupHook>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdHooksRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group hooks
     * Lists all group hooks.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdHooksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdHooksRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a group hook
     * Retrieves a specified group hook.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @return APIEntitiesGroupHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdHooksHookIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling getApiV4GroupsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("hook_id", hookId);

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

        ParameterizedTypeReference<APIEntitiesGroupHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group hook
     * Retrieves a specified group hook.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @return APIEntitiesGroupHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupHook getApiV4GroupsIdHooksHookId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdHooksHookIdRequestCreation(id, hookId).body(localVarReturnType);
    }

    /**
     * Retrieve a group hook
     * Retrieves a specified group hook.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @return ResponseEntity&lt;APIEntitiesGroupHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupHook> getApiV4GroupsIdHooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdHooksHookIdRequestCreation(id, hookId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group hook
     * Retrieves a specified group hook.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdHooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        return getApiV4GroupsIdHooksHookIdRequestCreation(id, hookId);
    }

    /**
     * List all events
     * Lists all events from the past seven days for a specified webhook. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @param status Response status code of the events, for example &#x60;200&#x60; or &#x60;500&#x60;. You can search by status category: &#x60;successful&#x60; (200-299), &#x60;client_failure&#x60; (400-499), and &#x60;server_failure&#x60; (500-599).
     * @param perPage Number of items to list per page.
     * @param page Page number of results to retrieve.
     * @return List&lt;APIEntitiesWebHookLog&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdHooksHookIdEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable List<String> status, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdHooksHookIdEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling getApiV4GroupsIdHooksHookIdEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("hook_id", hookId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesWebHookLog>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}/events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all events
     * Lists all events from the past seven days for a specified webhook. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @param status Response status code of the events, for example &#x60;200&#x60; or &#x60;500&#x60;. You can search by status category: &#x60;successful&#x60; (200-299), &#x60;client_failure&#x60; (400-499), and &#x60;server_failure&#x60; (500-599).
     * @param perPage Number of items to list per page.
     * @param page Page number of results to retrieve.
     * @return List&lt;APIEntitiesWebHookLog&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesWebHookLog> getApiV4GroupsIdHooksHookIdEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable List<String> status, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesWebHookLog>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdHooksHookIdEventsRequestCreation(id, hookId, status, perPage, page).body(localVarReturnType);
    }

    /**
     * List all events
     * Lists all events from the past seven days for a specified webhook. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @param status Response status code of the events, for example &#x60;200&#x60; or &#x60;500&#x60;. You can search by status category: &#x60;successful&#x60; (200-299), &#x60;client_failure&#x60; (400-499), and &#x60;server_failure&#x60; (500-599).
     * @param perPage Number of items to list per page.
     * @param page Page number of results to retrieve.
     * @return ResponseEntity&lt;List&lt;APIEntitiesWebHookLog&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesWebHookLog>> getApiV4GroupsIdHooksHookIdEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable List<String> status, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesWebHookLog>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdHooksHookIdEventsRequestCreation(id, hookId, status, perPage, page).toEntity(localVarReturnType);
    }

    /**
     * List all events
     * Lists all events from the past seven days for a specified webhook. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @param status Response status code of the events, for example &#x60;200&#x60; or &#x60;500&#x60;. You can search by status category: &#x60;successful&#x60; (200-299), &#x60;client_failure&#x60; (400-499), and &#x60;server_failure&#x60; (500-599).
     * @param perPage Number of items to list per page.
     * @param page Page number of results to retrieve.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdHooksHookIdEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable List<String> status, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        return getApiV4GroupsIdHooksHookIdEventsRequestCreation(id, hookId, status, perPage, page);
    }

    /**
     * List all system hooks
     * Lists all system hooks for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4HooksRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesHook>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/hooks", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all system hooks
     * Lists all system hooks for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesHook> getApiV4Hooks(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesHook>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4HooksRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all system hooks
     * Lists all system hooks for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesHook&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesHook>> getApiV4HooksWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesHook>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4HooksRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all system hooks
     * Lists all system hooks for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4HooksWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4HooksRequestCreation(page, perPage);
    }

    /**
     * Retrieve a system hook
     * Retrieves a specified system hook.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @return APIEntitiesHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4HooksHookIdRequestCreation(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling getApiV4HooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);

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

        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/hooks/{hook_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a system hook
     * Retrieves a specified system hook.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @return APIEntitiesHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesHook getApiV4HooksHookId(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4HooksHookIdRequestCreation(hookId).body(localVarReturnType);
    }

    /**
     * Retrieve a system hook
     * Retrieves a specified system hook.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @return ResponseEntity&lt;APIEntitiesHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesHook> getApiV4HooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4HooksHookIdRequestCreation(hookId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a system hook
     * Retrieves a specified system hook.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4HooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        return getApiV4HooksHookIdRequestCreation(hookId);
    }

    /**
     * List all webhooks for a project
     * Lists all webhooks for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdHooksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdHooks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesProjectHook>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all webhooks for a project
     * Lists all webhooks for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesProjectHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesProjectHook> getApiV4ProjectsIdHooks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectHook>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdHooksRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all webhooks for a project
     * Lists all webhooks for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesProjectHook&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesProjectHook>> getApiV4ProjectsIdHooksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesProjectHook>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdHooksRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all webhooks for a project
     * Lists all webhooks for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdHooksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdHooksRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a project webhook
     * Retrieves a specified webhook for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @return APIEntitiesProjectHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdHooksHookIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling getApiV4ProjectsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("hook_id", hookId);

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

        ParameterizedTypeReference<APIEntitiesProjectHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a project webhook
     * Retrieves a specified webhook for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @return APIEntitiesProjectHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectHook getApiV4ProjectsIdHooksHookId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdHooksHookIdRequestCreation(id, hookId).body(localVarReturnType);
    }

    /**
     * Retrieve a project webhook
     * Retrieves a specified webhook for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @return ResponseEntity&lt;APIEntitiesProjectHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectHook> getApiV4ProjectsIdHooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdHooksHookIdRequestCreation(id, hookId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a project webhook
     * Retrieves a specified webhook for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdHooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        return getApiV4ProjectsIdHooksHookIdRequestCreation(id, hookId);
    }

    /**
     * List all events
     * Lists all events from the past seven days for a specified webhook. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the hook.
     * @param status Response status code of the events, for example &#x60;200&#x60; or &#x60;500&#x60;. You can search by status category: &#x60;successful&#x60; (200-299), &#x60;client_failure&#x60; (400-499), and &#x60;server_failure&#x60; (500-599).
     * @param perPage Number of items to list per page.
     * @param page Page number of results to retrieve.
     * @return List&lt;APIEntitiesWebHookLog&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdHooksHookIdEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable List<String> status, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdHooksHookIdEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling getApiV4ProjectsIdHooksHookIdEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("hook_id", hookId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesWebHookLog>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}/events", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all events
     * Lists all events from the past seven days for a specified webhook. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the hook.
     * @param status Response status code of the events, for example &#x60;200&#x60; or &#x60;500&#x60;. You can search by status category: &#x60;successful&#x60; (200-299), &#x60;client_failure&#x60; (400-499), and &#x60;server_failure&#x60; (500-599).
     * @param perPage Number of items to list per page.
     * @param page Page number of results to retrieve.
     * @return List&lt;APIEntitiesWebHookLog&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesWebHookLog> getApiV4ProjectsIdHooksHookIdEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable List<String> status, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesWebHookLog>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdHooksHookIdEventsRequestCreation(id, hookId, status, perPage, page).body(localVarReturnType);
    }

    /**
     * List all events
     * Lists all events from the past seven days for a specified webhook. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the hook.
     * @param status Response status code of the events, for example &#x60;200&#x60; or &#x60;500&#x60;. You can search by status category: &#x60;successful&#x60; (200-299), &#x60;client_failure&#x60; (400-499), and &#x60;server_failure&#x60; (500-599).
     * @param perPage Number of items to list per page.
     * @param page Page number of results to retrieve.
     * @return ResponseEntity&lt;List&lt;APIEntitiesWebHookLog&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesWebHookLog>> getApiV4ProjectsIdHooksHookIdEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable List<String> status, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesWebHookLog>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdHooksHookIdEventsRequestCreation(id, hookId, status, perPage, page).toEntity(localVarReturnType);
    }

    /**
     * List all events
     * Lists all events from the past seven days for a specified webhook. This feature was introduced in GitLab 17.3.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the hook.
     * @param status Response status code of the events, for example &#x60;200&#x60; or &#x60;500&#x60;. You can search by status category: &#x60;successful&#x60; (200-299), &#x60;client_failure&#x60; (400-499), and &#x60;server_failure&#x60; (500-599).
     * @param perPage Number of items to list per page.
     * @param page Page number of results to retrieve.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdHooksHookIdEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable List<String> status, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        return getApiV4ProjectsIdHooksHookIdEventsRequestCreation(id, hookId, status, perPage, page);
    }

    /**
     * Create a group hook
     * Creates a hook for a specified group. This endpoint has a rate limit of 10 requests per minute for each authenticated user for a given group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdHooksRequest The postApiV4GroupsIdHooksRequest parameter
     * @return APIEntitiesGroupHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdHooksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdHooksRequest postApiV4GroupsIdHooksRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdHooks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdHooksRequest' is set
        if (postApiV4GroupsIdHooksRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdHooksRequest' when calling postApiV4GroupsIdHooks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGroupHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdHooksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group hook
     * Creates a hook for a specified group. This endpoint has a rate limit of 10 requests per minute for each authenticated user for a given group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdHooksRequest The postApiV4GroupsIdHooksRequest parameter
     * @return APIEntitiesGroupHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupHook postApiV4GroupsIdHooks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdHooksRequest postApiV4GroupsIdHooksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdHooksRequestCreation(id, postApiV4GroupsIdHooksRequest).body(localVarReturnType);
    }

    /**
     * Create a group hook
     * Creates a hook for a specified group. This endpoint has a rate limit of 10 requests per minute for each authenticated user for a given group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdHooksRequest The postApiV4GroupsIdHooksRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupHook> postApiV4GroupsIdHooksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdHooksRequest postApiV4GroupsIdHooksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdHooksRequestCreation(id, postApiV4GroupsIdHooksRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group hook
     * Creates a hook for a specified group. This endpoint has a rate limit of 10 requests per minute for each authenticated user for a given group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdHooksRequest The postApiV4GroupsIdHooksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdHooksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdHooksRequest postApiV4GroupsIdHooksRequest) throws RestClientResponseException {
        return postApiV4GroupsIdHooksRequestCreation(id, postApiV4GroupsIdHooksRequest);
    }

    /**
     * Resend a webhook event
     * Resends a webhook event. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. All webhooks in the same project or group share the limit. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param hookLogId ID of the hook log entry.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdHooksHookIdEventsHookLogIdResendRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull Integer hookLogId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling postApiV4GroupsIdHooksHookIdEventsHookLogIdResend", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookLogId' is set
        if (hookLogId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookLogId' when calling postApiV4GroupsIdHooksHookIdEventsHookLogIdResend", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdHooksHookIdEventsHookLogIdResend", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("hook_log_id", hookLogId);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}/events/{hook_log_id}/resend", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Resend a webhook event
     * Resends a webhook event. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. All webhooks in the same project or group share the limit. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param hookLogId ID of the hook log entry.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdHooksHookIdEventsHookLogIdResend(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull Integer hookLogId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdHooksHookIdEventsHookLogIdResendRequestCreation(hookId, hookLogId, id).body(localVarReturnType);
    }

    /**
     * Resend a webhook event
     * Resends a webhook event. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. All webhooks in the same project or group share the limit. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param hookLogId ID of the hook log entry.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdHooksHookIdEventsHookLogIdResendWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull Integer hookLogId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdHooksHookIdEventsHookLogIdResendRequestCreation(hookId, hookLogId, id).toEntity(localVarReturnType);
    }

    /**
     * Resend a webhook event
     * Resends a webhook event. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. All webhooks in the same project or group share the limit. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param hookLogId ID of the hook log entry.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdHooksHookIdEventsHookLogIdResendWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull Integer hookLogId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4GroupsIdHooksHookIdEventsHookLogIdResendRequestCreation(hookId, hookLogId, id);
    }

    /**
     * Trigger a test webhook
     * Triggers a test webhook. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 16.11 for project hooks and GitLab 17.1 for group hooks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param hookId ID of the hook.
     * @param trigger Type of webhook event to test.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdHooksHookIdTestTriggerRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String trigger, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling postApiV4GroupsIdHooksHookIdTestTrigger", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'trigger' is set
        if (trigger == null) {
            throw new RestClientResponseException("Missing the required parameter 'trigger' when calling postApiV4GroupsIdHooksHookIdTestTrigger", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdHooksHookIdTestTrigger", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("trigger", trigger);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}/test/{trigger}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Trigger a test webhook
     * Triggers a test webhook. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 16.11 for project hooks and GitLab 17.1 for group hooks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param hookId ID of the hook.
     * @param trigger Type of webhook event to test.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdHooksHookIdTestTrigger(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String trigger, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdHooksHookIdTestTriggerRequestCreation(hookId, trigger, id).body(localVarReturnType);
    }

    /**
     * Trigger a test webhook
     * Triggers a test webhook. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 16.11 for project hooks and GitLab 17.1 for group hooks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param hookId ID of the hook.
     * @param trigger Type of webhook event to test.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdHooksHookIdTestTriggerWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String trigger, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdHooksHookIdTestTriggerRequestCreation(hookId, trigger, id).toEntity(localVarReturnType);
    }

    /**
     * Trigger a test webhook
     * Triggers a test webhook. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 16.11 for project hooks and GitLab 17.1 for group hooks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param hookId ID of the hook.
     * @param trigger Type of webhook event to test.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdHooksHookIdTestTriggerWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String trigger, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4GroupsIdHooksHookIdTestTriggerRequestCreation(hookId, trigger, id);
    }

    /**
     * Create a system hook
     * Creates a system hook. This endpoint has a rate limit of five requests per minute for each authenticated user. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4HooksRequest The postApiV4HooksRequest parameter
     * @return APIEntitiesHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4HooksRequestCreation(@jakarta.annotation.Nonnull PostApiV4HooksRequest postApiV4HooksRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4HooksRequest' is set
        if (postApiV4HooksRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4HooksRequest' when calling postApiV4Hooks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/hooks", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4HooksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a system hook
     * Creates a system hook. This endpoint has a rate limit of five requests per minute for each authenticated user. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4HooksRequest The postApiV4HooksRequest parameter
     * @return APIEntitiesHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesHook postApiV4Hooks(@jakarta.annotation.Nonnull PostApiV4HooksRequest postApiV4HooksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4HooksRequestCreation(postApiV4HooksRequest).body(localVarReturnType);
    }

    /**
     * Create a system hook
     * Creates a system hook. This endpoint has a rate limit of five requests per minute for each authenticated user. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4HooksRequest The postApiV4HooksRequest parameter
     * @return ResponseEntity&lt;APIEntitiesHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesHook> postApiV4HooksWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4HooksRequest postApiV4HooksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4HooksRequestCreation(postApiV4HooksRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a system hook
     * Creates a system hook. This endpoint has a rate limit of five requests per minute for each authenticated user. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4HooksRequest The postApiV4HooksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4HooksWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4HooksRequest postApiV4HooksRequest) throws RestClientResponseException {
        return postApiV4HooksRequestCreation(postApiV4HooksRequest);
    }

    /**
     * Create a test run
     * Creates a test run for a webhook. Executes the webhook with mock data.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4HooksHookIdRequestCreation(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling postApiV4HooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);

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
        return apiClient.invokeAPI("/api/v4/hooks/{hook_id}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a test run
     * Creates a test run for a webhook. Executes the webhook with mock data.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4HooksHookId(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4HooksHookIdRequestCreation(hookId).body(localVarReturnType);
    }

    /**
     * Create a test run
     * Creates a test run for a webhook. Executes the webhook with mock data.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4HooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4HooksHookIdRequestCreation(hookId).toEntity(localVarReturnType);
    }

    /**
     * Create a test run
     * Creates a test run for a webhook. Executes the webhook with mock data.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the system hook.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4HooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId) throws RestClientResponseException {
        return postApiV4HooksHookIdRequestCreation(hookId);
    }

    /**
     * Add a webhook to a project
     * Adds a webhook to a specified project. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdHooksRequest The postApiV4ProjectsIdHooksRequest parameter
     * @return APIEntitiesProjectHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdHooksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdHooksRequest postApiV4ProjectsIdHooksRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdHooks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdHooksRequest' is set
        if (postApiV4ProjectsIdHooksRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdHooksRequest' when calling postApiV4ProjectsIdHooks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesProjectHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdHooksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a webhook to a project
     * Adds a webhook to a specified project. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdHooksRequest The postApiV4ProjectsIdHooksRequest parameter
     * @return APIEntitiesProjectHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectHook postApiV4ProjectsIdHooks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdHooksRequest postApiV4ProjectsIdHooksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdHooksRequestCreation(id, postApiV4ProjectsIdHooksRequest).body(localVarReturnType);
    }

    /**
     * Add a webhook to a project
     * Adds a webhook to a specified project. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdHooksRequest The postApiV4ProjectsIdHooksRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectHook> postApiV4ProjectsIdHooksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdHooksRequest postApiV4ProjectsIdHooksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdHooksRequestCreation(id, postApiV4ProjectsIdHooksRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a webhook to a project
     * Adds a webhook to a specified project. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdHooksRequest The postApiV4ProjectsIdHooksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdHooksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdHooksRequest postApiV4ProjectsIdHooksRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdHooksRequestCreation(id, postApiV4ProjectsIdHooksRequest);
    }

    /**
     * Resend a webhook event
     * Resends a webhook event. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. All webhooks in the same project or group share the limit. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param hookLogId ID of the hook log entry.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdHooksHookIdEventsHookLogIdResendRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull Integer hookLogId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling postApiV4ProjectsIdHooksHookIdEventsHookLogIdResend", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookLogId' is set
        if (hookLogId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookLogId' when calling postApiV4ProjectsIdHooksHookIdEventsHookLogIdResend", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdHooksHookIdEventsHookLogIdResend", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("hook_log_id", hookLogId);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}/events/{hook_log_id}/resend", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Resend a webhook event
     * Resends a webhook event. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. All webhooks in the same project or group share the limit. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param hookLogId ID of the hook log entry.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdHooksHookIdEventsHookLogIdResend(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull Integer hookLogId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdHooksHookIdEventsHookLogIdResendRequestCreation(hookId, hookLogId, id).body(localVarReturnType);
    }

    /**
     * Resend a webhook event
     * Resends a webhook event. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. All webhooks in the same project or group share the limit. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param hookLogId ID of the hook log entry.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdHooksHookIdEventsHookLogIdResendWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull Integer hookLogId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdHooksHookIdEventsHookLogIdResendRequestCreation(hookId, hookLogId, id).toEntity(localVarReturnType);
    }

    /**
     * Resend a webhook event
     * Resends a webhook event. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. All webhooks in the same project or group share the limit. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param hookLogId ID of the hook log entry.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdHooksHookIdEventsHookLogIdResendWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull Integer hookLogId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4ProjectsIdHooksHookIdEventsHookLogIdResendRequestCreation(hookId, hookLogId, id);
    }

    /**
     * Trigger a test webhook
     * Triggers a test webhook. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 16.11 for project hooks and GitLab 17.1 for group hooks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param hookId ID of the hook.
     * @param trigger Type of webhook event to test.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdHooksHookIdTestTriggerRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String trigger, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling postApiV4ProjectsIdHooksHookIdTestTrigger", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'trigger' is set
        if (trigger == null) {
            throw new RestClientResponseException("Missing the required parameter 'trigger' when calling postApiV4ProjectsIdHooksHookIdTestTrigger", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdHooksHookIdTestTrigger", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("trigger", trigger);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}/test/{trigger}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Trigger a test webhook
     * Triggers a test webhook. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 16.11 for project hooks and GitLab 17.1 for group hooks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param hookId ID of the hook.
     * @param trigger Type of webhook event to test.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdHooksHookIdTestTrigger(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String trigger, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdHooksHookIdTestTriggerRequestCreation(hookId, trigger, id).body(localVarReturnType);
    }

    /**
     * Trigger a test webhook
     * Triggers a test webhook. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 16.11 for project hooks and GitLab 17.1 for group hooks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param hookId ID of the hook.
     * @param trigger Type of webhook event to test.
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdHooksHookIdTestTriggerWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String trigger, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdHooksHookIdTestTriggerRequestCreation(hookId, trigger, id).toEntity(localVarReturnType);
    }

    /**
     * Trigger a test webhook
     * Triggers a test webhook. This endpoint has a rate limit of five requests per minute for each authenticated user for a given project or group. On GitLab Self-Managed and GitLab Dedicated, an administrator can change this limit in the application settings. This feature was introduced in GitLab 16.11 for project hooks and GitLab 17.1 for group hooks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param hookId ID of the hook.
     * @param trigger Type of webhook event to test.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdHooksHookIdTestTriggerWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String trigger, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4ProjectsIdHooksHookIdTestTriggerRequestCreation(hookId, trigger, id);
    }

    /**
     * Update a group hook
     * Updates a hook for a specified group. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @param putApiV4GroupsIdHooksHookIdRequest The putApiV4GroupsIdHooksHookIdRequest parameter
     * @return APIEntitiesGroupHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdHooksHookIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4GroupsIdHooksHookIdRequest putApiV4GroupsIdHooksHookIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling putApiV4GroupsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("hook_id", hookId);

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

        ParameterizedTypeReference<APIEntitiesGroupHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdHooksHookIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group hook
     * Updates a hook for a specified group. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @param putApiV4GroupsIdHooksHookIdRequest The putApiV4GroupsIdHooksHookIdRequest parameter
     * @return APIEntitiesGroupHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGroupHook putApiV4GroupsIdHooksHookId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4GroupsIdHooksHookIdRequest putApiV4GroupsIdHooksHookIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdHooksHookIdRequestCreation(id, hookId, putApiV4GroupsIdHooksHookIdRequest).body(localVarReturnType);
    }

    /**
     * Update a group hook
     * Updates a hook for a specified group. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @param putApiV4GroupsIdHooksHookIdRequest The putApiV4GroupsIdHooksHookIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGroupHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGroupHook> putApiV4GroupsIdHooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4GroupsIdHooksHookIdRequest putApiV4GroupsIdHooksHookIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGroupHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdHooksHookIdRequestCreation(id, hookId, putApiV4GroupsIdHooksHookIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group hook
     * Updates a hook for a specified group. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param hookId ID of the group webhook.
     * @param putApiV4GroupsIdHooksHookIdRequest The putApiV4GroupsIdHooksHookIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdHooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4GroupsIdHooksHookIdRequest putApiV4GroupsIdHooksHookIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdHooksHookIdRequestCreation(id, hookId, putApiV4GroupsIdHooksHookIdRequest);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest The putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling putApiV4GroupsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4GroupsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest' is set
        if (putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest' when calling putApiV4GroupsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("key", key);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}/custom_headers/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest The putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4GroupsIdHooksHookIdCustomHeadersKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id, putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest The putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4GroupsIdHooksHookIdCustomHeadersKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id, putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest The putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdHooksHookIdCustomHeadersKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        return putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id, putApiV4GroupsIdHooksHookIdCustomHeadersKeyRequest);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest The putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling putApiV4GroupsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4GroupsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest' is set
        if (putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest' when calling putApiV4GroupsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("key", key);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/hooks/{hook_id}/url_variables/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest The putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4GroupsIdHooksHookIdUrlVariablesKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id, putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest The putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4GroupsIdHooksHookIdUrlVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id, putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest The putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdHooksHookIdUrlVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        return putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id, putApiV4GroupsIdHooksHookIdUrlVariablesKeyRequest);
    }

    /**
     * Update a system hook
     * Updates a specified system hook.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param hookId ID of the system hook.
     * @param putApiV4HooksHookIdRequest The putApiV4HooksHookIdRequest parameter
     * @return APIEntitiesHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4HooksHookIdRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4HooksHookIdRequest putApiV4HooksHookIdRequest) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling putApiV4HooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);

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

        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/hooks/{hook_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4HooksHookIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a system hook
     * Updates a specified system hook.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param hookId ID of the system hook.
     * @param putApiV4HooksHookIdRequest The putApiV4HooksHookIdRequest parameter
     * @return APIEntitiesHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesHook putApiV4HooksHookId(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4HooksHookIdRequest putApiV4HooksHookIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4HooksHookIdRequestCreation(hookId, putApiV4HooksHookIdRequest).body(localVarReturnType);
    }

    /**
     * Update a system hook
     * Updates a specified system hook.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param hookId ID of the system hook.
     * @param putApiV4HooksHookIdRequest The putApiV4HooksHookIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesHook> putApiV4HooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4HooksHookIdRequest putApiV4HooksHookIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4HooksHookIdRequestCreation(hookId, putApiV4HooksHookIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a system hook
     * Updates a specified system hook.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param hookId ID of the system hook.
     * @param putApiV4HooksHookIdRequest The putApiV4HooksHookIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4HooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4HooksHookIdRequest putApiV4HooksHookIdRequest) throws RestClientResponseException {
        return putApiV4HooksHookIdRequestCreation(hookId, putApiV4HooksHookIdRequest);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param putApiV4HooksHookIdCustomHeadersKeyRequest The putApiV4HooksHookIdCustomHeadersKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4HooksHookIdCustomHeadersKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PutApiV4HooksHookIdCustomHeadersKeyRequest putApiV4HooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling putApiV4HooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4HooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4HooksHookIdCustomHeadersKeyRequest' is set
        if (putApiV4HooksHookIdCustomHeadersKeyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4HooksHookIdCustomHeadersKeyRequest' when calling putApiV4HooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("key", key);

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
        return apiClient.invokeAPI("/api/v4/hooks/{hook_id}/custom_headers/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4HooksHookIdCustomHeadersKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param putApiV4HooksHookIdCustomHeadersKeyRequest The putApiV4HooksHookIdCustomHeadersKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4HooksHookIdCustomHeadersKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PutApiV4HooksHookIdCustomHeadersKeyRequest putApiV4HooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4HooksHookIdCustomHeadersKeyRequestCreation(hookId, key, putApiV4HooksHookIdCustomHeadersKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param putApiV4HooksHookIdCustomHeadersKeyRequest The putApiV4HooksHookIdCustomHeadersKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4HooksHookIdCustomHeadersKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PutApiV4HooksHookIdCustomHeadersKeyRequest putApiV4HooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4HooksHookIdCustomHeadersKeyRequestCreation(hookId, key, putApiV4HooksHookIdCustomHeadersKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param putApiV4HooksHookIdCustomHeadersKeyRequest The putApiV4HooksHookIdCustomHeadersKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4HooksHookIdCustomHeadersKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PutApiV4HooksHookIdCustomHeadersKeyRequest putApiV4HooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        return putApiV4HooksHookIdCustomHeadersKeyRequestCreation(hookId, key, putApiV4HooksHookIdCustomHeadersKeyRequest);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param putApiV4HooksHookIdUrlVariablesKeyRequest The putApiV4HooksHookIdUrlVariablesKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4HooksHookIdUrlVariablesKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PutApiV4HooksHookIdUrlVariablesKeyRequest putApiV4HooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling putApiV4HooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4HooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4HooksHookIdUrlVariablesKeyRequest' is set
        if (putApiV4HooksHookIdUrlVariablesKeyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4HooksHookIdUrlVariablesKeyRequest' when calling putApiV4HooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("key", key);

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
        return apiClient.invokeAPI("/api/v4/hooks/{hook_id}/url_variables/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4HooksHookIdUrlVariablesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param putApiV4HooksHookIdUrlVariablesKeyRequest The putApiV4HooksHookIdUrlVariablesKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4HooksHookIdUrlVariablesKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PutApiV4HooksHookIdUrlVariablesKeyRequest putApiV4HooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4HooksHookIdUrlVariablesKeyRequestCreation(hookId, key, putApiV4HooksHookIdUrlVariablesKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param putApiV4HooksHookIdUrlVariablesKeyRequest The putApiV4HooksHookIdUrlVariablesKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4HooksHookIdUrlVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PutApiV4HooksHookIdUrlVariablesKeyRequest putApiV4HooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4HooksHookIdUrlVariablesKeyRequestCreation(hookId, key, putApiV4HooksHookIdUrlVariablesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param putApiV4HooksHookIdUrlVariablesKeyRequest The putApiV4HooksHookIdUrlVariablesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4HooksHookIdUrlVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PutApiV4HooksHookIdUrlVariablesKeyRequest putApiV4HooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        return putApiV4HooksHookIdUrlVariablesKeyRequestCreation(hookId, key, putApiV4HooksHookIdUrlVariablesKeyRequest);
    }

    /**
     * Update a project webhook
     * Updates a specified webhook for a project. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @param putApiV4ProjectsIdHooksHookIdRequest The putApiV4ProjectsIdHooksHookIdRequest parameter
     * @return APIEntitiesProjectHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdHooksHookIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4ProjectsIdHooksHookIdRequest putApiV4ProjectsIdHooksHookIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling putApiV4ProjectsIdHooksHookId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("hook_id", hookId);

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

        ParameterizedTypeReference<APIEntitiesProjectHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdHooksHookIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a project webhook
     * Updates a specified webhook for a project. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @param putApiV4ProjectsIdHooksHookIdRequest The putApiV4ProjectsIdHooksHookIdRequest parameter
     * @return APIEntitiesProjectHook
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesProjectHook putApiV4ProjectsIdHooksHookId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4ProjectsIdHooksHookIdRequest putApiV4ProjectsIdHooksHookIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdHooksHookIdRequestCreation(id, hookId, putApiV4ProjectsIdHooksHookIdRequest).body(localVarReturnType);
    }

    /**
     * Update a project webhook
     * Updates a specified webhook for a project. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @param putApiV4ProjectsIdHooksHookIdRequest The putApiV4ProjectsIdHooksHookIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesProjectHook&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesProjectHook> putApiV4ProjectsIdHooksHookIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4ProjectsIdHooksHookIdRequest putApiV4ProjectsIdHooksHookIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesProjectHook> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdHooksHookIdRequestCreation(id, hookId, putApiV4ProjectsIdHooksHookIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a project webhook
     * Updates a specified webhook for a project. Setting &#x60;duo_flow_callback_enabled&#x60; to &#x60;false&#x60; is always accepted.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the project.
     * @param hookId ID of the project webhook.
     * @param putApiV4ProjectsIdHooksHookIdRequest The putApiV4ProjectsIdHooksHookIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdHooksHookIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nullable PutApiV4ProjectsIdHooksHookIdRequest putApiV4ProjectsIdHooksHookIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdHooksHookIdRequestCreation(id, hookId, putApiV4ProjectsIdHooksHookIdRequest);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest The putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling putApiV4ProjectsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4ProjectsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest' is set
        if (putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest' when calling putApiV4ProjectsIdHooksHookIdCustomHeadersKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("key", key);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}/custom_headers/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest The putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdHooksHookIdCustomHeadersKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id, putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest The putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdHooksHookIdCustomHeadersKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id, putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a custom header
     * Updates a custom header for a specified webhook. This feature was introduced in GitLab 17.1.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Name of the custom header.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest The putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdHooksHookIdCustomHeadersKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequestCreation(hookId, key, id, putApiV4ProjectsIdHooksHookIdCustomHeadersKeyRequest);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest The putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequestCreation(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        // verify the required parameter 'hookId' is set
        if (hookId == null) {
            throw new RestClientResponseException("Missing the required parameter 'hookId' when calling putApiV4ProjectsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new RestClientResponseException("Missing the required parameter 'key' when calling putApiV4ProjectsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest' is set
        if (putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest' when calling putApiV4ProjectsIdHooksHookIdUrlVariablesKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("hook_id", hookId);
        pathParams.put("key", key);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/hooks/{hook_id}/url_variables/{key}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest The putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdHooksHookIdUrlVariablesKey(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id, putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest).body(localVarReturnType);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest The putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdHooksHookIdUrlVariablesKeyWithHttpInfo(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id, putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a URL variable
     * Updates a URL variable for a specified webhook. Setting the value does not replace a literal value in the existing webhook URL with the placeholder. To use the variable, also update the webhook URL to include the &#x60;{key}&#x60; placeholder.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param hookId ID of the hook.
     * @param key Key of the URL variable.
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest The putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdHooksHookIdUrlVariablesKeyWithResponseSpec(@jakarta.annotation.Nonnull Integer hookId, @jakarta.annotation.Nonnull String key, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PutApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequestCreation(hookId, key, id, putApiV4ProjectsIdHooksHookIdUrlVariablesKeyRequest);
    }
}
