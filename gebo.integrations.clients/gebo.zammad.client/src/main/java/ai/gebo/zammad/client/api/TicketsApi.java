package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ConditionSearchRequest;
import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.SearchGroupsOrderByParameter;
import ai.gebo.zammad.client.model.SearchTickets200Response;
import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.client.model.TicketCreate;
import ai.gebo.zammad.client.model.TicketUpdate;

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
public class TicketsApi {
    private ApiClient apiClient;

    public TicketsApi() {
        this(new ApiClient());
    }

    public TicketsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create ticket
     * Agents may set &#x60;customer_id&#x60; to &#x60;guess:&lt;email&gt;&#x60; to create or resolve a customer. Custom Object Manager fields are accepted.
     * <p><b>201</b> - Ticket created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketCreate The ticketCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Ticket
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createTicketRequestCreation(@jakarta.annotation.Nonnull TicketCreate ticketCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketCreate' is set
        if (ticketCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketCreate' when calling createTicket", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Ticket> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets", HttpMethod.POST, pathParams, localVarQueryParams, ticketCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create ticket
     * Agents may set &#x60;customer_id&#x60; to &#x60;guess:&lt;email&gt;&#x60; to create or resolve a customer. Custom Object Manager fields are accepted.
     * <p><b>201</b> - Ticket created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketCreate The ticketCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Ticket
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Ticket createTicket(@jakarta.annotation.Nonnull TicketCreate ticketCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Ticket> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketRequestCreation(ticketCreate, from).body(localVarReturnType);
    }

    /**
     * Create ticket
     * Agents may set &#x60;customer_id&#x60; to &#x60;guess:&lt;email&gt;&#x60; to create or resolve a customer. Custom Object Manager fields are accepted.
     * <p><b>201</b> - Ticket created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketCreate The ticketCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Ticket&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Ticket> createTicketWithHttpInfo(@jakarta.annotation.Nonnull TicketCreate ticketCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Ticket> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketRequestCreation(ticketCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create ticket
     * Agents may set &#x60;customer_id&#x60; to &#x60;guess:&lt;email&gt;&#x60; to create or resolve a customer. Custom Object Manager fields are accepted.
     * <p><b>201</b> - Ticket created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketCreate The ticketCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createTicketWithResponseSpec(@jakarta.annotation.Nonnull TicketCreate ticketCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createTicketRequestCreation(ticketCreate, from);
    }

    /**
     * Delete ticket
     * Permanently removes the ticket, articles, and attachments.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteTicketRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling deleteTicket", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete ticket
     * Permanently removes the ticket, articles, and attachments.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteTicket(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTicketRequestCreation(ticketId, from).body(localVarReturnType);
    }

    /**
     * Delete ticket
     * Permanently removes the ticket, articles, and attachments.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteTicketWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTicketRequestCreation(ticketId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete ticket
     * Permanently removes the ticket, articles, and attachments.
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
    public ResponseSpec deleteTicketWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteTicketRequestCreation(ticketId, from);
    }

    /**
     * Show ticket
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return Ticket
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTicketRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling getTicket", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);

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

        ParameterizedTypeReference<Ticket> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show ticket
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return Ticket
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Ticket getTicket(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        ParameterizedTypeReference<Ticket> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketRequestCreation(ticketId, from, expand).body(localVarReturnType);
    }

    /**
     * Show ticket
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return ResponseEntity&lt;Ticket&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Ticket> getTicketWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        ParameterizedTypeReference<Ticket> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketRequestCreation(ticketId, from, expand).toEntity(localVarReturnType);
    }

    /**
     * Show ticket
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTicketWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        return getTicketRequestCreation(ticketId, from, expand);
    }

    /**
     * List tickets
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
     * @return List&lt;Ticket&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listTicketsRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<Ticket>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List tickets
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
     * @return List&lt;Ticket&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<Ticket> listTickets(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<Ticket>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketsRequestCreation(from, expand, page, perPage).body(localVarReturnType);
    }

    /**
     * List tickets
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
     * @return ResponseEntity&lt;List&lt;Ticket&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Ticket>> listTicketsWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<Ticket>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketsRequestCreation(from, expand, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List tickets
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
    public ResponseSpec listTicketsWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return listTicketsRequestCreation(from, expand, page, perPage);
    }

    /**
     * Search tickets
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return SearchTickets200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchTicketsRequestCreation(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new RestClientResponseException("Missing the required parameter 'query' when calling searchTickets", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort_by", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));

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

        ParameterizedTypeReference<SearchTickets200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search tickets
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return SearchTickets200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SearchTickets200Response searchTickets(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<SearchTickets200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTicketsRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).body(localVarReturnType);
    }

    /**
     * Search tickets
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseEntity&lt;SearchTickets200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchTickets200Response> searchTicketsWithHttpInfo(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<SearchTickets200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTicketsRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).toEntity(localVarReturnType);
    }

    /**
     * Search tickets
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchTicketsWithResponseSpec(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        return searchTicketsRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy);
    }

    /**
     * Search tickets by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return SearchTickets200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchTicketsByConditionRequestCreation(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'conditionSearchRequest' is set
        if (conditionSearchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'conditionSearchRequest' when calling searchTicketsByCondition", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

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

        ParameterizedTypeReference<SearchTickets200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/search", HttpMethod.POST, pathParams, localVarQueryParams, conditionSearchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search tickets by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return SearchTickets200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SearchTickets200Response searchTicketsByCondition(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<SearchTickets200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTicketsByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).body(localVarReturnType);
    }

    /**
     * Search tickets by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;SearchTickets200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchTickets200Response> searchTicketsByConditionWithHttpInfo(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<SearchTickets200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTicketsByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Search tickets by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchTicketsByConditionWithResponseSpec(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return searchTicketsByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage);
    }

    /**
     * Update ticket
     * A nested &#x60;article&#x60; creates a new article; it never edits an existing article.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketUpdate The ticketUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Ticket
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateTicketRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketUpdate ticketUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling updateTicket", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ticketUpdate' is set
        if (ticketUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketUpdate' when calling updateTicket", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Ticket> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}", HttpMethod.PUT, pathParams, localVarQueryParams, ticketUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update ticket
     * A nested &#x60;article&#x60; creates a new article; it never edits an existing article.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketUpdate The ticketUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Ticket
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Ticket updateTicket(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketUpdate ticketUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Ticket> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTicketRequestCreation(ticketId, ticketUpdate, from).body(localVarReturnType);
    }

    /**
     * Update ticket
     * A nested &#x60;article&#x60; creates a new article; it never edits an existing article.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketUpdate The ticketUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Ticket&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Ticket> updateTicketWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketUpdate ticketUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Ticket> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTicketRequestCreation(ticketId, ticketUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update ticket
     * A nested &#x60;article&#x60; creates a new article; it never edits an existing article.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketUpdate The ticketUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateTicketWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull TicketUpdate ticketUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateTicketRequestCreation(ticketId, ticketUpdate, from);
    }
}
