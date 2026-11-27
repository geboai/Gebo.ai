package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.RootModel;

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
public class RootApi {
    private ApiClient apiClient;

    public RootApi() {
        this(new ApiClient());
    }

    public RootApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View root
     * Returns the root resource, containing basic information about the server instance and a collection of useful links.
     * <p><b>200</b> - OK
     * @return RootModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRootRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<RootModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View root
     * Returns the root resource, containing basic information about the server instance and a collection of useful links.
     * <p><b>200</b> - OK
     * @return RootModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RootModel getRoot() throws RestClientResponseException {
        ParameterizedTypeReference<RootModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRootRequestCreation().body(localVarReturnType);
    }

    /**
     * View root
     * Returns the root resource, containing basic information about the server instance and a collection of useful links.
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;RootModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RootModel> getRootWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<RootModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRootRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * View root
     * Returns the root resource, containing basic information about the server instance and a collection of useful links.
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRootWithResponseSpec() throws RestClientResponseException {
        return getRootRequestCreation();
    }
}
