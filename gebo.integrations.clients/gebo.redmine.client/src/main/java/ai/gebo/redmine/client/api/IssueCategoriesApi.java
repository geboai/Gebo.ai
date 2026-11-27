package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.CreateIssueCategory201Response;
import ai.gebo.redmine.client.model.CreateIssueCategoryRequest;
import ai.gebo.redmine.client.model.Errors;
import ai.gebo.redmine.client.model.GetIssueCategories200Response;
import ai.gebo.redmine.client.model.UpdateIssueCategoryRequest;

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
public class IssueCategoriesApi {
    private ApiClient apiClient;

    public IssueCategoriesApi() {
        this(new ApiClient());
    }

    public IssueCategoriesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create issue category
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createIssueCategoryRequest The createIssueCategoryRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateIssueCategory201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#POST">Create issue category Documentation</a>
     */
    private ResponseSpec createIssueCategoryRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateIssueCategoryRequest createIssueCategoryRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createIssueCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling createIssueCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createIssueCategoryRequest' is set
        if (createIssueCategoryRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createIssueCategoryRequest' when calling createIssueCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CreateIssueCategory201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/issue_categories.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createIssueCategoryRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create issue category
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createIssueCategoryRequest The createIssueCategoryRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateIssueCategory201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#POST">Create issue category Documentation</a>
     */
    public CreateIssueCategory201Response createIssueCategory(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateIssueCategoryRequest createIssueCategoryRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateIssueCategory201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createIssueCategoryRequestCreation(format, projectId, createIssueCategoryRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create issue category
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createIssueCategoryRequest The createIssueCategoryRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateIssueCategory201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#POST">Create issue category Documentation</a>
     */
    public ResponseEntity<CreateIssueCategory201Response> createIssueCategoryWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateIssueCategoryRequest createIssueCategoryRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateIssueCategory201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createIssueCategoryRequestCreation(format, projectId, createIssueCategoryRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create issue category
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createIssueCategoryRequest The createIssueCategoryRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#POST">Create issue category Documentation</a>
     */
    public ResponseSpec createIssueCategoryWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateIssueCategoryRequest createIssueCategoryRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return createIssueCategoryRequestCreation(format, projectId, createIssueCategoryRequest, xRedmineSwitchUser);
    }

    /**
     * Delete issue category
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param reassignToId ID of another issue category to reassign issues to before deleting this category. If the category has associated issues and this parameter is not provided, the issues will have their category unset.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#DELETE">Delete issue category Documentation</a>
     */
    private ResponseSpec deleteIssueCategoryRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteIssueCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueCategoryId' is set
        if (issueCategoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueCategoryId' when calling deleteIssueCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("issue_category_id", issueCategoryId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reassign_to_id", reassignToId));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/issue_categories/{issue_category_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete issue category
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param reassignToId ID of another issue category to reassign issues to before deleting this category. If the category has associated issues and this parameter is not provided, the issues will have their category unset.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#DELETE">Delete issue category Documentation</a>
     */
    public void deleteIssueCategory(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteIssueCategoryRequestCreation(format, issueCategoryId, xRedmineSwitchUser, reassignToId).body(localVarReturnType);
    }

    /**
     * Delete issue category
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param reassignToId ID of another issue category to reassign issues to before deleting this category. If the category has associated issues and this parameter is not provided, the issues will have their category unset.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#DELETE">Delete issue category Documentation</a>
     */
    public ResponseEntity<Void> deleteIssueCategoryWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteIssueCategoryRequestCreation(format, issueCategoryId, xRedmineSwitchUser, reassignToId).toEntity(localVarReturnType);
    }

    /**
     * Delete issue category
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param reassignToId ID of another issue category to reassign issues to before deleting this category. If the category has associated issues and this parameter is not provided, the issues will have their category unset.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#DELETE">Delete issue category Documentation</a>
     */
    public ResponseSpec deleteIssueCategoryWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        return deleteIssueCategoryRequestCreation(format, issueCategoryId, xRedmineSwitchUser, reassignToId);
    }

    /**
     * List issue categories
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @return GetIssueCategories200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#GET">List issue categories Documentation</a>
     */
    private ResponseSpec getIssueCategoriesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getIssueCategories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getIssueCategories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "nometa", nometa));

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

        ParameterizedTypeReference<GetIssueCategories200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/issue_categories.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List issue categories
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @return GetIssueCategories200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#GET">List issue categories Documentation</a>
     */
    public GetIssueCategories200Response getIssueCategories(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssueCategories200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssueCategoriesRequestCreation(format, projectId, nometa, xRedmineSwitchUser, xRedmineNometa).body(localVarReturnType);
    }

    /**
     * List issue categories
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @return ResponseEntity&lt;GetIssueCategories200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#GET">List issue categories Documentation</a>
     */
    public ResponseEntity<GetIssueCategories200Response> getIssueCategoriesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssueCategories200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssueCategoriesRequestCreation(format, projectId, nometa, xRedmineSwitchUser, xRedmineNometa).toEntity(localVarReturnType);
    }

    /**
     * List issue categories
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#GET">List issue categories Documentation</a>
     */
    public ResponseSpec getIssueCategoriesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa) throws RestClientResponseException {
        return getIssueCategoriesRequestCreation(format, projectId, nometa, xRedmineSwitchUser, xRedmineNometa);
    }

    /**
     * Show issue category
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateIssueCategory201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#GET-2">Show issue category Documentation</a>
     */
    private ResponseSpec getIssueCategoryRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getIssueCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueCategoryId' is set
        if (issueCategoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueCategoryId' when calling getIssueCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("issue_category_id", issueCategoryId);

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

        ParameterizedTypeReference<CreateIssueCategory201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/issue_categories/{issue_category_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show issue category
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateIssueCategory201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#GET-2">Show issue category Documentation</a>
     */
    public CreateIssueCategory201Response getIssueCategory(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateIssueCategory201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssueCategoryRequestCreation(format, issueCategoryId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Show issue category
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateIssueCategory201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#GET-2">Show issue category Documentation</a>
     */
    public ResponseEntity<CreateIssueCategory201Response> getIssueCategoryWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateIssueCategory201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssueCategoryRequestCreation(format, issueCategoryId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Show issue category
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#GET-2">Show issue category Documentation</a>
     */
    public ResponseSpec getIssueCategoryWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return getIssueCategoryRequestCreation(format, issueCategoryId, xRedmineSwitchUser);
    }

    /**
     * Update issue category
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueCategoryRequest The updateIssueCategoryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#PUT">Update issue category Documentation</a>
     */
    private ResponseSpec updateIssueCategoryRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueCategoryRequest updateIssueCategoryRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateIssueCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueCategoryId' is set
        if (issueCategoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueCategoryId' when calling updateIssueCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("issue_category_id", issueCategoryId);

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
        return apiClient.invokeAPI("/issue_categories/{issue_category_id}.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, updateIssueCategoryRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update issue category
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueCategoryRequest The updateIssueCategoryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#PUT">Update issue category Documentation</a>
     */
    public void updateIssueCategory(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueCategoryRequest updateIssueCategoryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateIssueCategoryRequestCreation(format, issueCategoryId, xRedmineSwitchUser, updateIssueCategoryRequest).body(localVarReturnType);
    }

    /**
     * Update issue category
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueCategoryRequest The updateIssueCategoryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#PUT">Update issue category Documentation</a>
     */
    public ResponseEntity<Void> updateIssueCategoryWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueCategoryRequest updateIssueCategoryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateIssueCategoryRequestCreation(format, issueCategoryId, xRedmineSwitchUser, updateIssueCategoryRequest).toEntity(localVarReturnType);
    }

    /**
     * Update issue category
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueCategoryRequest The updateIssueCategoryRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#PUT">Update issue category Documentation</a>
     */
    public ResponseSpec updateIssueCategoryWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueCategoryRequest updateIssueCategoryRequest) throws RestClientResponseException {
        return updateIssueCategoryRequestCreation(format, issueCategoryId, xRedmineSwitchUser, updateIssueCategoryRequest);
    }

    /**
     * Update issue category (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueCategoryRequest The updateIssueCategoryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#PUT">Update issue category (PATCH) Documentation</a>
     */
    private ResponseSpec updateIssueCategoryViaPatchRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueCategoryRequest updateIssueCategoryRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateIssueCategoryViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueCategoryId' is set
        if (issueCategoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueCategoryId' when calling updateIssueCategoryViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("issue_category_id", issueCategoryId);

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
        return apiClient.invokeAPI("/issue_categories/{issue_category_id}.{format}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateIssueCategoryRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update issue category (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueCategoryRequest The updateIssueCategoryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#PUT">Update issue category (PATCH) Documentation</a>
     */
    public void updateIssueCategoryViaPatch(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueCategoryRequest updateIssueCategoryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateIssueCategoryViaPatchRequestCreation(format, issueCategoryId, xRedmineSwitchUser, updateIssueCategoryRequest).body(localVarReturnType);
    }

    /**
     * Update issue category (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueCategoryRequest The updateIssueCategoryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#PUT">Update issue category (PATCH) Documentation</a>
     */
    public ResponseEntity<Void> updateIssueCategoryViaPatchWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueCategoryRequest updateIssueCategoryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateIssueCategoryViaPatchRequestCreation(format, issueCategoryId, xRedmineSwitchUser, updateIssueCategoryRequest).toEntity(localVarReturnType);
    }

    /**
     * Update issue category (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueCategoryId The issueCategoryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueCategoryRequest The updateIssueCategoryRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories#PUT">Update issue category (PATCH) Documentation</a>
     */
    public ResponseSpec updateIssueCategoryViaPatchWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueCategoryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueCategoryRequest updateIssueCategoryRequest) throws RestClientResponseException {
        return updateIssueCategoryViaPatchRequestCreation(format, issueCategoryId, xRedmineSwitchUser, updateIssueCategoryRequest);
    }
}
