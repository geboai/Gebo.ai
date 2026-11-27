package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.TicketPriority;
import ai.gebo.zammad.client.model.TicketPriorityCreate;
import ai.gebo.zammad.client.model.TicketPriorityUpdate;

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
public class TicketPrioritiesApi {
    private ApiClient apiClient;

    public TicketPrioritiesApi() {
        this(new ApiClient());
    }

    public TicketPrioritiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create ticket priority
     * 
     * <p><b>201</b> - Resource created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityCreate The ticketPriorityCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketPriority
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createTicketPriorityRequestCreation(@jakarta.annotation.Nonnull TicketPriorityCreate ticketPriorityCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketPriorityCreate' is set
        if (ticketPriorityCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketPriorityCreate' when calling createTicketPriority", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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

        ParameterizedTypeReference<TicketPriority> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/ticket_priorities", HttpMethod.POST, pathParams, localVarQueryParams, ticketPriorityCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create ticket priority
     * 
     * <p><b>201</b> - Resource created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityCreate The ticketPriorityCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketPriority
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketPriority createTicketPriority(@jakarta.annotation.Nonnull TicketPriorityCreate ticketPriorityCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketPriority> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketPriorityRequestCreation(ticketPriorityCreate, from).body(localVarReturnType);
    }

    /**
     * Create ticket priority
     * 
     * <p><b>201</b> - Resource created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityCreate The ticketPriorityCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TicketPriority&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketPriority> createTicketPriorityWithHttpInfo(@jakarta.annotation.Nonnull TicketPriorityCreate ticketPriorityCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketPriority> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketPriorityRequestCreation(ticketPriorityCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create ticket priority
     * 
     * <p><b>201</b> - Resource created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityCreate The ticketPriorityCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createTicketPriorityWithResponseSpec(@jakarta.annotation.Nonnull TicketPriorityCreate ticketPriorityCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createTicketPriorityRequestCreation(ticketPriorityCreate, from);
    }

    /**
     * Delete ticket priority
     * Permanently deletes the resource.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteTicketPriorityRequestCreation(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketPriorityId' is set
        if (ticketPriorityId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketPriorityId' when calling deleteTicketPriority", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketPriorityId", ticketPriorityId);

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
        return apiClient.invokeAPI("/api/v1/ticket_priorities/{ticketPriorityId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete ticket priority
     * Permanently deletes the resource.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteTicketPriority(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTicketPriorityRequestCreation(ticketPriorityId, from).body(localVarReturnType);
    }

    /**
     * Delete ticket priority
     * Permanently deletes the resource.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteTicketPriorityWithHttpInfo(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTicketPriorityRequestCreation(ticketPriorityId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete ticket priority
     * Permanently deletes the resource.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteTicketPriorityWithResponseSpec(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteTicketPriorityRequestCreation(ticketPriorityId, from);
    }

    /**
     * Show ticket priority
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return TicketPriority
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTicketPriorityRequestCreation(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        // verify the required parameter 'ticketPriorityId' is set
        if (ticketPriorityId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketPriorityId' when calling getTicketPriority", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketPriorityId", ticketPriorityId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));

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

        ParameterizedTypeReference<TicketPriority> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/ticket_priorities/{ticketPriorityId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show ticket priority
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return TicketPriority
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketPriority getTicketPriority(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        ParameterizedTypeReference<TicketPriority> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketPriorityRequestCreation(ticketPriorityId, from, expand).body(localVarReturnType);
    }

    /**
     * Show ticket priority
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return ResponseEntity&lt;TicketPriority&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketPriority> getTicketPriorityWithHttpInfo(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        ParameterizedTypeReference<TicketPriority> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketPriorityRequestCreation(ticketPriorityId, from, expand).toEntity(localVarReturnType);
    }

    /**
     * Show ticket priority
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTicketPriorityWithResponseSpec(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        return getTicketPriorityRequestCreation(ticketPriorityId, from, expand);
    }

    /**
     * List ticket priorities
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
     * @return List&lt;TicketPriority&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listTicketPrioritiesRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<TicketPriority>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/ticket_priorities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List ticket priorities
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
     * @return List&lt;TicketPriority&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<TicketPriority> listTicketPriorities(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<TicketPriority>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketPrioritiesRequestCreation(from, expand, page, perPage).body(localVarReturnType);
    }

    /**
     * List ticket priorities
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
     * @return ResponseEntity&lt;List&lt;TicketPriority&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<TicketPriority>> listTicketPrioritiesWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<TicketPriority>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketPrioritiesRequestCreation(from, expand, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List ticket priorities
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
    public ResponseSpec listTicketPrioritiesWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return listTicketPrioritiesRequestCreation(from, expand, page, perPage);
    }

    /**
     * Update ticket priority
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param ticketPriorityUpdate The ticketPriorityUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketPriority
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateTicketPriorityRequestCreation(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nonnull TicketPriorityUpdate ticketPriorityUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketPriorityId' is set
        if (ticketPriorityId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketPriorityId' when calling updateTicketPriority", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ticketPriorityUpdate' is set
        if (ticketPriorityUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketPriorityUpdate' when calling updateTicketPriority", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketPriorityId", ticketPriorityId);

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

        ParameterizedTypeReference<TicketPriority> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/ticket_priorities/{ticketPriorityId}", HttpMethod.PUT, pathParams, localVarQueryParams, ticketPriorityUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update ticket priority
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param ticketPriorityUpdate The ticketPriorityUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketPriority
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketPriority updateTicketPriority(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nonnull TicketPriorityUpdate ticketPriorityUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketPriority> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTicketPriorityRequestCreation(ticketPriorityId, ticketPriorityUpdate, from).body(localVarReturnType);
    }

    /**
     * Update ticket priority
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param ticketPriorityUpdate The ticketPriorityUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TicketPriority&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketPriority> updateTicketPriorityWithHttpInfo(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nonnull TicketPriorityUpdate ticketPriorityUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketPriority> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTicketPriorityRequestCreation(ticketPriorityId, ticketPriorityUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update ticket priority
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketPriorityId Ticket priority ID.
     * @param ticketPriorityUpdate The ticketPriorityUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateTicketPriorityWithResponseSpec(@jakarta.annotation.Nonnull Long ticketPriorityId, @jakarta.annotation.Nonnull TicketPriorityUpdate ticketPriorityUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateTicketPriorityRequestCreation(ticketPriorityId, ticketPriorityUpdate, from);
    }
}
