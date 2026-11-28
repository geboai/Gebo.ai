package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.CreateMembership201Response;
import ai.gebo.redmine.client.model.CreateMembershipRequest;
import ai.gebo.redmine.client.model.Errors;
import ai.gebo.redmine.client.model.GetMemberships200Response;
import ai.gebo.redmine.client.model.UpdateMembershipRequest;

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
public class ProjectMembershipsApi {
    private ApiClient apiClient;

    public ProjectMembershipsApi() {
        this(new ApiClient());
    }

    public ProjectMembershipsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create membership
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createMembershipRequest The createMembershipRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateMembership201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#POST">Create membership Documentation</a>
     */
    private ResponseSpec createMembershipRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateMembershipRequest createMembershipRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling createMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createMembershipRequest' is set
        if (createMembershipRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createMembershipRequest' when calling createMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CreateMembership201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/memberships.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createMembershipRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create membership
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createMembershipRequest The createMembershipRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateMembership201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#POST">Create membership Documentation</a>
     */
    public CreateMembership201Response createMembership(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateMembershipRequest createMembershipRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateMembership201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMembershipRequestCreation(format, projectId, createMembershipRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create membership
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createMembershipRequest The createMembershipRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateMembership201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#POST">Create membership Documentation</a>
     */
    public ResponseEntity<CreateMembership201Response> createMembershipWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateMembershipRequest createMembershipRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateMembership201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMembershipRequestCreation(format, projectId, createMembershipRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create membership
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createMembershipRequest The createMembershipRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#POST">Create membership Documentation</a>
     */
    public ResponseSpec createMembershipWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateMembershipRequest createMembershipRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return createMembershipRequestCreation(format, projectId, createMembershipRequest, xRedmineSwitchUser);
    }

    /**
     * Delete membership
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned without a body when the membership is not deletable (e.g. inherited from a group).
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#DELETE">Delete membership Documentation</a>
     */
    private ResponseSpec deleteMembershipRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'membershipId' is set
        if (membershipId == null) {
            throw new RestClientResponseException("Missing the required parameter 'membershipId' when calling deleteMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("membership_id", membershipId);

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
        return apiClient.invokeAPI("/memberships/{membership_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete membership
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned without a body when the membership is not deletable (e.g. inherited from a group).
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#DELETE">Delete membership Documentation</a>
     */
    public void deleteMembership(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteMembershipRequestCreation(format, membershipId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Delete membership
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned without a body when the membership is not deletable (e.g. inherited from a group).
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#DELETE">Delete membership Documentation</a>
     */
    public ResponseEntity<Void> deleteMembershipWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteMembershipRequestCreation(format, membershipId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Delete membership
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned without a body when the membership is not deletable (e.g. inherited from a group).
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#DELETE">Delete membership Documentation</a>
     */
    public ResponseSpec deleteMembershipWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return deleteMembershipRequestCreation(format, membershipId, xRedmineSwitchUser);
    }

    /**
     * Show membership
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateMembership201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#GET-2">Show membership Documentation</a>
     */
    private ResponseSpec getMembershipRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'membershipId' is set
        if (membershipId == null) {
            throw new RestClientResponseException("Missing the required parameter 'membershipId' when calling getMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("membership_id", membershipId);

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

        ParameterizedTypeReference<CreateMembership201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/memberships/{membership_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show membership
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateMembership201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#GET-2">Show membership Documentation</a>
     */
    public CreateMembership201Response getMembership(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateMembership201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMembershipRequestCreation(format, membershipId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Show membership
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateMembership201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#GET-2">Show membership Documentation</a>
     */
    public ResponseEntity<CreateMembership201Response> getMembershipWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateMembership201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMembershipRequestCreation(format, membershipId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Show membership
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#GET-2">Show membership Documentation</a>
     */
    public ResponseSpec getMembershipWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return getMembershipRequestCreation(format, membershipId, xRedmineSwitchUser);
    }

    /**
     * List memberships
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @return GetMemberships200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#GET">List memberships Documentation</a>
     */
    private ResponseSpec getMembershipsRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getMemberships", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getMemberships", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "nometa", nometa));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));

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

        ParameterizedTypeReference<GetMemberships200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/memberships.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List memberships
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @return GetMemberships200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#GET">List memberships Documentation</a>
     */
    public GetMemberships200Response getMemberships(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        ParameterizedTypeReference<GetMemberships200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMembershipsRequestCreation(format, projectId, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, page).body(localVarReturnType);
    }

    /**
     * List memberships
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @return ResponseEntity&lt;GetMemberships200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#GET">List memberships Documentation</a>
     */
    public ResponseEntity<GetMemberships200Response> getMembershipsWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        ParameterizedTypeReference<GetMemberships200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMembershipsRequestCreation(format, projectId, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, page).toEntity(localVarReturnType);
    }

    /**
     * List memberships
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#GET">List memberships Documentation</a>
     */
    public ResponseSpec getMembershipsWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        return getMembershipsRequestCreation(format, projectId, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, page);
    }

    /**
     * Update membership
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateMembershipRequest The updateMembershipRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#PUT">Update membership Documentation</a>
     */
    private ResponseSpec updateMembershipRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateMembershipRequest updateMembershipRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'membershipId' is set
        if (membershipId == null) {
            throw new RestClientResponseException("Missing the required parameter 'membershipId' when calling updateMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("membership_id", membershipId);

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
        return apiClient.invokeAPI("/memberships/{membership_id}.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, updateMembershipRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update membership
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateMembershipRequest The updateMembershipRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#PUT">Update membership Documentation</a>
     */
    public void updateMembership(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateMembershipRequest updateMembershipRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateMembershipRequestCreation(format, membershipId, xRedmineSwitchUser, updateMembershipRequest).body(localVarReturnType);
    }

    /**
     * Update membership
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateMembershipRequest The updateMembershipRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#PUT">Update membership Documentation</a>
     */
    public ResponseEntity<Void> updateMembershipWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateMembershipRequest updateMembershipRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMembershipRequestCreation(format, membershipId, xRedmineSwitchUser, updateMembershipRequest).toEntity(localVarReturnType);
    }

    /**
     * Update membership
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateMembershipRequest The updateMembershipRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#PUT">Update membership Documentation</a>
     */
    public ResponseSpec updateMembershipWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateMembershipRequest updateMembershipRequest) throws RestClientResponseException {
        return updateMembershipRequestCreation(format, membershipId, xRedmineSwitchUser, updateMembershipRequest);
    }

    /**
     * Update membership (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateMembershipRequest The updateMembershipRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#PUT">Update membership (PATCH) Documentation</a>
     */
    private ResponseSpec updateMembershipViaPatchRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateMembershipRequest updateMembershipRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateMembershipViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'membershipId' is set
        if (membershipId == null) {
            throw new RestClientResponseException("Missing the required parameter 'membershipId' when calling updateMembershipViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("membership_id", membershipId);

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
        return apiClient.invokeAPI("/memberships/{membership_id}.{format}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateMembershipRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update membership (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateMembershipRequest The updateMembershipRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#PUT">Update membership (PATCH) Documentation</a>
     */
    public void updateMembershipViaPatch(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateMembershipRequest updateMembershipRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateMembershipViaPatchRequestCreation(format, membershipId, xRedmineSwitchUser, updateMembershipRequest).body(localVarReturnType);
    }

    /**
     * Update membership (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateMembershipRequest The updateMembershipRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#PUT">Update membership (PATCH) Documentation</a>
     */
    public ResponseEntity<Void> updateMembershipViaPatchWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateMembershipRequest updateMembershipRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMembershipViaPatchRequestCreation(format, membershipId, xRedmineSwitchUser, updateMembershipRequest).toEntity(localVarReturnType);
    }

    /**
     * Update membership (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param membershipId The membershipId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateMembershipRequest The updateMembershipRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Memberships#PUT">Update membership (PATCH) Documentation</a>
     */
    public ResponseSpec updateMembershipViaPatchWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer membershipId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateMembershipRequest updateMembershipRequest) throws RestClientResponseException {
        return updateMembershipViaPatchRequestCreation(format, membershipId, xRedmineSwitchUser, updateMembershipRequest);
    }
}
