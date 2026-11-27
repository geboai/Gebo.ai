package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.GetDocumentCategories200Response;
import ai.gebo.redmine.client.model.GetIssuePriorities200Response;
import ai.gebo.redmine.client.model.GetTimeEntryActivities200Response;

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
public class EnumerationsApi {
    private ApiClient apiClient;

    public EnumerationsApi() {
        this(new ApiClient());
    }

    public EnumerationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List document categories
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;DocumentCategory&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetDocumentCategories200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET-3">List document categories Documentation</a>
     */
    private ResponseSpec getDocumentCategoriesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getDocumentCategories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetDocumentCategories200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/enumerations/document_categories.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List document categories
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;DocumentCategory&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetDocumentCategories200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET-3">List document categories Documentation</a>
     */
    public GetDocumentCategories200Response getDocumentCategories(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetDocumentCategories200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDocumentCategoriesRequestCreation(format, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * List document categories
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;DocumentCategory&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;GetDocumentCategories200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET-3">List document categories Documentation</a>
     */
    public ResponseEntity<GetDocumentCategories200Response> getDocumentCategoriesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetDocumentCategories200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDocumentCategoriesRequestCreation(format, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * List document categories
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;DocumentCategory&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET-3">List document categories Documentation</a>
     */
    public ResponseSpec getDocumentCategoriesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return getDocumentCategoriesRequestCreation(format, xRedmineSwitchUser);
    }

    /**
     * List issue priorities
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;IssuePriority&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetIssuePriorities200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET">List issue priorities Documentation</a>
     */
    private ResponseSpec getIssuePrioritiesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getIssuePriorities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetIssuePriorities200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/enumerations/issue_priorities.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List issue priorities
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;IssuePriority&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetIssuePriorities200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET">List issue priorities Documentation</a>
     */
    public GetIssuePriorities200Response getIssuePriorities(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssuePriorities200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssuePrioritiesRequestCreation(format, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * List issue priorities
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;IssuePriority&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;GetIssuePriorities200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET">List issue priorities Documentation</a>
     */
    public ResponseEntity<GetIssuePriorities200Response> getIssuePrioritiesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssuePriorities200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssuePrioritiesRequestCreation(format, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * List issue priorities
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;IssuePriority&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET">List issue priorities Documentation</a>
     */
    public ResponseSpec getIssuePrioritiesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return getIssuePrioritiesRequestCreation(format, xRedmineSwitchUser);
    }

    /**
     * List time entry activities
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;TimeEntryActivity&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetTimeEntryActivities200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET-2">List time entry activities Documentation</a>
     */
    private ResponseSpec getTimeEntryActivitiesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getTimeEntryActivities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetTimeEntryActivities200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/enumerations/time_entry_activities.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List time entry activities
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;TimeEntryActivity&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetTimeEntryActivities200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET-2">List time entry activities Documentation</a>
     */
    public GetTimeEntryActivities200Response getTimeEntryActivities(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetTimeEntryActivities200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntryActivitiesRequestCreation(format, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * List time entry activities
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;TimeEntryActivity&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;GetTimeEntryActivities200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET-2">List time entry activities Documentation</a>
     */
    public ResponseEntity<GetTimeEntryActivities200Response> getTimeEntryActivitiesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetTimeEntryActivities200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntryActivitiesRequestCreation(format, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * List time entry activities
     * The same collection is available at &#x60;GET /enumerations.json?type&#x3D;TimeEntryActivity&#x60;.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Enumerations#GET-2">List time entry activities Documentation</a>
     */
    public ResponseSpec getTimeEntryActivitiesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return getTimeEntryActivitiesRequestCreation(format, xRedmineSwitchUser);
    }
}
