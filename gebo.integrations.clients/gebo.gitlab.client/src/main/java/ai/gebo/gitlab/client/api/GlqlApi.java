package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGlqlResult;
import ai.gebo.gitlab.client.model.PostApiV4GlqlRequest;

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
public class GlqlApi {
    private ApiClient apiClient;

    public GlqlApi() {
        this(new ApiClient());
    }

    public GlqlApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve the GLQL schema
     * Retrieves the GLQL schema: data sources with their filter, display and sort fields, the operator, value kind and reference type vocabularies, the available functions, and the display types a query can be rendered as. The document describes the query language. A query against a data source that is not available to the user fails at &#x60;POST /glql&#x60;. The document changes only when GitLab is upgraded. It is served with an &#x60;ETag&#x60;, so clients can send &#x60;If-None-Match&#x60; to revalidate and receive &#x60;304 Not Modified&#x60; instead of the full document. The endpoint takes no parameters and returns the same document for every user. Analytics modes list the arguments their dimensions and metrics accept in &#x60;parameterized_fields&#x60;, so a query can set them explicitly rather than relying on the default. Each entry has a &#x60;name&#x60; and a &#x60;parameters&#x60; array. Each parameter has a &#x60;name&#x60; and a &#x60;kind&#x60;, and can have &#x60;values&#x60;, &#x60;min&#x60;, &#x60;max&#x60;, &#x60;min_days&#x60;, &#x60;max_days&#x60;, &#x60;default&#x60;, &#x60;optional&#x60;, &#x60;required&#x60;, &#x60;requires&#x60;, &#x60;items&#x60;, &#x60;min_length&#x60;, &#x60;max_length&#x60;, and &#x60;strictly_ascending&#x60;. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>500</b> - Internal server error
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GlqlSchemaRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/glql/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the GLQL schema
     * Retrieves the GLQL schema: data sources with their filter, display and sort fields, the operator, value kind and reference type vocabularies, the available functions, and the display types a query can be rendered as. The document describes the query language. A query against a data source that is not available to the user fails at &#x60;POST /glql&#x60;. The document changes only when GitLab is upgraded. It is served with an &#x60;ETag&#x60;, so clients can send &#x60;If-None-Match&#x60; to revalidate and receive &#x60;304 Not Modified&#x60; instead of the full document. The endpoint takes no parameters and returns the same document for every user. Analytics modes list the arguments their dimensions and metrics accept in &#x60;parameterized_fields&#x60;, so a query can set them explicitly rather than relying on the default. Each entry has a &#x60;name&#x60; and a &#x60;parameters&#x60; array. Each parameter has a &#x60;name&#x60; and a &#x60;kind&#x60;, and can have &#x60;values&#x60;, &#x60;min&#x60;, &#x60;max&#x60;, &#x60;min_days&#x60;, &#x60;max_days&#x60;, &#x60;default&#x60;, &#x60;optional&#x60;, &#x60;required&#x60;, &#x60;requires&#x60;, &#x60;items&#x60;, &#x60;min_length&#x60;, &#x60;max_length&#x60;, and &#x60;strictly_ascending&#x60;. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>500</b> - Internal server error
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GlqlSchema() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GlqlSchemaRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve the GLQL schema
     * Retrieves the GLQL schema: data sources with their filter, display and sort fields, the operator, value kind and reference type vocabularies, the available functions, and the display types a query can be rendered as. The document describes the query language. A query against a data source that is not available to the user fails at &#x60;POST /glql&#x60;. The document changes only when GitLab is upgraded. It is served with an &#x60;ETag&#x60;, so clients can send &#x60;If-None-Match&#x60; to revalidate and receive &#x60;304 Not Modified&#x60; instead of the full document. The endpoint takes no parameters and returns the same document for every user. Analytics modes list the arguments their dimensions and metrics accept in &#x60;parameterized_fields&#x60;, so a query can set them explicitly rather than relying on the default. Each entry has a &#x60;name&#x60; and a &#x60;parameters&#x60; array. Each parameter has a &#x60;name&#x60; and a &#x60;kind&#x60;, and can have &#x60;values&#x60;, &#x60;min&#x60;, &#x60;max&#x60;, &#x60;min_days&#x60;, &#x60;max_days&#x60;, &#x60;default&#x60;, &#x60;optional&#x60;, &#x60;required&#x60;, &#x60;requires&#x60;, &#x60;items&#x60;, &#x60;min_length&#x60;, &#x60;max_length&#x60;, and &#x60;strictly_ascending&#x60;. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>500</b> - Internal server error
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GlqlSchemaWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GlqlSchemaRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve the GLQL schema
     * Retrieves the GLQL schema: data sources with their filter, display and sort fields, the operator, value kind and reference type vocabularies, the available functions, and the display types a query can be rendered as. The document describes the query language. A query against a data source that is not available to the user fails at &#x60;POST /glql&#x60;. The document changes only when GitLab is upgraded. It is served with an &#x60;ETag&#x60;, so clients can send &#x60;If-None-Match&#x60; to revalidate and receive &#x60;304 Not Modified&#x60; instead of the full document. The endpoint takes no parameters and returns the same document for every user. Analytics modes list the arguments their dimensions and metrics accept in &#x60;parameterized_fields&#x60;, so a query can set them explicitly rather than relying on the default. Each entry has a &#x60;name&#x60; and a &#x60;parameters&#x60; array. Each parameter has a &#x60;name&#x60; and a &#x60;kind&#x60;, and can have &#x60;values&#x60;, &#x60;min&#x60;, &#x60;max&#x60;, &#x60;min_days&#x60;, &#x60;max_days&#x60;, &#x60;default&#x60;, &#x60;optional&#x60;, &#x60;required&#x60;, &#x60;requires&#x60;, &#x60;items&#x60;, &#x60;min_length&#x60;, &#x60;max_length&#x60;, and &#x60;strictly_ascending&#x60;. This feature was introduced in GitLab 19.3.
     * <p><b>200</b> - OK
     * <p><b>500</b> - Internal server error
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GlqlSchemaWithResponseSpec() throws RestClientResponseException {
        return getApiV4GlqlSchemaRequestCreation();
    }

    /**
     * Execute a GLQL query
     * Executes a GLQL query to search and filter GitLab resources. Queries are rate limited by the SHA-256 hash of the query. Queries that time out are tracked, and a query that times out too often is temporarily blocked. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too Many Requests
     * <p><b>500</b> - Internal server error
     * <p><b>503</b> - Service unavailable
     * @param postApiV4GlqlRequest The postApiV4GlqlRequest parameter
     * @return APIEntitiesGlqlResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GlqlRequestCreation(@jakarta.annotation.Nonnull PostApiV4GlqlRequest postApiV4GlqlRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4GlqlRequest' is set
        if (postApiV4GlqlRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GlqlRequest' when calling postApiV4Glql", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGlqlResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/glql", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GlqlRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Execute a GLQL query
     * Executes a GLQL query to search and filter GitLab resources. Queries are rate limited by the SHA-256 hash of the query. Queries that time out are tracked, and a query that times out too often is temporarily blocked. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too Many Requests
     * <p><b>500</b> - Internal server error
     * <p><b>503</b> - Service unavailable
     * @param postApiV4GlqlRequest The postApiV4GlqlRequest parameter
     * @return APIEntitiesGlqlResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGlqlResult postApiV4Glql(@jakarta.annotation.Nonnull PostApiV4GlqlRequest postApiV4GlqlRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGlqlResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GlqlRequestCreation(postApiV4GlqlRequest).body(localVarReturnType);
    }

    /**
     * Execute a GLQL query
     * Executes a GLQL query to search and filter GitLab resources. Queries are rate limited by the SHA-256 hash of the query. Queries that time out are tracked, and a query that times out too often is temporarily blocked. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too Many Requests
     * <p><b>500</b> - Internal server error
     * <p><b>503</b> - Service unavailable
     * @param postApiV4GlqlRequest The postApiV4GlqlRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGlqlResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGlqlResult> postApiV4GlqlWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4GlqlRequest postApiV4GlqlRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGlqlResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GlqlRequestCreation(postApiV4GlqlRequest).toEntity(localVarReturnType);
    }

    /**
     * Execute a GLQL query
     * Executes a GLQL query to search and filter GitLab resources. Queries are rate limited by the SHA-256 hash of the query. Queries that time out are tracked, and a query that times out too often is temporarily blocked. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>429</b> - Too Many Requests
     * <p><b>500</b> - Internal server error
     * <p><b>503</b> - Service unavailable
     * @param postApiV4GlqlRequest The postApiV4GlqlRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GlqlWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4GlqlRequest postApiV4GlqlRequest) throws RestClientResponseException {
        return postApiV4GlqlRequestCreation(postApiV4GlqlRequest);
    }
}
