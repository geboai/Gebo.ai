package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.TimeAccounting;
import ai.gebo.zammad.client.model.TimeAccountingWrite;

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
public class TimeAccountingApi {
    private ApiClient apiClient;

    public TimeAccountingApi() {
        this(new ApiClient());
    }

    public TimeAccountingApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create ticket time accounting
     * 
     * <p><b>201</b> - Time accounting created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingWrite The timeAccountingWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TimeAccounting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createTicketTimeAccountingRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TimeAccountingWrite timeAccountingWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling createTicketTimeAccounting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeAccountingWrite' is set
        if (timeAccountingWrite == null) {
            throw new RestClientResponseException("Missing the required parameter 'timeAccountingWrite' when calling createTicketTimeAccounting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<TimeAccounting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/time_accountings", HttpMethod.POST, pathParams, localVarQueryParams, timeAccountingWrite, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create ticket time accounting
     * 
     * <p><b>201</b> - Time accounting created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingWrite The timeAccountingWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TimeAccounting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TimeAccounting createTicketTimeAccounting(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TimeAccountingWrite timeAccountingWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TimeAccounting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketTimeAccountingRequestCreation(ticketId, timeAccountingWrite, from).body(localVarReturnType);
    }

    /**
     * Create ticket time accounting
     * 
     * <p><b>201</b> - Time accounting created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingWrite The timeAccountingWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TimeAccounting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TimeAccounting> createTicketTimeAccountingWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TimeAccountingWrite timeAccountingWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TimeAccounting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketTimeAccountingRequestCreation(ticketId, timeAccountingWrite, from).toEntity(localVarReturnType);
    }

    /**
     * Create ticket time accounting
     * 
     * <p><b>201</b> - Time accounting created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingWrite The timeAccountingWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createTicketTimeAccountingWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TimeAccountingWrite timeAccountingWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createTicketTimeAccountingRequestCreation(ticketId, timeAccountingWrite, from);
    }

    /**
     * Delete ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteTicketTimeAccountingRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling deleteTicketTimeAccounting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeAccountingId' is set
        if (timeAccountingId == null) {
            throw new RestClientResponseException("Missing the required parameter 'timeAccountingId' when calling deleteTicketTimeAccounting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);
        pathParams.put("timeAccountingId", timeAccountingId);

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
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/time_accountings/{timeAccountingId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteTicketTimeAccounting(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTicketTimeAccountingRequestCreation(ticketId, timeAccountingId, from).body(localVarReturnType);
    }

    /**
     * Delete ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteTicketTimeAccountingWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTicketTimeAccountingRequestCreation(ticketId, timeAccountingId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteTicketTimeAccountingWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteTicketTimeAccountingRequestCreation(ticketId, timeAccountingId, from);
    }

    /**
     * Show ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TimeAccounting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTicketTimeAccountingRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling getTicketTimeAccounting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeAccountingId' is set
        if (timeAccountingId == null) {
            throw new RestClientResponseException("Missing the required parameter 'timeAccountingId' when calling getTicketTimeAccounting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);
        pathParams.put("timeAccountingId", timeAccountingId);

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

        ParameterizedTypeReference<TimeAccounting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/time_accountings/{timeAccountingId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TimeAccounting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TimeAccounting getTicketTimeAccounting(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TimeAccounting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketTimeAccountingRequestCreation(ticketId, timeAccountingId, from).body(localVarReturnType);
    }

    /**
     * Show ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TimeAccounting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TimeAccounting> getTicketTimeAccountingWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TimeAccounting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketTimeAccountingRequestCreation(ticketId, timeAccountingId, from).toEntity(localVarReturnType);
    }

    /**
     * Show ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTicketTimeAccountingWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getTicketTimeAccountingRequestCreation(ticketId, timeAccountingId, from);
    }

    /**
     * List ticket time accountings
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;TimeAccounting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listTicketTimeAccountingsRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling listTicketTimeAccountings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

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

        ParameterizedTypeReference<List<TimeAccounting>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/time_accountings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List ticket time accountings
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;TimeAccounting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<TimeAccounting> listTicketTimeAccountings(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<TimeAccounting>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketTimeAccountingsRequestCreation(ticketId, from, page, perPage).body(localVarReturnType);
    }

    /**
     * List ticket time accountings
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;List&lt;TimeAccounting&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<TimeAccounting>> listTicketTimeAccountingsWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<TimeAccounting>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketTimeAccountingsRequestCreation(ticketId, from, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List ticket time accountings
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listTicketTimeAccountingsWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return listTicketTimeAccountingsRequestCreation(ticketId, from, page, perPage);
    }

    /**
     * Update ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param timeAccountingWrite The timeAccountingWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TimeAccounting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateTicketTimeAccountingRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nonnull TimeAccountingWrite timeAccountingWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling updateTicketTimeAccounting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeAccountingId' is set
        if (timeAccountingId == null) {
            throw new RestClientResponseException("Missing the required parameter 'timeAccountingId' when calling updateTicketTimeAccounting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeAccountingWrite' is set
        if (timeAccountingWrite == null) {
            throw new RestClientResponseException("Missing the required parameter 'timeAccountingWrite' when calling updateTicketTimeAccounting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);
        pathParams.put("timeAccountingId", timeAccountingId);

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

        ParameterizedTypeReference<TimeAccounting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/time_accountings/{timeAccountingId}", HttpMethod.PUT, pathParams, localVarQueryParams, timeAccountingWrite, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param timeAccountingWrite The timeAccountingWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TimeAccounting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TimeAccounting updateTicketTimeAccounting(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nonnull TimeAccountingWrite timeAccountingWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TimeAccounting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTicketTimeAccountingRequestCreation(ticketId, timeAccountingId, timeAccountingWrite, from).body(localVarReturnType);
    }

    /**
     * Update ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param timeAccountingWrite The timeAccountingWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TimeAccounting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TimeAccounting> updateTicketTimeAccountingWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nonnull TimeAccountingWrite timeAccountingWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TimeAccounting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTicketTimeAccountingRequestCreation(ticketId, timeAccountingId, timeAccountingWrite, from).toEntity(localVarReturnType);
    }

    /**
     * Update ticket time accounting
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param timeAccountingId Time accounting ID.
     * @param timeAccountingWrite The timeAccountingWrite parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateTicketTimeAccountingWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long timeAccountingId, @jakarta.annotation.Nonnull TimeAccountingWrite timeAccountingWrite, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateTicketTimeAccountingRequestCreation(ticketId, timeAccountingId, timeAccountingWrite, from);
    }
}
