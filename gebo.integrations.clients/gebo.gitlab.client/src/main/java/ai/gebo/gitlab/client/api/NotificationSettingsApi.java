package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGlobalNotificationSetting;
import ai.gebo.gitlab.client.model.APIEntitiesNotificationSetting;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdNotificationSettingsRequest;
import ai.gebo.gitlab.client.model.PutApiV4NotificationSettingsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdNotificationSettingsRequest;

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
public class NotificationSettingsApi {
    private ApiClient apiClient;

    public NotificationSettingsApi() {
        this(new ApiClient());
    }

    public NotificationSettingsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve notification settings for a group
     * Retrieves the notification level for a specified group. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdNotificationSettingsRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdNotificationSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/notification_settings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve notification settings for a group
     * Retrieves the notification level for a specified group. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return APIEntitiesNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNotificationSetting getApiV4GroupsIdNotificationSettings(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdNotificationSettingsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve notification settings for a group
     * Retrieves the notification level for a specified group. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;APIEntitiesNotificationSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNotificationSetting> getApiV4GroupsIdNotificationSettingsWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdNotificationSettingsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve notification settings for a group
     * Retrieves the notification level for a specified group. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdNotificationSettingsWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4GroupsIdNotificationSettingsRequestCreation(id);
    }

    /**
     * Retrieve global notification settings
     * Retrieves the global notification level and email address.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesGlobalNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4NotificationSettingsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesGlobalNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/notification_settings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve global notification settings
     * Retrieves the global notification level and email address.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesGlobalNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGlobalNotificationSetting getApiV4NotificationSettings() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGlobalNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NotificationSettingsRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve global notification settings
     * Retrieves the global notification level and email address.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseEntity&lt;APIEntitiesGlobalNotificationSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGlobalNotificationSetting> getApiV4NotificationSettingsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGlobalNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4NotificationSettingsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve global notification settings
     * Retrieves the global notification level and email address.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4NotificationSettingsWithResponseSpec() throws RestClientResponseException {
        return getApiV4NotificationSettingsRequestCreation();
    }

    /**
     * Retrieve notification settings for a project
     * Retrieves the notification level for a specified project. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdNotificationSettingsRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdNotificationSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/notification_settings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve notification settings for a project
     * Retrieves the notification level for a specified project. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNotificationSetting getApiV4ProjectsIdNotificationSettings(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdNotificationSettingsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve notification settings for a project
     * Retrieves the notification level for a specified project. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesNotificationSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNotificationSetting> getApiV4ProjectsIdNotificationSettingsWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdNotificationSettingsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve notification settings for a project
     * Retrieves the notification level for a specified project. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdNotificationSettingsWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4ProjectsIdNotificationSettingsRequestCreation(id);
    }

    /**
     * Update notification settings for a group
     * Updates the notification settings for a specified group. The response includes the notification &#x60;level&#x60;. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdNotificationSettingsRequest The putApiV4GroupsIdNotificationSettingsRequest parameter
     * @return APIEntitiesNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdNotificationSettingsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdNotificationSettingsRequest putApiV4GroupsIdNotificationSettingsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdNotificationSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/notification_settings", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdNotificationSettingsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update notification settings for a group
     * Updates the notification settings for a specified group. The response includes the notification &#x60;level&#x60;. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdNotificationSettingsRequest The putApiV4GroupsIdNotificationSettingsRequest parameter
     * @return APIEntitiesNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNotificationSetting putApiV4GroupsIdNotificationSettings(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdNotificationSettingsRequest putApiV4GroupsIdNotificationSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdNotificationSettingsRequestCreation(id, putApiV4GroupsIdNotificationSettingsRequest).body(localVarReturnType);
    }

    /**
     * Update notification settings for a group
     * Updates the notification settings for a specified group. The response includes the notification &#x60;level&#x60;. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdNotificationSettingsRequest The putApiV4GroupsIdNotificationSettingsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNotificationSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNotificationSetting> putApiV4GroupsIdNotificationSettingsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdNotificationSettingsRequest putApiV4GroupsIdNotificationSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdNotificationSettingsRequestCreation(id, putApiV4GroupsIdNotificationSettingsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update notification settings for a group
     * Updates the notification settings for a specified group. The response includes the notification &#x60;level&#x60;. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param putApiV4GroupsIdNotificationSettingsRequest The putApiV4GroupsIdNotificationSettingsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdNotificationSettingsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4GroupsIdNotificationSettingsRequest putApiV4GroupsIdNotificationSettingsRequest) throws RestClientResponseException {
        return putApiV4GroupsIdNotificationSettingsRequestCreation(id, putApiV4GroupsIdNotificationSettingsRequest);
    }

    /**
     * Update global notification settings
     * Updates notification settings and email address.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * @param putApiV4NotificationSettingsRequest The putApiV4NotificationSettingsRequest parameter
     * @return APIEntitiesGlobalNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4NotificationSettingsRequestCreation(@jakarta.annotation.Nullable PutApiV4NotificationSettingsRequest putApiV4NotificationSettingsRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesGlobalNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/notification_settings", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4NotificationSettingsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update global notification settings
     * Updates notification settings and email address.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * @param putApiV4NotificationSettingsRequest The putApiV4NotificationSettingsRequest parameter
     * @return APIEntitiesGlobalNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGlobalNotificationSetting putApiV4NotificationSettings(@jakarta.annotation.Nullable PutApiV4NotificationSettingsRequest putApiV4NotificationSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGlobalNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4NotificationSettingsRequestCreation(putApiV4NotificationSettingsRequest).body(localVarReturnType);
    }

    /**
     * Update global notification settings
     * Updates notification settings and email address.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * @param putApiV4NotificationSettingsRequest The putApiV4NotificationSettingsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGlobalNotificationSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGlobalNotificationSetting> putApiV4NotificationSettingsWithHttpInfo(@jakarta.annotation.Nullable PutApiV4NotificationSettingsRequest putApiV4NotificationSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGlobalNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4NotificationSettingsRequestCreation(putApiV4NotificationSettingsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update global notification settings
     * Updates notification settings and email address.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * @param putApiV4NotificationSettingsRequest The putApiV4NotificationSettingsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4NotificationSettingsWithResponseSpec(@jakarta.annotation.Nullable PutApiV4NotificationSettingsRequest putApiV4NotificationSettingsRequest) throws RestClientResponseException {
        return putApiV4NotificationSettingsRequestCreation(putApiV4NotificationSettingsRequest);
    }

    /**
     * Update notification settings for a project
     * Updates the notification settings for a specified project. The response includes the notification &#x60;level&#x60;. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdNotificationSettingsRequest The putApiV4ProjectsIdNotificationSettingsRequest parameter
     * @return APIEntitiesNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdNotificationSettingsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4ProjectsIdNotificationSettingsRequest putApiV4ProjectsIdNotificationSettingsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdNotificationSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/notification_settings", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdNotificationSettingsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update notification settings for a project
     * Updates the notification settings for a specified project. The response includes the notification &#x60;level&#x60;. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdNotificationSettingsRequest The putApiV4ProjectsIdNotificationSettingsRequest parameter
     * @return APIEntitiesNotificationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNotificationSetting putApiV4ProjectsIdNotificationSettings(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4ProjectsIdNotificationSettingsRequest putApiV4ProjectsIdNotificationSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdNotificationSettingsRequestCreation(id, putApiV4ProjectsIdNotificationSettingsRequest).body(localVarReturnType);
    }

    /**
     * Update notification settings for a project
     * Updates the notification settings for a specified project. The response includes the notification &#x60;level&#x60;. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdNotificationSettingsRequest The putApiV4ProjectsIdNotificationSettingsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesNotificationSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNotificationSetting> putApiV4ProjectsIdNotificationSettingsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4ProjectsIdNotificationSettingsRequest putApiV4ProjectsIdNotificationSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNotificationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdNotificationSettingsRequestCreation(id, putApiV4ProjectsIdNotificationSettingsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update notification settings for a project
     * Updates the notification settings for a specified project. The response includes the notification &#x60;level&#x60;. For the &#x60;custom&#x60; level, the response also includes an &#x60;events&#x60; object with the setting of each notification event: &#x60;true&#x60; if the notification is turned on, &#x60;false&#x60; if it is turned off, and &#x60;null&#x60; if it uses the default setting.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdNotificationSettingsRequest The putApiV4ProjectsIdNotificationSettingsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdNotificationSettingsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PutApiV4ProjectsIdNotificationSettingsRequest putApiV4ProjectsIdNotificationSettingsRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdNotificationSettingsRequestCreation(id, putApiV4ProjectsIdNotificationSettingsRequest);
    }
}
