package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.AttachmentUploadModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.MeetingAgendaItemCollectionModel;
import ai.gebo.openproject.client.model.MeetingAgendaItemModel;
import ai.gebo.openproject.client.model.MeetingAgendaItemWriteModel;
import ai.gebo.openproject.client.model.MeetingCollectionModel;
import ai.gebo.openproject.client.model.MeetingModel;
import ai.gebo.openproject.client.model.MeetingOutcomeCollectionModel;
import ai.gebo.openproject.client.model.MeetingOutcomeModel;
import ai.gebo.openproject.client.model.MeetingOutcomeWriteModel;
import ai.gebo.openproject.client.model.MeetingSectionCollectionModel;
import ai.gebo.openproject.client.model.MeetingSectionModel;
import ai.gebo.openproject.client.model.MeetingSectionWriteModel;
import ai.gebo.openproject.client.model.MeetingWriteModel;

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
public class MeetingsApi {
    private ApiClient apiClient;

    public MeetingsApi() {
        this(new ApiClient());
    }

    public MeetingsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create meeting
     * Creates a new meeting applying the attributes provided in the body.  You can use the form and schema to retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingWriteModel The meetingWriteModel parameter
     * @return MeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createMeetingRequestCreation(@jakarta.annotation.Nullable MeetingWriteModel meetingWriteModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings", HttpMethod.POST, pathParams, localVarQueryParams, meetingWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create meeting
     * Creates a new meeting applying the attributes provided in the body.  You can use the form and schema to retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingWriteModel The meetingWriteModel parameter
     * @return MeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingModel createMeeting(@jakarta.annotation.Nullable MeetingWriteModel meetingWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMeetingRequestCreation(meetingWriteModel).body(localVarReturnType);
    }

    /**
     * Create meeting
     * Creates a new meeting applying the attributes provided in the body.  You can use the form and schema to retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingWriteModel The meetingWriteModel parameter
     * @return ResponseEntity&lt;MeetingModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingModel> createMeetingWithHttpInfo(@jakarta.annotation.Nullable MeetingWriteModel meetingWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMeetingRequestCreation(meetingWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create meeting
     * Creates a new meeting applying the attributes provided in the body.  You can use the form and schema to retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingWriteModel The meetingWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createMeetingWithResponseSpec(@jakarta.annotation.Nullable MeetingWriteModel meetingWriteModel) throws RestClientResponseException {
        return createMeetingRequestCreation(meetingWriteModel);
    }

    /**
     * Create meeting agenda item
     * Creates a new agenda item. The request body must link the meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the linked meeting does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingAgendaItemWriteModel The meetingAgendaItemWriteModel parameter
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createMeetingAgendaItemRequestCreation(@jakarta.annotation.Nullable MeetingAgendaItemWriteModel meetingAgendaItemWriteModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_agenda_items", HttpMethod.POST, pathParams, localVarQueryParams, meetingAgendaItemWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create meeting agenda item
     * Creates a new agenda item. The request body must link the meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the linked meeting does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingAgendaItemWriteModel The meetingAgendaItemWriteModel parameter
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemModel createMeetingAgendaItem(@jakarta.annotation.Nullable MeetingAgendaItemWriteModel meetingAgendaItemWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMeetingAgendaItemRequestCreation(meetingAgendaItemWriteModel).body(localVarReturnType);
    }

    /**
     * Create meeting agenda item
     * Creates a new agenda item. The request body must link the meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the linked meeting does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingAgendaItemWriteModel The meetingAgendaItemWriteModel parameter
     * @return ResponseEntity&lt;MeetingAgendaItemModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemModel> createMeetingAgendaItemWithHttpInfo(@jakarta.annotation.Nullable MeetingAgendaItemWriteModel meetingAgendaItemWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMeetingAgendaItemRequestCreation(meetingAgendaItemWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create meeting agenda item
     * Creates a new agenda item. The request body must link the meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the linked meeting does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingAgendaItemWriteModel The meetingAgendaItemWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createMeetingAgendaItemWithResponseSpec(@jakarta.annotation.Nullable MeetingAgendaItemWriteModel meetingAgendaItemWriteModel) throws RestClientResponseException {
        return createMeetingAgendaItemRequestCreation(meetingAgendaItemWriteModel);
    }

    /**
     * Create meeting outcome
     * Creates a new meeting outcome. The request body must link the agenda item.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the linked agenda item does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingOutcomeWriteModel The meetingOutcomeWriteModel parameter
     * @return MeetingOutcomeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createMeetingOutcomeRequestCreation(@jakarta.annotation.Nullable MeetingOutcomeWriteModel meetingOutcomeWriteModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_outcomes", HttpMethod.POST, pathParams, localVarQueryParams, meetingOutcomeWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create meeting outcome
     * Creates a new meeting outcome. The request body must link the agenda item.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the linked agenda item does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingOutcomeWriteModel The meetingOutcomeWriteModel parameter
     * @return MeetingOutcomeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingOutcomeModel createMeetingOutcome(@jakarta.annotation.Nullable MeetingOutcomeWriteModel meetingOutcomeWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMeetingOutcomeRequestCreation(meetingOutcomeWriteModel).body(localVarReturnType);
    }

    /**
     * Create meeting outcome
     * Creates a new meeting outcome. The request body must link the agenda item.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the linked agenda item does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingOutcomeWriteModel The meetingOutcomeWriteModel parameter
     * @return ResponseEntity&lt;MeetingOutcomeModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingOutcomeModel> createMeetingOutcomeWithHttpInfo(@jakarta.annotation.Nullable MeetingOutcomeWriteModel meetingOutcomeWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMeetingOutcomeRequestCreation(meetingOutcomeWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create meeting outcome
     * Creates a new meeting outcome. The request body must link the agenda item.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the linked agenda item does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingOutcomeWriteModel The meetingOutcomeWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createMeetingOutcomeWithResponseSpec(@jakarta.annotation.Nullable MeetingOutcomeWriteModel meetingOutcomeWriteModel) throws RestClientResponseException {
        return createMeetingOutcomeRequestCreation(meetingOutcomeWriteModel);
    }

    /**
     * Create meeting section
     * Creates a new section. The request body must link the meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the linked meeting does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingSectionWriteModel The meetingSectionWriteModel parameter
     * @return MeetingSectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createMeetingSectionRequestCreation(@jakarta.annotation.Nullable MeetingSectionWriteModel meetingSectionWriteModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_sections", HttpMethod.POST, pathParams, localVarQueryParams, meetingSectionWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create meeting section
     * Creates a new section. The request body must link the meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the linked meeting does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingSectionWriteModel The meetingSectionWriteModel parameter
     * @return MeetingSectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingSectionModel createMeetingSection(@jakarta.annotation.Nullable MeetingSectionWriteModel meetingSectionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMeetingSectionRequestCreation(meetingSectionWriteModel).body(localVarReturnType);
    }

    /**
     * Create meeting section
     * Creates a new section. The request body must link the meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the linked meeting does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingSectionWriteModel The meetingSectionWriteModel parameter
     * @return ResponseEntity&lt;MeetingSectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingSectionModel> createMeetingSectionWithHttpInfo(@jakarta.annotation.Nullable MeetingSectionWriteModel meetingSectionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMeetingSectionRequestCreation(meetingSectionWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create meeting section
     * Creates a new section. The request body must link the meeting.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the linked meeting does not exist or the client does not have sufficient permissions to see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param meetingSectionWriteModel The meetingSectionWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createMeetingSectionWithResponseSpec(@jakarta.annotation.Nullable MeetingSectionWriteModel meetingSectionWriteModel) throws RestClientResponseException {
        return createMeetingSectionRequestCreation(meetingSectionWriteModel);
    }

    /**
     * Delete meeting
     * Deletes the meeting. Participants will be notified of the cancellation.
     * <p><b>204</b> - Returned if the meeting was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete meetings
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Meeting identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/meetings/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete meeting
     * Deletes the meeting. Participants will be notified of the cancellation.
     * <p><b>204</b> - Returned if the meeting was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete meetings
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Meeting identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteMeeting(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteMeetingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete meeting
     * Deletes the meeting. Participants will be notified of the cancellation.
     * <p><b>204</b> - Returned if the meeting was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete meetings
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Meeting identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteMeetingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete meeting
     * Deletes the meeting. Participants will be notified of the cancellation.
     * <p><b>204</b> - Returned if the meeting was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete meetings
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteMeetingRequestCreation(id);
    }

    /**
     * Delete a meeting agenda item
     * Deletes the agenda item.
     * <p><b>204</b> - Returned if the agenda item was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * @param id Agenda item identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteMeetingAgendaItemRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteMeetingAgendaItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_agenda_items/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a meeting agenda item
     * Deletes the agenda item.
     * <p><b>204</b> - Returned if the agenda item was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * @param id Agenda item identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteMeetingAgendaItem(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteMeetingAgendaItemRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a meeting agenda item
     * Deletes the agenda item.
     * <p><b>204</b> - Returned if the agenda item was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * @param id Agenda item identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteMeetingAgendaItemWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteMeetingAgendaItemRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a meeting agenda item
     * Deletes the agenda item.
     * <p><b>204</b> - Returned if the agenda item was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * @param id Agenda item identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteMeetingAgendaItemWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteMeetingAgendaItemRequestCreation(id);
    }

    /**
     * Delete a meeting outcome
     * Deletes the outcome.
     * <p><b>204</b> - Returned if the outcome was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * @param id Outcome identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteMeetingOutcomeRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteMeetingOutcome", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_outcomes/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a meeting outcome
     * Deletes the outcome.
     * <p><b>204</b> - Returned if the outcome was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * @param id Outcome identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteMeetingOutcome(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteMeetingOutcomeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a meeting outcome
     * Deletes the outcome.
     * <p><b>204</b> - Returned if the outcome was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * @param id Outcome identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteMeetingOutcomeWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteMeetingOutcomeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a meeting outcome
     * Deletes the outcome.
     * <p><b>204</b> - Returned if the outcome was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * @param id Outcome identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteMeetingOutcomeWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteMeetingOutcomeRequestCreation(id);
    }

    /**
     * Delete a meeting section
     * Deletes the meeting section and all its agenda items.  The backlog section cannot be deleted and will return &#x60;422 Unprocessable Entity&#x60;.
     * <p><b>204</b> - Returned if the section was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * @param id Section identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteMeetingSectionRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteMeetingSection", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_sections/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a meeting section
     * Deletes the meeting section and all its agenda items.  The backlog section cannot be deleted and will return &#x60;422 Unprocessable Entity&#x60;.
     * <p><b>204</b> - Returned if the section was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * @param id Section identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteMeetingSection(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteMeetingSectionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a meeting section
     * Deletes the meeting section and all its agenda items.  The backlog section cannot be deleted and will return &#x60;422 Unprocessable Entity&#x60;.
     * <p><b>204</b> - Returned if the section was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * @param id Section identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteMeetingSectionWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteMeetingSectionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a meeting section
     * Deletes the meeting section and all its agenda items.  The backlog section cannot be deleted and will return &#x60;422 Unprocessable Entity&#x60;.
     * <p><b>204</b> - Returned if the section was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * @param id Section identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteMeetingSectionWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteMeetingSectionRequestCreation(id);
    }

    /**
     * Toggle an agenda item downvote
     * Toggles the current user&#39;s downvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downvoteMeetingAgendaItemRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling downvoteMeetingAgendaItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_agenda_items/{id}/downvote", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Toggle an agenda item downvote
     * Toggles the current user&#39;s downvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemModel downvoteMeetingAgendaItem(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downvoteMeetingAgendaItemRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Toggle an agenda item downvote
     * Toggles the current user&#39;s downvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param id Agenda item identifier
     * @return ResponseEntity&lt;MeetingAgendaItemModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemModel> downvoteMeetingAgendaItemWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downvoteMeetingAgendaItemRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Toggle an agenda item downvote
     * Toggles the current user&#39;s downvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param id Agenda item identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downvoteMeetingAgendaItemWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return downvoteMeetingAgendaItemRequestCreation(id);
    }

    /**
     * Toggle an agenda item downvote
     * Toggles the current user&#39;s downvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downvoteMeetingAgendaItemByMeetingRequestCreation(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new RestClientResponseException("Missing the required parameter 'meetingId' when calling downvoteMeetingAgendaItemByMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling downvoteMeetingAgendaItemByMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("meeting_id", meetingId);
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

        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{meeting_id}/agenda_items/{id}/downvote", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Toggle an agenda item downvote
     * Toggles the current user&#39;s downvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemModel downvoteMeetingAgendaItemByMeeting(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downvoteMeetingAgendaItemByMeetingRequestCreation(meetingId, id).body(localVarReturnType);
    }

    /**
     * Toggle an agenda item downvote
     * Toggles the current user&#39;s downvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return ResponseEntity&lt;MeetingAgendaItemModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemModel> downvoteMeetingAgendaItemByMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downvoteMeetingAgendaItemByMeetingRequestCreation(meetingId, id).toEntity(localVarReturnType);
    }

    /**
     * Toggle an agenda item downvote
     * Toggles the current user&#39;s downvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downvoteMeetingAgendaItemByMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return downvoteMeetingAgendaItemByMeetingRequestCreation(meetingId, id);
    }

    /**
     * Get a meeting
     * Retrieve an individual meeting as identified by the id parameter
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * @param id Meeting identifier
     * @return MeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a meeting
     * Retrieve an individual meeting as identified by the id parameter
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * @param id Meeting identifier
     * @return MeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingModel getMeeting(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a meeting
     * Retrieve an individual meeting as identified by the id parameter
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * @param id Meeting identifier
     * @return ResponseEntity&lt;MeetingModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingModel> getMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a meeting
     * Retrieve an individual meeting as identified by the id parameter
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * @param id Meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getMeetingRequestCreation(id);
    }

    /**
     * Get a meeting agenda item
     * Retrieve an individual agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMeetingAgendaItemRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getMeetingAgendaItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_agenda_items/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a meeting agenda item
     * Retrieve an individual agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemModel getMeetingAgendaItem(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingAgendaItemRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a meeting agenda item
     * Retrieve an individual agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * @param id Agenda item identifier
     * @return ResponseEntity&lt;MeetingAgendaItemModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemModel> getMeetingAgendaItemWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingAgendaItemRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a meeting agenda item
     * Retrieve an individual agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * @param id Agenda item identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMeetingAgendaItemWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getMeetingAgendaItemRequestCreation(id);
    }

    /**
     * Get a meeting agenda item
     * Retrieve an individual agenda item of a meeting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMeetingAgendaItemByMeetingRequestCreation(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new RestClientResponseException("Missing the required parameter 'meetingId' when calling getMeetingAgendaItemByMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getMeetingAgendaItemByMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("meeting_id", meetingId);
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

        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{meeting_id}/agenda_items/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a meeting agenda item
     * Retrieve an individual agenda item of a meeting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemModel getMeetingAgendaItemByMeeting(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingAgendaItemByMeetingRequestCreation(meetingId, id).body(localVarReturnType);
    }

    /**
     * Get a meeting agenda item
     * Retrieve an individual agenda item of a meeting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return ResponseEntity&lt;MeetingAgendaItemModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemModel> getMeetingAgendaItemByMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingAgendaItemByMeetingRequestCreation(meetingId, id).toEntity(localVarReturnType);
    }

    /**
     * Get a meeting agenda item
     * Retrieve an individual agenda item of a meeting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMeetingAgendaItemByMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getMeetingAgendaItemByMeetingRequestCreation(meetingId, id);
    }

    /**
     * Get a meeting outcome
     * Retrieve an individual meeting outcome.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * @param id Outcome identifier
     * @return MeetingOutcomeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMeetingOutcomeRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getMeetingOutcome", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_outcomes/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a meeting outcome
     * Retrieve an individual meeting outcome.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * @param id Outcome identifier
     * @return MeetingOutcomeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingOutcomeModel getMeetingOutcome(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingOutcomeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a meeting outcome
     * Retrieve an individual meeting outcome.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * @param id Outcome identifier
     * @return ResponseEntity&lt;MeetingOutcomeModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingOutcomeModel> getMeetingOutcomeWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingOutcomeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a meeting outcome
     * Retrieve an individual meeting outcome.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * @param id Outcome identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMeetingOutcomeWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getMeetingOutcomeRequestCreation(id);
    }

    /**
     * Get a meeting outcome
     * Retrieve an individual outcome of a meeting agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the outcome, agenda item, or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param agendaItemId Agenda item identifier
     * @param id Outcome identifier
     * @return MeetingOutcomeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMeetingOutcomeByAgendaItemRequestCreation(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer agendaItemId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new RestClientResponseException("Missing the required parameter 'meetingId' when calling getMeetingOutcomeByAgendaItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agendaItemId' is set
        if (agendaItemId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agendaItemId' when calling getMeetingOutcomeByAgendaItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getMeetingOutcomeByAgendaItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("meeting_id", meetingId);
        pathParams.put("agenda_item_id", agendaItemId);
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

        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{meeting_id}/agenda_items/{agenda_item_id}/outcomes/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a meeting outcome
     * Retrieve an individual outcome of a meeting agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the outcome, agenda item, or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param agendaItemId Agenda item identifier
     * @param id Outcome identifier
     * @return MeetingOutcomeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingOutcomeModel getMeetingOutcomeByAgendaItem(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer agendaItemId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingOutcomeByAgendaItemRequestCreation(meetingId, agendaItemId, id).body(localVarReturnType);
    }

    /**
     * Get a meeting outcome
     * Retrieve an individual outcome of a meeting agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the outcome, agenda item, or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param agendaItemId Agenda item identifier
     * @param id Outcome identifier
     * @return ResponseEntity&lt;MeetingOutcomeModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingOutcomeModel> getMeetingOutcomeByAgendaItemWithHttpInfo(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer agendaItemId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingOutcomeByAgendaItemRequestCreation(meetingId, agendaItemId, id).toEntity(localVarReturnType);
    }

    /**
     * Get a meeting outcome
     * Retrieve an individual outcome of a meeting agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the outcome, agenda item, or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param agendaItemId Agenda item identifier
     * @param id Outcome identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMeetingOutcomeByAgendaItemWithResponseSpec(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer agendaItemId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getMeetingOutcomeByAgendaItemRequestCreation(meetingId, agendaItemId, id);
    }

    /**
     * Get meeting schema
     * Retrieves the schema for meetings, providing information about which properties are writable, their types, and constraints.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view meetings in any project
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMeetingSchemaRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get meeting schema
     * Retrieves the schema for meetings, providing information about which properties are writable, their types, and constraints.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view meetings in any project
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getMeetingSchema() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getMeetingSchemaRequestCreation().body(localVarReturnType);
    }

    /**
     * Get meeting schema
     * Retrieves the schema for meetings, providing information about which properties are writable, their types, and constraints.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view meetings in any project
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getMeetingSchemaWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingSchemaRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get meeting schema
     * Retrieves the schema for meetings, providing information about which properties are writable, their types, and constraints.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view meetings in any project
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMeetingSchemaWithResponseSpec() throws RestClientResponseException {
        return getMeetingSchemaRequestCreation();
    }

    /**
     * Get a meeting section
     * Retrieve an individual meeting section.  The backlog section can be retrieved via this endpoint even though it is not part of the regular &#x60;meeting.sections&#x60; association. Its &#x60;_links.meeting&#x60; points to the owning meeting (the series template for recurring meeting occurrences).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * @param id Section identifier
     * @return MeetingSectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMeetingSectionRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getMeetingSection", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_sections/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a meeting section
     * Retrieve an individual meeting section.  The backlog section can be retrieved via this endpoint even though it is not part of the regular &#x60;meeting.sections&#x60; association. Its &#x60;_links.meeting&#x60; points to the owning meeting (the series template for recurring meeting occurrences).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * @param id Section identifier
     * @return MeetingSectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingSectionModel getMeetingSection(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingSectionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a meeting section
     * Retrieve an individual meeting section.  The backlog section can be retrieved via this endpoint even though it is not part of the regular &#x60;meeting.sections&#x60; association. Its &#x60;_links.meeting&#x60; points to the owning meeting (the series template for recurring meeting occurrences).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * @param id Section identifier
     * @return ResponseEntity&lt;MeetingSectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingSectionModel> getMeetingSectionWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingSectionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a meeting section
     * Retrieve an individual meeting section.  The backlog section can be retrieved via this endpoint even though it is not part of the regular &#x60;meeting.sections&#x60; association. Its &#x60;_links.meeting&#x60; points to the owning meeting (the series template for recurring meeting occurrences).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * @param id Section identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMeetingSectionWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getMeetingSectionRequestCreation(id);
    }

    /**
     * Get a meeting section
     * Retrieve an individual section of a meeting.  The backlog section can be retrieved via this nested endpoint even though it is not part of the regular &#x60;meeting.sections&#x60; association. Its &#x60;_links.self&#x60; and &#x60;_links.meeting&#x60; point to the canonical flat section resource and the owning meeting (the series template for recurring meeting occurrences).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the section or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param id Section identifier
     * @return MeetingSectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMeetingSectionByMeetingRequestCreation(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new RestClientResponseException("Missing the required parameter 'meetingId' when calling getMeetingSectionByMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getMeetingSectionByMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("meeting_id", meetingId);
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

        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{meeting_id}/sections/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a meeting section
     * Retrieve an individual section of a meeting.  The backlog section can be retrieved via this nested endpoint even though it is not part of the regular &#x60;meeting.sections&#x60; association. Its &#x60;_links.self&#x60; and &#x60;_links.meeting&#x60; point to the canonical flat section resource and the owning meeting (the series template for recurring meeting occurrences).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the section or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param id Section identifier
     * @return MeetingSectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingSectionModel getMeetingSectionByMeeting(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingSectionByMeetingRequestCreation(meetingId, id).body(localVarReturnType);
    }

    /**
     * Get a meeting section
     * Retrieve an individual section of a meeting.  The backlog section can be retrieved via this nested endpoint even though it is not part of the regular &#x60;meeting.sections&#x60; association. Its &#x60;_links.self&#x60; and &#x60;_links.meeting&#x60; point to the canonical flat section resource and the owning meeting (the series template for recurring meeting occurrences).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the section or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param id Section identifier
     * @return ResponseEntity&lt;MeetingSectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingSectionModel> getMeetingSectionByMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMeetingSectionByMeetingRequestCreation(meetingId, id).toEntity(localVarReturnType);
    }

    /**
     * Get a meeting section
     * Retrieve an individual section of a meeting.  The backlog section can be retrieved via this nested endpoint even though it is not part of the regular &#x60;meeting.sections&#x60; association. Its &#x60;_links.self&#x60; and &#x60;_links.meeting&#x60; point to the canonical flat section resource and the owning meeting (the series template for recurring meeting occurrences).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the section or meeting does not exist or the client does not have sufficient permissions.
     * @param meetingId Meeting identifier
     * @param id Section identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMeetingSectionByMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getMeetingSectionByMeetingRequestCreation(meetingId, id);
    }

    /**
     * List meeting agenda items
     * Lists all agenda items for the given meeting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param id Meeting identifier
     * @return MeetingAgendaItemCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listMeetingAgendaItemsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listMeetingAgendaItems", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingAgendaItemCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{id}/agenda_items", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List meeting agenda items
     * Lists all agenda items for the given meeting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param id Meeting identifier
     * @return MeetingAgendaItemCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemCollectionModel listMeetingAgendaItems(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMeetingAgendaItemsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List meeting agenda items
     * Lists all agenda items for the given meeting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param id Meeting identifier
     * @return ResponseEntity&lt;MeetingAgendaItemCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemCollectionModel> listMeetingAgendaItemsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMeetingAgendaItemsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List meeting agenda items
     * Lists all agenda items for the given meeting.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param id Meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listMeetingAgendaItemsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listMeetingAgendaItemsRequestCreation(id);
    }

    /**
     * List meeting outcomes
     * Lists all outcomes for the given meeting agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item or meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param meetingId Meeting identifier
     * @param agendaItemId Agenda item identifier
     * @return MeetingOutcomeCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listMeetingOutcomesRequestCreation(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer agendaItemId) throws RestClientResponseException {
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new RestClientResponseException("Missing the required parameter 'meetingId' when calling listMeetingOutcomes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agendaItemId' is set
        if (agendaItemId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agendaItemId' when calling listMeetingOutcomes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("meeting_id", meetingId);
        pathParams.put("agenda_item_id", agendaItemId);

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

        ParameterizedTypeReference<MeetingOutcomeCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{meeting_id}/agenda_items/{agenda_item_id}/outcomes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List meeting outcomes
     * Lists all outcomes for the given meeting agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item or meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param meetingId Meeting identifier
     * @param agendaItemId Agenda item identifier
     * @return MeetingOutcomeCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingOutcomeCollectionModel listMeetingOutcomes(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer agendaItemId) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMeetingOutcomesRequestCreation(meetingId, agendaItemId).body(localVarReturnType);
    }

    /**
     * List meeting outcomes
     * Lists all outcomes for the given meeting agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item or meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param meetingId Meeting identifier
     * @param agendaItemId Agenda item identifier
     * @return ResponseEntity&lt;MeetingOutcomeCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingOutcomeCollectionModel> listMeetingOutcomesWithHttpInfo(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer agendaItemId) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMeetingOutcomesRequestCreation(meetingId, agendaItemId).toEntity(localVarReturnType);
    }

    /**
     * List meeting outcomes
     * Lists all outcomes for the given meeting agenda item.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the agenda item or meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param meetingId Meeting identifier
     * @param agendaItemId Agenda item identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listMeetingOutcomesWithResponseSpec(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer agendaItemId) throws RestClientResponseException {
        return listMeetingOutcomesRequestCreation(meetingId, agendaItemId);
    }

    /**
     * List meeting sections
     * Lists all sections for the given meeting, including the backlog section as the last element.  The backlog section is read-only and its &#x60;_links.meeting&#x60; points to the owning meeting (the series template for recurring meeting occurrences, otherwise the meeting itself).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param id Meeting identifier
     * @return MeetingSectionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listMeetingSectionsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listMeetingSections", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingSectionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{id}/sections", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List meeting sections
     * Lists all sections for the given meeting, including the backlog section as the last element.  The backlog section is read-only and its &#x60;_links.meeting&#x60; points to the owning meeting (the series template for recurring meeting occurrences, otherwise the meeting itself).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param id Meeting identifier
     * @return MeetingSectionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingSectionCollectionModel listMeetingSections(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMeetingSectionsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List meeting sections
     * Lists all sections for the given meeting, including the backlog section as the last element.  The backlog section is read-only and its &#x60;_links.meeting&#x60; points to the owning meeting (the series template for recurring meeting occurrences, otherwise the meeting itself).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param id Meeting identifier
     * @return ResponseEntity&lt;MeetingSectionCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingSectionCollectionModel> listMeetingSectionsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMeetingSectionsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List meeting sections
     * Lists all sections for the given meeting, including the backlog section as the last element.  The backlog section is read-only and its &#x60;_links.meeting&#x60; points to the owning meeting (the series template for recurring meeting occurrences, otherwise the meeting itself).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings
     * @param id Meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listMeetingSectionsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listMeetingSectionsRequestCreation(id);
    }

    /**
     * List all visible meetings
     * Retrieve a paginated collection of meetings visible to the authenticated user.
     * <p><b>200</b> - OK
     * @return MeetingCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listMeetingsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<MeetingCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all visible meetings
     * Retrieve a paginated collection of meetings visible to the authenticated user.
     * <p><b>200</b> - OK
     * @return MeetingCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingCollectionModel listMeetings() throws RestClientResponseException {
        ParameterizedTypeReference<MeetingCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMeetingsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all visible meetings
     * Retrieve a paginated collection of meetings visible to the authenticated user.
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;MeetingCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingCollectionModel> listMeetingsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<MeetingCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMeetingsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all visible meetings
     * Retrieve a paginated collection of meetings visible to the authenticated user.
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listMeetingsWithResponseSpec() throws RestClientResponseException {
        return listMeetingsRequestCreation();
    }

    /**
     * Move an agenda item to the next meeting
     * Moves the agenda item to the next available occurrence of its recurring meeting
     * <p><b>200</b> - The agenda item, now linked to the next meeting and its section
     * <p><b>403</b> - The user lacks &#x60;manage_agendas&#x60; in the source or target project
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client
     * <p><b>422</b> - The item&#39;s meeting is not recurring, is a series template, is closed, or the series has no upcoming occurrence available to move the item to
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec moveMeetingAgendaItemToNextMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling moveMeetingAgendaItemToNextMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_agenda_items/{id}/move_to_next_meeting", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Move an agenda item to the next meeting
     * Moves the agenda item to the next available occurrence of its recurring meeting
     * <p><b>200</b> - The agenda item, now linked to the next meeting and its section
     * <p><b>403</b> - The user lacks &#x60;manage_agendas&#x60; in the source or target project
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client
     * <p><b>422</b> - The item&#39;s meeting is not recurring, is a series template, is closed, or the series has no upcoming occurrence available to move the item to
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemModel moveMeetingAgendaItemToNextMeeting(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return moveMeetingAgendaItemToNextMeetingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Move an agenda item to the next meeting
     * Moves the agenda item to the next available occurrence of its recurring meeting
     * <p><b>200</b> - The agenda item, now linked to the next meeting and its section
     * <p><b>403</b> - The user lacks &#x60;manage_agendas&#x60; in the source or target project
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client
     * <p><b>422</b> - The item&#39;s meeting is not recurring, is a series template, is closed, or the series has no upcoming occurrence available to move the item to
     * @param id Agenda item identifier
     * @return ResponseEntity&lt;MeetingAgendaItemModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemModel> moveMeetingAgendaItemToNextMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return moveMeetingAgendaItemToNextMeetingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Move an agenda item to the next meeting
     * Moves the agenda item to the next available occurrence of its recurring meeting
     * <p><b>200</b> - The agenda item, now linked to the next meeting and its section
     * <p><b>403</b> - The user lacks &#x60;manage_agendas&#x60; in the source or target project
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client
     * <p><b>422</b> - The item&#39;s meeting is not recurring, is a series template, is closed, or the series has no upcoming occurrence available to move the item to
     * @param id Agenda item identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec moveMeetingAgendaItemToNextMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return moveMeetingAgendaItemToNextMeetingRequestCreation(id);
    }

    /**
     * Prepare a direct attachment upload for a meeting
     * Prepares a direct upload of an attachment for the given meeting, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this meeting as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings  *Note that you will only receive this error, if you are at least allowed to see the meeting*
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the meeting to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec prepareMeetingAttachmentUploadRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling prepareMeetingAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metadata' is set
        if (metadata == null) {
            throw new RestClientResponseException("Missing the required parameter 'metadata' when calling prepareMeetingAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("metadata", metadata);

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{id}/attachments/prepare", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a meeting
     * Prepares a direct upload of an attachment for the given meeting, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this meeting as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings  *Note that you will only receive this error, if you are at least allowed to see the meeting*
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the meeting to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentUploadModel prepareMeetingAttachmentUpload(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareMeetingAttachmentUploadRequestCreation(id, metadata).body(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a meeting
     * Prepares a direct upload of an attachment for the given meeting, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this meeting as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings  *Note that you will only receive this error, if you are at least allowed to see the meeting*
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the meeting to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseEntity&lt;AttachmentUploadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentUploadModel> prepareMeetingAttachmentUploadWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareMeetingAttachmentUploadRequestCreation(id, metadata).toEntity(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a meeting
     * Prepares a direct upload of an attachment for the given meeting, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this meeting as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings  *Note that you will only receive this error, if you are at least allowed to see the meeting*
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the meeting to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec prepareMeetingAttachmentUploadWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        return prepareMeetingAttachmentUploadRequestCreation(id, metadata);
    }

    /**
     * Update meeting
     * Updates the given meeting by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the meeting&#39;s project
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Meeting identifier
     * @param meetingWriteModel The meetingWriteModel parameter
     * @return MeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingWriteModel meetingWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, meetingWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update meeting
     * Updates the given meeting by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the meeting&#39;s project
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Meeting identifier
     * @param meetingWriteModel The meetingWriteModel parameter
     * @return MeetingModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingModel updateMeeting(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingWriteModel meetingWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMeetingRequestCreation(id, meetingWriteModel).body(localVarReturnType);
    }

    /**
     * Update meeting
     * Updates the given meeting by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the meeting&#39;s project
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Meeting identifier
     * @param meetingWriteModel The meetingWriteModel parameter
     * @return ResponseEntity&lt;MeetingModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingModel> updateMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingWriteModel meetingWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMeetingRequestCreation(id, meetingWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update meeting
     * Updates the given meeting by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the meeting&#39;s project
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Meeting identifier
     * @param meetingWriteModel The meetingWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingWriteModel meetingWriteModel) throws RestClientResponseException {
        return updateMeetingRequestCreation(id, meetingWriteModel);
    }

    /**
     * Update a meeting agenda item
     * Updates the given agenda item.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Agenda item identifier
     * @param meetingAgendaItemWriteModel The meetingAgendaItemWriteModel parameter
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateMeetingAgendaItemRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingAgendaItemWriteModel meetingAgendaItemWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateMeetingAgendaItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_agenda_items/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, meetingAgendaItemWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a meeting agenda item
     * Updates the given agenda item.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Agenda item identifier
     * @param meetingAgendaItemWriteModel The meetingAgendaItemWriteModel parameter
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemModel updateMeetingAgendaItem(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingAgendaItemWriteModel meetingAgendaItemWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMeetingAgendaItemRequestCreation(id, meetingAgendaItemWriteModel).body(localVarReturnType);
    }

    /**
     * Update a meeting agenda item
     * Updates the given agenda item.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Agenda item identifier
     * @param meetingAgendaItemWriteModel The meetingAgendaItemWriteModel parameter
     * @return ResponseEntity&lt;MeetingAgendaItemModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemModel> updateMeetingAgendaItemWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingAgendaItemWriteModel meetingAgendaItemWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMeetingAgendaItemRequestCreation(id, meetingAgendaItemWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update a meeting agenda item
     * Updates the given agenda item.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the agenda item does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Agenda item identifier
     * @param meetingAgendaItemWriteModel The meetingAgendaItemWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateMeetingAgendaItemWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingAgendaItemWriteModel meetingAgendaItemWriteModel) throws RestClientResponseException {
        return updateMeetingAgendaItemRequestCreation(id, meetingAgendaItemWriteModel);
    }

    /**
     * Update a meeting outcome
     * Updates the given meeting outcome.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Outcome identifier
     * @param meetingOutcomeWriteModel The meetingOutcomeWriteModel parameter
     * @return MeetingOutcomeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateMeetingOutcomeRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingOutcomeWriteModel meetingOutcomeWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateMeetingOutcome", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_outcomes/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, meetingOutcomeWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a meeting outcome
     * Updates the given meeting outcome.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Outcome identifier
     * @param meetingOutcomeWriteModel The meetingOutcomeWriteModel parameter
     * @return MeetingOutcomeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingOutcomeModel updateMeetingOutcome(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingOutcomeWriteModel meetingOutcomeWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMeetingOutcomeRequestCreation(id, meetingOutcomeWriteModel).body(localVarReturnType);
    }

    /**
     * Update a meeting outcome
     * Updates the given meeting outcome.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Outcome identifier
     * @param meetingOutcomeWriteModel The meetingOutcomeWriteModel parameter
     * @return ResponseEntity&lt;MeetingOutcomeModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingOutcomeModel> updateMeetingOutcomeWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingOutcomeWriteModel meetingOutcomeWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingOutcomeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMeetingOutcomeRequestCreation(id, meetingOutcomeWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update a meeting outcome
     * Updates the given meeting outcome.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage outcomes
     * <p><b>404</b> - Returned if the outcome does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Outcome identifier
     * @param meetingOutcomeWriteModel The meetingOutcomeWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateMeetingOutcomeWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingOutcomeWriteModel meetingOutcomeWriteModel) throws RestClientResponseException {
        return updateMeetingOutcomeRequestCreation(id, meetingOutcomeWriteModel);
    }

    /**
     * Update a meeting section
     * Updates the given meeting section.  The backlog section cannot be updated and will return &#x60;422 Unprocessable Entity&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Section identifier
     * @param meetingSectionWriteModel The meetingSectionWriteModel parameter
     * @return MeetingSectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateMeetingSectionRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingSectionWriteModel meetingSectionWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateMeetingSection", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_sections/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, meetingSectionWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a meeting section
     * Updates the given meeting section.  The backlog section cannot be updated and will return &#x60;422 Unprocessable Entity&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Section identifier
     * @param meetingSectionWriteModel The meetingSectionWriteModel parameter
     * @return MeetingSectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingSectionModel updateMeetingSection(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingSectionWriteModel meetingSectionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMeetingSectionRequestCreation(id, meetingSectionWriteModel).body(localVarReturnType);
    }

    /**
     * Update a meeting section
     * Updates the given meeting section.  The backlog section cannot be updated and will return &#x60;422 Unprocessable Entity&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Section identifier
     * @param meetingSectionWriteModel The meetingSectionWriteModel parameter
     * @return ResponseEntity&lt;MeetingSectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingSectionModel> updateMeetingSectionWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingSectionWriteModel meetingSectionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingSectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMeetingSectionRequestCreation(id, meetingSectionWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update a meeting section
     * Updates the given meeting section.  The backlog section cannot be updated and will return &#x60;422 Unprocessable Entity&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage agendas
     * <p><b>404</b> - Returned if the section does not exist or the client does not have sufficient permissions.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Section identifier
     * @param meetingSectionWriteModel The meetingSectionWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateMeetingSectionWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MeetingSectionWriteModel meetingSectionWriteModel) throws RestClientResponseException {
        return updateMeetingSectionRequestCreation(id, meetingSectionWriteModel);
    }

    /**
     * Toggle an agenda item upvote
     * Toggles the current user&#39;s upvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec upvoteMeetingAgendaItemRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling upvoteMeetingAgendaItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meeting_agenda_items/{id}/upvote", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Toggle an agenda item upvote
     * Toggles the current user&#39;s upvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemModel upvoteMeetingAgendaItem(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return upvoteMeetingAgendaItemRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Toggle an agenda item upvote
     * Toggles the current user&#39;s upvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param id Agenda item identifier
     * @return ResponseEntity&lt;MeetingAgendaItemModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemModel> upvoteMeetingAgendaItemWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return upvoteMeetingAgendaItemRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Toggle an agenda item upvote
     * Toggles the current user&#39;s upvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param id Agenda item identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec upvoteMeetingAgendaItemWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return upvoteMeetingAgendaItemRequestCreation(id);
    }

    /**
     * Toggle an agenda item upvote
     * Toggles the current user&#39;s upvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec upvoteMeetingAgendaItemByMeetingRequestCreation(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new RestClientResponseException("Missing the required parameter 'meetingId' when calling upvoteMeetingAgendaItemByMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling upvoteMeetingAgendaItemByMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("meeting_id", meetingId);
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

        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/{meeting_id}/agenda_items/{id}/upvote", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Toggle an agenda item upvote
     * Toggles the current user&#39;s upvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return MeetingAgendaItemModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MeetingAgendaItemModel upvoteMeetingAgendaItemByMeeting(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return upvoteMeetingAgendaItemByMeetingRequestCreation(meetingId, id).body(localVarReturnType);
    }

    /**
     * Toggle an agenda item upvote
     * Toggles the current user&#39;s upvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return ResponseEntity&lt;MeetingAgendaItemModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MeetingAgendaItemModel> upvoteMeetingAgendaItemByMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MeetingAgendaItemModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return upvoteMeetingAgendaItemByMeetingRequestCreation(meetingId, id).toEntity(localVarReturnType);
    }

    /**
     * Toggle an agenda item upvote
     * Toggles the current user&#39;s upvote. Repeating the same action removes the vote; the opposite action replaces it. Each user has at most one vote per item. No request body is required. Voting requires &#x60;view_meetings&#x60; and vote-based sorting. Votes do not change manual positions or section ordering.
     * <p><b>200</b> - The updated agenda item, including its net vote score.
     * <p><b>400</b> - The meeting is not in vote-based sorting mode. Existing votes are unchanged.
     * <p><b>403</b> - Voting is unavailable for closed meetings, templates, or backlog items.
     * <p><b>404</b> - The item or meeting does not exist or is invisible to the client.
     * @param meetingId Meeting identifier
     * @param id Agenda item identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec upvoteMeetingAgendaItemByMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer meetingId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return upvoteMeetingAgendaItemByMeetingRequestCreation(meetingId, id);
    }

    /**
     * Meeting create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateMeetingCreateFormRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/meetings/form", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Meeting create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateMeetingCreateForm() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateMeetingCreateFormRequestCreation().body(localVarReturnType);
    }

    /**
     * Meeting create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateMeetingCreateFormWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateMeetingCreateFormRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Meeting create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create meetings in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateMeetingCreateFormWithResponseSpec() throws RestClientResponseException {
        return validateMeetingCreateFormRequestCreation();
    }

    /**
     * Meeting update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Meeting identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateMeetingUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateMeetingUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/meetings/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Meeting update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Meeting identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateMeetingUpdateForm(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateMeetingUpdateFormRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Meeting update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Meeting identifier
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateMeetingUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateMeetingUpdateFormRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Meeting update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings in the meeting&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Meeting identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateMeetingUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return validateMeetingUpdateFormRequestCreation(id);
    }
}
