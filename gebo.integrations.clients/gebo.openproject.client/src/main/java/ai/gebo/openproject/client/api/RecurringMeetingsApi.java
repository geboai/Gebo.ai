package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.CompleteRecurringMeetingTemplateRequest;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.MeetingModel;
import java.time.OffsetDateTime;
import ai.gebo.openproject.client.model.RecurringMeetingCollectionModel;
import ai.gebo.openproject.client.model.RecurringMeetingModel;
import ai.gebo.openproject.client.model.RecurringMeetingOccurrenceCollectionModel;
import ai.gebo.openproject.client.model.RecurringMeetingWriteModel;

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
public class RecurringMeetingsApi {
    private ApiClient apiClient;

    public RecurringMeetingsApi() {
        this(new ApiClient());
    }

    public RecurringMeetingsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Cancel an occurrence
     * Cancels the occurrence at the given &#x60;start_time&#x60; for this recurring meeting series. A &#x60;ScheduledMeeting&#x60; record is created (or updated) with &#x60;cancelled: true&#x60;.  An occurrence that has already been instantiated as a meeting cannot be cancelled via this endpoint — delete the meeting directly via &#x60;DELETE /api/v3/meetings/{id}&#x60; instead.
     * <p><b>204</b> - Returned if the occurrence was successfully cancelled.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>409</b> - Returned if the occurrence has already been instantiated as a meeting. Delete the meeting directly via &#x60;DELETE /api/v3/meetings/{id}&#x60; instead.
     * @param id Recurring meeting identifier
     * @param startTime The ISO 8601 start time of the occurrence to cancel (e.g. &#x60;2025-06-02T10:00:00Z&#x60;)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec cancelRecurringMeetingOccurrenceRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull OffsetDateTime startTime) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling cancelRecurringMeetingOccurrence", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'startTime' is set
        if (startTime == null) {
            throw new RestClientResponseException("Missing the required parameter 'startTime' when calling cancelRecurringMeetingOccurrence", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("start_time", startTime);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}/occurrences/{start_time}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Cancel an occurrence
     * Cancels the occurrence at the given &#x60;start_time&#x60; for this recurring meeting series. A &#x60;ScheduledMeeting&#x60; record is created (or updated) with &#x60;cancelled: true&#x60;.  An occurrence that has already been instantiated as a meeting cannot be cancelled via this endpoint — delete the meeting directly via &#x60;DELETE /api/v3/meetings/{id}&#x60; instead.
     * <p><b>204</b> - Returned if the occurrence was successfully cancelled.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>409</b> - Returned if the occurrence has already been instantiated as a meeting. Delete the meeting directly via &#x60;DELETE /api/v3/meetings/{id}&#x60; instead.
     * @param id Recurring meeting identifier
     * @param startTime The ISO 8601 start time of the occurrence to cancel (e.g. &#x60;2025-06-02T10:00:00Z&#x60;)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void cancelRecurringMeetingOccurrence(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull OffsetDateTime startTime) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        cancelRecurringMeetingOccurrenceRequestCreation(id, startTime).body(localVarReturnType);
    }

    /**
     * Cancel an occurrence
     * Cancels the occurrence at the given &#x60;start_time&#x60; for this recurring meeting series. A &#x60;ScheduledMeeting&#x60; record is created (or updated) with &#x60;cancelled: true&#x60;.  An occurrence that has already been instantiated as a meeting cannot be cancelled via this endpoint — delete the meeting directly via &#x60;DELETE /api/v3/meetings/{id}&#x60; instead.
     * <p><b>204</b> - Returned if the occurrence was successfully cancelled.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>409</b> - Returned if the occurrence has already been instantiated as a meeting. Delete the meeting directly via &#x60;DELETE /api/v3/meetings/{id}&#x60; instead.
     * @param id Recurring meeting identifier
     * @param startTime The ISO 8601 start time of the occurrence to cancel (e.g. &#x60;2025-06-02T10:00:00Z&#x60;)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> cancelRecurringMeetingOccurrenceWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull OffsetDateTime startTime) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return cancelRecurringMeetingOccurrenceRequestCreation(id, startTime).toEntity(localVarReturnType);
    }

    /**
     * Cancel an occurrence
     * Cancels the occurrence at the given &#x60;start_time&#x60; for this recurring meeting series. A &#x60;ScheduledMeeting&#x60; record is created (or updated) with &#x60;cancelled: true&#x60;.  An occurrence that has already been instantiated as a meeting cannot be cancelled via this endpoint — delete the meeting directly via &#x60;DELETE /api/v3/meetings/{id}&#x60; instead.
     * <p><b>204</b> - Returned if the occurrence was successfully cancelled.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>409</b> - Returned if the occurrence has already been instantiated as a meeting. Delete the meeting directly via &#x60;DELETE /api/v3/meetings/{id}&#x60; instead.
     * @param id Recurring meeting identifier
     * @param startTime The ISO 8601 start time of the occurrence to cancel (e.g. &#x60;2025-06-02T10:00:00Z&#x60;)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec cancelRecurringMeetingOccurrenceWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull OffsetDateTime startTime) throws RestClientResponseException {
        return cancelRecurringMeetingOccurrenceRequestCreation(id, startTime);
    }

    /**
     * Complete the series template
     * Completes the series template and starts the recurring meeting. This exits draft mode, initiates the first occurrence from it, and schedules the job that creates the next occurrence.  The &#x60;notify&#x60; flag sets the series-wide email notification setting (stored on the template). When &#x60;true&#x60;, the initial series invitations are sent to the template participants. &#x60;notify&#x60; must be provided explicitly.  Completing an already-completed series is rejected.  Returns the updated &#x60;RecurringMeeting&#x60; resource.
     * <p><b>200</b> - Returned when the template is completed successfully.
     * <p><b>400</b> - Returned if the required &#x60;notify&#x60; parameter is missing.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if the template could not be completed, for example because the series has no next occurrence to schedule or it has already been completed.
     * @param id Recurring meeting ID
     * @param completeRecurringMeetingTemplateRequest The completeRecurringMeetingTemplateRequest parameter
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec completeRecurringMeetingTemplateRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable CompleteRecurringMeetingTemplateRequest completeRecurringMeetingTemplateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling completeRecurringMeetingTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}/template_completed", HttpMethod.POST, pathParams, localVarQueryParams, completeRecurringMeetingTemplateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Complete the series template
     * Completes the series template and starts the recurring meeting. This exits draft mode, initiates the first occurrence from it, and schedules the job that creates the next occurrence.  The &#x60;notify&#x60; flag sets the series-wide email notification setting (stored on the template). When &#x60;true&#x60;, the initial series invitations are sent to the template participants. &#x60;notify&#x60; must be provided explicitly.  Completing an already-completed series is rejected.  Returns the updated &#x60;RecurringMeeting&#x60; resource.
     * <p><b>200</b> - Returned when the template is completed successfully.
     * <p><b>400</b> - Returned if the required &#x60;notify&#x60; parameter is missing.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if the template could not be completed, for example because the series has no next occurrence to schedule or it has already been completed.
     * @param id Recurring meeting ID
     * @param completeRecurringMeetingTemplateRequest The completeRecurringMeetingTemplateRequest parameter
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingModel completeRecurringMeetingTemplate(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable CompleteRecurringMeetingTemplateRequest completeRecurringMeetingTemplateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return completeRecurringMeetingTemplateRequestCreation(id, completeRecurringMeetingTemplateRequest).body(localVarReturnType);
    }

    /**
     * Complete the series template
     * Completes the series template and starts the recurring meeting. This exits draft mode, initiates the first occurrence from it, and schedules the job that creates the next occurrence.  The &#x60;notify&#x60; flag sets the series-wide email notification setting (stored on the template). When &#x60;true&#x60;, the initial series invitations are sent to the template participants. &#x60;notify&#x60; must be provided explicitly.  Completing an already-completed series is rejected.  Returns the updated &#x60;RecurringMeeting&#x60; resource.
     * <p><b>200</b> - Returned when the template is completed successfully.
     * <p><b>400</b> - Returned if the required &#x60;notify&#x60; parameter is missing.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if the template could not be completed, for example because the series has no next occurrence to schedule or it has already been completed.
     * @param id Recurring meeting ID
     * @param completeRecurringMeetingTemplateRequest The completeRecurringMeetingTemplateRequest parameter
     * @return ResponseEntity&lt;RecurringMeetingModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingModel> completeRecurringMeetingTemplateWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable CompleteRecurringMeetingTemplateRequest completeRecurringMeetingTemplateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return completeRecurringMeetingTemplateRequestCreation(id, completeRecurringMeetingTemplateRequest).toEntity(localVarReturnType);
    }

    /**
     * Complete the series template
     * Completes the series template and starts the recurring meeting. This exits draft mode, initiates the first occurrence from it, and schedules the job that creates the next occurrence.  The &#x60;notify&#x60; flag sets the series-wide email notification setting (stored on the template). When &#x60;true&#x60;, the initial series invitations are sent to the template participants. &#x60;notify&#x60; must be provided explicitly.  Completing an already-completed series is rejected.  Returns the updated &#x60;RecurringMeeting&#x60; resource.
     * <p><b>200</b> - Returned when the template is completed successfully.
     * <p><b>400</b> - Returned if the required &#x60;notify&#x60; parameter is missing.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if the template could not be completed, for example because the series has no next occurrence to schedule or it has already been completed.
     * @param id Recurring meeting ID
     * @param completeRecurringMeetingTemplateRequest The completeRecurringMeetingTemplateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec completeRecurringMeetingTemplateWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable CompleteRecurringMeetingTemplateRequest completeRecurringMeetingTemplateRequest) throws RestClientResponseException {
        return completeRecurringMeetingTemplateRequestCreation(id, completeRecurringMeetingTemplateRequest);
    }

    /**
     * Create recurring meeting
     * Creates a new recurring meeting series applying the attributes provided in the body. A template meeting is automatically created alongside the recurring meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in the given project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param recurringMeetingWriteModel The recurringMeetingWriteModel parameter
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createRecurringMeetingRequestCreation(@jakarta.annotation.Nullable RecurringMeetingWriteModel recurringMeetingWriteModel) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings", HttpMethod.POST, pathParams, localVarQueryParams, recurringMeetingWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create recurring meeting
     * Creates a new recurring meeting series applying the attributes provided in the body. A template meeting is automatically created alongside the recurring meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in the given project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param recurringMeetingWriteModel The recurringMeetingWriteModel parameter
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingModel createRecurringMeeting(@jakarta.annotation.Nullable RecurringMeetingWriteModel recurringMeetingWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createRecurringMeetingRequestCreation(recurringMeetingWriteModel).body(localVarReturnType);
    }

    /**
     * Create recurring meeting
     * Creates a new recurring meeting series applying the attributes provided in the body. A template meeting is automatically created alongside the recurring meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in the given project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param recurringMeetingWriteModel The recurringMeetingWriteModel parameter
     * @return ResponseEntity&lt;RecurringMeetingModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingModel> createRecurringMeetingWithHttpInfo(@jakarta.annotation.Nullable RecurringMeetingWriteModel recurringMeetingWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createRecurringMeetingRequestCreation(recurringMeetingWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create recurring meeting
     * Creates a new recurring meeting series applying the attributes provided in the body. A template meeting is automatically created alongside the recurring meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in the given project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param recurringMeetingWriteModel The recurringMeetingWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createRecurringMeetingWithResponseSpec(@jakarta.annotation.Nullable RecurringMeetingWriteModel recurringMeetingWriteModel) throws RestClientResponseException {
        return createRecurringMeetingRequestCreation(recurringMeetingWriteModel);
    }

    /**
     * Instantiate an occurrence
     * Instantiates the occurrence at the given &#x60;start_time&#x60; by creating a real meeting from the recurring meeting&#39;s template. Returns the newly created &#x60;Meeting&#x60; resource.  If a previously cancelled occurrence exists for this &#x60;start_time&#x60;, it is restored, and if &#x60;notify&#x60; is set to true, its participants are notified via email.
     * <p><b>201</b> - Created — the occurrence was successfully instantiated as a meeting.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Recurring meeting identifier
     * @param startTime The ISO 8601 start time of the occurrence to instantiate (e.g. &#x60;2025-06-02T10:00:00Z&#x60;)
     * @return MeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createRecurringMeetingOccurrenceRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull OffsetDateTime startTime) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createRecurringMeetingOccurrence", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'startTime' is set
        if (startTime == null) {
            throw new RestClientResponseException("Missing the required parameter 'startTime' when calling createRecurringMeetingOccurrence", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("start_time", startTime);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}/occurrences/{start_time}/init", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Instantiate an occurrence
     * Instantiates the occurrence at the given &#x60;start_time&#x60; by creating a real meeting from the recurring meeting&#39;s template. Returns the newly created &#x60;Meeting&#x60; resource.  If a previously cancelled occurrence exists for this &#x60;start_time&#x60;, it is restored, and if &#x60;notify&#x60; is set to true, its participants are notified via email.
     * <p><b>201</b> - Created — the occurrence was successfully instantiated as a meeting.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Recurring meeting identifier
     * @param startTime The ISO 8601 start time of the occurrence to instantiate (e.g. &#x60;2025-06-02T10:00:00Z&#x60;)
     * @return MeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingModel createRecurringMeetingOccurrence(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull OffsetDateTime startTime) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createRecurringMeetingOccurrenceRequestCreation(id, startTime).body(localVarReturnType);
    }

    /**
     * Instantiate an occurrence
     * Instantiates the occurrence at the given &#x60;start_time&#x60; by creating a real meeting from the recurring meeting&#39;s template. Returns the newly created &#x60;Meeting&#x60; resource.  If a previously cancelled occurrence exists for this &#x60;start_time&#x60;, it is restored, and if &#x60;notify&#x60; is set to true, its participants are notified via email.
     * <p><b>201</b> - Created — the occurrence was successfully instantiated as a meeting.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Recurring meeting identifier
     * @param startTime The ISO 8601 start time of the occurrence to instantiate (e.g. &#x60;2025-06-02T10:00:00Z&#x60;)
     * @return ResponseEntity&lt;MeetingModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingModel> createRecurringMeetingOccurrenceWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull OffsetDateTime startTime) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createRecurringMeetingOccurrenceRequestCreation(id, startTime).toEntity(localVarReturnType);
    }

    /**
     * Instantiate an occurrence
     * Instantiates the occurrence at the given &#x60;start_time&#x60; by creating a real meeting from the recurring meeting&#39;s template. Returns the newly created &#x60;Meeting&#x60; resource.  If a previously cancelled occurrence exists for this &#x60;start_time&#x60;, it is restored, and if &#x60;notify&#x60; is set to true, its participants are notified via email.
     * <p><b>201</b> - Created — the occurrence was successfully instantiated as a meeting.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Recurring meeting identifier
     * @param startTime The ISO 8601 start time of the occurrence to instantiate (e.g. &#x60;2025-06-02T10:00:00Z&#x60;)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createRecurringMeetingOccurrenceWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull OffsetDateTime startTime) throws RestClientResponseException {
        return createRecurringMeetingOccurrenceRequestCreation(id, startTime);
    }

    /**
     * Delete recurring meeting
     * Deletes the entire recurring meeting series, including the template meeting and all scheduled occurrences that have not yet been instantiated. Already-created meeting instances are not deleted.
     * <p><b>204</b> - Returned if the recurring meeting was successfully deleted.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete meetings
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Recurring meeting identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteRecurringMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteRecurringMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete recurring meeting
     * Deletes the entire recurring meeting series, including the template meeting and all scheduled occurrences that have not yet been instantiated. Already-created meeting instances are not deleted.
     * <p><b>204</b> - Returned if the recurring meeting was successfully deleted.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete meetings
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Recurring meeting identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteRecurringMeeting(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteRecurringMeetingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete recurring meeting
     * Deletes the entire recurring meeting series, including the template meeting and all scheduled occurrences that have not yet been instantiated. Already-created meeting instances are not deleted.
     * <p><b>204</b> - Returned if the recurring meeting was successfully deleted.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete meetings
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Recurring meeting identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteRecurringMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteRecurringMeetingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete recurring meeting
     * Deletes the entire recurring meeting series, including the template meeting and all scheduled occurrences that have not yet been instantiated. Already-created meeting instances are not deleted.
     * <p><b>204</b> - Returned if the recurring meeting was successfully deleted.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete meetings
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Recurring meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteRecurringMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteRecurringMeetingRequestCreation(id);
    }

    /**
     * End a meeting series
     * Ends the recurring meeting series. The series end date is set so that no further occurrences are scheduled, any future already-instantiated occurrences are cancelled and removed, and participants receive cancellation and \&quot;series ended\&quot; notification emails if &#x60;notify&#x60; is set to true for the series.  Ending a series before its first occurrence is rejected.  Returns the updated &#x60;RecurringMeeting&#x60; resource.
     * <p><b>200</b> - Returned if the series was ended successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if the series could not be ended, for example because it has not started yet.
     * @param id Recurring meeting ID
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec endRecurringMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling endRecurringMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}/end", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * End a meeting series
     * Ends the recurring meeting series. The series end date is set so that no further occurrences are scheduled, any future already-instantiated occurrences are cancelled and removed, and participants receive cancellation and \&quot;series ended\&quot; notification emails if &#x60;notify&#x60; is set to true for the series.  Ending a series before its first occurrence is rejected.  Returns the updated &#x60;RecurringMeeting&#x60; resource.
     * <p><b>200</b> - Returned if the series was ended successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if the series could not be ended, for example because it has not started yet.
     * @param id Recurring meeting ID
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingModel endRecurringMeeting(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return endRecurringMeetingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * End a meeting series
     * Ends the recurring meeting series. The series end date is set so that no further occurrences are scheduled, any future already-instantiated occurrences are cancelled and removed, and participants receive cancellation and \&quot;series ended\&quot; notification emails if &#x60;notify&#x60; is set to true for the series.  Ending a series before its first occurrence is rejected.  Returns the updated &#x60;RecurringMeeting&#x60; resource.
     * <p><b>200</b> - Returned if the series was ended successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if the series could not be ended, for example because it has not started yet.
     * @param id Recurring meeting ID
     * @return ResponseEntity&lt;RecurringMeetingModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingModel> endRecurringMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return endRecurringMeetingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * End a meeting series
     * Ends the recurring meeting series. The series end date is set so that no further occurrences are scheduled, any future already-instantiated occurrences are cancelled and removed, and participants receive cancellation and \&quot;series ended\&quot; notification emails if &#x60;notify&#x60; is set to true for the series.  Ending a series before its first occurrence is rejected.  Returns the updated &#x60;RecurringMeeting&#x60; resource.
     * <p><b>200</b> - Returned if the series was ended successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>422</b> - Returned if the series could not be ended, for example because it has not started yet.
     * @param id Recurring meeting ID
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec endRecurringMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return endRecurringMeetingRequestCreation(id);
    }

    /**
     * Get a recurring meeting
     * Retrieve an individual recurring meeting series as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRecurringMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getRecurringMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a recurring meeting
     * Retrieve an individual recurring meeting series as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingModel getRecurringMeeting(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRecurringMeetingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a recurring meeting
     * Retrieve an individual recurring meeting series as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return ResponseEntity&lt;RecurringMeetingModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingModel> getRecurringMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRecurringMeetingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a recurring meeting
     * Retrieve an individual recurring meeting series as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRecurringMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getRecurringMeetingRequestCreation(id);
    }

    /**
     * List cancelled occurrences of a recurring meeting
     * Returns a collection of cancelled occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records with &#x60;cancelled: true&#x60; are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return RecurringMeetingOccurrenceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listRecurringMeetingOccurrencesCancelledRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listRecurringMeetingOccurrencesCancelled", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}/occurrences/cancelled", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List cancelled occurrences of a recurring meeting
     * Returns a collection of cancelled occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records with &#x60;cancelled: true&#x60; are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return RecurringMeetingOccurrenceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingOccurrenceCollectionModel listRecurringMeetingOccurrencesCancelled(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingOccurrencesCancelledRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List cancelled occurrences of a recurring meeting
     * Returns a collection of cancelled occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records with &#x60;cancelled: true&#x60; are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return ResponseEntity&lt;RecurringMeetingOccurrenceCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingOccurrenceCollectionModel> listRecurringMeetingOccurrencesCancelledWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingOccurrencesCancelledRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List cancelled occurrences of a recurring meeting
     * Returns a collection of cancelled occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records with &#x60;cancelled: true&#x60; are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listRecurringMeetingOccurrencesCancelledWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listRecurringMeetingOccurrencesCancelledRequestCreation(id);
    }

    /**
     * List open occurrences of a recurring meeting
     * Returns a collection of open (instantiated, non-cancelled) occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records that have an associated meeting and are not cancelled are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return RecurringMeetingOccurrenceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listRecurringMeetingOccurrencesOpenRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listRecurringMeetingOccurrencesOpen", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}/occurrences/open", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List open occurrences of a recurring meeting
     * Returns a collection of open (instantiated, non-cancelled) occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records that have an associated meeting and are not cancelled are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return RecurringMeetingOccurrenceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingOccurrenceCollectionModel listRecurringMeetingOccurrencesOpen(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingOccurrencesOpenRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List open occurrences of a recurring meeting
     * Returns a collection of open (instantiated, non-cancelled) occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records that have an associated meeting and are not cancelled are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return ResponseEntity&lt;RecurringMeetingOccurrenceCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingOccurrenceCollectionModel> listRecurringMeetingOccurrencesOpenWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingOccurrencesOpenRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List open occurrences of a recurring meeting
     * Returns a collection of open (instantiated, non-cancelled) occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records that have an associated meeting and are not cancelled are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listRecurringMeetingOccurrencesOpenWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listRecurringMeetingOccurrencesOpenRequestCreation(id);
    }

    /**
     * List past occurrences of a recurring meeting
     * Returns a collection of past, non-cancelled occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records with a &#x60;start_time&#x60; in the past are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return RecurringMeetingOccurrenceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listRecurringMeetingOccurrencesPastRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listRecurringMeetingOccurrencesPast", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}/occurrences/past", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List past occurrences of a recurring meeting
     * Returns a collection of past, non-cancelled occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records with a &#x60;start_time&#x60; in the past are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return RecurringMeetingOccurrenceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingOccurrenceCollectionModel listRecurringMeetingOccurrencesPast(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingOccurrencesPastRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List past occurrences of a recurring meeting
     * Returns a collection of past, non-cancelled occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records with a &#x60;start_time&#x60; in the past are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return ResponseEntity&lt;RecurringMeetingOccurrenceCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingOccurrenceCollectionModel> listRecurringMeetingOccurrencesPastWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingOccurrencesPastRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List past occurrences of a recurring meeting
     * Returns a collection of past, non-cancelled occurrences for the given recurring meeting. Only persisted &#x60;ScheduledMeeting&#x60; records with a &#x60;start_time&#x60; in the past are returned.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listRecurringMeetingOccurrencesPastWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listRecurringMeetingOccurrencesPastRequestCreation(id);
    }

    /**
     * List upcoming occurrences of a recurring meeting
     * Returns a collection of upcoming occurrences for the given recurring meeting. The list merges computed occurrences from the recurrence schedule (via IceCube) with any persisted &#x60;ScheduledMeeting&#x60; records, including already-instantiated open meetings.  Occurrences are ordered chronologically and limited by the &#x60;limit&#x60; parameter.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @param limit Maximum number of occurrences to return. Defaults to 20.
     * @return RecurringMeetingOccurrenceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listRecurringMeetingOccurrencesUpcomingRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer limit) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listRecurringMeetingOccurrencesUpcoming", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}/occurrences/upcoming", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List upcoming occurrences of a recurring meeting
     * Returns a collection of upcoming occurrences for the given recurring meeting. The list merges computed occurrences from the recurrence schedule (via IceCube) with any persisted &#x60;ScheduledMeeting&#x60; records, including already-instantiated open meetings.  Occurrences are ordered chronologically and limited by the &#x60;limit&#x60; parameter.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @param limit Maximum number of occurrences to return. Defaults to 20.
     * @return RecurringMeetingOccurrenceCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingOccurrenceCollectionModel listRecurringMeetingOccurrencesUpcoming(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer limit) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingOccurrencesUpcomingRequestCreation(id, limit).body(localVarReturnType);
    }

    /**
     * List upcoming occurrences of a recurring meeting
     * Returns a collection of upcoming occurrences for the given recurring meeting. The list merges computed occurrences from the recurrence schedule (via IceCube) with any persisted &#x60;ScheduledMeeting&#x60; records, including already-instantiated open meetings.  Occurrences are ordered chronologically and limited by the &#x60;limit&#x60; parameter.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @param limit Maximum number of occurrences to return. Defaults to 20.
     * @return ResponseEntity&lt;RecurringMeetingOccurrenceCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingOccurrenceCollectionModel> listRecurringMeetingOccurrencesUpcomingWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer limit) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingOccurrenceCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingOccurrencesUpcomingRequestCreation(id, limit).toEntity(localVarReturnType);
    }

    /**
     * List upcoming occurrences of a recurring meeting
     * Returns a collection of upcoming occurrences for the given recurring meeting. The list merges computed occurrences from the recurrence schedule (via IceCube) with any persisted &#x60;ScheduledMeeting&#x60; records, including already-instantiated open meetings.  Occurrences are ordered chronologically and limited by the &#x60;limit&#x60; parameter.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * @param id Recurring meeting identifier
     * @param limit Maximum number of occurrences to return. Defaults to 20.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listRecurringMeetingOccurrencesUpcomingWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer limit) throws RestClientResponseException {
        return listRecurringMeetingOccurrencesUpcomingRequestCreation(id, limit);
    }

    /**
     * List all visible recurring meetings
     * Retrieve a paginated collection of recurring meetings visible to the authenticated user.
     * <p><b>200</b> - OK
     * @return RecurringMeetingCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listRecurringMeetingsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all visible recurring meetings
     * Retrieve a paginated collection of recurring meetings visible to the authenticated user.
     * <p><b>200</b> - OK
     * @return RecurringMeetingCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingCollectionModel listRecurringMeetings() throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all visible recurring meetings
     * Retrieve a paginated collection of recurring meetings visible to the authenticated user.
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;RecurringMeetingCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingCollectionModel> listRecurringMeetingsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRecurringMeetingsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all visible recurring meetings
     * Retrieve a paginated collection of recurring meetings visible to the authenticated user.
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listRecurringMeetingsWithResponseSpec() throws RestClientResponseException {
        return listRecurringMeetingsRequestCreation();
    }

    /**
     * Update recurring meeting
     * Updates the given recurring meeting series by applying the attributes provided in the body. If schedule-related attributes (frequency, interval, startTime, endAfter, etc.) are changed, the meeting schedule is automatically recalculated.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Recurring meeting identifier
     * @param recurringMeetingWriteModel The recurringMeetingWriteModel parameter
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateRecurringMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RecurringMeetingWriteModel recurringMeetingWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateRecurringMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/recurring_meetings/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, recurringMeetingWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update recurring meeting
     * Updates the given recurring meeting series by applying the attributes provided in the body. If schedule-related attributes (frequency, interval, startTime, endAfter, etc.) are changed, the meeting schedule is automatically recalculated.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Recurring meeting identifier
     * @param recurringMeetingWriteModel The recurringMeetingWriteModel parameter
     * @return RecurringMeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RecurringMeetingModel updateRecurringMeeting(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RecurringMeetingWriteModel recurringMeetingWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateRecurringMeetingRequestCreation(id, recurringMeetingWriteModel).body(localVarReturnType);
    }

    /**
     * Update recurring meeting
     * Updates the given recurring meeting series by applying the attributes provided in the body. If schedule-related attributes (frequency, interval, startTime, endAfter, etc.) are changed, the meeting schedule is automatically recalculated.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Recurring meeting identifier
     * @param recurringMeetingWriteModel The recurringMeetingWriteModel parameter
     * @return ResponseEntity&lt;RecurringMeetingModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RecurringMeetingModel> updateRecurringMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RecurringMeetingWriteModel recurringMeetingWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<RecurringMeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateRecurringMeetingRequestCreation(id, recurringMeetingWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update recurring meeting
     * Updates the given recurring meeting series by applying the attributes provided in the body. If schedule-related attributes (frequency, interval, startTime, endAfter, etc.) are changed, the meeting schedule is automatically recalculated.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the recurring meeting&#39;s project
     * <p><b>404</b> - Returned if the recurring meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the recurring meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Recurring meeting identifier
     * @param recurringMeetingWriteModel The recurringMeetingWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateRecurringMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RecurringMeetingWriteModel recurringMeetingWriteModel) throws RestClientResponseException {
        return updateRecurringMeetingRequestCreation(id, recurringMeetingWriteModel);
    }
}
