package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.CustomActionModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.ExecuteCustomActionRequest;

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
public class CustomActionsApi {
    private ApiClient apiClient;

    public CustomActionsApi() {
        this(new ApiClient());
    }

    public CustomActionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Execute custom action
     * A POST to this endpoint executes the custom action on the work package provided in the payload. The altered work package will be returned. In order to avoid executing  the custom action unbeknown to a change that has already taken place, the client has to provide the work package&#39;s current lockVersion.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages - Additional permissions might be required based on the custom action.
     * <p><b>404</b> - Returned if the custom action does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>409</b> - Returned if the client provided an outdated lockVersion or no lockVersion at all.
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the custom action was not executed successfully e.g. when a constraint on a work package property was violated.
     * @param id The id of the custom action to execute
     * @param executeCustomActionRequest The executeCustomActionRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec executeCustomActionRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ExecuteCustomActionRequest executeCustomActionRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling executeCustomAction", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/custom_actions/{id}/execute", HttpMethod.POST, pathParams, localVarQueryParams, executeCustomActionRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Execute custom action
     * A POST to this endpoint executes the custom action on the work package provided in the payload. The altered work package will be returned. In order to avoid executing  the custom action unbeknown to a change that has already taken place, the client has to provide the work package&#39;s current lockVersion.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages - Additional permissions might be required based on the custom action.
     * <p><b>404</b> - Returned if the custom action does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>409</b> - Returned if the client provided an outdated lockVersion or no lockVersion at all.
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the custom action was not executed successfully e.g. when a constraint on a work package property was violated.
     * @param id The id of the custom action to execute
     * @param executeCustomActionRequest The executeCustomActionRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void executeCustomAction(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ExecuteCustomActionRequest executeCustomActionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        executeCustomActionRequestCreation(id, executeCustomActionRequest).body(localVarReturnType);
    }

    /**
     * Execute custom action
     * A POST to this endpoint executes the custom action on the work package provided in the payload. The altered work package will be returned. In order to avoid executing  the custom action unbeknown to a change that has already taken place, the client has to provide the work package&#39;s current lockVersion.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages - Additional permissions might be required based on the custom action.
     * <p><b>404</b> - Returned if the custom action does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>409</b> - Returned if the client provided an outdated lockVersion or no lockVersion at all.
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the custom action was not executed successfully e.g. when a constraint on a work package property was violated.
     * @param id The id of the custom action to execute
     * @param executeCustomActionRequest The executeCustomActionRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> executeCustomActionWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ExecuteCustomActionRequest executeCustomActionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return executeCustomActionRequestCreation(id, executeCustomActionRequest).toEntity(localVarReturnType);
    }

    /**
     * Execute custom action
     * A POST to this endpoint executes the custom action on the work package provided in the payload. The altered work package will be returned. In order to avoid executing  the custom action unbeknown to a change that has already taken place, the client has to provide the work package&#39;s current lockVersion.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages - Additional permissions might be required based on the custom action.
     * <p><b>404</b> - Returned if the custom action does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>409</b> - Returned if the client provided an outdated lockVersion or no lockVersion at all.
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the custom action was not executed successfully e.g. when a constraint on a work package property was violated.
     * @param id The id of the custom action to execute
     * @param executeCustomActionRequest The executeCustomActionRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec executeCustomActionWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ExecuteCustomActionRequest executeCustomActionRequest) throws RestClientResponseException {
        return executeCustomActionRequestCreation(id, executeCustomActionRequest);
    }

    /**
     * Get a custom action
     * Retrieves a custom action by id.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages in any project
     * <p><b>404</b> - Returned if the custom action does not exist.
     * @param id The id of the custom action to fetch
     * @return CustomActionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCustomActionRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getCustomAction", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CustomActionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/custom_actions/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a custom action
     * Retrieves a custom action by id.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages in any project
     * <p><b>404</b> - Returned if the custom action does not exist.
     * @param id The id of the custom action to fetch
     * @return CustomActionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public CustomActionModel getCustomAction(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CustomActionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomActionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a custom action
     * Retrieves a custom action by id.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages in any project
     * <p><b>404</b> - Returned if the custom action does not exist.
     * @param id The id of the custom action to fetch
     * @return ResponseEntity&lt;CustomActionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CustomActionModel> getCustomActionWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CustomActionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomActionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a custom action
     * Retrieves a custom action by id.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages in any project
     * <p><b>404</b> - Returned if the custom action does not exist.
     * @param id The id of the custom action to fetch
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCustomActionWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getCustomActionRequestCreation(id);
    }
}
