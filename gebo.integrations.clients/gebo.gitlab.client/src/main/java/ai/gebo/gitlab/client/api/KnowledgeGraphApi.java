package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;

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
public class KnowledgeGraphApi {
    private ApiClient apiClient;

    public KnowledgeGraphApi() {
        this(new ApiClient());
    }

    public KnowledgeGraphApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Disable a namespace for Knowledge Graph
     * This endpoint disables Knowledge Graph for a specific namespace
     * <p><b>204</b> - No Content
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param id ID or URL-encoded path of the namespace.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4AdminKnowledgeGraphNamespacesIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4AdminKnowledgeGraphNamespacesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/admin/knowledge_graph/namespaces/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Disable a namespace for Knowledge Graph
     * This endpoint disables Knowledge Graph for a specific namespace
     * <p><b>204</b> - No Content
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param id ID or URL-encoded path of the namespace.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4AdminKnowledgeGraphNamespacesId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4AdminKnowledgeGraphNamespacesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Disable a namespace for Knowledge Graph
     * This endpoint disables Knowledge Graph for a specific namespace
     * <p><b>204</b> - No Content
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param id ID or URL-encoded path of the namespace.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4AdminKnowledgeGraphNamespacesIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminKnowledgeGraphNamespacesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Disable a namespace for Knowledge Graph
     * This endpoint disables Knowledge Graph for a specific namespace
     * <p><b>204</b> - No Content
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param id ID or URL-encoded path of the namespace.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4AdminKnowledgeGraphNamespacesIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4AdminKnowledgeGraphNamespacesIdRequestCreation(id);
    }

    /**
     * Get all enabled namespaces for Knowledge Graph
     * This endpoint retrieves all namespaces that have Knowledge Graph enabled
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @return List&lt;APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminKnowledgeGraphNamespacesRequestCreation() throws RestClientResponseException {
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

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/knowledge_graph/namespaces", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get all enabled namespaces for Knowledge Graph
     * This endpoint retrieves all namespaces that have Knowledge Graph enabled
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @return List&lt;APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace> getApiV4AdminKnowledgeGraphNamespaces() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminKnowledgeGraphNamespacesRequestCreation().body(localVarReturnType);
    }

    /**
     * Get all enabled namespaces for Knowledge Graph
     * This endpoint retrieves all namespaces that have Knowledge Graph enabled
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @return ResponseEntity&lt;List&lt;APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace>> getApiV4AdminKnowledgeGraphNamespacesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminKnowledgeGraphNamespacesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get all enabled namespaces for Knowledge Graph
     * This endpoint retrieves all namespaces that have Knowledge Graph enabled
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminKnowledgeGraphNamespacesWithResponseSpec() throws RestClientResponseException {
        return getApiV4AdminKnowledgeGraphNamespacesRequestCreation();
    }

    /**
     * Enable a namespace for Knowledge Graph
     * This endpoint enables Knowledge Graph for a specific namespace
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>503</b> - 503 Service Unavailable
     * @param id ID or URL-encoded path of the namespace.
     * @return APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminKnowledgeGraphNamespacesIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4AdminKnowledgeGraphNamespacesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/knowledge_graph/namespaces/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Enable a namespace for Knowledge Graph
     * This endpoint enables Knowledge Graph for a specific namespace
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>503</b> - 503 Service Unavailable
     * @param id ID or URL-encoded path of the namespace.
     * @return APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace putApiV4AdminKnowledgeGraphNamespacesId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminKnowledgeGraphNamespacesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Enable a namespace for Knowledge Graph
     * This endpoint enables Knowledge Graph for a specific namespace
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>503</b> - 503 Service Unavailable
     * @param id ID or URL-encoded path of the namespace.
     * @return ResponseEntity&lt;APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace> putApiV4AdminKnowledgeGraphNamespacesIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAnalyticsKnowledgeGraphEnabledNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminKnowledgeGraphNamespacesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Enable a namespace for Knowledge Graph
     * This endpoint enables Knowledge Graph for a specific namespace
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>503</b> - 503 Service Unavailable
     * @param id ID or URL-encoded path of the namespace.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminKnowledgeGraphNamespacesIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return putApiV4AdminKnowledgeGraphNamespacesIdRequestCreation(id);
    }
}
