package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.Errors;
import java.io.File;
import ai.gebo.redmine.client.model.GetAttachment200Response;
import ai.gebo.redmine.client.model.UpdateAttachmentViaPutRequest;
import ai.gebo.redmine.client.model.UploadAttachmentFile201Response;

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
     * Delete attachment
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#DELETE">Delete attachment Documentation</a>
     */
    private ResponseSpec deleteAttachmentRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'attachmentId' when calling deleteAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("attachment_id", attachmentId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/attachments/{attachment_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete attachment
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#DELETE">Delete attachment Documentation</a>
     */
    public void deleteAttachment(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteAttachmentRequestCreation(format, attachmentId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Delete attachment
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#DELETE">Delete attachment Documentation</a>
     */
    public ResponseEntity<Void> deleteAttachmentWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteAttachmentRequestCreation(format, attachmentId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Delete attachment
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#DELETE">Delete attachment Documentation</a>
     */
    public ResponseSpec deleteAttachmentWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return deleteAttachmentRequestCreation(format, attachmentId, xRedmineSwitchUser);
    }

    /**
     * Download attachment by ID
     * Downloads an attachment by ID without a filename in the path. With &#x60;.json&#x60;, errors have empty response bodies; successful responses contain the original file bytes.
     * <p><b>200</b> - 
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The attachment was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadAttachmentByIdRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling downloadAttachmentById", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'attachmentId' when calling downloadAttachmentById", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("attachment_id", attachmentId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }
        if (ifNoneMatch != null) {
            headerParams.add("If-None-Match", apiClient.parameterToString(ifNoneMatch));
        }

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/attachments/download/{attachment_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download attachment by ID
     * Downloads an attachment by ID without a filename in the path. With &#x60;.json&#x60;, errors have empty response bodies; successful responses contain the original file bytes.
     * <p><b>200</b> - 
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The attachment was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File downloadAttachmentById(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downloadAttachmentByIdRequestCreation(format, attachmentId, xRedmineSwitchUser, ifNoneMatch).body(localVarReturnType);
    }

    /**
     * Download attachment by ID
     * Downloads an attachment by ID without a filename in the path. With &#x60;.json&#x60;, errors have empty response bodies; successful responses contain the original file bytes.
     * <p><b>200</b> - 
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The attachment was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> downloadAttachmentByIdWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downloadAttachmentByIdRequestCreation(format, attachmentId, xRedmineSwitchUser, ifNoneMatch).toEntity(localVarReturnType);
    }

    /**
     * Download attachment by ID
     * Downloads an attachment by ID without a filename in the path. With &#x60;.json&#x60;, errors have empty response bodies; successful responses contain the original file bytes.
     * <p><b>200</b> - 
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The attachment was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadAttachmentByIdWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        return downloadAttachmentByIdRequestCreation(format, attachmentId, xRedmineSwitchUser, ifNoneMatch);
    }

    /**
     * Download attachment file
     * 
     * <p><b>200</b> - 
     * <p><b>302</b> - Redirects to the sign-in page when an anonymous request cannot access the attachment.
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - HTML access-denied page, including when the request sends &#x60;Accept: application/json&#x60;.
     * <p><b>404</b> - Returned as an HTML error page regardless of the Accept header — the route pins the request format to html.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param attachmentId The attachmentId parameter
     * @param filename The filename parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadAttachmentFileRequestCreation(@jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nonnull String filename, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'attachmentId' when calling downloadAttachmentFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'filename' is set
        if (filename == null) {
            throw new RestClientResponseException("Missing the required parameter 'filename' when calling downloadAttachmentFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("attachment_id", attachmentId);
        pathParams.put("filename", filename);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }
        if (ifNoneMatch != null) {
            headerParams.add("If-None-Match", apiClient.parameterToString(ifNoneMatch));
        }

        final String[] localVarAccepts = { 
            "*/*", "text/html"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/attachments/download/{attachment_id}/{filename}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download attachment file
     * 
     * <p><b>200</b> - 
     * <p><b>302</b> - Redirects to the sign-in page when an anonymous request cannot access the attachment.
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - HTML access-denied page, including when the request sends &#x60;Accept: application/json&#x60;.
     * <p><b>404</b> - Returned as an HTML error page regardless of the Accept header — the route pins the request format to html.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param attachmentId The attachmentId parameter
     * @param filename The filename parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File downloadAttachmentFile(@jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nonnull String filename, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downloadAttachmentFileRequestCreation(attachmentId, filename, xRedmineSwitchUser, ifNoneMatch).body(localVarReturnType);
    }

    /**
     * Download attachment file
     * 
     * <p><b>200</b> - 
     * <p><b>302</b> - Redirects to the sign-in page when an anonymous request cannot access the attachment.
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - HTML access-denied page, including when the request sends &#x60;Accept: application/json&#x60;.
     * <p><b>404</b> - Returned as an HTML error page regardless of the Accept header — the route pins the request format to html.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param attachmentId The attachmentId parameter
     * @param filename The filename parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> downloadAttachmentFileWithHttpInfo(@jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nonnull String filename, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downloadAttachmentFileRequestCreation(attachmentId, filename, xRedmineSwitchUser, ifNoneMatch).toEntity(localVarReturnType);
    }

    /**
     * Download attachment file
     * 
     * <p><b>200</b> - 
     * <p><b>302</b> - Redirects to the sign-in page when an anonymous request cannot access the attachment.
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - HTML access-denied page, including when the request sends &#x60;Accept: application/json&#x60;.
     * <p><b>404</b> - Returned as an HTML error page regardless of the Accept header — the route pins the request format to html.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param attachmentId The attachmentId parameter
     * @param filename The filename parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadAttachmentFileWithResponseSpec(@jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nonnull String filename, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        return downloadAttachmentFileRequestCreation(attachmentId, filename, xRedmineSwitchUser, ifNoneMatch);
    }

    /**
     * Download thumbnail
     * The same thumbnail is available at &#x60;/attachments/thumbnail/{attachment_id}/{size}&#x60;. The size in the path takes precedence over the query parameter.
     * <p><b>200</b> - Redmine selects the thumbnail&#39;s &#x60;Content-Type&#x60; from the attachment&#39;s &#x60;content_type&#x60;, inferring the media type from the filename when &#x60;content_type&#x60; is empty or &#x60;application/octet-stream&#x60;. The response uses &#x60;image/png&#x60; for non-image media types.
     * <p><b>302</b> - Redirects to the sign-in page when an anonymous request cannot access the attachment.
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Empty response body for &#x60;Accept: application/json&#x60;; an HTML access-denied page for browser-style requests.
     * <p><b>404</b> - If the attachment does not exist, the error format depends on &#x60;Accept&#x60;: an empty body for JSON or an HTML error page for HTML. If the attachment exists but cannot be thumbnailed, the body is empty.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param size Desired thumbnail size in pixels (max 800). Rounded up to the nearest 50px increment. If not specified, the configured default thumbnail size is used.
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadThumbnailRequestCreation(@jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer size, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'attachmentId' when calling downloadThumbnail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("attachment_id", attachmentId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }
        if (ifNoneMatch != null) {
            headerParams.add("If-None-Match", apiClient.parameterToString(ifNoneMatch));
        }

        final String[] localVarAccepts = { 
            "image/*", "application/json", "text/html"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/attachments/thumbnail/{attachment_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download thumbnail
     * The same thumbnail is available at &#x60;/attachments/thumbnail/{attachment_id}/{size}&#x60;. The size in the path takes precedence over the query parameter.
     * <p><b>200</b> - Redmine selects the thumbnail&#39;s &#x60;Content-Type&#x60; from the attachment&#39;s &#x60;content_type&#x60;, inferring the media type from the filename when &#x60;content_type&#x60; is empty or &#x60;application/octet-stream&#x60;. The response uses &#x60;image/png&#x60; for non-image media types.
     * <p><b>302</b> - Redirects to the sign-in page when an anonymous request cannot access the attachment.
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Empty response body for &#x60;Accept: application/json&#x60;; an HTML access-denied page for browser-style requests.
     * <p><b>404</b> - If the attachment does not exist, the error format depends on &#x60;Accept&#x60;: an empty body for JSON or an HTML error page for HTML. If the attachment exists but cannot be thumbnailed, the body is empty.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param size Desired thumbnail size in pixels (max 800). Rounded up to the nearest 50px increment. If not specified, the configured default thumbnail size is used.
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File downloadThumbnail(@jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer size, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downloadThumbnailRequestCreation(attachmentId, xRedmineSwitchUser, size, ifNoneMatch).body(localVarReturnType);
    }

    /**
     * Download thumbnail
     * The same thumbnail is available at &#x60;/attachments/thumbnail/{attachment_id}/{size}&#x60;. The size in the path takes precedence over the query parameter.
     * <p><b>200</b> - Redmine selects the thumbnail&#39;s &#x60;Content-Type&#x60; from the attachment&#39;s &#x60;content_type&#x60;, inferring the media type from the filename when &#x60;content_type&#x60; is empty or &#x60;application/octet-stream&#x60;. The response uses &#x60;image/png&#x60; for non-image media types.
     * <p><b>302</b> - Redirects to the sign-in page when an anonymous request cannot access the attachment.
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Empty response body for &#x60;Accept: application/json&#x60;; an HTML access-denied page for browser-style requests.
     * <p><b>404</b> - If the attachment does not exist, the error format depends on &#x60;Accept&#x60;: an empty body for JSON or an HTML error page for HTML. If the attachment exists but cannot be thumbnailed, the body is empty.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param size Desired thumbnail size in pixels (max 800). Rounded up to the nearest 50px increment. If not specified, the configured default thumbnail size is used.
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> downloadThumbnailWithHttpInfo(@jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer size, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downloadThumbnailRequestCreation(attachmentId, xRedmineSwitchUser, size, ifNoneMatch).toEntity(localVarReturnType);
    }

    /**
     * Download thumbnail
     * The same thumbnail is available at &#x60;/attachments/thumbnail/{attachment_id}/{size}&#x60;. The size in the path takes precedence over the query parameter.
     * <p><b>200</b> - Redmine selects the thumbnail&#39;s &#x60;Content-Type&#x60; from the attachment&#39;s &#x60;content_type&#x60;, inferring the media type from the filename when &#x60;content_type&#x60; is empty or &#x60;application/octet-stream&#x60;. The response uses &#x60;image/png&#x60; for non-image media types.
     * <p><b>302</b> - Redirects to the sign-in page when an anonymous request cannot access the attachment.
     * <p><b>304</b> - The ETag matches; no response body is sent.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Empty response body for &#x60;Accept: application/json&#x60;; an HTML access-denied page for browser-style requests.
     * <p><b>404</b> - If the attachment does not exist, the error format depends on &#x60;Accept&#x60;: an empty body for JSON or an HTML error page for HTML. If the attachment exists but cannot be thumbnailed, the body is empty.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param size Desired thumbnail size in pixels (max 800). Rounded up to the nearest 50px increment. If not specified, the configured default thumbnail size is used.
     * @param ifNoneMatch ETag from a previous response. Redmine returns 304 with no response body when the tag matches.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadThumbnailWithResponseSpec(@jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer size, @jakarta.annotation.Nullable String ifNoneMatch) throws RestClientResponseException {
        return downloadThumbnailRequestCreation(attachmentId, xRedmineSwitchUser, size, ifNoneMatch);
    }

    /**
     * Show attachment
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetAttachment200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#GET">Show attachment Documentation</a>
     */
    private ResponseSpec getAttachmentRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'attachmentId' when calling getAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("attachment_id", attachmentId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetAttachment200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/attachments/{attachment_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show attachment
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetAttachment200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#GET">Show attachment Documentation</a>
     */
    public GetAttachment200Response getAttachment(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetAttachment200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getAttachmentRequestCreation(format, attachmentId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Show attachment
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;GetAttachment200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#GET">Show attachment Documentation</a>
     */
    public ResponseEntity<GetAttachment200Response> getAttachmentWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetAttachment200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getAttachmentRequestCreation(format, attachmentId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Show attachment
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#GET">Show attachment Documentation</a>
     */
    public ResponseSpec getAttachmentWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return getAttachmentRequestCreation(format, attachmentId, xRedmineSwitchUser);
    }

    /**
     * Update attachment
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateAttachmentViaPutRequest The updateAttachmentViaPutRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#PATCH">Update attachment Documentation</a>
     */
    private ResponseSpec updateAttachmentRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateAttachmentViaPutRequest updateAttachmentViaPutRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'attachmentId' when calling updateAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("attachment_id", attachmentId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/attachments/{attachment_id}.{format}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateAttachmentViaPutRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update attachment
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateAttachmentViaPutRequest The updateAttachmentViaPutRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#PATCH">Update attachment Documentation</a>
     */
    public void updateAttachment(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateAttachmentViaPutRequest updateAttachmentViaPutRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateAttachmentRequestCreation(format, attachmentId, xRedmineSwitchUser, updateAttachmentViaPutRequest).body(localVarReturnType);
    }

    /**
     * Update attachment
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateAttachmentViaPutRequest The updateAttachmentViaPutRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#PATCH">Update attachment Documentation</a>
     */
    public ResponseEntity<Void> updateAttachmentWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateAttachmentViaPutRequest updateAttachmentViaPutRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateAttachmentRequestCreation(format, attachmentId, xRedmineSwitchUser, updateAttachmentViaPutRequest).toEntity(localVarReturnType);
    }

    /**
     * Update attachment
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateAttachmentViaPutRequest The updateAttachmentViaPutRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#PATCH">Update attachment Documentation</a>
     */
    public ResponseSpec updateAttachmentWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateAttachmentViaPutRequest updateAttachmentViaPutRequest) throws RestClientResponseException {
        return updateAttachmentRequestCreation(format, attachmentId, xRedmineSwitchUser, updateAttachmentViaPutRequest);
    }

    /**
     * Update attachment (PUT)
     * Equivalent to PATCH on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateAttachmentViaPutRequest The updateAttachmentViaPutRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#PATCH">Update attachment (PUT) Documentation</a>
     */
    private ResponseSpec updateAttachmentViaPutRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateAttachmentViaPutRequest updateAttachmentViaPutRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateAttachmentViaPut", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'attachmentId' when calling updateAttachmentViaPut", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("attachment_id", attachmentId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/attachments/{attachment_id}.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, updateAttachmentViaPutRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update attachment (PUT)
     * Equivalent to PATCH on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateAttachmentViaPutRequest The updateAttachmentViaPutRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#PATCH">Update attachment (PUT) Documentation</a>
     */
    public void updateAttachmentViaPut(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateAttachmentViaPutRequest updateAttachmentViaPutRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateAttachmentViaPutRequestCreation(format, attachmentId, xRedmineSwitchUser, updateAttachmentViaPutRequest).body(localVarReturnType);
    }

    /**
     * Update attachment (PUT)
     * Equivalent to PATCH on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateAttachmentViaPutRequest The updateAttachmentViaPutRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#PATCH">Update attachment (PUT) Documentation</a>
     */
    public ResponseEntity<Void> updateAttachmentViaPutWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateAttachmentViaPutRequest updateAttachmentViaPutRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateAttachmentViaPutRequestCreation(format, attachmentId, xRedmineSwitchUser, updateAttachmentViaPutRequest).toEntity(localVarReturnType);
    }

    /**
     * Update attachment (PUT)
     * Equivalent to PATCH on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param attachmentId The attachmentId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateAttachmentViaPutRequest The updateAttachmentViaPutRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Attachments#PATCH">Update attachment (PUT) Documentation</a>
     */
    public ResponseSpec updateAttachmentViaPutWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer attachmentId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateAttachmentViaPutRequest updateAttachmentViaPutRequest) throws RestClientResponseException {
        return updateAttachmentViaPutRequestCreation(format, attachmentId, xRedmineSwitchUser, updateAttachmentViaPutRequest);
    }

    /**
     * Upload attachment file
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>406</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param filename Filename for the uploaded file. If not provided, a random hexadecimal name is generated.
     * @param contentType MIME type of the file (e.g., &#x60;application/pdf&#x60;, &#x60;image/png&#x60;). If not provided, auto-detected from the filename.
     * @param body The body parameter
     * @return UploadAttachmentFile201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_api#Attaching-files">Upload attachment file Documentation</a>
     */
    private ResponseSpec uploadAttachmentFileRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String filename, @jakarta.annotation.Nullable String contentType, @jakarta.annotation.Nullable File body) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling uploadAttachmentFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filename", filename));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "content_type", contentType));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/octet-stream"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<UploadAttachmentFile201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/uploads.{format}", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload attachment file
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>406</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param filename Filename for the uploaded file. If not provided, a random hexadecimal name is generated.
     * @param contentType MIME type of the file (e.g., &#x60;application/pdf&#x60;, &#x60;image/png&#x60;). If not provided, auto-detected from the filename.
     * @param body The body parameter
     * @return UploadAttachmentFile201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_api#Attaching-files">Upload attachment file Documentation</a>
     */
    public UploadAttachmentFile201Response uploadAttachmentFile(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String filename, @jakarta.annotation.Nullable String contentType, @jakarta.annotation.Nullable File body) throws RestClientResponseException {
        ParameterizedTypeReference<UploadAttachmentFile201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return uploadAttachmentFileRequestCreation(format, xRedmineSwitchUser, filename, contentType, body).body(localVarReturnType);
    }

    /**
     * Upload attachment file
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>406</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param filename Filename for the uploaded file. If not provided, a random hexadecimal name is generated.
     * @param contentType MIME type of the file (e.g., &#x60;application/pdf&#x60;, &#x60;image/png&#x60;). If not provided, auto-detected from the filename.
     * @param body The body parameter
     * @return ResponseEntity&lt;UploadAttachmentFile201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_api#Attaching-files">Upload attachment file Documentation</a>
     */
    public ResponseEntity<UploadAttachmentFile201Response> uploadAttachmentFileWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String filename, @jakarta.annotation.Nullable String contentType, @jakarta.annotation.Nullable File body) throws RestClientResponseException {
        ParameterizedTypeReference<UploadAttachmentFile201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return uploadAttachmentFileRequestCreation(format, xRedmineSwitchUser, filename, contentType, body).toEntity(localVarReturnType);
    }

    /**
     * Upload attachment file
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>406</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param filename Filename for the uploaded file. If not provided, a random hexadecimal name is generated.
     * @param contentType MIME type of the file (e.g., &#x60;application/pdf&#x60;, &#x60;image/png&#x60;). If not provided, auto-detected from the filename.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_api#Attaching-files">Upload attachment file Documentation</a>
     */
    public ResponseSpec uploadAttachmentFileWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String filename, @jakarta.annotation.Nullable String contentType, @jakarta.annotation.Nullable File body) throws RestClientResponseException {
        return uploadAttachmentFileRequestCreation(format, xRedmineSwitchUser, filename, contentType, body);
    }
}
