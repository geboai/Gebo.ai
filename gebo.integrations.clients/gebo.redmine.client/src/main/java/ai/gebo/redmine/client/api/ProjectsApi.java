package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.CreateProject201Response;
import ai.gebo.redmine.client.model.CreateProjectRequest;
import ai.gebo.redmine.client.model.Errors;
import ai.gebo.redmine.client.model.FrameworkError;
import ai.gebo.redmine.client.model.GetProjects200Response;
import ai.gebo.redmine.client.model.UpdateProjectRequest;

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
public class ProjectsApi {
    private ApiClient apiClient;

    public ProjectsApi() {
        this(new ApiClient());
    }

    public ProjectsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Archive project
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Archiving-a-project">Archive project Documentation</a>
     */
    private ResponseSpec archiveProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling archiveProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling archiveProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/archive.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Archive project
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Archiving-a-project">Archive project Documentation</a>
     */
    public void archiveProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        archiveProjectRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Archive project
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Archiving-a-project">Archive project Documentation</a>
     */
    public ResponseEntity<Void> archiveProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return archiveProjectRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Archive project
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Archiving-a-project">Archive project Documentation</a>
     */
    public ResponseSpec archiveProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return archiveProjectRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Archive project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Archiving-a-project">Archive project (POST) Documentation</a>
     */
    private ResponseSpec archiveProjectViaPostRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling archiveProjectViaPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling archiveProjectViaPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/archive.{format}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Archive project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Archiving-a-project">Archive project (POST) Documentation</a>
     */
    public void archiveProjectViaPost(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        archiveProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Archive project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Archiving-a-project">Archive project (POST) Documentation</a>
     */
    public ResponseEntity<Void> archiveProjectViaPostWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return archiveProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Archive project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Archiving-a-project">Archive project (POST) Documentation</a>
     */
    public ResponseSpec archiveProjectViaPostWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return archiveProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Close project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec closeProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling closeProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling closeProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/close.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Close project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void closeProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        closeProjectRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Close project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> closeProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return closeProjectRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Close project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec closeProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return closeProjectRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Close project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec closeProjectViaPostRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling closeProjectViaPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling closeProjectViaPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/close.{format}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Close project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void closeProjectViaPost(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        closeProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Close project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> closeProjectViaPostWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return closeProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Close project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec closeProjectViaPostWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return closeProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Create project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createProjectRequest The createProjectRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateProject201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Creating-a-project">Create project Documentation</a>
     */
    private ResponseSpec createProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateProjectRequest createProjectRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createProjectRequest' is set
        if (createProjectRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createProjectRequest' when calling createProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

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

        ParameterizedTypeReference<CreateProject201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createProjectRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createProjectRequest The createProjectRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateProject201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Creating-a-project">Create project Documentation</a>
     */
    public CreateProject201Response createProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateProjectRequest createProjectRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateProject201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createProjectRequestCreation(format, createProjectRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createProjectRequest The createProjectRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateProject201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Creating-a-project">Create project Documentation</a>
     */
    public ResponseEntity<CreateProject201Response> createProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateProjectRequest createProjectRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateProject201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createProjectRequestCreation(format, createProjectRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createProjectRequest The createProjectRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Creating-a-project">Create project Documentation</a>
     */
    public ResponseSpec createProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateProjectRequest createProjectRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return createProjectRequestCreation(format, createProjectRequest, xRedmineSwitchUser);
    }

    /**
     * Delete project
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Deleting-a-project">Delete project Documentation</a>
     */
    private ResponseSpec deleteProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling deleteProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete project
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Deleting-a-project">Delete project Documentation</a>
     */
    public void deleteProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteProjectRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Delete project
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Deleting-a-project">Delete project Documentation</a>
     */
    public ResponseEntity<Void> deleteProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteProjectRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Delete project
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Deleting-a-project">Delete project Documentation</a>
     */
    public ResponseSpec deleteProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return deleteProjectRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Show project
     * Authenticated users, including administrators, receive 403 for archived projects.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;trackers&#x60; (available issue trackers), &#x60;issue_categories&#x60; (issue categories), &#x60;time_entry_activities&#x60; (time entry activity types), &#x60;enabled_modules&#x60; (enabled modules/features), &#x60;issue_custom_fields&#x60; (custom fields for issues)
     * @return CreateProject201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Showing-a-project">Show project Documentation</a>
     */
    private ResponseSpec getProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "include", include));

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

        ParameterizedTypeReference<CreateProject201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show project
     * Authenticated users, including administrators, receive 403 for archived projects.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;trackers&#x60; (available issue trackers), &#x60;issue_categories&#x60; (issue categories), &#x60;time_entry_activities&#x60; (time entry activity types), &#x60;enabled_modules&#x60; (enabled modules/features), &#x60;issue_custom_fields&#x60; (custom fields for issues)
     * @return CreateProject201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Showing-a-project">Show project Documentation</a>
     */
    public CreateProject201Response getProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<CreateProject201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectRequestCreation(format, projectId, xRedmineSwitchUser, include).body(localVarReturnType);
    }

    /**
     * Show project
     * Authenticated users, including administrators, receive 403 for archived projects.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;trackers&#x60; (available issue trackers), &#x60;issue_categories&#x60; (issue categories), &#x60;time_entry_activities&#x60; (time entry activity types), &#x60;enabled_modules&#x60; (enabled modules/features), &#x60;issue_custom_fields&#x60; (custom fields for issues)
     * @return ResponseEntity&lt;CreateProject201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Showing-a-project">Show project Documentation</a>
     */
    public ResponseEntity<CreateProject201Response> getProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<CreateProject201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectRequestCreation(format, projectId, xRedmineSwitchUser, include).toEntity(localVarReturnType);
    }

    /**
     * Show project
     * Authenticated users, including administrators, receive 403 for archived projects.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;trackers&#x60; (available issue trackers), &#x60;issue_categories&#x60; (issue categories), &#x60;time_entry_activities&#x60; (time entry activity types), &#x60;enabled_modules&#x60; (enabled modules/features), &#x60;issue_custom_fields&#x60; (custom fields for issues)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Showing-a-project">Show project Documentation</a>
     */
    public ResponseSpec getProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        return getProjectRequestCreation(format, projectId, xRedmineSwitchUser, include);
    }

    /**
     * List projects
     * Lists visible projects. The default status filter selects active projects. A saved query or explicit filters can select other statuses.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The saved query was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param include Comma-separated list of associated data to include for each project. Values: &#x60;trackers&#x60; (available issue trackers), &#x60;issue_categories&#x60; (issue categories), &#x60;time_entry_activities&#x60; (time entry activity types), &#x60;enabled_modules&#x60; (enabled modules/features), &#x60;issue_custom_fields&#x60; (custom fields for issues)
     * @param status Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;1|5&#x60;, &#x60;!1&#x60;
     * @param id Filter by project ID. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;mine&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects) Examples: &#x60;1|2|3&#x60;, &#x60;mine&#x60;, &#x60;!mine&#x60;, &#x60;mine|bookmarks&#x60;
     * @param name Filter by project name. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by project description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param parentId Filter by parent project. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any, i.e. is a subproject), &#x60;!*&#x60; (none, i.e. is a root project) Special values: &#x60;mine&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects) Examples: &#x60;1|2&#x60;, &#x60;mine&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param isPublic Filter by public/private status. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (public), &#x60;0&#x60; (private) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last update date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Available fields: &#x60;name&#x60;, &#x60;status&#x60;, &#x60;short_description&#x60;, &#x60;homepage&#x60;, &#x60;identifier&#x60;, &#x60;parent_id&#x60;, &#x60;is_public&#x60;, &#x60;created_on&#x60;, &#x60;updated_on&#x60;. Sortable custom-field columns use &#x60;cf_&lt;id&gt;&#x60;. Example: &#x60;name:desc&#x60;.
     * @return GetProjects200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Listing-projects">List projects Documentation</a>
     */
    private ResponseSpec getProjectsRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String isPublic, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getProjects", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "nometa", nometa));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "include", include));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "id", id));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "description", description));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "parent_id", parentId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "is_public", isPublic));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_on", createdOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_on", updatedOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query_id", queryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "f[]", f));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "op", op));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "v", v));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cf_x", cfX));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }
        if (xRedmineNometa != null) {
            headerParams.add("X-Redmine-Nometa", apiClient.parameterToString(xRedmineNometa));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetProjects200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List projects
     * Lists visible projects. The default status filter selects active projects. A saved query or explicit filters can select other statuses.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The saved query was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param include Comma-separated list of associated data to include for each project. Values: &#x60;trackers&#x60; (available issue trackers), &#x60;issue_categories&#x60; (issue categories), &#x60;time_entry_activities&#x60; (time entry activity types), &#x60;enabled_modules&#x60; (enabled modules/features), &#x60;issue_custom_fields&#x60; (custom fields for issues)
     * @param status Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;1|5&#x60;, &#x60;!1&#x60;
     * @param id Filter by project ID. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;mine&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects) Examples: &#x60;1|2|3&#x60;, &#x60;mine&#x60;, &#x60;!mine&#x60;, &#x60;mine|bookmarks&#x60;
     * @param name Filter by project name. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by project description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param parentId Filter by parent project. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any, i.e. is a subproject), &#x60;!*&#x60; (none, i.e. is a root project) Special values: &#x60;mine&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects) Examples: &#x60;1|2&#x60;, &#x60;mine&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param isPublic Filter by public/private status. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (public), &#x60;0&#x60; (private) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last update date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Available fields: &#x60;name&#x60;, &#x60;status&#x60;, &#x60;short_description&#x60;, &#x60;homepage&#x60;, &#x60;identifier&#x60;, &#x60;parent_id&#x60;, &#x60;is_public&#x60;, &#x60;created_on&#x60;, &#x60;updated_on&#x60;. Sortable custom-field columns use &#x60;cf_&lt;id&gt;&#x60;. Example: &#x60;name:desc&#x60;.
     * @return GetProjects200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Listing-projects">List projects Documentation</a>
     */
    public GetProjects200Response getProjects(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String isPublic, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<GetProjects200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectsRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, include, status, id, name, description, parentId, isPublic, createdOn, updatedOn, page, queryId, f, op, v, cfX, sort).body(localVarReturnType);
    }

    /**
     * List projects
     * Lists visible projects. The default status filter selects active projects. A saved query or explicit filters can select other statuses.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The saved query was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param include Comma-separated list of associated data to include for each project. Values: &#x60;trackers&#x60; (available issue trackers), &#x60;issue_categories&#x60; (issue categories), &#x60;time_entry_activities&#x60; (time entry activity types), &#x60;enabled_modules&#x60; (enabled modules/features), &#x60;issue_custom_fields&#x60; (custom fields for issues)
     * @param status Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;1|5&#x60;, &#x60;!1&#x60;
     * @param id Filter by project ID. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;mine&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects) Examples: &#x60;1|2|3&#x60;, &#x60;mine&#x60;, &#x60;!mine&#x60;, &#x60;mine|bookmarks&#x60;
     * @param name Filter by project name. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by project description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param parentId Filter by parent project. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any, i.e. is a subproject), &#x60;!*&#x60; (none, i.e. is a root project) Special values: &#x60;mine&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects) Examples: &#x60;1|2&#x60;, &#x60;mine&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param isPublic Filter by public/private status. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (public), &#x60;0&#x60; (private) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last update date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Available fields: &#x60;name&#x60;, &#x60;status&#x60;, &#x60;short_description&#x60;, &#x60;homepage&#x60;, &#x60;identifier&#x60;, &#x60;parent_id&#x60;, &#x60;is_public&#x60;, &#x60;created_on&#x60;, &#x60;updated_on&#x60;. Sortable custom-field columns use &#x60;cf_&lt;id&gt;&#x60;. Example: &#x60;name:desc&#x60;.
     * @return ResponseEntity&lt;GetProjects200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Listing-projects">List projects Documentation</a>
     */
    public ResponseEntity<GetProjects200Response> getProjectsWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String isPublic, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<GetProjects200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectsRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, include, status, id, name, description, parentId, isPublic, createdOn, updatedOn, page, queryId, f, op, v, cfX, sort).toEntity(localVarReturnType);
    }

    /**
     * List projects
     * Lists visible projects. The default status filter selects active projects. A saved query or explicit filters can select other statuses.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The saved query was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param include Comma-separated list of associated data to include for each project. Values: &#x60;trackers&#x60; (available issue trackers), &#x60;issue_categories&#x60; (issue categories), &#x60;time_entry_activities&#x60; (time entry activity types), &#x60;enabled_modules&#x60; (enabled modules/features), &#x60;issue_custom_fields&#x60; (custom fields for issues)
     * @param status Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;1|5&#x60;, &#x60;!1&#x60;
     * @param id Filter by project ID. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;mine&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects) Examples: &#x60;1|2|3&#x60;, &#x60;mine&#x60;, &#x60;!mine&#x60;, &#x60;mine|bookmarks&#x60;
     * @param name Filter by project name. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by project description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param parentId Filter by parent project. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any, i.e. is a subproject), &#x60;!*&#x60; (none, i.e. is a root project) Special values: &#x60;mine&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects) Examples: &#x60;1|2&#x60;, &#x60;mine&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param isPublic Filter by public/private status. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (public), &#x60;0&#x60; (private) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last update date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Available fields: &#x60;name&#x60;, &#x60;status&#x60;, &#x60;short_description&#x60;, &#x60;homepage&#x60;, &#x60;identifier&#x60;, &#x60;parent_id&#x60;, &#x60;is_public&#x60;, &#x60;created_on&#x60;, &#x60;updated_on&#x60;. Sortable custom-field columns use &#x60;cf_&lt;id&gt;&#x60;. Example: &#x60;name:desc&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Listing-projects">List projects Documentation</a>
     */
    public ResponseSpec getProjectsWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String id, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String isPublic, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getProjectsRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, include, status, id, name, description, parentId, isPublic, createdOn, updatedOn, page, queryId, f, op, v, cfX, sort);
    }

    /**
     * Reopen project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reopenProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling reopenProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling reopenProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/reopen.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reopen project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void reopenProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        reopenProjectRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Reopen project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> reopenProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reopenProjectRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Reopen project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reopenProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return reopenProjectRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Reopen project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reopenProjectViaPostRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling reopenProjectViaPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling reopenProjectViaPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/reopen.{format}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reopen project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void reopenProjectViaPost(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        reopenProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Reopen project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> reopenProjectViaPostWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reopenProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Reopen project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reopenProjectViaPostWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return reopenProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Unarchive project
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Unarchiving-a-project">Unarchive project Documentation</a>
     */
    private ResponseSpec unarchiveProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling unarchiveProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling unarchiveProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/unarchive.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unarchive project
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Unarchiving-a-project">Unarchive project Documentation</a>
     */
    public void unarchiveProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        unarchiveProjectRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Unarchive project
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Unarchiving-a-project">Unarchive project Documentation</a>
     */
    public ResponseEntity<Void> unarchiveProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unarchiveProjectRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Unarchive project
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Unarchiving-a-project">Unarchive project Documentation</a>
     */
    public ResponseSpec unarchiveProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return unarchiveProjectRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Unarchive project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Unarchiving-a-project">Unarchive project (POST) Documentation</a>
     */
    private ResponseSpec unarchiveProjectViaPostRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling unarchiveProjectViaPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling unarchiveProjectViaPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/unarchive.{format}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unarchive project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Unarchiving-a-project">Unarchive project (POST) Documentation</a>
     */
    public void unarchiveProjectViaPost(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        unarchiveProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Unarchive project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Unarchiving-a-project">Unarchive project (POST) Documentation</a>
     */
    public ResponseEntity<Void> unarchiveProjectViaPostWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unarchiveProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Unarchive project (POST)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Unarchiving-a-project">Unarchive project (POST) Documentation</a>
     */
    public ResponseSpec unarchiveProjectViaPostWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return unarchiveProjectViaPostRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Update project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateProjectRequest The updateProjectRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Updating-a-project">Update project Documentation</a>
     */
    private ResponseSpec updateProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateProjectRequest updateProjectRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling updateProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/projects/{project_id}.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, updateProjectRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateProjectRequest The updateProjectRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Updating-a-project">Update project Documentation</a>
     */
    public void updateProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateProjectRequest updateProjectRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateProjectRequestCreation(format, projectId, xRedmineSwitchUser, updateProjectRequest).body(localVarReturnType);
    }

    /**
     * Update project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateProjectRequest The updateProjectRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Updating-a-project">Update project Documentation</a>
     */
    public ResponseEntity<Void> updateProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateProjectRequest updateProjectRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateProjectRequestCreation(format, projectId, xRedmineSwitchUser, updateProjectRequest).toEntity(localVarReturnType);
    }

    /**
     * Update project
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateProjectRequest The updateProjectRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Updating-a-project">Update project Documentation</a>
     */
    public ResponseSpec updateProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateProjectRequest updateProjectRequest) throws RestClientResponseException {
        return updateProjectRequestCreation(format, projectId, xRedmineSwitchUser, updateProjectRequest);
    }

    /**
     * Update project (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateProjectRequest The updateProjectRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Updating-a-project">Update project (PATCH) Documentation</a>
     */
    private ResponseSpec updateProjectViaPatchRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateProjectRequest updateProjectRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateProjectViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling updateProjectViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/projects/{project_id}.{format}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateProjectRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update project (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateProjectRequest The updateProjectRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Updating-a-project">Update project (PATCH) Documentation</a>
     */
    public void updateProjectViaPatch(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateProjectRequest updateProjectRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateProjectViaPatchRequestCreation(format, projectId, xRedmineSwitchUser, updateProjectRequest).body(localVarReturnType);
    }

    /**
     * Update project (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateProjectRequest The updateProjectRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Updating-a-project">Update project (PATCH) Documentation</a>
     */
    public ResponseEntity<Void> updateProjectViaPatchWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateProjectRequest updateProjectRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateProjectViaPatchRequestCreation(format, projectId, xRedmineSwitchUser, updateProjectRequest).toEntity(localVarReturnType);
    }

    /**
     * Update project (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateProjectRequest The updateProjectRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Projects#Updating-a-project">Update project (PATCH) Documentation</a>
     */
    public ResponseSpec updateProjectViaPatchWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateProjectRequest updateProjectRequest) throws RestClientResponseException {
        return updateProjectViaPatchRequestCreation(format, projectId, xRedmineSwitchUser, updateProjectRequest);
    }
}
