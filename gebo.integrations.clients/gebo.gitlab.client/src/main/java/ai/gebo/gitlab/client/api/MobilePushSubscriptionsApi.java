package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesMobilePushSubscription;
import ai.gebo.gitlab.client.model.PostApiV4UserPushSubscriptionsRequest;

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
public class MobilePushSubscriptionsApi {
    private ApiClient apiClient;

    public MobilePushSubscriptionsApi() {
        this(new ApiClient());
    }

    public MobilePushSubscriptionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Unregister a mobile device from push notifications
     * Deletes every push subscription for the given device token, including subscriptions that other users registered.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param deviceToken The hexadecimal APNs device token
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UserPushSubscriptionsRequestCreation(@jakarta.annotation.Nullable String deviceToken) throws RestClientResponseException {
        // verify the required parameter 'deviceToken' is set
        if (deviceToken == null) {
            throw new RestClientResponseException("Missing the required parameter 'deviceToken' when calling deleteApiV4UserPushSubscriptions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "device_token", deviceToken));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/push_subscriptions", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unregister a mobile device from push notifications
     * Deletes every push subscription for the given device token, including subscriptions that other users registered.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param deviceToken The hexadecimal APNs device token
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4UserPushSubscriptions(@jakarta.annotation.Nullable String deviceToken) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4UserPushSubscriptionsRequestCreation(deviceToken).body(localVarReturnType);
    }

    /**
     * Unregister a mobile device from push notifications
     * Deletes every push subscription for the given device token, including subscriptions that other users registered.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param deviceToken The hexadecimal APNs device token
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4UserPushSubscriptionsWithHttpInfo(@jakarta.annotation.Nullable String deviceToken) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UserPushSubscriptionsRequestCreation(deviceToken).toEntity(localVarReturnType);
    }

    /**
     * Unregister a mobile device from push notifications
     * Deletes every push subscription for the given device token, including subscriptions that other users registered.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param deviceToken The hexadecimal APNs device token
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UserPushSubscriptionsWithResponseSpec(@jakarta.annotation.Nullable String deviceToken) throws RestClientResponseException {
        return deleteApiV4UserPushSubscriptionsRequestCreation(deviceToken);
    }

    /**
     * Register a mobile device for push notifications
     * Idempotently registers the calling user&#39;s device token for APNs push notifications. Re-registering an existing token refreshes its attributes; a token previously owned by another user is reassigned to the calling user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param postApiV4UserPushSubscriptionsRequest The postApiV4UserPushSubscriptionsRequest parameter
     * @return APIEntitiesMobilePushSubscription
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserPushSubscriptionsRequestCreation(@jakarta.annotation.Nonnull PostApiV4UserPushSubscriptionsRequest postApiV4UserPushSubscriptionsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UserPushSubscriptionsRequest' is set
        if (postApiV4UserPushSubscriptionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UserPushSubscriptionsRequest' when calling postApiV4UserPushSubscriptions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMobilePushSubscription> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/push_subscriptions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UserPushSubscriptionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Register a mobile device for push notifications
     * Idempotently registers the calling user&#39;s device token for APNs push notifications. Re-registering an existing token refreshes its attributes; a token previously owned by another user is reassigned to the calling user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param postApiV4UserPushSubscriptionsRequest The postApiV4UserPushSubscriptionsRequest parameter
     * @return APIEntitiesMobilePushSubscription
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMobilePushSubscription postApiV4UserPushSubscriptions(@jakarta.annotation.Nonnull PostApiV4UserPushSubscriptionsRequest postApiV4UserPushSubscriptionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMobilePushSubscription> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserPushSubscriptionsRequestCreation(postApiV4UserPushSubscriptionsRequest).body(localVarReturnType);
    }

    /**
     * Register a mobile device for push notifications
     * Idempotently registers the calling user&#39;s device token for APNs push notifications. Re-registering an existing token refreshes its attributes; a token previously owned by another user is reassigned to the calling user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param postApiV4UserPushSubscriptionsRequest The postApiV4UserPushSubscriptionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMobilePushSubscription&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMobilePushSubscription> postApiV4UserPushSubscriptionsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UserPushSubscriptionsRequest postApiV4UserPushSubscriptionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMobilePushSubscription> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserPushSubscriptionsRequestCreation(postApiV4UserPushSubscriptionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Register a mobile device for push notifications
     * Idempotently registers the calling user&#39;s device token for APNs push notifications. Re-registering an existing token refreshes its attributes; a token previously owned by another user is reassigned to the calling user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param postApiV4UserPushSubscriptionsRequest The postApiV4UserPushSubscriptionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserPushSubscriptionsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UserPushSubscriptionsRequest postApiV4UserPushSubscriptionsRequest) throws RestClientResponseException {
        return postApiV4UserPushSubscriptionsRequestCreation(postApiV4UserPushSubscriptionsRequest);
    }
}
