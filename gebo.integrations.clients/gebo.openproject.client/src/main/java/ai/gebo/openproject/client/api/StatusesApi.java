package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.StatusCollectionModel;
import ai.gebo.openproject.client.model.StatusModel;

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
public class StatusesApi {
    private ApiClient apiClient;

    public StatusesApi() {
        this(new ApiClient());
    }

    public StatusesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get a work package status
     * Returns a work package status by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * <p><b>404</b> - Returned if the status does not exist.
     * @param id Status id
     * @return StatusModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getStatusRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<StatusModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/statuses/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a work package status
     * Returns a work package status by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * <p><b>404</b> - Returned if the status does not exist.
     * @param id Status id
     * @return StatusModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StatusModel getStatus(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<StatusModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getStatusRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a work package status
     * Returns a work package status by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * <p><b>404</b> - Returned if the status does not exist.
     * @param id Status id
     * @return ResponseEntity&lt;StatusModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StatusModel> getStatusWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<StatusModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getStatusRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a work package status
     * Returns a work package status by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * <p><b>404</b> - Returned if the status does not exist.
     * @param id Status id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getStatusWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getStatusRequestCreation(id);
    }

    /**
     * List the collection of all statuses
     * Returns a collection of all work package statuses.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * @return StatusCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listStatusesRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<StatusCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/statuses", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List the collection of all statuses
     * Returns a collection of all work package statuses.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * @return StatusCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StatusCollectionModel listStatuses() throws RestClientResponseException {
        ParameterizedTypeReference<StatusCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listStatusesRequestCreation().body(localVarReturnType);
    }

    /**
     * List the collection of all statuses
     * Returns a collection of all work package statuses.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * @return ResponseEntity&lt;StatusCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StatusCollectionModel> listStatusesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<StatusCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listStatusesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List the collection of all statuses
     * Returns a collection of all work package statuses.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listStatusesWithResponseSpec() throws RestClientResponseException {
        return listStatusesRequestCreation();
    }
}
