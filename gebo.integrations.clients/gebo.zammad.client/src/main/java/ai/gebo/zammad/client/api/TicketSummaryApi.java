package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.TicketSummaryResult;

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
public class TicketSummaryApi {
    private ApiClient apiClient;

    public TicketSummaryApi() {
        this(new ApiClient());
    }

    public TicketSummaryApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get or trigger ticket summary
     * Returns an existing AI summary or starts asynchronous generation. If generation was started, retry no sooner than 30 seconds.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param regenerationOfId Analytics run ID to regenerate.
     * @param aiAnalyticsRunErrorId Failed analytics run whose error to return.
     * @param body The body parameter
     * @return TicketSummaryResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec summarizeTicketRequestCreation(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Long regenerationOfId, @jakarta.annotation.Nullable Long aiAnalyticsRunErrorId, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // verify the required parameter 'ticketId' is set
        if (ticketId == null) {
            throw new RestClientResponseException("Missing the required parameter 'ticketId' when calling summarizeTicket", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ticketId", ticketId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "regeneration_of_id", regenerationOfId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ai_analytics_run_error_id", aiAnalyticsRunErrorId));

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

        ParameterizedTypeReference<TicketSummaryResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/tickets/{ticketId}/summarize", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get or trigger ticket summary
     * Returns an existing AI summary or starts asynchronous generation. If generation was started, retry no sooner than 30 seconds.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param regenerationOfId Analytics run ID to regenerate.
     * @param aiAnalyticsRunErrorId Failed analytics run whose error to return.
     * @param body The body parameter
     * @return TicketSummaryResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TicketSummaryResult summarizeTicket(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Long regenerationOfId, @jakarta.annotation.Nullable Long aiAnalyticsRunErrorId, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSummaryResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return summarizeTicketRequestCreation(ticketId, from, regenerationOfId, aiAnalyticsRunErrorId, body).body(localVarReturnType);
    }

    /**
     * Get or trigger ticket summary
     * Returns an existing AI summary or starts asynchronous generation. If generation was started, retry no sooner than 30 seconds.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param regenerationOfId Analytics run ID to regenerate.
     * @param aiAnalyticsRunErrorId Failed analytics run whose error to return.
     * @param body The body parameter
     * @return ResponseEntity&lt;TicketSummaryResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TicketSummaryResult> summarizeTicketWithHttpInfo(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Long regenerationOfId, @jakarta.annotation.Nullable Long aiAnalyticsRunErrorId, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<TicketSummaryResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return summarizeTicketRequestCreation(ticketId, from, regenerationOfId, aiAnalyticsRunErrorId, body).toEntity(localVarReturnType);
    }

    /**
     * Get or trigger ticket summary
     * Returns an existing AI summary or starts asynchronous generation. If generation was started, retry no sooner than 30 seconds.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param ticketId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param regenerationOfId Analytics run ID to regenerate.
     * @param aiAnalyticsRunErrorId Failed analytics run whose error to return.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec summarizeTicketWithResponseSpec(@jakarta.annotation.Nonnull Long ticketId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Long regenerationOfId, @jakarta.annotation.Nullable Long aiAnalyticsRunErrorId, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return summarizeTicketRequestCreation(ticketId, from, regenerationOfId, aiAnalyticsRunErrorId, body);
    }
}
