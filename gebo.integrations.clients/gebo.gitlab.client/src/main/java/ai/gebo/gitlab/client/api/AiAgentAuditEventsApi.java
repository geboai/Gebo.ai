package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdAiAgentAuditEventsRequest;

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
public class AiAgentAuditEventsApi {
    private ApiClient apiClient;

    public AiAgentAuditEventsApi() {
        this(new ApiClient());
    }

    public AiAgentAuditEventsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Ingest external agent audit events.
     * Accepts a batch of up to 500 audit events for an external agent session. Events are validated, deduplicated by cloud_event_id, and enqueued for storage.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentAuditEventsRequest The postApiV4ProjectsIdAiAgentAuditEventsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdAiAgentAuditEventsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentAuditEventsRequest postApiV4ProjectsIdAiAgentAuditEventsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdAiAgentAuditEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdAiAgentAuditEventsRequest' is set
        if (postApiV4ProjectsIdAiAgentAuditEventsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdAiAgentAuditEventsRequest' when calling postApiV4ProjectsIdAiAgentAuditEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ai_agent/audit_events", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdAiAgentAuditEventsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Ingest external agent audit events.
     * Accepts a batch of up to 500 audit events for an external agent session. Events are validated, deduplicated by cloud_event_id, and enqueued for storage.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentAuditEventsRequest The postApiV4ProjectsIdAiAgentAuditEventsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdAiAgentAuditEvents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentAuditEventsRequest postApiV4ProjectsIdAiAgentAuditEventsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdAiAgentAuditEventsRequestCreation(id, postApiV4ProjectsIdAiAgentAuditEventsRequest).body(localVarReturnType);
    }

    /**
     * Ingest external agent audit events.
     * Accepts a batch of up to 500 audit events for an external agent session. Events are validated, deduplicated by cloud_event_id, and enqueued for storage.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentAuditEventsRequest The postApiV4ProjectsIdAiAgentAuditEventsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdAiAgentAuditEventsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentAuditEventsRequest postApiV4ProjectsIdAiAgentAuditEventsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAiAgentAuditEventsRequestCreation(id, postApiV4ProjectsIdAiAgentAuditEventsRequest).toEntity(localVarReturnType);
    }

    /**
     * Ingest external agent audit events.
     * Accepts a batch of up to 500 audit events for an external agent session. Events are validated, deduplicated by cloud_event_id, and enqueued for storage.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentAuditEventsRequest The postApiV4ProjectsIdAiAgentAuditEventsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdAiAgentAuditEventsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentAuditEventsRequest postApiV4ProjectsIdAiAgentAuditEventsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdAiAgentAuditEventsRequestCreation(id, postApiV4ProjectsIdAiAgentAuditEventsRequest);
    }
}
