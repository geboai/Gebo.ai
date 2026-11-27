package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.OnlineNotification;
import ai.gebo.zammad.client.model.OnlineNotificationUpdate;

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
public class OnlineNotificationsApi {
    private ApiClient apiClient;

    public OnlineNotificationsApi() {
        this(new ApiClient());
    }

    public OnlineNotificationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteOnlineNotificationRequestCreation(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'onlineNotificationId' is set
        if (onlineNotificationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'onlineNotificationId' when calling deleteOnlineNotification", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("onlineNotificationId", onlineNotificationId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/online_notifications/{onlineNotificationId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteOnlineNotification(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteOnlineNotificationRequestCreation(onlineNotificationId, from).body(localVarReturnType);
    }

    /**
     * Delete online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteOnlineNotificationWithHttpInfo(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteOnlineNotificationRequestCreation(onlineNotificationId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteOnlineNotificationWithResponseSpec(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteOnlineNotificationRequestCreation(onlineNotificationId, from);
    }

    /**
     * Show online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return OnlineNotification
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getOnlineNotificationRequestCreation(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'onlineNotificationId' is set
        if (onlineNotificationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'onlineNotificationId' when calling getOnlineNotification", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("onlineNotificationId", onlineNotificationId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<OnlineNotification> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/online_notifications/{onlineNotificationId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return OnlineNotification
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public OnlineNotification getOnlineNotification(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<OnlineNotification> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getOnlineNotificationRequestCreation(onlineNotificationId, from).body(localVarReturnType);
    }

    /**
     * Show online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;OnlineNotification&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OnlineNotification> getOnlineNotificationWithHttpInfo(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<OnlineNotification> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getOnlineNotificationRequestCreation(onlineNotificationId, from).toEntity(localVarReturnType);
    }

    /**
     * Show online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getOnlineNotificationWithResponseSpec(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getOnlineNotificationRequestCreation(onlineNotificationId, from);
    }

    /**
     * List online notifications
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;OnlineNotification&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listOnlineNotificationsRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<List<OnlineNotification>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/online_notifications", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List online notifications
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;OnlineNotification&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<OnlineNotification> listOnlineNotifications(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<OnlineNotification>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listOnlineNotificationsRequestCreation(from, expand, page, perPage).body(localVarReturnType);
    }

    /**
     * List online notifications
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;List&lt;OnlineNotification&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<OnlineNotification>> listOnlineNotificationsWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<OnlineNotification>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listOnlineNotificationsRequestCreation(from, expand, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List online notifications
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listOnlineNotificationsWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return listOnlineNotificationsRequestCreation(from, expand, page, perPage);
    }

    /**
     * Mark all online notifications as read
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec markAllOnlineNotificationsAsReadRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/online_notifications/mark_all_as_read", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Mark all online notifications as read
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object markAllOnlineNotificationsAsRead(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return markAllOnlineNotificationsAsReadRequestCreation(from, body).body(localVarReturnType);
    }

    /**
     * Mark all online notifications as read
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> markAllOnlineNotificationsAsReadWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return markAllOnlineNotificationsAsReadRequestCreation(from, body).toEntity(localVarReturnType);
    }

    /**
     * Mark all online notifications as read
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec markAllOnlineNotificationsAsReadWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return markAllOnlineNotificationsAsReadRequestCreation(from, body);
    }

    /**
     * Update online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param onlineNotificationUpdate The onlineNotificationUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return OnlineNotification
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateOnlineNotificationRequestCreation(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nonnull OnlineNotificationUpdate onlineNotificationUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'onlineNotificationId' is set
        if (onlineNotificationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'onlineNotificationId' when calling updateOnlineNotification", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'onlineNotificationUpdate' is set
        if (onlineNotificationUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'onlineNotificationUpdate' when calling updateOnlineNotification", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("onlineNotificationId", onlineNotificationId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<OnlineNotification> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/online_notifications/{onlineNotificationId}", HttpMethod.PUT, pathParams, localVarQueryParams, onlineNotificationUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param onlineNotificationUpdate The onlineNotificationUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return OnlineNotification
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public OnlineNotification updateOnlineNotification(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nonnull OnlineNotificationUpdate onlineNotificationUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<OnlineNotification> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateOnlineNotificationRequestCreation(onlineNotificationId, onlineNotificationUpdate, from).body(localVarReturnType);
    }

    /**
     * Update online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param onlineNotificationUpdate The onlineNotificationUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;OnlineNotification&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OnlineNotification> updateOnlineNotificationWithHttpInfo(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nonnull OnlineNotificationUpdate onlineNotificationUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<OnlineNotification> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateOnlineNotificationRequestCreation(onlineNotificationId, onlineNotificationUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update online notification
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param onlineNotificationId Online notification ID.
     * @param onlineNotificationUpdate The onlineNotificationUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateOnlineNotificationWithResponseSpec(@jakarta.annotation.Nonnull Long onlineNotificationId, @jakarta.annotation.Nonnull OnlineNotificationUpdate onlineNotificationUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateOnlineNotificationRequestCreation(onlineNotificationId, onlineNotificationUpdate, from);
    }
}
