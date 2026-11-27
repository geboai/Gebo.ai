package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.NotificationCollectionModel;
import ai.gebo.openproject.client.model.NotificationModel;
import ai.gebo.openproject.client.model.ValuesPropertyModel;

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
public class NotificationsApi {
    private ApiClient apiClient;

    public NotificationsApi() {
        this(new ApiClient());
    }

    public NotificationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get the notification
     * Returns the notification identified by the notification id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @return NotificationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getNotificationRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getNotification", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<NotificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/notifications/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the notification
     * Returns the notification identified by the notification id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @return NotificationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public NotificationModel getNotification(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<NotificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNotificationRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get the notification
     * Returns the notification identified by the notification id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @return ResponseEntity&lt;NotificationModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<NotificationModel> getNotificationWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<NotificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNotificationRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get the notification
     * Returns the notification identified by the notification id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getNotificationWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getNotificationRequestCreation(id);
    }

    /**
     * Get a notification detail
     * Returns an individual detail of a notification identified by the notification id and the id of the detail.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification or the detail of it does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param notificationId notification id
     * @param id detail id
     * @return ValuesPropertyModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getNotificationDetailRequestCreation(@jakarta.annotation.Nonnull Integer notificationId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'notificationId' is set
        if (notificationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'notificationId' when calling getNotificationDetail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getNotificationDetail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("notification_id", notificationId);
        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<ValuesPropertyModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/notifications/{notification_id}/details/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a notification detail
     * Returns an individual detail of a notification identified by the notification id and the id of the detail.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification or the detail of it does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param notificationId notification id
     * @param id detail id
     * @return ValuesPropertyModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ValuesPropertyModel getNotificationDetail(@jakarta.annotation.Nonnull Integer notificationId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ValuesPropertyModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNotificationDetailRequestCreation(notificationId, id).body(localVarReturnType);
    }

    /**
     * Get a notification detail
     * Returns an individual detail of a notification identified by the notification id and the id of the detail.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification or the detail of it does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param notificationId notification id
     * @param id detail id
     * @return ResponseEntity&lt;ValuesPropertyModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ValuesPropertyModel> getNotificationDetailWithHttpInfo(@jakarta.annotation.Nonnull Integer notificationId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ValuesPropertyModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNotificationDetailRequestCreation(notificationId, id).toEntity(localVarReturnType);
    }

    /**
     * Get a notification detail
     * Returns an individual detail of a notification identified by the notification id and the id of the detail.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification or the detail of it does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param notificationId notification id
     * @param id detail id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getNotificationDetailWithResponseSpec(@jakarta.annotation.Nonnull Integer notificationId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getNotificationDetailRequestCreation(notificationId, id);
    }

    /**
     * Get notification collection
     * Returns the collection of available in-app notifications. The notifications returned depend on the provided parameters and also on the requesting user&#39;s permissions.  Contrary to most collections, this one also links to and embeds schemas for the &#x60;details&#x60; properties of the notifications returned. This is an optimization. Clients will receive the information necessary to display the various types of details that a notification can carry.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * <p><b>422</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + reason: Sort by notification reason  + readIAN: Sort by read status
     * @param groupBy string specifying group_by criteria.  + reason: Group by notification reason  + project: Sort by associated project
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + readIAN: Filter by read status  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with the &#x60;resourceId&#x60; filter.
     * @return NotificationCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listNotificationsRequestCreation(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupBy", groupBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<NotificationCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/notifications", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get notification collection
     * Returns the collection of available in-app notifications. The notifications returned depend on the provided parameters and also on the requesting user&#39;s permissions.  Contrary to most collections, this one also links to and embeds schemas for the &#x60;details&#x60; properties of the notifications returned. This is an optimization. Clients will receive the information necessary to display the various types of details that a notification can carry.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * <p><b>422</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + reason: Sort by notification reason  + readIAN: Sort by read status
     * @param groupBy string specifying group_by criteria.  + reason: Group by notification reason  + project: Sort by associated project
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + readIAN: Filter by read status  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with the &#x60;resourceId&#x60; filter.
     * @return NotificationCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public NotificationCollectionModel listNotifications(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<NotificationCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listNotificationsRequestCreation(offset, pageSize, sortBy, groupBy, filters).body(localVarReturnType);
    }

    /**
     * Get notification collection
     * Returns the collection of available in-app notifications. The notifications returned depend on the provided parameters and also on the requesting user&#39;s permissions.  Contrary to most collections, this one also links to and embeds schemas for the &#x60;details&#x60; properties of the notifications returned. This is an optimization. Clients will receive the information necessary to display the various types of details that a notification can carry.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * <p><b>422</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + reason: Sort by notification reason  + readIAN: Sort by read status
     * @param groupBy string specifying group_by criteria.  + reason: Group by notification reason  + project: Sort by associated project
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + readIAN: Filter by read status  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with the &#x60;resourceId&#x60; filter.
     * @return ResponseEntity&lt;NotificationCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<NotificationCollectionModel> listNotificationsWithHttpInfo(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<NotificationCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listNotificationsRequestCreation(offset, pageSize, sortBy, groupBy, filters).toEntity(localVarReturnType);
    }

    /**
     * Get notification collection
     * Returns the collection of available in-app notifications. The notifications returned depend on the provided parameters and also on the requesting user&#39;s permissions.  Contrary to most collections, this one also links to and embeds schemas for the &#x60;details&#x60; properties of the notifications returned. This is an optimization. Clients will receive the information necessary to display the various types of details that a notification can carry.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * <p><b>422</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + reason: Sort by notification reason  + readIAN: Sort by read status
     * @param groupBy string specifying group_by criteria.  + reason: Group by notification reason  + project: Sort by associated project
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + readIAN: Filter by read status  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with the &#x60;resourceId&#x60; filter.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listNotificationsWithResponseSpec(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listNotificationsRequestCreation(offset, pageSize, sortBy, groupBy, filters);
    }

    /**
     * Read notification
     * Marks the given notification as read.
     * <p><b>204</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec readNotificationRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling readNotification", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/notifications/{id}/read_ian", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Read notification
     * Marks the given notification as read.
     * <p><b>204</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void readNotification(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        readNotificationRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Read notification
     * Marks the given notification as read.
     * <p><b>204</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> readNotificationWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return readNotificationRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Read notification
     * Marks the given notification as read.
     * <p><b>204</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec readNotificationWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return readNotificationRequestCreation(id);
    }

    /**
     * Read all notifications
     * Marks the whole notification collection as read. The collection contains only elements the authenticated user can see, and can be further reduced with filters.
     * <p><b>204</b> - OK
     * <p><b>400</b> - Returned if the request is not properly formatted.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the   &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with   the &#x60;resourceId&#x60; filter.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec readNotificationsRequestCreation(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/notifications/read_ian", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Read all notifications
     * Marks the whole notification collection as read. The collection contains only elements the authenticated user can see, and can be further reduced with filters.
     * <p><b>204</b> - OK
     * <p><b>400</b> - Returned if the request is not properly formatted.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the   &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with   the &#x60;resourceId&#x60; filter.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void readNotifications(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        readNotificationsRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * Read all notifications
     * Marks the whole notification collection as read. The collection contains only elements the authenticated user can see, and can be further reduced with filters.
     * <p><b>204</b> - OK
     * <p><b>400</b> - Returned if the request is not properly formatted.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the   &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with   the &#x60;resourceId&#x60; filter.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> readNotificationsWithHttpInfo(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return readNotificationsRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * Read all notifications
     * Marks the whole notification collection as read. The collection contains only elements the authenticated user can see, and can be further reduced with filters.
     * <p><b>204</b> - OK
     * <p><b>400</b> - Returned if the request is not properly formatted.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the   &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with   the &#x60;resourceId&#x60; filter.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec readNotificationsWithResponseSpec(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return readNotificationsRequestCreation(filters);
    }

    /**
     * Unread notification
     * Marks the given notification as unread.
     * <p><b>204</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec unreadNotificationRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling unreadNotification", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/notifications/{id}/unread_ian", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unread notification
     * Marks the given notification as unread.
     * <p><b>204</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void unreadNotification(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        unreadNotificationRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unread notification
     * Marks the given notification as unread.
     * <p><b>204</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> unreadNotificationWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unreadNotificationRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unread notification
     * Marks the given notification as unread.
     * <p><b>204</b> - OK
     * <p><b>404</b> - Returned if the notification does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param id notification id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec unreadNotificationWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return unreadNotificationRequestCreation(id);
    }

    /**
     * Unread all notifications
     * Marks the whole notification collection as unread. The collection contains only elements the authenticated user can see, and can be further reduced with filters.
     * <p><b>204</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the   &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with   the &#x60;resourceId&#x60; filter.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec unreadNotificationsRequestCreation(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/notifications/unread_ian", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unread all notifications
     * Marks the whole notification collection as unread. The collection contains only elements the authenticated user can see, and can be further reduced with filters.
     * <p><b>204</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the   &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with   the &#x60;resourceId&#x60; filter.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void unreadNotifications(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        unreadNotificationsRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * Unread all notifications
     * Marks the whole notification collection as unread. The collection contains only elements the authenticated user can see, and can be further reduced with filters.
     * <p><b>204</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the   &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with   the &#x60;resourceId&#x60; filter.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> unreadNotificationsWithHttpInfo(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unreadNotificationsRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * Unread all notifications
     * Marks the whole notification collection as unread. The collection contains only elements the authenticated user can see, and can be further reduced with filters.
     * <p><b>204</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Filter by primary key  + project: Filter by the project the notification was created in  + reason: Filter by the reason, e.g. &#39;mentioned&#39; or &#39;assigned&#39; the notification was created because of  + resourceId: Filter by the id of the resource the notification was created for. Ideally used together with the   &#x60;resourceType&#x60; filter.  + resourceType: Filter by the type of the resource the notification was created for. Ideally used together with   the &#x60;resourceId&#x60; filter.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec unreadNotificationsWithResponseSpec(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return unreadNotificationsRequestCreation(filters);
    }
}
