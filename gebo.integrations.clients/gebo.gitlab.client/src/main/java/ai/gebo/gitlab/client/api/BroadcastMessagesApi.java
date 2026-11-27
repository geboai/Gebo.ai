package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesSystemBroadcastMessage;
import ai.gebo.gitlab.client.model.PostApiV4BroadcastMessagesRequest;
import ai.gebo.gitlab.client.model.PutApiV4BroadcastMessagesIdRequest;

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
public class BroadcastMessagesApi {
    private ApiClient apiClient;

    public BroadcastMessagesApi() {
        this(new ApiClient());
    }

    public BroadcastMessagesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a broadcast message
     * Deletes a specified broadcast message.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @return APIEntitiesSystemBroadcastMessage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4BroadcastMessagesIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4BroadcastMessagesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/broadcast_messages/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a broadcast message
     * Deletes a specified broadcast message.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @return APIEntitiesSystemBroadcastMessage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSystemBroadcastMessage deleteApiV4BroadcastMessagesId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4BroadcastMessagesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a broadcast message
     * Deletes a specified broadcast message.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @return ResponseEntity&lt;APIEntitiesSystemBroadcastMessage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSystemBroadcastMessage> deleteApiV4BroadcastMessagesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4BroadcastMessagesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a broadcast message
     * Deletes a specified broadcast message.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4BroadcastMessagesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4BroadcastMessagesIdRequestCreation(id);
    }

    /**
     * List all broadcast messages
     * Lists all broadcast messages for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSystemBroadcastMessage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4BroadcastMessagesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesSystemBroadcastMessage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/broadcast_messages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all broadcast messages
     * Lists all broadcast messages for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSystemBroadcastMessage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSystemBroadcastMessage> getApiV4BroadcastMessages(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSystemBroadcastMessage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BroadcastMessagesRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all broadcast messages
     * Lists all broadcast messages for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSystemBroadcastMessage&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSystemBroadcastMessage>> getApiV4BroadcastMessagesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSystemBroadcastMessage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BroadcastMessagesRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all broadcast messages
     * Lists all broadcast messages for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4BroadcastMessagesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4BroadcastMessagesRequestCreation(page, perPage);
    }

    /**
     * Retrieve a broadcast message
     * Retrieves a specified broadcast message.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @return APIEntitiesSystemBroadcastMessage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4BroadcastMessagesIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4BroadcastMessagesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/broadcast_messages/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a broadcast message
     * Retrieves a specified broadcast message.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @return APIEntitiesSystemBroadcastMessage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSystemBroadcastMessage getApiV4BroadcastMessagesId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BroadcastMessagesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a broadcast message
     * Retrieves a specified broadcast message.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @return ResponseEntity&lt;APIEntitiesSystemBroadcastMessage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSystemBroadcastMessage> getApiV4BroadcastMessagesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4BroadcastMessagesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a broadcast message
     * Retrieves a specified broadcast message.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4BroadcastMessagesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4BroadcastMessagesIdRequestCreation(id);
    }

    /**
     * Create a broadcast message
     * Creates a broadcast message. Broadcast messages are publicly accessible through the API regardless of targeting settings. Do not include sensitive or confidential information.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4BroadcastMessagesRequest The postApiV4BroadcastMessagesRequest parameter
     * @return APIEntitiesSystemBroadcastMessage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4BroadcastMessagesRequestCreation(@jakarta.annotation.Nonnull PostApiV4BroadcastMessagesRequest postApiV4BroadcastMessagesRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4BroadcastMessagesRequest' is set
        if (postApiV4BroadcastMessagesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4BroadcastMessagesRequest' when calling postApiV4BroadcastMessages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/broadcast_messages", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4BroadcastMessagesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a broadcast message
     * Creates a broadcast message. Broadcast messages are publicly accessible through the API regardless of targeting settings. Do not include sensitive or confidential information.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4BroadcastMessagesRequest The postApiV4BroadcastMessagesRequest parameter
     * @return APIEntitiesSystemBroadcastMessage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSystemBroadcastMessage postApiV4BroadcastMessages(@jakarta.annotation.Nonnull PostApiV4BroadcastMessagesRequest postApiV4BroadcastMessagesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4BroadcastMessagesRequestCreation(postApiV4BroadcastMessagesRequest).body(localVarReturnType);
    }

    /**
     * Create a broadcast message
     * Creates a broadcast message. Broadcast messages are publicly accessible through the API regardless of targeting settings. Do not include sensitive or confidential information.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4BroadcastMessagesRequest The postApiV4BroadcastMessagesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSystemBroadcastMessage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSystemBroadcastMessage> postApiV4BroadcastMessagesWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4BroadcastMessagesRequest postApiV4BroadcastMessagesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4BroadcastMessagesRequestCreation(postApiV4BroadcastMessagesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a broadcast message
     * Creates a broadcast message. Broadcast messages are publicly accessible through the API regardless of targeting settings. Do not include sensitive or confidential information.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4BroadcastMessagesRequest The postApiV4BroadcastMessagesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4BroadcastMessagesWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4BroadcastMessagesRequest postApiV4BroadcastMessagesRequest) throws RestClientResponseException {
        return postApiV4BroadcastMessagesRequestCreation(postApiV4BroadcastMessagesRequest);
    }

    /**
     * Update a broadcast message
     * Updates a specified broadcast message. Broadcast messages are publicly accessible through the API regardless of targeting settings. Do not include sensitive or confidential information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @param putApiV4BroadcastMessagesIdRequest The putApiV4BroadcastMessagesIdRequest parameter
     * @return APIEntitiesSystemBroadcastMessage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4BroadcastMessagesIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4BroadcastMessagesIdRequest putApiV4BroadcastMessagesIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4BroadcastMessagesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/broadcast_messages/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4BroadcastMessagesIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a broadcast message
     * Updates a specified broadcast message. Broadcast messages are publicly accessible through the API regardless of targeting settings. Do not include sensitive or confidential information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @param putApiV4BroadcastMessagesIdRequest The putApiV4BroadcastMessagesIdRequest parameter
     * @return APIEntitiesSystemBroadcastMessage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSystemBroadcastMessage putApiV4BroadcastMessagesId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4BroadcastMessagesIdRequest putApiV4BroadcastMessagesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4BroadcastMessagesIdRequestCreation(id, putApiV4BroadcastMessagesIdRequest).body(localVarReturnType);
    }

    /**
     * Update a broadcast message
     * Updates a specified broadcast message. Broadcast messages are publicly accessible through the API regardless of targeting settings. Do not include sensitive or confidential information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @param putApiV4BroadcastMessagesIdRequest The putApiV4BroadcastMessagesIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSystemBroadcastMessage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSystemBroadcastMessage> putApiV4BroadcastMessagesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4BroadcastMessagesIdRequest putApiV4BroadcastMessagesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSystemBroadcastMessage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4BroadcastMessagesIdRequestCreation(id, putApiV4BroadcastMessagesIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a broadcast message
     * Updates a specified broadcast message. Broadcast messages are publicly accessible through the API regardless of targeting settings. Do not include sensitive or confidential information.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the broadcast message.
     * @param putApiV4BroadcastMessagesIdRequest The putApiV4BroadcastMessagesIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4BroadcastMessagesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4BroadcastMessagesIdRequest putApiV4BroadcastMessagesIdRequest) throws RestClientResponseException {
        return putApiV4BroadcastMessagesIdRequestCreation(id, putApiV4BroadcastMessagesIdRequest);
    }
}
