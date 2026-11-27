package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.TicketSharedDraftDeleteResult;
import ai.gebo.zammad.client.model.TicketSharedDraftResult;
import ai.gebo.zammad.client.model.TicketSharedDraftWrite;

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
public class TicketSharedDraftsApi {
    private ApiClient apiClient;

    public TicketSharedDraftsApi() {
        this(new ApiClient());
    }

    public TicketSharedDraftsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create ticket shared draft
     * 
     * <p><b>200</b> - Shared draft created or replaced.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketSharedDraftWrite The ticketSharedDraftWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketSharedDraftResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createTicketSharedDraftRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketSharedDraftWrite ticketSharedDraftWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling createTicketSharedDraft", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ticketSharedDraftWrite' is set
        if (ticketSharedDraftWrite == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketSharedDraftWrite' when calling createTicketSharedDraft", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);

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

        ParameterizedTypeReference<TicketSharedDraftResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/shared_draft", HttpMethod.PUT, pathParams, localVarQueryParams, ticketSharedDraftWrite, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create ticket shared draft
     * 
     * <p><b>200</b> - Shared draft created or replaced.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketSharedDraftWrite The ticketSharedDraftWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketSharedDraftResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketSharedDraftResult createTicketSharedDraft(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketSharedDraftWrite ticketSharedDraftWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSharedDraftResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketSharedDraftRequestCreation(ticketId, ticketSharedDraftWrite, from).body(localVarReturnType);
    }

    /**
     * Create ticket shared draft
     * 
     * <p><b>200</b> - Shared draft created or replaced.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketSharedDraftWrite The ticketSharedDraftWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TicketSharedDraftResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketSharedDraftResult> createTicketSharedDraftWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketSharedDraftWrite ticketSharedDraftWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSharedDraftResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketSharedDraftRequestCreation(ticketId, ticketSharedDraftWrite, from).toEntity(localVarReturnType);
    }

    /**
     * Create ticket shared draft
     * 
     * <p><b>200</b> - Shared draft created or replaced.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketSharedDraftWrite The ticketSharedDraftWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createTicketSharedDraftWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketSharedDraftWrite ticketSharedDraftWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createTicketSharedDraftRequestCreation(ticketId, ticketSharedDraftWrite, from);
    }

    /**
     * Delete ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketSharedDraftDeleteResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteTicketSharedDraftRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling deleteTicketSharedDraft", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);

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

        ParameterizedTypeReference<TicketSharedDraftDeleteResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/shared_draft", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketSharedDraftDeleteResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketSharedDraftDeleteResult deleteTicketSharedDraft(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSharedDraftDeleteResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTicketSharedDraftRequestCreation(ticketId, from).body(localVarReturnType);
    }

    /**
     * Delete ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TicketSharedDraftDeleteResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketSharedDraftDeleteResult> deleteTicketSharedDraftWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSharedDraftDeleteResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTicketSharedDraftRequestCreation(ticketId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteTicketSharedDraftWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteTicketSharedDraftRequestCreation(ticketId, from);
    }

    /**
     * Show ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketSharedDraftResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTicketSharedDraftRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling getTicketSharedDraft", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);

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

        ParameterizedTypeReference<TicketSharedDraftResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/shared_draft", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketSharedDraftResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketSharedDraftResult getTicketSharedDraft(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSharedDraftResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketSharedDraftRequestCreation(ticketId, from).body(localVarReturnType);
    }

    /**
     * Show ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TicketSharedDraftResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketSharedDraftResult> getTicketSharedDraftWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSharedDraftResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketSharedDraftRequestCreation(ticketId, from).toEntity(localVarReturnType);
    }

    /**
     * Show ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTicketSharedDraftWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getTicketSharedDraftRequestCreation(ticketId, from);
    }

    /**
     * Update ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketSharedDraftWrite The ticketSharedDraftWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketSharedDraftResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateTicketSharedDraftRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketSharedDraftWrite ticketSharedDraftWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling updateTicketSharedDraft", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ticketSharedDraftWrite' is set
        if (ticketSharedDraftWrite == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketSharedDraftWrite' when calling updateTicketSharedDraft", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);

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

        ParameterizedTypeReference<TicketSharedDraftResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/shared_draft", HttpMethod.PATCH, pathParams, localVarQueryParams, ticketSharedDraftWrite, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketSharedDraftWrite The ticketSharedDraftWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketSharedDraftResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketSharedDraftResult updateTicketSharedDraft(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketSharedDraftWrite ticketSharedDraftWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSharedDraftResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTicketSharedDraftRequestCreation(ticketId, ticketSharedDraftWrite, from).body(localVarReturnType);
    }

    /**
     * Update ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketSharedDraftWrite The ticketSharedDraftWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TicketSharedDraftResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketSharedDraftResult> updateTicketSharedDraftWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketSharedDraftWrite ticketSharedDraftWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSharedDraftResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTicketSharedDraftRequestCreation(ticketId, ticketSharedDraftWrite, from).toEntity(localVarReturnType);
    }

    /**
     * Update ticket shared draft
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketSharedDraftWrite The ticketSharedDraftWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateTicketSharedDraftWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketSharedDraftWrite ticketSharedDraftWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateTicketSharedDraftRequestCreation(ticketId, ticketSharedDraftWrite, from);
    }
}
