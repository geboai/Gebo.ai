package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.AttachmentUploadModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.WikiPageModel;

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
public class WikiPagesApi {
    private ApiClient apiClient;

    public WikiPagesApi() {
        this(new ApiClient());
    }

    public WikiPagesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View Wiki Page
     * Retrieve an individual wiki page as identified by the id parameter
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki page in the page&#39;s project
     * @param id Wiki page identifier
     * @return WikiPageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getWikiPageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<WikiPageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/wiki_pages/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View Wiki Page
     * Retrieve an individual wiki page as identified by the id parameter
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki page in the page&#39;s project
     * @param id Wiki page identifier
     * @return WikiPageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WikiPageModel getWikiPage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<WikiPageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiPageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View Wiki Page
     * Retrieve an individual wiki page as identified by the id parameter
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki page in the page&#39;s project
     * @param id Wiki page identifier
     * @return ResponseEntity&lt;WikiPageModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WikiPageModel> getWikiPageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<WikiPageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiPageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View Wiki Page
     * Retrieve an individual wiki page as identified by the id parameter
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the wiki page does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view wiki page in the page&#39;s project
     * @param id Wiki page identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getWikiPageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getWikiPageRequestCreation(id);
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
}
