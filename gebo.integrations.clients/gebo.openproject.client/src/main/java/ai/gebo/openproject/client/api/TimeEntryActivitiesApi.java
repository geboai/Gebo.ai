package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.TimeEntryActivityModel;

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
public class TimeEntryActivitiesApi {
    private ApiClient apiClient;

    public TimeEntryActivitiesApi() {
        this(new ApiClient());
    }

    public TimeEntryActivitiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View time entries activity
     * Fetches the time entry activity resource by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or if the user does not have permission to view them.  **Required permission** &#x60;view time entries&#x60;, &#x60;log time&#x60;, &#x60;edit time entries&#x60;, &#x60;edit own time entries&#x60; or &#x60;manage project activities&#x60; in any project
     * @param id Time entries activity id
     * @return TimeEntryActivityModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTimeEntriesActivityRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getTimeEntriesActivity", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<TimeEntryActivityModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/time_entries/activities/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View time entries activity
     * Fetches the time entry activity resource by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or if the user does not have permission to view them.  **Required permission** &#x60;view time entries&#x60;, &#x60;log time&#x60;, &#x60;edit time entries&#x60;, &#x60;edit own time entries&#x60; or &#x60;manage project activities&#x60; in any project
     * @param id Time entries activity id
     * @return TimeEntryActivityModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TimeEntryActivityModel getTimeEntriesActivity(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryActivityModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntriesActivityRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View time entries activity
     * Fetches the time entry activity resource by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or if the user does not have permission to view them.  **Required permission** &#x60;view time entries&#x60;, &#x60;log time&#x60;, &#x60;edit time entries&#x60;, &#x60;edit own time entries&#x60; or &#x60;manage project activities&#x60; in any project
     * @param id Time entries activity id
     * @return ResponseEntity&lt;TimeEntryActivityModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TimeEntryActivityModel> getTimeEntriesActivityWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryActivityModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntriesActivityRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View time entries activity
     * Fetches the time entry activity resource by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or if the user does not have permission to view them.  **Required permission** &#x60;view time entries&#x60;, &#x60;log time&#x60;, &#x60;edit time entries&#x60;, &#x60;edit own time entries&#x60; or &#x60;manage project activities&#x60; in any project
     * @param id Time entries activity id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTimeEntriesActivityWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getTimeEntriesActivityRequestCreation(id);
    }
}
