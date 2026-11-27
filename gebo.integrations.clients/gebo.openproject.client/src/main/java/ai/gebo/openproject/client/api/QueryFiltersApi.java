package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.QueryFilterModel;

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
public class QueryFiltersApi {
    private ApiClient apiClient;

    public QueryFiltersApi() {
        this(new ApiClient());
    }

    public QueryFiltersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View Query Filter
     * Retrieve an individual QueryFilter as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * <p><b>404</b> - Returned if the QueryFilter does not exist.
     * @param id QueryFilter identifier
     * @return QueryFilterModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getQueryFilterRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getQueryFilter", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<QueryFilterModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/filters/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View Query Filter
     * Retrieve an individual QueryFilter as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * <p><b>404</b> - Returned if the QueryFilter does not exist.
     * @param id QueryFilter identifier
     * @return QueryFilterModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public QueryFilterModel getQueryFilter(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<QueryFilterModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getQueryFilterRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View Query Filter
     * Retrieve an individual QueryFilter as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * <p><b>404</b> - Returned if the QueryFilter does not exist.
     * @param id QueryFilter identifier
     * @return ResponseEntity&lt;QueryFilterModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<QueryFilterModel> getQueryFilterWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<QueryFilterModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getQueryFilterRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View Query Filter
     * Retrieve an individual QueryFilter as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * <p><b>404</b> - Returned if the QueryFilter does not exist.
     * @param id QueryFilter identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getQueryFilterWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getQueryFilterRequestCreation(id);
    }
}
