package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ActivityCommentWriteModel;
import ai.gebo.openproject.client.model.ActivityModel;
import ai.gebo.openproject.client.model.AttachmentModel;
import ai.gebo.openproject.client.model.AttachmentUploadModel;
import ai.gebo.openproject.client.model.AttachmentsModel;
import ai.gebo.openproject.client.model.EmojiReactionModel;
import ai.gebo.openproject.client.model.EmojiReactionsModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import java.io.File;
import ai.gebo.openproject.client.model.FileUploadFormMetadata;
import ai.gebo.openproject.client.model.ToggleActivityEmojiReactionRequest;

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
public class ActivitiesApi {
    private ApiClient apiClient;

    public ActivitiesApi() {
        this(new ApiClient());
    }

    public ActivitiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add attachment to activity
     * Adds an attachment to the specified activity.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view_work_packages or view_internal_comments (for internal comments)  *Note that you will only receive this error, if you are at least allowed to see the activity*
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view_work_packages or view_internal_comments (for internal comments)  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the activity to receive the attachment
     * @param metadata The metadata parameter
     * @param _file The _file parameter
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createActivityAttachmentRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileUploadFormMetadata metadata, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createActivityAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (metadata != null) {
            formParams.add("metadata", metadata);
        }
        if (_file != null) {
            formParams.add("file", new FileSystemResource(_file));
        }

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/activities/{id}/attachments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add attachment to activity
     * Adds an attachment to the specified activity.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view_work_packages or view_internal_comments (for internal comments)  *Note that you will only receive this error, if you are at least allowed to see the activity*
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view_work_packages or view_internal_comments (for internal comments)  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the activity to receive the attachment
     * @param metadata The metadata parameter
     * @param _file The _file parameter
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentModel createActivityAttachment(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileUploadFormMetadata metadata, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createActivityAttachmentRequestCreation(id, metadata, _file).body(localVarReturnType);
    }

    /**
     * Add attachment to activity
     * Adds an attachment to the specified activity.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view_work_packages or view_internal_comments (for internal comments)  *Note that you will only receive this error, if you are at least allowed to see the activity*
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view_work_packages or view_internal_comments (for internal comments)  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the activity to receive the attachment
     * @param metadata The metadata parameter
     * @param _file The _file parameter
     * @return ResponseEntity&lt;AttachmentModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentModel> createActivityAttachmentWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileUploadFormMetadata metadata, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createActivityAttachmentRequestCreation(id, metadata, _file).toEntity(localVarReturnType);
    }

    /**
     * Add attachment to activity
     * Adds an attachment to the specified activity.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view_work_packages or view_internal_comments (for internal comments)  *Note that you will only receive this error, if you are at least allowed to see the activity*
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view_work_packages or view_internal_comments (for internal comments)  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the activity to receive the attachment
     * @param metadata The metadata parameter
     * @param _file The _file parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createActivityAttachmentWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileUploadFormMetadata metadata, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return createActivityAttachmentRequestCreation(id, metadata, _file);
    }

    /**
     * Get an activity
     * Returns the requested activity resource identified by its unique id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the user does not have sufficient permissions to see it.  **Required permission:** depends on the related entity, e.g. &#x60;view_work_package&#x60; for work packages.
     * @param id Activity id
     * @return ActivityModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getActivityRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getActivity", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ActivityModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/activities/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get an activity
     * Returns the requested activity resource identified by its unique id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the user does not have sufficient permissions to see it.  **Required permission:** depends on the related entity, e.g. &#x60;view_work_package&#x60; for work packages.
     * @param id Activity id
     * @return ActivityModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ActivityModel getActivity(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ActivityModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getActivityRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get an activity
     * Returns the requested activity resource identified by its unique id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the user does not have sufficient permissions to see it.  **Required permission:** depends on the related entity, e.g. &#x60;view_work_package&#x60; for work packages.
     * @param id Activity id
     * @return ResponseEntity&lt;ActivityModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ActivityModel> getActivityWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ActivityModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getActivityRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get an activity
     * Returns the requested activity resource identified by its unique id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the user does not have sufficient permissions to see it.  **Required permission:** depends on the related entity, e.g. &#x60;view_work_package&#x60; for work packages.
     * @param id Activity id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getActivityWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getActivityRequestCreation(id);
    }

    /**
     * List attachments by activity
     * List all attachments of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listActivityAttachmentsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listActivityAttachments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/activities/{id}/attachments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List attachments by activity
     * List all attachments of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentsModel listActivityAttachments(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listActivityAttachmentsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List attachments by activity
     * List all attachments of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose attachments will be listed
     * @return ResponseEntity&lt;AttachmentsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentsModel> listActivityAttachmentsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listActivityAttachmentsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List attachments by activity
     * List all attachments of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose attachments will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listActivityAttachmentsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listActivityAttachmentsRequestCreation(id);
    }

    /**
     * List emoji reactions by activity
     * List all emoji reactions of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listActivityEmojiReactionsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listActivityEmojiReactions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/activities/{id}/emoji_reactions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List emoji reactions by activity
     * List all emoji reactions of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public EmojiReactionsModel listActivityEmojiReactions(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listActivityEmojiReactionsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List emoji reactions by activity
     * List all emoji reactions of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose emoji reactions will be listed
     * @return ResponseEntity&lt;EmojiReactionsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EmojiReactionsModel> listActivityEmojiReactionsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listActivityEmojiReactionsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List emoji reactions by activity
     * List all emoji reactions of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose emoji reactions will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listActivityEmojiReactionsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listActivityEmojiReactionsRequestCreation(id);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageActivitiesEmojiReactionsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageActivitiesEmojiReactions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/activities_emoji_reactions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public EmojiReactionsModel listWorkPackageActivitiesEmojiReactions(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageActivitiesEmojiReactionsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return ResponseEntity&lt;EmojiReactionsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EmojiReactionsModel> listWorkPackageActivitiesEmojiReactionsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageActivitiesEmojiReactionsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageActivitiesEmojiReactionsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWorkPackageActivitiesEmojiReactionsRequestCreation(id);
    }

    /**
     * Prepare a direct attachment upload for an activity
     * Prepares a direct upload of an attachment for the given activity (e.g. a work package comment), to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this activity as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add_work_package_comments  *Note that you will only receive this error, if you are at least allowed to see the activity*
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the activity to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec prepareActivityAttachmentUploadRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling prepareActivityAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metadata' is set
        if (metadata == null) {
            throw new RestClientResponseException("Missing the required parameter 'metadata' when calling prepareActivityAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/activities/{id}/attachments/prepare", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for an activity
     * Prepares a direct upload of an attachment for the given activity (e.g. a work package comment), to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this activity as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add_work_package_comments  *Note that you will only receive this error, if you are at least allowed to see the activity*
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the activity to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentUploadModel prepareActivityAttachmentUpload(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareActivityAttachmentUploadRequestCreation(id, metadata).body(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for an activity
     * Prepares a direct upload of an attachment for the given activity (e.g. a work package comment), to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this activity as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add_work_package_comments  *Note that you will only receive this error, if you are at least allowed to see the activity*
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the activity to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseEntity&lt;AttachmentUploadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentUploadModel> prepareActivityAttachmentUploadWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareActivityAttachmentUploadRequestCreation(id, metadata).toEntity(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for an activity
     * Prepares a direct upload of an attachment for the given activity (e.g. a work package comment), to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this activity as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add_work_package_comments  *Note that you will only receive this error, if you are at least allowed to see the activity*
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the activity to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec prepareActivityAttachmentUploadWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        return prepareActivityAttachmentUploadRequestCreation(id, metadata);
    }

    /**
     * Toggle emoji reaction for an activity
     * Toggle an emoji reaction for a given activity. If the user has already reacted with the given emoji, the reaction will be removed. Otherwise, a new reaction will be created.  **Note:** The response contains the complete collection of all emoji reactions for this activity.  **Required permission:** - &#x60;add_work_package_comments&#x60; - for internal comments: &#x60;add_internal_comments&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request is invalid. For example, if the reaction is not valid.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to toggle the emoji reaction for the activity.
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.
     * @param id ID of the activity to toggle emoji reaction for
     * @param toggleActivityEmojiReactionRequest The toggleActivityEmojiReactionRequest parameter
     * @return EmojiReactionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toggleActivityEmojiReactionRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull ToggleActivityEmojiReactionRequest toggleActivityEmojiReactionRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling toggleActivityEmojiReaction", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'toggleActivityEmojiReactionRequest' is set
        if (toggleActivityEmojiReactionRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'toggleActivityEmojiReactionRequest' when calling toggleActivityEmojiReaction", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
            "application/hal+json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<EmojiReactionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/activities/{id}/emoji_reactions", HttpMethod.PATCH, pathParams, localVarQueryParams, toggleActivityEmojiReactionRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Toggle emoji reaction for an activity
     * Toggle an emoji reaction for a given activity. If the user has already reacted with the given emoji, the reaction will be removed. Otherwise, a new reaction will be created.  **Note:** The response contains the complete collection of all emoji reactions for this activity.  **Required permission:** - &#x60;add_work_package_comments&#x60; - for internal comments: &#x60;add_internal_comments&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request is invalid. For example, if the reaction is not valid.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to toggle the emoji reaction for the activity.
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.
     * @param id ID of the activity to toggle emoji reaction for
     * @param toggleActivityEmojiReactionRequest The toggleActivityEmojiReactionRequest parameter
     * @return EmojiReactionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public EmojiReactionModel toggleActivityEmojiReaction(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull ToggleActivityEmojiReactionRequest toggleActivityEmojiReactionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toggleActivityEmojiReactionRequestCreation(id, toggleActivityEmojiReactionRequest).body(localVarReturnType);
    }

    /**
     * Toggle emoji reaction for an activity
     * Toggle an emoji reaction for a given activity. If the user has already reacted with the given emoji, the reaction will be removed. Otherwise, a new reaction will be created.  **Note:** The response contains the complete collection of all emoji reactions for this activity.  **Required permission:** - &#x60;add_work_package_comments&#x60; - for internal comments: &#x60;add_internal_comments&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request is invalid. For example, if the reaction is not valid.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to toggle the emoji reaction for the activity.
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.
     * @param id ID of the activity to toggle emoji reaction for
     * @param toggleActivityEmojiReactionRequest The toggleActivityEmojiReactionRequest parameter
     * @return ResponseEntity&lt;EmojiReactionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EmojiReactionModel> toggleActivityEmojiReactionWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull ToggleActivityEmojiReactionRequest toggleActivityEmojiReactionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toggleActivityEmojiReactionRequestCreation(id, toggleActivityEmojiReactionRequest).toEntity(localVarReturnType);
    }

    /**
     * Toggle emoji reaction for an activity
     * Toggle an emoji reaction for a given activity. If the user has already reacted with the given emoji, the reaction will be removed. Otherwise, a new reaction will be created.  **Note:** The response contains the complete collection of all emoji reactions for this activity.  **Required permission:** - &#x60;add_work_package_comments&#x60; - for internal comments: &#x60;add_internal_comments&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request is invalid. For example, if the reaction is not valid.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to toggle the emoji reaction for the activity.
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.
     * @param id ID of the activity to toggle emoji reaction for
     * @param toggleActivityEmojiReactionRequest The toggleActivityEmojiReactionRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toggleActivityEmojiReactionWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull ToggleActivityEmojiReactionRequest toggleActivityEmojiReactionRequest) throws RestClientResponseException {
        return toggleActivityEmojiReactionRequestCreation(id, toggleActivityEmojiReactionRequest);
    }

    /**
     * Update activity
     * Updates an activity&#39;s comment and, on success, returns the updated activity.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit journals
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to modify a read-only property.
     * @param id Activity id
     * @param activityCommentWriteModel The activityCommentWriteModel parameter
     * @return ActivityModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateActivityRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ActivityCommentWriteModel activityCommentWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateActivity", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ActivityModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/activities/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, activityCommentWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update activity
     * Updates an activity&#39;s comment and, on success, returns the updated activity.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit journals
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to modify a read-only property.
     * @param id Activity id
     * @param activityCommentWriteModel The activityCommentWriteModel parameter
     * @return ActivityModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ActivityModel updateActivity(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ActivityCommentWriteModel activityCommentWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<ActivityModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateActivityRequestCreation(id, activityCommentWriteModel).body(localVarReturnType);
    }

    /**
     * Update activity
     * Updates an activity&#39;s comment and, on success, returns the updated activity.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit journals
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to modify a read-only property.
     * @param id Activity id
     * @param activityCommentWriteModel The activityCommentWriteModel parameter
     * @return ResponseEntity&lt;ActivityModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ActivityModel> updateActivityWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ActivityCommentWriteModel activityCommentWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<ActivityModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateActivityRequestCreation(id, activityCommentWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update activity
     * Updates an activity&#39;s comment and, on success, returns the updated activity.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit journals
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to modify a read-only property.
     * @param id Activity id
     * @param activityCommentWriteModel The activityCommentWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateActivityWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ActivityCommentWriteModel activityCommentWriteModel) throws RestClientResponseException {
        return updateActivityRequestCreation(id, activityCommentWriteModel);
    }
}
