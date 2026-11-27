package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesSearchZoektIndexedNamespace;
import ai.gebo.gitlab.client.model.APIEntitiesSearchZoektNode;
import ai.gebo.gitlab.client.model.APIEntitiesSearchZoektProjectIndexSuccess;
import ai.gebo.gitlab.client.model.PatchApiV4AdminZoektNamespacesIdRequest;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PutApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest;

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
public class CodeSearchApi {
    private ApiClient apiClient;

    public CodeSearchApi() {
        this(new ApiClient());
    }

    public CodeSearchApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Remove a namespace from a node for Zoekt indexing
     * Removes a namespace from a Zoekt node, deleting its indexed data and disabling search.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @param namespaceId ID of the namespace to remove from the node.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequestCreation(@jakarta.annotation.Nonnull Integer nodeId, @jakarta.annotation.Nonnull Integer namespaceId) throws RestClientResponseException {
        // verify the required parameter 'nodeId' is set
        if (nodeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'nodeId' when calling deleteApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'namespaceId' is set
        if (namespaceId == null) {
            throw new RestClientResponseException("Missing the required parameter 'namespaceId' when calling deleteApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("node_id", nodeId);
        pathParams.put("namespace_id", namespaceId);

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
        return apiClient.invokeAPI("/api/v4/admin/zoekt/shards/{node_id}/indexed_namespaces/{namespace_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove a namespace from a node for Zoekt indexing
     * Removes a namespace from a Zoekt node, deleting its indexed data and disabling search.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @param namespaceId ID of the namespace to remove from the node.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceId(@jakarta.annotation.Nonnull Integer nodeId, @jakarta.annotation.Nonnull Integer namespaceId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequestCreation(nodeId, namespaceId).body(localVarReturnType);
    }

    /**
     * Remove a namespace from a node for Zoekt indexing
     * Removes a namespace from a Zoekt node, deleting its indexed data and disabling search.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @param namespaceId ID of the namespace to remove from the node.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdWithHttpInfo(@jakarta.annotation.Nonnull Integer nodeId, @jakarta.annotation.Nonnull Integer namespaceId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequestCreation(nodeId, namespaceId).toEntity(localVarReturnType);
    }

    /**
     * Remove a namespace from a node for Zoekt indexing
     * Removes a namespace from a Zoekt node, deleting its indexed data and disabling search.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @param namespaceId ID of the namespace to remove from the node.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdWithResponseSpec(@jakarta.annotation.Nonnull Integer nodeId, @jakarta.annotation.Nonnull Integer namespaceId) throws RestClientResponseException {
        return deleteApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequestCreation(nodeId, namespaceId);
    }

    /**
     * Get all the Zoekt nodes
     * Returns a list of all Zoekt search nodes available in the instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @return List&lt;APIEntitiesSearchZoektNode&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminZoektShardsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesSearchZoektNode>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/zoekt/shards", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get all the Zoekt nodes
     * Returns a list of all Zoekt search nodes available in the instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @return List&lt;APIEntitiesSearchZoektNode&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSearchZoektNode> getApiV4AdminZoektShards() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSearchZoektNode>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminZoektShardsRequestCreation().body(localVarReturnType);
    }

    /**
     * Get all the Zoekt nodes
     * Returns a list of all Zoekt search nodes available in the instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @return ResponseEntity&lt;List&lt;APIEntitiesSearchZoektNode&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSearchZoektNode>> getApiV4AdminZoektShardsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSearchZoektNode>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminZoektShardsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get all the Zoekt nodes
     * Returns a list of all Zoekt search nodes available in the instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminZoektShardsWithResponseSpec() throws RestClientResponseException {
        return getApiV4AdminZoektShardsRequestCreation();
    }

    /**
     * Get all the indexed namespaces for this node
     * Retrieves all root namespaces indexed by the specified Zoekt search node.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @return List&lt;APIEntitiesSearchZoektIndexedNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminZoektShardsNodeIdIndexedNamespacesRequestCreation(@jakarta.annotation.Nonnull Integer nodeId) throws RestClientResponseException {
        // verify the required parameter 'nodeId' is set
        if (nodeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'nodeId' when calling getApiV4AdminZoektShardsNodeIdIndexedNamespaces", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("node_id", nodeId);

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

        ParameterizedTypeReference<List<APIEntitiesSearchZoektIndexedNamespace>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/zoekt/shards/{node_id}/indexed_namespaces", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get all the indexed namespaces for this node
     * Retrieves all root namespaces indexed by the specified Zoekt search node.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @return List&lt;APIEntitiesSearchZoektIndexedNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSearchZoektIndexedNamespace> getApiV4AdminZoektShardsNodeIdIndexedNamespaces(@jakarta.annotation.Nonnull Integer nodeId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSearchZoektIndexedNamespace>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminZoektShardsNodeIdIndexedNamespacesRequestCreation(nodeId).body(localVarReturnType);
    }

    /**
     * Get all the indexed namespaces for this node
     * Retrieves all root namespaces indexed by the specified Zoekt search node.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSearchZoektIndexedNamespace&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSearchZoektIndexedNamespace>> getApiV4AdminZoektShardsNodeIdIndexedNamespacesWithHttpInfo(@jakarta.annotation.Nonnull Integer nodeId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSearchZoektIndexedNamespace>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminZoektShardsNodeIdIndexedNamespacesRequestCreation(nodeId).toEntity(localVarReturnType);
    }

    /**
     * Get all the indexed namespaces for this node
     * Retrieves all root namespaces indexed by the specified Zoekt search node.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminZoektShardsNodeIdIndexedNamespacesWithResponseSpec(@jakarta.annotation.Nonnull Integer nodeId) throws RestClientResponseException {
        return getApiV4AdminZoektShardsNodeIdIndexedNamespacesRequestCreation(nodeId);
    }

    /**
     * Update the number of replicas override for an enabled namespace
     * Updates the replication factor override for a namespace enabled in the Zoekt search engine.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param patchApiV4AdminZoektNamespacesIdRequest The patchApiV4AdminZoektNamespacesIdRequest parameter
     * @return APIEntitiesSearchZoektIndexedNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4AdminZoektNamespacesIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4AdminZoektNamespacesIdRequest patchApiV4AdminZoektNamespacesIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4AdminZoektNamespacesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesSearchZoektIndexedNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/zoekt/namespaces/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4AdminZoektNamespacesIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update the number of replicas override for an enabled namespace
     * Updates the replication factor override for a namespace enabled in the Zoekt search engine.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param patchApiV4AdminZoektNamespacesIdRequest The patchApiV4AdminZoektNamespacesIdRequest parameter
     * @return APIEntitiesSearchZoektIndexedNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSearchZoektIndexedNamespace patchApiV4AdminZoektNamespacesId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4AdminZoektNamespacesIdRequest patchApiV4AdminZoektNamespacesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSearchZoektIndexedNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4AdminZoektNamespacesIdRequestCreation(id, patchApiV4AdminZoektNamespacesIdRequest).body(localVarReturnType);
    }

    /**
     * Update the number of replicas override for an enabled namespace
     * Updates the replication factor override for a namespace enabled in the Zoekt search engine.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param patchApiV4AdminZoektNamespacesIdRequest The patchApiV4AdminZoektNamespacesIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSearchZoektIndexedNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSearchZoektIndexedNamespace> patchApiV4AdminZoektNamespacesIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4AdminZoektNamespacesIdRequest patchApiV4AdminZoektNamespacesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSearchZoektIndexedNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4AdminZoektNamespacesIdRequestCreation(id, patchApiV4AdminZoektNamespacesIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update the number of replicas override for an enabled namespace
     * Updates the replication factor override for a namespace enabled in the Zoekt search engine.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @param id ID or URL-encoded path of the namespace.
     * @param patchApiV4AdminZoektNamespacesIdRequest The patchApiV4AdminZoektNamespacesIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4AdminZoektNamespacesIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4AdminZoektNamespacesIdRequest patchApiV4AdminZoektNamespacesIdRequest) throws RestClientResponseException {
        return patchApiV4AdminZoektNamespacesIdRequestCreation(id, patchApiV4AdminZoektNamespacesIdRequest);
    }

    /**
     * Triggers indexing for the specified project
     * Triggers background indexing of the specified project using the Zoekt search engine.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project to index.
     * @return APIEntitiesSearchZoektProjectIndexSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminZoektProjectsProjectIdIndexRequestCreation(@jakarta.annotation.Nonnull Integer projectId) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling putApiV4AdminZoektProjectsProjectIdIndex", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("project_id", projectId);

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

        ParameterizedTypeReference<APIEntitiesSearchZoektProjectIndexSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/zoekt/projects/{project_id}/index", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Triggers indexing for the specified project
     * Triggers background indexing of the specified project using the Zoekt search engine.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project to index.
     * @return APIEntitiesSearchZoektProjectIndexSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSearchZoektProjectIndexSuccess putApiV4AdminZoektProjectsProjectIdIndex(@jakarta.annotation.Nonnull Integer projectId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSearchZoektProjectIndexSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminZoektProjectsProjectIdIndexRequestCreation(projectId).body(localVarReturnType);
    }

    /**
     * Triggers indexing for the specified project
     * Triggers background indexing of the specified project using the Zoekt search engine.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project to index.
     * @return ResponseEntity&lt;APIEntitiesSearchZoektProjectIndexSuccess&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSearchZoektProjectIndexSuccess> putApiV4AdminZoektProjectsProjectIdIndexWithHttpInfo(@jakarta.annotation.Nonnull Integer projectId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSearchZoektProjectIndexSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminZoektProjectsProjectIdIndexRequestCreation(projectId).toEntity(localVarReturnType);
    }

    /**
     * Triggers indexing for the specified project
     * Triggers background indexing of the specified project using the Zoekt search engine.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project to index.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminZoektProjectsProjectIdIndexWithResponseSpec(@jakarta.annotation.Nonnull Integer projectId) throws RestClientResponseException {
        return putApiV4AdminZoektProjectsProjectIdIndexRequestCreation(projectId);
    }

    /**
     * Add a namespace to a node for Zoekt indexing
     * Enables and assigns a namespace to a specific Zoekt node for code search indexing.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @param namespaceId ID of the namespace to index on the node.
     * @param putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest The putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest parameter
     * @return APIEntitiesSearchZoektIndexedNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequestCreation(@jakarta.annotation.Nonnull Integer nodeId, @jakarta.annotation.Nonnull Integer namespaceId, @jakarta.annotation.Nullable PutApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest) throws RestClientResponseException {
        // verify the required parameter 'nodeId' is set
        if (nodeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'nodeId' when calling putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'namespaceId' is set
        if (namespaceId == null) {
            throw new RestClientResponseException("Missing the required parameter 'namespaceId' when calling putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("node_id", nodeId);
        pathParams.put("namespace_id", namespaceId);

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

        ParameterizedTypeReference<APIEntitiesSearchZoektIndexedNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/zoekt/shards/{node_id}/indexed_namespaces/{namespace_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a namespace to a node for Zoekt indexing
     * Enables and assigns a namespace to a specific Zoekt node for code search indexing.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @param namespaceId ID of the namespace to index on the node.
     * @param putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest The putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest parameter
     * @return APIEntitiesSearchZoektIndexedNamespace
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSearchZoektIndexedNamespace putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceId(@jakarta.annotation.Nonnull Integer nodeId, @jakarta.annotation.Nonnull Integer namespaceId, @jakarta.annotation.Nullable PutApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSearchZoektIndexedNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequestCreation(nodeId, namespaceId, putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest).body(localVarReturnType);
    }

    /**
     * Add a namespace to a node for Zoekt indexing
     * Enables and assigns a namespace to a specific Zoekt node for code search indexing.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @param namespaceId ID of the namespace to index on the node.
     * @param putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest The putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSearchZoektIndexedNamespace&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSearchZoektIndexedNamespace> putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdWithHttpInfo(@jakarta.annotation.Nonnull Integer nodeId, @jakarta.annotation.Nonnull Integer namespaceId, @jakarta.annotation.Nullable PutApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSearchZoektIndexedNamespace> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequestCreation(nodeId, namespaceId, putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a namespace to a node for Zoekt indexing
     * Enables and assigns a namespace to a specific Zoekt node for code search indexing.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param nodeId ID of the Zoekt node.
     * @param namespaceId ID of the namespace to index on the node.
     * @param putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest The putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdWithResponseSpec(@jakarta.annotation.Nonnull Integer nodeId, @jakarta.annotation.Nonnull Integer namespaceId, @jakarta.annotation.Nullable PutApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest) throws RestClientResponseException {
        return putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequestCreation(nodeId, namespaceId, putApiV4AdminZoektShardsNodeIdIndexedNamespacesNamespaceIdRequest);
    }
}
