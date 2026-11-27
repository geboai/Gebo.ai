package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.SpecificationModel;

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
public class OpenApiApi {
    private ApiClient apiClient;

    public OpenApiApi() {
        this(new ApiClient());
    }

    public OpenApiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get OpenAPI specification
     * Returns the OpenAPI specification in JSON format. Equivalent to &#x60;/api/v3/spec.json&#x60;.
     * <p><b>200</b> - OpenAPI specification
     * @return SpecificationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getOpenapiRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<SpecificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/openapi.json", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get OpenAPI specification
     * Returns the OpenAPI specification in JSON format. Equivalent to &#x60;/api/v3/spec.json&#x60;.
     * <p><b>200</b> - OpenAPI specification
     * @return SpecificationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SpecificationModel getOpenapi() throws RestClientResponseException {
        ParameterizedTypeReference<SpecificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getOpenapiRequestCreation().body(localVarReturnType);
    }

    /**
     * Get OpenAPI specification
     * Returns the OpenAPI specification in JSON format. Equivalent to &#x60;/api/v3/spec.json&#x60;.
     * <p><b>200</b> - OpenAPI specification
     * @return ResponseEntity&lt;SpecificationModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SpecificationModel> getOpenapiWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<SpecificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getOpenapiRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get OpenAPI specification
     * Returns the OpenAPI specification in JSON format. Equivalent to &#x60;/api/v3/spec.json&#x60;.
     * <p><b>200</b> - OpenAPI specification
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getOpenapiWithResponseSpec() throws RestClientResponseException {
        return getOpenapiRequestCreation();
    }

    /**
     * Get specification (YAML)
     * Returns the OpenAPI specification in YAML format.
     * <p><b>200</b> - OpenAPI specification
     * @return SpecificationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSpecRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/yaml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<SpecificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/spec.yml", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get specification (YAML)
     * Returns the OpenAPI specification in YAML format.
     * <p><b>200</b> - OpenAPI specification
     * @return SpecificationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SpecificationModel getSpec() throws RestClientResponseException {
        ParameterizedTypeReference<SpecificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSpecRequestCreation().body(localVarReturnType);
    }

    /**
     * Get specification (YAML)
     * Returns the OpenAPI specification in YAML format.
     * <p><b>200</b> - OpenAPI specification
     * @return ResponseEntity&lt;SpecificationModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SpecificationModel> getSpecWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<SpecificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSpecRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get specification (YAML)
     * Returns the OpenAPI specification in YAML format.
     * <p><b>200</b> - OpenAPI specification
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSpecWithResponseSpec() throws RestClientResponseException {
        return getSpecRequestCreation();
    }

    /**
     * Get specification (JSON)
     * Returns the OpenAPI specification in JSON format.
     * <p><b>200</b> - OpenAPI specification
     * @return SpecificationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSpecJsonRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<SpecificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/spec.json", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get specification (JSON)
     * Returns the OpenAPI specification in JSON format.
     * <p><b>200</b> - OpenAPI specification
     * @return SpecificationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SpecificationModel getSpecJson() throws RestClientResponseException {
        ParameterizedTypeReference<SpecificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSpecJsonRequestCreation().body(localVarReturnType);
    }

    /**
     * Get specification (JSON)
     * Returns the OpenAPI specification in JSON format.
     * <p><b>200</b> - OpenAPI specification
     * @return ResponseEntity&lt;SpecificationModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SpecificationModel> getSpecJsonWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<SpecificationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSpecJsonRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get specification (JSON)
     * Returns the OpenAPI specification in JSON format.
     * <p><b>200</b> - OpenAPI specification
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSpecJsonWithResponseSpec() throws RestClientResponseException {
        return getSpecJsonRequestCreation();
    }
}
