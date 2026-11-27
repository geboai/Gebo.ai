package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.CreateFileRequest;
import ai.gebo.redmine.client.model.GetFiles200Response;

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
public class FilesApi {
    private ApiClient apiClient;

    public FilesApi() {
        this(new ApiClient());
    }

    public FilesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create file
     * An invalid or unknown upload token results in 404, not 400 — the controller&#39;s 400 render references an API template that does not exist, and the MissingTemplate handler responds with 404.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createFileRequest The createFileRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Files#POST">Create file Documentation</a>
     */
    private ResponseSpec createFileRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateFileRequest createFileRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling createFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createFileRequest' is set
        if (createFileRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createFileRequest' when calling createFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/files.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createFileRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create file
     * An invalid or unknown upload token results in 404, not 400 — the controller&#39;s 400 render references an API template that does not exist, and the MissingTemplate handler responds with 404.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createFileRequest The createFileRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Files#POST">Create file Documentation</a>
     */
    public void createFile(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateFileRequest createFileRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        createFileRequestCreation(format, projectId, createFileRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create file
     * An invalid or unknown upload token results in 404, not 400 — the controller&#39;s 400 render references an API template that does not exist, and the MissingTemplate handler responds with 404.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createFileRequest The createFileRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Files#POST">Create file Documentation</a>
     */
    public ResponseEntity<Void> createFileWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateFileRequest createFileRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createFileRequestCreation(format, projectId, createFileRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create file
     * An invalid or unknown upload token results in 404, not 400 — the controller&#39;s 400 render references an API template that does not exist, and the MissingTemplate handler responds with 404.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createFileRequest The createFileRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Files#POST">Create file Documentation</a>
     */
    public ResponseSpec createFileWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateFileRequest createFileRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return createFileRequestCreation(format, projectId, createFileRequest, xRedmineSwitchUser);
    }

    /**
     * List files
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param sort Sort field and optional direction: &#x60;filename&#x60;, &#x60;created_on&#x60;, &#x60;size&#x60;, or &#x60;downloads&#x60;, followed by &#x60;:asc&#x60; or &#x60;:desc&#x60;. Defaults to &#x60;filename:asc&#x60;.
     * @return GetFiles200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Files#GET">List files Documentation</a>
     */
    private ResponseSpec getFilesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

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

        ParameterizedTypeReference<GetFiles200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/files.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List files
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param sort Sort field and optional direction: &#x60;filename&#x60;, &#x60;created_on&#x60;, &#x60;size&#x60;, or &#x60;downloads&#x60;, followed by &#x60;:asc&#x60; or &#x60;:desc&#x60;. Defaults to &#x60;filename:asc&#x60;.
     * @return GetFiles200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Files#GET">List files Documentation</a>
     */
    public GetFiles200Response getFiles(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<GetFiles200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getFilesRequestCreation(format, projectId, xRedmineSwitchUser, sort).body(localVarReturnType);
    }

    /**
     * List files
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param sort Sort field and optional direction: &#x60;filename&#x60;, &#x60;created_on&#x60;, &#x60;size&#x60;, or &#x60;downloads&#x60;, followed by &#x60;:asc&#x60; or &#x60;:desc&#x60;. Defaults to &#x60;filename:asc&#x60;.
     * @return ResponseEntity&lt;GetFiles200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Files#GET">List files Documentation</a>
     */
    public ResponseEntity<GetFiles200Response> getFilesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<GetFiles200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getFilesRequestCreation(format, projectId, xRedmineSwitchUser, sort).toEntity(localVarReturnType);
    }

    /**
     * List files
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param sort Sort field and optional direction: &#x60;filename&#x60;, &#x60;created_on&#x60;, &#x60;size&#x60;, or &#x60;downloads&#x60;, followed by &#x60;:asc&#x60; or &#x60;:desc&#x60;. Defaults to &#x60;filename:asc&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Files#GET">List files Documentation</a>
     */
    public ResponseSpec getFilesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getFilesRequestCreation(format, projectId, xRedmineSwitchUser, sort);
    }
}
