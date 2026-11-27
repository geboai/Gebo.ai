package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.AttachmentModel;
import ai.gebo.openproject.client.model.AttachmentUploadModel;
import ai.gebo.openproject.client.model.AttachmentsModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import java.io.File;
import ai.gebo.openproject.client.model.FileUploadFormMetadata;

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
public class AttachmentsApi {
    private ApiClient apiClient;

    public AttachmentsApi() {
        this(new ApiClient());
    }

    public AttachmentsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add attachment to meeting
     * Adds an attachment with the meeting as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings  *Note that you will only receive this error, if you are at least allowed to see the meeting*
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the meeting to receive the attachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addAttachmentToMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling addAttachmentToMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/meetings/{id}/attachments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add attachment to meeting
     * Adds an attachment with the meeting as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings  *Note that you will only receive this error, if you are at least allowed to see the meeting*
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the meeting to receive the attachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void addAttachmentToMeeting(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        addAttachmentToMeetingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Add attachment to meeting
     * Adds an attachment with the meeting as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings  *Note that you will only receive this error, if you are at least allowed to see the meeting*
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the meeting to receive the attachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> addAttachmentToMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addAttachmentToMeetingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Add attachment to meeting
     * Adds an attachment with the meeting as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit meetings  *Note that you will only receive this error, if you are at least allowed to see the meeting*
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the meeting to receive the attachment
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addAttachmentToMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return addAttachmentToMeetingRequestCreation(id);
    }

    /**
     * Add attachment to post
     * Adds an attachment with the post as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit messages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the post to receive the attachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addAttachmentToPostRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling addAttachmentToPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/posts/{id}/attachments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add attachment to post
     * Adds an attachment with the post as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit messages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the post to receive the attachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void addAttachmentToPost(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        addAttachmentToPostRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Add attachment to post
     * Adds an attachment with the post as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit messages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the post to receive the attachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> addAttachmentToPostWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addAttachmentToPostRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Add attachment to post
     * Adds an attachment with the post as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit messages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the post to receive the attachment
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addAttachmentToPostWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return addAttachmentToPostRequestCreation(id);
    }

    /**
     * Add attachment to wiki page
     * Adds an attachment with the wiki page as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit wiki pages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a wiki page That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the wiki page to receive the attachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addAttachmentToWikiPageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling addAttachmentToWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/wiki_pages/{id}/attachments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add attachment to wiki page
     * Adds an attachment with the wiki page as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit wiki pages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a wiki page That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the wiki page to receive the attachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void addAttachmentToWikiPage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        addAttachmentToWikiPageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Add attachment to wiki page
     * Adds an attachment with the wiki page as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit wiki pages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a wiki page That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the wiki page to receive the attachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> addAttachmentToWikiPageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addAttachmentToWikiPageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Add attachment to wiki page
     * Adds an attachment with the wiki page as its container.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit wiki pages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a wiki page That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the wiki page to receive the attachment
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addAttachmentToWikiPageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return addAttachmentToWikiPageRequestCreation(id);
    }

    /**
     * Complete a direct attachment upload
     * This is the third and final step of the direct upload flow described at &#x60;POST /api/v3/attachments/prepare&#x60;. It is called by the client after it has successfully uploaded the file content directly to the storage backend, as instructed by the &#x60;addAttachment&#x60; link of the preceding &#x60;prepare&#x60; step.  The server checks that the uploaded file is now present in the storage. If so, the attachment is scheduled to be finalized asynchronously (its definitive content type is determined, matched against the configured content type allowlist if any is configured, a virus scan is run if enabled, and the attachment&#39;s container is notified). The finalization may therefore not yet be reflected in the returned representation, in particular the &#x60;status&#x60; property might still read &#x60;prepared&#x60; for a brief moment, but will eventually change to &#x60;uploaded&#x60; (or the attachment removed, should the finalization fail, e.g. because the content type is not allowed).  Calling this endpoint before the direct upload to the storage has actually finished (or if it failed) results in a &#x60;404&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if any of the following is true:  * No pending, prepared upload with the given id exists (e.g. it was already completed, was   never prepared, or does not belong to the current user)  * the client does not have sufficient permissions to see the attachment  * the file has not actually been uploaded to the storage backend yet (i.e. step 2 of the   flow was skipped or has not finished successfully)  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id The &#x60;id&#x60; of the pending attachment, as returned by the &#x60;prepare&#x60; step (either in the top-level &#x60;id&#x60; property or, equivalently, in the &#x60;_links.completeUpload&#x60; link of the response).
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec completeAttachmentUploadRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling completeAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/attachments/{id}/uploaded", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Complete a direct attachment upload
     * This is the third and final step of the direct upload flow described at &#x60;POST /api/v3/attachments/prepare&#x60;. It is called by the client after it has successfully uploaded the file content directly to the storage backend, as instructed by the &#x60;addAttachment&#x60; link of the preceding &#x60;prepare&#x60; step.  The server checks that the uploaded file is now present in the storage. If so, the attachment is scheduled to be finalized asynchronously (its definitive content type is determined, matched against the configured content type allowlist if any is configured, a virus scan is run if enabled, and the attachment&#39;s container is notified). The finalization may therefore not yet be reflected in the returned representation, in particular the &#x60;status&#x60; property might still read &#x60;prepared&#x60; for a brief moment, but will eventually change to &#x60;uploaded&#x60; (or the attachment removed, should the finalization fail, e.g. because the content type is not allowed).  Calling this endpoint before the direct upload to the storage has actually finished (or if it failed) results in a &#x60;404&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if any of the following is true:  * No pending, prepared upload with the given id exists (e.g. it was already completed, was   never prepared, or does not belong to the current user)  * the client does not have sufficient permissions to see the attachment  * the file has not actually been uploaded to the storage backend yet (i.e. step 2 of the   flow was skipped or has not finished successfully)  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id The &#x60;id&#x60; of the pending attachment, as returned by the &#x60;prepare&#x60; step (either in the top-level &#x60;id&#x60; property or, equivalently, in the &#x60;_links.completeUpload&#x60; link of the response).
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentModel completeAttachmentUpload(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return completeAttachmentUploadRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Complete a direct attachment upload
     * This is the third and final step of the direct upload flow described at &#x60;POST /api/v3/attachments/prepare&#x60;. It is called by the client after it has successfully uploaded the file content directly to the storage backend, as instructed by the &#x60;addAttachment&#x60; link of the preceding &#x60;prepare&#x60; step.  The server checks that the uploaded file is now present in the storage. If so, the attachment is scheduled to be finalized asynchronously (its definitive content type is determined, matched against the configured content type allowlist if any is configured, a virus scan is run if enabled, and the attachment&#39;s container is notified). The finalization may therefore not yet be reflected in the returned representation, in particular the &#x60;status&#x60; property might still read &#x60;prepared&#x60; for a brief moment, but will eventually change to &#x60;uploaded&#x60; (or the attachment removed, should the finalization fail, e.g. because the content type is not allowed).  Calling this endpoint before the direct upload to the storage has actually finished (or if it failed) results in a &#x60;404&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if any of the following is true:  * No pending, prepared upload with the given id exists (e.g. it was already completed, was   never prepared, or does not belong to the current user)  * the client does not have sufficient permissions to see the attachment  * the file has not actually been uploaded to the storage backend yet (i.e. step 2 of the   flow was skipped or has not finished successfully)  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id The &#x60;id&#x60; of the pending attachment, as returned by the &#x60;prepare&#x60; step (either in the top-level &#x60;id&#x60; property or, equivalently, in the &#x60;_links.completeUpload&#x60; link of the response).
     * @return ResponseEntity&lt;AttachmentModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentModel> completeAttachmentUploadWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return completeAttachmentUploadRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Complete a direct attachment upload
     * This is the third and final step of the direct upload flow described at &#x60;POST /api/v3/attachments/prepare&#x60;. It is called by the client after it has successfully uploaded the file content directly to the storage backend, as instructed by the &#x60;addAttachment&#x60; link of the preceding &#x60;prepare&#x60; step.  The server checks that the uploaded file is now present in the storage. If so, the attachment is scheduled to be finalized asynchronously (its definitive content type is determined, matched against the configured content type allowlist if any is configured, a virus scan is run if enabled, and the attachment&#39;s container is notified). The finalization may therefore not yet be reflected in the returned representation, in particular the &#x60;status&#x60; property might still read &#x60;prepared&#x60; for a brief moment, but will eventually change to &#x60;uploaded&#x60; (or the attachment removed, should the finalization fail, e.g. because the content type is not allowed).  Calling this endpoint before the direct upload to the storage has actually finished (or if it failed) results in a &#x60;404&#x60; response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if any of the following is true:  * No pending, prepared upload with the given id exists (e.g. it was already completed, was   never prepared, or does not belong to the current user)  * the client does not have sufficient permissions to see the attachment  * the file has not actually been uploaded to the storage backend yet (i.e. step 2 of the   flow was skipped or has not finished successfully)  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id The &#x60;id&#x60; of the pending attachment, as returned by the &#x60;prepare&#x60; step (either in the top-level &#x60;id&#x60; property or, equivalently, in the &#x60;_links.completeUpload&#x60; link of the response).
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec completeAttachmentUploadWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return completeAttachmentUploadRequestCreation(id);
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
     * Create Attachment
     * Clients can create attachments without a container first and attach them later on. This is useful if the container does not exist at the time the attachment is uploaded. After the upload, the client can then claim such containerless attachments for any resource eligible (e.g. WorkPackage) on subsequent requests. The upload and the claiming *must* be done for the same user account. Attachments uploaded by another user cannot be claimed and once claimed for a resource, they cannot be claimed by another.  The upload request must be of type &#x60;multipart/form-data&#x60; with exactly two parts.  The first part *must* be called &#x60;metadata&#x60;. Its content type is expected to be &#x60;application/json&#x60;, the body *must* be a single JSON object, containing at least the &#x60;fileName&#x60; and optionally the attachments &#x60;description&#x60;.  The second part *must* be called &#x60;file&#x60;, its content type *should* match the mime type of the file. The body *must* be the raw content of the file. Note that a &#x60;filename&#x60; *must* be indicated in the &#x60;Content-Disposition&#x60; of this part, although it will be ignored. Instead the &#x60;fileName&#x60; inside the JSON of the metadata part will be used.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** At least one permission in any project: edit work package, add work package, edit messages, edit wiki pages (plugins might extend this list)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createAttachmentRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/attachments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create Attachment
     * Clients can create attachments without a container first and attach them later on. This is useful if the container does not exist at the time the attachment is uploaded. After the upload, the client can then claim such containerless attachments for any resource eligible (e.g. WorkPackage) on subsequent requests. The upload and the claiming *must* be done for the same user account. Attachments uploaded by another user cannot be claimed and once claimed for a resource, they cannot be claimed by another.  The upload request must be of type &#x60;multipart/form-data&#x60; with exactly two parts.  The first part *must* be called &#x60;metadata&#x60;. Its content type is expected to be &#x60;application/json&#x60;, the body *must* be a single JSON object, containing at least the &#x60;fileName&#x60; and optionally the attachments &#x60;description&#x60;.  The second part *must* be called &#x60;file&#x60;, its content type *should* match the mime type of the file. The body *must* be the raw content of the file. Note that a &#x60;filename&#x60; *must* be indicated in the &#x60;Content-Disposition&#x60; of this part, although it will be ignored. Instead the &#x60;fileName&#x60; inside the JSON of the metadata part will be used.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** At least one permission in any project: edit work package, add work package, edit messages, edit wiki pages (plugins might extend this list)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentModel createAttachment() throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createAttachmentRequestCreation().body(localVarReturnType);
    }

    /**
     * Create Attachment
     * Clients can create attachments without a container first and attach them later on. This is useful if the container does not exist at the time the attachment is uploaded. After the upload, the client can then claim such containerless attachments for any resource eligible (e.g. WorkPackage) on subsequent requests. The upload and the claiming *must* be done for the same user account. Attachments uploaded by another user cannot be claimed and once claimed for a resource, they cannot be claimed by another.  The upload request must be of type &#x60;multipart/form-data&#x60; with exactly two parts.  The first part *must* be called &#x60;metadata&#x60;. Its content type is expected to be &#x60;application/json&#x60;, the body *must* be a single JSON object, containing at least the &#x60;fileName&#x60; and optionally the attachments &#x60;description&#x60;.  The second part *must* be called &#x60;file&#x60;, its content type *should* match the mime type of the file. The body *must* be the raw content of the file. Note that a &#x60;filename&#x60; *must* be indicated in the &#x60;Content-Disposition&#x60; of this part, although it will be ignored. Instead the &#x60;fileName&#x60; inside the JSON of the metadata part will be used.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** At least one permission in any project: edit work package, add work package, edit messages, edit wiki pages (plugins might extend this list)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @return ResponseEntity&lt;AttachmentModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentModel> createAttachmentWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createAttachmentRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Create Attachment
     * Clients can create attachments without a container first and attach them later on. This is useful if the container does not exist at the time the attachment is uploaded. After the upload, the client can then claim such containerless attachments for any resource eligible (e.g. WorkPackage) on subsequent requests. The upload and the claiming *must* be done for the same user account. Attachments uploaded by another user cannot be claimed and once claimed for a resource, they cannot be claimed by another.  The upload request must be of type &#x60;multipart/form-data&#x60; with exactly two parts.  The first part *must* be called &#x60;metadata&#x60;. Its content type is expected to be &#x60;application/json&#x60;, the body *must* be a single JSON object, containing at least the &#x60;fileName&#x60; and optionally the attachments &#x60;description&#x60;.  The second part *must* be called &#x60;file&#x60;, its content type *should* match the mime type of the file. The body *must* be the raw content of the file. Note that a &#x60;filename&#x60; *must* be indicated in the &#x60;Content-Disposition&#x60; of this part, although it will be ignored. Instead the &#x60;fileName&#x60; inside the JSON of the metadata part will be used.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** At least one permission in any project: edit work package, add work package, edit messages, edit wiki pages (plugins might extend this list)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createAttachmentWithResponseSpec() throws RestClientResponseException {
        return createAttachmentRequestCreation();
    }

    /**
     * Create work package attachment
     * To add an attachment to a work package, a client needs to issue a request of type &#x60;multipart/form-data&#x60; with exactly two parts.  The first part *must* be called &#x60;metadata&#x60;. Its content type is expected to be &#x60;application/json&#x60;, the body *must* be a single JSON object, containing at least the &#x60;fileName&#x60; and optionally the attachments &#x60;description&#x60;.  The second part *must* be called &#x60;file&#x60;, its content type *should* match the mime type of the file. The body *must* be the raw content of the file. Note that a &#x60;filename&#x60; must be indicated in the &#x60;Content-Disposition&#x60; of this part, however it will be ignored. Instead the &#x60;fileName&#x60; inside the JSON of the metadata part will be used.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the work package to receive the attachment
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createWorkPackageAttachmentRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createWorkPackageAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/attachments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create work package attachment
     * To add an attachment to a work package, a client needs to issue a request of type &#x60;multipart/form-data&#x60; with exactly two parts.  The first part *must* be called &#x60;metadata&#x60;. Its content type is expected to be &#x60;application/json&#x60;, the body *must* be a single JSON object, containing at least the &#x60;fileName&#x60; and optionally the attachments &#x60;description&#x60;.  The second part *must* be called &#x60;file&#x60;, its content type *should* match the mime type of the file. The body *must* be the raw content of the file. Note that a &#x60;filename&#x60; must be indicated in the &#x60;Content-Disposition&#x60; of this part, however it will be ignored. Instead the &#x60;fileName&#x60; inside the JSON of the metadata part will be used.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the work package to receive the attachment
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentModel createWorkPackageAttachment(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageAttachmentRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Create work package attachment
     * To add an attachment to a work package, a client needs to issue a request of type &#x60;multipart/form-data&#x60; with exactly two parts.  The first part *must* be called &#x60;metadata&#x60;. Its content type is expected to be &#x60;application/json&#x60;, the body *must* be a single JSON object, containing at least the &#x60;fileName&#x60; and optionally the attachments &#x60;description&#x60;.  The second part *must* be called &#x60;file&#x60;, its content type *should* match the mime type of the file. The body *must* be the raw content of the file. Note that a &#x60;filename&#x60; must be indicated in the &#x60;Content-Disposition&#x60; of this part, however it will be ignored. Instead the &#x60;fileName&#x60; inside the JSON of the metadata part will be used.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the work package to receive the attachment
     * @return ResponseEntity&lt;AttachmentModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentModel> createWorkPackageAttachmentWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageAttachmentRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Create work package attachment
     * To add an attachment to a work package, a client needs to issue a request of type &#x60;multipart/form-data&#x60; with exactly two parts.  The first part *must* be called &#x60;metadata&#x60;. Its content type is expected to be &#x60;application/json&#x60;, the body *must* be a single JSON object, containing at least the &#x60;fileName&#x60; and optionally the attachments &#x60;description&#x60;.  The second part *must* be called &#x60;file&#x60;, its content type *should* match the mime type of the file. The body *must* be the raw content of the file. Note that a &#x60;filename&#x60; must be indicated in the &#x60;Content-Disposition&#x60; of this part, however it will be ignored. Instead the &#x60;fileName&#x60; inside the JSON of the metadata part will be used.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting one of the required parts (metadata and file)  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to send an invalid attachment. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Sending a file that is too large
     * @param id ID of the work package to receive the attachment
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createWorkPackageAttachmentWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return createWorkPackageAttachmentRequestCreation(id);
    }

    /**
     * Delete attachment
     * Permanently deletes the specified attachment.
     * <p><b>204</b> - Returned if the attachment was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned along with an appropriate HTTP status.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit permission for the container of the attachment or being the author for attachments without container  *Note that you will only receive this error, if you are at least allowed to see the attachment.*
     * <p><b>404</b> - Returned if the attachment does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view permission for the container of the attachment or being the author for attachments without container  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Attachment id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteAttachmentRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/attachments/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete attachment
     * Permanently deletes the specified attachment.
     * <p><b>204</b> - Returned if the attachment was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned along with an appropriate HTTP status.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit permission for the container of the attachment or being the author for attachments without container  *Note that you will only receive this error, if you are at least allowed to see the attachment.*
     * <p><b>404</b> - Returned if the attachment does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view permission for the container of the attachment or being the author for attachments without container  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Attachment id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteAttachment(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteAttachmentRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete attachment
     * Permanently deletes the specified attachment.
     * <p><b>204</b> - Returned if the attachment was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned along with an appropriate HTTP status.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit permission for the container of the attachment or being the author for attachments without container  *Note that you will only receive this error, if you are at least allowed to see the attachment.*
     * <p><b>404</b> - Returned if the attachment does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view permission for the container of the attachment or being the author for attachments without container  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Attachment id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteAttachmentWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteAttachmentRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete attachment
     * Permanently deletes the specified attachment.
     * <p><b>204</b> - Returned if the attachment was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned along with an appropriate HTTP status.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit permission for the container of the attachment or being the author for attachments without container  *Note that you will only receive this error, if you are at least allowed to see the attachment.*
     * <p><b>404</b> - Returned if the attachment does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view permission for the container of the attachment or being the author for attachments without container  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Attachment id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteAttachmentWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteAttachmentRequestCreation(id);
    }

    /**
     * View attachment
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the attachment does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view permission for the container of the attachment or being the author for attachments without container  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Attachment id
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAttachmentRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/attachments/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View attachment
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the attachment does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view permission for the container of the attachment or being the author for attachments without container  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Attachment id
     * @return AttachmentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentModel getAttachment(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getAttachmentRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View attachment
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the attachment does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view permission for the container of the attachment or being the author for attachments without container  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Attachment id
     * @return ResponseEntity&lt;AttachmentModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentModel> getAttachmentWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getAttachmentRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View attachment
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the attachment does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view permission for the container of the attachment or being the author for attachments without container  *Note: A client without sufficient permissions shall not be able to test for the existence of an attachment. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Attachment id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAttachmentWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getAttachmentRequestCreation(id);
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
     * List attachments by meeting
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the meeting whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAttachmentsByMeetingRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAttachmentsByMeeting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/meetings/{id}/attachments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List attachments by meeting
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the meeting whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentsModel listAttachmentsByMeeting(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAttachmentsByMeetingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List attachments by meeting
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the meeting whose attachments will be listed
     * @return ResponseEntity&lt;AttachmentsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentsModel> listAttachmentsByMeetingWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAttachmentsByMeetingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List attachments by meeting
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the meeting does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view meetings  *Note: A client without sufficient permissions shall not be able to test for the existence of a meeting. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the meeting whose attachments will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAttachmentsByMeetingWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAttachmentsByMeetingRequestCreation(id);
    }

    /**
     * List attachments by post
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the post whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAttachmentsByPostRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAttachmentsByPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/posts/{id}/attachments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List attachments by post
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the post whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentsModel listAttachmentsByPost(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAttachmentsByPostRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List attachments by post
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the post whose attachments will be listed
     * @return ResponseEntity&lt;AttachmentsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentsModel> listAttachmentsByPostWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAttachmentsByPostRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List attachments by post
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the post whose attachments will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAttachmentsByPostWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAttachmentsByPostRequestCreation(id);
    }

    /**
     * List attachments by wiki page
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the wiki page whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAttachmentsByWikiPageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAttachmentsByWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/wiki_pages/{id}/attachments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List attachments by wiki page
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the wiki page whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentsModel listAttachmentsByWikiPage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAttachmentsByWikiPageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List attachments by wiki page
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the wiki page whose attachments will be listed
     * @return ResponseEntity&lt;AttachmentsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentsModel> listAttachmentsByWikiPageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAttachmentsByWikiPageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List attachments by wiki page
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the wiki page whose attachments will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAttachmentsByWikiPageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAttachmentsByWikiPageRequestCreation(id);
    }

    /**
     * List attachments by work package
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageAttachmentsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageAttachments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/attachments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List attachments by work package
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose attachments will be listed
     * @return AttachmentsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentsModel listWorkPackageAttachments(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageAttachmentsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List attachments by work package
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose attachments will be listed
     * @return ResponseEntity&lt;AttachmentsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentsModel> listWorkPackageAttachmentsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageAttachmentsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List attachments by work package
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose attachments will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageAttachmentsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWorkPackageAttachmentsRequestCreation(id);
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
     * Prepare a direct attachment upload
     * Prepares a **direct upload** of a file to OpenProject&#39;s attachment storage, bypassing the OpenProject server for the actual file transfer.  ## Why this endpoint exists  Attachments can always be created in one step by sending the raw file content together with the request, see &#x60;POST /api/v3/attachments&#x60; and the equivalent &#x60;.../attachments&#x60; endpoints of the various containers (work packages, wiki pages, meetings, ...). For large files or slow connections this means the file has to be transferred to the OpenProject server first, and then from there to the configured remote storage (e.g. an S3-compatible object store) - twice the traffic and twice the time.  When OpenProject is configured to use a remote, S3-compatible storage for attachments, it can instead hand the client a short-lived, pre-signed upload instruction that allows the client to upload the file **directly** to that storage, without the OpenProject server ever being involved in the transfer. This is what this endpoint, and its per-container siblings such as &#x60;POST /api/v3/work_packages/{id}/attachments/prepare&#x60;, are used for.  If direct uploads are not available (e.g. because attachments are stored on the local disk of the OpenProject server, or a remote storage without access-key credentials is used), this request fails with a &#x60;422&#x60; error. Clients should fall back to the regular, single-step upload in that case.  ## The three-step flow  1. **Prepare** (this request): the client tells the server the file name and size (and    optionally its content type, description and a checksum) it intends to upload. The server    creates a pending &#x60;Attachment&#x60; (&#x60;status: prepared&#x60;) without any file content yet and    responds with an &#x60;AttachmentUpload&#x60; resource. That resource contains, among others:     - &#x60;_links.addAttachment&#x60;: the direct upload instruction (&#x60;href&#x60;, &#x60;method&#x60; and &#x60;form_fields&#x60;)       for the storage backend.     - &#x60;_links.completeUpload&#x60;: the link to call once step 2 has finished.  2. **Upload**: the client sends a &#x60;multipart/form-data&#x60; request exactly as instructed by    &#x60;_links.addAttachment&#x60;, i.e. to the given &#x60;href&#x60;, using the given &#x60;method&#x60;, including every    given &#x60;form_fields&#x60; entry as its own part (in the given order), and finally a &#x60;file&#x60; part    carrying the raw file content as the *last* part of the request. This request goes directly    to the storage backend, not to the OpenProject server.  3. **Complete** (&#x60;GET&#x60; the href given in &#x60;_links.completeUpload&#x60;, i.e.    &#x60;/api/v3/attachments/{id}/uploaded&#x60;): tells the OpenProject server that the direct upload has    finished. The server verifies that the file is now present in the storage, and asynchronously    finalizes the attachment (determining its definitive content type, matching it against the    configured content type allowlist if any, running a virus scan if enabled, and creating    activity/notifications for its container). The response is the regular &#x60;Attachment&#x60; resource.    Until this step has completed, the pending attachment is not considered part of its    container and will eventually be cleaned up if it is never completed.  Only the user that prepared the upload can complete it; a pending upload that is never completed (e.g. because the client failed to perform step 2 or 3) is garbage collected after some time.  ## Container vs. containerless uploads  This endpoint (&#x60;/api/v3/attachments/prepare&#x60;) does not take a container - it prepares an attachment that is not yet attached to anything, e.g. because the containing resource (a new work package, for instance) does not exist yet. Such a containerless attachment can later be **claimed** by referencing its &#x60;id&#x60; while creating the eligible resource, exactly like containerless attachments created via &#x60;POST /api/v3/attachments&#x60;.  Most containers that support attachments (e.g. work packages, wiki pages, meetings, forum messages, activity comments) additionally expose the very same operation scoped to an existing instance of themselves, e.g. &#x60;POST /api/v3/work_packages/{id}/attachments/prepare&#x60;. Using one of those instead immediately associates the resulting attachment with that container, and is subject to the same permission requirements as &#x60;POST .../attachments&#x60; on that container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * Sending unparsable JSON in the &#x60;metadata&#x60; part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** At least one permission in any project that allows creating attachments (e.g. edit work package, add work package, edit messages, edit wiki pages; plugins might extend this list)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload.
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. This is, unlike the regular attachment creation endpoints, the *only* part of this request - no file content is sent here.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec prepareAttachmentUploadRequestCreation(@jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        // verify the required parameter 'metadata' is set
        if (metadata == null) {
            throw new RestClientResponseException("Missing the required parameter 'metadata' when calling prepareAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v3/attachments/prepare", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload
     * Prepares a **direct upload** of a file to OpenProject&#39;s attachment storage, bypassing the OpenProject server for the actual file transfer.  ## Why this endpoint exists  Attachments can always be created in one step by sending the raw file content together with the request, see &#x60;POST /api/v3/attachments&#x60; and the equivalent &#x60;.../attachments&#x60; endpoints of the various containers (work packages, wiki pages, meetings, ...). For large files or slow connections this means the file has to be transferred to the OpenProject server first, and then from there to the configured remote storage (e.g. an S3-compatible object store) - twice the traffic and twice the time.  When OpenProject is configured to use a remote, S3-compatible storage for attachments, it can instead hand the client a short-lived, pre-signed upload instruction that allows the client to upload the file **directly** to that storage, without the OpenProject server ever being involved in the transfer. This is what this endpoint, and its per-container siblings such as &#x60;POST /api/v3/work_packages/{id}/attachments/prepare&#x60;, are used for.  If direct uploads are not available (e.g. because attachments are stored on the local disk of the OpenProject server, or a remote storage without access-key credentials is used), this request fails with a &#x60;422&#x60; error. Clients should fall back to the regular, single-step upload in that case.  ## The three-step flow  1. **Prepare** (this request): the client tells the server the file name and size (and    optionally its content type, description and a checksum) it intends to upload. The server    creates a pending &#x60;Attachment&#x60; (&#x60;status: prepared&#x60;) without any file content yet and    responds with an &#x60;AttachmentUpload&#x60; resource. That resource contains, among others:     - &#x60;_links.addAttachment&#x60;: the direct upload instruction (&#x60;href&#x60;, &#x60;method&#x60; and &#x60;form_fields&#x60;)       for the storage backend.     - &#x60;_links.completeUpload&#x60;: the link to call once step 2 has finished.  2. **Upload**: the client sends a &#x60;multipart/form-data&#x60; request exactly as instructed by    &#x60;_links.addAttachment&#x60;, i.e. to the given &#x60;href&#x60;, using the given &#x60;method&#x60;, including every    given &#x60;form_fields&#x60; entry as its own part (in the given order), and finally a &#x60;file&#x60; part    carrying the raw file content as the *last* part of the request. This request goes directly    to the storage backend, not to the OpenProject server.  3. **Complete** (&#x60;GET&#x60; the href given in &#x60;_links.completeUpload&#x60;, i.e.    &#x60;/api/v3/attachments/{id}/uploaded&#x60;): tells the OpenProject server that the direct upload has    finished. The server verifies that the file is now present in the storage, and asynchronously    finalizes the attachment (determining its definitive content type, matching it against the    configured content type allowlist if any, running a virus scan if enabled, and creating    activity/notifications for its container). The response is the regular &#x60;Attachment&#x60; resource.    Until this step has completed, the pending attachment is not considered part of its    container and will eventually be cleaned up if it is never completed.  Only the user that prepared the upload can complete it; a pending upload that is never completed (e.g. because the client failed to perform step 2 or 3) is garbage collected after some time.  ## Container vs. containerless uploads  This endpoint (&#x60;/api/v3/attachments/prepare&#x60;) does not take a container - it prepares an attachment that is not yet attached to anything, e.g. because the containing resource (a new work package, for instance) does not exist yet. Such a containerless attachment can later be **claimed** by referencing its &#x60;id&#x60; while creating the eligible resource, exactly like containerless attachments created via &#x60;POST /api/v3/attachments&#x60;.  Most containers that support attachments (e.g. work packages, wiki pages, meetings, forum messages, activity comments) additionally expose the very same operation scoped to an existing instance of themselves, e.g. &#x60;POST /api/v3/work_packages/{id}/attachments/prepare&#x60;. Using one of those instead immediately associates the resulting attachment with that container, and is subject to the same permission requirements as &#x60;POST .../attachments&#x60; on that container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * Sending unparsable JSON in the &#x60;metadata&#x60; part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** At least one permission in any project that allows creating attachments (e.g. edit work package, add work package, edit messages, edit wiki pages; plugins might extend this list)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload.
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. This is, unlike the regular attachment creation endpoints, the *only* part of this request - no file content is sent here.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentUploadModel prepareAttachmentUpload(@jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareAttachmentUploadRequestCreation(metadata).body(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload
     * Prepares a **direct upload** of a file to OpenProject&#39;s attachment storage, bypassing the OpenProject server for the actual file transfer.  ## Why this endpoint exists  Attachments can always be created in one step by sending the raw file content together with the request, see &#x60;POST /api/v3/attachments&#x60; and the equivalent &#x60;.../attachments&#x60; endpoints of the various containers (work packages, wiki pages, meetings, ...). For large files or slow connections this means the file has to be transferred to the OpenProject server first, and then from there to the configured remote storage (e.g. an S3-compatible object store) - twice the traffic and twice the time.  When OpenProject is configured to use a remote, S3-compatible storage for attachments, it can instead hand the client a short-lived, pre-signed upload instruction that allows the client to upload the file **directly** to that storage, without the OpenProject server ever being involved in the transfer. This is what this endpoint, and its per-container siblings such as &#x60;POST /api/v3/work_packages/{id}/attachments/prepare&#x60;, are used for.  If direct uploads are not available (e.g. because attachments are stored on the local disk of the OpenProject server, or a remote storage without access-key credentials is used), this request fails with a &#x60;422&#x60; error. Clients should fall back to the regular, single-step upload in that case.  ## The three-step flow  1. **Prepare** (this request): the client tells the server the file name and size (and    optionally its content type, description and a checksum) it intends to upload. The server    creates a pending &#x60;Attachment&#x60; (&#x60;status: prepared&#x60;) without any file content yet and    responds with an &#x60;AttachmentUpload&#x60; resource. That resource contains, among others:     - &#x60;_links.addAttachment&#x60;: the direct upload instruction (&#x60;href&#x60;, &#x60;method&#x60; and &#x60;form_fields&#x60;)       for the storage backend.     - &#x60;_links.completeUpload&#x60;: the link to call once step 2 has finished.  2. **Upload**: the client sends a &#x60;multipart/form-data&#x60; request exactly as instructed by    &#x60;_links.addAttachment&#x60;, i.e. to the given &#x60;href&#x60;, using the given &#x60;method&#x60;, including every    given &#x60;form_fields&#x60; entry as its own part (in the given order), and finally a &#x60;file&#x60; part    carrying the raw file content as the *last* part of the request. This request goes directly    to the storage backend, not to the OpenProject server.  3. **Complete** (&#x60;GET&#x60; the href given in &#x60;_links.completeUpload&#x60;, i.e.    &#x60;/api/v3/attachments/{id}/uploaded&#x60;): tells the OpenProject server that the direct upload has    finished. The server verifies that the file is now present in the storage, and asynchronously    finalizes the attachment (determining its definitive content type, matching it against the    configured content type allowlist if any, running a virus scan if enabled, and creating    activity/notifications for its container). The response is the regular &#x60;Attachment&#x60; resource.    Until this step has completed, the pending attachment is not considered part of its    container and will eventually be cleaned up if it is never completed.  Only the user that prepared the upload can complete it; a pending upload that is never completed (e.g. because the client failed to perform step 2 or 3) is garbage collected after some time.  ## Container vs. containerless uploads  This endpoint (&#x60;/api/v3/attachments/prepare&#x60;) does not take a container - it prepares an attachment that is not yet attached to anything, e.g. because the containing resource (a new work package, for instance) does not exist yet. Such a containerless attachment can later be **claimed** by referencing its &#x60;id&#x60; while creating the eligible resource, exactly like containerless attachments created via &#x60;POST /api/v3/attachments&#x60;.  Most containers that support attachments (e.g. work packages, wiki pages, meetings, forum messages, activity comments) additionally expose the very same operation scoped to an existing instance of themselves, e.g. &#x60;POST /api/v3/work_packages/{id}/attachments/prepare&#x60;. Using one of those instead immediately associates the resulting attachment with that container, and is subject to the same permission requirements as &#x60;POST .../attachments&#x60; on that container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * Sending unparsable JSON in the &#x60;metadata&#x60; part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** At least one permission in any project that allows creating attachments (e.g. edit work package, add work package, edit messages, edit wiki pages; plugins might extend this list)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload.
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. This is, unlike the regular attachment creation endpoints, the *only* part of this request - no file content is sent here.
     * @return ResponseEntity&lt;AttachmentUploadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentUploadModel> prepareAttachmentUploadWithHttpInfo(@jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareAttachmentUploadRequestCreation(metadata).toEntity(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload
     * Prepares a **direct upload** of a file to OpenProject&#39;s attachment storage, bypassing the OpenProject server for the actual file transfer.  ## Why this endpoint exists  Attachments can always be created in one step by sending the raw file content together with the request, see &#x60;POST /api/v3/attachments&#x60; and the equivalent &#x60;.../attachments&#x60; endpoints of the various containers (work packages, wiki pages, meetings, ...). For large files or slow connections this means the file has to be transferred to the OpenProject server first, and then from there to the configured remote storage (e.g. an S3-compatible object store) - twice the traffic and twice the time.  When OpenProject is configured to use a remote, S3-compatible storage for attachments, it can instead hand the client a short-lived, pre-signed upload instruction that allows the client to upload the file **directly** to that storage, without the OpenProject server ever being involved in the transfer. This is what this endpoint, and its per-container siblings such as &#x60;POST /api/v3/work_packages/{id}/attachments/prepare&#x60;, are used for.  If direct uploads are not available (e.g. because attachments are stored on the local disk of the OpenProject server, or a remote storage without access-key credentials is used), this request fails with a &#x60;422&#x60; error. Clients should fall back to the regular, single-step upload in that case.  ## The three-step flow  1. **Prepare** (this request): the client tells the server the file name and size (and    optionally its content type, description and a checksum) it intends to upload. The server    creates a pending &#x60;Attachment&#x60; (&#x60;status: prepared&#x60;) without any file content yet and    responds with an &#x60;AttachmentUpload&#x60; resource. That resource contains, among others:     - &#x60;_links.addAttachment&#x60;: the direct upload instruction (&#x60;href&#x60;, &#x60;method&#x60; and &#x60;form_fields&#x60;)       for the storage backend.     - &#x60;_links.completeUpload&#x60;: the link to call once step 2 has finished.  2. **Upload**: the client sends a &#x60;multipart/form-data&#x60; request exactly as instructed by    &#x60;_links.addAttachment&#x60;, i.e. to the given &#x60;href&#x60;, using the given &#x60;method&#x60;, including every    given &#x60;form_fields&#x60; entry as its own part (in the given order), and finally a &#x60;file&#x60; part    carrying the raw file content as the *last* part of the request. This request goes directly    to the storage backend, not to the OpenProject server.  3. **Complete** (&#x60;GET&#x60; the href given in &#x60;_links.completeUpload&#x60;, i.e.    &#x60;/api/v3/attachments/{id}/uploaded&#x60;): tells the OpenProject server that the direct upload has    finished. The server verifies that the file is now present in the storage, and asynchronously    finalizes the attachment (determining its definitive content type, matching it against the    configured content type allowlist if any, running a virus scan if enabled, and creating    activity/notifications for its container). The response is the regular &#x60;Attachment&#x60; resource.    Until this step has completed, the pending attachment is not considered part of its    container and will eventually be cleaned up if it is never completed.  Only the user that prepared the upload can complete it; a pending upload that is never completed (e.g. because the client failed to perform step 2 or 3) is garbage collected after some time.  ## Container vs. containerless uploads  This endpoint (&#x60;/api/v3/attachments/prepare&#x60;) does not take a container - it prepares an attachment that is not yet attached to anything, e.g. because the containing resource (a new work package, for instance) does not exist yet. Such a containerless attachment can later be **claimed** by referencing its &#x60;id&#x60; while creating the eligible resource, exactly like containerless attachments created via &#x60;POST /api/v3/attachments&#x60;.  Most containers that support attachments (e.g. work packages, wiki pages, meetings, forum messages, activity comments) additionally expose the very same operation scoped to an existing instance of themselves, e.g. &#x60;POST /api/v3/work_packages/{id}/attachments/prepare&#x60;. Using one of those instead immediately associates the resulting attachment with that container, and is subject to the same permission requirements as &#x60;POST .../attachments&#x60; on that container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * Sending unparsable JSON in the &#x60;metadata&#x60; part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** At least one permission in any project that allows creating attachments (e.g. edit work package, add work package, edit messages, edit wiki pages; plugins might extend this list)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload.
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. This is, unlike the regular attachment creation endpoints, the *only* part of this request - no file content is sent here.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec prepareAttachmentUploadWithResponseSpec(@jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        return prepareAttachmentUploadRequestCreation(metadata);
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
     * Prepare a direct attachment upload for a post
     * Prepares a direct upload of an attachment for the given forum post, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this post as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit messages  *Note that you will only receive this error, if you are at least allowed to see the post.*
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the post to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec preparePostAttachmentUploadRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling preparePostAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metadata' is set
        if (metadata == null) {
            throw new RestClientResponseException("Missing the required parameter 'metadata' when calling preparePostAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/posts/{id}/attachments/prepare", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a post
     * Prepares a direct upload of an attachment for the given forum post, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this post as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit messages  *Note that you will only receive this error, if you are at least allowed to see the post.*
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the post to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentUploadModel preparePostAttachmentUpload(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return preparePostAttachmentUploadRequestCreation(id, metadata).body(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a post
     * Prepares a direct upload of an attachment for the given forum post, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this post as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit messages  *Note that you will only receive this error, if you are at least allowed to see the post.*
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the post to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseEntity&lt;AttachmentUploadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentUploadModel> preparePostAttachmentUploadWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return preparePostAttachmentUploadRequestCreation(id, metadata).toEntity(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a post
     * Prepares a direct upload of an attachment for the given forum post, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this post as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit messages  *Note that you will only receive this error, if you are at least allowed to see the post.*
     * <p><b>404</b> - Returned if the post does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view messages  *Note: A client without sufficient permissions shall not be able to test for the existence of a post. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the post to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec preparePostAttachmentUploadWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        return preparePostAttachmentUploadRequestCreation(id, metadata);
    }

    /**
     * Prepare a direct attachment upload for a wiki page
     * Prepares a direct upload of an attachment for the given wiki page, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this wiki page as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit wiki pages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a wiki page That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the wiki page to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec prepareWikiPageAttachmentUploadRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling prepareWikiPageAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metadata' is set
        if (metadata == null) {
            throw new RestClientResponseException("Missing the required parameter 'metadata' when calling prepareWikiPageAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/wiki_pages/{id}/attachments/prepare", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a wiki page
     * Prepares a direct upload of an attachment for the given wiki page, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this wiki page as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit wiki pages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a wiki page That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the wiki page to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentUploadModel prepareWikiPageAttachmentUpload(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareWikiPageAttachmentUploadRequestCreation(id, metadata).body(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a wiki page
     * Prepares a direct upload of an attachment for the given wiki page, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this wiki page as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit wiki pages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a wiki page That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the wiki page to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseEntity&lt;AttachmentUploadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentUploadModel> prepareWikiPageAttachmentUploadWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareWikiPageAttachmentUploadRequestCreation(id, metadata).toEntity(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a wiki page
     * Prepares a direct upload of an attachment for the given wiki page, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this wiki page as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit wiki pages  *Note that you will only receive this error, if you are at least allowed to see the wiki page*
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki pages  *Note: A client without sufficient permissions shall not be able to test for the existence of a wiki page That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the wiki page to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec prepareWikiPageAttachmentUploadWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        return prepareWikiPageAttachmentUploadRequestCreation(id, metadata);
    }

    /**
     * Prepare a direct attachment upload for a work package
     * Prepares a direct upload of an attachment for the given work package, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this work package as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the work package to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec prepareWorkPackageAttachmentUploadRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling prepareWorkPackageAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metadata' is set
        if (metadata == null) {
            throw new RestClientResponseException("Missing the required parameter 'metadata' when calling prepareWorkPackageAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/attachments/prepare", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a work package
     * Prepares a direct upload of an attachment for the given work package, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this work package as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the work package to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentUploadModel prepareWorkPackageAttachmentUpload(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareWorkPackageAttachmentUploadRequestCreation(id, metadata).body(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a work package
     * Prepares a direct upload of an attachment for the given work package, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this work package as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the work package to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseEntity&lt;AttachmentUploadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentUploadModel> prepareWorkPackageAttachmentUploadWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareWorkPackageAttachmentUploadRequestCreation(id, metadata).toEntity(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a work package
     * Prepares a direct upload of an attachment for the given work package, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this work package as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the work package to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec prepareWorkPackageAttachmentUploadWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        return prepareWorkPackageAttachmentUploadRequestCreation(id, metadata);
    }
}
