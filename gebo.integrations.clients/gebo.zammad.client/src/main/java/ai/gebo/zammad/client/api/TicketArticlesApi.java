package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.DownloadTicketAttachmentViewParameter;
import ai.gebo.zammad.client.model.ErrorResponse;
import java.io.File;
import ai.gebo.zammad.client.model.TicketArticle;
import ai.gebo.zammad.client.model.TicketArticleCreate;

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
public class TicketArticlesApi {
    private ApiClient apiClient;

    public TicketArticlesApi() {
        this(new ApiClient());
    }

    public TicketArticlesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create ticket article
     * 
     * <p><b>201</b> - Ticket article created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketArticleCreate The ticketArticleCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketArticle
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createTicketArticleRequestCreation(@jakarta.annotation.Nonnull TicketArticleCreate ticketArticleCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'ticketArticleCreate' is set
        if (ticketArticleCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketArticleCreate' when calling createTicketArticle", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<TicketArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/ticket_articles", HttpMethod.POST, pathParams, localVarQueryParams, ticketArticleCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create ticket article
     * 
     * <p><b>201</b> - Ticket article created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketArticleCreate The ticketArticleCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return TicketArticle
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketArticle createTicketArticle(@jakarta.annotation.Nonnull TicketArticleCreate ticketArticleCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketArticleRequestCreation(ticketArticleCreate, from).body(localVarReturnType);
    }

    /**
     * Create ticket article
     * 
     * <p><b>201</b> - Ticket article created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketArticleCreate The ticketArticleCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;TicketArticle&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketArticle> createTicketArticleWithHttpInfo(@jakarta.annotation.Nonnull TicketArticleCreate ticketArticleCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<TicketArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTicketArticleRequestCreation(ticketArticleCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create ticket article
     * 
     * <p><b>201</b> - Ticket article created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketArticleCreate The ticketArticleCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createTicketArticleWithResponseSpec(@jakarta.annotation.Nonnull TicketArticleCreate ticketArticleCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createTicketArticleRequestCreation(ticketArticleCreate, from);
    }

    /**
     * Download ticket attachment
     * 
     * <p><b>200</b> - Raw attachment bytes.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketArticleId Ticket article ID.
     * @param attachmentId Attachment ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param view Use &#x60;inline&#x60; to request inline display.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadTicketAttachmentRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long ticketArticleId, @jakarta.annotation.Nonnull Long attachmentId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable DownloadTicketAttachmentViewParameter view) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling downloadTicketAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ticketArticleId' is set
        if (ticketArticleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketArticleId' when calling downloadTicketAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'attachmentId' when calling downloadTicketAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);
        pathParams.put("ticketArticleId", ticketArticleId);
        pathParams.put("attachmentId", attachmentId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "view", view));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/octet-stream", "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/ticket_attachment/{ticketId}/{ticketArticleId}/{attachmentId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download ticket attachment
     * 
     * <p><b>200</b> - Raw attachment bytes.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketArticleId Ticket article ID.
     * @param attachmentId Attachment ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param view Use &#x60;inline&#x60; to request inline display.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File downloadTicketAttachment(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long ticketArticleId, @jakarta.annotation.Nonnull Long attachmentId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable DownloadTicketAttachmentViewParameter view) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downloadTicketAttachmentRequestCreation(ticketId, ticketArticleId, attachmentId, from, view).body(localVarReturnType);
    }

    /**
     * Download ticket attachment
     * 
     * <p><b>200</b> - Raw attachment bytes.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketArticleId Ticket article ID.
     * @param attachmentId Attachment ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param view Use &#x60;inline&#x60; to request inline display.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> downloadTicketAttachmentWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long ticketArticleId, @jakarta.annotation.Nonnull Long attachmentId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable DownloadTicketAttachmentViewParameter view) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downloadTicketAttachmentRequestCreation(ticketId, ticketArticleId, attachmentId, from, view).toEntity(localVarReturnType);
    }

    /**
     * Download ticket attachment
     * 
     * <p><b>200</b> - Raw attachment bytes.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param ticketArticleId Ticket article ID.
     * @param attachmentId Attachment ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param view Use &#x60;inline&#x60; to request inline display.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadTicketAttachmentWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nonnull Long ticketArticleId, @jakarta.annotation.Nonnull Long attachmentId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable DownloadTicketAttachmentViewParameter view) throws RestClientResponseException {
        return downloadTicketAttachmentRequestCreation(ticketId, ticketArticleId, attachmentId, from, view);
    }

    /**
     * Show ticket article
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketArticleId Ticket article ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return TicketArticle
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTicketArticleRequestCreation(@jakarta.annotation.Nonnull Long ticketArticleId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        // verify the required parameter 'ticketArticleId' is set
        if (ticketArticleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketArticleId' when calling getTicketArticle", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketArticleId", ticketArticleId);

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

        ParameterizedTypeReference<TicketArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/ticket_articles/{ticketArticleId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show ticket article
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketArticleId Ticket article ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return TicketArticle
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketArticle getTicketArticle(@jakarta.annotation.Nonnull Long ticketArticleId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        ParameterizedTypeReference<TicketArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketArticleRequestCreation(ticketArticleId, from, expand).body(localVarReturnType);
    }

    /**
     * Show ticket article
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketArticleId Ticket article ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return ResponseEntity&lt;TicketArticle&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketArticle> getTicketArticleWithHttpInfo(@jakarta.annotation.Nonnull Long ticketArticleId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        ParameterizedTypeReference<TicketArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTicketArticleRequestCreation(ticketArticleId, from, expand).toEntity(localVarReturnType);
    }

    /**
     * Show ticket article
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketArticleId Ticket article ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTicketArticleWithResponseSpec(@jakarta.annotation.Nonnull Long ticketArticleId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand) throws RestClientResponseException {
        return getTicketArticleRequestCreation(ticketArticleId, from, expand);
    }

    /**
     * List ticket articles
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
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;TicketArticle&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listTicketArticlesRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling listTicketArticles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);

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

        ParameterizedTypeReference<List<TicketArticle>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/ticket_articles/by_ticket/{ticketId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List ticket articles
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
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;TicketArticle&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<TicketArticle> listTicketArticles(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<TicketArticle>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketArticlesRequestCreation(ticketId, from, expand, page, perPage).body(localVarReturnType);
    }

    /**
     * List ticket articles
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
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;List&lt;TicketArticle&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<TicketArticle>> listTicketArticlesWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<TicketArticle>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketArticlesRequestCreation(ticketId, from, expand, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List ticket articles
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
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listTicketArticlesWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return listTicketArticlesRequestCreation(ticketId, from, expand, page, perPage);
    }
}
