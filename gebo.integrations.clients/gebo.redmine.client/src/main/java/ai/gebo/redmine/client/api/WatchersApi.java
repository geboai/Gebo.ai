package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.AddWatchersRequest;

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
public class WatchersApi {
    private ApiClient apiClient;

    public WatchersApi() {
        this(new ApiClient());
    }

    public WatchersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add watchers to resources
     * Adds users or regular groups as watchers of one or more resources. Requires permission to add watchers for each resource type and access to the selected projects. Unknown IDs are ignored.
     * <p><b>204</b> - The request was processed; only eligible users or regular groups were added.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param addWatchersRequest The addWatchersRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addWatchersRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull AddWatchersRequest addWatchersRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling addWatchers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'addWatchersRequest' is set
        if (addWatchersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'addWatchersRequest' when calling addWatchers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/watchers.{format}", HttpMethod.POST, pathParams, localVarQueryParams, addWatchersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add watchers to resources
     * Adds users or regular groups as watchers of one or more resources. Requires permission to add watchers for each resource type and access to the selected projects. Unknown IDs are ignored.
     * <p><b>204</b> - The request was processed; only eligible users or regular groups were added.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param addWatchersRequest The addWatchersRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void addWatchers(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull AddWatchersRequest addWatchersRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        addWatchersRequestCreation(format, addWatchersRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Add watchers to resources
     * Adds users or regular groups as watchers of one or more resources. Requires permission to add watchers for each resource type and access to the selected projects. Unknown IDs are ignored.
     * <p><b>204</b> - The request was processed; only eligible users or regular groups were added.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param addWatchersRequest The addWatchersRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> addWatchersWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull AddWatchersRequest addWatchersRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addWatchersRequestCreation(format, addWatchersRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Add watchers to resources
     * Adds users or regular groups as watchers of one or more resources. Requires permission to add watchers for each resource type and access to the selected projects. Unknown IDs are ignored.
     * <p><b>204</b> - The request was processed; only eligible users or regular groups were added.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param addWatchersRequest The addWatchersRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addWatchersWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull AddWatchersRequest addWatchersRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return addWatchersRequestCreation(format, addWatchersRequest, xRedmineSwitchUser);
    }

    /**
     * Remove a watcher from resources
     * Removes a user or regular group as a watcher of the selected resources. Requires permission to delete watchers for each resource type and access to the selected projects.
     * <p><b>204</b> - The watcher was removed.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The user or group was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param objectType Watchable resource type. For &#x60;wiki_page&#x60; and &#x60;message&#x60;, obtain resource IDs from search results.
     * @param objectId Resource IDs from which to remove the watcher.
     * @param userId ID of the user or regular group to remove as a watcher.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec removeWatcherFromResourcesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull String objectType, @jakarta.annotation.Nonnull List<Integer> objectId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling removeWatcherFromResources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'objectType' is set
        if (objectType == null) {
            throw new RestClientResponseException("Missing the required parameter 'objectType' when calling removeWatcherFromResources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'objectId' is set
        if (objectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'objectId' when calling removeWatcherFromResources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling removeWatcherFromResources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "object_type", objectType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "object_id[]", objectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/watchers.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove a watcher from resources
     * Removes a user or regular group as a watcher of the selected resources. Requires permission to delete watchers for each resource type and access to the selected projects.
     * <p><b>204</b> - The watcher was removed.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The user or group was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param objectType Watchable resource type. For &#x60;wiki_page&#x60; and &#x60;message&#x60;, obtain resource IDs from search results.
     * @param objectId Resource IDs from which to remove the watcher.
     * @param userId ID of the user or regular group to remove as a watcher.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void removeWatcherFromResources(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull String objectType, @jakarta.annotation.Nonnull List<Integer> objectId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        removeWatcherFromResourcesRequestCreation(format, objectType, objectId, userId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Remove a watcher from resources
     * Removes a user or regular group as a watcher of the selected resources. Requires permission to delete watchers for each resource type and access to the selected projects.
     * <p><b>204</b> - The watcher was removed.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The user or group was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param objectType Watchable resource type. For &#x60;wiki_page&#x60; and &#x60;message&#x60;, obtain resource IDs from search results.
     * @param objectId Resource IDs from which to remove the watcher.
     * @param userId ID of the user or regular group to remove as a watcher.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> removeWatcherFromResourcesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull String objectType, @jakarta.annotation.Nonnull List<Integer> objectId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return removeWatcherFromResourcesRequestCreation(format, objectType, objectId, userId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Remove a watcher from resources
     * Removes a user or regular group as a watcher of the selected resources. Requires permission to delete watchers for each resource type and access to the selected projects.
     * <p><b>204</b> - The watcher was removed.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The user or group was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param objectType Watchable resource type. For &#x60;wiki_page&#x60; and &#x60;message&#x60;, obtain resource IDs from search results.
     * @param objectId Resource IDs from which to remove the watcher.
     * @param userId ID of the user or regular group to remove as a watcher.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec removeWatcherFromResourcesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull String objectType, @jakarta.annotation.Nonnull List<Integer> objectId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return removeWatcherFromResourcesRequestCreation(format, objectType, objectId, userId, xRedmineSwitchUser);
    }
}
