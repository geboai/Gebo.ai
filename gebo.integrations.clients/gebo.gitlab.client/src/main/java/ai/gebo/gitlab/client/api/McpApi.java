package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PostApiV4McpRequest;

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
public class McpApi {
    private ApiClient apiClient;

    public McpApi() {
        this(new ApiClient());
    }

    public McpApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get MCP response listener
     * Listens for Model Context Protocol responses
     * <p><b>405</b> - Method not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4McpRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/mcp", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get MCP response listener
     * Listens for Model Context Protocol responses
     * <p><b>405</b> - Method not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4Mcp() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4McpRequestCreation().body(localVarReturnType);
    }

    /**
     * Get MCP response listener
     * Listens for Model Context Protocol responses
     * <p><b>405</b> - Method not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4McpWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4McpRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get MCP response listener
     * Listens for Model Context Protocol responses
     * <p><b>405</b> - Method not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4McpWithResponseSpec() throws RestClientResponseException {
        return getApiV4McpRequestCreation();
    }

    /**
     * Create MCP request handler
     * Handles Model Context Protocol requests
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted (JSON-RPC notification)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Method not found
     * @param postApiV4McpRequest The postApiV4McpRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4McpRequestCreation(@jakarta.annotation.Nonnull PostApiV4McpRequest postApiV4McpRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4McpRequest' is set
        if (postApiV4McpRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4McpRequest' when calling postApiV4Mcp", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/mcp", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4McpRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create MCP request handler
     * Handles Model Context Protocol requests
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted (JSON-RPC notification)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Method not found
     * @param postApiV4McpRequest The postApiV4McpRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4Mcp(@jakarta.annotation.Nonnull PostApiV4McpRequest postApiV4McpRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4McpRequestCreation(postApiV4McpRequest).body(localVarReturnType);
    }

    /**
     * Create MCP request handler
     * Handles Model Context Protocol requests
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted (JSON-RPC notification)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Method not found
     * @param postApiV4McpRequest The postApiV4McpRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4McpWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4McpRequest postApiV4McpRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4McpRequestCreation(postApiV4McpRequest).toEntity(localVarReturnType);
    }

    /**
     * Create MCP request handler
     * Handles Model Context Protocol requests
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted (JSON-RPC notification)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Method not found
     * @param postApiV4McpRequest The postApiV4McpRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4McpWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4McpRequest postApiV4McpRequest) throws RestClientResponseException {
        return postApiV4McpRequestCreation(postApiV4McpRequest);
    }
}
