package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.UpdateMyPreferencesRequest;

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
public class UserPreferencesApi {
    private ApiClient apiClient;

    public UserPreferencesApi() {
        this(new ApiClient());
    }

    public UserPreferencesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Show my preferences
     * 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if no user is currently authenticated
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMyPreferencesRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/my_preferences", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show my preferences
     * 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if no user is currently authenticated
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getMyPreferences() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMyPreferencesRequestCreation().body(localVarReturnType);
    }

    /**
     * Show my preferences
     * 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if no user is currently authenticated
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getMyPreferencesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMyPreferencesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Show my preferences
     * 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if no user is currently authenticated
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMyPreferencesWithResponseSpec() throws RestClientResponseException {
        return getMyPreferencesRequestCreation();
    }

    /**
     * Update my preferences
     * When calling this endpoint the client provides a single object, containing the properties that it wants to change, in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if no user is currently authenticated
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the update contains invalid properties. Reasons are:  * Specifying an invalid type  * Using an unknown time zone
     * @param updateMyPreferencesRequest The updateMyPreferencesRequest parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateMyPreferencesRequestCreation(@jakarta.annotation.Nullable UpdateMyPreferencesRequest updateMyPreferencesRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/my_preferences", HttpMethod.PATCH, pathParams, localVarQueryParams, updateMyPreferencesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update my preferences
     * When calling this endpoint the client provides a single object, containing the properties that it wants to change, in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if no user is currently authenticated
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the update contains invalid properties. Reasons are:  * Specifying an invalid type  * Using an unknown time zone
     * @param updateMyPreferencesRequest The updateMyPreferencesRequest parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object updateMyPreferences(@jakarta.annotation.Nullable UpdateMyPreferencesRequest updateMyPreferencesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMyPreferencesRequestCreation(updateMyPreferencesRequest).body(localVarReturnType);
    }

    /**
     * Update my preferences
     * When calling this endpoint the client provides a single object, containing the properties that it wants to change, in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if no user is currently authenticated
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the update contains invalid properties. Reasons are:  * Specifying an invalid type  * Using an unknown time zone
     * @param updateMyPreferencesRequest The updateMyPreferencesRequest parameter
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> updateMyPreferencesWithHttpInfo(@jakarta.annotation.Nullable UpdateMyPreferencesRequest updateMyPreferencesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMyPreferencesRequestCreation(updateMyPreferencesRequest).toEntity(localVarReturnType);
    }

    /**
     * Update my preferences
     * When calling this endpoint the client provides a single object, containing the properties that it wants to change, in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if no user is currently authenticated
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the update contains invalid properties. Reasons are:  * Specifying an invalid type  * Using an unknown time zone
     * @param updateMyPreferencesRequest The updateMyPreferencesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateMyPreferencesWithResponseSpec(@jakarta.annotation.Nullable UpdateMyPreferencesRequest updateMyPreferencesRequest) throws RestClientResponseException {
        return updateMyPreferencesRequestCreation(updateMyPreferencesRequest);
    }
}
